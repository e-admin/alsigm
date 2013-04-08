package se.tramites;

public class EmplazamientoImpl implements Emplazamiento {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String pais;
	String comunidad;
	String poblacion;
	String concejo;
	String localizacion;
	String validado;

	public EmplazamientoImpl() {
	}

	/**
	 * @param pais
	 * @param comunidad
	 * @param poblacion
	 * @param concejo
	 * @param localizacion
	 */
	public EmplazamientoImpl(String pais, String comunidad, String concejo,
			String poblacion, String localizacion, String validado) {
		super();
		this.pais = pais;
		this.comunidad = comunidad;
		this.poblacion = poblacion;
		this.concejo = concejo;
		this.localizacion = localizacion;
		this.validado = validado;
	}

	/**
	 * Devuelve el país.
	 * 
	 * @return País.
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * Devuelve la comunidad
	 * 
	 * @return Comunidad.
	 */
	public String getComunidad() {
		return comunidad;
	}

	/**
	 * Devuelve la población.
	 * 
	 * @return Población.
	 */
	public String getPoblacion() {
		return poblacion;
	}

	/**
	 * Devuelve la localización.
	 * 
	 * @return Localización.
	 */
	public String getLocalizacion() {
		return localizacion;
	}

	/**
	 * Devuelve el municipio.
	 * 
	 * @return Municipio.
	 */
	public String getConcejo() {
		return concejo;
	}

	/**
	 * @param comunidad
	 *            The comunidad to set.
	 */
	public void setComunidad(String comunidad) {
		this.comunidad = comunidad;
	}

	/**
	 * @param concejo
	 *            The concejo to set.
	 */
	public void setConcejo(String concejo) {
		this.concejo = concejo;
	}

	/**
	 * @param localizacion
	 *            The localizacion to set.
	 */
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	/**
	 * @param pais
	 *            The pais to set.
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * @param poblacion
	 *            The poblacion to set.
	 */
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getValidado() {
		return validado;
	}

	public void setValidado(String validado) {
		this.validado = validado;
	}

}
