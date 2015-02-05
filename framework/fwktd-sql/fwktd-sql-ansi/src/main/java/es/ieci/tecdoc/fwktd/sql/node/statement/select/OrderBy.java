/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.SqlNode;
import es.ieci.tecdoc.fwktd.sql.node.visitor.SelectBodyVisitor;

/**
 * Claúsula Order BY.
 */
public class OrderBy extends SqlNode {

	public OrderBy() {
		super();
	}

	public OrderBy(boolean aRandom) {
		this();
		setRandom(aRandom);
	}

	public OrderBy(List<OrderByElement> anOrderByElements) {
		this();
		this.orderByElements = anOrderByElements;
	}

	public void accept(SelectBodyVisitor aSelectVisitor) {
		aSelectVisitor.visit(this);
	}

	/**
	 * Añade un <code>OrderByElement</code> a la lista
	 * 
	 * @param anOrderByElement
	 */
	public void addOrderByElement(OrderByElement anOrderByElement) {

		if (orderByElements == null) {
			orderByElements = new ArrayList<OrderByElement>();
		}

		orderByElements.add(anOrderByElement);
	}

	public List<OrderByElement> getOrderByElements() {
		return orderByElements;
	}

	public void setOrderByElements(List<OrderByElement> anOrderByElements) {
		this.orderByElements = anOrderByElements;
	}

	public boolean isRandom() {
		return random;
	}

	public void setRandom(boolean aRandom) {
		this.random = aRandom;
	}

	@Override
	public boolean isOrContains(ISqlNode aNode) {
		if (super.isOrContains(aNode)) {
			return true;
		}

		if (CollectionUtils.isNotEmpty(getOrderByElements())) {
			for (OrderByElement orderByElement : getOrderByElements()) {
				if (orderByElement.isOrContains(aNode)) {
					return true;
				}
			}
		}

		return false;
	}
	
	// Members
	
	private static final long serialVersionUID = -2955811472909902262L;

	/** Elementos de ordenación */
	private List<OrderByElement> orderByElements;

	/** Flag que indica si ordenar de forma aleatoria */
	private boolean random = false;
}
