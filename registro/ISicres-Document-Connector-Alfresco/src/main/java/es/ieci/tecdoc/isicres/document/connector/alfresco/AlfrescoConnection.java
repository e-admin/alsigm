package es.ieci.tecdoc.isicres.document.connector.alfresco;

import org.alfresco.webservice.authentication.AuthenticationFault;
import org.alfresco.webservice.authoring.AuthoringServiceSoapBindingStub;
import org.alfresco.webservice.content.ContentServiceSoapBindingStub;
import org.alfresco.webservice.repository.RepositoryServiceSoapBindingStub;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.WebServiceFactory;
import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoConnectorConfigurationVO;



/**
 * 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class AlfrescoConnection {

	private static final Logger log = Logger.getLogger(AlfrescoConnection.class);
	
	private RepositoryServiceSoapBindingStub repository;
	private ContentServiceSoapBindingStub  contentRepository;
	private AuthoringServiceSoapBindingStub authoringService;
	
	
	/**
	 * Inicio de la conexión
	 * @throws Exception 
	 * 
	 */
	public void connection(AlfrescoConnectorConfigurationVO connectorVO) throws Exception{			
		// Se establece la ruta de conexion cn el api
		WebServiceFactory.setEndpointAddress(connectorVO.getPathAPI());
			
		//Se inicia Sesion en Alfresco
		try {
			AuthenticationUtils.startSession(connectorVO.getUsuario(), connectorVO.getPass());
		} catch (AuthenticationFault e) {
			// TODO Auto-generated catch block
			log.error("Error al establecer la conexion", e);
			throw e;
		}				
					
		// Se recupera el servicio para acceder al repositorio
		repository = WebServiceFactory.getRepositoryService();
		contentRepository = WebServiceFactory.getContentService();
		authoringService = WebServiceFactory.getAuthoringService();
	}
	
	/**
	 * Se cierra la conexion
	 */
	public void endConnection(){		
		AuthenticationUtils.endSession();		
	}

	/**
	 * @return the repository
	 */
	public RepositoryServiceSoapBindingStub getRepository() {
		return repository;
	}

	/**
	 * @param repository the repository to set
	 */
	public void setRepository(RepositoryServiceSoapBindingStub repository) {
		this.repository = repository;
	}

	/**
	 * @return the contentRepository
	 */
	public ContentServiceSoapBindingStub getContentRepository() {
		return contentRepository;
	}

	/**
	 * @param contentRepository the contentRepository to set
	 */
	public void setContentRepository(ContentServiceSoapBindingStub contentRepository) {
		this.contentRepository = contentRepository;
	}

	/**
	 * @return the authoringService
	 */
	public AuthoringServiceSoapBindingStub getAuthoringService() {
		return authoringService;
	}

	/**
	 * @param authoringService the authoringService to set
	 */
	public void setAuthoringService(AuthoringServiceSoapBindingStub authoringService) {
		this.authoringService = authoringService;
	}	
	
	
}