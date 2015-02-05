package ieci.tdw.applets.applauncher;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/** Clase para obtener los recursos del Applet. */
final class AppLauncherMessages {
	
	private AppLauncherMessages() {
		// No permitimos la instanciacion
	}

	/** Nombre del fichero de recursos. */
	private static final String BUNDLE_NAME =
		"ieci.tdw.applets.applauncher.resources.AppLauncherAppletMessages"; //$NON-NLS-1$

	/** Recursos. */
	private static final ResourceBundle RESOURCE_BUNDLE =
		ResourceBundle.getBundle(BUNDLE_NAME);

    private static final String PREFIX = "{"; //$NON-NLS-1$
    private static final String SUFFIX = "}"; //$NON-NLS-1$

	/** Obtiene el texto del recurso especificado.
	 * @param key Clave del texto.
	 * @return Texto. */
	static String getString(final String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		}
		catch (final MissingResourceException e) {
			return key;
		}
	}

	/** Obtiene el texto del recurso especificado.
	 * @param key Clave del texto.
	 * @param params Parámetros para sustituir en el texto.
	 * @return Texto. */
	static String getString(final String key, final Object [] params) {
		try {
			String text = RESOURCE_BUNDLE.getString(key);
			if ((text != null) && (text.length() > 0)) {
				text = format(text, params);
			}
			return text;
		}
		catch (final MissingResourceException e) {
			return key;
		}
	}

    private static String replace(final String text, final String param, final String value) {
		final int pos = text.indexOf(param);
		if (pos < 0) {
			return text;
		}
		return new StringBuffer()
			.append(text.substring(0, pos))
			.append(value)
			.append(text.substring(pos + param.length(), text.length()))
			.toString();
	}

	private static String format(final String txt, final Object params[]) {
		String text = txt;
		for (int i = 0; i < params.length; i++) {
			final Object obj = params[i];
			if (obj != null) {
				text = replace(text, PREFIX + i + SUFFIX, obj.toString());
			}
		}
		return text;
	}

}
