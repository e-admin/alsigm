package ieci.tecdoc.sgm.core.services.publicador;

import ieci.tecdoc.sgm.core.exception.SigemException;

import java.util.Locale;

/**
 * Excepción producida en el servicio de publicación.
 *
 */
public class PublicadorException extends SigemException {
	
	public static final long EC_PREFIX 				= 290000000;
	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 
	public static final long PARAMETERS_INVALID		= EC_PREFIX + 1;
	
	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.publicador.PublicadorExceptionMessages";
	
	
	public PublicadorException(long errorCode) {
		this(errorCode, null);
	}
	
	public PublicadorException(String message) {
		this(message, null);
	}
	
	public PublicadorException(Throwable cause) {
		this(0, cause);
	}
	
	public PublicadorException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public PublicadorException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public PublicadorException(String message, Throwable cause) {
	   super(message, cause);
	   errorMessage = message;
   }
   
   public String getMessagesFile() {
      return RESOURCE_FILE;
   }

}
