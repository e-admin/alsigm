package ieci.tecdoc.sgm.core.services.gestion_administracion;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;


/**
 * Excepción específica del catálogo de trámites.
 * 
 * @author IECISA
 */
public class GestionUsuariosAdministracionException extends SigemException{
    
	// Tipos de excepcion
	public static final long EC_PREFIX = 1011000000;
	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 
	

	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.gestion_administracion.GestionUsuariosBackOfficeExceptionMessages";
	
	
	public GestionUsuariosAdministracionException(long errorCode) {
		this(errorCode, null);
	}
	
	public GestionUsuariosAdministracionException(String message) {
		this(message, null);
	}
	
	public GestionUsuariosAdministracionException(Throwable cause) {
		this(0, cause);
	}
	
	public GestionUsuariosAdministracionException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public GestionUsuariosAdministracionException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public GestionUsuariosAdministracionException(String message, Throwable cause) {
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
