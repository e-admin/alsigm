/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.functions.date;

import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.expression.Function;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.LongValue;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Función de Suma o resta de meses a una fecha.
 */
public class AddMonthsFunction extends Function {

	/**
	 * Constructor
	 * 
	 * @param anExpression
	 *            Expresion tipo fecha
	 * @param units
	 *            número de meses a sumar o restar.
	 */
	public AddMonthsFunction(IExpression anExpression, LongValue units) {

		Assert.notNull(anExpression);
		setDateExpression(anExpression);
		setUnits(units);
	}

	public void accept(ExpressionVisitor anExpressionVisitor) {
		anExpressionVisitor.visit(this);
	}

	public IExpression getDateExpression() {
		return dateExpression;
	}

	public void setDateExpression(IExpression aDateExpression) {
		this.dateExpression = aDateExpression;
	}

	public LongValue getUnits() {
		return units;
	}

	public void setUnits(LongValue units) {
		this.units = units;
	}

	public boolean isOrContains(ISqlNode aNode) {

		if (super.isOrContains(aNode)) {
			return true;
		}

		if (getDateExpression() != null
				&& getDateExpression().isOrContains(aNode)) {
			return true;
		}

		return false;
	}
	
	// Members
	
	private static final long serialVersionUID = -4264825587222832734L;

	/**
	 * Expresion Fecha
	 */
	protected IExpression dateExpression;

	/**
	 * Nº de meses.
	 */
	protected LongValue units = new LongValue((long) 0);
}
