/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Constante Entera.
 */
public class LongValue extends Expression {

	/**
	 * Constructor.
	 * 
	 * @param aValue
	 *            value Long
	 */
	public LongValue(Long aValue) {
		Assert.notNull(aValue);
		setValue(aValue);
	}

	/**
	 * Constructor.
	 * 
	 * @param aValue
	 *            valor como String
	 */
	public LongValue(String aValue) {
		Assert.notNull(aValue);
		setStringLong(aValue);
	}

	/**
	 * Constructor.
	 * 
	 * @param aValue
	 *            valor como String
	 * @param aMask
	 *            Mascara de formato
	 */
	public LongValue(String aValue, String aMask) {
		Assert.notNull(aValue);
		Assert.notNull(aMask);
		setStringLong(aValue, aMask);
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	/**
	 * Convierte un String a Double
	 * 
	 * @param value
	 *            String value de un double
	 */
	private void setStringLong(String value) {
		CustomNumberEditor editor = new CustomNumberEditor(Number.class, false);
		editor.setAsText(value);
		this.value = ((BigDecimal) editor.getValue()).longValue();
	}

	/**
	 * Convierte un String a Double
	 * 
	 * @param value
	 *            String value de un double
	 * @param mask
	 *            Mascara del formato String como double
	 */
	private void setStringLong(String value, String mask) {
		CustomNumberEditor editor = new CustomNumberEditor(Long.class,
				new DecimalFormat(mask), false);
		editor.setAsText(value);
		this.value = (Long) editor.getValue();
	}
	
	// Members
	
	private static final long serialVersionUID = -772246494537263539L;

	/**
	 * Valor Constante Entera
	 */
	private Long value;

}
