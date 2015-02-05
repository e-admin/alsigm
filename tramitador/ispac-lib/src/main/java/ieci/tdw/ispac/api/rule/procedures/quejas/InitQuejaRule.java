package ieci.tdw.ispac.api.rule.procedures.quejas;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;

import org.apache.log4j.Logger;

public class InitQuejaRule implements IRule {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(InitQuejaRule.class);

	private final int ENTITY_ID = 201;
	
	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		try {
			//Creamos un registro de la entidad de Reclamaciones, Sugerencias y Quejas y se establecen valores
			IEntitiesAPI entitiesAPI = rulectx.getClientContext().getAPI().getEntitiesAPI();

			// Comprobar si ya se ha creado el registro
			IItemCollection collection = entitiesAPI.getEntities(ENTITY_ID, rulectx.getNumExp());
			if (!collection.next()) {
				IItem item = entitiesAPI.createEntity(ENTITY_ID);
				item.set("NUMEXP", rulectx.getNumExp());
				item.store(rulectx.getClientContext());
			}
			
		} catch (ISPACException e) {
			logger.error("Error en la regla " + getClass().getName(), e);
			throw new ISPACRuleException(e);
		}
		return null;
	}

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
	}


}
