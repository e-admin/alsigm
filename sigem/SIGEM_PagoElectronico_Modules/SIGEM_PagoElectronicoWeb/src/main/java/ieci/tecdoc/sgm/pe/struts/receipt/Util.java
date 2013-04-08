package ieci.tecdoc.sgm.pe.struts.receipt;

import java.text.DecimalFormat;

public class Util {

	public static String formatearImporte(String pcImporte){
		int enCentimos = Integer.valueOf(pcImporte).intValue();
		double enEuros = enCentimos / 100.0;
		DecimalFormat format =  new DecimalFormat("#######.00");
		return format.format(enEuros);
	}
}
