package ieci.tdw.ispac.ispacweb.api;

public class ManagerState
{
	public static final int LOGIN = 0;
	public static final int PROCEDURELIST = 1;
	public static final int STAGESLIST = 2;
	public static final int PROCESSESLIST = 3;
	public static final int TASKSLIST = 4;
	public static final int EXPEDIENT = 5;
	public static final int TASK = 6;
	public static final int SEARCHRESULTS = 9;
	public static final int CREATEPROCESSLIST = 10;
	public static final int INTRAYLIST = 12;
	public static final int DELEGATE = 13;
	public static final int DATA = 14;
	public static final int BATCHTASK = 15;
	public static final int BATCHTASKLIST = 16;
	public static final int TERMS = 17;
	public static final int SIGNDOCUMENT = 18;
	public static final int BATCHSIGNLIST = 19;
	public static final int SEARCH = 20;
	public static final int SUBPROCESSESLIST = 21;
	public static final int SUBPROCESS = 22;
	public static final int EXPSTRASHLIST=23;
    public static final int CLOSEDTASKSLIST = 24;
    public static final int NOTICELIST = 25;

	
	public static final int CUSTOM = 100;
	
	public static final int READONLY_REASON_EXPEDIENT_CLOSED = 4;

	public static final String PARAM_PCDID 			= "pcdId";
	public static final String PARAM_SUBPCDID 		= "subPcdId";
	public static final String PARAM_STAGEPCDID 	= "stagePcdId";
	public static final String PARAM_ACTIVITYPCDID 	= "activityPcdId";
	public static final Object PARAM_TASKPCDID 		= "taskPcdId";
	public static final String PARAM_TASKCTLID 		= "taskCtlId";
	public static final String PARAM_STAGEID 		= "stageId";
	public static final String PARAM_ACTIVITYID 	= "activityId";
	public static final String PARAM_TASKID 		= "taskId";
	public static final String PARAM_ENTITYID 		= "entity";
	public static final String PARAM_ENTREGID 		= "key";
	public static final String PARAM_NUMEXP 		= "numexp";
	public static final String PARAM_IDSSTAGES 		= "idsStage";
	public static final String PARAM_IDSTASK 		= "idsTask";
	public static final String PARAM_BATCHTASKID 	= "batchTaskId";
	public static final String PARAM_READONLY 		= "readonly";
	public static final String PARAM_QUERYSTRING 	= "?";
	public static final String PARAM_METHOD         = "method"; 
	
	public static final String LABEL_TASKSLIST = "showtasklist";
	public static final String LABEL_CLOSEDTASKSLIST = "showclosedtasklist";
	public static final String LABEL_LOGIN = "login";
	public static final String LABEL_PROCEDURELIST = "showmain";
	public static final String LABEL_STAGESLIST = "showstagelist";
	public static final String LABEL_PROCESSLIST = "showprocesslist";
	public static final String LABEL_BATCHTASKSLIST = "showBatchTaskList";
	public static final String LABEL_EXPEDIENT = "showexp";
	public static final String LABEL_SUBPROCESS = "showsubprocess";
	public static final String LABEL_TASK = "showtask";
	public static final String LABEL_BATCHTASK = "showBatchTask";
	public static final String LABEL_DATA = "showdata";
	public static final String LABEL_CREATEPROCESS = "showcreateprocess";
	public static final String LABEL_TRAY = "showtray";
	public static final String LABEL_DELEGATE = "delegateorg";
	public static final String LABEL_TERMS = "showExpiredTerms";
	public static final String LABEL_SIGN = "showtask";
	public static final String LABEL_BATCHSIGNLIST = "showBatchSignList";
	public static final String LABEL_SEARCHRESULTS = "searchForm";
	public static final String LABEL_SUBPROCESSLIST = "showsubprocesslist";
	public static final String LABEL_EXPSTRASHLIST  = "list"; 
	public static final String LABEL_NOTICE = "shownotices";
	
	//TODO Sin implementar este action, no se muestra lista de actividades para un subproceso, por ahora
	public static final String LABEL_ACTIVITIESLIST = "showactivitylist";
	
	
	
}
