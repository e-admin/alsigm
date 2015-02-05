package gcontrol.db;

import gcontrol.vos.RolVO;
import gcontrol.vos.UsuarioVO;

import java.util.List;

import common.db.IDBEntity;

/**
 * Entidad: <b>ASCAROLUSR</b>
 * 
 * @author IECISA
 * 
 */
public interface IRolUsuarioDBEntity extends IDBEntity {
	/**
	 * Obtiene los roles de un usuario.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @return Roles del usuario.
	 */
	public abstract List getRolesUsuario(String idUsuario);

	/**
	 * Obtiene los usuarios que tienen asociado un determinado rol
	 * 
	 * @param idRol
	 *            Identificador de rol
	 * @return Lista de usuarios {@link UsuarioVO}
	 */
	public abstract List getUsuariosByRol(String idRol);

	/**
	 * Elimina roles del conjunto de roles asociados a un usuario
	 * 
	 * @param idUsuario
	 *            Identificador de usuario. Si es nulo se eliminan todos los
	 *            usuarios de los roles suministrados
	 * @param roles
	 *            Lista de identificadores de los roles a eliminar En caso de
	 *            ser null se eliminan todos los roles del usuario
	 */
	public abstract void removeRolesUsuario(String idUsuario, String[] roles);

	/**
	 * Incorpora un rol al conjunto de roles asociados a un usuario
	 * 
	 * @param idUsuario
	 *            Identificador de usuario
	 * @param rol
	 *            Rol a incorporar {@link gcontrol.vos.RolVO}
	 */
	public abstract void insertRolUsuario(String idUsuario, RolVO rol);

	/**
	 * Desasigna todos los usuarios actualmente asignados a los roles que se
	 * indican
	 * 
	 * @param roles
	 *            Lista de identificadores de rol
	 */
	public abstract void clearUsuariosRol(String[] roles);
}