/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.initialize.random;

import es.ieci.tecdoc.fwktd.sql.node.statement.Statement;
import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;

/**
 * Invocación a procedimiento "Initialize random".
 */
public class InitializeRandom extends Statement {

	public InitializeRandom(int aSeed) {
		super();
		setSeed(aSeed);
	}

	public void accept(StatementVisitor aStatementVisitor) {
		aStatementVisitor.visit(this);
	}

	public int getSeed() {
		return seed;
	}

	public void setSeed(int aSeed) {
		this.seed = aSeed;
	}

	// Members

	private static final long serialVersionUID = -1819119278586554083L;

	/** Semilla */
	private int seed;
}
