package ieci.tecdoc.sgm.pe.exception;
/*
 * $Id: PagoElectronicoExcepcion.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class PagoElectronicoExcepcion extends Exception {

	public static final long EC_PREFIX = 10010000;
	
	 public static final long EC_PARAMETROS_INCORRECTOS = EC_PREFIX + 1;
	 public static final long EC_PAGO_NO_PERMITIDO = EC_PREFIX + 2;
	 public static final long EC_OBTENIENDO_NUMERO_SECUENCIA = EC_PREFIX + 3;
	 public static final long EC_REALIZANDO_PAGO = EC_PREFIX + 4;
	 public static final long EC_GUARDANDO_DATOS_PAGO = EC_PREFIX + 5;
	 public static final long EC_INVOCANDO_SERVICO_PAGO_EXTERNO = EC_PREFIX + 6;
	 public static final long EC_OPERACION_PAGO = EC_PREFIX + 7;
	 public static final long EC_CONSULTA_PAGOS = EC_PREFIX + 8;
	 public static final long EC_ALTA_LIQUIDACION_BAD_PARAMS = EC_PREFIX + 9;
	 public static final long EC_RECUPERANDO_LIQUIDACION = EC_PREFIX + 10;
	 public static final long EC_ACTUALIZANDO_LIQUIDACION = EC_PREFIX + 11;
	 public static final long EC_CREANDO_LIQUIDACION = EC_PREFIX + 12;
	 public static final long EC_ELIMINANDO_LIQUIDACION = EC_PREFIX + 13;
	 public static final long EC_CREANDO_TASA = EC_PREFIX + 14;
	 public static final long EC_RECUPERANDO_TASA = EC_PREFIX + 15;
	 public static final long EC_ACTUALIZANDO_TASA = EC_PREFIX + 16;
	 public static final long EC_ELIMINANDO_TASA = EC_PREFIX + 17;
	 public static final long EC_PAGO_LIQUIDACION_SIN_LIQUIDACION = EC_PREFIX + 18;
	 public static final long EC_LIQUIDACION_YA_REALIZADA = EC_PREFIX + 19;
	 public static final long EC_PAGO_BAD_PARAMS = EC_PREFIX + 20;
	 public static final long EC_GENERANDO_NREF_AL12 = EC_PREFIX + 21;
	 public static final long EC_GENERANDO_NREF_AL3 = EC_PREFIX + 22;
	 public static final long EC_BAJA_LIQUIDACION_BAD_PARAMS = EC_PREFIX + 23;
	 public static final long EC_MODIFICACION_LIQUIDACION_BAD_PARAMS = EC_PREFIX + 24;
	 public static final long EC_BUSCANDO_LIQUIDACION = EC_PREFIX + 25;
	 
	 public static final long EC_PAGO_AL3_NOT_SUPPORTED = EC_PREFIX + 100;
	 public static final long EC_PAGO_C57_NOT_SUPPORTED = EC_PREFIX + 101;
	 
	   /**
	    * Construye un objeto de la clase.
	    * 
	    * @param errorCode
	    *           Código de error.
	    */

	   public PagoElectronicoExcepcion(long errorCode) {

	      this(errorCode, null);
	   }

	   /**
	    * Construye un objeto de la clase.
	    * 
	    * @param message
	    *           Mensaje de error.
	    */

	   public PagoElectronicoExcepcion(String message) {

	      this(message, null);
	   }

	   /**
	    * Construye un objeto de la clase.
	    * 
	    * @param cause
	    *           Excepción que ha causado ésta.
	    */

	   public PagoElectronicoExcepcion(Throwable cause) {

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

	   public PagoElectronicoExcepcion(long errorCode, Throwable cause) {

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

	   public PagoElectronicoExcepcion(String message, Throwable cause) {

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
	            Logger.getLogger(PagoElectronicoExcepcion.class).warn(mre);
	         }
	         catch (Exception exc) {
	            msg = null;
	            Logger.getLogger(PagoElectronicoExcepcion.class).warn(exc);
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

	   private static String RESOURCE_FILE = "ieci.tecdoc.sgm.pe.resources.error";
}
