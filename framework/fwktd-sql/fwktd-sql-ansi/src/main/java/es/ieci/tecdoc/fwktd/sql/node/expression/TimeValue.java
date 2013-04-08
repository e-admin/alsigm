/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression;

import java.sql.Date;
import java.sql.Time;

import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Constante Time:A Time in the form {t 'hh:mm:ss'}
 */
public class TimeValue extends DateValue {

	public TimeValue(Date date) {
		super(date);
	}

	public TimeValue(Time time) {
		super(new Date(time.getTime()));
	}

	public TimeValue(String value, String mask) {
		super(value, mask);
	}

	public TimeValue(String value) {
		super(value);
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public Time getTimeValue() {
		return new Time(getValue().getTime());
	}
	
	// Members
	
	private static final long serialVersionUID = 6305491943206677608L;

	protected static final String DEFAULT_DATE_FORMAT = "HH:mm:ss";
}
