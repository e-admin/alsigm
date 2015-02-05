/*
 * Created on 25-abr-2005
 *
 */
package ieci.tdw.ispac.ispaclib.worklist;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.util.PrefixBuilder;

/**
 * @author david
 *
 */
// Tabla agregada a la consulta con LEFT OUTER JOIN
public class WLTableJoinDef extends WLObjectDef
{
    String property="";
    WLTableDef table=null;

    public WLTableJoinDef() {
    	super();
    }
    
    public WLTableJoinDef(String name, String property, WLTableDef table) {
    	this();
    	setName(name);
    	setProperty(property);
    	setTable(table);
    }
    
    /**
     * @return Returns the property.
     */
    public String getProperty()
    {
        return property;
    }
    /**
     * @param property The property to set.
     */
    public void setProperty(String property)
    {
        this.property = property;
    }

    /**
     * @return Returns the table.
     */
    public WLTableDef getTable()
    {
        return table;
    }
    /**
     * @param table The table to set.
     */
    public void setTable(WLTableDef table)
    {
        this.table = table;
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.worklist.WLObjectDef#getJoinBind(ieci.tdw.ispac.ispaclib.db.DbCnt, java.lang.String)
     */
    public String getJoinBind(DbCnt cnt, String worklistbind)
            throws ISPACException
    {
        if (table==null)
            return "";

        String tblfield= PrefixBuilder.buildRawName(table.getName(),table.getNumExpField());
        
        return getJoinConditions(cnt.getProductName(), tblfield);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.worklist.WLObjectDef#addJoin(ieci.tdw.ispac.ispaclib.db.DbCnt, ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO)
     */
    public void addJoin(DbCnt cnt, TableJoinFactoryDAO joinfactory)
            throws ISPACException
    {
        if (table==null)
            return;
        
        joinfactory.addTableJoined(this, table.getTableName(), table.getName()); 
    }
    
    private String getJoinConditions(String productName, String tblfield){
        String join = "( "+PrefixBuilder.buildRawName(getName(),getProperty())+ " = " + tblfield+" )";
//        if (productName.startsWith("MICROSOFT SQL SERVER"))
//            join = "( "+PrefixBuilder.buildRawName(getName(),getProperty())+ " *= " + tblcodefield+" )";
//        else if(productName.startsWith("ORACLE"))
//            join = "( "+PrefixBuilder.buildRawName(getName(),getProperty())+ " = " + tblcodefield+ " (+) )";
        return join;        
    }
}
