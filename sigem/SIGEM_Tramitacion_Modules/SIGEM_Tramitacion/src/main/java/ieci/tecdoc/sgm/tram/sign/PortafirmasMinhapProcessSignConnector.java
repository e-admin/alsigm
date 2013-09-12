package ieci.tecdoc.sgm.tram.sign;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.expedients.Document;
import ieci.tdw.ispac.api.impl.SignAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.sign.SignCircuitDetailDAO;
import ieci.tdw.ispac.ispaclib.dao.sign.SignCircuitHeaderDAO;
import ieci.tdw.ispac.ispaclib.sign.SignDetailEntry;
import ieci.tdw.ispac.ispaclib.sign.exception.InvalidSignatureValidationException;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnector;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.vo.ProcessSignProperties;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.vo.Signer;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.tram.helpers.EntidadHelper;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import _0.v2.type.pfirma.cice.juntadeandalucia.Authentication;
import _0.v2.type.pfirma.cice.juntadeandalucia.DocumentType;
import _0.v2.type.pfirma.cice.juntadeandalucia.ImportanceLevel;
import _0.v2.type.pfirma.cice.juntadeandalucia.Job;
import _0.v2.type.pfirma.cice.juntadeandalucia.RemitterList;
import _0.v2.type.pfirma.cice.juntadeandalucia.Request;
import _0.v2.type.pfirma.cice.juntadeandalucia.SignLine;
import _0.v2.type.pfirma.cice.juntadeandalucia.SignLineList;
import _0.v2.type.pfirma.cice.juntadeandalucia.SignLineType;
import _0.v2.type.pfirma.cice.juntadeandalucia.SignType;
import _0.v2.type.pfirma.cice.juntadeandalucia.User;
import _0.v2.type.pfirma.cice.juntadeandalucia.UserJob;
import _0.v2.type.pfirma.cice.juntadeandalucia.UserList;
import es.ieci.webservice.portafirma.PortafirmasMinhapWebServiceClient;

/**
 * Implementacion del conector de procesos de firma para el portafirmas Minhap
 * @author Iecisa
 * @version $Revision$
 *
 */
public class PortafirmasMinhapProcessSignConnector implements ProcessSignConnector {

	/**
    * Valor a introducir en la entidad
    */
	public static final String REFERENCE_SEPARATOR = "_";

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger
			.getLogger(PortafirmasMinhapProcessSignConnector.class);

	/**
	 * Authenticacion de los servicios
	 */
	private static Authentication authentication = null;
	
	/**
	 * URL del servicio de consulta
	 */
	private static String urlQuery = null;

	/**
	 * URL del servicio de modificacion 
	 */
	private static String urlModify = null;
	
	/**
	 * Formato de la fecha para la firma 
	 */
	public final static String DATE_FORMAT			= "DATE_FORMAT";
	
	/**
	 * Formato por defecto para la fecha de la firma.
	 */
	protected static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");
	
