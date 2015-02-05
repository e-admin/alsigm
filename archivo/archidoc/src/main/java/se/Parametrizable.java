package se;

import java.util.Properties;

import common.exceptions.SistemaExternoException;

/**
 * Interfaz para los contectores que sean parametrizables.
 */
public interface Parametrizable {

	/**
	 * Inicializa con los parámetros de configuración.
	 * 
	 * @param params
	 *            Parámetros de configuración.
	 * @throws SistemaExternoException
	 *             si ocurre algún error.
	 */
	public void initialize(Properties params) throws SistemaExternoException;

}
