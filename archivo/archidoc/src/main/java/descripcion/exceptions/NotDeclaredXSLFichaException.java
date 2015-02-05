package descripcion.exceptions;

import common.exceptions.ArchivoModelException;

/**
 * Excepción lanzada cuando ocurre algún error al obtener la XSL correspondiente
 * de la ficha.
 */
public class NotDeclaredXSLFichaException extends ArchivoModelException {

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
	public NotDeclaredXSLFichaException(Exception e, String where, String msg) {
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
	public NotDeclaredXSLFichaException(Class where, String method, String cause) {
		super(where, method, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public NotDeclaredXSLFichaException(String cause) {
		super(cause);
	}
}