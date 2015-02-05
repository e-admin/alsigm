package ieci.tdw.ispac.ispaclib.bpm;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;

import java.lang.reflect.Constructor;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Factoría para la creación del API de BPM.
 *
 */
public class BPMAPIFactory {

	/** 
	 * Logger de la clase. 
	 */
	private static final Logger logger = Logger.getLogger(BPMAPIFactory.class);

	/** 
	 * Clase por defecto que implementa el API de BPM. 
	 */
	private static final String DEFAULT_BPM_API_CLASS = 
		"ieci.tdw.ispac.ispaclib.bpm.BPMAPI";

    /**
     * Clase que implementa el API de BPM.
     */
    private static Class bmpAPIClass = null;

    /**
     * Constructor con el contexto de cliente.
     */
    private static Constructor clientContextConstructor = null;
    
    
	/**
	 * Obtiene el API de BPM.
	 * @param ctx Contexto del cliente.
	 * @return API de BPM.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static synchronized IBPMAPI getBPMAPI(ClientContext ctx) 
			throws ISPACException {
		
		if (bmpAPIClass == null) {
			
			// Nombre de la clase
			String className = ISPACConfiguration.getInstance().get(
					ISPACConfiguration.BPM_API_CLASS);
	
			// Comprobar que se haya establecido el nombre de la clase 
			if (StringUtils.isBlank(className)) {
				className = DEFAULT_BPM_API_CLASS; 
			}
			
			try {
				
				// Cargar la clase
				bmpAPIClass = Class.forName(className);
				if (!IBPMAPI.class.isAssignableFrom(bmpAPIClass)) {
					throw new ISPACException(className 
							+ " no implementa la interfaz IBPMAPI");
				}

				// Constructor con el contexto de cliente
				clientContextConstructor = bmpAPIClass.getConstructor(
						new Class [] { ClientContext.class });
				
			} catch (ISPACException e) {
				logger.error("Error al obtener el conector de BPM: " + className, e);
				throw e;
			} catch (Exception e) {
				logger.error("Error al obtener el conector de BPM: " + className, e);
				throw new ISPACException(e);
			}
		}

		// API de BPM
		IBPMAPI bpmAPI = null;
		
		try { 
			
			// Instanciar la clase en función del constructor
			if (clientContextConstructor != null) {
				bpmAPI = (IBPMAPI) clientContextConstructor.newInstance(
						new Object [] { ctx });
			} else {
				bpmAPI = (IBPMAPI) bmpAPIClass.newInstance();
			}
	
			if (logger.isDebugEnabled()) {
				logger.debug("IBPMAPI creado [" + bpmAPI.getClass().getName() + "]");
			}		
			
		} catch (Exception e) {
			logger.error("Error al obtener el conector de BPM", e);
			throw new ISPACException(e);
		}

		return bpmAPI;
	}	
}