package ieci.tecdoc.sgm.core.services.mensajes_cortos;

import ieci.tecdoc.sgm.core.exception.SigemException;

import java.util.Locale;

/**
 * Excepción lanzada cuando ocurre algún error en el servicio de mensajes cortos.
 *
 */
public class MensajesCortosException extends SigemException {

	// Tipos de excepción
	public static final long EC_PREFIX = 300010000;
	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 

	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.mensajes_cortos.MensajesCortosExceptionMessages";
	
	
	public MensajesCortosException(long errorCode) {
		this(errorCode, null);
	}
	
	public MensajesCortosException(String message) {
		this(message, null);
	}
	
	public MensajesCortosException(Throwable cause) {
		this(EXC_GENERIC_EXCEPCION, cause);
	}
	
	public MensajesCortosException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public MensajesCortosException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public MensajesCortosException(String message, Throwable cause) {
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
