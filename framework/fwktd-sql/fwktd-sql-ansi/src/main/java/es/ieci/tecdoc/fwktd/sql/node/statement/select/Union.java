/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.visitor.SelectBodyVisitor;

/**
 * A UNION statement.
 */
public class Union extends SelectBody {

	public void accept(SelectBodyVisitor aSelectVisitor) {
		aSelectVisitor.visit(this);
	}

	public List<ISelectBody> getSelectBodys() {
		return selectBodys;
	}

	public void setSelectBodys(List<ISelectBody> aSelectBodys) {
		this.selectBodys = aSelectBodys;
	}

	@Override
	public boolean isOrContains(ISqlNode aNode) {
		if (super.isOrContains(aNode)) {
			return true;
		}

		if (CollectionUtils.isNotEmpty(getSelectBodys())) {
			for (ISelectBody selectBody : getSelectBodys()) {
				if (selectBody.isOrContains(aNode)) {
					return true;
				}
			}
		}
		return false;
	}

	// Members

	private static final long serialVersionUID = 2961308124247077439L;

	/** Cuerpos de SELECT de los que hacer la unión */
	private List<ISelectBody> selectBodys;
}
