package es.ieci.tecdoc.fwktd.time.exception;

public class TimeException extends Exception {

	private static final long serialVersionUID = -4048002778120563128L;

	public TimeException() {
		super();
	}

	public TimeException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public TimeException(String message) {
		super(message);
	}

	public TimeException(Throwable throwable) {
		super(throwable);
	}
}
