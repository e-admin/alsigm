package ieci.tecdoc.sgm.core.services.estructura_organizativa;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;

public class EstructuraOrganizativaException extends SigemException{

	public static final long EXC_GENERIC_EXCEPCION=13500000;
	public static final long EXC_ERROR_LOAD_DEPT=EXC_GENERIC_EXCEPCION+1;
	public static final long EXC_INEXISTENT_PERMISSION=EXC_GENERIC_EXCEPCION+2;
	public static final long EXC_INEXISTENT_PROFILE=EXC_GENERIC_EXCEPCION+3;	
	
	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.estructura_organizativa.EstructuraOrganizativaExceptionMessages";

	public EstructuraOrganizativaException(long errorCode) {
		this(errorCode,null);
	}
	
	public EstructuraOrganizativaException(long errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
	
	public EstructuraOrganizativaException(String message) {
		this(message, null);
	}
	
	public EstructuraOrganizativaException(Throwable cause) {
		this(0, cause);
	}
	
	public EstructuraOrganizativaException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}

   public EstructuraOrganizativaException(String message, Throwable cause) {
	   super(message, cause);
	   errorMessage = message;
   }	

   public String getMessagesFile() {
	   return RESOURCE_FILE;
   }   
	
}
