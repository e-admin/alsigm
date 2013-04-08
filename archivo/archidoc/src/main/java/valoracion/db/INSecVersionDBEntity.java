package valoracion.db;

import common.db.IDBEntity;

/**
 * Metodos de acceso a datos referentes al numero de version para las
 * valoraciones de series documentales <br>
 * Entidad: <b>ASGFNUMSECVAL</b>
 */
public interface INSecVersionDBEntity extends IDBEntity {

	/**
	 * Obtiene el número de versión de la última valoracion realizada sobre la
	 * serie
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 * @return Número de versión de la última valoración realizada sobre la
	 *         serie
	 */
	public int getCurrentVersion(String idSerie);

	/**
	 * Incrementa el número de versión de las valoraciones de una serie.
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 * @return Nuevo número de versión.
	 */
	public int incrementarNumeroVersion(String idSerie);

	/**
	 * Decrementar el número de versión de las valoraciones de una serie.
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 * @return Nuevo número de versión.
	 */
	public int decrementarNumeroVersion(String idSerie);

}