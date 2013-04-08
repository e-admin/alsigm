package common.db;

import common.vos.BaseVO;

public class TableDef extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String tableName = null;
	String alias = null;
	String declaration = null;

	public TableDef(String tableName, String alias) {
		this.tableName = tableName;
		this.alias = alias;
		declaration = new StringBuffer(tableName).append(" ").append(alias)
				.toString();
	}

	public TableDef(String tableName) {
		this(tableName, tableName);
		// this.tableName = tableName;
		// declaration = new StringBuffer(tableName).append(" ").toString();
		// if (alias!=null)
		// declaration= new StringBuffer(declaration).append(alias).toString();
	}

	public String getAlias() {
		return alias;
	}

	public String getTableName() {
		return tableName;
	}

	public String getName() {
		String name = null;
		if (alias != null)
			name = getAlias();
		else
			name = getTableName();
		return name;
	}

	public String getDeclaration() {
		return this.declaration;
	}
}