/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression;

import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;

/**
 * Siguiente valor de una secuencia.
 */
public class SequenceNextValue extends Expression {

	public SequenceNextValue(String aSchema, String aName) {
		super();
		setSchema(aSchema);
		setName(aName);
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	// Members

	private static final long serialVersionUID = -2695120440369722701L;

	/** Nombre del schema al que pertenece */
	private String schema;

	/** Nombre de la secuencia */
	private String name;
}
