package ieci.tdw.ispac.api.rule.docs;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.common.constants.SignStatesConstants;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;



/**
 * Regla abstracta
 * Comprueba si el documento está firmado y en caso
 * que no sea así muestra un mensaje informativo y no permite llevar a cabo la acción
 *
 * La regla que extienda de ésta ha de especificar el mensaje informativo a mostrar
 * @author IECISA
 *
 */
public abstract class CheckDocumentSignedRule implements IRule {
	/**
     * Logger de la clase.
     */
    private static final Logger logger = Logger.getLogger(CheckDocumentSignedRule.class);

	public void cancel(IRuleContext rctx) throws ISPACRuleException {


	}

	public Object execute(IRuleContext rctx) throws ISPACRuleException {

		return null;
	}

	public boolean init(IRuleContext rctx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rctx) throws ISPACRuleException {

		try{
			if(logger.isDebugEnabled()){
				logger.debug("Inicio regla... "+this.getClass());
			}
			String numexp=rctx.getNumExp();
			IClientContext ctx=rctx.getClientContext();
			IInvesflowAPI invesFlowAPI= ctx.getAPI();
			IEntitiesAPI entitiesAPI= invesFlowAPI.getEntitiesAPI();
			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
			StringBuffer sql = new StringBuffer();
			String idTpDoc=catalogAPI.getIdTpDocByCode(getNameVarCodTipoDocumental());
			if(StringUtils.isNotBlank(idTpDoc)){
			sql.append(" WHERE NUMEXP='")
				.append(DBUtil.replaceQuotes(numexp)).append("' AND ID_TPDOC=")
				.append(idTpDoc)
				.append(" AND ESTADOFIRMA='")
				.append(DBUtil.replaceQuotes(SignStatesConstants.FIRMADO))
				.append("'");
				int contador=entitiesAPI.countEntities(SpacEntities.SPAC_DT_DOCUMENTOS, sql.toString());
				if(contador>0){
					if(logger.isDebugEnabled()){
						logger.debug("Fin regla... "+this.getClass()+" Documento de diligencia de apertura firmado  encontrado");
					}
					return true;
				}

			}
		} catch (Exception e) {
			logger.error(this.getClass()+""+e);
			throw new ISPACRuleException(e);
		}

		if(logger.isDebugEnabled()){
			logger.debug("Fin regla... "+this.getClass()+" Documento de diligencia de apertura firmado NO encontrado");
		}
		rctx.setInfoMessage(getMessage(rctx.getClientContext().getLocale()));
		return false;
	}

	public abstract String getMessage(Locale locale);
    public abstract  String getNameVarCodTipoDocumental();


}
