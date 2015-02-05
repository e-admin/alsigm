/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.create.table;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.Statement;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ISelectBody;
import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;

/**
 * Sentencia "CREATE TABLE".
 */
public class CreateTable extends Statement {

	public void accept(StatementVisitor aStatementVisitor) {
		aStatementVisitor.visit(this);
	}

	public CreateTable(Table aTable) {
		super();
		setTable(aTable);
	}

	/**
	 * A list of options (as simple strings) of this table definition, as
	 * ("TYPE", "=", "MYISAM")
	 */
	public List<String> getTableOptionsStrings() {
		return tableOptionsStrings;
	}

	public void setTableOptionsStrings(List<String> list) {
		tableOptionsStrings = list;
	}

	public boolean isTemporary() {
		return temporary;
	}

	public void setTemporary(boolean temporary) {
		this.temporary = temporary;
	}

	public List<TableElement> getTableElements() {
		return tableElements;
	}

	public void setTableElements(List<TableElement> tableElements) {
		this.tableElements = tableElements;
	}

	public ISelectBody getSelect() {
		return select;
	}

	public void setSelect(ISelectBody select) {
		this.select = select;
	}

	@Override
	public boolean isOrContains(ISqlNode node) {

		if (super.isOrContains(node)) {
			return true;
		}

		if (CollectionUtils.isNotEmpty(getTableElements())) {
			for (TableElement tableElement : getTableElements()) {
				if (tableElement.isOrContains(node)) {
					return true;
				}
			}
		}
		return (getSelect() != null && getSelect().isOrContains(node));
	}

	// Members

	private static final long serialVersionUID = 4233580365408947695L;

	/** Flag que indica si la tabla es temporal */
	private boolean temporary;

	private List<String> tableOptionsStrings;

	/** Elementos de la tabla: columnas y constantes */
	private List<TableElement> tableElements;

	/** Cuerpo de select para el caso de que se trate de un Create AS Select */
	private ISelectBody select;
}