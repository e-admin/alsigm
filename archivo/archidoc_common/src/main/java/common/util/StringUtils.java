package common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.Constants;


/**
 * Extensión de la funcionalidad de la clase de Apache Commons.
 */
public class StringUtils extends org.apache.commons.lang.StringUtils
{

	/**
	 * Indica si dos cadenas son iguales.<br/>
	 * Dos cadenas son iguales si:
	 * <ul>
	 * <li>Ambas cadenas son nulas.</li>
	 * <li>La primera es nula y la segunda está vacía.</li>
	 * <li>Ambas cadenas son no nulas e iguales.</li>
	 * </ul>
	 * @param str1 Cadena 1.
	 * @param str2 Cadena 2.
	 * @return true si las cadenas son iguales.
	 */
	public static boolean equalsNullEmpty(String str1, String str2)
	{
		return str1 != null ? str1.equals(str2) : (str2 == null || str2.length() == 0);
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

    public static boolean isEmpty(String string)
    {
    	return string == null || string.trim().equalsIgnoreCase("");
    }

    public static boolean isContenidoVacio(String [] array)
    {
    	if(array!=null && array.length>0)
    	{
    		for(int i=0;i<array.length;i++)
    		{
    			if(!StringUtils.isEmpty(array[i]))
    				return false;
    		}
    	}
    	return true;
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

    public static boolean isNotEmpty(String string)
    {
    	return string != null && !string.trim().equalsIgnoreCase("");
    }

    public static String parseToken(String regex, String delimiter, int numToken){
	    if (regex!=null){
	        String[] tokens = regex.split(delimiter);
	        if (tokens.length>numToken)
	            return tokens[numToken].trim();
	    }
	    return "";
	}

    /**
     * Comprueba si el string pasado por parámetro
     * aplicado a la función trim, es vacio (""). En tal caso devuelve nulo.
     * En caso contrario devuelve el mismo valor
     * pasado por parámetro
     *
     * @param value
     * @return null o value
     */
    public static String changeBlankToNull(String value)
	{
		if(value==null) return null;
    	if(value.trim().equals(""))
			return null;
		return value;
	}

    public static String[] toString(Object[] objects)
    {
    	String[] string=new String[objects.length];
    	for(int i=0;i<objects.length;i++)
    	{
    		string[i]=(String)objects[i];
    	}
    	return string;

    }

	/**
	 * Devuelve una cadena de dos dígitos con la longitud de un número
	 * @param num
	 * @return
	 */
	public static String numDigitos(String num, boolean ignorarCerosIzquierda) {
		if (num == null)
			return "";

		String numToCount = num;
		if (ignorarCerosIzquierda)
		{
			while(numToCount.startsWith("0") && numToCount.length()>1)
				numToCount = numToCount.substring(1);
		}

		if (numToCount.length()>9)
			return String.valueOf(numToCount.length());
		else
			return "0"+String.valueOf(numToCount.length());
	}

	/**
	 * Función de normalización de texto
	 *
	 * Ej: 0101_ 023345asd 0 0000 2 000 --> 03101_ 0523345ASD 010 010 012 010
	 * Ej: 0 00 000 --> 010 010 010
	 *
	 * SQL ORACLE equivalente
	 *

create or replace function norm_txt (val in varchar2) return varchar2 as
  i0       integer;
  i1       integer;
  i2       integer;
  i3       integer;
  dig      varchar2(10) := '1234567890';
  nval     varchar(1024);
  Error    integer;
begin

   Error := -1;
   begin
     nval := '';
     if val is null then return nval; end if;

     i0 := 1;
     loop
       i1 := instr(dig, substr(val, i0, 1));
       if i1 > 0 then
         i2 := i0;
         loop
           exit when i2 > length(val) or instr(dig, substr(val, i2, 1)) = 0;
           i2 := i2+1;
         end loop;
         i2 := i2-1;
         i3 := i0;
         loop
           exit when i3 >= length(val) or substr(val, i3, 1) <> '0';
           i3 := i3+1;
         end loop;
         if instr(dig, substr(val, i3, 1)) = 0 then i3:=i3-1; end if;
         nval := nval||lpad(i2-i3+1, 2, '0')||substr(val, i3, i2-i3+1);
         i0:=i2+1;
       else
         nval := nval||upper(substr(val, i0, 1));
         i0:=i0+1;
       end if;

       exit when i0 > length(val);
     end loop;
     return nval;

   exception
      when others then Error := 1;
      return nval;
   end;

end norm_txt;

	 *
	 * @param texto
	 * @return
	 */
	public static String normalizarTexto(String texto) {

		// Digitos
		String digitos = "1234567890";

		// Cadena resultado
		String res = "";

		// Cadena para almacenar un número
		String num = "";

		try {
			// Separar el texto por dígitos
			StringTokenizer strTok = new StringTokenizer(texto,"1234567890",true);
			while (strTok.hasMoreElements()){
				String tok = strTok.nextToken();

				if ((tok!=null) && (!tok.equals(""))) {
					if  (digitos.indexOf(tok)== -1){
						// El token es una cadena

						// Añadir el número anterior si existe
						if ((num != null) && (!num.equals(""))) {
							res = res + numDigitos(num, true) + eliminarCerosIzquierda(num);
							num = "";
						}

						// Añadir la cadena
						res = res + tok;

					} else {

						// El token es un numero
						num = num + tok;
					}
				}
			}

			// Añadir el último número si existe
			if ((num != null) && (!num.equals(""))) {
				res = res + numDigitos(num, true) + eliminarCerosIzquierda(num);
				num = "";
			}
		} catch (Exception e) {
			res = "";
		}

		return res.toUpperCase();
	}

	public static String eliminarCerosIzquierda (String num)
	{
		if (num != null && !num.equals(""))
		{
			while(num.length()>1 && num.startsWith("0"))
				num = num.substring(1);
		}

		return num;
	}

	public static boolean isTokenIn(String string, String myToken, String delimiter){

		if(StringUtils.isEmpty(string) || StringUtils.isEmpty(myToken))
			return false;

		StringTokenizer strTok=new StringTokenizer(string,delimiter);
		String token=null;
		while(strTok.hasMoreElements())
		{
			token=(String)strTok.nextElement();
			if(token.equals(myToken))
				return true;

		}
		return false;
	}

	public static String getToken(String string, String delimiter, int position)
	{
		StringTokenizer strTok=new StringTokenizer(string,delimiter);
		int i=0;
		String token=null;
		while(strTok.hasMoreElements() && i<position)
		{
			token=(String)strTok.nextElement();
			i++;
		}
		return token;
	}

	public static String insertToken(String string, String token, String delimiter, int position)
	{
		StringTokenizer strTok=new StringTokenizer(string,delimiter);
		int i=1;
		String newToken=null;
		while(strTok.hasMoreElements())
		{
			if(position==i)
			{
				if(newToken==null)
					newToken=token;
				else
					newToken+=delimiter+token;
			}
			else
			{
				if(newToken==null)
					newToken=(String)strTok.nextElement();
				else
					newToken+=delimiter+(String)strTok.nextElement();
			}
			i++;
		}
		return newToken;
	}

	public static String removeToken(String string, String delimiter, int position)
	{
		StringTokenizer strTok=new StringTokenizer(string,delimiter);
		int i=1;
		String newToken=null;
		while(strTok.hasMoreElements())
		{
			if(position!=i)
			{
				if(newToken==null)
					newToken=(String)strTok.nextElement();
				else
					newToken+=delimiter+(String)strTok.nextElement();
			}
			else
			{
				strTok.nextElement();
			}

			i++;
		}
		return newToken;
	}

	public static int numberOfTokens(String string, String delimiter)
	{
		StringTokenizer strTok=new StringTokenizer(string,delimiter);
		return strTok.countTokens();
	}

	public static String getLastToken(String string, String delimiter)
	{
		StringTokenizer strTok=new StringTokenizer(string,delimiter);
		String token=null;
		while(strTok.hasMoreElements())
			token=(String)strTok.nextElement();

		return token;
	}

	public static String addCharacterAtLeft(String string, int length, String character)
    {
		if(!common.util.StringUtils.isEmpty(string))
		{
			while(string.length()<length)
			{
				string=character+string;
			}
		}
        return string;
    }

	/**
	 * Permite codificar un valor para enviar en una URL
	 * @param value Valor a codificar
	 * @return Valor codificado
	 */
	public static String encodeUrlParameterValue(String value){
        try {
			value = URLEncoder.encode(value, Constants.ENCODING_ISO_8859_1);
		} catch (UnsupportedEncodingException e) {}

		return value;
	}

	/**
	 * Permite decodificar un valor de un parámetro procedente de la URL
	 * @param value Valor a decodificar
	 * @return Valor decodificado
	 */
	public static String decodeUrlParameterValue(String value){
        try {
			value = URLDecoder.decode(value, Constants.ENCODING_ISO_8859_1);
		} catch (UnsupportedEncodingException e) {}

		return value;
	}

	public static String getPrimerCaracterNoNumerico(String cadena){
		for(int i=0;i<cadena.length();i++){
			String car=""+cadena.charAt(i);
			if(!StringUtils.isNumeric(car)) return car;
		}
		return null;
	}

	public static String truncarCadena(String cadena, int caracteres, String relleno){
		String retorno = Constants.STRING_EMPTY;

		if(caracteres == 0) return "";

		if(StringUtils.isNotBlank(cadena) && cadena.length()> caracteres){
			retorno = cadena.substring(cadena.length()-caracteres);
			if(StringUtils.isNotEmpty(relleno)) retorno = relleno + retorno;
		}
		else {
			retorno = cadena;
		}

		return retorno;
	}

	public static String replace(String text, String repl, String with) {
		return replace(text, repl, with, -1);
	}

	/**
	 * Metodo sobreescrito para que el reemplazo simple y avanzado funcione correctamente
	 * y distinga entre mayusculas y minusculas.
	 * @param text
	 * @param repl
	 * @param with
	 * @param max
	 * @return
	 */
    public static String replace(String text, String repl, String with, int max) {
        if (text == null || isEmpty(repl) || with == null || max == 0) {
            return text;
        }

        StringBuffer buf = new StringBuffer(text.length());
        int start = 0, end = 0;
        while ((end = text.toLowerCase().indexOf(repl.toLowerCase(), start)) != -1) {
            buf.append(text.substring(start, end)).append(with);
            start = end + repl.length();

            if (--max == 0) {
                break;
            }
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    public static boolean isLetra(String letra){
    	if(!StringUtils.isBlank(letra)){
	    	Pattern mask = Pattern.compile(Constants.REGEXP_LETRAS);
			Matcher matcher = mask.matcher(letra.toUpperCase());

			if (matcher.matches()) {
				return true;
			}
    	}
		return false;
    }

}
