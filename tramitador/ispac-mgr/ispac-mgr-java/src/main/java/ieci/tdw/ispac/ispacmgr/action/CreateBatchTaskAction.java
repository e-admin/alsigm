package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispacmgr.action.form.BatchForm;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.IWorklist;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.context.NextActivity;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Crea una nueva tramitación agrupada con los expedientes seleccionados.
 *
 */
public class CreateBatchTaskAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

	  	ClientContext cct = session.getClientContext();
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance()
			.getManagerAPI(cct);
		IState state = managerAPI.currentState(getStateticket(request));

		IInvesflowAPI invesflowAPI = session.getAPI();
    	ITXTransaction tx = invesflowAPI.getTransactionAPI();
		int batchTaskId = tx.createBatchTask(state.getStagePcdId(), 
				getNumExps(form, state, managerAPI.getWorklistAPI()));
		
		return NextActivity.afterCreateBatchTask(batchTaskId, mapping);
	}
	
	private String [] getNumExps(ActionForm form, IState state, 
			IWorklist workList) throws ISPACException {
		
		List numExps = new ArrayList();
		
		// Identificadores de los expedientes seleccionados
		String [] stageIds = ((BatchForm) form).getMultibox();

		// Lista de expedientes
	    IItemCollection itc = workList.getProcesses(state);
	    List exps = CollectionBean.getBeanList(itc);
	    if (!CollectionUtils.isEmpty(exps)) {
	    	ItemBean itemBean;
	    	for (int i = 0; i < exps.size(); i++) {
	    		itemBean = (ItemBean) exps.get(i);
	    		if (ArrayUtils.contains(stageIds, String.valueOf(
	    				itemBean.getProperty("ID_STAGE")))) {
	    			numExps.add(itemBean.getProperty("NUMEXP"));
	    		}
	    	}
	    }

    	return (String []) numExps.toArray(new String[0]);
	}
}