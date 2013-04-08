/*
 * 
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import es.ieci.tecdoc.fwktd.sql.node.visitor.SelectBodyVisitor;

/**
 * An index of a column in expressions as "GROUP BY 1" or as "ORDER BY 1,2".
 */
public class ColumnIndex extends ColumnReference {

	public ColumnIndex(int anIndex) {
		super();
		setIndex(anIndex);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int i) {
		index = i;
	}

	public void accept(SelectBodyVisitor aSelectVisitor) {
		aSelectVisitor.visit(this);
	}

	// Members

	private static final long serialVersionUID = 4595907981546121318L;

	private int index;

}
