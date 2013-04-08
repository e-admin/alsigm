
package ieci.tecdoc.sgm.autenticacion.util.hook.document;


import ieci.tecdoc.sgm.autenticacion.util.document.DocumentoExt;

import java.io.InputStream;

/**
 * Clase que representa el documento de justificante de registro.
 * 
 * @author IECISA
 *
 */
public class RegistryReceipt implements DocumentoExt {
   
   public RegistryReceipt() {
   }
   
   /**
    * Método para la validación de un justificante de registro.
    * @param data Datos del justitificante.
    * @param additionalInfo Información adicional para la validación. No necesario en este tipo de documento.
    */   
   public String validate(InputStream data, String additionalInfo) throws Exception
   {
      String valid = "1";
      
      return valid;
   }
}
