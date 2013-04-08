package ieci.tdw.ispac.ispaclib.gendoc.parser;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.gendoc.parser.openoffice.OpenOfficeDocumentParserConnectorImpl;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;

import java.lang.reflect.Constructor;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Factoría para la creación del conector con el módulo de parseo de documentos.
 * 
 */
public class DocumentParserConnectorFactory {

	/**
	 *  Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(DocumentParserConnectorFactory.class);
    
	/**
	 * Obtiene el conector de parseo de documentos.
	 * 
	 * @param ctx
	 *            Contexto del cliente.
	 * @return Conector de parseo de documentos.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public static synchronized IDocumentParserConnector getTemplateConnector(ClientContext ctx) throws ISPACException {
		
		IDocumentParserConnector connector = null;
		
		try {
			
			// Comprobar que se haya establecido el nombre de la clase 
			String className = ISPACConfiguration.getInstance().get(
					ISPACConfiguration.PARSER_CONNECTOR_CLASS);
			
			if (logger.isDebugEnabled()) {
				logger.debug("PARSER_CONNECTOR_CLASS: [" + className + "]");
			}
			
			if (StringUtils.isNotBlank(className)) {
			
				// Cargar la clase
				Class<?> clazz = Class.forName(className);
				if (!IDocumentParserConnector.class.isAssignableFrom(clazz)) {
					throw new ISPACException(className + " no extiende la clase IDocumentParserConnector");
				}
				
				// Invocar al constructor pasando el contexto del cliente
				Constructor<?> constructor = getConstructor(clazz, new Class [] { ClientContext.class });
				if (constructor != null) {
					connector = (IDocumentParserConnector) constructor.newInstance(new Object [] { ctx });
				} else {
					connector = (IDocumentParserConnector) clazz.newInstance();
				}
				
			} else {
				
				// Conector por defecto
				connector = new OpenOfficeDocumentParserConnectorImpl(ctx);
			}

			if (logger.isInfoEnabled()) {
				logger.info("IDocumentParserConnector creado [" + connector.getClass().toString() + "]");
			}
			
		} catch (Throwable t) {
			logger.error("Error al instanciar el conector de parseo de documentos", t);
			throw new ISPACException(t);
		}
		
		return connector;
	}
	
	private static Constructor<?> getConstructor(Class<?> clazz, Class<?>[] params) {
		
		Constructor<?> constructor = null;
		
		try {
			constructor = clazz.getConstructor(params);
		} catch (NoSuchMethodException e) {
			// No existe el contructor
		}
		
		return constructor;
	}

}
