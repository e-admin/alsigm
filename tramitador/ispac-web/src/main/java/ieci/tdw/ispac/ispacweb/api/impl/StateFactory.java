package ieci.tdw.ispac.ispacweb.api.impl;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerState;
import ieci.tdw.ispac.ispacweb.api.impl.states.BatchSignDocumentState;
import ieci.tdw.ispac.ispacweb.api.impl.states.BatchTaskListState;
import ieci.tdw.ispac.ispacweb.api.impl.states.BatchTaskState;
import ieci.tdw.ispac.ispacweb.api.impl.states.ClosedTasksListState;
import ieci.tdw.ispac.ispacweb.api.impl.states.CreateProcessState;
import ieci.tdw.ispac.ispacweb.api.impl.states.DataState;
import ieci.tdw.ispac.ispacweb.api.impl.states.DelegateState;
import ieci.tdw.ispac.ispacweb.api.impl.states.ExpedientState;
import ieci.tdw.ispac.ispacweb.api.impl.states.ExpsTrashListState;
import ieci.tdw.ispac.ispacweb.api.impl.states.IntrayState;
import ieci.tdw.ispac.ispacweb.api.impl.states.LoginState;
import ieci.tdw.ispac.ispacweb.api.impl.states.NoticeState;
import ieci.tdw.ispac.ispacweb.api.impl.states.ProcedureListState;
import ieci.tdw.ispac.ispacweb.api.impl.states.ProcessListState;
import ieci.tdw.ispac.ispacweb.api.impl.states.SearchResultsState;
import ieci.tdw.ispac.ispacweb.api.impl.states.SearchState;
import ieci.tdw.ispac.ispacweb.api.impl.states.SignDocumentState;
import ieci.tdw.ispac.ispacweb.api.impl.states.StagesListState;
import ieci.tdw.ispac.ispacweb.api.impl.states.SubProcessListState;
import ieci.tdw.ispac.ispacweb.api.impl.states.SubProcessState;
import ieci.tdw.ispac.ispacweb.api.impl.states.TaskState;
import ieci.tdw.ispac.ispacweb.api.impl.states.TasksListState;
import ieci.tdw.ispac.ispacweb.api.impl.states.TermsState;

import java.util.Map;


public class StateFactory {
	
	public StateFactory () {
		super();
	}
	
	/**
	 * Crea el estado correcto
	 * @param stateContext StateContext
	 * @return IState estado
	 */
	public IState createState (String stateticket) throws ISPACException {
		
		StateContext stateContext = new StateContext(stateticket);
		
		int state = stateContext.getState();
		switch (state) {
			case ManagerState.LOGIN:
			{
				LoginState loginState = new LoginState(stateContext);
				return loginState;
			}
			case ManagerState.PROCEDURELIST:
			{
				ProcedureListState pcdListState = 
					new ProcedureListState(stateContext);
				return pcdListState;
			}
			case ManagerState.STAGESLIST:
			{
				StagesListState stagesListState =
					new StagesListState(stateContext);
				return stagesListState;
			}
			case ManagerState.PROCESSESLIST:
			{
				ProcessListState processState = 
					new ProcessListState(stateContext);
				return processState;
			}
			case ManagerState.SUBPROCESSESLIST:
			{
				SubProcessListState subProcessState = 
					new SubProcessListState(stateContext);
				return subProcessState;
			}
			case ManagerState.TASKSLIST:
			{
				TasksListState tasksListState = 
					new TasksListState(stateContext);
				return tasksListState;
			}
			case ManagerState.CLOSEDTASKSLIST:
			{
				ClosedTasksListState closedTasksListState = 
					new ClosedTasksListState(stateContext);
				return closedTasksListState;
			}

			case ManagerState.EXPEDIENT:
			{
				ExpedientState expState =
					new ExpedientState(stateContext);
				return expState;
			}
			case ManagerState.SUBPROCESS:
			{	
				SubProcessState subProcessState =
					new SubProcessState(stateContext);
				return subProcessState;
			}
			case ManagerState.TASK:
			{
				TaskState taskState = 
					new TaskState(stateContext);
				return taskState;
			}
			case ManagerState.BATCHTASKLIST:
			{	
				BatchTaskListState batchTaskListState = 
					new BatchTaskListState(stateContext);
				return batchTaskListState;
			}
			case ManagerState.BATCHTASK:
			{	
				BatchTaskState batchTaskState = 
					new BatchTaskState(stateContext);
				return batchTaskState;
			}
			case ManagerState.CREATEPROCESSLIST:
			{
				CreateProcessState createProcState = 
					new CreateProcessState(stateContext);
				return createProcState;
			}
			case ManagerState.INTRAYLIST:
			{
				IntrayState intrayState =
					new IntrayState(stateContext);
				return intrayState;
			}
			case ManagerState.NOTICELIST:
			{
				NoticeState noticeState =
					new NoticeState(stateContext);
				return noticeState;
			}
			case ManagerState.DELEGATE:
			{
				DelegateState delegateState = 
					new DelegateState(stateContext);
				return delegateState;
			}
			case ManagerState.DATA:
			{
				DataState dataState = 
					new DataState(stateContext);
				return dataState;
			}
			case ManagerState.TERMS:
			{
				TermsState termsState = 
					new TermsState(stateContext);
				return termsState;
			}
			case ManagerState.SIGNDOCUMENT:
			{
				SignDocumentState signDocumentState = 
					new SignDocumentState(stateContext);
				return signDocumentState;
			}
			case ManagerState.BATCHSIGNLIST:
			{
				BatchSignDocumentState batchSignDocumentState = 
					new BatchSignDocumentState(stateContext);
				return batchSignDocumentState;
			}
			case ManagerState.SEARCH:
			{	
				SearchState searchState =
					new SearchState (stateContext);
				return searchState;
			}
			case ManagerState.SEARCHRESULTS:
			{	
				SearchResultsState searchResultState =
					new SearchResultsState (stateContext);
				return searchResultState;
			}	
			case ManagerState.EXPSTRASHLIST: 
			{ 
				  ExpsTrashListState expsTrashListState = 
				  new ExpsTrashListState (stateContext); 
				  return expsTrashListState; 
			} 
		}
		throw new ISPACException("Error en StateFactory::createState(): " +
				"No se puede crear ningún estado");
	}
	
