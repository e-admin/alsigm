package ieci.tecdoc.sgm.core.services.consulta;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;

public class ConsultaExpedientesException extends SigemException {

	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 

	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.consulta.ConsultaExpedientesExceptionMessages";
	
	
	public ConsultaExpedientesException(long errorCode) {
		this(errorCode, null);
	}
	
	public ConsultaExpedientesException(String message) {
		this(message, null);
	}
	
	public ConsultaExpedientesException(Throwable cause) {
		this(0, cause);
	}
	
	public ConsultaExpedientesException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
		   
   public ConsultaExpedientesException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public ConsultaExpedientesException(String message, Throwable cause) {
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
