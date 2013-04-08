package ieci.core.db;

import java.io.Serializable;

import common.db.PropertyNoAccesible;
import common.db.TableDef;
import common.util.StringUtils;

public class DbColumnDef implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String m_name;
	protected int m_dataType;
	protected int m_maxLen;
	protected boolean m_nullable;

	protected String bindPropertyVO = null;
	protected String tableName = null;

	protected InfoOuterJoin outerJoinInfo = null;
	private TableDef tableDef = null;

	public DbColumnDef() {
	}

	public DbColumnDef(String bindPropertyVO, TableDef tableDef, String name,
			int dataType, boolean nullable) {
		this(bindPropertyVO, tableDef.getAlias(), name, dataType, nullable);
		this.tableDef = tableDef;
	}

	public DbColumnDef(String bindPropertyVO, String tableName, String name,
			int dataType, boolean nullable) {
		this(name, dataType, nullable);
		this.bindPropertyVO = bindPropertyVO;
		if (!StringUtils.isEmpty(tableName))
			this.tableDef = new TableDef(tableName);
		this.tableName = tableName;
	}

	public DbColumnDef(String bindPropertyVO, String tableName, String name,
			int dataType, int maxLen, boolean nullable) {
		this(name, dataType, maxLen, nullable);
		this.bindPropertyVO = bindPropertyVO;
		if (!StringUtils.isEmpty(tableName))
			this.tableDef = new TableDef(tableName);
		this.tableName = tableName;
	}

	public DbColumnDef(String bindPropertyVO, String name, int dataType,
			boolean nullable) {
		this(name, dataType, nullable);
		this.bindPropertyVO = bindPropertyVO;
	}

	public DbColumnDef(TableDef tableDef, DbColumnDef columnDef) {
		this(columnDef.getName(), columnDef.getDataType(), columnDef
				.getMaxLen(), columnDef.isNullable());
		this.bindPropertyVO = columnDef.getBindPropertyVO();
		this.tableName = tableDef.getAlias();
		this.tableDef = tableDef;
	}

	public DbColumnDef(String alias, DbColumnDef columnDef) {
		this(columnDef.getName(), columnDef.getDataType(), columnDef
				.getMaxLen(), columnDef.isNullable());
		this.tableName = columnDef.getTableName();
		this.bindPropertyVO = alias;
	}

	public DbColumnDef(String alias, String tableName, DbColumnDef columnDef) {
		this(columnDef.getName(), columnDef.getDataType(), columnDef
				.getMaxLen(), columnDef.isNullable());
		this.tableName = tableName;
		if (!StringUtils.isEmpty(tableName))
			this.tableDef = new TableDef(tableName);
		this.bindPropertyVO = alias;
	}

	public DbColumnDef(String alias, TableDef tableDef, DbColumnDef columnDef) {
		this(columnDef.getName(), columnDef.getDataType(), columnDef
				.getMaxLen(), columnDef.isNullable());
		this.tableName = tableDef.getAlias();
		this.tableDef = tableDef;
		this.bindPropertyVO = alias;
	}

	public DbColumnDef(String name, String alias, TableDef tableDef,
			DbColumnDef columnDef) {
		this(name, columnDef.getDataType(), columnDef.getMaxLen(), columnDef
				.isNullable());
		this.tableName = tableDef.getAlias();
		this.tableDef = tableDef;
		this.bindPropertyVO = alias;
	}

	public DbColumnDef(String alias, String sql) {
		this.m_name = sql;
		this.bindPropertyVO = alias;
	}

	public DbColumnDef(String alias, String sql, int dataType) {
		this.m_name = sql;
		this.bindPropertyVO = alias;
		this.m_dataType = dataType;
	}

	public DbColumnDef(String name, int dataType, boolean nullable) {
		m_name = name;
		m_dataType = dataType;
		m_maxLen = 0;
		m_nullable = nullable;
	}

	public DbColumnDef(String bindPropertyVO, String name, int dataType,
			int maxLen, boolean nullable) {
		this(name, dataType, maxLen, nullable);
		this.bindPropertyVO = bindPropertyVO;
	}

	public DbColumnDef(String name, int dataType, int maxLen, boolean nullable) {
		m_name = name;
		m_dataType = dataType;
		m_maxLen = maxLen;
		m_nullable = nullable;
	}

	public String getBindPropertyVO() {
		return bindPropertyVO;
	}

	public String getAliasOrName() {
		return bindPropertyVO != null ? bindPropertyVO : m_name;
	}

	public String getName() {
		return m_name;
	}

	public String getQualifiedName() {
		if (tableName == null)
			return m_name;
		return new StringBuffer(tableName).append(".").append(m_name)
				.toString();
	}

	public String getTableName() {
		return tableName;
	}

	public int getDataType() {
		return m_dataType;
	}

	public void setDataType(int dataType) {
		this.m_dataType = dataType;
	}

	public int getMaxLen() {
		return m_maxLen;
	}

	public void setMaxLen(int maxLen) {
		this.m_maxLen = maxLen;
	}

	public boolean isNullable() {
		return m_nullable;
	}

	public void setIsNullable(boolean nullable) {
		this.m_nullable = nullable;
	}

	public String toString() {
		return getQualifiedName();
	}

	public String getTableDeclaration() {
		if (this.tableDef != null)
			return this.tableDef.getDeclaration();
		else
			throw new PropertyNoAccesible(
					"Necesario inicializar la clase con el constructor que recibe un tableDef");

	}

} // class
