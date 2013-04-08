package ieci.tecdoc.sgm.core.services.permisos_backoffice;

import ieci.tecdoc.sgm.core.exception.SigemException;

import java.util.Locale;

public class PermisosBackOfficeException extends SigemException{
	// Tipos de excepcion
	public static final long EC_PREFIX = 14500000;
	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX;
	public static final long EXC_ERROR_SQL	= EC_PREFIX+1;
	public static final long EXC_USUARIO_PREMISOS_EXISTENTE	= EC_PREFIX+2;
	
	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.permisos_backoffice.PermisosBackOfficeExceiptionMessages";	

	public PermisosBackOfficeException(long errorCode) {
		this(errorCode, null);
	}
	
	public PermisosBackOfficeException(String message) {
		this(message, null);
	}
	
	public PermisosBackOfficeException(Throwable cause) {
		this(0, cause);
	}
	
	public PermisosBackOfficeException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public PermisosBackOfficeException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public PermisosBackOfficeException(String message, Throwable cause) {
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
