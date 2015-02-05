package es.ieci.tecdoc.isicres.legacy.ws.manager.impl;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.manager.LoginManager;
import es.ieci.tecdoc.isicres.api.business.manager.RegistroManager;
import es.ieci.tecdoc.isicres.api.business.vo.CampoGenericoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaRegistroSqlVO;
import es.ieci.tecdoc.isicres.api.business.vo.DocumentoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.LibroEntradaVO;
import es.ieci.tecdoc.isicres.api.business.vo.LibroSalidaVO;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaExternoVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroSalidaExternoVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroSalidaVO;
import es.ieci.tecdoc.isicres.api.business.vo.ResultadoBusquedaRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceRegistersManager;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input.ArrayOfWSParamDocumentToListOfDocumentoRegistroVOMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input.ArrayOfWSParamFieldToListOfCampoGenericoRegistroVOMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input.WSDocumentsResponseToListOfDocumentoRegistroVOMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input.WSLoadInputRegistersExToCriterioBusquedaRegistroSqlVOMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input.WSLoadOutputRegistersExToCriterioBusquedaRegistroSqlVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input.WSParamInputRegisterExToRegistroEntradaExternoVOMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input.WSParamInputRegisterExToRegistroEntradaVOMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input.WSParamOutputRegisterExToRegistroSalidaExternoVOMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input.WSParamOutputRegisterExToRegistroSalidaVOMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.RegistroEntradaVOToWSInputRegisterMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.RegistroEntradaVOToWSRegisterMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.RegistroSalidaVOToWSOutputRegisterMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.RegistroSalidaVOToWSRegisterMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.ResultadoBusquedaRegistroVOToWSInputRegistersResponseMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.ResultadoBusquedaRegistroVOToWSLoadInputRegistersExResponseMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.ResultadoBusquedaRegistroVOToWSLoadOutputRegistersExResponseMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.ResultadoBusquedaRegistroVOToWSOutputRegistersResponseMapper;
import es.ieci.tecdoc.isicres.legacy.ws.service.impl.adapter.UsuarioVOBuilder;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSParamDocument;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSParamField;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSDocumentsResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSInputRegister;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSInputRegistersResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSLoadInputRegistersEx;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSLoadInputRegistersExResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSLoadOutputRegistersEx;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSLoadOutputRegistersExResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSOutputRegister;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSOutputRegistersResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamInputRegisterEx;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamOutputRegisterEx;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSRegister;

/**
 *
 * @author IECISA
 *
 */
