package ieci.tecdoc.sgm.tram.secretaria.rules.libro;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaResourcesHelper;

import org.apache.log4j.Logger;



/**
 * Se comprueba si se ha informado el campo num_hoja_ini
 *
 */
public class CheckNumHojaIniRule implements IRule {

	private static final Logger logger = Logger.getLogger(CheckNumHojaIniRule.class);

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {

	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		return null;
	}

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {

		String numExp = rulectx.getNumExp();
		if(logger.isDebugEnabled()){
			logger.debug("Ejecutamos CheckNumHojaIniRule: validate");
		}
		try {
				String numHojaIni="";
				IEntitiesAPI entitiesAPI = rulectx.getClientContext().getAPI().getEntitiesAPI();
				IItemCollection itemcol= entitiesAPI.getEntities(SecretariaConstants.ENTITY_LIBRO, numExp);
				IItem libro=itemcol.value();
				numHojaIni=libro.getString(SecretariaConstants.FIELD_LIBRO_NUM_HOJA_INI);
				if(StringUtils.isBlank(numHojaIni)){
					if(logger.isDebugEnabled()){
						logger.debug("Núm. Hoja Inicial vacía");
					}
					String message =  SecretariaResourcesHelper.getMessage(rulectx.getClientContext().getLocale(),"numHojaIni.empty");
					rulectx.setInfoMessage(" "+message);
					return false;
				}

		} catch (ISPACException e) {
			rulectx.setInfoMessage(e.getMessage());
			return false;
		}

		return true;
	}

}
