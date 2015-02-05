package ieci.tdw.ispac.ispaclib.producers;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;

import java.lang.reflect.Constructor;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Factoría para la creación del conector de productores.
 */
public class ProducersConnectorFactory {

	/**
	 *  Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(ProducersConnectorFactory.class);
	
	/** 
	 * Instancia de la clase. 
	 */
    private static ProducersConnectorFactory mInstance = null;
    
    /** 
     * Nombre de la clase que implementa el conector de productores. 
     */
    private static String className = null;
    
	/**
	 * Constructor.
	 * 
	 * @throws ISPACException si ocurre algún error.
	 */
    private ProducersConnectorFactory() throws ISPACException {
    	
		ISPACConfiguration ispacConfiguration = ISPACConfiguration.getInstance();
		className = ispacConfiguration.get(ISPACConfiguration.PRODUCERS_CONNECTOR_CLASS);
   	}
    
	/**
	 * Obtiene una instancia de la factoría.
	 * 
	 * @return Intancia de la factoría.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static synchronized ProducersConnectorFactory getInstance()	throws ISPACException {
		
		if (mInstance == null) {
			mInstance = new ProducersConnectorFactory();
		}
		return mInstance;
	}
	
	/**
	 * Obtiene el conector de productores.
	 * 
	 * @param ctx Contexto del cliente.
	 * @return Conector de productores.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IProducersConnector getProducersConnector(IClientContext ctx) throws ISPACException {
		
		IProducersConnector producersConnector = null;
		
		try {
			
			// Comprobar que se haya establecido el nombre de la clase 
			if (StringUtils.isNotBlank(className)) {
			
				// Cargar la clase
				Class clazz = Class.forName(className);
				if (!IProducersConnector.class.isAssignableFrom(clazz)) {
					throw new ISPACException(className + " no extiende la clase IProducersConnector");
				}
				
				// Invocar al constructor pasando el contexto del cliente
				Constructor constructor = clazz.getConstructor(new Class [] { IClientContext.class });
				if (constructor != null) {
					producersConnector = (IProducersConnector) constructor.newInstance(new Object [] { ctx });
				} else {
					producersConnector = (IProducersConnector) clazz.newInstance();
				}
				
			} else {
				
				// Conector por defecto
				producersConnector = new ProducersConnectorImpl(ctx);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("IProducersConnector creado [" + producersConnector.getClass().toString() + "]");
			}
			
		} catch (Throwable t) {
			logger.error("Error al instanciar el conector con productores", t);
			throw new ISPACException(t);
		}
		
		return producersConnector;
	}

}
