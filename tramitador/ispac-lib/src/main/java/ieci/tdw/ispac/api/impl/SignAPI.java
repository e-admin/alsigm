package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.sign.SameMinhapSignerException;
import ieci.tdw.ispac.api.errors.sign.SameSignerException;
import ieci.tdw.ispac.api.errors.sign.SignerAlreadyExistsException;
import ieci.tdw.ispac.api.errors.sign.SignerMinhapAlreadyExistsException;
import ieci.tdw.ispac.api.expedients.Document;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.common.constants.DocumentLockStates;
import ieci.tdw.ispac.ispaclib.common.constants.SignStatesConstants;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.ExpedientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.dao.security.SustitucionDAO;
import ieci.tdw.ispac.ispaclib.dao.sign.SignCircuitDetailDAO;
import ieci.tdw.ispac.ispaclib.dao.sign.SignCircuitHeaderDAO;
import ieci.tdw.ispac.ispaclib.dao.sign.SignCircuitInstanceDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.gendoc.sign.SignDocumentMgr;
import ieci.tdw.ispac.ispaclib.sign.ISignConnector;
import ieci.tdw.ispac.ispaclib.sign.ResultadoValidacionCertificado;
import ieci.tdw.ispac.ispaclib.sign.SignCircuitFilter;
import ieci.tdw.ispac.ispaclib.sign.SignCircuitMgr;
import ieci.tdw.ispac.ispaclib.sign.SignConnectorFactory;
import ieci.tdw.ispac.ispaclib.sign.SignDetailEntry;
import ieci.tdw.ispac.ispaclib.sign.SignDocument;
import ieci.tdw.ispac.ispaclib.sign.exception.InvalidSignatureValidationException;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnector;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnectorFactory;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.vo.ProcessSignProperties;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;

import com.lowagie.text.pdf.PdfReader;

public class SignAPI implements ISignAPI {

	public static final Logger logger = Logger.getLogger(SignAPI.class);

	ClientContext mcontext;

	public SignAPI(ClientContext mcontext) {
		this.mcontext = mcontext;
	}


	public String initCircuitPortafirmas(int id, int documentId, ProcessSignProperties properties)
			throws ISPACException {

		String identificador = "";
		IEntitiesAPI entitiesAPI = mcontext.getAPI().getEntitiesAPI();
		ProcessSignConnector processSignConnector = ProcessSignConnectorFactory
				.getInstance().getProcessSignConnector();
		IItemCollection documentos = entitiesAPI.getEntities(
				SpacEntities.SPAC_DT_DOCUMENTOS, mcontext.getStateContext()
						.getNumexp(), " ID=" + documentId);
		if (documentos.next()) {
			IItem documento = documentos.value();
			// Comprobar si para el documento ya se ha iniciado un circuito de firma
			String estadoFirma = documento.getString("ESTADOFIRMA");
			if (SignStatesConstants.PENDIENTE_CIRCUITO_FIRMA.equals(estadoFirma)) {
				throw new ISPACInfo("exception.signProcess.inUse.document", true);
			}
			// Referencia al objeto en el gestor documental que almacena el
			// documento f�sico.
			String infoPage = documento.getString("INFOPAG");
			IGenDocAPI genDocAPI = this.mcontext.getAPI().getGenDocAPI();
			Object connectorSession = null;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			connectorSession = genDocAPI.createConnectorSession();
			if (!genDocAPI.existsDocument(connectorSession, infoPage)) {
				logger
						.error("No se ha encontrado el documento f�sico con identificador: '"
								+ infoPage
								+ "' en el repositorio de documentos");
				throw new ISPACInfo("exception.documents.notExists", false);
			}

			genDocAPI.getDocument(connectorSession, infoPage, baos);
			Document document = new Document();
			document.setContent(new ByteArrayInputStream(baos.toByteArray()));
			document.setExtension(documento.getString("EXTENSION"));
			document.setName(documento.getString("NOMBRE"));
			document.setId(documento.getString("ID"));
			document.setLength(genDocAPI.getDocumentSize(connectorSession,
					infoPage));

			// Ejecuci�n en un contexto transaccional
			boolean ongoingTX = mcontext.ongoingTX();
			boolean bCommit = false;

	        try {
				if (!ongoingTX) {
					mcontext.beginTX();
				}


				identificador = processSignConnector.initSignProcess(mcontext,
						String.valueOf(id),document, properties);


				documento.set("ID_PROCESO_FIRMA", identificador);
				documento.set("ID_CIRCUITO", id);

				// Estado de Firma del documento a PENDIENTE
				documento.set("ESTADOFIRMA", SignStatesConstants.PENDIENTE_CIRCUITO_FIRMA);
				documento.set("FFIRMA", new Date());

				//Bloqueamos el documento para la edicion
				documento.set("BLOQUEO", DocumentLockStates.EDIT_LOCK);
				documento.store(mcontext);

				bCommit = true;

			} finally {
				if (!ongoingTX) {
					mcontext.endTX(bCommit);
				}
			}


		} else {
			logger.warn("No se ha encontrado el documento con id: "
					+ documentId + " del expediente "
					+ mcontext.getStateContext().getNumexp());
		}
		return identificador;
	}
	public int initCircuit(int id, int documentId) throws ISPACException {

		// Ejecuci�n en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

        try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}

