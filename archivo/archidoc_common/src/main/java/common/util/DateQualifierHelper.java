package common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Utilidad para tratar calificadores de fechas.
 */
public class DateQualifierHelper
{

	//=======================================================================
	// CALIFICADORES DE FECHAS
	//=======================================================================
	public static final String CALIFICADOR_ANTERIOR_A				= "ant";
	public static final String CALIFICADOR_POSTERIOR_A				= "pos";
	public static final String CALIFICADOR_APROXIMADA 				= "apr";
	public static final String CALIFICADOR_CONOCIDA					= "con";
	public static final String CALIFICADOR_SUPUESTA					= "sup";
	public static final String CALIFICADOR_TAL_Y_COMO_SE_HA_ESCRITO	= "sic";
	public static final String CALIFICADOR_ANTES_DE_CRISTO 			= "a.C.";
	public static final String CALIFICADOR_PRIMERA_MITAD 			= "p.m.";
	public static final String CALIFICADOR_SEGUNDA_MITAD			= "s.m.";
	public static final String CALIFICADOR_PRIMER_TERCIO 			= "p.t.";
	public static final String CALIFICADOR_SEGUNDO_TERCIO 			= "s.t.";
	public static final String CALIFICADOR_ULTIMO_TERCIO 			= "u.t.";
	public static final String CALIFICADOR_CERCA 					= "c.";
	public static final String CALIFICADOR_PRINCIPIOS 				= "p.";
	public static final String CALIFICADOR_MEDIDADOS 				= "m.";
	public static final String CALIFICADOR_FINALES 					= "f.";
	public static final String CALIFICADOR_SIN_FECHA 				= "sf";
	//=======================================================================

	
	/**
	 * Obtiene la fecha mínima en función del calificador.
	 * @param qualifier Calificador de la fecha.
	 * @param syear Año.
	 * @return Fecha mínima.
	 */
	public static Date getMinDate(String qualifier, String syear)
	{
		int year = TypeConverter.toInt(syear);
		int month = Calendar.JANUARY;

		/*
		 * Faltan:
			CALIFICADOR_APROXIMADA
			CALIFICADOR_CONOCIDA
			CALIFICADOR_SUPUESTA
			CALIFICADOR_TAL_Y_COMO_SE_HA_ESCRITO
			CALIFICADOR_CERCA
		*/

		if (!StringUtils.isBlank(qualifier))
		{
			if (CALIFICADOR_ANTERIOR_A.equals(qualifier) ||
				CALIFICADOR_SIN_FECHA.equals(qualifier))
				return null;
			
			if (CALIFICADOR_POSTERIOR_A.equals(qualifier))
				year++;
			else if (CALIFICADOR_ANTES_DE_CRISTO.equals(qualifier))
				year *= -1;
			else if (CALIFICADOR_SEGUNDA_MITAD.equals(qualifier) ||
					 CALIFICADOR_FINALES.equals(qualifier))
				month = Calendar.JULY;
			else if (CALIFICADOR_SEGUNDO_TERCIO.equals(qualifier) ||
					CALIFICADOR_MEDIDADOS.equals(qualifier))
				month = Calendar.MAY;
			else if (CALIFICADOR_ULTIMO_TERCIO.equals(qualifier))
				month = Calendar.SEPTEMBER;
		}

		return createDate(year, month, 1, 0, 0, 0);
	}

	
	/**
	 * Obtiene la fecha máxima en función del calificador.
	 * @param qualifier Calificador de la fecha.
	 * @param syear Año.
	 * @return Fecha máxima.
	 */
	public static Date getMaxDate(String qualifier, String syear)
	{
		int year = TypeConverter.toInt(syear);
		int month = Calendar.DECEMBER;
		
		/*
		 * Faltan:
			CALIFICADOR_APROXIMADA
			CALIFICADOR_CONOCIDA
			CALIFICADOR_SUPUESTA
			CALIFICADOR_TAL_Y_COMO_SE_HA_ESCRITO
			CALIFICADOR_CERCA
		*/

		if (!StringUtils.isBlank(qualifier))
		{
			if (CALIFICADOR_POSTERIOR_A.equals(qualifier) ||
				CALIFICADOR_SIN_FECHA.equals(qualifier))
				return null;
			
			if (CALIFICADOR_ANTERIOR_A.equals(qualifier))
				year--;
			else if (CALIFICADOR_ANTES_DE_CRISTO.equals(qualifier))
				year *= -1;
			else if (CALIFICADOR_PRIMERA_MITAD.equals(qualifier) ||
					 CALIFICADOR_PRINCIPIOS.equals(qualifier))
				month = Calendar.JUNE;
			else if (CALIFICADOR_PRIMER_TERCIO.equals(qualifier))
				month = Calendar.APRIL;
			else if (CALIFICADOR_SEGUNDO_TERCIO.equals(qualifier) ||
					CALIFICADOR_MEDIDADOS.equals(qualifier))
				month = Calendar.AUGUST;
		}

		return createDate(year, month, getMaxDayOfMonth(year, month), 23, 59, 59);
	}

		
	/**
	 * Obtiene la fecha mínima en función del calificador.
	 * @param qualifier Calificador de la fecha.
	 * @param syear Año.
	 * @param smonth Mes.
	 * @return Fecha mínima.
	 */
	public static Date getMinDate(String qualifier, String syear, String smonth)
	{
		int year = TypeConverter.toInt(syear); 
		int month = TypeConverter.toInt(smonth, 1) - 1;
		int day = 1;

		/*
		 * Faltan:
			CALIFICADOR_APROXIMADA
			CALIFICADOR_CONOCIDA
			CALIFICADOR_SUPUESTA
			CALIFICADOR_TAL_Y_COMO_SE_HA_ESCRITO
			CALIFICADOR_CERCA
		*/

		if (!StringUtils.isBlank(qualifier))
		{
			if (CALIFICADOR_ANTERIOR_A.equals(qualifier) ||
				CALIFICADOR_SIN_FECHA.equals(qualifier))
				return null;
			
			if (CALIFICADOR_POSTERIOR_A.equals(qualifier))
				month++;
			else if (CALIFICADOR_ANTES_DE_CRISTO.equals(qualifier))
				year *= -1;
			else if (CALIFICADOR_SEGUNDA_MITAD.equals(qualifier) ||
					CALIFICADOR_FINALES.equals(qualifier))
				day = 16;
			else if (CALIFICADOR_SEGUNDO_TERCIO.equals(qualifier) ||
					CALIFICADOR_MEDIDADOS.equals(qualifier))
				day = 11;
			else if (CALIFICADOR_ULTIMO_TERCIO.equals(qualifier))
				day = 21;
		}

		return createDate(year, month, day, 0, 0, 0);
	}


