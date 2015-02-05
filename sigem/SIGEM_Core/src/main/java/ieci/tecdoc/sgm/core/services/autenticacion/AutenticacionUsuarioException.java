package ieci.tecdoc.sgm.core.services.autenticacion;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;


/**
 * Excepción específica del catálogo de trámites.
 * 
 * @author IECISA
 */
public class AutenticacionUsuarioException extends SigemException{
    
	// Tipos de excepcion
	public static final long EC_PREFIX = 1011000000;
	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 

	/**
	* Error en la base de datos de usuarios.
	*/
	public static final long EC_ERR_DB = EC_PREFIX + 1;
	   
	/**
	* El usuario no es válido.
	*/
	public static final long EC_BAD_USER_OR_PASS = EC_PREFIX + 2;
	
	/**
	* Error al crear el usuario.
	*/
	public static final long EC_ADD_USER = EC_PREFIX + 3;
	
	/**
	* Error al eliminar el usuario.
	*/
	public static final long EC_DELETE_USER = EC_PREFIX + 4;
	
	/**
	* Error al recuperar la información del usuario.
	*/
	public static final long EC_GET_USER = EC_PREFIX + 5;
	
	/**
	* Error al actualizar la información del usuario.
	*/
	public static final long EC_UPDATE_USER = EC_PREFIX + 6;
	
	/**
	* Error obteniendo lista de usuarios.
	*/
	public static final long EC_FIND_USERS = EC_PREFIX + 7;
	

	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.autenticacion.AutenticacionUsuariosExceptionMessages";
	
	
	public AutenticacionUsuarioException(long errorCode) {
		this(errorCode, null);
	}
	
	public AutenticacionUsuarioException(String message) {
		this(message, null);
	}
	
	public AutenticacionUsuarioException(Throwable cause) {
		this(0, cause);
	}
	
	public AutenticacionUsuarioException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public AutenticacionUsuarioException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public AutenticacionUsuarioException(String message, Throwable cause) {
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
