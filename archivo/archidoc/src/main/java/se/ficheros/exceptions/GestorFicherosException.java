package se.ficheros.exceptions;

import es.archigest.framework.core.exceptions.ArchigestModelException;

/**
 * Excepción lanzada cuando ocurre algún error el sistema gestor de ficheros
 */
public class GestorFicherosException extends ArchigestModelException {
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
	public GestorFicherosException(Exception e, String where, String msg) {
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
	public GestorFicherosException(Class where, String method, String cause) {
		super(where, method, cause);
	}

	/**
	 * Constructor.
	 *
	 * @param cause
	 *            Causa de la excepción.
	 */
	public GestorFicherosException(String cause) {
		super(cause);
	}
}
