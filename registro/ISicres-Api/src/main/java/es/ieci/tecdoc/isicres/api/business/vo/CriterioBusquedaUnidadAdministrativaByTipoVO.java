package es.ieci.tecdoc.isicres.api.business.vo;

public class CriterioBusquedaUnidadAdministrativaByTipoVO extends
		CriterioBusquedaUnidadAdministrativaVO {

	private static final long serialVersionUID = -4562266163781452260L;

	/**
	 * Constructor por defecto de la clase.
	 */
	public CriterioBusquedaUnidadAdministrativaByTipoVO() {
		super();
	}

	/**
	 * Constructor con parámetros de la clase.
	 * 
	 * @param limit
	 * @param offset
	 */
	public CriterioBusquedaUnidadAdministrativaByTipoVO(Long limit, Long offset) {
		super(limit, offset);
	}

	/**
	 * 
	 * @param limit
	 * @param offset
	 * @param idTipoUnidadAdministrativa
	 */
	public CriterioBusquedaUnidadAdministrativaByTipoVO(Long limit,
			Long offset, String idTipoUnidadAdministrativa) {
		super(limit, offset);
		setIdTipoUnidadAdministrativa(idTipoUnidadAdministrativa);
	}

	public String getIdTipoUnidadAdministrativa() {
		return idTipoUnidadAdministrativa;
	}

	public void setIdTipoUnidadAdministrativa(String idTipoUnidadAdministrativa) {
		this.idTipoUnidadAdministrativa = idTipoUnidadAdministrativa;
	}

	// Members
	protected String idTipoUnidadAdministrativa;
}
