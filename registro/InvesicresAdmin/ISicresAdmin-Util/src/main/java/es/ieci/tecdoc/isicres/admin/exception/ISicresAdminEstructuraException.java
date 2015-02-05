package es.ieci.tecdoc.isicres.admin.exception;

import java.util.Locale;


public class ISicresAdminEstructuraException extends ISicresAdminException{

	public static final long EXC_GENERIC_EXCEPCION=13500000;
	public static final long EXC_ERROR_LOAD_DEPT=EXC_GENERIC_EXCEPCION+1;
	public static final long EXC_INEXISTENT_PERMISSION=EXC_GENERIC_EXCEPCION+2;
	public static final long EXC_INEXISTENT_PROFILE=EXC_GENERIC_EXCEPCION+3;
	public static final long EXC_ERROR_LOAD_GROUP_LDAP=EXC_GENERIC_EXCEPCION+4;

	private static String RESOURCE_FILE = "es.ieci.tecdoc.isicres.admin.exception.ISicresAdminExceptionMessages";

	public ISicresAdminEstructuraException(long errorCode) {
		this(errorCode,null);
	}

	public ISicresAdminEstructuraException(long errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}

	public ISicresAdminEstructuraException(String message) {
		this(message, null);
	}

	public ISicresAdminEstructuraException(Throwable cause) {
		this(0, cause);
	}

	public ISicresAdminEstructuraException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}

   public ISicresAdminEstructuraException(String message, Throwable cause) {
	   super(message, cause);
	   errorMessage = message;
   }

   public String getMessagesFile() {
	   return RESOURCE_FILE;
   }

}
