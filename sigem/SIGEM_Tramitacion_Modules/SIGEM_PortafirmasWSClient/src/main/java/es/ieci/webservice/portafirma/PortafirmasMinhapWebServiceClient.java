package es.ieci.webservice.portafirma;

import java.io.InputStream;
import java.net.MalformedURLException;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;

import org.apache.commons.io.IOUtils;

import axis.mtom.client.handler.XOPHandler;

import _0.v2.modify.pfirma.cice.juntadeandalucia.ModifyServiceSoapBindingStub;
import _0.v2.modify.pfirma.cice.juntadeandalucia.ModifyService_PortType;
import _0.v2.modify.pfirma.cice.juntadeandalucia.ModifyService_ServiceLocator;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryServiceSoapBindingStub;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryService_PortType;
import _0.v2.query.pfirma.cice.juntadeandalucia.QueryService_ServiceLocator;
import _0.v2.type.pfirma.cice.juntadeandalucia.Authentication;
import _0.v2.type.pfirma.cice.juntadeandalucia.Document;
import _0.v2.type.pfirma.cice.juntadeandalucia.DocumentTypeList;
import _0.v2.type.pfirma.cice.juntadeandalucia.JobList;
import _0.v2.type.pfirma.cice.juntadeandalucia.Request;
import _0.v2.type.pfirma.cice.juntadeandalucia.Signature;
import _0.v2.type.pfirma.cice.juntadeandalucia.StateList;
import _0.v2.type.pfirma.cice.juntadeandalucia.UserList;



/**
 * Cliente del servicio web de trans
 * 
 * @author Iecisa
 * @version $Revision$
 *
 */
public class PortafirmasMinhapWebServiceClient {

	private QueryService_PortType wsQueryService;
	private ModifyService_PortType wsModifyService;
	private Authentication authentication;
	
	/**
	 * Constructor para el portafirmas
	 * @param queryUrl url de consulta
	 * @param modifyUrl url de modificacion
	 * @param authentication objeto de autenticacion
	 * @throws MalformedURLException
	 */
	public PortafirmasMinhapWebServiceClient(String queryUrl, String modifyUrl, Authentication authentication) throws MalformedURLException, ServiceException {
		QueryService_ServiceLocator  serviceLocatorQuery = new QueryService_ServiceLocator();      
		serviceLocatorQuery.setQueryServicePortEndpointAddress(queryUrl);  
		wsQueryService = (QueryServiceSoapBindingStub)serviceLocatorQuery.getQueryServicePort();
		ModifyService_ServiceLocator  serviceLocatorModify = new ModifyService_ServiceLocator();      
		serviceLocatorModify.setModifyServicePortEndpointAddress(modifyUrl);  
		wsModifyService = (ModifyServiceSoapBindingStub)serviceLocatorModify.getModifyServicePort();

		this.authentication = authentication;
	}

	/**
	 * Permite obtener los usuarios a partir de una query
	 * @param query string de busqueda
	 * @return
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public UserList getUsers(String query)
			throws MalformedURLException, Exception {
		return wsQueryService.queryUsers(authentication, query);
	}

	/**
	 * Permite obtener los cargos a partir de una query
	 * @param query string de busqueda
	 * @return
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public JobList getJobs(String query)
			throws MalformedURLException, Exception {
		return wsQueryService.queryJobs(authentication, query);
	}

	/**
	 * Permite obtener los estados de la peticion
	 * @param query string de busqueda
	 * @return
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public StateList getStates(String query)
			throws MalformedURLException, Exception {
		return wsQueryService.queryStates(authentication, query);
	}

	/**
	 * Permite obtener los tipos de documento
	 * @param query string de busqueda
	 * @return
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public DocumentTypeList getDocumentTypes(String query)
			throws MalformedURLException, Exception {
		return wsQueryService.queryDocumentTypes(authentication, query);
	}

	/**
	 * Permite obtener una peticion
	 * @param requestHash hash de la peticion
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public Request getRequest(String requestHash) 
			throws MalformedURLException, Exception {
		return wsQueryService.queryRequest(authentication, requestHash);
	}

	/**
	 * Permite obtener las firmas de un documento
	 * @param docHash hash del documento
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public Signature getSigns(String docHash) 
			throws MalformedURLException, Exception {
		Signature signature = wsQueryService.downloadSign(authentication, docHash);
		InputStream is = XOPHandler.getDocumentStream();
		signature.setContent(IOUtils.toByteArray(is));
		return signature;
	}
	
	/**
	 * Permite obtener los bytes de un documento
	 * @param docHash hash del documento
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public byte[] getDocument(String docHash) 
			throws MalformedURLException, Exception {
		wsQueryService.downloadDocument(authentication, docHash);
		InputStream is = XOPHandler.getDocumentStream();
		return IOUtils.toByteArray(is);
		
	}
	
	/**
	 * Permite crear una peticion
	 * @param request peticion a crear
	 * @return
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public String createRequest(Request request) 
			throws MalformedURLException, Exception {
		return wsModifyService.createRequest(authentication, request);
	}

	/**
	 * Permite enviar una peticion
	 * @param requestHash hash de la peticion
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public void sendRequest(StringHolder requestHash) 
			throws MalformedURLException, Exception {
		wsModifyService.sendRequest(authentication, requestHash);
	}

	/**
	 * Permite insertar un documento a una peticion
	 * @param requestHash hash de la peticion
	 * @param document documento a insertar
	 * @return
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public String insertDocument(String requestHash, Document document) 
			throws MalformedURLException, Exception {
		return wsModifyService.insertDocument(authentication, requestHash,document);
	}
}
