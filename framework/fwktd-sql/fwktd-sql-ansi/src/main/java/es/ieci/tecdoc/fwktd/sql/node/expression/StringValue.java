/*
 * 
 */

package es.ieci.tecdoc.fwktd.sql.node.expression;

import org.apache.commons.lang.StringUtils;

import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;

/**
 * Constante String.
 */
public class StringValue extends Expression {

	/**
	 * Constructor
	 * 
	 * @param aValue
	 *            valor no entrecomillado de la constante.
	 */
	public StringValue(String aValue) {
		this(aValue, false);
	}

	/**
	 * Constructor
	 * 
	 * @param aValue
	 *            constante string
	 * @param aQuoted
	 *            true si ya está entrecomillada la cadena, false en caso
	 *            contrario.
	 */
	public StringValue(String aValue, boolean aQuoted) {
		setValue(aValue);
		setQuoted(aQuoted);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String string) {
		value = string;
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public boolean isQuoted() {
		return quoted;
	}

	public void setQuoted(boolean aQuoted) {
		this.quoted = aQuoted;
	}

	// Members

	private static final long serialVersionUID = 6038164214168604645L;

	private String value = StringUtils.EMPTY;

	protected boolean quoted;
}
