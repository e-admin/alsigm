package ieci.tecdoc.sgm.pe;

import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Util {
	public static String getFechaCuaderno60(Date pfecha){
		   Calendar cal = GregorianCalendar.getInstance();
		   cal.setTime(pfecha);
		   return getFechaCuaderno60(cal);
	   }

	   public static String getFechaCuaderno60(Calendar cal){
		   if(cal==null) return null;
		   StringBuffer sbFecha = new StringBuffer();
		   sbFecha.append(cal.get(GregorianCalendar.YEAR));
		   String cAux = String.valueOf(cal.get(GregorianCalendar.MONTH) + 1);
		   if(cAux.length() < 2){
			   sbFecha.append("0");
		   }
		   sbFecha.append(cAux);
		   cAux = String.valueOf(cal.get(GregorianCalendar.DAY_OF_MONTH));
		   if(cAux.length() < 2){
			   sbFecha.append("0");
		   }
		   sbFecha.append(cAux);
		   return sbFecha.toString();
	   }
	   
	   public static Date getDateCuaderno60(String fecha){
		   Date date = null;
		   try{ date = DateTimeUtil.getDate(fecha, "yyyyMMdd"); }
		   catch(Exception e){ }
		   return date;
	   }
	   
	   public static String getFechaDDMMAA(Date fecha){
		   String retorno = null;
		   SimpleDateFormat dateFormat=new SimpleDateFormat("ddMMyy");
		   try{ retorno = dateFormat.format(fecha); }
		   catch(Exception e){ }
		   return retorno;
	   }

	   public static String getHoraCuaderno60(Calendar cal){
		   StringBuffer sbFecha = new StringBuffer();
		   String cAux = String.valueOf(cal.get(GregorianCalendar.HOUR_OF_DAY));
		   if(cAux.length() < 2){
			   sbFecha.append("0");
		   }
		   sbFecha.append(cAux);
		   cAux = String.valueOf(cal.get(GregorianCalendar.MINUTE));
		   if(cAux.length() < 2){
			   sbFecha.append("0");
		   }
		   sbFecha.append(cAux);
		   cAux = String.valueOf(cal.get(GregorianCalendar.SECOND));
		   if(cAux.length() < 2){
			   sbFecha.append("0");
		   }
		   sbFecha.append(cAux);
		   cAux = String.valueOf(cal.get(GregorianCalendar.MILLISECOND));
		   if(cAux.length() < 2){
			   sbFecha.append("0");
			   if(cAux.length() < 3){
				   sbFecha.append("0");
			   }
		   }
		   sbFecha.append(cAux);
		   sbFecha.append("0");
		   return sbFecha.toString();
	   }
}
