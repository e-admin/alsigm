package es.ieci.tecdoc.isicres.api.business.exception;

public class LoginException extends RuntimeException {

	public LoginException() {
		super();
	}

	public LoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginException(String message) {
		super(message);
	}

	public LoginException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1677678832953662885L;

}
