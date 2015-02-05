/*
 * Created on 15-abr-2005
 *
 */
package ieci.tdw.ispac.ispaclib.worklist;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author juanin
 *
 */
public class WLCodeTableDef extends WLObjectDef
{
    ArrayList objdeflist = new ArrayList();

    String tblCode="";
    String fldCode="";
    String fldValue="";

    public WLCodeTableDef() {
    	super();
    }

    public WLCodeTableDef(String name, String code, String table, String value) {
    	this();
    	setName(name);
    	setCode(code);
    	setTable(table);
    	setValue(value);
    }
    
    /**
     * @return Returns the tblCode.
     */
    public String getTable()
    {
        return tblCode;
    }
    /**
     * @param tblCode The tblCode to set.
     */
    public void setTable(String tblCode)
    {
        this.tblCode = tblCode;
    }

    /**
     * @return Returns the fldCode.
     */
    public String getCode()
    {
        return fldCode;
    }
    /**
     * @param fldCode The fldCode to set.
     */
    public void setCode(String fldCode)
    {
        this.fldCode = fldCode;
    }
    /**
     * @return Returns the fldValue.
     */
    public String getValue()
    {
        return fldValue;
    }
    /**
     * @param fldValue The fldValue to set.
     */
    public void setValue(String fldValue)
    {
        this.fldValue = fldValue;
    }


    public void add(WLSubstituteDef objdef)
    {
        objdef.setCodeTable(this);
        objdeflist.add(objdef);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.worklist.WLObjectDef#addJoin(ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO)
     */
    public void addJoin(DbCnt cnt, TableJoinFactoryDAO joinfactory) throws ISPACException
    {
        Iterator it=objdeflist.iterator();
        while (it.hasNext())
        {
            WLObjectDef objdef = (WLObjectDef) it.next();
            objdef.addJoin(cnt,joinfactory);
        }

    }
    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.worklist.WLObjectDef#getJoinBind()
     */
    public String getJoinBind(DbCnt cnt, String worklistbind) throws ISPACException
    {
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
