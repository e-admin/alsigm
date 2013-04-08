package ieci.tdw.ispac.ispacpublicador.business.exceptions;

import ieci.tdw.ispac.api.errors.ISPACException;

import org.apache.log4j.Logger;

/**
 * Excepción lanzada cuando ocurre algún error en el publicador.
 *
 */
public class PublisherException extends ISPACException {

    /** Logger de la clase. */
    private static final Logger logger = 
    	Logger.getLogger(PublisherException.class);

    /**
     * Constructor.
     */
    public PublisherException() {
		super();
		logger.error("Error desconocido");
    }
    
    /**
     * Constructor.
     * @param message Mensaje de la excepción.
     * @param cause Causa de la excepción.
     */
    public PublisherException(String message, Throwable cause) {
		super(message,cause);
		logger.error(composeMessage(message+" - "+ cause.getMessage()));
    }
    
    /**
     * Constructor.
     * @param message Mensaje de la excepción.
     */
    public PublisherException(String message) {
    	super(message);
    	logger.error(composeMessage(message));
    }
    
    /**
     * Constructor.
     * @param cause Causa de la excepción.
     */
    public PublisherException(Throwable cause) {
        super(cause);
        logger.error(composeMessage(cause.getMessage()));
    }
    
    /**
     * Compone el mensaje de error.
     * @param message Mensaje del error.
     * @return Mensaje del error.
     */
	protected String composeMessage(String message)
	{
	    return	new StringBuffer()
	    	.append(message)
	    	.append("; ")
	    	.append(getStackTrace()[0].getFileName())
	    	.append("; ")
	    	.append(getStackTrace()[0].getMethodName())
	    	.append("; ")
	    	.append(getStackTrace()[0].getLineNumber())
	    	.toString();
	}    

}
