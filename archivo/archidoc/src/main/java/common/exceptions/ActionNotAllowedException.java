package common.exceptions;

/**
 * Excepcion lanzada cuando no se puede realizar una determinada accion.
 */
public class ActionNotAllowedException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int codError = -1;
	private int module = 0;

	private Error error = null;

	public ActionNotAllowedException(String debugInfo, int codError, int module) {
		super(debugInfo);
		setCodError(codError);
		setModule(module);
	}

	/**
	 * Constructor.
	 * 
	 * @param e
	 *            Excepción capturada.
	 */
	// public ActionNotAllowedException(Throwable e) {
	// super(e);
	// }

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            Causa de la excepción.
	 * @param cause
	 *            Causa de la excepción.
	 */
	// public ActionNotAllowedException(String message, Throwable cause)
	// {
	// super(message,cause);
	// }
	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	// public ActionNotAllowedException(String cause)
	// {
	// super(cause);
	// }
	/**
	 * Constructor.
	 * 
	 * @param codError
	 *            Codigo de error que provoco la exception
	 */
	public ActionNotAllowedException(int codError) {
		super();
		this.codError = codError;
	}

	/**
	 * Constructor.
	 * 
	 * @param codError
	 *            Codigo de error que provoco la exception
	 */
	public ActionNotAllowedException(int codError, String cause) {
		super(cause);
		this.codError = codError;
	}

	public int getCodError() {
		return codError;
	}

	public void setCodError(int codError) {
		this.codError = codError;
	}

	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

	public Error getError() {
		return error;
	}
}