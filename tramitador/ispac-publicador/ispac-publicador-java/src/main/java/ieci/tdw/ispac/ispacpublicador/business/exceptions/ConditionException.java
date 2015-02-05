package ieci.tdw.ispac.ispacpublicador.business.exceptions;

import org.apache.log4j.Logger;

public class ConditionException extends PublisherException {
	
	/** Logger de la clase. */
    private static final Logger logger = 
    	Logger.getLogger(ConditionException.class);
    
	/**
	 * Constructor.
	 */
	public ConditionException() {
		super();
		logger.error("Error desconocido.");
	}

	/**
	 * @param message
	 */
	public ConditionException(String message) {
		super(message);
		logger.error(message);
	}

	/**
	 * @param causeExp
	 */
	public ConditionException(Throwable causeExp) {
		super(causeExp);
		logger.error(causeExp.getMessage());
	}

	/**
	 * @param message
	 * @param causeExp
	 */
	public ConditionException(String message, Throwable causeExp) {
		super(message, causeExp);
		logger.error(message+" - "+causeExp.getMessage());
	}
}
