package ieci.tdw.ispac.ispaclib.templates;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;

public class TemplateTableInfo {
	private String tableName; 
	private String[] columns;
	private String[] titleColumns;
	private IItemCollection results;
	public TemplateTableInfo(String tableName, String[] columns,
			String[] titleColumns) {
		setTableName(tableName);
		setColumns(columns);
		setTitleColumns(titleColumns);
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String[] getColumns() {
		return columns;
	}
	public void setColumns(String[] columns) {
		this.columns = columns;
	}
	public String[] getTitleColumns() {
		return titleColumns;
	}
	public void setTitleColumns(String[] titleColumns) {
		this.titleColumns = titleColumns;
	}
	public IItemCollection getResults() {
		return results;
	}
	public void setResults(IItemCollection results) {
		this.results = results;
	}
	public int getSize() throws ISPACException {
		return results.toList().size();
	}
}
