package es.ieci.tecdoc.isicres.api.audit.business.manager.impl;

import es.ieci.tecdoc.fwktd.audit.core.service.impl.AuditoriaServiceFactory;
import es.ieci.tecdoc.fwktd.audit.integration.business.manager.impl.AuditoriaAplicationManagerImpl;
import es.ieci.tecdoc.isicres.api.audit.business.manager.IsicresAuditoriaManager;

/**
 * Implementacion del sistema de auditoria para registro
 *
 * @author Ieicisa
 *
 * {@link AuditoriaAplicationManager}
 * {@link AuditoriaAplicationManagerImpl}
 * {@link IsicresAuditoriaManager}
 *
 */
public class IsicresAuditoriaManagerImpl extends AuditoriaAplicationManagerImpl
		implements IsicresAuditoriaManager {

	public IsicresAuditoriaManagerImpl() {
		setAuditoriaService(AuditoriaServiceFactory
				.getInstance(AuditoriaServiceFactory.NON_SPRING_INTEGRATION));
		setTrazaAuditoriaBuilder(new IsicresTrazaAuditoriaBuilderImpl());
	}

}
