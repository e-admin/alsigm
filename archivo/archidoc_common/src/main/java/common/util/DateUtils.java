package common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.GenericValidator;

import solicitudes.prestamos.PrestamosConstants;

import common.Constants;

public class DateUtils {

	/**
	 * Días de cada mes
	 */
	private static final int [] monthDays = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};

    /** Formateador para formatear y parsear fechas */
    public static final DateFormat DATE_FORMATTER = new SimpleDateFormat(Constants.FORMATO_FECHA);
    public static final DateFormat TIME_FORMATTER = new SimpleDateFormat(Constants.FORMATO_HORA);

    /** Formateador para formatear y parsear timestamps */
    public static final DateFormat DETAILED_DATE_FORMATTER =
        new SimpleDateFormat(Constants.FORMATO_FECHA_DETALLADA);

    /** Formateador para formatear y parsear fechas extendidas */
    public static final DateFormat EXTENDED_DATE_FORMATTER =
        new SimpleDateFormat(Constants.FORMATO_FECHA_EXTENDIDA);




    /** Milisegundos en 24 horas. */
    public static final long TIME_24H = 24 * 60 * 60 * 1000;


//    /** Obtiene la fecha actual del sistema */
//    public static Date getCurrentDateTime() {
//        return new GregorianCalendar().getTime();
//    }

    /** Obtiene la fecha actual del sistema */
    public final static Date getFechaActual() {
        return new GregorianCalendar().getTime();
    }

    /** Obtiene la fecha actual del sistema sin Hora */
    public final static Date getFechaActualSinHora() {
        Calendar cal =  GregorianCalendar.getInstance();
    	return getCalendar(cal).getTime();
    }