	/**
	 * Obtiene la fecha máxima en función del calificador.
	 * @param qualifier Calificador de la fecha.
	 * @param syear Año.
	 * @param smonth Mes.
	 * @return Fecha máxima.
	 */
	public static Date getMaxDate(String qualifier, String syear, String smonth)
	{
		int year = TypeConverter.toInt(syear);
		int month = TypeConverter.toInt(smonth, 12) - 1;
		int day = getMaxDayOfMonth(year, month);
		
		/*
		 * Faltan:
			CALIFICADOR_APROXIMADA
			CALIFICADOR_CONOCIDA
			CALIFICADOR_SUPUESTA
			CALIFICADOR_TAL_Y_COMO_SE_HA_ESCRITO
			CALIFICADOR_CERCA
		*/

		/*
		CALIFICADOR_PRIMERA_MITAD
		CALIFICADOR_SEGUNDA_MITAD
		CALIFICADOR_PRIMER_TERCIO
		CALIFICADOR_SEGUNDO_TERCIO
		CALIFICADOR_ULTIMO_TERCIO
		CALIFICADOR_PRINCIPIOS
		CALIFICADOR_MEDIDADOS
		CALIFICADOR_FINALES
		*/
		
		if (!StringUtils.isBlank(qualifier))
		{
			if (CALIFICADOR_POSTERIOR_A.equals(qualifier) ||
				CALIFICADOR_SIN_FECHA.equals(qualifier))
				return null;
			
			if (CALIFICADOR_ANTERIOR_A.equals(qualifier))
			{
				month--;
				day = getMaxDayOfMonth(year, month);
			}
			else if (CALIFICADOR_ANTES_DE_CRISTO.equals(qualifier))
				year *= -1;
			else if (CALIFICADOR_PRIMERA_MITAD.equals(qualifier) ||
					 CALIFICADOR_PRINCIPIOS.equals(qualifier))
				day = 15;
			else if (CALIFICADOR_PRIMER_TERCIO.equals(qualifier))
				day = 10;
			else if (CALIFICADOR_SEGUNDO_TERCIO.equals(qualifier) ||
					CALIFICADOR_MEDIDADOS.equals(qualifier))
				day = 20;
		}
		
		return createDate(year, month, day, 23, 59, 59);
	}

	
	/**
	 * Obtiene la fecha mínima en función del calificador.
	 * @param qualifier Calificador de la fecha.
	 * @param syear Año.
	 * @param smonth Mes.
	 * @param sday Día.
	 * @return Fecha mínima.
	 */
	public static Date getMinDate(String qualifier, String syear, String smonth, String sday)
	{
		int year = TypeConverter.toInt(syear); 
		int month = TypeConverter.toInt(smonth, 1) - 1;
		int day = TypeConverter.toInt(sday, 1);

		/*
		 * Faltan:
			CALIFICADOR_APROXIMADA
			CALIFICADOR_CONOCIDA
			CALIFICADOR_SUPUESTA
			CALIFICADOR_TAL_Y_COMO_SE_HA_ESCRITO
			CALIFICADOR_CERCA
		*/

		if (!StringUtils.isBlank(qualifier))
		{
			if (CALIFICADOR_ANTERIOR_A.equals(qualifier) ||
				CALIFICADOR_SIN_FECHA.equals(qualifier))
				return null;
			
			if (CALIFICADOR_POSTERIOR_A.equals(qualifier))
				day++;
			else if (CALIFICADOR_ANTES_DE_CRISTO.equals(qualifier))
				year *= -1;
		}

		return createDate(year, month, day, 0, 0, 0);
	}


