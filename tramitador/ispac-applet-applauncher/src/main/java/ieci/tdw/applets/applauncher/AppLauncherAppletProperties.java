package ieci.tdw.applets.applauncher;

import java.io.File;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

final class AppLauncherAppletProperties {

	private final Preferences prefs;

	/**
	 * Constructor que inicializa las preferencias.
	 */
	AppLauncherAppletProperties() {
		// Almacena las propiedades en el directorio Home
		this.prefs = Preferences.userRoot().node(this.getClass().getName());
	}

	/**
	 * Obtiene el valor de una propiedad.
	 * @param key	Clave de la propiedad
	 * @return	Valor encontrado o null en caso contrario
	 */
	String getStringProperty(final String key) {
		return this.prefs.get(key, null);
	}

	/**
	 * Almacena una propiedad.
	 * @param key
	 * @param value
	 */
	void putStringProperty(final String key, final String value) {
		this.prefs.put(key, value);
	}

	/**
	 * Verifica si una ruta almacenada en una propiedad es un fichero existente en disco.
	 * En caso contrario suprime la propiedad
	 * @param path
	 * @return
	 */
	boolean checkPropertyPath(final String key) {
		if (!checkAppPath(getStringProperty(key))) {
			this.prefs.remove(key);
			return false;
		}

		return true;
	}

	/**
	 * Obtiene todas las claves del fichero de propiedades
	 * @return Claves del fichero de propiedades
	 * @throws BackingStoreException
	 */
	String[] getKeys() throws BackingStoreException {
		return this.prefs.keys();
	}

	/**
	 * Vac&iacute;a el fichero de propiedades.
	 * @throws BackingStoreException
	 */
	void clear() throws BackingStoreException {
		this.prefs.clear();
	}

	/**
	 * Obtiene el listado de propiedades almacenadas en el fichero.
	 * @return	Listado de claves y valores
	 */
	String listProperties() {
		String properties = ""; //$NON-NLS-1$
		try {
			for (final String key : this.prefs.keys()) {
				properties += this.prefs.get(key, null) + "\n"; //$NON-NLS-1$
			}
		}
		catch (final BackingStoreException e) {
			// Lo ignoramos ya que no deberia de darse
		}

		return properties;
	}

	/**
	 * Verifica si una ruta existe en el disco.
	 * @param appPath
	 * @return
	 */
	static boolean checkAppPath(final String appPath) {
		if (appPath == null) {
			return false;
		}
		return new File(appPath).isFile();
	}
}
