package ieci.tecdoc.sgm.entidades.exception;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class EntidadException extends Exception {

	public static final long EC_PREFIX = 50000000;
	public static final long EC_GENERIC_ERROR = EC_PREFIX + 1;
	public static final long EC_NOT_FOUND = EC_PREFIX + 2;
	public static final long EC_INSERT = EC_PREFIX + 3;
	public static final long EC_CLOSE_CONNECTION = EC_PREFIX + 4;
	public static final long EC_DELETE = EC_PREFIX + 5;
	public static final long EC_UPDATE = EC_PREFIX + 6;
	public static final long EC_BAD_PARAMETERS = EC_PREFIX + 7;
	
	
	
	protected long errorCode;
	protected String errorMessage;
	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.entidades.resources.EntidadExceptionMessages";
	
	   /**
	    * Construye un objeto de la clase.
	    * 
	    * @param errorCode
	    *           Código de error.
	    */

	   public EntidadException(long errorCode) {

	      this(errorCode, null);
	   }

	   /**
	    * Construye un objeto de la clase.
	    * 
	    * @param message
	    *           Mensaje de error.
	    */

	   public EntidadException(String message) {

	      this(message, null);
	   }

	   /**
	    * Construye un objeto de la clase.
	    * 
	    * @param cause
	    *           Excepción que ha causado ésta.
	    */

	   public EntidadException(Throwable cause) {

	      this(0, cause);
	   }

	   /**
	    * Construye un objeto de la clase.
	    * 
	    * @param errorCode
	    *           Código de error.
	    * @param cause
	    *           Excepción que ha causado ésta.
	    */

	   public EntidadException(long errorCode, Throwable cause) {

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

	   public EntidadException(String message, Throwable cause) {

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

	   protected String loadMessage(Locale locale) {

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

	   protected String getExtendedMessage(Locale locale) {

	      String msg = null;
	      ResourceBundle resBundle;

	      if (errorCode != 0) {
	         try {
	            resBundle = ResourceBundle.getBundle(getMessagesFile(), locale);
	            msg = resBundle.getString(new Long(errorCode).toString());
	         }
	         catch (MissingResourceException mre) {
	            msg = null;
	            mre.printStackTrace(System.err);
	         }
	         catch (Exception exc) {
	            msg = null;
	            exc.printStackTrace(System.err);
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
