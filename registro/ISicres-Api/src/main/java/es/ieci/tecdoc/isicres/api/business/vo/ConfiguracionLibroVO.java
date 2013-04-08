package es.ieci.tecdoc.isicres.api.business.vo;

public class ConfiguracionLibroVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = -412951517064665383L;

	/**
	 * tipo de numero de registro: central o por oficina
	 * 
	 * 0 central: 12digitos 4 del año y 8 de contador 2 oficina: 15 digitos 4 de
	 * año 3 de codigo oficina, y 8 de contador por oficina
	 */
	protected Integer tipoNumeracion;

	/**
	 * 0 no se autentican 1 las imagenes se sellan
	 */
	protected Integer tipoAutenticacionImagenes;

	public Integer getTipoNumeracion() {
		return tipoNumeracion;
	}

	public void setTipoNumeracion(Integer tipoNumeracion) {
		this.tipoNumeracion = tipoNumeracion;
	}

	public Integer getTipoAutenticacionImagenes() {
		return tipoAutenticacionImagenes;
	}

	public void setTipoAutenticacionImagenes(Integer tipoAutenticacionImagenes) {
		this.tipoAutenticacionImagenes = tipoAutenticacionImagenes;
	}
}
