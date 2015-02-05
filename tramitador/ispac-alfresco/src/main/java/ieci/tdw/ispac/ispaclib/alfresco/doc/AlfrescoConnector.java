package ieci.tdw.ispac.ispaclib.alfresco.doc;

import ieci.tdw.ispac.api.connector.IDocConnector;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.alfresco.doc.helper.AlfrescoHelper;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.gendoc.config.Repositories;
import ieci.tdw.ispac.ispaclib.gendoc.config.RepositoriesCache;
import ieci.tdw.ispac.ispaclib.gendoc.config.Repository;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;
import ieci.tdw.ispac.ispaclib.utils.MapUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.document.connector.ISicresDocumentConnector;
import es.ieci.tecdoc.isicres.document.connector.alfresco.AlfrescoDocumentConnector;
import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoDatosEspecificosVO;
import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoDatosEspecificosValueVO;
import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoDocumentVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresAbstractDocumentVO;

/**
 * Conector documental con Alfresco.
 *
 */
public class AlfrescoConnector implements IDocConnector {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(AlfrescoConnector.class);
	
	/**
	 * Información del repositorio documental que se va a utilizar.
	 */
	private Repository repository = null;
	
	/**
	 * Conector documental.
	 */
	private ISicresDocumentConnector connector = new AlfrescoDocumentConnector();

	
	/**
	 * Constructor.
	 * @param ctx Contexto de cliente.
	 * @param repository Información del repositorio documental.
	 */
	protected AlfrescoConnector(Repository repository) {
		super();
		this.repository = repository;
	}

	/**
	 * Constructor.
	 * @param ctx Contexto de cliente.
	 * @throws ISPACException si ocurre algún error.
	 */
	protected AlfrescoConnector(ClientContext ctx) throws ISPACException {
		super();
		
		Repositories repositories = RepositoriesCache.getRepositories(ctx, AlfrescoConstants.DEFAULT_CONFIG_FILENAME);
		this.repository = repositories.getDefaultRepository();
	}

	
	protected AlfrescoConnector(ClientContext ctx, String repositoryAlias) throws ISPACException {
		super();
		
		Repositories repositories = RepositoriesCache.getRepositories(ctx, AlfrescoConstants.DEFAULT_CONFIG_FILENAME);
		
		if (StringUtils.isNotBlank(repositoryAlias)) {
			this.repository = repositories.getRepositoryByAlias(repositoryAlias);
		}
		
		if (this.repository == null) {
			this.repository = repositories.getDefaultRepository();
		}
	}

	protected AlfrescoConnector(ClientContext ctx, Integer repositoryId) throws ISPACException {
		super();
		
		Repositories repositories = RepositoriesCache.getRepositories(ctx, AlfrescoConstants.DEFAULT_CONFIG_FILENAME);
		
		if (repositoryId != null) {
			this.repository = repositories.getRepositoryById(repositoryId.intValue());
		}
		
		if (this.repository == null) {
			this.repository = repositories.getDefaultRepository();
		}
	}
	
	
	/**
	 * Obtiene una instancia del conector.
	 * @param ctx Contexto de cliente.
	 * @return Instancia del conector.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static synchronized AlfrescoConnector getInstance(ClientContext ctx) throws ISPACException {
		return new AlfrescoConnector(ctx);
	}

	/**
	 * Obtiene una instancia del conector.
	 * @param ctx Contexto de cliente.
	 * @param repositoryAlias Alias del repositorio.
	 * @return Instancia del conector.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static synchronized AlfrescoConnector getInstance(ClientContext ctx, String repositoryAlias) throws ISPACException {
		return new AlfrescoConnector(ctx, repositoryAlias);
	}

	/**
	 * Obtiene una instancia del conector.
	 * @param ctx Contexto de cliente.
	 * @param repositoryId Identificador del repositorio.
	 * @return Instancia del conector.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static synchronized AlfrescoConnector getInstance(ClientContext ctx, Integer repositoryId) throws ISPACException {
		return new AlfrescoConnector(ctx, repositoryId);
	}

	/**
	 * Crea una sesión de trabajo.
	 * @return Objeto con la sesión.
	 * @throws ISPACException si ocurre algún error.
	 */
	public Object createSession() throws ISPACException {

		try {
			
			AlfrescoHelper.createSession();

			if (logger.isInfoEnabled()) {
				logger.info("Sesión con Alfresco creada");
			}
			
			return null;

		} catch (Exception e) {
			logger.error("Error al crear la sesión con Alfresco", e);
			throw new ISPACException("Error al crear la sesión con Alfresco", e);
		}
	}

