package es.ieci.tecdoc.isicres.api.business.vo;

public class CriterioBusquedaUnidadAdministrativaByUnidadVO extends
		CriterioBusquedaUnidadAdministrativaVO {

	private static final long serialVersionUID = 4614364498536658409L;

	/**
	 * Constructor por defecto de la clase.
	 */
	public CriterioBusquedaUnidadAdministrativaByUnidadVO() {
		super();
	}

	public CriterioBusquedaUnidadAdministrativaByUnidadVO(Long limit,
			Long offset) {
		super(limit, offset);
	}

	public CriterioBusquedaUnidadAdministrativaByUnidadVO(Long limit,
			Long offset, String idUnidadAdministrativaPadre) {
		super(limit, offset);
		setIdUnidadAdministrativaPadre(idUnidadAdministrativaPadre);
	}

	public String getIdUnidadAdministrativaPadre() {
		return idUnidadAdministrativaPadre;
	}

	public void setIdUnidadAdministrativaPadre(
			String idUnidadAdministrativaPadre) {
		this.idUnidadAdministrativaPadre = idUnidadAdministrativaPadre;
	}

	// Members
	protected String idUnidadAdministrativaPadre;
}
