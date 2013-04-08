package se.tramites;

import java.io.Serializable;

/**
 * Interfaz para la información del emplazamiento.
 */
public interface Emplazamiento extends Serializable {

	/**
	 * Devuelve si el emplazamiento está validado.
	 * 
	 * @return
	 */
	public String getValidado();

	/**
	 * Devuelve el país.
	 * 
	 * @return País.
	 */
	public String getPais();

	/**
	 * Devuelve la comunidad
	 * 
	 * @return Comunidad.
	 */
	public String getComunidad();

	/**
	 * Devuelve la población.
	 * 
	 * @return Población.
	 */
	public String getPoblacion();

	/**
	 * Devuelve la localización.
	 * 
	 * @return Localización.
	 */
	public String getLocalizacion();

	/**
	 * Devuelve el municipio.
	 * 
	 * @return Municipio.
	 */
	public String getConcejo();
}