	/**
	 * Obtiene la fecha máxima en función del calificador.
	 * @param qualifier Calificador de la fecha.
	 * @param syear Año.
	 * @param smonth Mes.
	 * @param sday Día.
	 * @return Fecha máxima.
	 */
	public static Date getMaxDate(String qualifier, String syear, String smonth, String sday)
	{
		int year = TypeConverter.toInt(syear);
		int month = TypeConverter.toInt(smonth, 12) - 1;
		int day = TypeConverter.toInt(sday, getMaxDayOfMonth(year, month));
		
		/*
		 * Faltan:
			CALIFICADOR_APROXIMADA
			CALIFICADOR_CONOCIDA
			CALIFICADOR_SUPUESTA
			CALIFICADOR_TAL_Y_COMO_SE_HA_ESCRITO
			CALIFICADOR_CERCA
		*/

		if (!StringUtils.isBlank(qualifier))
		{
			if (CALIFICADOR_POSTERIOR_A.equals(qualifier) ||
				CALIFICADOR_SIN_FECHA.equals(qualifier))
				return null;
			
			if (CALIFICADOR_ANTERIOR_A.equals(qualifier))
				day--;
			else if (CALIFICADOR_ANTES_DE_CRISTO.equals(qualifier))
				year *= -1;
		}

		return createDate(year, month, day, 23, 59, 59);
	}


