package ieci.tecdoc.sgm.core.services.catastro;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;

public class CatastroServicioException extends SigemException {

	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 

	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.catastro.CatastroExceptionMessages";
	
	
	public CatastroServicioException(long errorCode) {
		this(errorCode, null);
	}
	
	public CatastroServicioException(String message) {
		this(message, null);
	}
	
	public CatastroServicioException(Throwable cause) {
		this(0, cause);
	}
	
	public CatastroServicioException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public CatastroServicioException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public CatastroServicioException(String message, Throwable cause) {
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
