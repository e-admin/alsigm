package es.ieci.tecdoc.fwktd.audit.integration.business.manager;

import java.util.List;

import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.integration.business.vo.AuditEventVO;

/**
 * Interface encargado de generar una lista de trazas de auditoría a partir de
 * un evento de auditoria generado en una aplicación
 * 
 * Cada aplicación tendrá su propia jerarquía de objetos de tipo
 * {@link AuditEventVO} y tendrá que implementar este inteface
 * TrazaAuditoriaBuilder, ya que las aplicaciones son las que conocen el numero
 * y tipo de trazas a generar por un evento generado en ellas. Un evento
 * producido en una aplicación puede generar varias trazas de auditoria.
 * Ejemplo: Un evento de creación de un registro puede generar: 1 Traza de
 * creaciónn de registro, n Trazas de creacion de campos de registro
 * 
 * @author Iecisa
 * 
 */
public interface TrazaAuditoriaBuilder {

	/**
	 * Metodo encargado de generar una lista de trazas de auditoría a partir de
	 * un evento de auditoria generado en una aplicación
	 * 
	 * @param eventoAuditar
	 * @return
	 */
	public List<TrazaAuditoriaVO> buildTrazas(AuditEventVO eventoAuditar);
}
