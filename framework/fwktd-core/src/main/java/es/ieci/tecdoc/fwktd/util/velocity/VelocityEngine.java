/*
 * 
 */
package es.ieci.tecdoc.fwktd.util.velocity;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase encargada de una correcta inicialización del motor de plantillas
 * Velocity. También expone métodos para recuperar una plantilla y para mezclar
 * una plantilla con unos datos.
 * 
 * @author IECISA
 * 
 */
public class VelocityEngine {

	/**
	 * Constructor por defecto de la clase.
	 */
	public VelocityEngine() {
		init(null);
	}

	public VelocityEngine(Properties props) {
		init(props);
	}

	protected void init(Properties props) {
		engine = new org.apache.velocity.app.VelocityEngine();
		try {
			setDefaultProperties("");
			if (null != props) {
				setCustomProperties(props);
			}

			engine.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void setCustomProperties(Properties props) {
	}

	/**
	 * Establece las propiedades por defecto necesarias para configurar el motor
	 * de Velocity. Los valores establecidos son:
	 * <ul>
	 * <li>resource.loader=class</li>
	 * <li>
	 * class.resource.loader.class=org.apache.velocity.runtime.resource.loader
	 * .ClasspathResourceLoader</li>
	 * <li>runtime.log.logsystem.class=org.apache.velocity.runtime.log.
	 * NullLogSystem</li>
	 * <li>
	 * runtime.log.logsystem.log4j.category=es.ieci.tecdoc.fwktd.util.velocity
	 * .VelocityEngine</li>
	 * </ul>
	 * 
	 * @param macroPath
	 */
	protected void setDefaultProperties(String macroPath) {

		engine.setProperty("resource.loader", "class");
		engine
				.setProperty("class.resource.loader.class",
						"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

		if (!StringUtils.isEmpty(macroPath)) {
			engine.setProperty(
					org.apache.velocity.runtime.RuntimeConstants.VM_LIBRARY,
					macroPath);
		}

		// Por defecto, se deshabilita el log de Velocity
		engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
				"es.ieci.tecdoc.fwktd.util.velocity.log.Slf4jLogChute");

		// Se asigna a velocity el logger del engine
		engine.setProperty("runtime.log.logsystem.log4j.category", logger
				.getName());

	}

	/**
	 * 
	 * @param templateName
	 * @return
	 * @throws VelocityException
	 */
	public Template getTemplate(String templateName) {
		try {
			return engine.getTemplate(templateName);
		} catch (ResourceNotFoundException rnfe) {
			logger.error(rnfe.getMessage());
			throw new VelocityException(rnfe.getMessage());
		} catch (ParseErrorException pee) {
			logger.error(pee.getMessage());
			throw new VelocityException(pee.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new VelocityException(e.getMessage());
		}
	}

	/**
	 * Realiza el merge de la plantilla <code>template</code> con el contexto de
	 * datos <code>context</code>. El resultado se vuelca en <code>writer</code>
	 * . La codificación de caracteres usada es ISO-8859-1.
	 * 
	 * @param template
	 *            plantilla de Velocity
	 * @param context
	 *            contexto de datos a exponer en la plantilla
	 * @param writer
	 *            instancia de <code>Writer</code> en la que se vuelca el
	 *            resultado del merge
	 * @see Template
	 * @throws VelocityException
	 */
	public void merge(Template template, VelocityContext context, Writer writer) {
		merge(template.getName(), context, writer);
	}

	/**
	 * Realiza el merge de la plantilla de nombre <code>templateName</code> con
	 * el contexto <code>context</code>. El resultado se vuelca en
	 * <code>writer</code>.
	 * 
	 * @param templateName
	 *            nombre de la plantilla
	 * @param context
	 *            contexto con la información a exponer en la plantilla
	 * @param writer
	 *            <code>Writer</code> en el que volcar el resultado del merge de
	 *            plantilla y contexto de datos
	 * @see Writer
	 */
	@SuppressWarnings("static-access")
	public void merge(String templateName, VelocityContext context,
			Writer writer) {
		merge(templateName, context, engine.ENCODING_DEFAULT, writer);
	}

	/**
	 * Realiza el merge de la plantilla de nombre <code>templateName</code> con
	 * el contexto <code>context</code>.
	 * 
	 * @param templateName
	 *            nombre de la plantilla
	 * @param context
	 *            contexto con la información a exponer en la plantilla
	 * @return un <code>java.lang.String</code> con el resultado de mezclar la
	 *         plantilla con el contexto de datos
	 */
	public String merge(String templateName, VelocityContext context) {
		StringWriter writer = new StringWriter();

		merge(templateName, context, writer);

		return writer.toString();
	}

	/**
	 * Realiza el merge de la plantilla de nombre <code>templateName</code> con
	 * el contexto <code>context</code> usando de codificación de caracteres de
	 * código <code>enconding</code>. El resultado se vuelca a
	 * <code>writer</code>.
	 * 
	 * @param templateName
	 *            nombre de la plantilla
	 * @param context
	 *            contexto de Velocity con la información a exponer en la
	 *            plantilla
	 * @param encoding
	 *            codificación a utilizar en la generación de contenido
	 * @param writer
	 *            <code>Writer</code> al que volcar el resultado de la
	 *            generación de contenido
	 */
	public void merge(String templateName, VelocityContext context,
			String encoding, Writer writer) {
		try {
			engine.mergeTemplate(templateName, encoding, context, writer);
		} catch (ResourceNotFoundException rnfe) {
			logger.error("No se ha podido encontrar la plantilla {}",
					templateName);
			throw new VelocityException(rnfe.getMessage());
		} catch (ParseErrorException pee) {
			logger.error("");
			throw new VelocityException(pee.getMessage());
		} catch (MethodInvocationException mie) {
			logger.error("");
			throw new VelocityException(mie.getMessage());
		} catch (Exception e) {
			logger.error("");
			throw new VelocityException(e.getMessage());
		}
	}

	// Members
	protected static final Logger logger = LoggerFactory
			.getLogger(VelocityEngine.class);

	// Motor de Apache Velocity (no se usa el modo Singleton)
	protected org.apache.velocity.app.VelocityEngine engine;
}