//    public final static String getFechaActualString() {
//        return formatDate(getFechaActual());
//    }

    public final static int getAnoActual() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public final static String getAnoActualStr() {
        return Integer.toString(getAnoActual());
    }

    public static final String getAnio(Date fecha){
    	if(fecha != null){
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(fecha);
		    return String.valueOf(cal.get(Calendar.YEAR));
    	}

    	return null;
    }

    public static final String getMes(Date fecha){
    	if(fecha != null){
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(fecha);
			return String.valueOf(cal.get(Calendar.MONTH)+1);
    	}

    	return null;
    }

    public static final String getDia(Date fecha){
    	if(fecha != null){
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(fecha);
		    return String.valueOf(cal.get(Calendar.DATE));
    	}

    	return null;
    }


    public static final String getHoras(Date fecha){
		if(fecha != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(fecha);
			return String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		}

		return null;
    }

    public static final String getMinutos(Date fecha){
		if(fecha != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(fecha);
			return String.valueOf(cal.get(Calendar.MINUTE));
		}

		return null;
    }

    public static final String getSegundos(Date fecha){
		if(fecha != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(fecha);
			return String.valueOf(cal.get(Calendar.SECOND));
		}

		return null;
    }

    public final static int getDiaActual() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    public final static int getMesActual() {
        return Calendar.getInstance().get(Calendar.MONTH + 1);
    }

    public static boolean esHoy(Date date) {
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(date);
        Calendar hoy = Calendar.getInstance();
        return calendarDate.get(Calendar.YEAR) == hoy.get(Calendar.YEAR)
                && calendarDate.get(Calendar.DATE) == hoy.get(Calendar.DATE)
                && calendarDate.get(Calendar.MONTH) == hoy.get(Calendar.MONTH);
    }

    public final static Date getDate(String dateString) {
        Date date = null;
        try {
            if (dateString != null)
                date = DATE_FORMATTER.parse(dateString);
        } catch (ParseException e) {}
        return date;
    }

    public static Date getTimestamp(String dateString) {
        Date date = null;
        try {
            if (dateString != null)
                date = DETAILED_DATE_FORMATTER.parse(dateString);
        } catch (ParseException e) {}
        return date;
    }

    public static String formatTime(Date date) {
        String dateAsString = null;
        if (date != null)
            try {
                dateAsString = TIME_FORMATTER.format(date);
            } catch (Exception e) {}
        return dateAsString;
    }

    public static String formatDate(Date date) {
        String dateAsString = null;
        if (date != null)
            try {
                dateAsString = DATE_FORMATTER.format(date);
            } catch (Exception e) {}
        return dateAsString;
    }

    public static String formatTimestamp(Date date) {
        String dateAsString = null;
        if (date != null)
            try {
                dateAsString = DETAILED_DATE_FORMATTER.format(date);
            } catch (Exception e) {}
        return dateAsString;
    }

    public final static boolean isDate(String date) {
    	/*if(GenericValidator.isBlankOrNull(date)){
			return true;
		}*/

    	//Comprobar si cumple el formato de Fecha DD/MM/YYYY
    	Pattern mask = Pattern.compile(Constants.REGEXP_FORMATO_FECHA);
		Matcher matcher = mask.matcher(date);

		if (matcher.matches()) {
	    	if (GenericValidator.isDate(date, common.Constants.FORMATO_FECHA, false))
	            return true;
	        return false;
		}
		return false;
    }

    public final static Date getNextDay(Date date){
		if (date!=null){
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(date);
		    cal.add(Calendar.DAY_OF_YEAR,1);
		    return cal.getTime();
		}
		return null;
    }

    public static boolean isInTimeBeforeNow(Date date, long time) {
        boolean beforeNow = false;
        if (date != null)
        {
            Date now = new Date();
            beforeNow = date.before(now)
            	&& (now.getTime() - date.getTime()) <= time;
        }
        return beforeNow;
    }


	public static final int FECHA_MAYOR = 1;
	public static final int FECHA_MENOR = -1;
	public static final int FECHA_IGUAL = 0;

	/**
	 * Compara dos fechas de tipo Calendar
	 * @param fecha1 Fecha Base de la Comparación
	 * @param fecha2 Fecha de Comparación
	 * @return Devuelve -1 si fecha1 < fecha2, 0 si fecha1 = fecha2, 1 si fecha1 > fecha2
	 */
	private static int compararFechas(Date fechaBase, Date fechaComparacion)
	{
		GregorianCalendar fecha1 = new GregorianCalendar();
		fecha1.setTime(fechaBase);
		GregorianCalendar fecha2 = new GregorianCalendar();
		fecha2.setTime(fechaComparacion);

		int anio1 = fecha1.get(GregorianCalendar.YEAR);
		int anio2 = fecha2.get(GregorianCalendar.YEAR);
		int mes1 = fecha1.get(GregorianCalendar.MONTH);
		int mes2 = fecha2.get(GregorianCalendar.MONTH);
		int dia1 = fecha1.get(GregorianCalendar.DATE);
		int dia2 = fecha2.get(GregorianCalendar.DATE);

		if (anio1 > anio2)
			return FECHA_MAYOR;
		else if (anio1 < anio2)
			return FECHA_MENOR;

		if (mes1 > mes2)
			return FECHA_MAYOR;
		else if (mes1 < mes2)
			return FECHA_MENOR;

		if (dia1 > dia2)
			return FECHA_MAYOR;
		else if (dia1 < dia2)
			return FECHA_MENOR;

		return FECHA_IGUAL;
	}

	/**
	 * Compara dos fechas de tipo Calendar
	 * @param fecha1 Fecha Base de la Comparación
	 * @param fecha2 Fecha de Comparación
	 * @return Devuelve -1 si fecha1 < fecha2, 0 si fecha1 = fecha2, 1 si fecha1 > fecha2
	 */
	private static int compararFechas(Calendar fechaBase, Calendar fechaComparacion)
	{
		return compararFechas(fechaBase.getTime(), fechaComparacion.getTime());
	}

	/**
	 * Comprueba si la <b>fecha1</b> es MAYOR que la <b>fecha2</b>
	 * @param fecha1 Fecha Base de Comparación
	 * @param fecha2 Fecha a Comparar
	 * @return <b>true</b> Si la fecha1 es MAYOR que la <b>fecha2</b>
	 * @author Lucas Alvarez
	 */
	public static boolean isFechaMayor(Date fecha1, Date fecha2)
	{
		if(compararFechas(fecha1, fecha2) == FECHA_MAYOR ){
			return true;
		}
		return false;
	}

	/**
	 * Comprueba si la <b>fecha1</b> es MENOR que la <b>fecha2</b>
	 * @param fecha1 Fecha Base de Comparación
	 * @param fecha2 Fecha a Comparar
	 * @return <b>true</b> Si la fecha1 es MENOR que la <b>fecha2</b>
	 * @author Lucas Alvarez
	 */
	public static boolean isFechaMenor(Date fecha1, Date fecha2){
		if(compararFechas(fecha1, fecha2) == FECHA_MENOR ){
			return true;
		}
		return false;
	}

	/**
	 * Comprueba si la <b>fecha1</b> es IGUAL que la <b>fecha2</b>
	 * @param fecha1 Fecha Base de Comparación
	 * @param fecha2 Fecha a Comparar
	 * @return <b>true</b> Si la fecha1 es IGUAL que la <b>fecha2</b>
	 * @author Lucas Alvarez
	 */
	public static boolean isFechaIgual(Date fecha1, Date fecha2){
		if(compararFechas(fecha1, fecha2) == FECHA_IGUAL ){
			return true;
		}
		return false;
	}

	/**
	 * Comprueba si la <b>fecha1</b> es MAYOR O IGUAL que la <b>fecha2</b>
	 * @param fecha1 Fecha Base de Comparación
	 * @param fecha2 Fecha a Comparar
	 * @return <b>true</b> Si la fecha1 es MAYOR O IGUAL que la <b>fecha2</b>
	 * @author Lucas Alvarez
	 */
	public static boolean isFechaMayorOIgual(Date fecha1, Date fecha2){
		if(compararFechas(fecha1, fecha2) >= FECHA_IGUAL ){
			return true;
		}
		return false;
	}

	/**
	 * Comprueba si la <b>fecha1</b> es MENOR O IGUAL que la <b>fecha2</b>
	 * @param fecha1 Fecha Base de Comparación
	 * @param fecha2 Fecha a Comparar
	 * @return <b>true</b> Si la fecha1 es MENOR O IGUAL que la <b>fecha2</b>
	 * @author Lucas Alvarez
	 */
	public static boolean isFechaMenorOIgual(Date fecha1, Date fecha2){
		if(compararFechas(fecha1, fecha2) <= FECHA_IGUAL ){
			return true;
		}
		return false;
	}

	/**
	 * Comprueba si la <b>fecha1</b> es MENOR O IGUAL que la <b>fecha2</b>
	 * @param fecha1 Fecha Base de Comparación
	 * @param fecha2 Fecha a Comparar
	 * @return <b>true</b> Si la fecha1 es MENOR O IGUAL que la <b>fecha2</b>
	 * @author Lucas Alvarez
	 */
	public static boolean isFechaMenorOIgual(Calendar fecha1, Calendar fecha2){
		if(compararFechas(fecha1, fecha2) <= FECHA_IGUAL ){
			return true;
		}
		return false;
	}

	/**
	 * Crea una copia de un objeto Calendar, copia los campos dia, mes y anio del calendar
	 * pasado como parámetro, poniendo el resto a 0
	 *
	 * @param obj Calendario a copiar
	 * @return Copia del calendario
	 */
	public static Calendar getCalendar(Calendar cal)
	{
		Calendar newCal = GregorianCalendar.getInstance();
		newCal.set(cal.get(GregorianCalendar.YEAR), cal.get(GregorianCalendar.MONTH), cal.get(GregorianCalendar.DATE), 0, 0, 0 );
		newCal.set(GregorianCalendar.MILLISECOND,0);
		return newCal;
	}

	/**
	 * Permite obtener los días de un mes en concreto
	 * @param month Mes del que se quieren obtener los días (rango 0 - 11)
	 * @return Días del mes indicado -1 si el mes no es correcto
	 */
	public static int getMonthDays(int month, int year){
		if ((month<0)||(month>11))
			return -1;
		int days = monthDays[month];
		if ((month==1)&&(esBisiesto(year)))
			days++;
		return days;
	}

	/**
	 * Devuelve si un año es bisiesto
	 * @param year Anio a comprobar
	 * @return booleano
	 */
	public static boolean esBisiesto(int year){
		GregorianCalendar calendar = new GregorianCalendar();
		return calendar.isLeapYear(year);
	}

    /**
     * Obtiene el valor del campo de una fecha.
     * @param date Fecha.
     * @param field Campo de la fecha.
     * @return Valor del campo de la fecha.
     */
    public static int getDateField(Date date, int field) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(field);
    }

    /**
     * Permite obtener una instancia de Calendar con los datos pasados como parámetros
     * @param year Año
     * @param month Mes Enero=1
     * @param day Día
     * @return Instancia de calendar
     */
