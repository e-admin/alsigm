package es.ieci.tecdoc.isicres.terceros.business.vo.search;

import java.util.ArrayList;
import java.util.List;

import es.ieci.tecdoc.fwktd.core.model.Entity;

/**
 *
 *
 * @see SearchType
 * @author IECISA
 *
 */
public class CriteriaVO extends Entity {

	public SearchType getType() {
		return type;
	}

	public void setType(SearchType type) {
		this.type = type;
	}

	public List<FilterVO> getFilters() {
		return filters;
	}

	public void setFilters(List<FilterVO> filters) {
		this.filters = filters;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	protected SearchType type;

	protected List<FilterVO> filters = new ArrayList<FilterVO>();

	protected String orderBy;

	protected String where;

	private static final long serialVersionUID = 172352309841783470L;

}
