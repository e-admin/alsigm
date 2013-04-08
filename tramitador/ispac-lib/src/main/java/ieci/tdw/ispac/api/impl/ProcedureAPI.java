package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.graph.GInfo;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ProcedureSvr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.context.TransactionContainer;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.TableDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTEntityDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTFrmBusquedaDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTProcedureDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTReportDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTReportOrgDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTaskTpDocDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.FrmBusquedaReportDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.PReportDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.TemplateDAO;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PCtoFirmaDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PDepTramiteDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PEntidadDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PEventoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFaseTDocDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFlujoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFrmFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFrmTramiteDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PNodoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PProcedimientoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PSincNodoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PTramiteDAO;
import ieci.tdw.ispac.ispaclib.dao.sign.SignCircuitInstanceDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.File;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * ProcedureAPI
 *
 */
public class ProcedureAPI implements IProcedureAPI {

    private ClientContext mcontext;

    /**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(ProcedureAPI.class);


	public ProcedureAPI(ClientContext context) {
		mcontext = context;
	}

	public int createProcedure(String newname,List ctstages, Map ctstagestask)
  			throws ISPACException {

	    TransactionContainer txcontainer=mcontext.getTXContainer();

	    try {

	        txcontainer.begin();

	        ProcedureSvr pcdsvr=new ProcedureSvr(mcontext);
	        int pcdid=pcdsvr.create(txcontainer,
	                newname,ctstages,ctstagestask);

	        txcontainer.commit();

	        return pcdid;

	    } finally {
	        txcontainer.release();
	    }
  	}

	public int cloneProcedure(int procedureid, String newname, int parent)
			throws ISPACException {
		TransactionContainer txcontainer = mcontext.getTXContainer();
		try {
			txcontainer.begin();

			ProcedureSvr pcdsvr = new ProcedureSvr(mcontext);
			int pcdid = pcdsvr.clone(txcontainer, procedureid, newname, parent);

			txcontainer.commit();

			return pcdid;
		} finally {
			txcontainer.release();
		}
	}

	public int cloneProcedure(int procedureid, String newname, List ctstages,
			Map ctstagestask, int parent) throws ISPACException {
		TransactionContainer txcontainer = mcontext.getTXContainer();
		try {
			txcontainer.begin();

			ProcedureSvr pcdsvr = new ProcedureSvr(mcontext);
			int pcdid = pcdsvr.clone(txcontainer, procedureid, newname,
					ctstages, ctstagestask, parent);

			txcontainer.commit();

			return pcdid;
		} finally {
			txcontainer.release();
		}
	}

    public int modifyProcedure(int procedureid, String newname,
			List ctstages, Map ctstagestask) throws ISPACException {
		TransactionContainer txcontainer = mcontext.getTXContainer();
		try {
			txcontainer.begin();

			ProcedureSvr pcdsvr = new ProcedureSvr(mcontext);
			int pcdid = pcdsvr.modify(txcontainer, procedureid, newname,
					ctstages, ctstagestask);

			txcontainer.commit();

			return pcdid;
		} finally {
			txcontainer.release();
		}
	}

    public int createDraft(int procedureid, String newname)
			throws ISPACException {
		TransactionContainer txcontainer = mcontext.getTXContainer();
		try {
			txcontainer.begin();

			ProcedureSvr pcdsvr = new ProcedureSvr(mcontext);
			int pcdid = pcdsvr.createDraft(txcontainer, procedureid, newname);

			txcontainer.commit();

			return pcdid;
		} finally {
			txcontainer.release();
		}
	}

    public int createDraft(int procedureid, String newname, List ctstages,
			Map ctstagestask) throws ISPACException {

		TransactionContainer txcontainer = mcontext.getTXContainer();

		try {
			txcontainer.begin();

			ProcedureSvr pcdsvr = new ProcedureSvr(mcontext);
			int pcdid = pcdsvr.createDraft(txcontainer, procedureid, newname,
					ctstages, ctstagestask);

			txcontainer.commit();

			return pcdid;

		} finally {
			txcontainer.release();
		}
	}

    /**
	 * Crea un borrador del subproceso indicado.
	 *
	 * @param subPcdId
	 *            Identificador del subproceso.
	 * @param subPcdName
	 *            Nombre del subproceso.
	 * @param activitiesNames
	 *            Nombres de las actividades.
	 * @return Identificador del subproceso creado.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
    public int createSubProcedureDraft(int subPcdId, String subPcdName,
            List activitiesNames) throws ISPACException {

	    TransactionContainer txcontainer = mcontext.getTXContainer();

	    try {

	        txcontainer.begin();

	        ProcedureSvr pcdsvr = new ProcedureSvr(mcontext);
	        int pcdid = pcdsvr.createSubProcedureDraft(txcontainer,
	                subPcdId, subPcdName, activitiesNames);

	        txcontainer.commit();

	        return pcdid;

	    } finally {
	        txcontainer.release();
	    }
    }

    /**
     * Clona el subproceso indicado.
     * @param subPcdId Identificador del subproceso.
     * @param subPcdName Nombre del subproceso.
     * @param activitiesNames Nombres de las actividades.
     * @return Identificador del subproceso creado.
     * @throws ISPACException si ocurre algún error.
     */
    public int cloneSubProcedure(int subPcdId, String subPcdName,
            List activitiesNames, int parent) throws ISPACException {

	    TransactionContainer txcontainer = mcontext.getTXContainer();

	    try {

	        txcontainer.begin();

	        ProcedureSvr pcdsvr = new ProcedureSvr(mcontext);
	        int pcdid = pcdsvr.cloneSubProcedure(txcontainer,
	                subPcdId, subPcdName, activitiesNames, parent);

	        txcontainer.commit();

	        return pcdid;

	    } finally {
	        txcontainer.release();
	    }
    }

    /**
     * Modifica el subproceso indicado.
     * @param subPcdId Identificador del subproceso.
     * @param subPcdName Nombre del subproceso.
     * @param activitiesNames Nombres de las actividades.
     * @return Identificador del subproceso.
     * @throws ISPACException si ocurre algún error.
     */
    public int modifySubProcedure(int subPcdId, String subPcdName,
            List activitiesNames) throws ISPACException {

	    TransactionContainer txcontainer = mcontext.getTXContainer();

	    try {

	        txcontainer.begin();

	        ProcedureSvr pcdsvr = new ProcedureSvr(mcontext);
	        int pcdid = pcdsvr.modifySubProcedure(txcontainer, subPcdId,
	        		subPcdName, activitiesNames);

	        txcontainer.commit();

	        return pcdid;

	    } finally {
	        txcontainer.release();
	    }
    }