	/**
	 * Cierra la sesión de trabajo.
	 * @param session Sesión de trabajo.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void closeSession(Object session) throws ISPACException {
		
		try {
			
			AlfrescoHelper.closeSession();

			if (logger.isInfoEnabled()) {
				logger.info("Sesión con Alfresco finalizada");
			}

		} catch (Exception e) {
			logger.error("Error al finalizar la sesión con Alfresco", e);
			throw new ISPACException("Error al finalizar la sesión con Alfresco", e);
		}
	}

	/**
	 * Crea el repositorio de documentos.
	 * @param session Sesión de trabajo.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void createRepository(Object session) throws ISPACException {
		// No se utiliza
	}

	/**
	 * Obtiene la información del repositorio de documentos.
	 * @param session Sesión de trabajo.
	 * @param repId Identificador del repositorio.
	 * @return Información del repositorio.
	 * @throws ISPACException si ocurre algún error.
	 */
	public String getRepositoryInfo(Object session, String repId) throws ISPACException {
		
		String xml = null;
		
		try {

			xml = AlfrescoHelper.getRepositoryInfo(repository);
			
			if (logger.isDebugEnabled()) {
				logger.debug("Información del repositorio: " + xml);
			}
			
		}  catch (Exception e) {
			logger.error("Error al obtener la información del repositorio", e);
			throw new ISPACException("Error al obtener la información del repositorio", e);
		}

		return xml;
	}

	/**
	 * Comprueba si existe un documento.
	 * @param session Sesión de trabajo.
	 * @param uid UID del documento.
	 * @return true si el documento existe, false en caso contrario.
	 * @throws ISPACException si ocurre algún error.
	 */
	public boolean existsDocument(Object session, String uid) throws ISPACException {
		
		boolean exists = false;
		
		try {
			
			exists = AlfrescoHelper.existsDocument(repository, uid);

			if (logger.isInfoEnabled()) {
				logger.info("Documento [" + uid + "] existe? => " + exists);
			}

		} catch (Exception e) {
			logger.error("Error al comprobar si existe el documento", e);
			throw new ISPACException("Error al comprobar si existe el documento", e);
		}
		
		return exists;
	}

	/**
	 * Obtiene el contenido de un documento.
	 * @param session Sesión de trabajo.
	 * @param uid UID del documento.
	 * @param out OutputStream para escribir el documento.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void getDocument(Object session, String uid, OutputStream out) throws ISPACException {

		try {
			
			if (StringUtils.isNotBlank(uid)) {
				
				// Componer información del documento
				AlfrescoDocumentVO documentVO = new AlfrescoDocumentVO();
				documentVO.setConfiguration(AlfrescoHelper.getConfiguration(repository));
				documentVO.setId(uid);
				documentVO.setDatosEspecificos(AlfrescoHelper.getDatosEspecificos(repository));
				
				ISicresAbstractDocumentVO document = connector.retrieve(documentVO);
				if ((document != null) && !ArrayUtils.isEmpty(document.getContent())){
					out.write(document.getContent());
					
					if (logger.isInfoEnabled()) {
						logger.info("Contenido del documento [" + uid + "] leído: " + document.getContent().length + " bytes");
					}
				}
			}

		} catch (Exception e) {
			logger.error("Error al obtener el contenido del documento", e);
			throw new ISPACException("Error al obtener el contenido del documento", e);
		}
	}

	/**
	 * Crea un nuevo documento.
	 * @param session Sesión de trabajo.
	 * @param in InputStream para obtener el contenido del documento.
	 * @param length Tamaño del documento en bytes.
	 * @param properties Metadatos del documento.
	 * @return UID del documento creado.
	 * @throws ISPACException si ocurre algún error.
	 */
	public String newDocument(Object session, InputStream in, int length, String properties) throws ISPACException {

		try {
			
			String uid = null;
			
			// Comprobar si existe el espacio
			AlfrescoHelper.checkFolderPath(repository, properties);
			
			AlfrescoDocumentVO alfrescoDocumentVO = new AlfrescoDocumentVO();

			alfrescoDocumentVO.setName(AlfrescoHelper.getDocName(repository, properties));
			alfrescoDocumentVO.setContent(FileUtils.retrieveFile(in));
			alfrescoDocumentVO.setConfiguration(AlfrescoHelper.getConfiguration(repository));
			alfrescoDocumentVO.setDatosEspecificos(AlfrescoHelper.getDatosEspecificos(repository, properties));

			ISicresAbstractDocumentVO result = connector.create(alfrescoDocumentVO);
			if (result != null) {
				uid = result.getId();
				if (logger.isInfoEnabled()) {
					logger.info("Documento [" + uid + "] creado correctamente");
				}
			}

			return uid;
		    
		} catch (Exception e) {
			logger.error("Error al crear el documento", e);
			throw new ISPACException("Error al crear el documento", e);
		}
	}

