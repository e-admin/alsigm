/*
 * Created on 06-abr-2004
 *
 */
package ieci.tdw.ispac.ispaclib.dao.dbmgr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author juanin
 *
 */
public class DBTableJoinDesc extends DBTableDesc
{

	private static final long serialVersionUID = 1L;
	
    public DBTableJoinDesc(String tablename,DBTableDesc[] tabledescarr)
	{
	    super(tablename);
	    processJoinColumns(tabledescarr);
	}

	public void processJoinColumns(DBTableDesc[] tabledescarr)
	{
	    int shiftordinal=0;
	    for (int i=0;i<tabledescarr.length;i++)
        {
	        Iterator it=tabledescarr[i].iterator();
	        while (it.hasNext())
	        {
		        DBColumn column=tabledescarr[i].getColumn(it).shiftOrdinal(tabledescarr[i].mtablename, shiftordinal);
		        put(column.getName(),column);
	        }
	        shiftordinal+=tabledescarr[i].size();
        }
	}


	public List getMultivalueDBColums() {
		List list = new ArrayList();
		Iterator it = iterator();
		while (it.hasNext()){
			DBColumn col = getColumn(it);
			if (col.isMultivalue()){
				String aliasTableName = col.getRawName().split("[.]")[0];
				list.add(new Object[]{col, col.getTablename(), aliasTableName});
			}
		}
		return list;
	}	
	
	
	
}