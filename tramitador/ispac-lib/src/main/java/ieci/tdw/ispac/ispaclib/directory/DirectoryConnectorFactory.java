package ieci.tdw.ispac.ispaclib.directory;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Factoría para la creación del API de directorio de usuarios.
 *
 */
public class DirectoryConnectorFactory {

	/**
	 * Nombre de la clase del conector por defecto.
	 */
    private static final String DEFAULT_CONNECTOR_CLASS = 
    	"ieci.tdw.ispac.ispaclib.invesdoc.directory.InvesDocDirectoryConnector"; 

	/** 
	 * Logger de la clase. 
	 */
	private static final Logger logger = 
		Logger.getLogger(DirectoryConnectorFactory.class);

	/** 
     * Clase que implementa el API de acceso al directorio de usuarios. 
     */
    private static Class apiClass = null;
    


	/**
	 * Obtiene el API de directorio de usuarios.
	 * @return API de directorio de usuarios.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static synchronized IDirectoryConnector getConnector() 
			throws ISPACException {
		
		if (apiClass == null) {
			
			// Nombre de la clase
			String className = ISPACConfiguration.getInstance().get(
					ISPACConfiguration.DIRECTORY_CONNECTOR_CLASS);
			
			// Comprobar si se ha configurado el nombre de la clase 
			if (StringUtils.isBlank(className)) {
				className = DEFAULT_CONNECTOR_CLASS;
			}

			try {
				// Cargar la clase
				apiClass = Class.forName(className);
				if (!IDirectoryConnector.class.isAssignableFrom(apiClass)) {
					apiClass = null;
					throw new ISPACException(className 
							+ " no extiende la clase IDirectoryConnector");
				}
			} catch (Exception e) {
				throw new ISPACException(e);
			}
		}

		try {
			
			// Instanciar la clase
			IDirectoryConnector directoryAPI = 
				(IDirectoryConnector) apiClass.newInstance();

			if (logger.isDebugEnabled()) {
				logger.debug("IDirectoryConnector creado [" 
						+ directoryAPI.getClass().getName() + "]");
			}
			
			return directoryAPI;

		} catch (Exception e) {
			throw new ISPACException(e);
		}
	}
}