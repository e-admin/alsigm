package se.autenticacion;

import es.archigest.framework.core.exceptions.ArchigestModelException;

/**
 * Excepción lanzada cuando ocurre algún error en el conector con el sistema de
 * autenticación.
 */
public class AuthenticationConnectorException extends ArchigestModelException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

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
	public AuthenticationConnectorException(Exception e, String where,
			String msg) {
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
	public AuthenticationConnectorException(Class where, String method,
			String cause) {
		super(where, method, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public AuthenticationConnectorException(String cause) {
		super(cause);
	}
}
