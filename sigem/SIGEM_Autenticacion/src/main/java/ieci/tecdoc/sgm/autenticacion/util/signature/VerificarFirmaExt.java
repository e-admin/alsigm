
package ieci.tecdoc.sgm.autenticacion.util.signature;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Interfaz de corportamiento de clases para la validación de firmas.
 * 
 * @author IECISA
 *
 */
public interface VerificarFirmaExt {
   
	/**
	 * Método de verificación de firma.
	 * @param data Datos a verificar.
	 * @param additionalInfo Información adicional para la verificación.
	 * @return SignaturesValidationInfo Información del resultado de la validación.
	 * @throws Exception En caso de producirse algún error.
	 */   
   public abstract ValidacionFirmasInfo verifySign(InputStream data, String additionalInfo) throws Exception;

   /**
    * Método que obtiene el documento incluido en la firma.
    * @param data Datos de la firma.
    * @param additionalInfo Información adicional necesaria.
    * @return ByteArrayOutputStream Con los datos del documento.
    * @throws Exception En caso de producirse algún error.
    */
   public abstract ByteArrayOutputStream getDocument(InputStream data, String additionalInfo) throws Exception;
   
}
