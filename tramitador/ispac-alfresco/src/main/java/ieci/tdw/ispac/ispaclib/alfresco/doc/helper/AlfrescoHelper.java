package ieci.tdw.ispac.ispaclib.alfresco.doc.helper;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.alfresco.doc.AlfrescoConstants;
import ieci.tdw.ispac.ispaclib.gendoc.config.ConfigConstants;
import ieci.tdw.ispac.ispaclib.gendoc.config.Mapping;
import ieci.tdw.ispac.ispaclib.gendoc.config.Repository;
import ieci.tdw.ispac.ispaclib.gendoc.config.Token;
import ieci.tdw.ispac.ispaclib.gendoc.config.TokenEvaluator;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.HTTPUtils;
import ieci.tdw.ispac.ispaclib.utils.MapUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.content.ContentServiceSoapBindingStub;
import org.alfresco.webservice.repository.RepositoryServiceSoapBindingStub;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.types.CMLCreate;
import org.alfresco.webservice.types.CMLUpdate;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.Node;
import org.alfresco.webservice.types.ParentReference;
import org.alfresco.webservice.types.Predicate;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.Utils;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.document.connector.alfresco.AlfrescoConnection;
import es.ieci.tecdoc.isicres.document.connector.alfresco.keys.AlfrescoKeys;
import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoAspectVO;
import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoConnectorConfigurationVO;
import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoDatosEspecificosVO;
import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoDatosEspecificosValueVO;

public class AlfrescoHelper {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(AlfrescoHelper.class);

	private static final Store STORE = new Store(Constants.WORKSPACE_STORE, AlfrescoKeys.NAME_SPACE_STORE);
	
	
	public static AlfrescoConnectorConfigurationVO getConfiguration(Repository repository) {

		AlfrescoConnectorConfigurationVO configuration = new AlfrescoConnectorConfigurationVO();
		
		configuration.setIp(repository.getProperty(AlfrescoConstants.ALFRESCO_IP_ADDRESS));
		configuration.setPuerto(repository.getProperty(AlfrescoConstants.ALFRESCO_PORT));
		configuration.setUsuario(repository.getProperty(AlfrescoConstants.ALFRESCO_USER));
		configuration.setPass(repository.getProperty(AlfrescoConstants.ALFRESCO_PASSWORD));

		return configuration;
	}

	public static String getDocName(Repository repository, String properties) throws Exception {
		
		String tokenDocumentName = repository.getProperty(AlfrescoConstants.DOCUMENT_NAME_TOKEN);
		Token token = repository.getTokens().getToken(tokenDocumentName);
		return TokenEvaluator.evaluateToken(repository, token, properties);
	}


	public static AlfrescoDatosEspecificosVO getDatosEspecificos(Repository repository, String properties) throws Exception {
		
		AlfrescoDatosEspecificosVO alfrescoDatosEspecificosVO = new AlfrescoDatosEspecificosVO();
		
		alfrescoDatosEspecificosVO.setValues(getDatosEspecificosValues(repository, properties));
		alfrescoDatosEspecificosVO.setListAspects(getAspects(repository));
		
		alfrescoDatosEspecificosVO.setPathSpace(TokenEvaluator.evaluateTokenString(repository, repository.getFolderPath(), properties));
		alfrescoDatosEspecificosVO.setNameStore(repository.getProperty(AlfrescoConstants.ALFRESCO_STORE_NAMESPACE));
		alfrescoDatosEspecificosVO.setFileKey(repository.getProperty(AlfrescoConstants.ALFRESCO_FILE_KEY));
		
		return alfrescoDatosEspecificosVO;
	}

