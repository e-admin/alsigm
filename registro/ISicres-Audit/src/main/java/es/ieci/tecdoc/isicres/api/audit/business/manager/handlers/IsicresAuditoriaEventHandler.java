package es.ieci.tecdoc.isicres.api.audit.business.manager.handlers;

import es.ieci.tecdoc.isicres.api.audit.business.vo.enums.IsicresEventAuditHandlerTypeEnum;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditEventVO;

/**
 * Interfaz de manejador de eventos de auditoria
 * @author IECISA
 *
 */
public interface IsicresAuditoriaEventHandler {

	/**
	 * metodo para manejar un evento de auditoria devolviendo algun resultado generico
	 * @param isicresAuditEvent
	 * @return
	 */
	public Object handle(IsicresAuditEventVO isicresAuditEvent);
	
	/**
	 * Metodo que devuelve el tipo de manjeador que se esta utilizando.
	 * @return
	 */
	public IsicresEventAuditHandlerTypeEnum getTypeHandler();
	
	
}
