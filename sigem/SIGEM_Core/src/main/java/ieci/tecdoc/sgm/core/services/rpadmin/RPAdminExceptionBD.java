package ieci.tecdoc.sgm.core.services.rpadmin;

/**
 * Clase de Excepciones de Base de Datos especificas de la Administración de
 * Registro presencial
 * 
 * @see ieci.tecdoc.sgm.core.services.rpadmin.RPAdminException
 * 
 */
public class RPAdminExceptionBD extends RPAdminException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param errorCode
	 */
	public RPAdminExceptionBD(long errorCode) {
		super(errorCode);
	}

	/**
	 * @param message
	 */
	public RPAdminExceptionBD(String message) {
		this(message, null);
	}

	/**
	 * @param cause
	 */
	public RPAdminExceptionBD(Throwable cause) {
		this(0, cause);
	}

	/**
	 * @param errorCode
	 * @param message
	 * @param cause
	 */
	public RPAdminExceptionBD(long errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	/**
	 * @param errorCode
	 * @param cause
	 */
	public RPAdminExceptionBD(long errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RPAdminExceptionBD(String message, Throwable cause) {
		super(message, cause);
	}

}
