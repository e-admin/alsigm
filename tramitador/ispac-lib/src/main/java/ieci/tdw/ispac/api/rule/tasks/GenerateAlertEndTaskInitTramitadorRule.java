package ieci.tdw.ispac.api.rule.tasks;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispactx.TXConstants;

public class GenerateAlertEndTaskInitTramitadorRule extends GenerateAlertEndTaskRule {

	@Override
	protected String getResponsible(IRuleContext rulectx)
			throws ISPACException {
		
		IInvesflowAPI invesFlowAPI = rulectx.getClientContext().getAPI();

		IItemCollection itemcol = invesFlowAPI.getMilestones(rulectx.getProcessId(), rulectx.getStageProcedureId(), rulectx.getTaskProcedureId(), rulectx.getTaskId(), TXConstants.MILESTONE_TASK_START);
		if (itemcol.next()){
			return itemcol.value().getString("AUTOR");
		}
		return null;
	}				
}