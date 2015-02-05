/**
 * @author José Antonio Nogales Rincón
 * 
 * Fecha de Creación: 20-mar-2007
 */


package ieci.tecdoc.sgm.rde;

import ieci.tecdoc.sgm.base.guid.Guid;
import ieci.tecdoc.sgm.base.miscelanea.Goodies;
import ieci.tecdoc.sgm.rde.database.*;
import ieci.tecdoc.sgm.rde.database.util.*;
import ieci.tecdoc.sgm.rde.datatypes.ContenedorDocumentoImpl;
import ieci.tecdoc.sgm.rde.datatypes.DocumentosTemporales;
import ieci.tecdoc.sgm.rde.exception.RepositorioDocumentosCodigosError;
import ieci.tecdoc.sgm.rde.exception.RepositorioDocumentosExcepcion;
import ieci.tecdoc.sgm.rde.exception.GuidIncorrectoCodigosError;
import ieci.tecdoc.sgm.rde.exception.GuidIncorrectoExcepcion;


import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * Case que implementa el mánager relacionado con el repositorio de 
 * documentos (tablas SGMRDEDOCUMENTOS, SGMRDETIPOSMIME, SGMRTTMP_DOCUMENTOS)
 * @author x53492no
 *
 */
public class ContenedorDocumentosManager {

	private static final Logger logger= Logger.getLogger(ContenedorDocumentosManager.class);
			
  protected static boolean isDebugeable = true;

