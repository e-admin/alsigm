package ieci.tecdoc.sgm.migration.exception;

import ieci.tecdoc.sgm.core.exception.SigemException;

import java.util.Locale;

public class MigrationException extends SigemException {



		private static final long serialVersionUID = 1L;
		// Tipos de excepción
		public static final long EC_PREFIX = 2100010000;
		public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 

		private static String RESOURCE_FILE = "ieci.tecdoc.sgm.migration.exception.MigrationRegistersExceptionMessages";
		
		
		public MigrationException(long errorCode) {
			this(errorCode, null);
		}
		
		public MigrationException(String message) {
			this(message, null);
		}
		
		public MigrationException(Throwable cause) {
			this(MigrationException.EXC_GENERIC_EXCEPCION, cause);
		}
		
		public MigrationException(long errorCode, String message, Throwable cause){
			super(message,cause);
			this.errorCode = errorCode;
			errorMessage = loadMessage(Locale.getDefault());
		}
			   
	   public MigrationException(long errorCode, Throwable cause) {
		   super(cause);
		   this.errorCode = errorCode;
		   errorMessage = loadMessage(Locale.getDefault());
	   }

	   public MigrationException(String message, Throwable cause) {
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
