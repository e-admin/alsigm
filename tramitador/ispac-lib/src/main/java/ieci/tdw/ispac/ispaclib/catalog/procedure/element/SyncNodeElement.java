/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispaclib/src/ieci/tdw/ispac/ispaclib/catalog/procedure/element/SyncNodeElement.java,v $
 * $Revision: 1.14 $
 * $Date: 2008/07/08 09:34:00 $
 * $Author: ildefong $
 *
 */
package ieci.tdw.ispac.ispaclib.catalog.procedure.element;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.graph.GInfo;
import ieci.tdw.ispac.ispaclib.bpm.ProcessNode;
import ieci.tdw.ispac.ispaclib.bpm.ProcessSyncNode;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ExportProcedureMgr;
import ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement;
import ieci.tdw.ispac.ispaclib.dao.procedure.PNodoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PSincNodoDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * SyncNodeElement
 *
 *
 * @version $Revision: 1.14 $ $Date: 2008/07/08 09:34:00 $
 */
public class SyncNodeElement implements IPcdElement
{
    public static final int SYNCNODE_AND                = 1;
    public static final int SYNCNODE_OR                 = 2;
    public static final int SYNCNODE_CUSTOM             = 3;

    private PSincNodoDAO syncnodedao;
    private PNodoDAO nodedao;

    private List flowinlist;
    private List flowoutlist;

