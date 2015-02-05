package ieci.tecdoc.sgm.catalogo_tramites;

import ieci.tecdoc.sgm.catalogo_tramites.util.ConectorAutenticacion;
import ieci.tecdoc.sgm.catalogo_tramites.util.ConectorAutenticacionImpl;
import ieci.tecdoc.sgm.catalogo_tramites.util.ConectoresAutenticacion;
import ieci.tecdoc.sgm.catalogo_tramites.util.ConectorAutenticacionManager;
import ieci.tecdoc.sgm.catalogo_tramites.util.OrganoDestinatarioImpl;
import ieci.tecdoc.sgm.catalogo_tramites.util.OrganoDestinatarioManager;
import ieci.tecdoc.sgm.catalogo_tramites.util.OrganosDestinatarios;
import ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoImpl;
import ieci.tecdoc.sgm.catalogo_tramites.util.Documento;
import ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoManager;
import ieci.tecdoc.sgm.catalogo_tramites.util.Documentos;
import ieci.tecdoc.sgm.catalogo_tramites.util.ConectorImpl;
import ieci.tecdoc.sgm.catalogo_tramites.util.ConectorManager;
import ieci.tecdoc.sgm.catalogo_tramites.util.TipoConectorImpl;
import ieci.tecdoc.sgm.catalogo_tramites.util.TipoConectorManager;
import ieci.tecdoc.sgm.catalogo_tramites.util.TiposConectores;
import ieci.tecdoc.sgm.catalogo_tramites.util.Conectores;
import ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoTramite;
import ieci.tecdoc.sgm.catalogo_tramites.util.TramiteImpl;
import ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoTramiteImpl;
import ieci.tecdoc.sgm.catalogo_tramites.util.TramiteManager;
import ieci.tecdoc.sgm.catalogo_tramites.util.TramiteConsulta;
import ieci.tecdoc.sgm.catalogo_tramites.util.Tramites;
import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion;

/**
 * EJB sin estado que proporciona las operaciones sobre el catálogo de trámites
 * 
 * @author IECISA
 * 
 */
public class CatalogoTramites {
  
    /**
     * Añade un nuevo trámite al catálogo.
     * 
     * @param procedure Información del trámite.
     * @throws CatalogoTramitesExcepcion Si se produce algún error.
     */        
   public static void addProcedure(TramiteImpl procedure, String entidad) throws CatalogoTramitesExcepcion
   {
       TramiteManager.add(procedure, entidad);
   }
   
   /**
    * Recupera la información de un trámite del catálogo de trámites.
    * 
    * @param procedureId Identificador del trámite.
    * @param loadDocuments Si hay que cargar la información de los documentos.
    * @return La información del trámite.
    * @throws CatalogoTramitesExcepcion Si se produce algún error.
    */      
   public static TramiteImpl getProcedure(String procedureId, boolean loadDocuments, String entidad) throws CatalogoTramitesExcepcion
   {
       return (TramiteImpl)TramiteManager.get(procedureId, loadDocuments, entidad);
   }
           
   /**
    * Recupera un conjunto de trámites del catálogo de trámites a partir de 
    * unos valores de búsqueda.
    * 
    * @param query Información con los valores de búsqueda.
    * @return La lista de trámites que cumplen los criterios de búsqueda.
    * @throws CatalogoTramitesExcepcion Si se produce algún error.
    */              
   public static Tramites query(TramiteConsulta query, String entidad) throws CatalogoTramitesExcepcion
   {
       return TramiteManager.query(query, entidad);
   }

   /**
    * Elimina un trámite del catálogo de trámites.
    * 
    * @param procedureId Identificador del trámite.
    * @throws CatalogoTramitesExcepcion Si se produce algún error.
    */   
   public static void deleteProcedure(String procedureId, String entidad) throws CatalogoTramitesExcepcion
   {
       TramiteManager.delete(procedureId, entidad);
   }
   
