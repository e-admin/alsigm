package ieci.tdw.ispac.ispaclib.utils;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.log4j.Logger;

/**
 *	DateUtil
 *	Collection of usefull static methos for date manipulation
 *
 *	TODO:
 *	1 - Date Formatting is not internationalized
 *	2 - Date formatting now is static, cannot be changed
 *
 *  @author: Max neri <max.neri@iol.it>
 *  @version: 1.0
 *  @since: Nov 21th, 2002
 */

public class DateUtil {
    
	private static final Logger logger = Logger.getLogger(DateUtil.class);
    private static DateFormat df, df2, df3, df4, timeFormat, datetimeFormat;
    
	public static final Locale defaultLocale = new Locale("es","ES");
	public static final int FECHA_MAYOR = 1;
	public static final int FECHA_MENOR = -1;
	public static final int FECHA_IGUAL = 0;
	
    static {
        // df = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, Locale.ITALY);
        df = new SimpleDateFormat("dd MMMM yyyy", defaultLocale);
        df2 = new SimpleDateFormat("dd-MM-yyyy", defaultLocale);
        df3 = new SimpleDateFormat("dd.MM.yyyy 'at' hh:mm:ss z", defaultLocale);
        df4 = new SimpleDateFormat("dd/MM/yyyy", defaultLocale);
        timeFormat = new SimpleDateFormat("HH:mm", defaultLocale);
        datetimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", defaultLocale);
    }
    
    public static DateFormat getDateFormat() {
        
        return df;
    }
    
    public static String format(Date date){
        if(date == null){
            return "";
        }
        try{
            return df.format(date);
        } catch(NullPointerException e) {
        	logger.error("DateUtil.format(): formatting " + date, e);
            return null;
        }
    }
    
    public static String format(Timestamp ts){
        Date date = new Date(ts.getTime());
        return format(date);
    }
    
    
    public static String format(java.util.Date date,String formato){
        DateFormat df = new SimpleDateFormat(formato, defaultLocale);
         if(date == null){
            return "";
        }
        try{
            return df.format(date);
        } catch(NullPointerException e) {
        	logger.error("DateUtil.format(): formatting " + date, e);
            return null;
        }
    }
    
    public static String formatDateTime(java.util.Date date){
         if(date == null){
            return "";
        }
        try{
            return df3.format(date);
        } catch(NullPointerException e) {
        	logger.error("DateUtil.formatDateTime(): formatting " + date, e);
            return null;
        }
    }
    
    public static String formatTime(Date date){
         if(date == null){
            return "";
        }
        try{
            return timeFormat.format(date);
        } catch(NullPointerException e) {
        	logger.error("DateUtil.formatTime():  " + date, e);
            return null;
        }
    }

    public static String formatDate(java.util.Date date) {
		if (date == null) {
			return "";
		}
		try {
			return df4.format(date);
		} catch (NullPointerException e) {
			logger.error("DateUtil.formatDate():  " + date, e);
			return null;
		}
	}

    public static String formatCalendar(Calendar calendar) {
		if (calendar == null) {
			return "";
		}
		try {
			return datetimeFormat.format(calendar.getTime());
		} catch (NullPointerException e) {
			System.out.println("DateUtil.formatCalendar():  " + calendar + " - " + e);
			return null;
		}
	}

   public static Date getDate(String date){
        
        java.util.Date d;
        
        try{
            d = df.parse(date);
        } catch(java.text.ParseException e) {
            try{
                d = df2.parse(date);
            } catch(java.text.ParseException e1) {
                try{
                    d = df4.parse(date);
                } catch(java.text.ParseException e2) {
	            	System.out.println("DateUtil.getDate(): parsing " + date + " - " + e2);
	                return null;
                }
            }
        } catch(NullPointerException e2) {
        	System.out.println("DateUtil.getDate(): parsing " + date + " - " + e2);
            return null;
        }
        
        return new Date(d.getTime());
    }
    
    public static Date getDate(String date,String formato){
        
        java.util.Date d;
        DateFormat df = new SimpleDateFormat(formato, defaultLocale);
        try{
            d = df.parse(date);
        } catch(java.text.ParseException e) {
            try{
                d = df2.parse(date);
            } catch(java.text.ParseException e1) {
            	System.out.println("DateUtil.getDate(): parsing " + date + " - " + e1);
                return null;
            }
        } catch(NullPointerException e2) {
        	System.out.println("DateUtil.getDate(): parsing " + date + " - " + e2);
            return null;
        }
        
        return new Date(d.getTime());
    }
    
    public static Date getToday(){
        return new Date(System.currentTimeMillis());
    }
    
    public static Time getTime(int horas, int minutos) {
        return new Time(getSqlDate(minutos, horas, 0,0,0).getTime());
    }
    
    public static Calendar getCalendar(java.util.Date d){
        Calendar c = new GregorianCalendar();
        c.setTime(d);
        return c;
    }

    public static Calendar getCalendar(String dateString){
        Calendar ret = Calendar.getInstance();
        
        try {
            ret.setTime(datetimeFormat.parse(dateString));
        } catch (ParseException e) {
            try {
                ret.setTime(df4.parse(dateString));
            } catch (ParseException e1) {
                String msgError = DateUtil.class.getName() + ":Error al parsear la fecha '"
                + dateString + "'";
            	logger.error(msgError, e);
		        throw new RuntimeException(msgError);
            }
        }
        return ret;
    }

    public static Calendar getCalendar(String dateString, String pattern){
        Calendar ret = Calendar.getInstance();
        
        try {
        	SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            ret.setTime(dateFormat.parse(dateString));
        } catch (ParseException e) {
            String msgError = DateUtil.class.getName() + ":Error al parsear la fecha '"
            + dateString + "'";
        	//logger.warn(msgError, e);
	        throw new RuntimeException(msgError);
        }
        return ret;
    }

