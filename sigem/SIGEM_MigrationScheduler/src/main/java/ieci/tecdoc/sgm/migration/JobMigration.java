package ieci.tecdoc.sgm.migration;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.migration.config.Config;
import ieci.tecdoc.sgm.migration.exception.MigrationException;
 
/**
 * Clase encargada de ejecutar el proceso de migración de los registros
 * telemáticos procedentes de SIGEM Housing al SIGEM UAM
 * @author eacuna
 */

public class JobMigration {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(JobMigration.class);
	
	/**
	 * Método que arranca el scheluder de migración de registros telemáticos
	 */
	public void run() {
		
		try {
			if(Config.getInstance().getEjecution()) {
				SigemMigrationServiceAdapter job = new SigemMigrationServiceAdapter();
				try {
					logger.debug("Arrancamos la migración de registros telemáticos");
					job.migracion();
				} catch (MigrationException e) {
					logger.error("ERROR: en el funcionamiento del Quartz (migración de registros telemáticos).");
					e.printStackTrace();
				}
			}
		} catch (MigrationException e) {
			e.printStackTrace();
			logger.error("No se ha podido leer el fichero de configuracion de la aplicacion");
		}
	}
}