	/**
	 * Crea el estado correcto
	 * @param state  identidicador de estado
	 * @param params parámetros a guardar en el contexto
	 * @return IState estado
	 */
	public IState createState (int state, Map params, 
			IClientContext cct) throws ISPACException {
		switch (state) {
			case ManagerState.LOGIN:
			{
				LoginState loginState = new LoginState(state);
				return loginState;
			}
			case ManagerState.PROCEDURELIST:
			{
				ProcedureListState pcdListState = 
					new ProcedureListState(state);
				return pcdListState;
			}
			case ManagerState.STAGESLIST:
			{
				StagesListState stagesListState =
					new StagesListState(state,params);
				return stagesListState;
			}
			case ManagerState.PROCESSESLIST:
			{	
				ProcessListState processState = 
					new ProcessListState(state,params, cct);
				return processState;
			}
			case ManagerState.SUBPROCESSESLIST:
			{	
				SubProcessListState subProcessState = 
					new SubProcessListState(state,params, cct);
				return subProcessState;
			}
			
			case ManagerState.TASKSLIST:
			{	
				TasksListState tasksListState = 
					new TasksListState(state,params,cct);
				return tasksListState;
			}
			case ManagerState.CLOSEDTASKSLIST:
			{	
				ClosedTasksListState closedTasksListState = 
					new ClosedTasksListState(state,params,cct);
				return closedTasksListState;
			}
			case ManagerState.BATCHTASKLIST:
			{	
				BatchTaskListState batchTaskListState = 
					new BatchTaskListState(state,params,cct);
				return batchTaskListState;
			}
			case ManagerState.BATCHTASK:
			{	
				BatchTaskState batchTaskState = 
					new BatchTaskState(state,params,cct);
				return batchTaskState;
			}
			case ManagerState.EXPEDIENT:
			{	
				ExpedientState expState =
					new ExpedientState(state,params,cct);
				return expState;
			}
			case ManagerState.SUBPROCESS:
			{	
				SubProcessState subProcessState =
					new SubProcessState(state,params,cct);
				return subProcessState;
			}
			case ManagerState.TASK:
			{	
				TaskState taskState =
					new TaskState (state,params,cct);
				return taskState;
			}
			case ManagerState.CREATEPROCESSLIST:
			{
				CreateProcessState createProcState = 
					new CreateProcessState(state);
				return createProcState;
			}
			case ManagerState.INTRAYLIST:
			{
				IntrayState intrayState =
					new IntrayState(state);
				return intrayState;
			}
			case ManagerState.NOTICELIST:
			{
				NoticeState noticeState =
					new NoticeState(state);
				return noticeState;
			}
			case ManagerState.DELEGATE:
			{
				DelegateState delegateState = 
					new DelegateState(state,params,cct);
				return delegateState;
			}
			case ManagerState.DATA:
			{
				DataState dataState = 
					new DataState(state,params,cct);
				return dataState;
			}
			case ManagerState.TERMS:
			{
				TermsState termsState = 
					new TermsState(state);
				return termsState;
			}
			case ManagerState.SIGNDOCUMENT:
			{	
				SignDocumentState  signDocumentState =
					new SignDocumentState (state,params,cct);
				return signDocumentState;
			}
			case ManagerState.BATCHSIGNLIST:
			{	
				BatchSignDocumentState  batchSignDocumentState =
					new BatchSignDocumentState (state);
				return batchSignDocumentState;
			}			
			case ManagerState.SEARCH:
			{	
				SearchState  searchState =
					new SearchState (state);
				return searchState;
			}
			case ManagerState.SEARCHRESULTS:
			{	
				SearchResultsState searchResultState =
					new SearchResultsState (state);
				return searchResultState;
			}
			case ManagerState.EXPSTRASHLIST: 
			{ 
				  ExpsTrashListState expsTrashListState = 
				  new ExpsTrashListState (state); 
				  return expsTrashListState; 
			} 
		}
		throw new ISPACException("Error en StateFactory::createState(): " +
		"No se puede crear ningún estado");
	}
}
