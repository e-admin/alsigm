package se.tramites;

import java.io.Serializable;

/**
 * Interfaz para la información básica de un expediente.
 */
public interface InfoBExpediente extends Serializable {

	/**
	 * Devuelve el identificador único.
	 * 
	 * @return Identificador único.
	 */
	public String getId();

	/**
	 * Devuelve el número de expediente.
	 * 
	 * @return Número de expediente.
	 */
	public String getNumExp();

	/**
	 * Devuelve los datos que identifican al expediente.
	 * 
	 * @return Datos que identifican al expediente.
	 */
	public String getDatosIdentificativos();

}
