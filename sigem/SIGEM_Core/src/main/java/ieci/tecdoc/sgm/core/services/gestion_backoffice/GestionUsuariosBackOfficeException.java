package ieci.tecdoc.sgm.core.services.gestion_backoffice;

import ieci.tecdoc.sgm.core.exception.SigemException;

import java.util.Locale;


/**
 * Excepci�n espec�fica del cat�logo de tr�mites.
 * 
 * @author IECISA
 */
public class GestionUsuariosBackOfficeException extends SigemException{
    
	// Tipos de excepcion
	public static final long EC_PREFIX = 1011000000;
	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 
	public static final long EXC_CERTIFICADO_INVALIDO_EXCEPTION = EC_PREFIX+1;
	

	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.gestion_backoffice.GestionUsuariosBackOfficeExceptionMessages";
	
	
	public GestionUsuariosBackOfficeException(long errorCode) {
		this(errorCode, null);
	}
	
	public GestionUsuariosBackOfficeException(String message) {
		this(message, null);
	}
	
	public GestionUsuariosBackOfficeException(Throwable cause) {
		this(0, cause);
	}
	
	public GestionUsuariosBackOfficeException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public GestionUsuariosBackOfficeException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public GestionUsuariosBackOfficeException(String message, Throwable cause) {
	   super(message, cause);
	   errorMessage = message;
   }
   
   /**
    * Devuelve el nombre del archivo de recursos que contiene los mensajes para
    * esta excepci�n.
    * 
    * @return El nombre del archivo de recursos mencionado.
    */

   public String getMessagesFile() {

      return RESOURCE_FILE;
   }


}
