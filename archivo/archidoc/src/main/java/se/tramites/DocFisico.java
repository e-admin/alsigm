package se.tramites;

import java.io.Serializable;

/**
 * Interfaz para la información de un documento físico.
 */
public interface DocFisico extends Serializable {

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

}
