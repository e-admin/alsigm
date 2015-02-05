package es.ieci.tecdoc.isicres.legacy.ws.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.dao.UnidadAdministrativaDAO;
import es.ieci.tecdoc.isicres.api.business.dao.legacy.impl.UnidadAdministrativaLegacyDAOImpl;
import es.ieci.tecdoc.isicres.api.business.manager.LoginManager;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoUnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaUnidadAdministrativaByTipoVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaUnidadAdministrativaByUnidadVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaWhereSqlVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoUnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceUnitsManager;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.ListOfTipoUnidadesAdministrativaVOToWSUnitTypesResponseMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.ListOfUnidadAdministrativaVOToWSUnitsResponseMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.UnidadAdministrativaVOToWsUnitMapper;
import es.ieci.tecdoc.isicres.legacy.ws.service.impl.adapter.UsuarioVOBuilder;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.WSUnit;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.WSUnitTypesResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.WSUnitsResponse;

public class ISWebServiceUnitsManagerImpl implements ISWebServiceUnitsManager {

	public ISWebServiceUnitsManagerImpl(){
		FIRST_COLLECTIONS_INIT_VALUE = ISicresConfigurationHelper
				.getFirstCollectionsInitValue();
	}
	/*
	 * (non-Javadoc)
	 *
	 * @seees.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceUnitsManager#
	 * loadUnitTypes(int, int,
	 * es.ieci.tecdoc.isicres.ws.legacy.service.units.Security)
	 */
	@SuppressWarnings("unchecked")
	public WSUnitTypesResponse loadUnitTypes(int initValue, int size, Security security) {

		Assert.isTrue(initValue >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
		Assert.isTrue(size >= 0,
				"Indique un número positivo.");

		//Si esta configurado para que comience por 1 hay que restarle uno
		if (FIRST_COLLECTIONS_INIT_VALUE==1){
			initValue--;
		}

		List<TipoUnidadAdministrativaVO> listadoTipoUnidad = null;

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		// Logeamos al usuario
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		// obtenemos el numero de tipo de unidades administrativas
		// FIXME: ¿Esta llamada al Dao?
		int totalCount = getUnidadAdministrativaDAO().findCountAllTipoUnidadesAdmin(
						usuario.getConfiguracionUsuario().getLocale());
		if (totalCount > 0) {
			// creamos un objeto con el formato de la consulta
			CriterioBusquedaTipoUnidadAdministrativaVO criterio = new CriterioBusquedaTipoUnidadAdministrativaVO(
					new Long(size), new Long(initValue));

			// obtenemos el tipo de unidades administrativas segun la consulta
			listadoTipoUnidad = getUnidadAdministrativaDAO().findAllTipoUnidadesAdmin(
					usuario.getConfiguracionUsuario().getLocale(), criterio);
		}

		WSUnitTypesResponse result = (WSUnitTypesResponse) new ListOfTipoUnidadesAdministrativaVOToWSUnitTypesResponseMapper(
				totalCount).map(listadoTipoUnidad);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seees.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceUnitsManager#
	 * loadUnitsFromCondition(java.lang.String, int, int,
	 * es.ieci.tecdoc.isicres.ws.legacy.service.units.Security)
	 */
	@SuppressWarnings("unchecked")
	public WSUnitsResponse loadUnitsFromCondition(String condition, int initValue, int size,
			Security security) {

		Assert.isTrue(initValue >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
		Assert.isTrue(size >= 0,
				"Indique un número positivo.");

		//Si esta configurado para que comience por 1 hay que restarle uno
		if (FIRST_COLLECTIONS_INIT_VALUE==1){
			initValue--;
		}

		List<UnidadAdministrativaVO> listadoUnidadAdministrativaVO = new ArrayList<UnidadAdministrativaVO>();

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		// Logeamos al usuario
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		// creamos un objeto con el formato de la consulta
		CriterioBusquedaWhereSqlVO criterioBusqueda = new CriterioBusquedaWhereSqlVO(
				new Long(size), new Long(initValue), condition);

		// obtenemos el numero de elementos que cumplen el criterio de busqueda
		int totalCount = getUnidadAdministrativaDAO().findCountUnidadesAdmWhereSQL(
				usuario.getConfiguracionUsuario().getLocale(), criterioBusqueda);

		if (totalCount > 0) {
			// realizamos la consulta segun el criterio de busqueda
			listadoUnidadAdministrativaVO = getUnidadAdministrativaDAO().findUnidadesAdmWhereSQL(
					usuario.getConfiguracionUsuario().getLocale(), criterioBusqueda);
		}

		WSUnitsResponse result = (WSUnitsResponse) new ListOfUnidadAdministrativaVOToWSUnitsResponseMapper(
				totalCount).map(listadoUnidadAdministrativaVO);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seees.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceUnitsManager#
	 * loadUnitsFromType(java.lang.String, int, int,
	 * es.ieci.tecdoc.isicres.ws.legacy.service.units.Security)
	 */
	@SuppressWarnings("unchecked")
	public WSUnitsResponse loadUnitsFromType(String typeCode, int initValue, int size,
			Security security) {

		Assert.isTrue(initValue >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
		Assert.isTrue(size >= 0,
				"Indique un número positivo.");

		//Si esta configurado para que comience por 1 hay que restarle uno
		if (FIRST_COLLECTIONS_INIT_VALUE==1){
			initValue--;
		}

		List<UnidadAdministrativaVO> listadoUnidadAdministrativaVO = new ArrayList<UnidadAdministrativaVO>();

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		int totalCount = 0;
		// Logeamos al usuario
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		// Hay que obtener el identificador del tipo a partir de su código
		// para realizar la consulta
		CriterioBusquedaTipoUnidadAdministrativaVO criterio = new CriterioBusquedaTipoUnidadAdministrativaVO();
		
		//El api de Hibernate el parámetro maxResults es int, no long.		
		criterio.setLimit(Long.valueOf(Integer.MAX_VALUE));
		criterio.setOffset(0L);
		TipoUnidadAdministrativaVO tipoUnidad = getUnidadAdministrativaDAO()
				.getTipoUnidadesAdminByCode(typeCode,
						usuario.getConfiguracionUsuario().getLocale(), criterio);

		if (tipoUnidad != null) {
			String idTipoUnidad = tipoUnidad.getId();
		// creamos un objeto con el formato de la consulta
		CriterioBusquedaUnidadAdministrativaByTipoVO criterioBusqueda = new CriterioBusquedaUnidadAdministrativaByTipoVO(
					new Long(size), new Long(initValue), idTipoUnidad);

			// obtenemos el numero de elementos que cumplen el criterio de
			// busqueda
		// FIXME:llamada a Dao en capa erronea
			totalCount = getUnidadAdministrativaDAO().findCountUnidadesAdmByTipo(
					usuario.getConfiguracionUsuario().getLocale(), criterioBusqueda);

		if (totalCount > 0) {
			// realizamos la consulta segun el criterio de busqueda
			// FIXME:llamada a Dao en capa erronea
				listadoUnidadAdministrativaVO = getUnidadAdministrativaDAO().findUnidadesAdmByTipo(
						usuario.getConfiguracionUsuario().getLocale(), criterioBusqueda);
			}
		}

		WSUnitsResponse result = (WSUnitsResponse) new ListOfUnidadAdministrativaVOToWSUnitsResponseMapper(
				totalCount).map(listadoUnidadAdministrativaVO);
		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seees.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceUnitsManager#
	 * loadUnitsFromUnit(java.lang.String, int, int,
	 * es.ieci.tecdoc.isicres.ws.legacy.service.units.Security)
	 */
	@SuppressWarnings("unchecked")
	public WSUnitsResponse loadUnitsFromUnit(String unitCode, int initValue, int size,
			Security security) {

		Assert.isTrue(initValue >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
		Assert.isTrue(size >= 0,
				"Indique un número positivo.");

		//Si esta configurado para que comience por 1 hay que restarle uno
		if (FIRST_COLLECTIONS_INIT_VALUE==1){
			initValue--;
		}

		List<UnidadAdministrativaVO> unidadesAdministrativas = new ArrayList<UnidadAdministrativaVO>();

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		int totalCount = 0;
		// Logeamos al usuario
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		Locale locale = usuario.getConfiguracionUsuario().getLocale();
		UnidadAdministrativaVO unidadPadre = getUnidadAdministrativaDAO().findUnidadByCode(locale, unitCode);
		// creamos un objeto con el formato de la consulta
		if (unidadPadre !=null){
		CriterioBusquedaUnidadAdministrativaByUnidadVO criterioBusqueda = new CriterioBusquedaUnidadAdministrativaByUnidadVO(
				new Long(size), new Long(initValue), unidadPadre.getId());

		// obtenemos el numero de elementos que cumplen el criterio de busqueda
		// FIXME: ¿Por qué se llama al Dao directamente desde esta capa?
		totalCount = getUnidadAdministrativaDAO().findCountUnidadesAdmByUnidad(locale,
						criterioBusqueda);

		if (totalCount > 0) {

			// realizamos la consulta segun el criterio de busqueda
			// FIXME: ¿Por qué se llama al Dao directamente desde esta capa?
			unidadesAdministrativas = getUnidadAdministrativaDAO().findUnidadesAdmByUnidad(
					usuario.getConfiguracionUsuario().getLocale(), criterioBusqueda);
		}
		}

		WSUnitsResponse result = (WSUnitsResponse) new ListOfUnidadAdministrativaVOToWSUnitsResponseMapper(
				totalCount).map(unidadesAdministrativas);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seees.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceUnitsManager#
	 * validateUnitCode(java.lang.String,
	 * es.ieci.tecdoc.isicres.ws.legacy.service.units.Security)
	 */
	public WSUnit validateUnitCode(String unitCode, Security security) {
		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		// Logeamos al usuario
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		// realizamos la busqueda segun el codigo de la unidad
		UnidadAdministrativaVO unidadAdmin = getUnidadAdministrativaDAO().findUnidadByCode(
						usuario.getConfiguracionUsuario().getLocale(), unitCode);

		WSUnit result = (null != unidadAdmin) ? (WSUnit) new UnidadAdministrativaVOToWsUnitMapper()
				.map(unidadAdmin) : null;

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	public UnidadAdministrativaDAO getUnidadAdministrativaDAO() {
		return unidadAdministrativaDAO;
	}

	public void setUnidadAdministrativaDAO(UnidadAdministrativaDAO unidadAdministrativaDAO) {
		this.unidadAdministrativaDAO = unidadAdministrativaDAO;
	}

	public UsuarioVOBuilder getUsuarioBuilder() {
		return usuarioBuilder;
	}

	public void setUsuarioBuilder(UsuarioVOBuilder usuarioAdapter) {
		this.usuarioBuilder = usuarioAdapter;
	}

	public LoginManager getLoginManager() {
		return loginManager;
	}

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

	// Members
	// FIXME: Este Dao no debería estar aquí
	protected UnidadAdministrativaDAO unidadAdministrativaDAO = new UnidadAdministrativaLegacyDAOImpl();
	protected LoginManager loginManager;
	protected UsuarioVOBuilder usuarioBuilder;
	protected int FIRST_COLLECTIONS_INIT_VALUE= 1;

}
