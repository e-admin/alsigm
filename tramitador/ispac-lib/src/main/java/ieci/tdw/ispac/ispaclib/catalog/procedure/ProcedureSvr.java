package ieci.tdw.ispac.ispaclib.catalog.procedure;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.graph.GInfo;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.bpm.ProcessFlow;
import ieci.tdw.ispac.ispaclib.bpm.ProcessNode;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.FlowElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.ProcedureElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.StageElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.SyncNodeElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.TaskElement;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.TransactionContainer;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTApplicationDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTEntityDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTHelpDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTRuleDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTStageDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTStageTaskDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTaskDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTpDocDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.PcdTemplateDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.TemplateDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PDepTramiteDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PEntidadDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFaseTDocDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFrmFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFrmTramiteDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PProcedimientoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PSincNodoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PTramiteDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

/**
 * Gestiona la información de los procedimientos.
 *
 */
public class ProcedureSvr {

	/** Contexto de cliente. */
	private final ClientContext ctx;

	/**
	 * Constructor
	 * @param ctx Contexto de cliente.
	 */
	public ProcedureSvr(ClientContext ctx) {
	    this.ctx=ctx;
	}

	public int create(TransactionContainer txContainer, String name,
			List ctstages, Map ctstagestask) throws ISPACException {

		DbCnt cnt = txContainer.getConnection();

        PcdElementFactory pcdfactory = new PcdElementFactory();
        ProcedureElement procedure = pcdfactory.createProcedure(ctx, cnt,
        		ProcedureElement.PCD_CT_PARENT_ROOT, name,
        		IPcdElement.TYPE_PROCEDURE);

        if ( (ctstages != null) && (ctstages.size() > 0) ) {
	        Map pstagesmap=pcdfactory.createStages(cnt,ctstages);
	        Iterator it=pstagesmap.values().iterator();
	        while (it.hasNext()) {
	            StageElement stage = (StageElement) it.next();
	            Map taskmap=pcdfactory.createTasks(cnt,stage,ctstagestask);
	            stage.setTasks(taskmap);
	        }

	        procedure.setStages(cnt,pstagesmap);
        }

        procedure.store(cnt);

        // Crea el modelo de proceso en el BPM
        createBPMProcessModel(txContainer, procedure);

        return procedure.getId();
    }

	public int createSubProcedure(TransactionContainer txContainer, String name,
			List activityNames) throws ISPACException {

		DbCnt cnt = txContainer.getConnection();

        PcdElementFactory pcdfactory = new PcdElementFactory();
        ProcedureElement procedure = pcdfactory.createProcedure(ctx, cnt,
        		ProcedureElement.PCD_CT_PARENT_ROOT, name,
        		IPcdElement.TYPE_SUBPROCEDURE);

        if ( (activityNames != null) && (activityNames.size() > 0) ) {
	        Map pactivitiesmap = pcdfactory.createActivities(cnt, activityNames);
	        procedure.setStages(cnt, pactivitiesmap);
        }

        procedure.store(cnt);

        // Crea el modelo de proceso en el BPM
        createBPMProcessModel(txContainer, procedure);

        return procedure.getId();
    }

	public int clone(TransactionContainer txContainer, int procedureid,
			String newname, int typeparent) throws ISPACException {

		DbCnt cnt = txContainer.getConnection();

        PcdElementBuilder pcdbuilder=new PcdElementBuilder();
        ProcedureElement procedure=pcdbuilder.buildProcedureElement(ctx,cnt,procedureid);
        ProcedureElement newprocedure=procedure.duplicate(cnt, newname, null, null);

        newprocedure.setTitle(newprocedure.getName());
        newprocedure.setSubject(newprocedure.getName());

        setParent(newprocedure,procedure,typeparent);
        newprocedure.store(cnt);

        // Crea el modelo de proceso en el BPM
        createBPMProcessModel(txContainer, newprocedure);

        return newprocedure.getId();
	}

	public int clone(TransactionContainer txContainer, int procedureid,
			String newname, List ctstages, Map ctstagestask,
			int typeparent) throws ISPACException {

		DbCnt cnt = txContainer.getConnection();

	    if (ctstages==null || ctstages.size()==0) {
	        throw new ISPACException("ProcedureSvr.cloneProcedure() - El procedimiento debe tener al menos una fase.");
	    }

        PcdElementBuilder pcdbuilder=new PcdElementBuilder();
        ProcedureElement procedure=pcdbuilder.buildProcedureElement(ctx,cnt,procedureid);
        ProcedureElement newprocedure=procedure.duplicate(cnt, newname, ctstages, ctstagestask);

        newprocedure.setTitle(newprocedure.getName());
        newprocedure.setSubject(newprocedure.getName());

        newprocedure.modify(cnt,newname,ctstages,ctstagestask);
        setParent(newprocedure,procedure,typeparent);
        newprocedure.store(cnt);

        // Crea el modelo de proceso en el BPM
        createBPMProcessModel(txContainer, newprocedure);

	    return newprocedure.getId();
	}

	public int cloneSubProcedure(TransactionContainer txContainer,
			int subprocedureid, String newname, List activitiesNames,
			int typeparent) throws ISPACException {

		DbCnt cnt = txContainer.getConnection();

        PcdElementBuilder pcdbuilder = new PcdElementBuilder();
        ProcedureElement procedure = pcdbuilder.buildProcedureElement(ctx, cnt, subprocedureid);
        ProcedureElement newprocedure = procedure.duplicate(cnt, newname, activitiesNames, null);
        newprocedure.setTitle(newprocedure.getName());
        newprocedure.setSubject(newprocedure.getName());

        newprocedure.modify(cnt, newname, activitiesNames);
        setParent(newprocedure,procedure,typeparent);
        newprocedure.store(cnt);

        // Crea el modelo de proceso en el BPM
        createBPMProcessModel(txContainer, newprocedure);

	    return newprocedure.getId();
	}

	private void setParent(ProcedureElement newprocedure,
			ProcedureElement origpcd, int typeparent) throws ISPACException {

        switch(typeparent) {
	        case IProcedureAPI.CLONEPCD_PARENT_ROOT:
	            newprocedure.setParent(ProcedureElement.PCD_CT_PARENT_ROOT);
	        	break;
	        case IProcedureAPI.CLONEPCD_PARENT_CHILDREN:
	            newprocedure.setParent(origpcd);
	    		break;
	        case IProcedureAPI.CLONEPCD_PARENT_SIBLING:
	            newprocedure.setParent(origpcd.getParentId());
	    		break;
	        default:
	            newprocedure.setParent(ProcedureElement.PCD_CT_PARENT_ROOT);
	    		break;
        }
	}

    public int modify(TransactionContainer txContainer, int procedureid,
    			String newname, List ctstages, Map ctstagestask)
    		throws ISPACException {

	    if (ctstages==null || ctstages.size()==0)
	        throw new ISPACException("ProcedureSvr.cloneProcedure() - El procedimiento debe tener al menos una fase.");

	    DbCnt cnt = txContainer.getConnection();

        PcdElementBuilder pcdbuilder=new PcdElementBuilder();
        ProcedureElement procedure=pcdbuilder.buildProcedureElement(ctx,cnt,procedureid);

        procedure.modify(cnt,newname,ctstages,ctstagestask);
        procedure.store(cnt);
	    return procedure.getId();
	}

    public int modifySubProcedure(TransactionContainer txContainer,
    			int subprocedureid, String newname, List activitiesNames)
    		throws ISPACException {

	    DbCnt cnt = txContainer.getConnection();

        PcdElementBuilder pcdbuilder = new PcdElementBuilder();
        ProcedureElement procedure = pcdbuilder.buildProcedureElement(ctx, cnt, subprocedureid);

        procedure.modify(cnt, newname, activitiesNames);
        procedure.store(cnt);

	    return procedure.getId();
	}

	public int createDraft(TransactionContainer txContainer, int procedureid, String newname)
			throws ISPACException {

		DbCnt cnt = txContainer.getConnection();

	    PcdElementBuilder pcdbuilder=new PcdElementBuilder();
	    ProcedureElement procedure=pcdbuilder.buildProcedureElement(ctx,cnt,procedureid);
	    ProcedureElement newprocedure=procedure.duplicate(cnt, newname, null, null);

	    newprocedure.setStateDraft(cnt,procedure);
	    newprocedure.setCode(procedure.getCode());
	    //newprocedure.setParent(procedure.getParentId());
	    newprocedure.store(cnt);

        // Crea el modelo de proceso en el BPM
        createBPMProcessModel(txContainer, newprocedure);

	    return newprocedure.getId();
	}

