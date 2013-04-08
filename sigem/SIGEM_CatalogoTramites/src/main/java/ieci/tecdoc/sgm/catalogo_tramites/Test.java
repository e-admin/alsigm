package ieci.tecdoc.sgm.catalogo_tramites;

import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion;
import ieci.tecdoc.sgm.catalogo_tramites.util.Documento;
import ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoImpl;
import ieci.tecdoc.sgm.catalogo_tramites.util.Documentos;
import ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoTramiteImpl;
import ieci.tecdoc.sgm.catalogo_tramites.util.TramiteImpl;
import ieci.tecdoc.sgm.catalogo_tramites.util.Tramites;
import ieci.tecdoc.sgm.catalogo_tramites.util.database.DocumentoTramiteDatos;
import ieci.tecdoc.sgm.core.services.dto.Entidad;

/**
 * Clase que prueba la funcionalidad contenidas del módulo del catálogo de trámites
 *
 */
public class Test {
  
  public static void main(String[] args){
    //BasicConfigurator.configure();
    CatalogoTramites ct = new CatalogoTramites();
    Entidad entidad = new Entidad();
    String codEntidad = null;
    /***************** DOCUMENTO REFERENCIADO *****************/
    try{
      boolean isReferenced = ct.isDocumentReferenced("PDF", codEntidad);
      System.out.println("isDocumentReferenced: el docuemnto PDF " + ((isReferenced)?"SI":"NO") + " está referenciado");
    }catch(CatalogoTramitesExcepcion e){
      System.out.println("isDocumentReferenced: Se ha producido un error al ver si esta referenciado documento PDF");
    }
    /*************** FIN DOCUMENTO REFERENCIADO ***************/
    
    /******************** AÑADIR UN DOCUMENTO *****************/
    try{
      DocumentoImpl docData = new DocumentoImpl();
      docData.setId("PDF");
      docData.setExtension("PDF");
      docData.setDescription("Documento en PDF");
      ct.addDocument(docData, codEntidad);
      System.out.println("addDocument: Añadido documento PDF");
    }catch(CatalogoTramitesExcepcion e){
      System.out.println("addDocument: Se ha producido un error al añadir documento");
    }
    /****************** FIN AÑADIR UN DOCUMENTO ***************/
    
    /******************* OBTENER UN DOCUMENTO *****************/
    DocumentoImpl docData1 = null;
    try{
      docData1 = ct.getDocument("PDF", codEntidad);
      System.out.println("getDocument: Obtenido documento PDF: " + docData1.toString());
    }catch(CatalogoTramitesExcepcion e){
      System.out.println("getDocument: Se ha producido un error al obtener documento");
    }
    /***************** FIN OBTENER UN DOCUMENTO ***************/
    
    /****************** MODIFICAR UN DOCUMENTO ****************/
    try{
      docData1.setDescription("Documento en formato PDF");
      ct.updateDocument(docData1, codEntidad);
      docData1 = ct.getDocument("PDF", codEntidad);
      System.out.println("updateDocument: Obtenido documento PDF modificado: " + docData1.toString());
    }catch(CatalogoTramitesExcepcion e){
      System.out.println("updateDocument: Se ha producido un error al modificar documento");
    }
    /**************** FIN MODIFICAR UN DOCUMENTO **************/
    
    /********************* AÑADIR UN TRAMITE ******************/
    try{
      TramiteImpl procData = new TramiteImpl();
      procData.setId("TRAMITE_1");
      procData.setTopic("TRAMITE1");
      procData.setDescription("Trámite Web Nivel 2");
      procData.setAddressee("0001");
      procData.setLimitDocs(false);
      ct.addProcedure(procData, codEntidad);
      System.out.println("addProcedure: Añadido trámite TRAMITE_1");
    }catch(CatalogoTramitesExcepcion e){
      System.out.println("addProcedure: Se ha producido un error al añadir tramite");
    }
    /******************* FIN AÑADIR UN TRAMITE ****************/
    
    /******************** OBTENER UN TRAMITE ******************/
    TramiteImpl procData1 = null;
    try{
      procData1 = ct.getProcedure("TRAMITE_1", false, codEntidad);
      System.out.println("getProcedure: Obtenido tramite TRAMITE_1: " + procData1.toString());
    }catch(CatalogoTramitesExcepcion e){
      System.out.println("getProcedure: Se ha producido un error al obtener tramite");
    }
    /****************** FIN OBTENER UN TRAMITE ****************/
    
    /******************* MODIFICAR UN TRAMITE *****************/
    try{
      procData1.setDescription("Trámite Web Nivel 2 modificado");
      ct.updateProcedure(procData1, codEntidad);
      procData1 = ct.getProcedure("TRAMITE_1", false, codEntidad);
      System.out.println("updateProcedure: Obtenido tramite TRAMITE_1 modificado: " + procData1.toString());
    }catch(CatalogoTramitesExcepcion e){
      System.out.println("updateProcedure: Se ha producido un error al modificar tramite");
    }
    /***************** FIN MODIFICAR UN TRAMITE ***************/
    
    /************** AÑADIR UN DOCUMENTO A TRAMITE *************/
    try{
      DocumentoTramiteImpl procDocData = new DocumentoTramiteImpl();
      procDocData.setDocumentId("PDF");
      procDocData.setProcedureId("TRAMITE_1");
      procDocData.setCode("TRAMITE_PDF");
      procDocData.setMandatory(true);
      ct.addProcedureDocument(procDocData, codEntidad);
      System.out.println("addProcedureDocument: Añadido tramite-documento TRAMITE_1-PDF");
    }catch(CatalogoTramitesExcepcion e){
      System.out.println("addProcedureDocument: Se ha producido un error al añadir tramite-documento");
    }
    /************ FIN AÑADIR UN DOCUMENTO A TRAMITE ***********/
    
    /************* OBTENER UN DOCUMENTO A TRAMITE *************/
    DocumentoTramiteDatos procDocData1 = null;
    try{
      procDocData1 = (DocumentoTramiteDatos)ct.getProcedureDocument("TRAMITE_1", "PDF", codEntidad);
      System.out.println("getProcedureDocument: Obtenido tramite-documento TRAMITE_1-PDF: " + procDocData1.toString());
    }catch(CatalogoTramitesExcepcion e){
      System.out.println("getProcedureDocument: Se ha producido un error al obtener tramite-documento");
    }
    /*********** FIN OBTENER UN DOCUMENTO A TRAMITE ***********/
    
    /************ MODIFICAR UN DOCUMENTO A TRAMITE ************/
    try{
      procDocData1.setMandatory(false);
      ct.updateProcedureDocument(procDocData1, codEntidad);
      procDocData1 = (DocumentoTramiteDatos)ct.getProcedureDocument("TRAMITE_1", "PDF", codEntidad);
      System.out.println("updateProcedureDocument: Obtenido tramite-documento TRAMITE_1-PDF modificado: " + procDocData1.toString());
    }catch(CatalogoTramitesExcepcion e){
      System.out.println("updateProcedureDocument: Se ha producido un error al modificar tramite-documento");
    }
    /********** FIN MODIFICAR UN DOCUMENTO A TRAMITE **********/
  
    /************** OBTENER DOCUMENTO POR CODIGO **************/
    try{
      Documento doc = ct.getDocumentfromCode("TRAMITE_PDF", codEntidad);
      System.out.println("getDocumentfromCode: Obtenido documento por codigo: " + doc.toString());
    }catch(CatalogoTramitesExcepcion e){
      System.out.println("getDocumentfromCode: Se ha producido un error al obtener documento por codigo");
    }
    /************ FIN OBTENER DOCUMENTO POR CODIGO ************/
    
    /******************** OBTENER DOCUMENTOS ******************/
    try{
      Documentos docs = ct.getDocuments(codEntidad);
      System.out.println("getDocuments: Obtenidos documentos");
      System.out.println(docs.toXML(false));
    }catch(CatalogoTramitesExcepcion e){
      System.out.println("getDocuments: Se ha producido un error al obtener documentos");
    }
    /***************** FIN OBTENER DOCUMENTOS *****************/
    
    /******************** OBTENER TRAMITES ********************/
    try{
      Tramites procs = ct.getProcedures(codEntidad);
      System.out.println("getProcedures: Obtenidos trámites");
      System.out.println(procs.toXML(false));
    }catch(CatalogoTramitesExcepcion e){
      System.out.println("getProcedures: Se ha producido un error al obtener tramites");
    }
    /****************** FIN OBTENER TRAMITES ******************/
    
    /*************** OBTENER TRAMITE-DOCUMENTOS ***************/
    try{
      Documentos docs = ct.getProcedureDocuments("TRAMITE_1", codEntidad);
      System.out.println("getProcedureDocuments: Obtenidos documentos del tramite TRAMITE_1");
      System.out.println(docs.toXML(false));
    }catch(CatalogoTramitesExcepcion e){
      System.out.println("getDocuments: Se ha producido un error al obtener documentos");
    }
    /************ FIN OBTENER TRAMITE-DOCUMENTOS **************/
    
    /***************** DOCUMENTO REFERENCIADO *****************/
    try{
      boolean isReferenced = ct.isDocumentReferenced("PDF", codEntidad);
      System.out.println("isDocumentReferenced: el docuemnto PDF " + ((isReferenced)?"SI":"NO") + " está referenciado");
    }catch(CatalogoTramitesExcepcion e){
      System.out.println("isDocumentReferenced: Se ha producido un error al ver si esta referenciado documento PDF");
    }
    /*************** FIN DOCUMENTO REFERENCIADO ***************/
    
    /************* ELIMINAR UN DOCUMENTO A TRAMITE ************/
    try{
      DocumentoTramiteDatos procDocData = new DocumentoTramiteDatos();
      procDocData.setDocumentId("PDF");
      procDocData.setProcedureId("TRAMITE_1");
      ct.deleteProcedureDocument(procDocData, codEntidad);
      System.out.println("deleteProcedureDocument: Eliminado el trámite-documento TRAMITE_1-PDF");
    }catch(CatalogoTramitesExcepcion e){
      System.out.println("deleteProcedureDocument: Se ha producido un error al eliminar tramite-documento");
    }
    /*********** FIN ELIMINAR UN DOCUMENTO A TRAMITE **********/
    
    /****************** ELIMINAR UN TRAMITE *******************/
    try{
      ct.deleteProcedure("TRAMITE_1", codEntidad);
      System.out.println("deleteProcedure: Eliminado el trámite TRAMITE_1");
    }catch(CatalogoTramitesExcepcion e){
      System.out.println("deleteProcedure: Se ha producido un error al eliminar tramite");
    }
    /**************** FIN ELIMINAR UN TRAMITE *****************/
    
    /******************* ELIMINAR UN DOCUMENTO ****************/
    try{
      ct.deleteDocument("PDF", codEntidad);
      System.out.println("deleteDocument: Eliminado documento PDF");
    }catch(CatalogoTramitesExcepcion e){
      System.out.println("deleteDocument: Se ha producido un error al eliminar documento");
    }
    /***************** FIN ELIMINAR UN DOCUMENTO **************/
    

  }
}
