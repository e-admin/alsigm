package ieci.tdw.ispac.ispaclib.worklist;

import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.util.ArrayList;
import java.util.Iterator;

public class WLTaskListBuilder
{
    ArrayList objdeflist = new ArrayList();
    WLTaskListDef tasklistdef=null;

    String query="";


    /**
     * @return Returns the query.
     */
    public String getQuery()
    {
        return query;
    }
    /**
     * @param query The query to set.
     */
    public void setQuery(String query)
    {
        this.query = query;
    }

    public void add(WLObjectDef objdef)
    {
        objdeflist.add(objdef);
    }

    public void addTaskList(WLTaskListDef wldef)
    {
    	tasklistdef=wldef;
        objdeflist.add(wldef);
    }

    public CollectionDAO getTaskList(DbCnt cnt, int ctTaskId, String resplist)
    throws ISPACException
    {
		return getTaskList(cnt, ctTaskId, resplist, ISPACEntities.ENTITY_NULLREGKEYID, ISPACEntities.ENTITY_NULLREGKEYID);
    }

    public CollectionDAO getTaskList(DbCnt cnt, int ctTaskId, String resplist, int pcdId)
    throws ISPACException
    {
		return getTaskList(cnt, ctTaskId, resplist, pcdId, ISPACEntities.ENTITY_NULLREGKEYID);
    }

    public CollectionDAO getTaskList(DbCnt cnt, int ctTaskId, String resplist, int pcdId, int idTaskPCD)
    throws ISPACException
    {
    	tasklistdef.setCtTaskId(ctTaskId);
		tasklistdef.setPcdId(pcdId);
		tasklistdef.setIdTaskPCD(idTaskPCD);
    	tasklistdef.setRespList(resplist);

        String wlbindfield=tasklistdef.getBindField();

        TableJoinFactoryDAO joinfactory=new TableJoinFactoryDAO();

        StringBuffer sqljoinquery=new StringBuffer();
        Iterator it=objdeflist.iterator();
        while (it.hasNext())
        {
            WLObjectDef wlobjdef = (WLObjectDef) it.next();
            wlobjdef.addJoin(cnt,joinfactory);

            // Tabla agregada a la consulta con LEFT OUTER JOIN
            boolean outerjoin = false;

            if (wlobjdef instanceof WLCodeTableDef) {
            	outerjoin = true;
            }
            else if (wlobjdef instanceof WLTableDef) {

            	outerjoin = ((WLTableDef) wlobjdef).isOuterjoin();
            }

            if (!outerjoin) {
	            if (sqljoinquery.length()!=0)
	                sqljoinquery.append(" AND ");


	            sqljoinquery.append(wlobjdef.getJoinBind(cnt,wlbindfield));
            }
        }

        if (getQuery().length()>0)
        {
            sqljoinquery.append(" AND (");
            sqljoinquery.append(getQuery());
            sqljoinquery.append(" )");
        }

        return joinfactory.queryTableJoin(cnt,"WHERE "+sqljoinquery.toString());
    }

}