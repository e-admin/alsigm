/**
 * 
 */
package ieci.tdw.ispac.audit.business.manager.impl;

import ieci.tdw.ispac.audit.business.IspacAuditoriaManager;
import es.ieci.tecdoc.fwktd.audit.core.service.impl.AuditoriaServiceFactory;
import es.ieci.tecdoc.fwktd.audit.integration.business.manager.impl.AuditoriaAplicationManagerImpl;

/**
 * 
 * Implementación  del sistema de auditoría para Ispac
 * 
 * @author IECISA
 * @version $Revision$
 * 
 */
public class IspacAuditoriaManagerImpl extends AuditoriaAplicationManagerImpl implements
		IspacAuditoriaManager {
	
	public IspacAuditoriaManagerImpl() {
		setAuditoriaService(AuditoriaServiceFactory
				.getInstance(AuditoriaServiceFactory.NON_SPRING_INTEGRATION));
		setTrazaAuditoriaBuilder(new IspacTrazaAuditoriaBuilderImpl());
	}

}
