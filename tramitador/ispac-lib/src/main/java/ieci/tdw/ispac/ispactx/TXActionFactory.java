package ieci.tdw.ispac.ispactx;

import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.rule.IRuleContextParams;
import ieci.tdw.ispac.ispactx.tx.TXActivateStage;
import ieci.tdw.ispac.ispactx.tx.TXArchiveProcess;
import ieci.tdw.ispac.ispactx.tx.TXCancelProcess;
import ieci.tdw.ispac.ispactx.tx.TXCleanProcess;
import ieci.tdw.ispac.ispactx.tx.TXCloseProcess;
import ieci.tdw.ispac.ispactx.tx.TXCloseStageBack;
import ieci.tdw.ispac.ispactx.tx.TXCloseStageForward;
import ieci.tdw.ispac.ispactx.tx.TXCloseTask;
import ieci.tdw.ispac.ispactx.tx.TXCreateBatchTask;
import ieci.tdw.ispac.ispactx.tx.TXCreateProcess;
import ieci.tdw.ispac.ispactx.tx.TXCreateTask;
import ieci.tdw.ispac.ispactx.tx.TXDelegateProc;
import ieci.tdw.ispac.ispactx.tx.TXDelegateStage;
import ieci.tdw.ispac.ispactx.tx.TXDelegateTask;
import ieci.tdw.ispac.ispactx.tx.TXDeleteBatchTask;
import ieci.tdw.ispac.ispactx.tx.TXDeleteProcess;
import ieci.tdw.ispac.ispactx.tx.TXDeleteSubProcess;
import ieci.tdw.ispac.ispactx.tx.TXDeleteTask;
import ieci.tdw.ispac.ispactx.tx.TXExecuteEvents;
import ieci.tdw.ispac.ispactx.tx.TXExecuteRule;
import ieci.tdw.ispac.ispactx.tx.TXNewMilestone;
import ieci.tdw.ispac.ispactx.tx.TXOpenNextStages;
import ieci.tdw.ispac.ispactx.tx.TXOpenPreviousStage;
import ieci.tdw.ispac.ispactx.tx.TXOpenStage;
import ieci.tdw.ispac.ispactx.tx.TXPauseDeadline;
import ieci.tdw.ispac.ispactx.tx.TXRecalculateDeadline;
import ieci.tdw.ispac.ispactx.tx.TXRedeployProcess;
import ieci.tdw.ispac.ispactx.tx.TXRemark;
import ieci.tdw.ispac.ispactx.tx.TXRestoreProcess;
import ieci.tdw.ispac.ispactx.tx.TXResumeDeadline;
import ieci.tdw.ispac.ispactx.tx.TXResumeProcess;
import ieci.tdw.ispac.ispactx.tx.TXSendProcessToTrash;
import ieci.tdw.ispac.ispactx.tx.TXSetBaseDate;
import ieci.tdw.ispac.ispactx.tx.TXSetCalendar;
import ieci.tdw.ispac.ispactx.tx.TXUpdateBatchTask;

import java.util.Date;
import java.util.Map;

public class TXActionFactory
{
	private static TXActionFactory _instance;

	private TXActionFactory()
	{
		super();
	}

	public static synchronized TXActionFactory getInstance()
	{
		if (_instance == null)
			_instance =  new TXActionFactory();

		return _instance;
	}

	/*
	public ITXAction createProcess(int nIdProcedure,String numexp,Map params)
	{
		return new TXCreateProcess(nIdProcedure,numexp,params);
	}
	*/
	
	public ITXAction createProcess(int nIdProcedure,String numexp,Map params, int type)
	{
		return new TXCreateProcess(nIdProcedure,numexp,params, type);
	}

	public ITXAction createSubProcess(int nIdProcedure, String numexp, String subProcessUID, String activityUID, String subProcessRespId, String activityRespId) {
		return new TXCreateProcess(nIdProcedure, numexp, subProcessUID, activityUID, subProcessRespId, activityRespId, IProcess.SUBPROCESS_TYPE);
	}
	
