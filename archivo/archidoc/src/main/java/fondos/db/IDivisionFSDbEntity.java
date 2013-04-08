package fondos.db;

import java.util.List;

import common.db.IDBEntity;

import fondos.vos.DivisionFraccionSerieVO;

/**
 * Métodos de acceso a datos referentes a divisiones de fracciones de serie. <br>
 * Entidad: <b>ASGFDIVISIONFS</b>
 */
public interface IDivisionFSDbEntity extends IDBEntity {

	/**
	 * Recupera la división de fracción de serie cuya fracción de serie tiene el
	 * identificador que se suministra
	 * 
	 * @param idFS
	 *            Identificador de la fracción de serie
	 * @return Datos de la división de fracción de serie
	 */
	public DivisionFraccionSerieVO getDivisionFSXId(final String idFS);

	/**
	 * Inserta una nueva división de fracción de serie en la base de datos
	 * 
	 * @param DivisionFraccionSerieVO
	 *            Datos de la división de fracción de serie a guardar
	 * @return Datos de la fracción de serie insertada
	 */
	public DivisionFraccionSerieVO insertDivisionFS(
			final DivisionFraccionSerieVO divisionFSVO);

	/**
	 * Actualiza los datos de la división de fracciones de serie que se indica
	 * por parámetro
	 * 
	 * @param divisionFSVO
	 *            Datos de la división de fracción de serie a actualizar
	 */
	public void updateDivisionFS(final DivisionFraccionSerieVO divisionFSVO);

	/**
	 * Elimina la división de fracción de serie indicada de la tabla de
	 * divisiones de fracción de serie
	 * 
	 * @param idFS
	 *            : identificador de la fracción de serie
	 */
	public void deleteDivisionFS(String idFS);

	/**
	 * Cuenta el número de divisiones de fracción de serie del gestor indicado y
	 * en los estados indicados. Si no se indican estados, se presupone que se
	 * buscan en cualquier estado
	 * 
	 * @param idUsrGestor
	 * @param estados
	 * @return número de divisiones de fracción de serie en los estados
	 *         indicados
	 */
	public int getCountDivisionFSXUsr(String idUsrGestor, int[] estados);

	/**
	 * Devuelve las divisiones de fracción de serie del gestor indicado y en los
	 * estados indicados. Si no se indican estados, se presupone que se buscan
	 * en cualquier estado
	 * 
	 * @param idUsrGestor
	 * @param estados
	 * @return divisiones de fracción de serie en los estados indicados y cuyo
	 *         gestor es el indicado
	 */
	public List getDivisionesFSXUsr(String idUsrGestor, int[] estados);

	/**
	 * Recupera las divisiones de fracción de serie cuyos identificadores se
	 * pasan como parámetro
	 * 
	 * @param idsFS
	 *            Identificadores de las fracciones de serie
	 * @return Lista de la divisiones de fracción de serie
	 */
	public List getDivisionesFSXId(String[] idsFS);

	/**
	 * Obtiene los Ids de las Divisiones que contienen los descriptores
	 * seleccionados.
	 * 
	 * @param idsDescriptores
	 *            Identificadores de los descriptores a buscar
	 * @return Lista de {@link DivisionFraccionSerieVO}
	 */
	public List getUdocsByInfoDescriptor(String[] idsDescriptores);
}