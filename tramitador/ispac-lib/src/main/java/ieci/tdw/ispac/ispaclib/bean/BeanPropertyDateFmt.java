package ieci.tdw.ispac.ispaclib.bean;

import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.util.Date;

public class BeanPropertyDateFmt extends BeanPropertyFmt {

	public BeanPropertyDateFmt ()
	{
		super();
		comparator="ieci.tdw.ispac.ispacweb.comparators.DateComparator";
	}

	public Object format(Object value)
	{
		if (value== null)
		    return "";

		if (getFormat()== null || getFormat().equals(""))
		    return value.toString();

		return TypeConverter.toString((Date)value,getFormat());

		/*
		TimeZone tz = TimeZone.getDefault();
		DateFormat sdf = new SimpleDateFormat(getFormat());
		sdf.setTimeZone(tz);
		return sdf.format((Date)value);
		*/
	}
	
}