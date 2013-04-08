package es.ieci.tecdoc.isicres.api.business.vo;

public class CriterioBusquedaTipoAsuntoSqlVO extends CriterioBusquedaWhereSqlVO {

	private static final long serialVersionUID = -4282622808459048754L;

	public CriterioBusquedaTipoAsuntoSqlVO() {
		super();
	}

	public CriterioBusquedaTipoAsuntoSqlVO(Long limit, Long offset, String sql) {
		super(limit, offset, sql);
	}

	public CriterioBusquedaTipoAsuntoSqlVO(Long limit, Long offset) {
		super(limit, offset);
	}

}
