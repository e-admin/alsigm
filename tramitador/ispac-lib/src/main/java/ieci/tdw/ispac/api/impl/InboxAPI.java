package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInboxAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRegisterAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.IThirdPartyAPI;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.expedients.Expedients;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.audit.IspacAuditConstants;
import ieci.tdw.ispac.audit.business.IspacAuditoriaManager;
import ieci.tdw.ispac.audit.business.manager.impl.IspacAuditoriaManagerImpl;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventAvisoConsultaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventRegDistConsultaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventRegDistModificacionVO;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.InboxContext;
import ieci.tdw.ispac.ispaclib.dao.wl.DeadLineDAO;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.notices.Notices;
import ieci.tdw.ispac.ispaclib.sicres.RegisterHelper;
import ieci.tdw.ispac.ispaclib.sicres.vo.Annexe;
import ieci.tdw.ispac.ispaclib.sicres.vo.Intray;
import ieci.tdw.ispac.ispaclib.sicres.vo.Register;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterInfo;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterType;
import ieci.tdw.ispac.ispaclib.sicres.vo.ThirdPerson;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnectorFactory;
import ieci.tdw.ispac.ispaclib.thirdparty.IElectronicAddressAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.IPostalAddressAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.IThirdPartyAdapter;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.OutputStream;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * API para gestionar la información de la bandeja de entrada.
 *
 */
public class InboxAPI implements IInboxAPI {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(InboxAPI.class);


	/**
	 * Contexto de cliente.
	 */
	private final ClientContext m_ctx;

	private IRegisterAPI registerAPI;

	private final IspacAuditoriaManager auditoriaManager;
	
