package ieci.tdw.ispac.ispaclib.catalog.procedure.element;

import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.graph.GInfo;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.bpm.Process;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ExportProcedureMgr;
import ieci.tdw.ispac.ispaclib.catalog.procedure.FlowNodeMgr;
import ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ImportProcedureMgr;
import ieci.tdw.ispac.ispaclib.catalog.procedure.PcdElementBuilder;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ProcedureUtil;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTHelpDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTProcedureDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTaskDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.xml.XmlPcdTemplateDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PEventoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PProcedimientoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PRelPlazoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PTramiteDAO;
import ieci.tdw.ispac.ispaclib.dao.publisher.PubReglaDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.MapUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

/**
 * ProcedureElement
 */
final public class ProcedureElement implements IPcdElement
{
    static public final int PCD_CT_PARENT_ROOT = -1;

    private PProcedimientoDAO procdao;
    private CTProcedureDAO ctprocdao;

    private List entitylist;
    private List eventlist;
    private List pubrulelist;

    //-------------------------------------
    FlowNodeMgr flownodemgr;

    private List deadlinelist;

    private List permissionlist;

    private List otherpermissionlist;

    private List helplist;


    private final ClientContext ctx;

	private ProcedureElement(ClientContext ctx,PProcedimientoDAO procdao,
	        CTProcedureDAO ctprocdao,
	        FlowNodeMgr flownodemgr,
	        List entitylist,
	        List eventlist,
			List deadlinelist,
			List permissionlist ,
			List otherpermissionList,
			List pubrulelist)
	{
		this(ctx,procdao,ctprocdao,flownodemgr,entitylist,eventlist,deadlinelist,permissionlist,null, otherpermissionList, pubrulelist);
	}

	private ProcedureElement(ClientContext ctx,PProcedimientoDAO procdao,
	        CTProcedureDAO ctprocdao,
	        FlowNodeMgr flownodemgr,
	        List entitylist,
	        List eventlist,
			List deadlinelist,
			List permissionlist,
			List helplist,
			List otherpermissionList,
			List pubrulelist)
	{
	    this.ctx=ctx;

	    this.flownodemgr=flownodemgr;

	    this.procdao=procdao;
	    this.ctprocdao=ctprocdao;
	    this.entitylist=entitylist;
	    this.eventlist=eventlist;
	    this.deadlinelist=deadlinelist;
	    this.permissionlist=permissionlist;
	    this.helplist=helplist;
	    this.otherpermissionlist=otherpermissionList;
	    this.pubrulelist = pubrulelist;

	}

	public ProcedureElement(ClientContext ctx,DbCnt cnt,int procedureId)
	throws ISPACException
	{
	    this.ctx=ctx;

	    this.flownodemgr=new FlowNodeMgr();

	    this.procdao=new PProcedimientoDAO(cnt,procedureId);

	    if (procdao.getInt("TIPO") == IPcdElement.TYPE_PROCEDURE) {
	    	this.ctprocdao=new CTProcedureDAO(cnt,procedureId);
	    }

	    this.entitylist=null;

	    this.eventlist = null;
	    this.deadlinelist = null;
	    this.permissionlist = null;
	    this.pubrulelist = null;
	}

	public ProcedureElement(ClientContext ctx,PProcedimientoDAO procdao,
	        CTProcedureDAO ctprocdao)
	{
	    this.ctx=ctx;

	    flownodemgr=new FlowNodeMgr();

	    this.procdao=procdao;
	    this.ctprocdao=ctprocdao;

	    this.entitylist=null;
	    this.eventlist=null;
	    this.pubrulelist = null;

	    this.deadlinelist=null;
	    this.permissionlist=null;
	}

	public ProcedureElement(ClientContext ctx,PProcedimientoDAO procdao,
	        CTProcedureDAO ctprocdao,List entitylist)
	{
	    this.ctx=ctx;

	    flownodemgr=new FlowNodeMgr();

	    this.procdao=procdao;
	    this.ctprocdao=ctprocdao;

	    this.entitylist=entitylist;
	    this.eventlist=null;
	    this.pubrulelist = null;

	    this.deadlinelist=null;
	    this.permissionlist=null;
	}

	public void load(DbCnt cnt) throws ISPACException {

	    int procedureId=procdao.getKeyInt();
	    entitylist=procdao.getEntities(cnt).toList();
	    eventlist=PEventoDAO.getEvents(cnt,EventsDefines.EVENT_OBJ_PROCEDURE,procedureId).toList();
	    //tendriamos que obtener todos los permisos spac_ss_permisos y spac_permisos
	    permissionlist=procdao.getPermissions(cnt).toList();
	    otherpermissionlist = procdao.getOtherPermissions(cnt).toList();
	    flownodemgr.load(cnt,procdao);

	    PcdElementBuilder objdeffactory=new PcdElementBuilder();

	    CollectionDAO coldao=PRelPlazoDAO.getRelDeadline(cnt,PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE,getId());
	    deadlinelist=objdeffactory.buildDeadlineElement(cnt,coldao);
	    helplist=CTHelpDAO.getProcedureHelps(cnt, procedureId).toList();

	    pubrulelist = PubReglaDAO.getRulesAsignedOnlyProcedure(cnt, getId()).toList();
	}

