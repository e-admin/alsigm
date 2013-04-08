package descripcion.exceptions;

import common.exceptions.SecurityException;

/**
 * Excepción lanzada cuando ocurre algún error en la la ficha de descripción al
 * no tener permisos para salvar un descriptor.
 */
public class DescriptorRightsException extends SecurityException {

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
	public DescriptorRightsException(Exception e, String where, String msg) {
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
	public DescriptorRightsException(Class where, String method, String cause) {
		super(where, method, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public DescriptorRightsException(String cause) {
		super(cause);
	}
}
