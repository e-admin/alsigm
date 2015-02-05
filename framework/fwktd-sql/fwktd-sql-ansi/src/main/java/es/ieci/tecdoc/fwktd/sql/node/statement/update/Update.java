/*
 * 
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.update;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.statement.Statement;
import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;

/**
 * Sentencia "UPDATE".
 */
public class Update extends Statement {

	public void accept(StatementVisitor aStatementVisitor) {
		aStatementVisitor.visit(this);
	}

	public IExpression getWhere() {
		return where;
	}

	public void setWhere(IExpression anExpression) {
		where = anExpression;
	}

	public List<SetClause> getSetClauses() {
		return setClauses;
	}

	public void setSetClauses(List<SetClause> aSetClauses) {
		this.setClauses = aSetClauses;
	}

	@Override
	public boolean isOrContains(ISqlNode aNode) {
		if (super.isOrContains(aNode)) {
			return true;
		}

		if (CollectionUtils.isNotEmpty(getSetClauses())) {
			for (SetClause setClause : getSetClauses()) {
				if (setClause.isOrContains(aNode)) {
					return true;
				}
			}
		}

		return (getWhere().isOrContains(aNode));
	}

	// Members

	private static final long serialVersionUID = -4165961905857158525L;

	/** Claúsulas set de actualización de columnas */
	private List<SetClause> setClauses;

	/** Expression para filtar las filas a actualizar */
	private IExpression where;
}
