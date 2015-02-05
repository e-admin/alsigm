/**
 * 
 */
package es.ieci.tecdoc.fwktd.audit.core.exception.handler;

import es.ieci.tecdoc.fwktd.audit.core.exception.AuditoriaException;

/**
 * @author IECISA
 * 
 *         Interfaz del manejador de excepciones del servicio de auditoría
 * 
 */
public interface AuditoriaExceptionHandler {

	public void handleException(Exception e, String message)
			throws AuditoriaException;

}
