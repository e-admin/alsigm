package ieci.tdw.ispac.ispaclib.catalog.procedure;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.graph.GInfo;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.FlowElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.StageElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.SyncNodeElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.TaskElement;
import ieci.tdw.ispac.ispaclib.dao.procedure.PProcedimientoDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.util.transition.RelationComparator;
import ieci.tdw.ispac.ispaclib.util.transition.TransitionTable;
import ieci.tdw.ispac.ispaclib.utils.MapUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * FlowNodeMgr
 *
 */
public class FlowNodeMgr
{
    //-------------------------------------
    //Los nodos de sincronización del procedimiento.
    private Map nodedefmap;

    //Mapa de fases del procedimiento exclusivamente.
    private Map stagedefmap;

    private Map nodemap;

    //Los flujos existentes en el procedimiento.
    private Map flowmap;

    public FlowNodeMgr()
    {
        this.nodedefmap = new LinkedHashMap();
        this.stagedefmap = new LinkedHashMap();
        this.flowmap = new LinkedHashMap();
        this.nodemap = new LinkedHashMap();
    }

    private FlowNodeMgr(Map nodedefmap, Map stagedefmap, Map flowmap)
            throws ISPACException
    {
        this.nodedefmap = nodedefmap;
        this.stagedefmap = stagedefmap;
        this.flowmap = flowmap;

        nodemap = new LinkedHashMap();
        nodemap.putAll(stagedefmap);
        nodemap.putAll(nodedefmap);

        buildGraph();
    }

    public boolean hasInitialStage() {

    	if (MapUtils.isEmpty(stagedefmap)) {
    		return false;
    	}
    	
    	// Conjunto de identificadores de fases/actividades
    	Set stageIdSet = stagedefmap.keySet();
    	
    	// Eliminar del conjunto los nodos enlazados
    	if (MapUtils.isNotEmpty(flowmap)) {
    		Iterator flowsIt = flowmap.values().iterator();
    		while (flowsIt.hasNext()) {
    			try {
	    			FlowElement flow = (FlowElement) flowsIt.next();
	    			stageIdSet.remove(flow.getDestId());
    			} catch (ISPACException e) {}
    		}
    	}
    	
    	return (stageIdSet.size() == 1);
    }
    
    public boolean hasIsolatedNodes() {
    	
    	if (MapUtils.isEmpty(nodemap) || (nodemap.size() == 1)) {
    		return false;
    	}
    	
    	// Conjunto de identificadores de nodos 
    	Set nodeIdSet = nodemap.keySet();
    	
    	// Eliminar del conjunto los nodos enlazados
    	if (MapUtils.isNotEmpty(flowmap)) {
    		Iterator flowsIt = flowmap.values().iterator();
    		while (flowsIt.hasNext()) {
    			try {
	    			FlowElement flow = (FlowElement) flowsIt.next();
	    			nodeIdSet.remove(flow.getOrigId());
	    			nodeIdSet.remove(flow.getDestId());
    			} catch (ISPACException e) {}
    		}
    	}
    	
    	return !nodeIdSet.isEmpty();
    }
    
    public Map getStages() {
        return stagedefmap;
    }

    public StageElement getStage(int stageId) {
        return (StageElement) stagedefmap.get(new Integer(stageId));
    }

    public Map getSyncNodes() {
        return nodedefmap;
    }

    public SyncNodeElement getSyncNode(int syncNodeId) {
        return (SyncNodeElement) nodedefmap.get(new Integer(syncNodeId));
    }

    public Map getFlows() {
        return flowmap;
    }

    public FlowElement getFlow(int flowId) {
        return (FlowElement) flowmap.get(new Integer(flowId));
    }

