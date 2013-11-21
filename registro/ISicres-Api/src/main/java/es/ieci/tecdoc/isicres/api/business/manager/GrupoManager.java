package es.ieci.tecdoc.isicres.api.business.manager;

import java.util.List;

import es.ieci.tecdoc.isicres.api.business.vo.BaseUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.GrupoUsuarioVO;

public abstract class GrupoManager {

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
	 * Devuelve un listado de los usuarios asociados al grupo
	 *
	 * @param grupoUsuarioVO Grupo del que queremos obtener los usuarios
	 *
	 * @return
	 */
	public abstract List<BaseUsuarioVO> getUsuariosDelGrupo (Integer idGrupo);

	/**
	 * Devuelve los grupos a los que pertenece un usuario
	 *
	 * @param idUsuario Identificador del usuario del que queremos obtener los grupos a los que NO pertenece
	 *
	 * @return
	 */
	public abstract List<GrupoUsuarioVO> getGruposPertenecientesUsuario (Integer idUsuario);

	/**
	 * Devuelve los grupos q los que el usuario NO pertenece
	 *
	 * @param idUsuario Identificador del usuario del que queremos obtener los grupos a los que NO pertenece
	 *
	 * @return
	 */
	public abstract List<GrupoUsuarioVO> getGruposNoPertenecientesUsuario (Integer idUsuario);

}
