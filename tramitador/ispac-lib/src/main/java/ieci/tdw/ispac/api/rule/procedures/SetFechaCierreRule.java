package ieci.tdw.ispac.api.rule.procedures;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Date;

/**
 * Regla que establece la fecha de cierre de un expediente.
 * Esta regla debería asociarse al evento de fin de un expediente (Tipo de Objeto: Procedimiento , Evento: Terminar),
 * o también podría asociarse al iniciar la fase de Archivo.
 *
 */
public class SetFechaCierreRule implements IRule {

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		
		try{
			IEntitiesAPI entitiesAPI = rulectx.getClientContext().getAPI().getEntitiesAPI();
			IItem item = entitiesAPI.getExpedient(rulectx.getNumExp());
			if ((item != null) && StringUtils.isBlank(item.getString("FCIERRE"))) {
				item.set("FCIERRE", new Date());
				item.store(rulectx.getClientContext());
			}
		}catch(ISPACException e){
			throw new ISPACRuleException(e);
		}
		return null;
	}

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {

	}

}
