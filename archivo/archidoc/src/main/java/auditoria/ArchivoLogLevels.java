package auditoria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import auditoria.vos.ArchivoLogLevel;

import common.Messages;

/**
 * Clase que encapsula los niveles de criticidad para la aplicación.
 */
public class ArchivoLogLevels {

	/** Niveles de LOG */
	public final static int LOGLEVEL_NONE = Integer.MIN_VALUE;
	public final static int LOGLEVEL_VERY_LOW = 50;
	public final static int LOGLEVEL_LOW = 100;
	public final static int LOGLEVEL_MEDIUM = 150;
	public final static int LOGLEVEL_HIGH = 200;

	/** Nombre de Niveles de LOG */
	// public final static String LOGLEVEL_NONE_NAME =
	// Messages.getString("archigest.archivo.log.level.none");
	// public final static String LOGLEVEL_VERY_LOW_NAME =
	// Messages.getString("archigest.archivo.log.level.very.low");
	// public final static String LOGLEVEL_LOW_NAME =
	// Messages.getString("archigest.archivo.log.level.low");
	// public final static String LOGLEVEL_MEDIUM_NAME =
	// Messages.getString("archigest.archivo.log.level.medium");
	// public final static String LOGLEVEL_HIGH_NAME =
	// Messages.getString("archigest.archivo.log.level.high");

	/** Asociacion level/nombre */
	private static HashMap logLevelNames = new HashMap();

	static {
		/*
		 * logLevelNames = new HashMap(); logLevelNames.put(new
		 * Integer(LOGLEVEL_NONE),LOGLEVEL_NONE_NAME); logLevelNames.put(new
		 * Integer(LOGLEVEL_VERY_LOW),LOGLEVEL_VERY_LOW_NAME);
		 * logLevelNames.put(new Integer(LOGLEVEL_LOW),LOGLEVEL_LOW_NAME);
		 * logLevelNames.put(new Integer(LOGLEVEL_MEDIUM),LOGLEVEL_MEDIUM_NAME);
		 * logLevelNames.put(new Integer(LOGLEVEL_HIGH),LOGLEVEL_HIGH_NAME);
		 */
	}

	private static void loadLogLevelNames(Locale locale) {
		logLevelNames.put(new Integer(LOGLEVEL_NONE),
				Messages.getString("archigest.archivo.log.level.none", locale));
		logLevelNames.put(new Integer(LOGLEVEL_VERY_LOW), Messages.getString(
				"archigest.archivo.log.level.very.low", locale));
		logLevelNames.put(new Integer(LOGLEVEL_LOW),
				Messages.getString("archigest.archivo.log.level.low", locale));
		logLevelNames.put(new Integer(LOGLEVEL_MEDIUM), Messages.getString(
				"archigest.archivo.log.level.medium", locale));
		logLevelNames.put(new Integer(LOGLEVEL_HIGH),
				Messages.getString("archigest.archivo.log.level.high", locale));
	}

	/**
	 * Obtiene el nombre de un nivel de debug
	 * 
	 * @param level
	 *            Nivel de debug
	 * @return Nombre del nivel de debug
	 */
	public static String getLogLevelName(Locale locale, int level) {
		if (logLevelNames.size() == 0) {
			loadLogLevelNames(locale);
		}

		return (String) logLevelNames.get(new Integer(level));
	}

	/**
	 * Obtiene un listado de los niveles de log existentes
	 * 
	 * @return Listado de los niveles de log{@link ArchivoLogLevel} existentes
	 */
	public static Collection getLogLevelNames(Locale locale) {
		if (logLevelNames.size() == 0) {
			loadLogLevelNames(locale);
		}

		Integer i = null;
		String name = null;
		ArchivoLogLevel ll = null;
		ArrayList result = new ArrayList();
		Iterator it = logLevelNames.keySet().iterator();

		while (it.hasNext()) {
			i = (Integer) it.next();

			name = (String) logLevelNames.get(i);
			ll = new ArchivoLogLevel();
			ll.setId(i.intValue());
			ll.setName(name);

			result.add(ll);
		}

		Collections.sort(result);
		return result;
	}
}
