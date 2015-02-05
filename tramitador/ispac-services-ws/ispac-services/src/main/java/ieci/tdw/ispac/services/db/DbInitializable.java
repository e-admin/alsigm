package ieci.tdw.ispac.services.db;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbQuery;

/**
 * Interfaz para los VO que pueden ser rellenados desde los DAOs.
 *
 */
public interface DbInitializable {

	/**
	 * Inicializa los valores del VO.
	 * @param dbq Resultado de la consulta.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void init(DbQuery dbq) throws ISPACException;
}
