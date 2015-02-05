/*
 * Created on 28-jul-2004
 *
 */
package ieci.tdw.ispac.api.errors;

/**
 * 
 *
 */
public class ISPACLockedObject extends ISPACException
{
    /** Log4j */
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
            .getLogger(ISPACLockedObject.class);
    
	public ISPACLockedObject()
	{
	    super(false);
	    logger.warn("El objeto se encuentra bloqueado por otra sesión");
	}

	/**
	 * @param message
	 */
	public ISPACLockedObject(String message)
	{
		super(message,false);
		logger.warn(message);
	}

	/**
	 * @param cause
	 */
	public ISPACLockedObject(Throwable cause)
	{
		super(cause,false);
		logger.warn(cause.getMessage());
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ISPACLockedObject(String message, Throwable cause)
	{
		super(message,cause,false);
		logger.warn(message+" - "+cause.getMessage());
	}
}
