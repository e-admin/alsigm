/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression;

import java.sql.Date;
import java.sql.Timestamp;

import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Constante Timestamp:A Timestamp in the form {ts 'yyyy-mm-dd hh:mm:ss.f . .
 * .'}
 */
public class TimestampValue extends DateValue {

	public TimestampValue(Date date) {
		super(date);
	}

	public TimestampValue(Timestamp timestamp) {
		super(new Date(timestamp.getTime()));
	}

	public TimestampValue(String value, String mask) {
		super(value, mask);
	}

	public TimestampValue(String value) {
		super(value, DEFAULT_DATE_FORMAT);
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public Timestamp getTimeStampValue() {
		return new Timestamp(getValue().getTime());
	}
	
	// Members
	
	private static final long serialVersionUID = 2157562280703580571L;

	protected static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
