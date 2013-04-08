package ieci.tecdoc.sgm.pe;
/*
 * $Id: Util.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Util {
	
	public static int JGREG= 15 + 31*(10+12*1582);
	
	public static String rellenarConCerosIzquierda(String cadena, int tam){
		int eCeros = tam - cadena.length();
		StringBuffer sbAux = new StringBuffer();
		for(int eContador = 0; eContador < eCeros; eContador++){
			sbAux.append("0");
		}
		sbAux.append(cadena);
		return sbAux.toString();
	}
	
	public static String formatearImporteAEuros(String pcImporte){
		if( (pcImporte == null)
			||("".equals(pcImporte))
		){
			return null;
		}
		int enCentimos = Integer.valueOf(pcImporte).intValue();
		double enEuros = enCentimos / 100.0;
		DecimalFormat format =  new DecimalFormat("#######.00");
		return format.format(enEuros);
	}

	public static String formatearImporte(String pcImporte, int peTamano){
		if((pcImporte == null) || ("".equals(pcImporte))){
			return null;
		}
		if(peTamano <= 0){
			return null;
		}
		String cAux = removeChar(pcImporte, '.');
		if(cAux.length() > peTamano){
			return null;
		}else{
			int eCeros = peTamano - cAux.length();
			StringBuffer sbAux = new StringBuffer();
			for(int eContador = 0; eContador < eCeros; eContador++){
				sbAux.append("0");
			}
			sbAux.append(cAux);
			return sbAux.toString();
		}
		
	}
	public static String removeChar(String str, char c)
	{

	      byte[] data = str.getBytes();
	      
	      ByteArrayOutputStream out = new ByteArrayOutputStream();
	      for (int i = 0; i < data.length; i++)
	      {
	         if (data[i] != c)
	            out.write(data[i]);
	      }
	      
	      return(out.toString());
	}

	   
	   public static String getDecimalsStr(String str, int numDec)
	   {

	      byte[] data = str.getBytes();
	      
	      ByteArrayOutputStream out = new ByteArrayOutputStream();
	      for (int i = data.length - 1 ; i >= 0; i--)
	      {
	         if ((data[i] < 48) || (data[i] > 57))
	         {
	            for (int j = 1; j <= numDec; j++)
	            {
	               if ((i + j) < data.length)
	                  out.write(data[i+j]);
	            }
	            break;
	         }
	      }
	      
	      return(out.toString());
	   }

	   
	   public static String format(double value, int intDigits)
	   {
	      int    i;
	      String pattern = "";
	      String tmp, num;
	      
	      DecimalFormat numFormat = (DecimalFormat)DecimalFormat.getInstance();
	      for (i = 1; i <= intDigits; i++)
	         pattern = pattern + "#";
	      
	      numFormat.applyPattern(pattern);

	      tmp = numFormat.format(value);
	      num = "";
	      for (i = 1; i <= intDigits - tmp.length(); i++)
	      {
	         num += "0";
	      }
	      num += tmp;
	      
	      return num;
	   }

	   public static long getJulianDate(Calendar poCalendar){
		   int[] params = new int[3];
		   params[0] = poCalendar.get(Calendar.YEAR);
		   params[1] = poCalendar.get(Calendar.MONTH);
		   params[2] = poCalendar.get(Calendar.DAY_OF_MONTH);
		   return new Double(toJulian(params)).longValue();
	   }
	   
	   public static Date parsearFechaCuaderno69(String pcFecha, String pcHora){
		   if( (pcFecha == null) || ("".equals(pcFecha)) || (pcHora == null) || ("".equals(pcHora))
		   ){
			   return null;   
		   }
		   String cAux = pcFecha.substring(0, 4);
		   Calendar oCal = GregorianCalendar.getInstance();
		   oCal.set(GregorianCalendar.YEAR, Integer.valueOf(cAux).intValue());
		   cAux = pcFecha.substring(4,6);
		   oCal.set(GregorianCalendar.MONTH, Integer.valueOf(cAux).intValue() - 1);
		   cAux = pcFecha.substring(6,8);
		   oCal.set(GregorianCalendar.DAY_OF_MONTH, Integer.valueOf(cAux).intValue());
		   
		   cAux = pcHora.substring(0,2);
		   oCal.set(GregorianCalendar.HOUR_OF_DAY, Integer.valueOf(cAux).intValue());
		   
		   cAux = pcHora.substring(2,4);
		   oCal.set(GregorianCalendar.MINUTE, Integer.valueOf(cAux).intValue());

		   cAux = pcHora.substring(4,6);
		   oCal.set(GregorianCalendar.SECOND, Integer.valueOf(cAux).intValue());

		   return oCal.getTime();
	   }
	   
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

	   
	   private static double toJulian(int[] ymd) {
		   int year=ymd[0];
		   int month=ymd[1]; // jan=1, feb=2,...
		   int day=ymd[2];    
		   int julianYear = year;
		   if (year < 0) julianYear++;
		   int julianMonth = month;
		   if (month > 2) {
		     julianMonth++;
		   }
		   else {
		     julianYear--;
		     julianMonth += 13;
		   }
		   
		   double julian = (java.lang.Math.floor(365.25 * julianYear)
		        + java.lang.Math.floor(30.6001*julianMonth) + day + 1720995.0);
		   if (day + 31 * (month + 12 * year) >= JGREG) {
		     // change over to Gregorian calendar
		     int ja = (int)(0.01 * julianYear);
		     julian += 2 - ja + (0.25 * ja);
		   }
		   return java.lang.Math.floor(julian);
		 }	   
}