	/**
	 * Constructor.
	 * @param context Contexto de cliente.
	 * @throws ISPACException
	 */
	public InboxAPI(ClientContext context) throws ISPACException {
		this.m_ctx = context;
		registerAPI = context.getAPI().getRegisterAPI();
		auditoriaManager = new IspacAuditoriaManagerImpl();
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IInboxAPI#getIntrays()
	 */
	public IItemCollection getInbox() throws ISPACException {

		List inboxItems = new ArrayList();

	    IInvesflowAPI invesflowAPI = m_ctx.getAPI();
	    IWorklistAPI worklistAPI = invesflowAPI.getWorkListAPI();

		Properties propSet = new Properties();
		propSet.add(new Property(0,"NOMBRE",Types.VARCHAR));
		propSet.add(new Property(1,"URL",Types.VARCHAR));
		propSet.add(new Property(2,"COUNT",Types.INTEGER));
		GenericItem inboxItem;

		// Avisos
		Notices notices = new Notices(m_ctx);
		int numNotices = notices.countNotices();
		if (numNotices > 0){
			inboxItem = new GenericItem(propSet, "NOMBRE");
			inboxItem.set("NOMBRE", "sucesos.avisosElectronicos");
			inboxItem.set("URL", "showNoticeList.do");
			inboxItem.set("COUNT", numNotices);
			inboxItems.add(inboxItem);
		}

		// Registros distribuidos
		//ISicresConnector sicres = SicresConnectorFactory.getInstance().getSicresConnector(m_ctx);
		//if (sicres != null) {
		if (registerAPI.existConnector()){
			int nIntrays = registerAPI.countIntrais();
			if (nIntrays > 0){
				inboxItem = new GenericItem(propSet, "NOMBRE");
				inboxItem.set("NOMBRE", "sucesos.registro");
				inboxItem.set("URL", "showIntrayList.do");
				inboxItem.set("COUNT", nIntrays);
				inboxItems.add(inboxItem);
			}
		}

//		Terms terms = new Terms(m_ctx);
//		int nTerms = terms.countTerms();
		int nTerms = worklistAPI.countExpiredTerms(DeadLineDAO.TYPE_ALL);

		// Plazos
		inboxItem = new GenericItem(propSet, "NOMBRE");
		inboxItem.set("NOMBRE", "sucesos.plazos");
		//Introducimos en la URL un parametro para que al hacer la consulta de
		//plazos expirados la realice con fecha final el día actual
		SimpleDateFormat fechaEs = new SimpleDateFormat("dd/MM/yyyy");
		inboxItem.set("URL", "showExpiredTerms.do?fechaFin="
				+ fechaEs.format(new Date()));
		inboxItem.set("COUNT", nTerms);
		inboxItems.add(inboxItem);

		// Informacion de tramitaciones agrupadas
	    int numeroTareasAgrupadas = worklistAPI.countBatchTasks();
	    if (numeroTareasAgrupadas>0){
			inboxItem = new GenericItem(propSet, "NOMBRE");
			inboxItem.set("NOMBRE", "sucesos.tramitacionesAgrupadas");
			//Introducimos en la URL un parametro para que al hacer la consulta de
			//plazos expirados la realice con fecha final el día actual

			inboxItem.set("URL", "showBatchTaskList.do");
			inboxItem.set("COUNT", numeroTareasAgrupadas);
			inboxItems.add(inboxItem);
	    }

	    // Portafirmas
	    if (ProcessSignConnectorFactory.getInstance().isDefaultConnector() && StringUtils.isNotBlank(ISPACConfiguration.getInstance().getProperty(
	    		ISPACConfiguration.DIGITAL_SIGN_CONNECTOR_CLASS))) {
	        ISignAPI signAPI = invesflowAPI.getSignAPI();
			inboxItem = new GenericItem(propSet, "NOMBRE");
			inboxItem.set("NOMBRE", "sucesos.portafirmas");
			inboxItem.set("URL", "showBatchSignList.do");
			inboxItem.set("COUNT", signAPI.countCircuitsStepts(m_ctx.getRespId()));
			inboxItems.add(inboxItem);
	    }

	    String responsible=m_ctx.getRespId();
	    if(StringUtils.equalsIgnoreCase(responsible, IResponsible.SUPERVISOR_TOTAL) ||StringUtils.equalsIgnoreCase(responsible, IResponsible.SUPERVISOR_MONITORING)){
	    	int nProcessSentTrash = invesflowAPI.countExpedientsSentToTrash();
	    	if(nProcessSentTrash>0){
	    		inboxItem = new GenericItem(propSet, "NOMBRE");
				inboxItem.set("NOMBRE", "sucesos.expedientesPapelera");
				//Introducimos en la URL un parametro para que al hacer la consulta de
				//plazos expirados la realice con fecha final el día actual
				inboxItem.set("URL", "showExpedientsSentToTrash.do?method=list&numreg="+nProcessSentTrash);
				inboxItem.set("COUNT", nProcessSentTrash);
				inboxItems.add(inboxItem);
	    	}
	    }


		return new ListCollection(inboxItems);
	}

	public IItemCollection getInbox(String resp) throws ISPACException {

		List inboxItems = new ArrayList();

	    IInvesflowAPI invesflowAPI = m_ctx.getAPI();
	    IWorklistAPI worklistAPI = invesflowAPI.getWorkListAPI();

		Properties propSet = new Properties();
		propSet.add(new Property(0,"NOMBRE",Types.VARCHAR));
		propSet.add(new Property(1,"URL",Types.VARCHAR));
		propSet.add(new Property(2,"COUNT",Types.INTEGER));
		GenericItem inboxItem;

		// Avisos
		Notices notices = new Notices(m_ctx);
		int numNotices = notices.countNotices();
		if (numNotices > 0){
			inboxItem = new GenericItem(propSet, "NOMBRE");
			inboxItem.set("NOMBRE", "sucesos.avisosElectronicos");
			inboxItem.set("URL", "showNoticeList.do");
			inboxItem.set("COUNT", numNotices);
			inboxItems.add(inboxItem);
		}

		// Registros distribuidos
		if (registerAPI.existConnector()) {
			int nIntrays = registerAPI.countIntrais();
			if (nIntrays > 0){
				inboxItem = new GenericItem(propSet, "NOMBRE");
				inboxItem.set("NOMBRE", "sucesos.registro");
				inboxItem.set("URL", "showIntrayList.do");
				inboxItem.set("COUNT", nIntrays);
				inboxItems.add(inboxItem);
			}
		}

//		Terms terms = new Terms(m_ctx);
//		int nTerms = terms.countTerms();
		int nTerms = worklistAPI.countExpiredTerms(DeadLineDAO.TYPE_ALL, resp);

		// Plazos
		inboxItem = new GenericItem(propSet, "NOMBRE");
		inboxItem.set("NOMBRE", "sucesos.plazos");
		//Introducimos en la URL un parametro para que al hacer la consulta de
		//plazos expirados la realice con fecha final el día actual
		SimpleDateFormat fechaEs = new SimpleDateFormat("dd/MM/yyyy");
		inboxItem.set("URL", "showExpiredTerms.do?fechaFin="
				+ fechaEs.format(new Date()));
		inboxItem.set("COUNT", nTerms);
		inboxItems.add(inboxItem);

		// Informacion de tramitaciones agrupadas
	    int numeroTareasAgrupadas = worklistAPI.countBatchTasks(resp);
	    if (numeroTareasAgrupadas>0){
			inboxItem = new GenericItem(propSet, "NOMBRE");
			inboxItem.set("NOMBRE", "sucesos.tramitacionesAgrupadas");
			//Introducimos en la URL un parametro para que al hacer la consulta de
			//plazos expirados la realice con fecha final el día actual

			inboxItem.set("URL", "showBatchTaskList.do");
			inboxItem.set("COUNT", numeroTareasAgrupadas);
			inboxItems.add(inboxItem);
	    }

	    // Portafirmas
	    if (ProcessSignConnectorFactory.getInstance().isDefaultConnector() && StringUtils.isNotBlank(ISPACConfiguration.getInstance().getProperty(
	    		ISPACConfiguration.DIGITAL_SIGN_CONNECTOR_CLASS))) {
	        ISignAPI signAPI = invesflowAPI.getSignAPI();
			inboxItem = new GenericItem(propSet, "NOMBRE");
			inboxItem.set("NOMBRE", "sucesos.portafirmas");
			inboxItem.set("URL", "showBatchSignList.do");
			inboxItem.set("COUNT", signAPI.countCircuitsStepts(m_ctx.getRespId()));
			inboxItems.add(inboxItem);
	    }


	    if(StringUtils.equalsIgnoreCase(resp, IResponsible.SUPERVISOR)||StringUtils.equalsIgnoreCase(resp, IResponsible.SUPERVISOR_MONITORING)){
	    	int nProcessSentTrash = invesflowAPI.countExpedientsSentToTrash();
	    	if(nProcessSentTrash>0){
	    		inboxItem = new GenericItem(propSet, "NOMBRE");
				inboxItem.set("NOMBRE", "sucesos.expedientesPapelera");
				//Introducimos en la URL un parametro para que al hacer la consulta de
				//plazos expirados la realice con fecha final el día actual
				inboxItem.set("URL", "showExpedientsSentToTrash.do?method=list&numreg="+nProcessSentTrash);
				inboxItem.set("COUNT", nProcessSentTrash);
				inboxItems.add(inboxItem);
	    	}
	    }

		return new ListCollection(inboxItems);
	}

	public IItemCollection getNotices() throws ISPACException {
		Notices notices = new Notices(m_ctx);

		IItemCollection result = notices.getNotices();

		// TODO: Auditar consulta de avisos
		auditConsultaAvisos(result);

		return result;
	}

	/**
	 * @param result
	 * @throws ISPACException
	 */
	private void auditConsultaAvisos(IItemCollection result) throws ISPACException {
		AuditContext auditContext = AuditContextHolder.getAuditContext();

		IspacAuditEventAvisoConsultaVO evento = new IspacAuditEventAvisoConsultaVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		Iterator iterResults = result.iterator();
		Map avisos = new HashMap();
		while (iterResults.hasNext()) {
			IItem item = (IItem) iterResults.next();
			avisos.put(item.getString("SPAC_AVISOS_ELECTRONICOS:ID_AVISO"), item.getXmlValues());
		}
		
		evento.setAvisos(avisos);
		evento.setUser("");
		evento.setIdUser("");
		evento.setUserHostName("");
		evento.setUserIp("");

		evento.setFecha(new Date());

		if (auditContext != null) {
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
			evento.setUser(auditContext.getUser());
			evento.setIdUser(auditContext.getUserId());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
		logger.info("Auditando la consulta del aviso");
		auditoriaManager.audit(evento);
	}

	
	public void modifyNotice(int noticeId, int newstate) throws ISPACException {
		Notices notices = new Notices(m_ctx);
		notices.modifyNotice(noticeId, newstate);
	}

	/**
	 * Obtiene la información del registro distribuido.
	 * @param register Número de registro.
	 * @return Información del registro distribuido.
	 * @throws ISPACException si ocurre algún error.
	 */
	public Intray getIntray(String register) throws ISPACException {
		if (!registerAPI.existConnector()) {
			throw new ISPACException("exception.sicres.notConfigured");
		}
		// TODO: Auditar la consulta de registros distribuidos
		
		Intray intray = registerAPI.getIntray(register); 
		List intrays = new ArrayList();
		intrays.add(intray);
		auditarConsultaRegistrosDistribuidos(intrays);
		return intray;
	}

	/**
	 * Obtiene la lista de registros distribuidos asociados al usuario conectado.
	 * @return Lista de registros distribuidos ({@link Intray}).
	 * @throws ISPACException si ocurre algún error.
	 */
	public List getIntrays() throws ISPACException {
		if (!registerAPI.existConnector()) {
			throw new ISPACException("exception.sicres.notConfigured");
		}
		
		// TODO: Auditar la consulta de registros distribuidos

		List intrays = registerAPI.getIntrays();
		auditarConsultaRegistrosDistribuidos(intrays);

		return intrays;
	}
	
	/**
	 * @param registrosDistribuidosMap
	 * @param intrays
	 */
	private void auditarConsultaRegistrosDistribuidos(List intrays) {

		Map registrosDistribuidosMap = new HashMap();
		Iterator iterIntrays = intrays.iterator();
		while (iterIntrays.hasNext()) {
			Intray intray = (Intray) iterIntrays.next();
			intray.getId();
			registrosDistribuidosMap.put(intray.getId(), intray.toString());
		}

		AuditContext auditContext = AuditContextHolder.getAuditContext();

		IspacAuditEventRegDistConsultaVO evento = new IspacAuditEventRegDistConsultaVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		evento.setUserHostName("");
		evento.setUserIp("");
		evento.setUser("");
		evento.setIdUser("");
		evento.setRegistros(registrosDistribuidosMap);

		evento.setFecha(new Date());

		if (auditContext != null) {
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
			evento.setUser(auditContext.getUser());
			evento.setIdUser(auditContext.getUserId());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
		logger.info("Auditando la consulta de los registros distribuidos");
		auditoriaManager.audit(evento);
	}

	/**
	 * @param registrosDistribuidosMap
	 * @param intrays
	 */
	private void auditarModificacionRegistroDistribuidos(String intrayId) {

		AuditContext auditContext = AuditContextHolder.getAuditContext();

		IspacAuditEventRegDistModificacionVO evento = new IspacAuditEventRegDistModificacionVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		evento.setUserHostName("");
		evento.setUserIp("");
		evento.setUser("");
		evento.setIdUser("");
		evento.setIdRegistroDistribuido(intrayId);
		evento.setNewValue("ARCHIVADO");
		evento.setFecha(new Date());

		if (auditContext != null) {
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
		logger.info("Auditando la consulta de los registros distribuidos");
		auditoriaManager.audit(evento);
	}


//	public void addToProccess(String register, int process, int type)
//			throws ISPACException {
//
//		ITXTransaction transaction = m_ctx.getAPI().getTransactionAPI();
//		InboxContext ctx = new InboxContext(m_ctx, register);
//		ctx.setProcess(process);
//
//		if (type == IInboxAPI.CREADO)
//			transaction.executeEvents(EventsDefines.EVENT_OBJ_PROCEDURE, ctx
//					.getProcedure(), EventsDefines.EVENT_INBOX_CREATE, ctx);
//		else if (type == IInboxAPI.ANEXADO)
//			transaction.executeEvents(EventsDefines.EVENT_OBJ_PROCEDURE, ctx
//					.getProcedure(), EventsDefines.EVENT_INBOX_ANNEX, ctx);
//
//		ISicresConnector sicres = SicresConnectorFactory.getInstance()
//		.getSicresConnector(m_ctx);
//		sicres.addToProccess(Integer.parseInt(register), process, type);
//	}

	/**
	 * Inicia un expediente a partir de un registro distribuido.
	 * @param register Número de registro distribuido.
	 * @param pcdId Identificador del procedimiento.
	 * @return Identificador del proceso creado.
	 * @throws ISPACException si ocurre algún error.
	 */
	public int createProcess(String register, int pcdId)
			throws ISPACException {

		if (logger.isInfoEnabled()) {
			logger.info("Crear un expediente del procedimiento [" + pcdId
					+ "] a partir del registro distribuido [" + register + "]");
		}

		// Ejecución en un contexto transaccional
		boolean ongoingTX = m_ctx.ongoingTX();
		boolean bCommit = false;

		int processId = ISPACEntities.ENTITY_NULLREGKEYID;

		try {

			// Conector con SICRES
			if (!registerAPI.existConnector()) {
				throw new ISPACException("exception.sicres.notConfigured");
			}

			if (!ongoingTX) {
				m_ctx.beginTX();
			}

			IInvesflowAPI invesflowAPI = m_ctx.getAPI();
			IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
			ITXTransaction tx = invesflowAPI.getTransactionAPI();
			IInboxAPI inboxAPI = invesflowAPI.getInboxAPI();
			IThirdPartyAPI thirdPartyAPI = invesflowAPI.getThirdPartyAPI();

			// Información del procedimiento
			IItem ctProcedure = entitiesAPI.getEntity(
					SpacEntities.SPAC_CT_PROCEDIMIENTOS, pcdId);

			// Parámetros para el expediente
			Map params = new HashMap();
			params.put("COD_PCD", ctProcedure.getString("COD_PCD"));

			// Crear el proceso del expediente
	    	processId = tx.createProcess(pcdId, params);

			InboxContext ctx = new InboxContext(m_ctx, register);
			ctx.setProcess(processId);

	    	// Información del proceso
	    	IProcess process = invesflowAPI.getProcess(processId);

	        // Información del registro de entrada
			Intray intray = inboxAPI.getIntray(register);

	    	// Información del expediente creado
	    	String numExp = process.getString("NUMEXP");
	    	String registerNumber = intray.getRegisterNumber();

			IItem expedient = entitiesAPI.getExpedient(numExp);
	    	expedient.set("NREG", registerNumber);

	    	// Información del registro de entrada
			RegisterInfo registerInfo = new RegisterInfo(null,
					registerNumber, null, RegisterType.ENTRADA);
			Register inReg = registerAPI.readRegister(registerInfo);
	    	if (inReg != null) {

		    	Date registerDate = inReg.getRegisterOrigin().getRegisterDate().getTime();


	    		//Comprobamos si la entidad SPAC_REGISTROS_ES esta vinculada al procedimiento para vincular el Apunte de registro al expediente a crear
	    		boolean associatedRegESEntity = RegisterHelper.isAssocitedRegistrosESEntity(m_ctx, numExp);
	    		IItem itemRegisterES = null;

	    		if (associatedRegESEntity){
	    			//Si no esta asociado ya el apunte de registro con el expediente, se realiza la asociacion
	    			IItemCollection itemcol = entitiesAPI.queryEntities(SpacEntities.SPAC_REGISTROS_ES_NAME, "WHERE NREG = '" + registerNumber + "' AND NUMEXP = '"+DBUtil.replaceQuotes(numExp)+"' AND TP_REG = '" + RegisterType.ENTRADA + "'");
	    			if (!itemcol.next()){
	    				itemRegisterES = entitiesAPI.createEntity(SpacEntities.SPAC_REGISTROS_ES_NAME, numExp);
	    				itemRegisterES.set("NREG", registerNumber);
	    				itemRegisterES.set("FREG", registerDate);
	    				itemRegisterES.set("TP_REG", RegisterType.ENTRADA);
	    				itemRegisterES.set("ORIGINO_EXPEDIENTE", "SI");

	    				//Se vincula el expediente al apunte de registro
	    				registerAPI.linkExpedient(new RegisterInfo(null, registerNumber, null, RegisterType.ENTRADA), numExp);
	    			}

	    			//Se da de alta los interesados como participantes en el expediente menos el primero que se asocia como interesado principal
		    		RegisterHelper.insertParticipants(m_ctx, registerNumber,RegisterType.ENTRADA, numExp, false);	
	    		}	    		
	    		
	    		
	    		



	    		// Fecha de registro
	    		expedient.set("FREG", registerDate);

	            if (inReg.getRegisterData() != null) {

	            	// INTERESADO PRINCIPAL
	            	if (!ArrayUtils.isEmpty(inReg.getRegisterData().getParticipants())) {

	                	ThirdPerson thirdPerson = inReg.getRegisterData().getParticipants() [0];
	                	if (thirdPerson != null) {

	                		// Obtener el interesado principal
		                	IThirdPartyAdapter thirdParty = null;

		                	if ((thirdPartyAPI != null) && StringUtils.isNotBlank(thirdPerson.getId())) {
		                		thirdParty = thirdPartyAPI.lookupById(thirdPerson.getId(), thirdPerson.getAddressId(), null);
	                		}

		                	if (thirdParty != null) {

			                	expedient.set("TIPOPERSONA", thirdParty.getTipoPersona());
			                	expedient.set("IDTITULAR", thirdParty.getIdExt());
			                	expedient.set("NIFCIFTITULAR", thirdParty.getIdentificacion());
			                	expedient.set("IDENTIDADTITULAR", thirdParty.getNombreCompleto());

			                	if (itemRegisterES != null){
				    				itemRegisterES.set("ID_INTERESADO", thirdParty.getIdExt());
				    				itemRegisterES.set("INTERESADO", thirdParty.getNombreCompleto());
			                	}


			                	IPostalAddressAdapter dirPostal = thirdParty.getDefaultDireccionPostal();
			                	if (dirPostal != null) {
			                		expedient.set("IDDIRECCIONPOSTAL", dirPostal.getId());
				                	expedient.set("DOMICILIO", dirPostal.getDireccionPostal());
				                	expedient.set("CPOSTAL", dirPostal.getCodigoPostal());
				                	expedient.set("CIUDAD", dirPostal.getMunicipio());
				                	expedient.set("TFNOFIJO", dirPostal.getTelefono());

				                	String regionPais = dirPostal.getProvincia();
				                	if (StringUtils.isNotEmpty(dirPostal.getPais()))
				                		regionPais += "/"+dirPostal.getPais();

				                	expedient.set("REGIONPAIS", regionPais);
			                	}

			                	IElectronicAddressAdapter dirElectronica = thirdParty.getDefaultDireccionElectronica();
			                	if (dirElectronica != null) {
			                		if (dirElectronica.getTipo() == IElectronicAddressAdapter.PHONE_TYPE) {
			                			expedient.set("TFNOMOVIL", dirElectronica.getDireccion());
			                		} else {
			                			expedient.set("DIRECCIONTELEMATICA", dirElectronica.getDireccion());
			                		}
			                	}

			                	String addressType = "P";
			                	if (thirdParty.isNotificacionTelematica()) {
			                		addressType = "T";
			                	}
			                	expedient.set("TIPODIRECCIONINTERESADO", addressType);

		                	} else {
		                		//Interesado principal no validado
		                		expedient.set("IDENTIDADTITULAR", thirdPerson.getName());
		                		expedient.set("DOMICILIO", thirdPerson.getAddress());
		                		
			                	if (itemRegisterES != null){
				    				itemRegisterES.set("INTERESADO", thirdPerson.getName());
			                	}
		                	}
	                	}
	            	}

	            	// ASUNTO (se corresponde con el resumen de registro)
	    	    	if (StringUtils.isNotBlank(inReg.getRegisterData().getSummary())) {
	    	    		expedient.set("ASUNTO", inReg.getRegisterData().getSummary());
	    	    	} else if (inReg.getRegisterData().getSubject() != null) {
	    	    		expedient.set("ASUNTO", StringUtils.nullToEmpty(inReg.getRegisterData().getSubject().getName()));
	    	    	}

	    	    	if (itemRegisterES != null){
	    	    		itemRegisterES.set("ASUNTO", expedient.get("ASUNTO"));
	    	    	}


	            }

	            if (itemRegisterES != null){
	            	itemRegisterES.store(m_ctx);
	            }

	    	}

	    	expedient.store(m_ctx);

			// Ejecutar el evento
			ITXTransaction transaction = invesflowAPI.getTransactionAPI();
			transaction.executeEvents(EventsDefines.EVENT_OBJ_PROCEDURE, ctx
					.getProcedure(), EventsDefines.EVENT_INBOX_CREATE, ctx);

			// Adjuntar los documentos del registro distribuido al expediente
			Expedients expedientsAPI = new Expedients(m_ctx);
			expedientsAPI.addDocuments(process.getString("NUMEXP"), intray);

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
		}
		finally {
			if (!ongoingTX) {
				m_ctx.endTX(bCommit);
			}
		}

		// Archivar el registro distribuido
		checkAutoArchiving(register);

		return processId;
	}

	/**
	 * Anexa el registro distribuido al expediente.
	 * @param register Número de registro distribuido.
	 * @param numExp Número de expediente.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void annexToProcess(String register, String numExp) throws ISPACException {
		annexToProcess(register, numExp, 0);
	}

	public void annexToProcess(String register, String numExp, int taskId)
			throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Anexar los documentos del registro distribuido ["
					+ register + "] al expediente [" + numExp + "]");
		}

		IInvesflowAPI invesflowAPI = m_ctx.getAPI();

		// Ejecución en un contexto transaccional
		boolean ongoingTX = m_ctx.ongoingTX();
		boolean bCommit = false;

		try {
			// Inicio de transacción
			if (!ongoingTX) {
				m_ctx.beginTX();
			}

			// Información del proceso
			IProcess process = invesflowAPI.getProcess(numExp);

			// Contexto de la bandeja de entrada
			ITXTransaction transaction = invesflowAPI.getTransactionAPI();
			InboxContext ctx = new InboxContext(m_ctx, register);
			ctx.setProcess(process.getInt("ID"));

			// Ejecutar el evento
			transaction.executeEvents(EventsDefines.EVENT_OBJ_PROCEDURE,
					ctx.getProcedure(), EventsDefines.EVENT_INBOX_ANNEX, ctx);

			// Información del registro distribuido
			IInboxAPI inboxAPI = invesflowAPI.getInboxAPI();
			Intray intray = inboxAPI.getIntray(register);

			// Adjuntar los documentos del registro distribuido al expediente
			Expedients expedientsAPI = new Expedients(m_ctx);
			if (taskId == 0){
				expedientsAPI.addDocuments(numExp, intray);
			}else{
				expedientsAPI.addDocuments(numExp, intray, taskId);
			}

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
		}
		finally {
			if (!ongoingTX) {
				m_ctx.endTX(bCommit);
			}
		}

		// Archivar el registro distribuido
		checkAutoArchiving(register);

	}


//	public void distribute(String register, Responsible responsible,
//			String message, boolean archive) throws ISPACException {
//		ISicresConnector sicres = SicresConnectorFactory.getInstance()
//			.getSicresConnector(m_ctx);
//		sicres.distribute(Integer.parseInt(register), responsible, message,
//				archive);
//	}


	/**
	 * Acepta un registro distribuido.
	 * @param register Número de registro.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void acceptIntray(String register) throws ISPACException {
		if (!registerAPI.existConnector()) {
			throw new ISPACException("exception.sicres.notConfigured");
		}
		registerAPI.acceptIntray(register);
	}

	/**
	 * Rechaza un registro distribuido.
	 * @param register Número de registro.
	 * @param reason Motivo del rechazo.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void rejectIntray(String register, String reason) throws ISPACException {
		if (!registerAPI.existConnector()) {
			throw new ISPACException("exception.sicres.notConfigured");
		}
		registerAPI.rejectIntray(register, reason);
	}

	/**
	 * Archiva un registro distribuido.
	 * @param register Número de registro.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void archiveIntray(String register) throws ISPACException {

		if (logger.isInfoEnabled()) {
			logger.info("Archivar el registro distribuido [" + register + "]");
		}

		if (!registerAPI.existConnector()) {
			throw new ISPACException("exception.sicres.notConfigured");
		}
		// TODO: Auditar el archivado del registro distribuido
		registerAPI.archiveIntray(register);
		this.auditarModificacionRegistroDistribuidos(register);
	}

//	public void changeState(String register, int state) throws ISPACException {
//		ISicresConnector sicres = SicresConnectorFactory.getInstance()
//			.getSicresConnector(m_ctx);
//		sicres.changeState(Integer.parseInt(register), state);
//	}

	public Annexe[] getAnnexes(String register) throws ISPACException {
		if (!registerAPI.existConnector()) {
			throw new ISPACException("exception.sicres.notConfigured");
		}
		return registerAPI.getAnnexes(register);
	}

	public void getAnnexe(String register, String annexe, OutputStream out)
			throws ISPACException {
		if (!registerAPI.existConnector()) {
			throw new ISPACException("exception.sicres.notConfigured");
		}
		registerAPI.getAnnexe(register, annexe, out);
	}

	/**
	 * Comprueba si se debe archivar automáticamente el registro distribuido.
	 * @param register Identificador del registro distribuido.
	 * @throws ISPACException si ocurre algún error.
	 */
	protected void checkAutoArchiving(String register) throws ISPACException {

		// Archivar el registro distribuido
		String autoArchiving = ConfigurationMgr.getVarGlobal(m_ctx, ConfigurationMgr.INTRAY_AUTO_ARCHIVING);
		if (logger.isInfoEnabled()) {
			logger.info(ConfigurationMgr.INTRAY_AUTO_ARCHIVING + ": [" + autoArchiving + "]");
		}

		if ("true".equals(autoArchiving)) {

			if (logger.isInfoEnabled()) {
				logger.info("Está configurado el archivo automático de los registros distribuidos");
			}

			archiveIntray(register);
		}
	}


}
