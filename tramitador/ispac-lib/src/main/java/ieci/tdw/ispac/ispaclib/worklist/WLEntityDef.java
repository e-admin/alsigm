/*
 * Created on 15-abr-2005
 *
 */
package ieci.tdw.ispac.ispaclib.worklist;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.cat.CTEntityDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

/**
 * @author juanin
 *
 */
public class WLEntityDef extends WLObjectDef
{
    int entityid;
    CTEntityDAO catentity=null;

    public int getEntityId()
    {
        return this.entityid;
    }

    public void setEntityId(int entityid)
    {
        this.entityid=entityid;
    }

    private CTEntityDAO getCatalogEntity(DbCnt cnt) throws ISPACException
    {
        if (catentity!=null)
            return catentity;

        catentity=EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt,getEntityId());
        return catentity;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.worklist.WLObjectDef#addJoin(ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO)
     */
    public void addJoin(DbCnt cnt, TableJoinFactoryDAO joinfactory) throws ISPACException
    {
        CTEntityDAO ctent=getCatalogEntity(cnt);
        joinfactory.addEntity(ctent,getName());
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.worklist.WLObjectDef#getJoinBind()
     */
    public String getJoinBind(DbCnt cnt, String worklistbind) throws ISPACException
    {
        CTEntityDAO ctent=getCatalogEntity(cnt);
        return "("+worklistbind+"="+getName()+"."+ctent.getString("CAMPO_NUMEXP")+" )";
    }
}
