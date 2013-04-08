package es.ieci.tecdoc.fwktd.applets.scan.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class JsaneConfig {
	private static final String BUNDLE_NAME = "resources.jsaneConfig";
	private static final ResourceBundle resources = ResourceBundle.getBundle(BUNDLE_NAME);
	
	public JsaneConfig() {}

	/**
	 * Obtiene el array de valores del recurso.
	 * @param key Prefijo de la clave del recurso.
	 * @return Array de valores del recurso.
	 */
	public static String[] getString(String keyPrefix) {
	    String[] result;
	    Enumeration<String> keys = resources.getKeys();
	    ArrayList<String> temp = new ArrayList<String>();

	    for (Enumeration<String> e = keys; keys.hasMoreElements();) {
	        String key = e.nextElement();
	        if (key.startsWith(keyPrefix)) {
	                temp.add(key);
	        }
	    }
	    result = new String[temp.size()];

	    for (int i = 0; i < temp.size(); i++) {
	        result[i] = resources.getString(temp.get(i));
	    }

	    return result;
	}
}

