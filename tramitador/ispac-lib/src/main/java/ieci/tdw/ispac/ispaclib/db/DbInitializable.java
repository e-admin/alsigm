package ieci.tdw.ispac.ispaclib.db;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbQuery;

/**
 * Interfaz para que los VOs se puedan inicializar a partir del
 * resultado de una consulta.
 *
 */
public interface DbInitializable {

	/**
	 * Inicializa el VO con el resultado de la consulta.
	 * @param dbq Resultado de la consulta.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void init(DbQuery dbq) throws ISPACException;
}
