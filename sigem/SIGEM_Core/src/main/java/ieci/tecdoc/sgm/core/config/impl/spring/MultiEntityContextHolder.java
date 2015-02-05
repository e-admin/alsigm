package ieci.tecdoc.sgm.core.config.impl.spring;

import org.apache.log4j.Logger;

public class MultiEntityContextHolder {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(MultiEntityContextHolder.class);
	
	
	
	private static ThreadLocal contextHolder = new ThreadLocal();
	
	public static void setEntity(String entity) {
		
		if (contextHolder.get() != null && !contextHolder.get().equals(entity)) {
			
			if (logger.isDebugEnabled()) {
				logger.debug("Seteando MultiEntityContext " + contextHolder.get() + " a " + entity);
			}
		}
		
		contextHolder.set(entity);
	}

	public static String getEntity() {
		
		String result=(String) contextHolder.get();
		
		if (logger.isDebugEnabled()) {
			logger.debug("Entidad en uso -getEntity() - String result=" + result);
		}
				
		return result;
	}
}
