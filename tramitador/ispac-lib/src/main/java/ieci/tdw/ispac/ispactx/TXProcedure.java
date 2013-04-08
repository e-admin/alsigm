package ieci.tdw.ispac.ispactx;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.api.item.util.MapCollection;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFlujoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PNodoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PProcedimientoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PSincNodoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PTramiteDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Información del procedimiento.
 * 
 */
public class TXProcedure extends PProcedimientoDAO implements IProcedure
{
	private Map mstages;
	private final Map msincnodes;
	private Map mtasks;
	private final TXStateTable mstateTable;

	/**
	 *
	 *
	 */
	public TXProcedure(DbCnt cnt,int id)
	throws ISPACException
	{
		super(cnt,id);

		mstages=super.getStages(cnt).toMapIntegerKey();
		msincnodes=super.getSyncNodes(cnt).toMapIntegerKey();
		mtasks=super.getTasks(cnt).toMapIntegerKey();

		mstateTable = new TXStateTable();
		buildStateTable(cnt);
	}

	public void reload(DbCnt cnt) throws ISPACException {
		load(cnt);
		mstages = super.getStages(cnt).toMapIntegerKey();
		mtasks = super.getTasks(cnt).toMapIntegerKey();
	}
	
	private void buildStateTable(DbCnt cnt) throws ISPACException
	{

	    CollectionDAO nodes = super.getNodes(cnt);
		while (nodes.next())
		{
		    PNodoDAO node = (PNodoDAO) nodes.value();
			mstateTable.addState(node.getKeyInt());
		}

		CollectionDAO flujos = super.getFlows(cnt);
		while (flujos.next())
		{
			PFlujoDAO flujo = (PFlujoDAO) flujos.value();
			mstateTable.addTransition(flujo.getNodeStart(),
									  flujo.getNodeEnd(),
									  flujo.getKeyInt());
		}
	}

	public TXStateTable getStateTable()
	//throws ISPACException
	{
//		if (mstateTable == null)
//		{
//			buildStateTable(cnt);
//		}
		return mstateTable;
	}

	public PFaseDAO getStageDAO(int nIdStage)
	{
		return (PFaseDAO) mstages.get(new Integer(nIdStage));
	}

	public PSincNodoDAO getSyncNodeDAO(int nIdNode)
	{
		return (PSincNodoDAO) msincnodes.get(new Integer(nIdNode));
	}

	public PTramiteDAO getTaskDAO(int nIdTask)
	{
		return (PTramiteDAO) mtasks.get(new Integer(nIdTask));
	}

	public PProcedimientoDAO getProcedureDAO()
	{
		return this;
	}

	 ////////////////////////////////////////////////
	// Implementación  Interface IProcedure

	public IItem getStage(int nIdStage)
	{
		return (IItem) getStageDAO(nIdStage);
	}

	public IItem getTask(int nIdTask)
	{
		return (IItem) getTaskDAO(nIdTask);
	}

	public IItemCollection getStages()
	{
		return new MapCollection(Collections.unmodifiableMap(mstages));
	}

	public IItemCollection getTasks()
	{
		return new MapCollection(Collections.unmodifiableMap(mtasks));
	}

	public IItemCollection getTasks(int nIdStagePCD)
		throws ISPACException
	{
		LinkedHashMap map=new LinkedHashMap();

		Iterator it=mtasks.values().iterator();
		while (it.hasNext())
		{
			PTramiteDAO task = (PTramiteDAO) it.next();
			if (task.getInt("ID_FASE")==nIdStagePCD)
				map.put(task.getKeyInteger(),task);
		}
		return new MapCollection(map);
	}

	public IItemCollection getTasks(int nIdStagePCD, int type)
	throws ISPACException
	{
		LinkedHashMap map=new LinkedHashMap();
	
		Iterator it=mtasks.values().iterator();
		while (it.hasNext())
		{
			PTramiteDAO task = (PTramiteDAO) it.next();
			if (task.getInt("ID_FASE")==nIdStagePCD){
				if ((type == ITask.COMPLEX_TASK_TYPE && task.get("ID_PCD_SUB") != null)
						|| (type == ITask.SIMPLE_TASK_TYPE && task.get("ID_PCD_SUB") == null)) {
					map.put(task.getKeyInteger(),task);
				}
			}
		}
		return new MapCollection(map);
	}
	
	
	public int getId() throws ISPACException {
		return super.getInt("ID");
	}

	public int getVersion() throws ISPACException {
		return super.getInt("NVERSION");
	}

	public String getNombre() throws ISPACException {
		return super.getString("NOMBRE");
	}

	public int getEstado() throws ISPACException {
		return super.getInt("ESTADO");
	}

	/**
	 * @return Fase inicial del Procedimiento.
	 */
	public PFaseDAO getInitialStage() {
		//Obtenemos la fase inicial del procedimiento	
		TXStateTable statetbl = getStateTable();
		Iterator it = statetbl.getStartStages().iterator();
		PFaseDAO pstage = null;
		if (it.hasNext()){
			int nIdNodePCD = ((Integer)it.next()).intValue();
			pstage=getStageDAO(nIdNodePCD);
		}		
		return pstage;
	}

	public PFaseDAO getStageDAO(DbCnt cnt, String stageUID) throws ISPACException {
		
		PNodoDAO node = super.getNode(cnt, stageUID);
		return (PFaseDAO)mstages.get(node.getKeyInteger());
	}
}
