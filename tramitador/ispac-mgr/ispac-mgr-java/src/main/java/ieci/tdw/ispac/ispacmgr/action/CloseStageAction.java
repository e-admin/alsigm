package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;
import ieci.tdw.ispac.ispacweb.context.NextActivity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CloseStageAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form, 
									   HttpServletRequest request,
									   HttpServletResponse response, 
									   SessionAPI session) throws Exception {
		
		ClientContext cct = session.getClientContext();
		
		IInvesflowAPI invesflowAPI = session.getAPI();
		ITXTransaction tx = invesflowAPI.getTransactionAPI();
		IWorklistAPI worklistAPI = invesflowAPI.getWorkListAPI();
		
		// Estado del contexto de tramitación
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState currentstate = managerAPI.currentState(getStateticket(request));
		
		String idsStage = request.getParameter("idsStage");
		if (StringUtils.isEmpty(idsStage))
			idsStage = request.getParameter("idsActivity");
			
		String[] stageids = idsStage.split("-");

		int nidstage = 0;
		int nIdProcess = 0;
		int nIdPcdStage = 0;
		
		StringBuffer message = new StringBuffer();
		String[] args = new String[stageids.length];
		
		for (int i = 0; i < stageids.length; i++) {
			
			IStage stage = null;
			nidstage = Integer.parseInt(stageids[i]);
			
			// En el caso de cerrar una sola fase de un expediente se intentará
			// seguir trabajando con el expediente
			//if (stageids.length == 1) {
			//	nidstage = Integer.parseInt(stageids[0]);
			
			stage = invesflowAPI.getStage(nidstage);

			if(!worklistAPI.isInResponsibleList(stage.getString("ID_RESP"), stage)) {
				message.append(getMessage(request, cct.getLocale(), "errors.close.state.resp", new Object[]{stage.getString("NUMEXP")})+"<br/>");
				continue;
			}
			
			nIdProcess = stage.getInt("ID_EXP");
			nIdPcdStage = stage.getInt("ID_FASE");
			
			//Comprobamos que el usuario conectado puede cerrar la fase
			//}
			
			try {
				
				// Información del expediente en el contexto
				StateContext stateContext = cct.getStateContext();
				stateContext.setNumexp(stage.getString("NUMEXP"));
				stateContext.setProcessId(nIdProcess);
				stateContext.setStagePcdId(nIdPcdStage);
				stateContext.setStageId(stage.getKeyInt());

				tx.deployNextStage(nIdProcess,nIdPcdStage, nidstage);
			}
			catch (ISPACInfo e) {

				String msg = e.getExtendedMessage(request.getLocale());
				if (msg != null) {
					
					if (stage == null) {
						stage = invesflowAPI.getStage(nidstage);
					}
					
					msg = StringUtils.replace(msg, "{0}", "{" + i + "}");
					args[i] = stage.getString("NUMEXP");
				}
				
				message.append(msg)
					   .append("<br/><br/>");
			}
		}
		
		/*if (message.length() > 0) {
			throw new ISPACInfo(message.toString(), args,false);
		}*/
		
		ISPACInfo info=null;
		if (message.length() > 0) {
			info=new ISPACInfo(message.toString(), args,false);
			request.getSession().setAttribute("infoAlert", info);
		}

		// En el caso de cerrar una sola fase o una actividad de un expediente se intentará
		// seguir trabajando con el expediente
		if ((stageids.length == 1) && (nIdProcess != 0)){
			IProcess process = invesflowAPI.getProcess(nIdProcess);
			if (process.isSubProcess() && currentstate.getState() ==  ManagerState.SUBPROCESS)
				return NextActivity.afterCloseActivity(request, currentstate, invesflowAPI, mapping);
			if (currentstate.getState() == ManagerState.EXPEDIENT)
				return NextActivity.afterCloseStage(request, nIdProcess, invesflowAPI, mapping);
		}
		
		return NextActivity.refresh(request, mapping, currentstate);
	}
	
}