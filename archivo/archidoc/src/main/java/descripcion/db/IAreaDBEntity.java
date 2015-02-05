package descripcion.db;

import java.util.List;

import common.db.IDBEntityKeyValue;

import descripcion.vos.AreaVO;

/**
 * Entidad: <b>ADAREA</b>
 * 
 * @author IECISA
 * 
 */
public interface IAreaDBEntity extends IDBEntityKeyValue {

	/**
	 * Obtiene un AreaVO perteneciente al id pasado por parámetro
	 * 
	 * @param id
	 * @return un AreaVO
	 */
	public AreaVO getArea(String id);

	/**
	 * Obtiene un AreaVO perteneciente al nombre pasado por parámetro
	 * 
	 * @param nombre
	 * @return un AreaVO
	 */
	public AreaVO getAreaPorNombre(String nombre);

	/**
	 * Obtiene la lista de areas
	 * 
	 * @return una lista de areas
	 */
	public List getAreas();

	/**
	 * Obtiene la lista de areas de un tipo de norma.
	 * 
	 * @param tipoNorma
	 *            Tipo de norma.
	 * @return Lista de areas.
	 */
	public List getAreasByTipoNorma(int tipoNorma);

	/**
	 * Inserta un nuevo area.
	 * 
	 * @param AreaVO
	 * @return AreaVO
	 */
	public AreaVO createArea(AreaVO areaVO);

	/**
	 * Elimina las areas correspondientes a los ids pasados por parametro
	 * 
	 * @param String
	 *            [] idsAreas
	 */
	public void deleteAreas(String[] idsAreas);

	/**
	 * Actualiza un AreaVO
	 * 
	 * @param areaVO
	 */
	public AreaVO updateArea(AreaVO areaVO);
}
