package ieci.tdw.ispac.ispaclib.dao.dbmgr;

import ieci.tdw.ispac.api.EntityType;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.util.PrefixBuilder;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Factoría para la gestión de descripciones de tablas.
 *
 */
public class DBTableMgrFactory
{

	private static DBTableMgrFactory _instance;
	private final Map mtablemap;
	//Tamaño máximo del nombre de una entidad (tabla en BD), restringido a este valor por ORACLE
	public static final int TABLENAME_MAXLENGTH = 30;
	public static final int COLUMNNAME_MAXLENGTH = 30;
	public static final int SEQUENCENAME_MAXLENGTH = 30;
	
	/**
	 *
	 */
	private DBTableMgrFactory()
	{
		mtablemap= Collections.synchronizedMap(new HashMap());
	}

	public static synchronized DBTableMgrFactory getInstance()
	{
		if (_instance == null)
			_instance =  new DBTableMgrFactory();

		return _instance;
	}


	private DBTableDesc newTableDesc(DbCnt cnt,String tablekey,String sTable,String prefix,Property[] tableColumn)
	throws ISPACException
	{
		
		DBTableDesc tabledesc = null;
		DbResultSet dbrs = null;
		
		try {
			dbrs=cnt.getColumns(sTable);
	
			if (tableColumn == null) {
				tabledesc=new DBTableDesc(sTable, dbrs.getResultSet(),prefix);
			} else {
				tabledesc=new DBTableDesc(sTable, dbrs.getResultSet(),prefix,tableColumn);
			}
		} finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}

		mtablemap.put(getTableDescKey(tablekey), tabledesc);
		
		return tabledesc;
	}
	
	/*
	private DBTableDesc newTableDesc(String tablekey,String sTable,Property[] tableColumns)
	throws ISPACException
	{
		DBTableDesc tabledesc=new DBTableDesc(sTable,tableColumns);
		mtablemap.put(tablekey,tabledesc);
		return tabledesc;
	}
	 */

	private DBTableDesc addTableDesc(DbCnt cnt,String tablekey,String sTable,String prefix,Property[] tableColumns)
	throws ISPACException
	{
	    if (cnt != null)
	    {
	        return newTableDesc(cnt,tablekey,sTable,prefix,tableColumns);
	    }

		cnt = new DbCnt();
		cnt.getConnection();

		try
		{
			return newTableDesc(cnt,tablekey,sTable,prefix,tableColumns);
		}
		finally
		{
			cnt.closeConnection();
		}
	}

	public DBTableDesc getTableDesc(DbCnt cnt,String sTable,String prefix,Property[] tableColumns,Date timestamp, boolean isPosibleProcessEntity)
	throws ISPACException
	{
		String tablekey=getTableKey(sTable,prefix,tableColumns);

		DBTableDesc tabledesc= (DBTableDesc)mtablemap.get(getTableDescKey(tablekey));
		if (tabledesc != null) {
			
			if (timestamp != null) {
				// Comparar los timestamp
				if (DateUtil.getCalendar(timestamp).before(DateUtil.getCalendar(tabledesc.getTimestamp()))) {
					
					// Si la fecha de la entidad es anterior a la fecha de descripción de la tabla
					// la descripción de la tabla es válida
					return tabledesc;
				}
			}
			else {
				
				return tabledesc;
			}
		}
		if (isPosibleProcessEntity){
			DbResultSet rs = null;
			try {
				//Se comprueba si es una entidad de proceso para obtener la descripcion como entidad y no como tabla
				rs = cnt.executeQuery("SELECT TIPO FROM SPAC_CT_ENTIDADES WHERE NOMBRE = '" + sTable + "'");
				if (rs.getResultSet().next()){
					if (rs.getResultSet().getInt("TIPO") == EntityType.PROCESS_ENTITY_TYPE.getId())
						return addEntityDesc(cnt,tablekey,sTable,prefix,tableColumns);
				}
			} catch (SQLException e) {
				throw new ISPACException(e);
			} finally{
				if (rs != null)
					rs.close();
			}
		}
		return addTableDesc(cnt,tablekey,sTable,prefix,tableColumns);	
		
	}

	
	public DBTableDesc reloadEntityDesc(DbCnt cnt, String tablename) throws ISPACException {
		String tablekey=getTableKey(tablename,null,null);
		return addEntityDesc(cnt,tablekey,tablename,null,null);
	}

	
	public DBTableDesc getEntityDesc(DbCnt cnt, String tablename, String prefix, Property[] tableColumns, Date timestamp) throws ISPACException {
		String tablekey=getTableKey(tablename,prefix,tableColumns);

		DBTableDesc tabledesc= (DBTableDesc)mtablemap.get(getTableDescKey(tablekey));
		if (tabledesc != null && (tabledesc instanceof DBEntityDesc)) {
			
			if (timestamp != null) {
				// Comparar los timestamp
				if (DateUtil.getCalendar(timestamp).before(DateUtil.getCalendar(tabledesc.getTimestamp()))) {
					
					// Si la fecha de la entidad es anterior a la fecha de descripción de la tabla
					// la descripción de la tabla es válida
					return tabledesc;
				}
			}
			else {
				
				return tabledesc;
			}
		}

		return addEntityDesc(cnt,tablekey,tablename,prefix,tableColumns);
	}
	

    private DBTableDesc addEntityDesc(DbCnt cnt, String tablekey, String sTable, String prefix, Property[] tableColumns) throws ISPACException {
        if (cnt != null)
        	{
                return newEntityDesc( cnt,tablekey,sTable,prefix,tableColumns);
            }

        	cnt = new DbCnt();
        	cnt.getConnection();

        	try
        	{
        		return newEntityDesc(cnt,tablekey,sTable,prefix,tableColumns);
        	}
        	finally
        	{
        		cnt.closeConnection();
        	}	
     }

    

	private DBTableDesc newEntityDesc(DbCnt cnt,String tablekey,String sTable,String prefix,Property[] tableColumn)
	throws ISPACException
	{
		IEntityDef entitydef = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, sTable);
		DBTableDesc tabledesc = null;

		if (tableColumn == null)
			tabledesc=new DBEntityDesc(sTable, entitydef,prefix);
		else
			tabledesc=new DBEntityDesc(sTable, entitydef,prefix,tableColumn);
	
		mtablemap.put(getTableDescKey(tablekey),tabledesc);
		return tabledesc;
	}    
	
	public String getTableKey(String sTable,String prefix,Property[] tableColumns)
	{
	    if (prefix==null && tableColumns==null)
	        return sTable;

	    String tablekey=sTable;
	    if (prefix!=null)
	        tablekey=PrefixBuilder.buildName(prefix,sTable);

	    if (tableColumns!=null)
	    {
	        String colkey="";
	        for (int i=0;i<tableColumns.length;i++)
	        {
	            colkey+=PrefixBuilder.PREFIX_ISPAC+tableColumns[i].getName();
	        }
	        tablekey+=colkey;
	    }
	    return tablekey;
	}

	public String getTableDescKey(String tableKey) {

		String key = null;
		
		OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
		if (info != null) {
			key = info.getOrganizationId() + "_" + tableKey;
		} else {
			key = tableKey;
		}

		return key;
	}
}