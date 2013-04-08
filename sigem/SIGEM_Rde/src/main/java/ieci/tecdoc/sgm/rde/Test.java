package ieci.tecdoc.sgm.rde;

import ieci.tecdoc.sgm.rde.database.DocumentoHashInfo;
import ieci.tecdoc.sgm.rde.database.DocumentoInfo;
import ieci.tecdoc.sgm.rde.database.util.Utilities;
import ieci.tecdoc.sgm.rde.exception.RepositorioDocumentosCodigosError;
import ieci.tecdoc.sgm.rde.exception.RepositorioDocumentosExcepcion;
import ieci.tecdoc.sgm.rde.exception.GuidIncorrectoExcepcion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;


public class Test
{
  /**
   * Programa de prueba de todas las funciones contenidas en ContenedorDocumentosManager.java
   * @param args Ninguno
   */
  public static void main (String[] args){
    ContenedorDocumentosManager manager = new ContenedorDocumentosManager();
    
    /***************** ALMACENAR DOCUMENTO INPUTSTREAM ***************/
    String guid1 = ""; 
    try{
      guid1 = manager.storeDocument("", Utilities.getStreamFromFile("d:\\m.doc"), Utilities.getExtension("c:\\m.doc"), "");
      System.out.println("GUID: " + guid1);
    }catch (RepositorioDocumentosExcepcion e){
      System.out.println("storeDocument(InputStream): Error en RDE");
    }catch (FileNotFoundException e){
      System.out.println("storeDocument(InputStream): Fichero no encontrado");
    }
    /*************** FIN ALMACENAR DOCUMENTO INPUTSTREAM *************/
    
    /****************** OBTENER DOCUMENTO ****************************/
    try{  
      DocumentoInfo docInfo = manager.retrieveDocument("", guid1, "");
      System.out.println("Extensión: " + docInfo.getExtension() + " MimeType: " + docInfo.getMimeType());
      Utilities.createFile("d:\\", docInfo.getGuid(), docInfo.getExtension(), docInfo.getContent());
    }catch (IOException e){
      System.out.println("retrieveDocument: Error al guardar fichero");
    }catch (GuidIncorrectoExcepcion e){
      System.out.println("retrieveDocument: Guid no válido");
    }catch (RepositorioDocumentosExcepcion e){
      System.out.println("retrieveDocument: Error en RDE");
    }catch (Exception e){
      System.out.println("retrieveDocument: Error en manejo de streams");
    }
    /**************** FIN OBTENER DOCUMENTO **************************/
    
    /***************** ALMACENAR DOCUMENTO INPUTSTREAM ***************/
    String guid2 = "";
    try{
      guid2 = manager.storeDocument("", Utilities.getStreamFromFile("d:\\m.doc"), Utilities.getExtension("d:\\m.doc"), "");
      System.out.println("GUID: " + guid2);
    }catch (RepositorioDocumentosExcepcion e){
      System.out.println("storeDocument(InputStream): Error en RDE");
    }catch (FileNotFoundException e){
      System.out.println("storeDocument(InputStream): Fichero no encontrado");
    }
    /*************** FIN ALMACENAR DOCUMENTO INPUTSTREAM *************/

    /****************** OBTENER DOCUMENTO ****************************/
    try{  
      DocumentoInfo docInfo = manager.retrieveDocument("", guid2, "");
      System.out.println("Extensión: " + docInfo.getExtension() + " MimeType: " + docInfo.getMimeType());
      Utilities.createFile("d:\\", docInfo.getGuid(), docInfo.getExtension(), docInfo.getContent());
    }catch (IOException e){
      System.out.println("retrieveDocument: Error al guardar fichero");
    }catch (GuidIncorrectoExcepcion e){
      System.out.println("retrieveDocument: Guid no válido");
    }catch (RepositorioDocumentosExcepcion e){
      System.out.println("retrieveDocument: Error en RDE");
    }catch (Exception e){
      System.out.println("retrieveDocument: Error en manejo de streams");
    }
    /**************** FIN OBTENER DOCUMENTO **************************/
    
    /***************** ALMACENAR DOCUMENTO PATH **********************/
    String guid3 = "";
    try{
      guid3 = manager.storeDocument("", "d:\\m.doc", Utilities.getExtension("d:\\m.doc"), "");
      System.out.println("GUID: " + guid3);
    }catch (RepositorioDocumentosExcepcion e){
      if (e.getErrorCode() == RepositorioDocumentosCodigosError.EC_FILE_NOT_FOUND)
        System.out.println("storeDocument(Path): Fichero no encontrado");
      else System.out.println("storeDocument(Path): Error en RDE");
    }
    /*************** FIN ALMACENAR DOCUMENTO PATH ********************/
    
    /****************** OBTENER DOCUMENTO ****************************/
    try{  
      DocumentoInfo docInfo = manager.retrieveDocument("", guid3, "");
      System.out.println("Extensión: " + docInfo.getExtension() + " MimeType: " + docInfo.getMimeType());
      Utilities.createFile("d:\\", docInfo.getGuid(), docInfo.getExtension(), docInfo.getContent());
    }catch (IOException e){
      System.out.println("retrieveDocument: Error al guardar fichero");
    }catch (GuidIncorrectoExcepcion e){
      System.out.println("retrieveDocument: Guid no válido");
    }catch (RepositorioDocumentosExcepcion e){
      System.out.println("retrieveDocument: Error en RDE");
    }catch (Exception e){
      System.out.println("retrieveDocument: Error en manejo de streams");
    }
    /**************** FIN OBTENER DOCUMENTO **************************/
    
    /**************** OBTENER RUTA FICHERO ***************************/
    try{
      String path = manager.retrieveDocument("", guid1, "d:\\temp\\", "");
      System.out.println("Path: " + path);
    }catch (GuidIncorrectoExcepcion e){
      System.out.println("retrieveDocument: Guid no válido");
    }catch (RepositorioDocumentosExcepcion e){
      if (e.getErrorCode() == RepositorioDocumentosCodigosError.EC_FILE_NOT_FOUND)
        System.out.println("retrieveDocument: Fichero no encontrado");
      else System.out.println("retrieveDocument: Error en RDE");
    }
    /************** FIN OBTENER RUTA FICHERO *************************/
    
    /******************* BORRAR DOCUMENTO ****************************/
    try{
      manager.deleteDocument("", guid1, "");
    }catch (GuidIncorrectoExcepcion e){
      System.out.println("deleteDocument: Guid no válido");
    }catch (RepositorioDocumentosExcepcion e){
      System.out.println("deleteDocument: Error en RDE");
    }
    /***************** FIN BORRAR DOCUMENTO **************************/
    
    /***************** OBTENER HASH DOCUMENTO ************************/
    try{
      String hash = manager.getHash("", guid2, "");
      System.out.println("Hash: " + hash);
    }catch (GuidIncorrectoExcepcion e){
      System.out.println("getHash: Guid no válido");
    }catch (RepositorioDocumentosExcepcion e){
      System.out.println("getHash: Error en RDE");
    }
    /*************** FIN OBTENER HASH DOCUMENTO **********************/
    
    /************ ALMACENAR DOCUMENTOS Y OBTENER GUID Y HASH *********/
    try{
      Vector vector = new Vector();
      DocumentoHashInfo dhi1 = new DocumentoHashInfo("d:\\temp\\cc1.rtf", Utilities.getExtension("d:\\temp\\cc1.rtf"));
      DocumentoHashInfo dhi2 = new DocumentoHashInfo("d:\\temp\\cc2.rtf", Utilities.getExtension("d:\\temp\\cc2.rtf"));
      vector.add(dhi1);
      vector.add(dhi2);
      manager.storeDocumentsAndGetHashes("", vector, "");
      for(int i=0; i<vector.size(); i++){
        DocumentoHashInfo aux = (DocumentoHashInfo)vector.get(i);
        System.out.println("Path: " + aux.getPath() + " GUID: " + aux.getGuid() + " Hash: " + aux.getHash());
      }
    }catch (RepositorioDocumentosExcepcion e){
      if (e.getErrorCode() == RepositorioDocumentosCodigosError.EC_FILE_NOT_FOUND)
        System.out.println("storeDocumentsAndGetHashes: Fichero no encontrado");
      else System.out.println("storeDocumentsAndGetHashes: Error en RDE");
    }
    /********** FIN ALMACENAR DOCUMENTOS Y OBTENER GUID Y HASH *******/
  }
}
