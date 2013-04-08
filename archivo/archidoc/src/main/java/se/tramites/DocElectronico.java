package se.tramites;

import java.io.Serializable;

/**
 * Interfaz para la información de un documento electrónico.
 */
public interface DocElectronico extends Serializable {

	/**
	 * Devuelve el tipo de documento.
	 * 
	 * @return Tipo de documento.
	 */
	public String getTipoDocumento();

	/**
	 * Devuelve el asunto.
	 * 
	 * @return Asunto.
	 */
	public String getAsunto();

	/**
	 * Devuelve el repositorio.
	 * 
	 * @return Repositorio.
	 */
	public String getRepositorio();

	/**
	 * Devuelve el localizador para recuperar de forma única el documento.
	 * 
	 * @return Localizador.
	 */
	public String getLocalizador();

	/**
	 * Devuelve la extensión del fichero.
	 * 
	 * @return Extensión del fichero.
	 */
	public String getExtension();
}
