package es.ieci.tecdoc.isicres.api.business.exception;

public class UnknownDistributionException extends RuntimeException {

	public UnknownDistributionException() {
		super();
	}

	public UnknownDistributionException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownDistributionException(String message) {
		super(message);
	}

	public UnknownDistributionException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6882530591617638844L;

}
