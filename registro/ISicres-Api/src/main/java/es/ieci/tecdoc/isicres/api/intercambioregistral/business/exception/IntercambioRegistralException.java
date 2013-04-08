package es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception;


/**
 * Excepcion base para Intercambio Registral
 *
 */
public class IntercambioRegistralException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Código de la excepcion
	 */
	private Integer exceptionCode=IntercambioRegistralExceptionCodes.DEFAULT_CODE;



	public IntercambioRegistralException() {
	}

	public IntercambioRegistralException(String message) {
		super(message);
	}

	public IntercambioRegistralException(String message, Integer exceptionCode)
	{
		super(message);
		this.exceptionCode=exceptionCode;
	}

	public IntercambioRegistralException(Throwable cause) {
		super(cause);
	}

	public IntercambioRegistralException(String message, Throwable cause) {
		super(message, cause);
	}

	public Integer getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(Integer exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

}
