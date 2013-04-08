/**
 * @author Alvaro Ugarte Gómez
 * 
 * Fecha de Creación: 22-jun-2004
 */

package ieci.tecdoc.sgm.nt.exception;

/**
 * Excepción lanzada cuando se produce un error en el Sistema de Archivo
 * Digital reseñable. <br><br>Además de la información contenida en todos los
 * objetos Exception (message, cause y stack trace), cada instancia de esta
 * clase contiene también un código de error.
 */

public class DocumentosRepositorioExcepcion extends SgmException {

   /**
    * Construye un objeto de la clase.
    * 
    * @param errorCode
    *           Código de error.
    */

   public DocumentosRepositorioExcepcion(long errorCode) {
      super(errorCode);
   }

   /**
    * Construye un objeto de la clase.
    * 
    * @param errorCode
    *           Código de error.
    * @param cause
    *           Excepción que ha causado ésta.
    */

   public DocumentosRepositorioExcepcion(long errorCode, Throwable cause) {
      super(errorCode, cause);
   }

   public String getMessagesFile() {
      return RESOURCE_FILE;
   }

   private static String RESOURCE_FILE = "ieci.tecdoc.sgm.nt.resources.error";
   
}
