package ieci.tdw.ispac.ispaclib.sign;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Factoría para la creación del conector de firma digital.
 *
 */
public class SignConnectorFactory {

	/** 
	 * Logger de la clase. 
	 */
	private static final Logger logger = 
		Logger.getLogger(SignConnectorFactory.class);

	/**
	 * Obtiene una instancia del conector de firma digital.
	 * 
	 * @return Conector de firma digital.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static synchronized ISignConnector getSignConnector() throws ISPACException {
		
		ISignConnector signConnector = null;
		
		try {
			
			// Nombre de la clase que implementa el conector de firma digital
			String className = ISPACConfiguration.getInstance().get(ISPACConfiguration.DIGITAL_SIGN_CONNECTOR_CLASS);
			if (StringUtils.isNotBlank(className)) {
				
				// Cargar la clase
				Class clazz = Class.forName(className);
				
				if (!ISignConnector.class.isAssignableFrom(clazz)) {
					throw new ISPACException(className + " no extiende la clase ISignConnector");
				}
				
				// Instanciar la clase
				signConnector = (ISignConnector) clazz.newInstance();

				if (logger.isDebugEnabled()) {
					logger.debug("ISignConnector creado [" + className + "]");
				}
			}
		} catch (ISPACException e) {
			throw e;
		} catch (Exception e) {
			throw new ISPACException(e);
		}
		
		return signConnector;
	}
	
}