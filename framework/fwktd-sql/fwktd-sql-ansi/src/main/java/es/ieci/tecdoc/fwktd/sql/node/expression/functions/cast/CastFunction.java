/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.functions.cast;

import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.expression.Function;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Función para conversiones de tipos.
 */
public abstract class CastFunction extends Function {

	public CastFunction(IExpression anExpression) {
		this(anExpression, null);
	}

	public CastFunction(IExpression anExpression, String aMask) {
		Assert.notNull(anExpression);
		setExpression(anExpression);
		setMask(aMask);
	}

	public void accept(ExpressionVisitor anExpressionVisitor) {
		anExpressionVisitor.visit(this);
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String aMask) {
		this.mask = aMask;
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
	
	private static final long serialVersionUID = 3471064785341923529L;

	/** Expresion */
	protected IExpression expression;

	/**
	 * Mascara java de formato para conversión de String
	 */
	protected String mask;
}
