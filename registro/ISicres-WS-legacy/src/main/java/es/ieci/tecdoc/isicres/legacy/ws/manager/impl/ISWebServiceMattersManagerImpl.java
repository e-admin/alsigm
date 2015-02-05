package es.ieci.tecdoc.isicres.legacy.ws.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.manager.LoginManager;
import es.ieci.tecdoc.isicres.api.business.manager.TipoAsuntoManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseCriterioBusquedaVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoAsuntoSqlVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoAsuntoVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceMattersManager;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.ListOfTipoAsuntoVOToWSMatterTypesResponseMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.TipoAsuntoVOToWSMatterTypeMapper;
import es.ieci.tecdoc.isicres.legacy.ws.service.impl.adapter.UsuarioVOBuilder;
import es.ieci.tecdoc.isicres.ws.legacy.service.matters.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.matters.WSMatterType;
import es.ieci.tecdoc.isicres.ws.legacy.service.matters.WSMatterTypesResponse;

/**
 *
 * @author IECISA
 *
 */
public class ISWebServiceMattersManagerImpl implements
		ISWebServiceMattersManager {

	public ISWebServiceMattersManagerImpl(){
		FIRST_COLLECTIONS_INIT_VALUE = ISicresConfigurationHelper
				.getFirstCollectionsInitValue();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seees.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceMattersManager#
	 * loadMatterTypes(int, int,
	 * es.ieci.tecdoc.isicres.ws.legacy.service.matters.Security)
	 */
	@SuppressWarnings("unchecked")
	public WSMatterTypesResponse loadMatterTypes(int initValue, int size,
			Security security) {

		Assert.isTrue(initValue >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
		Assert.isTrue(size >= 0,
				"Indique un número de asuntos a devolver positivo.");

		//Si esta configurado para que comience por 1 hay que restarle uno
		if (FIRST_COLLECTIONS_INIT_VALUE==1){
			initValue--;
		}
		// generamos un criterio de busqueda
		BaseCriterioBusquedaVO criterioBusqueda = new BaseCriterioBusquedaVO(
				new Long(size), new Long(initValue));

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		// Logeamos al usuario
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		// obtenemos el contador de valores de la consulta
		int totalCount = getTipoAsuntoManager().findAllCount(usuario);

		List<TipoAsuntoVO> listado = new ArrayList<TipoAsuntoVO>();
		if (totalCount > 0) {
			// obtenemos el listado
			listado = getTipoAsuntoManager().findAll(usuario, criterioBusqueda);

		}

		WSMatterTypesResponse result = (WSMatterTypesResponse) new ListOfTipoAsuntoVOToWSMatterTypesResponseMapper(
				totalCount).map(listado);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seees.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceMattersManager#
	 * matterTypesFromCondition(java.lang.String, int, int,
	 * es.ieci.tecdoc.isicres.ws.legacy.service.matters.Security)
	 */
	@SuppressWarnings("unchecked")
	public WSMatterTypesResponse matterTypesFromCondition(String condition,
			int initValue, int size, Security security) {

		Assert.isTrue(initValue >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
		Assert.isTrue(size >= 0,
				"Indique un número de asuntos a devolver positivo.");

		//Si esta configurado para que comience por 1 hay que restarle uno
		if (FIRST_COLLECTIONS_INIT_VALUE==1){
			initValue--;
		}

		// formamos el criterio de busqueda
		CriterioBusquedaTipoAsuntoSqlVO criterioBusqueda = new CriterioBusquedaTipoAsuntoSqlVO(
				new Long(size), new Long(initValue), condition);

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		// Logeamos al usuario
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		// obtenemos el contador de valores de la consulta
		int totalCount = getTipoAsuntoManager()
				.findTipoAsuntoByCriterioWhereSqlCount(usuario,
						criterioBusqueda);

		List<TipoAsuntoVO> listado = new ArrayList<TipoAsuntoVO>();
		if (totalCount > 0) {

			// obtenemos el listado de la consulta
			listado = getTipoAsuntoManager().findTipoAsuntoByCriterioWhereSql(
					usuario, criterioBusqueda);

		}

		WSMatterTypesResponse result = (WSMatterTypesResponse) new ListOfTipoAsuntoVOToWSMatterTypesResponseMapper(
				totalCount).map(listado);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seees.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceMattersManager#
	 * validateMatterTypeCode(java.lang.String,
	 * es.ieci.tecdoc.isicres.ws.legacy.service.matters.Security)
	 */
	public WSMatterType validateMatterTypeCode(String matterTypeCode,
			Security security) {

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		// Logeamos al usuario
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		// obtenemos la validacion del asunto
		TipoAsuntoVO tipoAsunto = getTipoAsuntoManager().getTipoAsuntoByCodigo(
				usuario, matterTypeCode);

		WSMatterType result = (null != tipoAsunto) ? (WSMatterType) new TipoAsuntoVOToWSMatterTypeMapper()
		.map(tipoAsunto)
		: null;

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	public TipoAsuntoManager getTipoAsuntoManager() {
		return tipoAsuntoManager;
	}

	public LoginManager getLoginManager() {
		return loginManager;
	}

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

	public void setTipoAsuntoManager(TipoAsuntoManager tipoAsuntoManager) {
		this.tipoAsuntoManager = tipoAsuntoManager;
	}

	public UsuarioVOBuilder getUsuarioBuilder() {
		return usuarioBuilder;
	}

	public void setUsuarioBuilder(UsuarioVOBuilder usuarioAdapter) {
		this.usuarioBuilder = usuarioAdapter;
	}

	// Members
	protected TipoAsuntoManager tipoAsuntoManager;

	protected LoginManager loginManager;

	protected UsuarioVOBuilder usuarioBuilder;

	private static int FIRST_COLLECTIONS_INIT_VALUE= 1;
}
