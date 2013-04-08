package ieci.tecdoc.sgm.core.services.tramitacion;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;

/**
 * Excepción lanzada cuando ocurre algún error en el servicio de Tramitación.
 *
 */
public class TramitacionException extends SigemException {

	// Tipos de excepcion
		
	public static final long EC_PREFIX = 1810010000;
	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 

	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.tramitacion.TramitacionExceptionMessages";
	
	
	public TramitacionException(long errorCode) {
		this(errorCode, null);
	}
	
	public TramitacionException(String message) {
		this(message, null);
	}
	
	public TramitacionException(Throwable cause) {
		this(0, cause);
	}
	
	public TramitacionException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public TramitacionException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public TramitacionException(String message, Throwable cause) {
	   super(message, cause);
	   errorMessage = message;
   }
   
   /**
    * Devuelve el nombre del archivo de recursos que contiene los mensajes para
    * esta excepción.
    * 
    * @return El nombre del archivo de recursos mencionado.
    */

   public String getMessagesFile() {

      return RESOURCE_FILE;
   }

}
