/*
 * IncorrectGuidException.java
 *
 * Created on 18 de mayo de 2007, 17:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.exception;

/**
 *
 * @author Usuario
 */
public class ServicioWebExcepcion extends SgmException {
    
    /**
    * Construye un objeto de la clase.
    * 
    * @param errorCode
    *           Código de error.
    */

   public ServicioWebExcepcion(long errorCode) {
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

   public ServicioWebExcepcion(long errorCode, Throwable cause) {
      super(errorCode, cause);
   }

   public String getMessagesFile() {
      return RESOURCE_FILE;
   }

   private static String RESOURCE_FILE = "ieci.tecdoc.sgm.nt.resources.error";
    
}
