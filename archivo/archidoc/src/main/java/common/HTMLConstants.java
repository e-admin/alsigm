/**
 *
 */
package common;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class HTMLConstants {

	private static String TAG_INICIO = "<";
	private static String TAG_FIN = ">";
	private static String BARRA = "/";

	public static String getStartTag(String tag) {
		return TAG_INICIO + tag + TAG_FIN;
	}

	public static String getEndTag(String tag) {
		return TAG_INICIO + BARRA + tag + TAG_FIN;
	}

	public static String getClosedTag(String tag) {
		return TAG_INICIO + tag + BARRA + TAG_FIN;
	}

	public static final String TAG_DIV = "div";

}
