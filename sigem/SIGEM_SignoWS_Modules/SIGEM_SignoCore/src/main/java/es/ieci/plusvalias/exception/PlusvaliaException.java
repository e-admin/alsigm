package es.ieci.plusvalias.exception;

/**
 * @author angel_castro@ieci.es - 10/09/2010
 */
public class PlusvaliaException extends RuntimeException
{
	private static final long serialVersionUID = -7926375157119663394L;
	private String errorCode;

	public PlusvaliaException(String errorCode)
	{
		super();
		this.errorCode = errorCode;
	}

	public PlusvaliaException(String errorCode, Throwable cause)
	{
		super(cause);
		this.errorCode = errorCode;
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}
}
