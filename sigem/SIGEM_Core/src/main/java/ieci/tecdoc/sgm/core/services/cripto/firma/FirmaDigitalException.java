package ieci.tecdoc.sgm.core.services.cripto.firma;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;

public class FirmaDigitalException extends SigemException {
	
	// Tipos de excepcion
	public static final long EC_PREFIX = 1111000000;
	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX +1;
	public static final long EXC_KEYSTORE_EXCEPTION = EC_PREFIX +2;
	public static final long EXC_CERTIFICATE_EXCEPTION = EC_PREFIX +3;
	public static final long EXC_PROVIDER_EXCEPTION = EC_PREFIX +4; 
	public static final long EXC_SIGN_EXCEPTION = EC_PREFIX + 5;
	
	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.cripto.firma.FirmaDigitalExceptionMessages";
	
	
	public FirmaDigitalException(long errorCode) {
		this(errorCode, null);
	}
	
	public FirmaDigitalException(String message) {
		this(message, null);
	}
	
	public FirmaDigitalException(Throwable cause) {
		this(0, cause);
	}
	
	public FirmaDigitalException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public FirmaDigitalException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public FirmaDigitalException(String message, Throwable cause) {
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
