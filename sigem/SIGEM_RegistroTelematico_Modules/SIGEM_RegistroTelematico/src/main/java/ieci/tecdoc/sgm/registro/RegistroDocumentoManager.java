/**
 * @author José Antonio Nogales Rincón
 * 
 * Fecha de Creación: 20-mar-2007
 */


package ieci.tecdoc.sgm.registro;


import ieci.tecdoc.sgm.registro.exception.RegistroCodigosError;
import ieci.tecdoc.sgm.registro.exception.RegistroExcepcion;
import ieci.tecdoc.sgm.registro.util.RegistroDocumento;
import ieci.tecdoc.sgm.registro.util.RegistroDocumentoImpl;
import ieci.tecdoc.sgm.registro.util.RegistroDocumentos;
import ieci.tecdoc.sgm.registro.util.database.RegistroDocumentoDatos;

import org.apache.log4j.Logger;

/**
 * Clase manager que implementa las funciones de documentos de registro
 */
public class RegistroDocumentoManager {
  private static final Logger logger = Logger.getLogger(RegistroDocumentoManager.class);
  protected static boolean isDebugeable = true;

 /**
  * Constructor de la clase RegistroDocumentoManager
  *
  */ 
  public RegistroDocumentoManager(){
    //DOMConfigurator.configure("ieci/tecdoc/sgm/rde/resources/log4j.xml");
    //isDebugeable = Config.getIsDebugeable();
    logger.debug("Debug: " + isDebugeable);
  }
 
  
  /**
   * Recupera un elemento del sistema.
   * @param sessionId Identificador de la sessión del usuario
   * @param registryNumber Número del registro
   * @param code Código de documento
   * @return RegistryDocument Un registro de documento.
   * @throws CatalogoTramitesExcepcion En caso de producirse algún error.
   */   
  public static RegistroDocumento getRegistryDocument(String sessionId, String registryNumber, String code, String entidad) 
    throws RegistroExcepcion {
    RegistroDocumentoDatos registryDocument = new RegistroDocumentoDatos();

     try {
    	 registryDocument.load(registryNumber, code, entidad);
     } catch (RegistroExcepcion exc) {
    	 logger.error("Error al obtener documento de registro [getRegistryDocument][RegistroException]", exc.fillInStackTrace());
    	 throw new RegistroExcepcion(RegistroCodigosError.EC_GET_DOCUMENT);
     } catch (Exception exc) {
    	 logger.error("Error al obtener documento de registro [getRegistryDocument][Exception]", exc.fillInStackTrace());
    	 throw new RegistroExcepcion(RegistroCodigosError.EC_GET_DOCUMENT);
     }
     
     return registryDocument;
  }
  
  
  /**
   * Añade un elemento del sistema.
   * @param registryDocument Elemento a añadir
   * @throws CatalogoTramitesExcepcion En caso de producirse algún error.
   */   
  public static RegistroDocumento addRegistryDocument(RegistroDocumentoImpl registryDocument, String entidad) 
    throws RegistroExcepcion {
     RegistroDocumentoDatos regDocument = new RegistroDocumentoDatos(registryDocument);

     try {
    	 regDocument.add(entidad);
     } catch (RegistroExcepcion exc) {
    	 logger.error("Error al anexar documento a registro [addRegistryDocument][RegistroException]", exc.fillInStackTrace());
    	 throw new RegistroExcepcion(RegistroCodigosError.EC_ADD_REGISTRY_DOCUMENT);
     } catch (Exception exc) {
    	 logger.error("Error al anexar documento a registro [addRegistryDocument][Exception]", exc.fillInStackTrace());
    	 throw new RegistroExcepcion(RegistroCodigosError.EC_ADD_REGISTRY_DOCUMENT);
     }
     
     return registryDocument;
  }
  
  
  /**
   * Actualiza un elemento del sistema.
   * @param registryDocument Datos del registro a actualizar
   * @throws CatalogoTramitesExcepcion En caso de producirse algún error.
   */ 
  public static void updateRegistryDocument(RegistroDocumentoImpl registryDocument, String entidad) 
  throws RegistroExcepcion {
     RegistroDocumentoDatos regDocument = new RegistroDocumentoDatos(registryDocument);

     try {
    	 regDocument.updateOne(entidad);
     } catch (RegistroExcepcion exc) {
    	 logger.error("Error al actualizar documento de registro [updateRegistryDocument][RegistroException]", exc.fillInStackTrace());
    	 throw new RegistroExcepcion(RegistroCodigosError.EC_UPDATE_REGISTRY_DOCUMENT);
     } catch (Exception exc) {
    	 logger.error("Error al actualizar documento de registro [updateRegistryDocument][Exception]", exc.fillInStackTrace());
    	 throw new RegistroExcepcion(RegistroCodigosError.EC_UPDATE_REGISTRY_DOCUMENT);
     }
  }
  
  
 
  
  /**
   * Elimina los documentos asociados a un elemento del sistema.
   * @param registryNumber Número de registro a eliminar los documentos
   * @throws CatalogoTramitesExcepcion En caso de producirse algún error.
   */  
  public static void deleteRegistryDocument(String registryNumber, String entidad) 
    throws RegistroExcepcion {
     RegistroDocumentoDatos registryDocument = new RegistroDocumentoDatos();

     try {
    	 registryDocument.setRegistryNumber(registryNumber);
    	 registryDocument.delete(entidad);
     } catch (RegistroExcepcion exc) {
    	 logger.error("Error al eliminar documento de registro [deleteRegistryDocument][RegistroException]", exc.fillInStackTrace());
    	 throw new RegistroExcepcion(RegistroCodigosError.EC_DELETE_REGISTRY_DOCUMENTS);
     } catch (Exception exc) {
    	 logger.error("Error al eliminar documento de registro [deleteRegistryDocument][Exception]", exc.fillInStackTrace());
    	 throw new RegistroExcepcion(RegistroCodigosError.EC_DELETE_REGISTRY_DOCUMENTS);
     }
  }
  
  
  /**
   * Recupera una colección con los procedimientos almacenados en el sistema.
   * @return Procedures Colección de procedimientos.
   * @throws CatalogoTramitesExcepcion En caso de producirse algún error.
   */   
  public static RegistroDocumentos getRegistryDocuments(String registryNumber, String entidad) 
    throws RegistroExcepcion {
    RegistroDocumentos registryDocuments = null;
  
     try {
    	 RegistroDocumentoDatos registryDocumentData = new RegistroDocumentoDatos();
    	 registryDocumentData.setRegistryNumber(registryNumber);
    	 registryDocuments = registryDocumentData.getRegistryDocuments(entidad);
     } catch (RegistroExcepcion exc) {
    	 logger.error("Error al obtener documentos de registro [getRegistryDocuments][RegistroException]", exc.fillInStackTrace());
    	 throw new RegistroExcepcion(RegistroCodigosError.EC_GET_REGISTRY_DOCUMENTS);
     } catch (Exception exc) {
    	 logger.error("Error al obtener documentos de registro [getRegistryDocuments][Exception]", exc.fillInStackTrace());
    	 throw new RegistroExcepcion(RegistroCodigosError.EC_GET_REGISTRY_DOCUMENTS);
     }
     
     return registryDocuments;
  }
  
}
