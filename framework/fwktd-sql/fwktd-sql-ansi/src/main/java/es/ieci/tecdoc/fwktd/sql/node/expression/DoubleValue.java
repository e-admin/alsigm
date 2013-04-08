/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression;

import java.text.DecimalFormat;

import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;

/**
 * Constante Flotante: Every number with a point or a exponential format is a
 * DoubleValue.
 */
public class DoubleValue extends Expression {

	/**
	 * Constructor.
	 * 
	 * @param aValue
	 *            Valor <code>Double</code>
	 */
	public DoubleValue(Double aValue) {
		Assert.notNull(aValue);
		setValue(aValue);
	}

	/**
	 * Constructor.
	 * 
	 * @param aValue
	 *            Valor <code>String</code>.
	 */
	public DoubleValue(String aValue) {
		Assert.notNull(aValue);
		setStringDouble(aValue);
	}

	/**
	 * Constructor
	 * 
	 * @param aValue
	 *            Valor <code>String</code>.
	 * @param aMask
	 *            Mascara del value
	 */
	public DoubleValue(String aValue, String aMask) {
		Assert.notNull(aValue);
		Assert.notNull(aMask);
		setStringDouble(aValue, aMask);
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getValue() {
		return value;
	}

	/**
	 * Convierte un String a Double
	 * 
	 * @param value
	 *            String value de un double
	 */
	private void setStringDouble(String value) {
		CustomNumberEditor editor = new CustomNumberEditor(Double.class, false);
		editor.setAsText(value);
		this.value = (Double) editor.getValue();
	}

	/**
	 * Convierte un String a Double
	 * 
	 * @param value
	 *            String value de un double
	 * @param mask
	 *            Mascara del formato String como double
	 */
	private void setStringDouble(String value, String mask) {
		CustomNumberEditor editor = new CustomNumberEditor(Double.class,
				new DecimalFormat(mask), false);
		editor.setAsText(value);
		this.value = (Double) editor.getValue();
	}

	// Members

	private static final long serialVersionUID = -3080985247221437927L;

	/**
	 * Constante Double.
	 */
	private Double value;
}
