/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.SqlNode;

/**
 * Sentencia DISTINCT [ON (expression, ...)].
 */
public class Distinct extends SqlNode {

	/**
	 * A list of {@link ISelectItem}s expressions, as in "select DISTINCT ON
	 * (a,b,c) a,b FROM..."
	 * 
	 * @return a list of {@link ISelectItem}s expressions
	 */
	public List<ISelectItem> getOnSelectItems() {
		return onSelectItems;
	}

	public void setOnSelectItems(List<ISelectItem> aList) {
		onSelectItems = aList;
	}

	@Override
	public boolean isOrContains(ISqlNode aNode) {
		if (super.isOrContains(aNode)) {
			return true;
		}

		if (CollectionUtils.isNotEmpty(getOnSelectItems())) {
			for (ISelectItem selectItem : getOnSelectItems()) {
				if (selectItem.isOrContains(aNode)) {
					return true;
				}
			}
		}

		return false;
	}
	
	// Members
	
	private static final long serialVersionUID = -6468295451494186029L;

	private List<ISelectItem> onSelectItems;

}
