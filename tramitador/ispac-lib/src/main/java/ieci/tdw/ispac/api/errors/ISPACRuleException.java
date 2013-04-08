/*
 * Created on 02-jun-2004
 *
 */
package ieci.tdw.ispac.api.errors;


/**
 * @author juanin
 *
 */
public class ISPACRuleException extends ISPACException
{
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
    .getLogger(ISPACRuleException.class);
	/**
	 * 
	 */
	public ISPACRuleException()
	{
		super(false);
		logger.error("Error desconocido.");
	}

	/**
	 * @param message
	 */
	public ISPACRuleException(String message)
	{
		super(message,false);
		logger.error(message);
	}

	/**
	 * @param causeExp
	 */
	public ISPACRuleException(Throwable causeExp)
	{
		super(causeExp,false);
		logger.error(causeExp.getMessage());
	}

	/**
	 * @param message
	 * @param causeExp
	 */
	public ISPACRuleException(String message, Throwable causeExp)
	{
		super(message, causeExp,false);
		logger.error(message+" - "+causeExp.getMessage());
	}

}
