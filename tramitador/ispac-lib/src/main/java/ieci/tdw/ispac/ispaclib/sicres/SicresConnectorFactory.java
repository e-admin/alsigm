package ieci.tdw.ispac.ispaclib.sicres;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;

import java.lang.reflect.Constructor;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Factoría para la creación del conector de SICRES.
 */
public class SicresConnectorFactory {
	
	/**
	 *  Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(SicresConnectorFactory.class);
	
	/** 
	 * Instancia de la clase. 
	 */
    private static SicresConnectorFactory mInstance = null;
    
    /** 
     * Nombre de la clase que implementa el conector de SICRES. 
     */
    private static String className = null;
    
	/**
	 * Constructor.
	 * 
	 * @throws ISPACException si ocurre algún error.
	 */
    private SicresConnectorFactory() throws ISPACException {
    	
		ISPACConfiguration ispacConfiguration = ISPACConfiguration.getInstance();
		className = ispacConfiguration.get(ISPACConfiguration.SICRES_CONNECTOR_CLASS);
   	}
    
	/**
	 * Obtiene una instancia de la factoría.
	 * 
	 * @return Intancia de la factoría.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static synchronized SicresConnectorFactory getInstance()	throws ISPACException {
		
		if (mInstance == null) {
			mInstance = new SicresConnectorFactory();
		}
		return mInstance;
	}
	
	/**
	 * Obtiene el conector de SICRES.
	 * 
	 * @param ctx Contexto del cliente.
	 * @return Conector de SICRES.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ISicresConnector getSicresConnector(ClientContext ctx) throws ISPACException {
		
		ISicresConnector sicresConnector = null;
		
		// Comprobar que se haya establecido el nombre de la clase 
		if (StringUtils.isBlank(className)) {
			return sicresConnector;
		}
		
		try {
			// Cargar la clase
			Class clazz = Class.forName(className);
			if (!ISicresConnector.class.isAssignableFrom(clazz)) {
				throw new ISPACException(className + " no extiende la clase ISicresConnector");
			}
			
			// Invocar al constructor pasando el contexto del cliente
			Constructor object = clazz.getConstructor(new Class [] { ClientContext.class });
			sicresConnector = (ISicresConnector) object.newInstance(new Object [] { ctx });

			if (logger.isDebugEnabled()) {
				logger.debug("ISicresConnector creado [" + className + "]");
			}		
		} 
		catch (Exception e) {
			throw new ISPACException(e);
		}
		
		return sicresConnector;
	}

}