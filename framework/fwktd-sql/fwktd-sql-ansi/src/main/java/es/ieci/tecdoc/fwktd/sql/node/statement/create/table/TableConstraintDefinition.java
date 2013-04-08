/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.create.table;

import java.util.List;

/**
 * Definición de una constante para una tabla.
 */
public abstract class TableConstraintDefinition extends TableElement {

	public List<String> getColumnsNames() {
		return columnsNames;
	}

	public void setColumnsNames(List<String> aColumnsNames) {
		this.columnsNames = aColumnsNames;
	}
	
	// Members
	
	private static final long serialVersionUID = 3316231515657205040L;

	private List<String> columnsNames;
}