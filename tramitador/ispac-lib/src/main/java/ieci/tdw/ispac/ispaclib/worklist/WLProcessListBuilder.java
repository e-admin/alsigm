package ieci.tdw.ispac.ispaclib.worklist;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 */
public class WLProcessListBuilder
{
    ArrayList objdeflist = new ArrayList();
    WLWorklistDef worklistdef=null;

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

    public void addWorklist(WLWorklistDef wldef)
    {
        worklistdef=wldef;
        objdeflist.add(wldef);
    }

    public CollectionDAO getWorklist(DbCnt cnt,int idStagePCD, String resplist) throws ISPACException {
    	return getWorklist(cnt, 0, idStagePCD, resplist);
    }
    
    public CollectionDAO getWorklist(DbCnt cnt,int parentPcdId, int idStagePCD, String resplist) throws ISPACException {

        worklistdef.setStageIdPCD(idStagePCD);
        worklistdef.setRespList(resplist);

        String wlbindfield=worklistdef.getBindField();

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

        // Comprobar si se está filtrando por procedimiento padre (en subprocesos)
        if (parentPcdId != 0) {
        	sqljoinquery.append(" AND ")
        		.append(worklistdef.getName()).append(".NUMEXP IN (SELECT NUMEXP FROM SPAC_EXPEDIENTES WHERE ID_PCD=") 
        		.append(parentPcdId).append(")");
        }

        return joinfactory.queryTableJoin(cnt,"WHERE "+sqljoinquery.toString());
    }

}