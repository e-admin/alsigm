/*
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.update;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.SqlNode;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.schema.Column;
import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;

/**
 * Elemento de una claúsula set para un insert.
 */
public class SetClause extends SqlNode {

	public enum SetClauseValueType {
		expression, null_value, default_value
	};

	public SetClause(Column aColumn, SetClauseValueType aSetClauseValueType) {
		super();
		setColumn(aColumn);
		setSetClauseValueType(aSetClauseValueType);
	}

	public SetClause(Column aColumn, SetClauseValueType aSetClauseValueType,
			IExpression anExpression) {
		this(aColumn, aSetClauseValueType);
		setExpression(anExpression);
	}

	public void accept(StatementVisitor aStatementVisitor) {
		aStatementVisitor.visit(this);
	}

	public IExpression getExpression() {
		return expression;
	}

	public void setExpression(IExpression anExpression) {
		this.expression = anExpression;
	}

	public SetClauseValueType getSetClauseValueType() {
		return setClauseValueType;
	}

	public void setSetClauseValueType(SetClauseValueType aSetClauseValueType) {
		this.setClauseValueType = aSetClauseValueType;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column aColumn) {
		this.column = aColumn;
	}

	@Override
	public boolean isOrContains(ISqlNode aNode) {
		if (super.isOrContains(aNode)) {
			return true;
		}

		if (getColumn() != null && getColumn().isOrContains(aNode)) {
			return true;
		}

		return (getExpression() != null && getExpression().isOrContains(aNode));
	}

	// Members

	private static final long serialVersionUID = -7231628318725001291L;

	/** Columna a actualizar */
	private Column column;

	/** Tipo de valor */
	private SetClauseValueType setClauseValueType;

	/** Expression a actualizar */
	private IExpression expression;

}
