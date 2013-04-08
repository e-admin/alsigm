package salas.db;

import java.util.List;

import salas.vos.ElementoNavegacionVO;
import salas.vos.MesaVO;

import common.db.IDBEntity;

/**
 * Entidad: <b>ASGSMESA</b>
 * 
 * @author IECISA
 * 
 */
public interface IMesaDBEntity extends IDBEntity {

	public void insertMesa(final MesaVO mesaVO);

	public void deleteMesa(final String idMesa);

	public void updateMesa(final MesaVO mesaVO);

	/**
	 * Obtiene una mesa por su identificador
	 * 
	 * @param idMesa
	 * @return Objeto {@link MesaVO}
	 */
	public MesaVO getMesaById(final String idMesa);

	/**
	 * Obtiene una lista de mesas
	 * 
	 * @param idsMesa
	 *            Identificadores de las mesas
	 * @return Lista de {@link MesaVO}
	 */
	public List getMesasById(String[] idsMesa);

	/**
	 * Obtiene la Lista de Mesas de la sala.
	 * 
	 * @param idSala
	 *            Identificador de las sala
	 * @return Lista de {@link MesaVO} que pertenecen a las salas especificadas.
	 */
	public List getMesas(String idSala);

	/**
	 * Devuelve el número de mesas que pertenecen a un determinada sala
	 * 
	 * @param idSala
	 * @return número de mesas
	 */
	public int countMesasPorSala(final String idSala);

	/**
	 * Devuelve el número de mesas ocupadas por sala
	 * 
	 * @param idSala
	 *            Identificador de la sala
	 * @return número de mesas ocupadas
	 */
	public int counMesasBySalaYEstados(String idSala, String[] estados);

	/**
	 * Obtiene el mayor número de orden de mesa de la sala
	 * 
	 * @param idSala
	 *            Identificador de la sala
	 * @return número de orden máximo
	 */
	public int getMaxNumOrden(final String idSala);

	/**
	 * Obtiene la mesa por sala y codigo
	 * 
	 * @param idSala
	 *            Identificador de la sala
	 * @param codigo
	 *            de la mesa
	 * @return Objeto {@link MesaVO}
	 */
	public MesaVO getMesaBySalaAndCodigo(final String idSala,
			final String codigo);

	/**
	 * Modifica el estado de las mesas
	 * 
	 * @param idsMesa
	 *            Identificadores de las mesas a modificar
	 * @param estadoAEstablecer
	 *            Nuevo estado a establecer a las mesas seleccionadas
	 * @return int numero de registros actualizados
	 */
	public void cambiarEstado(String[] idsMesa, String estadoAEstablecer);

	/**
	 * Obtiene el número de mesas que pertenecen a un edificio, especificando
	 * sus estados.
	 * 
	 * @param idEdificio
	 *            Identificador del Edificio
	 * @param estados
	 *            Array de cadenas que contienen los estados.
	 * @return
	 */
	public int countMesasByEdificiosYEstados(String idEdificio, String[] estados);

	/**
	 * Elimina las mesas de la sala
	 * 
	 * @param idSala
	 *            Identificador de la sala
	 * @param idsMesa
	 *            Identificadores de las mesas a eliminar
	 */
	public void deleteMesas(String idSala, String[] idsMesa);

	/**
	 * Elimina las mesas de la sala
	 * 
	 * @param idSala
	 *            Identificador de la sala
	 */
	public void deleteMesasByIdSala(final String idSala);

	/**
	 * Actualiza el codigo de la mesa
	 * 
	 * @param idMesa
	 *            Identificador de la mesa
	 * @param codigoMesa
	 *            nuevo codigo
	 */
	public void cambiarCodigo(final String idMesa, final String codigoMesa);

	/**
	 * Obtiene una lista de mesas de la sala para la navegación en registro
	 * 
	 * @param idSala
	 *            Identificador de la sala
	 * @return Lista de Objetos {@link ElementoNavegacionVO}
	 */
	public List getMesasNavegacion(final String idSala);

	/**
	 * Actualiza el registro de mesa para bloquear, para evitar que se produzcan
	 * dos registros sobre la misma mesa con usuarios diferentes.
	 * 
	 * @param idMesa
	 *            Identificador de la mesa a ocupar
	 */
	public void updateBloqueo(final String idMesa);

	/**
	 * Registrar Entrada el usuario en la sala
	 * 
	 * @param idMesa
	 *            Identificador de la mesa asignada
	 * @param idUsuario
	 *            Identificador del usuario de consulta en sala
	 */
	public int registrarEntrada(String idMesa, String idUsuario);

	/**
	 * Registrar Salida de la Sala de los usuarios seleccionados
	 * 
	 * @param idUsuarioSala
	 *            Identificador del usuario de consulta en sala
	 */
	public void registrarSalida(String idUsuarioSala);

	/**
	 * Obtiene las mesas ocupadas por el usuario en los archivos especificados
	 * 
	 * @param idUsuario
	 *            Identificador del usuario de consulta en sala
	 * @param idsArchivo
	 *            Identificadores de los archivos
	 * @return Lista de Objetos {@link MesaVO}
	 */
	public List getMesasByIdUsuarioSalaArchivos(String idUsuario,
			String[] idsArchivo);

	/**
	 * Obtiene las mesas ocupadas por el usuario de consulta en sala
	 * 
	 * @param idUsuario
	 *            Identificador del usuario de consulta en sala
	 * @return Lista de objetos {@link MesaVO}
	 */
	public List getMesasByIdUsuarioSala(String idUsuario);
}