	public static AlfrescoDatosEspecificosVO getDatosEspecificos(Repository repository) {
		
		AlfrescoDatosEspecificosVO alfrescoDatosEspecificosVO = new AlfrescoDatosEspecificosVO();
		
		alfrescoDatosEspecificosVO.setValues(getDatosEspecificosValues(repository));
		alfrescoDatosEspecificosVO.setListAspects(getAspects(repository));
		
		alfrescoDatosEspecificosVO.setPathSpace(repository.getFolderPath());
		alfrescoDatosEspecificosVO.setNameStore(repository.getProperty(AlfrescoConstants.ALFRESCO_STORE_NAMESPACE));
		alfrescoDatosEspecificosVO.setFileKey(repository.getProperty(AlfrescoConstants.ALFRESCO_FILE_KEY));
		
		return alfrescoDatosEspecificosVO;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, AlfrescoDatosEspecificosValueVO> getDatosEspecificosValues(Repository repository, String properties) {
		
		Map<String, AlfrescoDatosEspecificosValueVO> map = new HashMap<String, AlfrescoDatosEspecificosValueVO>();
		
		if (StringUtils.isNotEmpty(properties)) {
		
			List<Mapping> mappings = repository.getMetaDataMappings().getMappings();
			for (int i=0; i < mappings.size(); i++) {
				
				Mapping mapping = mappings.get(i);
				String value = null;
				
				try {
					
					if (StringUtils.equals(mapping.getSource().getType(), 
							ConfigConstants.CONFIG_MAPPING_SOURCE_TYPE_CONSTANT)){
						value = mapping.getSource().getValue();
					} else if (StringUtils.equals(mapping.getSource().getType(), 
							ConfigConstants.CONFIG_MAPPING_SOURCE_TYPE_DYNAMIC)){
						value = TokenEvaluator.evaluateDynamicToken(mapping.getSource().getValue());
					} else if (StringUtils.equals(mapping.getSource().getType(), 
							ConfigConstants.CONFIG_MAPPING_SOURCE_TYPE_TOKEN)){
						value = TokenEvaluator.evaluateToken(repository, 
								repository.getTokens().getToken(mapping.getSource().getValue()), properties);
					}
					
					if (StringUtils.isNotBlank(value)) {
						
						AlfrescoDatosEspecificosValueVO alfrescoDatosEspecificosValueVO = new AlfrescoDatosEspecificosValueVO();

						alfrescoDatosEspecificosValueVO.setName(mapping.getDestination().getValue());
						alfrescoDatosEspecificosValueVO.setValue(TokenEvaluator.formatValue(value,
								mapping.getSource().getFormat(), 
								mapping.getDestination().getFormat(), 
								mapping.getDestination().getType()));
						alfrescoDatosEspecificosValueVO.setType(getAlfrescoType(mapping.getDestination().getType()));
						alfrescoDatosEspecificosValueVO.setAspectName(mapping.getDestination().getAttribute("aspect"));
						alfrescoDatosEspecificosValueVO.setContentName(mapping.getDestination().getAttribute("model"));

						// Se carga un map con todos los metadatos
						map.put(alfrescoDatosEspecificosValueVO.getName(), alfrescoDatosEspecificosValueVO);
					}
					
				} catch (Exception e) {
					logger.warn("Error al obtener el valor #" + i, e);
				}
			}
		}
		
		return map;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, AlfrescoDatosEspecificosValueVO> getDatosEspecificosValues(Repository repository) {
		
		Map<String, AlfrescoDatosEspecificosValueVO> map = new HashMap<String, AlfrescoDatosEspecificosValueVO>();
		
		List<Mapping> mappings = repository.getMetaDataMappings().getMappings();
		for (int i=0; i < mappings.size(); i++) {
			
			Mapping mapping = mappings.get(i);
			
			try {
				
				AlfrescoDatosEspecificosValueVO alfrescoDatosEspecificosValueVO = new AlfrescoDatosEspecificosValueVO();

				alfrescoDatosEspecificosValueVO.setName(mapping.getDestination().getValue());
				alfrescoDatosEspecificosValueVO.setType(getAlfrescoType(mapping.getDestination().getType()));
				alfrescoDatosEspecificosValueVO.setAspectName(mapping.getDestination().getAttribute("aspect"));
				alfrescoDatosEspecificosValueVO.setContentName(mapping.getDestination().getAttribute("model"));

				// Se carga un map con todos los metadatos
				map.put(alfrescoDatosEspecificosValueVO.getName(), alfrescoDatosEspecificosValueVO);
				
			} catch (Exception e) {
				logger.warn("Error al obtener el valor #" + i, e);
			}
		}
		
		return map;
	}

	private static List<AlfrescoAspectVO> getAspects(Repository repository) {
		
		List<AlfrescoAspectVO> aspects = new ArrayList<AlfrescoAspectVO>();
	
		// Obtener los aspectos configurados
		String aspectsStr = repository.getProperty(AlfrescoConstants.ALFRESCO_ASPECTS);
		if (StringUtils.isNotBlank(aspectsStr)) {
			String[] aspectsArray = aspectsStr.split(",");
			for (int i = 0; i < aspectsArray.length; i++) {
				AlfrescoAspectVO aspect = createAspect(aspectsArray[i]);
				if (aspect != null) {
					aspects.add(aspect);
				}
			}
		}
	
		return aspects;
	}

	private static AlfrescoAspectVO createAspect(String aspectStr) {
		AlfrescoAspectVO aspect = null;

		if (StringUtils.isNotBlank(aspectStr)) {
			String[] tokens = StringUtils.split(aspectStr.trim(), "{}");
			if (tokens.length > 1) {
				aspect = new AlfrescoAspectVO();
				aspect.setNameContent(tokens[0]);
				aspect.setNameAspect(tokens[1]);
			}
		}

		return aspect;
	}

	private static String getAlfrescoType(String internalType) {
		
		String type = null;
		
		if (ConfigConstants.CONFIG_DESTINATION_TYPE_INTEGER.equals(internalType)) {
			type = "int";
		} else if (ConfigConstants.CONFIG_DESTINATION_TYPE_FLOAT.equals(internalType)) {
			type = "float";
		} else if (ConfigConstants.CONFIG_DESTINATION_TYPE_DATE.equals(internalType)) {
			type = "date";
		} else if (ConfigConstants.CONFIG_DESTINATION_TYPE_DATETIME.equals(internalType)) {
			type = "datetime";
		} else if (ConfigConstants.CONFIG_DESTINATION_TYPE_BOOLEAN.equals(internalType)) {
			type = "boolean";
		} else if (ConfigConstants.CONFIG_DESTINATION_TYPE_CURRENCY.equals(internalType)) {
			type = "text";
		} else { // CONFIG_DESTINATION_TYPE_STRING
			type = "text";
		} 
			
		return type;
	}
	
	
	/*
	 * A IMPLEMENTAR EN EL CONECTOR COMÚN ???
	 */

	public static void createSession() throws Exception {
		// TODO Crear sesión con Alfresco?
	}

	public static void closeSession() throws Exception {
		// TODO Cerrar sesión con Alfresco?
	}

	public static String getRepositoryInfo(Repository repository) throws ISPACException {
		
		StringBuffer xml = new StringBuffer();
		
		// Obtener los siguientes valores
		long length = 0;
		long size = 0;
		long files = 0;

		try {

			// Obtener la información del repositorio invocando a un webscript
			// de Alfresco
			String webscriptURL = repository.getProperty(AlfrescoConstants.ALFRESCO_REPOSITORY_WEBSCRIPT_URL);
			if (StringUtils.isNotBlank(webscriptURL)) {

				// Credenciales
				Credentials credentials = new UsernamePasswordCredentials(
						repository.getProperty(AlfrescoConstants.ALFRESCO_USER),
						repository.getProperty(AlfrescoConstants.ALFRESCO_PASSWORD));
				
				byte[] response = HTTPUtils.get(webscriptURL, credentials);

				XmlFacade responseXML = new XmlFacade(new String(response));
				size = TypeConverter.parseLong(responseXML.get("/space/size"), 0);
				files = TypeConverter.parseLong(responseXML.get("/space/files"), 0);
			}

		}  catch (Exception e) {
			logger.error("Error al obtener la información del repositorio", e);
			throw new ISPACException("Error al obtener la información del repositorio", e);
		}

		// Componer la información del XML
		StringBuffer properties = new StringBuffer();
		properties.append(XmlTag.newTag("length", length));
		properties.append(XmlTag.newTag("size", size));
		properties.append(XmlTag.newTag("files", files));

		// Componer el XML completo
		xml.append(XmlTag.getXmlInstruction("ISO-8859-1"));
		xml.append(XmlTag.newTag("repository", properties.toString(), 
				XmlTag.newAttr("id", repository.getId())));
		
		if (logger.isInfoEnabled()) {
			logger.info("Información del repositorio: " + xml.toString());
		}

		return xml.toString();
	}

	public static boolean existsDocument(Repository repository, String uid) throws Exception {
		return (getContentObject(repository, uid) != null);
	}

	public static int getDocumentSize(Repository repository, String uid) throws Exception {
		
		int size = 0;
		
		Content content = getContentObject(repository, uid);
		if (content != null) {
			size = new Long(content.getLength()).intValue();
		}

		return size;
	}

	public static String getDocumentMimeType(Repository repository, String uid) throws Exception {
		
		String mimeType = null;
		
		Content content = getContentObject(repository, uid);
		if (content != null) {
			mimeType = content.getFormat().getMimetype();
		}

		return mimeType;
	}

//	public static AlfrescoDatosEspecificosVO getMetadatos(Repository repository, String uid, ISicresDocumentConnector connector) 
//			throws Exception {
//
//		AlfrescoDatosEspecificosVO datosEspecificos = null;
//		
//		// Componer información del documento
//		AlfrescoDocumentVO documentVO = new AlfrescoDocumentVO();
//		documentVO.setConfiguration(getConfiguration(repository));
//		documentVO.setId(uid);
//		
//		documentVO.setDatosEspecificos(getDatosEspecificos(repository));
//		
//	    // Se deberían obtener los metadatos sin obtener el contenido del documento (ver método siguiente)
//		ISicresAbstractDocumentVO document = connector.retrieve(documentVO);
//		if (document != null) {
//			datosEspecificos = (AlfrescoDatosEspecificosVO) document.getDatosEspecificos();
//		}
//		
//		return datosEspecificos;
//	}

	public static AlfrescoDatosEspecificosVO getMetadatos(Repository repository, String uid) throws Exception {
		
	    Node[] nodes = getNodes(repository, uid);
	    AlfrescoDatosEspecificosVO datosEspecificosVO = getDatosEspecificos(repository);
	    
	    Map<String, AlfrescoDatosEspecificosValueVO> metadata = new HashMap<String, AlfrescoDatosEspecificosValueVO>();
	    if (nodes != null){
	    	for(int i=0;i<nodes.length;i++){
	    		Node node = nodes[i];
	    		NamedValue[] prop = node.getProperties();	  
	    		for(int f=0;f<prop.length;f++){
	    			NamedValue namedValue = (NamedValue)prop[f];
	    			String name = namedValue.getName().split("}")[1];
	    			AlfrescoDatosEspecificosValueVO alfrescoDatosEspecificosValueVO = null;			
	    			alfrescoDatosEspecificosValueVO = (AlfrescoDatosEspecificosValueVO) datosEspecificosVO.getValues().get(name);
                    if (alfrescoDatosEspecificosValueVO!=null) {
                    	alfrescoDatosEspecificosValueVO.setValue(namedValue.getValue());
                    	metadata.put(name, alfrescoDatosEspecificosValueVO);
                    }
                }
            }
        }
	    
	    datosEspecificosVO.setValues(metadata);
	    
		return datosEspecificosVO;
	}

	public static String getMetadato(Repository repository, String uid, String key) throws Exception {
		
	    Node[] nodes = getNodes(repository, uid);

	    if (!ArrayUtils.isEmpty(nodes) && StringUtils.isNotBlank(key)){
	    	
	    	for(int i=0;i<nodes.length;i++){
	    		
	    		Node node = nodes[i];
	    		NamedValue[] prop = node.getProperties();
	    		
	    		for(int f=0;f<prop.length;f++){
	    			
	    			NamedValue namedValue = (NamedValue)prop[f];
	    			String name = namedValue.getName().split("}")[1];
	    			
	    			if (StringUtils.equalsNullEmpty(key, name)) {
	    				return namedValue.getValue();
	    			}
                }
            }
        }
	    
		return null;
	}

	public static void setMetadato(Repository repository, String uid, String name, String value) throws Exception {

		Node[] nodes = getNodes(repository, uid);
		if (!ArrayUtils.isEmpty(nodes)) {
			
			AlfrescoConnection alfrescoConnection = new AlfrescoConnection();
			
			try {
				
				alfrescoConnection.connection(getConfiguration(repository));

				// Actualizar la lista de metadatos con el nuevo valor
		    	NamedValue[] titledProps = nodes[0].getProperties();	  
	    		for (int f = 0; f < titledProps.length; f++){
	    			
	    			NamedValue namedValue = (NamedValue)titledProps[f];
	    			String namedValueName = namedValue.getName().split("}")[1];
	    			
	    			if (StringUtils.equalsNullEmpty(namedValueName, name)) {
	    				namedValue.setValue(value);
	    			}
	            }
	
				// Carga del objeto CML
				Reference reference = new Reference(STORE, uid, null);	
				Predicate predicate = new Predicate(new Reference[] { reference }, null, null);	
		 		CMLUpdate cmlUpdate = new CMLUpdate(titledProps, predicate, null);
		 	     	    
				CML cml = new CML();
				cml.setUpdate(new CMLUpdate[] { cmlUpdate });
	
				alfrescoConnection.getRepository().update(cml);
			
			} finally {
				alfrescoConnection.endConnection();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static String updatePropertiesXML(Repository repository, String uid, String newProperties) throws Exception {

	    String properties = "";

	    XmlFacade newPropertiesXML = null;
	    if (StringUtils.isNotBlank(newProperties)) {
	    	newPropertiesXML = new XmlFacade(newProperties);
	    }
	    
		AlfrescoDatosEspecificosVO datosEspecificos = getMetadatos(repository, uid);
		if (datosEspecificos != null) {
			Map<String, AlfrescoDatosEspecificosValueVO> values = datosEspecificos.getValues();
			if (MapUtils.isNotEmpty(values)) {
				for (Iterator<String> keyIt = values.keySet().iterator(); keyIt.hasNext();) {
					String name = keyIt.next();
					
					String value = null;
					
					if (newPropertiesXML != null) {
						value = newPropertiesXML.get("/doc_properties/property[name=\"" + name + "\"]/value");
					} 

					if (value == null) {
						AlfrescoDatosEspecificosValueVO alfrescoDatosEspecificosValueVO = (AlfrescoDatosEspecificosValueVO) values.get(name);
						if (alfrescoDatosEspecificosValueVO != null) {
							value = alfrescoDatosEspecificosValueVO.getValue();
						}
					}
					
                	properties += XmlTag.newTag("property", XmlTag.newTag("name", name) 
                			+ XmlTag.newTag("value", XmlTag.newCDATA(StringUtils.nullToEmpty(value))));
				}
			}
		}

		String xml = XmlTag.getXmlInstruction("ISO-8859-1") + XmlTag.newTag("doc_properties", properties);
		
		return xml;
	}
	
	public static void checkFolderPath(Repository repository, String properties) throws Exception {
		
		AlfrescoConnection alfrescoConnection = new AlfrescoConnection();
		
		try {
			
			alfrescoConnection.connection(getConfiguration(repository));

			if (logger.isInfoEnabled()) {
				logger.info("folderPath: " + repository.getFolderPath());
			}

			// Evaluar el espacio para almacenar documentos
			String folderPath = TokenEvaluator.evaluateTokenString(repository, repository.getFolderPath(), properties);
			if (logger.isInfoEnabled()) {
				logger.info("folderPath evaluado: " + folderPath);
			}
			
			checkFolderPath(alfrescoConnection, folderPath);

		} finally {
			alfrescoConnection.endConnection();
		}
	}
	
	private static void checkFolderPath(AlfrescoConnection alfrescoConnection, String folderPath) 
			throws Exception {
		
		// Servicio de gestión del repositorio
		RepositoryServiceSoapBindingStub repositoryService = alfrescoConnection.getRepository();
		
		try {
			
			if (StringUtils.isNotBlank(folderPath)) {
				
				// Comprobar si existe el espacio
				Reference reference = new Reference(STORE, null, folderPath);
				repositoryService.get(new Predicate(new Reference[] { reference }, STORE, null));
				
				if (logger.isInfoEnabled()) {
					logger.info("folderPath existente [" + folderPath + "]");
				}
			}
			
		} catch (Exception e) {		
			
			if (logger.isInfoEnabled()) {
				logger.info("creando folderPath [" + folderPath + "]...");
			}

			String spaceName = getSpaceName(folderPath);
			String parentFolderPath = getParentFolderPath(folderPath);
			
			// Comprobar que exista el espacio padre
			checkFolderPath(alfrescoConnection, parentFolderPath);
			
			// Crear el espacio
			ParentReference parentReference = new ParentReference(STORE, null,
					parentFolderPath, Constants.ASSOC_CONTAINS, 
					Constants.createQNameString(Constants.NAMESPACE_CONTENT_MODEL, spaceName));

			CMLCreate create = new CMLCreate("1", parentReference, null, null, null, 
					Constants.TYPE_FOLDER, new NamedValue[] { 
							Utils.createNamedValue(Constants.PROP_NAME, spaceName), 
							Utils.createNamedValue(Constants.PROP_TITLE, spaceName),
							Utils.createNamedValue(Constants.PROP_DESCRIPTION, spaceName) });

			CML cml = new CML();
			cml.setCreate(new CMLCreate[] { create });
			repositoryService.update(cml);				
			
			if (logger.isInfoEnabled()) {
				logger.info("folderPath creado [" + folderPath + "]");
			}
		}
	}
	
	private static String getParentFolderPath(String folderPath) {
		return folderPath.substring(0, folderPath.lastIndexOf("/"));
	}
	
	private static String getSpaceName(String folderPath) {
		return folderPath.substring(folderPath.lastIndexOf(":") + 1);
	}

	
	/*
	 * A ELIMINAR 
	 */
	
	private static Content getContentObject(Repository repository, String uid) throws Exception {

		Content content = null;
		
		if (StringUtils.isNotBlank(uid)) {
			
			AlfrescoConnection alfrescoConnection = new AlfrescoConnection();
			
			try { 
				alfrescoConnection.connection(getConfiguration(repository));
				
				// Servicio de gestión de contenidos
				ContentServiceSoapBindingStub contentService = alfrescoConnection.getContentRepository();
				
				Reference reference = new Reference(STORE, uid, null);	
			    Content[] readResult = contentService.read(new Predicate(new Reference[]{reference}, STORE, null), Constants.PROP_CONTENT);
			    if (readResult != null) {
				    content = (Content) readResult[0];		    
				}
			    
			} finally {
				alfrescoConnection.endConnection();
			}
		}
		
		return content;
	}

	private static Node[] getNodes(Repository repository, String uid) throws Exception {
		
		AlfrescoConnection alfrescoConnection = new AlfrescoConnection();
		
		try {
			alfrescoConnection.connection(getConfiguration(repository));
			
			// Servicio de gestión de repositorio
			RepositoryServiceSoapBindingStub repositoryService = alfrescoConnection.getRepository();
	
			Reference reference = new Reference(STORE, uid, null);	
			Predicate predicate = new Predicate(new Reference[]{reference}, null, null);
			
		    return repositoryService.get(predicate);
		} finally {
			alfrescoConnection.endConnection();
		}
	}
	
}
