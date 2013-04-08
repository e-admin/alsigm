/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.create.table;

import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;

/**
 * <code>DataType</code> para columnas de tipo numérico.
 */
public class NumericDataType extends ColDataType {

	public NumericDataType() {
		super();
	}

	public NumericDataType(Integer aPrecision) {
		this();
		this.precision = aPrecision;
	}

	public NumericDataType(Integer aPrecision, Integer aScale) {
		this(aPrecision);
		setScale(aScale);
	}

	public void accept(StatementVisitor aStatementVisitor) {
		aStatementVisitor.visit(this);
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer aPrecision) {
		this.precision = aPrecision;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer aScale) {
		this.scale = aScale;
	}

	// Members

	private static final long serialVersionUID = -951594150353712249L;

	/** Escala (número de dígitos tras la coma) */
	private Integer scale;

	/** Precisión (número de dígitos) */
	private Integer precision;

}
