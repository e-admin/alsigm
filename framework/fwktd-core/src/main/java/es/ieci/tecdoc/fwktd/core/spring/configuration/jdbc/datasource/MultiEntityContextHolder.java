package es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 *          Clase en la que se almacenará y de la que se obtendrá en las
 *          aplicaciones el identificador de la entidad que se está usando en
 *          un determinado momento
 * 
 */
public class MultiEntityContextHolder {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(MultiEntityContextHolder.class);

	private static ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	/**
	 * 
	 * @param entity
	 */
	public static void setEntity(String entity) {

		if (contextHolder.get() != null && !contextHolder.get().equals(entity)) {

			if (logger.isDebugEnabled()) {
				logger.debug("Seteando MultiEntityContext {} a {}",
						contextHolder.get(), entity);
			}
		}

		contextHolder.set(entity);
	}

	/**
	 * 
	 * @return
	 */
	public static String getEntity() {

		String result = (String) contextHolder.get();

		if (logger.isDebugEnabled()) {
			logger.debug("Entidad en uso -getEntity() - String result= {}",
					result);
		}

		return result;
	}

}
