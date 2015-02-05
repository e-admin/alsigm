package ieci.tdw.ispac.ispacweb.api.impl;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerState;
import ieci.tdw.ispac.ispacweb.header.ContextHeader;

import java.util.Locale;

import org.apache.struts.util.MessageResources;

public class ContextHeaderFactory {
	
	private ClientContext mcct;
	private String PROPERTY_CURRENTSTATE = "CURRENTSTATE";
	
	public ContextHeaderFactory(ClientContext cct)
	{
		this.mcct = cct;
	}
	
	public ContextHeader createProcedureHeader (IState state) 
	throws ISPACException
	{
		ContextHeader contextHeader ;
		int pcdId = state.getPcdId();
		if (pcdId == 0)
			return null;
		
		IProcedure iProcedure = mcct.getAPI().getProcedure(pcdId);
		
		String queryString = "?" + ManagerState.PARAM_PCDID + "=" + pcdId;
		contextHeader = new ContextHeader(iProcedure.getString("NOMBRE"),
				ManagerState.LABEL_STAGESLIST, queryString);
		
		//Ponemos que el CURRENTSTATE es 2, para que el Nombre del Procedmiento no se muestre como enlace en la miga de pan
		/*
		contextHeader.set(PROPERTY_CURRENTSTATE, 0);
		
		if (state.getState() == ManagerState.STAGESLIST) {
			contextHeader.set(PROPERTY_CURRENTSTATE, 1);
		}
		*/
		
		contextHeader.set(PROPERTY_CURRENTSTATE, 2);

		return contextHeader;	
	}
	
	public ContextHeader createStagePcdHeader (IState state) 
	throws ISPACException
	{
		ContextHeader contextHeader ;
		int pcdId = state.getPcdId();
		int stagePcdId = state.getStagePcdId();
		if (stagePcdId == 0)
			return null;
		
		IProcedure iProcedure = mcct.getAPI().getProcedure(pcdId);
		
		String queryString = "?" + ManagerState.PARAM_STAGEPCDID + "=" + stagePcdId;
		contextHeader = new ContextHeader(iProcedure.getStage(stagePcdId).getString("NOMBRE"),
				ManagerState.LABEL_PROCESSLIST, queryString);
		
		contextHeader.set(PROPERTY_CURRENTSTATE, 0);
		
		if (state.getState() == ManagerState.PROCESSESLIST) {
			contextHeader.set(PROPERTY_CURRENTSTATE, 1);
		}
		
		return contextHeader;	
	}
	
	public ContextHeader createStagePcdHeader (IState state, String stagePcdIdS) 
	throws ISPACException
	{
		ContextHeader contextHeader ;
		int pcdId = state.getPcdId();
		int stagePcdId = state.getStagePcdId();
		if(StringUtils.isNotEmpty(stagePcdIdS)){
			stagePcdId=Integer.parseInt(stagePcdIdS);
		}
		if (stagePcdId == 0)
			return null;
		
		IProcedure iProcedure = mcct.getAPI().getProcedure(pcdId);
		
		String queryString = "?" + ManagerState.PARAM_STAGEPCDID + "=" + stagePcdId;
		contextHeader = new ContextHeader(iProcedure.getStage(stagePcdId).getString("NOMBRE"),
				ManagerState.LABEL_PROCESSLIST, queryString);
		
		contextHeader.set(PROPERTY_CURRENTSTATE, 0);
		
		if (state.getState() == ManagerState.PROCESSESLIST) {
			contextHeader.set(PROPERTY_CURRENTSTATE, 1);
		}
		
		return contextHeader;	
	}
	
	public ContextHeader createTaskCtlHeader (IState state) 
	throws ISPACException
	{
		ContextHeader contextHeader;
		int taskCtlId = state.getTaskCtlId();
		
		if (taskCtlId == 0)	return null;
		
		IItem item = mcct.getAPI().getProcedureTaskCTL(taskCtlId);
		
		String queryString = "?" + ManagerState.PARAM_TASKCTLID + "=" + taskCtlId + "&" + ManagerState.PARAM_PCDID + "=" + state.getPcdId();
		
		String label = ManagerState.LABEL_TASKSLIST;
		if (state.getState() == ManagerState.CLOSEDTASKSLIST || (state.getReadonly() && state.getReadonlyReason() == 0)){
			label = ManagerState.LABEL_CLOSEDTASKSLIST;
		}
		
		contextHeader = new ContextHeader(item.getString("NOMBRE"),label, queryString);
		
		contextHeader.set(PROPERTY_CURRENTSTATE, 0);
		
		if ((state.getState() == ManagerState.TASKSLIST) || 
			(state.getState() == ManagerState.CLOSEDTASKSLIST)) {
			contextHeader.set(PROPERTY_CURRENTSTATE, 1);
		}
		else if ((state.getState() == ManagerState.SUBPROCESSESLIST) ||
				 (state.getState() == ManagerState.SUBPROCESS)) {
			contextHeader.set(PROPERTY_CURRENTSTATE, 2);
		}

		
		return contextHeader;		
	}
	
