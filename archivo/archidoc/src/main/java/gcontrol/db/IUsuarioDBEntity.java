package gcontrol.db;

import gcontrol.vos.UsuarioVO;

import java.util.List;

import common.db.IDBEntity;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;

/**
 * Métodos para la recuperación y actualización de los datos referentes a
 * usuarios mantenidos por el sistema <br>
 * Entidad: <b>ASCAUSUARIO</b>
 */
public interface IUsuarioDBEntity extends IDBEntity {
	/**
	 * Obtiene la información de un usuario en archivo.
	 * 
	 * @param userType
	 *            Tipo de usuario.
	 * @param idUsrExtGestor
	 *            Identificador del usuario en el sistema gestor.
	 * @return Información de un usuario de archivo.
	 */
	public abstract UsuarioVO getUsuario(String userType, String idUsrExtGestor);

	/**
	 * Obtiene la información de un usuario en archivo.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario en la aplicación.
	 * @return Información de un usuario de archivo.
	 */
	public abstract UsuarioVO getUsuario(String idUsuario);

	/**
	 * Obtiene la información de un superusuario en archivo.
	 * 
	 * @param nombreUsuario
	 *            Nombre del usuario que se busca
	 * @return Información de un superusuario de archivo.
	 */
	public UsuarioVO getSuperusuario(String nombreUsuario);

	/**
	 * Obtiene la información de un conjunto de usuarios en archivo.
	 * 
	 * @param idUsuario
	 *            Identificadores de usuario en la aplicación.
	 * @return Información de usuarios de archivo.
	 */
	public abstract List getUsuarios(String[] idUsuario);

	/**
	 * Obtiene la información de un conjunto de usuarios en archivo.
	 * 
	 * @param idsUsuario
	 *            Identificadores de los usuario en el sistema de organización.
	 * @param permisos
	 *            Permisos de los usuarios.
	 * @return Información de usuarios de archivo.
	 */
	public List getUsuariosXIdsEnSistOrg(String[] idsUsuario, String[] permisos);

	/**
	 * Obtiene la lista de usuarios activos.
	 * 
	 * @return Lista de usuarios activos.
	 */
	public abstract List getUsuariosActivos();

	/**
	 * Recupera todos los usuarios dados de alta en el sistema
	 * 
	 * @param pageInfo
	 *            Datos para paginación de resultados
	 * @return Lista de usuarios {@link UsuarioVO}
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getUsuarios(PageInfo pageInfo) throws TooManyResultsException;

	/**
	 * Obtiene una lista de usuarios que tiene alguno de los permisos indicados
	 * 
	 * @param id
	 *            [] Listado de los identificadores de los roles que pueden
	 *            tener los usuarios
	 * @return Listado de usuarios con alguno de los roles indicados.
	 */
	public abstract List getUsuariosWithRoles(String id[]);

	/**
	 * Obtiene una lista de usuarios que tienen alguno de los permisos
	 * indicados.
	 * 
	 * @param permisos
	 *            Listado de permisos
	 * @return Listado de usuarios ({@link UsuarioVO}).
	 */
	public List getUsuariosWithPermissions(String[] permisos);

	/**
	 * Obtiene una lista de usuarios que tienen alguno de los permisos
	 * indicados. Y además filtrados por un nombre de solicitante.
	 * 
	 * @param permisos
	 *            Listado de permisos
	 * @param filtro
	 *            Nombre de solicitante a filtrar
	 * @return Listado de usuarios ({@link UsuarioVO}).
	 */
	public List getUsuariosWithPermissions(String[] permisos, String filtro);

	/**
	 * Obtiene una lista de usuarios que tienen alguno de los permisos indicados
	 * y pertenecen al archivo especificado.
	 * 
	 * @param permisos
	 *            Listado de permisos
	 * @param idArchivos
	 *            Identificadores de los archivos.
	 * @return Listado de usuarios ({@link UsuarioVO}).
	 */
	public List getUsuariosWithPermissionsAndArchive(String[] permisos,
			String[] idArchivos);

	/**
	 * Busqueda de usuarios por nombre
	 * 
	 * @param query
	 *            Cadena que debe estar contenida en el nombre o apellidos de
	 *            los usuarios
	 * @return Lista de usuarios {@link UsuarioVO}
	 */
	public abstract List findByName(String query);

	/**
	 * Busqueda de usuarios por nombre y apellidos
	 * 
	 * @param query
	 *            Cadena con el nombre y los apellidos de los usuarios
	 * @return Lista de usuarios {@link UsuarioVO}
	 */
	// public List findByNameSurname(String query);
	public List findByNameSurname(String name, String surname);

	/**
	 * @param user
	 */
	public abstract UsuarioVO insert(final UsuarioVO user);

	/**
	 * Actualziacion del usuario
	 * 
	 * @param user
	 * @return
	 */

	public abstract void updateUsuario(final UsuarioVO user);

	public abstract void deleteUsuario(final UsuarioVO user);

	/**
	 * Busca en la base de datos los usuarios que verifican los criterios que se
	 * indican
	 * 
	 * @param tipoUsuario
	 *            Tipo de usuario. Puede ser nulo.
	 * @param searchTokenNombre
	 *            . Texto que debe estar contenido en el nombre del usuario.
	 *            Puede ser nulo
	 * @param searchTokenApellidos
	 *            . Texto que debe estar contenido en los apellidos del usuario.
	 *            Puede ser nulo
	 * @return Lista de usurios existentes en la base de datos y que verifican
	 *         los criterios indicados {@link UsuarioVO}
	 */
	public List findUsuarios(String tipoUsuario, String searchTokenNombre,
			String searchTokenApellidos);

	/**
	 * Obtiene los usuarios filtrados por tipo.
	 * 
	 * @param tipo
	 * @param filtro
	 *            ;
	 * @return
	 */
	public List getUsuariosByTipo(String tipo, String filtro);

	/**
	 * Obtiene la lista de usuarios asociados a un determinado órgano cuya fecha
	 * de finalización es nula o es posterior a la fecha actual
	 * 
	 * @return Lista de usuarios activos.
	 */
	public abstract List getUsuariosVigentesAsociadosAOrgano(String idOrgano);
}