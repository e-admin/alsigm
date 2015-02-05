package ieci.tecdoc.sgm.core.services.administracion;

/*
 * $Id: AdministracionException.java,v 1.1.2.2 2008/04/25 10:24:29 jconca Exp $
 */
import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;

public class AdministracionException extends SigemException {

		// Tipos de excepcion
			
		public static final long EC_PREFIX = 2000010000;
		public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 
		public static final long EC_GENERIC_ERROR = EC_PREFIX + 1;
		public static final long EC_BAD_PARAMETERS = EC_PREFIX + 2;

		private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.administracion.AdministracionExceptionMessages";
		
		
		public AdministracionException(long errorCode) {
			this(errorCode, null);
		}
		
		public AdministracionException(String message) {
			this(message, null);
		}
		
		public AdministracionException(Throwable cause) {
			this(0, cause);
		}
		
		public AdministracionException(long errorCode, String message, Throwable cause){
			super(message,cause);
			this.errorCode = errorCode;
			errorMessage = loadMessage(Locale.getDefault());
		}
			   
	   public AdministracionException(long errorCode, Throwable cause) {
		   super(cause);
		   this.errorCode = errorCode;
		   errorMessage = loadMessage(Locale.getDefault());
	   }

	   public AdministracionException(String message, Throwable cause) {
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

