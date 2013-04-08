package es.ieci.tecdoc.fwktd.audit.core.service.impl;

import es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService;

/**
 * Factoria de servicio
 * @author Iecisa
 *
 */
public class AuditoriaServiceFactory {
	
	public static final int SPRING_INTEGRATION=1;
	public static final int NON_SPRING_INTEGRATION=2;
	
	

	public static AuditoriaService getInstance(int type){
		AuditoriaService result =null;
		
		switch (type) {
		
		case SPRING_INTEGRATION:
				result = (AuditoriaService) FwtkdAuditSpringAppContext.getApplicationContext().getBean(AuditoriaSpringServicesNames.AUDITORIA_SERVICE_BEANNAME);
			break;
			
		case NON_SPRING_INTEGRATION:
			result = new AuditoriaServiceNonSpringImpl();
		break;	

		default:
			break;
		}

		return result;
		
	}
	
	/**
	 * @param type
	 * @param params
	 * @return
	 */
	public static AuditoriaService getInstance(int type, String... params){
		AuditoriaService result =null;
		
		switch (type) {
		
		case SPRING_INTEGRATION:
				result = (AuditoriaService) FwtkdAuditSpringAppContext.getApplicationContext().getBean(es.ieci.tecdoc.fwktd.audit.core.service.impl.AuditoriaSpringServicesNames.AUDITORIA_SERVICE_BEANNAME);
			break;
			
		case NON_SPRING_INTEGRATION:
			result = new AuditoriaServiceNonSpringImpl(params[0]);
		break;	

		default:
			break;
		}

		return result;
		
	}
	
}
