package ieci.tdw.ispac.ispaclib.validations;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

/**
 * Clase que valida Rangos
 * @author 66423658
 *
 */

public class RangoValidator implements IValidator {

	/**
	 * Logger de la clase.
	 */
    private static final Logger logger = Logger.getLogger(RangoValidator.class);
    
	/**
     * Expresion regular para validar ENTEROS
     */
    protected final static String ENTEROS= "^((-)?([:digit:]+){0,10})$";

    
   /**
    * Valida si el dato es de tipo entero
    * @param num
    * @return
    */
    public static boolean validateEntero(String num)  {
      

    	boolean result = true;

        try {
			RE re = new RE(ENTEROS);

			if (!re.match(num)) {
			    result = false;
			} 
	
		} catch (RESyntaxException e) {
			
			return false;
		} 
        

        return result;
    }
    /**
     * Valida si el dato es de tipo decimal
     * @param num Parametro con el valor a validar
     * @return
     */
    
    public static boolean validateDecimal(String num, Locale locale)  {
      
        try {
        	
        	NumberFormat formatter = new DecimalFormat( "#,###.############", 
        			new DecimalFormatSymbols(locale) ); 

        	formatter.parse(num);
	
		} catch (ParseException e) {
			
			return false;
		} 
        

        return true;
    }
    
    public static double transformDecimalToDouble(String num, Locale locale) {
    	
    	Number number = null;

    	try {
    		NumberFormat formatter = DecimalFormat.getInstance(locale);
			number = formatter.parse(num);
		} catch (ParseException e) {
			logger.debug("No se ha podido parsear el número: [" + num + "]", e);
			number = new Double(0);
		}
		
    	return number.doubleValue();
    }
    
	public  boolean validate(Object objeto)  {
		return false;
	}
	
}