	public ContextHeader createBatchTaskCtlHeader(IState state, Locale locale, MessageResources resources)
	throws ISPACException
	{
		ContextHeader contextHeader;
		int batchTaskCtlId = state.getBatchTaskId();
		
		if (batchTaskCtlId == 0)	return null;
		
		IItem item = mcct.getAPI().getBatchTask(batchTaskCtlId);

		contextHeader = new ContextHeader(resources.getMessage(locale, "forms.batchTaskForm.context", new String[] {item.getString("FASE"), item.getString("PROCEDIMIENTO")}),
				ManagerState.LABEL_BATCHTASKSLIST, "");
		
		contextHeader.set(PROPERTY_CURRENTSTATE, 1);

		/*
		if (state.getState() == ManagerState.BATCHTASKLIST) {
			contextHeader.set(PROPERTY_CURRENTSTATE, 1);
		}
		*/
			
		return contextHeader;
	}
	
	public ContextHeader createStageHeader (IState state) 
	throws ISPACException
	{
		ContextHeader contextHeader;
		int stageId = state.getStageId();
		if (stageId == 0)
			return null;
		String numExpedient = state.getNumexp();
		IEntitiesAPI entitiesAPI = mcct.getAPI().getEntitiesAPI();
		
		String queryString = "?" + ManagerState.PARAM_STAGEID + "=" + stageId;
		contextHeader = new ContextHeader(entitiesAPI.getExpedient(numExpedient).getString("NUMEXP"),
				ManagerState.LABEL_EXPEDIENT, queryString);
		
		contextHeader.set(PROPERTY_CURRENTSTATE, 0);
		
		if ((state.getState() == ManagerState.EXPEDIENT) ||
			(state.getState() == ManagerState.TASK) ||
			(state.getState() == ManagerState.SUBPROCESS)) {
			
			contextHeader.set(PROPERTY_CURRENTSTATE, 1);
		}
		
		return contextHeader;	
	}

	public ContextHeader createSubProcedureHeader(IState state)
	throws ISPACException
	{
		ContextHeader contextHeader ;
		int subPcdId = state.getSubPcdId();
		if (subPcdId == 0)
			return null;
		
		IProcedure iProcedure = mcct.getAPI().getProcedure(subPcdId);
		
		String queryString = "?" + ManagerState.PARAM_SUBPCDID + "=" + subPcdId;
		contextHeader = new ContextHeader(iProcedure.getString("NOMBRE"),
				ManagerState.LABEL_ACTIVITIESLIST, queryString);
		
		//Ponemos que el CURRENTSTATE es 2, para que el Nombre del Procedmiento no se muestre como enlace en la miga de pan
		/*
		contextHeader.set(PROPERTY_CURRENTSTATE, 0);
		
		if (state.getState() == ManagerState.STAGESLIST) {
			contextHeader.set(PROPERTY_CURRENTSTATE, 1);
		}
		*/
		
		contextHeader.set(PROPERTY_CURRENTSTATE, 2);

		return contextHeader;	
	}

	public ContextHeader createActivityPcdHeader(IState state) 
	throws ISPACException{
		ContextHeader contextHeader ;
		int subPcdId = state.getSubPcdId();
		int activityPcdId = state.getActivityPcdId();
		if (activityPcdId == 0)
			return null;
		int taskCtlId = state.getTaskCtlId();
		IProcedure iProcedure = mcct.getAPI().getProcedure(subPcdId);
		
		String queryString = "?" + ManagerState.PARAM_ACTIVITYPCDID + "=" + activityPcdId + "&" + ManagerState.PARAM_TASKCTLID + "=" + taskCtlId;
		contextHeader = new ContextHeader(iProcedure.getStage(activityPcdId).getString("NOMBRE"),
				ManagerState.LABEL_SUBPROCESSLIST, queryString);
		
		contextHeader.set(PROPERTY_CURRENTSTATE, 0);
		
		if (state.getState() == ManagerState.SUBPROCESSESLIST) {
			contextHeader.set(PROPERTY_CURRENTSTATE, 1);
		}
		
		return contextHeader;	
	}
	
}