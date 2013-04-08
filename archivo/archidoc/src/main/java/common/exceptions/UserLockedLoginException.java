package common.exceptions;

import es.archigest.framework.core.exceptions.ArchigestSystemException;

public class UserLockedLoginException extends ArchigestSystemException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public UserLockedLoginException(Class where, String method, String cause) {
		super(where, method, cause);
		nested = null;
	}

	public UserLockedLoginException(Exception e, String where, String msg) {
		super(e, where, msg);
		nested = null;
	}

	public UserLockedLoginException(String cause) {
		super(cause);
		nested = null;
	}

	public UserLockedLoginException(String where, Exception nested) {
		super("Error en proceso de Login, en: " + where);
		this.nested = null;
		this.nested = nested;
	}

	public String getMessage() {
		return nested != null ? "Excepcion incontrolada autentificando: "
				+ nested.getMessage() : super.getMessage();
	}

	public String getLocalizedMessage() {
		return nested != null ? "Excepcion incontrolada autentificando: "
				+ nested.getLocalizedMessage() : super.getLocalizedMessage();
	}

	private Exception nested;
}
