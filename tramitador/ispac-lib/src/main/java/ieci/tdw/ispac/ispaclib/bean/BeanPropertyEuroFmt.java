package ieci.tdw.ispac.ispaclib.bean;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class BeanPropertyEuroFmt extends BeanPropertyFmt {

	public BeanPropertyEuroFmt ()
	{
		super();
		comparator="ieci.tdw.ispac.ispacweb.comparators.NumberComparator";
	}

	public Object format(Object value)
	{
		if (value== null)
		    return "";
		
		// Representación del euro
		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
		// TODO Repasar estos caracteres para que sean en funcion del idioma
		decimalFormatSymbols.setDecimalSeparator(',');
		decimalFormatSymbols.setGroupingSeparator('.');
		NumberFormat formatter = new DecimalFormat("#,###.##", decimalFormatSymbols);
		
		BigDecimal numeric = null;
		
		if (value instanceof BigDecimal) {
			
			numeric = (BigDecimal) value;
		}
		else {
			numeric = new BigDecimal(value.toString());
		}

		return formatter.format(numeric.doubleValue());
	}
	
}