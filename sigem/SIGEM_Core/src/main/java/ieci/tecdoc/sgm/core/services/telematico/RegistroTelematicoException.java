package ieci.tecdoc.sgm.core.services.telematico;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;

/**
 * Excepción específica de registro.
 * 
 * @author IECISA
 *
 */
public class RegistroTelematicoException extends SigemException
{
	public static final long EC_PREFIX = 900010000;
	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 
	
	public RegistroTelematicoException(long errorCode) {
		this(errorCode, null);
	}
	
	public RegistroTelematicoException(String message) {
		this(message, null);
	}
	
	public RegistroTelematicoException(Throwable cause) {
		this(0, cause);
	}
	
	public RegistroTelematicoException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public RegistroTelematicoException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public RegistroTelematicoException(String message, Throwable cause) {
	   super(message, cause);
	   errorMessage = message;
   }
   
   public String getMessagesFile() {

     return RESOURCE_FILE;
  }

  private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.telematico.RegistroTelematicoExceptionMessage";
}
