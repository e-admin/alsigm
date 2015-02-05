package ieci.tdw.ispac.api.rule.docs;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.api.rule.helper.RuleHelper;
import ieci.tdw.ispac.ispaclib.context.IClientContext;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public abstract class DocumentNoDeleteRule  implements IRule {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(DocumentNoDeleteRule.class);

	public void cancel(IRuleContext arg0) throws ISPACRuleException {


	}

	public Object execute(IRuleContext arg0) throws ISPACRuleException {

		return null;
	}

	public boolean init(IRuleContext arg0) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {

		//Comprobamos si el documento que se está intentado eliminar es sobre el que queremos aplicar la restricción(código)
		try {
			IClientContext ctx= rulectx.getClientContext();
    		ICatalogAPI catalogAPI =ctx.getAPI().getCatalogAPI();

    		String idTpDoc=catalogAPI.getIdTpDocByCode(getCodTipoDocumental());
    		IItem documento=rulectx.getItem();
    		if(StringUtils.equals(documento.getString("ID_TPDOC"),idTpDoc)){
    		   if(onlySignedDocument()){
    			   	//Comprobamos si el documento ya ha sido firmado (infopag_rde informando) independientemente de si esta pendiente
    			   // de circuito de firma o no
    			   if(StringUtils.isNotBlank(documento.getString("INFOPAG_RDE"))){
        			   rulectx.setInfoMessage( getMessage(rulectx.getClientContext().getLocale()));
    			     return false;
    			   }
    		   }
    		   else{
    			   rulectx.setInfoMessage(getMessage(rulectx.getClientContext().getLocale()));
    			   return false;
    		   }
    		}

		} catch (Exception e) {
			logger.error("DocumentNoDeleteRule :validate"+e.getMessage());

		}


		return true;
	}



	/**
	 * Indica si la restricción de eliminar el documento se aplica sólamente a los documento firmados,
	 * es decir si se intenta eliminar el documento sin firmar la regla lo permitiría, en caso de ser
	 * un documento firmado mostraría un mensaje de error.
	 * @return
	 */
	public boolean onlySignedDocument(){
		return true;
	}

	public abstract String getCodTipoDocumental();

	public String getMessage(Locale locale){

		return RuleHelper.getMessage(locale,"aviso.cantdelete.document");
	}


}