  /*
   * Constructor de la clase ContenedirDocumentosManage
   */
  public ContenedorDocumentosManager(){
    //DOMConfigurator.configure("ieci/tecdoc/sgm/rde/resources/log4j.xml");
    isDebugeable = Configuracion.getIsDebugeable();
  }
  
  
  /**
   * Recupera la información del documento cuyo guid es el pasado como parámetro
   * @param sessionId Identificador de sesión de la aplicación llamante
   * @param guid Identificador del documento a recuperar
   * @param path Directorio en el que se quiere almacenar el fichero recuperado de la BBDD
   * @return String Ruta completa donde se ha almacenado el fichero recuperado
   * @throws GuidIncorrectoExcepcion
   * @throws RepositorioDocumentosExcepcion
   */
  public String retrieveDocument(String sessionId, String guid, String path, String entidad)
    throws GuidIncorrectoExcepcion, RepositorioDocumentosExcepcion {
    String completePath = "";

    try{
    	DocumentoInfo docInfo = retrieveDocument(sessionId, guid, entidad);
      	completePath = path + "\\" + guid + "." + docInfo.getExtension();
      	File documento = new File(completePath);
      	FileOutputStream writer = new FileOutputStream(documento);
      	writer.write(docInfo.getContent());
      	writer.close();
    }catch(GuidIncorrectoExcepcion e){
    	logger.error("Error al recuperar documento [retrieveDocument][GuidIncorrectoException][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new GuidIncorrectoExcepcion(GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
    }catch(FileNotFoundException e){
    	logger.error("Error al recuperar documento [retrieveDocument][FileNotFoundException][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_FILE_NOT_FOUND);
    }catch(Exception e){
    	logger.error("Error al recuperar documento [retrieveDocument][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_RETRIEVE_DOCUMENT);
    }

    return completePath;
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
  public DocumentoInfo retrieveDocument(String sessionId, String guid, String entidad)
    throws GuidIncorrectoExcepcion, RepositorioDocumentosExcepcion {
    ContenedorDocumentoDatos docData = new ContenedorDocumentoDatos();
    TipoMimeDatos mimeData = new TipoMimeDatos();
    DocumentoInfo info = new DocumentoInfo();

    try{
    	docData.load(guid, entidad);
    }catch(GuidIncorrectoExcepcion e){
    	logger.error("Error al recuperar informacion de documento [retrieveDocument][GuidIncorrectoException][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new GuidIncorrectoExcepcion(GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
    }catch(Exception e){
    	logger.error("Error al recuperar informacion de documento [retrieveDocument][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_RETRIEVE_DOCUMENT); 
    }
    try{
    	String cExt = null;
    	if(docData.getExtension() != null){
    		cExt = docData.getExtension().toLowerCase();
    	}
      mimeData.load(cExt, entidad);
    }catch (Exception e) {
    	logger.error("Error al recuperar tipo mime de documento [retrieveDocument][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
    }

    info.setContent(docData.getContent());
    info.setExtension(docData.getExtension());
    info.setMimeType(mimeData.getMimeType());
    info.setGuid(docData.getGuid());

    return info;
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
  public ContenedorDocumentoImpl retrieveDocumentInfo(String sessionId, String guid, String entidad)
    throws GuidIncorrectoExcepcion, RepositorioDocumentosExcepcion {
    ContenedorDocumentoDatos docData = new ContenedorDocumentoDatos();
    TipoMimeDatos mimeData = new TipoMimeDatos();

    try{
    	docData.load(guid, entidad);
    }catch(GuidIncorrectoExcepcion e){
    	logger.error("Error al recuperar informacion de documento [retrieveDocument][GuidIncorrectoException][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new GuidIncorrectoExcepcion(GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
    }catch(Exception e){
    	logger.error("Error al recuperar informacion de documento [retrieveDocument][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_RETRIEVE_DOCUMENT); 
    }
    try{
    	String cExt = null;
    	if(docData.getExtension() != null){
    		cExt = docData.getExtension().toLowerCase();
    	}
    	mimeData.load(cExt, entidad);
    }catch (Exception e) {
    	logger.error("Error al recuperar tipo mime de documento [retrieveDocument][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
    }

     return docData;
  }
  
  
  /**
   * Almacena un documento
   * @param sessionId Identificador de sesión de la aplicación llamante
   * @param path Documento a almacenar (ruta del fichero)
   * @param extension Extensión del documento
   * @return String Identificador único asignado al documento
   * @throws RepositorioDocumentosExcepcion
   */
  public String storeDocument(String sessionId, String path, String extension, String entidad)
    throws RepositorioDocumentosExcepcion {

    try{
    	InputStream document = Utilities.getStreamFromFile(path);
    	String identificador = storeDocument(sessionId, document, extension, entidad);

    	return identificador;
    }catch(FileNotFoundException e){
    	logger.error("Error al almacenar documento [storeDocument][FileNotFoundException][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_FILE_NOT_FOUND);
    }catch(Exception e){
    	logger.error("Error al almacenar documento [storeDocument][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_STORE_DOCUMENT);
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
  public String storeDocument(String sessionId, InputStream document, String extension, String entidad)
    throws RepositorioDocumentosExcepcion {
    ContenedorDocumentoDatos docData = new ContenedorDocumentoDatos();
    String guid = "";

    try{
    	guid = new Guid().toString();
	    docData.setGuid(guid);
	    byte[] docByte = Goodies.getBytes(document);
	    docData.setContent(docByte);      
	    docData.setExtension(extension.toUpperCase());
	    docData.setHash(Utilities.getHash(docByte));
	    docData.setTimestamp(new Timestamp(System.currentTimeMillis()));
	    docData.add(entidad);
    }catch(Exception e){
    	logger.error("Error al almacenar documento [storeDocument][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_STORE_DOCUMENT);
    }

    return guid;
  }
  
  /**
   * Almacena un documento
   * @param sessionId Identificador de sesión de la aplicación llamante
   * @param document Documento a almacenar (contenido binario)
   * @param extension Extensión del documento
   * @return String Identificador único asignado al documento
   * @throws RepositorioDocumentosExcepcion
   */
  public String storeDocument(String sessionId, byte[] document, String extension, String entidad)
    throws RepositorioDocumentosExcepcion {
    ContenedorDocumentoDatos docData = new ContenedorDocumentoDatos();
    String guid = "";

    try{
    	guid = new Guid().toString();
    	docData.setGuid(guid);
    	byte[] docByte = document;
    	docData.setContent(docByte);      
    	docData.setExtension(extension.toUpperCase());
    	docData.setHash(Utilities.getHash(docByte));
    	docData.setTimestamp(new Timestamp(System.currentTimeMillis()));
    	docData.add(entidad);
    }catch(Exception e){
    	logger.error("Error al almacenar documento [storeDocument][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_STORE_DOCUMENT);
    }

    return guid;
  }
  
  
  /**
   * Almacena un documento con un determinado GUID
   * @param sessionId Identificador de sesión de la aplicación llamante
   * @param guid Identificador del fichero
   * @param document Documento a almacenar (contenido binario)
   * @param extension Extensión del documento
   * @return String Identificador único asignado al documento
   * @throws RepositorioDocumentosExcepcion
   */
  public String storeDocumentGuid(String sessionId, String guid, byte[] document, String extension, String entidad)
    throws RepositorioDocumentosExcepcion {
    ContenedorDocumentoDatos docData = new ContenedorDocumentoDatos();

    try{
    	docData.setGuid(guid);
    	byte[] docByte = document;
    	docData.setContent(docByte);      
    	docData.setExtension(extension.toUpperCase());
    	docData.setHash(Utilities.getHash(docByte));
    	docData.setTimestamp(new Timestamp(System.currentTimeMillis()));
    	docData.add(entidad);
    }catch(Exception e){
    	logger.error("Error al almacenar documento con guid asignado [storeDocumentGuid][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_STORE_DOCUMENT);
    }

    return guid;
  }
  
  
  /**
   * Elimina un documento del RDE
   * @param sessionId Identificador de sesión de la aplicación llamante
   * @param guid Identificador del documento que se quiere eliminar del RDE
   * @throws GuidIncorrectoExcepcion
   * @throws RepositorioDocumentosExcepcion
   */
  public void deleteDocument(String sessionId, String guid, String entidad)
    throws GuidIncorrectoExcepcion, RepositorioDocumentosExcepcion {
    ContenedorDocumentoDatos docData = new ContenedorDocumentoDatos();

    try{
    	docData.setGuid(guid);
    	docData.delete(entidad);
    }catch(GuidIncorrectoExcepcion e){
    	logger.error("Error al eliminar documento [deleteDocument][GuidIncorrectoException][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new GuidIncorrectoExcepcion(GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
    }catch(Exception e){
    	logger.error("Error al eliminar documento [deleteDocument][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_DELETE_DOCUMENT);
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
  public String getHash(String sessionId, String guid, String entidad)
    throws GuidIncorrectoExcepcion, RepositorioDocumentosExcepcion {
    ContenedorDocumentoDatos docData = new ContenedorDocumentoDatos();

    try{
    	docData.load(guid, entidad);
    }catch(GuidIncorrectoExcepcion e){
    	logger.error("Error al obtener hash de documento [getHash][GuidIncorrectoException][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new GuidIncorrectoExcepcion(GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
    }catch(Exception e){
    	logger.error("Error al obtener hash de documento [getHash][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_STORE_AND_GET_HASH);
    }

    return docData.getHash();
  }
  
  
  /**
   * Almacena los documentos del vector y devuelve sus guids y sus hashes
   * @param sessionId Identificador de la sesión de la apliación llamante
   * @param docsHashInfo Vector de objetos DocumentHashInfo. Únicamente se pasan con 
   *   información los campos correspondientes al path y a la extensión del documento
   * @throws RepositorioDocumentosExcepcion
   */
  public void storeDocumentsAndGetHashes(String sessionId, Vector docsHashInfo, String entidad)
    throws RepositorioDocumentosExcepcion {

    for(int i=0; i<docsHashInfo.size(); i++){
      DocumentoHashInfo dhi = (DocumentoHashInfo)docsHashInfo.get(i);
      ContenedorDocumentoDatos docData = new ContenedorDocumentoDatos();
      String guid = "";
      String hash = "";
      try{
    	  guid = new Guid().toString();
    	  docData.setGuid(guid);
    	  InputStream document = Utilities.getStreamFromFile(dhi.getPath());
    	  byte[] docByte = Goodies.getBytes(document);
    	  docData.setContent(docByte);
    	  docData.setExtension(dhi.getExtension().toUpperCase());
    	  hash = Utilities.getHash(docByte);
    	  docData.setHash(hash);
    	  docData.setTimestamp(new Timestamp(System.currentTimeMillis()));
    	  docData.add(entidad);
      }catch(FileNotFoundException e){
    	  logger.error("Error al almacenar documento y obtener su hash [storeDocumentsAndGetHashes][FileNotFoundException][Entidad:"+entidad+"]", e.fillInStackTrace());
    	  throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_FILE_NOT_FOUND);
      }catch(Exception e){
    	  logger.error("Error al almacenar documento y obtener su hash [storeDocumentsAndGetHashes][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
    	  throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_STORE_DOCUMENTS);
      }
      dhi.setGuid(guid);
      dhi.setHash(hash);
      docsHashInfo.set(i, dhi);
    }
  }
  
  
  /**
   * Almacena un documento temporal
   * @param sessionId Identificador de sesión de la aplicación llamante
   * @param guid Identificador único asignado al documento
   * @throws RepositorioDocumentosExcepcion
   */
  public void storeDocumentTmp(String sessionId, String guid, String entidad)
    throws RepositorioDocumentosExcepcion {
    DocumentoTemporalDatos docTmpData = new DocumentoTemporalDatos();

    try{
    	docTmpData.setGuid(guid);
    	docTmpData.setSessionId(sessionId);
    	docTmpData.setTimestamp(new Timestamp(System.currentTimeMillis()));
    	docTmpData.add(entidad);
    }catch(Exception e){
    	logger.error("Error al almacenar documento temporal [storeDocumentTmp][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_STORE_DOCUMENT);
    }
  }
  
  
  /**
   * Elimina los documentos temporales del RDE asociados a un sessionId
   * @param sessionId Identificador de sesión de la aplicación llamante
   * @throws GuidIncorrectoExcepcion
   * @throws RepositorioDocumentosExcepcion
   */
  public void deleteDocumentTmp(String sessionId, String entidad)
    throws GuidIncorrectoExcepcion, RepositorioDocumentosExcepcion {
    DocumentoTemporalDatos docTmpData = new DocumentoTemporalDatos();

    try{
    	docTmpData.setSessionId(sessionId);
    	docTmpData.delete(entidad);
    }catch(GuidIncorrectoExcepcion e){
    	logger.error("Error al eliminar documento temporal [deleteDocumentTmp][GuidIncorrectoException][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new GuidIncorrectoExcepcion(GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
    }catch(Exception e){
    	logger.error("Error al eliminar documento temporal [deleteDocumentTmp][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_DELETE_DOCUMENT);
    } 
  }
  
  
  /**
   * Obtiene los documentos temporales asociados a un sessionId
   * @param sessionId Identificador de sesión de la aplicación llamante
   * @return DocumentTmps Documentos temporales asociados a un sessionId
   * @throws RepositorioDocumentosExcepcion
   */
  public DocumentosTemporales getDocumentTmp(String sessionId, String entidad)
    throws RepositorioDocumentosExcepcion {
    DocumentosTemporales documentTmps = new DocumentosTemporales();

    try{
    	DocumentoTemporalDatos docTmpData = new DocumentoTemporalDatos();
    	docTmpData.setSessionId(sessionId);
    	documentTmps = docTmpData.getDocumentTmps(entidad);
    }catch(Exception e){
    	logger.error("Error al obtener documento temporal [getDocumentTmp][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_STORE_DOCUMENT);
    }
    return documentTmps;
  }
  
  
  /**
   * Obtiene los documentos temporales caducados
   * @param timeout Tiempo máximo de sesión
   * @return DocumentTmps Documentos temporales caducados
   * @throws RepositorioDocumentosExcepcion
   */
  public DocumentosTemporales getDocumentTmp(int timeout, String entidad)
    throws RepositorioDocumentosExcepcion {
    DocumentosTemporales documentTmps = new DocumentosTemporales();

    try{
    	DocumentoTemporalDatos docTmpData = new DocumentoTemporalDatos();
    	documentTmps = docTmpData.getDocumentTmps(timeout, entidad);
    }catch(Exception e){
    	logger.error("Error al obtener documento temporal de sesiones caducadas [getDocumentTmp][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
    	throw new RepositorioDocumentosExcepcion(RepositorioDocumentosCodigosError.EC_STORE_DOCUMENT);
    }
    return documentTmps;
  }
  
}
