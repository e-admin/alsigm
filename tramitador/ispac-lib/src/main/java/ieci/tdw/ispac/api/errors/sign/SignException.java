package ieci.tdw.ispac.api.errors.sign;

import ieci.tdw.ispac.api.errors.ISPACException;

public class SignException extends ISPACException {

	private static final long serialVersionUID = 1L;

	public SignException() {
		super();
	}

	public SignException(String message) {
		super(message);
	}

	public SignException(Throwable cause) {
		super(cause);
	}

	public SignException(String message, Throwable cause) {
		super(message, cause);
	}

}
