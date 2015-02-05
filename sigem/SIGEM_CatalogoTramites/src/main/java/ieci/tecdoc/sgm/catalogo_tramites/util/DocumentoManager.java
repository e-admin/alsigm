package ieci.tecdoc.sgm.catalogo_tramites.util;
import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesCodigosError;
import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion;
import ieci.tecdoc.sgm.catalogo_tramites.util.database.DocumentoDatos;
import ieci.tecdoc.sgm.catalogo_tramites.util.database.DocumentoTramiteDatos;

import org.apache.log4j.Logger;

/**
 * Clase para la gestión de los documentos del sistema (almacenar, eliminar,
 * recuperar y actualizar).
 * @author IECISA
 *
 */
public class DocumentoManager {
   
	private static final Logger logger = Logger.getLogger(DocumentoManager.class);
	
   private static final boolean isDebugeable = true;
  
   protected DocumentoManager() {
   }

   /**
    * Recupera la información de un documento.
    * @param documentId Identificador del documento.
    * @return Document El documento.
    * @throws CatalogoTramitesExcepcion Si se produce algún error.
    */
   public static Documento get(String documentId, String entidad) throws CatalogoTramitesExcepcion {
      DocumentoDatos document = new DocumentoDatos();

      try {
    	  document.load(documentId, entidad);
      } catch (CatalogoTramitesExcepcion exc) {
    	  logger.error("Error al obtener tipo de documento [get][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_DOCUMENT);
      } catch (Exception exc) {
    	  logger.error("Error al obtener tipo de documento [get][Excepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_DOCUMENT);
      }
      
      return document;
   }


   /**
    * Añade un nuevo tipo de documento al catálogo. 
    * @param document Información del documento.
    * @throws CatalogoTramitesExcepcion Si se produce algún error.
    */
   public static void add(Documento document, String entidad) throws CatalogoTramitesExcepcion {
      DocumentoDatos data = new DocumentoDatos(document);

      try {
    	  data.insert(entidad);
      } catch (CatalogoTramitesExcepcion exc) {
    	  logger.error("Error al añadir tipo de documento [add][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_DOCUMENT);
      } catch (Exception exc) {
    	  logger.error("Error al añadir tipo de documento [add][Excepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_DOCUMENT);
      }
   }

   
   /**
    * Elimina un documento.
    * @param documentId Identificador del documento.
    * @throws CatalogoTramitesExcepcion Si se produce algún error.
    */
   public static void delete(String documentId, String entidad) throws CatalogoTramitesExcepcion {
      DocumentoDatos document = new DocumentoDatos();

      try{
    	  if (TramiteManager.isDocumentReferenced(documentId, entidad)) {
    		  logger.error("Error al eliminar tipo de documento [delete][Documento referenciado]");
    		  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_DOCUMENT_USED);
    	  }
    	  document.setId(documentId);
    	  document.delete(entidad);
      } catch (CatalogoTramitesExcepcion exc) {
    	  logger.error("Error al eliminar tipo de documento [delete][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_DELETE_DOCUMENT);
      } catch (Exception exc) {
    	  logger.error("Error al eliminar tipo de documento [delete][Excepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_DELETE_DOCUMENT);
      }
   }

   
   /**
    * Actualiza el documento que se pasa como parámetro. 
    * @param document Documento con los nuevos datos.
    * @throws CatalogoTramitesExcepcion Si se produce algún error.
    */
   public static void update(Documento document, String entidad) throws CatalogoTramitesExcepcion {
      DocumentoDatos data = new DocumentoDatos(document);

      try {
    	  data.update(entidad);
      } catch (CatalogoTramitesExcepcion exc) {
    	  logger.error("Error al actualizar tipo de documento [update][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_UPDATE_DOCUMENT);
      } catch (Exception exc) {
    	  logger.error("Error al actualizar tipo de documento [update][Excepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_UPDATE_DOCUMENT);
      }
   }

   
   /**
    * Recupera la lista de documentos. 
    * @return La lista mencionada.
    * @throws CatalogoTramitesExcepcion Si se produce algún error.
    */
   public static Documentos getDocuments(String entidad) throws CatalogoTramitesExcepcion {
      Documentos documents = null;

      try {
    	  documents = DocumentoDatos.getDocuments(entidad);
      } catch (CatalogoTramitesExcepcion exc) {
    	  logger.error("Error al obtener tipos de documentos [getDocuments][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_DOCUMENTS);
      } catch (Exception exc) {
    	  logger.error("Error al obtener tipos de documentos [getDocuments][Excepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_DOCUMENTS);
      }
      
      return documents;
   }

   
   /**
    * Recupera un documento a partir de su código.
    * @param code Código del documento a recuperar.
    * @return Document Documento
    * @throws CatalogoTramitesExcepcion En caso de producirse algún error.
    */   
   public static Documento getDocumentfromCode (String code, String entidad) 
   throws CatalogoTramitesExcepcion{

   DocumentoTramiteImpl procedureDoc = new DocumentoTramiteImpl();
   DocumentoTramiteDatos procedureDocData = new DocumentoTramiteDatos(procedureDoc);
   DocumentoExtendido documentExt = null;
   try{
     procedureDocData.getDocumentInfo(code, entidad);
     DocumentoDatos docData = new DocumentoDatos();
     docData.load(procedureDocData.getDocumentId(), entidad);
     documentExt = new DocumentoExtendido();
     documentExt.setId(procedureDocData.getDocumentId());
     documentExt.setExtension(docData.getExtension());
     documentExt.setDescription(docData.getDescription());
     documentExt.setValidationHook(docData.getValidationHook());
     documentExt.setSignatureHook(docData.getSignatureHook());
     documentExt.setCode(procedureDocData.getCode());
     documentExt.setMandatory(procedureDocData.isMandatory());
   }catch (CatalogoTramitesExcepcion e){
	   logger.error("Error al obtener tipo de documento por codigo [getDocumentFronCode][CatalogoTramitesExcepcion]", e.fillInStackTrace());
	   throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_DOCUMENT);
   }catch (Exception e){
	   logger.error("Error al obtener tipo de documento por codigo [getDocumentFronCode][Excepcion]", e.fillInStackTrace());
	   throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_DOCUMENT);
   }
   return documentExt;
}
}
