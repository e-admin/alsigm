package es.ieci.tecdoc.fwktd.audit.core.vo.seach;

import java.util.ArrayList;
import java.util.List;

import es.ieci.tecdoc.fwktd.core.model.Entity;

public class CriteriaVO extends Entity {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 4619885633302781894L;

	protected List<FilterVO> filters = new ArrayList<FilterVO>();

	protected String orderBy;

	protected String where;
	
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

	

	


}
