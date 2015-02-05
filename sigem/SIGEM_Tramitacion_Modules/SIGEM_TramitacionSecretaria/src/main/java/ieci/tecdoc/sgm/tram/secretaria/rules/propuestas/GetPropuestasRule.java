package ieci.tecdoc.sgm.tram.secretaria.rules.propuestas;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.IItemHelper;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaResourcesHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * Se obtienen las propuesta del tipo ordinarias o urgente con las que este relacionado el expediente actual
 * @author IECISA
 *
 */
public abstract class GetPropuestasRule implements IRule{

	protected static final Logger logger = Logger.getLogger(GetPropuestasRule.class);
	public void cancel(IRuleContext rulectx) throws ISPACRuleException {

	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		if(logger.isDebugEnabled()){
			logger.debug("Vamos a obtener las propuestas de tipo "+getTipoPropuesta() +"y su participantes y tramitador");
		}
		//Se debe ejecutar en un contexto transacional pero la regla ya está dentro de un contexto transacional
		//Obtengo las propuestas relacionadas con el expediente
		IClientContext ctx =rulectx.getClientContext();
		IInvesflowAPI invesflowAPI = ctx.getAPI();
		IItem propuesta=null;
		String numexpPropuesta="";
		IItem participante=null;
		IItem item=null;
		int orden_propuesta=2;
		int idParticipante, idPropuesta;
		IItemCollection participantes=null;
		try {
			IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
			String query = " WHERE "
					+ SecretariaConstants.ENTITY_PROPUESTA
					+ ".NUMEXP IN (SELECT NUMEXP_HIJO FROM SPAC_EXP_RELACIONADOS WHERE SPAC_EXP_RELACIONADOS.NUMEXP_PADRE='"
					+ DBUtil.replaceQuotes(rulectx.getNumExp()) + "') AND "
					+ SecretariaConstants.ENTITY_PROPUESTA + "."
					+ SecretariaConstants.FIELD_PROPUESTA_TIPO_PROPUESTA + "='"
					+ DBUtil.replaceQuotes(getTipoPropuesta()) + "'";
			IItemCollection propuestas = entitiesAPI.queryEntities(
					SecretariaConstants.ENTITY_PROPUESTA, query);
			if(!propuestas.next()){
				rulectx.setInfoMessage(SecretariaResourcesHelper.getMessage(rulectx.getClientContext().getLocale(), "propuesta.empty.import"));
				return null;
			}
			//Obtenemos la propuesta sesion ordenadas desc para el expediente actual FALTA FILTRAR POR EL EXPEDIENTE ACTUAL
			IItemCollection propuestasSesionStored=entitiesAPI.queryEntities(getTablePropuesta(), "  WHERE NUMEXP='"+DBUtil.replaceQuotes(rulectx.getNumExp())+"'ORDER BY ORDEN DESC");
			if(propuestasSesionStored.next()){
				String orden=((IItem)propuestasSesionStored.value()).getString("ORDEN");
				orden_propuesta=Integer.parseInt(orden)+1;
			}

			//Obtenego los participantes de la propuesta e incorporo los datos
			while(propuestas.next()){
				propuesta=propuestas.value();
			    idPropuesta=propuesta.getInt("ID");
				numexpPropuesta=propuesta.getString("NUMEXP");
				if(logger.isDebugEnabled()){
					logger.debug("Vamos a obtener los participantes de la propuesta con numexp: "+numexpPropuesta);
				}

				//Obtenemos todos los participantes salvo el tramitador
				participantes=entitiesAPI.getEntities("SPAC_DT_INTERVINIENTES", numexpPropuesta);
				//Incorporamos los participantes al expediente actual
				while(participantes.next()){
					item=participantes.value();
					participante=entitiesAPI.createEntity("SPAC_DT_INTERVINIENTES", rulectx.getNumExp());
					IItemHelper.copyParticipantes(item, participante,"");
					//El tramitador de la propuesta se ha de identificar mediante el rol
					if(StringUtils.equals(SecretariaConstants.ROL_TRAMITADOR, item.getString("ROL"))){
						participante.set("ROL", SecretariaConstants.ROL_TRAMITADOR_PROPUESTA);
					}
					else{
						participante.set("ROL", SecretariaConstants.ROL_NOTIFICADO);

					}


					participante.store(ctx);
					idParticipante=participante.getInt("ID");
					//Vinculamos la información adicional de la propuesta con el id de la propuesta en conc_propuesta
					//y el id del participante que acabamos de crear
					participante=entitiesAPI.createEntity(getTableParticipantes(), rulectx.getNumExp());
					participante.set("ID_PARTICIPANTE", idParticipante);
					participante.set("ID_PROPUESTA", idPropuesta);
					participante.set("ID_PARTICIPANTE_PROPUESTA", item.get("ID"));
					participante.store(ctx);


				}


				//Vinculamos la propuesta con los datos adicionales
				item=entitiesAPI.createEntity(getTablePropuesta(), rulectx.getNumExp());
				item.set("ID_PROPUESTA", idPropuesta);
				item.set("ORDEN", orden_propuesta);
				item.store(ctx);
				orden_propuesta++;

			}
		} catch (Exception e) {
			logger.error("Error en la ejecución de la regla GetPropuestasRule para el tipo "+getTipoPropuesta()+e);
			throw new ISPACRuleException(e);
		}
		return null;
	}

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public abstract String getTipoPropuesta();
	public abstract String getTablePropuesta();
	public abstract String getTableParticipantes();

}
