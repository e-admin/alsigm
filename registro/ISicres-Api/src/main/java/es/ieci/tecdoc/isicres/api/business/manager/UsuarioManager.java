/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.manager;

import java.util.List;

import es.ieci.tecdoc.isicres.api.business.vo.BaseUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.ConfiguracionUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.GrupoUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public abstract class UsuarioManager {

	/**
	 * Devuelve un usuario a partir del identificador
	 *
	 * @param id
	 *            Identificador del usuario
	 *
	 * @return
	 */
	public abstract BaseUsuarioVO getUsuarioById(Integer id);

	/**
	 * Devuelve un usuario completo a partir del identificador. Con los grupos y
	 * las oficinas del usuario
	 *
	 * @param id
	 *            Identificador del usuario
	 *
	 * @return
	 */
	public abstract UsuarioVO getUsuario(Integer id);

	/**
	 * Devuelve un usuario a partir de su nombre
	 *
	 * @param name
	 *            Nombre del usuario
	 *
	 * @return
	 */
	public abstract BaseUsuarioVO getUsuarioByName(String name);

	/**
	 * Devuelve una lista con todos los usuarios
	 *
	 * @return
	 */
	public abstract List<BaseUsuarioVO> getUsuarios();

	/**
	 * Devuelve una lista con todos los usuarios de ldap
	 *
	 * @return
	 */
	public abstract List<BaseUsuarioVO> getUsuariosLdap();

	/**
	 * Devuelve una lista con las oficinas a las que pertenece el usuario
	 *
	 * @param id
	 *            Usuario
	 *
	 * @return
	 */
	public abstract List<OficinaVO> getOficinasUsuario(UsuarioVO usuario);

	/**
	 * Devuelve una lista con los grupos a los que pertenece el usuario
	 *
	 * @param id
	 *            Identificador del usuario
	 *
	 * @return
	 */
	public abstract List<GrupoUsuarioVO> getGruposUsuario(Integer idUsuario);

	/**
	 * Devuelve la configuración del usuario
	 *
	 * @param id
	 *            Identificador del usuario
	 *
	 * @return
	 */
	public abstract ConfiguracionUsuarioVO getConfiguracionUsuario(
			Integer idUsuario);

	/**
	 * Devuelve los permisos del usuario
	 *
	 * @param idUsuario
	 * 			Identificador del usuario
	 * @return
	 */
	public abstract PermisosUsuarioVO getPermisosUsuario(Integer idUsuario);

}
