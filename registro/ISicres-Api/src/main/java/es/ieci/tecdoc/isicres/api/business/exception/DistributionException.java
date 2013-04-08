package es.ieci.tecdoc.isicres.api.business.exception;

/**
 * Excepción que se lanza en caso de que se produzca una situación erronea
 * durante la operativa de distribución de registros.
 * 
 * @author IECISA
 * 
 */
public class DistributionException extends RuntimeException {

	public DistributionException() {
		super();
	}

	public DistributionException(String message, Throwable cause) {
		super(message, cause);
	}

	public DistributionException(String message) {
		super(message);
	}

	public DistributionException(Throwable cause) {
		super(cause);
	}

	private static final long serialVersionUID = 2374110498606610075L;

}
