/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.keys;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 *
 */
public class ConstantKeys {

	public static String ENTIDAD_DEFAULT = "";
	public static String LOCALE_LENGUAGE_DEFAULT = "es";
	public static String LOCALE_COUNTRY_DEFAULT = "ES";

	public static String FECHA_FORMAT_SHORT = "dd-MM-yyyy";
	public static String FECHA_FORMAT_LONG = "dd-MM-yyyy HH:mm:ss";

	// FLD KEY de un registro
	public static final String FLD_KEY = "Fld";
	public static final String FLD_KEY_NUMERO_REGISTRO = FLD_KEY + "1";
	public static final String FLD_KEY_FECHA_REGISTRO = FLD_KEY + "2";
	public static final String FLD_KEY_FECHA_TRABAJO = FLD_KEY + "4";
	public static final String FLD_KEY_ESTADO = FLD_KEY + "6";

	public static final String FLD_KEY_EREG_NUMERO_REGISTRO_ORIGINAL = FLD_KEY
			+ "10";
	public static final String FLD_KEY_EREG_TIPO_REGISTRO_ORIGINAL = FLD_KEY
			+ "11";
	public static final String FLD_KEY_EREG_FECHA_REGISTRO_ORIGINAL = FLD_KEY
			+ "12";

	public static final String FLD_KEY_EREG_TIPO_TRANSPORTE = FLD_KEY + "14";
	public static final String FLD_KEY_EREG_NUMERO_TRANSPORTE = FLD_KEY + "15";
	public static final String FLD_KEY_EREG_RESUMEN = FLD_KEY + "17";
	public static final String FLD_KEY_EREG_REF_EXPEDIENTE = FLD_KEY + "19";


	public static final String FLD_KEY_SREG_TIPO_TRANSPORTE = FLD_KEY + "10";
	public static final String FLD_KEY_SREG_NUMERO_TRANSPORTE = FLD_KEY + "11";
	public static final String FLD_KEY_SREG_RESUMEN = FLD_KEY + "13";

	public static final String FLD_KEY_REG_IS_INTERCAMBIO_REGISTRAL = FLD_KEY
	+ "503";
	// public static final String FLD_KEY_ = FLD_KEY + ;

	// Keys para los campos de un registro
	public static Integer ID_FLD_NUMERO_REGISTRO = new Integer(1);
	public static Integer ID_FLD_FECHA_REGISTRO = new Integer(2);
	public static Integer ID_FLD_USUARIO = new Integer(3);
	public static Integer ID_FLD_FECHA_TRABAJO = new Integer(4);
	public static Integer ID_FLD_OFICINA_REGISTRO = new Integer(5);
	public static Integer ID_FLD_ESTADO = new Integer(6);
	public static Integer ID_FLD_ORIGEN = new Integer(7);
	public static Integer ID_FLD_DESTINO = new Integer(8);

	// Keys para los campos de un registro de entrada
	public static Integer ID_FLD_EREG_NUMERO_REGISTRO_ORIGINAL = new Integer(10);
	public static Integer ID_FLD_EREG_TIPO_REGISTRO_ORIGINAL = new Integer(11);
	public static Integer ID_FLD_EREG_FECHA_REGISTRO_ORIGINAL = new Integer(12);
	public static Integer ID_FLD_EREG_ENTIDAD_REGISTRAL_ORIGINAL = new Integer(
			13);
	public static Integer ID_FLD_EREG_TIPO_TRANSPORTE = new Integer(14);
	public static Integer ID_FLD_EREG_NUMERO_TRANSPORTE = new Integer(15);
	public static Integer ID_FLD_EREG_TIPO_ASUNTO = new Integer(16);
	public static Integer ID_FLD_EREG_RESUMEN = new Integer(17);
	public static Integer ID_FLD_EREG_COMENTARIOS = new Integer(18);

	// Keys para los campos de un registro de salida
	public static Integer ID_FLD_SREG_TIPO_TRANSPORTE = new Integer(10);
	public static Integer ID_FLD_SREG_NUMERO_TRANSPORTE = new Integer(11);
	public static Integer ID_FLD_SREG_TIPO_ASUNTO = new Integer(12);
	public static Integer ID_FLD_SREG_RESUMEN = new Integer(13);
	public static Integer ID_FLD_SREG_COMENTARIOS = new Integer(14);

	// Values de algunos keys de los campos de Isicres
	public static String EREG_VALUE_FLD_11_ENTRADA = "ENTRADA";
	public static String EREG_VALUE_FLD_11_SALIDA = "SALIDA";

	public static String EREG_VALUE_FLD_11_ENTRADA_ID = "1";
	public static String EREG_VALUE_FLD_11_SALIDA_ID = "2";

	public static String REG_IS_INTERCAMBIO_REGISTRAL = "1";

}
