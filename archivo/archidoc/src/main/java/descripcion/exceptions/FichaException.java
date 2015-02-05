package descripcion.exceptions;

import common.exceptions.ArchivoModelException;

/**
 * Excepción lanzada cuando ocurre algún error en la la ficha de descripción.
 */
public class FichaException extends ArchivoModelException {

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
	public FichaException(Exception e, String where, String msg) {
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
	public FichaException(Class where, String method, String cause) {
		super(where, method, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public FichaException(String cause) {
		super(cause);
	}
}