	/**
	 * Modifica un documento
	 * @param session Sesión de trabajo.
	 * @param uid UID del documento.
	 * @param in InputStream para obtener el contenido del documento.
	 * @param length Tamaño del documento en bytes.
	 * @param properties Metadatos del documento.
	 * @return UID del documento modificado.
	 * @throws ISPACException si ocurre algún error.
	 */
	public String updateDocument(Object session, String uid, InputStream in, int length, String properties) throws ISPACException {

		try {
			
			if (logger.isDebugEnabled()) {
				logger.debug("Metadatos a actualizar [" + uid + "]: " + properties);
			}

			// Actualizar el XML de los metadatos almacenados con la nueva información
			properties = AlfrescoHelper.updatePropertiesXML(repository, uid, properties);
			
			if (logger.isDebugEnabled()) {
				logger.debug("Metadatos completos actualizados [" + uid + "]: " + properties);
			}

			// Comprobar si existe el espacio
			AlfrescoHelper.checkFolderPath(repository, properties);

			AlfrescoDocumentVO alfrescoDocumentVO = new AlfrescoDocumentVO();

			alfrescoDocumentVO.setId(uid);
			alfrescoDocumentVO.setName(AlfrescoHelper.getDocName(repository, properties));
			alfrescoDocumentVO.setContent(FileUtils.retrieveFile(in));
			alfrescoDocumentVO.setConfiguration(AlfrescoHelper.getConfiguration(repository));
			alfrescoDocumentVO.setDatosEspecificos(AlfrescoHelper.getDatosEspecificos(repository, properties));

			ISicresAbstractDocumentVO result = connector.update(alfrescoDocumentVO);

			if (result != null) {
				uid = result.getId();
				if (logger.isInfoEnabled()) {
					logger.info("Documento [" + uid + "] actualizado correctamente");
				}
			}

			return uid;
		    
		} catch (Exception e) {
			logger.error("Error al actualizar el documento", e);
			throw new ISPACException("Error al actualizar el documento", e);
		}
	}

	/**
	 * Elimina un documento
	 * @param session Sesión de trabajo.
	 * @param uid UID del documento.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void deleteDocument(Object session, String uid) throws ISPACException {

		try {
			
			AlfrescoDocumentVO alfrescoDocumentVO = new AlfrescoDocumentVO();
			alfrescoDocumentVO.setConfiguration(AlfrescoHelper.getConfiguration(repository));
			alfrescoDocumentVO.setId(uid);

			connector.delete(alfrescoDocumentVO);
			
			if (logger.isInfoEnabled()) {
				logger.info("Documento [" + uid + "] eliminado correctamente");
			}
			
		} catch (Exception e) {
			logger.error("Error al eliminar el documento", e);
			throw new ISPACException("Error al eliminar el documento", e);
		}
	}

	/**
	 * Obtiene el tamaño de un documento
	 * @param session Sesión de trabajo.
	 * @param uid UID del documento.
	 * @return Tamaño del documento en bytes.
	 * @throws ISPACException si ocurre algún error.
	 */
	public int getDocumentSize(Object session, String uid) throws ISPACException {
		
		try {
			
			int size = AlfrescoHelper.getDocumentSize(repository, uid);

			if (logger.isInfoEnabled()) {
				logger.info("Tamaño del documento [" + uid + "]: " + size + " bytes");
			}

			return size;
			
		} catch (Exception e) {
			logger.error("Error al obtener el tamaño del documento", e);
			throw new ISPACException("Error al obtener el tamaño del documento", e);
		}
	}

