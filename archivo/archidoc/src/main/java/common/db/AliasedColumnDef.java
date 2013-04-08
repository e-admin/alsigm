package common.db;

import ieci.core.db.DbColumnDef;

public class AliasedColumnDef extends DbColumnDef {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public AliasedColumnDef(DbColumnDef column, TableDef table) {
		super(column.getBindPropertyVO(), table.getName(), column.getName(),
				column.getDataType(), column.getMaxLen(), column.isNullable());
	}

	public AliasedColumnDef(String bindPropertyVO, String name, int dataType,
			boolean nullable, TableDef table) {
		super(bindPropertyVO, table.getName(), name, dataType, nullable);
	}
}