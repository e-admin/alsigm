package descripcion.model.automaticos;

import common.exceptions.CheckedArchivoException;

/**
 * Excepción lanzada cuando ocurre algún error en la generación de datos
 * automáticos.
 */
public class ADReglaGenDatosException extends CheckedArchivoException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public ADReglaGenDatosException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param e
	 *            Excepción capturada.
	 */
	public ADReglaGenDatosException(Throwable e) {
		super(e);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            Causa de la excepción.
	 * @param cause
	 *            Causa de la excepción.
	 */
	public ADReglaGenDatosException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public ADReglaGenDatosException(String cause) {
		super(cause);
	}

}
