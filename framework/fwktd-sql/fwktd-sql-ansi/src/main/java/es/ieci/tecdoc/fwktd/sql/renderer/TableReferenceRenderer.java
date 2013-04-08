/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.renderer;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

import es.ieci.tecdoc.fwktd.sql.node.schema.Column;
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ITableReference;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.JoinCondition;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.JoinedTable;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.NamedColumnsJoin;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.SubSelect;
import es.ieci.tecdoc.fwktd.sql.node.visitor.TableReferenceVisitor;

/**
 * Renderer para un elemento de la claúsula FROM
 * 
 * @author IECISA
 */
public class TableReferenceRenderer implements TableReferenceVisitor {

	public void visit(ITableReference aTableReference) {

		// Si el tableReference tiene alias se lo añadimos
		if (StringUtils.isNotEmpty(aTableReference.getAlias())) {
			aTableReference.setSqlString(aTableReference.getSqlString()
					+ asDecorator() + decorate(aTableReference.getAlias()));
		}
	}

	public void visit(Table aTable) {

		// Establecemos el nombre físico
		if (StringUtils.isNotEmpty(aTable.getSchemaName())) {
			aTable.setWholeTableName(decorate(aTable.getSchemaName()) + "."
					+ decorate(aTable.getName()));
		} else {
			aTable.setWholeTableName(decorate(aTable.getName()));
		}

		// Establecemos el nombre de acceso
		if (StringUtils.isNotEmpty(aTable.getAlias())) {
			aTable.setReferenceName(decorate(aTable.getAlias()));
		} else {
			aTable.setReferenceName(decorate(aTable.getName()));
		}

		aTable.setSqlString(aTable.getWholeTableName());

		// Visitamos al padre, que añade el alias
		this.visit((ITableReference) aTable);
	}

	/**
	 * Decorador " AS " para los alias
	 * 
	 * @return " AS "
	 */
	protected String asDecorator() {
		return " AS ";
	}

	/**
	 * Decorador aplicable a cualquier tabla, en el standard no se decora
	 * 
	 * @param aName
	 *            Elemento a decorar de la tabla
	 * @return Elemento decorado, por defecto no se aplican decoradores
	 */
	protected String decorate(String aName) {
		return aName;
	}

	public void visit(SubSelect aSubSelect) {

		StringBuffer buffer = new StringBuffer();

		aSubSelect.getSelectBody().accept(getSelectBodyRenderer());

		buffer.append("(").append(aSubSelect.getSelectBody().getSqlString())
				.append(")");

		aSubSelect.setSqlString(buffer.toString());

		// Visitamos al padre, que añade el alias
		this.visit((ITableReference) aSubSelect);
	}

	public void visit(JoinedTable aJoinedTable) {

		StringBuffer buffer = new StringBuffer();

		// Visitamos las ITableReference izquierda y derecha
		aJoinedTable.getLeftTableReference().accept(this);
		aJoinedTable.getRightTableReference().accept(this);

		buffer.append(aJoinedTable.getLeftTableReference().getSqlString());

		if (aJoinedTable.isNatural()) {
			buffer.append(" NATURAL");
		}

		switch (aJoinedTable.getJoinType()) {
		case cross:
			buffer.append(" CROSS JOIN ");
			break;
		case inner:
			buffer.append(" INNER JOIN ");
			break;
		case full:
			buffer.append(" FULL JOIN ");
			break;
		case left:
			buffer.append(" LEFT JOIN ");
			break;
		case right:
			buffer.append(" RIGHT JOIN ");
			break;
		case union:
			buffer.append(" UNION JOIN ");
			break;
		default:
			buffer.append(" JOIN ");
			break;
		}

		buffer.append(aJoinedTable.getRightTableReference().getSqlString());

		// Si hay condición para el JOIN, la visitamos y la añadimos
		if (aJoinedTable.getJoinSpecification() != null) {

			aJoinedTable.getJoinSpecification().accept(this);
			buffer.append(aJoinedTable.getJoinSpecification().getSqlString());
		}

		aJoinedTable.setSqlString(buffer.toString());

		// Visitamos al padre, que añade el alias
		this.visit((ITableReference) aJoinedTable);
	}

	public void visit(NamedColumnsJoin aNamedColumnsJoin) {

		StringBuffer buffer = new StringBuffer();

		buffer.append(" USING (");
		for (Iterator<Column> iterator = aNamedColumnsJoin.getColumns()
				.iterator(); iterator.hasNext();) {
			Column column = iterator.next();
			column.accept(getExpressionRenderer());
			buffer.append(column.getSqlString());
			if (iterator.hasNext()) {
				buffer.append(", ");
			}
		}

		buffer.append(")");

		aNamedColumnsJoin.setSqlString(buffer.toString());
	}

	public void visit(JoinCondition aJoinCondition) {

		StringBuffer buffer = new StringBuffer(" ON ");

		aJoinCondition.getExpression().accept(getExpressionRenderer());
		buffer.append(aJoinCondition.getExpression().getSqlString());

		aJoinCondition.setSqlString(buffer.toString());
	}
	
	public SelectBodyRenderer getSelectBodyRenderer() {
		return selectBodyRenderer;
	}

	public ExpressionRenderer getExpressionRenderer() {
		return expressionRenderer;
	}

	public void setSelectBodyRenderer(SelectBodyRenderer aSelectBodyRenderer) {
		this.selectBodyRenderer = aSelectBodyRenderer;
	}

	public void setExpressionRenderer(ExpressionRenderer expressionRenderer) {
		this.expressionRenderer = expressionRenderer;
	}

	// Members

	/** Renderer para el cuerpo de un select */
	protected SelectBodyRenderer selectBodyRenderer;

	/** Renderer para expresiones */
	protected ExpressionRenderer expressionRenderer;
}
