//
// FileName: IDGeneratorFactory.java 
//
package com.ieci.tecdoc.utils.cache.idgenerator;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * @author lmvicente
 * @version @since @creationDate 01-abr-2004
 */

public class IDGeneratorFactory {
	//-------------------
	// Private attributes
	//-------------------

	private static final Logger log = Logger.getLogger(IDGeneratorFactory.class);

	/** Only one instance of the IDGenerator. */
	private static IDGenerator idGenerator = null;

	//---------------
	// Public  methods
	//---------------

	/**
	 * Return an implementation of the IDGenerator interface for a given class name.
	 * 
	 * @return the IDGenerator.
	 * @throws SessionException
	 *             if is impossible to obtain this IDGenerator implementation.
	 */
	public synchronized static IDGenerator getIDGenerator() throws SessionException {
		if (idGenerator == null) {
			try {
				Class instance = Class.forName(CacheFactory.getIdGeneratorClass());
				idGenerator = (IDGenerator) instance.newInstance();
			} catch (ClassNotFoundException e) {
				log.fatal("Imposible instanciar [" + CacheFactory.getIdGeneratorClass() + "].", e);
				throw new SessionException(SessionException.ERROR_SESSION_EXPIRED);
			} catch (InstantiationException e) {
				log.fatal("Imposible instanciar [" + CacheFactory.getIdGeneratorClass() + "].", e);
				throw new SessionException(SessionException.ERROR_SESSION_EXPIRED);
			} catch (IllegalAccessException e) {
				log.fatal("Imposible instanciar [" + CacheFactory.getIdGeneratorClass() + "].", e);
				throw new SessionException(SessionException.ERROR_SESSION_EXPIRED);
			}
		}

		return idGenerator;
	}

}