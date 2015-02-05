package gcontrol.db;

import gcontrol.vos.PermisoVO;
import gcontrol.vos.RolVO;

import java.util.List;

import common.db.IDBEntity;

/**
 * Entidad: <b>ASCAPERMGENROL</b>
 * 
 * @author IECISA
 * 
 */
public interface IPermisoRolDBEntity extends IDBEntity {
	/**
	 * Obtiene los permisos de un rol.
	 * 
	 * @param idRol
	 *            Identificador del rol.
	 * @return Permisos del rol.
	 */
	public List getPermisosRol(String idRol);

	/**
	 * Obtiene los roles que tiene asociado algunos de los permisos indicados
	 * 
	 * @param permisos
	 *            Conjunto de permisos posible de los que deseamos obtener los
	 *            roles
	 * @return Listado de roles que contienen alguno de los permisos
	 */
	public List getRolesPermisos(String permisos[]);

	/**
	 * Establece los permisos de un rol
	 * 
	 * @param id
	 *            Identificador de rol
	 * @param permisosRol
	 *            Lista de permisos a establecer
	 */
	public void setPermisosRol(RolVO rol, String[] permisosRol);

	/**
	 * Quita un conjunto de permisos de un rol
	 * 
	 * @param idRol
	 *            Identificador de rol
	 * @param permisoRol
	 *            Lista de permisos
	 */
	public void quitarPermisosRol(String idRol, String[] permisoRol);

	/**
	 * Agrega un conjunto de permisos a un rol
	 * 
	 * @param rol
	 *            Rol al que se incorporan los permisos
	 * @param permisoRol
	 *            Lista de permisos
	 */
	public void agregarPermisosRol(RolVO rol, String[] permisoRol);

	/**
	 * Quita todos los permisos asignados al conjunto de roles indicados
	 * 
	 * @param roles
	 *            Conjunto de identificadores de rol
	 */
	public void clearPermisosRol(String[] roles);

	/**
	 * Obtiene los permisos de un usuario
	 * 
	 * @param idUsuario
	 *            Cadena que contiene el identificador del usuario.
	 * @return Lista de {@link PermisoVO}
	 */
	public List getPermisosUsuario(String idUsuario);

	public List getPermisosUsuario(String idUsuario, String[] permisos);
}