	public int createDraft(TransactionContainer txContainer, int procedureid,
				String newname, List ctstages, Map ctstagestask)
			throws ISPACException {

	    if (ctstages==null || ctstages.size()==0)
	        throw new ISPACException("ProcedureSvr.cloneProcedure() - El procedimiento debe tener al menos una fase.");

	    DbCnt cnt = txContainer.getConnection();

        PcdElementBuilder pcdbuilder=new PcdElementBuilder();
        ProcedureElement procedure=pcdbuilder.buildProcedureElement(ctx,cnt,procedureid);
        ProcedureElement newprocedure=procedure.duplicate(cnt, newname, ctstages, ctstagestask);

        newprocedure.modify(cnt,newname,ctstages,ctstagestask);
        newprocedure.setStateDraft(cnt,procedure.getGroupId());
        newprocedure.setCode(procedure.getCode());
        //newprocedure.setParent(procedure);

        newprocedure.store(cnt);

        // Crea el modelo de proceso en el BPM
        createBPMProcessModel(txContainer, newprocedure);

	    return newprocedure.getId();
	}

	public int createSubProcedureDraft(TransactionContainer txContainer,
				int subprocedureid, String newname, List activitiesNames)
			throws ISPACException {

	    DbCnt cnt = txContainer.getConnection();

        PcdElementBuilder pcdbuilder = new PcdElementBuilder();
        ProcedureElement procedure = pcdbuilder.buildProcedureElement(ctx, cnt, subprocedureid);
        ProcedureElement newprocedure=procedure.duplicate(cnt, newname, activitiesNames, null);

        newprocedure.modify(cnt, newname, activitiesNames);
        newprocedure.setStateDraft(cnt, procedure.getGroupId());

        newprocedure.store(cnt);

        // Crea el modelo de proceso en el BPM
        createBPMProcessModel(txContainer, newprocedure);

	    return newprocedure.getId();
	}

    public int addStage(TransactionContainer txContainer, int procedureId,
    		int ctstageId, String ctstageName, GInfo ginfo) throws ISPACException {

    	DbCnt cnt = txContainer.getConnection();

    	if (StringUtils.isBlank(ctstageName)) {
            throw new ISPACInfo("exception.procedures.stage.noName");
    	}

    	if (PFaseDAO.checkStageInstances(cnt, procedureId, ctstageName)) {
            throw new ISPACInfo("exception.procedures.stage.alreadyExists");
    	}

        PcdElementBuilder pcdbuilder = new PcdElementBuilder();
        ProcedureElement procedure = pcdbuilder.buildProcedureElement(ctx, cnt,
        		procedureId);

        StageElement stage = procedure.addStage(cnt, procedureId, ctstageId,
        		ctstageName, ginfo);

        procedure.store(cnt);

        // Crear el nodo de la fase en el BPM
		String bpmNodeId = txContainer.getBPMAPI().createNode(
				procedure.getBPMPcdId(),
				stage.getProcessModelNode());
		if (StringUtils.isNotBlank(bpmNodeId)) {
			stage.setBPMNodeId(bpmNodeId);
			stage.store(cnt);
		}

		return stage.getId();
    }

    public void removeStage(TransactionContainer txContainer, int procedureId, int pstageId)
			throws ISPACException {

    	DbCnt cnt = txContainer.getConnection();

    	if (PFaseDAO.checkStageInstances(cnt, pstageId)) {
            throw new ISPACInfo("exception.procedures.stage.inUse");
    	}

        PcdElementBuilder pcdbuilder = new PcdElementBuilder();
        ProcedureElement procedure = pcdbuilder.buildProcedureElement(ctx, cnt,
        		procedureId);

        StageElement stage = procedure.getStage(pstageId);

        procedure.removeStage(cnt, procedureId, pstageId);

        procedure.store(cnt);

        // Elimina el nodo de la fase en el BPM
        if (StringUtils.isNotBlank(stage.getBPMNodeId())) {
        	txContainer.getBPMAPI().deleteNode(procedure.getBPMPcdId(), stage.getBPMNodeId());
        }
    }

    public int addSyncNode(TransactionContainer txContainer, int procedureId,
    		int type, GInfo ginfo) throws ISPACException {

    	DbCnt cnt = txContainer.getConnection();

        PcdElementBuilder pcdbuilder = new PcdElementBuilder();
        ProcedureElement procedure = pcdbuilder.buildProcedureElement(ctx, cnt,
        		procedureId);

        SyncNodeElement syncNode = procedure.addSyncNode(cnt, procedureId, type, ginfo);

        procedure.store(cnt);

        // Crear el nodo de sincronización en el BPM
		String bpmNodeId = txContainer.getBPMAPI().createNode(
				procedure.getBPMPcdId(),
				syncNode.getProcessModelNode());
		if (StringUtils.isNotBlank(bpmNodeId)) {
			syncNode.setBPMNodeId(bpmNodeId);
			syncNode.store(cnt);
		}

		return syncNode.getId();
    }

    public void removeSyncNode(TransactionContainer txContainer, int procedureId, int psyncNodeId)
			throws ISPACException {

    	DbCnt cnt = txContainer.getConnection();

    	if (PSincNodoDAO.checkSyncNodeInstances(cnt, psyncNodeId)) {
            throw new ISPACInfo("exception.procedures.syncnode.inUse");
    	}

        PcdElementBuilder pcdbuilder = new PcdElementBuilder();
        ProcedureElement procedure = pcdbuilder.buildProcedureElement(ctx, cnt,
        		procedureId);

        SyncNodeElement syncNode = procedure.getSyncNode(psyncNodeId);

        procedure.removeSyncNode(cnt, procedureId, psyncNodeId);

        procedure.store(cnt);

        // Elimina el nodo de la fase en el BPM
        if (StringUtils.isNotBlank(syncNode.getBPMNodeId())) {
        	txContainer.getBPMAPI().deleteNode(procedure.getBPMPcdId(), syncNode.getBPMNodeId());
        }
    }

    public int createFlow(TransactionContainer txContainer, int procedureId,
    		int origId, int destId) throws ISPACException {

    	DbCnt cnt = txContainer.getConnection();

        PcdElementBuilder pcdbuilder = new PcdElementBuilder();
        ProcedureElement procedure = pcdbuilder.buildProcedureElement(ctx, cnt,
        		procedureId);

        FlowElement flow = procedure.createFlow(cnt, procedureId, origId, destId);

        procedure.store(cnt);

        // Crear el flujo en el BPM
		String bpmFlowId = txContainer.getBPMAPI().createFlow(
				procedure.getBPMPcdId(),
				flow.getProcessModelFlow());
		if (StringUtils.isNotBlank(bpmFlowId)) {
			flow.setBPMFlowId(bpmFlowId);
			flow.store(cnt);
		}

		return flow.getId();
    }

    public void removeFlow(TransactionContainer txContainer, int procedureId, int pflowId)
			throws ISPACException {

    	DbCnt cnt = txContainer.getConnection();

		PcdElementBuilder pcdbuilder = new PcdElementBuilder();
		ProcedureElement procedure = pcdbuilder.buildProcedureElement(ctx, cnt,
				procedureId);

		FlowElement flow = procedure.getFlow(pflowId);

		procedure.removeFlow(cnt, pflowId);

		procedure.store(cnt);

        // Elimina el flujo en el BPM
        if (StringUtils.isNotBlank(flow.getBPMFlowId())) {
        	txContainer.getBPMAPI().deleteFlow(procedure.getBPMPcdId(), flow.getBPMFlowId());
        }
    }

    public int addTask(TransactionContainer txContainer,int procedureId,
    		int pstageId, int cttaskId) throws ISPACException {

    	DbCnt cnt = txContainer.getConnection();

    	if (PTramiteDAO.checkTaskInstances(cnt, procedureId, pstageId, cttaskId)) {
            throw new ISPACInfo("exception.procedures.task.alreadyExists");
    	}

        PcdElementBuilder pcdbuilder=new PcdElementBuilder();
        ProcedureElement procedure=pcdbuilder.buildProcedureElement(ctx,cnt,procedureId);

        TaskElement task = procedure.addTask(cnt, pstageId, cttaskId);

        procedure.store(cnt);

        // Crear el trámite en el BPM
        IBPMAPI bpmAPI = txContainer.getBPMAPI();
		String bpmTaskId = bpmAPI.createTask(task.getProcessModel());
		if (StringUtils.isNotBlank(bpmTaskId)) {

			// Información de la fase
			StageElement stage = procedure.getStage(pstageId);

			// Asociar el trámite con la fase
			bpmAPI.addNodeSubProcess(stage.getBPMNodeId(), bpmTaskId);

			task.setBPMTaskId(bpmTaskId);
			task.store(cnt);
		}

		return task.getId();
	}

