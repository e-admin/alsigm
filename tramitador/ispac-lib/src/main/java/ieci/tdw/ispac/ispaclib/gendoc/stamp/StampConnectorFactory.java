package ieci.tdw.ispac.ispaclib.gendoc.stamp;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Factoría para la creación del conector de sellado.
 *
 */
public class StampConnectorFactory {

	/** 
	 * Logger de la clase. 
	 */
	private static final Logger logger = 
		Logger.getLogger(StampConnectorFactory.class);

	/** 
     * Instancia de la clase. 
     */
    private static StampConnectorFactory mInstance = null;

    /** 
     * Nombre de la clase que implementa el conector de sellado. 
     */
    private static String className = null;

    
	/**
	 * Constructor.
	 * 
	 * @throws ISPACException si ocurre algún error.
	 */
    private StampConnectorFactory() throws ISPACException {
		className = ISPACConfiguration.getInstance().get(ISPACConfiguration.STAMP_CONNECTOR_CLASS);
   	}

	/**
	 * Obtiene una instancia de la factoría.
	 * 
	 * @return Intancia de la factoría.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static synchronized StampConnectorFactory getInstance() throws ISPACException {
		
		if (mInstance == null) {
			mInstance = new StampConnectorFactory();
		}
		return mInstance;
	}

	/**
	 * Obtiene el conector de sellado de documentos.
	 * @return Conector de sellado de documentos.
	 * @throws ISPACException si ocurre algún error.
	 */
	public StampConnector getStampConnector() throws ISPACException {
		
		StampConnector stampConnector = null;
		
		// Comprobar que se haya establecido el nombre de la clase 
		if (StringUtils.isBlank(className)) {
			return new StampConnectorImpl();
		}

		try {
			
			// Cargar la clase
			Class clazz = Class.forName(className);
			if (!StampConnector.class.isAssignableFrom(clazz)) {
				throw new ISPACException(className + " no implementa el interfaz StampConnector");
			}
			
			// Instanciar la clase
			stampConnector = (StampConnector) clazz.newInstance();

			if (logger.isDebugEnabled()) {
				logger.debug("StampConnector creado [" + className + "]");
			}		
		} 
		catch (Exception e) {
			logger.error("Error al instanciar el conector de sellado", e);
			throw new ISPACException(e);
		}

		return stampConnector;
	}	
}