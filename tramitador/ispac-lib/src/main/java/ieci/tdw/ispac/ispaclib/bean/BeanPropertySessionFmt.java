/*
 * Created on 01-jul-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispaclib.bean;

import java.text.SimpleDateFormat;

/**
 * @author marisa
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class BeanPropertySessionFmt extends BeanPropertyFmt {
	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt#format(java.lang.Object)
	 */
	public static final String TOHHMMSS= "TOHHMMSS";

	public BeanPropertySessionFmt ()
	{
	}

	public Object format(Object value) {
		String result = null;
		if (getFormat().equals(TOHHMMSS))
			result = toHoursMinutesAndSeconds ((Long)value);
		else
			result = timestampToDate ((Long)value);
		return result;
	}

	/**
	 * Convierte un timestamp en un formato fecha
	 * @param timestamp
	 * @return fecha
	 */
	public String timestampToDate (Long timestamp)
	{
		java.util.Date date = new java.util.Date (timestamp.longValue());
		SimpleDateFormat formatter = new SimpleDateFormat (getFormat());
		return formatter.format(date);
	}

	public String toHoursMinutesAndSeconds (Long time)
	{
		long t = time.longValue() / 1000;
		long[] result = {0,0,0};
		for (int i=0; i<2; i++)
		{
			long div = t / 60;
			long mod = t % 60;
			result[i] = mod;
			if (div < 60 || i == 1)
			{
				result [i+1] = div;
				break;
			}
			t = div;
		}
		return result[2] + " h " + result[1] + "min " +  result[0] + " sg";
	}
}
