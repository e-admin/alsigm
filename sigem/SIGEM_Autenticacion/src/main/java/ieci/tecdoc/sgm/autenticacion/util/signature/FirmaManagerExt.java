
package ieci.tecdoc.sgm.autenticacion.util.signature;

import java.io.InputStream;

/**
 * Clase con el comportamiento básico de un gestor de firmas.
 * 
 * @author IECISA,
 *
 */
public class FirmaManagerExt 
{
   /**
    * Método de validación de firma.
    * @param data Datos a validar.
    * @param auxiliaryInformation Información adicional.
    * @param implementationTarget Conector que implementa la lógica de encriptación.
    * @return
    */
   public ValidacionFirmasInfo validate(InputStream data, 
               String[] auxiliaryInformation, String implementationTarget) throws Exception
   {
      return null;
   }
}