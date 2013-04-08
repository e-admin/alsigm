package ieci.tdw.ispac.ispaclib.catalog.procedure.element;

import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.graph.GInfo;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.bpm.ProcessNode;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ExportProcedureMgr;
import ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.PcdElementBuilder;
import ieci.tdw.ispac.ispaclib.catalog.procedure.PcdElementFactory;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ProcedureUtil;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PEventoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PNodoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PRelPlazoDAO;
import ieci.tdw.ispac.ispaclib.dao.publisher.PubReglaDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * StageElement
 *
 *
 * @version $Revision: 1.26 $ $Date: 2009/03/17 12:11:50 $
 */
public class StageElement implements IPcdElement
{
    private PFaseDAO stagedao;
    private PNodoDAO nodedao;

    private Map taskdefmap;
    private List deadlinelist;

    private List tpdoclist;
    private List stageapplist;
    private List eventlist;

    private List flowinlist;
    private List flowoutlist;

    private List pubrulelist;

    public StageElement(PFaseDAO stagedao,PNodoDAO nodedao)
    throws ISPACException
    {
        if (stagedao.getKeyInt()!=nodedao.getKeyInt())
            throw new ISPACException("Error StageDef - El  identificador de nodo no corresponde con la fase.");
        this.stagedao=stagedao;
        this.nodedao=nodedao;

        this.taskdefmap = new LinkedHashMap();
        this.tpdoclist = null;
        this.stageapplist = null;
        this.eventlist = null;
        this.deadlinelist = null;
        this.pubrulelist = null;

        flowinlist = new ArrayList();
        flowoutlist = new ArrayList();
    }


    private StageElement(PFaseDAO stagedao, PNodoDAO nodedao,
            Map taskdefmap, List tpdoclist, List stageapplist, List eventlist, List deadlinelist, List pubrulelist)
    throws ISPACException
    {
        if (stagedao.getKeyInt()!=nodedao.getKeyInt())
            throw new ISPACException("Error StageDef - El  identificador de nodo no corresponde con la fase.");
        this.stagedao=stagedao;
        this.nodedao=nodedao;

        this.taskdefmap = taskdefmap;
        this.tpdoclist = tpdoclist;
        this.stageapplist = stageapplist;
        this.eventlist = eventlist;
        this.deadlinelist = deadlinelist;
        this.pubrulelist = pubrulelist;

        flowinlist = new ArrayList();
        flowoutlist = new ArrayList();
    }

    public void load(DbCnt cnt) throws ISPACException
    {
        stageapplist=stagedao.getStageApps(cnt).toList();
        tpdoclist=stagedao.getStageTpDocs(cnt).toList();

        PcdElementBuilder objdeffactory= new PcdElementBuilder();
        taskdefmap=objdeffactory.buildTaskElement(cnt,stagedao.getTasks(cnt));

        CollectionDAO coldao=PRelPlazoDAO.getRelDeadline(cnt,PRelPlazoDAO.DEADLINE_OBJ_STAGE,getId());
        deadlinelist=objdeffactory.buildDeadlineElement(cnt,coldao);

        eventlist=PEventoDAO.getEvents(cnt,EventsDefines.EVENT_OBJ_STAGE,getId()).toList();
        pubrulelist = PubReglaDAO.getRulesAsignedOnlyStage(cnt, getId()).toList();
    }

	public void setTasks(Map tasks)
	throws ISPACException
	{
	    taskdefmap=tasks;

        ProcedureUtil.setPcdElementProperty(taskdefmap,null,
                stagedao.getKeyInteger(),null);
	}

	public void removeTasks(DbCnt cnt,Collection cttaskids) throws ISPACException
	{
	    Map currenttaskctmap=ProcedureUtil.indexPcdElementByCatalogId(taskdefmap);
	    Iterator it=cttaskids.iterator();
	    while (it.hasNext())
        {
            Integer cttaskid = (Integer) it.next();
            TaskElement task=(TaskElement)currenttaskctmap.get(cttaskid);
            taskdefmap.remove(task.getIntegerId());
            task.delete(cnt);
            deleteTaskChildDependencies(cnt, task.getId());
        }
	}

