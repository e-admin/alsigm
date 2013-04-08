package es.ieci.tecdoc.isicres.legacy.ws.manager.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager;
import es.ieci.tecdoc.isicres.api.business.manager.LoginManager;
import es.ieci.tecdoc.isicres.api.business.manager.RegistroManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaDistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.DistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.ImplicadoDistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.NullLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.ResultadoBusquedaDistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.enums.EstadoDistribucionEnum;
import es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceDistributionsManager;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.DistribucionVOToWSDistributionMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.ListOfDistribucionVOToWSDistributionsResponseMapper;
import es.ieci.tecdoc.isicres.legacy.ws.service.impl.adapter.UsuarioVOBuilder;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.WSAcceptDistributionEx;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.WSAcceptDistributionExResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.WSDistribution;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.WSDistributionsResponse;

/**
 *
 * @author IECISA
 *
 */
public class ISWebServiceDistributionsManagerImpl implements
		ISWebServiceDistributionsManager {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ISWebServiceDistributionsManagerImpl.class);

	private static int FIRST_COLLECTIONS_INIT_VALUE= 1;

	public ISWebServiceDistributionsManagerImpl() {
		FIRST_COLLECTIONS_INIT_VALUE = ISicresConfigurationHelper
				.getFirstCollectionsInitValue();
	}

	/**
	 * {@inheritDoc}
	 */
	public void acceptDistribution(String numReg, Security security) {

		Assert.isTrue(StringUtils.isNotEmpty(numReg),
				"Debe especificar un número de registro.");

		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		DistribucionVO distribucion = instantiateDistribution(numReg);

		getDistribucionManager().acceptDistribution(usuario, distribucion);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);
	}

	/**
	 * {@inheritDoc}
	 */
	public WSAcceptDistributionExResponse acceptDistributionExResponse(
			WSAcceptDistributionEx parameters, Security security) {

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		DistribucionVO distribucion = instantiateDistribution(parameters
				.getNumReg());

		BaseLibroVO libro = (parameters.getBookId() == 0) ? new NullLibroVO()
				: new BaseLibroVO(String.valueOf(parameters.getBookId()));

		getDistribucionManager().acceptDistribution(usuario, distribucion,
				libro);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		// Se devuelve un objeto vacío
		return new WSAcceptDistributionExResponse();
	}

	/**
	 * {@inheritDoc}
	 */
	public void archiveDistribution(String numReg, Security security) {

		Assert.isTrue(StringUtils.isNotEmpty(numReg),
				"Debe especificar un número de registro.");

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		DistribucionVO distribucion = instantiateDistribution(numReg);

		getDistribucionManager().archiveDistribution(usuario, distribucion);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public WSDistributionsResponse loadInputDistributions(int state,
			int initValue, int size, Security security) {



		Assert.notNull(EstadoDistribucionEnum.getEnum(state),
				"Valor para estado de las distribuciones no permitido.");
		Assert.isTrue(initValue >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
		Assert.isTrue(size >= 0,
				"Indique un número de distribuciones a devolver positivo.");

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		//Si esta configurado para que comience por 1 hay que restarle uno
		if (FIRST_COLLECTIONS_INIT_VALUE==1){
			initValue--;
		}
		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO(
				Long.valueOf(size), Long.valueOf(initValue),
				EstadoDistribucionEnum.getEnum(state));

		ResultadoBusquedaDistribucionVO inputDistributions = getDistribucionManager()
				.getInputDistributions(usuario, criterio);

		WSDistributionsResponse response = (WSDistributionsResponse) new ListOfDistribucionVOToWSDistributionsResponseMapper(
				usuario).map(inputDistributions.getDistributions());
		response.setTotal(inputDistributions.getTotal());

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public WSDistributionsResponse loadInputDistributionsByUserId(int state,
			int userId, int initValue, int size, Security security) {

		Assert.notNull(EstadoDistribucionEnum.getEnum(state),
				"Valor para estado de las distribuciones no permitido.");
		Assert.isTrue(initValue >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
		Assert.isTrue(size >= 0,
				"Indique un número de distribuciones a devolver positivo.");

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		//Si esta configurado para que comience por 1 hay que restarle uno
		if (FIRST_COLLECTIONS_INIT_VALUE==1){
			initValue--;
		}
		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO(
				Long.valueOf(size), Long.valueOf(initValue),
				EstadoDistribucionEnum.getEnum(state), String.valueOf(userId));

		ResultadoBusquedaDistribucionVO inputDistributions = getDistribucionManager()
				.getInputDistributions(usuario, criterio);

		WSDistributionsResponse response = (WSDistributionsResponse) new ListOfDistribucionVOToWSDistributionsResponseMapper(
				usuario).map(inputDistributions.getDistributions());
		response.setTotal(inputDistributions.getTotal());

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public WSDistributionsResponse loadOutputDistributions(int state,
			int initValue, int size, Security security) {

		Assert.notNull(EstadoDistribucionEnum.getEnum(state),
				"Valor para estado de las distribuciones no permitido.");
		Assert.isTrue(initValue >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
		Assert.isTrue(size >= 0,
				"Indique un número de distribuciones a devolver positivo.");

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		//Si esta configurado para que comience por 1 hay que restarle uno
		if (FIRST_COLLECTIONS_INIT_VALUE==1){
			initValue--;
		}
		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO(
				Long.valueOf(size), Long.valueOf(initValue),
				EstadoDistribucionEnum.getEnum(state));

		ResultadoBusquedaDistribucionVO outputDistributions = getDistribucionManager()
				.getOutputDistributions(usuario, criterio);

		WSDistributionsResponse response = (WSDistributionsResponse) new ListOfDistribucionVOToWSDistributionsResponseMapper(
				usuario).map(outputDistributions.getDistributions());
		response.setTotal(outputDistributions.getTotal());

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public WSDistributionsResponse loadOutputDistributionsByUserId(int state,
			int userId, int initValue, int size, Security security) {

		Assert.notNull(EstadoDistribucionEnum.getEnum(state),
				"Valor para estado de las distribuciones no permitido.");
		Assert.isTrue(initValue >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
		Assert.isTrue(size >= 0,
				"Indique un número de distribuciones a devolver positivo.");

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		// TODO: Asertos nulos para el estado
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		//Si esta configurado para que comience por 1 hay que restarle uno
		if (FIRST_COLLECTIONS_INIT_VALUE==1){
			initValue--;
		}
		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO(
				Long.valueOf(size), Long.valueOf(initValue),
				EstadoDistribucionEnum.getEnum(state), String.valueOf(userId));
		ResultadoBusquedaDistribucionVO outputDistributions = getDistribucionManager()
				.getOutputDistributions(usuario, criterio);

		WSDistributionsResponse response = (WSDistributionsResponse) new ListOfDistribucionVOToWSDistributionsResponseMapper(
				usuario).map(outputDistributions.getDistributions());
		response.setTotal(outputDistributions.getTotal());

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	public void redistributeInputDistribution(String numReg, String codeDest,
			Security security) {

		Assert.isTrue(StringUtils.isNotEmpty(numReg),
				"Debe especificar un número de registro.");
		Assert
				.isTrue(StringUtils.isNotEmpty(codeDest),
						"Debe indicar el destino al que quiere redistribuir el registro.");

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		DistribucionVO distribution = instantiateDistribution(numReg, codeDest,null,
				null,null);

		getDistribucionManager().redistributeInputDistribution(usuario,
				distribution);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);
	}

	/**
	 * {@inheritDoc}
	 */
	public void redistributeOutputDistribution(String numReg, String codeDest,
			Security security) {

		Assert.isTrue(StringUtils.isNotEmpty(numReg),
				"Debe especificar un número de registro.");
		Assert
				.isTrue(StringUtils.isNotEmpty(codeDest),
						"Debe indicar el destino al que quiere redistribuir el registro.");

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		DistribucionVO distribution = instantiateDistribution(numReg, codeDest,
				null,null, null);

		getDistribucionManager().redistributeOutputDistribution(usuario,
				distribution);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);
	}

	/**
	 * {@inheritDoc}
	 */
	public void rejectDistribution(String numReg, String matter,
			Security security) {

		Assert.isTrue(StringUtils.isNotEmpty(numReg),
				"Debe especificar un número de registro a rechazar.");
		Assert.isTrue(StringUtils.isNotEmpty(matter),
				"No se puede rechazar un registro sin indicar un motivo.");

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		DistribucionVO distribution = instantiateDistribution(numReg, null, null,null,
				matter);

		getDistribucionManager().rejectDistribution(usuario, distribution);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

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

	public DistribucionManager getDistribucionManager() {
		return distribucionManager;
	}

	public void setDistribucionManager(DistribucionManager distribucionManager) {
		this.distribucionManager = distribucionManager;
	}



	/**
	 * @return el registroManager
	 */
	public RegistroManager getRegistroManager() {
		return registroManager;
	}

	/**
	 * @param registroManager el registroManager a fijar
	 */
	public void setRegistroManager(RegistroManager registroManager) {
		this.registroManager = registroManager;
	}

	protected DistribucionVO instantiateDistribution(String numReg,
			String codeDest, Integer typeDest, String destinationName, String matter) {

		DistribucionVO distribution = instantiateDistribution(numReg);

		if (!StringUtils.isEmpty(codeDest)) {
			ImplicadoDistribucionVO destinoDistribucion = new ImplicadoDistribucionVO();
			destinoDistribucion.setId(codeDest);
			if (typeDest != null){
				destinoDistribucion.setTipo(String.valueOf(typeDest));
			}
			destinoDistribucion.setName(destinationName);
			distribution.setDestinoDistribucion(destinoDistribucion);
		}

		if (!StringUtils.isEmpty(matter)) {
			distribution.setMensajeDistribucion(matter);
		}

		return distribution;
	}

	protected DistribucionVO instantiateDistribution(String numReg) {
		DistribucionVO distribucion = new DistribucionVO();
		BaseRegistroVO registro = new BaseRegistroVO();
		registro.setNumeroRegistro(numReg);
		distribucion.setRegistro(registro);
		return distribucion;
	}

	// Members
	protected LoginManager loginManager;

	protected UsuarioVOBuilder usuarioBuilder;

	protected DistribucionManager distribucionManager;

	protected RegistroManager registroManager;

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceDistributionsManager#loadInputDistributionsByCondition(java.lang.String, int, int, es.ieci.tecdoc.isicres.ws.legacy.service.distributions.Security)
	 */
	public WSDistributionsResponse loadInputDistributionsByCondition(String query, int initValue,
			int size, Security security) {

		Assert.isTrue(initValue >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
		Assert.isTrue(size >= 0,
				"Indique un número de distribuciones a devolver positivo.");

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO();
		//Si esta configurado para que comience por 1 hay que restarle uno
		if (FIRST_COLLECTIONS_INIT_VALUE==1){
			initValue--;
		}
		criterio.setOffset(Long.valueOf(initValue));
		criterio.setLimit(Long.valueOf(size));
		ResultadoBusquedaDistribucionVO resultadoBusquedaDistribuccionVO = distribucionManager.getUserInputDistributionsByCondition(usuario, criterio, query);

		WSDistributionsResponse response = (WSDistributionsResponse) new ListOfDistribucionVOToWSDistributionsResponseMapper(
				usuario).map(resultadoBusquedaDistribuccionVO.getDistributions());
		response.setTotal(resultadoBusquedaDistribuccionVO.getTotal());

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return response;
	}


	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceDistributionsManager#loadInputDistributionsByCondition(java.lang.String, int, int, es.ieci.tecdoc.isicres.ws.legacy.service.distributions.Security)
	 */
	public WSDistributionsResponse loadOutputDistributionsByCondition(String query, int initValue,
			int size, Security security) {

		Assert.isTrue(initValue >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
		Assert.isTrue(size >= 0,
				"Indique un número de distribuciones a devolver positivo.");

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO();
		//Si esta configurado para que comience por 1 hay que restarle uno
		if (FIRST_COLLECTIONS_INIT_VALUE==1){
			initValue--;
		}
		criterio.setOffset(Long.valueOf(initValue));
		criterio.setLimit(Long.valueOf(size));
		ResultadoBusquedaDistribucionVO resultadoBusquedaDistribuccionVO = distribucionManager.getUserOutputDistributionsByCondition(usuario, criterio, query);

		WSDistributionsResponse response = (WSDistributionsResponse) new ListOfDistribucionVOToWSDistributionsResponseMapper(
				usuario).map(resultadoBusquedaDistribuccionVO.getDistributions());
		response.setTotal(resultadoBusquedaDistribuccionVO.getTotal());

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return response;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceDistributionsManager#loadDistributionsByConditionEx(java.lang.String, int, int, es.ieci.tecdoc.isicres.ws.legacy.service.distributions.Security)
	 */
	public WSDistributionsResponse loadDistributionsByConditionEx(String query, int initValue,
			int size, Security security) {

		Assert.isTrue(initValue >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
		Assert.isTrue(size >= 0,
				"Indique un número de distribuciones a devolver positivo.");

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO();

		//Si esta configurado para que comience por 1 hay que restarle uno
		if (FIRST_COLLECTIONS_INIT_VALUE==1){
			initValue--;
		}
		criterio.setOffset(Long.valueOf(initValue));
		criterio.setLimit(Long.valueOf(size));

		ResultadoBusquedaDistribucionVO resultadoBusquedaDistribuccionVO = distribucionManager.getDistributionsByCondition(usuario, criterio, query);

		WSDistributionsResponse response = (WSDistributionsResponse) new ListOfDistribucionVOToWSDistributionsResponseMapper(
				usuario).map(resultadoBusquedaDistribuccionVO.getDistributions());
		response.setTotal(resultadoBusquedaDistribuccionVO.getTotal());

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return response;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceDistributionsManager#registerDistribute(java.lang.String, int, int, int, java.lang.String, int, int, java.lang.String, java.lang.String, es.ieci.tecdoc.isicres.ws.legacy.service.distributions.Security)
	 */
	public WSDistribution registerDistribute(String numReg, int bookId, int senderType,
			int senderId, String senderName, int destinationType, int destinationId,
			String destinationName, String matter, Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("registerDistribute(String, int, int, int, String, int, int, String, String, Security) - start");
		}

		WSDistribution wsDistribution = null;
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		BaseRegistroVO registro = registroManager.findRegistroByNumRegistro(usuario, numReg);

		Integer idRegistro = Integer.valueOf(registro.getIdRegistro());
		UsuarioVO usuarioOrigen = new UsuarioVO();
		usuarioOrigen.setId(String.valueOf(senderId));
		usuarioOrigen.setFullName(senderName);

		logger.info("Creando una distribucion desde el origen "+senderId+" al destino "+destinationId+" para el registro numero: " + numReg);

		DistribucionVO distribucionCreada = distribucionManager.createDistribution(usuario, bookId, idRegistro, senderType, senderId, destinationType, destinationId, matter);
		if (distribucionCreada != null){

			DistribucionVOToWSDistributionMapper mapper = new DistribucionVOToWSDistributionMapper(usuario);
			wsDistribution = (WSDistribution) mapper.map(distribucionCreada);
		}



		if (logger.isDebugEnabled()) {
			logger.debug("registerDistribute(String, int, int, int, String, int, int, String, String, Security) - end");
		}

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return wsDistribution;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceDistributionsManager#redistributeInputDistributionManual(java.lang.String, int, int, java.lang.String, es.ieci.tecdoc.isicres.ws.legacy.service.distributions.Security)
	 */
	public void redistributeInputDistributionManual(String numReg, int destinationType,
			int destinationId, String destinationName, String matter, Security security) {
		Assert.isTrue(StringUtils.isNotEmpty(numReg),
				"Debe especificar un número de registro.");


		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		String codeDest = String.valueOf(destinationId);

		Assert
		.isTrue(StringUtils.isNotEmpty(codeDest),
				"Debe indicar el destino al que quiere redistribuir el registro.");

		ImplicadoDistribucionVO destinoDistribucion = new ImplicadoDistribucionVO();
		destinoDistribucion.setId(String.valueOf(destinationId));
		destinoDistribucion.setName(destinationName);
		destinoDistribucion.setTipo(String.valueOf(destinationType));

		getDistribucionManager().redistributeInputDistributionsManual(usuario, numReg,destinoDistribucion, matter);

		
		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceDistributionsManager#redistributeOutputDistributionManual(java.lang.String, int, int, java.lang.String, es.ieci.tecdoc.isicres.ws.legacy.service.distributions.Security)
	 */
	public void redistributeOutputDistributionManual(String numReg, int destinationType,
			int destinationId, String destinationName, String matter, Security security) {
		Assert.isTrue(StringUtils.isNotEmpty(numReg),
				"Debe especificar un número de registro.");


		/*
		 * Distribuye todas las distribuciones del registro
		 * Se necesitará el id de la distribución en lugar del número de registro
		 */


		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		String codeDest = String.valueOf(destinationId);

		Assert
		.isTrue(StringUtils.isNotEmpty(codeDest),
				"Debe indicar el destino al que quiere redistribuir el registro.");


		ImplicadoDistribucionVO destinoDistribucion = new ImplicadoDistribucionVO();
		destinoDistribucion.setId(String.valueOf(destinationId));
		destinoDistribucion.setName(destinationName);
		destinoDistribucion.setTipo(String.valueOf(destinationType));

		getDistribucionManager().redistributeOutputDistributionsManual(usuario, numReg,destinoDistribucion, matter);

			//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

	}



}
