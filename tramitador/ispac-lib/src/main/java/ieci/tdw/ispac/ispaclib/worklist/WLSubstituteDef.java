/*
 * Created on 25-abr-2005
 *
 */
package ieci.tdw.ispac.ispaclib.worklist;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.util.PrefixBuilder;

/**
 * @author juanin
 *
 */
public class WLSubstituteDef extends WLObjectDef
{
    String property="";
    WLCodeTableDef codeTable=null;

    public WLSubstituteDef() {
    	super();
    }
    
    public WLSubstituteDef(String name, String property, WLCodeTableDef codeTable) {
    	this();
    	setName(name);
    	setProperty(property);
    	setCodeTable(codeTable);
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
     * @return Returns the codeTable.
     */
    public WLCodeTableDef getCodeTable()
    {
        return codeTable;
    }
    /**
     * @param codeTable The codeTable to set.
     */
    public void setCodeTable(WLCodeTableDef codeTable)
    {
        this.codeTable = codeTable;
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.worklist.WLObjectDef#getJoinBind(ieci.tdw.ispac.ispaclib.db.DbCnt, java.lang.String)
     */
    public String getJoinBind(DbCnt cnt, String worklistbind)
            throws ISPACException
    {
        if (codeTable==null)
            return "";

        String prefix=PrefixBuilder.buildName(codeTable.getName(),getName());
        String tblcodefield= PrefixBuilder.buildRawName(prefix,codeTable.getCode());

        //return "( "+PrefixBuilder.buildRawName(getName(),getProperty())+" = "+ tblcodefield+" )";
        //[ildfns]
        return getJoinConditions(cnt.getProductName(), tblcodefield);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.worklist.WLObjectDef#addJoin(ieci.tdw.ispac.ispaclib.db.DbCnt, ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO)
     */
    public void addJoin(DbCnt cnt, TableJoinFactoryDAO joinfactory)
            throws ISPACException
    {
        if (codeTable==null)
            return;

        String prefix=PrefixBuilder.buildName(codeTable.getName(),getName());

        Property properties[]={
                //new Property(1,codeTable.getCode()),
                //new Property(1,getProperty(),codeTable.getValue())
        		new Property(1,codeTable.getValue())
        };

        //joinfactory.addTable(codeTable.getTable(),prefix,properties);
        joinfactory.addTableJoined(this, prefix, properties); 
    }
    
    private String getJoinConditions(String productName, String tblcodefield){
        String join = "( "+PrefixBuilder.buildRawName(getName(),getProperty())+ " = " + tblcodefield+" )";
//        if (productName.startsWith("MICROSOFT SQL SERVER"))
//            join = "( "+PrefixBuilder.buildRawName(getName(),getProperty())+ " *= " + tblcodefield+" )";
//        else if(productName.startsWith("ORACLE"))
//            join = "( "+PrefixBuilder.buildRawName(getName(),getProperty())+ " = " + tblcodefield+ " (+) )";
        return join;        
    }
}
