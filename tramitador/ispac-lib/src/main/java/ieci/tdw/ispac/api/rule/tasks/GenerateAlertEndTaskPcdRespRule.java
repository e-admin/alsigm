package ieci.tdw.ispac.api.rule.tasks;

import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.rule.IRuleContext;

public class GenerateAlertEndTaskPcdRespRule extends GenerateAlertEndTaskRule {

	@Override
	protected String getResponsible(IRuleContext rulectx)
			throws ISPACException {
		
		IProcedureAPI procedureAPI = rulectx.getClientContext().getAPI().getProcedureAPI();
		IItem procedure = procedureAPI.getProcedureById(rulectx.getProcedureId());
		
		return  procedure.getString("PPROCEDIMIENTOS:ID_RESP");
	}

}
