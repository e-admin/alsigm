/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.visitor.TableReferenceVisitor;

/**
 * Especifica una tabla derivada de un producto cartesiano o un join.
 */
public class JoinedTable extends TableReference {

	public static enum JoinType {
		cross, inner, union, left, right, full
	};

	public JoinedTable(ITableReference aLeftTableReference,
			ITableReference aRightTableReference) {
		super();
		setLeftTableReference(aLeftTableReference);
		setRightTableReference(aRightTableReference);
	}

	public void accept(TableReferenceVisitor aTableReferenceVisitor) {
		aTableReferenceVisitor.visit(this);
	}

	public ITableReference getLeftTableReference() {
		return leftTableReference;
	}

	public void setLeftTableReference(ITableReference aLeftTableReference) {
		this.leftTableReference = aLeftTableReference;
	}

	public ITableReference getRightTableReference() {
		return rightTableReference;
	}

	public void setRightTableReference(ITableReference aRightTableReference) {
		this.rightTableReference = aRightTableReference;
	}

	public boolean isNatural() {
		return isNatural;
	}

	public void setNatural(boolean isNatural) {
		this.isNatural = isNatural;
	}

	public JoinSpecification getJoinSpecification() {
		return joinSpecification;
	}

	public void setJoinSpecification(JoinSpecification aJoinSpecification) {
		this.joinSpecification = aJoinSpecification;
	}

	public JoinType getJoinType() {
		return joinType;
	}

	public void setJoinType(JoinType aJoinType) {
		this.joinType = aJoinType;
	}

	@Override
	public boolean isOrContains(ISqlNode aNode) {
		if (super.isOrContains(aNode)) {
			return true;
		}

		if (getLeftTableReference() != null
				&& getLeftTableReference().isOrContains(aNode)) {
			return true;
		}

		if (getRightTableReference() != null
				&& getRightTableReference().isOrContains(aNode)) {
			return true;
		}

		return (getJoinSpecification() != null && getJoinSpecification()
				.isOrContains(aNode));
	}

	// Members

	private static final long serialVersionUID = 2827681736098762633L;

	/** ITableReference la parte izquierda */
	private ITableReference leftTableReference;

	/** ITableReference la parte derecha */
	private ITableReference rightTableReference;

	/** Tipo de join */
	private JoinType joinType = JoinType.inner;

	private boolean isNatural;

	/** Condición para el join */
	private JoinSpecification joinSpecification;

}
