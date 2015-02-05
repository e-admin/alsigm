package es.ieci.tecdoc.fwktd.audit.integration.business.manager;

import es.ieci.tecdoc.fwktd.audit.core.vo.AppAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.integration.business.manager.impl.AuditoriaAplicationManagerImpl;
import es.ieci.tecdoc.fwktd.audit.integration.business.vo.AuditEventVO;

/**
 * Interface de integración en las aplicaciones del sistema de auditoria Se
 * encargará de realizar las auditorias correspondientes. Cada aplicación tendrá
 * su propia jerarquía de objetos de tipo {@link AuditEventVO}
 * 
 * Un evento producido en una aplicación puede generar varias trazas de
 * auditoria {@link TrazaAuditoriaVO} 
 * 
 * Ejemplo: Un evento de creación de un
 * registro puede generar: 1 Traza de creaciónn de registro, n Trazas de creacion
 * de campos de registro
 * 
 * 
 * @author IECISA
 * 
 *         Implementación abstracta disponible para usar en las aplicaciones:
 * 
 * @see {@link AuditoriaAplicationManagerImpl}
 */
public interface AuditoriaAplicationManager {

	/**
	 * Audita el evento producido por la aplicacion
	 * 
	 * @param evento
	 */
	public void audit(AuditEventVO evento);
	
	/**
	 * Obtiene las aplicaciones registradas en la tabla de auditorías a partir de su descripción.
	 * 
	 * Las aplicaciones registradas tienen que tener una descripción y un identificador único
	 * @param descripcion
	 * @return
	 */
	public AppAuditoriaVO getAppAuditoria(String descripcion);

}
