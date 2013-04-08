package es.ieci.tecdoc.isicres.admin.exception;

import java.util.Locale;
import java.util.ResourceBundle;

public class ISicresAdminException extends Exception {

	private static String RESOURCE_FILE = "es.ieci.tecdoc.isicres.admin.exception.ISicresAdminExceptionMessages";

	// Tipos de excepcion
	public static final long EC_PREFIX = 10000000;
	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX + 1;


	protected long errorCode;
	protected String errorMessage;


	   /**
	    * Construye un objeto de la clase.
	    *
	    * @param errorCode
	    *           Código de error.
	    */

	   public ISicresAdminException(long errorCode) {

	      this(errorCode, null);
	   }

	   /**
	    * Construye un objeto de la clase.
	    *
	    * @param message
	    *           Mensaje de error.
	    */

	   public ISicresAdminException(String message) {

	      this(message, null);
	   }

	   /**
	    * Construye un objeto de la clase.
	    *
	    * @param cause
	    *           Excepción que ha causado ésta.
	    */

	   public ISicresAdminException(Throwable cause) {

	      this(0, cause);
	   }

	   public ISicresAdminException(long errorCode, String message, Throwable cause){
	     super(message,cause);
	     this.errorCode = errorCode;
	     errorMessage = loadMessage(Locale.getDefault());
	   }

	   /**
	    * Construye un objeto de la clase.
	    *
	    * @param errorCode
	    *           Código de error.
	    * @param cause
	    *           Excepción que ha causado ésta.
	    */

	   public ISicresAdminException(long errorCode, Throwable cause) {

	      super(cause);
	      this.errorCode = errorCode;
	      errorMessage = loadMessage(Locale.getDefault());
	   }

	   /**
	    * Construye un objeto de la clase.
	    *
	    * @param message
	    *           Mensaje de error.
	    * @param cause
	    *           Excepción que ha causado ésta.
	    */

	   public ISicresAdminException(String message, Throwable cause) {

	      super(message, cause);
	      errorMessage = message;
	   }

	   /**
	    * Devuelve el código de error. <br>
	    * El valor del código de error es 0 si éste no se ha establecido
	    *
	    * @return El código de error mencionado.
	    */

	   public long getErrorCode() {

	      return errorCode;
	   }

	   /**
	    * Devuelve el mensaje de error.
	    *
	    * @return El mensaje de error mencionado.
	    */

	   public String getMessage() {

	      return errorMessage;
	   }

	   /**
	    * Carga un mensaje de error del archivo de recursos en el idioma
	    * especificiado.
	    *
	    * @param locale
	    *           Código de localización con el idioma especificado.
	    * @return El mensaje de error mencionado.
	    */

	   public String loadMessage(Locale locale) {

	      if (getCause() == null)
	         return getExtendedMessage(locale);
	      else {
	         StringBuffer sbf = new StringBuffer();
	         sbf.append(getExtendedMessage(locale));
	         sbf.append(" {\n ");
	         sbf.append(getCause().getMessage());
	         sbf.append("\n}");
	         return sbf.toString();
	      }
	   }

	   /**
	    * Carga un mensaje de error del archivo de recursos en el idioma
	    * especificiado.
	    *
	    * @param locale
	    *           Código de localización con el idioma especificado.
	    * @return El mensaje de error mencionado.
	    */

	   public String getExtendedMessage(Locale locale) {

	      String msg = null;
	      ResourceBundle resBundle;

	      if (errorCode != 0) {
	         try {
	            resBundle = ResourceBundle.getBundle(getMessagesFile(), locale);
	            msg = resBundle.getString(new Long(errorCode).toString());
	         }
	         catch (Exception exc) {
	        	 try{
	        		 resBundle = ResourceBundle.getBundle(getMessagesFile());
	        		 msg = resBundle.getString(new Long(errorCode).toString());
	        	 }catch(Exception exc2){
	 	            msg = null;
		            exc2.printStackTrace(System.err);
	        	 }
	         }
	      }
	      return msg;
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