	public ITXAction createSubProcess(int nIdProcedure, String numexp, String subProcessUID, String activityUID, String subProcessRespId, String activityRespId, Map params) {
		return new TXCreateProcess(nIdProcedure, numexp, subProcessUID, activityUID, subProcessRespId, activityRespId, IProcess.SUBPROCESS_TYPE, params);
	}
	
	public ITXAction closeStageForward(int nIdStage) {
		return new TXCloseStageForward(nIdStage);
	}

	public ITXAction closeStageForward(int nIdStage, Map params) {
		return new TXCloseStageForward(nIdStage, params);
	}

	public ITXAction closeStageForward(int nIdStage, boolean endTasks) {
		return new TXCloseStageForward(nIdStage, endTasks);
	}	

	public ITXAction closeStageForward(int nIdStage, boolean endTasks, Map params) {
		return new TXCloseStageForward(nIdStage, endTasks, params);
	}	

	public ITXAction closeStageBack(int nIdStage) {
		return new TXCloseStageBack(nIdStage);
	}

	public ITXAction closeStageBack(int nIdStage, Map params) {
		return new TXCloseStageBack(nIdStage, params);
	}

	public ITXAction closeStageBack(int nIdStage, boolean endTasks) {
		return new TXCloseStageBack(nIdStage, endTasks);
	}	

	public ITXAction closeStageBack(int nIdStage, boolean endTasks, Map params) {
		return new TXCloseStageBack(nIdStage, endTasks, params);
	}	

//	public ITXAction closeAllStages(int nIdProcess, boolean endTasks) {
//		return new TXCloseAllStages(nIdProcess, endTasks);
//	}
//	public ITXAction closeAllStages(int nIdProcess) {
//		return new TXCloseAllStages(nIdProcess);
//	}
	
	
	
	public ITXAction openStage(int nIdProcess, int nIdPcdStage) {
		return new TXOpenStage(nIdProcess, nIdPcdStage);
	}
	
	public ITXAction openStage(int nIdProcess, int nIdPcdStage, Map params) {
		return new TXOpenStage(nIdProcess, nIdPcdStage, params);
	}
	
	public ITXAction openNextStages(int nIdProcess, int nIdPcdStage) {
		return new TXOpenNextStages(nIdProcess, nIdPcdStage);
	}

	public ITXAction openNextStages(int nIdProcess, int nIdPcdStage, Map params) {
		return new TXOpenNextStages(nIdProcess, nIdPcdStage, params);
	}

	public ITXAction openPreviousStage(int nIdProcess, int nIdPcdStage) {
		return new TXOpenPreviousStage(nIdProcess, nIdPcdStage);
	}

	public ITXAction openPreviousStage(int nIdProcess, int nIdPcdStage, Map params) {
		return new TXOpenPreviousStage(nIdProcess, nIdPcdStage, params);
	}

	
//	public ITXAction deployNextStage(int nIdStage)
//	{
//		return new TXDeployNextStage(nIdStage);
//	}
	
//	public ITXAction redeployPreviousStage(int nIdStage)
//	{
//		return new TXRedeployPreviousStage(nIdStage);
//	}
	
	public ITXAction closeProcess(int nIdProcess) {
		return new TXCloseProcess(nIdProcess);
	}

	public ITXAction closeProcess(int nIdProcess, Map params) {
		return new TXCloseProcess(nIdProcess);
	}

	public ITXAction closeProcess(int nIdProcess, boolean endTasks) {
		return new TXCloseProcess(nIdProcess, endTasks);
	}

	public ITXAction closeProcess(int nIdProcess, boolean endTasks, Map params) {
		return new TXCloseProcess(nIdProcess, endTasks, params);
	}

	public ITXAction archiveProcess(int nIdProcess) {
		return new TXArchiveProcess(nIdProcess);
	}

	public ITXAction archiveProcess(int nIdProcess, Map params) {
		return new TXArchiveProcess(nIdProcess, params);
	}

	public ITXAction cancelProcess(int nIdProc) {
		return new TXCancelProcess(nIdProc);
	}

	public ITXAction cancelProcess(int nIdProc, Map params) {
		return new TXCancelProcess(nIdProc, params);
	}

