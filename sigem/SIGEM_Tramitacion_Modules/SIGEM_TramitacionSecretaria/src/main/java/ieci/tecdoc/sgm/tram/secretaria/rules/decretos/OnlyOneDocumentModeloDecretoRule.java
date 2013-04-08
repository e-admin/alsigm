package ieci.tecdoc.sgm.tram.secretaria.rules.decretos;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.api.rule.RuleProperties;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Comprobar si ya hay documento de tipo documental modelo decreto.
 * En caso afirmativo mostrar  mensaje informativo de que no se pueden
 * anexar/generar más documentos de dicho tipo documental.
 * @author IECISA
 *
 */
public class OnlyOneDocumentModeloDecretoRule implements IRule {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(OnlyOneDocumentModeloDecretoRule.class);

	public void cancel(IRuleContext rctx) throws ISPACRuleException {

	}

	public Object execute(IRuleContext rctx) throws ISPACRuleException {

		return null;
	}

	public boolean init(IRuleContext rctx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rctx) throws ISPACRuleException {
		IClientContext ctx= rctx.getClientContext();
		try {
			String numexp=rctx.getNumExp();
			IInvesflowAPI invesflowAPI=ctx.getAPI();
			ICatalogAPI catalogAPI = invesflowAPI.getCatalogAPI();
			String idTpdocModeloDecreto=catalogAPI.getIdTpDocByCode(SecretariaConstants.COD_TPDOC_MODELO_DECRETO);
			String idTpdocDocumento=rctx.get(RuleProperties.RCTX_DOCUMENTTYPE);

			if(StringUtils.equalsIgnoreCase(idTpdocDocumento, idTpdocModeloDecreto)){
				//Comprobamos si ya hay documentos.
				IEntitiesAPI entitiesAPI=invesflowAPI.getEntitiesAPI();
				String idDocumento=rctx.get(RuleProperties.RCTX_DOCUMENTID);
				StringBuffer sql = new StringBuffer();
				sql.append(" WHERE NUMEXP='")
						.append(DBUtil.replaceQuotes(numexp)).append("' AND ID_TPDOC=")
						.append(idTpdocModeloDecreto)
						.append(" AND ID!=")
						.append(idDocumento);
				int contador=entitiesAPI.countEntities(SpacEntities.SPAC_DT_DOCUMENTOS, sql.toString());
				if(contador>0){
					return false;
				}

			}


		} catch (ISPACException e) {
			logger.warn("Error en la regla SetNumDecretoRule", e);
			throw new ISPACRuleException(e);
		}

		return true;

	}


}
