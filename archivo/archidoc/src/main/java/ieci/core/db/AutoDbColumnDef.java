package ieci.core.db;

public class AutoDbColumnDef extends DbColumnDef {

	public AutoDbColumnDef(String bindPropertyVO, DbTableDef tableDef,
			String name) {
		super(bindPropertyVO, tableDef.getAlias() != null
				&& tableDef.getAlias().length() > 0 ? tableDef.getAlias()
				: tableDef.getName(), name, -1, false);
	}

	public AutoDbColumnDef(String bindPropertyVO, DbTableDef tableDef,
			String name, int tipo) {
		super(bindPropertyVO, tableDef.getAlias() != null
				&& tableDef.getAlias().length() > 0 ? tableDef.getAlias()
				: tableDef.getName(), name, tipo, false);
	}

}
