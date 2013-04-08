package es.ieci.tecdoc.fwktd.applets.scan.utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	/** Nombre del fichero de recursos. */
	private static final String BUNDLE_NAME = "resources.MessagesResources";

	/** Recursos. */
	private static final ResourceBundle resources = ResourceBundle.getBundle(BUNDLE_NAME);

    private static final String PREFIX = "{";
    private static final String SUFFIX = "}";

	
	/**
	 * Constructor.
	 *
	 */
	public Messages() {}

	/**
	 * Obtiene el valor del recurso.
	 * @param key Clave del recurso.
	 * @return Valor del recurso.
	 */
	public static String getString(String key) {
		if ((key == null) || (key.trim().length() == 0)) {
			return '!' + key + '!';
		}
		
		try {
			return resources.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	/**
	 * Obtiene el valor del recurso.
	 * @param key Clave del recurso.
	 * @param params Parámetros.
	 * @return Valor del recurso.
	 */
	public static String getString(String key, Object params[]) {
		String text = getString(key);
		
		for (int i = 0; i < params.length; i++) {
			Object obj = params[i];
			if (obj != null) {
				text = replace(text, PREFIX + String.valueOf(i) + SUFFIX, obj.toString());
			}
		}
		return text;
	}

	/**
	 * Reemplaza una cadena por un valor.
	 * @param text Cadena de texto.
	 * @param param Parámetro a sustituir.
	 * @param value Valor de sustitución.
	 * @return Cadena final.
	 */
    private static String replace(String text, String param, String value) {
		int pos = text.indexOf(param);
		if (pos < 0) {
			return text;
		} else {
			return new StringBuffer().append(text.substring(0, pos)).append(value).append(text.substring(pos + param.length(), text.length())).toString();
		}
	}
}

