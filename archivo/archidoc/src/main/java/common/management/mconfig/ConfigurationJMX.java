package common.management.mconfig;

import org.apache.log4j.Logger;

import xml.config.ConfiguracionSistemaArchivoFactory;
import es.archigest.framework.core.management.ManagementFactory;

/**
 * MBean para la gestión del fichero de configuración.
 */
public class ConfigurationJMX implements ConfigurationJMXMBean {

	/** Nombre JMX del MBean. */
	public static final String JMX_NAME = "CONFIG_JMX";

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(ConfigurationJMX.class);

	/** Indica si el MBean está activo. */
	private boolean active;

	/**
	 * Constructor.
	 */
	public ConfigurationJMX() {
		active = true;
	}

	/**
	 * Obtiene una instancia del MBean.
	 * 
	 * @return MBean.
	 */
	public static ConfigurationJMX newInstance() {
		return new ConfigurationJMX();
	}

	/**
	 * Recarga el contenido del fichero de configuración.
	 */
	public void reload() {
		ConfiguracionSistemaArchivoFactory.reload();
	}

	/**
	 * Inicia el MBean.
	 * 
	 * @see es.archigest.framework.core.management.Manageable#start()
	 */
	public void start() {
		if (!active) {
			active = true;

			if (logger.isInfoEnabled())
				logger.info(JMX_NAME + " iniciado");
		}
	}

	/**
	 * Detiene el MBean.
	 * 
	 * @see es.archigest.framework.core.management.Manageable#stop()
	 */
	public void stop() {
		if (active) {
			active = false;

			if (logger.isInfoEnabled())
				logger.info(JMX_NAME + " detenido");
		}
	}

	/**
	 * Indica si el MBean está iniciado.
	 * 
	 * @see es.archigest.framework.core.management.Manageable#isStarted()
	 */
	public boolean isStarted() {
		return active;
	}

	/**
	 * Crea el MBean.
	 * 
	 * @see es.archigest.framework.core.initialization.Launchable#create()
	 */
	public void create() {
		try {
			ManagementFactory.getFactory().registerMBeanObject(JMX_NAME, this);

			start();

			if (logger.isInfoEnabled())
				logger.info(JMX_NAME + " registrado con \351xito.");
		} catch (Exception e) {
			logger.error("Error al intentar registrar: " + JMX_NAME
					+ ". Abortado registro.", e);
		}
	}

	/**
	 * Destruye el MBean.
	 * 
	 * @see es.archigest.framework.core.initialization.Launchable#destroy()
	 */
	public void destroy() {
		try {
			stop();

			ManagementFactory.getFactory().unregisterMBeanObject(JMX_NAME);

			if (logger.isInfoEnabled())
				logger.info(JMX_NAME + " desregistrado con \351xito.");
		} catch (Exception e) {
			logger.error("Error al desregistrar: " + JMX_NAME, e);
		}
	}

}
