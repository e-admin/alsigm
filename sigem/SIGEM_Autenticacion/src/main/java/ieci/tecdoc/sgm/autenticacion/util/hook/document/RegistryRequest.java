
package ieci.tecdoc.sgm.autenticacion.util.hook.document;


import ieci.tecdoc.sgm.autenticacion.util.document.DocumentoExt;

import java.io.InputStream;

/**
 * Clase que representa un documento de solicitud de registro.
 * 
 * @author IECISA.
 *
 */
public class RegistryRequest implements DocumentoExt {
   
   public RegistryRequest() {
   }

   /**
    * Método para la validación de un documento de solicitud de registro.
    * @param data Datos del documento.
    * @param additionalInfo Información adicional necesaria para la validación. No utilizada en este tipo de documento.
    */   
   public String validate(InputStream data, String additionalInfo) throws Exception
   {
      String valid = "1";
      
      return valid;
   }
}
