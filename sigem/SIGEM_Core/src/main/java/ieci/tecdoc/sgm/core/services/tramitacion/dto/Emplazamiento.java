package ieci.tecdoc.sgm.core.services.tramitacion.dto;

import java.io.Serializable;

/**
 * Información de un emplazamiento.
 */
public class Emplazamiento implements Serializable {

	/** País. */
	private String pais = null;
	
	/** Comunidad. */
	private String comunidad = null;

	/** Concejo. */
	private String concejo = null;

	/** Población. */
	private String poblacion = null;

	/** Localización. */
	private String localizacion = null;



	/**
	 * Constructor.
	 */
	public Emplazamiento() {
		super();
	}

	/**
	 * Obtiene el concejo
	 * @return Concejo.
	 */
	public String getConcejo() {
		return concejo;
	}

	/**
	 * Establece el concejo.
	 * @param concejo Concejo.
	 */
	public void setConcejo(String concejo) {
		this.concejo = concejo;
	}

	/**
	 * Obtiene la Localización.
	 * @return Localización.
	 */
	public String getLocalizacion() {
		return localizacion;
	}

	/**
	 * Establece la localización.
	 * @param localizacion Localización.
	 */
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	/**
	 * Obtiene la población.
	 * @return Población.
	 */
	public String getPoblacion() {
		return poblacion;
	}

	/**
	 * Establece la población.
	 * @param poblacion Población.
	 */
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	/**
	 * Obtiene la comunidad.
	 * @return Comunidad.
	 */
	public String getComunidad() {
		return comunidad;
	}

	/**
	 * Establece la comunidad.
	 * @param comunidad Comunidad.
	 */
	public void setComunidad(String comunidad) {
		this.comunidad = comunidad;
	}

	/**
	 * Obtiene el país.
	 * @return País.
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * Establece el país.
	 * @param pais País.
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

}
