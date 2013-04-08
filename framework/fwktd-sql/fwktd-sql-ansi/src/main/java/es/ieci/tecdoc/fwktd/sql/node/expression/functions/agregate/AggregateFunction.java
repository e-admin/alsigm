/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.functions.agregate;

import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.expression.Function;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Clase base para las funciones de agregación.
 */
public abstract class AggregateFunction extends Function {

	public AggregateFunction() {
		super();
	}

	public AggregateFunction(IExpression anExpression) {
		this();
		
		Assert.notNull(anExpression);
		setExpression(anExpression);
	}

	public void accept(ExpressionVisitor anExpressionVisitor) {
		anExpressionVisitor.visit(this);
	}

	public IExpression getExpression() {
		return expression;
	}

	public void setExpression(IExpression anExpression) {
		this.expression = anExpression;
	}

	public boolean isOrContains(ISqlNode aNode) {

		if (super.isOrContains(aNode)) {
			return true;
		}

		if (getExpression() != null && getExpression().isOrContains(aNode)) {
			return true;
		}

		return false;
	}
	
	// Members

	private static final long serialVersionUID = 86787466107483882L;
	
	/**
	 * Expresion
	 */
	protected IExpression expression;
}
