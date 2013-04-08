package ieci.tdw.ispac.ispaclib.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;



/**
 * Extensión de la funcionalidad de la clase de Apache Commons.
 */
public class StringUtils extends org.apache.commons.lang.StringUtils
{
	
	public static final String NEWLINE = System.getProperty("line.separator");
	
	
	/**
	 * Indica si dos cadenas son iguales.<br/>
	 * Dos cadenas son iguales si:
	 * <ul>
	 * <li>Ambas cadenas son nulas.</li>
	 * <li>Una es nula y la otra está vacía.</li>
	 * <li>Ambas cadenas son no nulas e iguales.</li>
	 * </ul>
	 * @param str1 Cadena 1.
	 * @param str2 Cadena 2.
	 * @return true si las cadenas son iguales.
	 */
	public static boolean equalsNullEmpty(String str1, String str2)
	{
		//return str1 != null ? str1.equals(str2) : (str2 == null || str2.length() == 0);
		if (str1 == null && str2  == null)
			return true;
		if (str1 == null && str2.length() == 0)
			return true;
		if (str2 == null && str1.length() == 0)
			return true;
		return equals(str1, str2);
	}

    public static String defaultIfBlank(String str, String defaultStr) {
        return StringUtils.isBlank(str) ? defaultStr : str;
    }

	/**
	 * 
	 * @param value
	 * @return Cierto si es un entero falso en caso contrario
	 */
	public static boolean isInteger (String value){

		boolean isInteger = true;
		
		try {
			Integer.parseInt(value);
		} catch (Exception e) {
			isInteger = false;
		}

		return isInteger;
	}

	/**
	 * Indica si una cadena es un numérico double válido.
	 * @param value
	 * @return True si es un double, false en caso contrario.
	 */
	public static boolean isDouble(String value) {

		boolean isDouble = true;
		
		try {
			Double.parseDouble(value);
		} catch (Exception e) {
			isDouble = false;
		}

		return isDouble;
	}

	/**
	 * Indica si un array está vacío o es nulo.
	 * @param array Array de cadenas.
	 * @retur true si el array está vacío o es nulo.
	 */
    public static boolean isEmpty(String [] array)
    {
    	return array == null || array.length == 0;
    }

    
	/**
	 * Indica si un array tiene valores.
	 * @param array Array de cadenas.
	 * @retur true si el array tiene valores.
	 */
    public static boolean isNotEmpty(String [] array)
    {
    	return array != null && array.length > 0;
    }

    public static String nullToEmpty(String str)
    {
        return str != null ? str : "";
    }

    public static String parseToken(String regex, String delimiter, int numToken){
	    if (regex!=null){
	        String[] tokens = regex.split(delimiter);
	        if (tokens.length>numToken)
	            return tokens[numToken].trim();
	    }
	    return "";
	}

    public static String escapeJava(String str) {
    	return StringEscapeUtils.escapeJava(str);
    }

    public static String unescapeJava(String str) {
    	return StringEscapeUtils.unescapeJava(str);
    }

    public static String escapeJavaScript(String str) {
    	return StringEscapeUtils.escapeJavaScript(str);
    }

    public static String unescapeJavaScript(String str) {
    	return StringEscapeUtils.unescapeJavaScript(str);
    }

    public static String escapeXml(String str) {
    	return StringEscapeUtils.escapeXml(str);
    }

    public static String unescapeXml(String str) {
    	return StringEscapeUtils.unescapeXml(str);
    }
    
    public static String escapeHtml(String str) {
    	return StringEscapeUtils.escapeHtml(str);
    }

    public static String unescapeHtml(String str) {
    	return StringEscapeUtils.unescapeHtml(str);
    }

    public static String escapeSql(String str) {
    	return StringEscapeUtils.escapeSql(str);
    }

    public static String escapeFile(String str) {
    	String newstr = null;
    	
    	if (str != null) {

    		str = str.trim();
    		newstr = "";
    		
    		for (int i = 0; i < str.length(); i++) {
    			char ch = str.charAt(i);
    			
    			if ( (ch >= 'A' && ch <= 'Z') 
    					|| (ch >= 'a' && ch <= 'z')
    					|| (ch >= '0' && ch <= '9')
    					|| (ch == '-')
    					|| (ch == '_')
    					|| (ch == '.') ) {
    				newstr += ch;
    			} else {
    				newstr += Integer.toHexString(ch).toUpperCase();
    			}
    		}
    	}
    	
    	return newstr;
    }
    
    public static String replaceVariables(String str, Map variables) {
    	
        StringBuffer value = new StringBuffer();

        if (StringUtils.isNotBlank(str)) {
        	
            Pattern pattern = Pattern.compile("\\$\\{[^}]*}");
            Matcher matcher = pattern.matcher(str);
            
            while (matcher.find()) { 
            	String var = matcher.group();
            	String key = var.substring(2, var.length()-1);
            	
            	Object varValue = variables.get(key);
            	if (varValue == null) {
            		varValue = "";
            	}
            	
                matcher.appendReplacement(value, varValue.toString());
            }
            
            matcher.appendTail(value);
        }

        return value.toString();
    }
}
