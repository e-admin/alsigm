package ieci.tecdoc.sgm.tram.secretaria.rules.decretos;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaResourcesHelper;

/**
 * Comprueba si el decreto no está generado
 * en caso contrario no permite lleva a cabo la operación
 * @author IECISA
 *
 */
public class CheckNumDecretoEmptyRule implements IRule {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(CheckNumDecretoEmptyRule.class);

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
			IEntitiesAPI entitiesAPI = rctx.getClientContext().getAPI().getEntitiesAPI();
			IItemCollection itemcol=entitiesAPI.getEntities(SecretariaConstants.ENTITY_DECRETO, numexp);
			if(itemcol.next()){
				IItem decreto=itemcol.value();
				if(StringUtils.isNotBlank(decreto.getString(SecretariaConstants.FIELD_DECRETO_NUM_DECRETO))){
					if(logger.isDebugEnabled()){
						logger.debug("No se puede eliminar el expediente de decretos: " +numexp+" porque ya tiene número de decreto generado");
					}
					//Si hay que hacer mas comprobación de operaciones permitidas o no en base al num decreto habri que especificar
					//el mensaje en las reglas que hereden de checknumdecretoempty
					 rctx.setInfoMessage(SecretariaResourcesHelper.getMessage(rctx.getClientContext().getLocale(), "aviso.not.allowed.delete.exp.decreto") );

					return false;
				}
			}
			else{
				//Si no hay registro no hay núm decreto generado
				if(logger.isDebugEnabled()){
					logger.debug("Se está intentando eliminar el expediente de decretos:" +numexp+" que no tiene ningún dato en la entidad decretos");
				}

			}

		} catch (ISPACException e) {
			logger.error("Error en la regla " + getClass().getName(), e);
			throw new ISPACRuleException(e);
		}
		return true;

	}

}
