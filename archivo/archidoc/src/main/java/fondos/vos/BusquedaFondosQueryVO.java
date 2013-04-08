package fondos.vos;

import ieci.core.db.DbColumnDef;

public class BusquedaFondosQueryVO {

	String where = null;
	String with = null;
	String orderByIds = null;
	String orderByFullQuery = null;
	String tablesIds = null;
	String tablesFullQuery = null;
	DbColumnDef[] colsIds = null;
	DbColumnDef[] colsIdsFill = null;
	DbColumnDef[] colsFullQuery = null;
	DbColumnDef[] colsFullQueryFill = null;

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getWith() {
		return with;
	}

	public void setWith(String with) {
		this.with = with;
	}

	public String getOrderByIds() {
		return orderByIds;
	}

	public void setOrderByIds(String orderByIds) {
		this.orderByIds = orderByIds;
	}

	public String getOrderByFullQuery() {
		return orderByFullQuery;
	}

	public void setOrderByFullQuery(String orderByFullQuery) {
		this.orderByFullQuery = orderByFullQuery;
	}

	public String getTablesIds() {
		return tablesIds;
	}

	public void setTablesIds(String tablesIds) {
		this.tablesIds = tablesIds;
	}

	public String getTablesFullQuery() {
		return tablesFullQuery;
	}

	public void setTablesFullQuery(String tablesFullQuery) {
		this.tablesFullQuery = tablesFullQuery;
	}

	public DbColumnDef[] getColsIds() {
		return colsIds;
	}

	public void setColsIds(DbColumnDef[] colsIds) {
		this.colsIds = colsIds;
	}

	public DbColumnDef[] getColsIdsFill() {
		return colsIdsFill;
	}

	public void setColsIdsFill(DbColumnDef[] colsIdsFill) {
		this.colsIdsFill = colsIdsFill;
	}

	public DbColumnDef[] getColsFullQuery() {
		return colsFullQuery;
	}

	public void setColsFullQuery(DbColumnDef[] colsFullQuery) {
		this.colsFullQuery = colsFullQuery;
	}

	public DbColumnDef[] getColsFullQueryFill() {
		return colsFullQueryFill;
	}

	public void setColsFullQueryFill(DbColumnDef[] colsFullQueryFill) {
		this.colsFullQueryFill = colsFullQueryFill;
	}

}
