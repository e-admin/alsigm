package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispacmgr.action.form.BatchForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeleteBatchTasksAction extends BaseAction  {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		// Tramitaciones agrupadas seleccionadas
		String[] multibox = ((BatchForm)form).getMultibox();
		if (!ArrayUtils.isEmpty(multibox)) {
			
			// API de transacciones
			ITXTransaction transactionAPI = session.getAPI().getTransactionAPI();
			
			// Eliminar las tramitaciones agrupadas
			for (int i = 0; i < multibox.length; i++) {
				try {
					int batchTaskId = TypeConverter.parseInt(multibox[i], -1);
					if (batchTaskId > 0) {

						IInvesflowAPI invesflowAPI = session.getAPI();
						
						IItem batchTask = invesflowAPI.getBatchTask(batchTaskId);
						
						// El usuario conectado tiene que tener responsabilidad para delegar
						if(!session.getAPI().getWorkListAPI().isInResponsibleList(batchTask.getString("ID_RESP") , batchTask)) {
							throw new ISPACInfo("exception.expedients.batchTask.delete.noResp", new String[] {batchTask.getString("FECHA_CREACION")});
						}

						transactionAPI.deleteBatchTask(batchTaskId);
					}
				} catch (ISPACInfo e) {
					logger.warn("Error al eliminar la tramitación agrupada: " 
							+ multibox[i], e);
					throw e;
				} catch (Exception e) {
					logger.error("Error al eliminar la tramitación agrupada: " 
							+ multibox[i], e);
					throw new ISPACInfo(e);
				}
			}
		}
			
		return mapping.findForward("success");
	}
}
