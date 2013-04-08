/*
 * Created on 12-ago-2005
 * @autor IECI S.A.
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ieci.tecdoc.sgm.autenticacion.exception;

import ieci.tecdoc.sgm.rde.exception.SgmExcepcion;

/**
 * Excepción de autenticación. 
 */
public class AutenticacionExcepcion extends SgmExcepcion
  {
      /**
       * Constructor de excepción de autenticacion.
       * @param errorCode Código de error.
       * @param msg Mensaje de error.
       */ 
   public AutenticacionExcepcion(String errorCode)
   {
      super(errorCode);   
   }
  
   /**
     * Constructor de excepción de autenticacion.
     * @param errorCode Código de error.
     * @param msg Mensaje de error.
     */ 
  public AutenticacionExcepcion(long errorCode)
  {
     super(errorCode);   
  }
   
   /**
    * Constructor de excepción de autenticacion.
    * @param msg mensaje de error.
    * @param cause Cause del error.
    */
   public AutenticacionExcepcion(String msg, Exception cause)
   {
      super(msg, cause);
   }
   
   /**
    * Devuelve el fichero de mensajes de error
    * @return String Fichero de mensajes de error
    */
   public String getMessagesFile() {
  
     return RESOURCE_FILE;
  }
  
  private static String RESOURCE_FILE = "ieci.tecdoc.sgm.autenticacion.resources.error";
      
}