    public void removeTask(TransactionContainer txContainer,int procedureId,int pstageId,int ptaskId)
	throws ISPACException
	{
    	DbCnt cnt = txContainer.getConnection();

        if (PTramiteDAO.checkTaskInstances(cnt,ptaskId))
            throw new ISPACInfo("exception.procedures.task.inUse");

        PcdElementBuilder pcdbuilder=new PcdElementBuilder();
        ProcedureElement procedure=pcdbuilder.buildProcedureElement(ctx,cnt,procedureId);

        TaskElement task = procedure.getTask(pstageId, ptaskId);

        procedure.removeTask(cnt,pstageId,ptaskId);
        procedure.store(cnt);

        // Elimina el nodo de la fase en el BPM
        if (StringUtils.isNotBlank(task.getBPMTaskId())) {
        	txContainer.getBPMAPI().deleteProcess(task.getBPMTaskId());
        }
	}

	public Map getCTStageTaskIds(DbCnt cnt, int procedureid)
			throws ISPACException {

	    PcdElementBuilder pcdbuilder = new PcdElementBuilder();
        ProcedureElement procedure = pcdbuilder.buildProcedureElement(ctx, cnt,
        		procedureid);

        return procedure.getCTStageTaskIds();
	}

	public void setStateCurrent(TransactionContainer txContainer,
			int procedureid) throws ISPACException {

		DbCnt cnt = txContainer.getConnection();

	    PcdElementBuilder pcdbuilder=new PcdElementBuilder();
        ProcedureElement procedure=pcdbuilder.buildProcedureElement(ctx,cnt,procedureid);
        procedure.checkStateCurrent(cnt);
        procedure.setStateCurrent(cnt);
        procedure.store(cnt);
	}

	public int delete(TransactionContainer txContainer, int procedureid)
			throws ISPACException {

		DbCnt cnt = txContainer.getConnection();

		PcdElementBuilder pcdbuilder = new PcdElementBuilder();
	    ProcedureElement procedure = pcdbuilder.buildProcedureElement(ctx, cnt,
	    		procedureid);

	    // Eliminar el procedimiento
	    procedure.delete(cnt);

	    // Eliminar el modelo de procesos en el BPM
	    if (StringUtils.isNotBlank(procedure.getBPMPcdId())) {
	    	txContainer.getBPMAPI().deleteProcess(procedure.getBPMPcdId());
	    }

	    return procedure.getId();
	}

    public String getXml(DbCnt cnt, int procedureid) throws ISPACException {

        PcdElementBuilder pcdbuilder = new PcdElementBuilder();
        ProcedureElement procedure = pcdbuilder.buildProcedureElement(ctx, cnt,
        		procedureid);

        return procedure.toXml();
    }

    public File exportProcedure(DbCnt cnt, int procedureid, String path) throws ISPACException {
        PcdElementBuilder pcdbuilder = new PcdElementBuilder();
        ProcedureElement procedure = pcdbuilder.buildProcedureElement(ctx, cnt,	procedureid);

        Map subPcdIds = new HashMap();
        Map ctStageIds = new HashMap();
        Map ctTaskIds = new HashMap();
        Map ctTpDocIds = new HashMap();
        Map ctEntityIds = new HashMap();
        Map ctValidationTableNames = new HashMap();
        Map ctRuleIds = new HashMap();
        Map ctHierarchicalTableNames = new HashMap();
        Map ctHelpIds = new HashMap();

    	// Procedimiento
    	StringBuffer procedures = new StringBuffer();
    	procedures.append(procedure.toXpdl(cnt, ctStageIds, ctTaskIds, ctRuleIds, ctEntityIds, ctTpDocIds, subPcdIds,ctHelpIds));
    	// Subprocesos asociados a los trámites del procedimiento
    	if (!subPcdIds.isEmpty()) {

    		Iterator it = subPcdIds.keySet().iterator();
    		while (it.hasNext()) {

    			Integer idSubPcd = (Integer) it.next();
    			procedures.append(getXpdl(cnt, idSubPcd.intValue(), ctStageIds, ctTaskIds, ctRuleIds, ctEntityIds, ctTpDocIds,ctHelpIds));
    		}
    	}
    	// XPDL de procesos (procedimiento y subprocesos)
    	String workflowProcesses = XmlTag.newTag(ExportProcedureMgr.TAG_WORKFLOW_PROCESSES, procedures.toString());

    	// Elementos del catálogo
    	StringBuffer catalog = new StringBuffer();

    	// Fases
    	catalog.append(XmlTag.newTag(ExportProcedureMgr.TAG_STAGES, ExportProcedureMgr.ctStagesToXml(cnt, ctStageIds)));

    	// Trámites
    	catalog.append(XmlTag.newTag(ExportProcedureMgr.TAG_TASKS, ExportProcedureMgr.ctTasksToXml(cnt, ctTaskIds, ctTpDocIds)));


    	// Tipos de documento con plantillas asociadas
    	List templates = new ArrayList();
    	catalog.append(XmlTag.newTag(ExportProcedureMgr.TAG_TP_DOCS, ExportProcedureMgr.ctTpDocsToXml(cnt, ctTpDocIds, procedureid, templates)));


    	// Entidades con formularios y recursos asociados
    	Map formatters = new HashMap();
    	List entityClasses = new ArrayList();
    	catalog.append(XmlTag.newTag(ExportProcedureMgr.TAG_ENTITIES, ExportProcedureMgr.ctEntitiesToXml(cnt, ctEntityIds, ctValidationTableNames, ctHierarchicalTableNames, formatters, entityClasses)));

    	// Tablas jerarquicas (se modifica el orden para añadir las tablas de validación de las jerarquías)
    	catalog.append(XmlTag.newTag(ExportProcedureMgr.TAG_HIERARCHICAL_TABLES,ExportProcedureMgr.ctHierarchicalTablesToXml(cnt, ctValidationTableNames, ctHierarchicalTableNames)));

    	// Tablas de validación
    	catalog.append(XmlTag.newTag(ExportProcedureMgr.TAG_VALIDATION_TABLES,ExportProcedureMgr.ctValidationTablesToXml(cnt, ctValidationTableNames, ctHierarchicalTableNames)));

    	// Reglas
    	List ruleClasses = new ArrayList();
    	catalog.append(XmlTag.newTag(ExportProcedureMgr.TAG_RULES, ExportProcedureMgr.ctRulesToXml(cnt, ctRuleIds, ruleClasses)));


    	// XPDL del procedimiento
		String sXpdl = XmlTag.getXmlInstruction("ISO-8859-1") +
					   XmlTag.newTag(ExportProcedureMgr.TAG_PACKAGE,
							   		 ExportProcedureMgr.getXpdlPackageHeader(cnt) + workflowProcesses + catalog.toString(),
							   		 ExportProcedureMgr.getXpdlPackageAttributes(procedure.getId(), procedure.getName()));

		return ExportProcedureMgr.createProcedureZipFile(cnt, sXpdl, templates, path, formatters, entityClasses, ruleClasses, ctValidationTableNames, ctHierarchicalTableNames, ctx.getAppLanguage());
    }


    private String getXpdl(DbCnt cnt,
			   int procedureid,
			   Map ctStageIds,
			   Map ctTaskIds,
			   Map ctRuleIds,
			   Map ctEntityIds,
			   Map ctTpDocIds,
			   Map ctHelpIds) throws ISPACException {

		PcdElementBuilder pcdbuilder = new PcdElementBuilder();
		ProcedureElement procedure = pcdbuilder.buildProcedureElement(ctx, cnt,	procedureid);

		return procedure.toXpdl(cnt, ctStageIds, ctTaskIds, ctRuleIds, ctEntityIds, ctTpDocIds, null,ctHelpIds);
    }

    public boolean importProcedure(TransactionContainer txContainer,
			   int parentPcdId,
			   File file,
			   StringBuffer importLog,
			   boolean test) throws ISPACException {

    	return importProcedure(txContainer, parentPcdId, false, file, importLog, test);
    }

