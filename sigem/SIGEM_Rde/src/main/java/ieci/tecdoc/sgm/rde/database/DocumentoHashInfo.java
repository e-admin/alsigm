/**
 * @author José Antonio Nogales Rincón
 * 
 * Fecha de creación: 19-mar-2007
 */

package ieci.tecdoc.sgm.rde.database;

import java.io.Serializable;

/**
 * Clase que almacena información relacionada con los documentos
 *
 */
public class DocumentoHashInfo implements Serializable {

   /**
    * Construye un objeto de la clase.
    */
   public DocumentoHashInfo() {
      super();
   }

   /**
    * Construye un objeto de la clase a partir de la ruta del documento
    * y la extensión.
    */
   public DocumentoHashInfo(String path, String extension){
     super();
     this.extension = extension;
     this.path = path;
  }
   
   /**
    * Devuelve el hash o resumen de un documento
    * @return String Hash o resumen de un documento
    */
   public String getHash() {

      return hash;
   }

   /**
    * Establece el hash o resumen de un documento
    * @param hash Hash o resumen de un documento
    */
   public void setHash(String hash) {
      this.hash = hash;
   }

   /**
    * Devuelve la localización del documento
    * @return String Localización del documento
    */
   public String getPath() {
      return path;
   }

   /**
    * Establece la localización del documento
    * @param path Localización del documento
    */
   public void setPath(String path){

      this.path = path;
   }

   /**
    * Devuelve la extensión del documento
    * @return String Extensión del documento
    */
   public String getExtension() {
      return extension;
   }

   /**
    * Establece la extensión del documento
    * @param extension Extensión del documento
    */
   public void setExtension(String extension) {
      this.extension = extension;
   }

   /**
    * Devuelve el GUID de un documento
    * @return StringGUID de un documento
    */
   public String getGuid() {
      return guid;
   }

   /**
    * Establece el GUID de un documento
    * @param guid GUID de un documento
    */
   public void setGuid(String guid) {
      this.guid = guid;
   }

   private String guid;
   private String path;
   private String extension;
   private String hash;
}