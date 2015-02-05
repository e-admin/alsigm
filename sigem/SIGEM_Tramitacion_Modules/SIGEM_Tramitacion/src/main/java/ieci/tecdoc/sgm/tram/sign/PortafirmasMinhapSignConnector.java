package ieci.tecdoc.sgm.tram.sign;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.sign.SignDocument;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;

import _0.v2.type.pfirma.cice.juntadeandalucia.Authentication;
import _0.v2.type.pfirma.cice.juntadeandalucia.Job;
import _0.v2.type.pfirma.cice.juntadeandalucia.Request;
import _0.v2.type.pfirma.cice.juntadeandalucia.SignLine;
import _0.v2.type.pfirma.cice.juntadeandalucia.SignLineList;
import _0.v2.type.pfirma.cice.juntadeandalucia.User;
import _0.v2.type.pfirma.cice.juntadeandalucia.UserJob;
import es.ieci.webservice.portafirma.PortafirmasMinhapWebServiceClient;

public class PortafirmasMinhapSignConnector extends Sigm30SignConnector {

	/**
    * Separador entre firmante y fecha
    */
	private static final String SIGNER_DATE_SEPARATOR = ";;";

	/**
    * Logger de la clase.
    */
	private static final Logger logger = Logger.getLogger(PortafirmasMinhapSignConnector.class);

	Authentication authentication;
	String urlQuery;
	String urlModify;

	public PortafirmasMinhapSignConnector() {
		super();
		authentication = new Authentication();
		try {
			authentication.setUserName(ISPACConfiguration.getInstance().getProperty(ISPACConfiguration.PROCESS_SIGN_CONNECTOR_USER));
			authentication.setPassword(ISPACConfiguration.getInstance().getProperty(ISPACConfiguration.PROCESS_SIGN_CONNECTOR_PASSWORD));

			urlQuery = ISPACConfiguration.getInstance().getProperty(ISPACConfiguration.PROCESS_SIGN_CONNECTOR_QUERY_URL);
			urlModify = ISPACConfiguration.getInstance().getProperty(ISPACConfiguration.PROCESS_SIGN_CONNECTOR_MODIFY_URL);
		} catch (ISPACException e) {
			logger.error(e);
		}
	}

	/**
    * Realiza la firma del documento
    */
	public void sign(boolean changeState) throws ISPACException{
		generatePdfToSign(changeState);
		storeInformacionFirmantes();
	}

	/**
    * Almacena la información de los firmantes
    *
    * @throws ISPACException
    */
	private void storeInformacionFirmantes() throws ISPACException {
		IGenDocAPI genDocAPI = clientContext.getAPI().getGenDocAPI();
		Object connectorSession = null;
		try {
			String infoPagRDE = signDocument.getItemDoc().getString("INFOPAG_RDE");

			if (logger.isInfoEnabled()){
				logger.info("Actualizando idTransaccion sobre el documento RDE: " + infoPagRDE);
			}

			List signers = getSignersListInfo(signDocument, true);
			connectorSession = genDocAPI.createConnectorSession();
			XmlFacade xmlFacade = new XmlFacade("<firmas/>");
			String xPath = null;
			xPath = "/firmas";
			xmlFacade.set(xPath+"/@tipo", "Portafirmas");

			int i = 0;
			if (signers!=null){
				Iterator it = signers.iterator();
				String encodedSigner = null;
				while (it.hasNext()){
					xPath = "/firmas/firma[@id='"+Integer.toString(i)+"']";
					encodedSigner = new String(Base64.encode(((String)it.next()).getBytes()));
					xmlFacade.set(xPath, encodedSigner);
					i++;
				}
			}

			genDocAPI.setDocumentProperty(connectorSession, infoPagRDE, "Firma", xmlFacade.toString() );
			logger.debug(xmlFacade.toString());
		}
		finally {
			if (connectorSession != null) {
				genDocAPI.closeConnectorSession(connectorSession);
			}
		}
	}

	/**
    * {@inheritDoc}
    * @see ieci.tecdoc.sgm.tram.sign.Sigem23SignConnector#getSignerList(ieci.tdw.ispac.ispaclib.sign.SignDocument)
    */
	protected List getSignerList(SignDocument signDocument) throws ISPACException {

		return getSignersListInfo(signDocument, false);
	}

	/**
    * Permite obtener la informacion de los firmantes indicando si se proporciona la fecha de la firma
    * @param signDocument documento firmando
    * @param withSignDate indica si se devuelve por cada firmante la fecha de firma
    * @return
    * @throws ISPACException
    */
	private List getSignersListInfo(SignDocument signDocument, boolean withSignDate)
			throws ISPACException {
		PortafirmasMinhapWebServiceClient portafirmasWsClient;
		try {
			portafirmasWsClient = new PortafirmasMinhapWebServiceClient(urlQuery, urlModify, authentication);

			IInvesflowAPI invesflowAPI = clientContext.getAPI();
			IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
			String idProcesoFirma = signDocument.getItemDoc().getString("ID_PROCESO_FIRMA");

			XmlFacade xmlFacade = new XmlFacade(idProcesoFirma);
			String requestHash = xmlFacade.get("/process/requestHash");
			String docHash = xmlFacade.get("/process/docHash");
			Request request = portafirmasWsClient.getRequest(requestHash);

			List signers = new ArrayList();
			SignLineList signLineList = request.getSignLineList();
			String signerDescription = null;
			UserJob userJob = null;
			User user = null;
			Job job = null;
			Calendar calendar = null;
			_0.v2.type.pfirma.cice.juntadeandalucia.SignerList signerList = null;
			_0.v2.type.pfirma.cice.juntadeandalucia.Signer signer = null;
			for (SignLine signLine : signLineList.getSignLine()){
				signerList = signLine.getSignerList();

				if ((signerList!=null)&&(signerList.getSigner()!=null)&&(signerList.getSigner().length>0)){
					signer = signerList.getSigner(0);
					calendar = signer.getFstate();
					userJob = signer.getUserJob();
					if (userJob instanceof User){
						user = (User) userJob;
						signerDescription = user.getName() + ((user.getSurname1()!=null)?" "+user.getSurname1():"") + ((user.getSurname2()!=null)?" "+user.getSurname2():"");
					} else if (userJob instanceof Job){
						job = (Job) userJob;
						signerDescription = job.getDescription();
					}
					if (withSignDate){
						signers.add(signerDescription+SIGNER_DATE_SEPARATOR+getSignDateFormatter(DATE_FORMATTER).format(calendar.getTime()));
					} else {
						signers.add(signerDescription);
					}
				}
			}

			return signers;
		} catch (MalformedURLException e) {
			throw new ISPACException(e);
		} catch (ServiceException e) {
			throw new ISPACException(e);
		} catch (Exception e) {
			throw new ISPACException(e);
		}
	}

}
