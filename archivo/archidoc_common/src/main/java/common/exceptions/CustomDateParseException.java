package common.exceptions;


/**
 * Excepción generada cuando ocurre algún error en el análisis sintáctico de una fecha.
 */
public class CustomDateParseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public CustomDateParseException(){
		super();
	}

	/**
	 * Constructor.
	 * @param cause Causa de la excepción.
	 */
	public CustomDateParseException(String cause){
		super(cause);
	}

	/**
	 * Constructor.
	 * @param cause Causa de la excepción.
	 * @param exception Excepcion que se lanza.
	 */
	public CustomDateParseException(String cause, Exception exception)
	{
		super(cause, exception);
	}
}