    public SyncNodeElement(PSincNodoDAO syncnodedao,PNodoDAO nodedao)
    throws ISPACException
    {
        if (syncnodedao.getKeyInt()!=nodedao.getKeyInt())
            throw new ISPACException("Error SyncNodeDef - El  identificador de nodo no corresponde con el nodo de sincronización.");

        this.syncnodedao=syncnodedao;
        this.nodedao=nodedao;
        flowinlist=new ArrayList();
        flowoutlist=new ArrayList();
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#getId()
     */
    public int getId() throws ISPACException
    {
        if (syncnodedao==null)
            throw new ISPACException("SyncNodeDef - La estructura del nodo de sincronización no está debidamente construida");
        return syncnodedao.getKeyInt();
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#getIntegerId()
     */
    public Integer getIntegerId() throws ISPACException
    {
        if (syncnodedao==null)
            throw new ISPACException("SyncNodeDef - La estructura del nodo de sincronización no está debidamente construida");
        return syncnodedao.getKeyInteger();
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getCatalogId()
     */
    public int getCatalogId() throws ISPACException
    {
        return 0;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getCatalogIntegerId()
     */
    public Integer getCatalogIntegerId() throws ISPACException
    {
        return null;
    }

    public void load(DbCnt cnt) throws ISPACException
    {

    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#delete(ieci.tdw.ispac.ispaclib.db.DbCnt)
     */
    public void delete(DbCnt cnt) throws ISPACException
    {
        syncnodedao.delete(cnt);
        nodedao.delete(cnt);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#duplicate(ieci.tdw.ispac.ispaclib.db.DbCnt)
     */
    public IPcdElement duplicate(DbCnt cnt) throws ISPACException
    {
        PSincNodoDAO newsyncnodedao=(PSincNodoDAO)syncnodedao.duplicate(cnt);
        PNodoDAO newnodedao=(PNodoDAO)nodedao.duplicate(cnt);
        newnodedao.setKey(newsyncnodedao.getKey());
        return new SyncNodeElement(newsyncnodedao,newnodedao);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#store(ieci.tdw.ispac.ispaclib.db.DbCnt)
     */
    public void store(DbCnt cnt) throws ISPACException
    {
        syncnodedao.store(cnt);
        nodedao.store(cnt);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#setProcedureId(ieci.tdw.ispac.ispaclib.db.DbCnt, int)
     */
    public void setProcedureId(int procedureId) throws ISPACException
    {
        syncnodedao.set("ID_PCD",procedureId);
        nodedao.set("ID_PCD",procedureId);
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
        	throw new ISPACException("SyncNodeDef - La estructura del nodo de sincronización no está debidamente construida");
        }
        return nodedao.getString("ID_NODO_BPM");
    }

    public void setBPMNodeId(String bpmNodeId) throws ISPACException {
        if (nodedao==null) {
        	throw new ISPACException("SyncNodeDef - La estructura del nodo de sincronización no está debidamente construida");
        }
        nodedao.set("ID_NODO_BPM", bpmNodeId);
    }

    public GInfo getGInfo() throws ISPACException {
        if (nodedao==null) {
        	throw new ISPACException("SyncNodeDef - La estructura del nodo de sincronización no está debidamente construida");
        }
        return GInfo.parse(nodedao.getString("G_INFO"));
    }

    public void setGInfo(GInfo ginfo) throws ISPACException {
        if (nodedao==null) {
        	throw new ISPACException("SyncNodeDef - La estructura del nodo de sincronización no está debidamente construida");
        }
        nodedao.set("G_INFO", (ginfo != null ? ginfo.toXml() : (String)null));
    }

    public ProcessNode getProcessModelNode() throws ISPACException {
    	
    	ProcessSyncNode syncNode = new ProcessSyncNode();

    	syncNode.setId(getBPMNodeId());
    	syncNode.setSpacNodeId(getId());
    	syncNode.setName(getBPMNodeId());
    	syncNode.setType(ProcessNode.NODE_TYPE_SYNCNODE);
    	syncNode.setSyncNodeType(syncnodedao.getInt("TIPO"));

    	return syncNode;
    }

    public String toXml() throws ISPACException
    {
        String sXml = null;
        StringBuffer buffer = new StringBuffer();

        int type=syncnodedao.getInt("TIPO");
        String name="";
        switch(type)
        {
        case SyncNodeElement.SYNCNODE_AND:
            name="Nodo AND";
            break;
        case SyncNodeElement.SYNCNODE_OR:
            name="Nodo OR";
            break;
        case SyncNodeElement.SYNCNODE_CUSTOM:
            name="Nodo evento";
            break;
        }

        buffer.append(XmlTag.newTag("name",name));
        buffer.append(XmlTag.newTag("type",type));
        
        //El campo G_INFO contiene la información gráfica (posición) en formato xml.
        buffer.append(StringUtils.nullToEmpty(nodedao.getString("G_INFO")));

        sXml=XmlTag.newTag("syncnode",buffer.toString(),
                XmlTag.newAttr("id",Integer.toString(getId())));
        return sXml;
    }
    
    public String toXpdl(DbCnt cnt,
    					 Map ctStageIds,
			  			 Map ctTaskIds,
			  			 Map ctRuleIds,
			  			 Map ctEntityIds,
			  			 Map ctTpDocIds,
			  			 Map subPcdIds) throws ISPACException {
    	
        String sXpdl = null;
        StringBuffer buffer = new StringBuffer();
        
        int tipo = syncnodedao.getInt("TIPO");
        String type = ExportProcedureMgr.VAL_XOR;
        switch(tipo)
        {
        case SyncNodeElement.SYNCNODE_AND:
        	type = ExportProcedureMgr.VAL_AND;
            break;
        /*
        case SyncNodeElement.SYNCNODE_OR:
        	type = "OR";
            break;
        */
        }
        
        String join = XmlTag.newTag(ExportProcedureMgr.TAG_JOIN, null, XmlTag.newAttr(ExportProcedureMgr.ATR_TYPE, type));
        String transitionRestriction = XmlTag.newTag(ExportProcedureMgr.TAG_TRANSITION_RESTRICTION, join, null);

        // Transiciones
        buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_ROUTE, null, XmlTag.newAttr(ExportProcedureMgr.ATR_GATEWAY_TYPE, type)));
        buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_TRANSITION_RESTRICTIONS, transitionRestriction, null));
        
        // Información gráfica
        GInfo gInfo = nodedao.getGInfo();
        if (gInfo != null) {
        	buffer.append(ExportProcedureMgr.getXpdlNodeGraphicsInfo(gInfo));
        }

        StringBuffer attributes = new StringBuffer();
        attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID, Integer.toString(getId())))
        		  .append(XmlTag.newAttr(ExportProcedureMgr.ATR_NAME, ""));
        		
        sXpdl = XmlTag.newTag(ExportProcedureMgr.TAG_ACTIVITY, buffer.toString(), attributes.toString());
        return sXpdl;
    }

	public String toXpdl(DbCnt cnt, Map ctStageIds, Map ctTaskIds,
			Map ctRuleIds, Map ctEntityIds, Map ctTpDocIds, Map subPcdIds,
			Map ctHelpsIds) throws ISPACException {
		
		return toXpdl(cnt, ctStageIds, ctTaskIds, ctRuleIds, ctEntityIds, ctTpDocIds, subPcdIds);
	}
	

}
