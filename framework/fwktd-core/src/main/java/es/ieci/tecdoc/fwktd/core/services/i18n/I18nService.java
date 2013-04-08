package es.ieci.tecdoc.fwktd.core.services.i18n;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import es.ieci.tecdoc.fwktd.core.services.messages.MessagesService;

/**
 * Servicio de internacionalizacion
 */
public class I18nService {

	/**
	 * Nombre de la property que tendrá el formato de fecha por defecto
	 */
	private static final String FORMAT_DATE = "formatDate";

	/**
	 * Nombre de la property que define el formato de fecha corta
	 */
	private static final String FORMAT_DATE_S = "formatDateS";

	/**
	 * Nombre de la property que define el formato de fecha completa (larga)
	 */
	private static final String FORMAT_DATE_F = "formatDateF";

	/**
	 * Nombre de la property que define el formato de fecha (media)
	 */
	private static final String FORMAT_DATE_M = "formatDateM";

	/**
	 * Nombre de la property que tendrá el formato de la hora por defecto
	 */
	private static final String FORMAT_TIME = "formatTime";

	private static I18nService instance = null;

	private I18nService() {
		// Empty
	}

	/**
	 * Obtiene la clase unica del servicio
	 * 
	 * @return el servicio de internacionalizacion
	 */
	public static I18nService getService() {
		if (instance == null) {
			instance = new I18nService();
		}

		return instance;
	}

	/**
	 * Se obtiene el local
	 * 
	 * @return Se retorna un objeto Locale con el local
	 */
	public Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}

	/**
	 * Metodo de utilidad para formatear fechas.
	 * 
	 * @param date
	 *            la fecha a formatear
	 * @param formatKey
	 *            el nombre de la property que indica el formato
	 * @param defaultFormat
	 *            el formato por defecto
	 * @return la cadena con la fecha
	 */
	protected String formatDate(Date date, String formatKey, int defaultFormat) {

		Locale locale = getLocale();

		DateFormat dateFormatter;
		String formatString = getString(formatKey);

		if (StringUtils.isEmpty(formatString)) {
			dateFormatter = DateFormat.getDateInstance(defaultFormat, locale);
		} else {
			dateFormatter = new SimpleDateFormat(formatString, locale);
		}

		return dateFormatter.format(date);
	}

	/**
	 * Formatea un dato con el local específico, formato MEDIUM. EJEMPLO: April
	 * 10, 1998 4:05:54 PM
	 * 
	 * @param date
	 *            fecha para ser formateada
	 * @return un string localizado con el dato
	 * 
	 */
	public String formatDateTimeMedium(Date date) {

		return formatDate(date, FORMAT_DATE_M, DateFormat.MEDIUM);
	}

	/**
	 * Formatea una fecha con el local específico, formato <code>DEFAULT</code>.
	 * En el fichero <code>.properties</code> debe haber una etiqueta
	 * <code>formatDate</code>, cuyo valor debe ser el formato (plantilla) para
	 * la fecha (por ejemplo: <code>dd/mm/YYYYY</code>).
	 * 
	 * Estilo Localidad U.S. Localidad Francia DEFAULT 10-Apr-98 10 avr 98 SHORT
	 * 4/10/98 10/04/98 FULL Friday, April 10, 1998 vendredi, 10 avril 1998
	 * 
	 * @param date
	 *            fecha para ser formateada
	 * @return un string localizado con el dato
	 * 
	 */
	public String formatDateDefault(Date date) {

		return formatDate(date, FORMAT_DATE, DateFormat.DEFAULT);
	}

	/**
	 * Formatea una fecha con el local específico, formato SHORT
	 * 
	 * @param date
	 *            para ser formateado
	 * @return un string localizado con el dato Estilo Localidad U.S. Localidad
	 *         Francia SHORT 4/10/98 10/04/98
	 */
	public String formatDateShort(Date date) {

		return formatDate(date, FORMAT_DATE_S, DateFormat.SHORT);

	}

	/**
	 * Formatea una fecha con el local específico, formato FULL
	 * 
	 * @param date
	 *            para ser formateado
	 * @return un string localizado con el dato Estilo Localidad U.S. Localidad
	 *         Francia FULL Friday, April 10, 1998 vendredi, 10 avril 1998
	 */
	public String formatDateFull(Date date) {

		return formatDate(date, FORMAT_DATE_F, DateFormat.FULL);

	}

	/** ************************ HORAS ********************************* */

	/**
	 * Formatea una hora con el local específico, formato DEFAULT
	 * 
	 * @param date
	 *            para ser formateado
	 * @return un string localizado con el dato Localidad U.S. Localidad Francia
	 *         DEFAULT 3:58:45 PM 15:58:45
	 */
	public String formatTime(Date date) {

		Locale locale = getLocale();

		DateFormat dateFormatter;
		String formatString = getString(FORMAT_TIME);

		if (StringUtils.isEmpty(formatString)) {
			dateFormatter = DateFormat.getTimeInstance(DateFormat.DEFAULT,
					locale);
		} else {
			dateFormatter = new SimpleDateFormat(formatString, locale);
		}

		return dateFormatter.format(date);
	}

	/** ************************* TEXTO ************************** */

	/**
	 * Retorna texto localizado
	 * 
	 * @param clave
	 *            del texto a retornar
	 * @return el mtexto localizado, si no se reconoce la cadena se devuelve esa
	 *         misma cadena
	 */
	protected String getString(String key) {

		try {
			return MessagesService.getInstance().getMessage(key, null, null);
		} catch (NoSuchMessageException ex) {
			return null;
		}
	}

	/**
	 * Retorna el valor numérico convenientemente localizado
	 * 
	 * @param d
	 *            numérico
	 * @return cantidad localizada
	 */
	public String getNumber(double d) {

		Locale locale = getLocale();
		Double amount = new Double(d);

		NumberFormat numberFormatter = NumberFormat.getNumberInstance(locale);
		return numberFormatter.format(amount);
	}

	/** ******************* MONEDA ***************************** */

	/**
	 * Retorna el valor monetario correctamente localizado
	 * 
	 * @param d
	 *            numérico que identifica la cantidad monetaria
	 * @return cantidad localizada
	 */

	public String getCurrency(double d) {

		Locale locale = getLocale();

		Double currency = new Double(d);
		NumberFormat currencyFormatter = NumberFormat
				.getCurrencyInstance(locale);
		return currencyFormatter.format(currency);

	}

	/** ******************* PORCENTAJES ***************************** */

	/**
	 * Si se pretende formatear valores porcentuales -entre 0 y 1-, en este
	 * ejemplo 0.75 => 75%
	 * 
	 * @param d
	 *            numérico entre 0 y 1
	 * @return cantidad porcentual
	 */
	public String getPercent(double d) {

		Locale locale = getLocale();

		Double percent = new Double(d);
		NumberFormat percentFormatter = NumberFormat.getPercentInstance(locale);
		return percentFormatter.format(percent);
	}

	/**
	 * El logger del servicio
	 */
	protected static final Logger logger = LoggerFactory
			.getLogger(I18nService.class);

}
