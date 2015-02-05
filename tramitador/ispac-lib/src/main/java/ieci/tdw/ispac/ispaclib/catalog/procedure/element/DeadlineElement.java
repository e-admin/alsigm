/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispaclib/src/ieci/tdw/ispac/ispaclib/catalog/procedure/element/DeadlineElement.java,v $
 * $Revision: 1.6 $
 * $Date: 2008/04/04 08:33:40 $
 * $Author: ildefong $
 *
 */
package ieci.tdw.ispac.ispaclib.catalog.procedure.element;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement;
import ieci.tdw.ispac.ispaclib.dao.procedure.PPlazoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PRelPlazoDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.util.List;
import java.util.Map;

/**
 * DeadlineElement
 *
 *
 * @version $Revision: 1.6 $ $Date: 2008/04/04 08:33:40 $
 */
public class DeadlineElement implements IPcdElement
{
    private PRelPlazoDAO reldeadlinedao;
    private PPlazoDAO deadlinedao;

    public DeadlineElement(PRelPlazoDAO reldeadlinedao)
    throws ISPACException
    {
        this.reldeadlinedao=reldeadlinedao;
        this.deadlinedao=null;
    }

    private DeadlineElement(PRelPlazoDAO reldeadlinedao,PPlazoDAO deadlinedao)
    {
        this.reldeadlinedao=reldeadlinedao;
        this.deadlinedao=deadlinedao;
    }

    public void load(DbCnt cnt) throws ISPACException
    {
        deadlinedao=new PPlazoDAO(cnt,getId());
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#delete(ieci.tdw.ispac.ispaclib.db.DbCnt)
     */
    public void delete(DbCnt cnt) throws ISPACException
    {
        reldeadlinedao.delete(cnt);
        if (deadlinedao!=null)
            deadlinedao.delete(cnt);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#getId()
     */
    public int getId()
    throws ISPACException
    {
        if (reldeadlinedao==null)
            throw new ISPACException("DeadlineElement - La estructura del plazo no está debidamente construida");

        return reldeadlinedao.getKeyInt();
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#getIntegerId()
     */
    public Integer getIntegerId()
    throws ISPACException
    {
        if (reldeadlinedao==null)
            throw new ISPACException("DeadlineElement - La estructura del plazo no está debidamente construida");

        return reldeadlinedao.getKeyInteger();
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


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#duplicate(ieci.tdw.ispac.ispaclib.db.DbCnt)
     */
    public IPcdElement duplicate(DbCnt cnt) throws ISPACException
    {
        PPlazoDAO newdeadlinedao=(PPlazoDAO)deadlinedao.duplicate(cnt);
        PRelPlazoDAO newreldeadlinedao=(PRelPlazoDAO)reldeadlinedao.duplicate(cnt);
        newreldeadlinedao.setKey(newdeadlinedao.getKeyInt());

        return new DeadlineElement(newreldeadlinedao,newdeadlinedao);
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#store(ieci.tdw.ispac.ispaclib.db.DbCnt)
     */
    public void store(DbCnt cnt) throws ISPACException
    {
        deadlinedao.store(cnt);
        reldeadlinedao.store(cnt);
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#setProcedureId(ieci.tdw.ispac.ispaclib.db.DbCnt, int)
     */
    public void setProcedureId(int procedureId) throws ISPACException
    {
        if (reldeadlinedao.getInt("TP_OBJ")==PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE)
            reldeadlinedao.set("ID_OBJ",procedureId);
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#setStagePcdId(ieci.tdw.ispac.ispaclib.db.DbCnt, int)
     */
    public void setStagePcdId(int stagePcdId) throws ISPACException
    {
        if (reldeadlinedao.getInt("TP_OBJ")==PRelPlazoDAO.DEADLINE_OBJ_STAGE)
            reldeadlinedao.set("ID_OBJ",stagePcdId);
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#setTaskPcdId(ieci.tdw.ispac.ispaclib.db.DbCnt, int)
     */
    public void setTaskPcdId(int taskPcdId) throws ISPACException
    {
        if (reldeadlinedao.getInt("TP_OBJ")==PRelPlazoDAO.DEADLINE_OBJ_TASK)
            reldeadlinedao.set("ID_OBJ",taskPcdId);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getOrigId()
     */
    public Integer getOrigId() throws ISPACException
    {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getDestId()
     */
    public Integer getDestId() throws ISPACException
    {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#setOrigId(java.lang.Integer)
     */
    public void setOrigId(Integer elementId) throws ISPACException
    {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#setDestId(java.lang.Integer)
     */
    public void setDestId(Integer elementId) throws ISPACException
    {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getOrigElement()
     */
    public List getOrigElement() throws ISPACException
    {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getDestElement()
     */
    public List getDestElement() throws ISPACException
    {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#addOrigElement(ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement)
     */
    public void addOrigElement(IPcdElement element) throws ISPACException
    {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#addDestElement(ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement)
     */
    public void addDestElement(IPcdElement element) throws ISPACException
    {
        // TODO Auto-generated method stub

    }

    public String toXml() throws ISPACException
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    public String toXpdl(DbCnt cnt,
    					 Map ctStageIds,
 			 			 Map ctTaskIds,
 			 			 Map ctRuleIds,
 			 			 Map ctEntityIds,
 			 			 Map ctTpDocIds,
 			 			 Map subPcdIds) throws ISPACException
    {
        // TODO Auto-generated method stub
        return null;
    }

	public String toXpdl(DbCnt cnt, Map ctStageIds, Map ctTaskIds,
			Map ctRuleIds, Map ctEntityIds, Map ctTpDocIds, Map subPcdIds,
			Map ctHelpsIds) throws ISPACException {
		// TODO Auto-generated method stub
		return null;
	}
    
}
