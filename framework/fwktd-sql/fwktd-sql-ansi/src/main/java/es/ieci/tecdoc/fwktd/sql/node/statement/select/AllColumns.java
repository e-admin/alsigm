/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import es.ieci.tecdoc.fwktd.sql.node.visitor.SelectBodyVisitor;

/**
 * Clase representando la selección de todas las columnas (p.e.
 * "SELECT * FROM ...").
 */
public class AllColumns extends SelectItem {

	public void accept(SelectBodyVisitor aSelectVisitor) {
		aSelectVisitor.visit(this);
	}

	// Members

	private static final long serialVersionUID = 7896289092446943713L;

}
