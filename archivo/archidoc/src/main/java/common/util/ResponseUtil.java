package common.util;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {

	private static ResponseUtil instance = null;

	public static synchronized ResponseUtil getInstance() {
		if (instance == null) {
			instance = new ResponseUtil();
		}
		return instance;
	}

	public void agregarCabecerasHTTP(HttpServletResponse response) {
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "private");
		response.setHeader("Pragma", "cache");
	}
}