	public void addTasks(DbCnt cnt,Collection cttaskids) throws ISPACException
	{
	    //Map currenttaskctmap=ProcedureUtil.indexPcdElementByCatalogId(taskdefmap);

        PcdElementFactory pcdfactory=new PcdElementFactory();

	    Iterator it=cttaskids.iterator();
	    while (it.hasNext())
        {
            Integer cttaskid = (Integer) it.next();
            TaskElement task=pcdfactory.createTask(cnt,cttaskid.intValue());
            taskdefmap.put(task.getIntegerId(),task);
        }
	}

	public void removeTask(DbCnt cnt,int ptaskId) throws ISPACException
	{

	    TaskElement task=(TaskElement)taskdefmap.remove(new Integer(ptaskId));
	    if (task!=null) {
	        task.delete(cnt);
	        deleteTaskChildDependencies(cnt, ptaskId);
	    }
	}

	protected void deleteTaskChildDependencies(DbCnt cnt, int ptaskId) throws ISPACException {
		Iterator taskIterator = taskdefmap.values().iterator();
		while (taskIterator.hasNext()) {
			TaskElement task = (TaskElement) taskIterator.next();
			task.deleteChildDependencies(cnt, ptaskId);
		}
	}
	
	public TaskElement addTask(DbCnt cnt,int cttaskId) throws ISPACException {
        PcdElementFactory pcdfactory=new PcdElementFactory();
        TaskElement task=pcdfactory.createTask(cnt,cttaskId);
        taskdefmap.put(task.getIntegerId(),task);

        if (stagedao==null)
            throw new ISPACException("StageElement - La estructura de la fase no está debidamente construida");

        task.setProcedureId(stagedao.getInt("ID_PCD"));
        task.setStagePcdId(getId());

        return task;
	}


	public List getCTTasksIdList() throws ISPACException
	{
        if (taskdefmap==null)
            return null;

        ArrayList cttaskidlist=new ArrayList();
        Iterator it=taskdefmap.values().iterator();
        while (it.hasNext())
        {
            IPcdElement objdef = (IPcdElement) it.next();
            cttaskidlist.add(objdef.getCatalogIntegerId());
        }
        return cttaskidlist;
	}

	public Collection getTasks() throws ISPACException {
		if (taskdefmap == null)
			return null;
		return taskdefmap.values();
	}

	public TaskElement getTask(int taskId) throws ISPACException {
		return (TaskElement) taskdefmap.get(new Integer(taskId));
	}