   /**
    * Actualiza el obejto trámite que se pasa como parámtro.
    * 
    * @param procedure Trámite con los nuevos datos.
    * @throws CatalogoTramitesExcepcion Si se produce algún error. 
    */      
   public static void updateProcedure(TramiteImpl procedure, String entidad) throws CatalogoTramitesExcepcion
   {
       TramiteManager.update(procedure, entidad);
   }
   
   /**
    * Indica si un documento está siendo referenciado por algún trámite
    * definido en el catálogo de trámites.
    * 
    * @param documentId Identificador del documento.
    * @return Si el documento está referenciado.
    * @throws CatalogoTramitesExcepcion Si se produce algún error.
    */      
   public static boolean isDocumentReferenced(String documentId, String entidad) throws CatalogoTramitesExcepcion
   {
       return TramiteManager.isDocumentReferenced(documentId, entidad);
   }
   
   /**
    * Asocia un documento a un trámite.
    * 
    * @param procedureDocument
    *           Relación de asociación entre el documento y el trámite
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */      
   public static void addProcedureDocument(DocumentoTramiteImpl procedureDocument, String entidad) throws CatalogoTramitesExcepcion
   {
       TramiteManager.addDocument(procedureDocument, entidad);
   }

   /**
    * Elimina una asociación de documento a un trámite.
    * 
    * @param procedureDocument
    *           Relación de asociación entre el documento y el trámite
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */   
   public static void deleteProcedureDocument(DocumentoTramiteImpl procedureDocument, String entidad) throws CatalogoTramitesExcepcion
   {
       TramiteManager.deleteDocument(procedureDocument, entidad);
   }
    
   /**
    * Recupera la información de un documento.
    * 
    * @param documentId
    *           Identificador del documento.
    * @return El documento.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */       
   public static DocumentoImpl getDocument(String documentId, String entidad) throws CatalogoTramitesExcepcion
   {
      return (DocumentoImpl)DocumentoManager.get(documentId, entidad);
   }

   /**
    * Añade un nuevo tipo de documento al catálogo.
    * 
    * @param document
    *           Información del documento.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */      
   public static void addDocument(DocumentoImpl document, String entidad) throws CatalogoTramitesExcepcion
   {
      DocumentoManager.add(document, entidad);
   }
   
   /**
    * Elimina un documento.
    * 
    * @param documentId
    *           Identificador del documento.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */      
   public static void deleteDocument(String documentId, String entidad) throws CatalogoTramitesExcepcion
   {
      DocumentoManager.delete(documentId, entidad);
   }
   
   /**
    * Actualiza el documento que se pasa como parámetro.
    * 
    * @param document
    *           Documento con los nuevos datos.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */      
   public static void updateDocument(DocumentoImpl document, String entidad) throws CatalogoTramitesExcepcion
   {
      DocumentoManager.update(document, entidad);
   }
   
   /**
    * Recupera la lista de documentos.
    * 
    * @return La lista mencionada.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */      
   public static Documentos getDocuments(String entidad) throws CatalogoTramitesExcepcion
   {
      return DocumentoManager.getDocuments(entidad);
   }
  
   
   /**
    * Devuelve una colección con los documentos asociados a un determinado procedimientos.
    * @param procedureId Identificador del procedimiento del que se quieren recuperar los documentos.
    * @return Documents Colección de documentos.
    * @throws CatalogoTramitesExcepcion En caso de producirse algún error.
    */      
   public static Documentos getProcedureDocuments(String procedureId, String entidad) throws CatalogoTramitesExcepcion
   {
      return TramiteManager.getDocuments(procedureId, entidad);
   }
   
   /**
    * Devuelve el documento que corresponde a los datos que se pasan como parámetros.
    * @param procedureId Identificador del procedimiento del que se quiere recuperar el documento.
    * @param documentId Identificador del documento.
    * @return ProcedureDocument Documento.
    * @throws CatalogoTramitesExcepcion En caso de producirse algún error.
    */      
   public static DocumentoTramite getProcedureDocument(String procedureId, String documentId, String entidad) throws CatalogoTramitesExcepcion
   {
      return TramiteManager.getDocument(procedureId, documentId, entidad);
   }
   
