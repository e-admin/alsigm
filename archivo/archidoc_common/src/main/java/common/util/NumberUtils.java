package common.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.StringTokenizer;

public class NumberUtils {

	public static String FORMATO_DOS_DECIMALES = "0.00";
	
	public static String FORMATO_CUATRO_DECIMALES = "#.####";
	
	/**
	 * Recibe un string de numeros separados por coma
	 * Ej: 1,2,3,4
	 * @return el mayor numero
	 */
	public static int getNumeroMayor(String string){
		
		StringTokenizer strTok=new StringTokenizer(string,",");
		String numeroMayor=null;
		String token=null;
		while(strTok.hasMoreElements())
		{
			token=(String)strTok.nextElement();
			if(numeroMayor==null)
				numeroMayor=token;
			else if(Integer.parseInt(token)>Integer.parseInt(numeroMayor))
				numeroMayor=token;
		}
		return Integer.parseInt(numeroMayor);
		
	}

	/**
	 * Divide un número Float entre un número entero.
	 * @param dividendo
	 * @param divisor
	 * @return
	 */
	public static float dividirNumeroFloat(float dividendo, int divisor) {
		Float dividendoFloat = new Float(dividendo);
    	float cociente = dividendoFloat.floatValue() / divisor;
    	return cociente;		
	}
	
	public static String formatea(float numero, String pattern) {
		DecimalFormat df = new DecimalFormat(pattern,getDecimalFormatSymbols());    	
    	return df.format(numero);
	}
	
	public static String formatea(double numero, String pattern) {
		DecimalFormat df = new DecimalFormat(pattern,getDecimalFormatSymbols());
    	return df.format(numero);
	}
	
	private static DecimalFormatSymbols getDecimalFormatSymbols() {
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		dfs.setMonetaryDecimalSeparator('.');
		return dfs;
	}
	
	public static boolean isShortInteger(String number){
		if(!StringUtils.isNumeric(number)) return false;
		
		int num=-1;
		try{ num=Integer.parseInt(number); }
		catch(Exception e){ return false; }
		
		short numShort=-1;
		try{ numShort=new Integer(num).shortValue(); }
		catch(Exception e){ return false;	}
		
		if(num>0 && numShort<0) return false;

		return true;
	}
	
	public static boolean isInteger(String number){
		if(!StringUtils.isNumeric(number)) return false;
		
		try{ Integer.parseInt(number); }
		catch(Exception e){ return false; }

		return true;
	}
	
	public static boolean isNumeroMayorCero(String number){
		if(!StringUtils.isNumeric(number)) return false;
		
		int num=-1;
		try{ num=Integer.parseInt(number); }
		catch(Exception e){ return false; }
		
		if(num>0) return true; 

		return false;
	}
	
}