public class ISWebServiceRegistersManagerImpl implements
		ISWebServiceRegistersManager {

	public ISWebServiceRegistersManagerImpl(){
		FIRST_COLLECTIONS_INIT_VALUE = ISicresConfigurationHelper
				.getFirstCollectionsInitValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public WSDocumentsResponse attachPage(int bookIdentification,
			int registerIdentification, ArrayOfWSParamDocument documents,
			Security security) {

		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		// Mapeo de WS Objects a objetos del dominio
		IdentificadorRegistroVO ir = new IdentificadorRegistroVO(Integer
				.toString(registerIdentification), Integer
				.toString(bookIdentification));

		List<DocumentoRegistroVO> docsRegistro = (List<DocumentoRegistroVO>) new ArrayOfWSParamDocumentToListOfDocumentoRegistroVOMapper()
				.map(documents);

		// Llamada al servicio
		List<DocumentoRegistroVO> responseDocs = getRegistroManager()
				.attachDocuments(ir, docsRegistro, usuario);

		// Mapeo de la respuesta a WS Objects
		WSDocumentsResponse result = (WSDocumentsResponse) new WSDocumentsResponseToListOfDocumentoRegistroVOMapper()
				.map(responseDocs);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public void attachPageEx(int bookIdentification,
			int registerIdentification, ArrayOfWSParamDocument documents,
			Security security) {

		AuditInfoHelper.setInfoAuditContextHolder();

		attachPage(bookIdentification, registerIdentification, documents,
				security);
	}

	/**
	 * {@inheritDoc}
	 */
	public void cancelInputRegister(int bookIdentification,
			int registerIdentification, Security security) {

		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		IdentificadorRegistroVO id = new IdentificadorRegistroVO(String
				.valueOf(registerIdentification), String
				.valueOf(bookIdentification));

		getRegistroManager().cancelRegistroEntradaById(usuario, id);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);
	}

	/**
	 * {@inheritDoc}
	 */
	public void cancelOutputRegister(int bookIdentification,
			int registerIdentification, Security security) {

		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		IdentificadorRegistroVO id = new IdentificadorRegistroVO(String
				.valueOf(registerIdentification), String
				.valueOf(bookIdentification));

		getRegistroManager().cancelRegistroSalidaById(usuario, id);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);
	}

	/**
	 * {@inheritDoc}
	 */
	public byte[] getPageByIndex(int bookIdentification,
			int registerIdentification, int documentIndex, int pageIndex,
			Security security) {

		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		IdentificadorRegistroVO identificadorRegistro = new IdentificadorRegistroVO(
				String.valueOf(registerIdentification), String
						.valueOf(bookIdentification));

		byte[] result = getRegistroManager().getPaginaByIndex(identificadorRegistro,
				documentIndex, pageIndex, usuario);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public byte[] getPageById(int bookIdentification,
			int registerIdentification, int documentId, int pageId,
			Security security) {

		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		IdentificadorRegistroVO identificadorRegistro = new IdentificadorRegistroVO(
				String.valueOf(registerIdentification), String
						.valueOf(bookIdentification));

		byte[] result = getRegistroManager().getPaginaById(identificadorRegistro,
				String.valueOf(documentId), String.valueOf(pageId), usuario);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public WSRegister importInputRegister(int bookIdentification,
			String regNumber, XMLGregorianCalendar regDate, String user,
			XMLGregorianCalendar sysDate, String office,
			WSParamInputRegisterEx datas, Security security) {

		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		// Mapeo de datos extendidos
		RegistroEntradaExternoVO registro = (RegistroEntradaExternoVO) new WSParamInputRegisterExToRegistroEntradaExternoVOMapper()
				.map(datas);

		// Datos básicos del registro
		registro.setIdLibro(String.valueOf(bookIdentification));
		registro.setNumeroRegistro(regNumber);
		if (null != regDate) {
			registro.setFechaRegistro(XMLGregorianCalendarHelper
					.toDate(regDate));
		}
		if (null != sysDate) {
			registro
					.setFechaAlta(XMLGregorianCalendarHelper.toDate(sysDate));
		}

		// Oficina
		OficinaVO oficinaRegistro = new OficinaVO();
		oficinaRegistro.setCodigoOficina(office);
		registro.setOficinaRegistro(oficinaRegistro);

		// Usuario
		UsuarioVO usuarioRegistro = new UsuarioVO();
		usuarioRegistro.setLoginName(user);
		registro.setUsuarioRegistro(usuarioRegistro);

		RegistroEntradaVO re = getRegistroManager().importRegistroEntrada(
				usuario, registro);

		// Mapeo de respuesta
		WSRegister result = (WSRegister) new RegistroEntradaVOToWSRegisterMapper().map(re);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public WSRegister importOutputRegister(int bookIdentification,
			String regNumber, XMLGregorianCalendar regDate, String user,
			XMLGregorianCalendar sysDate, String office,
			WSParamOutputRegisterEx datas, Security security) {

		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		RegistroSalidaExternoVO registro = (RegistroSalidaExternoVO) new WSParamOutputRegisterExToRegistroSalidaExternoVOMapper()
				.map(datas);

		registro.setIdLibro(String.valueOf(bookIdentification));
		registro.setNumeroRegistro(regNumber);
		if (null != regDate) {
			registro.setFechaRegistro(XMLGregorianCalendarHelper
					.toDate(regDate));
		}
		if (null != sysDate) {
			registro
					.setFechaAlta(XMLGregorianCalendarHelper.toDate(sysDate));
		}

		OficinaVO oficinaRegistro = new OficinaVO();
		oficinaRegistro.setCodigoOficina(office);
		registro.setOficinaRegistro(oficinaRegistro);

		UsuarioVO usuarioRegistro = new UsuarioVO();
		usuarioRegistro.setLoginName(user);
		registro.setUsuarioRegistro(usuarioRegistro);

		RegistroSalidaVO rs = getRegistroManager().importRegistroSalida(
				usuario, registro);

		// Mapeo de resultado
		WSRegister result = (WSRegister) new RegistroSalidaVOToWSRegisterMapper().map(rs);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public WSInputRegister loadInputRegisterFromId(int bookIdentification,
			int registerIdentification, Security security) {

		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		IdentificadorRegistroVO id = new IdentificadorRegistroVO(String
				.valueOf(registerIdentification), String
				.valueOf(bookIdentification));

		RegistroEntradaVO re = getRegistroManager().findRegistroEntradaById(
				usuario, id);

		WSInputRegister result =  (WSInputRegister) new RegistroEntradaVOToWSInputRegisterMapper()
				.map(re);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public WSInputRegistersResponse loadInputRegisters(int bookIdentification,
			String condition, int initValue, int size, Security security) {

		Assert.isTrue(initValue >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
		Assert.isTrue(size >= 0,
				"Indique un número de registros a devolver positivo.");

		//Si esta configurado para que comience por 1 hay que restarle uno
		if (FIRST_COLLECTIONS_INIT_VALUE==1){
			initValue--;
		}

		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		LibroEntradaVO libro = new LibroEntradaVO();
		libro.setId(String.valueOf(bookIdentification));

		CriterioBusquedaRegistroSqlVO criterioBusqueda = new CriterioBusquedaRegistroSqlVO();
		criterioBusqueda.setSql(condition);
		criterioBusqueda.setOffset(Long.valueOf(initValue));
		criterioBusqueda.setLimit(Long.valueOf(size));

		ResultadoBusquedaRegistroVO resultadoBusqueda = getRegistroManager()
				.findAllRegistroEntradaByCriterioWhereSql(usuario, libro,
						criterioBusqueda);

		// Se hace la búsqueda para recuperar el total de registros
		WSInputRegistersResponse result = (WSInputRegistersResponse) new ResultadoBusquedaRegistroVOToWSInputRegistersResponseMapper()
		.map(resultadoBusqueda);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public WSLoadInputRegistersExResponse loadInputRegistersExOp(
			WSLoadInputRegistersEx parameters, Security security) {

		Assert.isTrue(parameters.getInitValue() >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
		Assert.isTrue(parameters.getSize() >= 0,
				"Indique un número de registros a devolver positivo.");


		//Si esta configurado para que comience por 1 hay que restarle uno
		if (FIRST_COLLECTIONS_INIT_VALUE==1){
			parameters.setInitValue(parameters.getInitValue()-1);
		}

		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		CriterioBusquedaRegistroSqlVO criterioBusqueda = (CriterioBusquedaRegistroSqlVO) new WSLoadInputRegistersExToCriterioBusquedaRegistroSqlVOMapper()
				.map(parameters);

		ResultadoBusquedaRegistroVO resultadoBusquedaRegistroVO = getRegistroManager()
				.findAllRegistroEntradaForUserByCriterioWhereSql(usuario,
						criterioBusqueda);

		WSLoadInputRegistersExResponse result = (WSLoadInputRegistersExResponse) new ResultadoBusquedaRegistroVOToWSLoadInputRegistersExResponseMapper()
				.map(resultadoBusquedaRegistroVO);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public WSOutputRegister loadOutputRegisterFromId(int bookIdentification,
			int registerIdentification, Security security) {

		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		RegistroSalidaVO registro = getRegistroManager()
				.findRegistroSalidaById(
						usuario,
						new IdentificadorRegistroVO(String
								.valueOf(registerIdentification), String
								.valueOf(bookIdentification)));

		WSOutputRegister result = (WSOutputRegister) new RegistroSalidaVOToWSOutputRegisterMapper()
				.map(registro);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public WSOutputRegistersResponse loadOutputRegisters(
			int bookIdentification, String condition, int initValue, int size,
			Security security) {

		Assert.isTrue(initValue >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
		Assert.isTrue(size >= 0,
				"Indique un número de registros a devolver positivo.");

		if (FIRST_COLLECTIONS_INIT_VALUE==1){
			initValue--;
		}

		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		LibroSalidaVO libro = new LibroSalidaVO();
		libro.setId(String.valueOf(bookIdentification));
		CriterioBusquedaRegistroSqlVO criterioBusqueda = new CriterioBusquedaRegistroSqlVO();
		criterioBusqueda.setSql(condition);
		criterioBusqueda.setLimit(Long.valueOf(size));
		criterioBusqueda.setOffset(Long.valueOf(initValue));

		ResultadoBusquedaRegistroVO resultadoBusquedaRegistroVO = getRegistroManager()
				.findAllRegistroSalidaByCriterioWhereSql(usuario, libro,
						criterioBusqueda);

		WSOutputRegistersResponse result = (WSOutputRegistersResponse) new ResultadoBusquedaRegistroVOToWSOutputRegistersResponseMapper()
				.map(resultadoBusquedaRegistroVO);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public WSLoadOutputRegistersExResponse loadOutputRegistersExOp(
			WSLoadOutputRegistersEx parameters, Security security) {

		Assert.isTrue(parameters.getInitValue() >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
		Assert.isTrue(parameters.getSize() >= 0,
				"Indique un número de registros a devolver positivo.");

		//El primer elemento empieza por 1 por lo que tenemos que restar 1
		if (FIRST_COLLECTIONS_INIT_VALUE==1){
			parameters.setInitValue(parameters.getInitValue()-1);
		}

		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		CriterioBusquedaRegistroSqlVO criterioBusqueda = (CriterioBusquedaRegistroSqlVO) new WSLoadOutputRegistersExToCriterioBusquedaRegistroSqlVO()
				.map(parameters);

		ResultadoBusquedaRegistroVO resultadoBusquedaRegistroVO = getRegistroManager()
				.findAllRegistroSalidaForUserByCriterioWhereSql(usuario,
						criterioBusqueda);

		WSLoadOutputRegistersExResponse result = (WSLoadOutputRegistersExResponse) new ResultadoBusquedaRegistroVOToWSLoadOutputRegistersExResponseMapper()
				.map(resultadoBusquedaRegistroVO);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public WSRegister newInputRegister(int bookIdentification,
			WSParamInputRegisterEx datas, Security security) {

		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		RegistroEntradaVO registro = (RegistroEntradaVO) new WSParamInputRegisterExToRegistroEntradaVOMapper()
				.map(datas);
		registro.setIdLibro(String.valueOf(bookIdentification));

		RegistroEntradaVO re = getRegistroManager().createRegistroEntrada(
				usuario, registro);

		WSRegister result = (WSRegister) new RegistroEntradaVOToWSRegisterMapper().map(re);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public WSRegister newOutputRegister(int bookIdentification,
			WSParamOutputRegisterEx datas, Security security) {

		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		RegistroSalidaVO registro = (RegistroSalidaVO) new WSParamOutputRegisterExToRegistroSalidaVOMapper()
				.map(datas);
		registro.setIdLibro(String.valueOf(bookIdentification));

		RegistroSalidaVO rs = getRegistroManager().createRegistroSalida(
				usuario, registro);

		WSRegister result = (WSRegister) new RegistroSalidaVOToWSRegisterMapper().map(rs);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public void updateInputRegister(int bookIdentification,
			int registerIdentification, ArrayOfWSParamField fields,
			Security security) {

		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		List<CampoGenericoRegistroVO> camposGenericos = (List<CampoGenericoRegistroVO>) new ArrayOfWSParamFieldToListOfCampoGenericoRegistroVOMapper()
				.map(fields);

		getRegistroManager().updateRegistroEntrada(
				usuario,
				new IdentificadorRegistroVO(String
						.valueOf(registerIdentification), String
						.valueOf(bookIdentification)), camposGenericos);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public void updateOutputRegister(int bookIdentification,
			int registerIdentification, ArrayOfWSParamField fields,
			Security security) {

		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		List<CampoGenericoRegistroVO> camposGenericos = (List<CampoGenericoRegistroVO>) new ArrayOfWSParamFieldToListOfCampoGenericoRegistroVOMapper()
				.map(fields);
		getRegistroManager().updateRegistroSalida(
				usuario,
				new IdentificadorRegistroVO(String
						.valueOf(registerIdentification), String
						.valueOf(bookIdentification)), camposGenericos);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

	}

	public RegistroManager getRegistroManager() {
		return registroManager;
	}

	public void setRegistroManager(RegistroManager registroManager) {
		this.registroManager = registroManager;
	}

	public LoginManager getLoginManager() {
		return loginManager;
	}

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

	public UsuarioVOBuilder getUsuarioBuilder() {
		return usuarioBuilder;
	}

	public void setUsuarioBuilder(UsuarioVOBuilder usuarioAdapter) {
		this.usuarioBuilder = usuarioAdapter;
	}

	// Members
	protected RegistroManager registroManager;

	protected LoginManager loginManager;

	protected UsuarioVOBuilder usuarioBuilder;

	private static int FIRST_COLLECTIONS_INIT_VALUE= 1;

}
