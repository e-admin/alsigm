package common.util;

import java.util.Iterator;
import java.util.Map;

import common.RequestUtil;

public class ClientInvocationUtils {

	/**
	 * Permite eliminar de la url de la invocación todos los parámetros que son
	 * vacíos y que por tanto no aportan nada a la url
	 * 
	 * @path
	 * @uriParams parametros de la invocacion
	 * @return Url de la invocacion
	 */
	public static String getInvocationURIWithoutEmptyParameters(String path,
			Map uriParams) {
		String invocationPath;
		for (Iterator it = uriParams.entrySet().iterator(); it.hasNext();) {
			Map.Entry par = (Map.Entry) it.next();
			if (par.getValue() == null)
				it.remove();
			if (par.getValue() instanceof String[]
					&& ((String[]) par.getValue()).length == 0)
				it.remove();
			if (par.getValue() instanceof String) {
				if (StringUtils.isEmpty((String) par.getValue())) {
					it.remove();
				}
			}
		}
		invocationPath = RequestUtil.getPath(path, uriParams);
		return invocationPath;
	}
}
