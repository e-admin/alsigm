package docelectronicos.exceptions;

/**
 * Exception lanzada si se produce un acceso no permitido a un servicio al que
 * no tiene acceso un determinado usuario.
 */
public class DocElectronicosSecurityException extends
		common.exceptions.SecurityException {

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
	public DocElectronicosSecurityException(Exception e, String where,
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
	public DocElectronicosSecurityException(Class where, String method,
			String cause) {
		super(where, method, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public DocElectronicosSecurityException(String cause) {
		super(cause);
	}
}
