/**
 * 
 */
package ieci.tdw.ispac.audit.business.handlers;

import ieci.tdw.ispac.audit.business.vo.enums.IspacEventAuditHandlerTypeEnum;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventVO;

/**
 * 
 * Interface que define los métodos que tendrán los manejadores de eventos
 * 
 * @author IECISA
 * @version $Revision$
 * 
 */
public interface IspacAuditoriaEventHandler {

	/**
	 * Metodo para manejar un evento de auditoria devolviendo algun resultado
	 * generico
	 * 
	 * @param ispacAuditEvent
	 * @return
	 */
	public Object handle(IspacAuditEventVO ispacAuditEvent);

	/**
	 * Metodo que devuelve el tipo de manjeador que se esta utilizando.
	 * 
	 * @return
	 */
	public IspacEventAuditHandlerTypeEnum getTypeHandler();

}
