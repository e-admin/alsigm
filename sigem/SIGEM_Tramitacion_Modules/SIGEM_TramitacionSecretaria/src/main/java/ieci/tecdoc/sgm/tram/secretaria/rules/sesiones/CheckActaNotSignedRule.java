package ieci.tecdoc.sgm.tram.secretaria.rules.sesiones;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaResourcesHelper;

import org.apache.log4j.Logger;

/**
 * Comprueba si el acta de la sesión no está generada ,
 * en caso contrario no permite eliminarla
 *
 */
public abstract class CheckActaNotSignedRule implements IRule {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(CheckActaNotSignedRule.class);

	public void cancel(IRuleContext arg0) throws ISPACRuleException {

	}

	public Object execute(IRuleContext arg0) throws ISPACRuleException {
		return null;
	}

	public boolean init(IRuleContext arg0) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rctx) throws ISPACRuleException {
		try{
			String numexp=rctx.getNumExp();
			IClientContext ctx=rctx.getClientContext();
			IInvesflowAPI invesflowAPI =ctx.getAPI();
			IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();


    		String idTpDocActa=invesflowAPI.getCatalogAPI().getIdTpDocByCode(getCodTipoDocumental());
    		int numActas=entitiesAPI.countEntities(SpacEntities.SPAC_DT_DOCUMENTOS,
    				" WHERE ID_TPDOC="+idTpDocActa+" AND  NUMEXP='"+DBUtil.replaceQuotes(numexp)+"' " +
    				" AND  INFOPAG_RDE IS NOT NULL");
    		if(numActas>0){
    			rctx.setInfoMessage(SecretariaResourcesHelper.getMessage(rctx.getClientContext().getLocale(),getKeyMessage()) );
    			return false;
    		}
    		if(logger.isDebugEnabled()){
    			logger.debug("Eliminamos el expediente "+numexp+" ya que no tiene el acta firmada");
    		}


		} catch (ISPACException e) {
			logger.error("Error en la regla " + getClass().getName(), e);
			throw new ISPACRuleException(e);
		}
		return true;

	}

	public abstract String getCodTipoDocumental() ;
	public abstract String getKeyMessage();


}
