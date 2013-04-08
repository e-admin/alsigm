/**
 * 
 */
package es.ieci.tecdoc.isicres.legacy.ws.manager.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
public class ISicresConfigurationHelper {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ISicresConfigurationHelper.class);

	public static int getFirstCollectionsInitValue() {
		if (logger.isDebugEnabled()) {
			logger.debug("getFirstCollectionsInitValue() - start");
		}

		int firstCollectionsInitValue = 1;
		try {
			String firstCollectionsInitValueConfigutation = Configurator
					.getInstance()
					.getProperty(
							ConfigurationKeys.KEY_WS_FIRST_COLLECTIONS_INIT_VALUE);
			if (StringUtils.isNotEmpty(firstCollectionsInitValueConfigutation)) {
				if (firstCollectionsInitValueConfigutation
						.equalsIgnoreCase("1")) {
					firstCollectionsInitValue = 1;
				} else {
					firstCollectionsInitValue = 0;
				}
			}
		} catch (Exception e) {
			logger.error(
					"Ha ocurrido un error al obtener el valor del nodo de configuración: "
							+ ConfigurationKeys.KEY_WS_FIRST_COLLECTIONS_INIT_VALUE,
					e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getFirstCollectionsInitValue() - end");
		}
		return firstCollectionsInitValue;
	}

}
