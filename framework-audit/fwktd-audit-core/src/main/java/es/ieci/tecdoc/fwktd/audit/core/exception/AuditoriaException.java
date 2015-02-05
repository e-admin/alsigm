/**
 * 
 */
package es.ieci.tecdoc.fwktd.audit.core.exception;

/**
 * @author IECISA
 * 
 *         Excepción que lanza el servicio de auditoría cuando ha ocurrido algún
 *         error
 * 
 */
public class AuditoriaException extends RuntimeException {

	public AuditoriaException(String message, Exception e) {
		super(message, e);
	}
	
	public AuditoriaException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1898915257064408202L;

}
