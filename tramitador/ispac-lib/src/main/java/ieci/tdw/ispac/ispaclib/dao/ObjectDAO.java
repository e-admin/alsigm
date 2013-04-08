package ieci.tdw.ispac.ispaclib.dao;

import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.errors.ISPACPropertyUnknown;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.dbmgr.DBColumn;
import ieci.tdw.ispac.ispaclib.dao.dbmgr.DBTableDesc;
import ieci.tdw.ispac.ispaclib.dao.dbmgr.DBTableMgrFactory;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ObjectDAO implements IItem
{

	protected LinkedHashMap mMembersMap;
	protected DBTableDesc mTableDesc;
	protected boolean mbNewObject;
	int mPersist;
	private boolean isPosibleProcessEntity = false;
	
	public ObjectDAO(DbCnt cnt, int id, Property[] tableColumns)
	throws ISPACException
	{
		initTableDesc(cnt, getTableName(), tableColumns, null);
		setKey(id);
		load(cnt);
	}
	public ObjectDAO(DbCnt cnt, int id, Property[] tableColumns, Date timestamp)
			throws ISPACException
	{
		initTableDesc(cnt, getTableName(), tableColumns, timestamp);
		setKey(id);
		load(cnt);
	}

	/**
	 * Inicialización para tablas con clave primaria de tipo Object, por ejemplo,
	 * spac_sesiones
	 * @param cnt
	 * @param id
	 * @param tableColumns
	 * @throws ISPACException
	 */
	public ObjectDAO(DbCnt cnt, Object id, Property[] tableColumns)
	throws ISPACException
	{
		initTableDesc(cnt, getTableName(), tableColumns, null);
		setKey(id);
		load(cnt);
	}
	public ObjectDAO(DbCnt cnt, Object id, Property[] tableColumns, Date timestamp)
			throws ISPACException
	{
		initTableDesc(cnt, getTableName(), tableColumns, timestamp);
		setKey(id);
		load(cnt);
	}

	//Inicializa ObjectDAO en la BBDD suministrada.
	public ObjectDAO(DbCnt cnt, Property[] tableColumns) throws ISPACException
	{
		initTableDesc(cnt, getTableName(), tableColumns, null);
	}
	public ObjectDAO(DbCnt cnt, Property[] tableColumns, boolean isPosibleProcessEntity) throws ISPACException
	{
		setPosibleProcessEntity(isPosibleProcessEntity);
		initTableDesc(cnt, getTableName(), tableColumns, null);
	}
	
	public ObjectDAO(DbCnt cnt, Property[] tableColumns, Date timestamp) throws ISPACException
	{
		initTableDesc(cnt, getTableName(), tableColumns, timestamp);
	}
	
	public ObjectDAO(DbCnt cnt, String tablename, Property[] tableColumns)
	throws ISPACException
	{
		initTableDesc(cnt, tablename, tableColumns, null);
	}
	public ObjectDAO(DbCnt cnt, String tablename, Property[] tableColumns, Date timestamp)
			throws ISPACException
	{
		initTableDesc(cnt, tablename, tableColumns, timestamp);
	}
	
	public ObjectDAO(int id, Property[] tableColumns) throws ISPACException
	{
		this(null, id, tableColumns);
	}
	public ObjectDAO(int id, Property[] tableColumns, Date timestamp) throws ISPACException
	{
		this(null, id, tableColumns, timestamp);
	}
	
	public ObjectDAO(String tablename, Property[] tableColumns)
	throws ISPACException
	{
		initTableDesc(null, tablename, tableColumns, null);
	}
	public ObjectDAO(String tablename, Property[] tableColumns, Date timestamp)
			throws ISPACException
	{
		initTableDesc(null, tablename, tableColumns, timestamp);
	}
	
	public ObjectDAO(Property[] tableColumns) throws ISPACException
	{
		initTableDesc(null, getTableName(), tableColumns, null);
	}

	public ObjectDAO(Property[] tableColumns, boolean isPosibleProcessEntity) throws ISPACException
	{
		setPosibleProcessEntity(isPosibleProcessEntity);
		initTableDesc(null, getTableName(), tableColumns, null);
	}
	
	public ObjectDAO(Property[] tableColumns, Date timestamp) throws ISPACException
	{
		initTableDesc(null, getTableName(), tableColumns, timestamp);
	}

	/**
	 * Constructor exclusivo para EntityDAO y TableJoinDAO Es necesario inicializar el objeto
	 * con initTableDesc.
	 *
	 * @throws ISPACException
	 */
	public ObjectDAO() throws ISPACException
	{
	}

	public ObjectDAO(DbCnt cnt, int id, Property[] tableColumns, boolean isPosibleProcessEntity) throws ISPACException {
		setPosibleProcessEntity(isPosibleProcessEntity);
		initTableDesc(cnt, getTableName(), tableColumns, null);
		setKey(id);
		load(cnt);
	}
	/**
	 * Constructor exclusivo para duplicar objetos
	 *
	 * @throws ISPACException
	 */
	/*	protected ObjectDAO(DBTableDesc tabledesc) throws ISPACException
	{
	    initTableDesc(tabledesc);
	}
	*/
	
	protected void initTableDesc(DbCnt cnt, String tablename, Property[] tableColumns, Date timestamp) throws ISPACException
	{
		mMembersMap = new LinkedHashMap();
		mbNewObject = false;

		mTableDesc = DBTableMgrFactory.getInstance().getTableDesc(cnt, tablename, null, tableColumns, timestamp, isPosibleProcessEntity());

		Iterator it = mTableDesc.iterator();
		while (it.hasNext())
		{
			newMemberDAO(mTableDesc.getColumn(it));
		}
	}

	protected void initTableDesc(DbCnt cnt, String[] tablenames, Property[] tableColumns) throws ISPACException
	{
		mMembersMap = new LinkedHashMap();
		mbNewObject = false;

		if (!ArrayUtils.isEmpty(tablenames)) {
			
			for (int i = 0; i < tablenames.length; i++) {
				DBTableMgrFactory.getInstance().getTableDesc(cnt, tablenames[i], null, null, null, isPosibleProcessEntity());
			}

			mTableDesc = DBTableMgrFactory.getInstance().getTableDesc(cnt, tablenames[0], null, tableColumns, null, isPosibleProcessEntity());

			Iterator it = mTableDesc.iterator();
			while (it.hasNext())
			{
				newMemberDAO(mTableDesc.getColumn(it));
			}
		}
	}

	protected void initTableDesc(DBTableDesc tabledesc) throws ISPACException
	{
		mMembersMap = new LinkedHashMap();
		mbNewObject = false;

		mTableDesc = tabledesc;

		Iterator it = mTableDesc.iterator();
		while (it.hasNext())
		{
			newMemberDAO(mTableDesc.getColumn(it));
		}
	}
	
	protected void processRowData(ResultSet rs) throws ISPACException
	{
		mbNewObject = false;

		Iterator it = mTableDesc.iterator();
		while (it.hasNext())
		{
			DBColumn col = mTableDesc.getColumn(it);
			if (!col.isMultivalue()){
				int hashCode = col.getRawName().hashCode();
				String prefix = DBColumn.PREFIX_ALIAS_EVEN;
				if (hashCode > 0)
					prefix = DBColumn.PREFIX_ALIAS_ODD;
				String alias = prefix + Math.abs(hashCode);
				setMemberDAO(col.getName(), col.getMember(rs, alias), true);
			}
		}
	}		
	
	protected void processRowData(ResultSet rs, Map columns) throws ISPACException
	{
		mbNewObject = false;

		Iterator it = mTableDesc.iterator();
		while (it.hasNext())
		{
			DBColumn col = mTableDesc.getColumn(it);
			String columnName = col.getName();
			if (columns.containsKey(columnName)) {
				//setMemberDAO(columnName, col.getMember(rs, columnName), true);
				int hashCode = col.getRawName().hashCode();
				String prefix = DBColumn.PREFIX_ALIAS_EVEN;
				if (hashCode > 0)
					prefix = DBColumn.PREFIX_ALIAS_ODD;
				String alias = prefix + Math.abs(hashCode);
				setMemberDAO(columnName, col.getMember(rs, alias), true);
			}
		}
	}

	
	public void filterColumns(Map columns) {
		Iterator it = mTableDesc.iterator();
		while (it.hasNext()){
			DBColumn col = mTableDesc.getColumn(it);
			String columnName = col.getName();
			if (!columns.containsKey(columnName)) {
				mMembersMap.remove(columnName);
			}
		}
	}
	
	
	protected void storeData(DbCnt cnt, String sqlWhere) throws ISPACException
	{
		StringBuffer sqlValues = new StringBuffer();
		StringBuffer sqlCols = new StringBuffer();
		MemberDAO member;

		Iterator it = iterator();

		while (it.hasNext())
		{
			member = getMemberDAO(it);
			if (!member.getColumn().isMultivalue() && member.isDirty())
			{
				if (sqlCols.length() != 0)
				{
					sqlCols.append(", ");
					sqlValues.append(", ");
				}
				sqlCols.append(member.getRawName());
				sqlValues.append(" ? ");
			}
		}

		if (sqlCols.length() == 0)
		{
		    mbNewObject = false;
			return;
		}

		String stmt = "";

		try
		{
			stmt = "INSERT INTO "
				 + getTableName()
				 + " ("
				 + sqlCols
				 + ") VALUES ( "
				 + sqlValues
				 + " )";
			PreparedStatement ps = cnt.prepareStatement(stmt);

			int idx = 1;
			it = iterator();
			while (it.hasNext())
			{
				member = getMemberDAO(it);

				if (!member.getColumn().isMultivalue() && member.isDirty())
				{
					member.setObject(ps, idx++);
					member.cleanDirty();
				}
			}
			ps.executeUpdate();
			ps.close();
		}
		catch (Exception e)
		{
			throw new ISPACException("SQL:"+stmt+".", e);
		}

		mbNewObject = false;
	}

	protected void updateData(DbCnt cnt, String sqlWhere) throws ISPACException
	{
		StringBuffer sqlValues = new StringBuffer();
		MemberDAO member;

		Iterator it = iterator();
		while (it.hasNext())
		{
			member = getMemberDAO(it);
			if (!member.getColumn().isMultivalue() && member.isDirty())
			{
				if (sqlValues.length() != 0)
					sqlValues.append(", ");

				sqlValues.append(member.getRawName() + " = ? ");
			}
		}

		if (sqlValues.length() == 0)
		    return;

		String stmt = "";
		try
		{
			stmt = "UPDATE "
				 + getTableName()
				 + " SET "
				 + sqlValues
				 + " "
				 + sqlWhere;
			PreparedStatement ps = cnt.prepareStatement(stmt);

			int idx = 1;
			it = iterator();

			while (it.hasNext())
			{
				member = getMemberDAO(it);
				if (!member.getColumn().isMultivalue() && member.isDirty())
				{
					member.setObject(ps, idx++);
					member.cleanDirty();
				}
			}

			ps.executeUpdate();
			ps.close();
		}
		catch (Exception e)
		{
			throw new ISPACException(e.getMessage()+ " - SQL: " + stmt + ".", e);
		}
	}

	protected void newMemberDAO(DBColumn col)
	{
		MemberDAO member = new MemberDAO(col, false);
		mMembersMap.put(col.getName(), member);
	}

	private MemberDAO getMemberDAO(String sMember) throws ISPACException
	{
		MemberDAO mbm = (MemberDAO) mMembersMap.get(sMember);
		if (mbm == null)
			throw new ISPACPropertyUnknown("No existe la propiedad [" + sMember + "] en el objeto "+ getTableName() +".");
		return mbm;
	}

	protected void setMemberDAO(String sMember, Object value, boolean bClean)
			throws ISPACException
	{
		MemberDAO member = (MemberDAO) mMembersMap.get(sMember);
		if (member == null)
			throw new ISPACPropertyUnknown("No existe la propiedad [" + sMember + "] en el objeto "+ getTableName() +".");

		if (((member.getValue() == null) && (value != null)) ||
			((member.getValue() != null) && (!member.getValue().equals(value)))) {

			member.setValue(value);
			if (bClean) {
				member.cleanDirty();
			}
		}
	}

	protected Iterator iterator()
	{
		return mMembersMap.entrySet().iterator();
	}

	protected MemberDAO getMemberDAO(Iterator it)
	{
		return (MemberDAO) (((Map.Entry) it.next()).getValue());
	}

	public String toString()
	{
		String sRet = null;
		Iterator it = iterator();
		while (it.hasNext())
		{
			sRet += getMemberDAO(it).toString();
		}
		return sRet;
	}
	public boolean isNull(String sMember) throws ISPACException
	{
		return (getMemberDAO(sMember).value()==null);
	}
	public Object get(String sMember) throws ISPACException
	{
		return getMemberDAO(sMember).value();
	}

	public Object getKey() throws ISPACException
	{
		return getMemberDAO(getKeyName()).value();
	}

	public Integer getKeyInteger() throws ISPACException
	{
		Number value = (Number) getMemberDAO(getKeyName()).value();
		if (value != null)
		{
			return new Integer(value.intValue());
		}
		return null;
	}

	public Long getKeyLong() throws ISPACException
	{
		Number value = (Number) getMemberDAO(getKeyName()).value();
		if (value != null)
		{
			return new Long(value.longValue());
		}
		return null;
	}

	public int getKeyInt() throws ISPACException
	{
		Number value = (Number) getMemberDAO(getKeyName()).value();
		if (value != null)
		{
			return value.intValue();
		}
		return ISPACEntities.ENTITY_NULLREGKEYID;
	}
	
	public int getKeyInt(String sMember) throws ISPACException
	{
		Number value = (Number) getMemberDAO(sMember).value();
		if (value != null)
		{
			return value.intValue();
		}
		return ISPACEntities.ENTITY_NULLREGKEYID;
	}

	public int getInt(String sMember) throws ISPACException
	{
		Number value = (Number) getMemberDAO(sMember).value();
		if (value != null)
		{
			return value.intValue();
		}
		return Integer.MIN_VALUE;
	}

	public long getLong(String sMember) throws ISPACException
	{
		Number value = (Number) getMemberDAO(sMember).value();
		if (value != null)
		{
			return value.longValue();
		}
		return Long.MIN_VALUE;
	}

	public short getShort(String sMember) throws ISPACException
	{
		Number value = (Number) getMemberDAO(sMember).value();
		if (value != null)
		{
			return value.shortValue();
		}
		return Short.MIN_VALUE;
	}

	public float getFloat(String sMember) throws ISPACException
	{
		Number value = (Number) getMemberDAO(sMember).value();
		if (value != null)
		{
			return value.floatValue();
		}
		return Float.MIN_VALUE;
	}

	public double getDouble(String sMember) throws ISPACException
	{
		Number value = (Number) getMemberDAO(sMember).value();
		if (value != null)
		{
			return value.doubleValue();
		}
		return Double.MIN_VALUE;
	}

	public BigDecimal getBigDecimal(String sMember) throws ISPACException
	{
		return (BigDecimal) getMemberDAO(sMember).value();
	}

	public byte getByte(String sMember) throws ISPACException
	{
		Number value = (Number) getMemberDAO(sMember).value();
		if (value != null)
		{
			return value.byteValue();
		}
		return Byte.MIN_VALUE;
	}

	public String getString(String sMember) throws ISPACException
	{
		MemberDAO member=getMemberDAO(sMember);
		//Caso multivalor
		if(member.getValue() instanceof Object []){
			String valor="";
			Object [] valores=(Object[]) member.getValue();
			if(valores!=null && valores.length>0){
				valor=valores[0].toString();
				for(int i=1; i<valores.length; i++){
					valor+=", "+valores[i];
				}
			}
			return valor;
		}
	return member.toString();
	}

	public Date getDate(String sMember) throws ISPACException
	{
		return (Date) getMemberDAO(sMember).value();
	}

	public void set(String sMember, Object value) throws ISPACException
	{
		setMemberDAO(sMember, value, false);
	}

	public void setKey(Object value) throws ISPACException
	{
		setMemberDAO(getKeyName(), value, false);
	}

	public void setKey(int value) throws ISPACException
	{
		setMemberDAO(getKeyName(), new Integer(value), false);
	}

	public void set(String sMember, int value) throws ISPACException
	{
		setMemberDAO(sMember, new Integer(value), false);
	}

	public void set(String sMember, long value) throws ISPACException
	{
		setMemberDAO(sMember, new Long(value), false);
	}

	public void set(String sMember, short value) throws ISPACException
	{
		setMemberDAO(sMember, new Short(value), false);
	}

	public void set(String sMember, float value) throws ISPACException
	{
		setMemberDAO(sMember, new Float(value), false);
	}

	public void set(String sMember, double value) throws ISPACException
	{
		setMemberDAO(sMember, new Double(value), false);
	}

	public void set(String sMember, BigDecimal value) throws ISPACException
	{
		setMemberDAO(sMember, value, false);
	}

	public void set(String sMember, byte value) throws ISPACException
	{
		setMemberDAO(sMember, new Byte(value), false);
	}

	public void set(String sMember, String value) throws ISPACException
	{
		setMemberDAO(sMember, value, false);
	}

	public void set(String sMember, Date value) throws ISPACException
	{
		setMemberDAO(sMember, value, false);
	}

	public void store(IClientContext context) throws ISPACException
	{
		DbCnt cnt = context.getConnection();
		try
		{	
			store(cnt, context);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}
	
	protected void executeLoadRules(IClientContext context) throws ISPACException {
	}

	public void load(IClientContext context) throws ISPACException
	{
		DbCnt cnt = context.getConnection();
		try
		{
			load(cnt);
			executeLoadRules(context);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	protected void executeDeleteRules(IClientContext context) throws ISPACException {
	}
	
	public void delete(IClientContext context) throws ISPACException
	{
		DbCnt cnt = context.getConnection();
		try
		{
			executeDeleteRules(context);
			delete(cnt);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	public void createNew(DbCnt cnt) throws ISPACException
	{
		Iterator it = iterator();
		while (it.hasNext())
		{
			//getMemberDAO(it).setValue(null);
			getMemberDAO(it).cleanDirty();
		}
		newObject(cnt);
		mbNewObject = true;
	}

	/**
     * @deprecated
	 * @throws ISPACException
	 */
	public void store() throws ISPACException
	{
		DbCnt cnt = new DbCnt();
		cnt.getConnection();
		try
		{
			store(cnt);
		}
		finally
		{
			cnt.closeConnection();
		}
	}

	/**
     * @deprecated
	 * @throws ISPACException
	 */
	public void load() throws ISPACException
	{
		DbCnt cnt = new DbCnt();
		cnt.getConnection();
		try
		{
			load(cnt);
		}
		finally
		{
			cnt.closeConnection();
		}
	}

	/**
     * @deprecated
	 * @throws ISPACException
	 */
	public void delete() throws ISPACException
	{
		DbCnt cnt = new DbCnt();
		cnt.getConnection();
		try
		{
			delete(cnt);
		}
		finally
		{
			cnt.closeConnection();
		}
	}
	
	public void store(DbCnt cnt) throws ISPACException
	{
		if (mbNewObject){
			storeData(cnt, getDefaultSQL(ActionDAO.STORE));
		}else{
			updateData(cnt, getDefaultSQL(ActionDAO.UPDATE));
		}
		storeDataMultivalue(cnt, getDefaultSQL(ActionDAO.UPDATE));
	}

	protected void executeNewRules(IClientContext context) throws ISPACException {
	}
	
	protected void executeStoreRules(IClientContext context) throws ISPACException {
	}
	
	public void store(DbCnt cnt, IClientContext context) throws ISPACException
	{
		if (mbNewObject) {
			executeNewRules(context);
			storeData(cnt, getDefaultSQL(ActionDAO.STORE));
		}
		else {
			executeStoreRules(context);
			updateData(cnt, getDefaultSQL(ActionDAO.UPDATE));
		}
		storeDataMultivalue(cnt, getDefaultSQL(ActionDAO.STORE));

	}

	public void load(DbCnt cnt) throws ISPACException
	{
		load(cnt, getDefaultSQL(ActionDAO.LOAD));
	}
    
	public void store(DbCnt cnt, String sqlWhere) throws ISPACException
	{
		if (mbNewObject){
			storeData(cnt, sqlWhere);
		}else{
			updateData(cnt, sqlWhere);
		}
		storeDataMultivalue(cnt, sqlWhere);
	}

	protected void storeDataMultivalue(DbCnt cnt, String sqlWhere) throws ISPACException {
		//Para los ObjectDAO no hay campos multivalue a persistir
	}
	
	public String getColsSQL()
	{
		return mTableDesc.getColsSQL();
	}

	public void load(DbCnt cnt, String sqlWhere) throws ISPACException
	{
		String stmt = "SELECT "
			+ mTableDesc.getColsSQL() + " FROM " + getTableName();

        if (sqlWhere != null)
			stmt += " " + sqlWhere;

		DbResultSet dbrs = null;
		try
		{
			dbrs = cnt.executeQuery(stmt);
			
			if (!dbrs.getResultSet().next())
				throw new ISPACNullObject("No se ha encontrado el objeto. SQL: " + stmt);

			processRowData(dbrs.getResultSet());			
		}
		catch (SQLException e)
		{
			throw new ISPACException("SQL: " + stmt, e);
		}
        finally
        {
        	if (dbrs != null) {
        		dbrs.close();
        	}
        }
	}

	public void delete(DbCnt cnt) throws ISPACException
	{
		String delsql = getDefaultSQL(ActionDAO.DELETE);

		if (delsql == null || delsql == "")
			throw new ISPACException("ObjectDAO.delete() - Se requiere un filtro para el borrado de datos ");
		String sql = "DELETE FROM " + getTableName() + " " + delsql;

		cnt.directExec(sql);

		mbNewObject = true;
	}

	public void load(ResultSet rs) throws ISPACException
	{
		processRowData(rs);
	}
	
	public void load(ResultSet rs, Map columns) throws ISPACException
	{
		processRowData(rs, columns);
	}

	public String getXmlValues()
	{
		StringBuffer sXml = new StringBuffer();

		Iterator it = iterator();
		while (it.hasNext())
		{
			sXml.append(getMemberDAO(it).toXml());
		}

		return  sXml.toString();
	}

	public String toXml()
	{
		String sXml = null;

		// Propiedades del objeto
		String sProperties = getProperties().toXML();
		// Valores del objeto
		String sValues = XmlTag.newTag("values", getXmlValues());
		sXml = XmlTag.getXmlInstruction("ISO-8859-1")
				 + XmlTag.newTag("item", sProperties + sValues);

		return sXml;
	}

	public Map toMap()
	{
		LinkedHashMap valuemap = new LinkedHashMap();
		Iterator it = iterator();

		while (it.hasNext())
		{
			MemberDAO mbm = getMemberDAO(it);
			valuemap.put(mbm.getName(), mbm.value());
		}
		return valuemap;
	}

	public int getColumnType (String name)
	{
		DBColumn column = (DBColumn) mTableDesc.get(name);
		return column.getType();
	}

	public Properties getProperties()
	{
		return mTableDesc.getProperties();
	}
	public Property getProperty(String name)
	{
		return mTableDesc.getProperty(name);
	}

	public boolean copy(ObjectDAO obj,boolean copyPropKey) throws ISPACException
	{
	    boolean allpropertiescopied=true;

	    Properties properties=getProperties();
	    String keyname=getKeyName();

	    Iterator it=properties.iterator();
	    while (it.hasNext())
        {
            Property prop = (Property) it.next();
            String propname=prop.getName();

            //Si no se indica lo contrario se omite el copiar el campo clave
            if (!copyPropKey&&propname.equalsIgnoreCase(keyname))
                continue;

            if (obj.getProperty(propname)!=null)
                this.set(propname,obj.get(propname));
            else
                allpropertiescopied=false;
        }
	    return allpropertiescopied;
	}

	/** Crea un nuevo objeto del mismo tipo que contiene las mismas propiedades
	 * y valores excepto la propiedad clave.
	 * @param cnt
	 * @return Objeto duplicado pero con distinta clave.
	 * @throws ISPACException
	 */
	public ObjectDAO duplicate(DbCnt cnt) throws ISPACException
	{
	    Class classobjdao=this.getClass();
	    try
		{
		    //Constructor sin parámetros.
	        //Es necesario reescribirla en TableDAO y EntityDAO para
	        //que funcione adecuadamente.

	        ObjectDAO obj = (ObjectDAO) classobjdao.newInstance();
			obj.initTableDesc(mTableDesc);
			obj.createNew(cnt);
			obj.copy(this,false);
			return obj;
		}
		catch (Exception e)
		{
			throw new ISPACException("Error en ObjectDAO:duplicate() ", e);
		}
	}
	
	public static CollectionDAO getByIds(DbCnt cnt, Class cls, Map mapIds, String sqlOrderBy) throws ISPACException {
		
		String ids = "";
		Iterator it = mapIds.keySet().iterator();
		while (it.hasNext()) {
			
			ids += it.next().toString() + ", ";
		}
		ids = ids.substring(0, ids.length() - 2);
		
		String sql = "WHERE ID IN (" + ids + ") ";
		
		// Ordenación
		if (StringUtils.isNotBlank(sqlOrderBy)) {
			sql = sql + sqlOrderBy;
		}
		 
		CollectionDAO collection = new CollectionDAO(cls);
		collection.query(cnt,sql);
		return collection;
	}
	
	public static ObjectDAO getByName(DbCnt cnt, Class cls, String name) throws ISPACException {
				
		String sql = "WHERE NOMBRE = '" + DBUtil.replaceQuotes(name) + "'";
		 
		CollectionDAO collection = new CollectionDAO(cls);
		collection.query(cnt,sql);
		if (collection.next()) {
			return collection.value();
		}
		return null;
	}
	
	public static CollectionDAO getByNames(DbCnt cnt, Class cls, Map mapNames, String sqlOrderBy) throws ISPACException {
		
		String names = "";
		Iterator it = mapNames.keySet().iterator();
		while (it.hasNext()) {
			
			String name = (String) it.next();
			names += "'" + DBUtil.replaceQuotes(name) + "', ";
		}
		names = names.substring(0, names.length() - 2);
		
		String sql = "WHERE NOMBRE IN (" + names + ") ";
		
		// Ordenación
		if (StringUtils.isNotBlank(sqlOrderBy)) {
			sql = sql + sqlOrderBy;
		}
		 
		CollectionDAO collection = new CollectionDAO(cls);
		collection.query(cnt,sql);
		return collection;
	}
	
	public static CollectionDAO getForUpdate(DbCnt cnt, Class cls, String sqlQuery) throws ISPACException {
		
		CollectionDAO collection = new CollectionDAO(cls);
		collection.queryForUpdate(cnt, sqlQuery);
		return collection;
	}

	public void reset() throws ISPACException{
		Iterator it = mTableDesc.iterator();
		while (it.hasNext())
		{
			DBColumn col = mTableDesc.getColumn(it);
			setMemberDAO(col.getName(), (Object)null, true);
		}
	}
	
	public void reset(boolean keepFieldId) throws ISPACException{
		if (!keepFieldId){
			reset();
			return;
		}
		Iterator it = mTableDesc.iterator();
		while (it.hasNext())
		{
			DBColumn col = mTableDesc.getColumn(it);
			if (!StringUtils.equals(col.getName(),getKeyName()) && !isFieldNumExp(col.getName()) && !isFieldId(col.getName())  ){					
				setMemberDAO(col.getName(), (Object)null, true);
			}
		}

	}
	private boolean isFieldNumExp (String propertyName){
		String[] split = StringUtils.split(propertyName, ":");
		return StringUtils.equals(split[split.length-1], "NUMEXP");
	}

	private boolean isFieldId (String propertyName){
		String[] split = StringUtils.split(propertyName, ":");
		return StringUtils.equalsIgnoreCase(split[split.length-1], "ID");
	}	

	public void loadMultivalues(DbCnt cnt) throws ISPACException {
		return;
	}
	
	public void loadMultivalues(DbCnt cnt, Map columns) throws ISPACException {
		return;
	}
	
	
	
	public abstract String getTableName();

	protected abstract String getDefaultSQL(int nActionDAO) throws ISPACException;

	protected abstract void newObject(DbCnt cnt) throws ISPACException;

	public abstract String getKeyName() throws ISPACException;
	public void setPosibleProcessEntity(boolean isPosibleProcessEntity) {
		this.isPosibleProcessEntity = isPosibleProcessEntity;
	}
	public boolean isPosibleProcessEntity() {
		return isPosibleProcessEntity;
	}

	public boolean isString(String sMember) throws ISPACException {
		MemberDAO member = getMemberDAO(sMember);
		DBColumn column = member.getColumn();
		return column.getType() == Types.VARCHAR;
	}
	
}