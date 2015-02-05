package docelectronicos.db;

import java.util.List;

import common.db.IDBEntity;
import common.exceptions.TooManyResultsException;

import docelectronicos.EstadoTareaCaptura;
import docelectronicos.vos.BusquedaTareasVO;
import docelectronicos.vos.DocTCapturaVO;

/**
 * Entidad: <b>ADOCTCAPTURA</b>
 * 
 * @author IECISA
 * 
 */
public interface IDocTCapturaDBEntity extends IDBEntity {

	/**
	 * Obtiene la lista de tareas
	 * 
	 * @return Lista de tareas
	 */
	public List fetchTareas();

	/**
	 * Devuelve el número de tareas en unos estados determinados
	 * 
	 * @return Número de tareas en estados
	 */
	public int fetchCountTareasXEstados(EstadoTareaCaptura[] estados);

	/**
	 * 
	 * @return tareas por estado
	 */
	public List fetchTareasXEstados(EstadoTareaCaptura[] estados);

	/**
	 * @param estados
	 * @param idUsuarioCaptura
	 * @return
	 */
	public int fetchCountTareasXEstadosYUsuario(EstadoTareaCaptura[] estados,
			String idUsuarioCaptura);

	/**
	 * 
	 * @param estado
	 * @param idUsuarioCaptura
	 * @return
	 */
	public List fetchTareasXEstadosYUsuario(EstadoTareaCaptura[] estado,
			String idUsuarioCaptura);

	/**
	 * Obtiene la tareas de digitalizacion
	 * 
	 * @param id
	 *            Identificador de la tarea
	 * @return Ficha de clasificadores documentales.
	 */
	public DocTCapturaVO fetchTareaXId(String id);

	/**
	 * Insercion de captura
	 * 
	 * @param tarea
	 * @return Tarea insertada en BD
	 */
	public DocTCapturaVO insert(DocTCapturaVO tarea);

	/**
	 * Actualizacion de tarea
	 * 
	 * @param tarea
	 */
	public void update(DocTCapturaVO tarea);

	/**
	 * 
	 * @param idTarea
	 * @param newGestor
	 */
	public void updateGestor(String[] idTarea, String newGestor);

	/**
	 * 
	 * @param idTarea
	 */
	public void removeXId(String idTarea);

	/**
	 * Eliminar tareas de un elemento
	 * 
	 * @param idElemento
	 *            de 1 a 7
	 * @param idTipoObjeto
	 */
	public void removeTareasXIdElementoYTipoObj(String idElemento,
			int idTipoObjeto);

	/**
	 * 
	 * @param idElemento
	 * @param idTipoObjeto
	 * @return
	 */
	public List fetchXIdElementoYTipoObj(String idElemento, int idTipoObjeto);

	/**
	 * @param busquedaVO
	 * @return Lista de tareas que cumpla las condiciones insertadas en
	 *         busquedaVO
	 */
	public List getTareas(BusquedaTareasVO busquedaVO)
			throws TooManyResultsException;

	/**
	 * 
	 * @param ids
	 * @return Lista de tareas por ids
	 */
	public List fetchTareasXIds(String[] ids);
}
