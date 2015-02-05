/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.renderer;

import java.util.Iterator;

import org.apache.commons.collections.CollectionUtils;

import es.ieci.tecdoc.fwktd.sql.node.schema.Column;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.AllColumns;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.AllTableColumns;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ColumnIndex;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.IColumnReference;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ISelectBody;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ISelectItem;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ITableReference;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.OrderBy;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.OrderByElement;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.PlainSelect;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.SelectExpressionItem;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.Union;
import es.ieci.tecdoc.fwktd.sql.node.visitor.SelectBodyVisitor;

/**
 * Renderer para el cuerpo de un SELECT
 * 
 * @author IECISA
 */
public class SelectBodyRenderer implements SelectBodyVisitor {

	public void visit(ISelectBody aSelectBody) {
		/*
		 * Completa el SqlString generado las clases hija de SelectBody
		 * añadiéndole la parte OrderBy y la parte Limit
		 */
		StringBuffer buffer = new StringBuffer(aSelectBody.getSqlString());

		// Añadimos la parte orderBy
		if (aSelectBody.getOrderBy() != null) {

			aSelectBody.getOrderBy().accept(this);
			buffer.append(" ").append(aSelectBody.getOrderBy().getSqlString());
		}

		/*
		 * El ANSI SQL 92 no soporta la claúsula Limit. Está soportada en los
		 * VOs porque hay dialectos SQL que sí implementan esta funcionalidad
		 * (de formas diferentes)
		 */
		if (aSelectBody.getLimit() != null) {
			throw new UnsupportedOperationException(
					"Operación Limit no permitida en ANSI SQL 92");
		}

		aSelectBody.setSqlString(buffer.toString());
	}

	public void visit(PlainSelect aPlainSelect) {

		StringBuffer buffer = new StringBuffer();

		// Parte SELECT
		buffer.append("SELECT ");
		if (aPlainSelect.getDistinct() != null) {
			buffer.append("DISTINCT ");
			if (CollectionUtils.isNotEmpty(aPlainSelect.getDistinct()
					.getOnSelectItems())) {
				buffer.append("ON (");
				for (Iterator<ISelectItem> iter = aPlainSelect.getDistinct()
						.getOnSelectItems().iterator(); iter.hasNext();) {
					ISelectItem selectItem = (ISelectItem) iter.next();
					selectItem.accept(this);
					buffer.append(selectItem.getSqlString());
					if (iter.hasNext()) {
						buffer.append(", ");
					}
				}
				buffer.append(") ");
			}
		}

		for (Iterator<ISelectItem> iter = aPlainSelect.getSelectItems()
				.iterator(); iter.hasNext();) {
			ISelectItem selectItem = (ISelectItem) iter.next();
			selectItem.accept(this);
			buffer.append(selectItem.getSqlString());
			if (iter.hasNext()) {
				buffer.append(", ");
			}
		}

		// Parte FROM
		buffer.append(" FROM ");
		for (Iterator<ITableReference> iter = aPlainSelect.getTableReferences()
				.iterator(); iter.hasNext();) {

			ITableReference tableReference = (ITableReference) iter.next();
			tableReference.accept(getTableReferenceRenderer());
			buffer.append(tableReference.getSqlString());

			if (iter.hasNext()) {
				buffer.append(", ");
			}
		}

		// Parte WHERE
		if (aPlainSelect.getWhere() != null) {
			buffer.append(" WHERE ");
			aPlainSelect.getWhere().accept(getExpressionRenderer());
			buffer.append(aPlainSelect.getWhere().getSqlString());
		}

		// Parte GROUP BY
		if (aPlainSelect.getGroupByColumnReferences() != null) {
			buffer.append(" GROUP BY ");
			for (Iterator<IColumnReference> iter = aPlainSelect
					.getGroupByColumnReferences().iterator(); iter.hasNext();) {
				IColumnReference columnReference = (IColumnReference) iter
						.next();
				columnReference.accept(this);
				buffer.append(columnReference.getSqlString());
				if (iter.hasNext()) {
					buffer.append(", ");
				}
			}
		}

		// Parte HAVING
		if (aPlainSelect.getHaving() != null) {
			buffer.append(" HAVING ");
			aPlainSelect.getHaving().accept(getExpressionRenderer());
			buffer.append(aPlainSelect.getHaving().getSqlString());
		}

		aPlainSelect.setSqlString(buffer.toString());

		/*
		 * Visitamos al padre, que se encarga de la parte orderBy y la parte
		 * Limit
		 */
		this.visit((ISelectBody) aPlainSelect);
	}

