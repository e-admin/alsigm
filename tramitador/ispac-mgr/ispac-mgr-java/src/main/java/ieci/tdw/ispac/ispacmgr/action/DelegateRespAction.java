package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispacmgr.action.form.UsersForm;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.context.NextActivity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DelegateRespAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form,
									   HttpServletRequest request, 
									   HttpServletResponse response,
									   SessionAPI session) throws Exception	{
		
		ClientContext cct = session.getClientContext();
		
		IInvesflowAPI invesflowAPI = session.getAPI();
		ITXTransaction tx = invesflowAPI.getTransactionAPI();
		IRespManagerAPI respAPI = invesflowAPI.getRespManagerAPI();
		
		// Estado del contexto de tramitación desde el que se delega
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState state = managerAPI.currentState(getStateticket(request));
		
		// Responsable seleccionado
		UsersForm usersForm = (UsersForm) form;
		String resp = usersForm.getUid();
		
		//Calculamos le nombre del nuevo responsable
		//Nota: El nombre se calcula en este punto ,no dentro de cada txdelegateTask o Stage porque si son varios
		//trámites o fases se estaria realizando tantas veces la misma consulta como trámites o fases tengamos
		String nameResp=((Responsible)respAPI.getResp(resp)).getName();
		
		
		String idsStage = request.getParameter("idsStage");
		String idsTask = request.getParameter("idsTask");
		
		// Delegar varias fases o trámites
		String[] ids = null;
		if (idsStage != null) {
			ids = idsStage.split("-");
		}
		else if (idsTask != null){
			ids = idsTask.split("-");
		}
		
		StringBuffer message = new StringBuffer();
		String[] args = new String[ids.length];

		for (int i=0; ids != null && i < ids.length; i++) {
			
			int id = Integer.parseInt(ids[i]);
			
			try {
				if (idsStage != null && !idsStage.equals("")) {
					
					//Información del expediente en el contexto
					IStage stage = invesflowAPI.getStage(id);
					StateContext stateContext = cct.getStateContext();
					stateContext.setNumexp(stage.getString("NUMEXP"));
					stateContext.setProcessId(stage.getInt("ID_EXP"));
					stateContext.setStagePcdId(stage.getInt("ID_FASE"));
					stateContext.setStageId(stage.getKeyInt());

					// El usuario conectado tiene que tener responsabilidad para delegar
					if(!invesflowAPI.getWorkListAPI().isInResponsibleList(stage.getString("ID_RESP"), stage)) {
						throw new ISPACInfo("errors.delegate.state.resp", new String[] {stage.getString("NUMEXP")});
					}

					// Delegar fase
					tx.delegateStage(id,resp,nameResp);
				}
				else {

					// Información del trámite en el contexto
					ITask task = invesflowAPI.getTask(id);
					StateContext stateContext = cct.getStateContext();
					stateContext.setNumexp(task.getString("NUMEXP"));
					stateContext.setProcessId(task.getInt("ID_EXP"));
					stateContext.setTaskCtlId(task.getInt("ID_CTTRAMITE"));
					stateContext.setTaskPcdId(task.getInt("ID_TRAMITE"));
					stateContext.setTaskId(task.getKeyInt());
					stateContext.setPcdId(task.getInt("ID_PCD"));
					stateContext.setStageId(task.getInt("ID_FASE_EXP"));
					stateContext.setStagePcdId(task.getInt("ID_FASE_PCD"));

					// El usuario conectado tiene que tener responsabilidad para delegar
					if(!invesflowAPI.getWorkListAPI().isInResponsibleList(task.getString("ID_RESP") , task)) {
						throw new ISPACInfo("errors.delegate.task.resp", new String[] {task.getString("NUMEXP")});
					}
					
					// Delegar trámite
					tx.delegateTask(id,resp, nameResp);
				}
			}
			catch (ISPACInfo e) {

				String msg = e.getExtendedMessage(request.getLocale());
				if (msg != null) {
					
					message.append(getMessage(request, cct.getLocale(), msg, e.getArgs())+"<br/>");
				}
			}
		}
		
		if (message.length() > 0) {
			throw new ISPACInfo(message.toString(), args, false);
		}
		
//		ISPACInfo info=null;
//		if (message.length() > 0) {
//			info=new ISPACInfo(message.toString(), args,false);
//			request.getSession().setAttribute("infoAlert", info);
//		}
		
		return NextActivity.afterDelegate(request, state, mapping);
	}
	
}