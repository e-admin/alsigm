package ieci.tdw.ispac.ispacpublicador.business.exceptions;

import org.apache.log4j.Logger;

public class ActionException extends PublisherException {
	
	private static final long serialVersionUID = 1L;
	
	/** Logger de la clase. */
    private static final Logger logger = 
    	Logger.getLogger(ActionException.class);
    
	/**
	 * Constructor. 
	 */
	public ActionException() {
		super();
		logger.error("Error desconocido.");
	}

	/**
	 * @param message
	 */
	public ActionException(String message) {
		super(message);
		logger.error(message);
	}

	/**
	 * @param causeExp
	 */
	public ActionException(Throwable causeExp) {
		super(causeExp);
		logger.error(causeExp.getMessage());
	}

	/**
	 * @param message
	 * @param causeExp
	 */
	public ActionException(String message, Throwable causeExp) {
		super(message, causeExp);
		logger.error(message+" - "+causeExp.getMessage());
	}
}