    public boolean importProcedure(TransactionContainer txContainer,
    							   int parentPcdId,
    							   boolean asVersion,
    							   File file,
    							   StringBuffer importLog,
    							   boolean test) throws ISPACException {

    	ZipFile zipFile = null;
    	PProcedimientoDAO parentPcd = null;
    	String logSuffix = "";

    	DbCnt cnt = txContainer.getConnection();

    	try {
	    	// Fichero ZIP
	    	zipFile = new ZipFile(file, "CP437");

	    	String language = ctx.getAppLanguage();

			// Obtener la definición XPDL del procedimiento
			ZipEntry zipEntry = (ZipEntry) zipFile.getEntry(ProcedureUtil.getString(language, "export.procedure.fileName.xpdl"));
			if (zipEntry != null) {

		    	// Procesar el XPDL
				XmlFacade xmlFacade = null;
				InputStream zipEntryInputStream = null;
				try {
					zipEntryInputStream = zipFile.getInputStream(zipEntry);
					xmlFacade = new XmlFacade(zipEntryInputStream);
				} finally {
					if (zipEntryInputStream != null) {
						try {
							zipEntryInputStream.close();
						} catch (Exception e) {
						}
					}
				}

		    	Node packageNode = xmlFacade.getSingleNode(ExportProcedureMgr.TAG_PACKAGE);

		    	// Nombre del procedimiento que se va a importar
		    	String pcdName = ImportProcedureMgr.importName(XmlFacade.getAttributeValue(packageNode, ExportProcedureMgr.ATR_NAME));

		    	// Comprobar si la versión de exportación es válida (igual o anterior a la versión del producto)
		    	String version = ImportProcedureMgr.validateExportVersion(cnt, packageNode);
		    	if (version == null) {

		    		throw new ISPACInfo("exception.procedures.import.previousVersion", new String[] {pcdName});
		    	}

		    	// Procedimiento

		    	// Si el procedimiento se importa como familia hija (no como nueva version)
		    	if (!asVersion) {

			    	// Comprobar si el nombre del procedimiento ya existe (bloqueando los procedimientos al importar)
			    	if (!test) {
			    		ObjectDAO.getForUpdate(cnt, PProcedimientoDAO.class, null);
			    	}
			    	PProcedimientoDAO procedure = (PProcedimientoDAO) ObjectDAO.getByName(cnt, PProcedimientoDAO.class, pcdName);
			    	if (procedure != null) {

			    		throw new ISPACInfo("exception.procedures.import.existProcedure", new String[] {pcdName});
			    		//ProcedureUtil.generateLog(importLog, "import.procedure.log.procedure.exists", new String[] {pcdName});
			    	}
		    	}
		    	// Importar como versión
		    	else {
		    		parentPcd = new PProcedimientoDAO(cnt, parentPcdId);
		    		if (parentPcd != null) {

			    		String parentPcdName = parentPcd.getString("NOMBRE");

			    		if (parentPcd.getInt("ESTADO") == IProcedure.PCD_STATE_DRAFT) {

			    			throw new ISPACInfo("exception.procedures.import.existProcedure.version", new String[] {pcdName, parentPcdName});
			    		} else {
			    			// Mismo nombre que el procedimiento padre
			    			pcdName = parentPcdName;

			    			// Sufijo para los mensajes de log
			    			logSuffix = ".version";
			    		}
		    		}
		    	}

	    		if (test) {
	    			ProcedureUtil.generateLog(importLog, language, "import.procedure.log.title.test" + logSuffix, new String[] {pcdName}, true);
	    		}
	    		else {
	    			ProcedureUtil.generateLog(importLog, language, "import.procedure.log.title" + logSuffix, new String[] {pcdName}, true);
	    		}

	    		// Capturar cualquier excepción para incluirla en el log de importación
	    		try {

		    		// Obtener los procesos <WorkflowProcesses>
		    		NodeIterator itWorkflowProcess = XmlFacade.getNodeIterator(packageNode, ImportProcedureMgr.PATH_WORKFLOW_PROCESS);

		    		// Procedimiento
		    		Node pcdNode = itWorkflowProcess.nextNode();

		    		// Elementos de catálogo
		    		Map ctRuleDAOs = new HashMap();
		    		Map ctEntityDAOs = new HashMap();
		    		Map ctHierarchicalDAOs = new HashMap();
		    		Map ctFormDAOs = new HashMap();
		    		Map ctTpDocDAOs = new HashMap();
		            Map ctTemplateDAOs = new HashMap();
		            Map ctTaskDAOs = new HashMap();
		            Map ctStageDAOs = new HashMap();
		            Map subPcds = new HashMap();
		            Map ctHelpDAOs = new HashMap();

		            importLog.append(ExportProcedureMgr.RETORNO);
		            ProcedureUtil.generateLog(importLog, language, "import.procedure.log.title.catalog");

		            // Importar reglas
		            ImportProcedureMgr.importRules(cnt, packageNode, ctRuleDAOs, importLog, test, version, language);

		            // Importar tablas de validación
		            ImportProcedureMgr.importValidationTables(cnt, packageNode, ctEntityDAOs, importLog, test, version, language);

		            // Importar tablas jerarquicas
		            ImportProcedureMgr.importHierarchicalTables(cnt, packageNode, ctHierarchicalDAOs,  importLog, test, version, language);

		            // Importar entidades y formularios asociados
		    		ImportProcedureMgr.importEntities(cnt, packageNode, ctEntityDAOs, ctFormDAOs, importLog, test, version, language,ctx.getAPI().getCatalogAPI());

		    		// Importar tipos de documento
		    		ImportProcedureMgr.importTpDocs(ctx, cnt, packageNode, ctTpDocDAOs, ctTemplateDAOs, importLog, test, zipFile);

		    		// Importar trámites y obtener los trámites complejos (subproceso asociado)
		    		Map xmlSubPcdTasks = ImportProcedureMgr.importTasks(ctx, cnt, packageNode, ctTaskDAOs, importLog, ctTpDocDAOs, test);

		    		// Importar fases
		    		ImportProcedureMgr.importStages(ctx, cnt, packageNode, ctStageDAOs, importLog, test);

		    		// Importar Ayudas
		            ImportProcedureMgr.importHelps(ctx, cnt, pcdNode, ctHelpDAOs, importLog, test, language);

		    		boolean printTitleSubProc = true;

		    		// Importar subprocesos
		    		for (Node subPcdNode = itWorkflowProcess.nextNode(); subPcdNode != null; subPcdNode = itWorkflowProcess.nextNode()) {

		    			if (printTitleSubProc) {

		    				printTitleSubProc=false;
		    				importLog.append(ExportProcedureMgr.RETORNO);
				    		ProcedureUtil.generateLog(importLog, language, "import.procedure.log.title.subproc");
				            importLog.append(ExportProcedureMgr.RETORNO);
		    			}

		    			Integer xmlSubPcdId = Integer.valueOf(XmlFacade.getAttributeValue(subPcdNode, ExportProcedureMgr.ATR_ID));
		    			String subPcdName = ImportProcedureMgr.importName(XmlFacade.getAttributeValue(subPcdNode, ExportProcedureMgr.ATR_NAME));
		    			PProcedimientoDAO subprocessDAO = (PProcedimientoDAO) ObjectDAO.getByName(cnt, PProcedimientoDAO.class, subPcdName);

		    			if (subprocessDAO == null) {

		    				ImportProcedureMgr.importHelps(ctx, cnt, subPcdNode, ctHelpDAOs, importLog, test, language);
		    				ProcedureUtil.generateLog(importLog, language, "import.procedure.log.subprocess", new String[] {subPcdName});

		    				if (!test) {

			    				ProcedureElement subPcdElement = importProcedure(txContainer, 0, null, subPcdNode, subPcdName, IPcdElement.TYPE_SUBPROCEDURE, importLog, ctRuleDAOs, ctEntityDAOs, ctFormDAOs, ctTpDocDAOs, ctTemplateDAOs, ctTaskDAOs, ctStageDAOs,ctHelpDAOs);

			    				// Metemos en el map de subprocesos [clave=id del xml, valor=el nuevo subproceso en BD]
			    				subPcds.put(xmlSubPcdId, subPcdElement.getProcdao());

			    				ProcedureUtil.generateLog(importLog, language, "import.procedure.log.subprocess.create", new String[] {subPcdName});
		    				}
		    			}
		    			else {
		    				// Metemos en el map de subprocesos [clave=id del xml, valor=el subproceso existente en BD]
		    				subPcds.put(xmlSubPcdId, subprocessDAO);

		    				ProcedureUtil.generateLog(importLog, language, "import.procedure.log.subprocess.exists", new String[] {subPcdName});
		    			}
		    		}

		    		// Asignar los subprocesos al trámite correspondiente
		    		if ((!test) &&
		    			(!xmlSubPcdTasks.isEmpty())) {

		    			Iterator it = xmlSubPcdTasks.entrySet().iterator();
		    			while (it.hasNext()) {

		    				Entry entry = (Entry) it.next();
		    				Integer xmlTaskId = (Integer) entry.getKey();
		    				Integer xmlSubPcdId = (Integer) entry.getValue();

		    				// Subproceso en BD
		    				PProcedimientoDAO subprocessDAO = (PProcedimientoDAO) subPcds.get(xmlSubPcdId);
		    				if (subprocessDAO != null) {

		    					// Asignar el subproceso al trámite
		    					CTTaskDAO ctTaskDAO = (CTTaskDAO) ctTaskDAOs.get(xmlTaskId);
		    					ctTaskDAO.set("ID_SUBPROCESO", subprocessDAO.getKeyInt());
		    					ctTaskDAO.store(cnt);

		    					ProcedureUtil.generateLog(importLog, language, "import.procedure.log.task.subprocess", new String[] {ctTaskDAO.getString("NOMBRE"), subprocessDAO.getString("NOMBRE")});
		    				}
		    			}
		    		}

		    		importLog.append(ExportProcedureMgr.RETORNO);
		    		ProcedureUtil.generateLog(importLog, language, "import.procedure.log.title.procedure" + logSuffix);
		            importLog.append(ExportProcedureMgr.RETORNO);

		    		ProcedureUtil.generateLog(importLog, language, "import.procedure.log.procedure" + logSuffix, new String[] {pcdName});
    				if (!test) {

			    		// Importar el procedimiento
			    		importProcedure(txContainer, parentPcdId, parentPcd, pcdNode, pcdName, IPcdElement.TYPE_PROCEDURE, importLog, ctRuleDAOs, ctEntityDAOs, ctFormDAOs, ctTpDocDAOs, ctTemplateDAOs, ctTaskDAOs, ctStageDAOs, ctHelpDAOs);

			    		// El procedimiento / nueva versión se ha importado correctamente
			    		importLog.append(ExportProcedureMgr.RETORNO);
		    			ProcedureUtil.generateLog(importLog, language, "import.procedure.log.procedure.create" + logSuffix, new String[] {pcdName}, true);
    				}

		    		// Incluir en el log de importación el readme
					zipEntry = (ZipEntry) zipFile.getEntry(ProcedureUtil.getString(language, "export.procedure.fileName.readme"));
					if (zipEntry != null) {

						zipEntryInputStream = zipFile.getInputStream(zipEntry);
						try {
							String readme = FileUtils.retrieveInputStreamAsString(zipEntryInputStream);
							if (readme.length() > 0) {

								importLog.append(ExportProcedureMgr.RETORNO)
										 .append(ExportProcedureMgr.RETORNO);
								ProcedureUtil.generateLog(importLog, language, "import.procedure.log.title.readme", true);
					            importLog.append(readme);
							}
						} finally {
							if (zipEntryInputStream != null) {
								try {
									zipEntryInputStream.close();
								} catch (Exception e) {
								}
							}
						}
					}
	    		}
	    	    catch (Exception e) {

	    	    	// Incluir la excepción como error en el log de importación
	    	    	importLog.append(ExportProcedureMgr.RETORNO);
	    	    	ProcedureUtil.generateLog(importLog, language, "import.procedure.log.title.error", true);

	    	    	// Mensaje
	    	    	ProcedureUtil.generateLog(importLog, language, "import.procedure.log.title.error.message");
	    	    	importLog.append(ExportProcedureMgr.RETORNO)
	    	    			 .append(e.getMessage())
	    	    			 .append(ExportProcedureMgr.RETORNO);

	    	    	// Causa
	    	    	importLog.append(ExportProcedureMgr.RETORNO);
	    	    	ProcedureUtil.generateLog(importLog, language, "import.procedure.log.title.error.cause");
	    	    	importLog.append(ExportProcedureMgr.RETORNO)
	    	    			 .append(e.getCause())
	    	    			 .append(ExportProcedureMgr.RETORNO);

	    	    	// El procedimiento no se ha importado
		    		importLog.append(ExportProcedureMgr.RETORNO);
		    		ProcedureUtil.generateLog(importLog, language, "import.procedure.log.title.error.procedure" + logSuffix, new String[] {pcdName}, true);

		    		return false;
	    	    }

		    	return true;
			}
			else {
				throw new ISPACInfo("exception.procedures.import.noXpdlFile");
			}
    	}
        catch (IOException e) {
            throw new ISPACInfo("exception.procedures.import.errorZipFile");
        }
        finally {
        	if (zipFile != null) {
        		try {
        			zipFile.close();
        		} catch (Exception e) {
        		}
        	}
        }
    }

