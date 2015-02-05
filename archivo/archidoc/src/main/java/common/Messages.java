package common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import util.ErrorsTag;

import common.util.ArrayUtils;
import common.util.ListUtils;

public class Messages {
	private static final String DEFAULT_BUNDLE_NAME = "resources.ApplicationResources";

	private static final String DEFAULT_BUNDLE_KEY = "es";

	private static final HashMap mapResources = new HashMap();

	private Messages() {
	}

	public static String getString(String key) {
		return getString(key, null);
	}

	/**
	 * Comprueba si el valor devuelto es igual que la key pasada como parametro
	 * en cuyo caso se devuelve la cadena vacia.
	 * 
	 * @param key
	 * @param locale
	 * @return
	 */
	public static String getDefaultString(String key, Locale locale) {
		String defaultString = getString(key, locale);
		if (StringUtils.isNotEmpty(defaultString)
				&& defaultString.indexOf(key) != -1)
			defaultString = Constants.STRING_EMPTY;
		return defaultString;
	}

	public static String getString(String key, Locale locale) {
		try {
			String keyMap = DEFAULT_BUNDLE_KEY;
			if (locale != null) {
				keyMap = locale.getLanguage();
				if (StringUtils.isEmpty(keyMap))
					keyMap = DEFAULT_BUNDLE_KEY;
			}

			ResourceBundle bundle = (ResourceBundle) mapResources.get(keyMap);
			if (bundle == null) {
				try {
					synchronized (mapResources) {
						if (locale != null)
							bundle = ResourceBundle.getBundle(
									DEFAULT_BUNDLE_NAME, locale);
						else
							bundle = ResourceBundle
									.getBundle(DEFAULT_BUNDLE_NAME);
						mapResources.put(keyMap, bundle);
					}
				} catch (Exception e) {
					return key;
				}
			}

			String ret = bundle.getString(key);

			// Si la cadena está vacía intentar cogerla de los recursos por
			// defecto
			if (StringUtils.isEmpty(ret)) {
				bundle = (ResourceBundle) mapResources.get(DEFAULT_BUNDLE_KEY);
				if (bundle == null) {
					try {
						synchronized (mapResources) {
							bundle = ResourceBundle
									.getBundle(DEFAULT_BUNDLE_NAME);
							mapResources.put(keyMap, bundle);
						}
					} catch (Exception e) {
						return key;
					}
				}

				ret = bundle.getString(key);
			}

			return ret;

		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Permite obtener el texto de un error de bloqueo por marcas a partir de
	 * las marcas activadas, todas las marcas, la key del error y el prefijo de
	 * la key de la propiedad de cada uno de los detalles
	 * 
	 * @param locale
	 *            locale actual
	 * @param marcasActivadas
	 *            array con solo las marcas activadas
	 * @param todasMarcas
	 *            array con todas las marcas posibles
	 * @param messageKey
	 *            key del error
	 * @param prefijoDetallesKey
	 *            prefijo utilizado para generar los detalles de cada una de las
	 *            marcas
	 * @return Texto del error
	 */
	public static String getTextoMessageBloqueoPorMarcas(Locale locale,
			int[] marcasActivadas, int[] todasMarcas, String messageKey,
			String prefijoDetallesKey) {

		List ltMessages = new ArrayList();
		if ((todasMarcas != null) && (marcasActivadas != null)
				&& (todasMarcas.length > 0) && (marcasActivadas.length > 0)) {
			for (int i = 0; i < todasMarcas.length; i++) {
				int marca = todasMarcas[i];
				if (ArrayUtils.contains(marcasActivadas, marca)) {
					ltMessages.add("- "
							+ Messages.getString(prefijoDetallesKey + marca,
									locale));
				}
			}
		}

		StringBuffer causaError = new StringBuffer(Messages.getString(
				messageKey, locale));
		if (!ListUtils.isEmpty(ltMessages)) {
			causaError.append(ErrorsTag.getTextoElementosLista(ltMessages));
		}

		return causaError.toString();
	}
}