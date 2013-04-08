package es.ieci.tecdoc.fwktd.audit.core.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ApplicationContextAware para el modulo de auditori
 * @author Iecisa
 *
 */
public class FwktdAuditSpringApplicationContextProvider implements
		ApplicationContextAware {

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {

		FwtkdAuditSpringAppContext.setApplicationContext(applicationContext);

	}

}
