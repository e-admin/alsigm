/*
 * Created on 27-may-2005
 *
 */
package util;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Clase para la creacion de una lista de parametros.
 */
public class ParamsSet {
	HashMap params;

	public ParamsSet() {
		params = new HashMap();
	}

	/**
	 * Aniade un parametro a la lista de parametros
	 * 
	 * @param nameParam
	 *            Parametros a añadir
	 * @param value
	 *            Valor del parametro
	 * @return El conjunto de parametros
	 */
	public ParamsSet append(String nameParam, String value) {
		params.put(nameParam, value);
		return this;
	}

	public String toString() {
		StringBuffer ret = new StringBuffer();
		Iterator iter = params.keySet().iterator();
		String nameParam, valueParam;
		if (iter.hasNext()) {
			nameParam = (String) iter.next();
			valueParam = (String) params.get(nameParam);
			ret.append(nameParam).append("=").append(valueParam);
		}
		for (; iter.hasNext();) {
			nameParam = (String) iter.next();
			valueParam = (String) params.get(nameParam);
			ret.append("&").append(nameParam).append("=").append(valueParam);
		}
		return ret.toString();
	}

}