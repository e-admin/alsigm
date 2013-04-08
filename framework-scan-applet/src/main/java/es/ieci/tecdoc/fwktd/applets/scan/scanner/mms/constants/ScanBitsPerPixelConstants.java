package es.ieci.tecdoc.fwktd.applets.scan.scanner.mms.constants;

import uk.co.mmscomputing.device.twain.TwainConstants;

/**
 * Constantes para indicar los bits por pixel
 */
public class ScanBitsPerPixelConstants {

	/**
	 * Escaneo en blanco y negro. Valor: {@value}
	 */
	public static final int BLACK_WHITE = TwainConstants.TWPT_BW;

	/**
	 * Escaneo en escala de grises. Valor: {@value}
	 */
	public static final int GRAYS = TwainConstants.TWPT_GRAY;

	/**
	 * Escaneo en color. Valor: {@value}
	 */
	public static final int COLOR = TwainConstants.TWPT_RGB;

	 /**
	  * Permite obtener la descripcion de bpp
	  * @param bpp bits per pixel
	  * @return descripcion de bpp
	  */
	 public static String getBitsPerPixelDescription(int bpp){
		 String description = null;
		 switch(bpp){
		 case BLACK_WHITE: description = "1 - BW"; break;
		 case GRAYS: description = "8 - GRAYS"; break;
		 case COLOR: description = "24 - COLOR"; break;
		 }
		 return description;
	 }
}
