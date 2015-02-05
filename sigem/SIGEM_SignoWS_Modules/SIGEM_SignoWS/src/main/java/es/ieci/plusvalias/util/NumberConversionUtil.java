package es.ieci.plusvalias.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author angel_castro@ieci.es - 23/07/2010
 */
public class NumberConversionUtil {

	private static final String NUMBER_PATTERN = "###,###,###.##";
	private static final String NUMBER_PATTERN2 = "#########.##";

	/**
	 * Pasa de double a String con comas como decimales y marcando millares con puntos
	*/
	public static String formatNumber(double number) {
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setDecimalSeparator(',');
		DecimalFormat format = new DecimalFormat(NUMBER_PATTERN, simbolos);
		return format.format(number);
	}
	
	/**
	 * Pasa de double a String con comas como decimales sin marcar millares
	*/
	public static String parseDouble2(double number){
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setDecimalSeparator(',');
		DecimalFormat format = new DecimalFormat(NUMBER_PATTERN2, simbolos);
		return format.format(number);
	}
	
	/**
	 * Pasa de String a double con comas como decimales sin marcar millares
	*/
	public static double parseNumber(String number) throws ParseException{
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setDecimalSeparator(',');
		DecimalFormat format = new DecimalFormat(NUMBER_PATTERN, simbolos);
		return format.parse(number).doubleValue();
	}
	
	/**
	 * Pasa de String a int
	*/
	public static int parseNumberInt(String number) throws ParseException{
		int result = 0;
		if(number != null){
			result = Integer.parseInt(number);
		}
		return result;
	}
	
	/**
	 * Pasa de String a Date con formato dd/MM/yyyy
	*/
	public static Date parseDate(String fechaStr) throws Exception{
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
		return formatoDeFecha.parse(fechaStr);
	}
	
	/**
	 * Pasa de Date a String con formato dd/MM/yyyy
	*/
	public static String formatDate(Date fecha) throws Exception{
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
		return formatoDeFecha.format(fecha);
	}
	
	/**
	 * Comprueba si el procedimiento es válido
	*/
	public static boolean procedureIsValid(String code){
		boolean valid = false;
		int pos = Arrays.binarySearch(Constants.PROCEDURES_CODE, code);
		if(pos >= 0){
			valid = true;
		}
		return valid;
	}
}
