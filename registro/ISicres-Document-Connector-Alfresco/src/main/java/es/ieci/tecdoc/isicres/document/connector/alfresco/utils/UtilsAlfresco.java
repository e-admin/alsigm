package es.ieci.tecdoc.isicres.document.connector.alfresco.utils;

import java.io.File;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.alfresco.webservice.authoring.AuthoringServiceSoapBindingStub;
import org.alfresco.webservice.authoring.CheckoutResult;
import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.content.ContentServiceSoapBindingStub;
import org.alfresco.webservice.repository.RepositoryServiceSoapBindingStub;
import org.alfresco.webservice.repository.UpdateResult;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.types.CMLAddAspect;
import org.alfresco.webservice.types.CMLCreate;
import org.alfresco.webservice.types.CMLDelete;
import org.alfresco.webservice.types.ContentFormat;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.Node;
import org.alfresco.webservice.types.ParentReference;
import org.alfresco.webservice.types.Predicate;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.ContentUtils;
import org.alfresco.webservice.util.Utils;

import es.ieci.tecdoc.isicres.document.connector.alfresco.keys.AlfrescoKeys;
import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoAspectVO;
import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoDatosEspecificosValueVO;
import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoDatosEspecificosVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresAbstractDocumentVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresDatosEspecificos;

