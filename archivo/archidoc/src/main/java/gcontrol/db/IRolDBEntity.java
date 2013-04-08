package gcontrol.db;

import gcontrol.vos.RolVO;

import java.util.List;

import common.db.IDBEntity;

/**
 * Interface para recuperacion y almacenamiento en la base de datos de
 * informacion referente a los roles de usuario definidos en el sistema <br>
 * Entidad: <b>ASCAROL</b>
 */
public interface IRolDBEntity extends IDBEntity {
	/**
	 * Obtiene la lista de roles asociados a alguno de los modulos indicados
	 * 
	 * @param modules
	 *            Lista de identificadores de modulo . Si el param es nulo dev
	 *            todos los roles
	 * @return Lista de roles {@link gcontrol.vos.RolVO}
	 */
	public List getRoles(int[] modules);

	/**
	 * Obtiene la informacion de un rol de usuario
	 * 
	 * @param idRol
	 *            Identificador de rol
	 * @return Datos de rol {@link RolVO}
	 */
	public RolVO getRolById(String idRol);

	/**
	 * Inserta un rol en la base de datos
	 * 
	 * @param rol
	 *            Datos de un rol
	 */
	public void insertRol(RolVO rol);

	/**
	 * Actualiza en la base de datos la informacion de un rol
	 * 
	 * @param rol
	 *            Datos del rol a actualizar
	 */
	public void updateRol(RolVO rol);

	/**
	 * Elimina de la base de datos roles de usuario
	 * 
	 * @param roles
	 *            Lista de identificadores de rol a eliminar
	 */
	public void eliminarRoles(String[] roles);
}