			SignCircuitMgr signCircuitMgr = new SignCircuitMgr(mcontext);
			int signCircuitInstancedId = signCircuitMgr.initCircuit(id, documentId);


			IEntitiesAPI entitiesAPI = mcontext.getAPI().getEntitiesAPI();
			IItem itemDoc = entitiesAPI.getDocument(documentId);

			// Comprobar si para el documento ya se ha iniciado un circuito de firma
			if (SignStatesConstants.PENDIENTE_CIRCUITO_FIRMA.equals(itemDoc.get("ESTADOFIRMA"))) {
				throw new ISPACInfo("exception.signProcess.inUse.document", true);
			}

			// Estado de Firma del documento a PENDIENTE
			itemDoc.set("ESTADOFIRMA", SignStatesConstants.PENDIENTE_CIRCUITO_FIRMA);
			itemDoc.set("FFIRMA", new Date());

			//Bloqueamos el documento para la edicion
			itemDoc.set("BLOQUEO", DocumentLockStates.EDIT_LOCK);
			itemDoc.store(mcontext);

			bCommit = true;

			return signCircuitInstancedId;

		} finally {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}

	public IItemCollection getCircuit(int instanceCircuitId) throws ISPACException {
        DbCnt cnt = mcontext.getConnection();
		try {
			return SignCircuitInstanceDAO.getInstancedSingCircuit(cnt,instanceCircuitId).disconnect();
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	public IItemCollection getCircuitSetps(String respId) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();

		try {
        	String resplist = "'" + DBUtil.replaceQuotes(respId) + "'";

        	// Obtener los responsables sustituidos
        	IItemCollection substitutes = SustitucionDAO.getSubstitutes(cnt, respId).disconnect();
        	while (substitutes.next()) {

        		IItem substitute = (IItem) substitutes.value();
        		resplist += ",'" + DBUtil.replaceQuotes(substitute.getString("UID_SUSTITUIDO")) + "'";
        	}

			return SignCircuitInstanceDAO.getCircuitSteps(cnt, resplist).disconnect();
		}
		finally {
			mcontext.releaseConnection(cnt);
		}
	}

	public int countCircuitsStepts(String respId) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();

        try {
        	String resplist = "'" + DBUtil.replaceQuotes(respId) + "'";

        	// Obtener los responsables sustituidos
        	IItemCollection substitutes = SustitucionDAO.getSubstitutes(cnt, respId).disconnect();
        	while (substitutes.next()) {

        		IItem substitute = (IItem) substitutes.value();
        		resplist += ",'" + DBUtil.replaceQuotes(substitute.getString("UID_SUSTITUIDO")) + "'";
        	}

			return SignCircuitInstanceDAO.countCircuitSteps(cnt, resplist);
		}
        finally {
			mcontext.releaseConnection(cnt);
		}
	}

	public IItemCollection getHistorics(String respId, Date init, Date end, int state) throws ISPACException {
        DbCnt cnt = mcontext.getConnection();
		try {
			return SignCircuitInstanceDAO.getHistorics(cnt, respId, init, end, state).disconnect();
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	public IItemCollection getCircuits() throws ISPACException {
        DbCnt cnt = mcontext.getConnection();
		try {
			return SignCircuitHeaderDAO.getCircuits(cnt).disconnect();
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	public IItemCollection getCircuits(SignCircuitFilter filter) throws ISPACException {
        DbCnt cnt = mcontext.getConnection();
		try {
			return SignCircuitHeaderDAO.getCircuits(cnt, filter).disconnect();
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	public boolean signStep(SignDocument signDocument, int instancedStepId) throws ISPACException {

		// Ejecuci�n en un contexto transaccional


		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

		DbCnt cnt = mcontext.getConnection();

		try {

			if (!ongoingTX) {
				mcontext.beginTX();
			}

			SignCircuitMgr signCircuitMgr = new SignCircuitMgr(mcontext);
			// Tratar evento Despues de firmar

			// Obtenemos informaci�n de la instancia
			SignCircuitInstanceDAO signCircuitInstanceDAO = new SignCircuitInstanceDAO(
					cnt, instancedStepId);

			// Comprobar si el firmante del paso de firma no es el mismo que el
			// usuario conectado (sustituto)
			if (!signCircuitInstanceDAO.getString("ID_FIRMANTE").equals(
					mcontext.getRespId())) {

				signCircuitInstanceDAO.set("ID_FIRMANTE", mcontext.getRespId());
				signCircuitInstanceDAO.set("NOMBRE_FIRMANTE", mcontext
						.getResponsible().getRespName());
			}

			// Habria que a�adir al contexto los datos del firmante (Nombre y
			// Apellidos)
			List signsCertList = signDocument.getSignCertificate();
			String x509CertString = (String) signsCertList.get(signsCertList
					.size() - 1);
			Map params = new HashMap();
			try {
				ISignConnector signConnector = SignConnectorFactory
						.getSignConnector();
				String nameFirmante = signConnector.getInfoCert(x509CertString);
				params.put("FIRMANTE", nameFirmante);

			} catch (Exception e) {
				throw new ISPACException(e);
			}

			signCircuitMgr.processCircuitStepEvents(cnt,
					EventsDefines.EVENT_OBJ_SIGN_CIRCUIT_STEP_SIGN_AFTER,
					signCircuitInstanceDAO, params);

			this.sign(signDocument, signCircuitMgr.isLastStep(instancedStepId));

			boolean ret = signCircuitMgr.signStep(signDocument, instancedStepId);


			bCommit = true;

			return ret;


		} finally {

			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}

	public void sign(SignDocument signDocument, boolean changeState)throws ISPACException {

		// Ejecuci�n en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

        try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}

			// Firmamos con el conector de firma que estemos utilizando
			ISignConnector signConnector = SignConnectorFactory.getSignConnector();

			signConnector.initializate(signDocument, mcontext);
			signConnector.sign(changeState);

			// Informaci�n del documento
			IItem doc = signDocument.getItemDoc();

			// EVENTO: Firmar documento
			ExpedientContext expCtx = new ExpedientContext(mcontext);
			ITXTransaction tx = mcontext.getAPI().getTransactionAPI();

			StateContext stateContext = mcontext.getStateContext();
			expCtx.addContextParam("ID_DOCUMENTO" , doc.getString("ID"));

			if (stateContext.getActivityId() > 0) {
				expCtx.setStage(stateContext.getStageId());
				expCtx.setTask(doc.getInt("ID_TRAMITE"));
				expCtx.setActivity(stateContext.getActivityId(), doc.getInt("ID_TRAMITE"), doc.getInt("ID_TRAMITE_PCD"));
				tx.executeEvents(EventsDefines.EVENT_OBJ_ACTIVITY, doc.getInt("ID_FASE_PCD"), EventsDefines.EVENT_DOCUMENT_SIGN, expCtx);
			} else if (doc.getInt("ID_TRAMITE") > 0) {
				expCtx.setStage(doc.getInt("ID_FASE"));
				expCtx.setTask(doc.getInt("ID_TRAMITE"));
				tx.executeEvents(EventsDefines.EVENT_OBJ_TASK, doc.getInt("ID_TRAMITE_PCD"), EventsDefines.EVENT_DOCUMENT_SIGN, expCtx);
			} else if (doc.getInt("ID_FASE") > 0) {
				expCtx.setStage(doc.getInt("ID_FASE"));
				tx.executeEvents(EventsDefines.EVENT_OBJ_STAGE, doc.getInt("ID_FASE_PCD"), EventsDefines.EVENT_DOCUMENT_SIGN, expCtx);
			}

			bCommit = true;

        } finally {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
        }
	}

	public IItem getCircuitStep(int circuitId, int stepId) throws ISPACException {
        DbCnt cnt = mcontext.getConnection();
		try {
			return SignCircuitDetailDAO.getStep(cnt, circuitId, stepId);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	public IItem getStepInstancedCircuit(int instancedStepId) throws ISPACException {
        DbCnt cnt = mcontext.getConnection();
		try {
			SignCircuitInstanceDAO signCircuitInstanceDAO = new SignCircuitInstanceDAO(cnt, instancedStepId);
			return  signCircuitInstanceDAO;
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	public String generateHashCode(SignDocument signDocument) throws ISPACException {
		SignDocumentMgr signDocumentMgr = new SignDocumentMgr(signDocument,  mcontext);
		String hashCode = signDocumentMgr.generateHashCode();
		return hashCode;
	}

	public boolean isResponsible(int documentId, String respId) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
		try {
			return SignCircuitInstanceDAO.isResponsible(cnt, respId, documentId);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	/**
	 * Obtiene los pasos del circuito de firma en funci�n del
	 * identificador del documento a firmar o firmado en dicho circuito de firma.
	 *
	 * @param documentId Identificador del documento
	 * @return
	 * @throws ISPACException
	 */
	public IItemCollection getStepsByDocument(int documentId) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
		try {
			return SignCircuitInstanceDAO.getStepsByDocument(cnt, documentId).disconnect();
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	/**
	 * Elimina los pasos del circuito de firma asociados al documento.
	 * @param documentId Identificador del documento.
	 * @throws ISPACException si ocurre alg�n error.
	 */
	public void deleteStepsByDocument(int documentId) throws ISPACException {

        IItemCollection itemcol = getStepsByDocument(documentId);
		while (itemcol.next()) {
		    IItem stepSignCircuit = itemcol.value();
		    stepSignCircuit.delete(mcontext);
		}
	}

	/**
	 * Elimina los pasos del circuito de firma asociados a los documentos de una fase.
	 * @param stageId Identificador de la fase activa.
	 * @throws ISPACException si ocurre alg�n error.
	 */
	public void deleteStepsByStage(int stageId) throws ISPACException {

        IItemCollection itemcol = mcontext.getAPI().getEntitiesAPI().getStageDocuments(stageId);
		while (itemcol.next()) {
		    IItem document = itemcol.value();
		    deleteStepsByDocument(document.getKeyInt());
		}
	}

	/**
	 * Firma en bloque de pasos de circuito de firma.
	 *
	 * @param stepIds Identificadores de los pasos de circuito de firma
	 * @param signs Firmas del hash de los documentos asociados a los pasos
	 * @param certificado Certificado con el que se van a realizar las firmas
	 * @return Lista de documentos firmados
	 * @throws ISPACException
	 */
	public List batchSignSteps(String[] stepIds, String[] signs, String certificado) throws ISPACException {

		//Se comprubea la validez del certificado
		IInvesflowAPI invesflowAPI = mcontext.getAPI();
		IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();

		List signDocuments = new ArrayList();

		// Ejecuci�n en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

        try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}

			// Firmar el documento asociado a cada paso de circuito de firma
			for (int i = 0; i < stepIds.length; i++) {

				// Paso del circuito
				IItem stepCircuit = getStepInstancedCircuit(Integer.parseInt(stepIds[i]));

				// Documento asociado al paso
				IItem document = entitiesAPI.getDocument(stepCircuit.getInt("ID_DOCUMENTO"));

				// Expediente del documento
				String numExp = document.getString("NUMEXP");
				int idPcd = entitiesAPI.getExpedient(numExp).getInt("ID_PCD");

				// Documento a firmar
				SignDocument signDocument = new SignDocument(document);

				signDocument.setIdPcd(idPcd);
				signDocument.setNumExp(numExp);
				signDocument.addCertificate(certificado);
				signDocument.setHash(generateHashCode(signDocument));
				signDocument.addSign(signs[i]);
				signDocument.setNumSigner(stepCircuit.getInt("ID_PASO"));


				// Firma asociada al paso del circuito
				signStep(signDocument, stepCircuit.getKeyInt());

				signDocuments.add(signDocument);
			}

			// Si todo ha sido correcto se hace commit de la transacci�n
			bCommit = true;
	    }
	    finally {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}

	    return signDocuments;
	}




	/**
	 * Muestra los detalles de la firma de un documento asociado al expediente.
	 * La informaci�n mostrada es fecha de firma, nombre del firmante, y si el
	 * documento est� asociado a un circuito de firma, muestra el nombre de los
	 * usuarios que est�n pendientes de firmar.
	 *
	 * @param documentId
	 *            Identificador del documento dado por ispac
	 *            (SPAC_DT_DOCUMENTOS)
	 *
	 * @throws ISPACException
	 */

	public List showSignInfo(int documentId) throws ISPACException, InvalidSignatureValidationException {
		IGenDocAPI genDocAPI = this.mcontext.getAPI().getGenDocAPI();
		Object connectorSession = null;
		String signProperty = null;


		IEntitiesAPI entitiesAPI = this.mcontext.getAPI().getEntitiesAPI();

		IItem iitem = entitiesAPI.getDocument(documentId);

		List  details = new ArrayList();

		String state = iitem.getString("ESTADOFIRMA");
		if (state.equals(ESTADO_SIN_FIRMA)){
			return details;
		}

		// Referencia al objeto en el gestor documental que almacena el documento f�sico.
		String infoPage = iitem.getString("INFOPAG");

		//Referencia al documento firmado almacenado en el Repositorio de Documentos Electr�nicos
		String infoPageRDE = iitem.getString("INFOPAG_RDE");

	    List list =new ArrayList();
	    List certificates=new ArrayList();

		try {
			if (StringUtils.isNotBlank(infoPageRDE)){ // Existe al menos un firmante.

			// String guid = "<guid><archive>4</archive><folder>29</folder><document>1</document></guid>";
			connectorSession = genDocAPI.createConnectorSession();
			if (!genDocAPI.existsDocument(connectorSession, infoPageRDE)){
				logger.error("No se ha encontrado el documento f�sico con identificador: '"+infoPageRDE+"' en el repositorio de documentos");
				throw new ISPACInfo("exception.documents.notExists", false);
			}

			//Obtenemos el xml con las firmas adjuntadas antes de a�adir la nueva
		    signProperty = genDocAPI.getDocumentProperty(connectorSession, infoPageRDE, "Firma");

	    	XmlFacade xmlFacade = new XmlFacade(signProperty);

		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (StringUtils.isBlank(infoPage) || !genDocAPI.existsDocument(connectorSession, infoPage)){
				logger.error("No se ha encontrado el documento f�sico con identificador: '"+infoPage+"' en el repositorio de documentos");
				throw new ISPACInfo("exception.documents.notExists", false);
			}
		    genDocAPI.getDocument(connectorSession, infoPage, baos);


		    list = xmlFacade.getList("/" + SignDocument.TAG_FIRMAS + "/" + SignDocument.TAG_FIRMA);
		    certificates=xmlFacade.getList("/" + SignDocument.TAG_CERTIFICADOS + "/" + SignDocument.TAG_CERTIFICADO);

		    ISignConnector signConnector= SignConnectorFactory.getSignConnector();

			logger.debug("Firmas del documento: \n " + list.toString());

		    for (int i=0; i<list.size(); i++) {
				Map results = null;
				SignDetailEntry entry = new SignDetailEntry();
				//Verificamos la integridad de la firma

		    	byte signedContent [] = Base64.encode (baos.toByteArray()) ;
				results = signConnector.verify((String) list.get(i),new String(Base64.encode(signedContent)));
				//Obtenemos la informacion del firmante
				if(i<certificates.size()){

					entry.setAuthor(signConnector.getInfoCert((String) certificates.get(i)));
				}
				else{

					//Firmado por el servidor de @ firma
				}

				entry.setIntegrity((String) results.get(INTEGRIDAD));
				if (StringUtils.isNotEmpty( (String) results.get(ISignAPI.DN))){
					entry.setAuthor((String) results.get(ISignAPI.DN));

				}else{
					if(results.get(NOMBRE)!=null){
						entry.setAuthor( results.get(NOMBRE) + " " + results.get(ISignAPI.APELLIDOS));
					}
					else{
						entry.setAuthor("");
					}
				}

				entry.setSignDate(iitem.getString("FFIRMA"));
				entry.setFirmado(true);
				details.add(entry);
			}
		    }

		    IItemCollection rs = getStepsByDocument(documentId);

		 // NOMBRE_FIRMANTE
			int numberOfSigner = 0;
			while (rs.next()){
				numberOfSigner ++;
				IItem row = rs.value();
				if (numberOfSigner > list.size() ){
					SignDetailEntry entry = new SignDetailEntry();
					entry.setAuthor((String) row.get("NOMBRE_FIRMANTE"));
					entry.setSignDate("");
					entry.setFirmado(false);
					details.add(entry);
				}
			}
			for (int i=0; i< details.size(); i++) {
				SignDetailEntry signDetailEntry=(SignDetailEntry) details.get(i);
				logger.debug(signDetailEntry);
			}
		}
	    finally {
	    	if (connectorSession != null) {
				genDocAPI.closeConnectorSession(connectorSession);
			}
    	}
	    return details;
	}


	/**
	 *
	 * @param x09Cert Certificado
	 * @return El campo DN del certificado correspondiente al firmante
	 * @throws CertificateException
	 */
	public String getFirmanteFromCertificado (String x09Cert) throws CertificateException{
		String firmante="";
		if(StringUtils.isNotBlank(x09Cert)){
			String x509CertString = "-----BEGIN CERTIFICATE-----\n" + x09Cert+ "\n-----END CERTIFICATE-----";
			ByteArrayInputStream bais = new ByteArrayInputStream((byte[])x509CertString.getBytes());
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate x509cer = (X509Certificate)cf.generateCertificate(bais);

		    try {

		    	firmante = x509cer.getSubjectX500Principal().getName();
		    	if (StringUtils.isNotBlank(firmante)) {

		    		// Parsear el valor de CN teniendo en cuenta que puede tener comas (\,)
				    int ix = firmante.indexOf("CN=");
				    if (ix >= 0) {
				    	firmante = firmante.substring(ix + 3);
				    }

				    ix = firmante.indexOf(",");
				    if (ix > 0) {
				    	boolean found = false;
				    	while (!found) {
				    		if (firmante.charAt(ix - 1) == '\\') {
				    			ix = firmante.indexOf(",", ix + 1);
				    		} else {
				    			found = true;
				    			firmante = firmante.substring(0, ix);
				    		}
				    	}
				    }
		    	}

		    } catch (Throwable t) {
		    	firmante = x509cer.getSubjectX500Principal().toString();
		    }
		}

		return firmante;
	}

	/**
	 * Obtiene el n�mero de pasos de un circuito de firmas.
	 * @param circuitId Identificador del circuito de firmas.
	 * @return N�mero de pasos del circuito.
	 * @throws ISPACException si ocurre alg�n error.
	 */
	public int countCircuitSteps(int circuitId) throws ISPACException {
        DbCnt cnt = mcontext.getConnection();
		try {
			return SignCircuitDetailDAO.countSteps(cnt, circuitId);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	/**
	 * A�ade un firmaten a un circuito de firma.
	 * @param circuitId Identificador del circuito de firmas.
	 * @param signerUID UID del firmante.
	 * @throws ISPACException si ocurre alg�n error.
	 * @throws SignerAlreadyExistsException si el firmante ya est� asociado al circuito de firmas.
	 */
	public void addSigner(int circuitId, String signerUID) throws ISPACException {
		IInvesflowAPI invesFlowAPI = mcontext.getAPI();
        IRespManagerAPI respAPI = invesFlowAPI.getRespManagerAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		// Ejecuci�n en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

		try {

	        // Abrir transacci�n
	        if (!ongoingTX) {
	        	mcontext.beginTX();
	        }

			// Informaci�n del nuevo responsable
			IResponsible responsible = respAPI.getResp(signerUID);

	        // No se permite a�adir un firmante al circuito de firma si ya ha
			// est� presente en el circuito
	        if (existsSigner(circuitId, signerUID)) {
	        	throw new SignerAlreadyExistsException(responsible);
	        }

	        // N�mero de pasos del circuito
        	int numsteps = countCircuitSteps(circuitId) + 1;

        	// Generar el detalle
        	IItem detail = catalogAPI.createCTEntity(ICatalogAPI.ENTITY_SIGNPROCESS_DETAIL);
        	detail.set("ID_CIRCUITO", circuitId);
        	detail.set("ID_PASO", numsteps);
        	detail.set("ID_FIRMANTE", responsible.getUID());
        	detail.set("NOMBRE_FIRMANTE", responsible.getRespName());

        	detail.store(mcontext);

        	// Actualizar el n�mero de pasos del proceso de firma
        	IItem signprocess = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_SIGNPROCESS_HEADER, circuitId);
        	signprocess.set("NUM_PASOS", numsteps);
        	signprocess.store(mcontext);

	    	bCommit = true;

		} finally {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}

	/**
	 * A�ade un firmaten a un circuito de firma.
	 * @param circuitId Identificador del circuito de firmas.
	 * @param signerUID UID del firmante.
	 * @param signerName Nombre del firmante.
	 * @param signerType Tipo del firmante.
	 * @throws ISPACException si ocurre alg�n error.
	 * @throws SignerAlreadyExistsException si el firmante ya est� asociado al circuito de firmas.
	 */
	public void addSigner(int circuitId, String signerUID, String signerName, String signerType) throws ISPACException {

        IInvesflowAPI invesFlowAPI = mcontext.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		// Ejecuci�n en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

		try {

	        // Abrir transacci�n
	        if (!ongoingTX) {
	        	mcontext.beginTX();
	        }

	        // No se permite a�adir un firmante al circuito de firma si ya ha
			// est� presente en el circuito
	        if (existsSigner(circuitId, signerUID, signerType)) {
	        	throw new SignerMinhapAlreadyExistsException(signerName);
	        }

	        // N�mero de pasos del circuito
        	int numsteps = countCircuitSteps(circuitId) + 1;

        	// Generar el detalle
        	IItem detail = catalogAPI.createCTEntity(ICatalogAPI.ENTITY_SIGNPROCESS_DETAIL);
        	detail.set("ID_CIRCUITO", circuitId);
        	detail.set("ID_PASO", numsteps);
        	detail.set("ID_FIRMANTE", signerUID);
        	detail.set("NOMBRE_FIRMANTE", signerName);
       		detail.set("TIPO_FIRMANTE", signerType);

        	detail.store(mcontext);

        	// Actualizar el n�mero de pasos del proceso de firma
        	IItem signprocess = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_SIGNPROCESS_HEADER, circuitId);
        	signprocess.set("NUM_PASOS", numsteps);
        	signprocess.store(mcontext);

	    	bCommit = true;

		} finally {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}

	/**
	 * Sustituye un firmante en el circuito de firmas.
	 * @param circuitId Identificador del circuito de firmas.
	 * @param signerId Identificador del paso del circuito de firmas.
	 * @param newSignerUID UID del nuevo firmante.
	 * @throws ISPACException si ocurre alg�n error.
	 * @throws SignerAlreadyExistsException si el nuevo firmante ya est� asociado al circuito de firmas.
	 * @throws SameSignerException si el nuevo firmante es el mismo que el antiguo.
	 */
	public void substituteSigner(int circuitId, int signerId, String newSignerUID) throws ISPACException {

        IInvesflowAPI invesFlowAPI = mcontext.getAPI();
        IRespManagerAPI respAPI = invesFlowAPI.getRespManagerAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		// Ejecuci�n en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

		try {

	        // Abrir transacci�n
	        if (!ongoingTX) {
	        	mcontext.beginTX();
	        }

			// Informaci�n del detalle del firmate
	    	IItem detail = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_SIGNPROCESS_DETAIL, signerId);

			// Informaci�n del nuevo responsable
			IResponsible responsible = respAPI.getResp(newSignerUID);

			// Comprobar si el firmante seleccionado es el mismo que el seleccionado para sustituir.
	        if (StringUtils.equals(newSignerUID, detail.getString("ID_FIRMANTE"))) {
				throw new SameSignerException(responsible);
			}

	        // No se permite a�adir un firmante al circuito de firma si ya ha
			// est� presente en el circuito
	        if (existsSigner(circuitId, newSignerUID)) {
	        	throw new SignerAlreadyExistsException(responsible);
	        }

	    	// Sustituir un firmante
	    	detail.set("ID_FIRMANTE", responsible.getUID());
	    	detail.set("NOMBRE_FIRMANTE", responsible.getName());
	    	detail.store(mcontext);

	    	bCommit = true;

		} finally {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}

	/**
	 * Sustituye un firmante en el circuito de firmas.
	 * @param circuitId Identificador del circuito de firmas.
	 * @param signerId Identificador del paso del circuito de firmas.
	 * @param newSignerUID UID del nuevo firmante.
	 * @param signerName Nombre del firmante.
	 * @param signerType Tipo del firmante.
	 * @throws ISPACException si ocurre alg�n error.
	 * @throws SignerAlreadyExistsException si el nuevo firmante ya est� asociado al circuito de firmas.
	 * @throws SameSignerException si el nuevo firmante es el mismo que el antiguo.
	 */
	public void substituteSigner(int circuitId, int signerId, String newSignerUID, String signerName, String signerType) throws ISPACException {

        IInvesflowAPI invesFlowAPI = mcontext.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		// Ejecuci�n en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

		try {

	        // Abrir transacci�n
	        if (!ongoingTX) {
	        	mcontext.beginTX();
	        }

			// Informaci�n del detalle del firmate
	    	IItem detail = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_SIGNPROCESS_DETAIL, signerId);

			// Comprobar si el firmante seleccionado es el mismo que el seleccionado para sustituir.
	        if (StringUtils.equals(newSignerUID, detail.getString("ID_FIRMANTE"))) {
				throw new SameMinhapSignerException(signerName);
			}

	        // No se permite a�adir un firmante al circuito de firma si ya ha
			// est� presente en el circuito
	        if (existsSigner(circuitId, newSignerUID, signerType)) {
	        	throw new SignerMinhapAlreadyExistsException(signerName);
	        }

	    	// Sustituir un firmante
	    	detail.set("ID_FIRMANTE", newSignerUID);
	    	detail.set("NOMBRE_FIRMANTE", signerName);
	    	detail.set("TIPO_FIRMANTE", signerType);
	    	detail.store(mcontext);

	    	bCommit = true;

		} finally {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}

	/**
	 * Indica si el firmante ya est� asociado al circuito de firmas.
	 * @param circuitId Identificador del circuito de firmas.
	 * @param signerUID UID del firmante.
	 * @return true si el firmante ya est� asociado al circuito de firmas, false en caso contrario.
	 * @throws ISPACException si ocurre alg�n error.
	 */
	public boolean existsSigner(int circuitId, String signerUID) throws ISPACException {

        ICatalogAPI catalogAPI = mcontext.getAPI().getCatalogAPI();

        IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_SIGNPROCESS_DETAIL,
				" WHERE ID_CIRCUITO = " + circuitId + " AND ID_FIRMANTE = '" + DBUtil.replaceQuotes(signerUID) + "'");

		return itemcol.next();
	}

	/**
	 * Indica si el firmante ya est� asociado al circuito de firmas.
	 * @param circuitId Identificador del circuito de firmas.
	 * @param signerUID UID del firmante.
	 * @return true si el firmante ya est� asociado al circuito de firmas, false en caso contrario.
	 * @throws ISPACException si ocurre alg�n error.
	 */
	public boolean existsSigner(int circuitId, String signerUID, String signerType) throws ISPACException {

        ICatalogAPI catalogAPI = mcontext.getAPI().getCatalogAPI();

        IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_SIGNPROCESS_DETAIL,
				" WHERE ID_CIRCUITO = " + circuitId + " AND ID_FIRMANTE = '" + DBUtil.replaceQuotes(signerUID) + "'"
				+ " AND TIPO_FIRMANTE = '" + DBUtil.replaceQuotes(signerType) + "'");

		return itemcol.next();
	}


	/**
	 * Devuelve el n�mero de hojas del documento firmado (pdf)
	 * @param infopagRDE
	 * @return
	 * @throws ISPACException
	 */

	public int getNumHojasDocumentSigned(String infopagRde)
			throws ISPACException {
		int num_hojas = 0;

		File sourceFile = null;
		try {

			IInvesflowAPI invesflowAPI = mcontext.getAPI();
			IGenDocAPI genDocAPI = invesflowAPI.getGenDocAPI();

			// Fichero origen
			FileTemporaryManager fileTmpMgr = FileTemporaryManager
					.getInstance();
			sourceFile = fileTmpMgr.newFile(".pdf");

			// Obtiene el fichero origen del gestor documental
			OutputStream out = new FileOutputStream(sourceFile);

			Object connectorSession = genDocAPI.createConnectorSession();
			genDocAPI.getDocument(connectorSession, infopagRde, out);
			out.close();
			PdfReader reader = new PdfReader(sourceFile.getAbsolutePath());
			num_hojas = reader.getNumberOfPages();
			if (logger.isDebugEnabled()) {
				logger
						.debug("SignAPI:getNumHojasDocumentSignedt  n�mero de p�ginas"
								+ num_hojas);
			}
			return num_hojas;

		} catch (Exception e) {
			logger.error("Error en SignAPI:getNumHojasDocumentSigned ", e);
			throw new ISPACException(
					"Error SignAPI:getNumHojasDocumentSigned " + e);
		} finally {
			if (sourceFile != null) {
				try {
					FileTemporaryManager.getInstance().delete(sourceFile);
				} catch (ISPACException e) {
					logger.error(e);
					throw new ISPACException(e);
				}
			}
		}

	}


	/**
	 * Muestra los detalles de la firma de un documento enviado al portafirmas
	 * La informaci�n mostrada es fecha de firma, nombre del firmante, y si el
	 * documento est� asociado a un circuito de firma, muestra el nombre de los
	 * usuarios que est�n pendientes de firmar.
	 *
	 * @param documentId
	 *            Identificador del documento dado por ispac
	 *            (SPAC_DT_DOCUMENTOS)
	 *
	 * @throws ISPACException
	 */

	public List <SignDetailEntry> getSignDetailDocumentInPortafirmas(int documentId) throws ISPACException {

		if(logger.isDebugEnabled()){
			logger.debug("SignAPI:getSignDetailDocumentInPortafirmas Inicio ejecuci�n ...");
		}
		IEntitiesAPI entitiesAPI = this.mcontext.getAPI().getEntitiesAPI();
		List <SignDetailEntry>  details = new ArrayList();
		IItemCollection itemcol = entitiesAPI.getEntities(
				SpacEntities.SPAC_DT_DOCUMENTOS, mcontext.getStateContext()
						.getNumexp(), " ID=" + documentId);
		if (itemcol.next()) {
			IItem documento = itemcol.value();
			ProcessSignConnector processSignConnector = ProcessSignConnectorFactory
					.getInstance().getProcessSignConnector();
			details = processSignConnector.getSigns(mcontext, documento
					.getString("ID_PROCESO_FIRMA"));
		}

		if(logger.isDebugEnabled()){
			logger.debug("SignAPI:getSignDetailDocumentInPortafirmas; Fin ejecuci�n , el documento ha sido firmado "+details.size()+" veces");
		}
		return details;
	}

	public String getStateDocumentInPortafirmas(int documentId)
			throws ISPACException {

		if (logger.isDebugEnabled()) {
			logger.debug("SignAPI:getStateDocument: Inicio ejecuci�n ..");
		}
		String estado = "";
		// Se obtiene el id_proceso_firma para enviarlo al conector del
		// portafirmas
		IEntitiesAPI entitiesAPI = this.mcontext.getAPI().getEntitiesAPI();
		IItemCollection itemcol = entitiesAPI.getEntities(
				SpacEntities.SPAC_DT_DOCUMENTOS, mcontext.getStateContext()
						.getNumexp(), " ID=" + documentId);
		if (itemcol.next()) {
			IItem documento = itemcol.value();
			ProcessSignConnector processSignConnector = ProcessSignConnectorFactory
					.getInstance().getProcessSignConnector();
			estado = processSignConnector.getState(mcontext, documento
					.getString("ID_PROCESO_FIRMA"));
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("SignAPI:getStateDocument: Fin ejecuci�n, el estado obtenido es: "
							+ estado);
		}
		return estado;

	}


	public ResultadoValidacionCertificado validateCertificate(String certificado)
			throws ISPACException {
		ISignConnector signConnector = SignConnectorFactory.getSignConnector();
		ResultadoValidacionCertificado resultado = signConnector.validateCertificate(certificado);
		return resultado;
	}

}