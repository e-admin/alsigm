package es.ieci.tecdoc.isicres.api.intercambioregistral.business.repository;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.adapter.IRepositoryManager;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;

/**
 * Patrón AbstractFactory.
 * 
 * @author lmvicente
 * @version
 * @since
 * @creationDate 24-mar-2004
 */

public class RepositoryFactory {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static final Logger log = Logger.getLogger(RepositoryFactory.class);

	private static IRepositoryManager repositoryManager = null;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	public static IRepositoryManager getCurrentPolicy() throws TecDocException {
		return getPolicy(Configurator.getInstance().getProperty(
				ConfigurationKeys.KEY_SERVER_REPOSITORY_MANAGER));
	}

	public static synchronized IRepositoryManager getPolicy(
			String repositoryClassName) throws TecDocException {
		if (repositoryManager == null) {
			try {
				Class repositoryClass = Class.forName(repositoryClassName);

				repositoryManager = (IRepositoryManager) repositoryClass
						.newInstance();
			} catch (ClassNotFoundException e) {
				log.fatal(
						"Imposible instanciar [" + repositoryClassName + "].",
						e);
				throw new TecDocException(
						TecDocException.ERROR_REPOSITORY_POLICY_NOTFOUND, e);
			} catch (InstantiationException e) {
				log.fatal(
						"Imposible instanciar [" + repositoryClassName + "].",
						e);
				throw new TecDocException(
						TecDocException.ERROR_REPOSITORY_POLICY_NOTFOUND, e);
			} catch (IllegalAccessException e) {
				log.fatal(
						"Imposible instanciar [" + repositoryClassName + "].",
						e);
				throw new TecDocException(
						TecDocException.ERROR_REPOSITORY_POLICY_NOTFOUND, e);
			}
		}
		return repositoryManager;
	}

	/***************************************************************************
	 * Protected methods
	 **************************************************************************/

	/***************************************************************************
	 * Private methods
	 **************************************************************************/

	/***************************************************************************
	 * Inner classes
	 **************************************************************************/

	/***************************************************************************
	 * Test brench
	 **************************************************************************/

}