    public void modify(DbCnt cnt,List cttaskidlist) throws ISPACException
    {
        //Se calculan los trámites que se deben eliminar de esta fase.
        Map currenttaskctmap=ProcedureUtil.indexPcdElementByCatalogId(taskdefmap);
        Set removecttaskidset=new HashSet(currenttaskctmap.keySet());

        removecttaskidset.removeAll(cttaskidlist);
        removeTasks(cnt,removecttaskidset);

        //Se calculan los nuevos trámites que se deben añadir a esta fase.
        Set currencttaskidset=new HashSet(currenttaskctmap.keySet());
        cttaskidlist.removeAll(currencttaskidset);

        addTasks(cnt,cttaskidlist);

        ProcedureUtil.setPcdElementProperty(taskdefmap,null,
                getIntegerId(),null);
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#getId()
     */
    public int getId()
    throws ISPACException
    {
        if (stagedao==null)
            throw new ISPACException("StageElement - La estructura de la fase no está debidamente construida");

        return stagedao.getKeyInt();
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#getIntegerId()
     */
    public Integer getIntegerId()
    throws ISPACException
    {
        if (stagedao==null)
            throw new ISPACException("StageElement - La estructura de la fase no está debidamente construida");

        return stagedao.getKeyInteger();
    }

    public String getName() throws ISPACException {
        if (stagedao==null) {
            throw new ISPACException("StageElement - La estructura de la fase no está debidamente construida");
        }

        return stagedao.getString("NOMBRE");
    }

    public String getResponsibleId() throws ISPACException {
        if (stagedao==null) {
            throw new ISPACException("StageElement - La estructura de la fase no está debidamente construida");
        }

        return stagedao.getString("ID_RESP");
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getCatalogId()
     */
    public int getCatalogId() throws ISPACException
    {
        if (stagedao==null)
            throw new ISPACException("StageElement - La estructura de la fase no está debidamente construida");


        return stagedao.getInt("ID_CTFASE");
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getCatalogIntegerId()
     */
    public Integer getCatalogIntegerId() throws ISPACException
    {
        if (stagedao==null)
            throw new ISPACException("StageElement - La estructura de la fase no está debidamente construida");

        return new Integer(stagedao.getInt("ID_CTFASE"));
    }
    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#duplicate(ieci.tdw.ispac.ispaclib.db.DbCnt)
     */
    public IPcdElement duplicate(DbCnt cnt) throws ISPACException
    {
        PFaseDAO newstagedao=(PFaseDAO)stagedao.duplicate(cnt);
        PNodoDAO newnodedao=(PNodoDAO)nodedao.duplicate(cnt);
        newnodedao.setKey(newstagedao.getKey());


        Map newtaskdefmap=ProcedureUtil.duplicateTaskElements(cnt,taskdefmap);
        
        List newdeadlinelist=ProcedureUtil.duplicatePcdElement(cnt,deadlinelist);

        List newtpdoclist=ProcedureUtil.duplicate(cnt,tpdoclist);
        List newstageapplist=ProcedureUtil.duplicate(cnt,stageapplist);
        List neweventlist=ProcedureUtil.duplicate(cnt,eventlist);

         ProcedureUtil.setPcdElementProperty(newtaskdefmap,null,
                newstagedao.getKeyInteger(),null);

        ProcedureUtil.setPcdElementProperty(newdeadlinelist,null,newstagedao.getKeyInteger(),null);

        ProcedureUtil.setProperty(newtpdoclist,"ID_FASE",newstagedao.getKey());
        ProcedureUtil.setProperty(newstageapplist,"ID_FASE",newstagedao.getKey());
        ProcedureUtil.setProperty(neweventlist,"ID_OBJ",newstagedao.getKey());

        // Reglas de publicación
        List newpubrulelist = ProcedureUtil.duplicate(cnt, pubrulelist);
        ProcedureUtil.setProperty(newpubrulelist, "ID_FASE", stagedao.getKey());

        return new StageElement(newstagedao,newnodedao,newtaskdefmap,
                newtpdoclist, newstageapplist, neweventlist, newdeadlinelist, newpubrulelist);
    }

    public IPcdElement duplicate(DbCnt cnt, List cttaskidlist) throws ISPACException
    {
        PFaseDAO newstagedao=(PFaseDAO)stagedao.duplicate(cnt);
        PNodoDAO newnodedao=(PNodoDAO)nodedao.duplicate(cnt);
        newnodedao.setKey(newstagedao.getKey());

        // Duplicar los trámites a partir de los IDs de catálogo recibidos
        Map newtaskdefmap = new LinkedHashMap();

        if (cttaskidlist != null) {

        	// Trámites de la fase por ID de catálogo
        	Map currenttaskctmap = ProcedureUtil.indexPcdElementByCatalogId(taskdefmap);
        	Map<Integer, Integer> taskIdMap = new HashMap<Integer, Integer>();

        	Iterator it = cttaskidlist.iterator();
        	while (it.hasNext()) {

        		Integer ctTaskId = (Integer) it.next();

        		IPcdElement taskElement = (IPcdElement) currenttaskctmap.get(ctTaskId);
        		if (taskElement != null) {

        			// Trámite de la fase del procedimiento
	        		IPcdElement dupTaskElement = taskElement.duplicate(cnt);
	        		newtaskdefmap.put(dupTaskElement.getIntegerId(), dupTaskElement);
	        		taskIdMap.put(taskElement.getIntegerId(), dupTaskElement.getIntegerId());
        		}
        		else {
        			// Nuevo trámite para la fase
        	        PcdElementFactory pcdfactory = new PcdElementFactory();
        	        TaskElement newTaskElement = pcdfactory.createTask(cnt, ctTaskId.intValue());
        	        newtaskdefmap.put(newTaskElement.getIntegerId(), newTaskElement);
        	        taskIdMap.put(newTaskElement.getIntegerId(), newTaskElement.getIntegerId());
        		}
        	}
        	
    		// Actualizar el identificador del trámite padre en las dependencias entre trámites
    		Iterator taskIterator = newtaskdefmap.values().iterator();
    		while (taskIterator.hasNext()) {
    			TaskElement task = (TaskElement) taskIterator.next();
    			task.updateDependencies(cnt, taskIdMap);
    		}
        }

        List newdeadlinelist=ProcedureUtil.duplicatePcdElement(cnt,deadlinelist);
        List newtpdoclist=ProcedureUtil.duplicate(cnt,tpdoclist);
        List newstageapplist=ProcedureUtil.duplicate(cnt,stageapplist);
        List neweventlist=ProcedureUtil.duplicate(cnt,eventlist);

        ProcedureUtil.setPcdElementProperty(newtaskdefmap,null,newstagedao.getKeyInteger(),null);
        ProcedureUtil.setPcdElementProperty(newdeadlinelist,null,newstagedao.getKeyInteger(),null);

        ProcedureUtil.setProperty(newtpdoclist,"ID_FASE",newstagedao.getKey());
        ProcedureUtil.setProperty(newstageapplist,"ID_FASE",newstagedao.getKey());
        ProcedureUtil.setProperty(neweventlist,"ID_OBJ",newstagedao.getKey());

        // Reglas de publicación
        List newpubrulelist = ProcedureUtil.duplicate(cnt, pubrulelist);
        ProcedureUtil.setProperty(newpubrulelist, "ID_FASE", newstagedao.getKey());

        return new StageElement(newstagedao,newnodedao,newtaskdefmap,
                newtpdoclist, newstageapplist, neweventlist, newdeadlinelist, newpubrulelist);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#store(ieci.tdw.ispac.ispaclib.db.DbCnt)
     */
    public void store(DbCnt cnt) throws ISPACException
    {
        stagedao.store(cnt);
        nodedao.store(cnt);

        ProcedureUtil.storePcdElement(cnt,taskdefmap);
        ProcedureUtil.storePcdElement(cnt,deadlinelist);

        ProcedureUtil.store(cnt,tpdoclist);
        ProcedureUtil.store(cnt,stageapplist);
        ProcedureUtil.store(cnt,eventlist);

        // Duplicar las reglas de publicación
        // En el duplicate el nuevo PFaseDAO aún no tiene establecido
        // el identificador del nuevo procedimiento también duplicado
        ProcedureUtil.setProperty(pubrulelist, "ID_PCD", stagedao.getInt("ID_PCD"));
        ProcedureUtil.store(cnt, pubrulelist);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#delete(ieci.tdw.ispac.ispaclib.db.DbCnt)
     */
    public void delete(DbCnt cnt) throws ISPACException
    {
        stagedao.delete(cnt);
        nodedao.delete(cnt);

        if (taskdefmap!=null)
            ProcedureUtil.deletePcdElement(cnt,taskdefmap.values());
        ProcedureUtil.deletePcdElement(cnt,deadlinelist);

        ProcedureUtil.delete(cnt,tpdoclist);
        ProcedureUtil.delete(cnt,stageapplist);
        ProcedureUtil.delete(cnt,eventlist);
        ProcedureUtil.delete(cnt, pubrulelist);

        this.taskdefmap=null;
        this.tpdoclist=null;
        this.stageapplist=null;
        this.eventlist=null;
        this.deadlinelist=null;
        this.pubrulelist = null;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#setProcedureId(ieci.tdw.ispac.ispaclib.db.DbCnt, int)
     */
    public void setProcedureId(int procedureId) throws ISPACException
    {
        stagedao.set("ID_PCD",procedureId);
        nodedao.set("ID_PCD",procedureId);

        if (taskdefmap!=null)
            ProcedureUtil.setPcdElementProperty(taskdefmap.values(),new Integer(procedureId),
                null,null);
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
        return flowinlist;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getDestElement()
     */
    public List getDestElement() throws ISPACException
    {
        return flowoutlist;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#addOrigElement(ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement)
     */
    public void addOrigElement(IPcdElement element) throws ISPACException
    {
        flowinlist.add(element);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#addDestElement(ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement)
     */
    public void addDestElement(IPcdElement element) throws ISPACException
    {
        flowoutlist.add(element);
    }

    public String getBPMNodeId() throws ISPACException {
        if (nodedao==null) {
            throw new ISPACException("StageElement - La estructura de la fase no está debidamente construida");
        }
        return nodedao.getString("ID_NODO_BPM");
    }

    public void setBPMNodeId(String bpmNodeId) throws ISPACException {
        if (nodedao==null) {
            throw new ISPACException("StageElement - La estructura de la fase no está debidamente construida");
        }
        nodedao.set("ID_NODO_BPM", bpmNodeId);
    }

    public GInfo getGInfo() throws ISPACException {
        if (nodedao==null) {
            throw new ISPACException("StageElement - La estructura de la fase no está debidamente construida");
        }
        return GInfo.parse(nodedao.getString("G_INFO"));
    }

    public void setGInfo(GInfo ginfo) throws ISPACException {
        if (nodedao==null) {
            throw new ISPACException("StageElement - La estructura de la fase no está debidamente construida");
        }
        nodedao.set("G_INFO", (ginfo != null ? ginfo.toXml() : (String)null));
    }

    public ProcessNode getProcessModelNode() throws ISPACException {

    	ProcessNode stage = new ProcessNode();

    	stage.setId(getBPMNodeId());
    	stage.setSpacNodeId(getId());
    	stage.setName(getName());
    	stage.setRespId(getResponsibleId());
    	stage.setType(ProcessNode.NODE_TYPE_STAGE);

		//TODO Subprocesos
		//stage.addSubProcess(subProcess);

    	return stage;
    }

    public String toXml() throws ISPACException
    {
        String sXml = null;

        StringBuffer buffer = new StringBuffer();

        buffer.append(XmlTag.newTag("name",stagedao.getString("NOMBRE")));
        buffer.append(XmlTag.newTag("idctstage",stagedao.getString("ID_CTFASE")));
        buffer.append(XmlTag.newTag("idresp",stagedao.getString("ID_RESP")));

        //El campo G_INFO contiene la información gráfica (posición) en formato xml.
        buffer.append(StringUtils.nullToEmpty(nodedao.getString("G_INFO")));

        // Trámites de la fase
        buffer.append(XmlTag.newTag("tasks",
        		elementsToXml(taskdefmap.values().iterator())));

        sXml=XmlTag.newTag("stage",buffer.toString(),
                XmlTag.newAttr("id",Integer.toString(getId())));

        return sXml;
    }

    private String elementsToXml(Iterator it) throws ISPACException {
        StringBuffer buffer = new StringBuffer();
        while (it.hasNext()) {
            IPcdElement el = (IPcdElement) it.next();
            buffer.append(el.toXml());
        }
        return buffer.toString();
    }

    public String toXpdl(DbCnt cnt,
    					 Map ctStageIds,
			  			 Map ctTaskIds,
			  			 Map ctRuleIds,
			  			 Map ctEntityIds,
			  			 Map ctTpDocIds,
			  			 Map subPcdIds) throws ISPACException {

    	// Fase en el catálogo
    	if (getCatalogId() != ISPACEntities.ENTITY_NULLREGKEYID) {

    		Integer idCatalog = new Integer(getCatalogId());
	        if (!ctStageIds.containsKey(idCatalog)) {
	        	ctStageIds.put(idCatalog, null);
	        }
    	}

        String sXpdl = null;
        StringBuffer buffer =  new StringBuffer();

        buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_IMPLEMENTATION, XmlTag.newTag(ExportProcedureMgr.TAG_NO, null, null), null));

        // Transiciones
        StringBuffer transitionRestrictions = new StringBuffer();

        // Con varios flujos de entrada hay que generar <TransitionRestriction> con Join de tipo XOR
        if (flowinlist.size() > 1) {

            String join = XmlTag.newTag(ExportProcedureMgr.TAG_JOIN, null, XmlTag.newAttr(ExportProcedureMgr.ATR_TYPE, ExportProcedureMgr.VAL_XOR));
            transitionRestrictions.append(XmlTag.newTag(ExportProcedureMgr.TAG_TRANSITION_RESTRICTION, join, null));
        }

        // Con varios flujos de salida hay que generar <TransitionRestriction> con Split de tipo AND
        if (flowoutlist.size() > 1) {

	        StringBuffer transitionRefs = new StringBuffer();
	        Iterator it = flowoutlist.iterator();
	        while (it.hasNext()) {

	        	FlowElement flowOut = (FlowElement) it.next();
	        	transitionRefs.append(XmlTag.newTag(ExportProcedureMgr.TAG_TRANSITION_REF, null, XmlTag.newAttr(ExportProcedureMgr.ATR_ID, String.valueOf(flowOut.getId()))));
	        }

	        String split = XmlTag.newTag(ExportProcedureMgr.TAG_SPLIT, XmlTag.newTag(ExportProcedureMgr.TAG_TRANSITION_REFS, transitionRefs.toString(), null), XmlTag.newAttr(ExportProcedureMgr.ATR_TYPE, ExportProcedureMgr.VAL_AND));
	        transitionRestrictions.append(XmlTag.newTag(ExportProcedureMgr.TAG_TRANSITION_RESTRICTION, split, null));
        }

        if (transitionRestrictions.length() > 0) {
        	buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_TRANSITION_RESTRICTIONS, transitionRestrictions.toString(), null));
        }

        // Información gráfica
        GInfo gInfo = nodedao.getGInfo();
        if (gInfo != null) {
        	buffer.append(ExportProcedureMgr.getXpdlNodeGraphicsInfo(gInfo));
        }

        // Eventos
        buffer.append(ExportProcedureMgr.eventsToXml(eventlist, ctRuleIds));

        // Formularios asociados a entidades
        buffer.append(ExportProcedureMgr.formsToXml(stageapplist, ctEntityIds, ctRuleIds));

        // Tipos documentales
        buffer.append(ExportProcedureMgr.tpDocsToXml(tpdoclist, ctTpDocIds));

        // Trámites asociados a la fase
        buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_TASKS, ExportProcedureMgr.elementsToXpdl(cnt, taskdefmap.values().iterator(), ctStageIds, ctTaskIds, ctRuleIds, ctEntityIds, ctTpDocIds, subPcdIds)));

        StringBuffer attributes = new StringBuffer();
        attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID, String.valueOf(getId())))
        		  .append(XmlTag.newAttr(ExportProcedureMgr.ATR_NAME, ExportProcedureMgr.exportName(stagedao.getString("NOMBRE"))))
        		  .append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID_CATALOGO, stagedao.getString("ID_CTFASE")));

        sXpdl = XmlTag.newTag(ExportProcedureMgr.TAG_ACTIVITY, buffer.toString(), attributes.toString());
        return sXpdl;
    }


	public String toXpdl(DbCnt cnt, Map ctStageIds, Map ctTaskIds,
			Map ctRuleIds, Map ctEntityIds, Map ctTpDocIds, Map subPcdIds,
			Map ctHelpsIds) throws ISPACException {
		return toXpdl(cnt, ctStageIds, ctTaskIds, ctRuleIds, ctEntityIds, ctTpDocIds, subPcdIds);
	}

}
