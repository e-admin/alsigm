/*
 * Created on 28-jul-2004
 *
 */
package ieci.tdw.ispac.api.errors;

/**
 * @author Lema
 *
 */
public class ISPACNullObject extends ISPACException
{
    /** Log4j */
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
            .getLogger(ISPACNullObject.class);
    
	public ISPACNullObject()
	{
	    super(false);
	    logger.info("El objeto es nulo");
	}

	/**
	 * @param message
	 */
	public ISPACNullObject(String message)
	{
		super(message,false);
		logger.info(message);
	}

	public ISPACNullObject(String message, Object args[])
	{
		super(message, args, false);
		logger.info(message);
	}
	
	/**
	 * @param cause
	 */
	public ISPACNullObject(Throwable cause)
	{
		super(cause,false);
		logger.info(cause.getMessage());
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ISPACNullObject(String message, Throwable cause)
	{
		super(message,cause,false);
		if (logger.isInfoEnabled()) {
			logger.info(message+" - "+cause.getMessage());
		}
	}
}