	/**
	 * Obtiene la fecha mínima en función del calificador.
	 * @param qualifier Calificador de la fecha.
	 * @param century Siglo.
	 * @return Fecha mínima.
	 */
	public static Date getMinDate(String qualifier, int century)
	{
		int year = (century - 1) * 100 + 1; // Sin calificador, CALIFICADOR_PRIMERA_MITAD, CALIFICADOR_PRINCIPIOS, CALIFICADOR_PRIMER_TERCIO
		int month = Calendar.JANUARY;
		int day = 1;

		/*
		 * Faltan:
			CALIFICADOR_APROXIMADA
			CALIFICADOR_CONOCIDA
			CALIFICADOR_SUPUESTA
			CALIFICADOR_TAL_Y_COMO_SE_HA_ESCRITO
			CALIFICADOR_CERCA
		*/

		if (!StringUtils.isBlank(qualifier))
		{
			if (CALIFICADOR_ANTERIOR_A.equals(qualifier) ||
				CALIFICADOR_SIN_FECHA.equals(qualifier))
				return null;

			if (CALIFICADOR_ANTES_DE_CRISTO.equals(qualifier))
				year = -(century * 100);
			else 
				if (CALIFICADOR_SEGUNDA_MITAD.equals(qualifier) || 
					CALIFICADOR_FINALES.equals(qualifier))
				year = (century - 1) * 100 + 51;
			else
				if (CALIFICADOR_SEGUNDO_TERCIO.equals(qualifier) ||
					CALIFICADOR_MEDIDADOS.equals(qualifier))
				year = (century - 1) * 100 + 34;
			else
				if (CALIFICADOR_ULTIMO_TERCIO.equals(qualifier))
				year = (century - 1) * 100 + 67;
			else
				if (CALIFICADOR_POSTERIOR_A.equals(qualifier))
				year = century * 100 + 1;
		}
		
		return createDate(year, month, day, 0, 0, 0);
	}

	
	/**
	 * Obtiene la fecha máxima en función del calificador.
	 * @param qualifier Calificador de la fecha.
	 * @param century Siglo.
	 * @return Fecha máxima.
	 */
	public static Date getMaxDate(String qualifier, int century)
	{
		int year = century * 100; // Sin calificador, CALIFICADOR_SEGUNDA_MITAD, CALIFICADOR_FINALES, CALIFICADOR_ULTIMO_TERCIO 
		int month = Calendar.DECEMBER;
		int day = 31;
		
		/*
		 * Faltan:
			CALIFICADOR_APROXIMADA
			CALIFICADOR_CONOCIDA
			CALIFICADOR_SUPUESTA
			CALIFICADOR_TAL_Y_COMO_SE_HA_ESCRITO
			CALIFICADOR_CERCA
		 */
		
		if (!StringUtils.isBlank(qualifier))
		{
			if (CALIFICADOR_POSTERIOR_A.equals(qualifier) ||
				CALIFICADOR_SIN_FECHA.equals(qualifier))
				return null;

			if (CALIFICADOR_ANTES_DE_CRISTO.equals(qualifier))
				year = -(century * 100) + 99;
			else 
				if (CALIFICADOR_PRIMERA_MITAD.equals(qualifier) ||
					CALIFICADOR_PRINCIPIOS.equals(qualifier))
				year = (century - 1) * 100 + 50;
			else 
				if (CALIFICADOR_PRIMER_TERCIO.equals(qualifier))
				year = (century - 1) * 100 + 33;
			else 
				if (CALIFICADOR_SEGUNDO_TERCIO.equals(qualifier) ||
					CALIFICADOR_MEDIDADOS.equals(qualifier))
				year = (century - 1) * 100 + 66;
			else 
				if (CALIFICADOR_ANTERIOR_A.equals(qualifier))
				year = (century - 1) * 100;
		}
		
		return createDate(year, month, day, 23, 59, 59);
	}
	
	
	/**
	 * Crea un objeto Date.
	 * @param year Año.
	 * @param month Mes.
	 * @param day Día.
     * @param hour Hora.
     * @param minute Minuto.
     * @param second Segundo.
	 * @return Objeto Date.
	 */
	protected static Date createDate(int year, int month, int day, int hour, 
            int minute, int second)
	{
		Calendar cal = new GregorianCalendar();
		cal.set(year, month, day, hour, minute, second);
		
		return cal.getTime();
	}
	
	
	/**
	 * Obtiene el número del máximo día del mes. 
	 * @param year Año.
	 * @param month Mes.
	 * @return Número del máximo día del mes.
	 */
	protected static int getMaxDayOfMonth(int year, int month)
	{
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
}
