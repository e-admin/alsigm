package ieci.tdw.ispac.api.errors;


public class ISPACSessionException extends ISPACException
{
    /** Log4j */
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
            .getLogger(ISPACSessionException.class);
    
	public ISPACSessionException()
	{
	    super(false);
	    logger.info("Sesion caducada o inexistente");
	}

	/**
	 * @param message
	 */
	public ISPACSessionException(String message)
	{
		super(message,false);
		logger.info(message);
	}

	public ISPACSessionException(String message, Object args[])
	{
		super(message, args, false);
		logger.info(message);
	}
	
	/**
	 * @param cause
	 */
	public ISPACSessionException(Throwable cause)
	{
		super(cause,false);
		logger.info(cause.getMessage());
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ISPACSessionException(String message, Throwable cause)
	{
		super(message,cause,false);
		logger.info(message+" - "+cause.getMessage());
		
	}
}
