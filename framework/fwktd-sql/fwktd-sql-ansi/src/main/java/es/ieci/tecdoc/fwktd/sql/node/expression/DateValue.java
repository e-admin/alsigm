/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Constante Fecha.
 */
public class DateValue extends Expression {

	/**
	 * Constructor
	 * 
	 * @param value
	 *            Date in the form {d 'yyyy-mm-dd'}
	 */
	public DateValue(String value) {
		this(value, DEFAULT_DATE_FORMAT);
	}

	/**
	 * Constructor
	 * 
	 * @param value
	 *            Fecha como String
	 * @param mask
	 *            Mascara de formato de la fecha
	 */
	public DateValue(String value, String mask) {
		this(value, mask, Locale.getDefault());
	}

	/**
	 * Constructor
	 * 
	 * @param value
	 *            Fecha como String
	 * @param mask
	 *            mask
	 * @param locale
	 *            locale
	 */
	public DateValue(String value, String mask, Locale locale) {
		Assert.notNull(value);
		Assert.notNull(mask);
		Assert.notNull(locale, "Locale no debe ser nulo");

		setStringDate(value, mask, locale);
	}

	/**
	 * Constructor
	 * 
	 * @param date
	 *            Fecha.
	 */
	public DateValue(java.util.Date date) {
		Assert.notNull(date);
		value = new Date(date.getTime());
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public Date getValue() {
		return value;
	}

	public void setValue(Date d) {
		value = d;
	}

	/**
	 * Convierte una fecha String a Date
	 * 
	 * @param value
	 *            String value de una fecha
	 * @param mask
	 *            Mascara del formato String como fecha
	 */
	private void setStringDate(String value, String mask, Locale locale) {
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat(
				mask, locale), false);
		editor.setAsText(value);
		java.util.Date utilDate = (java.util.Date) editor.getValue();
		this.value = new Date(utilDate.getTime());
	}

	// Members
	
	private static final long serialVersionUID = -5735219128488002119L;

	/**
	 * Mascara por defecto.
	 */
	protected static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * Fecha
	 */
	protected Date value;

}