	public void modify(DbCnt cnt, String newName, List ctstages,
			Map cttaskidmap) throws ISPACException {

	    flownodemgr.modify(cnt,procdao.getKeyInteger(),ctstages,cttaskidmap);

	    procdao.set("ID_UPD",ctx.getRespId());
	    procdao.set("NOMBRE_UPD",ctx.getUser().getName());
	    procdao.set("TS_UPD",new Date());
	}

	public void modify(DbCnt cnt, String newName, List activitiesNames)
			throws ISPACException {

	    flownodemgr.modify(cnt, procdao.getKeyInteger(), activitiesNames);

	    procdao.set("ID_UPD",ctx.getRespId());
	    procdao.set("NOMBRE_UPD",ctx.getUser().getName());
	    procdao.set("TS_UPD",new Date());
	}

	public void setStages(DbCnt cnt, Map stages) throws ISPACException {
	    flownodemgr.setStages(cnt,stages,procdao.getKeyInteger());
	}

    public StageElement addStage(DbCnt cnt, int pcdId, int ctstageId,
    		String ctstageName, GInfo ginfo) throws ISPACException {
        return flownodemgr.addStage(cnt, pcdId, ctstageId, ctstageName, ginfo);
    }

    public StageElement getStage(int stageId) throws ISPACException {
        return flownodemgr.getStage(stageId);
    }

    public Collection getStages() throws ISPACException {
        return flownodemgr.getStages().values();
    }

    public void removeStage(DbCnt cnt, int procedureId, int pstageId)
			throws ISPACException {
    	flownodemgr.removeStage(cnt, procedureId, pstageId);
	}

    public SyncNodeElement addSyncNode(DbCnt cnt, int pcdId, int type, GInfo ginfo)
    		throws ISPACException {
		return flownodemgr.addSyncNode(cnt, pcdId, type, ginfo);
	}

    public SyncNodeElement getSyncNode(int sincNodeId) throws ISPACException {
        return flownodemgr.getSyncNode(sincNodeId);
    }

    public Collection getSyncNodes() throws ISPACException {
        return flownodemgr.getSyncNodes().values();
    }

	public void removeSyncNode(DbCnt cnt, int procedureId, int psincNodeId)
			throws ISPACException {
		flownodemgr.removeSyncNode(cnt, procedureId, psincNodeId);
	}

    public FlowElement createFlow(DbCnt cnt, int pcdId, int origId, int destId)
    		throws ISPACException {
		return flownodemgr.createFlow(cnt, pcdId, origId, destId);
	}

    public FlowElement getFlow(int flowId) throws ISPACException {
		return flownodemgr.getFlow(flowId);
	}

    public Collection getFlows() throws ISPACException {
		return flownodemgr.getFlows().values();
	}

    public void removeFlow(DbCnt cnt, int pflowId)
			throws ISPACException {
		flownodemgr.removeStageFlow(cnt, pflowId);
	}

	public void setParent(ProcedureElement pcd) throws ISPACException {
		if (ctprocdao != null) {
			ctprocdao.set("ID_PADRE",pcd.getId());
		}
	}

	public void setParent(int parentid) throws ISPACException {
		if (ctprocdao != null) {
			ctprocdao.set("ID_PADRE",parentid);
		}
	}

	public void setTitle(String title) throws ISPACException {
		if (ctprocdao != null) {
			ctprocdao.set("TITULO", title);
		}
	}

	public void setSubject(String subject) throws ISPACException {
		if (ctprocdao != null) {
			ctprocdao.set("ASUNTO", subject);
		}
	}

	public void setStateDraft(DbCnt cnt,ProcedureElement pcd)
			throws ISPACException {
	    setStateDraft(cnt,pcd.getGroupId());
	}

	public void setStateDraft(DbCnt cnt,int groupId) throws ISPACException {

	    PProcedimientoDAO procgroupdao=PProcedimientoDAO.getProcedureMaxVersion(cnt,groupId);
	    if (procgroupdao==null)
	        return;

	    int version=procgroupdao.getInt("NVERSION")+1;
	    procdao.set("NVERSION",version);
	    procdao.set("ID_GROUP",groupId);
	    procdao.set("ESTADO",IProcedure.PCD_STATE_DRAFT);

	}

