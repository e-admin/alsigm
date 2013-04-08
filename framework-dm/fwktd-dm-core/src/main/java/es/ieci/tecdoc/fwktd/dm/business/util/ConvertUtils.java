package es.ieci.tecdoc.fwktd.dm.business.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

public class ConvertUtils {

	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
	public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static final String TYPE_STRING = "string";
	public static final String TYPE_INTEGER = "integer";
	public static final String TYPE_LONG = "long";
	public static final String TYPE_FLOAT = "float";
	public static final String TYPE_DOUBLE = "double";
	public static final String TYPE_DATE = "date";
	public static final String TYPE_TIME = "time";
	public static final String TYPE_DATETIME = "datetime";
	public static final String TYPE_BOOLEAN = "boolean";


	public static String toString(Object valor) throws ParseException {
		return toString(valor, null);
	}

	public static String toString(Object valor, String formato)
			throws ParseException {

		String resultado = null;

		if (valor != null) {
			if (valor instanceof String) {
				resultado = (String) valor;
			} else if (valor instanceof Date) {
				SimpleDateFormat sdf = new SimpleDateFormat(
						StringUtils.isNotBlank(formato)
							? formato : DEFAULT_DATETIME_PATTERN);
				resultado = sdf.format(valor);

			} else if (valor instanceof Number) {
				if (StringUtils.isNotBlank(formato)) {
					NumberFormat formatter = new DecimalFormat(formato);
					resultado = formatter.format(valor);
				} else {
					resultado = String.valueOf(valor);
				}
			} else {
				resultado = String.valueOf(valor);
			}
		}

		return resultado;
	}

	public static Integer toInteger(Object valor, String formato)
			throws ParseException {

		Integer resultado = null;

		if (valor != null) {
			if (valor instanceof Integer) {
				resultado = (Integer) valor;
			} else if (valor instanceof Number) {
				resultado = Integer.valueOf(((Number)valor).intValue());
			} else {
				String strvalor = String.valueOf(valor);
				if (StringUtils.isNotBlank(strvalor)) {
					if (StringUtils.isNotBlank(formato)) {
						NumberFormat formatter = new DecimalFormat(formato);
						Number number = formatter.parse(strvalor);
						resultado = Integer.valueOf(number.intValue());
					} else {
						resultado = Integer.valueOf(strvalor);
					}
				}
			}
		}

		return resultado;
	}

	public static Long toLong(Object valor, String formato)
			throws ParseException {

		Long resultado = null;

		if (valor != null) {
			if (valor instanceof Long) {
				resultado = (Long) valor;
			} else if (valor instanceof Number) {
				resultado = Long.valueOf(((Number) valor).longValue());
			} else {
				String strvalor = String.valueOf(valor);
				if (StringUtils.isNotBlank(strvalor)) {
					if (StringUtils.isNotBlank(formato)) {
						NumberFormat formatter = new DecimalFormat(formato);
						Number number = formatter.parse(strvalor);
						resultado = Long.valueOf(number.longValue());
					} else {
						resultado = Long.valueOf(strvalor);
					}
				}
			}
		}

		return resultado;
	}

	public static Float toFloat(Object valor, String formato)
			throws ParseException {

		Float resultado = null;

		if (valor != null) {
			if (valor instanceof Float) {
				resultado = (Float) valor;
			} else if (valor instanceof Number) {
				resultado = Float.valueOf(((Number) valor).floatValue());
			} else {
				String strvalor = String.valueOf(valor);
				if (StringUtils.isNotBlank(strvalor)) {
					if (StringUtils.isNotBlank(formato)) {
						NumberFormat formatter = new DecimalFormat(formato);
						Number number = formatter.parse(strvalor);
						resultado = Float.valueOf(number.floatValue());
					} else {
						resultado = Float.valueOf(strvalor);
					}
				}
			}
		}

		return resultado;
	}

	public static Double toDouble(Object valor, String formato)
			throws ParseException {

		Double resultado = null;

		if (valor != null) {
			if (valor instanceof Double) {
				resultado = (Double) valor;
			} else if (valor instanceof Number) {
				resultado = Double.valueOf(((Number) valor).doubleValue());
			} else {
				String strvalor = String.valueOf(valor);
				if (StringUtils.isNotBlank(strvalor)) {
					if (StringUtils.isNotBlank(formato)) {
						NumberFormat formatter = new DecimalFormat(formato);
						Number number = formatter.parse(strvalor);
						resultado = Double.valueOf(number.doubleValue());
					} else {
						resultado = Double.valueOf(strvalor);
					}
				}
			}
		}

		return resultado;
	}

	public static Boolean toBoolean(Object valor) throws ParseException {

		Boolean resultado = null;

		if (valor != null) {
			if (valor instanceof Boolean) {
				resultado = (Boolean) valor;
			} else {
				String strvalor = String.valueOf(valor);
				if (StringUtils.isNotBlank(strvalor)) {
					resultado = Boolean.valueOf(strvalor);
				}
			}
		}

		return resultado;
	}

	public static Date toDate(Object valor, String formato, Date defaultDate) {

		Date result = defaultDate;

		try {
			if (valor != null) {
				result = toDate(valor, formato);
			}
		} catch (ParseException e) {
			result = defaultDate;
		}

		return result;
	}

	public static Date toDate(Object valor, String formato) throws ParseException {

		Date resultado = null;

		if (valor != null) {
			if (valor instanceof Date) {
				resultado = (Date) valor;
			} else {

				String[] patterns = null;
				if (StringUtils.isNotBlank(formato)) {
					patterns = new String[] { formato };
				} else {
					patterns = new String[] { DEFAULT_DATE_PATTERN };
				}

				String strvalor = String.valueOf(valor);
				if (StringUtils.isNotBlank(strvalor)) {
					resultado = DateUtils.parseDateStrictly(
							String.valueOf(valor), patterns);
				}
			}
		}

		return resultado;
	}

	public static Object convert(Object valor, String tipo) throws ParseException {
		return convert(valor, tipo, null);
	}

	public static Object convert(Object valor, String tipo, String formato)
			throws ParseException {

		Object resultado = null;

		if (valor != null) {

			if (TYPE_STRING.equalsIgnoreCase(tipo)) {
				resultado = toString(valor, formato);
			} else if (TYPE_INTEGER.equalsIgnoreCase(tipo)) {
				resultado = toInteger(valor, formato);
			} else if (TYPE_LONG.equalsIgnoreCase(tipo)) {
				resultado = toLong(valor, formato);
			} else if (TYPE_FLOAT.equalsIgnoreCase(tipo)) {
				resultado = toFloat(valor, formato);
			} else if (TYPE_DOUBLE.equalsIgnoreCase(tipo)) {
				resultado = toDouble(valor, formato);
			} else if (TYPE_DATE.equalsIgnoreCase(tipo)) {
				resultado = toDate(valor,
						(StringUtils.isNotBlank(formato)
								? formato : DEFAULT_DATETIME_PATTERN));
			} else if (TYPE_TIME.equalsIgnoreCase(tipo)) {
				resultado = toDate(valor,
						(StringUtils.isNotBlank(formato)
								? formato : DEFAULT_TIME_PATTERN));
			} else if (TYPE_DATETIME.equalsIgnoreCase(tipo)) {
				resultado = toDate(valor,
						(StringUtils.isNotBlank(formato)
								? formato : DEFAULT_DATETIME_PATTERN));
			} else if (TYPE_BOOLEAN.equalsIgnoreCase(tipo)) {
				resultado = toBoolean(valor);
			} else {
				resultado = valor;
			}
		}

		return resultado;
	}
}
