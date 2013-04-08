package ieci.tdw.ispac.ispaclib.dao;

import ieci.tdw.ispac.api.EntityType;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaclib.dao.dbmgr.DBColumn;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.MapUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Para que CollectionDAO funcione corréctamente es necesario que la
 * clase que se pase como parámetro implemente un constructor vacío.
 *
 */
public class CollectionDAO implements Serializable
{
	Class mclassDAO;
	DbResultSet mdbrs;

	List mlist;
	Iterator miterator;
	
	Object[] marguments;
	Class[] margumentTypes;
	
	/**
	 * Constructor CollectionDAO
	 * @param cls Class del los objeto que devolvera el CollectionDAO
	 * @throws ISPACException
	 */
	public CollectionDAO(Class cls)
	throws ISPACException
	{
		this(cls,(Object[])null);
	}
	
	/**
	 * Constructor con conexión y clase
	 * @param cls Class del objeto que va a devolver el collectionDAO
	 * @param cnt
	 * @throws ISPACException
	 */
	public CollectionDAO(Class cls, DbCnt cnt)
	throws ISPACException
	{
	    Object[] objarray=new Object[1];
	    objarray[0]=cnt;
	    initCollectionDAO(cls,objarray);
	}
	
	/**
	 * Crea un collectionDAO a partir de la clase de un DAO y una lista de argumentos para su contructor 
	 * 
	 * @param cls
	 * @param args
	 * @throws ISPACException
	 */
	public CollectionDAO(Class cls,Object[] args)
	throws ISPACException
	{
	    initCollectionDAO(cls,args);
	}

	private void initCollectionDAO(Class cls,Object[] args)
	throws ISPACException
	{
		if (!ObjectDAO.class.isAssignableFrom(cls))
			throw new ISPACException("La clase "+ObjectDAO.class.toString()+" no extiende la clase ObjectDAO");

		mclassDAO=cls;
		mdbrs=null;
		mlist = new ArrayList();
		marguments=args;
		margumentTypes=null;

		if (marguments==null)
		    return;

		margumentTypes=new Class[marguments.length];
		for (int i = 0; i < marguments.length; i++)
		{
			if (marguments[i] == null)
				throw new ISPACException("La entidad está mal definida");
			margumentTypes[i] = marguments[i].getClass();
		}
	}
	
	protected ObjectDAO getObjDAO()
	throws ISPACException
	{
		try
		{
			//Por defecto se utiliza el constructor sin parámetros.
			if (marguments==null)
				return (ObjectDAO)mclassDAO.newInstance();

			Constructor newdao = mclassDAO.getConstructor( margumentTypes );
			return (ObjectDAO)newdao.newInstance(marguments);
		}
		catch(Exception e)
		{
			throw new ISPACException(e);
		}
	}

	protected ObjectDAO getObjDAO(DbCnt cnt)
	throws ISPACException
	{
		try
		{
			Constructor newdao = null;
			
			// Constructor con conexión
			if (marguments == null) {
				
				marguments = new Object[1];
				margumentTypes = new Class[1];
				
				marguments[0] = cnt;
				margumentTypes[0] = cnt.getClass();
			}
			else if ((marguments.length > 0) &&
					 (!(marguments[0] instanceof DbCnt))) {
				
				// Añadir la conexión como primer argumento para el constructor
				Object[] arguments = new Object[marguments.length + 1];
				Class[] argumentTypes = new Class[margumentTypes.length + 1];
				
				arguments[0] = cnt;
				argumentTypes[0] = cnt.getClass();
				
				for (int i = 0; i < marguments.length; i++) {
					arguments[i + 1] = marguments[i];
					argumentTypes[i + 1] = margumentTypes[i];
				}
				
				marguments = arguments;
				margumentTypes = argumentTypes;
			}

			newdao = mclassDAO.getConstructor(margumentTypes);
			return (ObjectDAO)newdao.newInstance(marguments);
		}
		catch(Exception e)
		{
			throw new ISPACException(e);
		}
	}

	public void query(DbCnt cnt,String sqlWhere)
	throws ISPACException
	{
		ObjectDAO objdao = getObjDAO(cnt);
		String sql = "SELECT " + objdao.getColsSQL() + " FROM " + objdao.getTableName();
		if (sqlWhere != null){
			sql += " " + sqlWhere;
		}

		try {
			mdbrs=cnt.executeQuery(sql);
			convertToList(cnt);
		} finally {
			if (mdbrs != null) {
				mdbrs.close();
			}
		}
	}

