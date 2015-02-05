/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.functions.character;

import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.expression.Function;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Función de concatenación de expresiones.
 */
public class ConcatFunction extends Function {


	public ConcatFunction(IExpression aFirstExpression,
			IExpression aSecondExpression) {
		Assert.notNull(aFirstExpression);
		Assert.notNull(aSecondExpression);
		setFirstExpression(aFirstExpression);
		setSecondExpression(aSecondExpression);
	}

	public void accept(ExpressionVisitor anExpressionVisitor) {
		anExpressionVisitor.visit(this);
	}

	public IExpression getFirstExpression() {
		return firstExpression;
	}

	public void setFirstExpression(IExpression aFirstExpression) {
		this.firstExpression = aFirstExpression;
	}

	public IExpression getSecondExpression() {
		return secondExpression;
	}

	public void setSecondExpression(IExpression aSecondExpression) {
		this.secondExpression = aSecondExpression;
	}

	public boolean isOrContains(ISqlNode aNode) {

		if (super.isOrContains(aNode)) {
			return true;
		}

		if (getFirstExpression() != null
				&& getFirstExpression().isOrContains(aNode)) {
			return true;
		}

		if (getSecondExpression() != null
				&& getSecondExpression().isOrContains(aNode)) {
			return true;
		}

		return false;
	}
	
	// Members
	
	private static final long serialVersionUID = 1270172380386480023L;

	/**
	 * Primera expresión.
	 */
	protected IExpression firstExpression;

	/**
	 * Segunda expresión.
	 */
	protected IExpression secondExpression;

}
