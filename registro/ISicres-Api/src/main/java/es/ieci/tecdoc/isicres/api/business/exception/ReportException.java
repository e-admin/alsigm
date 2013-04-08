package es.ieci.tecdoc.isicres.api.business.exception;

/**
 * 
 * @author IECISA
 * 
 */
public class ReportException extends RuntimeException {

	public ReportException() {
	}

	public ReportException(String message) {
		super(message);
	}

	public ReportException(Throwable cause) {
		super(cause);
	}

	public ReportException(String message, Throwable cause) {
		super(message, cause);
	}

	private static final long serialVersionUID = -7741102931261613271L;
}