    /**
     * - month starts from 1
     *
     * This method has been taken here:
     * <link>http://www.jguru.com/faq/view.jsp?EID=422110</link>
     */
    public static Date getSqlDate(int minute, int hour, int day, int month, int year){
        Calendar cal = Calendar.getInstance();
        
        // set Date portion
        cal.set( year, month-1, day, hour, minute );
        
        return new java.sql.Date( cal.getTime().getTime() );
    }// getSqlDate
    
    public static long diferenciaDias(Date fechaInicio,Date fechaFin){
        return (fechaFin.getTime() - fechaInicio.getTime())/(1000*60*60*24);
    }
    /**
     * Funcion que retorna la fecha java.sql.Date del sistema según el default Locale de la
     * clase.
     * @return java.sql.Date con la fecha del sistema
     */
	public static java.sql.Date getSystemDate(){
			Calendar calendar = Calendar.getInstance(defaultLocale);
			return new java.sql.Date(calendar.getTime().getTime());
		}    
	
	/**
	 * Obtiene el año en curso.
	 * @return Año en curso.
	 */
	public static int getCurrentYear() {
        Calendar cal = new GregorianCalendar();
        return cal.get(Calendar.YEAR); 
	}

	
	/**
	 * Compara dos fechas de tipo Calendar.
	 * 
	 * @param fechaInicial Fecha de inicio.
	 * @param fechaFinal Fecha de fin.
	 * @return Devuelve -1 si fechaInicial &lt; fechaFinal,
	 *                   0 si fechaInicial = fechaFinal,
	 *                   1 si fechaInicial &gt; fechaFinal 
	 */
	public static int compare(Calendar fechaInicial, Calendar fechaFinal) throws Exception {
		//Esta funcion esta copiada¿¿¿No se por que no compara las fechas directamente con fechaInicial.before(fechaFinal)???
		
		int anioInicial = fechaInicial.get(GregorianCalendar.YEAR);
		int anioFinal = fechaFinal.get(GregorianCalendar.YEAR);
		int mesInicial = fechaInicial.get(GregorianCalendar.MONTH);
		int mesFinal = fechaFinal.get(GregorianCalendar.MONTH);
		int diaInicial = fechaInicial.get(GregorianCalendar.DATE);
		int diaFinal = fechaFinal.get(GregorianCalendar.DATE);
		
		int horaInicial = fechaInicial.get(Calendar.HOUR);
		int horaFinal = fechaFinal.get(Calendar.HOUR);
		int minutoInicial = fechaInicial.get(Calendar.MINUTE);
		int minutoFinal = fechaFinal.get(Calendar.MINUTE);
		int segundoInicial = fechaInicial.get(Calendar.SECOND);
		int segundoFinal = fechaFinal.get(Calendar.SECOND);
		int milisegundoInicial = fechaInicial.get(Calendar.MILLISECOND);
		int milisegundoFinal = fechaFinal.get(Calendar.MILLISECOND);

		if (anioInicial > anioFinal)
			return FECHA_MAYOR;
		else if (anioInicial < anioFinal)
			return FECHA_MENOR;
		
		if (mesInicial > mesFinal)
			return FECHA_MAYOR;
		else if (mesInicial < mesFinal)
			return FECHA_MENOR;
		
		if (diaInicial > diaFinal)
			return FECHA_MAYOR;
		else if (diaInicial < diaFinal)
			return FECHA_MENOR;
		
		if (horaInicial > horaFinal)
			return FECHA_MAYOR;
		else if (horaInicial < horaFinal)
			return FECHA_MENOR;		
		
		if (minutoInicial > minutoFinal)
			return FECHA_MAYOR;
		else if (minutoInicial < minutoFinal)
			return FECHA_MENOR;		

		if (segundoInicial > segundoFinal)
			return FECHA_MAYOR;
		else if (segundoInicial < segundoFinal)
			return FECHA_MENOR;
		
		if (milisegundoInicial > milisegundoFinal)
			return FECHA_MAYOR;
		else if (milisegundoInicial < milisegundoFinal)
			return FECHA_MENOR;
		
		return FECHA_IGUAL;
	}	
	/**
	 * Compara dos fechas de tipo Date.
	 * 
	 * @param fechaInicial Fecha de inicio.
	 * @param fechaFinal Fecha de fin.
	 * @return Devuelve -1 si fechaInicial < fechaFinal,
	 *                   0 si fechaInicial = fechaFinal,
	 *                   1 si fechaInicial > fechaFinal 
	 * @throws Exception 
	 */
	public static int compare(java.util.Date init, java.util.Date end) throws Exception {
			GregorianCalendar gcFechaInicial = new GregorianCalendar();
			gcFechaInicial.setTime(init);
			GregorianCalendar gcFechaFinal = new GregorianCalendar();
			gcFechaFinal.setTime(end);
			
			return compare(gcFechaInicial, gcFechaFinal);
		
	}

	public static java.util.Date parseDate(String datestring) {
        try{
        	if (StringUtils.isBlank(datestring)) {
        		return null;
        	}
            return df4.parse(datestring);
        } catch(Throwable e) {
        	System.out.println("DateUtil.parseDate(): " + datestring + " - " + e);
            return null;
        }
	}

	public static java.util.Date parseDatetime(String datestring) {
        try{
        	if (StringUtils.isBlank(datestring)) {
        		return null;
        	}
            return datetimeFormat.parse(datestring);
        } catch(ParseException e) {
        	System.out.println("DateUtil.parseDatetime(): " + datestring + " - " + e);
            return null;
        }
	}

}