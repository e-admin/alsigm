package ieci.tdw.ispac.ispactx;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.deadline.Deadline;
import ieci.tdw.ispac.deadline.DeadlineFactory;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.TransactionContainer;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PRelPlazoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXAnotacionDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXHitoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXSincNodoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramitacionAgrupadaDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramiteDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.entity.EntityManager;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author   juanin  To change the template for this generated type comment go to  Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TXTransactionDataContainer
{
	private final HashMap mprocessMap;
	private final HashMap mstageMap;
	private final HashMap mtaskMap;
	private final HashMap mbatchTaskMap;
	private final HashMap msyncnodeMap;
	private final HashMap mmilestoneMap;
	private final HashMap mremarksMap;
	//private HashMap mexpedMap;
	private final TXLockManager mlockmgr;
	private final ClientContext mccxt;
	
	private final TransactionContainer txcontainer;

	private boolean error;
	private IBPMAPI bpmAPI;
	/**
	 *
	 */
	public TXTransactionDataContainer(ClientContext cs)
	throws ISPACException
	{
		super();

		mprocessMap=new HashMap();
		mstageMap=new HashMap();
		mtaskMap=new HashMap();
		mbatchTaskMap=new HashMap();
		msyncnodeMap=new HashMap();
		mmilestoneMap=new HashMap();
		mremarksMap=new HashMap();
		//mexpedMap=new HashMap();
		mccxt=cs;
		mlockmgr=new TXLockManager(mccxt);
		
		txcontainer = mccxt.getTXContainer();
		txcontainer.begin();
		error = false;
		//Se obtiene el API del BPM el cual iniciara la sesion
		bpmAPI = mccxt.getAPI().getBPMAPI();
	}

	private void persist(DbCnt cnt,Iterator it)
	throws ISPACException
	{
		while (it.hasNext())
		{
			ObjectDAO obj=(ObjectDAO)((Map.Entry)it.next()).getValue();
			obj.store(cnt);
		}
	}

	public void persist()
	throws ISPACException
	{
			DbCnt cnt=mccxt.getConnection();
	
			persist(cnt,mprocessMap.entrySet().iterator());
			persist(cnt,mstageMap.entrySet().iterator());
			persist(cnt,mtaskMap.entrySet().iterator());
			persist(cnt,mbatchTaskMap.entrySet().iterator());
			persist(cnt,msyncnodeMap.entrySet().iterator());
			persist(cnt,mmilestoneMap.entrySet().iterator());
			persist(cnt,mremarksMap.entrySet().iterator());

			bpmAPI.closeBPMSession(true);
			txcontainer.commit();

	}

	public void release()
	throws ISPACException
	{
		mlockmgr.unlockAll();
		if (error)
			bpmAPI.closeBPMSession(false);
		txcontainer.release();
	}

	public TXLockManager getLockManager()
	{
		return mlockmgr;
	}

	public Iterator getStages()
	{
		return mstageMap.entrySet().iterator();
	}
	public Iterator getSyncNodes()
	{
		return msyncnodeMap.entrySet().iterator();
	}

	public void add(TXProcesoDAO exp)
	throws ISPACException
	{
		mprocessMap.put(exp.getKeyInteger(),exp);
	}

	public void add(TXFaseDAO stage)
	throws ISPACException
	{
		mstageMap.put(stage.getKeyInteger(),stage);
	}

	public void add(TXSincNodoDAO syncnode)
	throws ISPACException
	{
		msyncnodeMap.put(syncnode.getKeyInteger(),syncnode);
	}

	public void add(TXHitoDAO milestone)
	throws ISPACException
	{
		mmilestoneMap.put(milestone.getKeyInteger(),milestone);
	}

	public void add(TXTramiteDAO task)
	throws ISPACException
	{
		mtaskMap.put(task.getKeyInteger(),task);
	}

	public void add(TXTramitacionAgrupadaDAO batchTask) throws ISPACException {
		mbatchTaskMap.put(batchTask.getKeyInteger(), batchTask);
	}

	public void add(TXAnotacionDAO remarks)
	throws ISPACException
	{
		mremarksMap.put(remarks.getKeyInteger(),remarks);
	}

	public CollectionDAO getSubProcess(String numexp) throws ISPACException{
		TXProcesoDAO exp=new TXProcesoDAO(mccxt.getConnection());
		return exp.getSubprocess(mccxt.getConnection(), numexp);
	}
	public TXProcesoDAO getProcess(int nIdProc)
	throws ISPACException
	{
		Integer id=new Integer(nIdProc);
		if (mprocessMap.containsKey(id))
			return (TXProcesoDAO)mprocessMap.get(id);

		TXProcesoDAO exp=new TXProcesoDAO(mccxt.getConnection(),nIdProc);

		mprocessMap.put(id,exp);
		return exp;
	}

	public boolean testExistNumExp(String numexp)
	throws ISPACException
	{
	    DbCnt cnt=mccxt.getConnection();
		String sql=" AND ESTADO IN ("+IProcess.OPEN+","+IProcess.CANCELED+")";
		return (TXProcesoDAO.countProcess(cnt,numexp,sql)!=0);
	}

	public TXProcesoDAO newProcess()
	throws ISPACException
	{
		DbCnt cnt = mccxt.getConnection();
		
		TXProcesoDAO exp = new TXProcesoDAO(cnt);
		exp.createNew(cnt);

		mprocessMap.put(exp.getKeyInteger(),exp);
		return exp;
	}

	public void loadProcessStages(int nIdProc)
	throws ISPACException
	{
		TXProcesoDAO exped=getProcess(nIdProc);

		CollectionDAO stagelist=exped.getStages(mccxt.getConnection());
		while(stagelist.next())
		{
			TXFaseDAO stage=(TXFaseDAO)stagelist.value();
			if (!mstageMap.containsKey(stage.getKeyInteger()))
				mstageMap.put(stage.getKeyInteger(),stage);
		}
	}
	public void loadProcessTasks(int nIdProc)
	throws ISPACException
	{
		TXProcesoDAO exped=getProcess(nIdProc);

		CollectionDAO tasklist=exped.getTasks(mccxt.getConnection());
		while(tasklist.next())
		{
			TXTramiteDAO task=(TXTramiteDAO)tasklist.value();
			if (!mtaskMap.containsKey(task.getKeyInteger()))
				mtaskMap.put(task.getKeyInteger(),task);
		}
	}
	public void loadProcessSyncNodes(int nIdProc)
	throws ISPACException
	{
		TXProcesoDAO exped=getProcess(nIdProc);

		CollectionDAO syncnodelist=exped.getSyncnodes(mccxt.getConnection());
		while(syncnodelist.next())
		{
			TXSincNodoDAO syncnode=(TXSincNodoDAO)syncnodelist.value();
			if (!msyncnodeMap.containsKey(syncnode.getKeyInteger()))
				msyncnodeMap.put(syncnode.getKeyInteger(),syncnode);
		}
	}

	public TXFaseDAO getStage(int nIdStage)
	throws ISPACException
	{
		Integer id=new Integer(nIdStage);
		if (mstageMap.containsKey(id))
			return (TXFaseDAO)mstageMap.get(id);

		TXFaseDAO stage=new TXFaseDAO(mccxt.getConnection(),nIdStage);

		mstageMap.put(id,stage);
		return stage;
	}

	public TXFaseDAO newStage()
	throws ISPACException
	{
		DbCnt cnt = mccxt.getConnection();
		
		TXFaseDAO stage = new TXFaseDAO(cnt);
		stage.createNew(cnt);

		mstageMap.put(stage.getKeyInteger(),stage);
		return stage;
	}
	
	public void deleteProcess(int nIdProc)
	throws ISPACException
	{
		TXProcesoDAO process=getProcess(nIdProc);
		mprocessMap.remove(process.getKeyInteger());
		process.delete(mccxt.getConnection());
	}

	public void deleteStage(int nIdStage)
	throws ISPACException
	{
		TXFaseDAO stage=getStage(nIdStage);
		mstageMap.remove(stage.getKeyInteger());
		stage.delete(mccxt.getConnection());
	}

	public TXTramiteDAO newTask()
	throws ISPACException
	{
		DbCnt cnt = mccxt.getConnection();
		
		TXTramiteDAO task = new TXTramiteDAO(cnt);
		task.createNew(cnt);

		mtaskMap.put(task.getKeyInteger(),task);
		return task;
	}

	/**
	 * @param nIdTask
	 * @return
	 */
	public TXTramiteDAO getTask(int nIdTask)
	throws ISPACException
	{
		Integer id=new Integer(nIdTask);
		if (mtaskMap.containsKey(id))
			return (TXTramiteDAO)mtaskMap.get(id);

		TXTramiteDAO task=new TXTramiteDAO(mccxt.getConnection(),nIdTask);

		mtaskMap.put(id,task);
		return task;
	}

	/**
	 * @param nIdTask
	 */
	public void deleteTask(int nIdTask)
	throws ISPACException
	{
		TXTramiteDAO task=getTask(nIdTask);
		mtaskMap.remove(task.getKeyInteger());
		task.delete(mccxt.getConnection());
	}



	public TXSincNodoDAO newSyncNode()
	throws ISPACException
	{
		DbCnt cnt = mccxt.getConnection();
		
		TXSincNodoDAO syncnode = new TXSincNodoDAO(cnt);
		syncnode.createNew(cnt);

		msyncnodeMap.put(syncnode.getKeyInteger(),syncnode);
		return syncnode;
	}

	public TXSincNodoDAO getSyncNode(int nIdSyncNode)
	throws ISPACException
	{
		Integer id=new Integer(nIdSyncNode);
		if (msyncnodeMap.containsKey(id))
			return (TXSincNodoDAO)msyncnodeMap.get(id);

		TXSincNodoDAO syncnode=new TXSincNodoDAO(mccxt.getConnection(),nIdSyncNode);

		msyncnodeMap.put(id,syncnode);
		return syncnode;
	}

	public void deleteSyncNode(int nIdSyncNode)
	throws ISPACException
	{
		TXSincNodoDAO syncnode=getSyncNode(nIdSyncNode);
		msyncnodeMap.remove(syncnode.getKeyInteger());
		syncnode.delete(mccxt.getConnection());
	}


	public TXHitoDAO newMilestone()
	throws ISPACException
	{
		DbCnt cnt = mccxt.getConnection();
		
		TXHitoDAO milestone = new TXHitoDAO(cnt);
		milestone.createNew(cnt);

		mmilestoneMap.put(milestone.getKeyInteger(),milestone);
		return milestone;
	}

	public TXHitoDAO newMilestone(	int nIdProc,
									int nIdStagePCD,
									int nIdTaskPCD,
									int milestoneId)
	throws ISPACException
	{
		return newMilestone(nIdProc,nIdStagePCD,nIdTaskPCD,milestoneId,null);
	}

	public TXHitoDAO newMilestone(int nIdProc,
			int nIdStagePCD,
			int nIdTaskPCD,
			int milestoneId,
			String desc)
	throws ISPACException
	{
		TXHitoDAO hito=newMilestone();
		hito.set("ID_EXP",nIdProc);
		hito.set("ID_FASE",nIdStagePCD);
		hito.set("ID_TRAMITE",nIdTaskPCD);
		hito.set("HITO",milestoneId);
		hito.set("AUTOR",mccxt.getRespId());
		hito.set("FECHA",new Date());
		hito.set("FECHA_LIMITE",(Date)null);
		hito.set("INFO",(String)null);
		hito.set("DESCRIPCION",desc);
		return hito;
	}

	public void deleteMilestones(int nIdProc,	
			int nIdStagePCD,
			int nIdTaskPCD,
			int milestoneId)throws ISPACException{
	
		DbCnt cnt = mccxt.getConnection();
		
		TXHitoDAO milestone = new TXHitoDAO(cnt);
		String query="";
		boolean and=false;
		
		if(nIdStagePCD>=0){
			query=" ID_FASE="+nIdStagePCD+" ";
			and=true;
		}
		if(nIdTaskPCD>=0){
			if(and){
				query+=" AND ";
			}
			query+=" ID_TRAMITE= "+nIdTaskPCD;
			and=true;
		}
		if(milestoneId>=0){
			if(and){
				query+=" AND ";
			}
			query+=" HITO= "+milestoneId;
			
		}
		
		milestone.delete(cnt, nIdProc, query);
	
		
	}
	
	/**
	 * @param nIdProc
	 */
	public List getProcessStages(int nIdProc)
	throws ISPACException
	{
		loadProcessStages(nIdProc);
		ArrayList list=new ArrayList();
		Iterator it=mstageMap.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry entry=(Map.Entry)it.next();
			TXFaseDAO stage=(TXFaseDAO)entry.getValue();
			if (stage.getInt("ID_EXP")==nIdProc)
				list.add(stage);
		}
		return list;
	}

	/**
	 * @param nIdProc
	 */
	public List getProcessSyncNodes(int nIdProc)
	throws ISPACException
	{
		loadProcessSyncNodes(nIdProc);
		ArrayList list=new ArrayList();
		Iterator it=msyncnodeMap.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry entry=(Map.Entry)it.next();
			TXSincNodoDAO sincnodo=(TXSincNodoDAO)entry.getValue();
			if (sincnodo.getInt("ID_EXP")==nIdProc)
				list.add(sincnodo);
		}
		return list;
	}

	/**
	 * @param nIdProc
	 */
	public List getProcessTasks(int nIdProc)
	throws ISPACException
	{
		loadProcessTasks(nIdProc);
		ArrayList list=new ArrayList();
		Iterator it=mtaskMap.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry entry=(Map.Entry)it.next();
			TXTramiteDAO task=(TXTramiteDAO)entry.getValue();
			if (task.getInt("ID_EXP")==nIdProc)
				list.add(task);
		}
		return list;
	}

	public TXAnotacionDAO newRemark()
	throws ISPACException
	{
		DbCnt cnt = mccxt.getConnection();
		
		TXAnotacionDAO remark = new TXAnotacionDAO(cnt);
		remark.createNew(cnt);

		mremarksMap.put(remark.getKeyInteger(),remark);
		return remark;
	}

	/**
	 * @param nIdProc
	 */
	public void deleteProcessTasks(int nIdProc)
	throws ISPACException
	{
		Iterator it=mtaskMap.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry entry=(Map.Entry)it.next();
			TXTramiteDAO task=(TXTramiteDAO)entry.getValue();
			if (task.getInt("ID_EXP")==nIdProc)
				it.remove();
			task.delete(mccxt.getConnection());
		}
	}
	/**
	 * @param nIdProc
	 */
	public void deleteProcessStages(int nIdProc)
	throws ISPACException
	{
		Iterator it=mstageMap.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry entry=(Map.Entry)it.next();
			TXFaseDAO stage=(TXFaseDAO)entry.getValue();
			if (stage.getInt("ID_EXP")==nIdProc)
				it.remove();
			stage.delete(mccxt.getConnection());
		}
	}
	/**
	 * @param nIdProc
	 */
	public void deleteProcessSyncNodes(int nIdProc)
	throws ISPACException
	{
		Iterator it=msyncnodeMap.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry entry=(Map.Entry)it.next();
			TXSincNodoDAO sincnodo=(TXSincNodoDAO)entry.getValue();
			if (sincnodo.getInt("ID_EXP")==nIdProc)
				it.remove();
			sincnodo.delete(mccxt.getConnection());
		}
	}

	public boolean testStageOpen(int nIdProc,int nIdStagePCD)
	throws ISPACException
	{
		Iterator it=mstageMap.entrySet().iterator();
		while (it.hasNext())
		{
			TXFaseDAO stage = (TXFaseDAO) it.next();
			if (nIdStagePCD == stage.getInt("ID_FASE"))
				return (stage.getInt("ESTADO")==TXConstants.STATUS_OPEN);
		}
		return false;
	}

	public EntityDAO createEntity(int idEntity,String numexp)
	throws ISPACException
	{
		DbCnt cnt=mccxt.getConnection();
		EntityManager entmgr=new EntityManager(mccxt);
		EntityDAO entity=entmgr.createEntity(cnt,idEntity,numexp);
		entity.store(mccxt);
		return entity;
	}

	public EntityDAO createExpedientEntity(IProcess process)
	throws ISPACException
	{
		DbCnt cnt=mccxt.getConnection();
		EntityManager entmgr=new EntityManager(mccxt);
		EntityDAO entity=entmgr.createExpedient(cnt, process);
		entity.store(mccxt);
		return entity;
	}

	public void setExpedientEndDate(String numexp) throws ISPACException {
		DbCnt cnt = mccxt.getConnection();
		EntityManager entmgr = new EntityManager(mccxt);
		EntityDAO entity = entmgr.getExpedient(cnt, numexp);
		if ((entity != null) && StringUtils.isBlank(entity.getString("FCIERRE"))) {
			entity.set("FCIERRE", new Date());
			entity.store(mccxt);
		}
	}

	public EntityDAO createTaskEntity(TXTramiteDAO task)
	throws ISPACException
	{
		DbCnt cnt=mccxt.getConnection();
		EntityManager entmgr=new EntityManager(mccxt);
		EntityDAO entity=entmgr.createTask(cnt,task);
		//Si el tramite es complejo se copia en el registro de datos del tramite el id del subproceso
		if (task.isComplex())
			entity.set("ID_SUBPROCESO", task.getInt("ID_SUBPROCESO"));
		entity.store(mccxt);
		return entity;
	}

	public boolean testExistExpedientEntity(String numexp)
	throws ISPACException
	{
		DbCnt cnt=mccxt.getConnection();
		String sql="WHERE NUMEXP = '" + DBUtil.replaceQuotes(numexp) + "'";
		int count = EntityFactoryDAO.getInstance().countEntities(cnt,
				ISPACEntities.DT_ID_EXPEDIENTES,sql);

		return count > 0;
	}

	public void closeTaskEntity(int nIdTask, String idRespClosedTask)
	throws ISPACException
	{
		DbCnt cnt=mccxt.getConnection();
		String sql="WHERE ID_TRAM_EXP = "+nIdTask;
		CollectionDAO entities = EntityFactoryDAO.getInstance().queryEntities(cnt,
				ISPACEntities.DT_ID_TRAMITES,sql);

		if (!entities.next())
			throw new ISPACException("No existe la entidad del trámite con id "+nIdTask);

		EntityDAO entity=(EntityDAO)entities.value();
		//entity.set("ID_TRAM_EXP",0);
		entity.set("ESTADO",ISPACEntities.TASKSTATUS_CLOSE);
		entity.set("FECHA_CIERRE",new Date());
		entity.set("ID_RESP_CLOSED", idRespClosedTask);
		entity.store(mccxt);
	}

	public void deleteTaskEntity(int nIdTask)
	throws ISPACException
	{
		DbCnt cnt=mccxt.getConnection();
		String sql="WHERE ID_TRAM_EXP = "+nIdTask;
		CollectionDAO entities = EntityFactoryDAO.getInstance().queryEntities(cnt,
				ISPACEntities.DT_ID_TRAMITES,sql);

		if (!entities.next())
			throw new ISPACException("No existe la entidad del trámite con id "+nIdTask);

		EntityDAO entity=(EntityDAO)entities.value();
		entity.delete(mccxt);
	}

	public void pauseDeadline(int tipoObjeto, int idObjeto) throws ISPACException {

		IItem object = null;
		String deadlineXML = null;
		DbCnt cnt = mccxt.getConnection();

		if (tipoObjeto == PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE) {
			IProcess process = getProcess(idObjeto);
			object = process;
			deadlineXML = process.getDeadlineXML(cnt);

		} else if (tipoObjeto == PRelPlazoDAO.DEADLINE_OBJ_STAGE) {
			IStage stage = getStage(idObjeto);
			object = stage;
			deadlineXML = stage.getDeadlineXML(cnt);

		} else if (tipoObjeto == PRelPlazoDAO.DEADLINE_OBJ_TASK) {
			ITask task = getTask(idObjeto);
			object = task;
			deadlineXML = task.getDeadlineXML(cnt);
		}

		object.set("ESTADO_PLAZO", 2);// objeto en pausa

		// numero de dias naturales para cumplir el plazo
		int numDiasPlazo = object.getInt("NUM_DIAS_PLAZO");

		// calcular los dias naturales cumplidos hasta la parada de plazo
		int idCalendar;
		//se coge el calendario del objeto (puede no tener)
		String sIdCalendar = object.getString("ID_CALENDARIO");
		if (sIdCalendar != null){
			idCalendar = Integer.parseInt(sIdCalendar);
		
		}else {
			// cogemos el calendario del proceso que siempre tendra calendario
			int idProcess = object.getInt("ID_EXP");
			IProcess proccessOfObject = getProcess(idProcess);
			idCalendar = proccessOfObject.getInt("ID_CALENDAR");
		}

		String numExp = object.getString("NUMEXP");
		Date fechaInicio = object.getDate("FECHA_INICIO_PLAZO");
		EventManager eventmgr = new EventManager(mccxt);
		DeadlineFactory df = new DeadlineFactory(mccxt, idCalendar);

		Deadline dln = df.createDeadline(mccxt, eventmgr, XMLDocUtil.newDocument(deadlineXML),
				numExp);
		int calcPassNaturalDays = dln.calcPassNaturalDays(fechaInicio);

		// dias naturales que quedan por cumplir
		object.set("NUM_DIAS_PLAZO", numDiasPlazo - calcPassNaturalDays);

		object.set("FECHA_INICIO_PLAZO", (Object) null);
		object.set("FECHA_LIMITE", (Object) null);

		object.store(mccxt);

	}
	
	public void resumeDeadline(int tipoObjeto, int idObjeto) throws ISPACException {
		IItem object = null;
		if (tipoObjeto == PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE) {
			object = getProcess(idObjeto);

		} else if (tipoObjeto == PRelPlazoDAO.DEADLINE_OBJ_STAGE) {
			object = getStage(idObjeto);

		} else if (tipoObjeto == PRelPlazoDAO.DEADLINE_OBJ_TASK) {
			object = getTask(idObjeto);
		}

		object.set("ESTADO_PLAZO", 1);// proceso en pausa
		Date dateDeadline = new Date();
		object.set("FECHA_INICIO_PLAZO", new Date());

		// calcular fecha limite
		int numDiasPlazo = object.getInt("NUM_DIAS_PLAZO");
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dateDeadline);
		gc.set(Calendar.HOUR, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.add(Calendar.DATE, numDiasPlazo);
		object.set("FECHA_LIMITE", gc.getTime());

		object.store(mccxt);
	}

	public void setError() {
		error = true;
	}

	public IBPMAPI getBPMAPI() {
		return bpmAPI;
	}

}