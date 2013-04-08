/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.alter.table;

import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.Statement;
import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;

/**
 * Sentencia "ALTER TABLE".
 */
public class AlterTable extends Statement {

	/**
	 * Constructor que desactiva la primary key.
	 * 
	 * @param aTable
	 */
	public AlterTable(Table aTable) {
		this(aTable, false);
	}

	/**
	 * Constructor
	 * 
	 * @param aTable
	 *            Table
	 * @param enable
	 *            True para activar la primary key, false en caso contrario
	 */
	public AlterTable(Table aTable, boolean enable) {
		Assert.notNull(aTable);
		Assert.notNull(enable);
		setTable(aTable);
		setEnable(enable);
	}

	public void accept(StatementVisitor aStatementVisitor) {
		aStatementVisitor.visit(this);
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	// Members

	private static final long serialVersionUID = -5112474099687321743L;
	/**
	 * True activa la primary key.
	 */
	private boolean enable;
}
