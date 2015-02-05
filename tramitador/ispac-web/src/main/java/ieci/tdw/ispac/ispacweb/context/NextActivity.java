package ieci.tdw.ispac.ispacweb.context;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class NextActivity
{
	public NextActivity()
	{
	}

	/**
	 * Devuelve al contexto inicial
	 */
	public static ActionForward initialContext (
			ActionMapping mapping) {
		return mapping.findForward("showmain");
	}

	/**
	 * Refresca el estado actual
	 */	
	public static ActionForward refresh(IState iState,
										ActionMapping mapping) {
		
		ActionForward action = mapping.findForward(iState.getLabel());
		
		return new ActionForward(action.getName(),
			action.getPath() + ((iState.getQueryString()== null) ? "" : iState.getQueryString()),true); 
	}
	
	public static ActionForward refresh(HttpServletRequest request,
										ActionMapping mapping,
										IState iState) {
		
		String queryString = request.getQueryString();
		ActionForward action = mapping.findForward(iState.getLabel());
		
		String path = action.getPath();
		if (iState.getQueryString() != null) {
			if (iState.getQueryString().equals(ManagerState.PARAM_QUERYSTRING)) {
				if (!StringUtils.isEmpty(queryString)) {
				path = path + "?" + queryString;
				}
			}
			else {
				// Evitar bucles al volver a estados anteriores
				if ((path.indexOf(mapping.getPath()) != -1) &&
					(iState.getQueryString().indexOf(queryString) != -1)) {
					
					// El mismo action con los mismos parámetros se está repitiendo (bucle infinito)
					// Ir a la página principal
					request.setAttribute(Globals.MESSAGE_KEY, null);
					return mapping.findForward("showmain");
				}
				
				path = path + iState.getQueryString();
			}
		}
		
		return new ActionForward(action.getName(), path, true);
	}

	/**
	 * Acción después de iniciar un expediente
	 */
	public static ActionForward afterStartProcess (HttpServletRequest request, int processId,
			IInvesflowAPI invesflowAPI,
			ActionMapping mapping)
			throws ISPACException {

		return afterCloseStage(request, processId,
				invesflowAPI,mapping);
	}

	/**
	 * Acción después de cerrar una fase
	 */
	public static ActionForward afterCloseStage(HttpServletRequest request, 
												int processId,
												IInvesflowAPI invesflowAPI,
												ActionMapping mapping) throws ISPACException {
		
		IWorklistAPI workListAPI = invesflowAPI.getWorkListAPI();
		
		// Responsabilidades del usuario conectado
		String resp = workListAPI.getRespString();

		// 1º: Buscar cualquier fase activa en el expediente
		IItemCollection activeStagesSet = workListAPI.findActiveStages(processId, resp);
    	if (activeStagesSet.next())
    	{
    		int idstage = activeStagesSet.value().getInt("ID_STAGE");
			ActionForward showexp = mapping.findForward("showexp");
			return new ActionForward(showexp.getName(),
				showexp.getPath()+"?stageId="+idstage,true);
    	}
    	
    	/*
    	// 2º: Buscar cualquier trámite activo en el expediente
    	IItemCollection activeTasksSet = workListAPI.findActiveTasks(processId, resp);
		if (activeTasksSet.next())
		{
			int idtask = activeTasksSet.value().getInt("ID");
			ActionForward showtask = mapping.findForward("showtask");
			return new ActionForward(showtask.getName(),
				showtask.getPath()+"?taskId="+idtask,true);
		}
		*/

		// 3º: Volver a la pantalla inicial
		IProcess process = invesflowAPI.getProcess(processId);
		// Comprobar si el expediente se ha cerrado al cerrar la fase,
		// o es que no se tiene responsabilidad sobre el expediente
		ISPACInfo e = null;
		if (process.getInt("ESTADO") == IProcess.CLOSED) {
			
			e = new ISPACInfo("exception.expedients.closeStage.statusClosed", new Object[]{process.getString("NUMEXP")});
		}
		else {
			e = new ISPACInfo("exception.expedients.responsability", new Object[]{process.getString("NUMEXP")});
		}
		request.setAttribute(Globals.MESSAGE_KEY, e);
		request.getSession().setAttribute("infoAlert", e);
		ActionForward showmain = mapping.findForward("showmain");
		request.setAttribute("refresh", showmain.getPath());
		return mapping.findForward("refresh");
	}

	/**
	 * Acción después de cerrar una actividad de un subproceso
	 */
	public static ActionForward afterCloseActivity(HttpServletRequest request, 
												   IState state,
												   IInvesflowAPI invesflowAPI,
												   ActionMapping mapping) throws ISPACException {
		
		IWorklistAPI workListAPI = invesflowAPI.getWorkListAPI();
		
		// Responsabilidades del usuario conectado
		//La fase ya se ha cerrado por lo que se indica el id del procedimiento al que pertenece
		String resp = workListAPI.getRespStringSubProceso(0 , state.getPcdId());

		// 1º: Buscar cualquier actividad activa en el subproceso
		IItemCollection activeStagesSet = workListAPI.findActiveStages(state.getSubProcessId(), resp);
    	if (activeStagesSet.next())
    	{
    		int idactivity = activeStagesSet.value().getInt("ID_STAGE");
			ActionForward showsubprocess = mapping.findForward("showsubprocess");
			return new ActionForward(showsubprocess.getName(),
					showsubprocess.getPath()+"?activityId="+idactivity,true);
    	}

		// 2º: Volver a la fase del trámite del subproceso
		IProcess process = invesflowAPI.getProcess(state.getSubProcessId());
		// Comprobar si el subproceso se ha cerrado al cerrar la actividad para cerrar también el trámite,
		// o es que no se tiene responsabilidad sobre el subproceso
		if (process.getInt("ESTADO") == IProcess.CLOSED) {
			
			ISPACInfo e = new ISPACInfo("exception.subprocess.closeActivity.statusClosed", new Object[]{process.getString("NUMEXP")});
			request.setAttribute(Globals.MESSAGE_KEY, e);
			
			// Cerrar el trámite del subproceso
			ActionForward closetask = mapping.findForward("closeTask");
			request.setAttribute("refresh", closetask.getPath() + "?idsTask=" + state.getTaskId());
			return mapping.findForward("refresh");			
		}
		else {
			ISPACInfo e = new ISPACInfo("exception.subprocess.responsability", new Object[]{process.getString("NUMEXP")});
			request.setAttribute(Globals.MESSAGE_KEY,e);
			
			// Ir a la fase del trámite del subproceso
			ActionForward showexp = mapping.findForward("showexp");
			request.setAttribute("refresh", showexp.getPath() + "?stageId=" + state.getStageId());
			return mapping.findForward("refresh");
		}
	}
	
	/**
	 * Acción después de haber cerrado un trámite
	 */
	public static ActionForward afterCloseTask(int processId,
											   IState state,
											   IInvesflowAPI invesflowAPI,
											   ActionMapping mapping) throws ISPACException {
		
		// Ir a la fase del trámite
		int idstage = state.getStageId();
		
		ActionForward showexp = mapping.findForward("showexp");
		return new ActionForward(showexp.getName(), showexp.getPath() + "?stageId=" + idstage, true);
		
		/*
		IWorklistAPI workListAPI = invesflowAPI.getWorkListAPI();
		
		// Responsabilidades del usuario conectado
		String resp = workListAPI.getRespString();
		
		// 1º: Intentar navegar a otro trámite del expediente
		IItemCollection activeTasksSet = workListAPI.findActiveTasks(processId, resp);
		if (activeTasksSet.next())
		{
			int idtask = activeTasksSet.value().getInt("ID");
			ActionForward showtask = mapping.findForward("showtask");
			return new ActionForward(showtask.getName(),
				showtask.getPath()+"?taskId="+idtask,true);
		}

		// 2º: Intentar navegar a una fase activa
		IItemCollection activeStagesSet = workListAPI.findActiveStages(processId, resp);
    	if (activeStagesSet.next())
    	{
    		int idstage = activeStagesSet.value().getInt("ID_STAGE");
			ActionForward showexp = mapping.findForward("showexp");
			return new ActionForward(showexp.getName(),
				showexp.getPath()+"?stageId="+idstage,true);
    	}

    	// 3º: Volver a la pantalla inicial
		return initialContext(mapping);
		*/
	}

	/**
	 * Acción después de haber iniciado un trámite
	 */
	public static ActionForward afterCreateTask (int processId,
												 int stageId,
												 int taskId,
												 IInvesflowAPI invesflowAPI,
												 ActionMapping mapping) throws ISPACException {
		
		IItemCollection activeTasksSet = invesflowAPI.getWorkListAPI().findActiveTasks(processId);

		// 1º: Intentar navegar al trámite recién creado
		Iterator it = activeTasksSet.iterator();

		while (it.hasNext()) {
			IItem item = (IItem)it.next();
			if (taskId == item.getInt("ID")) {
				ActionForward showtask =mapping.findForward("showtask");
				return new ActionForward(showtask.getName(),
					showtask.getPath()+"?taskId="+taskId,true);
			}
		}

		// 2º: El trámite es ajeno --> Permanecer en la fase
		ActionForward showexp =mapping.findForward("showexp");
		return new ActionForward(showexp.getName(),	showexp.getPath()+"?stageId="+stageId,true);
	}

	public static ActionForward afterCreateBatchTask (int batchTaskId,
			ActionMapping mapping) throws ISPACException {
		
		ActionForward forward = mapping.findForward("showBatchTask");
		return new ActionForward(forward.getName(),
				forward.getPath()+"?batchTaskId="+batchTaskId,
				true);
	}

	/**
	 * Acción después de crear un documento en una fase
	 */
	public static ActionForward afterGenerateDocumentInStage ( int entityId,
			int keyId,
			int stageId,
			ActionMapping mapping)
			throws ISPACException {

		ActionForward action = mapping.findForward("showexp");
		return new ActionForward(action.getName(),
			action.getPath()+"?entity="+entityId+"&key="+keyId+
			"&stageId="+stageId,true);
	}
	
	/**
	 * Acción después de crear un documento en un trámite
	 */
	public static ActionForward afterGenerateDocumentInTask ( int entityId,
			int keyId,
			int taskId,
			ActionMapping mapping)
			throws ISPACException {

		ActionForward action = mapping.findForward("showtask");
		return new ActionForward(action.getName(),
			action.getPath()+"?entity="+entityId+"&key="+keyId+
			"&taskId="+taskId,true);
	}
	
	/**
	 * Acción después de delegar
	 */
	public static ActionForward afterDelegate(HttpServletRequest request,
											  IState state,
											  ActionMapping mapping) throws ISPACException {
		
		String url = null;
		
		// Delegación de una actividad o un trámite
		if ((state.getState() == ManagerState.SUBPROCESS) ||
			(state.getState() == ManagerState.TASK)) {
			
			// Ir a la fase del trámite
    		int idstage = state.getStageId();
    		
			ActionForward showexp = mapping.findForward("showexp");
			url = showexp.getPath() + "?stageId=" + idstage;
		}
		else {
			ActionForward showmain = mapping.findForward("showmain");
			url = showmain.getPath();			
		}
		
		// Como la delegación se hace desde el workframe
		// la acción de después de delegar se ejecuta sobre la ventana principal
		request.setAttribute("target", "top");
		// Url sin la / inicial
		request.setAttribute("url", url.substring(1, url.length()));
		
		return mapping.findForward("loadOnTarget");
	}
	
    public static ActionForward afterRedeployProcess(HttpServletRequest request, int processId, IInvesflowAPI invesflowAPI, ActionMapping mapping)
    throws ISPACException
    {
    	return afterCloseStage(request, processId, invesflowAPI, mapping);
    }

	public static ActionForward afterCreateBatchTask(String batchTaskId,
 			ActionMapping mapping) {
  		ActionForward forward = mapping.findForward("showBatchTask");
  		return new ActionForward(forward.getName(), forward.getPath()
 				+ "?batchTaskId=" + batchTaskId, true);
 	}

	/**
	 * Acción después de haber cerrado un trámite de subproceso
	 */
	public static ActionForward afterCloseComplexTask(HttpServletRequest request, 
													  IState state, 
													  IInvesflowAPI invesflowAPI, 
													  ActionMapping mapping) throws ISPACException {
		
		IWorklistAPI workListAPI = invesflowAPI.getWorkListAPI();
		
		// Responsabilidades del usuario conectado
		String resp = workListAPI.getRespString();

		// 1º: Intentar navegar a una fase activa
		IItemCollection activeStagesSet = workListAPI.findActiveStages(state.getProcessId(), resp);
    	if (activeStagesSet.next())
    	{
    		int idstage = activeStagesSet.value().getInt("ID_STAGE");
			ActionForward showexp = mapping.findForward("showexp");
			return new ActionForward(showexp.getName(),
				showexp.getPath()+"?stageId="+idstage,true);
    	}

    	// 2º: Volver a la pantalla inicial
		return initialContext(mapping);
	}

	public static ActionForward afterCreateSubProcess(int idProcess, int activityId, IInvesflowAPI invesflowAPI, ActionMapping mapping) {
		ActionForward showsubprocess = mapping.findForward("showsubprocess");
		return new ActionForward(showsubprocess.getName(), showsubprocess.getPath()+"?activityId="+activityId,true);
	}
	
}