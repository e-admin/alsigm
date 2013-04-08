package ieci.tecdoc.sgm.catalogo_tramites.util;

import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.catalogo_tramites.Configuracion;
import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesCodigosError;
import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion;
import ieci.tecdoc.sgm.catalogo_tramites.util.database.DocumentoDatos;
import ieci.tecdoc.sgm.catalogo_tramites.util.database.DocumentoTramiteDatos;
import ieci.tecdoc.sgm.catalogo_tramites.util.database.TramiteDatos;

import org.apache.log4j.Logger;

/**
 * Gestor de implementa la funcionalidad para los trámites (alamacenaje, recuperación,
 * almacenaje, actualización de trámites)
 *
 */
public class TramiteManager {
	
   private static final Logger logger = Logger.getLogger(TramiteManager.class);
   
   protected static boolean isDebugeable = true;
  
   /**
    * Constructor de la clase TramiteManager
    *
    */
   protected TramiteManager(){
     isDebugeable = Configuracion.getIsDebugeable();
   }
   
   
   /**
    * Añade un nuevo trámite al catálogo.
    * @param procedure Información del trámite.
    * @throws CatalogoTramitesExcepcion Si se produce algún error.
    */
   public static void add(Tramite procedure, String entidad) 
     throws CatalogoTramitesExcepcion {
      TramiteDatos data = new TramiteDatos(procedure);

      try{
    	  data.insert(entidad);
      }catch (CatalogoTramitesExcepcion exc){
    	  logger.error("Error al añadir un tramite al catalogo [add][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_PROCEDURE);
      }catch (Exception exc){
    	  logger.error("Error al añadir un tramite al catalogo [add][Excepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_PROCEDURE);
      }
   }

   
   /**
    * Recupera la información de un trámite del catálogo de trámites. 
    * @param procedureId Identificador del trámite.
    * @param loadDocuments Si hay que cargar la información de los documentos.
    * @return La información del trámite.
    * @throws CatalogoTramitesExcepcion Si se produce algún error.
    */
   public static Tramite get(String procedureId, boolean loadDocuments, String entidad) 
     throws CatalogoTramitesExcepcion{
      TramiteDatos procedure = new TramiteDatos();

      try{
         procedure.load(procedureId, entidad);
         if (loadDocuments)
            procedure.setDocuments(DocumentoDatos.getDocuments(procedureId, entidad));
      }catch (CatalogoTramitesExcepcion exc){
    	  logger.error("Error al recuperar un tramite del catalogo [get][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
          throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_PROCEDURE);
      }catch (Exception exc){
    	  logger.error("Error al recuperar un tramite del catalogo [get][Excepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_PROCEDURE);
      }
      
      return procedure;
   }


   /**
    * Recupera un conjunto de trámites del catálogo de trámites a partir de 
    * unos valores de búsqueda. 
    * @param query Información con los valores de búsqueda.
    * @return La lista de trámites que cumplen los criterios de búsqueda.
    * @throws CatalogoTramitesExcepcion Si se produce algún error.
    */
   public static Tramites query(TramiteConsulta query, String entidad)
     throws CatalogoTramitesExcepcion {
      
      TramiteDatos tramiteData = new TramiteDatos();
      Tramites procedures = new Tramites();
      
      try {
         
         DynamicRow rowInfo = tramiteData.query(query, entidad);
         for (int i = 1; i < rowInfo.getRowCount() ; i++) {
        	TramiteImpl tramite = new TramiteImpl();
        	tramiteData = (TramiteDatos)rowInfo.getRow(i);
        	tramite.setId(tramiteData.getId());
        	tramite.setTopic(tramiteData.getTopic());
        	tramite.setDescription(tramiteData.getDescription());
        	tramite.setAddressee(tramiteData.getAddressee());
        	tramite.setLimitDocs(tramiteData.getLimitDocs());
        	tramite.setFirma(tramiteData.getFirma());
        	tramite.setDocuments(tramiteData.getDocuments());
        	tramite.setIdProcedimiento(tramiteData.getIdProcedimiento());
            
        	procedures.add(tramite);
         }      
         //Log.setQuery(sessionId, query);
      } catch (Exception e) {
    	  logger.error("Error al obtener resultados de busqueda [query][Excepcion]", e.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_NO_PROCEDURE);
      }

      return procedures;
   }

   
   /**
    * Elimina un trámite del catálogo de trámites. 
    * @param procedureId Identificador del trámite.
    * @throws CatalogoTramitesExcepcion Si se produce algún error.
    */
   public static void delete(String procedureId, String entidad) 
     throws CatalogoTramitesExcepcion {
      TramiteDatos procedure = new TramiteDatos();
      DocumentoTramite procedureDocument = new DocumentoTramiteImpl();

      try {
    	  procedureDocument.setProcedureId(procedureId);
    	  deleteDocument(procedureDocument, entidad);
    	  procedure.setId(procedureId);
    	  procedure.delete(entidad);
      } catch (CatalogoTramitesExcepcion exc) {
    	  logger.error("Error al eliminar un tramite del catalogo [delete][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_DELETE_PROCEDURE);
      } catch (Exception exc) {
    	  logger.error("Error al eliminar un tramite del catalogo [delete][Excepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_DELETE_PROCEDURE);
      }
   }
   
   
   /**
    * Actualiza el obejto trámite que se pasa como parámtro. 
    * @param procedure Trámite con los nuevos datos.
    * @throws CatalogoTramitesExcepcion Si se produce algún error. 
    */
   public static void update(Tramite procedure, String entidad) 
     throws CatalogoTramitesExcepcion {
      TramiteDatos data = new TramiteDatos(procedure);

      try {
    	  data.update(entidad);
      } catch (CatalogoTramitesExcepcion exc) {
    	  logger.error("Error al actualizar un tramite del catalogo [update][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_UPDATE_PROCEDURE);
      } catch (Exception exc) {
    	  logger.error("Error al actualizar un tramite del catalogo [update][Excepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_UPDATE_PROCEDURE);
      }
   }
   
   
   /**
    * Actualiza el obejto trámite-documento que se pasa como parámtro. 
    * @param procedureDocument Trámite-Documento con los nuevos datos.
    * @throws CatalogoTramitesExcepcion Si se produce algún error. 
    */
   public static void update(DocumentoTramite procedureDocument, String entidad) 
     throws CatalogoTramitesExcepcion {
      DocumentoTramiteDatos data = new DocumentoTramiteDatos(procedureDocument);

      try {
    	  data.update(entidad);
      } catch (CatalogoTramitesExcepcion exc) {
    	  logger.error("Error al actualizar un documento de tramite del catalogo [update][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_UPDATE_PROCEDUREDOCUMENT);
      } catch (Exception exc) {
    	  logger.error("Error al actualizar un documento de tramite del catalogo [update][Excepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_UPDATE_PROCEDUREDOCUMENT);
      }
   }

   
   /**
    * Indica si un documento está siendo referenciado por algún trámite
    * definido en el catálogo de trámites. 
    * @param documentId Identificador del documento.
    * @return Si el documento está referenciado.
    * @throws CatalogoTramitesExcepcion Si se produce algún error.
    */
   public static boolean isDocumentReferenced(String documentId, String entidad) 
     throws CatalogoTramitesExcepcion {
      boolean isReferenced = false;
      DocumentoTramiteDatos procedure = new DocumentoTramiteDatos();

      try {
         procedure.setDocumentId(documentId);
         isReferenced = procedure.isDocumentReferenced(entidad);
      } catch (CatalogoTramitesExcepcion exc) {
    	  logger.error("Error al comprobar referencia de documento en tramites [isDocumentReference][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_PROCEDURE);
      } catch (Exception exc) {
    	  logger.error("Error al comprobar referencia de documento en tramites [isDocumentReference][Excepcion]", exc.fillInStackTrace());
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_PROCEDURE);
      }
      
      return isReferenced;
   }


   /**
    * Recupera una colección con los procedimientos almacenados en el sistema.
    * @return Procedures Colección de procedimientos.
    * @throws CatalogoTramitesExcepcion En caso de producirse algún error.
    */   
   public static Tramites getProcedures(String entidad) 
     throws CatalogoTramitesExcepcion {
      Tramites procedures = null;

      try {
    	  procedures = TramiteDatos.getProcedures(entidad);
      } catch (CatalogoTramitesExcepcion exc) {
    	  logger.error("Error al obtener tramites [getProcedures][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_PROCEDURES);
      } catch (Exception exc) {
    	  logger.error("Error al obtener tramites [getProcedures][Excepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_PROCEDURES);
      }
      
      return procedures;
   }
   
   
   /**
    * Devuelve una colección con los documentos asociados a un determinado procedimientos.
    * @param procedureId Identificador del procedimiento del que se quieren recuperar los documentos.
    * @return Documents Colección de documentos.
    * @throws CatalogoTramitesExcepcion En caso de producirse algún error.
    */   
   public static Documentos getDocuments(String procedureId, String entidad) 
     throws CatalogoTramitesExcepcion {
      Documentos documents = null;

      try {
    	  documents = DocumentoDatos.getDocuments(procedureId, entidad);
      }catch (CatalogoTramitesExcepcion exc){
    	  logger.error("Error al obtener tipos de documentos [getDocuments][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_PROCEDURE);
      }catch (Exception exc){
    	  logger.error("Error al obtener tipos de documentos [getDocuments][Excepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_PROCEDURE);
      }
      
      return documents;
   }

   
   /**
    * Asocia un documento a un trámite. 
    * @param procedureDocument Relación de asociación entre el documento y el trámite
    * @throws CatalogoTramitesExcepcion Si se produce algún error.
    */
   public static void addDocument(DocumentoTramite procedureDocument, String entidad) 
     throws CatalogoTramitesExcepcion {

      DocumentoTramiteDatos data = new DocumentoTramiteDatos(procedureDocument);

      try{
    	  data.insert(entidad);
      }catch (CatalogoTramitesExcepcion exc){
    	  logger.error("Error al añadir tipo de documento [addDocument][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_DOCUMENT);
      }catch (Exception exc){
    	  logger.error("Error al añadir tipo de documento [addDocument][Excepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_DOCUMENT);
      }
   }

   
   /**
    * Recupera la información de un documento asociado a un trámite. 
    * @param procedureId Identificador del trámite.
    * @param documentID Identificador del documento.
    * @return El documento asociado al trámite.
    * @throws CatalogoTramitesExcepcion Si se produce algún error.
    */
   public static DocumentoTramite getDocument(String procedureId, String documentId, String entidad) 
     throws CatalogoTramitesExcepcion {

      DocumentoTramiteDatos data = new DocumentoTramiteDatos();

      try {
    	  data.load(procedureId, documentId, null, entidad);
      } catch (CatalogoTramitesExcepcion exc) {
    	  logger.error("Error al obtener tipo de documento [getDocument][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_DOCUMENT);
      } catch (Exception exc) {
    	  logger.error("Error al obtener tipo de documento [getDocument][Excepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_DOCUMENT);
      }
      
      return data;
   }
   
   /**
    * Recupera la información de un documento asociado a un trámite. 
    * @param procedureId Identificador del trámite.
    * @param documentID Identificador del documento.
    * @return El documento asociado al trámite.
    * @throws CatalogoTramitesExcepcion Si se produce algún error.
    */
   public static DocumentoTramite getDocument(String procedureId, String documentId, String code, String entidad) 
     throws CatalogoTramitesExcepcion {

      DocumentoTramiteDatos data = new DocumentoTramiteDatos();

      try {
    	  data.load(procedureId, documentId, code, entidad);
      } catch (CatalogoTramitesExcepcion exc) {
    	  logger.error("Error al obtener tipo de documento [getDocument][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_DOCUMENT);
      } catch (Exception exc) {
    	  logger.error("Error al obtener tipo de documento [getDocument][Excepcion]", exc.fillInStackTrace());
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_DOCUMENT);
      }
      
      return data;
   }

   /**
    * Elimina una asociación de documento a un trámite. 
    * @param procedureDocument Relación de asociación entre el documento y el trámite
    * @throws CatalogoTramitesExcepcion Si se produce algún error.
    */
   public static void deleteDocument(DocumentoTramite procedureDocument, String entidad) 
     throws CatalogoTramitesExcepcion {

      DocumentoTramiteDatos data = new DocumentoTramiteDatos(procedureDocument);

      try {
         data.delete(entidad);
      } catch (CatalogoTramitesExcepcion exc) {
    	  logger.error("Error al eliminar tipo de documento [deleteDocument][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_DELETE_DOCUMENT);
      } catch (Exception exc) {
    	  logger.error("Error al eliminar tipo de documento [deleteDocument][Excepcion]", exc.fillInStackTrace());
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_DELETE_DOCUMENT);
      }
   }
}