	public void visit(Union anUnion) {

		StringBuffer buffer = new StringBuffer();

		for (Iterator<ISelectBody> iter = anUnion.getSelectBodys().iterator(); iter
				.hasNext();) {
			buffer.append("(");
			ISelectBody selectBody = (PlainSelect) iter.next();
			selectBody.accept(this);
			buffer.append(selectBody.getSqlString());
			buffer.append(")");
			if (iter.hasNext()) {
				buffer.append(" UNION ");
			}
		}

		anUnion.setSqlString(buffer.toString());

		/*
		 * Visitamos al padre, que se encarga de la parte orderBy y la parte
		 * Limit
		 */
		this.visit((ISelectBody) anUnion);
	}

	public void visit(AllColumns anAllColumns) {
		anAllColumns.setSqlString("*");
	}

	public void visit(AllTableColumns anAllTableColumns) {

		anAllTableColumns.getTable().accept(getTableReferenceRenderer());

		anAllTableColumns.setSqlString(anAllTableColumns.getTable()
				.getReferenceName()
				+ ".*");
	}

	public void visit(SelectExpressionItem aSelectExpressionItem) {

		StringBuffer buffer = new StringBuffer();

		aSelectExpressionItem.getExpression().accept(getExpressionRenderer());
		buffer.append(aSelectExpressionItem.getExpression().getSqlString());

		if (aSelectExpressionItem.getAlias() != null) {
			buffer.append(" AS " + decorate(aSelectExpressionItem.getAlias()));
		}

		aSelectExpressionItem.setSqlString(buffer.toString());
	}

	public void visit(OrderBy anOrderBy) {

		StringBuffer buffer = new StringBuffer();

		if (anOrderBy.isRandom()) {
			throw new UnsupportedOperationException(
					"Operación random no soportada en el standard ANSI SQL 92");
		}

		if (CollectionUtils.isNotEmpty(anOrderBy.getOrderByElements())) {

			buffer.append("ORDER BY ");
			for (Iterator<OrderByElement> iter = anOrderBy.getOrderByElements()
					.iterator(); iter.hasNext();) {

				OrderByElement orderByElement = iter.next();
				orderByElement.accept(this);
				buffer.append(orderByElement.getSqlString());
				if (iter.hasNext()) {
					buffer.append(", ");
				}
			}
		}

		anOrderBy.setSqlString(buffer.toString());
	}

	public void visit(OrderByElement anOrderByElement) {
		String ascString = " ASC";
		String descString = " DESC";
		String orderString = descString;

		anOrderByElement.getColumnReference().accept(this);
		if (anOrderByElement.isAsc()) {
			orderString = ascString;
		}
		anOrderByElement.setSqlString(anOrderByElement.getColumnReference()
				.getSqlString()
				+ orderString);
	}

	/**
	 * Decorador aplicable a cualquier elemento, en el standard no se decora
	 * 
	 * @param aName
	 *            Elemento a decorar
	 * @return Elemento decorado, por defecto no se aplican decoradores
	 */
	protected String decorate(String aName) {
		return aName;
	}

	public void visit(Column aColumn) {
		aColumn.accept(getExpressionRenderer());
	}

	public void visit(ColumnIndex aColumnIndex) {

		aColumnIndex.setSqlString(Integer.toString(aColumnIndex.getIndex()));
	}

	public ExpressionRenderer getExpressionRenderer() {
		return expressionRenderer;
	}

	public TableReferenceRenderer getTableReferenceRenderer() {
		return tableReferenceRenderer;
	}

	public void setExpressionRenderer(ExpressionRenderer anExpressionRenderer) {
		this.expressionRenderer = anExpressionRenderer;
	}

	public void setTableReferenceRenderer(
			TableReferenceRenderer aTableReferenceRenderer) {
		this.tableReferenceRenderer = aTableReferenceRenderer;
	}

	// Members

	/** Renderer para expresiones */
	protected ExpressionRenderer expressionRenderer;

	/** Renderer para los elementos de la claúsula FROM */
	protected TableReferenceRenderer tableReferenceRenderer;
}
