package gcontrol.db;

import java.util.List;

import common.db.IDBEntity;

/**
 * Metodos de recuperacion y almacenamiento en base de datos de la asignación de
 * usuarios a grupos <br>
 * Entidad: <b>ASCAUSRGRP</b>
 */
public interface IGrupoUsuarioDBEntity extends IDBEntity {
	/**
	 * Obtiene los grupos del usuario.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @return Grupos del usuario.
	 */
	public List getGruposUsuario(String idUsuario);

	/**
	 * Obtiene los usuarios que pertenecen a un grupo
	 * 
	 * @param idGrupo
	 *            Identificador de grupo
	 * @return Lista de usuarios {@link gcontrol.vos.UsuarioVO}
	 */
	public List getUsuariosGrupo(String idGrupo);

	/**
	 * Incorpora un usuario a un grupo
	 * 
	 * @param idGrupo
	 *            Identificador de grupo
	 * @param idUsuario
	 *            Identificador de usuario
	 */
	public void insertGrupoUsuario(String idGrupo, String idUsuario);

	/**
	 * Elimina un conjunto de usuarios de un grupo
	 * 
	 * @param idGrupo
	 *            Identificador de grupo
	 * @param idUsuario
	 *            Lista de identificadores de usuario. En caso de ser null se
	 *            eliminaran todos los usuarios del grupo
	 */
	public void removeGrupoUsuario(String idGrupo, String[] idUsuario);

}