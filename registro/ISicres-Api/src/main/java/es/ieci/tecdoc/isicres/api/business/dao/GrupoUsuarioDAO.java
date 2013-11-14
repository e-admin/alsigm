package es.ieci.tecdoc.isicres.api.business.dao;

import java.util.List;

import es.ieci.tecdoc.isicres.api.business.vo.BaseUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.GrupoUsuarioVO;

public interface GrupoUsuarioDAO {

	/**
	 * Devuelve el grupo a partir de su identificador
	 *
	 * @param id Identificador del grupo
	 *
	 * @return
	 */
	public abstract GrupoUsuarioVO getGrupoById(Integer idGrupo);

	/**
	 * Devuelve un listado con todos los grupos
	 *
	 * @return
	 *
	 */
	public abstract List<GrupoUsuarioVO> getGrupos();

	/**
	 * Devuelve un listado con todos los grupos de LDAP
	 *
	 * @return
	 */
	public List<GrupoUsuarioVO> getGruposLdap();

	/**
	 *
	 * TODO
	 *
	 * Devuelve un listado de los usuarios asociados al grupo
	 *
	 * @param idGrupo Identificador del grupo del que queremos obtener los usuarios
	 *
	 * @return
	 */
	public abstract List<BaseUsuarioVO> getUsuariosDelGrupo(
			Integer idGrupo);

	/**
	 * Devuelve los grupos a los que pertenece un usuario
	 *
	 * @param usuarioVO Usuario del que queremos obtener los grupos a los que NO pertenece
	 *
	 * @return
	 */
	public abstract List<GrupoUsuarioVO> getGruposPertenecientesUsuario(
			Integer idUsuario);

	/**
	 * Devuelve los grupos q los que el usuario NO pertenece
	 *
	 * @param usuarioVO Usuario del que queremos obtener los grupos a los que NO pertenece
	 *
	 * @return
	 */
	public abstract List<GrupoUsuarioVO> getGruposNoPertenecientesUsuario(
			Integer idUsuario);

}
