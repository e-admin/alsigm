package solicitudes.consultas.db;

import ieci.core.db.DbColumnDef;

import java.util.HashMap;

/**
 * Objeto que encapsula los elementos de una consulta SQL, es decir las tablas,
 * el where, el order by
 */
public class ConsultaObject {
	private HashMap pairsTableNamesColsDefs = new HashMap();
	private String tables = null;
	private String where = null;
	private String orderBy = null;

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getTables() {
		return tables;
	}

	public void setTables(String tables) {
		this.tables = tables;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public HashMap getPairsTableNamesColsDefs() {
		return pairsTableNamesColsDefs;
	}

	public void addPair(String tableName, DbColumnDef dbColDef[]) {
		pairsTableNamesColsDefs.put(tableName, dbColDef);
	}
}
