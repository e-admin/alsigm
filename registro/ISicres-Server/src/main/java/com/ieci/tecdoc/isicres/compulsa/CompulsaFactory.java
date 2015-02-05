package com.ieci.tecdoc.isicres.compulsa;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.adapter.ICompulsaManager;
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

public class CompulsaFactory {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static final Logger log = Logger.getLogger(CompulsaFactory.class);

	private static ICompulsaManager compulsaManager = null;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	public static ICompulsaManager getCurrentPolicy() throws TecDocException {
		return getPolicy(Configurator.getInstance().getProperty(
				ConfigurationKeys.KEY_DESKTOP_COMPULSA_POLICY));
	}

	public static synchronized ICompulsaManager getPolicy(
			String compulsaClassName) throws TecDocException {
		if (compulsaManager == null) {
			try {
				Class compulsaClass = Class.forName(compulsaClassName);

				compulsaManager = (ICompulsaManager) compulsaClass
						.newInstance();
			} catch (ClassNotFoundException e) {
				log.fatal(
						"Imposible instanciar [" + compulsaClassName + "].",
						e);
				throw new TecDocException(
						TecDocException.ERROR_COMPULSA_POLICY_NOTFOUND, e);
			} catch (InstantiationException e) {
				log.fatal(
						"Imposible instanciar [" + compulsaClassName + "].",
						e);
				throw new TecDocException(
						TecDocException.ERROR_COMPULSA_POLICY_NOTFOUND, e);
			} catch (IllegalAccessException e) {
				log.fatal(
						"Imposible instanciar [" + compulsaClassName + "].",
						e);
				throw new TecDocException(
						TecDocException.ERROR_COMPULSA_POLICY_NOTFOUND, e);
			}
		}
		return compulsaManager;
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
