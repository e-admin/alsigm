package ieci.tdw.ispac.ispacmgr.rules.procedures;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;

/**
 * Regla para inicar registro de entidad para Certificados de instalacion de Calefaccion y Gas para DEMO.
 *
 */
public class InitCCGInstalacionRule implements IRule {
	
	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		try {
			IEntitiesAPI entitiesAPI = rulectx.getClientContext().getAPI().getEntitiesAPI();
			IItem item = entitiesAPI.createEntity(115);
			item.set("NUMEXP", rulectx.getNumExp());
			item.store(rulectx.getClientContext());
		} catch (ISPACException e) {
			throw new ISPACRuleException(e);
		}
		return null;
	}


	public void cancel(IRuleContext rulectx) throws ISPACRuleException {

	}

}