	/**
	 * Obtiene el tipo MIME de un documento
	 * @param session Sesión de trabajo.
	 * @param uid UID del documento.
	 * @return Tipo MIME del documento.
	 * @throws ISPACException si ocurre algún error.
	 */
	public String getMimeType(Object session, String uid) throws ISPACException {

		try {
			
			String mimeType = AlfrescoHelper.getDocumentMimeType(repository, uid);

			if (logger.isInfoEnabled()) {
				logger.info("Tipo MIME del documento [" + uid + "]: " + mimeType);
			}
			
			return mimeType;

		} catch (Exception e) {
			logger.error("Error al obtener el tipo MIME del documento", e);
			throw new ISPACException("Error al obtener el tipo MIME del documento", e);
		}
	}

	/**
	 * Obtiene los metadatos de un documento
	 * @param session Sesión de trabajo.
	 * @param uid UID del documento.
	 * @return Metadatos del documento.
	 * @throws ISPACException si ocurre algún error.
	 */
	@SuppressWarnings("unchecked")
	public String getProperties(Object session, String uid) throws ISPACException {

		try {
			
		    String properties = "";

			AlfrescoDatosEspecificosVO datosEspecificos = AlfrescoHelper.getMetadatos(repository, uid);
			if (datosEspecificos != null) {
				Map<String, AlfrescoDatosEspecificosValueVO> values = datosEspecificos.getValues();
				if (MapUtils.isNotEmpty(values)) {
					for (Iterator<String> keyIt = values.keySet().iterator(); keyIt.hasNext();) {
						String name = keyIt.next();
						String value = "";
						
						AlfrescoDatosEspecificosValueVO alfrescoDatosEspecificosValueVO = (AlfrescoDatosEspecificosValueVO) values.get(name);
						if (alfrescoDatosEspecificosValueVO != null) {
							value = alfrescoDatosEspecificosValueVO.getValue();
						}
						
                    	properties += XmlTag.newTag("property", XmlTag.newTag("name", name) 
                    			+ XmlTag.newTag("value", XmlTag.newCDATA(value)));
					}
				}
			}

			String xml = XmlTag.getXmlInstruction("ISO-8859-1") + XmlTag.newTag("doc_properties", properties);
			
			if (logger.isInfoEnabled()) {
				logger.info("Propiedades leídas correctamente [" + uid + "]");
				if (logger.isDebugEnabled()) {
					logger.debug("Metadatos [" + uid + "]: " + xml);
				}
			}
			
			return xml;

		} catch (Exception e) {
			logger.error("Error al obtener los metadatos del documento", e);
			throw new ISPACException("Error al obtener los metadatos del documento", e);
		}
	}

	/**
	 * Obtiene un metadato de un documento
	 * @param session Sesión de trabajo.
	 * @param uid UID del documento.
	 * @param property Nombre del metadato.
	 * @return Valor del metadato del documento.
	 * @throws ISPACException si ocurre algún error.
	 */
	public String getProperty(Object session, String uid, String property) throws ISPACException {

		try {
			
			String value = AlfrescoHelper.getMetadato(repository, uid, property);
			
			if (logger.isInfoEnabled()) {
				logger.info("Metadato [" + uid + "]: [" + property + "] = ["+ value + "]");
			}

			return value;

		} catch (Exception e) {
			logger.error("Error al obtener el valor del metadato [" + uid + "]: [" + property + "]", e);
			throw new ISPACException("Error al obtener el metadato [" + uid + "]: [" + property + "]", e);
		}
	}

	/**
	 * Establece un metadato de un documento.
	 * @param session Sesión de trabajo.
	 * @param uid UID del documento.
	 * @param name Nombre del metadato.
	 * @param value Valor del metadato.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void setProperty(Object session, String uid, String name, String value) throws ISPACException {

		try {
			
			AlfrescoHelper.setMetadato(repository, uid, name, value);
			
			if (logger.isInfoEnabled()) {
				logger.info("Metadato establecido [" + uid + "]: [" + name + "] = ["+ value + "]");
			}

		} catch (Exception e) {
			logger.error("Error al establecer el valor del metadato [" + uid + "]: [" + name + "]", e);
			throw new ISPACException("Error al establecer el metadato [" + uid + "]: [" + name + "]", e);
		}
	}

}
