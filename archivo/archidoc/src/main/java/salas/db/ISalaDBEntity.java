package salas.db;

import java.util.List;

import salas.vos.ElementoNavegacionVO;
import salas.vos.MesaVO;
import salas.vos.SalaVO;

import common.db.IDBEntity;

/**
 * Entidad: <b>ASGSSALA</b>
 * 
 * @author IECISA
 * 
 */
public interface ISalaDBEntity extends IDBEntity {

	public void insertSala(final SalaVO salaVO);

	public void deleteSala(final String idSala);

	public void updateSala(final SalaVO salaVO);

	public void updateSalaConMesasOcupadas(SalaVO salaVO);

	/**
	 * Obtiene una sala por su identificador
	 * 
	 * @param idSala
	 * @return Objeto {@link SalaVO}
	 */
	public SalaVO getSalaById(final String idSala);

	/**
	 * Obtiene la Lista de Salas que pertenecen a un edificio
	 * 
	 * @param idEdificio
	 *            Identificadores de los edificios
	 * @return Lista de {@link SalaVO} que pertenecen a los edificios
	 *         especificados.
	 */
	public List getSalas(String idsEdificio);

	/**
	 * Devuelve el número de salas que pertenecen a un determinado edificio
	 * 
	 * @param idEdificio
	 * @return número de salas
	 */
	public int countSalasPorEdificio(final String idEdificio);

	/**
	 * Obtiene la Sala a partir del nombre y del edificio al que pertenece
	 * 
	 * @param nombre
	 *            de la Sala
	 * @param idEdificio
	 *            al que pertenece la sala
	 * @return Objeto {@link SalaVO}
	 */
	public SalaVO getSalaByNombreAndEdificio(final String nombre,
			final String idEdificio);

	/**
	 * Obtiene la lista de salas con id, nombre y número de mesas libres de cada
	 * una
	 * 
	 * @param idEdificio
	 *            Identificador del edificio
	 * @param salasConEquipoInformatico
	 *            Indica si busca en salas con equipo informático o no.
	 * @return Lista de objetos {@link ElementoNavegacionVO}
	 */
	public List getMesasLibresSalaByEdificio(final String idEdificio,
			final String salasConEquipoInformatico);

	/**
	 * Obtiene la lista de mesas de la sala y archivo que tengan equipo
	 * informatico o no.
	 * 
	 * @param idArchivo
	 *            Identificador del archivo
	 * @param idSala
	 *            Identificador de la sala
	 * @param salasConEquipoInformatico
	 *            Si tiene equipo informatico o no
	 * @return Lista de objetos {@link MesaVO}
	 */
	public List getMesasLibresBySala(final String idArchivo,
			final String idSala, final String salasConEquipoInformatico);
}