/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.manager.impl;

import java.util.List;

import es.ieci.tecdoc.isicres.api.business.dao.DepartamentoDAO;
import es.ieci.tecdoc.isicres.api.business.manager.DepartamentoManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseDepartamentoVO;
import es.ieci.tecdoc.isicres.api.business.vo.DepartamentoOficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.DepartamentoUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DepartamentoManagerImpl extends DepartamentoManager{

	protected DepartamentoDAO departamentoDAO;

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DepartamentoManager#getDepartamentoUsuario(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO)
	 */
	@Override
	public DepartamentoUsuarioVO getDepartamentoUsuario(UsuarioVO usuario) {

		return departamentoDAO.getDepartamentoUsuario(usuario);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DepartamentoManager#getDepartamentoOficina(es.ieci.tecdoc.isicres.api.business.vo.OficinaVO)
	 */
	@Override
	public DepartamentoOficinaVO getDepartamentoOficina(OficinaVO oficina) {

		return departamentoDAO.getDepartamentoOficina(oficina);
	}


	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DepartamentoManager#getDepartamentoById(java.lang.Integer)
	 */
	@Override
	public BaseDepartamentoVO getDepartamentoById(Integer idDepartamento) {
		return departamentoDAO.getDepartamentoById(idDepartamento);
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DepartamentoManager#getDepartamentos()
	 */
	@Override
	public List<BaseDepartamentoVO> getDepartamentos() {
		return departamentoDAO.findDepartamentos();
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DepartamentoManager#getDepartamentosGrupoLdap(java.lang.Integer)
	 */
	@Override
	public List<BaseDepartamentoVO> getDepartamentosGrupoLdap(
			Integer idGrupoLdap) {
		return departamentoDAO.getDepartamentosGrupoLdap(idGrupoLdap);
	}



	public DepartamentoDAO getDepartamentoDAO() {
		return departamentoDAO;
	}

	public void setDepartamentoDAO(DepartamentoDAO departamentoDAO) {
		this.departamentoDAO = departamentoDAO;
	}





}