    public void load(DbCnt cnt, PProcedimientoDAO procdao)
            throws ISPACException
    {
        Map pcdnodesmap = procdao.getNodes(cnt).toMap();

        PcdElementBuilder objdefbuilder = new PcdElementBuilder();
        nodedefmap = objdefbuilder.buildSyncNodeElement(cnt, procdao
                .getSyncNodes(cnt), pcdnodesmap);
        stagedefmap = objdefbuilder.buildStageElement(cnt, procdao
                .getStages(cnt), pcdnodesmap);

        flowmap = objdefbuilder.buildFlowElement(cnt, procdao.getFlows(cnt));

        nodemap.clear();
        nodemap.putAll(stagedefmap);
        nodemap.putAll(nodedefmap);

        buildGraph();
    }

    public void buildGraph() throws ISPACException
    {
        Iterator it = flowmap.values().iterator();
        while (it.hasNext())
        {
            IPcdElement flow = (FlowElement) it.next();

            IPcdElement nodeorig = (IPcdElement) nodemap.get(flow.getOrigId());
            IPcdElement nodedest = (IPcdElement) nodemap.get(flow.getDestId());

            if (nodeorig==null || nodedest==null)
                throw new ISPACException("buildGraph() - El flujo con id ["+flow.getId()+"] está mal definido."+
                        "La fase origen o destino no existe");

            nodeorig.addDestElement(flow);
            nodedest.addOrigElement(flow);

            flow.addOrigElement(nodeorig);
            flow.addDestElement(nodedest);
        }
    }

    public FlowNodeMgr duplicate(DbCnt cnt, Integer newpcdId, List ctstages, Map ctstagestask)
            throws ISPACException
    {
        Map oldidxnewnodes = new LinkedHashMap();

        // Duplicar las fases / actividades con el orden establecido e
        // incluyendo las que se han añadido nuevas
        Map newstagedefmap = ProcedureUtil.duplicateStageElement(cnt, stagedefmap, oldidxnewnodes, ctstages, ctstagestask);
        
        // Duplicar los nodos de sincronización
        Map newnodedefmap = ProcedureUtil.duplicatePcdElement(cnt, nodedefmap, oldidxnewnodes);

        // Duplicar los flujos de los nodos del procedimiento / subproceso original
        Map newflowmap = duplicateFlows(cnt, oldidxnewnodes);

        // Establecer el id de procedimiento / subproceso en los elementos generados
        ProcedureUtil.setPcdElementProperty(newstagedefmap, newpcdId, null, null);
        ProcedureUtil.setPcdElementProperty(newnodedefmap, newpcdId, null, null);
        ProcedureUtil.setPcdElementProperty(newflowmap, newpcdId, null, null);

        return new FlowNodeMgr(newnodedefmap, newstagedefmap, newflowmap);
    }

    public Map duplicateFlows(DbCnt cnt, Map newnodes) throws ISPACException
    {
        LinkedHashMap newflowmap = new LinkedHashMap();

        Iterator it = flowmap.values().iterator();
        while (it.hasNext())
        {
            IPcdElement flowdef = (IPcdElement) it.next();
            IPcdElement dupdef = flowdef.duplicate(cnt);

            IPcdElement nodeorig = (IPcdElement) newnodes.get(flowdef.getOrigId());
            IPcdElement nodedest = (IPcdElement) newnodes.get(flowdef.getDestId());

            if (nodeorig==null || nodedest==null)
                throw new ISPACException("duplicateFlows() - El flujo con id ["+flowdef.getId()+"] está mal definido."+
                        "La fase origen o destino no existe");

            dupdef.setOrigId(nodeorig.getIntegerId());
            dupdef.setDestId(nodedest.getIntegerId());

            newflowmap.put(dupdef.getIntegerId(), dupdef);
        }
        return newflowmap;
    }

    /**
     * @param cnt
     */
    public void store(DbCnt cnt) throws ISPACException
    {
        ProcedureUtil.storePcdElement(cnt, stagedefmap);
        ProcedureUtil.storePcdElement(cnt, nodedefmap);
        ProcedureUtil.storePcdElement(cnt, flowmap);
    }

    /**
     * @param cnt
     */
    public void delete(DbCnt cnt) throws ISPACException
    {
        ProcedureUtil.deletePcdElement(cnt, flowmap);
        ProcedureUtil.deletePcdElement(cnt, stagedefmap);
        ProcedureUtil.deletePcdElement(cnt, nodedefmap);

        flowmap.clear();
        nodedefmap.clear();
        stagedefmap.clear();
    }

