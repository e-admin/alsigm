package ieci.tdw.ispac.api.rule.helper;

import ieci.tdw.ispac.ispaclib.messages.MessagesFormatter;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class RuleHelper {

	/** Nombre del fichero de recursos. */
	private static String BUNDLE_NAME = "ieci.tdw.ispac.api.rule.RuleMessages";

	/**
	 * Devuelve el nombre del archivo de recursos que contiene los mensajes.
	 *
	 * @return El nombre del archivo de recursos mencionado.
	 */
	private static String getMessagesFile() {
		return BUNDLE_NAME;
	}

	/**
	 * Obtiene un mensaje del archivo de recursos en el idioma especificiado.
	 *
	 * @param locale Código de localización con el idioma especificado
	 * @param key Clave a buscar en el archivo de recursos
	 * @return El mensaje en el idoma especificado
	 */
	public static String getMessage(Locale locale, String key) {

		try {
			return ResourceBundle.getBundle(getMessagesFile(), locale).getString(key);
		}
		catch (MissingResourceException mre) {
			return key;
		}
	}

	/**
	 * Obtiene un mensaje del archivo de recursos en el idioma especificiado.
	 *
	 * @param locale Código de localización con el idioma especificado
	 * @param key Clave a buscar en el archivo de recursos
	 * @param params Parámetros para sustituir en el texto
	 * @return El mensaje en el idoma especificado
	 */
	public static String getMessage(Locale locale, String key, Object [] params) {

		try {
			String text = getMessage(locale, key);
			if ((text != null) &&
				(text.length() > 0) &&
				(params != null)) {

				// Establecer los parámetros en el mensaje
				text = MessagesFormatter.format(text, params);
			}

			return text;
		}
		catch (MissingResourceException e) {
			return key;
		}
	}



}
