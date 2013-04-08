package ieci.tecdoc.sgm.core.services.consolidacion;

import ieci.tecdoc.sgm.core.exception.SigemException;

import java.util.Locale;

/**
 * Excepción lanzada cuando ocurre algún error en la consolidación.
 *
 */
public class ConsolidacionException extends SigemException {

	// Tipos de excepción
	public static final long EC_PREFIX = 310010000;
	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 

	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.consolidacion.ConsolidacionExceptionMessages";
	
	
	public ConsolidacionException(long errorCode) {
		this(errorCode, null);
	}
	
	public ConsolidacionException(String message) {
		this(message, null);
	}
	
	public ConsolidacionException(Throwable cause) {
		this(ConsolidacionException.EXC_GENERIC_EXCEPCION, cause);
	}
	
	public ConsolidacionException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public ConsolidacionException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public ConsolidacionException(String message, Throwable cause) {
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
