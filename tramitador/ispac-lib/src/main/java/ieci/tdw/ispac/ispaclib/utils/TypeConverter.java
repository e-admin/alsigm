package ieci.tdw.ispac.ispaclib.utils;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

final public class TypeConverter
{
	public static final String DATETIMEFORMAT = "dd/MM/yyyy kk:mm:ss";
	//public static final String TIMESTAMPFORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String TIMESTAMPFORMAT = "dd/MM/yyyy HH:mm:ss";
	public static final String DATEFORMAT = "dd/MM/yyyy";
	public static final String TIMEFORMAT = "kk:mm:ss";
	public static final String DEFAULTNUMBERFORMAT = "#,###.##";

	public static String toString(boolean value) {
		return (value ? "S" : "N");
	}
	
	public static boolean toBoolean(String value) {
		return "S".equalsIgnoreCase(value);
	}

	public static boolean toBoolean(Boolean value, boolean def) {
		return (value != null ? value.booleanValue() : def);
	}

	public static String toString(Date value)
	{
		return toString(value, DATEFORMAT);
	}

	public static String toString(Date value, String dateformat)
	{
		if (value == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		return sdf.format(value);
	}

    public static String toString(Object value)
    {
        if (value==null)
            return "";

        if (value instanceof Date)
            return toString((Date)value, DATEFORMAT);

        return value.toString();
    }

	public static String toString(float value, String numberformat)
	{
		DecimalFormat sdf = new DecimalFormat(numberformat);
		return sdf.format(value);
	}
	
	public static String toString(double value, String numberformat)
	{
		DecimalFormat sdf = new DecimalFormat(numberformat);
		return sdf.format(value);
	}	
    
	public static Date toDate(String value) throws ISPACException
	{
		return toDate(value, DATEFORMAT);
	}

	public static Date toDate(String value, String dateformat)
			throws ISPACException
	{
		if (StringUtils.isBlank(value)) {
			return null;
		}
		
		try
		{
			 SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
			 Date date=sdf.parse(value);
			if(!sdf.format(date).equals(value)){
			throw new ISPACException("invalid date");
			
			}
			return date;
			
		}
		catch (java.text.ParseException e)
		{
			throw new ISPACException(e);
		}
	}

	public static Timestamp toTimestamp(Date date) {
		Timestamp timestamp = null;
		
		if (date != null) {
			timestamp = new Timestamp(date.getTime());
		}
		
		return timestamp;
	}

	public static Object parse( int type, String value)
	throws ISPACException
	{
		if (value == null || value.length() == 0)
			return null;

		try
		{
			switch(type)
			{
				case Types.BIT:
				case Types.TINYINT:
				case Types.SMALLINT:
						return new Short(Short.parseShort(value));
				case Types.INTEGER:
						return new Integer(Integer.parseInt(value));
				case Types.BIGINT:
						return new Long(Long.parseLong(value));
				case Types.REAL:
						//return new Float(Float.parseFloat(value));
				case Types.FLOAT:
				case Types.DOUBLE:
						return new Double(Double.parseDouble(value));
				case Types.NUMERIC:
				case Types.DECIMAL:
						return new BigDecimal(value);
				case Types.CHAR:
				case Types.VARCHAR:
				case Types.LONGVARCHAR:
                case Types.CLOB:
						return value;
				case Types.DATE:
						return toDate(value, DATEFORMAT);
				case Types.TIME:
						return toDate(value, TIMEFORMAT);
				case Types.TIMESTAMP:
						Date res=null;
						try {
							res= toDate(value, TIMESTAMPFORMAT);
						} catch (Exception e) {
							return toDate(value, DATEFORMAT);
						}
						return res; 
			}
		}
		catch(Exception ie)
		{
			throw new ISPACException( ie);
		}

		return null;
	}


	public static int parseInt(String value)
	throws ISPACException
	{
		if (value == null || value.length() == 0)
			throw new ISPACException("TypeConverter.parseInt(): Imposible convertir la cadena a un entero ");

		try
		{
			return Integer.parseInt(value);
		} catch (NumberFormatException e)
		{
			throw new ISPACException("TypeConverter.parseInt(): Imposible convertir el valor '"+
					value+"' a un entero ",e);
		}
	}

	public static int parseInt(String value, int def) {

		if (value == null || value.length() == 0) {
			return def;
		}

		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	public static int toInt(Integer value, int def) {
		return (value != null ? value.intValue() : def);
	}

	public static Integer parseInteger(String value)
	throws ISPACException
	{
		if (value == null || value.length() == 0)
			throw new ISPACException("TypeConverter.parseInteger(): Imposible convertir la cadena a un entero ");

		try
		{
			return new Integer(value);
		} catch (NumberFormatException e)
		{
			throw new ISPACException("TypeConverter.parseInteger(): Imposible convertir el valor '"+
					value+"' a un entero ",e);
		}
	}

	public static Integer parseInteger(String value, Integer def) throws ISPACException {
		if (StringUtils.isBlank(value)) {
			return def;
		}

		try {
			return new Integer(value);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	public static long parseLong(String value) throws ISPACException {
		
		if (value == null || value.length() == 0) {
			throw new ISPACException("TypeConverter.parseLong(): Imposible convertir la cadena a un entero largo");
		}

		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			throw new ISPACException("TypeConverter.parseLong(): Imposible convertir el valor '" + value+"' a un entero largo",e);
		}
	}

	public static long parseLong(String value, long def) {

		if (value == null || value.length() == 0) {
			return def;
		}

		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			return def;
		}
	}
	
	public static String getString(int type,Object value)
	{
		if (value==null)
			return null;

		switch(type)
		{
		case Types.BIT:
		case Types.TINYINT:
		case Types.SMALLINT:
		case Types.INTEGER:
		case Types.BIGINT:
		case Types.REAL:
		case Types.FLOAT:
		case Types.DOUBLE:
		case Types.NUMERIC:
		case Types.DECIMAL:
			return value.toString();

		case Types.CHAR:
		case Types.VARCHAR:
		case Types.LONGVARCHAR:
			return value.toString();

		case Types.DATE:
			return TypeConverter.toString((Date)value, DATEFORMAT);
			
		case Types.TIME:
			return TypeConverter.toString((Date)value, TIMEFORMAT);
			
		case Types.TIMESTAMP:
			return TypeConverter.toString((Date)value, TIMESTAMPFORMAT);

		default:
			return value.toString();
		}
	}

	public static List parseInt(Collection stringvaluecollection)
	throws ISPACException
	{
		List intlist=new ArrayList();

		Iterator it=stringvaluecollection.iterator();
		while (it.hasNext())
        {
            Integer value = TypeConverter.parseInteger((String)it.next());
            intlist.add(value);
        }
		return intlist;
	}
	
	public static Calendar toCalendar(Date date) {

		Calendar cal = null;
		
		if (date != null) {
			cal = Calendar.getInstance();
			cal.setTime(date);
		}
		
		return cal;
	}
}
