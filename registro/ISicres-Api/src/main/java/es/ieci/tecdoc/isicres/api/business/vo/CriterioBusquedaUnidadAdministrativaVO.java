package es.ieci.tecdoc.isicres.api.business.vo;

public class CriterioBusquedaUnidadAdministrativaVO extends
		BaseCriterioBusquedaVO {

	private static final long serialVersionUID = 2739070168101701750L;

	/**
	 * Constructor por defecto de la clase.
	 */
	public CriterioBusquedaUnidadAdministrativaVO() {
		super();
	}

	/**
	 * Constructor con parámetros de la clase.
	 * 
	 * @param limit
	 *            número máximo de resultados
	 * @param offset
	 *            posición inicial de la página de resultados
	 */
	public CriterioBusquedaUnidadAdministrativaVO(Long limit, Long offset) {
		super(limit, offset);
	}

}
