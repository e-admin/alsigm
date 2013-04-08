// 
// FileName: AuthenticationFactory.java
//
package com.ieci.tecdoc.utils.cache;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;

/**
 * Patrón AbstractFactory.
 * 
 * @author lmvicente
 * @version @since @creationDate 24-mar-2004
 */

public class CacheFactory {

	/***************************************************************************************************************************************
	 * Attributes
	 **************************************************************************************************************************************/

	private static final Logger log = Logger.getLogger(CacheFactory.class);

	private static CacheInterface currentCacheInterface = null;

	private static int cacheCleanerSleepTime = Integer.MIN_VALUE;
	
	private static String idGeneratorClass = null;

	/***************************************************************************************************************************************
	 * Constructors
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Public methods
	 **************************************************************************************************************************************/

	public static CacheInterface getCacheInterface() throws TecDocException {
		return getCacheInterface(Configurator.getInstance().getProperty(ConfigurationKeys.KEY_SERVER_CACHE_CLASS));
	}

	public static synchronized CacheInterface getCacheInterface(String currentCacheInterfaceName) throws TecDocException {
		// Debe existir un único objeto de cache, por eso se guarda y siempre se devuelve el mismo.
		if (currentCacheInterface == null) {
			try {
				Class cacheClass = Class.forName(currentCacheInterfaceName);
				currentCacheInterface = (CacheInterface) cacheClass.newInstance();
			} catch (ClassNotFoundException e) {
				log.fatal("Imposible instanciar [" + currentCacheInterfaceName + "].", e);
				throw new TecDocException(TecDocException.ERROR_CACHE_NOTFOUND);
			} catch (InstantiationException e) {
				log.fatal("Imposible instanciar [" + currentCacheInterfaceName + "].", e);
				throw new TecDocException(TecDocException.ERROR_CACHE_NOTFOUND);
			} catch (IllegalAccessException e) {
				log.fatal("Imposible instanciar [" + currentCacheInterfaceName + "].", e);
				throw new TecDocException(TecDocException.ERROR_CACHE_NOTFOUND);
			}
		}
		return currentCacheInterface;
	}

	public static int getCacheCleanerSleepTime() {
		if (cacheCleanerSleepTime == Integer.MIN_VALUE) {
			cacheCleanerSleepTime =
				Integer.parseInt(Configurator.getInstance().getProperty(ConfigurationKeys.KEY_SERVER_CACHE_CLEANER_SLEEP_TIME)) * 1000;
		}
		return cacheCleanerSleepTime;
	}

	public static String getIdGeneratorClass() {
		if (idGeneratorClass == null) {
			idGeneratorClass = Configurator.getInstance().getProperty(ConfigurationKeys.KEY_SERVER_CACHE_ENTRY_SESSION_IDGENERATOR);
		}
		return idGeneratorClass;
	}
	
	/***************************************************************************************************************************************
	 * Protected methods
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Private methods
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Inner classes
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Test brench
	 **************************************************************************************************************************************/

}