	static {
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
	 * Constructor.
	 */
	public PortafirmasMinhapProcessSignConnector() {
		super();
	}

	/**
	 * En conector por defecto no maneja estados distintos a los que se muestran ya en en
	 * la aplicacion (pendiente de firma, firmado, sin firma...)
	 *
	 * @throws ISPACException
	 * @see ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnector#getState(ieci.tdw.ispac.ispaclib.context.ClientContext,
	 *      java.lang.String)
	 */
	public String getState(ClientContext ctx, String processId)
			throws ISPACException {

		if (logger.isDebugEnabled()) {
			logger
					.info("MptProcessSignConnectorImpl->getState. ProcessId: "+processId);
		}
		
		try {
			PortafirmasMinhapWebServiceClient portafirmasWsClient = new PortafirmasMinhapWebServiceClient(urlQuery, urlModify, authentication);

			XmlFacade xmlFacade = new XmlFacade(processId);
			String requestHash = xmlFacade.get("/process/requestHash");
			Request request = portafirmasWsClient.getRequest(requestHash);
			return request.getRequestStatus().getValue();

		} catch (MalformedURLException e) {
			logger
			.error("No se ha podido acceder al servicio: '"
					+ urlQuery);
			throw new ISPACException(e);
		} catch (ServiceException e) {
			logger
			.error("Se ha producido un error al acceder al servicio: '"
					+ urlQuery);
			throw new ISPACException(e);
		} catch (Exception e){
			logger
			.error("Se ha producido un error al acceder al servicio: '"
					+ urlQuery);
			throw new ISPACException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnector#getDocument(ieci.tdw.ispac.ispaclib.context.ClientContext, java.lang.String)
	 */
	public byte[] getDocument(ClientContext ctx, String documentId) throws ISPACException {

		try {
			IEntitiesAPI entitiesAPI = ctx.getAPI().getEntitiesAPI();
			IItem itemDoc = entitiesAPI.getDocument(Integer.parseInt(documentId));

			String idProcesoFirma = itemDoc.getString("ID_PROCESO_FIRMA");


			PortafirmasMinhapWebServiceClient portafirmasWsClient = new PortafirmasMinhapWebServiceClient(urlQuery, urlModify, authentication);

			XmlFacade xmlFacade = new XmlFacade(idProcesoFirma);
			String docHash = xmlFacade.get("/process/docHash");
			return portafirmasWsClient.getDocument(docHash);
		} catch (MalformedURLException e) {
			logger
			.error("No se ha podido acceder al servicio: '"
					+ urlQuery);
			throw new ISPACException(e);
		} catch (ServiceException e) {
			logger
			.error("Se ha producido un error al acceder al servicio: '"
					+ urlQuery);
			throw new ISPACException(e);
		} catch (Exception e){
			logger
			.error("Se ha producido un error al acceder al servicio: '"
					+ urlQuery);
			throw new ISPACException(e);
		}
	}

	/**
	 * Permite obtener un documento no firmado
	 * @param ctx contexto
	 * @param documentId identificador del documento
	 * @return bytes del documento
	 * @throws ISPACException
	 */
	private byte[] getNotSignedDocument(ClientContext ctx, String documentId) throws ISPACException {
		IEntitiesAPI entitiesAPI = ctx.getAPI().getEntitiesAPI();
		IItem itemDoc = entitiesAPI.getDocument(Integer.parseInt(documentId));
		String infoPageRde = itemDoc.getString("INFOPAG");
		IGenDocAPI genDocAPI = ctx.getAPI().getGenDocAPI();
		Object connectorSession = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		connectorSession = genDocAPI.createConnectorSession();
		if (!genDocAPI.existsDocument(connectorSession, infoPageRde)) {
			logger
					.error("No se ha encontrado el documento fisico con identificador: '"
							+ infoPageRde
							+ "' en el repositorio de documentos");
			throw new ISPACInfo("exception.documents.notExists", false);
		}

		genDocAPI.getDocument(connectorSession, infoPageRde, baos);
		return baos.toByteArray();
	}
	
	/**
	 * Este metodo no aplica en el conector por defecto ya que se seguira tirando de la estructura organizativa como siempre
	 * y se permitira navegar a traves de la misma a la hora de seleccionar u
	 * {@inheritDoc}
	 * @see ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnector#getSigners(ieci.tdw.ispac.ispaclib.context.ClientContext)
	 */
	public List<Signer> getSigners(ClientContext ctx, String query) throws ISPACException {

		if (logger.isDebugEnabled()) {
			logger
					.info("MptProcessSignConnectorImpl->getSigners. Query: "+query);
		}
		
		try {
			PortafirmasMinhapWebServiceClient portafirmasWsClient = new PortafirmasMinhapWebServiceClient(urlQuery, urlModify, authentication);
			
			String wildcardQuery = StringUtils.EMPTY;
			if (StringUtils.isNotEmpty(query)){
				wildcardQuery = "%"+query+"%";
			}
			List <Signer> signers = getSigners(portafirmasWsClient.getUsers(wildcardQuery));

			if (logger.isDebugEnabled()) {
				logger.info("ViewSignersAction->Hemos obtenido " + signers.size()
						+ " firmantes");
			}
			return signers;

		} catch (MalformedURLException e) {
			logger
			.error("No se ha podido acceder al servicio: '"
					+ urlQuery);
			throw new ISPACException(e);
		} catch (ServiceException e) {
			logger
			.error("Se ha producido un error al acceder al servicio: '"
					+ urlQuery);
			throw new ISPACException(e);
		} catch (Exception e){
			logger
			.error("Se ha producido un error al acceder al servicio: '"
					+ urlQuery);
			throw new ISPACException(e);
		}
	}

	/**
	 * Este metodo no tiene aplicacion en la implementacion por defecto ya que el portafirmas por defecto no maneja estados,
	 * son los mismo que la propia aplicacion : Sin firma, firmado, pendiente de firma...
	 * {@inheritDoc}
	 * @throws ISPACException
	 * @throws InvalidSignatureValidationException
	 * @see ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnector#getSigns(ieci.tdw.ispac.ispaclib.context.ClientContext, java.lang.String)
	 */
	public List <SignDetailEntry> getSigns(ClientContext ctx, String documentId) throws InvalidSignatureValidationException, ISPACException {
		
		try {
			PortafirmasMinhapWebServiceClient portafirmasWsClient = new PortafirmasMinhapWebServiceClient(urlQuery, urlModify, authentication);

			IEntitiesAPI entitiesAPI = ctx.getAPI().getEntitiesAPI();
			IItem itemDoc = entitiesAPI.getDocument(Integer.parseInt(documentId));
			String idProcesoFirma = itemDoc.getString("ID_PROCESO_FIRMA");

			XmlFacade xmlFacade = new XmlFacade(idProcesoFirma);
			String requestHash = xmlFacade.get("/process/requestHash");
			Request request = portafirmasWsClient.getRequest(requestHash);
			
			List <SignDetailEntry> signDetailEntries = new ArrayList<SignDetailEntry>();
			SignLineList signLineList = request.getSignLineList();
			SignDetailEntry entry = null;
			UserJob userJob = null;
			User user = null;
			Job job = null;
			_0.v2.type.pfirma.cice.juntadeandalucia.SignerList signerList = null;
			_0.v2.type.pfirma.cice.juntadeandalucia.Signer signer = null;
			for (SignLine signLine : signLineList.getSignLine()){
				signerList = signLine.getSignerList();
				
				if ((signerList!=null)&&(signerList.getSigner()!=null)&&(signerList.getSigner().length>0)){
					signer = signerList.getSigner(0);
					entry = new SignDetailEntry();
					userJob = signer.getUserJob();
					if (userJob instanceof User){
						user = (User) userJob;
						entry.setAuthor(user.getName() + user.getSurname1()!=null?user.getSurname1():"" + user.getSurname2()!=null?user.getSurname2():"");						
					} else if (userJob instanceof Job){
						job = (Job) userJob;
						entry.setAuthor(job.getDescription());
					}
					
					if ("FIRMADO".equals(signer.getState().getIdentifier())){
						entry.setFirmado(true);
					} else {
						entry.setFirmado(false);
					}
					
					if (signer.getFstate()!=null){
						entry.setSignDate(getSignDateFormatter(DATE_FORMATTER).format(signer.getFstate().getTime()));
					}
					
					entry.setIntegrity(SignAPI.INTEGRIDAD_NO_APLICA);
					signDetailEntries.add(entry);
				}
			}
			return signDetailEntries;
		} catch (MalformedURLException e) {
			logger
			.error("No se ha podido acceder al servicio: '"
					+ urlQuery);
			throw new ISPACException(e);
		} catch (ServiceException e) {
			logger
			.error("Se ha producido un error al acceder al servicio: '"
					+ urlQuery);
			throw new ISPACException(e);
		} catch (Exception e){
			logger
			.error("Se ha producido un error al acceder al servicio: '"
					+ urlQuery);
			throw new ISPACException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnector#getSignsContent(ieci.tdw.ispac.ispaclib.context.ClientContext, java.lang.String)
	 */
	public byte[] getSignsContent(ClientContext ctx, String documentId) throws InvalidSignatureValidationException, ISPACException {
		
		try {
			PortafirmasMinhapWebServiceClient portafirmasWsClient = new PortafirmasMinhapWebServiceClient(urlQuery, urlModify, authentication);

			IEntitiesAPI entitiesAPI = ctx.getAPI().getEntitiesAPI();
			IItem itemDoc = entitiesAPI.getDocument(Integer.parseInt(documentId));
			String idProcesoFirma = itemDoc.getString("ID_PROCESO_FIRMA");

			XmlFacade xmlFacade = new XmlFacade(idProcesoFirma);
			String docHash = xmlFacade.get("/process/docHash");
			
			return portafirmasWsClient.getSigns(docHash).getContent();
		} catch (MalformedURLException e) {
			logger
			.error("No se ha podido acceder al servicio: '"
					+ urlQuery);
			throw new ISPACException(e);
		} catch (ServiceException e) {
			logger
			.error("Se ha producido un error al acceder al servicio: '"
					+ urlQuery);
			throw new ISPACException(e);
		} catch (Exception e){
			logger
			.error("Se ha producido un error al acceder al servicio: '"
					+ urlQuery);
			throw new ISPACException(e);
		}
	}
	
	/**
	 * En la implementacion por defecto el ID_PROCESO_FIRMA y el ID seran el mismo.
	 * {@inheritDoc}
	 * @see ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnector#initSignProcess(ieci.tdw.ispac.ispaclib.context.ClientContext, java.lang.String, ieci.tdw.ispac.api.expedients.Document)
	 */
	public String initSignProcess(ClientContext ctx, String processTemplateId,
			Document document , ProcessSignProperties properties) throws ISPACException {

		String xmlProcesoFirma = null;
		try {
			PortafirmasMinhapWebServiceClient portafirmasWsClient = new PortafirmasMinhapWebServiceClient(urlQuery, urlModify, authentication);
			
			Request request = new Request();
			
			// Aplicacion 
			request.setApplication(
							ISPACConfiguration.getInstance().getProperty(ISPACConfiguration.PROCESS_SIGN_CONNECTOR_APPLICATION));

			// Usuario de envio
			UserList users = portafirmasWsClient.getUsers(ISPACConfiguration.getInstance().getProperty(ISPACConfiguration.PROCESS_SIGN_CONNECTOR_USER));
			RemitterList remitters = new RemitterList();
			remitters.setUser(users.getUser());
			request.setRemitterList(remitters);

			// Firmantes de la linea
			_0.v2.type.pfirma.cice.juntadeandalucia.SignerList signers = null;
			_0.v2.type.pfirma.cice.juntadeandalucia.Signer signer = null;
			SignLine signLine = null;
			User user = null;
			Job job = null;
			
			int steps = SignCircuitDetailDAO.countSteps(ctx.getConnection(), Integer.parseInt(processTemplateId));
			SignLineList signLineList = new SignLineList();
			SignLine[] signLineArray = new SignLine[steps];
			
			int i = 0;
			IItemCollection itemcol = SignCircuitDetailDAO.getSteps(ctx.getConnection(), Integer.parseInt(processTemplateId)).disconnect();
			for (Iterator<IItem> iter = itemcol.iterator(); iter.hasNext();) {
				IItem item = (IItem) iter.next();
				signers = new _0.v2.type.pfirma.cice.juntadeandalucia.SignerList();
				signer = new _0.v2.type.pfirma.cice.juntadeandalucia.Signer();
				signLine = new SignLine();
				
				if ("U".equals(item.getString("TIPO_FIRMANTE"))){
					user = new User();
					user.setIdentifier(item.getString("ID_FIRMANTE"));
					user.setName(item.getString("NOMBRE_FIRMANTE"));					
					signer.setUserJob(user);
				} else {
					job = new Job();
					job.setIdentifier(item.getString("ID_FIRMANTE"));
					job.setDescription(item.getString("NOMBRE_FIRMANTE"));					
					signer.setUserJob(job);
				}
				
				signers.setSigner(new _0.v2.type.pfirma.cice.juntadeandalucia.Signer[] {signer});
				signLine.setSignerList(signers);
				signLine.setType(SignLineType.FIRMA);
				signLineArray[i]=signLine;
				i++;
			}

			// Linea de firma
			signLineList.setSignLine(signLineArray);
			request.setSignLineList(signLineList);

			// Objeto
			request.setSubject(properties.getSubject());
			
			// Asunto
			request.setText(properties.getContent());
			
			// Nivel de importancia
			ImportanceLevel importanceLevel = new ImportanceLevel();
			importanceLevel.setLevelCode(properties.getLevelOfImportance());
			request.setImportanceLevel(importanceLevel);
			
			// Fecha inicio
			if (properties.getFstart()!=null){
				request.setFentry(properties.getFstart());
			}

			// Fecha fin
			if (properties.getFexpiration()!=null){
				request.setFexpiration(properties.getFexpiration());
			}

			// Tipo de firma
			IItemCollection itemCircuit = SignCircuitHeaderDAO.getCircuit(ctx.getConnection(), Integer.parseInt(processTemplateId)).disconnect();
			IItem circuit = (IItem) itemCircuit.value();
			request.setSignType(SignType.fromValue(getSequence(circuit.getString("SECUENCIA"))));

			// Documento a firmar
			byte [] documentBytes = getNotSignedDocument(ctx, document.getId());
			_0.v2.type.pfirma.cice.juntadeandalucia.Document doc = new _0.v2.type.pfirma.cice.juntadeandalucia.Document();
			_0.v2.type.pfirma.cice.juntadeandalucia.DocumentList docList = new _0.v2.type.pfirma.cice.juntadeandalucia.DocumentList();
			docList.setDocument(new _0.v2.type.pfirma.cice.juntadeandalucia.Document[] {doc});
			doc.setSign(true);
			DocumentType docType = new DocumentType();
			docType.setIdentifier(ISPACConfiguration.getInstance().getProperty(ISPACConfiguration.PROCESS_SIGN_CONNECTOR_DOCTYPE));
			doc.setDocumentType(docType);
			doc.setMime(MimetypeMapping.getMimeType(document.getExtension()));
			
			IEntitiesAPI entitiesAPI = ctx.getAPI().getEntitiesAPI();
			IItem itemDoc = entitiesAPI.getDocument(Integer.parseInt(document.getId()));
			String filename = itemDoc.getString("DESCRIPCION");
			
			doc.setName(filename);					
			doc.setContent(documentBytes);
			doc.setType(document.getExtension());
			request.setDocumentList(docList);
			
			// Referencia
			Entidad entidad = EntidadHelper.getEntidad();
			request.setReference(ctx.getStateContext().getNumexp()
					+ REFERENCE_SEPARATOR + document.getId()
					+ REFERENCE_SEPARATOR + entidad.getIdentificador());

			// Creacion de la peticion
			String requestHash = portafirmasWsClient.createRequest(request);
			StringHolder holder= new StringHolder (requestHash);

			// Envio de la peticion
			portafirmasWsClient.sendRequest(holder);
			request = portafirmasWsClient.getRequest(requestHash);
			String docHash = request.getDocumentList().getDocument()[0].getIdentifier();
			xmlProcesoFirma = getProcesoFirmaXml(requestHash, docHash);

		} catch (MalformedURLException e) {
			logger
			.error("Se ha producido un error al acceder al servicio: '"
					+ urlQuery);
			throw new ISPACException(e);
		} catch (ServiceException e) {
			logger
			.error("Se ha producido un error al acceder al servicio: '"
					+ urlQuery);
			throw new ISPACException(e);
		} catch (Exception e) {
			logger
			.error("Se ha producido un error al acceder al servicio: '"
					+ urlQuery);
			throw new ISPACException(e);
		}
		
		return xmlProcesoFirma;

	}

	public int getTypeObject() {
		return TYPE_OBJECT;
	}

	/**
	 * Obtener el formateador para la fecha de la firma.
	 * @param defaultSignDateFormatter Formateador por defecto.
	 *
	 * @return Formateador para la fecha de la firma con el formato establecido en la configuración (DATE_FORMAT)
	 * si existe y es correcto, en caso contrario, el formateador por defecto.
	 *
	 * @throws ISPACException Si se produce algún error.
	 */
	protected SimpleDateFormat getSignDateFormatter(SimpleDateFormat defaultSignDateFormatter) throws ISPACException {

		SimpleDateFormat singDateFormatter = null;

		String signDateFormat = ISPACConfiguration.getInstance().getProperty(DATE_FORMAT);
		if (StringUtils.isNotBlank(signDateFormat)) {

			try {
				singDateFormatter = new SimpleDateFormat(signDateFormat);
			} catch (Exception e) {
				logger.debug("Error en el formato configurado para la fecha de la firma", e);
			}
		}

		if (singDateFormatter == null) {
			singDateFormatter = defaultSignDateFormatter;
		}

		return singDateFormatter;
	}
	
	/**
	 * Obtiene el valor correspondiente para el identificador de secuencia especifido
	 * @param idSequence identificador de secuencia
	 * @return String con el valor de secuencia necesario para el portafirmas
	 * @throws ISPACInfo
	 */
	private String getSequence(String idSequence) throws ISPACInfo{
		if ("1".equals(idSequence)){
			return "CASCADA";
		} else if ("2".equals(idSequence)){
			return "PARALELA";
		} else if ("3".equals(idSequence)){
			return "PRIMER FIRMANTE";
		} else {
			throw new ISPACInfo("exception.sequence.value.incorrect", false);
		}
	}

	/**
	 * Permite obtener un firmante de tramitacion a partir de un usuario del portafirmas
	 * @param user usuario del portafirmas
	 * @return firmante 
	 */
	private Signer getSigner(User user) {
		Signer signer = new Signer();
		signer.setIdentifier(user.getIdentifier());

		if ((user.getSurname1()!=null) && StringUtils.isNotEmpty(user.getSurname1())){
			// Usuario
			signer.setName(user.getName() + " " + user.getSurname1());
			signer.setTipoFirmante(Signer.TYPE_SIGNER_USER);
		} else {
			// Cargo
			signer.setName(user.getName());
			signer.setTipoFirmante(Signer.TYPE_SIGNER_CARGO);
		}

		return signer;
	}
	
	/**
	 * Permite obtener una lista de firmantes de tramitacion a partir de una lista de usuarios del portafirmas
	 * @param users usuarios del portafirmas
	 * @return lista de firmantes
	 */
	private List<Signer> getSigners(UserList users) {
		List<Signer> signers = new ArrayList<Signer>();
		if (users!=null){
			for (User user: users.getUser()){
				signers.add(getSigner(user));
			}
		}
		return signers;
	}

	/**
	 * Permite obtener el xml para almacenar en la tabla de documentos
	 * @param requestHash hash de la peticion
	 * @param docHash hash del documento
	 * @return String con el xml 
	 */
	private String getProcesoFirmaXml(String requestHash, String docHash){
		return "<process><requestHash>"+requestHash+"</requestHash><docHash>"+docHash+"</docHash></process>";
		
	}
	
	public String getIdSystem() {
		return SIGNPROCESS_SYSTEM_MPT;
	}

}
