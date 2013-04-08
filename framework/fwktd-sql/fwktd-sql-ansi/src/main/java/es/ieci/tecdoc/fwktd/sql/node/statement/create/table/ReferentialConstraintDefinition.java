/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.create.table;

import java.util.List;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;

/**
 * Definición de una foreign key.
 */
public class ReferentialConstraintDefinition extends TableConstraintDefinition {

	public ReferentialConstraintDefinition(String aName,
			List<String> aColumnsNames, Table aReferencedTable,
			List<String> aReferencedColumns) {
		super();
		setName(aName);
		setColumnsNames(aColumnsNames);
		setReferencedTable(aReferencedTable);
		setReferencedColumns(aReferencedColumns);
	}

	@Override
	public void accept(StatementVisitor aStatementVisitor) {
		aStatementVisitor.visit(this);
	}

	public List<String> getReferencedColumns() {
		return referencedColumns;
	}

	public void setReferencedColumns(List<String> aReferencedColumns) {
		this.referencedColumns = aReferencedColumns;
	}

	public Table getReferencedTable() {
		return referencedTable;
	}

	public void setReferencedTable(Table aReferencedTable) {
		this.referencedTable = aReferencedTable;
	}

	@Override
	public boolean isOrContains(ISqlNode aNode) {
		if (super.isOrContains(aNode)) {
			return true;
		}
		return (getReferencedTable() != null && getReferencedTable()
				.isOrContains(aNode));
	}

	// Members

	private static final long serialVersionUID = 6109307642753841684L;

	/** Tabla a la que referencia la fk */
	private Table referencedTable;

	/** Columnas a las que referencia la fk */
	private List<String> referencedColumns;

}