	protected void query(DbCnt cnt,String sqlWhere, int limit)
	throws ISPACException
	{
		ObjectDAO objdao = getObjDAO(cnt);
		String sql = "SELECT " + objdao.getColsSQL() + " FROM " + objdao.getTableName();
		if (sqlWhere != null){
			sql += " " + sqlWhere;
		}
		
		// Oracle -> se establece el ROWNUM con un SELECT * FROM sobre la consulta
		if (cnt.isOracleDbEngine() && (limit > 0)) {
			
			sql = "SELECT * FROM ( " + sql + " ) WHERE ROWNUM <= " + limit;
		}
		
		try {
			mdbrs=cnt.executeQuery(sql);
			convertToList(cnt);
		} finally {
			if (mdbrs != null) {
				mdbrs.close();
			}
		}
	}

	public void query(DbCnt cnt, String sqlWhere, String order, int limit) throws ISPACException {

		//En sql server no existe el limit propiamente dicho hay que utilizar la vble rowcount para indicarle
		//el número de registros que queremos que nos devuelva.
		if ((limit > 0) && cnt.isSqlServerDbEngine()) {
			cnt.execute("SET ROWCOUNT " + limit);
		}
		
		String query = DBUtil.getSqlLimitBD(cnt, sqlWhere, order, limit, null);
		query(cnt, query, limit);
		
		//Reseteamos el valor de la vble en el caso de que la hubiésemos modificado
		if ((limit > 0) && cnt.isSqlServerDbEngine()) {
			cnt.execute("SET ROWCOUNT 0");
		}
	}

	private void convertToList(DbCnt cnt) throws ISPACException {
		convertToList(cnt, null);
	}


	private void convertToList(DbCnt cnt, String[] columns) throws ISPACException {
		try{
			while(mdbrs.getResultSet().next()){
				ObjectDAO objDAO=getObjDAO();
				//Invocamos a la carga de multivalores sólo cuando son susceptibles de tener multivalores
				if ( (objDAO instanceof EntityDAO  && ((EntityDAO)objDAO).getCTEntityType() == EntityType.PROCESS_ENTITY_TYPE.getId()) 
				   ||(objDAO instanceof TableJoinDAO) 
				   ){
					if (columns == null){
						objDAO.load(mdbrs.getResultSet());
						objDAO.loadMultivalues(cnt);
					}else{
						objDAO.load(mdbrs.getResultSet(), MapUtils.toMap(columns));
						objDAO.loadMultivalues(cnt, MapUtils.toMap(columns));
					}
				}else{
					if (ArrayUtils.isEmpty(columns))
						objDAO.load(mdbrs.getResultSet());
					else
						objDAO.load(mdbrs.getResultSet(), MapUtils.toMap(columns));
				}
				mlist.add( objDAO);
			}
			if (mlist != null)
				miterator = mlist.iterator();
		}
		catch(SQLException e){
			throw new ISPACException(e);
		} 
//		finally {
//			if (mdbrs != null) {
//				mdbrs.close();
//			}
//		}
	}	
	
	public void queryForUpdate(DbCnt cnt,String sqlWhere)
	throws ISPACException
	{
		String sqlPostWhere ="";
		String sqlPrevWhere ="";
		if (cnt.isSqlServerDbEngine()) {
			sqlPrevWhere = " WITH (UPDLOCK) ";
		}else{
			sqlPostWhere = " FOR UPDATE ";
		}
		//return " WITH (UPDLOCK) WHERE " + PKNAME + " = " + getInt(PKNAME);
		//return " WHERE " + PKNAME + " = " + getInt(PKNAME) + " FOR UPDATE";
		
		ObjectDAO objdao = getObjDAO(cnt);

		String sql = "SELECT " + objdao.getColsSQL() + " FROM " + objdao.getTableName();
		
		if (sqlWhere != null)
		{
			sql += " " + sqlPrevWhere + sqlWhere + sqlPostWhere;
		}

		try {
			mdbrs=cnt.executeQuery(sql);
			convertToList(cnt);
		} finally {
			if (mdbrs != null) {
				mdbrs.close();
			}
		}
	}
	
