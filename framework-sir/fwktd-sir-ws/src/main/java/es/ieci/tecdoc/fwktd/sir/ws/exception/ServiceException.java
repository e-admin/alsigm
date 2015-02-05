package es.ieci.tecdoc.fwktd.sir.ws.exception;

import es.ieci.tecdoc.fwktd.sir.core.types.ErroresEnum;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1476251379865667903L;

	private ErroresEnum error = null;

	/**
	 * Constructor.
	 */
	public ServiceException() {
		super();
	}

	public ServiceException(ErroresEnum error) {
		super();
		setError(error);
	}

	public ServiceException(ErroresEnum error, Throwable cause) {
		super(cause);
		setError(error);
	}

	public ErroresEnum getError() {
		return error;
	}

	public void setError(ErroresEnum error) {
		this.error = error;
	}

}
