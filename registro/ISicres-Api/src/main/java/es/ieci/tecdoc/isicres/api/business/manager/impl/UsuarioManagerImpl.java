/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.manager.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import es.ieci.tecdoc.isicres.api.business.dao.DepartamentoDAO;
import es.ieci.tecdoc.isicres.api.business.dao.GrupoUsuarioDAO;
import es.ieci.tecdoc.isicres.api.business.dao.OficinaDAO;
import es.ieci.tecdoc.isicres.api.business.dao.UsuarioDAO;
import es.ieci.tecdoc.isicres.api.business.helper.UsuarioHelper;
import es.ieci.tecdoc.isicres.api.business.manager.UsuarioManager;
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
public class UsuarioManagerImpl extends UsuarioManager {

	protected UsuarioDAO usuarioDAO;

	protected GrupoUsuarioDAO grupoUsuarioDAO;

	protected OficinaDAO oficinaDAO;

	protected DepartamentoDAO departamentoDAO;

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.UsuarioManager#getUsuarioById(java.lang.String)
	 */
	@Override
	public BaseUsuarioVO getUsuarioById(Integer id) {
		return getUsuarioDAO().getUsuarioById(id);
	}

	public UsuarioVO getUsuario(Integer id) {
		UsuarioVO usuario = null;
		BaseUsuarioVO baseusuario = getUsuarioById(id);
		if (baseusuario != null) {
			usuario = new UsuarioVO();
			BeanUtils.copyProperties(baseusuario, usuario);
			usuario.setGruposUsuario(getGruposUsuario(id));
			usuario.setOficinas(getOficinasUsuario(usuario));
			usuario.setDepartamentoUsuario(departamentoDAO
					.getDepartamentoUsuario(usuario));
		}

		return usuario;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.UsuarioManager#getUsuarioByName(java.lang.String)
	 */
	@Override
	public BaseUsuarioVO getUsuarioByName(String name) {
		return getUsuarioDAO().getUsuarioByLogin(name);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.UsuarioManager#getUsuarios()
	 */
	@Override
	public List<BaseUsuarioVO> getUsuarios() {
		return getUsuarioDAO().getUsuarios();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.UsuarioManager#getOficinasUsuario(java.lang.String)
	 */
	@Override
	public List<OficinaVO> getOficinasUsuario(UsuarioVO usuario) {
		if (usuario.getConfiguracionUsuario() == null
				|| usuario.getConfiguracionUsuario().getLocale() == null) {
			usuario = UsuarioHelper.validateUsusario(usuario);
		}
		return getOficinaDAO().getOficinasByUsuario(
				usuario.getConfiguracionUsuario().getLocale(), usuario);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.UsuarioManager#getGruposUsuario(java.lang.String)
	 */
	public List<GrupoUsuarioVO> getGruposUsuario(Integer idUsuario) {
		return getGrupoUsuarioDAO().getGruposPertenecientesUsuario(idUsuario);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.UsuarioManager#getConfiguracionUsuario(java.lang.String)
	 */
	public ConfiguracionUsuarioVO getConfiguracionUsuario(Integer idUsuario) {
		return getUsuarioDAO().getConfiguracionUsuario(idUsuario);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.UsuarioManager#getPermisosUsuario(java.lang.Integer)
	 */
	@Override
	public PermisosUsuarioVO getPermisosUsuario(Integer idUsuario) {
		return usuarioDAO.getPermisosUsuario(idUsuario);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.UsuarioManager#getUsuariosLdap()
	 */
	public List<BaseUsuarioVO> getUsuariosLdap() {
		return getUsuarioDAO().getUsuariosLdap();
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public GrupoUsuarioDAO getGrupoUsuarioDAO() {
		return grupoUsuarioDAO;
	}

	public void setGrupoUsuarioDAO(GrupoUsuarioDAO grupoUsuarioDAO) {
		this.grupoUsuarioDAO = grupoUsuarioDAO;
	}

	public OficinaDAO getOficinaDAO() {
		return oficinaDAO;
	}

	public void setOficinaDAO(OficinaDAO oficinaDAO) {
		this.oficinaDAO = oficinaDAO;
	}

	public DepartamentoDAO getDepartamentoDAO() {
		return departamentoDAO;
	}

	public void setDepartamentoDAO(DepartamentoDAO departamentoDAO) {
		this.departamentoDAO = departamentoDAO;
	}



}
