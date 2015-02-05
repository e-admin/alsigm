package ieci.tdw.ispac.ispaccatalog.messages;

import ieci.tdw.ispac.ispaclib.messages.MessagesFormatter;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	/** Nombre del fichero de recursos. */
	private static final String BUNDLE_NAME = 
		"ieci.tdw.ispac.ispaccatalog.resources.ApplicationResources";
	
	/** Recursos. */
	//private static final Map RESOURCE_BUNDLES = new HashMap();
	
	/**
	 * Obtiene el ResourceBundle del idioma.
	 * @param language
	 * @return
	 */
	/*
	private static ResourceBundle getResourceBundle(String language) {
		
		ResourceBundle resourceBundle = (ResourceBundle) RESOURCE_BUNDLES.get(language);
		if (resourceBundle == null) {
			
			resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale(language));
			RESOURCE_BUNDLES.put(language, resourceBundle);
		}
		
		return resourceBundle;
	}
	*/
	
	/**
	 * Obtiene el texto del recurso especificado.
	 * @param language Idioma
	 * @param key Clave del texto.
	 * @return Texto.
	 */
	public static String getString(String language, String key) {
		try {
			//return getResourceBundle(language).getString(key);
			return ResourceBundle.getBundle(BUNDLE_NAME, new Locale(language)).getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}
	
	/**
	 * Obtiene el texto del recurso especificado.
	 * @param language Idioma
	 * @param key Clave del texto.
	 * @param params Parámetros para sustituir en el texto.
	 * @return Texto.
	 */
	public static String getString(String language, String key, Object [] params) {
		try {
			//String text = getResourceBundle(language).getString(key);
			String text = getString(language, key);
			if ((text != null) && (text.length() > 0)) {
				text = MessagesFormatter.format(text, params);
			}
			return text;
		} catch (MissingResourceException e) {
			return key;
		}
	}

}