/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.manager.impl;

import java.util.List;

import es.ieci.tecdoc.isicres.api.business.dao.GrupoUsuarioDAO;
import es.ieci.tecdoc.isicres.api.business.manager.GrupoManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.GrupoUsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class GrupoManagerImpl extends GrupoManager{

	protected GrupoUsuarioDAO grupoUsuarioDAO;



	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.GrupoManager#getGrupoById(java.lang.Integer)
	 */
	@Override
	public GrupoUsuarioVO getGrupoById(Integer idGrupo) {
		return grupoUsuarioDAO.getGrupoById(idGrupo);
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.GrupoManager#getGrupos()
	 */
	@Override
	public List<GrupoUsuarioVO> getGrupos() {

		return grupoUsuarioDAO.getGrupos();
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.GrupoManager#getUsuariosDelGrupo(java.lang.Integer)
	 */
	@Override
	public List<BaseUsuarioVO> getUsuariosDelGrupo(Integer idGrupo) {

		return grupoUsuarioDAO.getUsuariosDelGrupo(idGrupo);
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.GrupoManager#getGruposPertenecientesUsuario(java.lang.Integer)
	 */
	@Override
	public List<GrupoUsuarioVO> getGruposPertenecientesUsuario(Integer idUsuario) {

		return grupoUsuarioDAO.getGruposPertenecientesUsuario(idUsuario);
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.GrupoManager#getGruposNoPertenecientesUsuario(java.lang.Integer)
	 */
	@Override
	public List<GrupoUsuarioVO> getGruposNoPertenecientesUsuario(Integer idUsuario) {

		return grupoUsuarioDAO.getGruposNoPertenecientesUsuario(idUsuario);
	}

	/**
	 *
	 * @return
	 */
	public GrupoUsuarioDAO getGrupoUsuarioDAO() {
		return grupoUsuarioDAO;
	}

	/**
	 *
	 * @param grupoUsuarioDAO
	 */
	public void setGrupoUsuarioDAO(GrupoUsuarioDAO grupoUsuarioDAO) {
		this.grupoUsuarioDAO = grupoUsuarioDAO;
	}


}
