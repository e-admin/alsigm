package es.ieci.tecdoc.isicres.api.business.manager.impl;

import java.util.List;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.api.business.dao.OficinaDAO;
import es.ieci.tecdoc.isicres.api.business.dao.TipoAsuntoDAO;
import es.ieci.tecdoc.isicres.api.business.manager.TipoAsuntoManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseCriterioBusquedaVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoAsuntoSqlVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoAsuntoVO;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoAsuntoVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public class TipoAsuntoManagerImpl extends TipoAsuntoManager {

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.manager.TipoAsuntoManager#
	 * getTipoAsuntoByCodigo(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 * java.lang.String)
	 */
	public TipoAsuntoVO getTipoAsuntoByCodigo(UsuarioVO usuario,
			String codigoTipo) {
		TipoAsuntoVO result = null;

		// Obtenemos la oficina del usuario
		OficinaVO oficina = getOficinaDAO().getOficinaByCodigo(
				usuario.getConfiguracionUsuario().getLocale(),
				usuario.getConfiguracionUsuario().getOficina()
						.getCodigoOficina());
		if (oficina != null) {
			// obtiene los tipos de asuntos segun la consulta realizada
			result = getTipoAsuntoDAO().getTipoAsuntoByCodigo(
					usuario.getConfiguracionUsuario().getLocale(),
					oficina.getId(), codigoTipo);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.manager.TipoAsuntoManager#findAll
	 * (es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 * es.ieci.tecdoc.isicres.api.business.vo.BaseCriterioBusquedaVO)
	 */
	public List findAll(UsuarioVO usuario,
			BaseCriterioBusquedaVO baseCriterioBusqueda) {
		List result = null;

		// Obtenemos la oficina del usuario
		OficinaVO oficina = getOficinaDAO().getOficinaByCodigo(
				usuario.getConfiguracionUsuario().getLocale(),
				usuario.getConfiguracionUsuario().getOficina()
						.getCodigoOficina());
		if (oficina != null) {
			// obtenemos el listado
			result = getTipoAsuntoDAO().findAllAsuntos(
					usuario.getConfiguracionUsuario().getLocale(),
					oficina.getId(), baseCriterioBusqueda);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.manager.TipoAsuntoManager#findAllCount
	 * (es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO)
	 */
	public int findAllCount(UsuarioVO usuario) {
		int result = 0;

		// Obtenemos la oficina del usuario
		OficinaVO oficina = getOficinaDAO().getOficinaByCodigo(
				usuario.getConfiguracionUsuario().getLocale(),
				usuario.getConfiguracionUsuario().getOficina()
						.getCodigoOficina());

		// obtenemos el numero de registros para mostrar
		result = getTipoAsuntoDAO().findAllAsuntosCount(
				usuario.getConfiguracionUsuario().getLocale(), oficina.getId());

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.manager.TipoAsuntoManager#
	 * findTipoAsuntoByCriterio
	 * (es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 * es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoAsuntoVO)
	 */
	public List findTipoAsuntoByCriterio(UsuarioVO usuario,
			CriterioBusquedaTipoAsuntoVO criterioBusqueda) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.manager.TipoAsuntoManager#
	 * findTipoAsuntoByCriterioWhereSql
	 * (es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 * es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoAsuntoSqlVO)
	 */
	public List findTipoAsuntoByCriterioWhereSql(UsuarioVO usuario,
			CriterioBusquedaTipoAsuntoSqlVO criterioBusqueda) {

		List result = null;

		// Obtenemos la oficina del usuario
		OficinaVO oficina = getOficinaDAO().getOficinaByCodigo(
				usuario.getConfiguracionUsuario().getLocale(),
				usuario.getConfiguracionUsuario().getOficina()
						.getCodigoOficina());

		if (oficina != null) {
			// obtenemos la lista con los asuntos validos para ese criterio
			result = getTipoAsuntoDAO().findTipoAsuntoByCriterioWhereSql(
					usuario.getConfiguracionUsuario().getLocale(),
					oficina.getId(), criterioBusqueda);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.manager.TipoAsuntoManager#
	 * findTipoAsuntoByCriterioWhereSqlCount
	 * (es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 * es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoAsuntoSqlVO)
	 */
	public int findTipoAsuntoByCriterioWhereSqlCount(UsuarioVO usuario,
			CriterioBusquedaTipoAsuntoSqlVO criterioBusqueda) {

		int result = 0;

		// Obtenemos la oficina del usuario
		OficinaVO oficina = getOficinaDAO().getOficinaByCodigo(
				usuario.getConfiguracionUsuario().getLocale(),
				usuario.getConfiguracionUsuario().getOficina()
						.getCodigoOficina());

		if (oficina != null) {
			// obtenemos la lista con los asuntos validos para ese criterio
			result = getTipoAsuntoDAO().findTipoAsuntoByCriterioWhereSqlCount(
					usuario.getConfiguracionUsuario().getLocale(),
					oficina.getId(), criterioBusqueda.getSql());
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.manager.TipoAsuntoManager#
	 * validateTipoAsuntoByCodigo
	 * (es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO, java.lang.String)
	 */
	public boolean validateTipoAsuntoByCodigo(UsuarioVO usuario,
			String codigoAsunto) {

		boolean result = false;
		TipoAsuntoVO tipoAsunto = null;

		// Obtenemos la oficina del usuario
		OficinaVO oficina = getOficinaDAO().getOficinaByCodigo(
				usuario.getConfiguracionUsuario().getLocale(),
				usuario.getConfiguracionUsuario().getOficina()
						.getCodigoOficina());

		if (oficina != null) {
			// obtiene los tipos de asuntos segun la consulta realizada
			tipoAsunto = getTipoAsuntoDAO().getTipoAsuntoAllOficOrIdOfic(
					usuario.getConfiguracionUsuario().getLocale(),
					oficina.getId(), codigoAsunto);

			if (tipoAsunto != null) {
				// si el objeto devuelto es distinto de null es un asunto valido
				result = true;
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.manager.TipoAsuntoManager#
	 * getTipoAsuntoById(java.util.Locale, java.lang.String)
	 */
	public TipoAsuntoVO getTipoAsuntoById(UsuarioVO usuario, String idAsunto) {
		TipoAsuntoVO result = null;

		// obtenemos el tipo de asunto segun el id del asunto
		result = getTipoAsuntoDAO().getTipoAsuntoById(
				usuario.getConfiguracionUsuario().getLocale(), idAsunto);

		return result;
	}

	public TipoAsuntoDAO getTipoAsuntoDAO() {
		return tipoAsuntoDAO;
	}

	public void setTipoAsuntoDAO(TipoAsuntoDAO tipoAsuntoDAO) {
		this.tipoAsuntoDAO = tipoAsuntoDAO;
	}

	public OficinaDAO getOficinaDAO() {
		return oficinaDAO;
	}

	public void setOficinaDAO(OficinaDAO oficinaDAO) {
		this.oficinaDAO = oficinaDAO;
	}

	// Members
	protected TipoAsuntoDAO tipoAsuntoDAO;

	protected OficinaDAO oficinaDAO;

	private static final Logger logger = Logger
			.getLogger(TipoAsuntoManagerImpl.class);

}