	public void checkStateCurrent(DbCnt cnt) throws ISPACException {

		if (getType() == IPcdElement.TYPE_PROCEDURE) {

			// Comprobar si tiene al menos una fase
			if (MapUtils.isEmpty(flownodemgr.getStages())) {
				throw new ISPACInfo("exception.procedures.promoteDraft.noStages");
			}

			// Comprobar que haya una fase inicial
			if (!flownodemgr.hasInitialStage()) {
				throw new ISPACInfo("exception.procedures.promoteDraft.noInitialStage");
			}

			// Comprobar que no haya nodos aislados
			if (flownodemgr.hasIsolatedNodes()) {
				throw new ISPACInfo("exception.procedures.promoteDraft.isolatedNodes");
			}

			// El código de procedimiento es obligatorio y tiene que ser único
			String codPcd = ctprocdao.getString("COD_PCD");
			if (StringUtils.isBlank(codPcd)) {
				throw new ISPACInfo("exception.procedures.counterType.pcdCode.required");
			}
			else {
				// Bloquear el catálogo de procedimientos
				ObjectDAO.getForUpdate(cnt, CTProcedureDAO.class, null);
				if (!CTProcedureDAO.isUniqueCode(cnt, codPcd, procdao.getInt("ID_GROUP"))) {
					throw new ISPACInfo("exception.procedures.counterType.pcdCode.repeated");
				}
			}
		}
		else {

			// Comprobar si tiene al menos una actividad
			if (MapUtils.isEmpty(flownodemgr.getStages())) {
				throw new ISPACInfo("exception.subprocedures.promoteDraft.noActivities");
			}

			// Comprobar que haya una actividad inicial
			if (!flownodemgr.hasInitialStage()) {
				throw new ISPACInfo("exception.subprocedures.promoteDraft.noInitialActivity");
			}

			// Comprobar que no haya nodos aislados
			if (flownodemgr.hasIsolatedNodes()) {
				throw new ISPACInfo("exception.subprocedures.promoteDraft.isolatedNodes");
			}
		}
	}

	public void setStateCurrent(DbCnt cnt) throws ISPACException {

		// Cambiar el estado del procediento vigente a HISTÓRICO
	    PProcedimientoDAO currentPcd = PProcedimientoDAO
	    	.getProcedureCurrentVersion(cnt, getGroupId());
	    if (currentPcd != null) {

	        currentPcd.set("ESTADO", IProcedure.PCD_STATE_OLD);
	        currentPcd.set("ID_UPD", ctx.getRespId());
	        currentPcd.set("NOMBRE_UPD", ctx.getUser().getName());
	        currentPcd.set("TS_UPD", new Date());
	        currentPcd.store(cnt);

	        if (getType() == IPcdElement.TYPE_SUBPROCEDURE) {

	        	// Actualizar el subproceso en los trámites del catálogo.
	        	CTTaskDAO.updateSubProcedureId(cnt, currentPcd.getKeyInt(), getId());

	        	// Actualizar el subproceso en los trámites del catálogo.
	        	PTramiteDAO.updateSubProcedureId(cnt, currentPcd.getKeyInt(), getId());
	        }

	    }

	    procdao.set("ESTADO",IProcedure.PCD_STATE_CURRENT);
	    procdao.set("ID_UPD",ctx.getRespId());
	    procdao.set("NOMBRE_UPD",ctx.getUser().getName());
	    procdao.set("TS_UPD",new Date());
	}

