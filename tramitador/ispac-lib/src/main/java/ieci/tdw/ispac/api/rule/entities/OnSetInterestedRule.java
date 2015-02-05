package ieci.tdw.ispac.api.rule.entities;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.common.constants.InformationMilestonesConstants;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

public class OnSetInterestedRule implements IRule {

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		String numExp = rulectx.getNumExp();
		try{
			IEntitiesAPI entitiesAPI = rulectx.getClientContext().getAPI().getEntitiesAPI();
			ITXTransaction txAPI = rulectx.getClientContext().getAPI().getTransactionAPI();
			IItem entityNewValues = rulectx.getItem();
			IItem entityOldValues = entitiesAPI.getExpedient(numExp);
			if ( !StringUtils.equalsIgnoreCase(entityNewValues.getString("NIFCIFTITULAR"), entityOldValues.getString("NIFCIFTITULAR")) ){
				//Insertamos un hito indicando que se ha cambiado el interesado principal en el expediente
				txAPI.newMilestone(entityNewValues.getInt("IDPROCESO"), 0, 0, InformationMilestonesConstants.CHANGE_MAIN_INTERESTED, null, "Cambio de interesado principal");	
			}
		}catch (ISPACException e) {
			throw new ISPACRuleException(e);
		}
		return null;
	}

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {

	}
}