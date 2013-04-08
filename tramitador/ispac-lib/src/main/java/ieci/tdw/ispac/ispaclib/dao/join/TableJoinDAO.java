/*
 * Created on 25-jun-2004
 *
 */
package ieci.tdw.ispac.ispaclib.dao.join;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACPropertyUnknown;
import ieci.tdw.ispac.ispaclib.dao.MemberDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.dbmgr.DBColumn;
import ieci.tdw.ispac.ispaclib.dao.dbmgr.DBTableJoinDesc;
import ieci.tdw.ispac.ispaclib.dao.entity.MultivalueTable;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author juanin
 *
 * Clase genérica para acceder a una tabla arbitraria en BBDD.
 */
public class TableJoinDAO extends ObjectDAO
{
    final String mtablename;
    final String midkey;
    
    public TableJoinDAO(DBTableJoinDesc tablejoin) throws ISPACException {
    	super();
    	setPosibleProcessEntity(true);
        mtablename = tablejoin.getName();
        midkey = null;
        initTableDesc(tablejoin);
    }
    
    public TableJoinDAO(DbCnt cnt, DBTableJoinDesc tablejoin) throws ISPACException {
    	super();
    	setPosibleProcessEntity(true);
    	mtablename = tablejoin.getName();
    	midkey = null;
    	initTableDesc(tablejoin);
    }
    
    public TableJoinDAO(DBTableJoinDesc tablejoin, String keyProperty) throws ISPACException {
    	super();
    	setPosibleProcessEntity(true);
        mtablename = tablejoin.getName();
        midkey = keyProperty;
        initTableDesc(tablejoin);
    }
    
    public TableJoinDAO(DbCnt cnt, DBTableJoinDesc tablejoin, String keyProperty) throws ISPACException {
    	super();
    	setPosibleProcessEntity(true);
    	mtablename = tablejoin.getName();
    	midkey = keyProperty;
    	initTableDesc(tablejoin);
    }

    public String getTableName()
    {
        return mtablename;
    }

    protected String getDefaultSQL(int nActionDAO) throws ISPACException
    {
        return " WHERE " + getKeyName() + " = " + getInt(getKeyName());
    }

    protected void newObject(DbCnt cnt) throws ISPACException
    {
        throw new ISPACException("TableJoinDAO - No se puede crear entradas en un consulta join.");
    }

    public String getKeyName() throws ISPACException
    {
        if (midkey == null || midkey.equals(""))
            throw new ISPACException("No se ha definido el acceso por defecto.");
        return midkey;
    }


	protected void storeData(DbCnt cnt, String sqlWhere) throws ISPACException
	{
	    throw new ISPACException("TableJoinDAO - No se puede guardar registros en un consulta join.");
	}

	protected void updateData(DbCnt cnt, String sqlWhere) throws ISPACException
	{
	    throw new ISPACException("TableJoinDAO - No se puede actualizar registros en un consulta join.");
	}

	public void delete(DbCnt cnt) throws ISPACException
	{
	    throw new ISPACException("TableJoinDAO - No se puede borrar registros en un consulta join.");
	}

	public ObjectDAO duplicate(DbCnt cnt) throws ISPACException
	{
	    throw new ISPACException("TableJoinDAO - No se puede duplicar registros en un consulta join.");
	}


	

	public void loadMultivalues(DbCnt cnt) throws ISPACException {
		loadMultivalues(cnt, null);
	}	
	
	
	private String getMultivalueTableName(String tableName, int type) {
		return MultivalueTable.getInstance().composeMultivalueTableName(tableName, type);
	}

	private String getMultivalueTableCols() {
		String[] columns = MultivalueTable.getInstance().getColumns();
		return StringUtils.join(columns, ',');
	}

	private void processMultivalueRowData(String physicalName, ResultSet rs, boolean bClean) throws ISPACException {
		DBColumn col = mTableDesc.getColumn(physicalName);
		MemberDAO member = (MemberDAO) mMembersMap.get(physicalName);
		if (member == null)
			throw new ISPACPropertyUnknown("No existe la propiedad [" + physicalName + "] en el objeto "+ getTableName() +".");
		List list = new ArrayList();
		try {
			while(rs.next()){
				switch(col.getType()){
					case Types.VARCHAR:
					case Types.LONGVARCHAR:
					case Types.SMALLINT:
					case Types.INTEGER:
					case Types.REAL:
					case Types.FLOAT:
					{
						list.add(rs.getString(MultivalueTable.FIELD_VALUE));
						break;
					}
					case Types.DATE:
					{
						Date date = rs.getDate(MultivalueTable.FIELD_VALUE);
						list.add(DateUtil.format(date, "dd/MM/yyyy"));
						break;
					}
					case Types.TIMESTAMP:
					{
						Timestamp ts = rs.getTimestamp(MultivalueTable.FIELD_VALUE);
						list.add(DateUtil.format(ts, "dd/MM/yyyy HH:mm:ss"));
						break;
					}
					case Types.LONGVARBINARY:
					{
						list.add(rs.getObject(MultivalueTable.FIELD_VALUE));
						break;
					}
					default:
					{
						list.add(rs.getObject(MultivalueTable.FIELD_VALUE));
						break;
					}
				}
			}
			member.setValue(list.toArray());
		}catch(SQLException e){
			throw new ISPACException(e);
		}
		if (bClean)
			member.cleanDirty();
	}	
	
	public void loadMultivalues(DbCnt cnt, Map columns) throws ISPACException {
		DbResultSet dbrs = null;
		String stmt = null; 
		List multivalueDbCols = null;
		
		multivalueDbCols = ((DBTableJoinDesc)mTableDesc).getMultivalueDBColums();
		
		for (Iterator iter = multivalueDbCols.iterator(); iter.hasNext();) {
			Object[] obj = (Object[])iter.next();
			DBColumn col = (DBColumn)obj[0];
			String colName = col.getRawName().split("[.]")[col.getRawName().split("[.]").length-1];
			String tableName = (String)obj[1];
			String aliasTableName = (String)obj[2];
			if (columns == null || columns.get(col.getName().toUpperCase()) == null) {
				stmt = "SELECT " + getMultivalueTableCols() + 
				      " FROM " + getMultivalueTableName(tableName, col.getType()) + 
				      " WHERE "+MultivalueTable.FIELD_FIELD+" = '" + colName.toUpperCase() + "' " +
		      		  " AND " + MultivalueTable.FIELD_REG + " = " + this.getInt(aliasTableName+":"+ICatalogAPI.ID_FIELD_NAME.toUpperCase());
				
				try {
					dbrs = cnt.executeQuery(stmt);
					processMultivalueRowData(col.getName().toUpperCase(), dbrs.getResultSet(), true);
				} finally {
					if (dbrs != null) {
						dbrs.close();
					}
				}
					
			}
		}
	}	
	
	
	
}