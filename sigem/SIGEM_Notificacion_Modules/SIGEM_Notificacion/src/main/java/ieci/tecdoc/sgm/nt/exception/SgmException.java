/**
 * @author Raúl Arranz Arnaiz
 * 
 * Fecha de Creación: 16-jun-2004
 */

package ieci.tecdoc.sgm.nt.exception;

import java.util.*;

import org.apache.log4j.Logger;

/**
 * Excepción lanzada cuando se produce un error reseñable. <br>
 * <br>
 * Además de la información contenida en todos los objetos Exception (message,
 * cause y stack trace), cada instancia de esta clase puede contener también un
 * código de error.
 */

public class SgmException extends Exception {

   /**
    * Construye un objeto de la clase.
    * 
    * @param errorCode
    *           Código de error.
    */

   public SgmException(long errorCode) {

      this(errorCode, null);
   }

   /**
    * Construye un objeto de la clase.
    * 
    * @param message
    *           Mensaje de error.
    */

   public SgmException(String message) {

      this(message, null);
   }

   /**
    * Construye un objeto de la clase.
    * 
    * @param cause
    *           Excepción que ha causado ésta.
    */

   public SgmException(Throwable cause) {

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

   public SgmException(long errorCode, Throwable cause) {

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

   public SgmException(String message, Throwable cause) {

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
            Logger.getLogger(SgmException.class).warn(mre);
         }
         catch (Exception exc) {
            msg = null;
            Logger.getLogger(SgmException.class).warn(exc);
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

   private static String RESOURCE_FILE = "ieci.tecdoc.sgm.nt.resources.error";

}