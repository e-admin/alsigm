
package ieci.tecdoc.sgm.autenticacion.util.document;

import java.io.InputStream;

/**
 * Interfaz para documentos.
 * 
 * @author IECISA
 *
 */
public interface DocumentoExt {
   
   	/**
	 * Método que implementa la validación del documento.
	 * @param data Datos a validar.
	 * @param additionalInfo Información adicional necesaria para la validación.
	 * @return String Con el resultado de la validación.
	 * @throws Exception En caso de producirse algún error.
	 */
   public abstract String validate(InputStream data, String additionalInfo) throws Exception;
   
}
