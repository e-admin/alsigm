package es.ieci.tecdoc.isicres.api.business.vo;

public class CriterioBusquedaWhereSqlVO extends BaseCriterioBusquedaVO {

	private static final long serialVersionUID = -6646278571534750690L;

	public CriterioBusquedaWhereSqlVO() {
		super();
	}

	public CriterioBusquedaWhereSqlVO(Long limit, Long offset) {
		super(limit, offset);
	}

	public CriterioBusquedaWhereSqlVO(Long limit, Long offset, String sql) {
		super(limit, offset);
		setSql(sql);
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	// Members
	protected String sql;

}