/**
 * @author Alvaro Ugarte Gómez
 * 
 * Fecha de Creación: 22-jun-2004
 */

package ieci.tecdoc.sgm.core.services.repositorio;

import ieci.tecdoc.sgm.core.exception.SigemException;

/**
 * Excepción lanzada cuando se produce un error en el Sistema de Archivo
 * Digital reseñable. <br><br>Además de la información contenida en todos los
 * objetos Exception (message, cause y stack trace), cada instancia de esta
 * clase contiene también un código de error.
 */

public class GuidIncorrectoRdeExcepcion extends SigemException {

	public static final String GUID_INCORRECTO	=	"1710006001";
   /**
    * Construye un objeto de la clase.
    * 
    * @param errorCode
    *           Código de error.
    */

   public GuidIncorrectoRdeExcepcion(long errorCode) {
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

   public GuidIncorrectoRdeExcepcion(long errorCode, Throwable cause) {
      super(errorCode, cause);
   }

   /**
    * Devuelve la ruta del fichero de mensajes de error
    * @return Ruta del fichero de mensajes de error
    */
   public String getMessagesFile() {
      return RESOURCE_FILE;
   }

   private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.repositorio.RepositorioDocumentosTramitacionExceptionMessages";
   
}