   /**
    * Devuelve el documento que corresponde a los datos que se pasan como parámetros.
    * @param procedureId Identificador del procedimiento del que se quiere recuperar el documento.
    * @param documentId Identificador del documento.
    * @return ProcedureDocument Documento.
    * @throws CatalogoTramitesExcepcion En caso de producirse algún error.
    */      
   public static DocumentoTramite getProcedureDocument(String procedureId, String documentId, String code, String entidad) throws CatalogoTramitesExcepcion
   {
      return TramiteManager.getDocument(procedureId, documentId, code, entidad);
   }

   /**
    * Actualiza el objeto trámite-documento que se pasa como parámtro.
    * 
    * @param procedure Trámite-Documento con los nuevos datos.
    * @throws CatalogoTramitesExcepcion Si se produce algún error. 
    */      
   public static void updateProcedureDocument(DocumentoTramiteImpl procedure, String entidad) throws CatalogoTramitesExcepcion
   {
       TramiteManager.update(procedure, entidad);
   }
   
   /**
    * Recupera una colección con los procedimientos almacenados en el sistema.
    * @return Procedures Colección de procedimientos.
    * @throws CatalogoTramitesExcepcion En caso de producirse algún error.
    */
   public static Tramites getProcedures(String entidad) throws CatalogoTramitesExcepcion
   {
      return TramiteManager.getProcedures(entidad);
   }
   /**
    * Recupera un documento a partir de su código.
    * @param code Código del documento a recuperar.
    * @return Document Documento
    * @throws CatalogoTramitesExcepcion En caso de producirse algún error.
    */   
   public static Documento getDocumentfromCode (String code, String entidad) throws CatalogoTramitesExcepcion{
       return DocumentoManager.getDocumentfromCode(code, entidad);
   }

   /**
    * Recupera la información de un órgano destinatario.
    * 
    * @param addresseeId
    *           Identificador del órgano destinatario.
    * @return El órgano destinatario.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */       
   public static OrganoDestinatarioImpl getAddressee(String addresseeId, String entidad) throws CatalogoTramitesExcepcion
   {
      return (OrganoDestinatarioImpl)OrganoDestinatarioManager.get(addresseeId, entidad);
   }

   /**
    * Añade un nuevo órgano destinatario al catálogo.
    * 
    * @param addressee
    *           Información del órgano destinatario.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */      
   public static void addAddressee(OrganoDestinatarioImpl addressee, String entidad) throws CatalogoTramitesExcepcion
   {
     OrganoDestinatarioManager.add(addressee, entidad);
   }
   
   /**
    * Elimina un órgano destinatario.
    * 
    * @param addresseeId
    *           Identificador del órgano destinatario.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */      
   public static void deleteAddressee(String addresseeId, String entidad) throws CatalogoTramitesExcepcion
   {
     OrganoDestinatarioManager.delete(addresseeId, entidad);
   }
   
   /**
    * Actualiza el órgano destinatario que se pasa como parámetro.
    * 
    * @param addressee
    *           Órgano destinatario con los nuevos datos.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */      
   public static void updateAddressee(OrganoDestinatarioImpl addressee, String entidad) throws CatalogoTramitesExcepcion
   {
     OrganoDestinatarioManager.update(addressee, entidad);
   }
   
   /**
    * Recupera la lista de órganos destinatarios.
    * 
    * @return La lista mencionada.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */      
   public static OrganosDestinatarios getAddressees(String entidad) throws CatalogoTramitesExcepcion
   {
      return OrganoDestinatarioManager.getAddressees(entidad);
   }
   
   
   /**
    * Recupera la información de un conector.
    * 
    * @param hookId
    *           Identificador del conector.
    * @return El conector.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */       
   public static ConectorImpl getHook(String hookId, String entidad) throws CatalogoTramitesExcepcion
   {
      return (ConectorImpl)ConectorManager.get(hookId, entidad);
   }

   /**
    * Añade un nuevo conector al catálogo.
    * 
    * @param hook
    *           Información del conector.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */      
   public static void addHook(ConectorImpl hook, String entidad) throws CatalogoTramitesExcepcion
   {
     ConectorManager.add(hook, entidad);
   }
   
