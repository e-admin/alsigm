package common.tags.calendar.config;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import common.Constants;

/**
 * Clase para representar un objeto de configuración del calendario
 */
public class CalendarTagConfig {

	private String id = null;
	private Integer year = null;
	private String function = null;
	private String parameters = null;
	private Integer numMonths = null;
	private Integer month = null;

	private String[] monthNames = null;
	private String[] dayNames = null;

	private Map months = new HashMap();

	/**
	 * Constructor de la configuración del calendario
	 */
	public CalendarTagConfig() {
		super();
	}

	/**
	 * Devuelve el año
	 * 
	 * @return año.
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * Establece el año
	 * 
	 * @param year
	 *            año.
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * Devuelve el map de meses
	 * 
	 * @return map de meses.
	 */
	public Map getMonths() {
		return months;
	}

	/**
	 * Establece el map de meses
	 * 
	 * @param dates
	 *            map de fechas.
	 */
	public void setMonths(Map months) {
		this.months = months;
	}

	/**
	 * Devuelve la función a ejecutar
	 * 
	 * @return función a ejecutar.
	 */
	public String getFunction() {
		return function;
	}

	/**
	 * Establece la función a ejecutar
	 * 
	 * @param function
	 *            función a ejecutar.
	 */
	public void setFunction(String function) {
		this.function = function;
	}

	/**
	 * Devuelve el id del calendario
	 * 
	 * @return id del calendario.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el id del calendario
	 * 
	 * @param id
	 *            id del calendario.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Devuelve el mes del calendario
	 * 
	 * @return mes del calendario.
	 */
	public Integer getMonth() {
		return month;
	}

	/**
	 * Establece el mes del calendario
	 * 
	 * @param month
	 *            mes del calendario.
	 */
	public void setMonth(Integer month) {
		this.month = month;
	}

	/**
	 * Devuelve el número de meses por fila del calendario
	 * 
	 * @return número de meses por fila del calendario.
	 */
	public Integer getNumMonths() {
		return numMonths;
	}

	/**
	 * Establece el número de meses por fila del calendario
	 * 
	 * @param numMonths
	 *            número de meses por fila del calendario.
	 */
	public void setNumMonths(Integer numMonths) {
		this.numMonths = numMonths;
	}

	/**
	 * Devuelve los parámetros de la función
	 * 
	 * @return parámetros de la función.
	 */
	public String getParameters() {
		return parameters;
	}

	/**
	 * Establece los parámetros de la función
	 * 
	 * @param parameters
	 *            parámetros de la función.
	 */
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	/**
	 * Devuelve los textos de los días
	 * 
	 * @return textos de los días.
	 */
	public String[] getDayNames() {
		return dayNames;
	}

	/**
	 * Establece los textos de los días
	 * 
	 * @param dayNames
	 *            textos de los días.
	 */
	public void setDayNames(String[] dayNames) {
		this.dayNames = dayNames;
	}

	/**
	 * Devuelve los textos de los meses
	 * 
	 * @return textos de los meses.
	 */
	public String[] getMonthNames() {
		return monthNames;
	}

	/**
	 * Establece los textos de los meses
	 * 
	 * @param monthNames
	 *            Textos de los meses.
	 */
	public void setMonthNames(String[] monthNames) {
		this.monthNames = monthNames;
	}

	/**
	 * Método para comprobar si la configuración del calendario es correcta
	 * 
	 * @return Indicador de configuración correcta
	 */
	public boolean isOk() throws Exception {
		return (this.getId() != null)
				&& (this.getYear() != null)
				&& (this.getYear().intValue() > 0)
				&& (((this.getNumMonths() != null)
						&& (this.getNumMonths().intValue() >= 1) && (this
						.getNumMonths().intValue() <= 12)) || ((this.getMonth() != null)
						&& (this.getMonth().intValue() >= 1) && (this
						.getMonth().intValue() <= 12)))
				&& (this.getMonthNames() != null)
				&& (this.getMonthNames().length == 12)
				&& (this.getDayNames() != null)
				&& (this.getDayNames().length == 7)
				&& (this.getMonths() != null);
	}

	/**
	 * Permite obtener el código para insertar una fecha en un Map
	 * 
	 * @param fecha
	 *            Fecha de la que se quiere obtener el código
	 * @return código de la fecha
	 * @throws Exception
	 */
	public static String getMapDateCode(Date fecha) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(fecha);

		String codigo = calendar.get(Calendar.DATE)
				+ Constants.SEPARATOR_CALENDAR_CODE
				+ (calendar.get(Calendar.MONTH) + 1)
				+ Constants.SEPARATOR_CALENDAR_CODE
				+ calendar.get(Calendar.YEAR);
		return codigo;
	}

	/**
	 * Permite obtener el código para insertar una fecha en un Map
	 * 
	 * @param dia
	 *            Día de la fecha
	 * @param mes
	 *            Mes de la fecha
	 * @param anio
	 *            Año de la fecha
	 * @return código de la fecha
	 * @throws Exception
	 */
	public static String getMapDateCode(int dia, int mes, int anio) {
		String codigo = dia + Constants.SEPARATOR_CALENDAR_CODE + (mes + 1)
				+ Constants.SEPARATOR_CALENDAR_CODE + anio;
		return codigo;
	}
}
