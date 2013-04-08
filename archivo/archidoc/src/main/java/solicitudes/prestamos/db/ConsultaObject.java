package solicitudes.prestamos.db;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Objeto que encapsula los elementos de una consulta, es decir las tablas, el
 * where, el order by
 */
public class ConsultaObject {
	private String tables = null;
	private String where = null;
	private String orderBy = null;
	private Set tableList = null;

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

	public void addTable(String tableName) {
		// if (tables.trim().length()>0)
		// tables += tables + "," + tableName;
		if (tableList == null)
			tableList = new HashSet();

		tableList.add(tableName);
	}

	public Collection getTableList() {
		if (tableList == null)
			tableList = new HashSet();

		return tableList;
	}
}