    /**
     * Crea un subproceso.
     * @param subPcdName Nombre del subproceso.
     * @param activitiesNames Nombres de las actividades.
     * @return Identificador del subproceso creado.
     * @throws ISPACException si ocurre algún error.
     */
    public int createSubProcedure(String subPcdName, List activitiesNames)
    	throws ISPACException {

	    TransactionContainer txcontainer = mcontext.getTXContainer();

	    try {

	        txcontainer.begin();

	        ProcedureSvr pcdsvr = new ProcedureSvr(mcontext);
	        int pcdid = pcdsvr.createSubProcedure(txcontainer, subPcdName,
	        		activitiesNames);

	        txcontainer.commit();

	        return pcdid;

	    } finally {
	        txcontainer.release();
	    }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IProcedureAPI#deleteProcedure(int)
     */
    public void deleteProcedure(int procedureid) throws ISPACException
    {
        TransactionContainer txcontainer=mcontext.getTXContainer();
	    try
	    {
	        txcontainer.begin();
	        ProcedureSvr pcdsvr=new ProcedureSvr(mcontext);
	        pcdsvr.delete(txcontainer,procedureid);
	        txcontainer.commit();
	    }
	    finally
	    {
	        txcontainer.release();
	    }
    }

    /**
     * Elimina una entidad del procedimiento,
     * eliminando también las posibles asociaciones de formularios de la entidad a nivel de fase y trámite
     * y los eventos asociados a la entidad en el procedimiento.
     *
     * @param pcdEntityId Identificador de la entidad en el procedimiento.
     * @throws ISPACException si ocurre algún error.
     */
    public void deletePcdEntity(int pcdEntityId) throws ISPACException {

		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

		try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
			DbCnt cnt = mcontext.getConnection();

			PEntidadDAO pcdEntityDAO = new PEntidadDAO(cnt, pcdEntityId);
			int entityId = pcdEntityDAO.getInt("ID_ENT");

			// Entidades no eliminables a nivel de procedimiento
			if ((entityId == SpacEntities.SPAC_EXPEDIENTES) ||
				(entityId == SpacEntities.SPAC_DT_INTERVINIENTES) ||
				(entityId == SpacEntities.SPAC_DT_DOCUMENTOS) ||
				(entityId == SpacEntities.SPAC_DT_TRAMITES)) {

				return;
			}

			int pcdId = pcdEntityDAO.getInt("ID_PCD");

			// Eliminar los posibles formularios de la entidad en las fases del procedimiento
			CollectionDAO frmStageCollection = PFrmFaseDAO.getFormsByEntityInPcd(cnt, pcdId, entityId);
			while (frmStageCollection.next()) {

				PFrmFaseDAO pFrmFaseDAO = (PFrmFaseDAO) frmStageCollection.value();
				pFrmFaseDAO.delete(cnt);
			}

			// Eliminar los posibles formularios de la entidad en los trámites del procedimiento
			CollectionDAO frmTaskCollection = PFrmTramiteDAO.getFormsByEntityInPcd(cnt, pcdId, entityId);
			while (frmTaskCollection.next()) {

				PFrmTramiteDAO pFrmTramiteDAO = (PFrmTramiteDAO) frmTaskCollection.value();
				pFrmTramiteDAO.delete(cnt);
			}

			/*
			// Eliminar los eventos asociados a la entidad en el procedimiento
			CollectionDAO entityEventsCollection = PEventoDAO.getEvents(cnt, EventsDefines.EVENT_OBJ_ENTITY, pcdEntityId);
			while (entityEventsCollection.next()) {

				PEventoDAO pEventoDAO = (PEventoDAO) entityEventsCollection.value();
				pEventoDAO.delete(cnt);
			}
			*/
			// Eliminar la entidad en el procedimiento
			// junto con los eventos asociados a la entidad en el procedimiento
			pcdEntityDAO.delete(cnt);

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
		}
		catch (ISPACException ie) {
			logger.error("Error en ProcedureAPI:deletePcdEntity(" + pcdEntityId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:deletePcdEntity(" + pcdEntityId + ")", ie);
		}
		finally {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IProcedureAPI#promoteDraftToCurrent(int)
     */
    public void markDraftAsCurrent(int procedureid) throws ISPACException
    {
        TransactionContainer txcontainer=mcontext.getTXContainer();
	    try
	    {
	        txcontainer.begin();

	        ProcedureSvr pcdsvr=new ProcedureSvr(mcontext);
	        pcdsvr.setStateCurrent(txcontainer,procedureid);
	        txcontainer.commit();
	    }
	    finally
	    {
	        txcontainer.release();
	    }
    }

    public int addStage(int pcdid, int ctstageid, String ctstagename)
    		throws ISPACException {

    	return addStage(pcdid, ctstageid, ctstagename, new GInfo());
    }

    public int addStage(int pcdid, int ctstageid, String ctstagename, GInfo ginfo)
			throws ISPACException {

		TransactionContainer txcontainer = mcontext.getTXContainer();

		try {
			txcontainer.begin();

			ProcedureSvr pcdsvr = new ProcedureSvr(mcontext);
			int pcdstageid = pcdsvr.addStage(txcontainer, pcdid, ctstageid,
					ctstagename, ginfo);

			txcontainer.commit();

			return pcdstageid;

		} finally {
			txcontainer.release();
		}
	}

    public void removeStage(int pcdid, int pstageid) throws ISPACException {

        TransactionContainer txcontainer = mcontext.getTXContainer();

		try {
			txcontainer.begin();

			ProcedureSvr pcdsvr = new ProcedureSvr(mcontext);
			pcdsvr.removeStage(txcontainer, pcdid, pstageid);

			txcontainer.commit();
		} finally {
			txcontainer.release();
		}
    }

    public int addSyncNode(int pcdid, int type) throws ISPACException {
    	return addSyncNode(pcdid, type, new GInfo());
    }

    public int addSyncNode(int pcdid, int type, GInfo ginfo) throws ISPACException {

        TransactionContainer txcontainer=mcontext.getTXContainer();

	    try {
			txcontainer.begin();

			ProcedureSvr pcdsvr = new ProcedureSvr(mcontext);
			int syncNodeId = pcdsvr.addSyncNode(txcontainer, pcdid, type, ginfo);

			txcontainer.commit();

			return syncNodeId;

		} finally {
			txcontainer.release();
		}
    }

	public void removeSyncNode(int pcdid, int psyncnodeid)
			throws ISPACException {

        TransactionContainer txcontainer = mcontext.getTXContainer();

		try {
			txcontainer.begin();

			ProcedureSvr pcdsvr = new ProcedureSvr(mcontext);
			pcdsvr.removeSyncNode(txcontainer, pcdid,
					psyncnodeid);

			txcontainer.commit();
		} finally {
			txcontainer.release();
		}
	}

    public int createFlow(int pcdid, int origId, int destId)
			throws ISPACException {

        TransactionContainer txcontainer=mcontext.getTXContainer();

	    try {
			txcontainer.begin();

			ProcedureSvr pcdsvr = new ProcedureSvr(mcontext);
			int flowId = pcdsvr.createFlow(txcontainer, pcdid, origId,
					destId);

			txcontainer.commit();

			return flowId;

		} finally {
			txcontainer.release();
		}
    }

    public void removeFlow(int pcdid, int pflowid) throws ISPACException {

        TransactionContainer txcontainer = mcontext.getTXContainer();

		try {
			txcontainer.begin();

			ProcedureSvr pcdsvr = new ProcedureSvr(mcontext);
			pcdsvr.removeFlow(txcontainer, pcdid, pflowid);

			txcontainer.commit();
		} finally {
			txcontainer.release();
		}
    }

    public int addTask(int pstageid,int cttaskid) throws ISPACException {

        TransactionContainer txcontainer=mcontext.getTXContainer();

	    try {

	        txcontainer.begin();

	        PFaseDAO stage= new PFaseDAO(txcontainer.getConnection(),pstageid);

	        ProcedureSvr pcdsvr=new ProcedureSvr(mcontext);
	        int taskId = pcdsvr.addTask(txcontainer, stage.getInt("ID_PCD"),
	        		pstageid, cttaskid);

	        txcontainer.commit();

	        return taskId;

	    } finally {
	        txcontainer.release();
	    }
	}

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IProcedureAPI#modifyProcedure(int, java.lang.String, java.util.List, java.util.Map)
     */
    public void removeTask(int ptaskid)
	throws ISPACException
	{
        TransactionContainer txcontainer=mcontext.getTXContainer();
	    try
	    {
	        txcontainer.begin();

	        PTramiteDAO task=new PTramiteDAO(txcontainer.getConnection(),ptaskid);

	        ProcedureSvr pcdsvr=new ProcedureSvr(mcontext);
	        pcdsvr.removeTask(txcontainer,
	                task.getInt("ID_PCD"),task.getInt("ID_FASE"),ptaskid);

	        txcontainer.commit();
	    }
	    finally
	    {
	        txcontainer.release();
	    }
	}

    public void moveTaskUp(int ptaskid) throws ISPACException {

        TransactionContainer txcontainer=mcontext.getTXContainer();

	    try {
			txcontainer.begin();

			DbCnt cnt = txcontainer.getConnection();

			// Trámite seleccionado
			PTramiteDAO task = new PTramiteDAO(cnt, ptaskid);

			// Lista de trámites de la fase
			IItemCollection tasks = getStageTasks(task.getInt("ID_FASE"));

			// Trámite anterior
			PTramiteDAO prevTask = getPrevTask(tasks, ptaskid);
			if (prevTask != null) {
				int prevTaskOrder = prevTask.getInt("ORDEN");

				prevTask.set("ORDEN", task.getInt("ORDEN"));
				prevTask.store(cnt);

				task.set("ORDEN", prevTaskOrder);
				task.store(cnt);
			}

			txcontainer.commit();
		} finally {
			txcontainer.release();
		}
	}

    private static PTramiteDAO getPrevTask(IItemCollection tasks, int ptaskid) throws ISPACException {

		PTramiteDAO prevTask = null;

		PTramiteDAO prev = null;
		PTramiteDAO aux = null;

		while ((prevTask == null) && tasks.next()) {
			prev = aux;
			aux = (PTramiteDAO) tasks.value();

			if (aux.getKeyInt() == ptaskid) {
				prevTask = prev;
			}
		}

		return prevTask;
    }

    public void moveTaskDown(int ptaskid) throws ISPACException {

        TransactionContainer txcontainer=mcontext.getTXContainer();

	    try {
			txcontainer.begin();

			DbCnt cnt = txcontainer.getConnection();

			// Trámite seleccionado
			PTramiteDAO task = new PTramiteDAO(cnt, ptaskid);

			// Lista de trámites de la fase
			IItemCollection tasks = getStageTasks(task.getInt("ID_FASE"));

			// Trámite posterior
			PTramiteDAO nextTask = getNextTask(tasks, ptaskid);
			if (nextTask != null) {
				int nextTaskOrder = nextTask.getInt("ORDEN");

				nextTask.set("ORDEN", task.getInt("ORDEN"));
				nextTask.store(cnt);

				task.set("ORDEN", nextTaskOrder);
				task.store(cnt);
			}

			txcontainer.commit();
		} finally {
			txcontainer.release();
		}
	}

    private static PTramiteDAO getNextTask(IItemCollection tasks, int ptaskid) throws ISPACException {

		PTramiteDAO aux = null;
		while (tasks.next()) {
			aux = (PTramiteDAO) tasks.value();
			if (aux.getKeyInt() == ptaskid) {
				if (tasks.next()) {
					return (PTramiteDAO)tasks.value();
				} else {
					return null;
				}
			}
		}

		return null;
    }

    public void reorderTasks(int[] ptaskids) throws ISPACException {

    	if (!ArrayUtils.isEmpty(ptaskids)) {

	        TransactionContainer txcontainer = mcontext.getTXContainer();

		    try {
				txcontainer.begin();

				DbCnt cnt = txcontainer.getConnection();

				for (int i = 0; i < ptaskids.length; i++ ) {
					PTramiteDAO task = new PTramiteDAO(cnt, ptaskids[i]);
					task.set("ORDEN", i+1);
					task.store(cnt);
				}

				txcontainer.commit();
			} finally {
				txcontainer.release();
			}
    	}
	}

    public String getProcedureXml( int procedureId)
    throws ISPACException
    {
        DbCnt cnt = mcontext.getConnection();
        try
        {
            ProcedureSvr pcdsvr=new ProcedureSvr(mcontext);
            return pcdsvr.getXml(cnt,procedureId);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    public File exportProcedure(int procedureId, String path)
    throws ISPACException
    {
        DbCnt cnt = mcontext.getConnection();
        try
        {
            ProcedureSvr pcdsvr=new ProcedureSvr(mcontext);
            return pcdsvr.exportProcedure(cnt, procedureId, path);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    public String importProcedure(int parentPcdId, File file, boolean test)
    throws ISPACException
    {
    	return importProcedure(parentPcdId, false, file, test);
    }


    public String importProcedure(int parentPcdId, boolean asVersion, File file, boolean test)
    throws ISPACException
    {
    	// Mensajes generados al importar el procedimiento
		StringBuffer importLog = new StringBuffer();

        TransactionContainer txcontainer=mcontext.getTXContainer();
	    try
	    {
	        txcontainer.begin();

	        ProcedureSvr pcdsvr = new ProcedureSvr(mcontext);

	        if(pcdsvr.importProcedure(txcontainer, parentPcdId, asVersion, file, importLog, test)) {

	        	// Si todo ha sido correcto se hace commit de la transacción
	        	txcontainer.commit();
	        }
	    }
	    finally
	    {
	        txcontainer.release();
	    }

	    return importLog.toString();
    }

    public boolean isInUse( int entityId, int keyId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = null;
			CollectionDAO collection = null;
			int count = 0;

			switch (entityId)
			{
				case ICatalogAPI.ENTITY_CT_ENTITY :
				{
					where = "WHERE ID_ENT  = " + keyId;
					// Entidades asociadas al procedimiento
		   			collection = new CollectionDAO(PEntidadDAO.class);
		   			count = collection.count( cnt, where);
		   			if (count != 0) break;
		   			/*
		   			// Entidades asociadas a la fase
		   			collection = new CollectionDAO(PFrmFaseDAO.class);
		   			count = collection.count( cnt, where);
		   			if (count != 0) break;
		   			// Entidades asociadas al trámite
		   			collection = new CollectionDAO(PFrmTramiteDAO.class);
		   			count = collection.count( cnt, where);
					break;
					*/
				}
				case ICatalogAPI.ENTITY_CT_APP :
				{
					where = "WHERE FRM_EDIT = " + keyId;
					// Formulario por defecto asociado a la entidad
		   			collection = new CollectionDAO(CTEntityDAO.class);
		   			count = collection.count( cnt, where);
		   			if (count != 0) break;
		   			// Formulario asociado al procedimiento
		   			collection = new CollectionDAO(PEntidadDAO.class);
		   			count = collection.count( cnt, where);
		   			if (count != 0) break;
		   			// Formulario específico asociado a la fase
		   			collection = new CollectionDAO(PFrmFaseDAO.class);
		   			count = collection.count( cnt, where);
		   			if (count != 0) break;
		   			// Formulario específico asociado a la actividad de un trámite complejo
		   			collection = new CollectionDAO(PFrmTramiteDAO.class);
		   			count = collection.count( cnt, where);
					break;
				}
				case ICatalogAPI.ENTITY_CT_RULE :
				{
					where = "WHERE ID_REGLA = " + keyId;
					collection = new CollectionDAO(PEventoDAO.class);
					count = collection.count( cnt, where);
					break;
				}
				case ICatalogAPI.ENTITY_CT_PROCEDURE :
				{

					where = "WHERE ID = " + keyId;
					collection = new CollectionDAO(PProcedimientoDAO.class);
					count = collection.count( cnt, where);
					break;
				}
				case ICatalogAPI.ENTITY_CT_STAGE :
				{
					where = "WHERE ID_CTFASE = " + keyId;
					collection = new CollectionDAO(PFaseDAO.class);
					count = collection.count( cnt, where);
					break;
				}
				case ICatalogAPI.ENTITY_CT_TASK :
				{
					where = "WHERE ID_CTTRAMITE = " + keyId;
					collection = new CollectionDAO(PTramiteDAO.class);
					count = collection.count( cnt, where);
					break;
				}
				case ICatalogAPI.ENTITY_CT_TYPEDOC :
				{
					// Documento asociado a la fase
					where = "WHERE ID_TPDOC = " + keyId;
					collection = new CollectionDAO(PFaseTDocDAO.class);
					count = collection.count( cnt, where);
					if (count != 0) break;
					// Documento asociado a la tarea
					collection = new CollectionDAO(CTTaskTpDocDAO.class);
					count = collection.count( cnt, where);
					break;
				}
				case ICatalogAPI.ENTITY_SIGNPROCESS_HEADER :
				{
					where = "WHERE ID_CIRCUITO = " + keyId;
					collection = new CollectionDAO(PCtoFirmaDAO.class);
					count = collection.count( cnt, where);
					break;
				}
				case ICatalogAPI.ENTITY_CT_INFORMES:
				{

					TableJoinFactoryDAO tableJoinFactoryDAO = new TableJoinFactoryDAO();
					tableJoinFactoryDAO.addTable(PReportDAO.TABLENAME,PReportDAO.TABLENAME) ;
					tableJoinFactoryDAO.addTable(FrmBusquedaReportDAO.TABLENAME, FrmBusquedaReportDAO.TABLENAME);
					where = "WHERE " + PReportDAO.TABLENAME + ".ID_INFORME = " + keyId +" OR "+FrmBusquedaReportDAO.TABLENAME+".ID_INFORME = "+keyId;
				    collection = tableJoinFactoryDAO.queryTableJoin(cnt, where);
					count = collection.count(cnt, where);
					break;
				}
			}

			return count != 0;
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:isInUse("+ entityId +", "+ keyId +")",ie);
			throw new ISPACException("Error en ProcedureAPI:isInUse("+ entityId +", "+ keyId +")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IProcedureAPI#createDraft(int, java.lang.String, java.util.List, java.util.Map)
     */
    public Map getCTStageTaskIds(int procedureid) throws ISPACException
    {
        DbCnt cnt = mcontext.getConnection();
	    try
	    {
	        ProcedureSvr pcdsvr=new ProcedureSvr(mcontext);
	        return pcdsvr.getCTStageTaskIds(cnt,procedureid);
	    }
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getCTStageTaskIds("+ procedureid + ")" ,ie);
			throw new ISPACException("Error en ProcedureAPI:getCTStageTaskIds("+ procedureid + ")", ie);
		}
	    finally
	    {
	        mcontext.releaseConnection(cnt);
	    }
    }


    public IItemCollection getEntityProceduresUse(int entityId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID=CTPCD.ID AND PROCD.ID = FORM.ID_PCD AND FORM.ID_ENT  = " + entityId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_ENTIDADES", "FORM");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getEntityProceduresUse("+ entityId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getEntityProceduresUse("+ entityId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	public IItemCollection getEntityStagesUse(int entityId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID=CTPCD.ID AND PROCD.ID = STAGE.ID_PCD AND STAGE.ID = FORM.ID_FASE AND FORM.ID_ENT  = " + entityId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_FASES", "STAGE");
			factory.addTable( "SPAC_P_FRMFASES", "FORM");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getEntityStagesUse("+ entityId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getEntityStagesUse("+ entityId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getEntityTasksUse(int entityId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID=CTPCD.ID AND PROCD.ID = STAGE.ID_PCD AND STAGE.ID = TASK.ID_FASE AND TASK.ID = FORM.ID_TRAMITE AND FORM.ID_ENT  = " + entityId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_FASES", "STAGE");
			factory.addTable( "SPAC_P_TRAMITES", "TASK");
			factory.addTable( "SPAC_P_FRMTRAMITES", "FORM");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getEntityTasksUse("+ entityId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getEntityTasksUse("+ entityId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getApplicationEntitiesUse(int applicationId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String sql = "WHERE FRM_EDIT = " + applicationId + " ORDER BY ID";

			CollectionDAO collection = new CollectionDAO(CTEntityDAO.class);
			collection.query(cnt,sql);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getApplicationEntitiesUse("+ applicationId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getApplicationEntitiesUse("+ applicationId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getApplicationProceduresUse(int applicationId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID = CTPCD.ID AND PROCD.ID = FORM.ID_PCD AND FORM.FRM_EDIT = " + applicationId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_ENTIDADES", "FORM");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getApplicationProceduresUse("+ applicationId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getApplicationProceduresUse("+ applicationId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getApplicationStagesUse(int applicationId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID = CTPCD.ID AND PROCD.ID = STAGE.ID_PCD AND STAGE.ID = FORM.ID_FASE AND FORM.FRM_EDIT = " + applicationId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_FASES", "STAGE");
			factory.addTable( "SPAC_P_FRMFASES", "FORM");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getApplicationStagesUse("+ applicationId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getApplicationStagesUse("+ applicationId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getApplicationTasksUse(int applicationId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID = CTPCD.ID AND PROCD.ID = STAGE.ID_PCD AND STAGE.ID = TASK.ID_FASE AND TASK.ID = FORM.ID_TRAMITE AND FORM.FRM_EDIT = " + applicationId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_FASES", "STAGE");
			factory.addTable( "SPAC_P_TRAMITES", "TASK");
			factory.addTable( "SPAC_P_FRMTRAMITES", "FORM");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getApplicationTasksUse("+ applicationId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getApplicationTasksUse("+ applicationId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getRuleEventsUse(int ruleId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String sql = "WHERE ID_REGLA = " + ruleId;

			CollectionDAO collection = new CollectionDAO(PEventoDAO.class);
			collection.query(cnt,sql);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getRuleEventsUse("+ ruleId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getRuleEventsUse("+ ruleId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getRuleProceduresUse(int ruleId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID=CTPCD.ID AND PROCD.ID = EVENT.ID_OBJ AND EVENT.TP_OBJ = 1 AND EVENT.ID_REGLA = " + ruleId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_EVENTOS", "EVENT");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getRuleProceduresUse("+ ruleId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getRuleProceduresUse("+ ruleId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getRuleProcedureEntitiesUse(int ruleId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID=CTPCD.ID AND PROCD.ID = PENTITY.ID_PCD AND PENTITY.ID_ENT = CTENTITY.ID AND PENTITY.ID = EVENT.ID_OBJ AND EVENT.TP_OBJ = "
						 + EventsDefines.EVENT_OBJ_ENTITY + " AND EVENT.ID_REGLA = " + ruleId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_ENTIDADES", "PENTITY");
			factory.addTable( "SPAC_P_EVENTOS", "EVENT");
			factory.addTable( "SPAC_CT_ENTIDADES", "CTENTITY");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getRuleProcedureEntitiesUse("+ ruleId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getRuleProcedureEntitiesUse("+ ruleId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getRuleProcedureEntityFormsUse(int ruleId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID=CTPCD.ID AND PROCD.ID = PENTITY.ID_PCD AND PENTITY.ID_ENT = CTENTITY.ID AND PENTITY.ID_RULE_FRM = " + ruleId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_ENTIDADES", "PENTITY");
			factory.addTable( "SPAC_CT_ENTIDADES", "CTENTITY");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getRuleProcedureEntityFormsUse("+ ruleId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getRuleProcedureEntityFormsUse("+ ruleId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getRuleStageEntityFormsUse(int ruleId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID = CTPCD.ID AND PROCD.ID = STAGE.ID_PCD AND STAGE.ID = FORM.ID_FASE AND FORM.ID_ENT = CTENTITY.ID AND FORM.ID_RULE_FRM = " + ruleId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_FASES", "STAGE");
			factory.addTable( "SPAC_P_FRMFASES", "FORM");
			factory.addTable( "SPAC_CT_ENTIDADES", "CTENTITY");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getRuleStageEntityFormsUse("+ ruleId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getRuleStageEntityFormsUse("+ ruleId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getRuleTaskEntityFormsUse(int ruleId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID = CTPCD.ID AND PROCD.ID = STAGE.ID_PCD AND STAGE.ID = TASK.ID_FASE AND TASK.ID = FORM.ID_TRAMITE AND FORM.ID_ENT = CTENTITY.ID AND FORM.ID_RULE_FRM = " + ruleId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_FASES", "STAGE");
			factory.addTable( "SPAC_P_TRAMITES", "TASK");
			factory.addTable( "SPAC_P_FRMTRAMITES", "FORM");
			factory.addTable( "SPAC_CT_ENTIDADES", "CTENTITY");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getRuleTaskEntityFormsUse("+ ruleId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getRuleTaskEntityFormsUse("+ ruleId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getRuleProcedureEntityVisibleUse(int ruleId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID=CTPCD.ID AND PROCD.ID = PENTITY.ID_PCD AND PENTITY.ID_ENT = CTENTITY.ID AND PENTITY.ID_RULE_VISIBLE = " + ruleId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_ENTIDADES", "PENTITY");
			factory.addTable( "SPAC_CT_ENTIDADES", "CTENTITY");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getRuleProcedureEntityVisibleUse("+ ruleId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getRuleProcedureEntityVisibleUse("+ ruleId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getRuleStageEntityVisibleUse(int ruleId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID = CTPCD.ID AND PROCD.ID = STAGE.ID_PCD AND STAGE.ID = FORM.ID_FASE AND FORM.ID_ENT = CTENTITY.ID AND FORM.ID_RULE_VISIBLE = " + ruleId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_FASES", "STAGE");
			factory.addTable( "SPAC_P_FRMFASES", "FORM");
			factory.addTable( "SPAC_CT_ENTIDADES", "CTENTITY");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getRuleStageEntityVisibleUse("+ ruleId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getRuleStageEntityVisibleUse("+ ruleId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getRuleTaskEntityVisibleUse(int ruleId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID = CTPCD.ID AND PROCD.ID = STAGE.ID_PCD AND STAGE.ID = TASK.ID_FASE AND TASK.ID = FORM.ID_TRAMITE AND FORM.ID_ENT = CTENTITY.ID AND FORM.ID_RULE_VISIBLE = " + ruleId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_FASES", "STAGE");
			factory.addTable( "SPAC_P_TRAMITES", "TASK");
			factory.addTable( "SPAC_P_FRMTRAMITES", "FORM");
			factory.addTable( "SPAC_CT_ENTIDADES", "CTENTITY");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getRuleTaskEntityVisibleUse("+ ruleId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getRuleTaskEntityVisibleUse("+ ruleId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getRuleStagesUse(int ruleId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID=CTPCD.ID AND PROCD.ID = STAGE.ID_PCD AND STAGE.ID = EVENT.ID_OBJ AND EVENT.TP_OBJ = 2 AND EVENT.ID_REGLA = " + ruleId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_FASES", "STAGE");
			factory.addTable( "SPAC_P_EVENTOS", "EVENT");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getRuleStagesUse("+ ruleId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getRuleStagesUse("+ ruleId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getRuleTasksUse(int ruleId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID=CTPCD.ID AND PROCD.ID = STAGE.ID_PCD AND STAGE.ID = TASK.ID_FASE AND TASK.ID = EVENT.ID_OBJ AND EVENT.TP_OBJ = 3 AND EVENT.ID_REGLA = " + ruleId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_FASES", "STAGE");
			factory.addTable( "SPAC_P_TRAMITES", "TASK");
			factory.addTable( "SPAC_P_EVENTOS", "EVENT");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getRuleTasksUse("+ ruleId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getRuleTasksUse("+ ruleId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getRuleFlowsUse(int ruleId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID=CTPCD.ID AND PROCD.ID = FLOW.ID_PCD AND FLOW.ID = EVENT.ID_OBJ AND EVENT.TP_OBJ = 6 AND EVENT.ID_REGLA = " + ruleId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_FLUJOS", "FLOW");
			factory.addTable( "SPAC_P_EVENTOS", "EVENT");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getRuleFlowsUse("+ ruleId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getRuleFlowsUse("+ ruleId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getProcedureUse(int procedureId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID=CTPCD.ID AND PROCD.ID = " + procedureId + " ORDER BY PROCD.ID";
			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getProcedureUse("+ procedureId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getProcedureUse("+ procedureId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getProcedureByName(String name)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String sql = "WHERE NOMBRE = '" + DBUtil.replaceQuotes(name.trim()) + "'";

			CollectionDAO collection = new CollectionDAO(CTProcedureDAO.class);
			collection.query(cnt,sql);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getProcedureByName("+ name + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getProcedureByName("+ name + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItem getProcedureByCode(String code, int state)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			IItem mitem = null;

			CollectionDAO collection = CTProcedureDAO.getProcedureByCode(cnt, code, state);
			if (collection.next()) {
				mitem = (IItem) collection.value();
			}

			return mitem;
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getProcedureByCode(" + code + ", " + state + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getProcedureByCode(" + code + ", " + state + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}


    public IItem getProcedureById(int pcdId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			IItem mitem = null;

			CollectionDAO collection = CTProcedureDAO.getProcedureById(cnt, pcdId);
			if (collection.next()) {
				mitem = (IItem) collection.value();
			}

			return mitem;
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getProcedureById(" + pcdId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getProcedureById(" + pcdId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    /**
     * Para obtener los subprocesos con el mismo nombre que se le pasa como parámetro.
     * @param name
     * @return
     * @throws ISPACException
     */
    public IItemCollection getSubprocedureByName(String name)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String sql = "WHERE NOMBRE = '" + DBUtil.replaceQuotes(name.trim()) + "' AND ID NOT IN ( SELECT ID FROM SPAC_CT_PROCEDIMIENTOS )";

			CollectionDAO collection = new CollectionDAO(PProcedimientoDAO.class);
			collection.query(cnt,sql);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getSubprocedureByName("+ name + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getSubprocedureByName("+ name + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getStageUse(int stageId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID=CTPCD.ID AND PROCD.ID = STAGE.ID_PCD AND STAGE.ID_CTFASE = " + stageId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_FASES", "STAGE");
	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getStageUse("+ stageId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getStageUse("+ stageId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getSignProcessUse(int signProcessId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID=CTPCD.ID AND PROCD.ID = CTOSFIRMA.ID_PCD AND CTOSFIRMA.ID_CIRCUITO = " + signProcessId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_CTOSFIRMA", "CTOSFIRMA");
	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getSignProcessUse("+ signProcessId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getSignProcessUse("+ signProcessId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getTaskTpDoc(int taskId)
    throws ISPACException
    {
    	DbCnt cnt = mcontext.getConnection();
		try
		{
			String sql = "WHERE TASKTPDOC.ID_TRAMITE  = " + taskId + " AND CT_TPDOC.ID = TASKTPDOC.ID_TPDOC ORDER BY TASKTPDOC.ID";
			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_TRTD", "TASKTPDOC");
			factory.addTable( "SPAC_CT_TPDOC", "CT_TPDOC");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, sql);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getTpDocInTask("+ taskId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getTpDocInTask("+ taskId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}

    }

    public IItemCollection getStageTpDoc(int pcdStageId)
    throws ISPACException
    {
    	DbCnt cnt = mcontext.getConnection();
		try
		{
			String sql = "WHERE STAGETPDOC.ID_FASE  = " + pcdStageId + " AND CT_TPDOC.ID = STAGETPDOC.ID_TPDOC ORDER BY STAGETPDOC.ID";
			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_P_FSTD", "STAGETPDOC");
			factory.addTable( "SPAC_CT_TPDOC", "CT_TPDOC");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, sql);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getStageTpDoc("+ pcdStageId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getStageTpDoc("+ pcdStageId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}

    }

	public IItemCollection getTaskDependencies(int ptaskId) throws ISPACException {

		DbCnt cnt = mcontext.getConnection();
		
		try {
			
			String sql = new StringBuffer("WHERE SPAC_P_DEP_TRAMITES.ID_TRAMITE_HIJO=")
				.append(ptaskId)
				.append(" AND SPAC_P_DEP_TRAMITES.ID_TRAMITE_PADRE=SPAC_P_TRAMITES.ID ORDER BY SPAC_P_TRAMITES.NOMBRE")
				.toString();
			
			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable("SPAC_P_DEP_TRAMITES", "SPAC_P_DEP_TRAMITES");
			factory.addTable("SPAC_P_TRAMITES", "SPAC_P_TRAMITES");

			CollectionDAO collection = factory.queryTableJoin(cnt, sql);
			return collection.disconnect();
			
		} catch (ISPACException ie) {
			logger.error( "Error en ProcedureAPI:getTaskDependencies(" + ptaskId + ")", ie);
			throw new ISPACException("Error en ProcedureAPI:getTaskDependencies(" + ptaskId + ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}

	}
	
	public void addTaskDependency(int ptaskId, int parentPtaskId)
			throws ISPACException {

		DbCnt cnt = mcontext.getConnection();

		try {

			PDepTramiteDAO dep = new PDepTramiteDAO(cnt);
			dep.createNew(cnt);
			dep.set(PDepTramiteDAO.ID_TRAMITE_PADRE, parentPtaskId);
			dep.set(PDepTramiteDAO.ID_TRAMITE_HIJO, ptaskId);
			dep.store(cnt);

		} catch (Exception e) {
			logger.error("Error al añadir la dependencia entre trámites ("
					+ ptaskId + ", " + parentPtaskId + ")", e);
			throw new ISPACException("Error al añadir la dependencia entre trámites ("
					+ ptaskId + ", " + parentPtaskId + ")", e);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}
	
	public void deleteTaskDependency(int dependencyId) throws ISPACException {

		DbCnt cnt = mcontext.getConnection();

		try {
			PDepTramiteDAO.delete(cnt, dependencyId);
		} catch (Exception e) {
			logger.error("Error al eliminar la dependencia de trámite ("+ dependencyId + ")",e);
			throw new ISPACException("Error al eliminar la dependencia de trámite (" + dependencyId + ")", e);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}


	/**
	 * Para sacar los procedimientos que están utilizando el trámite que se le pasa por parámetro
	 * @param taskId
	 * @return
	 * @throws ISPACException
	 */
	public IItemCollection getTaskUse(int taskId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID=CTPCD.ID AND PROCD.ID = STAGE.ID_PCD AND STAGE.ID = TASK.ID_FASE AND TASK.ID_CTTRAMITE = " + taskId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_FASES", "STAGE");
			factory.addTable( "SPAC_P_TRAMITES", "TASK");

	   		CollectionDAO collection = factory.queryTableJoin(cnt, where);
	   		return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getTaskUse("+ taskId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getTaskUse("+ taskId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	/**
	 * Para sacar las fases que utilizan el trámite que le pasamos como parámetro
	 * @param taskId
	 * @return
	 * @throws ISPACException
	 */
	public IItemCollection getStagesTaskUse(int taskId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
	   		// Consulta para sacar las fases que se utilicen de un trámite aunque dicha fase no este asociada a un procedimiento
	   		String where = "WHERE STAGE.ID=FSTR.ID_FASE AND TASK.ID=FSTR.ID_TRAMITE AND TASK.ID = " + taskId;
	   		TableJoinFactoryDAO factory2 = new TableJoinFactoryDAO();
	   		factory2.addTable( "SPAC_CT_FSTR", "FSTR");
	   		factory2.addTable( "SPAC_CT_FASES", "STAGE");
	   		factory2.addTable( "SPAC_CT_TRAMITES", "TASK");

	   		CollectionDAO collection = factory2.queryTableJoin( cnt, where);
	   		return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getStagesTaskUse("+ taskId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getStagesTaskUse("+ taskId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getDocumentStagesUse(int documentId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE PROCD.ID=CTPCD.ID AND PROCD.ID = STAGE.ID_PCD AND STAGE.ID = DOCUMENT.ID_FASE AND DOCUMENT.ID_TPDOC = " + documentId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable( "SPAC_P_FASES", "STAGE");
			factory.addTable( "SPAC_P_FSTD", "DOCUMENT");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getDocumentStagesUse("+ documentId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getDocumentStagesUse("+ documentId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getDocumentActivitiesUse(int documentId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE SUBPCD.ID = ACTIVITY.ID_PCD AND SUBPCD.TIPO = "
				+ IPcdElement.TYPE_SUBPROCEDURE
				+ " AND ACTIVITY.ID = DOCUMENT.ID_FASE AND DOCUMENT.ID_TPDOC = " + documentId
				+ " ORDER BY SUBPCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_P_PROCEDIMIENTOS", "SUBPCD");
			factory.addTable( "SPAC_P_FASES", "ACTIVITY");
			factory.addTable( "SPAC_P_FSTD", "DOCUMENT");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getDocumentActivitiesUse("+ documentId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getDocumentActivitiesUse("+ documentId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public IItemCollection getDocumentTasksUse(int documentId)
    throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE TASK.ID = DOCUMENT.ID_TRAMITE AND DOCUMENT.ID_TPDOC = " + documentId + " ORDER BY TASK.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CT_TRAMITES", "TASK");
			factory.addTable( "SPAC_CT_TRTD", "DOCUMENT");

	   		CollectionDAO collection = factory.queryTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getDocumentTasksUse("+ documentId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getDocumentTasksUse("+ documentId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    /**
	 * Obtiene la lista de procedimientos relacionados con el informe.
	 *
	 * @param reportId
	 *            Identificador del informe.
	 * @return Lista de procedimientos.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getReportProceduresUse(int reportId) throws ISPACException {

		DbCnt cnt = mcontext.getConnection();
		try {
			String where = "WHERE PROCD.ID=CTPCD.ID AND PROCD.ID=REPORT.ID_OBJ AND REPORT.TP_OBJ="
				+ EventsDefines.EVENT_OBJ_PROCEDURE
				+ " AND REPORT.ID_INFORME=" + reportId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable("SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable("SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable("SPAC_P_INFORMES", "REPORT");

			CollectionDAO collection = factory.queryTableJoin(cnt, where);
			return collection.disconnect();

		} catch (ISPACException ie) {
			logger.error("Error en ProcedureAPI:getReportProceduresUse(" + reportId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getReportProceduresUse(" + reportId + ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	/**
	 * Obtiene la lista de fases relacionadas con el informe.
	 *
	 * @param reportId
	 *            Identificador del informe.
	 * @return Lista de fases.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getReportStagesUse(int reportId) throws ISPACException {

		DbCnt cnt = mcontext.getConnection();

		try {
			String where = "WHERE PROCD.ID=CTPCD.ID AND PROCD.ID = STAGE.ID_PCD AND STAGE.ID = REPORT.ID_OBJ AND REPORT.TP_OBJ = "
				+ EventsDefines.EVENT_OBJ_STAGE
				+ " AND REPORT.ID_INFORME = " + reportId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable("SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable("SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable("SPAC_P_FASES", "STAGE");
			factory.addTable("SPAC_P_INFORMES", "REPORT");

			CollectionDAO collection = factory.queryTableJoin(cnt, where);
			return collection.disconnect();

		} catch (ISPACException ie) {
			logger.error("Error en ProcedureAPI:getReportStagesUse(" + reportId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getReportStagesUse(" + reportId + ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	/**
	 * Obtiene la lista de trámites relacionados con el informe.
	 *
	 * @param reportId
	 *            Identificador del informe.
	 * @return Lista de trámites.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getReportTasksUse(int reportId) throws ISPACException {

		DbCnt cnt = mcontext.getConnection();

		try {
			String where = "WHERE PROCD.ID=CTPCD.ID AND PROCD.ID = STAGE.ID_PCD AND STAGE.ID = TASK.ID_FASE AND TASK.ID = REPORT.ID_OBJ AND REPORT.TP_OBJ = "
				+ EventsDefines.EVENT_OBJ_TASK
				+ " AND REPORT.ID_INFORME = " + reportId + " ORDER BY PROCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable("SPAC_CT_PROCEDIMIENTOS", "CTPCD");
			factory.addTable("SPAC_P_PROCEDIMIENTOS", "PROCD");
			factory.addTable("SPAC_P_FASES", "STAGE");
			factory.addTable("SPAC_P_TRAMITES", "TASK");
			factory.addTable("SPAC_P_INFORMES", "REPORT");

			CollectionDAO collection = factory.queryTableJoin(cnt, where);
			return collection.disconnect();

		} catch (ISPACException ie) {
			logger.error("Error en ProcedureAPI:getReportTasksUse(" + reportId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getReportTasksUse(" + reportId + ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	/**
	 * Obtiene la lista de actividades relacionadas con el informe.
	 *
	 * @param reportId
	 *            Identificador del informe.
	 * @return Lista de actividades.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getReportActivitiesUse(int reportId) throws ISPACException {

		DbCnt cnt = mcontext.getConnection();
		try {
			String where = "WHERE SUBPCD.ID = ACTIVITY.ID_PCD AND SUBPCD.TIPO = "
					+ IPcdElement.TYPE_SUBPROCEDURE
					+ " AND ACTIVITY.ID = REPORT.ID_OBJ AND REPORT.TP_OBJ = "
					+ EventsDefines.EVENT_OBJ_ACTIVITY
					+ " AND REPORT.ID_INFORME = " + reportId + " ORDER BY SUBPCD.ID";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable("SPAC_P_PROCEDIMIENTOS", "SUBPCD");
			factory.addTable("SPAC_P_FASES", "ACTIVITY");
			factory.addTable("SPAC_P_INFORMES", "REPORT");

			CollectionDAO collection = factory.queryTableJoin(cnt, where);
			return collection.disconnect();

		} catch (ISPACException ie) {
			logger.error("Error en ProcedureAPI:getReportActivitiesUse(" + reportId + ")",ie);
			throw new ISPACException(
					"Error en ProcedureAPI:getReportActivitiesUse(" + reportId + ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	/**
	 * Obtiene el listado de formularios de busqueda relacionados con el informe.
	 * @param reportId
	 * 				Identificador del informe
	 * @return Lista de informes
	 * @throws ISPACException
	 * 						Si ocurre algún error
	 */
	public IItemCollection getReportSearchForm(int reportId) throws ISPACException{

		return getReportSearchForm(reportId,null);
	}
	/**
	 * Obtiene el listado de formularios de busqueda relacionados con el informe.
	 * @param reportId
	 * 				Identificador del informe
	 * @resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return Lista de informes
	 * @throws ISPACException
	 * 						Si ocurre algún error
	 */
	public IItemCollection getReportSearchForm(int reportId,String resp) throws ISPACException{

		String where = " WHERE "+FrmBusquedaReportDAO.TABLENAME+".ID_INFORME="+reportId;
		   where+=" AND "+FrmBusquedaReportDAO.TABLENAME+".ID_FMRBUSQUEDA=";
		   where+=CTFrmBusquedaDAO.TABLENAME+".ID ORDER BY "+CTFrmBusquedaDAO.TABLENAME+".DESCRIPCION";
		if(StringUtils.isEmpty(resp)){
			DbCnt cnt = mcontext.getConnection();
			try {


				TableJoinFactoryDAO tableJoinFactoryDAO = new TableJoinFactoryDAO();
				tableJoinFactoryDAO.addTable(CTFrmBusquedaDAO.TABLENAME,CTFrmBusquedaDAO.TABLENAME);
				tableJoinFactoryDAO.addTable(FrmBusquedaReportDAO.TABLENAME, FrmBusquedaReportDAO.TABLENAME);


				CollectionDAO collection = tableJoinFactoryDAO.queryTableJoin(cnt, where);
				return collection.disconnect();

			} catch (ISPACException ie) {
				logger.error("Error en ProcedureAPI:getReportActivitiesUse(" + reportId + ")",ie);
				throw new ISPACException(
						"Error en ProcedureAPI:getReportActivitiesUse(" + reportId + ")", ie);
			} finally {
				mcontext.releaseConnection(cnt);
			}
		}
		else{
			String [] tablesJoinToAddFactory= new String[2];
			tablesJoinToAddFactory [0]=CTFrmBusquedaDAO.TABLENAME;
			tablesJoinToAddFactory [1]=FrmBusquedaReportDAO.TABLENAME;
			return getReportsByResp(where, resp, tablesJoinToAddFactory);
		}
	}

    public IItemCollection getTpDocsTemplates(int tpDoc)
    throws ISPACException
    {
    	DbCnt cnt = mcontext.getConnection();
		try
		{
			String sql = "WHERE ID_TPDOC  = " + tpDoc + " ORDER BY ID";

			CollectionDAO collection = new CollectionDAO(TemplateDAO.class);
			collection.query(cnt,sql);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getTpDocsTemplates("+ tpDoc + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getTpDocsTemplates("+ tpDoc + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}

    }
    public IItemCollection getProcTemplates(int tpDoc, int procId)
    throws ISPACException{
    	DbCnt cnt = mcontext.getConnection();
		try
		{
			//OBTENER PLANTILLAS DE TPO DE DCUMENTO Y LAS ESPECIFICAS DEL PROC:
			// todas las plantillas del tipo de documento que no sean [plantillas especificas de otros procedimientos
			// y que no esten [añadidas como especificas al procedimiento]]
			StringBuffer sql = new StringBuffer("WHERE ID_TPDOC  = ").append(tpDoc).append(" AND ID NOT IN (");
			sql.append(" SELECT ID_P_PLANTDOC FROM SPAC_P_PLANTILLAS WHERE ID_PCD!=").append(procId);
			sql.append(" AND ID_P_PLANTDOC NOT IN (SELECT ID_P_PLANTDOC FROM SPAC_P_PLANTILLAS WHERE ID_PCD=").append(procId).append(" )");
			sql.append(" )ORDER BY ID");

			CollectionDAO collection = new CollectionDAO(TemplateDAO.class);
			collection.query(cnt,sql.toString());
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getTpDocsTemplates("+ tpDoc + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getTpDocsTemplates("+ tpDoc + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}

    }

	public boolean isFirstStage(int pcdId, int stagePcdId) throws ISPACException {
    	DbCnt cnt = mcontext.getConnection();
		try
		{
			return PFlujoDAO.isFirstNode(cnt, pcdId, stagePcdId);
		}
		catch (ISPACException ie)
		{
			logger.error("exception.procedures.flow.isFirst",ie);
			throw new ISPACException("exception.procedures.flow.isFirst");
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	public boolean isLastStage(int pcdId, int stagePcdId) throws ISPACException {
    	DbCnt cnt = mcontext.getConnection();
		try
		{
			return PFlujoDAO.isLastNode(cnt, pcdId, stagePcdId);
		}
		catch (ISPACException ie)
		{
			logger.error("exception.procedures.flow.isLast",ie);
			throw new ISPACException("exception.procedures.flow.isLast");
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    /**
     * Obtiene la lista de fases de un procedimiento.
     * @param pcdId Identificador del procedimiento.
     * @return Lista de fases.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getStages(int pcdId) throws ISPACException {

    	DbCnt cnt = mcontext.getConnection();

		try {

			String sql = new StringBuffer()
				.append("WHERE ID_PCD=")
				.append(pcdId)
				.append(" ORDER BY ID")
				.toString();

			CollectionDAO collection = new CollectionDAO(PFaseDAO.class);
			collection.query(cnt, sql);

			return collection.disconnect();

		} catch (ISPACException ie) {
			logger.error("Error en ProcedureAPI:getStages("
					+ pcdId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getStages("
					+ pcdId + ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}
    }

    /**
     * Obtiene la lista de trámites de una fase en el procedimiento.
     * @param pcdStageId Identificador de la fase del procedimiento.
     * @return Lista de trámites.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getStageTasks(int pcdStageId) throws ISPACException {

    	DbCnt cnt = mcontext.getConnection();

		try {

			String sql = new StringBuffer()
				.append("WHERE ID_FASE=")
				.append(pcdStageId)
				.append(" ORDER BY ORDEN")
				.toString();

			CollectionDAO collection = new CollectionDAO(PTramiteDAO.class);
			collection.query(cnt, sql);

			return collection.disconnect();

		} catch (ISPACException ie) {
			logger.error("Error en ProcedureAPI:getStageTasks("
					+ pcdStageId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getStageTasks("
					+ pcdStageId + ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}

    }

    /**
     * Obtiene la lista de nodos de sincronización del procedimiento.
     * @param pcdId Identificador del procedimiento.
     * @return Lista de nodos de sincronización.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getSyncNodes(int pcdId) throws ISPACException {

    	DbCnt cnt = mcontext.getConnection();

		try {

			String sql = new StringBuffer()
				.append("WHERE ID_PCD=")
				.append(pcdId)
				.append(" ORDER BY ID")
				.toString();

			CollectionDAO collection = new CollectionDAO(PSincNodoDAO.class);
			collection.query(cnt, sql);

			return collection.disconnect();

		} catch (ISPACException ie) {
			logger.error("Error en ProcedureAPI:getSyncNodes("
					+ pcdId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getSyncNodes("
					+ pcdId + ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}
    }

    /**
     * Obtiene la lista de flujos de entrada del nodo.
     * @param nodeId Identificador del nodo.
     * @return Lista de flujos de entrada.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getNodeInputFlows(int nodeId) throws ISPACException {

    	DbCnt cnt = mcontext.getConnection();

		try {

			String sql = new StringBuffer()
				.append("WHERE FLOW.ID_DESTINO=").append(nodeId)
				.append(" AND FLOW.ID_ORIGEN=NODE.ID")
				.toString();

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable("SPAC_P_FLUJOS", "FLOW");
			factory.addTable("SPAC_P_NODOS", "NODE");

	   		CollectionDAO collection = factory.queryTableJoin(cnt, sql);
	   		return getExtendedNodes(collection.disconnect());

		} catch (ISPACException ie) {
			logger.error("Error en ProcedureAPI:getNodeInputFlows("
					+ nodeId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getNodeInputFlows("
					+ nodeId + ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}
    }

    /**
     * Obtiene la lista de flujos de salida de un nodo.
     * @param nodeId Identificador del nodo.
     * @return Lista de flujos de salida.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getNodeOutputFlows(int nodeId) throws ISPACException {

    	DbCnt cnt = mcontext.getConnection();

		try {

			String sql = new StringBuffer()
				.append("WHERE FLOW.ID_ORIGEN=").append(nodeId)
				.append(" AND FLOW.ID_DESTINO=NODE.ID")
				.append(" ORDER BY FLOW.ID")
				.toString();

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable("SPAC_P_FLUJOS", "FLOW");
			factory.addTable("SPAC_P_NODOS", "NODE");

	   		CollectionDAO collection = factory.queryTableJoin(cnt, sql);
	   		return getExtendedNodes(collection.disconnect());

		} catch (ISPACException ie) {
			logger.error("Error en ProcedureAPI:getNodeOutputFlows("
					+ nodeId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getNodeOutputFlows("
					+ nodeId + ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}
    }

    /**
     * Obtiene la lista de nodos origen que se pueden asociar a un flujo para
     * un nodo destino.
     * @param pcdId Identificador del procedimiento.
     * @param nodeId Identificador del nodo destino del flujo.
     * @return Lista de nodos.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getAvailableInputFlowNodes(int pcdId, int nodeId)
    		throws ISPACException {

		final String conditions = new StringBuffer()
			.append("WHERE SPAC_P_NODOS.ID NOT IN ")
			.append("(SELECT ID_ORIGEN FROM SPAC_P_FLUJOS WHERE ID_DESTINO=")
			.append(nodeId).append(")")
			.append(" AND SPAC_P_NODOS.ID_PCD=").append(pcdId)
			.append(" AND SPAC_P_NODOS.ID!=").append(nodeId)
			.append(" ORDER BY SPAC_P_NODOS.ID")
			.toString();

		return getNodes(conditions);
    }

    /**
     * Obtiene la lista de nodos destino que se pueden asociar a un flujo para
     * un nodo origen.
     * @param pcdId Identificador del procedimiento.
     * @param nodeId Identificador del nodo destino del flujo.
     * @return Lista de nodos.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getAvailableOutputFlowNodes(int pcdId, int nodeId)
    		throws ISPACException {

		final String conditions = new StringBuffer()
			.append("WHERE SPAC_P_NODOS.ID NOT IN ")
			.append("(SELECT ID_DESTINO FROM SPAC_P_FLUJOS WHERE ID_ORIGEN=")
			.append(nodeId).append(")")
			.append(" AND SPAC_P_NODOS.ID_PCD=").append(pcdId)
			.append(" AND SPAC_P_NODOS.ID!=").append(nodeId)
			.append(" ORDER BY SPAC_P_NODOS.ID")
			.toString();

		return getNodes(conditions);
    }

    /**
     * Obtiene una lista de nodos.
     * @param conditions Condiciones de la búsqueda de nodos.
     * @return Lista de nodos.
     * @throws ISPACException si ocurre algún error.
     */
	private IItemCollection getNodes(String conditions) throws ISPACException {

		final Property[] cols = new Property[] {
			new Property(1, "ID", "SPAC_P_NODOS.ID", Types.INTEGER),
			new Property(2, "TIPO", "SPAC_P_NODOS.TIPO", Types.INTEGER),
			new Property(3, "NOMBRE", "SPAC_P_FASES.NOMBRE", Types.VARCHAR),
		};

		final String tables =
			"SPAC_P_NODOS LEFT OUTER JOIN SPAC_P_FASES ON SPAC_P_NODOS.ID=SPAC_P_FASES.ID";

		DbCnt cnt = mcontext.getConnection();

		try {

			CollectionDAO results = TableDAO.newCollectionDAO(TableDAO.class,
					tables, cols);

			results.query(cnt, conditions);
			return results.disconnect();

		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

    private IItemCollection getExtendedNodes(IItemCollection flows)
    		throws ISPACException {

    	List nodes = new ArrayList();

    	IItem flow;
    	int nodeType;

   		while (flows.next()) {

   			flow = flows.value();
   			nodeType = flow.getInt("NODE:TIPO");

   			if (nodeType == PNodoDAO.NODE_OBJ_STAGE) {

   				// Información de la fase
   				IItem stage = mcontext.getAPI().getProcedureStage(
   						flow.getInt("NODE:ID"));

   				CompositeItem item = new CompositeItem("FLOW:ID");
   				item.addItem(flow, "");
   				item.addItem(stage, "STAGE:");

   				nodes.add(item);

   			} else if (nodeType == PNodoDAO.NODE_OBJ_SYNCNODE) {

   				// Información del nodo de sincronización
   				nodes.add(flow);
   			}
   		}

   		return new ListCollection(nodes);
    }

    public void updateGInfoNode(GInfo gInfo,Integer nodeId) throws ISPACException{

    	if (gInfo != null && nodeId!=null) {

			// Ejecución en un contexto transaccional
			boolean ongoingTX = mcontext.ongoingTX();
			boolean bCommit = false;

			try {
				if (!ongoingTX) {
					mcontext.beginTX();
				}
				DbCnt cnt = mcontext.getConnection();

		        // Información gráfica del nodo
				PNodoDAO node = new PNodoDAO(cnt, nodeId.intValue());
				node.set("G_INFO", gInfo.toXml());
				node.store(cnt);

				// Si todo ha sido correcto se hace commit de la transacción
				bCommit = true;
		    }
			finally {
				if (!ongoingTX) {
					mcontext.endTX(bCommit);
				}
		    }
    	}
    }

	public boolean isStageAsociated(int pcdId, int stageCtId) throws ISPACException {
		DbCnt cnt = mcontext.getConnection();
		try{
			return mcontext.getAPI().getEntitiesAPI().countEntities(SpacEntities.SPAC_P_FASES, "WHERE ID_PCD = " + pcdId + " AND ID_CTFASE = " + stageCtId) >0;
		}catch (ISPACException ie){
			logger.error("Error en ProcedureAPI:isStageAsociated(" + pcdId + " " +stageCtId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:isStageAsociated(" + pcdId + " " +stageCtId + ")", ie);
		}finally{
			mcontext.releaseConnection(cnt);
		}
	}

	public IItem getStagePcd(int pcdId, String stageCtId) throws ISPACException {
		DbCnt cnt = mcontext.getConnection();
		try{
			IItemCollection itemcol = mcontext.getAPI().getEntitiesAPI().queryEntities(SpacEntities.SPAC_P_FASES, "WHERE ID_PCD = " + pcdId + " AND ID_CTFASE = " + stageCtId);
			if (itemcol.next())
				return itemcol.value();
		}catch (ISPACException ie){
			logger.error("Error en ProcedureAPI:getStagePcd(" + pcdId + ", " + stageCtId + ")",ie);
			throw new ISPACException("Error en ProcedureAPI:getStagePcd(" + pcdId + ", " + stageCtId + ")", ie);
		}finally{
			mcontext.releaseConnection(cnt);
		}
		return null;
	}

    /**
     * Actualiza la posición de los nodos del procedimiento.
     * @param gInfoMap Mapa con la posición de los nodos del procedimiento.
     * @throws ISPACException si ocurre algún errror.
     *
    public void updateGInfo(Map gInfoMap) throws ISPACException {

	    TransactionContainer txcontainer = mcontext.getTXContainer();

	    try {

	        if ((gInfoMap != null) && (!gInfoMap.isEmpty())) {

		        txcontainer.begin();

		        DbCnt cnt = txcontainer.getConnection();

		        Iterator nodeIdIt = gInfoMap.keySet().iterator();
		        while (nodeIdIt.hasNext()) {

		        	// Identificador del nodo
		        	// Comprobar el cast: si este valor viene de un ID de BD en Oracle son Long
		        	Integer nodeId = (Integer) nodeIdIt.next();
		        	if (nodeId != null) {

		        		// Información gráfica del nodo
			        	GInfo gInfo = (GInfo) gInfoMap.get(nodeId);
			        	if (gInfo != null) {

				        	PNodoDAO node = new PNodoDAO(cnt, nodeId.intValue());
				        	node.set("G_INFO", gInfo.toXml());
				        	node.store(cnt);
			        	}
		        	}
		        }

		        txcontainer.commit();
	        }

	    } finally {
	        txcontainer.release();
	    }
    }*/


	/**
	 * Obtiene los informes relacionados con un objeto.
	 * @param objectId Identificador del objeto.
	 * @param objectType Tipo de objeto.
	 * @return IItemCollection Lista de informes.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getPReports(int objectId , int objectType) throws ISPACException {

		return getPReports(objectId, objectType, null);
	}
	/**
	 * Obtiene los informes relacionados con un objeto.
	 * @param objectId Identificador del objeto.
	 * @param objectType Tipo de objeto.
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return IItemCollection Lista de informes.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getPReports(int objectId , int objectType,String resp) throws ISPACException {

		String where= "WHERE SPAC_CT_INFORMES.TIPO=2 and SPAC_P_INFORMES.ID_INFORME = SPAC_CT_INFORMES.ID AND SPAC_P_INFORMES.ID_OBJ  = "
			+ objectId + " AND SPAC_P_INFORMES.TP_OBJ=" + objectType;
		if(StringUtils.isEmpty(resp) ||StringUtils.equalsIgnoreCase(resp, IResponsible.SUPERVISOR)){
			DbCnt cnt = mcontext.getConnection();

			try {

				TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
				factory.addTable("SPAC_CT_INFORMES", "SPAC_CT_INFORMES");
				factory.addTable("SPAC_P_INFORMES", "SPAC_P_INFORMES");
				CollectionDAO collection = factory.queryTableJoin(cnt, where);
				return collection.disconnect();

			} catch (ISPACException ie) {
				logger.error("Error al obtener los informes relacionados con el objeto ("
						+ objectId + ", " + objectType + ")",ie);
				throw new ISPACException("Error al obtener los informes relacionados con el objeto ("
						+ objectId + ", " + objectType + ")", ie);
			} finally {
				mcontext.releaseConnection(cnt);
			}
		}
		else{
			String tablesToAddFactory[]=new String [1];
			tablesToAddFactory[0]="SPAC_P_INFORMES";
			return getReportsByResp(where, resp, tablesToAddFactory);
		}
	}

	/**
	  * Obtiene la lista de informes relacionados en el contexto del expediente.
	  * @param stateContext Contexto del expediente.
	  * @return Lista de informes disponibles para la fase o trámite actual ( generales + los del proc+ los propios de la fase o trámite)
	  * @throws ISPACException
	  */
	public IItemCollection getReports(StateContext stateContext) throws ISPACException {

		return getReports(stateContext, null);
	}
	/**
	  * Obtiene la lista de informes relacionados en el contexto del expediente.
	  * @param stateContext Contexto del expediente.
	  * @return Lista de informes disponibles para la fase o trámite actual ( generales + los del proc+ los propios de la fase o trámite)
	  * @throws ISPACException
	  */
	public IItemCollection getReports(StateContext stateContext,String resp)throws ISPACException {

		if(StringUtils.isEmpty(resp) ||StringUtils.equalsIgnoreCase(resp, IResponsible.SUPERVISOR)){
			DbCnt cnt = mcontext.getConnection();
			try {

				CollectionDAO collection = new CollectionDAO(CTReportDAO.class);
				collection.query(cnt, getStateReportsSQL(stateContext, true));
				return collection.disconnect();

			} catch (ISPACException ie) {
				logger.error("Error al obtener los informes disponibles",ie);
				throw new ISPACException("Error al obtener los informes disponibles", ie);
			} finally {
				mcontext.releaseConnection(cnt);
			}
		}
		else{
			return getReportsByResp(getStateReportsSQL(stateContext, false), resp,null);
		}
	}
	/**
	 * Obtiene la lista de informes globales
	 * @return
	 * @throws ISPACException
	 */
	public IItemCollection getGlobalReports()throws ISPACException{
		return getGlobalReports(null);
	}

	/**
	 * Obtiene la lista de informes globales en función de la responsabilidad asociada a los informes
     * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return
	 * @throws ISPACException
	 */
	public IItemCollection getGlobalReports(String resp) throws ISPACException {

		DbCnt cnt = mcontext.getConnection();
		String whereTipo="WHERE TIPO=" + CTReportDAO.GLOBAL_TYPE ;
		if(StringUtils.isEmpty(resp) ||StringUtils.equalsIgnoreCase(resp, IResponsible.SUPERVISOR)){
			try {
					CollectionDAO collection = new CollectionDAO(CTReportDAO.class);
					collection.query(cnt,  whereTipo +" ORDER BY NOMBRE" );
					return collection.disconnect();


			} catch (ISPACException ie) {
				logger.error("Error al obtener los informes globales disponibles",ie);
				throw new ISPACException("Error al obtener los informes globales disponibles", ie);
			} finally {
				mcontext.releaseConnection(cnt);
			}
		}
	  else{
		  return getReportsByResp(whereTipo, resp,null);
	  }
	}

	/**
     * Indica si hay informes asociados al formulario de busqueda
     * @deprecated
     * @param idForm
     * @return
     * @throws ISPACException
     */
    public boolean hashSearchReport(int idForm) throws ISPACException{

    	return hasSearchReport(idForm, null);
    }


	/**
     * Indica si hay informes asociados al formulario de busqueda
     * @param idForm
     * @return
     * @throws ISPACException
     */
    public boolean hasSearchReport(int idForm) throws ISPACException{

    	return hasSearchReport(idForm, null);
    }

	/**
     * Indica si hay informes asociados al formulario de busqueda y que o no tenga responsable asignado , o si lo tienen que el usuario
     * conectado pertenezca al la lista de responsables del informe
     * @param idForm
     * @return
     * @throws ISPACException
     */
    public boolean hasSearchReport(int idForm, String resp) throws ISPACException{
    	String where= "WHERE ID_FMRBUSQUEDA=" +idForm;
    	if(StringUtils.isEmpty(resp) ||StringUtils.equalsIgnoreCase(resp, IResponsible.SUPERVISOR)){
	    	DbCnt cnt = mcontext.getConnection();

			try {

				CollectionDAO collection = new CollectionDAO(FrmBusquedaReportDAO.class);
				int count = collection.count(cnt,  "WHERE ID_FMRBUSQUEDA=" +idForm);
				return (count > 0);

			} catch (ISPACException ie) {
				logger.error("Error al comprobar si hay informes disponibles asociados al formulario de busqueda ",ie);
				throw new ISPACException("Error al comprobar si hay informes disponibles asociados al formulario de busqueda "+idForm, ie);
			} finally {
				mcontext.releaseConnection(cnt);
			}
    	}
    	else{
    		return hasReportsByResp(where, resp," , "+ FrmBusquedaReportDAO.TABLENAME);
    	}
    }

	/**
	 * Obtiene la lista de informes de tipo búsqueda que están asociados al formulario de búsqueda que recibe como parámetro
	 * @param idFrmBusqueda Identificador del fomulario de búsqueda
	 * @return Lista de informes disponibles para el formulario de búsqueda actual.
	 * @throws ISPACException
	 */
	public IItemCollection getSearchReports(int idFrmBusqueda)throws ISPACException{
			return getSearchReports(idFrmBusqueda, null);
	}

	/**
	 * Obtiene la lista de informes de tipo búsqueda que están asociados al formulario de búsqueda que recibe como parámetro
	 * y que tenga al usuario como responsable o no tenga usuario responsable el informe
	 * @param idFrmBusqueda Identificador del fomulario de búsqueda
	  * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return Lista de informes disponibles para el formulario de búsqueda actual.
	 * @throws ISPACException
	 */
	public IItemCollection getSearchReports(int idFrmBusqueda, String resp)throws ISPACException{

		String sql = " WHERE " + CTReportDAO.TABLENAME + ".ID="+FrmBusquedaReportDAO.TABLENAME+".ID_INFORME AND "+FrmBusquedaReportDAO.TABLENAME+".ID_FMRBUSQUEDA="+idFrmBusqueda;

		if(StringUtils.isEmpty(resp) ||StringUtils.equalsIgnoreCase(resp, IResponsible.SUPERVISOR)){
			DbCnt cnt = mcontext.getConnection();

			try {
				TableJoinFactoryDAO tableJoinFactoryDAO = new TableJoinFactoryDAO();
				tableJoinFactoryDAO.addTable(CTReportDAO.TABLENAME,CTReportDAO.TABLENAME) ;
				tableJoinFactoryDAO.addTable(FrmBusquedaReportDAO.TABLENAME, FrmBusquedaReportDAO.TABLENAME);
				CollectionDAO collection = tableJoinFactoryDAO.queryTableJoin(cnt, sql);
				return collection.disconnect();

			} catch (ISPACException ie) {
				logger.error("Error al obtener los informes disponibles",ie);
				throw new ISPACException("Error al obtener los informes disponibles", ie);
			} finally {
				mcontext.releaseConnection(cnt);
			}
		}
		else{
			String[] tablesToAddFactory=new String[1];
			tablesToAddFactory[0]=FrmBusquedaReportDAO.TABLENAME;
			return getReportsByResp(sql, resp, tablesToAddFactory);
		}
	}

	 /**
     * Indica si existe o no informes globales
     * @return
     * @throws ISPACException
     */
	public boolean hasGlobalReports()throws ISPACException{
		return hasGlobalReports(null);
	}

	 /**
     * Indica si existe o no informes globales para el usuario conectado
     * @return
     * @throws ISPACException
     */
	public boolean hasGlobalReports(String resp)throws ISPACException{

		String where="WHERE TIPO=" + CTReportDAO.GLOBAL_TYPE;
		if(StringUtils.isEmpty(resp) ||StringUtils.equalsIgnoreCase(resp, IResponsible.SUPERVISOR)){
			DbCnt cnt = mcontext.getConnection();

			try {

				CollectionDAO collection = new CollectionDAO(CTReportDAO.class);
				int count = collection.count(cnt, where );
				return (count > 0);

			} catch (ISPACException ie) {
				logger.error("Error al comprobar si hay informes disponibles",ie);
				throw new ISPACException("Error al comprobar si hay informes disponibles", ie);
			} finally {
				mcontext.releaseConnection(cnt);
			}
		}
		return hasReportsByResp(where, resp,"");
	}

	/**
	 * Indica si el hay informes relacionados en el contexto del expediente.
	 * @param stateContext Contexto del expediente.
	 * @return True si hay algun informe disponible, false en caso contrario.
	 * @throws ISPACException si ocurre algún error.
	 */
	public boolean hasReports(StateContext stateContext)throws ISPACException {
		return hasReports(stateContext, null);
	}
	/**
	 * Indica si el hay informes relacionados en el contexto del expediente a los que tenga permiso el usuario.
	 * @param stateContext Contexto del expediente.
	 * @return True si hay algun informe disponible, false en caso contrario.
	 * @throws ISPACException si ocurre algún error.
	 */
	public boolean hasReports(StateContext stateContext, String resp)throws ISPACException {
		String where = getStateReportsSQL(stateContext, false);
		if(StringUtils.isEmpty(resp) ||StringUtils.equalsIgnoreCase(resp, IResponsible.SUPERVISOR)){
			DbCnt cnt = mcontext.getConnection();
			try {

				CollectionDAO collection = new CollectionDAO(CTReportDAO.class);
				int count = collection.count(cnt, where );
				return (count > 0);

			} catch (ISPACException ie) {
				logger.error("Error al comprobar si hay informes disponibles",ie);
				throw new ISPACException("Error al comprobar si hay informes disponibles", ie);
			} finally {
				mcontext.releaseConnection(cnt);
			}
		}
		else{
			return hasReportsByResp(where, resp ,"");
		}
	}


	private String getStateReportsSQL(StateContext stateContext, boolean ordered) {
		String sql = "WHERE (TIPO=" + CTReportDAO.GENERIC_TYPE;

		if (stateContext != null) {

			String pconds = "";

			if (stateContext.getPcdId() > 0) {
				pconds += "(TP_OBJ=" + EventsDefines.EVENT_OBJ_PROCEDURE
					+ " AND ID_OBJ=" + stateContext.getPcdId() + ")";
			}

			if (stateContext.getStagePcdId() > 0) {
				pconds += " OR (TP_OBJ=" + EventsDefines.EVENT_OBJ_STAGE
					+ " AND ID_OBJ=" + stateContext.getStagePcdId() + ")";
			}

			if (stateContext.getTaskPcdId() > 0) {
				pconds += " OR (TP_OBJ=" + EventsDefines.EVENT_OBJ_TASK
					+ " AND ID_OBJ=" + stateContext.getTaskPcdId() + ")";
			}

			if (stateContext.getSubPcdId() > 0) {
				pconds += " OR (TP_OBJ=" + EventsDefines.EVENT_OBJ_SUBPROCEDURE
					+ " AND ID_OBJ=" + stateContext.getSubPcdId() + ")";
			}

			if (stateContext.getActivityPcdId() > 0) {
				pconds += " OR (TP_OBJ=" + EventsDefines.EVENT_OBJ_ACTIVITY
					+ " AND ID_OBJ=" + stateContext.getActivityPcdId() + ")";
			}

			if (StringUtils.isNotBlank(pconds)) {
				sql += " OR "+CTReportDAO.TABLENAME+".ID IN (SELECT ID_INFORME FROM SPAC_P_INFORMES WHERE "
					+ pconds
					+ "))";
			}
		}

		if (ordered) {
			sql += " ORDER BY NOMBRE";
		}

		return sql;
	}

    /**
	 * Relaciona un informe con el objeto indicado.
	 *
	 * @param objectType
	 *            Tipo de objeto.
	 * @param objectId
	 *            Identificador de objeto.
	 * @param reportId
	 *            Identificador del informe.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void addPReport(int objectType, int objectId, int reportId)
			throws ISPACException {

		DbCnt cnt = mcontext.getConnection();

		try {

			PReportDAO objevent = new PReportDAO(cnt);
			objevent.createNew(cnt);
			objevent.set(PReportDAO.TPOBJ, objectType);
			objevent.set(PReportDAO.IDOBJ, objectId);
			objevent.set(PReportDAO.IDREPORT, reportId);
			objevent.store(cnt);

		} catch (Exception e) {
			logger.error("Error al relacionar el informe ("
					+ objectType + ", " + objectId + "," + reportId + ")",e);
			throw new ISPACException("Error al relacionar el informe ("
					+ objectType + ", " + objectId + "," + reportId + ")", e);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	/**
	 * Elimina la relación entre el informe y el objeto seleccionado.
	 *
	 * @param objectType
	 *            Tipo de objeto.
	 * @param objectId
	 *            Identificador de objeto.
	 * @param reportId
	 *            Identificador del informe.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void deletePReport(int objectType, int objectId, int reportId)
			throws ISPACException {

		DbCnt cnt = mcontext.getConnection();

		try {
			PReportDAO.delete(cnt, objectType, objectId, reportId);
		} catch (Exception e) {
			logger.error("Error al eliminar la relación del informe("
					+ objectType + ", " + objectId + "," + reportId + ")",e);
			throw new ISPACException("Error al eliminar la relación del informe("
					+ objectType + ", " + objectId + "," + reportId + ")", e);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	/**
	 * Elimina todas las relaciones del informe.
	 *
	 * @param reportId
	 *            Identificador del informe.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void deletePReport(int reportId) throws ISPACException {

		DbCnt cnt = mcontext.getConnection();

		try {
			PReportDAO.delete(cnt, reportId);
		} catch (Exception e) {
			logger.error("Error al eliminar las relaciones del informe(" + reportId + ")",e);
			throw new ISPACException("Error al eliminar las relaciones del informe(" + reportId + ")", e);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}
	/**
	 * Devuelve la lista de informes que cumple la where y o el responsable pertenece a la lista de responsables del informe
	 * o el informe no tiene responsable
	 * @param where
	 * @param resp
	 * @param tablesToAddFactory
	 * @return
	 * @throws ISPACException
	 */
	private IItemCollection getReportsByResp(String where , String resp, String [] tablesToAddFrom) throws ISPACException{
			DbCnt cnt = mcontext.getConnection();
			try {


				where +=" AND ( "+CTReportDAO.TABLENAME + ".VISIBILIDAD =1  OR ("+CTReportDAO.TABLENAME + ".VISIBILIDAD !=1 AND " + CTReportDAO.TABLENAME + ".ID = " + CTReportOrgDAO.TABLENAME + ".ID_INFORME";

				if(!StringUtils.equalsIgnoreCase(resp, IResponsible.SUPERVISOR)){
					where+= DBUtil.addAndInResponsibleCondition(CTReportOrgDAO.TABLENAME +".UID_USR", resp );
				}

				where+=" ) )" ;


				String sql="WHERE "+CTReportDAO.TABLENAME+".ID IN ( " +
					   "SELECT "+CTReportDAO.TABLENAME+".ID  FROM "+	CTReportDAO.TABLENAME+" LEFT OUTER JOIN "+CTReportOrgDAO.TABLENAME+" ON "+CTReportDAO.TABLENAME+".ID="+CTReportOrgDAO.TABLENAME+".ID_INFORME ";


				if(tablesToAddFrom!=null){
					int i=0;
					for(i=0; i<tablesToAddFrom.length; i++){
						sql+=","+tablesToAddFrom[i]+" ";
					}
				}
				where=sql+where+") ORDER BY " + CTReportDAO.TABLENAME + ".NOMBRE";

				return CTReportDAO.getReports( cnt, where);

			} catch (ISPACException ie) {
				logger.error("Error al obtener los informes  disponibles por responsabilidad",ie);
				throw new ISPACException("Error al obtener los informes  disponibles por responsabilidad", ie);
			} finally {
				mcontext.releaseConnection(cnt);
			}

	}
	/**
	 * Devuelve cierto si hay informes disponibles para el usuario conectado o no.
	 * @param where
	 * @param resp
	 * @param tablesToAddFactory
	 * @return
	 * @throws ISPACException
	 */
	private boolean hasReportsByResp(String where , String resp, String tablesToFrom ) throws ISPACException{

		DbCnt cnt = mcontext.getConnection();

		try {

			where +=" AND ( "+CTReportDAO.TABLENAME + ".VISIBILIDAD =1  OR ("+CTReportDAO.TABLENAME + ".VISIBILIDAD !=1  AND " + CTReportDAO.TABLENAME + ".ID = " + CTReportOrgDAO.TABLENAME + ".ID_INFORME";
			where += DBUtil.addAndInResponsibleCondition(CTReportOrgDAO.TABLENAME +".UID_USR", resp);
			where+=") )" ;

			final Property[] cols = new Property[] {
					new Property(1, "ID", CTReportDAO.TABLENAME+".ID", Types.INTEGER),
				};

			final String tables =
				CTReportDAO.TABLENAME+" LEFT OUTER JOIN "+CTReportOrgDAO.TABLENAME+" ON "+CTReportDAO.TABLENAME+".ID="+CTReportOrgDAO.TABLENAME+".ID_INFORME "+tablesToFrom;
				CollectionDAO results = TableDAO.newCollectionDAO(TableDAO.class,tables, cols);


			return(results.count(cnt, where) >0);

		} catch (ISPACException ie) {
			logger.error("Error al obtener los informes  disponibles por responsabilidad",ie);
			throw new ISPACException("Error al obtener los informes  disponibles por responsabilidad", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}

}


	/**
	 * Elimina la relación entre el informe y los usuarios
	 *
	 * @param reportId
	 *            Identificador del informe.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void deleteUsersAssociatedToReport(int reportId)
			throws ISPACException {

		DbCnt cnt = mcontext.getConnection();

		try {
			CTReportOrgDAO.deleteAllUsersToReport(cnt, reportId+"");
		} catch (Exception e) {
			logger.error("Error al eliminar los usuarios asociados al  informe",e);
			throw new ISPACException("Error al eliminar los usuarios asociados al  informe"+ reportId , e);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

   public IItemCollection getSignProcessInUse(int signProcessId) throws ISPACException{
    	DbCnt cnt = mcontext.getConnection();
		try
		{


			String where = "WHERE CTOSFIRMA.ID_CIRCUITO="+signProcessId+" AND DOCUMENTOS.ID = CTOSFIRMA.ID_DOCUMENTO AND CTOSFIRMA.ESTADO=1";

			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CTOS_FIRMA", "CTOSFIRMA");
			factory.addTable( "SPAC_DT_DOCUMENTOS", "DOCUMENTOS");
	   		CollectionDAO collection = factory.queryDistinctTableJoin( cnt, where);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:getSingProcessInUse("+ signProcessId + ")", ie);
			throw new ISPACException("Error en ProcedureAPI:getSingProcessInUse("+ signProcessId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
    }


    public boolean isSignProcessInUse(int signProcessId) throws ISPACException{

    	DbCnt cnt = mcontext.getConnection();
		try
		{
			String sql = "WHERE ID_CIRCUITO="+signProcessId+" AND ESTADO=1";

			CollectionDAO collection = new CollectionDAO(SignCircuitInstanceDAO.class);

			return collection.count(cnt,sql) > 0 ;
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:isSignerInUse("+ signProcessId +")", ie);
			throw new ISPACException("Error en ProcedureAPI:isSignerInUse("+ signProcessId +")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}

    }

	public int countExpedientesByPcd(int idPcd) throws ISPACException {
		
		DbCnt cnt = mcontext.getConnection();

		try
		{
			return TXProcesoDAO.countProcess(cnt, "", " TIPO= "+TXProcesoDAO.PROCESS_TYPE+" AND ID_PCD="+idPcd);
		
		}
		catch (ISPACException ie)
		{
			logger.error("Error en ProcedureAPI:countExpedientesByPcd("+ idPcd +")", ie);
			throw new ISPACException("Error en ProcedureAPI:countExpedientesByPcd("+ idPcd +")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

}