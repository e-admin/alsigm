package ieci.tecdoc.sgm.migration.mgr;

import ieci.tecdoc.sgm.core.exception.SigemException;

/**
 * Interfaz para los managers de migración de registros.
 *
 */
public interface MigrationManager {

	/**
	 * Realiza el proceso de migración de registros sobre una entidad. 
	 * @throws SigemException si ocurre algún error.
	 */
	public abstract void migrationRegisterEntity()
			throws SigemException;

}