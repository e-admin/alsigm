/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.functions.agregate;

import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Función conteo.
 */
public class CountFunction extends AggregateFunction {

	public CountFunction() {
		super();
		setAllColumns(true);
	}

	public CountFunction(IExpression expression) {
		super(expression);
		setAllColumns(false);
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public boolean isAllColumns() {
		return allColumns;
	}
	
	public void setAllColumns(boolean aAllColumns){
		this.allColumns = aAllColumns;
	}
	
	// Members
	
	private static final long serialVersionUID = 648560639971329688L;
	
	/**
	 * Todas las columnas = *
	 */
	protected boolean allColumns = true;
}
