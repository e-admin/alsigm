/**
 * 
 */
package ieci.tdw.ispac.audit.context;

import ieci.tdw.ispac.audit.business.vo.AuditContext;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
public class AuditContextHolder {

	/**
	 * Thread Local donde se almacena el contexto para la auditoría.
	 */
	private static ThreadLocal<AuditContext> threadLocal = new ThreadLocal<AuditContext>();

	public static void setAuditContext(AuditContext auditContext) {
		threadLocal.set(auditContext);
	}

	public static AuditContext getAuditContext() {
		return threadLocal.get();
	}
}
