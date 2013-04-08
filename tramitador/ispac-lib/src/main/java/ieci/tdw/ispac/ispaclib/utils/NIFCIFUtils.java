package ieci.tdw.ispac.ispaclib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase para comprobar si una cadena es un NIF o un CIF.
 * 
 */
public class NIFCIFUtils {

    private static final String NIF_PATTERN_STR = "^\\d{7,8}[A-Z]$";
    private static final String CIF_PATTERN_STR =  "(^[A-Z]\\d{7,8}$)|(^[A-Z]\\d{7}[A-Z]$)";
    
    private static final Pattern NIF_PATTERN = Pattern.compile(NIF_PATTERN_STR);
    private static final Pattern CIF_PATTERN = Pattern.compile(CIF_PATTERN_STR);

    
    /**
     * Indica si la cadena es un NIF válido.
     * @param value Cadena a comprobar.
     * @return true si 'value' es un NIF, false en caso contrario
     */
    public static boolean isNif(String value){
        Matcher matcher = NIF_PATTERN.matcher(value);
        return matcher.matches();
    }

    /**
     * Indica si la cadena es un CIF válido.
     * @param value Cadena a comprobar
     * @return true si 'value' es un CIF, false en caso contrario
     */
    public static boolean isCif(String value){
        Matcher matcher =  CIF_PATTERN.matcher(value);
        return matcher.matches();
    }

    /**
     * Indica si la cadena es un NIF o un CIF válido.
     * @param value Cadena a comprobar
     * @return true si 'value' es un CIF o un NIF, false en caso contrario
     */
    public static boolean isNifOrCif(String value){
        return (isNif(value) || isCif(value)); 
    }
}