	private ProcedureElement importProcedure(TransactionContainer txContainer,
											 int parentPcdId,
											 PProcedimientoDAO parentPcd,
											 Node node,
											 String name,
											 int type,
											 StringBuffer importLog,
											 Map ctRuleDAOs,
											 Map ctEntityDAOs,
											 Map ctFormDAOs,
											 Map ctTpDocDAOs,
											 Map ctTemplateDAOs,
											 Map ctTaskDAOs,
											 Map ctStageDAOs,
											 Map ctHelpDAOs) throws ISPACException {

		DbCnt cnt = txContainer.getConnection();

        PcdElementFactory pcdfactory = new PcdElementFactory();
        ProcedureElement procedure = null;
        boolean importPcdCode = true;

        if (parentPcd != null) {

        	// Importar como versión del procedimiento padre
        	procedure = pcdfactory.importProcedureAsVersion(ctx, cnt, parentPcd);
        	// Mantener el código de procedimiento del procedimiento padre
        	importPcdCode = false;
        } else {
        	// Importar como familia hija
        	procedure = pcdfactory.importProcedure(ctx, cnt, parentPcdId, name, type);
        }

        // Información del procedimiento
        procedure.importProcedure(node, importPcdCode);

        // Nodos del procedimientos
        Map nodes = new HashMap();

        // Procesar las fases <Activities>
		NodeIterator itActivity = XmlFacade.getNodeIterator(node, ImportProcedureMgr.PATH_ACTIVITY);
		for (Node activityNode = itActivity.nextNode(); activityNode != null; activityNode = itActivity.nextNode()) {

			String activityName = XmlFacade.getAttributeValue(activityNode, ExportProcedureMgr.ATR_NAME);
			if (StringUtils.isNotBlank(activityName)) {

				// Fase o actividad
				importStage(cnt, procedure, name, type, activityNode, ImportProcedureMgr.importName(activityName), importLog, ctStageDAOs, ctTaskDAOs, ctTpDocDAOs, ctRuleDAOs, ctEntityDAOs, ctFormDAOs, nodes);
			}
			else {
				// Nodo de sincronización
				Node joinNode = XmlFacade.getSingleNode(activityNode, ImportProcedureMgr.PATH_JOIN);

				// Tipo del nodo
				int syncNodeType = SyncNodeElement.SYNCNODE_OR;
				String joinType = XmlFacade.getAttributeValue(joinNode, ExportProcedureMgr.ATR_TYPE);
				if (joinType.equals(ExportProcedureMgr.VAL_AND)) {
					syncNodeType = SyncNodeElement.SYNCNODE_AND;
				}

				SyncNodeElement syncNode = procedure.addSyncNode(cnt, procedure.getId(), syncNodeType, ImportProcedureMgr.createGInfo(activityNode));
				Integer syncNodeId = Integer.valueOf(XmlFacade.getAttributeValue(activityNode, ExportProcedureMgr.ATR_ID));
				nodes.put(syncNodeId, syncNode.getIntegerId());
			}
		}

		// Procesar los flujos <Transitions>
		NodeIterator itTransition = XmlFacade.getNodeIterator(node, ImportProcedureMgr.PATH_TRANSITION);
		for (Node transitionNode = itTransition.nextNode(); transitionNode != null; transitionNode = itTransition.nextNode()) {

			// Obtener los Ids de los nodos en BD
			Integer xmlOrigId = Integer.valueOf(XmlFacade.getAttributeValue(transitionNode, ExportProcedureMgr.ATR_FROM));
			Integer xmlDestId = Integer.valueOf(XmlFacade.getAttributeValue(transitionNode, ExportProcedureMgr.ATR_TO));
			int origId = ((Integer) nodes.get(xmlOrigId)).intValue();
			int destId = ((Integer) nodes.get(xmlDestId)).intValue();

			// Crear el flujo
			FlowElement flow = procedure.createFlow(cnt, procedure.getId(), origId, destId);

			// Procesar los eventos <Events>
			importEvents(cnt, transitionNode, flow.getId(), EventsDefines.EVENT_OBJ_FLOW, ctRuleDAOs);
		}

		// Procesar los eventos <Events>
		if (type == IPcdElement.TYPE_PROCEDURE) {
			importEvents(cnt, node, procedure.getId(), EventsDefines.EVENT_OBJ_PROCEDURE, ctRuleDAOs);
		}
		else {
			importEvents(cnt, node, procedure.getId(), EventsDefines.EVENT_OBJ_SUBPROCEDURE, ctRuleDAOs);
		}

		// Procesar las entidades <Entities>
		importProcedureEntities(cnt, node, procedure.getId(), ctEntityDAOs, ctFormDAOs, ctRuleDAOs);

		// Procesar las plantillas específicas <Templates>
		importProcedureTemplates(cnt, node, procedure.getId(), ctTemplateDAOs);

		importHelps(cnt, procedure.getId(),ctHelpDAOs);

		// Guardar toda la definición
        procedure.store(cnt);

        // Crea el modelo de proceso en el BPM
        createBPMProcessModel(txContainer, procedure);

        return procedure;
    }

