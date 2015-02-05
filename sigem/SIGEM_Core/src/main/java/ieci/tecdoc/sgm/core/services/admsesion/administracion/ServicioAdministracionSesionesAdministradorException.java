package ieci.tecdoc.sgm.core.services.admsesion.administracion;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;

/**
 * Excepción lanzada cuando ocurre algún error en el servicio de Terceros.
 *
 */
public class ServicioAdministracionSesionesAdministradorException extends SigemException {

	// Tipos de excepcion
		
	public static final long EC_PREFIX = 1910010000;
	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 

	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.admsession.AdministradorExceptionMessages";
	
	
	public ServicioAdministracionSesionesAdministradorException(long errorCode) {
		this(errorCode, null);
	}
	
	public ServicioAdministracionSesionesAdministradorException(String message) {
		this(message, null);
	}
	
	public ServicioAdministracionSesionesAdministradorException(Throwable cause) {
		this(0, cause);
	}
	
	public ServicioAdministracionSesionesAdministradorException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public ServicioAdministracionSesionesAdministradorException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public ServicioAdministracionSesionesAdministradorException(String message, Throwable cause) {
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