    public void modify(DbCnt cnt, Integer pcdId, List ctstages,
            Map cttaskidmap) throws ISPACException {
    	
        Map stagectindexmap = ProcedureUtil.indexPcdElementByCatalogId(stagedefmap);

        LinkedHashMap newpcdstagemap = new LinkedHashMap();

        for (int i = 0; i < ctstages.size(); i++) {
        	
            IItem ctstage = (IItem) ctstages.get(i);
            Integer ctStageId = ctstage.getKeyInteger();
            StageElement stage = null;
            
            if (ctStageId.equals(new Integer(-1))) {
            	String stageName = ctstage.getString("NOMBRE");
            	stage = findStageByName(stageName);
	            if (stage != null) {
	            	newpcdstagemap.put(stage.getIntegerId(), stage);
	            }
            } 
            else {
	            stage = (StageElement) stagectindexmap.get(ctStageId);
	            if (stage != null) {
	            	newpcdstagemap.put(stage.getIntegerId(), stage);
	            }
            }
        }
        
        // Eliminar las fases que no se han seleccionado del procedimiento original
        // para que se reajusten los flujos
        Set keySet = new HashSet(stagedefmap.keySet());
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
        	
        	Integer stageId = (Integer) it.next();
        	if (!newpcdstagemap.containsKey(stageId)) {
        		
        		removeStage(cnt, pcdId.intValue(), stageId.intValue());
        	}
        }
        