	private void importStage(DbCnt cnt,
							 ProcedureElement procedure,
							 String pcdName,
							 int pcdType,
							 Node stageNode,
							 String stageName,
							 StringBuffer importLog,
							 Map ctStageDAOs,
							 Map ctTaskDAOs,
							 Map ctTpDocDAOs,
							 Map ctRuleDAOs,
							 Map ctEntityDAOs,
							 Map ctFormDAOs,
							 Map nodes) throws ISPACException {

		String language = ctx.getAppLanguage();

		int ctStageId = ISPACEntities.ENTITY_NULLREGKEYID;
		Integer xmlCtStageId = Integer.valueOf(XmlFacade.getAttributeValue(stageNode, ExportProcedureMgr.ATR_ID_CATALOGO));
		if (xmlCtStageId.intValue() != ISPACEntities.ENTITY_NULLREGKEYID) {
			// Obtener el Id de la fase o actividad en BD
			ctStageId = ((CTStageDAO) ctStageDAOs.get(xmlCtStageId)).getKeyInt();
		}

		// Asociar la fase al procedimiento o la actividad al subproceso
		StageElement stage = procedure.addStage(cnt, procedure.getId(), ctStageId, stageName, ImportProcedureMgr.createGInfo(stageNode));

		// Establecer como nodo
		Integer stageId = Integer.valueOf(XmlFacade.getAttributeValue(stageNode, ExportProcedureMgr.ATR_ID));
		nodes.put(stageId, stage.getIntegerId());

		// Procedimiento
		if (pcdType == IPcdElement.TYPE_PROCEDURE) {

			ProcedureUtil.generateLog(importLog, language, "import.procedure.log.stage.procedure", new String[] {stageName, pcdName});

			// Eventos asociados a la fase
			importEvents(cnt, stageNode, stage.getId(), EventsDefines.EVENT_OBJ_STAGE, ctRuleDAOs);
		}
		// Subproceso
		else {
			ProcedureUtil.generateLog(importLog, language, "import.procedure.log.activity.subprocess", new String[] {stageName, pcdName});

			// Eventos asociados a la actividad
			importEvents(cnt, stageNode, stage.getId(), EventsDefines.EVENT_OBJ_ACTIVITY, ctRuleDAOs);
		}

		// Formularios a nivel de fase o actividad
		importStageForms(cnt, stageNode, stage.getId(), ctEntityDAOs, ctFormDAOs, ctRuleDAOs);

		// Tipos documentales a nivel de fase o actividad
		importStageTpDocs(cnt, stageNode, stage.getId(), ctTpDocDAOs);

		// Trámites de la fase
		if (ctStageId != ISPACEntities.ENTITY_NULLREGKEYID) {

			Map<String, TaskElement> ptaskMap = new HashMap<String, TaskElement>();

	        // Procesar los trámites <Tasks>
			NodeIterator itTask = XmlFacade.getNodeIterator(stageNode, ImportProcedureMgr.PATH_TASK);
			for (Node taskNode = itTask.nextNode(); taskNode != null; taskNode = itTask.nextNode()) {

				// Obtener el Id del trámite en BD
				Integer xmlCtTaskId = Integer.valueOf(XmlFacade.getAttributeValue(taskNode, ExportProcedureMgr.ATR_ID_CATALOGO));
				int ctTaskId = ((CTTaskDAO) ctTaskDAOs.get(xmlCtTaskId)).getKeyInt();

				// Asociar el trámite a la fase en el procedimiento
				TaskElement task = procedure.addTask(cnt, stage.getId(), ctTaskId);
				ptaskMap.put(XmlFacade.getAttributeValue(taskNode, ExportProcedureMgr.ATR_ID), task);
				ProcedureUtil.generateLog(importLog, language, "import.procedure.log.stage.task", new String[] {stageName, task.getName() });

				// Es un trámite obligatorio?
				String obligatory = XmlFacade.getAttributeValue(taskNode, ExportProcedureMgr.ATR_OBLIGATORY);
				if ((obligatory != null) &&
					(obligatory.equals(ExportProcedureMgr.VAL_YES))) {

					task.setObligatory();
				}

				// Eventos asociados al trámite
				importEvents(cnt, taskNode, task.getId(), EventsDefines.EVENT_OBJ_TASK, ctRuleDAOs);

				// Formularios a nivel de trámite
				importTaskForms(cnt, taskNode, task.getId(), ctEntityDAOs, ctFormDAOs, ctRuleDAOs);

				// Asociar el trámite a la fase en el catálogo cuando no está asociado
				if(!CTStageTaskDAO.exist(cnt, ctStageId, ctTaskId)) {

					CTStageTaskDAO stageTask = new CTStageTaskDAO(cnt);
					stageTask.createNew(cnt);

					stageTask.set("ID_FASE", ctStageId);
					stageTask.set("ID_TRAMITE", ctTaskId);

					stageTask.store(cnt);

					ProcedureUtil.generateLog(importLog, language, "import.procedure.log.stage.task", new String[] {stageName, task.getName() });
				}
			}

			// Importar las dependencias entre los trámites de la fase en el procedimiento
			importTaskDependencies(cnt, stageNode, ptaskMap, importLog);
		}
	}

	private void importTaskDependencies(DbCnt cnt, Node stageNode, Map<String, TaskElement> ptaskMap, StringBuffer importLog) throws ISPACException {

		// Lista de trámites de la fase del procedimiento
		NodeIterator taskIterator = XmlFacade.getNodeIterator(stageNode, ImportProcedureMgr.PATH_TASK);
		for (Node taskNode = taskIterator.nextNode(); taskNode != null; taskNode = taskIterator.nextNode()) {

			// Trámite creado en el procedimiento
			TaskElement task = ptaskMap.get(XmlFacade.getAttributeValue(taskNode, ExportProcedureMgr.ATR_ID));
			if (task != null) {

				// Dependencias entre trámites de la misma fase del procedimiento
				NodeIterator dependencyIt = XmlFacade.getNodeIterator(taskNode, ExportProcedureMgr.TAG_DEPENDENCIES + "/" + ExportProcedureMgr.TAG_DEPENDENCY);
				for (Node dependencyNode = dependencyIt.nextNode(); dependencyNode != null; dependencyNode = dependencyIt.nextNode()) {

					// Trámite creado en el procedimiento del que depende el trámite actual
					TaskElement parentTask = ptaskMap.get(XmlFacade.getAttributeValue(dependencyNode, ExportProcedureMgr.ATR_ID_TRAMITE));
					if (parentTask != null) {

						// Añadir la dependencia entre los trámites
						PDepTramiteDAO dep = new PDepTramiteDAO(cnt);
						dep.createNew(cnt);
						dep.set(PDepTramiteDAO.ID_TRAMITE_PADRE, parentTask.getId());
						dep.set(PDepTramiteDAO.ID_TRAMITE_HIJO, task.getId());
						dep.store(cnt);

						ProcedureUtil.generateLog(importLog, ctx.getAppLanguage(), "import.procedure.log.stage.task.dependency", new String[] { task.getName(), parentTask.getName() });
					}
				}
			}
		}
	}

	private void importEvents(DbCnt cnt, Node node, int idObj, int tpObj, Map ctRuleDAOs) throws ISPACException {

        // Procesar los eventos <Events>
		NodeIterator itEvent = XmlFacade.getNodeIterator(node, ImportProcedureMgr.PATH_EVENT);
		for (Node eventNode = itEvent.nextNode(); eventNode != null; eventNode = itEvent.nextNode()) {

			String sXmlCtRuleId = XmlFacade.getAttributeValue(eventNode, ExportProcedureMgr.ATR_ID_REGLA);

			// Evento con regla o condición SQL
			if (sXmlCtRuleId != null) {

				// Obtener la regla en BD
				Integer xmlCtRuleId = Integer.valueOf(sXmlCtRuleId);
				CTRuleDAO ctRuleDAO = (CTRuleDAO) ctRuleDAOs.get(xmlCtRuleId);
				if (ctRuleDAO != null) {

					// Evento con el Id de la regla en BD
					ImportProcedureMgr.createEvent(cnt, eventNode, idObj, tpObj, ctRuleDAO.getKeyInt());
				}
			}
			else {
				// Evento con condición SQL (ID_REGLA a nulo)
				ImportProcedureMgr.createEvent(cnt, eventNode, idObj, tpObj, ISPACEntities.ENTITY_NULLREGKEYID);
			}
		}
	}


