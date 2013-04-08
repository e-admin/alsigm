package auditoria.db;

import java.util.Collection;

import auditoria.vos.CritUsuarioVO;

import common.db.IDBEntity;

/**
 * Entidad: <b>AANAUDITUSR</b>
 * 
 * @author IECISA
 */
public interface ICritUsuariosDBEntity extends IDBEntity {
	/**
	 * Obtiene el nivel de criticidad(log) del grupo de usuarios solicitado
	 * 
	 * @param i
	 *            Identificador del grupo de usuarios del que deseamos obtener
	 *            el nivel de criticidad.
	 * @return Nivel de criticidad para el grupo de usuario o Integer.MIN_VALUE
	 *         en caso de no estar definido.
	 */
	public abstract int getGroupLogLevel(String i);

	/**
	 * Obtiene el nivel de criticidad(log) del rol solicitado
	 * 
	 * @param i
	 *            Identificador del rol del que deseamos obtener el nivel de
	 *            criticidad.
	 * @return Nivel de criticidad para el rol o Integer.MIN_VALUE en caso de no
	 *         esta definido.
	 */
	public abstract int getRolLogLevel(String i);

	/**
	 * Obtiene el nivel de criticidad(log) del usuariosolicitado
	 * 
	 * @param i
	 *            Identificador del usuario del que deseamos obtener el nivel de
	 *            criticidad.
	 * @return Nivel de criticidad para el grupo de o Integer.MAX_VALUE en caso
	 *         de no esta definido.
	 */
	public abstract int getUserLogLevel(String i);

	/**
	 * Obtiene los grupos de usuarios existentes en la aplicacion con su nivel
	 * de criticidad
	 * 
	 * @return Listado de los grupos de usuarios existentes en la aplicacion
	 */
	public abstract Collection getGroupsWithLevels();

	/**
	 * Obtiene los usuarios existentes en la aplicacion con su nivel de
	 * criticidad
	 * 
	 * @return Listado de los usuarios existentes en la aplicacion
	 */
	public abstract Collection getUsersWithLevels();

	/**
	 * Actualiza el nivel de criticidad de un grupo de usuarios.
	 * 
	 * @param id
	 *            Identificador del grupo de usuario que deseamos actualzar
	 * @param logLevel
	 *            Nuevo nivel de criticidad para le grupo de usuarios
	 * @param tipo
	 *            Tipo del grupo 0->Usuario o 1->Grupo. -1 si no se desea cribar
	 *            por tipo
	 */
	public abstract void setGroupLogLevel(String id, int logLevel, int tipo);

	/**
	 * Actualiza el nivel de criticidad de un usuarios.
	 * 
	 * @param id
	 *            Identificador del usuario que deseamos actualzar
	 * @param logLevel
	 *            Nuevo nivel de criticidad para el usuario
	 */
	public abstract void setUserLogLevel(String id, int logLevel);

	/**
	 * Obtiene los datos de un grupo a partir de su identificador
	 * 
	 * @param id
	 *            Identifcador del grupo que deseamos obtener sus datos
	 *            asociados
	 * @param tipo
	 *            Tipo del grupo 0->Usuario o 1->Grupo. -1 si no se desea cribar
	 *            por tipo
	 * @return Datos del grupo asociado a los datos
	 */
	public abstract CritUsuarioVO getGroup(String id, int tipo);

	/**
	 * Realiza el borrado de un grupo con su criticidad asocidad de la tabla de
	 * la base de datos
	 * 
	 * @param id
	 *            Identificador del grupo que deseamos borrar
	 * @param tipo
	 *            Tipo del grupo 0->Usuario o 1->Grupo. -1 si no se desea cribar
	 *            por tipo
	 */
	public abstract void deleteGroupLogLevel(String id, int tipo);

	/**
	 * Elimina el nivel de auditoria establecido para un actor a grupo de
	 * actores que interactuan con el sistema
	 * 
	 * @param tipo
	 *            Tipo al que corresponden los actores a eliminar (Usuario,
	 *            Grupo u Órgano)
	 * @param id
	 *            Conjundo de identificadores de los actores cuyo nivel de
	 *            auditoria se quiere eliminar
	 */
	public void deleteLogLevel(int tipo, String[] id);

	/**
	 * Crear el nivel de log de un usuario o grupo de usuarios.
	 * 
	 * @param id
	 *            Identificador del grupo o usuario a crear
	 * @param logLevel
	 *            Nivel de log de la accion
	 * @param tipo
	 *            Tipo del grupo 0->Usuario o 1->Grupo
	 * @return Elemento creado en la abse de datos
	 */
	public abstract CritUsuarioVO insertGroupLogLevel(String id, int logLevel,
			int tipo);

}