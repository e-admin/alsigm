package common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import common.Constants;

/**
 * Utilidad para formatear fechas.
 */
public class CustomDateFormat {

	// =========================================================================
	// Formateadores de fechas
	// =========================================================================
	public static final String FORMATO_ANIO = "yyyy";
	public static final String FORMATO_MES = "MM";
	public static final String FORMATO_DIA = "dd";
	public static final String FORMATO_ANIO_MES = "yyyy-MM";
	public static final String FORMATO_ANIO_MES_DIA = "yyyy-MM-dd";
	public static final String FORMATO_DIA_MES_ANIO = "dd/MM/yyyy";
	public static final String FORMATO_ANIO_MES_DIA_HORAS_MINUTOS_SEGUNDOS = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMATO_FICHERO_LOG_ELIMINACION = "yyyy-MM-dd HH.mm.ss";

	public static final String FORMATO_HORA = "HH";
	public static final String FORMATO_MINUTOS = "mm";
	public static final String FORMATO_SEGUNDOS = "ss";

	public static final String FORMATO_HORA_MINUTOS_SEGUNDOS = "HH:mm:ss";

	public static final String SEPARADOR_FECHA_GUION = "-";
	public static final String SEPARADOR_FECHA_BARRA = "/";
	public static final String SEPARADOR_HORA = ":";

	/** Formateador de fechas del tipo "yyyy". */
	public static final SimpleDateFormat SDF_YYYY = new SimpleDateFormat(
			FORMATO_ANIO);

	/** Formateador de fechas del tipo "MM". */
	public static final SimpleDateFormat SDF_MM = new SimpleDateFormat(
			FORMATO_MES);

	/** Formateador de fechas del tipo "MM". */
	public static final SimpleDateFormat SDF_DD = new SimpleDateFormat(
			FORMATO_DIA);

	/** Formateador de fechas del tipo "yyyy-MM". */
	public static final SimpleDateFormat SDF_YYYYMM = new SimpleDateFormat(
			FORMATO_ANIO_MES);

	/** Formateador de fechas del tipo "yyyy-MM-dd". */
	public static final SimpleDateFormat SDF_YYYYMMDD = new SimpleDateFormat(
			FORMATO_ANIO_MES_DIA);

	public static final SimpleDateFormat SDF_DDMMYYYY = new SimpleDateFormat(
			FORMATO_DIA_MES_ANIO);

	/** Formateador de fechas del tipo "yyyy-MM-dd". */
	public static final SimpleDateFormat SDF_YYYYMMDD_HHMMSS = new SimpleDateFormat(
			FORMATO_ANIO_MES_DIA_HORAS_MINUTOS_SEGUNDOS);

	public static final SimpleDateFormat SDF_ELIMINACION_YYYYMMDD_HHMMSS = new SimpleDateFormat(
			FORMATO_FICHERO_LOG_ELIMINACION);
	// =========================================================================

	// =========================================================================
	// Formatos de las fechas
	// =========================================================================
	/** Año. */
	public static final String DATE_FORMAT_AAAA = "AAAA";

	/** Mes y año. */
	public static final String DATE_FORMAT_MMAAAA = "MMAAAA";

	/** Año y mes. */
	public static final String DATE_FORMAT_AAAAMM = "AAAAMM";

	/** Día, mes y año. */
	public static final String DATE_FORMAT_DDMMAAAA = "DDMMAAAA";

	/** Año, mes y día. */
	public static final String DATE_FORMAT_AAAAMMDD = "AAAAMMDD";

	/** Día, mes y año. fecha y hora */
	public static final String DATE_FORMAT_DDMMAAAA_HHMMSS = "DDMMAAAAHHMMSS";

	/** Año, mes y día. fecha y hora */
	public static final String DATE_FORMAT_AAAAMMDD_HHMMSS = "AAAAMMDDHHMMSS";

	/** Siglo. */
	public static final String DATE_FORMAT_S = "S";
	// =========================================================================

