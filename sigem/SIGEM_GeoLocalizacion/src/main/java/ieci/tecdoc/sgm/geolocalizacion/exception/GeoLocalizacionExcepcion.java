package ieci.tecdoc.sgm.geolocalizacion.exception;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * Excepción específica del catastro.
 * 
 * @author IECISA
 */
public class GeoLocalizacionExcepcion extends Exception {
    
	   /**
	    * Construye un objeto de la clase.
	    * 
	    * @param errorCode
	    *           Código de error.
	    */

	   public GeoLocalizacionExcepcion(long errorCode) {

	      this(errorCode, null);
	   }

	   /**
	    * Construye un objeto de la clase.
	    * 
	    * @param message
	    *           Mensaje de error.
	    */

	   public GeoLocalizacionExcepcion(String message) {

	      this(message, null);
	   }

	   /**
	    * Construye un objeto de la clase.
	    * 
	    * @param cause
	    *           Excepción que ha causado ésta.
	    */

	   public GeoLocalizacionExcepcion(Throwable cause) {

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

	   public GeoLocalizacionExcepcion(long errorCode, Throwable cause) {

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

	   public GeoLocalizacionExcepcion(String message, Throwable cause) {

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

	   protected long errorCode;
	   protected String errorMessage;

	   private static String RESOURCE_FILE = "ieci.tecdoc.sgm.geolocalizacion.resources.error";
  
}
