//
// FileName: AuthenticationFactory.java
//
package com.ieci.tecdoc.isicres.session.security.policies;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationPolicy;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;

/**
 * Patrón AbstractFactory.
 * 
 * @author lmvicente
 * @version @since @creationDate 24-mar-2004
 */

public class AuthenticationFactory {

	/***************************************************************************************************************************************
	 * Attributes
	 **************************************************************************************************************************************/

	private static final Logger log = Logger.getLogger(AuthenticationFactory.class);

	private static AuthenticationPolicy authenticationPolicy = null;

	/***************************************************************************************************************************************
	 * Constructors
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Public methods
	 **************************************************************************************************************************************/

	public static AuthenticationPolicy getCurrentPolicy() throws TecDocException {
		return getPolicy(Configurator.getInstance().getProperty(ConfigurationKeys.KEY_SERVER_AUTHENTICATION_POLICY));
	}

	public static synchronized AuthenticationPolicy getPolicy(String authenticationClassName) throws TecDocException {
		if (authenticationPolicy == null) {
			try {
				Class authenticationClass = Class.forName(authenticationClassName);
				authenticationPolicy = (AuthenticationPolicy) authenticationClass.newInstance();
			} catch (ClassNotFoundException e) {
				log.fatal("Imposible instanciar [" + authenticationClassName + "].", e);
				throw new TecDocException(TecDocException.ERROR_AUTHENTICATION_POLICY_NOTFOUND, e);
			} catch (InstantiationException e) {
				log.fatal("Imposible instanciar [" + authenticationClassName + "].", e);
				throw new TecDocException(TecDocException.ERROR_AUTHENTICATION_POLICY_NOTFOUND, e);
			} catch (IllegalAccessException e) {
				log.fatal("Imposible instanciar [" + authenticationClassName + "].", e);
				throw new TecDocException(TecDocException.ERROR_AUTHENTICATION_POLICY_NOTFOUND, e);
			}
		}
		return authenticationPolicy;
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
