package ieci.tecdoc.sgm.core.services.terceros;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;

/**
 * Excepción lanzada cuando ocurre algún error en el servicio de Terceros.
 *
 */
public class TercerosException extends SigemException {

	// Tipos de excepcion
		
	public static final long EC_PREFIX = 1910010000;
	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 

	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.terceros.TercerosExceptionMessages";
	
	
	public TercerosException(long errorCode) {
		this(errorCode, null);
	}
	
	public TercerosException(String message) {
		this(message, null);
	}
	
	public TercerosException(Throwable cause) {
		this(0, cause);
	}
	
	public TercerosException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public TercerosException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public TercerosException(String message, Throwable cause) {
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
