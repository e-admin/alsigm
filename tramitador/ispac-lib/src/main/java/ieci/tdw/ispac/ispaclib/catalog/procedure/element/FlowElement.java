package ieci.tdw.ispac.ispaclib.catalog.procedure.element;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.bpm.ProcessFlow;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ExportProcedureMgr;
import ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ProcedureUtil;
import ieci.tdw.ispac.ispaclib.dao.procedure.PEventoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFlujoDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.util.transition.ITransition;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TaskElement
 *
 */
public class FlowElement implements IPcdElement,ITransition
{

    private PFlujoDAO flujodao;
    private List eventlist;

    private IPcdElement nodedest;
    private IPcdElement nodeorig;


    public FlowElement(PFlujoDAO flujodao)
    throws ISPACException
    {
        this.flujodao=flujodao;
        this.eventlist=null;
        this.nodedest=null;
        this.nodeorig=null;
    }

    private FlowElement(PFlujoDAO flujodao,List eventlist)
    throws ISPACException
    {
        this.flujodao=flujodao;
        this.eventlist=eventlist;
        this.nodedest=null;
        this.nodeorig=null;
    }


    public void load(DbCnt cnt) throws ISPACException
    {
        eventlist=PEventoDAO.getEvents(cnt,EventsDefines.EVENT_OBJ_FLOW,getId()).toList();
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#delete(ieci.tdw.ispac.ispaclib.db.DbCnt)
     */
    public void delete(DbCnt cnt) throws ISPACException
    {
        flujodao.delete(cnt);
        ProcedureUtil.delete(cnt,eventlist);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#getId()
     */
    public int getId()
    throws ISPACException
    {
        if (flujodao==null)
            throw new ISPACException("FlowElement - La estructura del flujo no está debidamente construida");

        return flujodao.getKeyInt();
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#getIntegerId()
     */
    public Integer getIntegerId()
    throws ISPACException
    {
        if (flujodao==null)
            throw new ISPACException("FlowElement - La estructura del flujo no está debidamente construida");

        return flujodao.getKeyInteger();
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getCatalogId()
     */
    public int getCatalogId() throws ISPACException
    {
       throw new ISPACException("FlowElement - La estructura del flujo no está debidamente construida");
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getCatalogIntegerId()
     */
    public Integer getCatalogIntegerId() throws ISPACException
    {
       throw new ISPACException("FlowElement - La estructura del flujo no está debidamente construida");
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#duplicate(ieci.tdw.ispac.ispaclib.db.DbCnt)
     */
    public IPcdElement duplicate(DbCnt cnt) throws ISPACException
    {
        PFlujoDAO newflujodao=(PFlujoDAO)flujodao.duplicate(cnt);
        List neweventlist=ProcedureUtil.duplicate(cnt,eventlist);
        ProcedureUtil.setProperty(neweventlist,"ID_OBJ",newflujodao.getKey());

        return new FlowElement(newflujodao,neweventlist);
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#store(ieci.tdw.ispac.ispaclib.db.DbCnt)
     */
    public void store(DbCnt cnt) throws ISPACException
    {
        flujodao.store(cnt);
        ProcedureUtil.store(cnt,eventlist);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#setProcedureId(ieci.tdw.ispac.ispaclib.db.DbCnt, int)
     */
    public void setProcedureId(int procedureId) throws ISPACException
    {
        if (flujodao!=null)
            flujodao.set("ID_PCD",procedureId);
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
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#setStagePcdId(ieci.tdw.ispac.ispaclib.db.DbCnt, int)
     */
    public Integer getOrigId() throws ISPACException
    {
        if (flujodao==null)
            throw new ISPACException("FlowElement - La estructura del flujo no está debidamente construida");
        return new Integer(flujodao.getInt("ID_ORIGEN"));
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#setTaskPcdId(ieci.tdw.ispac.ispaclib.db.DbCnt, int)
     */
    public Integer getDestId() throws ISPACException
    {
        if (flujodao==null)
            throw new ISPACException("FlowElement - La estructura del flujo no está debidamente construida");
        return new Integer(flujodao.getInt("ID_DESTINO"));
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#setStagePcdId(ieci.tdw.ispac.ispaclib.db.DbCnt, int)
     */
    public void setOrigId(Integer nodeid) throws ISPACException
    {
        if (flujodao==null)
            throw new ISPACException("FlowElement - La estructura del flujo no está debidamente construida");

        flujodao.set("ID_ORIGEN",nodeid.intValue());
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#setTaskPcdId(ieci.tdw.ispac.ispaclib.db.DbCnt, int)
     */
    public void setDestId(Integer nodeid) throws ISPACException
    {
        if (flujodao==null)
            throw new ISPACException("FlowElement - La estructura del flujo no está debidamente construida");

        flujodao.set("ID_DESTINO",nodeid.intValue());
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getOrigElement()
     */
    public List getOrigElement() throws ISPACException
    {
        List nodelist=new ArrayList();
        nodelist.add(nodeorig);
        return nodelist;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getDestElement()
     */
    public List getDestElement() throws ISPACException
    {
        List nodelist=new ArrayList();
        nodelist.add(nodedest);
        return nodelist;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#addOrigElement(ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement)
     */
    public void addOrigElement(IPcdElement element) throws ISPACException
    {
        this.nodeorig=element;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#addDestElement(ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement)
     */
    public void addDestElement(IPcdElement element) throws ISPACException
    {
        this.nodedest=element;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.util.transition.ITransition#origin()
     */
    public Object origin()
    {
        return nodeorig;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.util.transition.ITransition#destination()
     */
    public Object destination()
    {
        return nodedest;
    }

    public String getCondition() throws ISPACException {
    	
    	String condition = null;
    	
    	if ((eventlist != null) && !eventlist.isEmpty()) {
    		IItem event = (IItem) eventlist.get(0);
			condition = event.getString("CONDICION");
    	}
    	
        return condition;
    }

    public String getBPMFlowId() throws ISPACException {
        if (flujodao==null) {
            throw new ISPACException("FlowElement - La estructura del flujo no está debidamente construida");
        }
        return flujodao.getString("ID_FLUJO_BPM");
    }

    public void setBPMFlowId(String bpmFlowId) throws ISPACException {
        if (flujodao==null) {
            throw new ISPACException("FlowElement - La estructura del flujo no está debidamente construida");
        }
        flujodao.set("ID_FLUJO_BPM", bpmFlowId);
    }

    public ProcessFlow getProcessModelFlow() throws ISPACException {
    	
    	ProcessFlow flow = new ProcessFlow();

    	flow.setId(getBPMFlowId());
    	flow.setSpacFlowId(getId());
    	
    	if (nodeorig != null) {
    		if (nodeorig instanceof StageElement) {
    			flow.setStartNodeId(((StageElement)nodeorig).getBPMNodeId());
    		} else if (nodeorig instanceof SyncNodeElement) {
    			flow.setStartNodeId(((SyncNodeElement)nodeorig).getBPMNodeId());
    		}
    	}
		
    	if (nodedest != null) {
    		if (nodedest instanceof StageElement) {
    			flow.setEndNodeId(((StageElement)nodedest).getBPMNodeId());
    		} else if (nodedest instanceof SyncNodeElement) {
    			flow.setEndNodeId(((SyncNodeElement)nodedest).getBPMNodeId());
    		}
    	}

    	return flow;
    }

    public String toXml() throws ISPACException
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append(XmlTag.newAttr("id", Integer.toString(getId())));
        buffer.append(XmlTag.newAttr("orig", getOrigId().toString()));
        buffer.append(XmlTag.newAttr("dest", getDestId().toString()));

        return XmlTag.newTag("flow", eventsToXml(), buffer.toString());
    }
    
    private String eventsToXml() throws ISPACException {
    	
        StringBuffer eventsXml = new StringBuffer();
        
        if (!CollectionUtils.isEmpty(eventlist)) {
        	
        	for (int i = 0; i < eventlist.size(); i++) {
        		
        		IItem event = (IItem) eventlist.get(i);

                StringBuffer eventXml = new StringBuffer();
                eventXml.append(XmlTag.newTag("code", event.getInt("EVENTO")));
                eventXml.append(XmlTag.newTag("order", event.getInt("ORDEN")));
                eventXml.append(XmlTag.newTag("ruleId", event.getInt("ID_REGLA")));
                
                eventXml.append(XmlTag.newTag("condition", 
                		StringUtils.escapeXml(event.getString("CONDICION")), true));
                
        		eventsXml.append(XmlTag.newTag("event", eventXml.toString()));
        	}
        }
        
        return XmlTag.newTag("events", eventsXml);
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
        
        StringBuffer attributes = new StringBuffer();
        attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID, Integer.toString(getId())))
        		  .append(XmlTag.newAttr(ExportProcedureMgr.ATR_NAME, ""))
        		  .append(XmlTag.newAttr(ExportProcedureMgr.ATR_FROM, getOrigId().toString()))
        		  .append(XmlTag.newAttr(ExportProcedureMgr.ATR_TO, getDestId().toString()));
        
        // Eventos
        buffer.append(ExportProcedureMgr.eventsToXml(eventlist, ctRuleIds));
        
        sXpdl = XmlTag.newTag(ExportProcedureMgr.TAG_TRANSITION, buffer.toString(), attributes.toString());
        return sXpdl;
    }

	public String toXpdl(DbCnt cnt, Map ctStageIds, Map ctTaskIds,
			Map ctRuleIds, Map ctEntityIds, Map ctTpDocIds, Map subPcdIds,
			Map ctHelpsIds) throws ISPACException {
		return toXpdl(cnt, ctStageIds, ctTaskIds, ctRuleIds, ctEntityIds, ctTpDocIds, subPcdIds);
	}
    
}
