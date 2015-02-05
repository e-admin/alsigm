package common.util;

import java.util.Date;

import common.Constants;
import common.CustomDateConstants;

/**
 * Utilidad para la conversión de tipos.
 */
public class TypeConverter
{

	/**
	 * Convierte una cadena numérica en un entero.
	 * @param value Cadena numérica.
	 * @return Entero.
	 */
	public static int toInt(String value)
	{
		return Integer.parseInt(value);
	}


	/**
	 * Convierte una cadena numérica en un entero.
	 * @param value Cadena numérica.
	 * @param defaultValue Valor por defecto.
	 * @return Entero.
	 */
	public static int toInt(String value, int defaultValue)
	{
		int intValue = defaultValue;

		try
		{
			if (value != null)
				intValue = Integer.parseInt(value);
		}
		catch (NumberFormatException e)
		{
			intValue = defaultValue;
		}

		return intValue;
	}


	/**
	 * Convierte una cadena numérica en un entero corto.
	 * @param value Cadena numérica.
	 * @return Entero corto.
	 */
	public static short toShort(String value)
	{
		return Short.parseShort(value);
	}


	/**
	 * Convierte una cadena numérica en un entero corto.
	 * @param value Cadena numérica.
	 * @param defaultValue Valor por defecto.
	 * @return Entero corto.
	 */
	public static short toShort(String value, short defaultValue)
	{
		short shortValue = defaultValue;

		try
		{
			if (value != null)
				shortValue = Short.parseShort(value);
		}
		catch (NumberFormatException e)
		{
			shortValue = defaultValue;
		}

		return shortValue;
	}


	/**
	 * Convierte una cadena numérica en un decimal.
	 * @param value Cadena numérica.
	 * @return Decimal.
	 */
	public static double toDouble(String value)
	{
		return Double.parseDouble(value);
	}


	/**
	 * Convierte una cadena numérica en un decimal.
	 * @param value Cadena numérica.
	 * @param defaultValue Valor por defecto.
	 * @return Decimal.
	 */
	public static double toDouble(String value, double defaultValue)
	{
		double doubleValue = defaultValue;

		try
		{
			if (value != null)
				doubleValue = Double.parseDouble(value);
		}
		catch (NumberFormatException e)
		{
			doubleValue = defaultValue;
		}

		return doubleValue;
	}


	/**
	 * Convierte una cadena numérica en un objeto Double.
	 * @param value Cadena numérica.
	 * @return Double.
	 */
	public static Double toDoubleObject(String value)
	{
		Double number = null;

		try
		{
			if (value != null)
				number = Double.valueOf(value);
		}
		catch (NumberFormatException e)
		{
		}

		return number;
	}


	/**
	 * Obtiene una representación de un booleano en formato carácter.
	 * @param bool Booleano
	 * @return Carácter.
	 */
	public static String toString(boolean bool)
	{
		return (bool ? Constants.TRUE_STRING : Constants.FALSE_STRING);
	}

    /**
     * Convierte un texto a booleano
     * @param str Cadena de texto
     * @return <b>true</b> si la cadena de texto es 'S' o 's' y <b>false</b> en caso contrario
     */
    public static boolean toBoolean(String str) {
        boolean boolValue = false;
        if (str != null)
            boolValue = str.equalsIgnoreCase(Constants.TRUE_STRING);
        return boolValue;
    }

	/**
	 * Obtiene una cadena numérica que representa el número decimal.
	 * Si el valor es un entero, elimina la parte decimal.
	 * @param number Número decimal.
	 * @return Cadena numérica.
	 */
	public static String toString(double number)
	{
		String str = Double.toString(number);

		if (str.endsWith(".0"))
			str = str.substring(0, str.length() - 2);

		return str;
	}


	/**
	 * Obtiene una cadena con la información de la fecha.
	 * @param date Fecha.
	 * @return Cadena.
	 */
	public static String toString(Date date)
	{
		String sdate = null;

		if (date != null)
			sdate = CustomDateConstants.SDF_YYYYMMDD.format(date);

		return sdate;
	}


	/**
	 * Obtiene una cadena con la información del objeto.
	 * @param obj Objeto.
	 * @return Cadena.
	 */
	public static String toString(Object obj)
	{
		return (obj != null ? obj.toString() : null);
	}

	public static int[] toIntArray(String[] stringArray){
        if (!ArrayUtils.isEmpty(stringArray)){
            int[] ret = new int[stringArray.length];
            for (int i = 0; i < stringArray.length; i++) {
                ret[i] = toInt(stringArray[i]);
            }
            return ret;
        }
        return null;
	}

}
