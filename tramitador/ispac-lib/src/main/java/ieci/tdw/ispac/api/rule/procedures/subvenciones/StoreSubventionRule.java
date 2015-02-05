package ieci.tdw.ispac.api.rule.procedures.subvenciones;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;

public class StoreSubventionRule implements IRule {

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		
		try {
			/*
			IEntitiesAPI entitiesAPI = rulectx.getClientContext().getAPI().getEntitiesAPI();
			int entityId = rulectx.getInt("entityId");
			int keyId = rulectx.getInt("keyId");
			
			IItem item =  entitiesAPI.getEntity(entityId, keyId);
			*/
			
			IItem item = rulectx.getItem();
			
			// Antes de guardar los datos se actualiza el valor del total aportacion, que sera igual a la suma de aportaciones
			String field = "";
			double total = 0;
			field = "APORTACION_AYTO";
			total += (item.get(field) != null)  ? item.getDouble(field) : 0 ;
			field = "APORTACION_ENTIDAD";
			total += (item.get(field) != null)  ? item.getDouble(field) : 0 ;
			field = "OTRAS_APORTACIONES";
			total += (item.get(field) != null)  ? item.getDouble(field) : 0 ;
			item.set("TOTAL_APORTACIONES", total);
			
			//item.store(rulectx.getClientContext());
		}
		catch(ISPACException e){
			throw new ISPACRuleException(e);
		}
		
		return null;
	}

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
	}

}