package ieci.tecdoc.sgm.core.services.cripto.validacion;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;

public class CriptoValidacionException extends SigemException {

	// Tipos de excepcion
	public static final long EC_PREFIX = 1211000000;
	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX +1; 
	/**
	 * Error de generación de hash
	 */
	public static final long EXC_HASH_GEN = EC_PREFIX +2;
	/**
	 * Error al validar el certificado
	 */
	public static final long EXC_CERT_VALIDATION = EC_PREFIX + 3;
	/**
	 * Error al validar hash
	 */
	public static final long EXC_HASH_VALIDATION = EC_PREFIX + 4;

	
	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.cripto.validacion.CriptoValidacionExceptionMessages";
	
	
	public CriptoValidacionException(long errorCode) {
		this(errorCode, null);
	}
	
	public CriptoValidacionException(String message) {
		this(message, null);
	}
	
	public CriptoValidacionException(Throwable cause) {
		this(0, cause);
	}
	
	public CriptoValidacionException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public CriptoValidacionException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public CriptoValidacionException(String message, Throwable cause) {
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
