package ieci.tecdoc.sgm.rde;

import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumento;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentoInfo;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentoTemporal;
import ieci.tecdoc.sgm.core.services.repositorio.GuidIncorrectoRdeExcepcion;
import ieci.tecdoc.sgm.core.services.repositorio.ServicioRepositorioDocumentosTramitacion;
import ieci.tecdoc.sgm.core.services.repositorio.RepositorioDocumentosRdeExcepcion;
import ieci.tecdoc.sgm.rde.datatypes.DocumentoTemporalImpl;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentosTemporales;
import ieci.tecdoc.sgm.rde.exception.RepositorioDocumentosExcepcion;
import ieci.tecdoc.sgm.rde.exception.GuidIncorrectoExcepcion;
import ieci.tecdoc.sgm.rde.exception.GuidIncorrectoCodigosError;
import ieci.tecdoc.sgm.rde.exception.RepositorioDocumentosCodigosError;

import java.io.InputStream;
import java.util.Vector;

import org.apache.log4j.Logger;

public class ServicioRepositorioDocumentosTramitacionAdapter implements
	ServicioRepositorioDocumentosTramitacion {

	private static final Logger logger = Logger.getLogger(ServicioRepositorioDocumentosTramitacionAdapter.class);
	
	private ContenedorDocumentosManager manager;
	
	  /**
	   * Recupera la información del documento cuyo guid es el pasado como parámetro
	   * @param sessionId Identificador de sesión de la aplicación llamante
	   * @param guid Identificador del documento a recuperar
	   * @param path Directorio en el que se quiere almacenar el fichero recuperado de la BBDD
	   * @return String Ruta completa donde se ha almacenado el fichero recuperado
	   * @throws GuidIncorrectoExcepcion
	   * @throws RepositorioDocumentosExcepcion
	   */
	  public String retrieveDocument(String sessionId, String guid, String path, Entidad entidad)
	    throws GuidIncorrectoRdeExcepcion, RepositorioDocumentosRdeExcepcion {
	    try{
	    	ContenedorDocumentosManager oManager = getManager();			
			return oManager.retrieveDocument(sessionId, guid, path, entidad.getIdentificador());
	     }catch(GuidIncorrectoExcepcion e){
	    	 logger.error("Error al obtener fichero: guid incorrecto.", e);
			 throw getGuidIncorrectoExcepcion(e, GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
	     }catch(RepositorioDocumentosExcepcion e){
	    	 logger.error("Error al obtener fichero.", e);
			 throw getRepositorioDocumentosExcepcion(e, e.getErrorCode());
	     }catch (Throwable e){
			logger.error("Error inesperado al obtener fichero.", e);
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosCodigosError.EC_RETRIEVE_DOCUMENT);
		 }
	  }
	  
	  
	  /**
	   * Recupera la información del documento cuyo guid es el pasado como parámetro
	   * @param sessionId Identificador de sesión de la aplicación llamante
	   * @param guid Identificador del documento a recuperar
	   * @return DocumentInfo Objeto que contiene el documento (en binario), la extensión
	   *   y el tipo MIME asociado a esa extensión (en caso de estar definida)
	   * @throws GuidIncorrectoExcepcion
	   * @throws RepositorioDocumentosExcepcion
	   */
	  public DocumentoInfo retrieveDocument(String sessionId, String guid, Entidad entidad)
	     throws GuidIncorrectoRdeExcepcion, RepositorioDocumentosRdeExcepcion {
	     try{
	    	ContenedorDocumentosManager oManager = getManager();			
			return getDocumentoInfoServicio(oManager.retrieveDocument(sessionId, guid, entidad.getIdentificador()));
	     }catch(GuidIncorrectoExcepcion e){
	    	 logger.error("Error al obtener fichero: guid incorrecto.", e);
			 throw getGuidIncorrectoExcepcion(e, GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
	     }catch(Exception e){
	    	 logger.error("Error inesperado al obtener fichero.", e);
			 throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosCodigosError.EC_RETRIEVE_DOCUMENT); 
	     }
	  }
	  
	  
	  /**
	   * Recupera la información del documento cuyo guid es el pasado como parámetro
	   * @param sessionId Identificador de sesión de la aplicación llamante
	   * @param guid Identificador del documento a recuperar
	   * @return ContenedorDocumento Objeto que contiene el documento (en binario), la extensión
	   *   y el tipo MIME asociado a esa extensión (en caso de estar definida)
	   * @throws GuidIncorrectoExcepcion
	   * @throws RepositorioDocumentosExcepcion
	   */
	  public ContenedorDocumento retrieveDocumentInfo(String sessionId, String guid, Entidad entidad)
	  throws GuidIncorrectoRdeExcepcion, RepositorioDocumentosRdeExcepcion {
		     try{
		    	ContenedorDocumentosManager oManager = getManager();			
				return getContenedorDocumentoServicio(oManager.retrieveDocumentInfo(sessionId, guid, entidad.getIdentificador()));
		     }catch(GuidIncorrectoExcepcion e){
		    	 logger.error("Error al obtener fichero: guid incorrecto.", e);
				 throw getGuidIncorrectoExcepcion(e, GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
		     }catch(Exception e){
		    	 logger.error("Error inesperado al obtener fichero.", e);
				 throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosCodigosError.EC_RETRIEVE_DOCUMENT); 
		     }
	  }
	  
	  
	  /**
	   * Almacena un documento
	   * @param sessionId Identificador de sesión de la aplicación llamante
	   * @param path Documento a almacenar (ruta del fichero)
	   * @param extension Extensión del documento
	   * @return String Identificador único asignado al documento
	   * @throws RepositorioDocumentosExcepcion
	   */
	  public String storeDocument(String sessionId, String path, String extension, Entidad entidad)
	    throws RepositorioDocumentosRdeExcepcion {
	    try{
	    	ContenedorDocumentosManager oManager = getManager();			
			return oManager.storeDocument(sessionId, path, extension, entidad.getIdentificador());
	    }catch(RepositorioDocumentosExcepcion e){
	    	 logger.error("Error al almacenar fichero.", e);
			 throw getRepositorioDocumentosExcepcion(e, e.getErrorCode());
	     }catch(Exception e){
	    	logger.error("Error inesperado al almacenar fichero.", e);
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosCodigosError.EC_STORE_DOCUMENT);
	    }
	  }
	  
	  
	  /**
	   * Almacena un documento
	   * @param sessionId Identificador de sesión de la aplicación llamante
	   * @param document Documento a almacenar (contenido binario)
	   * @param extension Extensión del documento
	   * @return String Identificador único asignado al documento
	   * @throws RepositorioDocumentosExcepcion
	   */
	  public String storeDocument(String sessionId, InputStream document, String extension, Entidad entidad)
	    throws RepositorioDocumentosRdeExcepcion {
	    try{
	    	ContenedorDocumentosManager oManager = getManager();			
			return oManager.storeDocument(sessionId, document, extension, entidad.getIdentificador());
	    }catch(Exception e){
	    	logger.error("Error inesperado al almacenar fichero.", e);
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosCodigosError.EC_STORE_DOCUMENT);
	    }
	  }
	  
	  
	  /**
	   * Almacena un documento
	   * @param sessionId Identificador de sesión de la aplicación llamante
	   * @param document Documento a almacenar (contenido binario)
	   * @param extension Extensión del documento
	   * @return String Identificador único asignado al documento
	   * @throws RepositorioDocumentosExcepcion
	   */
	  public String storeDocument(String sessionId, byte[] document, String extension, Entidad entidad)
	    throws RepositorioDocumentosRdeExcepcion {
	    try{
	    	ContenedorDocumentosManager oManager = getManager();			
			return oManager.storeDocument(sessionId, document, extension, entidad.getIdentificador());
	    }catch(Exception e){
	    	logger.error("Error inesperado al almacenar fichero.", e);
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosCodigosError.EC_STORE_DOCUMENT);
	    }
	  }
	  

	  /**
	   * Almacena un documento con un determinado identificador
	   * @param sessionId Identificador de sesión de la aplicación llamante
	   * @param guid Identificador del fichero
	   * @param document Documento a almacenar (contenido binario)
	   * @param extension Extensión del documento
	   * @return String Identificador único asignado al documento
	   * @throws RepositorioDocumentosExcepcion
	   */
	  public String storeDocumentGuid(String sessionId, String guid, byte[] document, String extension, Entidad entidad)
	    throws RepositorioDocumentosRdeExcepcion {
	    try{
	    	ContenedorDocumentosManager oManager = getManager();			
			return oManager.storeDocumentGuid(sessionId, guid, document, extension, entidad.getIdentificador());
	    }catch(Exception e){
	    	logger.error("Error inesperado al almacenar fichero.", e);
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosCodigosError.EC_STORE_DOCUMENT);
	    }
	  }
	  
	  
	  /**
	   * Elimina un documento del RDE
	   * @param sessionId Identificador de sesión de la aplicación llamante
	   * @param guid Identificador del documento que se quiere eliminar del RDE
	   * @throws GuidIncorrectoExcepcion
	   * @throws RepositorioDocumentosExcepcion
	   */
	  public void deleteDocument(String sessionId, String guid, Entidad entidad)
	    throws GuidIncorrectoRdeExcepcion, RepositorioDocumentosRdeExcepcion {
	    try{
	    	ContenedorDocumentosManager oManager = getManager();			
			oManager.deleteDocument(sessionId, guid, entidad.getIdentificador());
	    }catch(GuidIncorrectoExcepcion e){
	    	logger.error("Error al eliminar fichero: guid incorrecto.", e);
			 throw getGuidIncorrectoExcepcion(e, GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
	    }catch(RepositorioDocumentosExcepcion e){
	    	logger.error("Error al eliminar fichero.", e);
			 throw getRepositorioDocumentosExcepcion(e, e.getErrorCode());
	    }catch(Exception e){
	    	logger.error("Error inesperado al eliminar fichero.", e);
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosCodigosError.EC_DELETE_DOCUMENT);
	    }
	  }
	  
	  
	  /**
	   * Obtiene el hash del documento cuyo guid es el pasado como parametro
	   * @param sessionId Identificador de la sesión de la aplicación llamante
	   * @param guid Identificador del documento del que se quiere obtener el hash
	   * @return String Hash del documento
	   * @throws GuidIncorrectoExcepcion
	   * @throws RepositorioDocumentosExcepcion
	   */
	  public String getHash(String sessionId, String guid, Entidad entidad)
	    throws GuidIncorrectoRdeExcepcion, RepositorioDocumentosRdeExcepcion {
	    try{
	    	ContenedorDocumentosManager oManager = getManager();			
			return oManager.getHash(sessionId, guid, entidad.getIdentificador());
	    }catch(GuidIncorrectoExcepcion e){
	    	logger.error("Error al obtener el hash de fichero: guid incorrecto.", e);
			throw getGuidIncorrectoExcepcion(e, GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
	    }catch(RepositorioDocumentosExcepcion e){
	    	logger.error("Error al obtener hash de fichero.", e);
			 throw getRepositorioDocumentosExcepcion(e, e.getErrorCode());
	    }catch(Exception e){
	    	logger.error("Error inesperado al obtener hash de fichero.", e);
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosCodigosError.EC_RETRIEVE_DOCUMENT);
	    }
	  }
	  
	  
	  /**
	   * Almacena los documentos del vector y devuelve sus guids y sus hashes
	   * @param sessionId Identificador de la sesión de la apliación llamante
	   * @param docsHashInfo Vector de objetos DocumentHashInfo. Únicamente se pasan con 
	   *   información los campos correspondientes al path y a la extensión del documento
	   * @throws RepositorioDocumentosExcepcion
	   */
	  public void storeDocumentsAndGetHashes(String sessionId, Vector docsHashInfo, Entidad entidad)
	    throws RepositorioDocumentosRdeExcepcion {
	    try{
	    	ContenedorDocumentosManager oManager = getManager();			
			oManager.storeDocumentsAndGetHashes(sessionId, docsHashInfo, entidad.getIdentificador());
	      }catch(RepositorioDocumentosExcepcion e){
	    	  logger.error("Error al almacenar fichero y obtener su hash: fichero no creado.", e);
			  throw getRepositorioDocumentosExcepcion(e, e.getErrorCode());
		  }catch(Exception e){
	    	  logger.error("Error inesperado al almacenar fichero y obtener su hash.", e);
			  throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosCodigosError.EC_STORE_AND_GET_HASH);
	      }
	  }
	  
	  
	  /**
	   * Almacena un documento temporal
	   * @param sessionId Identificador de sesión de la aplicación llamante
	   * @param guid Identificador único asignado al documento
	   * @throws RepositorioDocumentosExcepcion
	   */
	  public void storeDocumentTmp(String sessionId, String guid, Entidad entidad)
	    throws RepositorioDocumentosRdeExcepcion {
	    try{
	    	ContenedorDocumentosManager oManager = getManager();			
			oManager.storeDocumentTmp(sessionId, guid, entidad.getIdentificador());
	    }catch(Exception e){
	    	logger.error("Error inesperado al almacenar fichero temporal.", e);
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosCodigosError.EC_STORE_DOCUMENT);
	    }
	  }
	  
	  
	  /**
	   * Elimina los documentos temporales del RDE asociados a un sessionId
	   * @param sessionId Identificador de sesión de la aplicación llamante
	   * @throws GuidIncorrectoExcepcion
	   * @throws RepositorioDocumentosExcepcion
	   */
	  public void deleteDocumentTmp(String sessionId, Entidad entidad)
	    throws GuidIncorrectoRdeExcepcion, RepositorioDocumentosRdeExcepcion {
	    try{
	    	ContenedorDocumentosManager oManager = getManager();			
			oManager.deleteDocumentTmp(sessionId, entidad.getIdentificador());
	     }catch(GuidIncorrectoExcepcion e){
	    	logger.error("Error al eliminar fichero temporal: guid incorrecto.", e);
			throw getGuidIncorrectoExcepcion(e, GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
	     }catch(RepositorioDocumentosExcepcion e){
	    	  logger.error("Error al eliminar fichero temporal.", e);
			  throw getRepositorioDocumentosExcepcion(e, e.getErrorCode());
		 }catch(Exception e){
	    	logger.error("Error inesperado al eliminar fichero temporal.", e);
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosCodigosError.EC_DELETE_DOCUMENT);
	    }
	  }
	  
	  
	  /**
	   * Obtiene los documentos temporales asociados a un sessionId
	   * @param sessionId Identificador de sesión de la aplicación llamante
	   * @return DocumentTmps Documentos temporales asociados a un sessionId
	   * @throws RepositorioDocumentosExcepcion
	   */
	  public DocumentosTemporales getDocumentTmp(String sessionId, Entidad entidad)
	    throws RepositorioDocumentosRdeExcepcion {
	    try{
	    	ContenedorDocumentosManager oManager = getManager();			
			return getDocumentosTemporalesServicio(oManager.getDocumentTmp(sessionId, entidad.getIdentificador()));
	    }catch(Exception e){
	    	logger.error("Error inesperado al obtener fichero temporal.", e);
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosCodigosError.EC_RETRIEVE_DOCUMENT);
	    }
	  }
	  
	  
	  /**
	   * Obtiene los documentos temporales caducados
	   * @param timeout Tiempo máximo de sesión
	   * @return DocumentTmps Documentos temporales caducados
	   * @throws RepositorioDocumentosExcepcion
	   */
	  public DocumentosTemporales getDocumentTmp(int timeout, Entidad entidad)
	    throws RepositorioDocumentosRdeExcepcion {
	    try{
	    	ContenedorDocumentosManager oManager = getManager();			
			return getDocumentosTemporalesServicio(oManager.getDocumentTmp(timeout, entidad.getIdentificador()));
	    }catch(Exception e){
	    	logger.error("Error inesperado al obtener fichero temporal.", e);
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosCodigosError.EC_RETRIEVE_DOCUMENT);
	    }
	  }
	  
	  
	  private DocumentoInfo getDocumentoInfoServicio(ieci.tecdoc.sgm.rde.database.DocumentoInfo oDocInfoImpl){
			if(oDocInfoImpl == null){
				return null;
			}
			DocumentoInfo oImpl = new DocumentoInfo();
			oImpl.setContent(oDocInfoImpl.getContent());
			oImpl.setExtension(oDocInfoImpl.getExtension());
			oImpl.setGuid(oDocInfoImpl.getGuid());
			oImpl.setMimeType(oDocInfoImpl.getMimeType());
			return oImpl;
	  }
	
	  
	  private ContenedorDocumento getContenedorDocumentoServicio(ieci.tecdoc.sgm.rde.datatypes.ContenedorDocumentoImpl oDocInfoImpl){
			if(oDocInfoImpl == null){
				return null;
			}
			ContenedorDocumento oImpl = new ContenedorDocumento();
			
			oImpl.setContent(oDocInfoImpl.getContent());
			oImpl.setContentSize(oDocInfoImpl.getContentSize());
			oImpl.setExtension(oDocInfoImpl.getExtension());
			oImpl.setGuid(oDocInfoImpl.getGuid());
			oImpl.setHash(oDocInfoImpl.getHash());
			oImpl.setTimestamp(oDocInfoImpl.getTimestamp());
			
			return oImpl;
	  }
	  
	  
	  private DocumentoTemporal getDocumentoTemporal(DocumentoTemporalImpl oDocTempImpl){
			if(oDocTempImpl == null){
				return null;
			}
			DocumentoTemporal oImpl = new DocumentoTemporal();
			oImpl.setGuid(oDocTempImpl.getGuid());
			oImpl.setSessionId(oDocTempImpl.getSessionId());
			oImpl.setTimestamp(oDocTempImpl.getTimestamp());
			return oImpl;
	  }
	
	  
	  private ContenedorDocumentosManager getManager() {
		  return manager;
	  }

	  
	  public void setManager(ContenedorDocumentosManager manager) {
		  this.manager = manager;
	  }
	
	  
	  private GuidIncorrectoRdeExcepcion getGuidIncorrectoExcepcion(GuidIncorrectoExcepcion poException, long lCode){
			if(poException == null){
				return new GuidIncorrectoRdeExcepcion(GuidIncorrectoRdeExcepcion.EXC_GENERIC_EXCEPCION);
			}
			StringBuffer cCodigo = new StringBuffer(ConstantesServicios.SERVICE_REPOSITORY_ERROR_PREFIX);
			if (lCode == -1)
				cCodigo.append(String.valueOf(poException.getErrorCode()));
			else cCodigo.append(String.valueOf(lCode));
			return new GuidIncorrectoRdeExcepcion(Long.valueOf(cCodigo.toString()).longValue(), poException);
	  }
		
	  
	  private RepositorioDocumentosRdeExcepcion getRepositorioDocumentosExcepcion(RepositorioDocumentosExcepcion poException, long lCode){
			if(poException == null){
				return new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosRdeExcepcion.EXC_GENERIC_EXCEPCION);
			}
			StringBuffer cCodigo = new StringBuffer(ConstantesServicios.SERVICE_REPOSITORY_ERROR_PREFIX);
			if (lCode == -1)
				cCodigo.append(String.valueOf(poException.getErrorCode()));
			else cCodigo.append(String.valueOf(lCode));
			return new RepositorioDocumentosRdeExcepcion(Long.valueOf(cCodigo.toString()).longValue(), poException);
	  }
	

	  private DocumentosTemporales getDocumentosTemporalesServicio(ieci.tecdoc.sgm.rde.datatypes.DocumentosTemporales poDocumentosTemporales){
		  DocumentosTemporales oLista = new DocumentosTemporales();
			if(poDocumentosTemporales == null){
				return oLista;
			}
		  
			for(int i = 0; i < poDocumentosTemporales.count(); i++){
				oLista.add(getDocumentoTemporal((DocumentoTemporalImpl)poDocumentosTemporales.get(i)));
			}
			return oLista;
	  }
}