    	/*
        Map stagectindexmap = ProcedureUtil.indexPcdElementByCatalogId(stagedefmap);

        PcdElementFactory pcdfactory = new PcdElementFactory();
        LinkedHashMap newstagemap = new LinkedHashMap();

        for (int i = 0; i < ctstages.size(); i++) {
        	
            IItem ctstage = (IItem) ctstages.get(i);
            Integer ctStageId = ctstage.getKeyInteger();
            StageElement stage = null;
            
            if (ctStageId.equals(new Integer(-1))) {
            	String stageName = ctstage.getString("NOMBRE");
            	stage = findStageByName(stageName);
	            if (stage == null) {
	            	stage = pcdfactory.createStage(cnt, 0, -1, stageName, new GInfo());
	            }
            } else {
	            stage = (StageElement) stagectindexmap.get(ctStageId);
	            if (stage != null) {
	                stagedefmap.remove(stage.getIntegerId());
	            } else {
	                stage = pcdfactory.createStage(cnt, ctStageId.intValue(), new GInfo());
	            }
                List cttaskidlist = (List) cttaskidmap.get(ctStageId);
                stage.modify(cnt, cttaskidlist);
            }
            
            newstagemap.put(stage.getIntegerId(), stage);
        }
        setStages(cnt, newstagemap, pcdId);
        */
    }
    
    public void modify(DbCnt cnt, Integer pcdId, List activitiesNames) 
	throws ISPACException {
    	
        LinkedHashMap newsubpcdactivitymap = new LinkedHashMap();

        for (int i = 0; i < activitiesNames.size(); i++) {
        	
        	String activityName = (String) activitiesNames.get(i);
        	
        	StageElement activity = findStageByName(activityName);
            if (activity != null) {
            	
            	newsubpcdactivitymap.put(activity.getIntegerId(), activity);
            }
        }
        
        // Eliminar las actividades que no se han seleccionado del subproceso original
        Iterator it = stagedefmap.keySet().iterator();
        while (it.hasNext()) {
        	
        	Integer stageId = (Integer) it.next();
        	if (!newsubpcdactivitymap.containsKey(stageId)) {
        		
        		removeStage(cnt, pcdId.intValue(), stageId.intValue());
        	}
        }
    	
    	/*
		PcdElementFactory pcdfactory = new PcdElementFactory();
		LinkedHashMap newstagemap = new LinkedHashMap();
		
		if (activitiesNames != null) {
			for (int i = 0; i < activitiesNames.size(); i++) {
				StageElement stage = pcdfactory.createStage(cnt, 0, -1, 
						(String) activitiesNames.get(i), new GInfo());
				newstagemap.put(stage.getIntegerId(), stage);
			}
		}
		
		setStages(cnt, newstagemap, pcdId);
		*/
	}

    protected StageElement findStageByName(String stageName) 
    		throws ISPACException {
    	StageElement stage = null;
    	
    	if (StringUtils.isNotBlank(stageName)) {
	    	Iterator stages = stagedefmap.values().iterator();
	    	while ((stage == null) && stages.hasNext()) {
	    		StageElement aux = (StageElement) stages.next();
	    		if (stageName.equals(aux.getName())) {
	    			stage = aux;
	    		}
	    	}
    	}
    	
    	return stage;
    }

    public StageElement addStage(DbCnt cnt, int pcdId, int ctstageId, 
    		String ctstageName, GInfo ginfo) throws ISPACException {
    	
    	PcdElementFactory pcdfactory = new PcdElementFactory();
    	StageElement stage = pcdfactory.createStage(cnt, pcdId, ctstageId, 
    			ctstageName, ginfo);
    	
    	stagedefmap.put(new Integer(stage.getId()), stage);
    	
    	return stage;
    }

    /*
     * private void deleteStage(DbCnt cnt,IPcdElement stage) throws
     * ISPACException { List flowinlist=stage.getOrigElement(); List
     * flowoutlist=stage.getDestElement();
     *
     * ProcedureUtil.removePcdElement(flowmap,flowinlist);
     * ProcedureUtil.deletePcdElement(cnt,flowinlist);
     *
     * ProcedureUtil.removePcdElement(flowmap,flowoutlist);
     * ProcedureUtil.deletePcdElement(cnt,flowoutlist); }
     */

    public void removeStage(DbCnt cnt, int procedureId, int stageid) 
    		throws ISPACException {
    	
        IPcdElement stage = (IPcdElement) stagedefmap.remove(new Integer(stageid));
        if (stage == null)
            throw new ISPACException("La fase con id " + stageid
                    + " no existe en el procedimiento");

        removeNode(cnt, procedureId, stage);
    }

    private void removeNode(DbCnt cnt, int pcdId, IPcdElement node) throws ISPACException {
    	
        List flowinlist = node.getOrigElement();
        List flowoutlist = node.getDestElement();

        //El nodo a eliminar no es ni el primero ni el último. Se calculan
        // entonces los nuevos flujos.
        if (flowoutlist.size() != 0 && flowinlist.size() != 0) {
            removeNodeFlows(cnt, pcdId, flowinlist, flowoutlist);
        }

        ProcedureUtil.removePcdElement(flowmap, flowinlist);
        ProcedureUtil.deletePcdElement(cnt, flowinlist);

        ProcedureUtil.removePcdElement(flowmap, flowoutlist);
        ProcedureUtil.deletePcdElement(cnt, flowoutlist);

        node.delete(cnt);
    }

    private void removeNodeFlows(DbCnt cnt, int pcdId, List flowinlist, 
    		List flowoutlist) throws ISPACException {
    	
		// Se crea todos los flujos posibles entre los nodos antecesores y los
		// predecesores del eliminado.
		PcdElementFactory pcdfactory = new PcdElementFactory();

		Iterator itout = flowoutlist.iterator();
		while (itout.hasNext()) {
			
			IPcdElement flowout = (IPcdElement) itout.next();

			Iterator itin = flowinlist.iterator();
			while (itin.hasNext()) {
				
				IPcdElement flowin = (IPcdElement) itin.next();

				IPcdElement newflow = pcdfactory.createFlow(cnt);
				newflow.setOrigId(flowin.getOrigId());
				newflow.setDestId(flowout.getDestId());
				newflow.setProcedureId(pcdId);

				flowmap.put(newflow.getIntegerId(), newflow);
				
				// Añadir el nuevo flujo como flujo de salida para el elemento origen
				List origElements = flowin.getOrigElement();
				Iterator itorig = origElements.iterator();
				while (itorig.hasNext()) {
					
					IPcdElement origElement = (IPcdElement) itorig.next();
					// Flujo de salida en el origen
					origElement.addDestElement(newflow);
					
					// Elemento origen para el nuevo flujo
					newflow.addOrigElement(origElement);
					
					// Eliminar el flujo de entrada como flujo de salida en el origen
					origElement.getDestElement().remove(flowin);
				}
				
				// Añadir el nuevo flujo como flujo de entrada para el elemento destino
				List destElements = flowout.getDestElement();
				Iterator itdest = destElements.iterator();
				while (itdest.hasNext()) {
					
					IPcdElement destElement = (IPcdElement) itdest.next();
					// Flujo de entrada en el destino
					destElement.addOrigElement(newflow);
					
					// Elemento destino para el nuevo flujo
					newflow.addDestElement(destElement);
				}
			}
			
			// Eliminar el flujo de salida como flujo de entrada en el destino
			List destElements = flowout.getDestElement();
			Iterator itdest = destElements.iterator();
			while (itdest.hasNext()) {
				
				IPcdElement destElement = (IPcdElement) itdest.next();
				destElement.getOrigElement().remove(flowout);
			}
		}
	}
    
    public void removeStageFlow(DbCnt cnt, int pflowId) throws ISPACException {

    	IPcdElement flow = (IPcdElement) flowmap.remove(new Integer(pflowId));
        if (flow == null)
            throw new ISPACException("El flujo con id " + pflowId
                    + " no existe en el procedimiento");
        flow.delete(cnt);
	}

    public void setStages(DbCnt cnt, Map stages, Integer pcdId)
            throws ISPACException
    {
        ProcedureUtil.deletePcdElement(cnt, stagedefmap);
        ProcedureUtil.deletePcdElement(cnt, nodedefmap);

        stagedefmap.clear();
        nodedefmap.clear();

        stagedefmap = stages;

        createFlows(cnt, pcdId, stagedefmap);

        ProcedureUtil.setPcdElementProperty(stagedefmap, pcdId, null,null);

        nodemap.clear();
        nodemap.putAll(stagedefmap);
        nodemap.putAll(nodedefmap);

        buildGraph();
    }

    public SyncNodeElement addSyncNode(DbCnt cnt, int pcdId, int type, GInfo ginfo) 
    		throws ISPACException {
    	
    	PcdElementFactory pcdfactory = new PcdElementFactory();
    	SyncNodeElement syncNode = pcdfactory.createSyncNode(cnt, pcdId, type, ginfo);
    	
    	nodedefmap.put(new Integer(syncNode.getId()), syncNode);
    	
    	return syncNode;
    }

    public void removeSyncNode(DbCnt cnt, int procedureId, int syncnodeid) 
    		throws ISPACException {
    	
        IPcdElement syncNode = (IPcdElement) nodedefmap.remove(
        		new Integer(syncnodeid));
        if (syncNode == null) {
            throw new ISPACException("El nodo de sincronización con id " 
            		+ syncnodeid + " no existe en el procedimiento");
        }

        removeNode(cnt, procedureId, syncNode);
    }

    public FlowElement createFlow(DbCnt cnt, int pcdId, int origId, int destId) 
    		throws ISPACException {
    	
		PcdElementFactory pcdfactory = new PcdElementFactory();

		FlowElement flow = pcdfactory.createFlow(cnt);
		flow.setOrigId(new Integer(origId));
		flow.addOrigElement((IPcdElement)nodemap.get(flow.getOrigId()));
		flow.setDestId(new Integer(destId));
		flow.addDestElement((IPcdElement)nodemap.get(flow.getDestId()));
		flow.setProcedureId(pcdId);
		
		flowmap.put(flow.getIntegerId(), flow);
		
		return flow;
	}

    private void createFlows(DbCnt cnt, Integer pcdId, Map newstagemap)
            throws ISPACException
    {
        PcdElementFactory pcdfactory = new PcdElementFactory();

        ProcedureUtil.deletePcdElement(cnt, flowmap);
        flowmap.clear();

        IPcdElement prevstage = null;
        IPcdElement nextstage = null;
        Iterator it = newstagemap.values().iterator();
        while (it.hasNext())
        {
            nextstage = (IPcdElement) it.next();
            IPcdElement flow = pcdfactory.createFlow(cnt, prevstage, nextstage);
            if (flow != null)
                flowmap.put(flow.getIntegerId(), flow);
            prevstage = nextstage;
        }

        ProcedureUtil.setPcdElementProperty(flowmap, pcdId, null, null);
    }

    public Map getCTStageTaskIds()
    throws ISPACException
    {
        TransitionTable table=new TransitionTable(flowmap.values());
        RelationComparator relcmp=new RelationComparator(table.closure());

        Set orderedset=new TreeSet(relcmp);
        Iterator it=stagedefmap.values().iterator();
        while (it.hasNext())
        {
            orderedset.add(it.next());
        }

        Map ctstagetaskmap=new LinkedHashMap();
        it=orderedset.iterator();
        while(it.hasNext())
        {
            StageElement stage=(StageElement)it.next();
            Integer ctstageid=stage.getCatalogIntegerId();
            ctstagetaskmap.put(ctstageid,stage.getCTTasksIdList());
        }

        return ctstagetaskmap;
    }


    public TaskElement addTask(DbCnt cnt, int  pstageId, int cttaskId) 
    		throws ISPACException {
    	
        StageElement stage=(StageElement)stagedefmap.get(new Integer(pstageId));
        if (stage==null)
            throw new ISPACException("addTask() - La fase con id["+pstageId+"] no existe el procedimiento indicado.");

        return stage.addTask(cnt, cttaskId);
    }

    public TaskElement getTask(int pstageId, int taskId) throws ISPACException {

    	StageElement stage=(StageElement)stagedefmap.get(new Integer(pstageId));
        if (stage==null)
            throw new ISPACException("addTask() - La fase con id["+pstageId+"] no existe el procedimiento indicado.");

        return (TaskElement) stage.getTask(taskId);
    }

    public void removeTask(DbCnt cnt, int pstageId, int ptaskId)
    throws ISPACException
    {
        StageElement stage=(StageElement)stagedefmap.get(new Integer(pstageId));
        if (stage==null)
            throw new ISPACException("removeTask() - La fase con id["+pstageId+"] no existe el procedimiento indicado.");

        stage.removeTask(cnt,ptaskId);
    }

    public String toXml() throws ISPACException
    {
        String sXml = null;
        StringBuffer buffer = new StringBuffer();

        sXml=elementsToXml(flowmap.values().iterator());
        buffer.append(XmlTag.newTag("flows",sXml));

        sXml=elementsToXml(stagedefmap.values().iterator());
        buffer.append(XmlTag.newTag("stages",sXml));

        sXml=elementsToXml(nodedefmap.values().iterator());
        buffer.append(XmlTag.newTag("syncnodes",sXml));

        return buffer.toString();
    }

    private String elementsToXml(Iterator it) throws ISPACException
    {
        StringBuffer buffer = new StringBuffer();
        while (it.hasNext())
        {
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
			 			 Map subPcdIds) throws ISPACException
    {
        String sXpdl = null;
        StringBuffer buffer = new StringBuffer();

        sXpdl = ExportProcedureMgr.elementsToXpdl(cnt, nodemap.values().iterator(), ctStageIds, ctTaskIds, ctRuleIds, ctEntityIds, ctTpDocIds, subPcdIds);
        buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_ACTIVITIES, sXpdl));
        
        sXpdl = ExportProcedureMgr.elementsToXpdl(cnt, flowmap.values().iterator(), ctStageIds, ctTaskIds, ctRuleIds, ctEntityIds, ctTpDocIds, subPcdIds);
        buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_TRANSITIONS, sXpdl));

        return buffer.toString();
    }
    
}