package common.management.mconfig;

import es.archigest.framework.core.management.Manageable;

/**
 * Interfaz del MBean que se encarga de la gestión del fichero de configuración.
 */
public interface ConfigurationJMXMBean extends Manageable {

	/**
	 * Recarga el contenido del fichero de configuración.
	 */
	public void reload();
}
