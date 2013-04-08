/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.renderer;

import java.util.Iterator;

import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.ExpressionList;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.SubSelect;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ItemsListVisitor;

/**
 * Renderer para una lista de elementos
 * 
 * @author IECISA
 */
public class ItemsListRenderer implements ItemsListVisitor {

	public void visit(ExpressionList anExpressionList) {

//		StringBuffer buffer = new StringBuffer("VALUES (");
		StringBuffer buffer = new StringBuffer();
		for (Iterator<IExpression> iter = anExpressionList.getExpressions()
				.iterator(); iter.hasNext();) {
			IExpression expression = (IExpression) iter.next();
			expression.accept(getExpressionRenderer());
			buffer.append(expression.getSqlString());

			if (iter.hasNext())
				buffer.append(", ");
		}
//		buffer.append(")");

		anExpressionList.setSqlString(buffer.toString());
	}

	public void visit(SubSelect aSubSelect) {

		aSubSelect.getSelectBody().accept(getSelectBodyRenderer());
		aSubSelect.setSqlString(aSubSelect.getSelectBody().getSqlString());
	}

	public SelectBodyRenderer getSelectBodyRenderer() {
		return selectBodyRenderer;
	}

	public ExpressionRenderer getExpressionRenderer() {
		return expressionRenderer;
	}

	public void setExpressionRenderer(ExpressionRenderer anExpressionRenderer) {
		this.expressionRenderer = anExpressionRenderer;
	}

	public void setSelectBodyRenderer(SelectBodyRenderer aSelectBodyRenderer) {
		this.selectBodyRenderer = aSelectBodyRenderer;
	}

	// Members

	/** Renderer para el cuerpo de un SELECT */
	protected SelectBodyRenderer selectBodyRenderer;

	/** Renderer de expresiones */
	protected ExpressionRenderer expressionRenderer;
}
