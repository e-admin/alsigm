package ieci.tecdoc.sgm.core.services.sesion;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;

public class SesionUsuarioException extends SigemException {

	public static final long EC_PREFIX = 1510010000;
	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 
	public static final long AUTENTICATION_ERROR_CODE = 1505555555;
	public static final long SECURITY_ERROR_CODE = 1509999999;
	public static final long INVALID_CREDENTIALS_ERROR_CODE = 1501101013;

	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.sesion.ServicioSesionUsuarioExceptionMessages";
	
	
	public SesionUsuarioException(long errorCode) {
		this(errorCode, null);
	}
	
	public SesionUsuarioException(String message) {
		this(message, null);
	}
	
	public SesionUsuarioException(Throwable cause) {
		this(0, cause);
	}
	
	public SesionUsuarioException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public SesionUsuarioException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public SesionUsuarioException(String message, Throwable cause) {
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
