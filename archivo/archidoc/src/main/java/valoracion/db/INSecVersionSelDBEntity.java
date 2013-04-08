package valoracion.db;

import common.db.IDBEntity;

/**
 * Metodos de acceso a datos referentes al numero de version para las
 * selecciones de series documentales. <br>
 * Entidad: <b>ASGFNUMSECSEL</b>
 */
public interface INSecVersionSelDBEntity extends IDBEntity {

	/**
	 * Obtiene el número de versión de la última selección realizada sobre la
	 * serie.
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 * @return Número de versión de la última selección realizada sobre la
	 *         serie.
	 */
	public int getCurrentVersion(String idSerie);

	/**
	 * Incrementa el número de versión de las selecciones de una serie.
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 * @return Nuevo número de versión.
	 */
	public int incrementarNumeroVersion(String idSerie);

}