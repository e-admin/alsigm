package common.vos;

import common.Constants;

public class Direccion {

	public String pais;
	public String comunidad;
	public String localizacion;
	public String concejo;
	// public String parroquia;
	public String poblacion;

	/**
	 * Indicador de si la Dirección procede de un sistema validado o se ha
	 * introducido manualmente.
	 */
	public String validado;

	public Direccion() {
	}

	/**
	 * Constructor
	 * 
	 * @param pais
	 *            Pais
	 * @param comunidad
	 *            Comunidad o estado dentro del pais
	 * @param localizacion
	 * @param concejo
	 *            Municipio
	 * @param parroquia
	 * @param poblacion
	 */
	public Direccion(String pais, String comunidad, String concejo,
			String poblacion, String localizacion, String validado) {
		super();
		this.pais = pais;
		this.comunidad = comunidad;
		this.localizacion = localizacion;
		this.concejo = concejo;
		this.poblacion = poblacion;
		this.validado = validado;
	}

	public String getConcejo() {
		return concejo;
	}

	public void setConcejo(String concejo) {
		this.concejo = concejo;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String emplazamiento) {
		this.localizacion = emplazamiento;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getComunidad() {
		return comunidad;
	}

	public void setComunidad(String comunidad) {
		this.comunidad = comunidad;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getValidado() {
		return validado;
	}

	public void setValidado(String validado) {
		// Si no existe el campo VALIDADO se traduce por una S, ya
		// ya que todos los introducidos hasta ahora son validados.
		if (validado == null)
			validado = Constants.TRUE_STRING;
		this.validado = validado;
	}

	// public String getParroquia() {
	// return parroquia;
	// }
	// public void setParroquia(String parroquia) {
	// this.parroquia = parroquia;
	// }
}
