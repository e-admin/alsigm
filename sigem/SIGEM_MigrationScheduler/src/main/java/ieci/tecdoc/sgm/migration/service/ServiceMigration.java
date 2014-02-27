package ieci.tecdoc.sgm.migration.service;

import ieci.tecdoc.sgm.migration.exception.MigrationException;

	/**
	 * Interfaz para el servicio de migracion de registros.
	 * 
	 */
	public interface ServiceMigration {

		/**
		 * Realiza la migración de registros telemáticos en todas las entidades definidas.
		 * @throws MigrationException si ocurre algún error.
		 */
		public void migracion() throws MigrationException;

	}
