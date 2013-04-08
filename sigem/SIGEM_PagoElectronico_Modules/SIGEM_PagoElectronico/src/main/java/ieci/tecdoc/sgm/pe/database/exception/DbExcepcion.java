/**
 * @author Alvaro Ugarte Gómez
 * 
 * Fecha de Creación: 22-jun-2004
 */

package ieci.tecdoc.sgm.pe.database.exception;
/*
 * $Id: DbExcepcion.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
import ieci.tecdoc.sgm.pe.exception.*;
/**
 * Excepción lanzada cuando se produce un error en el Sistema de Archivo
 * Digital reseñable. <br><br>Además de la información contenida en todos los
 * objetos Exception (message, cause y stack trace), cada instancia de esta
 * clase contiene también un código de error.
 */

public class DbExcepcion extends PagoElectronicoExcepcion {

   /**
    * Construye un objeto de la clase.
    * 
    * @param errorCode
    *           Código de error.
    */

   public DbExcepcion(long errorCode) {
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

   public DbExcepcion(long errorCode, Throwable cause) {
      super(errorCode, cause);
   }

   public String getMessagesFile() {
      return RESOURCE_FILE;
   }

   private static String RESOURCE_FILE = "ieci.tecdoc.sgm.pe.resources.error";
   
}
