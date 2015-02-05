package ieci.tecdoc.sgm.core.services.pago;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;

public class PagoTelematicoException extends SigemException {

	// Tipos de excepcion
		
	public static final long EC_PREFIX = 1310010000;
	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 

	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.pago.PagoTelematicoExceptionMessages";
	
	
	public PagoTelematicoException(long errorCode) {
		this(errorCode, null);
	}
	
	public PagoTelematicoException(String message) {
		this(message, null);
	}
	
	public PagoTelematicoException(Throwable cause) {
		this(0, cause);
	}
	
	public PagoTelematicoException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public PagoTelematicoException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public PagoTelematicoException(String message, Throwable cause) {
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
