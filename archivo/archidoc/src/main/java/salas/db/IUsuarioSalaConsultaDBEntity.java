/**
 *
 */
package salas.db;

import java.util.Date;
import java.util.List;

import salas.vos.BusquedaUsuarioSalaConsultaVO;
import salas.vos.UsuarioSalasConsultaVO;

import common.db.IDBEntity;

/**
 * Entidad: <b>ASGSUSRCSALA</b>
 * 
 * @author IECISA
 * 
 */
public interface IUsuarioSalaConsultaDBEntity extends IDBEntity {

	/**
	 * Obtiene el Usuario por su identificador
	 * 
	 * @param idUsuario
	 *            Cadena que define el identificador del usuario
	 * @return {@link UsuarioSalasConsultaVO} con los datos
	 */
	public UsuarioSalasConsultaVO getUsuarioById(String idUsuario);

	/**
	 * Obtiene los usuarios por su estado de vigencia
	 * 
	 * @param vigente
	 *            Cadena que define la vigencia. Si se pasa vacío no se incluye
	 *            en el filtro.
	 * @return Lista de {@link UsuarioSalasConsultaVO} con los datos.
	 */
	public List getUsuariosByVigencia(String vigente);

	/**
	 * Busca los usuario por los criterios de búsqueda.
	 * 
	 * @param busquedaUsuarioSalaConsultaVO
	 *            Datos para filtrar
	 * @return Lista de {@link UsuarioSalasConsultaVO}
	 */
	public List findUsuarios(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSalaConsultaVO);

	public List findUsuariosByArchivo(BusquedaUsuarioSalaConsultaVO vo);

	/**
	 * Inserta el Usuario en la aplicación
	 * 
	 * @param usuarioSalaConsultaVO
	 */
	public void insertUsuario(UsuarioSalasConsultaVO usuarioSalaConsultaVO);

	/**
	 * Actualiza los datos del usuario
	 * 
	 * @param usuarioSalaConsultaVO
	 */
	public void updateUsuario(UsuarioSalasConsultaVO usuarioSalaConsultaVO);

	/**
	 * Elimina el usuario seleccionado.
	 * 
	 * @param usuarioSalaConsultaVO
	 */
	public void deleteUsuario(String idUsuario);

	/**
	 * Obtiene el número de usuarios que están asociados a un usuario externo.
	 * 
	 * @param idUsuarioExterno
	 *            Identificador del usuario externo
	 * @param vigente
	 *            Indica si se buscan sólo usuarios vigentes
	 * @return Numero de usuasrios de consulta
	 */
	public int getCountUsuariosByIdExterno(String idUsuarioExterno,
			String vigente);

	/**
	 * Obtiene el número de usuarios que están asociados a un usuario externo
	 * distintos del usuario pasado como parámetro.
	 * 
	 * @param idUsuarioExterno
	 *            Identificador del usuario externo
	 * @param idDistintoUsuario
	 *            Identificador del usuario
	 * @param vigente
	 *            Indica si se buscan sólo usuarios vigentes
	 * @return Numero de usuasrios de consulta
	 */
	public int getCountUsuariosByIdExternoDistintosUsuario(
			String idUsuarioExterno, String idDistintoUsuario, String vigente);

	/**
	 * Obtiene el usuario de consulta vigente por el identificador del usuario
	 * de aplicación
	 * 
	 * @param idUsuarioExterno
	 *            Identificador de usuario de la aplicación
	 * @param vigente
	 *            Para indicar si es vigente o no
	 * @return Objeto {@link UsuarioSalasConsultaVO}
	 */
	public UsuarioSalasConsultaVO getUsuarioExterno(String idUsuarioExterno,
			String vigente);

	/**
	 * Obtiene los usuarios con permiso de consulta en sala por el identificador
	 * del archivo seleccionado
	 * 
	 * @param idArchivo
	 *            Identificador del archivo
	 * @param filtro
	 *            Filtro de búsqueda
	 * @return Lista de objetos {@link UsuarioSalasConsultaVO}
	 */
	public List getUsuariosConPermisoConsultaSala(String idArchivo,
			String filtro);

	/**
	 * Obtiene el numero de usuarios de consulta en sala, en el rango de fechas
	 * indicado
	 * 
	 * @param fechaIni
	 *            Fecha inicial de alta de usuario
	 * @param fechaFin
	 *            Fecha final de alta de usuario
	 * @return int con el numero de usuarios
	 */
	public int getCountUsuariosConsultaSala(Date fechaIni, Date fechaFin);

	/**
	 * Obtiene un listado de temas para el usuario de consulta en sala
	 * seleccionado
	 * 
	 * @param idUsrCSala
	 *            Identificador del usuario de consulta en sala
	 * @return Lista de objetos {@link UsuarioSalasConsultaVO}
	 */
	public List getTemasUsuarioConsulta(String idUsrCSala);
}
