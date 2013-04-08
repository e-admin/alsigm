
package ieci.tecdoc.sgm.autenticacion.util.hook.document;


import ieci.tecdoc.sgm.autenticacion.util.document.DocumentoExt;

import java.io.InputStream;

/**
 * Clase que representa un documento PDF.
 * 
 * @author IECISA
 *
 */
public class PDF implements DocumentoExt {
   
   public PDF() {
   }
   
    /**
    * Método para la validación de un documento PDF.
    * @param data Datos del documento.
    * @param additionalInfo Información adicional necesaria para la validación. No necesaria para este tipo de documento.
    * @return String Cadena con el resultado. 1 si el documento es un PDF. 0 si no lo es.
    */
   public String validate(InputStream data, String additionalInfo) throws Exception
   {
      String valid = "0";
      byte[] header = new byte[4];
      
      data.read(header);
      if (new String(header).compareTo("%PDF") == 0)
         valid = "1";
      
      return valid;
   }
   
}
