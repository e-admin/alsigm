package common.exceptions;

import es.archigest.framework.core.exceptions.ArchigestModelException;

/**
 * Excepción generada cuando ocurre algún error en la configuración.
 */
public class ConfigException extends ArchigestModelException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5900316406567138894L;

	/**
	 * Constructor.
	 * 
	 * @param e
	 *            Excepción capturada.
	 * @param where
	 *            Lugar donde se ha lanzado la excepción.
	 * @param msg
	 *            Texto del mensaje.
	 */
	public ConfigException(Exception e, String where, String msg) {
		super(e, where, msg);
	}

	/**
	 * Constructor.
	 * 
	 * @param where
	 *            Lugar donde se ha lanzado la excepción.
	 * @param method
	 *            Método que lanza la excepción.
	 * @param cause
	 *            Causa de la excepción.
	 */
	public ConfigException(Class where, String method, String cause) {
		super(where, method, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public ConfigException(String cause) {
		super(cause);
	}
}