	private void importHelps(DbCnt cnt, int idPcd,Map ctHelpDAOs) throws ISPACException{


		Iterator itr = ctHelpDAOs.keySet().iterator();
		while(itr.hasNext()){

			CTHelpDAO ctHelpDao= (CTHelpDAO)ctHelpDAOs.get(itr.next());
			ctHelpDao.set("ID_OBJ", idPcd);
			ctHelpDao.store(cnt);
		}
	}

	private void importProcedureEntities(DbCnt cnt, Node node, int pcdId, Map ctEntityDAOs, Map ctFormDAOs, Map ctRuleDAOs) throws ISPACException {

		// Procesar las entidades <Entities>
		NodeIterator itEntity = XmlFacade.getNodeIterator(node, ImportProcedureMgr.PATH_ENTITY);
		for (Node entityNode = itEntity.nextNode(); entityNode != null; entityNode = itEntity.nextNode()) {

			PEntidadDAO entity = new PEntidadDAO(cnt);
			entity.createNew(cnt);

			Integer xmlCtEntityId = Integer.valueOf(XmlFacade.getAttributeValue(entityNode, ExportProcedureMgr.ATR_ID));
			int ctEntityId = xmlCtEntityId.intValue();
			switch (ctEntityId) {

				// Entidades por defecto
				// El Id de la entidad en BD es el mismo en el XML
				case ISPACEntities.DT_ID_EXPEDIENTES:
				case ISPACEntities.DT_ID_TRAMITES:
				case ISPACEntities.DT_ID_DOCUMENTOS:
				case ISPACEntities.DT_ID_INTERVINIENTES:
				case ISPACEntities.DT_ID_REGISTROS_ES:
					break;

				default:
					// Obtener la entidad en BD
					CTEntityDAO ctEntityDAO = (CTEntityDAO) ctEntityDAOs.get(xmlCtEntityId);
					// Obtener el Id de la entidad en BD
					ctEntityId = ctEntityDAO.getKeyInt();
			}
			// Establecer el Id de la entidad en BD
			entity.set("ID_ENT", ctEntityId);

			entity.set("ID_PCD", pcdId);
			entity.set("ORDEN", entity.getKeyInt());

			String tpRel = XmlFacade.get(entityNode, ExportProcedureMgr.TAG_TP_REL);
			if(StringUtils.isNotBlank(tpRel)) {
				entity.set("TP_REL", Integer.parseInt(tpRel));
			}

			// Formulario
			Node formNode = XmlFacade.getSingleNode(entityNode, ExportProcedureMgr.TAG_FORM);
			if (formNode != null) {

				// Id del formulario en el xml
				String xmlEntityFormId = XmlFacade.getNodeValue(formNode);
				if(StringUtils.isNotBlank(xmlEntityFormId)) {

					Integer xmlCtFormId = Integer.valueOf(xmlEntityFormId);

					// Formulario no visible
					if (xmlCtFormId.intValue() == ISPACEntities.ENTITY_FORM_NO_VISIBLE) {
						entity.set("FRM_EDIT", xmlCtFormId);
					}
					else {
						// Obtener el formulario en BD
						CTApplicationDAO formDAO = (CTApplicationDAO) ctFormDAOs.get(xmlCtFormId);
						if (formDAO != null) {

							// Establecer el Id del formulario en BD
							entity.set("FRM_EDIT", formDAO.getKeyInt());
						}
					}
				}

				// Formulario en sólo lectura
				String sFrmReadonly = XmlFacade.getAttributeValue(formNode, ExportProcedureMgr.ATR_READONLY);
				if ((StringUtils.isNotBlank(sFrmReadonly)) &&
					(sFrmReadonly.equals(ExportProcedureMgr.VAL_YES))) {

					entity.set("FRM_READONLY", ISPACEntities.ENTITY_FORM_READONLY);
				}

				// Regla que asigna el formulario
				String sXmlCtRuleFormId = XmlFacade.getAttributeValue(formNode, ExportProcedureMgr.ATR_ID_REGLA_FORMULARIO);
				if (StringUtils.isNotBlank(sXmlCtRuleFormId)) {

					Integer xmlCtRuleFormId = Integer.valueOf(sXmlCtRuleFormId);

					// Obtener la regla en BD
					CTRuleDAO ruleDAO = (CTRuleDAO) ctRuleDAOs.get(xmlCtRuleFormId);
					if (ruleDAO != null) {

						// Establecer el Id de la regla en BD
						entity.set("ID_RULE_FRM", ruleDAO.getKeyInt());
					}
				}

				// Regla que establece la visibilidad de la entidad
				String sXmlCtRuleVisibleId = XmlFacade.getAttributeValue(formNode, ExportProcedureMgr.ATR_ID_REGLA_VISIBLE);
				if (StringUtils.isNotBlank(sXmlCtRuleVisibleId)) {

					Integer xmlCtRuleVisibleId = Integer.valueOf(sXmlCtRuleVisibleId);

					// Obtener la regla en BD
					CTRuleDAO ruleDAO = (CTRuleDAO) ctRuleDAOs.get(xmlCtRuleVisibleId);
					if (ruleDAO != null) {

						// Establecer el Id de la regla en BD
						entity.set("ID_RULE_VISIBLE", ruleDAO.getKeyInt());
					}
				}
			}

			// Guardar la entidad en el procedimiento
			entity.store(cnt);

			// Eventos de la entidad en el procedimiento
			importEvents(cnt, entityNode, entity.getKeyInt(), EventsDefines.EVENT_OBJ_ENTITY, ctRuleDAOs);
		}
	}

	private void importProcedureTemplates(DbCnt cnt, Node node, int pcdId, Map ctTemplateDAOs) throws ISPACException {

		// Procesar las plantillas <Templates>
		NodeIterator itTemplate = XmlFacade.getNodeIterator(node, ImportProcedureMgr.PATH_TEMPLATE);
		for (Node templateNode = itTemplate.nextNode(); templateNode != null; templateNode = itTemplate.nextNode()) {

			// Obtener la plantilla en BD
			Integer xmlTemplateId = Integer.valueOf(XmlFacade.getAttributeValue(templateNode, ExportProcedureMgr.ATR_ID));
			TemplateDAO templateDAO = (TemplateDAO) ctTemplateDAOs.get(xmlTemplateId);
			if (templateDAO != null) {

				PcdTemplateDAO pcdTemplate = new PcdTemplateDAO(cnt);
				pcdTemplate.createNew(cnt);

				pcdTemplate.set("ID_PCD", pcdId);

				// Establecer el Id de la plantilla en BD
				pcdTemplate.set("ID_P_PLANTDOC", templateDAO.getKeyInt());

				pcdTemplate.store(cnt);
			}
		}
	}

	/**
	 *
	 * @param cnt
	 * @param taskNode
	 * @param taskId
	 * @param ctEntityDAOs
	 * @param ctFormDAOs
	 * @param ctRuleDAOs
	 * @throws ISPACException
	 */
	private void importTaskForms(DbCnt cnt, Node taskNode, int taskId, Map ctEntityDAOs, Map ctFormDAOs, Map ctRuleDAOs) throws ISPACException {

        // Procesar los formularios <Forms>
		NodeIterator itForm = XmlFacade.getNodeIterator(taskNode, ImportProcedureMgr.PATH_FORM);
		for (Node formNode = itForm.nextNode(); formNode != null; formNode = itForm.nextNode()) {

			PFrmTramiteDAO taskForm = new PFrmTramiteDAO(cnt);
			taskForm.createNew(cnt);

			taskForm.set("ID_TRAMITE", taskId);
			importForm(formNode, taskForm, ctEntityDAOs, ctFormDAOs, ctRuleDAOs);

			taskForm.store(cnt);
		}
	}

	/**
	 *
	 * @param cnt
	 * @param stageNode
	 * @param stageId
	 * @param ctEntityDAOs
	 * @param ctFormDAOs
	 * @param ctRuleDAOs
	 * @throws ISPACException
	 */
	private void importStageForms(DbCnt cnt, Node stageNode, int stageId, Map ctEntityDAOs, Map ctFormDAOs, Map ctRuleDAOs) throws ISPACException {

        // Procesar los formularios <Forms>
		NodeIterator itForm = XmlFacade.getNodeIterator(stageNode, ImportProcedureMgr.PATH_FORM);
		for (Node formNode = itForm.nextNode(); formNode != null; formNode = itForm.nextNode()) {

			PFrmFaseDAO stageForm = new PFrmFaseDAO(cnt);
			stageForm.createNew(cnt);

			stageForm.set("ID_FASE", stageId);
			importForm(formNode, stageForm, ctEntityDAOs, ctFormDAOs, ctRuleDAOs);

			stageForm.store(cnt);
		}
	}

