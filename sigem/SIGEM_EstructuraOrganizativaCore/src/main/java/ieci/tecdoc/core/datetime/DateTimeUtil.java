package ieci.tecdoc.core.datetime;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;

public final class DateTimeUtil
{

	//~ Constructors -----------------------------------------------------------

	private DateTimeUtil(){}

	//~ Methods ----------------------------------------------------------------

	public static Date getCurrentDateTime()
	{

		return new GregorianCalendar().getTime();

	}

	// Días transcurridos desde 01-01-1971
	public static double getCurrentTimeDays()
	{

		long   ctm;
		double scale    = 1000*60*60*24;
		long   yearInMs = 1000*60*60*24;
		
		ctm = System.currentTimeMillis()-yearInMs;

		return ctm/scale;

	}

	//	 Horas transcurridas desde 01-01-1971
	public static double getCurrentTimeHours()
	{

		long   ctm;
		double scale    = 1000*60*60;
		long   hourInMs = 1000*60*60;

		ctm = System.currentTimeMillis()-hourInMs;

		return ctm/scale;

	}

	public static Date addMinutes(Date srcDate, int mins)
	{

		Calendar c;
		
		c = Calendar.getInstance();
		
		c.setTime(srcDate);
		c.add(Calendar.MINUTE, mins);

		return c.getTime();

	}

   public static Date getDate(String dateText, String pattern) throws Exception 
   {
      Date date;

      SimpleDateFormat format = new SimpleDateFormat(pattern);
      date = format.parse(dateText);

      return date;
   }
   
   public static String getCurrentDateTime(String pattern) 
   {

      String dateTime;
      Date date = getCurrentDateTime();
      
      dateTime = getDateTime(date, pattern);
      
      return dateTime;
   }

   public static String getDateTime(Date date, String pattern) 
   {
      String dateTime = null;
      SimpleDateFormat format = new SimpleDateFormat(pattern);
      
      dateTime = format.format(date);
      
      return dateTime;
   }


}//    class
