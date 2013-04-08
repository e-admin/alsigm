package es.ieci.tecdoc.isicres.api.business.manager.impl;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.api.business.dao.UnidadAdministrativaDAO;
import es.ieci.tecdoc.isicres.api.business.manager.UnidadAdministrativaManager;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoUnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaUnidadAdministrativaByTipoVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaUnidadAdministrativaByUnidadVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaWhereSqlVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoUnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public class UnidadAdministrativaManagerImpl extends
		UnidadAdministrativaManager {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.manager.UnidadAdministrativaManager
	 * #findAllTipoUnidadAdministrativa
	 * (es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 * es.ieci.tecdoc.isicres.
	 * api.business.vo.CriterioBusquedaTipoUnidadAdministrativaVO)
	 */
	public List findAllTipoUnidadAdministrativa(UsuarioVO usuario,
			CriterioBusquedaTipoUnidadAdministrativaVO criterio) {
		List result = null;

		// obtenemos el listado con los tipos de asunto
		result = getUnidadAdministrativaDAO().findAllTipoUnidadesAdmin(
				usuario.getConfiguracionUsuario().getLocale(), criterio);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.manager.UnidadAdministrativaManager
	 * #findCountAllTipoUnidadAdministrativa
	 * (es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO)
	 */
	public int findCountAllTipoUnidadAdministrativa(UsuarioVO usuario) {
		int result = 0;

		// obtenemos el listado con los tipos de asunto
		result = getUnidadAdministrativaDAO().findCountAllTipoUnidadesAdmin(
				usuario.getConfiguracionUsuario().getLocale());

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.manager.UnidadAdministrativaManager
	 * #getUnidadAdministrativaByCodigo
	 * (es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO, java.lang.String)
	 */
	public UnidadAdministrativaVO getUnidadAdministrativaByCodigo(
			UsuarioVO usuario, String codigoUnidad) {
		UnidadAdministrativaVO result = null;

		// obtenemos la unidadAdministrativa segun su codigo
		result = getUnidadAdministrativaDAO().findUnidadByCode(
				usuario.getConfiguracionUsuario().getLocale(), codigoUnidad);

		return result;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.manager.UnidadAdministrativaManager
	 * #findUnidadAdministrativaByTipo
	 * (es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 * es.ieci.tecdoc.isicres.
	 * api.business.vo.CriterioBusquedaUnidadAdministrativaByTipoVO)
	 */
	public List findUnidadAdministrativaByTipo(UsuarioVO usuario,
			CriterioBusquedaUnidadAdministrativaByTipoVO criterio) {
		List result = null;

		// obtenemos el listado de unidades administrativas segun el criterio
		result = getUnidadAdministrativaDAO().findUnidadesAdmByTipo(
				usuario.getConfiguracionUsuario().getLocale(), criterio);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.manager.UnidadAdministrativaManager
	 * #findCountUnidadAdministrativaByTipo
	 * (es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 * es.ieci.tecdoc.isicres.
	 * api.business.vo.CriterioBusquedaUnidadAdministrativaByTipoVO)
	 */
	public int findCountUnidadAdministrativaByTipo(UsuarioVO usuario,
			CriterioBusquedaUnidadAdministrativaByTipoVO criterio) {
		int result = 0;

		// obtenemos el numero de unidades administrativas segun el criterio
		result = getUnidadAdministrativaDAO().findCountUnidadesAdmByTipo(
				usuario.getConfiguracionUsuario().getLocale(), criterio);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.manager.UnidadAdministrativaManager
	 * #findUnidadAdministrativaByUnidad
	 * (es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 * es.ieci.tecdoc.isicres.
	 * api.business.vo.CriterioBusquedaUnidadAdministrativaByUnidadVO)
	 */
	public List findUnidadAdministrativaByUnidad(UsuarioVO usuario,
			CriterioBusquedaUnidadAdministrativaByUnidadVO criterio) {
		List result = null;

		// obtenemos el listado de unidades administrativas segun el criterio
		result = getUnidadAdministrativaDAO().findUnidadesAdmByUnidad(
				usuario.getConfiguracionUsuario().getLocale(), criterio);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.manager.UnidadAdministrativaManager
	 * #findCountUnidadAdministrativaByUnidad
	 * (es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 * es.ieci.tecdoc.isicres.
	 * api.business.vo.CriterioBusquedaUnidadAdministrativaByUnidadVO)
	 */
	public int findCountUnidadAdministrativaByUnidad(UsuarioVO usuario,
			CriterioBusquedaUnidadAdministrativaByUnidadVO criterio) {
		int result = 0;

		// obtenemos el numero de unidades administrativas segun el criterio
		result = getUnidadAdministrativaDAO().findCountUnidadesAdmByUnidad(
				usuario.getConfiguracionUsuario().getLocale(), criterio);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.manager.UnidadAdministrativaManager
	 * #findUnidadAdministrativaByCondition
	 * (es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 * es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaWhereSqlVO)
	 */
	public List findUnidadAdministrativaByCondition(UsuarioVO usuario,
			CriterioBusquedaWhereSqlVO criterio) {
		List result = null;

		// obtenemos el listado de las unidades administrativas segun el
		// criterio
		result = getUnidadAdministrativaDAO().findUnidadesAdmWhereSQL(
				usuario.getConfiguracionUsuario().getLocale(), criterio);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.manager.UnidadAdministrativaManager
	 * #findCountUnidadAdministrativaByCondition
	 * (es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 * es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaWhereSqlVO)
	 */
	public int findCountUnidadAdministrativaByCondition(UsuarioVO usuario,
			CriterioBusquedaWhereSqlVO criterio) {
		int result = 0;

		// obtenemos el numero de unidades administrativas segun el criterio de
		// busqueda
		result = getUnidadAdministrativaDAO().findCountUnidadesAdmWhereSQL(
				usuario.getConfiguracionUsuario().getLocale(), criterio);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.manager.UnidadAdministrativaManager
	 * #findUnidadById(java.util.Locale, java.lang.Integer)
	 */
	public UnidadAdministrativaVO findUnidadById(Locale locale, Integer idUnidad) {
		UnidadAdministrativaVO result = null;

		// obtenemos la unidad administrativa segun su id
		result = getUnidadAdministrativaDAO().findUnidadById(locale, idUnidad);

		return result;
	}

	public UnidadAdministrativaDAO getUnidadAdministrativaDAO() {
		return unidadAdministrativaDAO;
	}

	public void setUnidadAdministrativaDAO(
			UnidadAdministrativaDAO unidadAdministrativa) {
		this.unidadAdministrativaDAO = unidadAdministrativa;
	}

	// Members
	protected UnidadAdministrativaDAO unidadAdministrativaDAO;

	private static final Logger logger = Logger
			.getLogger(UnidadAdministrativaManagerImpl.class);

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.UnidadAdministrativaManager#findTipoUnidadesAdminByCode(java.lang.String, java.util.Locale, es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoUnidadAdministrativaVO)
	 */
	@Override
	public TipoUnidadAdministrativaVO getTipoUnidadesAdminByCode(String code, Locale locale,
			CriterioBusquedaTipoUnidadAdministrativaVO criterio) {
		TipoUnidadAdministrativaVO tipoUnidad = null;
		tipoUnidad = getUnidadAdministrativaDAO().getTipoUnidadesAdminByCode(code, locale, criterio);
		return tipoUnidad;
	}

}
