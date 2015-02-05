package ieci.tecdoc.sgm.core.services.antivirus;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;

public class AntivirusException extends SigemException {

	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 
	public static final long EXC_ALMACENAR_FICHERO	= EC_PREFIX + 1; 
	public static final long EXC_COMPROBAR_VIRUS	= EC_PREFIX + 2;
	public static final long EXC_FICHERO_NO_ENCONTRADO	= EC_PREFIX + 3;
	public static final long EXC_EJECUCION_NO_VALIDA	= EC_PREFIX + 4;
	public static final long EXC_NO_ANTIVIRUS= EC_PREFIX + 5;
	
	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.antivirus.AntivirusExceptionMessages";
	
	
	public AntivirusException(long errorCode) {
		this(errorCode, null);
	}
	
	public AntivirusException(String message) {
		this(message, null);
	}
	
	public AntivirusException(Throwable cause) {
		this(0, cause);
	}
	
	public AntivirusException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public AntivirusException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public AntivirusException(String message, Throwable cause) {
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
