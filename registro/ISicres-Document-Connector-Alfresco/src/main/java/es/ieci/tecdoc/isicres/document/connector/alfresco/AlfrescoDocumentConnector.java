package es.ieci.tecdoc.isicres.document.connector.alfresco;
        
import java.rmi.RemoteException;
import java.util.List;

import org.alfresco.webservice.authoring.AuthoringServiceSoapBindingStub;
import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.content.ContentServiceSoapBindingStub;
import org.alfresco.webservice.repository.RepositoryFault;
import org.alfresco.webservice.repository.RepositoryServiceSoapBindingStub;
import org.alfresco.webservice.repository.UpdateResult;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.types.ContentFormat;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.util.Constants;
import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.document.connector.ISicresDocumentConnector;
import es.ieci.tecdoc.isicres.document.connector.alfresco.keys.AlfrescoKeys;
import es.ieci.tecdoc.isicres.document.connector.alfresco.utils.MimeTypes;
import es.ieci.tecdoc.isicres.document.connector.alfresco.utils.UtilsAlfresco;
import es.ieci.tecdoc.isicres.document.connector.alfresco.utils.UtilsFile;
import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoConnectorConfigurationVO;
import es.ieci.tecdoc.isicres.document.connector.exception.IsicresDocumentConnectorException;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresAbstractCriterioBusquedaVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresAbstractDocumentVO;

	   
/**
 * 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class AlfrescoDocumentConnector implements ISicresDocumentConnector{

	private static final Logger log = Logger.getLogger(AlfrescoDocumentConnector.class);
	
	/**
	 * Inserta un nuevo contenido en el gestor
	 * @throws Exception 
	 */
	public ISicresAbstractDocumentVO create(ISicresAbstractDocumentVO document){

		
		// Instancias
		UtilsAlfresco utilsAlfresco = new UtilsAlfresco();		
		AlfrescoConnection alfrescoConnection = new AlfrescoConnection();

		// Se establece la conexion con alfresco
		try {
			alfrescoConnection.connection((AlfrescoConnectorConfigurationVO)document.getConfiguration());
		} catch (Exception e) {
			throw new IsicresDocumentConnectorException(
					"Impossible to save the file  [" + document.getName() + "]",
					e);
		}
		
		// Se recupera el repositorio
		RepositoryServiceSoapBindingStub repository = alfrescoConnection.getRepository();
		ContentServiceSoapBindingStub contentRepository = alfrescoConnection.getContentRepository();
			
		// Carga del objeto CML de Alfresco
		CML cml = utilsAlfresco.createCML(document);		
		
		// Se inserta el objeto en el repositorio
		UpdateResult[] result;
		try {
			result = repository.update(cml);		
			String uuid = result[0].getDestination().getUuid();
		
			// Se asigna el uuid al document
			document.setId(uuid);
			
			// Insertar bytes en el objeto		
			Reference contentNode = result[0].getDestination();
			MimeTypes mimeTypes = new MimeTypes();
			String mimeType = mimeTypes.getMimeType(document.getName());
			ContentFormat format = new ContentFormat(mimeType, AlfrescoKeys.ENCODING_UTF8);		
			Content contentRef  = contentRepository.write(contentNode,Constants.PROP_CONTENT, document.getContent(), format);
			byte[] bytes = UtilsFile.getByteContent(contentRef);
			document.setContent(bytes);
			
			// Convertir en versionable el contenido
			cml = utilsAlfresco.makeVersionable(document);
			repository.update(cml);
			
			// Se cierra la conexion con alfresco
			alfrescoConnection.endConnection();
		} catch (RepositoryFault e) {
			// TODO Auto-generated catch block
			log.error("Error en el repositorio",e);
			throw new IsicresDocumentConnectorException(
					"Impossible to save the file  [" + document.getName() + "]",
					e);
		} catch (RemoteException e) {
			log.error("Error en la conexion con Alfresco",e);
			throw new IsicresDocumentConnectorException(
					"Impossible to save the file  [" + document.getName() + "]",
					e);
		} catch (Exception e){
			log.error(e);
			throw new IsicresDocumentConnectorException(
					"Impossible to save the file  [" + document.getName() + "]",
					e);
		}		
		
		return document;
	}

	/**
	 * Elimina un contenido del gestor
	 */
	public void delete(ISicresAbstractDocumentVO document){	
		// Instancias
		UtilsAlfresco utilsAlfresco = new UtilsAlfresco();		
		AlfrescoConnection alfrescoConnection = new AlfrescoConnection();

		// Se establece la conexion con alfresco
		try {
			alfrescoConnection.connection((AlfrescoConnectorConfigurationVO)document.getConfiguration());
		} catch (Exception e) {
			throw new IsicresDocumentConnectorException(
					"Impossible to save the file  [" + document.getName() + "]",
					e);
		}
		
		// Se recupera el repositorio
		RepositoryServiceSoapBindingStub repository = alfrescoConnection.getRepository();
		
		// Carga del objeto CML de Alfresco
		CML cml = utilsAlfresco.deleteCML(document);
		
		// Se elimina el objeto en el repositorio
		try {
			repository.update(cml);
		} catch (RepositoryFault e) {
			// TODO Auto-generated catch block
			log.error("Error en el repositorio",e);
			throw new IsicresDocumentConnectorException(
					"Impossible to save the file  [" + document.getName() + "]",
					e);
		} catch (RemoteException e) {
			log.error("Error en la conexion con Alfresco",e);
			throw new IsicresDocumentConnectorException(
					"Impossible to save the file  [" + document.getName() + "]",
					e);
		} 		
	}

	
	public List find(ISicresAbstractCriterioBusquedaVO criterioBusqueda) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @throws Exception 
	 * 
	 */
	public ISicresAbstractDocumentVO retrieve(ISicresAbstractDocumentVO document) {
		
		// Instancias
		UtilsAlfresco utilsAlfresco = new UtilsAlfresco();		
		AlfrescoConnection alfrescoConnection = new AlfrescoConnection();

		// Se establece la conexion con alfresco
		try {
			alfrescoConnection.connection((AlfrescoConnectorConfigurationVO)document.getConfiguration());
		} catch (Exception e) {
			throw new IsicresDocumentConnectorException(
					"Impossible to save the file  [" + document.getName() + "]",
					e);
		}
		
		// Se recupera el repositorio
		RepositoryServiceSoapBindingStub repository = alfrescoConnection.getRepository();
		ContentServiceSoapBindingStub contentRepository = alfrescoConnection.getContentRepository();
		
		// Se recuperan los bytes y se cargarn en document		
		try {
			document = utilsAlfresco.search(document, null, contentRepository,repository);		
		} catch (Exception e) {
			log.error("Error al retornar los byte",e);
			throw new IsicresDocumentConnectorException(
					"Impossible to save the file  [" + document.getName() + "]",
					e);
		}
		return document;
	}

	/**
	 * 
	 */
	public ISicresAbstractDocumentVO update(ISicresAbstractDocumentVO document) {		
		// Instancias
		UtilsAlfresco utilsAlfresco = new UtilsAlfresco();		
		AlfrescoConnection alfrescoConnection = new AlfrescoConnection();

		// Se establece la conexion con alfresco
		try {
			alfrescoConnection.connection((AlfrescoConnectorConfigurationVO)document.getConfiguration());
		} catch (Exception e) {
			throw new IsicresDocumentConnectorException(
					"Impossible to save the file  [" + document.getName() + "]",
					e);
		}
		
		// Se recupera el repositorio		
		AuthoringServiceSoapBindingStub authoringService = alfrescoConnection.getAuthoringService();
		ContentServiceSoapBindingStub contentRepository = alfrescoConnection.getContentRepository();
		
		// Se actualiza el repositorio
		try {
			document = utilsAlfresco.updateCML(document, authoringService, contentRepository);
		} catch (Exception e) {
			throw new IsicresDocumentConnectorException(
					"Impossible to save the file  [" + document.getName() + "]",
					e);
		}
				
		return document;
	}
}
