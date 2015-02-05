package ieci.tecdoc.sgm.core.services.rpadmin;

import ieci.tecdoc.sgm.core.exception.SigemException;

import java.util.Locale;

/**
 * 
 * Clase de Excepciones especificas de la Administración de Registro presencial
 * 
 */
public class RPAdminException extends SigemException {

	private static final long serialVersionUID = 1L;
	public static final long EXC_GENERIC_EXCEPCION = 12470000;
	public static final long USUARIO_INEXISTENTE = EXC_GENERIC_EXCEPCION + 1;
	public static final long OFICINA_INEXISTENTE = EXC_GENERIC_EXCEPCION + 2;
	public static final long OFICINA_CON_REFERENCIAS = EXC_GENERIC_EXCEPCION + 3;
	public static final long OFICINA_CON_USUARIOS = EXC_GENERIC_EXCEPCION + 4;
	public static final long ORGANIZACION_CON_HIJAS = EXC_GENERIC_EXCEPCION + 5;
	public static final long ORGANIZACION_CON_REFERENCIAS = EXC_GENERIC_EXCEPCION + 6;
	public static final long USUARIO_SICRES_EXISTENTE = EXC_GENERIC_EXCEPCION + 7;
	public static final long OFICINA_SICRES_EXISTENTE = EXC_GENERIC_EXCEPCION + 8;
	public static final long IDGENERATOR_TABLE_NOT_FOUND = EXC_GENERIC_EXCEPCION + 9;
	public static final long CODIGO_OFICINA_SICRES_EXISTENTE = EXC_GENERIC_EXCEPCION + 10;
	public static final long CODIGO_ORGANIZACION_SICRES_EXISTENTE = EXC_GENERIC_EXCEPCION + 11;
	public static final long CODIGO_TIPO_ORGANIZACION_SICRES_EXISTENTE = EXC_GENERIC_EXCEPCION + 12;

	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.rpadmin.RPAdminExceptionMessages";

	/**
	 * @param errorCode
	 */
	public RPAdminException(long errorCode) {
		this(errorCode, null);
	}

	/**
	 * @param message
	 */
	public RPAdminException(String message) {
		this(message, null);
	}

	/**
	 * @param cause
	 */
	public RPAdminException(Throwable cause) {
		this(0, cause);
	}

	/**
	 * @param errorCode
	 * @param message
	 * @param cause
	 */
	public RPAdminException(long errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}

	/**
	 * @param errorCode
	 * @param cause
	 */
	public RPAdminException(long errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RPAdminException(String message, Throwable cause) {
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