	public ProcedureElement duplicate(DbCnt cnt, String newName, List ctstages, Map ctstagestask)
			throws ISPACException {

	    PProcedimientoDAO newpprocdao=new PProcedimientoDAO(cnt);
	    newpprocdao.createNew(cnt);

	    CTProcedureDAO newctprocdao = null;

	    if (ctprocdao != null) {
		    newctprocdao = (CTProcedureDAO) ctprocdao.duplicate(cnt);
		    newctprocdao.setKey(newpprocdao.getKeyInt());
		    newctprocdao.set("COD_PCD","PCD-"+newpprocdao.getKeyInt());

		    if (newName==null) {
		        newName="Copia de "+procdao.getString("NOMBRE");
		    }

		    newctprocdao.set("NOMBRE",newName);
	    }

	    newpprocdao.set("NVERSION",1);
	    newpprocdao.set("NOMBRE",newName);
	    newpprocdao.set("TIPO",procdao.getInt("TIPO"));
	    newpprocdao.set("ESTADO",IProcedure.PCD_STATE_DRAFT);
	    newpprocdao.set("ID_RESP",procdao.getString("ID_RESP"));
	    newpprocdao.set("ID_RESP_SEC",procdao.getString("ID_RESP_SEC"));

	    newpprocdao.set("ID_GROUP",newpprocdao.getKeyInt());

	    newpprocdao.set("ID_CRT",ctx.getRespId());
	    newpprocdao.set("NOMBRE_CRT",ctx.getUser().getName());
	    newpprocdao.set("TS_CRT",new Date());

	    newpprocdao.set("ID_UPD",ctx.getRespId());
	    newpprocdao.set("NOMBRE_UPD",ctx.getUser().getName());
	    newpprocdao.set("TS_UPD",new Date());


	    FlowNodeMgr newflownodemgr = flownodemgr.duplicate(cnt, newpprocdao.getKeyInteger(), ctstages, ctstagestask);

	    // Entidades
        List newentitylist = ProcedureUtil.duplicate(cnt, entitylist);
        ProcedureUtil.setProperty(newentitylist, "ID_PCD", newpprocdao.getKeyInteger());

        // Eventos de las entidades en el procedimiento
        ProcedureUtil.duplicatePcdEntityEvents(cnt, entitylist, newentitylist);

        // Eventos
        List neweventlist = ProcedureUtil.duplicate(cnt, eventlist);
        ProcedureUtil.setProperty(neweventlist, "ID_OBJ", newpprocdao.getKeyInteger());

        // Permisos
        List newpermissionlist = ProcedureUtil.duplicate(cnt, permissionlist);
        ProcedureUtil.setProperty(newpermissionlist, "ID_PCD", newpprocdao.getKeyInteger());

        // Otros permisos
        List newotherpermissionlist = ProcedureUtil.duplicateOtherPermission(otherpermissionlist);
        ProcedureUtil.setProperty(newotherpermissionlist, "ID_OBJ", newpprocdao.getKeyInteger());

	    // Plazos
	    List newdeadlinelist = ProcedureUtil.duplicatePcdElement(cnt, deadlinelist);
        ProcedureUtil.setPcdElementProperty(newdeadlinelist, newpprocdao.getKeyInteger(), null, null);

        // Reglas de publicación
        List newpubrulelist = ProcedureUtil.duplicate(cnt, pubrulelist);
        ProcedureUtil.setProperty(newpubrulelist, "ID_PCD", newpprocdao.getKey());

        return new ProcedureElement(
        		ctx, newpprocdao,
				newctprocdao,
                newflownodemgr,
    	        newentitylist,
    	        neweventlist,
				newdeadlinelist,
				newpermissionlist,
				otherpermissionlist,
				newpubrulelist);
	}

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#duplicate(ieci.tdw.ispac.ispaclib.db.DbCnt)
     */
    public IPcdElement duplicate(DbCnt cnt) throws ISPACException
    {
        return duplicate(cnt, null, null, null);
    }


    public Map getCTStageTaskIds()
    throws ISPACException
    {
        return flownodemgr.getCTStageTaskIds();
    }


    /**
     * @param cnt
     * @param pstageId
     * @param cttaskId
     */
    public TaskElement addTask(DbCnt cnt, int pstageId, int cttaskId)
    		throws ISPACException {
        return flownodemgr.addTask(cnt, pstageId, cttaskId);
    }

    public TaskElement getTask(int pstageId, int taskId) throws ISPACException {
        return flownodemgr.getTask(pstageId, taskId);
    }

