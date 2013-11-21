package es.ieci.tecdoc.isicres.api.business.dao;

import java.util.List;

import es.ieci.tecdoc.isicres.api.business.vo.BaseUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.ConfiguracionUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.GrupoUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosUsuarioVO;

public interface UsuarioDAO {

	/**
	 * Devuelve un usuario a partir del identificador
	 *
	 * @param id
	 *            Identificador del usuario
	 *
	 * @return
	 */
	public BaseUsuarioVO getUsuarioById(Integer id);

	/**
	 * Devuelve un usuario a partir de su nombre
	 *
	 * @param name
	 *            Nombre del usuario
	 *
	 * @return
	 */
	public BaseUsuarioVO getUsuarioByLogin(String name);

	/**
	 * Devuelve una lista con todos los usuarios
	 *
	 * @return
	 */
	public List<BaseUsuarioVO> getUsuarios();

	/**
	 * Devuelve una lista con todos los usuarios de ldap
	 *
	 * @return
	 */
	public List<BaseUsuarioVO> getUsuariosLdap();


	/**
	 * Devuelve la configuración del usuario
	 *
	 * @param id
	 *            Identificador del usuario
	 *
	 * @return
	 */
	public ConfiguracionUsuarioVO getConfiguracionUsuario(Integer idUsuario);

	/**
	 * Devuelve los permisos del usuario
	 *
	 * @param idUsuario
	 * 			Identificador del usuario
	 * @return
	 */
	public PermisosUsuarioVO getPermisosUsuario(Integer idUsuario);

}
