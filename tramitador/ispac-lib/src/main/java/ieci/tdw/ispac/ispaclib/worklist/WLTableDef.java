package ieci.tdw.ispac.ispaclib.worklist;

import java.util.ArrayList;
import java.util.Iterator;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

/**
 *
 */
public class WLTableDef extends WLObjectDef
{
    String tableName;
    String numExpField;

    // Tabla agregada a la consulta con LEFT OUTER JOIN
    ArrayList objdeflist = new ArrayList();
    
    public void add(WLTableJoinDef objdef)
    {
        objdef.setTable(this);
        objdeflist.add(objdef);
    }
    
    public boolean isOuterjoin() {
    	
    	if (objdeflist.isEmpty()) {
    		
    		return false;
    	}
    	else {
    		return true;
    	}
    }

    /**
     * @return Returns the numExpField.
     */
    public String getNumExpField()
    {
        return numExpField;
    }
    /**
     * @param numExpField The numExpField to set.
     */
    public void setNumExpField(String numExpField)
    {
        this.numExpField = numExpField;
    }
    /**
     * @return Returns the tableName.
     */
    public String getTableName()
    {
        return tableName;
    }
    /**
     * @param tableName The tableName to set.
     */
    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }
    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.worklist.WLObjectDef#addJoin(ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO)
     */
    public void addJoin(DbCnt cnt, TableJoinFactoryDAO joinfactory) throws ISPACException
    {
    	if (objdeflist.isEmpty()) {
    		
        joinfactory.addTable(getTableName(),getName());
    	}
    	// Tabla agregada a la consulta con LEFT OUTER JOIN
    	else {
    		
	        Iterator it=objdeflist.iterator();
	        while (it.hasNext())
	        {
	            WLObjectDef objdef = (WLObjectDef) it.next();
	            objdef.addJoin(cnt,joinfactory);
	        }
    	}
    }
    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.worklist.WLObjectDef#getJoinBind()
     */
    public String getJoinBind(DbCnt cnt, String worklistbind) throws ISPACException
    {
    	if (objdeflist.isEmpty()) {
    		
        return "("+worklistbind+"="+getName()+"."+getNumExpField()+" )";
    	}
    	// Tabla agregada a la consulta con LEFT OUTER JOIN
    	else {
            StringBuffer sqljoinquery=new StringBuffer();
            Iterator it=objdeflist.iterator();
            while (it.hasNext())
            {
                WLObjectDef wlobjdef = (WLObjectDef) it.next();
                if (sqljoinquery.length()!=0)
                    sqljoinquery.append(" AND ");
                sqljoinquery.append(wlobjdef.getJoinBind(cnt,worklistbind));
            }
            return sqljoinquery.toString();
    	}
    }
}
