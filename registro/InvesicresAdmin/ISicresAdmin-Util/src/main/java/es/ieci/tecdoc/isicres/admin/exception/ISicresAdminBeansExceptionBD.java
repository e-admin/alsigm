package es.ieci.tecdoc.isicres.admin.exception;

/**
 * Clase de Excepciones de Base de Datos especificas de la Administración de
 * Registro presencial
 * 
 * @see ISicresAdminBeansException.tecdoc.sgm.core.services.rpadmin.RPAdminException
 * 
 */
public class ISicresAdminBeansExceptionBD extends ISicresAdminBeansException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param errorCode
	 */
	public ISicresAdminBeansExceptionBD(long errorCode) {
		super(errorCode);
	}

	/**
	 * @param message
	 */
	public ISicresAdminBeansExceptionBD(String message) {
		this(message, null);
	}

	/**
	 * @param cause
	 */
	public ISicresAdminBeansExceptionBD(Throwable cause) {
		this(0, cause);
	}

	/**
	 * @param errorCode
	 * @param message
	 * @param cause
	 */
	public ISicresAdminBeansExceptionBD(long errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	/**
	 * @param errorCode
	 * @param cause
	 */
	public ISicresAdminBeansExceptionBD(long errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ISicresAdminBeansExceptionBD(String message, Throwable cause) {
		super(message, cause);
	}

}
