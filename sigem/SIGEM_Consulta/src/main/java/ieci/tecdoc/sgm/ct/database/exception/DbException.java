/**
 * @author Javier Septien Arceredillo
 * 
 * Fecha de Creación: 17-may-2007
 */

package ieci.tecdoc.sgm.ct.database.exception;

import ieci.tecdoc.sgm.ct.exception.SgmException;
/**
 * Excepción lanzada cuando se produce un error en el Sistema de Archivo
 * Digital reseñable. <br><br>Además de la información contenida en todos los
 * objetos Exception (message, cause y stack trace), cada instancia de esta
 * clase contiene también un código de error.
 */

public class DbException extends SgmException {

   /**
    * Construye un objeto de la clase.
    * 
    * @param errorCode
    *           Código de error.
    */

   public DbException(long errorCode) {
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

   public DbException(long errorCode, Throwable cause) {
      super(errorCode, cause);
   }

   public String getMessagesFile() {
      return RESOURCE_FILE;
   }

   private static String RESOURCE_FILE = "ieci.tecdoc.sgm.ct.resources.error";
   
}
