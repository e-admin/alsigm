package ieci.tecdoc.sgm.tram.secretaria.rules.documentos.tags;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Obtiene el campo de  diligencia informativa asociada al trámite actual
 * Posibles valores de la propiedad property:
 * 1-fecha
 * 2-num_dec_inic
 * 3-num_dec_fin
 *
 * En el caso que se quiere obtener la fecha se podrá indica el formato mediante la propiedad
 * dateformat
 * @author iecisa
 *
 */
public class GetDiligenciaInformativaRule implements IRule {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(GetDiligenciaInformativaRule.class);
	public void cancel(IRuleContext rulectx) throws ISPACRuleException {


	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		String field = rulectx.get("property");
		String dateFormat=rulectx.get("dateformat");


		String valor="";
		IClientContext ctx =rulectx.getClientContext();
		int taskId=ctx.getStateContext().getTaskId();
		try {
			IInvesflowAPI invesflowAPI = ctx.getAPI();
			IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
			IItemCollection itemcol=entitiesAPI.queryEntities(SecretariaConstants.ENTITY_DILIGENCIAS_INFORMATIVAS,
									" WHERE NUMEXP='"+rulectx.getNumExp()+"' AND "+
									SecretariaConstants.FIELD_DILIGENCIAS_ID_TRAMITE+"="+taskId);

			if (StringUtils.isBlank(field)) {
				if(logger.isDebugEnabled()){
					logger.debug("No se ha especificado propiedad luego el valor será vacío");
				}

			}
			if(itemcol.next()){
				if (StringUtils.isNotBlank(dateFormat)){
					valor=TypeConverter.toString(itemcol.value().getDate(field),dateFormat);

				}
				else{
					valor=itemcol.value().getString(field);
				}


			}
			else{
				if(logger.isDebugEnabled()){
					logger.debug("No hay registro de la entidad "+
							SecretariaConstants.ENTITY_DILIGENCIAS_INFORMATIVAS+" para el trámite con id "+taskId+
								" del expediente "+rulectx.getNumExp());
				}
			}

			return valor;

		} catch (Exception e) {
			logger.error("Error en GetDiligenciaInformativaRule:execute", e);
			throw new ISPACRuleException("Error GetDiligenciaInformativaRule:execute"+e);
		}

	}

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {

		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {

		return true;
	}

}