/**
 * 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class UtilsAlfresco {

	protected static final Store STORE = new Store(Constants.WORKSPACE_STORE, AlfrescoKeys.NAME_SPACE_STORE);
	
	/**
	 * Metodo que carga el objeto contenedor de metadatos de alfresco
	 * @param document
	 */
	public CML createCML(ISicresAbstractDocumentVO document){
		
		// VO - Config
		// ISicresAlfrescoDatosEspecificosVO
		AlfrescoDatosEspecificosVO datosEspecificosVO = (AlfrescoDatosEspecificosVO) document.getDatosEspecificos();
		
		// Metadatos
		NamedValue[] titledProps = this.chargeProperties(datosEspecificosVO);
		titledProps[0] = Utils.createNamedValue(Constants.PROP_NAME, System.currentTimeMillis() + "_" + document.getName());			

		// Carga de aspectos
		Iterator it = datosEspecificosVO.getListAspects().iterator();
		CMLAddAspect[] addAspects = new CMLAddAspect[datosEspecificosVO.getListAspects().size()+1];
		
		// aspect generico de alfresco (nombre, descripcion, fecha)
 		CMLAddAspect addAspect = new CMLAddAspect(Constants.ASPECT_TITLED, null, null, "1");
 		
 		addAspects[0] = addAspect;
 		int i = 1;
 		// aspectos especificos de cada carga
		while(it.hasNext()){					
			AlfrescoAspectVO aspect = (AlfrescoAspectVO)it.next();
			String aspectString = Constants.createQNameString(aspect.getNameContent(), aspect.getNameAspect());			
			CMLAddAspect addAspectIsicres = new CMLAddAspect(aspectString, null, null, "1");	 		
	 		addAspects[i] = addAspectIsicres;
	 		i++;
		}
		
		// Carga del objeto CML
 		ParentReference parentReference = new ParentReference(STORE, null, datosEspecificosVO.getPathSpace(), Constants.ASSOC_CONTAINS, Constants.ASSOC_CONTAINS);
 		CMLCreate cmlCreate = new CMLCreate("1", parentReference, parentReference.getUuid(), Constants.ASSOC_CONTAINS, null, Constants.PROP_CONTENT, titledProps);
 	     	    
		CML cml = new CML();
		cml.setCreate(new CMLCreate[] {cmlCreate});		
 		cml.setAddAspect(addAspects);

 		return cml;
	}
	
	/**
	 * Metodo que carga los metadatos en un array NamedValue de Alfresco
	 * @param datosEspecificosVO
	 * @return
	 */
	private NamedValue[] chargeProperties(AlfrescoDatosEspecificosVO datosEspecificosVO){
		Iterator it = datosEspecificosVO.getValues().values().iterator();
		
		NamedValue[] titledProps = new NamedValue[datosEspecificosVO.getValues().size()+1];
		int i = 1;
		while(it.hasNext()){
			AlfrescoDatosEspecificosValueVO MapVO = (AlfrescoDatosEspecificosValueVO) it.next();
			titledProps[i] = Utils.createNamedValue(Constants.createQNameString(MapVO.getContentName(), MapVO.getName()),MapVO.getValue() );	
			i++;
		}		
		return titledProps;
	}
	
	
	/**
	 * Metodo que borra un documento
	 * @param document
	 * @param contentNode
	 * @return
	 */
	public CML deleteCML(ISicresAbstractDocumentVO document){
		// Reference
		String id = document.getId();
		Reference reference = new Reference(STORE, id, null);	
		Predicate predicate = new Predicate(new Reference[]{reference}, null, null);	
		CMLDelete delete = new CMLDelete(predicate);
		CML cml = new CML();
        cml.setDelete(new CMLDelete[]{delete});
		return cml;
	}
	
	/**
	 * Metodo que busca un contenido y devuelve un byte[] de este
	 * @param id
	 * @param path
	 * @param repository
	 * @return
	 * @throws Exception 
	 */
	public byte[] search(String id, String path, ContentServiceSoapBindingStub contentRepository) throws Exception{
		
		Reference reference = new Reference(STORE, id, path);	
			    
	    Content[] readResult = contentRepository.read(new Predicate(new Reference[]{reference}, STORE, null), Constants.PROP_CONTENT);
	    byte[] contentBytes = null;
	    if(readResult!=null){
		    Content content = (Content) readResult[0];		    
		    contentBytes = UtilsFile.getByteContent(content);
		}
		return contentBytes;
	}
	
	/**
	 * Metodo que busca un contenido y devuelve sus byte[] y el metadato FileName de este
	 * @param id
	 * @param path
	 * @param repository
	 * @return
	 * @throws Exception 
	 */
	public ISicresAbstractDocumentVO search(ISicresAbstractDocumentVO document, String path, ContentServiceSoapBindingStub contentRepository,RepositoryServiceSoapBindingStub repository ) throws Exception{
		
		Reference reference = new Reference(STORE, document.getId(), path);	
			    
	    Content[] readResult = contentRepository.read(new Predicate(new Reference[]{reference}, STORE, null), Constants.PROP_CONTENT);
	    byte[] contentBytes = null;
	    if(readResult!=null){
		    Content content = (Content) readResult[0];		    
		    contentBytes = UtilsFile.getByteContent(content);
		    document.setContent(contentBytes);		    
		}
		Predicate predicate = new Predicate(new Reference[]{reference}, null, null);	
	    Node[] nodes = repository.get(predicate);
	    
	    // Se recupera la propiedad FileName
	    AlfrescoDatosEspecificosVO datosEspecificosVO = (AlfrescoDatosEspecificosVO) document.getDatosEspecificos();
	    
	    Map metadata = new HashMap();
	    if (nodes != null){
	    	for(int i=0;i<nodes.length;i++){
	    		Node node = nodes[i];
	    		NamedValue[] prop = node.getProperties();	  
	    		for(int f=0;f<prop.length;f++){
	    			NamedValue namedValue = (NamedValue)prop[f];
	    			String name = namedValue.getName().split("}")[1];
	    			AlfrescoDatosEspecificosValueVO alfrescoDatosEspecificosValueVO = null;			
	    			alfrescoDatosEspecificosValueVO = (AlfrescoDatosEspecificosValueVO) datosEspecificosVO.getValues().get(name);
                    if (alfrescoDatosEspecificosValueVO!=null)
                    {
                        //document.setName(namedValue.getValue());
                    	alfrescoDatosEspecificosValueVO.setValue(namedValue.getValue());
                    	metadata.put(name, alfrescoDatosEspecificosValueVO);
                    }
                }
            }
        }	   
	    AlfrescoDatosEspecificosValueVO alfrescoDatosEspecificosValueVO =(AlfrescoDatosEspecificosValueVO)metadata.get(datosEspecificosVO.getFileKey());
	    datosEspecificosVO.setValues(metadata);
	    document.setName(alfrescoDatosEspecificosValueVO.getValue());
		return document;
	}
	
	/**
	 * Metodo que actualiza el contenido, versionandolo
	 * @param document
	 * @param authoringService
	 * @param contentRepository
	 * @return
	 * @throws RemoteException
	 */
	public ISicresAbstractDocumentVO updateCML(ISicresAbstractDocumentVO document, AuthoringServiceSoapBindingStub authoringService, ContentServiceSoapBindingStub contentRepository
	) throws  Exception{
		Reference reference = new Reference(STORE, document.getId(),null);
	    Predicate predicate = new Predicate(new Reference[]{reference}, null, null);
	    CheckoutResult checkOutResult = authoringService.checkout(predicate, null);
	    
	    // Get a reference to the working copy
        Reference workingCopyReference = checkOutResult.getWorkingCopies()[0];
        
        // Update the content of the working copy
        // Se obtiene el mimetype del document
        MimeTypes mimeTypes = new MimeTypes();
        String mimeType = mimeTypes.getMimeType(document.getName());
        ContentFormat format = new ContentFormat(mimeType, AlfrescoKeys.ENCODING_UTF8);
        
        // Se actualiza el document
        Content contentResult = contentRepository.write(workingCopyReference, Constants.PROP_CONTENT, document.getContent(), format);
        
        byte[] bytes = UtilsFile.getByteContent(contentResult);
		document.setContent(bytes);
        
        // Now check the working copy in with a description of the change made that will be recorded in the version history
        Predicate predicate_working_copy = new Predicate(new Reference[]{workingCopyReference}, null, null);
        NamedValue[] comments = new NamedValue[]{Utils.createNamedValue("description", "El contenido ha sido actualizado")};
        authoringService.checkin(predicate_working_copy, comments, false);
       
        return document;
	}
	
	/**
	 * Metod que hace que un contenido sea versionable
	 * @param document
	 * @return
	 */
    public CML makeVersionable(ISicresAbstractDocumentVO document){
	    
    	Reference reference = new Reference(STORE, document.getId(),null);
	    Predicate predicate = new Predicate(new Reference[]{reference}, null, null);
	    CMLAddAspect addAspect = new CMLAddAspect(Constants.ASPECT_VERSIONABLE, null, predicate, null); 
	    
	    // Create the content management language query
	    CML cml = new CML();
	    cml.setAddAspect(new CMLAddAspect[]{addAspect});
	    
	    return cml;
    }

}
