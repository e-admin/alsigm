package salas.db;

import java.util.List;

import common.db.IDBEntity;

import salas.vos.EdificioVO;
import salas.vos.ElementoNavegacionVO;
import salas.vos.MesaVO;

/**
 * Entidad: <b>ASGSEDIFICIO</b>
 * 
 * @author IECISA
 * 
 */
public interface IEdificioDBEntity extends IDBEntity {
	/**
	 * Obtiene el Edificio por su Identificador
	 * 
	 * @param idEdificio
	 *            Cadena que define el identificador del Edificio
	 * @return EdificioVO con los datos del Edificio
	 */
	public EdificioVO getEdificioById(String idEdificio);

	/**
	 * Obtiene el Edificio por su Nombre
	 * 
	 * @param nombre
	 *            Cadena que define el nombre del Edificio
	 * @return EdificioVO con los datos del Edificio
	 */
	public EdificioVO getEdificioByNombre(String nombre);

	/**
	 * Obtiene la Lista de Edificios que pertenecen a unos archivos determinados
	 * 
	 * @param idsArchivo
	 *            Identificadores de los archivos
	 * @return Lista de {@link EdificioVO}. Si no se espedci
	 */
	public List getEdificiosByIdsArchivo(String[] idsArchivo);

	public List getEdificios();

	/**
	 * Inserta el Edificio
	 * 
	 * @param edificioVO
	 *            Datos del Edificio a Insertar
	 */
	public void insertEdificio(EdificioVO edificioVO);

	/**
	 * Actualiza los datos del Edificio
	 * 
	 * @param edificioVO
	 */
	public void updateEdificio(EdificioVO edificioVO);

	/**
	 * Borra los Datos del Edificio
	 * 
	 * @param idEdificio
	 *            Cadena que define el Identificador del Edificio
	 */
	public void deleteEdificio(String idEdificio);

	/**
	 * Obtiene el número de Edificios que pertenecen a un archivo
	 * 
	 * @return Número de Edificio
	 */
	public int getCountEdificiosByIdArchivo(String idArchivo);

	/**
	 * Obtiene la lista de mesas libres del edificio por archivo
	 * 
	 * @param idArchivo
	 *            Identificador de archivo
	 * @param salasConEquipoInformatico
	 *            Indica si queremos buscar dentro de salas con equipo
	 *            informático o no.
	 * @return Lista de objetos {@link ElementoNavegacionVO}
	 */
	public List getMesasLibresEdificio(String idArchivo,
			String salasConEquipoInformatico);

	/**
	 * Obtiene la lista de mesas libres del edificio para el archivo
	 * especificado. Indicando tambíen si queremos buscar en salas con equipo
	 * informatico o no
	 * 
	 * @param idArchivo
	 *            Identificador del archivo
	 * @param idEdificio
	 *            Identificador del edificio
	 * @param salasConEquipoInformatico
	 *            para buscar en salas con equipo informatico o no
	 * @return Lista de objetos {@link MesaVO}
	 */
	public List getMesasLibresByEdificio(final String idArchivo,
			final String idEdificio, final String salasConEquipoInformatico);
}