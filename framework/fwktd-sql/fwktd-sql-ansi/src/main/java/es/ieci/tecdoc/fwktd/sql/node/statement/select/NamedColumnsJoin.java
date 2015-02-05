/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.SqlNode;
import es.ieci.tecdoc.fwktd.sql.node.schema.Column;
import es.ieci.tecdoc.fwktd.sql.node.visitor.TableReferenceVisitor;

/**
 * Claúsula USING para un JOIN.
 */
public class NamedColumnsJoin extends SqlNode implements JoinSpecification {

	public NamedColumnsJoin(List<Column> aColums) {
		super();
		setColums(aColums);
	}

	public void accept(TableReferenceVisitor aTableReferenceVisitor) {
		aTableReferenceVisitor.visit(this);
	}

	public List<Column> getColumns() {
		return colums;
	}

	public void setColums(List<Column> aColums) {
		this.colums = aColums;
	}

	@Override
	public boolean isOrContains(ISqlNode aNode) {
		if (super.isOrContains(aNode)) {
			return true;
		}

		if (CollectionUtils.isNotEmpty(getColumns())) {
			for (Column column : getColumns()) {
				if (column.isOrContains(aNode)) {
					return true;
				}
			}
		}
		return false;
	}
	
	// Members
	
	private static final long serialVersionUID = 1901916251261074329L;

	/** Columnas por las que hacer el using */
	private List<Column> colums;
}
