/*
 * Created on 10-jun-2004
 *
 */
package ieci.tdw.ispac.api.errors;


/**
 * @author juanin
 *
 * Notificación del sistema
 */
public class ISPACInfo extends ISPACException
{
	private static final long serialVersionUID = 1L;
	
	//Indica si queremos relizar refresco o no de la página padre tras mostrar el mensaje de error
	private boolean refresh=true;

	/** Log4j */
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
            .getLogger(ISPACInfo.class);
    

	public ISPACInfo()
	{
	    super(false);
	    logger.info("");
	}

	/**
	 * @param message
	 */
	public ISPACInfo(String message)
	{
		super(message,false);
		logger.info(message);
	}
	
	public ISPACInfo (String message, boolean refresh){
		
		super(message,false);
		logger.info(message);
		this.refresh=refresh;
	}

	/**
	 * @param message
	 */
	public ISPACInfo(String message, String extraInfo)
	{
		super(message,extraInfo, false);
		logger.info(message);
	}
	
	public ISPACInfo(String message, String extraInfo, boolean refresh)
	{
		super(message,extraInfo, false);
		logger.info(message);
		this.refresh=refresh;
	}
	public ISPACInfo(String message, Object args[])
	{
		super(message, args, false);
		logger.info(message);
	}
	
	public ISPACInfo(String message, Object args[],boolean refresh)
	{
		super(message, args, false);
		logger.info(message);
		this.refresh=refresh;
	}
	/**
	 * @param cause
	 */
	public ISPACInfo(Throwable cause)
	{
		super(cause,false);
		logger.info(cause.getMessage());
	}

	
	/**
	 * @param cause
	 */
	public ISPACInfo(Throwable cause, boolean refresh)
	{
		super(cause,false);
		logger.info(cause.getMessage());
		this.refresh=refresh;
	}
	/**
	 * @param message
	 * @param cause
	 */
	public ISPACInfo(String message, Throwable cause)
	{
		super(message, cause,false);
		logger.info(message+" - "+cause.getMessage());
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ISPACInfo(String message, Throwable cause, boolean refresh)
	{
		super(message, cause,false);
		logger.info(message+" - "+cause.getMessage());
		this.refresh = refresh;
	}	
	
	public boolean isRefresh() {
		return refresh;
	}

	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}
}
