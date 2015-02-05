package es.ieci.plusvalias.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	public static int getYearFrom(Date date){
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * Calcula el numero de años de diferencia entre dos fechas teniendo en
	 * cuenta la diferencia de días
	 * 
	 * @param fechaTransActual
	 * @param fechaTransAnterior
	 * @return
	 */
	public static int getNumAnyos(Calendar fechaActual, Calendar fechaAnterior){
		int numAnyos = fechaActual.get(Calendar.YEAR) - fechaAnterior.get(Calendar.YEAR);
		int dias = fechaActual.get(Calendar.DAY_OF_YEAR) - fechaAnterior.get(Calendar.DAY_OF_YEAR);

		if (dias < 0)
		{
			numAnyos--;
		}

		return numAnyos;
	}
	
	public static int getDiasPlazoLiquidacion()	{
		int dias = 0;
		
		Calendar fechaActual = GregorianCalendar.getInstance();
		Calendar fechaTope = GregorianCalendar.getInstance();
		
		int diaActual = fechaActual.get(Calendar.DAY_OF_MONTH);
		
		if (diaActual < 16)
		{
			dias = 30 + (15 - diaActual); 
		}
		else
		{
			dias = 35 + (31 - diaActual); 
		}
		
		return dias;
	}

}
