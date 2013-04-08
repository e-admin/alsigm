package ieci.tecdoc.sgm.core.services.tiempos;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;

public class TiemposException extends SigemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Tipos de excepción
	public static final long EC_PREFIX = 320010000;
	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 

	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.tiempos.TiemposExceptionMessages";
	
	
	public TiemposException(long errorCode) {
		this(errorCode, null);
	}
	
	public TiemposException(String message) {
		this(message, null);
	}
	
	public TiemposException(Throwable cause) {
		this(EXC_GENERIC_EXCEPCION, cause);
	}
	
	public TiemposException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public TiemposException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public TiemposException(String message, Throwable cause) {
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
