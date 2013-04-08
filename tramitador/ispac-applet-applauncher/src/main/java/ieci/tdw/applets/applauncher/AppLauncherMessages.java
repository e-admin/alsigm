package ieci.tdw.applets.applauncher;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Clase para obtener los recursos del applet.
 *
 */
public class AppLauncherMessages {

	/** Nombre del fichero de recursos. */
	private static final String BUNDLE_NAME = 
		"ieci.tdw.applets.applauncher.resources.AppLauncherAppletMessages";

	/** Recursos. */
	private static final ResourceBundle RESOURCE_BUNDLE = 
		ResourceBundle.getBundle(BUNDLE_NAME);

    public static final String PREFIX = "{";
    public static final String SUFFIX = "}";

    
	/**
	 * Obtiene el texto del recurso especificado.
	 * @param key Clave del texto.
	 * @return Texto.
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Obtiene el texto del recurso especificado.
	 * @param key Clave del texto.
	 * @param params Parámetros para sustituir en el texto.
	 * @return Texto.
	 */
	public static String getString(String key, Object [] params) {
		try {
			String text = RESOURCE_BUNDLE.getString(key);
			if ((text != null) && (text.length() > 0)) {
				text = format(text, params);
			}
			return text;
		} catch (MissingResourceException e) {
			return key;
		}
	}

    public static String replace(String text, String param, String value) {
		int pos = text.indexOf(param);
		if (pos < 0) {
			return text;
		} else {
			return new StringBuffer()
				.append(text.substring(0, pos))
				.append(value)
				.append(text.substring(pos + param.length(), text.length()))
				.toString();
		}
	}

	public static String format(String text, Object params[]) {
		for (int i = 0; i < params.length; i++) {
			Object obj = params[i];
			if (obj != null) {
				text = replace(text, "{" + String.valueOf(i) + "}", 
						obj.toString());
			}
		}

		return text;
	}

}
