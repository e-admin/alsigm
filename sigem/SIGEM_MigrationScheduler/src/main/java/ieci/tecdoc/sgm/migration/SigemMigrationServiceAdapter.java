package ieci.tecdoc.sgm.migration;

import ieci.tecdoc.sgm.migration.config.Config;
import ieci.tecdoc.sgm.migration.exception.MigrationException;
import ieci.tecdoc.sgm.migration.mgr.MigrationManager;
import ieci.tecdoc.sgm.migration.mgr.impl.MigrationManagerImpl;
import ieci.tecdoc.sgm.migration.service.ServiceMigration;


import org.apache.log4j.Logger;


/**
 * Implementación del servicio de la migración de registros de SIGEM.
 *
 */
public class SigemMigrationServiceAdapter implements ServiceMigration {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(SigemMigrationServiceAdapter.class);
	
	/**
	 * Constructor.
	 */
	public SigemMigrationServiceAdapter () {
		super();
	}
	
	/**
	 * Realiza la migración de registros telemáticos en todas las entidades definidas.
	 * @throws MigracionRegisterException si ocurre algún error.
	 */
	public void migracion() throws MigrationException {
		
		try {
			
			new MigrationManagerImpl(Config.getInstance().getOrigenEntity(), 
					Config.getInstance().getDestinoEntity())
				.migrationRegisterEntity();
			
		} catch (MigrationException e){
			logger.error("Error inesperado en la migración de registros", e);
			throw e;
		} catch (Throwable e){
			logger.error("Error inesperado en la migración de registros", e);
			throw new MigrationException(e);
		}
	}
}
