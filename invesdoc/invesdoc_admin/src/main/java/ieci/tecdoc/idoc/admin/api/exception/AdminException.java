
package ieci.tecdoc.idoc.admin.api.exception;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.core.exception.ResourceMessage;

public class AdminException 
{

   /**
    * Lanza una excepción específica del sistema llamante.
    * 
    * @param errorCode Código de error de la excepción.
    * @throws ieci.tecdoc.core.exception.IeciTdException Excepción generada.
    */
    
   public static void throwException(long errorCode) throws IeciTdException
   {
      throw new IeciTdException(Long.toString(errorCode), 
                                ResourceMessage.getMessage(errorCode,
                                AdminResourceFile.ADMIN_RESOURCE_FILE));
   }

   private AdminException()
   {
   }
}