    /**
     * @param cnt
     * @param pstageId
     * @param ptaskId
     */
    public void removeTask(DbCnt cnt, int pstageId, int ptaskId)
    throws ISPACException
    {
        flownodemgr.removeTask(cnt,pstageId,ptaskId);
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#getId()
     */
    public int getId() throws ISPACException
    {
        if (procdao==null)
            throw new ISPACException("ProcedureElement - La estructura del procedimiento no está debidamente construida");
        return procdao.getKeyInt();
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#getIntegerId()
     */
    public Integer getIntegerId() throws ISPACException
    {
        if (procdao==null)
            throw new ISPACException("ProcedureElement - La estructura del procedimiento no está debidamente construida");

        return procdao.getKeyInteger();
    }

    public String getName() throws ISPACException {
        if (procdao==null) {
            throw new ISPACException("ProcedureElement - La estructura del procedimiento no está debidamente construida");
        }
        return procdao.getString("NOMBRE");
    }

    public int getType() throws ISPACException {
        if (procdao==null) {
            throw new ISPACException("ProcedureElement - La estructura del procedimiento no está debidamente construida");
        }
        return procdao.getInt("TIPO");
    }

    public String getResponsibleId() throws ISPACException {
        if (procdao==null) {
            throw new ISPACException("ProcedureElement - La estructura del procedimiento no está debidamente construida");
        }
        return procdao.getString("ID_RESP");
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getCatalogId()
     */
    public int getCatalogId() throws ISPACException
    {
        return getId();
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getCatalogIntegerId()
     */
    public Integer getCatalogIntegerId() throws ISPACException
    {
        return getIntegerId();
    }


    public int getGroupId() throws ISPACException
    {
        if (procdao==null)
            throw new ISPACException("ProcedureElement - La estructura del procedimiento no está debidamente construida");
        return procdao.getInt("ID_GROUP");
    }

    public int getVersion() throws ISPACException {
        if (procdao==null)
            throw new ISPACException("ProcedureElement - La estructura del procedimiento no está debidamente construida");
        return procdao.getInt("NVERSION");
    }


    public int getParentId() throws ISPACException {
        if (ctprocdao==null)
            throw new ISPACException("ProcedureElement - La estructura del procedimiento no está debidamente construida");
        return ctprocdao.getInt("ID_PADRE");
    }

    public String getCode() throws ISPACException {
        if (ctprocdao==null)
            throw new ISPACException("ProcedureElement - La estructura del procedimiento no está debidamente construida");
        return ctprocdao.getString("COD_PCD");
    }

	public void setCode(String code) throws ISPACException {
        if (ctprocdao==null)
            throw new ISPACException("ProcedureElement - La estructura del procedimiento no está debidamente construida");
		ctprocdao.set("COD_PCD", code);
	}

	public String getBPMPcdId() throws ISPACException {
        if (procdao==null)
            throw new ISPACException("ProcedureElement - La estructura del procedimiento no está debidamente construida");
        return procdao.getString("ID_PCD_BPM");
	}

	public void setBPMPcdId(String bpmPcdId) throws ISPACException {
        if (procdao==null) {
            throw new ISPACException("ProcedureElement - La estructura del procedimiento no está debidamente construida");
        }
        procdao.set("ID_PCD_BPM", bpmPcdId);
	}

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#store(ieci.tdw.ispac.ispaclib.db.DbCnt)
     */
    public void store(DbCnt cnt) throws ISPACException
    {
        procdao.store(cnt);

        if (ctprocdao != null) {
        	ctprocdao.store(cnt);
        }

        flownodemgr.store(cnt);

        ProcedureUtil.storePcdElement(cnt,deadlinelist);
        ProcedureUtil.store(cnt,entitylist);
        ProcedureUtil.store(cnt,eventlist);
        ProcedureUtil.store(cnt,permissionlist);
        ProcedureUtil.store(cnt, otherpermissionlist);
        ProcedureUtil.store(cnt, pubrulelist);
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#delete(ieci.tdw.ispac.ispaclib.db.DbCnt)
     */
    public void delete(DbCnt cnt) throws ISPACException {

    	if (getType() == IPcdElement.TYPE_PROCEDURE) {

	        //Sólo se permite borrar un procedimiento si se trata de un borrador y no tiene
	        //procesos instanciados. A partir de la version 6.4 si se permiten borrar vigentes
//	        if ((procdao.getInt("ESTADO")!=IProcedure.PCD_STATE_DRAFT)
//	        		|| (procdao.countInstancedProcess(cnt)!=0)) {
//	            throw new ISPACInfo("exception.procedures.delete.noDraft");
//	        }

	        //No se permite borrar un procedimiento que siendo la versión 1 y estando en borrador
	        //tenga al menos una familia hija
	        if ((procdao.getInt("NVERSION") == 1)
	        		//&& (procdao.getInt("ESTADO") == IProcedure.PCD_STATE_DRAFT) Ahora ya se pueden eliminar vigentes
	        		&& (CTProcedureDAO.countChildrenProcedures(cnt, procdao.getKeyInt()) > 0)) {
	            throw new ISPACInfo("exception.procedures.delete.hasChildren");
	        }

    	} else if (getType() == IPcdElement.TYPE_SUBPROCEDURE) {

    		if ( (procdao.getInt("ESTADO") != IProcedure.PCD_STATE_DRAFT)
    				&& ((procdao.countInstancedProcess(cnt) != 0)
	    				|| (procdao.countAsociatedCTTasks(cnt) != 0)
	    				|| (procdao.countAsociatedPTasks(cnt) != 0)
	    				|| (procdao.countAsociatedTasks(cnt) != 0)) ) {

    			throw new ISPACInfo("exception.subprocedures.delete.inUse");
    		}
    	}

        ProcedureUtil.deletePcdElement(cnt,deadlinelist);
        ProcedureUtil.delete(cnt,entitylist);
        ProcedureUtil.delete(cnt,eventlist);
        ProcedureUtil.delete(cnt,permissionlist);
        ProcedureUtil.delete(cnt, pubrulelist);
        

        flownodemgr.delete(cnt);
        procdao.delete(cnt);

        if (ctprocdao != null) {
        	ctprocdao.delete(cnt);
        }

        this.entitylist=null;
	    this.eventlist=null;
	    this.deadlinelist=null;
	    this.permissionlist=null;
	    this.pubrulelist = null;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#setProcedureId(ieci.tdw.ispac.ispaclib.db.DbCnt, int)
     */
    public void setProcedureId(int procedureId) throws ISPACException
    {

    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#setStagePcdId(ieci.tdw.ispac.ispaclib.db.DbCnt, int)
     */
    public void setStagePcdId(int stagePcdId) throws ISPACException
    {

    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#setTaskPcdId(ieci.tdw.ispac.ispaclib.db.DbCnt, int)
     */
    public void setTaskPcdId(int taskPcdId) throws ISPACException
    {

    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getOrigId()
     */
    public Integer getOrigId() throws ISPACException
    {
        return null;
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getDestId()
     */
    public Integer getDestId() throws ISPACException
    {
        return null;
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#setOrigId(java.lang.Integer)
     */
    public void setOrigId(Integer elementId) throws ISPACException
    {

    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#setDestId(java.lang.Integer)
     */
    public void setDestId(Integer elementId) throws ISPACException
    {

    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getOrigElement()
     */
    public List getOrigElement() throws ISPACException
    {
        return null;
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getDestElement()
     */
    public List getDestElement() throws ISPACException
    {
        return null;
    }

    public void addOrigElement(IPcdElement element) throws ISPACException {}

    public void addDestElement(IPcdElement element) throws ISPACException {}

    /**
     * Compone el modelo de procesos para el BPM.
     * @return Modelo de procesos.
     * @throws ISPACException si ocurre algún error.
     */
    public Process getProcessModel() throws ISPACException {

    	Process model = new Process();

    	model.setId(getBPMPcdId());
    	model.setSpacProcessId(getId());
    	model.setName(getName());
    	model.setRespId(getResponsibleId());

    	return model;
    }

    public String toXml() throws ISPACException {

        String sXml = null;
        StringBuffer buffer = new StringBuffer();

        buffer.append(XmlTag.newTag("name",procdao.getString("NOMBRE")));
        buffer.append(XmlTag.newTag("idgroup",procdao.getString("ID_GROUP")));
        buffer.append(XmlTag.newTag("state",procdao.getString("ESTADO")));
        buffer.append(XmlTag.newTag("idresp",procdao.getString("ID_RESP")));
        buffer.append(XmlTag.newTag("idcreator",procdao.getString("ID_CRT")));
        buffer.append(XmlTag.newTag("ncreator",procdao.getString("NOMBRE_CRT")));
        buffer.append(XmlTag.newTag("tscreator",procdao.getString("TS_CRT")));
        buffer.append(XmlTag.newTag("idupdate",procdao.getString("ID_UPD")));
        buffer.append(XmlTag.newTag("nupdate",procdao.getString("NOMBRE_UPD")));
        buffer.append(XmlTag.newTag("tsupdate",procdao.getString("TS_UPD")));
        buffer.append(XmlTag.newTag("tipo",procdao.getString("TIPO")));

        if (ctprocdao != null) {
        	buffer.append(XmlTag.newTag("codpcd",ctprocdao.getString("COD_PCD")));
            buffer.append(XmlTag.newTag("idparent",ctprocdao.getString("ID_PADRE")));
        }

        buffer.append(flownodemgr.toXml());

        sXml=XmlTag.getXmlInstruction("UTF-8")+
                XmlTag.newTag("procedure",buffer.toString(),
                XmlTag.newAttr("id",Integer.toString(getId())));
        return sXml;
    }
	public String toXpdl(DbCnt cnt, Map ctStageIds, Map ctTaskIds,
			Map ctRuleIds, Map ctEntityIds, Map ctTpDocIds, Map subPcdIds)
			throws ISPACException {

		return toXpdl(cnt, ctStageIds, ctTaskIds, ctRuleIds, ctEntityIds, ctTpDocIds, subPcdIds,new HashMap());
	}

    public String toXpdl(DbCnt cnt,
    					 Map ctStageIds,
    					 Map ctTaskIds,
    					 Map ctRuleIds,
    					 Map ctEntityIds,
    					 Map ctTpDocIds,
    					 Map subPcdIds,
    					 Map ctHelpsIds) throws ISPACException {

    	String sXpdl = null;
    	StringBuffer buffer =  new StringBuffer();

    	String attributes = XmlTag.newAttr(ExportProcedureMgr.ATR_ID, String.valueOf(getId())) +
		   					XmlTag.newAttr(ExportProcedureMgr.ATR_NAME, ExportProcedureMgr.exportName(getName()));

    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_VERSION, getVersion()));

    	// Ficha del procedimiento
    	if(getType() == TYPE_PROCEDURE) {

    		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_CODIGO, getCode(), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_TITULO, ctprocdao.getString("TITULO"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_OBJETO, ctprocdao.getString("OBJETO"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_ASUNTO, ctprocdao.getString("ASUNTO"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_ACTIVIDAD_FUNCION, ctprocdao.getString("ACT_FUNC"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_MATERIAS_COMPETENCIA, ctprocdao.getString("MTRS_COMP"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_SITUACION_TELEMATICA, ctprocdao.getString("SIT_TLM"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_URL, ctprocdao.getString("URL"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_INTERESADOS, ctprocdao.getString("INTERESADO"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_TIPO_RELACION, ctprocdao.getString("TP_REL"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_FORMA_INICIACION, ctprocdao.getString("FORMA_INIC"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_PLAZO_RESOLUCION, ctprocdao.getString("PLZ_RESOL")));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_UNIDAD_PLAZO, ctprocdao.getString("UNID_PLZ"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_EFECTO_SILENCIO, ctprocdao.getString("EFEC_SILEN"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_FIN_VIA_ADMINISTRATIVA, ctprocdao.getString("FIN_VIA_ADMIN"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_RECURSOS, ctprocdao.getString("RECURSOS"), true));
	    	Date fcatalog = ctprocdao.getDate("FCATALOG");
	    	if (fcatalog != null) {
	    		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_FECHA_CATALOGACION, DateUtil.format(fcatalog, "dd/MM/yyyy")));
	    	}
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_AUTOR, ctprocdao.getString("AUTOR"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_OBSERVACIONES, ctprocdao.getString("OBSERVACIONES"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_LUGAR_PRESENTACION, ctprocdao.getString("LUGAR_PRESENT"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_CONDICIONES_ECONOMICAS, ctprocdao.getString("CNDS_ECNMCS"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_INGRESOS, ctprocdao.getString("INGRESOS"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_FORMAS_PAGOS_COBROS, ctprocdao.getString("F_CBR_PGO"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_INFRACCIONES_SANCIONES, ctprocdao.getString("INFR_SANC"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_CALENDARIO, ctprocdao.getString("CALENDARIO"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_DESCRIPCION_TRAMITES, ctprocdao.getString("DSCR_TRAM"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_NORMATIVA, ctprocdao.getString("NORMATIVA"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_CONDICIONES_PARTICIPACION, ctprocdao.getString("CND_PARTICIP"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_DOCUMENTACION_APORTAR, ctprocdao.getString("DOCUMENTACION"), true));
	    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_CONFIGURACION_RT, ExportProcedureMgr.replaceEndCDATA(ctprocdao.getString("MAPEO_RT")), true));
    	}

    	// Flujo con actividades y transiciones (XPDL)
    	buffer.append(flownodemgr.toXpdl(cnt, ctStageIds, ctTaskIds, ctRuleIds, ctEntityIds, ctTpDocIds, subPcdIds));

        // Eventos
        buffer.append(ExportProcedureMgr.eventsToXml(eventlist, ctRuleIds));

        // Entidades
        StringBuffer entities = new StringBuffer();
    	if ((entitylist != null) &&
    		(!entitylist.isEmpty())) {

    		Iterator it = entitylist.iterator();
    		while (it.hasNext()) {

    			IItem entity = (IItem) it.next();

    			String idEntidad = entity.getString("ID_ENT");
    			Integer id = Integer.valueOf(idEntidad);

    			StringBuffer attrTagForm = new StringBuffer();
    			StringBuffer valueTagEntity = new StringBuffer();

    			// Formulario en sólo lectura
    			String sFrmReadonly = entity.getString("FRM_READONLY");
    			if ((StringUtils.isNotBlank(sFrmReadonly)) &&
    				(ISPACEntities.ENTITY_FORM_READONLY == Integer.parseInt(sFrmReadonly))) {

    				attrTagForm.append(XmlTag.newAttr(ExportProcedureMgr.ATR_READONLY, ExportProcedureMgr.VAL_YES));
    			}

    			// Regla que asigna el formulario
    			String sRuleFormId = entity.getString("ID_RULE_FRM");
    			if (StringUtils.isNotBlank(sRuleFormId)) {

    				attrTagForm.append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID_REGLA_FORMULARIO, sRuleFormId));

			    	Integer ruleId = Integer.valueOf(sRuleFormId);
			    	if (!ctRuleIds.containsKey(ruleId)) {
	    				ctRuleIds.put(ruleId, null);
	    			}
    			}

    			// Regla que establece la visibilidad de la entidad
    			String sRuleVisibleId = entity.getString("ID_RULE_VISIBLE");
    			if (StringUtils.isNotBlank(sRuleVisibleId)) {

    				attrTagForm.append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID_REGLA_VISIBLE, sRuleVisibleId));

			    	Integer ruleId = Integer.valueOf(sRuleVisibleId);
			    	if (!ctRuleIds.containsKey(ruleId)) {
	    				ctRuleIds.put(ruleId, null);
	    			}
    			}

    			valueTagEntity.append(XmlTag.newTag(ExportProcedureMgr.TAG_TP_REL, entity.get("TP_REL")))
    						  .append(XmlTag.newTag(ExportProcedureMgr.TAG_FORM, entity.getString("FRM_EDIT"), attrTagForm.toString()));

    			// Eventos de la entidad en el procedimiento
    			List entityEventList = PEventoDAO.getEvents(cnt, EventsDefines.EVENT_OBJ_ENTITY, entity.getKeyInt()).toList();

    			valueTagEntity.append(ExportProcedureMgr.eventsToXml(entityEventList, ctRuleIds));

    			entities.append(XmlTag.newTag(ExportProcedureMgr.TAG_ENTITY,
    										  valueTagEntity.toString(),
    										  XmlTag.newAttr(ExportProcedureMgr.ATR_ID, idEntidad)));

    			if (!ctEntityIds.containsKey(id)) {
    				ctEntityIds.put(id, null);
    			}
    		}
    	}

    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_ENTITIES, entities.toString()));

    	// Plantillas específicas
    	StringBuffer templates = new StringBuffer();
    	IItemCollection collection = XmlPcdTemplateDAO.getTemplates(cnt, getId()).disconnect();
    	while (collection.next()) {

    		XmlPcdTemplateDAO xmlPcdTemplateDAO = (XmlPcdTemplateDAO) collection.value();
    		templates.append(xmlPcdTemplateDAO.export());
    	}
    	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_TEMPLATES, templates.toString()));

    	 //Ayuda
        buffer.append(ExportProcedureMgr.ctHelpsToXml(helplist, ctHelpsIds));

    	sXpdl = XmlTag.newTag(ExportProcedureMgr.TAG_WORKFLOW_PROCESS, buffer.toString(), attributes);
    	return sXpdl;
    }

    /**
     *
     * @param node
     * @param importCodPcd
     * @throws ISPACException
     */
    public void importProcedure(Node node, boolean importPcdCode) throws ISPACException {

    	// Ficha del procedimiento
    	if(getType() == TYPE_PROCEDURE) {

    		if (importPcdCode) {
    			importCTProcedureProperty(node, ExportProcedureMgr.TAG_CODIGO, "COD_PCD");
    		}
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_TITULO, "TITULO");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_OBJETO, "OBJETO");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_ASUNTO, "ASUNTO");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_ACTIVIDAD_FUNCION, "ACT_FUNC");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_MATERIAS_COMPETENCIA, "MTRS_COMP");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_SITUACION_TELEMATICA, "SIT_TLM");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_URL, "URL");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_INTERESADOS, "INTERESADO");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_TIPO_RELACION, "TP_REL");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_FORMA_INICIACION, "FORMA_INIC");
    		String plzresol = XmlFacade.get(node, ExportProcedureMgr.TAG_PLAZO_RESOLUCION);
    		if (StringUtils.isNotBlank(plzresol)) {
    			ctprocdao.set("PLZ_RESOL", Integer.parseInt(plzresol));
    		}
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_UNIDAD_PLAZO, "UNID_PLZ");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_EFECTO_SILENCIO, "EFEC_SILEN");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_FIN_VIA_ADMINISTRATIVA, "FIN_VIA_ADMIN");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_RECURSOS, "RECURSOS");
    		String fcatalog = XmlFacade.get(node, ExportProcedureMgr.TAG_FECHA_CATALOGACION);
    		if (StringUtils.isNotBlank(fcatalog)) {
    			ctprocdao.set("FCATALOG", DateUtil.getDate(fcatalog));
    		}
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_AUTOR, "AUTOR");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_OBSERVACIONES, "OBSERVACIONES");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_LUGAR_PRESENTACION, "LUGAR_PRESENT");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_CONDICIONES_ECONOMICAS, "CNDS_ECNMCS");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_INGRESOS, "INGRESOS");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_FORMAS_PAGOS_COBROS, "F_CBR_PGO");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_INFRACCIONES_SANCIONES, "INFR_SANC");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_CALENDARIO, "CALENDARIO");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_DESCRIPCION_TRAMITES, "DSCR_TRAM");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_NORMATIVA, "NORMATIVA");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_CONDICIONES_PARTICIPACION, "CND_PARTICIP");
    		importCTProcedureProperty(node, ExportProcedureMgr.TAG_DOCUMENTACION_APORTAR, "DOCUMENTACION");
    		String mapeort = XmlFacade.get(node, ExportProcedureMgr.TAG_CONFIGURACION_RT);
    		if (StringUtils.isNotBlank(mapeort)) {
    			ctprocdao.set("MAPEO_RT", ImportProcedureMgr.replaceEndCDATA(mapeort));
    		}
    	}
    }

    private void importCTProcedureProperty(Node node,
    									   String tagName,
    									   String propertyName) throws ISPACException {

		String value = XmlFacade.get(node, tagName);
		if (StringUtils.isNotBlank(value)) {
			ctprocdao.set(propertyName, value);
		}
    }

	/**
	 * @return Returns the procdao.
	 */
	public PProcedimientoDAO getProcdao() {
		return procdao;
	}

}