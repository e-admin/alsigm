package ieci.tecdoc.sgm.core.services.registro;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;

/**
 * Clase de excepciones especificas de Registro Presencial
 * 
 */
public class RegistroException extends SigemException {

	// Tipos de excepcion

	private static final long serialVersionUID = 1L;
	public static final long EC_PREFIX = 2000010000;
	public static final long EXC_GENERIC_EXCEPCION = EC_PREFIX;

	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.registro.RegistroExceptionMessages";

	/**
	 * @param errorCode
	 */
	public RegistroException(long errorCode) {
		this(errorCode, null);
	}

	/**
	 * @param message
	 */
	public RegistroException(String message) {
		this(message, null);
	}

	/**
	 * @param cause
	 */
	public RegistroException(Throwable cause) {
		this(0, cause);
	}

	/**
	 * @param errorCode
	 * @param message
	 * @param cause
	 */
	public RegistroException(long errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}

	/**
	 * @param errorCode
	 * @param cause
	 */
	public RegistroException(long errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RegistroException(String message, Throwable cause) {
		super(message, cause);
		errorMessage = message;
	}

	/**
	 * Devuelve el nombre del archivo de recursos que contiene los mensajes para
	 * esta excepción.
	 * 
	 * @return El nombre del archivo de recursos mencionado.
	 */

	/* (non-Javadoc)
	 * @see ieci.tecdoc.sgm.core.exception.SigemException#getMessagesFile()
	 */
	public String getMessagesFile() {

		return RESOURCE_FILE;
	}

}
