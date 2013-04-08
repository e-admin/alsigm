package ieci.tdw.ispac.ispaclib.worklist;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.wl.WLTaskDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.worklist.WLObjectDef;

public class WLTaskListDef extends WLObjectDef
{
    int ctTaskId;
    String respList;

    /**
     * @return Returns the respList.
     */
    public String getRespList()
    {
        return respList;
    }
    /**
     * @param respList The respList to set.
     */
    public void setRespList(String respList)
    {
        this.respList = respList;
    }
    /**
     * @return Returns the ctTaskId.
     */
    public int getCtTaskId()
    {
        return ctTaskId;
    }
    /**
     * @param ctTaskId The ctTaskId to set.
     */
    public void setCtTaskId(int ctTaskId)
    {
        this.ctTaskId = ctTaskId;
    }

    public String getBindField()
    {
        return getName()+".NUMEXP";
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.worklist.WLObjectDef#addJoin(ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO)
     */
    public void addJoin(DbCnt cnt, TableJoinFactoryDAO joinfactory) throws ISPACException
    {
        joinfactory.addTable(WLTaskDAO.TABLENAME,getName());
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.worklist.WLObjectDef#getJoinBind()
     */
    public String getJoinBind(DbCnt cnt, String worklistbindfield) throws ISPACException
    {
    	String sql = "( " + getName()+ ".ID_CTTASK = " + getCtTaskId()
    	   		   // Añadir los responsables cuando no es el Supervisor
   		   		   + DBUtil.addAndInResponsibleCondition(getName() + ".RESP", getRespList())
   		   		   + ")";
        
        return  sql;
    }
    
}