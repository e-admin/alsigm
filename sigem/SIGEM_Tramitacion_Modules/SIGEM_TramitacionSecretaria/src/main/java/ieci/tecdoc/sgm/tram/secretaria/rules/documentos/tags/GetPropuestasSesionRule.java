package ieci.tecdoc.sgm.tram.secretaria.rules.documentos.tags;

import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Obtiene las propuestas de la sesion en funcion el tipo de propuesta

 * @author iecisa
 *
 */
public abstract class GetPropuestasSesionRule implements IRule {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(GetPropuestasSesionRule.class);

	protected IClientContext ctx =null;
	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		String tipoPropuesta = rulectx.get("tipoPropuesta");
		String query="";
		ctx =rulectx.getClientContext();
		try {
			TableJoinFactoryDAO tableJoinFactoryDAO = new TableJoinFactoryDAO();
			tableJoinFactoryDAO.addTable(SecretariaConstants.ENTITY_PROPUESTA,
					SecretariaConstants.ENTITY_PROPUESTA);
			tableJoinFactoryDAO.addTable(
					SecretariaConstants.ENTITY_PROPUESTA_SESION,
					SecretariaConstants.ENTITY_PROPUESTA_SESION);
			if (StringUtils.isNotBlank(tipoPropuesta)) {
				if(logger.isDebugEnabled()){
					logger.debug("Vamos a obtener las propuesta con tipoPropuesta a "+tipoPropuesta);
				}
				query = " AND " + SecretariaConstants.ENTITY_PROPUESTA + "."
						+ SecretariaConstants.FIELD_PROPUESTA_TIPO_PROPUESTA
						+ "='" + DBUtil.replaceQuotes(tipoPropuesta) + "'";
			}
			IItemCollection propuestasSesion = tableJoinFactoryDAO
					.queryTableJoin(
							ctx.getConnection(),
							" WHERE  "
									+ SecretariaConstants.ENTITY_PROPUESTA
									+ ".ID="
									+ SecretariaConstants.ENTITY_PROPUESTA_SESION
									+ "."
									+ SecretariaConstants.FIELD_PROPUESTA_SESION_ID_PROPUESTA
									+ " AND "
									+ SecretariaConstants.ENTITY_PROPUESTA_SESION
									+ ".NUMEXP='"
									+ DBUtil.replaceQuotes(rulectx.getNumExp())
									+ "'" + query+" ORDER BY "+ SecretariaConstants.ENTITY_PROPUESTA_SESION+
									"."+SecretariaConstants.FIELD_PROPUESTA_SESION_ORDEN+" ASC").disconnect();


			return composeResult(propuestasSesion);
		} catch (Exception e) {
			logger.error("Error en GetPropuestasSesionRule:execute", e);
			throw new ISPACRuleException("Error GetPropuestasSesionRule:execute"+e);
		}





	}


	public abstract String composeResult (IItemCollection itemcol)throws Exception;

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {

		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {

		return true;
	}

}
