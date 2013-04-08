package ieci.tecdoc.sgm.core.services.admsesion.backoffice;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;

/**
 * Excepción lanzada cuando ocurre algún error en el servicio de Terceros.
 *
 */
public class ServicioAdministracionSesionesBackOfficeException extends SigemException {

	// Tipos de excepcion
		
	public static final long EC_PREFIX = 1910010000;
	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 

	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.admsession.BackOfficeExceptionMessages";
	
	
	public ServicioAdministracionSesionesBackOfficeException(long errorCode) {
		this(errorCode, null);
	}
	
	public ServicioAdministracionSesionesBackOfficeException(String message) {
		this(message, null);
	}
	
	public ServicioAdministracionSesionesBackOfficeException(Throwable cause) {
		this(0, cause);
	}
	
	public ServicioAdministracionSesionesBackOfficeException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public ServicioAdministracionSesionesBackOfficeException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public ServicioAdministracionSesionesBackOfficeException(String message, Throwable cause) {
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
