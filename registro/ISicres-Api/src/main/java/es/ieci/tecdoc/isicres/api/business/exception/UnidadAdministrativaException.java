package es.ieci.tecdoc.isicres.api.business.exception;

public class UnidadAdministrativaException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public UnidadAdministrativaException() {
		super();
	}

	/**
	 * @param message
	 */
	public UnidadAdministrativaException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public UnidadAdministrativaException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UnidadAdministrativaException(String message, Throwable cause) {
		super(message, cause);
	}

}
