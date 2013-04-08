/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.replace;

import java.util.List;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.IItemsList;
import es.ieci.tecdoc.fwktd.sql.node.statement.Statement;
import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;

/**
 * Sentencia "REPLACE".
 */
public class Replace extends Statement {

	public void accept(StatementVisitor aStatementVisitor) {
		aStatementVisitor.visit(this);
	}

	public List<String> getColumns() {
		return columns;
	}

	/**
	 * An {@link IItemsList} (either from a "REPLACE mytab VALUES (exp1,exp2)"
	 * or a "REPLACE mytab SELECT * FROM mytab2") it is null in case of a
	 * "REPLACE mytab SET col1=exp1, col2=exp2"
	 */
	public IItemsList getItemsList() {
		return itemsList;
	}

	public void setColumns(List<String> aList) {
		columns = aList;
	}

	public void setItemsList(IItemsList aList) {
		itemsList = aList;
	}

	/**
	 * A list of Expressions (from a "REPLACE mytab SET col1=exp1, col2=exp2"). <br>
	 * it is null in case of a "REPLACE mytab (col1, col2) [...]"
	 */
	public List<String> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<String> aList) {
		expressions = aList;
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
		return (getItemsList() != null && getItemsList().isOrContains(aNode));
	}

	// Members

	private static final long serialVersionUID = -7128587446078296834L;

	private List<String> columns;

	private IItemsList itemsList;

	private List<String> expressions;

	private boolean useValues = true;

}