	private void importStageTpDocs(DbCnt cnt, Node stageNode, int stageId, Map ctTpDocDAOs) throws ISPACException {

        // Procesar los tipos documentales <TpDocs>
		NodeIterator itTpDoc = XmlFacade.getNodeIterator(stageNode, ImportProcedureMgr.PATH_TPDOC);
		for (Node tpDocNode = itTpDoc.nextNode(); tpDocNode != null; tpDocNode = itTpDoc.nextNode()) {

			// Obtener el tipo documental en BD
			Integer xmlCtTpDodId = Integer.valueOf(XmlFacade.getAttributeValue(tpDocNode, ExportProcedureMgr.ATR_ID));
			CTTpDocDAO ctTpDocDAO = (CTTpDocDAO) ctTpDocDAOs.get(xmlCtTpDodId);
			if (ctTpDocDAO != null) {

				PFaseTDocDAO stageTpDoc = new PFaseTDocDAO(cnt);
				stageTpDoc.createNew(cnt);

				stageTpDoc.set("ID_FASE", stageId);
				// Establecer el Id del tipo documental en BD
				stageTpDoc.set("ID_TPDOC", ctTpDocDAO.getKeyInt());

				stageTpDoc.store(cnt);
			}
		}
	}

	/**
	 *
	 * @param formNode
	 * @param objectDAO
	 * @param ctEntityDAOs
	 * @param ctFormDAOs
	 * @param ctRuleDAOs
	 * @throws ISPACException
	 */
	private void importForm(Node formNode, ObjectDAO objectDAO, Map ctEntityDAOs, Map ctFormDAOs, Map ctRuleDAOs) throws ISPACException {

		Integer xmlCtEntityId = Integer.valueOf(XmlFacade.getAttributeValue(formNode, ExportProcedureMgr.ATR_ID_ENTIDAD));
		int ctEntityId = xmlCtEntityId.intValue();
		switch (ctEntityId) {

			// Entidades por defecto
			// El Id de la entidad en BD es el mismo en el XML
			case ISPACEntities.DT_ID_EXPEDIENTES:
			case ISPACEntities.DT_ID_TRAMITES:
			case ISPACEntities.DT_ID_DOCUMENTOS:
			case ISPACEntities.DT_ID_INTERVINIENTES:
			case ISPACEntities.DT_ID_REGISTROS_ES:
				break;

			default:
				// Obtener la entidad en BD
				CTEntityDAO ctEntityDAO = (CTEntityDAO) ctEntityDAOs.get(xmlCtEntityId);
				// Obtener el Id de la entidad en BD
				ctEntityId = ctEntityDAO.getKeyInt();
		}
		// Establecer el Id de la entidad en BD
		objectDAO.set("ID_ENT", ctEntityId);

		// Id del formulario en el xml
		String sXmlCtFormId = XmlFacade.getAttributeValue(formNode, ExportProcedureMgr.ATR_ID);
		if (StringUtils.isNotBlank(sXmlCtFormId)) {

			Integer xmlCtFormId = Integer.valueOf(sXmlCtFormId);

			// Formulario no visible
			if (xmlCtFormId.intValue() == ISPACEntities.ENTITY_FORM_NO_VISIBLE) {
				objectDAO.set("FRM_EDIT", xmlCtFormId);
			}
			else {
				// Obtener el formulario en BD
				CTApplicationDAO formDAO = (CTApplicationDAO) ctFormDAOs.get(xmlCtFormId);
				if (formDAO != null) {

					// Establecer el Id del formulario en BD
					objectDAO.set("FRM_EDIT", formDAO.getKeyInt());
				}
			}
		}

		// Formulario sólo lectura
		String sFrmReadonly = XmlFacade.getAttributeValue(formNode, ExportProcedureMgr.ATR_READONLY);
		if (StringUtils.isNotBlank(sFrmReadonly)) {

			if (sFrmReadonly.equals(ExportProcedureMgr.VAL_NO)) {
				// Formulario editable
				objectDAO.set("FRM_READONLY", ISPACEntities.ENTITY_FORM_EDITABLE);
			}
			else if (sFrmReadonly.equals(ExportProcedureMgr.VAL_YES)) {
				// Formulario en sólo lectura
				objectDAO.set("FRM_READONLY", ISPACEntities.ENTITY_FORM_READONLY);
			}
		}

		// Regla que asigna el formulario
		String sXmlCtRuleFormId = XmlFacade.getAttributeValue(formNode, ExportProcedureMgr.ATR_ID_REGLA_FORMULARIO);
		if (StringUtils.isNotBlank(sXmlCtRuleFormId)) {

			Integer xmlCtRuleFormId = Integer.valueOf(sXmlCtRuleFormId);

			// Obtener la regla en BD
			CTRuleDAO ruleDAO = (CTRuleDAO) ctRuleDAOs.get(xmlCtRuleFormId);
			if (ruleDAO != null) {

				// Establecer el Id de la regla en BD
				objectDAO.set("ID_RULE_FRM", ruleDAO.getKeyInt());
			}
		}

		// Regla que establece la visibilidad de la entidad
		String sXmlCtRuleVisibleId = XmlFacade.getAttributeValue(formNode, ExportProcedureMgr.ATR_ID_REGLA_VISIBLE);
		if (StringUtils.isNotBlank(sXmlCtRuleVisibleId)) {

			Integer xmlCtRuleVisibleId = Integer.valueOf(sXmlCtRuleVisibleId);

			// Obtener la regla en BD
			CTRuleDAO ruleDAO = (CTRuleDAO) ctRuleDAOs.get(xmlCtRuleVisibleId);
			if (ruleDAO != null) {

				// Establecer el Id de la regla en BD
				objectDAO.set("ID_RULE_VISIBLE", ruleDAO.getKeyInt());
			}
		}
	}

    private void createBPMProcessModel(TransactionContainer txContainer,
    		ProcedureElement procedure) throws ISPACException {

    	DbCnt cnt = txContainer.getConnection();

        // Crear el nodo de la fase en el BPM
        IBPMAPI bpmAPI = txContainer.getBPMAPI();
		String bpmPcdId = bpmAPI.createProcess(procedure.getProcessModel());
		if (StringUtils.isNotBlank(bpmPcdId)) {
			procedure.setBPMPcdId(bpmPcdId);

			// Fases del modelo de procesos
			Iterator stages = procedure.getStages().iterator();
			while (stages.hasNext()) {
				StageElement stage = (StageElement) stages.next();
				ProcessNode bpmStage = stage.getProcessModelNode();
				String bpmStageId = bpmAPI.createNode(bpmPcdId, bpmStage);
				stage.setBPMNodeId(bpmStageId);

				// Trámites de la fase
				Collection collectionTasks = stage.getTasks();
				if (collectionTasks != null){
					Iterator tasks = collectionTasks.iterator();
					while (tasks.hasNext()) {
						TaskElement task = (TaskElement) tasks.next();
						String bpmTaskId = bpmAPI.createTask(task.getProcessModel());
						if (bpmTaskId != null) {

							// Establecer el id en el BPM
							task.setBPMTaskId(bpmTaskId);

							// Asociar el trámite con la fase
							bpmAPI.addNodeSubProcess(stage.getBPMNodeId(), bpmTaskId);
						}
					}
				}
			}

			// Nodos de sincronización del modelo de procesos
			Iterator syncNodes = procedure.getSyncNodes().iterator();
			while (syncNodes.hasNext()) {
				SyncNodeElement syncNode = (SyncNodeElement) syncNodes.next();
				ProcessNode bpmSyncNode = syncNode.getProcessModelNode();
				String bpmSyncNodeId = bpmAPI.createNode(bpmPcdId, bpmSyncNode);
				syncNode.setBPMNodeId(bpmSyncNodeId);
			}

			// Flujos del modelo de procesos
			Iterator flows = procedure.getFlows().iterator();
			while (flows.hasNext()) {
				FlowElement flow = (FlowElement) flows.next();
				ProcessFlow bpmFlow = flow.getProcessModelFlow();
				String bpmFlowId = bpmAPI.createFlow(bpmPcdId, bpmFlow);
				flow.setBPMFlowId(bpmFlowId);
			}

			procedure.store(cnt);
		}
    }

}