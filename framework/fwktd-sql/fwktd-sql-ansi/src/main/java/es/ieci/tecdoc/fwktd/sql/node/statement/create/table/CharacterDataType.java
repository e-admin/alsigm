/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.create.table;

import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;

/**
 * <code>DataType</code> para columnas de tipo carácter.
 */
public class CharacterDataType extends ColDataType {

	public CharacterDataType() {
		super();
	}

	public CharacterDataType(Integer aSize) {
		this();
		setSize(aSize);
	}

	public void accept(StatementVisitor statementVisitor) {
		statementVisitor.visit(this);
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	// Members

	private static final long serialVersionUID = -2010374471941017889L;

	private Integer size;

}
