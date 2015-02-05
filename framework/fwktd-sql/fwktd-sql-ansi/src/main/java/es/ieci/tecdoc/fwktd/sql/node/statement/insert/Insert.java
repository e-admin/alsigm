/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.insert;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.IItemsList;
import es.ieci.tecdoc.fwktd.sql.node.schema.Column;
import es.ieci.tecdoc.fwktd.sql.node.statement.Statement;
import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;

/**
 * Sentencia "INSERT".
 */
public class Insert extends Statement {

	public void accept(StatementVisitor aStatementVisitor) {
		aStatementVisitor.visit(this);
	}

	/**
	 * Get the columns (found in "INSERT INTO (col1,col2..) [...]" )
	 * 
	 * @return a list of Column
	 */
	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> aList) {
		columns = aList;
	}

	/**
	 * Get the values (as VALUES (...) or SELECT)
	 * 
	 * @return the values of the insert
	 */
	public IItemsList getValuesList() {
		return valuesList;
	}

	public void setValuesList(IItemsList aList) {
		valuesList = aList;
	}

	public boolean isUseValues() {
		return useValues;
	}

	public void setUseValues(boolean aUseValues) {
		this.useValues = aUseValues;
	}

	@Override
	public boolean isOrContains(ISqlNode aNode) {
		if (super.isOrContains(aNode)) {
			return true;
		}

		if (CollectionUtils.isNotEmpty(getColumns())) {
			for (Column column : getColumns()) {
				if (column.isOrContains(aNode)) {
					return true;
				}
			}
		}

		return (getValuesList() != null && getValuesList().isOrContains(aNode));
	}
	
	// Members
	
	private static final long serialVersionUID = 8093597091001934301L;

	/** Columnas a las que dar valor */
	private List<Column> columns;

	/** Valores */
	private IItemsList valuesList;

	private boolean useValues = true;

}