   /**
    * Elimina un conector.
    * 
    * @param hookId
    *           Identificador del conector.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */      
   public static void deleteHook(String hookId, String entidad) throws CatalogoTramitesExcepcion
   {
     ConectorManager.delete(hookId, entidad);
   }
   
   /**
    * Actualiza el conector que se pasa como parámetro.
    * 
    * @param hook
    *           Conector con los nuevos datos.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */      
   public static void updateHook(ConectorImpl hook, String entidad) throws CatalogoTramitesExcepcion
   {
     ConectorManager.update(hook, entidad);
   }
   
   /**
    * Recupera la lista de conectores.
    * 
    * @return La lista mencionada.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */      
   public static Conectores getHooks(String entidad) throws CatalogoTramitesExcepcion
   {
      return ConectorManager.getHooks(-1, entidad);
   }
   
   
   /**
    * Recupera la lista de conectores.
    * 
    * @return La lista mencionada.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */      
   public static Conectores getHooks(int hookType, String entidad) throws CatalogoTramitesExcepcion
   {
      return ConectorManager.getHooks(hookType, entidad);
   }
   
   
   /**
    * Recupera la información de un tipo de conector.
    * 
    * @param typeId
    *           Identificador del tipo de conector.
    * @return El tipo de conector.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */       
   public static TipoConectorImpl getHookType(int typeId, String entidad) throws CatalogoTramitesExcepcion
   {
      return (TipoConectorImpl)TipoConectorManager.get(typeId, entidad);
   }

   /**
    * Añade un nuevo tipo de conector al catálogo.
    * 
    * @param hookType
    *           Información del tipo de conector.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */      
   public static void addHookType(TipoConectorImpl hookType, String entidad) throws CatalogoTramitesExcepcion
   {
     TipoConectorManager.add(hookType, entidad);
   }
   
   /**
    * Elimina un tipo de conector.
    * 
    * @param typeId
    *           Identificador del tipo de conector.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */      
   public static void deleteHookType(int typeId, String entidad) throws CatalogoTramitesExcepcion
   {
     TipoConectorManager.delete(typeId, entidad);
   }
   
   /**
    * Actualiza el tipo de conector que se pasa como parámetro.
    * 
    * @param hookType
    *           Tipo de conector con los nuevos datos.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */      
   public static void updateHookType(TipoConectorImpl hookType, String entidad) throws CatalogoTramitesExcepcion
   {
     TipoConectorManager.update(hookType, entidad);
   }
   
   /**
    * Recupera la lista de tipos de conectores.
    * 
    * @return La lista mencionada.
    * @throws CatalogoTramitesExcepcion
    *            Si se produce algún error.
    */      
   public static TiposConectores getHookTypes(String entidad) throws CatalogoTramitesExcepcion
   {
      return TipoConectorManager.getHookTypes(entidad);
   }
   
   
   public static ConectoresAutenticacion getAuthHooks (String tramiteId, String entidad) throws CatalogoTramitesExcepcion
   {
	   return ConectorAutenticacionManager.getAuthHooks(tramiteId, entidad);
   }

   public static void addAuthHooks (ConectorAutenticacionImpl conectorAutenticacion, String entidad) throws CatalogoTramitesExcepcion
   {
	  ConectorAutenticacionManager.add(conectorAutenticacion, entidad);
   }
   
   public static void deleteAuthHooks (String tramiteId, String conectorId, String entidad) throws CatalogoTramitesExcepcion
   {
	   ConectorAutenticacionManager.delete(tramiteId, conectorId, entidad);
   }
   
   public static void updateAuthHooks (ConectorAutenticacionImpl conectorAutenticacion, String oldHookId, String entidad) throws CatalogoTramitesExcepcion
   {
	   ConectorAutenticacionManager.update(conectorAutenticacion, oldHookId, entidad);
   }
   
   public static ConectorAutenticacion getAuthHook (String tramiteId, String conectorId, String entidad) throws CatalogoTramitesExcepcion
   {
	   return ConectorAutenticacionManager.get(tramiteId, conectorId, entidad);
   }
}
