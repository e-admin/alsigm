package salas.db;

import java.util.Date;
import java.util.List;

import salas.vos.BusquedaRegistroConsultaSalaVO;
import salas.vos.RegistroConsultaSalaVO;

import common.db.IDBEntity;

/**
 * Entidad: <b>ASGSREGCSALA</b>
 * 
 * @author IECISA
 * 
 */
public interface IRegistroConsultaSalaDBEntity extends IDBEntity {

	public void insertRegistroConsultaSala(
			final RegistroConsultaSalaVO registroConsultaSalaVO);

	public void deleteRegistroConsultaSala(final String idRegistro);

	public void updateRegistroConsultaSala(
			final RegistroConsultaSalaVO registroConsultaSalaVO);

	/**
	 * Obtiene un registro de consulta de sala por su indentificador
	 * 
	 * @param idRegistro
	 * @return Objeto {@link RegistroConsultaSalaVO}
	 */
	public RegistroConsultaSalaVO getRegistroConsultaSalaById(
			final String idRegistro);

	/**
	 * Obtiene un registro por el identificador del usuario
	 * 
	 * @param idUsuarioConsulta
	 *            identificador del usuario de consulta de sala
	 * @return Objeto {@link RegistroConsultaSalaVO}
	 */
	public RegistroConsultaSalaVO getRegistroConsultaSalaByIdUsuario(
			String idUsuarioConsulta);

	/**
	 * Obtiene la lista de usuarios de consulta registrados en la sala
	 * 
	 * @return Lista de objetos {@link RegistroConsultaSalaVO}
	 */
	public List getUsuariosRegistrados();

	/**
	 * Obtiene los registros de consulta en sala por sus identificadores
	 * 
	 * @param idsRegistro
	 *            Identificadores de los registros
	 * @return Lista de objetos {@link RegistroConsultaSalaVO}
	 */
	public List getRegistrosConsultaSalaByIds(String[] idsRegistro);

	/**
	 * Actualiza los registros para indicar la fecha de salida de la sala
	 * 
	 * @param idRegistro
	 *            Identificador del registro de consulta en sala
	 */
	public void updateRegistrosConsultaSala(String idRegistro);

	/**
	 * Busca los registros de consulta en sala por los criterios de búsqueda.
	 * 
	 * @param BusquedaRegistroConsultaSalaVO
	 *            Datos para filtrar
	 * @return Lista de objetos {@link RegistroConsultaSalaVO}
	 */
	public List findRegistros(
			BusquedaRegistroConsultaSalaVO busquedaRegistroConsultaSalaVO);

	/**
	 * Obtiene una lista de registros abiertos
	 * 
	 * @return Lista de objetos {@link RegistroConsultaSalaVO}
	 */
	public List getRegistrosAbiertos();

	/**
	 * Obtiene el número de registros de consulta en sala abiertos para el
	 * usuario conectado
	 * 
	 * @return Número de registros de consulta en sala
	 */
	public int countRegistrosAbiertos();

	/**
	 * Comprueba si existe un registro de consulta en sala que cumpla las
	 * condiciones que se le indican por parámetro
	 * 
	 * @param idArchivo
	 *            Identificador del archivo
	 * @param idUsrCSala
	 *            Identificador del usuario de consulta en sala
	 * @param idScaUsr
	 *            Identificador del usuario asociado al usuario de consulta
	 * @param fechaEntrada
	 *            Fecha de entrada del registro de consulta
	 * @return Objeto de tipo {@link RegistroConsultaSalaVO}
	 */
	public RegistroConsultaSalaVO getRegistro(String idArchivo,
			String idUsrCSala, String idScaUsr, Date fechaEntrada);

	/**
	 * Numero de registros de consulta en sala
	 * 
	 * @param fechaIni
	 *            Fecha de inicio de entrada en la sala
	 * @param fechaFin
	 *            Fecha final de entrada en la sala
	 * @return int numero de registros
	 */
	public int getCountRegistros(Date fechaIni, Date fechaFin);
}