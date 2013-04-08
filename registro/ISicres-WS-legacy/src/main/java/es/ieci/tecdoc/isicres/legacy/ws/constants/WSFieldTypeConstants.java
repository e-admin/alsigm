/**
 * 
 */
package es.ieci.tecdoc.isicres.legacy.ws.constants;

import ieci.tecdoc.core.types.IeciTdType;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
public class WSFieldTypeConstants {

	/**
	 * IDOC_FLD_TYPE_SHORT_STR (1)
	 * 
	 * IDOC_FLD_TYPE_LONG_STR (2) 
	 * 
	 * IDOC_FLD_TYPE_SHORT_INT (3)
	 * 
	 * IDOC_FLD_TYPE_LONG_INT (4) 
	 * 
	 * IDOC_FLD_TYPE_SHORT_DEC (5)
	 * 
	 * IDOC_FLD_TYPE_LONG_DEC (6) 
	 * 
	 * IDOC_FLD_TYPE_DATE (7) 
	 * 
	 * IDOC_FLD_TYPE_TIME (8)
	 * 
	 * IDOC_FLD_TYPE_DATE_TIME (9)
	 */

	public static final int SHORT_TEXT = 1;
	public static final int LONG_TEXT = 2;
	public static final int SHORT_INTEGER = 3;
	public static final int LONG_INTEGER = 4;
	public static final int SHORT_DECIMAL = 5;
	public static final int LONG_DECIMAL = 6;
	public static final int DATE = 7;
	public static final int TIME = 8;
	public static final int DATE_TIME = 9;
	
	
	public static final String TYPE_CHAR = "char";// : Tipo carácter
	public static final String TYPE_NUMBER = "number"; // : Tipo entero
	public static final String TYPE_DECIMAL = "decimal"; // : Tipo decimal
	public static final String TYPE_DATE = "date";// : Tipo Fecha
	public static final String TYPE_DATETIME = "datetime";// : Tipo Fecha-Hora
	public static final String TYPE_LONG = "long";// : Tipo long

	
}
