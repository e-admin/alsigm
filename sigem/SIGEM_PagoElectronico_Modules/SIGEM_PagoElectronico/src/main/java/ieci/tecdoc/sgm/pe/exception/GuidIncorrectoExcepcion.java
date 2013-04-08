/**
 * @author Carlos Navas Cerdeiras
 * 
 * Fecha de Creación: 10/05/2007
 */
package ieci.tecdoc.sgm.pe.exception;
/*
 * $Id: GuidIncorrectoExcepcion.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
/**
 * Excepción lanzada cuando se produce un error reseñable. 
 * <br><br>Además de la información contenida en todos los
 * objetos Exception (message, cause y stack trace), cada instancia de esta
 * clase contiene también un código de error.
 */

public class GuidIncorrectoExcepcion extends PagoElectronicoExcepcion {

   /**
    * Construye un objeto de la clase.
    * 
    * @param errorCode
    *           Código de error.
    */

   public GuidIncorrectoExcepcion(long errorCode) {
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

   public GuidIncorrectoExcepcion(long errorCode, Throwable cause) {
      super(errorCode, cause);
   }

   public String getMessagesFile() {
      return RESOURCE_FILE;
   }

   private static String RESOURCE_FILE = "ieci.tecdoc.sgm.rde.resources.error";
   
}