	public void queryDistinct(DbCnt cnt,String sqlWhere)
	throws ISPACException
	{
		ObjectDAO objdao = getObjDAO(cnt);

		String sql = "SELECT DISTINCT " + objdao.getColsSQL() + " FROM "+ objdao.getTableName();

		if (sqlWhere != null)
		{
			sql = generateSqlDistinct(cnt, sql, sqlWhere);
		}

		try {
			mdbrs=cnt.executeQuery(sql);
			convertToList(cnt);
		} finally {
			if (mdbrs != null) {
				mdbrs.close();
			}
		}
	}
	
	public void queryDistinct(DbCnt cnt, String sqlWhere, String[] columns)
	throws ISPACException
	{

		ObjectDAO objdao = getObjDAO(cnt);
		
		String sColumns = "";
		if (columns.length > 0) {
			
			String separator = "";
			for (int i = 0; i < columns.length; i++) {
				int hashCode = columns[i].hashCode();
				String prefix = DBColumn.PREFIX_ALIAS_EVEN;
				if (hashCode > 0)
					prefix = DBColumn.PREFIX_ALIAS_ODD;
				String alias = prefix + Math.abs(hashCode);
				sColumns += separator + columns[i] + " AS "+ alias;
				separator = ", ";
			}
		}
		else {
			sColumns = objdao.getColsSQL();
		}

		String sql = "SELECT DISTINCT " + sColumns + " FROM "+ objdao.getTableName();

		if (sqlWhere != null)
		{
			sql = generateSqlDistinct(cnt, sql, sqlWhere);
		}

		try {
			mdbrs=cnt.executeQuery(sql);
			convertToList(cnt, columns);
		} finally {
			if (mdbrs != null) {
				mdbrs.close();
			}
		}
	}

	private String generateSqlDistinct(DbCnt cnt, String sql, String sqlWhere) {
		
		// Oracle -> para búsquedas sin tener en cuenta minúsculas/mayúsculas/acentos
		// la configuración es NLS_COMP=LINGUISTIC y NLS_SORT=BINARY_AI
		// ALTER SESSION SET NLS_COMP=LINGUISTIC
		// ALTER SESSION SET NLS_SORT=BINARY_AI
		// SELECT * FROM NLS_SESSION_PARAMETERS WHERE PARAMETER IN ('NLS_SORT','NLS_COMP');
		// Esta configuración hace que la consulta SELECT DISTINCT ... ORDER BY ... 
		// genere error -> ORA-01791: no es una expresión obtenida bajo SELECT
		// debido a los nombres de las columnas en el ORDER BY
		// La solución es aplicar un SELECT con el ORDER BY sobre la SELECT del DISTINCT:
		// SELECT * FROM ( SELECT DISTINCT ... ) ORDER BY ...
		if (cnt.isOracleDbEngine()) {
			
			int indexOrderBy = sqlWhere.toUpperCase().lastIndexOf("ORDER BY");
			if (indexOrderBy != -1) {
				
				String orderBy = sqlWhere.substring(indexOrderBy);

				if (orderBy.indexOf(")") == -1) {
					
					// Obtener los alias de las columnas del ORDER BY
					String columns = orderBy.substring(9);
					String[] sColumns = columns.split(",");
					
					String orderByAlias = "";
					
					// Generar el ORDER BY con los alias de las columnas
					for (int i = 0; i < sColumns.length; i++) {
						
						int hashCode = sColumns[i].trim().hashCode();
						String prefix = DBColumn.PREFIX_ALIAS_EVEN;
						if (hashCode > 0)
							prefix = DBColumn.PREFIX_ALIAS_ODD;

						orderByAlias += prefix + Math.abs(hashCode);
						
						if (i != sColumns.length - 1) {
							orderByAlias += ", ";
						}
					}
					
					return "SELECT * FROM ( " + sql + " " + sqlWhere.substring(0, indexOrderBy) + " ) ORDER BY " + orderByAlias;
				}
			}
		}
		
		return sql + " " + sqlWhere;
	}
	
	public int count(DbCnt cnt,String sqlWhere)
	throws ISPACException
	{
		ObjectDAO objdao = getObjDAO(cnt);

		String sql="SELECT COUNT(*) FROM " + objdao.getTableName();
		if (sqlWhere != null)
		{
			sql += " " + sqlWhere;
		}

		DbResultSet dbrs=cnt.executeQuery(sql);

		try
		{
			int count=0;
			if (dbrs.getResultSet().next())
				 count=dbrs.getResultSet().getInt(1);

			return count;
		}
		catch(SQLException e)
		{
			throw new ISPACException(e);
		}
		finally {
			dbrs.close();
		}
	}

