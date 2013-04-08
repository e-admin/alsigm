/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.visitor.SelectBodyVisitor;

/**
 * Núcleo de una sentencia "SELECT" (sin UNION ni ORDER BY).
 */
public class PlainSelect extends SelectBody {

	/**
	 * 
	 * @param aTableReference
	 */
	public void addTableReference(ITableReference aTableReference) {
		if (null == this.tableReferences) {
			setTableReferences(new ArrayList<ITableReference>());
		}
		tableReferences.add(aTableReference);
	}

	/**
	 * 
	 * @param aTableReference
	 */
	public void setTableReference(ITableReference aTableReference) {

		if (null == this.tableReferences) {
			setTableReferences(new ArrayList<ITableReference>());
		} else {
			this.tableReferences.clear();
		}

		tableReferences.add(aTableReference);
	}

	/**
	 * The {@link ITableReference}s in this query (for example tables,
	 * subselects etc.)
	 * 
	 * @return a list of {@link ITableReference}s
	 */
	public List<ITableReference> getTableReferences() {
		return tableReferences;
	}

	public Table getInto() {
		return into;
	}

	/**
	 * The {@link ISelectItem}s in this query (for example the A,B,C in "SELECT
	 * A,B,C")
	 * 
	 * @return a list of {@link ISelectItem}s
	 */
	public List<ISelectItem> getSelectItems() {
		return selectItems;
	}

	public IExpression getWhere() {
		return where;
	}

	public void setTableReferences(List<ITableReference> aList) {
		tableReferences = aList;
	}

	public void setInto(Table aTable) {
		into = aTable;
	}

	public void addSelectItem(ISelectItem anItem) {
		if (null == this.selectItems) {
			this.selectItems = new ArrayList<ISelectItem>();
		}
		this.selectItems.add(anItem);
	}

	public void setSelectItems(List<ISelectItem> aList) {
		selectItems = aList;
	}

	public void setWhere(IExpression aWhere) {
		this.where = aWhere;
	}

	public void accept(SelectBodyVisitor aSelectVisitor) {
		aSelectVisitor.visit(this);
	}

	public Top getTop() {
		return top;
	}

	public void setTop(Top aTop) {
		this.top = aTop;
	}

	public Distinct getDistinct() {
		return distinct;
	}

	public void setDistinct(Distinct aDistinct) {
		this.distinct = aDistinct;
	}

	public IExpression getHaving() {
		return having;
	}

	public void setHaving(IExpression anExpression) {
		having = anExpression;
	}

	/**
	 * A list of {@link IColumnReference}s of the GROUP BY clause. It is null in
	 * case there is no GROUP BY clause
	 * 
	 * @return a list of {@link IColumnReference}s
	 */
	public List<IColumnReference> getGroupByColumnReferences() {
		return groupByColumnReferences;
	}

	public void addGroupByColumnReference(IColumnReference aColumnReference) {
		if (null == this.groupByColumnReferences) {
			this.groupByColumnReferences = new ArrayList<IColumnReference>();
		}
		this.groupByColumnReferences.add(aColumnReference);
	}

	public void setGroupByColumnReferences(List<IColumnReference> aList) {
		groupByColumnReferences = aList;
	}

	@Override
	public boolean isOrContains(ISqlNode aNode) {
		if (super.isOrContains(aNode)) {
			return true;
		}

		if (getDistinct() != null && getDistinct().isOrContains(aNode)) {
			return true;
		}

		if (CollectionUtils.isNotEmpty(getSelectItems())) {
			for (ISelectItem selectItem : getSelectItems()) {
				if (selectItem.isOrContains(aNode)) {
					return true;
				}
			}
		}

		if (getInto() != null && getInto().isOrContains(aNode)) {
			return true;
		}

		if (CollectionUtils.isNotEmpty(getTableReferences())) {
			for (ITableReference tableReference : getTableReferences()) {
				if (tableReference.isOrContains(aNode)) {
					return true;
				}
			}
		}

		if (getWhere() != null && getWhere().isOrContains(aNode)) {
			return true;
		}

		if (CollectionUtils.isNotEmpty(getGroupByColumnReferences())) {
			for (IColumnReference columnReference : getGroupByColumnReferences()) {
				if (columnReference.isOrContains(aNode)) {
					return true;
				}
			}
		}

		if (getHaving() != null && getHaving().isOrContains(aNode)) {
			return true;
		}

		return (getTop() != null && getTop().isOrContains(aNode));
	}

	// Members

	private static final long serialVersionUID = 8171895349730332706L;

	private Distinct distinct = null;

	private List<ISelectItem> selectItems;

	private Table into;

	private List<ITableReference> tableReferences;

	private IExpression where;

	private List<IColumnReference> groupByColumnReferences;

	private IExpression having;

	private Top top;
}