	public ITXAction resumeProcess(int nIdProc) {
		return new TXResumeProcess(nIdProc);
	}

	public ITXAction resumeProcess(int nIdProc, Map params) {
		return new TXResumeProcess(nIdProc, params);
	}

	public ITXAction redeployProcess(int nIdProc, int nIdPCDStage)
	{
		return new TXRedeployProcess(nIdProc,nIdPCDStage);
	}

	public ITXAction delegateProc(int nIdProc, String IdResp) {
		return new TXDelegateProc(nIdProc, IdResp);
	}
	
	public ITXAction delegateProc(int nIdProc, String IdResp , String nameResp) {
		return new TXDelegateProc(nIdProc, IdResp , nameResp);
	}

	public ITXAction delegateProc(int nIdProc, String IdResp, Map params) {
		return new TXDelegateProc(nIdProc, IdResp, params);
	}
	
	public ITXAction delegateProc(int nIdProc, String IdResp, String nameResp, Map params) {
		return new TXDelegateProc(nIdProc, IdResp,nameResp, params);
	}
	
	
	public ITXAction cleanProcess(int nIdProc){
		return  new TXCleanProcess(nIdProc);
	}
	public ITXAction cleanProcess (int nIdProc , Map params){
		return new TXCleanProcess(nIdProc, params);
	}
	
	public ITXAction sendProcessToTrash(int nIdProc){
		return  new TXSendProcessToTrash(nIdProc);
	}
	public ITXAction sendProcessToTrash (int nIdProc , Map params){
		return new TXSendProcessToTrash(nIdProc, params);
	}
	
	public ITXAction deleteProcess(int nIdProc){
		return  new TXDeleteProcess(nIdProc);
	}
	public ITXAction deleteProcess (int nIdProc , Map params){
		return new TXDeleteProcess(nIdProc, params);
	}
	
	public ITXAction restoreProcess(int nIdProc){
		return  new TXRestoreProcess(nIdProc);
	}
	public ITXAction restoreProcess (int nIdProc , Map params){
		return new TXRestoreProcess(nIdProc, params);
	}

	public ITXAction delegateStage(int nIdStage, String IdResp) {
		return new TXDelegateStage(nIdStage, IdResp);
	}

	public ITXAction delegateStage(int nIdStage, String IdResp, Map params) {
		return new TXDelegateStage(nIdStage, IdResp, params);
	}
	
	public ITXAction delegateStage(int nIdStage, String IdResp, String nameResp) {
		return new TXDelegateStage(nIdStage, IdResp , nameResp);
	}
	public ITXAction delegateStage(int nIdStage, String IdResp,String nameResp, Map params) {
		return new TXDelegateStage(nIdStage, IdResp, nameResp, params);
	}

	public ITXAction delegateTask(int nIdTask, String IdResp) {
		return new TXDelegateTask(nIdTask, IdResp);
	}

	public ITXAction delegateTask(int nIdTask, String IdResp, String nameResp) {
		return new TXDelegateTask(nIdTask, IdResp , nameResp);
	}
	
	public ITXAction delegateTask(int nIdTask, String IdResp, Map params) {
		return new TXDelegateTask(nIdTask, IdResp, params);
	}
	
	public ITXAction delegateTask(int nIdTask, String IdResp,String nameResp, Map params) {
		return new TXDelegateTask(nIdTask, IdResp,nameResp, params);
	}


	public ITXAction closeTask(int nIdTask) {
		return new TXCloseTask(nIdTask);
	}

	public ITXAction closeTask(int nIdTask, Map params) {
		return new TXCloseTask(nIdTask, params);
	}

	public ITXAction createTask(int nIdStage, int nIdTaskPCD) {
		return new TXCreateTask(nIdStage, nIdTaskPCD);
	}

	public ITXAction createTask(int nIdStage, int nIdTaskPCD, Map params) {
		return new TXCreateTask(nIdStage, nIdTaskPCD, params);
	}

	public ITXAction createTask(int nIdPcd, int nIdStage, int nIdTaskPCD, String numExp) {
		return new TXCreateTask(nIdPcd, nIdStage, nIdTaskPCD, numExp);
	}	