	public boolean exist(DbCnt cnt,String sqlWhere)
	throws ISPACException
	{
		return count(cnt,sqlWhere)>0;
	}

	public int delete(DbCnt cnt,String sqlWhere)
	throws ISPACException
	{
		ObjectDAO objdao = getObjDAO(cnt);

		if (sqlWhere == null || sqlWhere.trim().length() == 0)
			throw new ISPACException("CollectionDAO.delete() - Se requiere un filtro para el borrado de datos.");

		String sql = "DELETE FROM " + objdao.getTableName() + " " + sqlWhere;
		return cnt.directExec(sql);
	}

	public boolean next()
	throws ISPACException
	{
//		if (mdbrs == null)
//			throw new ISPACException("No se ha especificado una búsqueda para la colección");
//		try
//		{
//			if (mdbrs.getResultSet().next())
//				return true;
//
//			mdbrs.close();
//			mdbrs=null;
//			return false;
//		}
//		catch(SQLException e)
//		{
//			throw new ISPACException(e);
//		}
		if (miterator == null){
			throw new ISPACException("exception.collection.nosearch");
		}
		return miterator.hasNext();
	}

	public ObjectDAO value()
	throws ISPACException
	{
//		if (mdbrs == null)
//			throw new ISPACException("No existen valores en la colección");
//
//		// La descripción de la tabla del DAO se cacheó con la consulta que obtiene la colección
//		ObjectDAO obj=getObjDAO();
//		obj.load(mdbrs.getResultSet());
//		return obj;
		if (miterator == null){
			throw new ISPACException("exception.collection.novalues");
		}
		return (ObjectDAO)miterator.next();
	}
	
	public ObjectDAO value(Map columns)
	throws ISPACException
	{
		ObjectDAO objDAO = value();
		objDAO.filterColumns(columns);
		return objDAO;
	}

	public IItemCollection disconnect() throws ISPACException
	{
		//return new ListCollection(this.toList());
		return new ListCollection(mlist);
	}
	
	public IItemCollection disconnect(Map properties) throws ISPACException
	{
		return new ListCollection(this.toList(properties));
	}

	public Map toMap()
	throws ISPACException
	{
		LinkedHashMap map=new LinkedHashMap();

		while(next())
		{
			IItem obj=value();
			map.put(obj.getKey(),obj);
		}
		return map;
	}
	
	public Map toMapIntegerKey()
	throws ISPACException
	{
		LinkedHashMap map=new LinkedHashMap();

		while(next())
		{
			IItem obj=value();
			map.put(obj.getKeyInteger(),obj);
		}
		return map;
	}

	public Map toMap(String hashMemberKey)
	throws ISPACException
	{
		LinkedHashMap map=new LinkedHashMap();

		while(next())
		{
			IItem obj=value();
			map.put(obj.get(hashMemberKey),obj);
		}
		return map;
	}

	public Map toMapStringKey(String hashMemberKey)
	throws ISPACException
	{
		LinkedHashMap map=new LinkedHashMap();

		while(next())
		{
			IItem obj=value();
			map.put(obj.getString(hashMemberKey),obj);
		}
		return map;
	}	
	
	public List toList()
	throws ISPACException
	{
//		ArrayList list = new ArrayList();
//
//		while(next())
//		{
//			IItem obj = value();
//			list.add( obj);
//		}
//		return list;
		return mlist;
	}
	
	public List toList(Map columns)
	throws ISPACException
	{
		ArrayList list = new ArrayList();

		while(next())
		{
			IItem obj = value(columns);
			list.add( obj);
		}
		return list;
	}

	public String toXml() throws ISPACException
	{
		String sXml = null;
		StringBuffer sItemsBuffer = new StringBuffer();

		// Propiedades del objeto
		ObjectDAO objdao = getObjDAO();
		String sProperties = objdao.getProperties().toXML();

		// Valores de la colección
		for (int i = 1; next(); i++)
		{
			IItem obj = value();
			sItemsBuffer.append(XmlTag.newTag("item", obj.getXmlValues(), i));
		}
		String sItems = XmlTag.newTag("items",sItemsBuffer);

		sXml = XmlTag.getXmlInstruction("ISO-8859-1")
				 + XmlTag.newTag("collection", sProperties + sItems);

		return sXml;
	}
}
