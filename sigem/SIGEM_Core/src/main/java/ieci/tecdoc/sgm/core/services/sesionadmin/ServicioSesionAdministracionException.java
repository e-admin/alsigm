package ieci.tecdoc.sgm.core.services.sesionadmin;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;

public class ServicioSesionAdministracionException extends SigemException {

	public static final long EC_PREFIX = 51000000;
	public static final long EC_GENERIC_ERROR = EC_PREFIX + 1;
	public static final long EC_NOT_FOUND = EC_PREFIX + 2;
	public static final long EC_INSERT = EC_PREFIX + 3;
	public static final long EC_CLOSE_CONNECTION = EC_PREFIX + 4;
	public static final long EC_DELETE = EC_PREFIX + 5;
	public static final long EC_UPDATE = EC_PREFIX + 6;
	public static final long EC_BAD_PARAMETERS = EC_PREFIX + 7;

	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.sesionadmin.ServicioSesionAdministracionExceptionMessages";
	
	
	public ServicioSesionAdministracionException(long errorCode) {
		this(errorCode, null);
	}
	
	public ServicioSesionAdministracionException(String message) {
		this(message, null);
	}
	
	public ServicioSesionAdministracionException(Throwable cause) {
		this(0, cause);
	}
	
	public ServicioSesionAdministracionException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public ServicioSesionAdministracionException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public ServicioSesionAdministracionException(String message, Throwable cause) {
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