//    public static Calendar getCalendar(int year, int month, int day) {
//    	Calendar calendar = new GregorianCalendar();
//    	calendar.set(Calendar.YEAR, year);
//    	calendar.set(Calendar.MONTH, month);
//    	calendar.set(Calendar.DATE, day);
//    	return calendar;
//    }

    /**
     * Permite obtener el primer día del mes indicado
     * @param year Año
     * @param month mes
     * @return
     */
    public static Date getFirstDateOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        return cal.getTime();
    }

    /**
     * Permite obtener el último día del mes
     * @param year Año
     * @param month Mes
     * @return
     */
    public static Date getLastDateOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        int days = getMonthDays(month, year);
        cal.set(year, month, days);
        return cal.getTime();
    }

    /**
     * Permite incrementar un día a una fecha
     * @param date fecha a incrementar
     * @return fecha incrementada
     */
    public static Date incDay(Date date){
    	Calendar calendar = new GregorianCalendar();
    	calendar.setTime(date);

        // Si se llega al último día del año devolver el primero del siguiente
        if ((calendar.get(GregorianCalendar.MONTH)==11)&&(calendar.get(GregorianCalendar.DATE)==31)) {
            calendar.set(Calendar.DATE, 1);
            calendar.set(Calendar.MONTH, 0);
            calendar.roll(Calendar.YEAR, true);
        } else {
	        // Incrementar el día en uno
	    	calendar.roll(Calendar.DAY_OF_YEAR, true);
        }
    	return calendar.getTime();
    }

    /**
     * Obtiene el Primero Día del Año
     * @param anio Año del que se quiere calcular la fecha
     * @return Date Fecha del Primer día del año
     */
    public static Date getFirstDayOfYear(int anio) {
    	return getFechaSinHora(1, 1, anio);
    }

    /**
     * Obtiene el Ultimo Día del Año
     * @param anio Año del que se quiere calcular la fecha
     * @return Date Fecha del Primer día del año
     */
    public static Date getLastDayOfYear(int anio) {
		return getFechaSinHora(31, 12, anio);
    }


    /**
     * Devuelve la fecha con hora 00:00:00
     * @return
     */
    public static Date getFechaSinHora(int dia, int mes, int anio) {

		Calendar newCal = GregorianCalendar.getInstance();
		newCal.set(anio, mes-1, dia, 0, 0, 0);
		newCal.set(GregorianCalendar.MILLISECOND,0);
		return newCal.getTime();
    }

    /**
     * Devuelve la fecha con hora
     * @return
     */
    public static Date getFechaConHora(int dia, int mes, int anio, int hora, int minutos, int segundos) {

		Calendar newCal = GregorianCalendar.getInstance();
		newCal.set(anio, mes-1, dia, hora, minutos, segundos);
		newCal.set(GregorianCalendar.MILLISECOND,0);
		return newCal.getTime();
    }

    /**
     * Devuelve la fecha con hora 00:00:00
     * @return
     */
    public static Date getFechaSinHora(Date fechaConHora) {

		Calendar newCal = GregorianCalendar.getInstance();
		newCal.setTime(fechaConHora);

		newCal.set(Calendar.DAY_OF_WEEK_IN_MONTH, newCal.get(Calendar.DAY_OF_WEEK_IN_MONTH));
		newCal.set(Calendar.MONTH, newCal.get(Calendar.MONTH));
		newCal.set(Calendar.YEAR, newCal.get(Calendar.YEAR));
		newCal.set(Calendar.HOUR_OF_DAY,0);
		newCal.set(Calendar.MINUTE,0);
		newCal.set(Calendar.SECOND,0);
		newCal.set(Calendar.MILLISECOND,0);

        return newCal.getTime();

    }

    /**
     * Añade a la fecha los días especificados
     * @param fecha Fecha inicial
     * @param numDias numero de dias a añadir
     * @return
     */
    public static Date addDays(Date fecha, int numDias){
		Calendar newCal = GregorianCalendar.getInstance();
		newCal.setTime(fecha);
    	newCal.add(Calendar.HOUR, numDias * PrestamosConstants.HORAS_DIA);

        return newCal.getTime();
    }

    public static Date getDate(String fecha, String formato, String separador) throws ParseException{
    	SimpleDateFormat sdf = CustomDateFormat.getSimpleDateFormat(formato, separador);

    	if(sdf != null){
			return sdf.parse(fecha);
    	}
    	return null;
    }
}