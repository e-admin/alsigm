package es.ieci.tecdoc.fwktd.audit.core.service.impl;

import org.springframework.context.ApplicationContext;

/**
 * Clase que proporciona acceso al contexto de spring del modulo de auditoria
 * @see  FwktdAuditSpringApplicationContextProvider
 * @author Iecisa
 *
 */
public class FwtkdAuditSpringAppContext {

	   private static ApplicationContext ctx;
	   

	    /**
	     * Injectqadeo desde la clase {@link FwktdAuditSpringApplicationContextProvider} que es ejecutado
	     * automáticamente en la Spring-Initialization.
	     */
	    public static void setApplicationContext(ApplicationContext applicationContext) {
	        ctx = applicationContext;
	    }

	    /**
	     * Proporciona acceso al Spring ApplicationContext desde cualquier punto de las aplicaciones
	     *
	     * @return
	     */
	    public static ApplicationContext getApplicationContext() {
	        return ctx;
	    }
}