	public ITXAction createTask(int nIdPcd, int nIdStage, int nIdTaskPCD, String numExp, Map params) {
		return new TXCreateTask(nIdPcd, nIdStage, nIdTaskPCD, numExp, params);
	}	


	public ITXAction createBatchTask(int idFase, String [] numExps) {
		return new TXCreateBatchTask(idFase, numExps);
	}
	
	public ITXAction updateBatchTask(IItem batchTask){
		return new TXUpdateBatchTask(batchTask);
	}

	public ITXAction deleteBatchTask(int batchTaskId){
		return new TXDeleteBatchTask(batchTaskId);
	}

	public ITXAction deleteTask(int nIdTask) {
		return new TXDeleteTask(nIdTask);
	}

	public ITXAction deleteTask(int nIdTask, Map params) {
		return new TXDeleteTask(nIdTask, params);
	}

	public ITXAction deleteSubProcess(int nIdSubProcess, int nIdTask) {
		return new TXDeleteSubProcess(nIdSubProcess, nIdTask);
	}

	public ITXAction deleteSubProcess(int nIdSubProcess, int nIdTask, Map params) {
		return new TXDeleteSubProcess(nIdSubProcess, nIdTask, params);
	}

	public ITXAction newMilestone(int nIdProc, int nIdStage, int nIdTask,int nIdInfo,String info, String desc)
	{
		return new TXNewMilestone(nIdProc,nIdStage,nIdTask,nIdInfo,info, desc);
	}

	public ITXAction remark(int nIdProc, int nIdStage, int nIdTask, String Text)
	{
		return new TXRemark(nIdProc,nIdStage,nIdTask,Text);
	}

	public ITXAction executeRule(int nIdRule)
	{
		return new TXExecuteRule(nIdRule,(Map)null);
	}

	public ITXAction executeRule(int nIdRule,Map parameters)
	{
		return new TXExecuteRule(nIdRule,parameters);
	}

	public ITXAction executeContextRule(int nIdRule,int nIdProc, int nIdStage,int nIdTask,Map parameters)
	{
		return new TXExecuteRule(nIdRule,nIdProc,nIdStage,nIdTask,parameters);
	}

	public ITXAction executeContextRule(int nIdRule,IRuleContextParams rctxobj)
	{
		return new TXExecuteRule(nIdRule,rctxobj);
	}

	public ITXAction executeEvents(int TypeObj,int nIdObj,int EventCode,IRuleContextParams rctxobj)
	{
		return new TXExecuteEvents(TypeObj,nIdObj,EventCode,rctxobj);
	}

	public ITXAction activateStage(int nIdProc, int nIdStagePCD)
	{
		return new TXActivateStage(nIdProc, nIdStagePCD);
	}
	
	public ITXAction setCalendar(int tipoObjeto, int idObjeto, int idCalendario){
		return new TXSetCalendar(tipoObjeto, idObjeto, idCalendario);  
	}
	/**
	 * Congela el plazo de un proceso
	 * @param idProcess
	 * @return
	 */
	public ITXAction pauseDeadline(int tipoObjeto, int idObjeto){
		return new TXPauseDeadline(tipoObjeto, idObjeto);
	}
	
	/**
	 * reactiva el plazo de un proceso
	 * @param idProcess
	 * @return
	 */
	public ITXAction resumeDeadline(int tipoObjeto, int idObjeto){
		return new TXResumeDeadline(tipoObjeto, idObjeto);
	}
	
	public ITXAction recalculateDeadline(int tipoObjeto, int idObjeto){
		return new TXRecalculateDeadline(idObjeto, tipoObjeto);
	}

	public ITXAction recalculateDeadline(int tipoObjeto, int idObjeto, Map params){
		return new TXRecalculateDeadline(idObjeto, tipoObjeto, params);
	}

	public ITXAction setBaseDate(int tipoObjeto, int idObjeto, Date baseDate){
		return new TXSetBaseDate(idObjeto, tipoObjeto, baseDate );
	}

}