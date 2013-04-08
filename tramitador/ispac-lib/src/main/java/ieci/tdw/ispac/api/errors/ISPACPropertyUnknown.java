
package ieci.tdw.ispac.api.errors;

/**
 * @author juanin
 *
 * El objeto no posee la propiedad requerida.
 */
public class ISPACPropertyUnknown extends ISPACException 
{
    /** Log4j */
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
            .getLogger(ISPACInfo.class);

	public ISPACPropertyUnknown()
	{
	    super(false);
	    logger.warn("Propiedad desconocida");
	}  
	/**
	 * @param message
	 */
	public ISPACPropertyUnknown(String message)
	{
		super(message,false);
		logger.warn(message);
	}

	/**
	 * @param cause
	 */
	public ISPACPropertyUnknown(Throwable cause)
	{
		super(cause,false);
		logger.warn(cause.getMessage());
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ISPACPropertyUnknown(String message, Throwable cause)
	{
		super(message, cause, false);
		logger.warn(message+" - "+cause.getMessage());
	}
}
