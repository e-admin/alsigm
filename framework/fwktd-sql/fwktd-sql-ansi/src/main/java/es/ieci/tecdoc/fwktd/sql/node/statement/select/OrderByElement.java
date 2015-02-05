/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.SqlNode;
import es.ieci.tecdoc.fwktd.sql.node.visitor.SelectBodyVisitor;

/**
 * Un elemento (referencia a una columna) dentro de una sentencia "ORDER BY".
 */
public class OrderByElement extends SqlNode {

	public OrderByElement(IColumnReference aReference, boolean aAsc) {
		setColumnReference(aReference);
		setAsc(aAsc);
	}

	public OrderByElement(IColumnReference aReference) {
		this(aReference, true);
	}
	
	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean b) {
		asc = b;
	}

	public void accept(SelectBodyVisitor aSelectVisitor) {
		aSelectVisitor.visit(this);
	}

	public IColumnReference getColumnReference() {
		return columnReference;
	}

	public void setColumnReference(IColumnReference aColumnReference) {
		this.columnReference = aColumnReference;
	}

	@Override
	public boolean isOrContains(ISqlNode aNode) {
		if (super.isOrContains(aNode)) {
			return true;
		}
		return (getColumnReference() != null && getColumnReference()
				.isOrContains(aNode));
	}

	// Members

	private static final long serialVersionUID = -8623225284427624897L;

	/** Columna a la que hace referencia */
	private IColumnReference columnReference;

	/** Flag que indica si se debe ordenar ascendente o descendentemente */
	private boolean asc = true;
}