	// =========================================================================
	// Operadores de fechas
	// =========================================================================
	/** Rango de fechas. */
	public static final String DATE_OPERATOR_RANGE = "[..]";

	/** Exacta. */
	public static final String DATE_OPERATOR_EXACT = "EX";

	/** Que contengan. */
	public static final String DATE_OPERATOR_CONTAINS = "QCN";

	/** Igual. */
	public static final String DATE_OPERATOR_EQ = "=";

	/** Mayor que. */
	public static final String DATE_OPERATOR_GT = ">";

	/** Mayor o igual. */
	public static final String DATE_OPERATOR_GT_OR_EQ = ">=";

	/** Menor que. */
	public static final String DATE_OPERATOR_LT = "<";

	/** Menor o igual. */
	public static final String DATE_OPERATOR_LT_OR_EQ = "<=";

	// =========================================================================

	/**
	 * Obtiene el rango de fechas en función del operador, fecha inicial y fecha
	 * final.
	 *
	 * @param operador
	 *            Operador de fechas.
	 * @param fecha
	 *            Fecha
	 * @param fechaIni
	 *            Fecha inicial del rango.
	 * @param fechaFin
	 *            Fecha final del rango.
	 * @return Rango de fechas.
	 */
	public static CustomDateRange getDateRange(String operador,
			CustomDate fecha, CustomDate fechaIni, CustomDate fechaFin) {
		CustomDateRange dateRange = new CustomDateRange();

		if (DATE_OPERATOR_RANGE.equals(operador)) {
			dateRange.setInitialDate(fechaIni.getMinDate());
			dateRange.setFinalDate(fechaFin.getMaxDate());
		} else if (DATE_OPERATOR_EQ.equals(operador)) {
			dateRange.setInitialDate(fecha.getMinDate());
			dateRange.setFinalDate(fecha.getMaxDate());
		} else if (DATE_OPERATOR_GT.equals(operador)) {
			Date date = fecha.getMaxDate();
			if (date != null) {
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);

				// Sumar un día
				calendar.add(Calendar.DAY_OF_YEAR, 1);

				dateRange.setInitialDate(calendar.getTime());
			}
		} else if (DATE_OPERATOR_GT_OR_EQ.equals(operador)) {
			dateRange.setInitialDate(fecha.getMinDate());
		} else if (DATE_OPERATOR_LT.equals(operador)) {
			Date date = fecha.getMinDate();
			if (date != null) {
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);

				// Restar un día
				calendar.add(Calendar.DAY_OF_YEAR, -1);

				dateRange.setFinalDate(calendar.getTime());
			}
		} else if (DATE_OPERATOR_LT_OR_EQ.equals(operador)) {
			dateRange.setFinalDate(fecha.getMaxDate());
		} else if (DATE_OPERATOR_EXACT.equals(operador)) {
			dateRange.setInitialDate(fecha.getMinDate());

			if (fechaFin.getMaxDate() != null) {
				dateRange.setFinalDate(fechaFin.getMaxDate());
			} else {
				dateRange.setFinalDate(fecha.getMaxDate());
			}
		} else if (DATE_OPERATOR_CONTAINS.equals(operador)) {
			dateRange.setInitialDate(fecha.getMinDate());

			if (fechaFin.getMaxDate() != null) {
				dateRange.setFinalDate(fechaFin.getMaxDate());
			} else {
				dateRange.setFinalDate(fecha.getMaxDate());
			}
		}

		return dateRange;
	}

	/**
	 * Obtiene el rango de fechas en función del operador, fecha inicial y fecha
	 * final, teniendo en cuenta que las fechas seran con horas
	 *
	 * @param operador
	 * @param fecha
	 * @param fechaIni
	 * @param fechaFin
	 * @return
	 */
	public static CustomDateRange getDateTimeRange(String operador,
			CustomDate fecha, CustomDate fechaIni, CustomDate fechaFin) {
		CustomDateRange dateRange = new CustomDateRange();

		if (DATE_OPERATOR_RANGE.equals(operador)) {

			dateRange.setInitialDate(fechaIni.getMinDate());

			dateRange.setFinalDate(DateUtils.getNextDay(fechaFin.getMaxDate()));

		} else if (DATE_OPERATOR_EQ.equals(operador)) {
			dateRange.setInitialDate(fecha.getMinDate());
			dateRange.setFinalDate(DateUtils.getNextDay(fecha.getMaxDate()));
		} else if (DATE_OPERATOR_GT.equals(operador)) {
			Date date = fecha.getMaxDate();
			if (date != null) {
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);

				// Sumar un día
				calendar.add(Calendar.DAY_OF_YEAR, 1);

				dateRange.setInitialDate(calendar.getTime());
			}
		} else if (DATE_OPERATOR_GT_OR_EQ.equals(operador)) {
			dateRange.setInitialDate(fecha.getMinDate());
		} else if (DATE_OPERATOR_LT.equals(operador)) {
			Date date = fecha.getMinDate();
			if (date != null) {
				// Calendar calendar = new GregorianCalendar();
				// calendar.setTime(date);
				//
				// // Restar un día
				// calendar.add(Calendar.DAY_OF_YEAR, -1);

				dateRange.setFinalDate(date);
			}
		} else if (DATE_OPERATOR_LT_OR_EQ.equals(operador)) {
			dateRange.setFinalDate(DateUtils.getNextDay(fecha.getMaxDate()));
		}

		return dateRange;
	}

	public static  String getPattern(String formato, String separador){

		formato = formato.replaceAll(Constants.STRING_SPACE, Constants.STRING_EMPTY);

    	if(DATE_FORMAT_AAAAMMDD.equals(formato)){
    		return new StringBuilder(FORMATO_ANIO)
    				.append(separador)
    				.append(FORMATO_MES)
    				.append(separador)
    				.append(FORMATO_DIA)
    			.toString();

    	} else	if(DATE_FORMAT_AAAAMMDD_HHMMSS.equals(formato)){
        		return new StringBuilder(FORMATO_ANIO)
        				.append(separador)
        				.append(FORMATO_MES)
        				.append(separador)
        				.append(FORMATO_DIA)
        				.append(Constants.STRING_SPACE)
        				.append(FORMATO_HORA_MINUTOS_SEGUNDOS)
        			.toString();
    	} else if(DATE_FORMAT_DDMMAAAA.equals(formato)){
    		return new StringBuilder(FORMATO_DIA)
			.append(separador)
			.append(FORMATO_MES)
			.append(separador)
			.append(FORMATO_ANIO)
			.toString();
    	} else if(DATE_FORMAT_DDMMAAAA_HHMMSS.equals(formato)){
    		return new StringBuilder(FORMATO_DIA)
			.append(separador)
			.append(FORMATO_MES)
			.append(separador)
			.append(FORMATO_ANIO)
			.append(Constants.STRING_SPACE)
        	.append(FORMATO_HORA_MINUTOS_SEGUNDOS)
			.toString();
    	}else if(DATE_FORMAT_MMAAAA.equals(formato)){
    		return new StringBuilder()
			.append(FORMATO_MES)
			.append(separador)
			.append(FORMATO_ANIO).toString();
   		} else if(DATE_FORMAT_AAAAMM.equals(formato)){
   			return new StringBuilder()
			.append(FORMATO_ANIO)
			.append(separador)
			.append(FORMATO_MES).toString();
    	}else if(DATE_FORMAT_AAAA.equals(formato)){
    		return FORMATO_ANIO;
    	}
    	return formato;
    }

	public static SimpleDateFormat getSimpleDateFormat(String formato, String separador){
		String pattern = getPattern(formato, separador);

		if(pattern != null){
			return new SimpleDateFormat(pattern);
		}
		else return null;
	}
}
