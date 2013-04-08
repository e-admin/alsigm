package ieci.tdw.ispac.ispacweb.util;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

/**
 * Utilidades para obtener las rutas de elementos.
 *
 */
public class PathUtils {

	/**
	 * Obtiene la ruta real de un recurso.
	 * @param context Contexto del servlet.
	 * @param path Ruta relativa del recurso.
	 * @return Ruta real del recurso.
	 */
	public static String getRealPath(ServletContext context, String path) {
		
		String realPath = null;
		
		if (context != null) {
			realPath = context.getRealPath(path);
			if ((realPath != null) && !realPath.endsWith(File.separator)) {
				realPath = realPath + File.separator;
			}
		}
		
		return realPath;
	}

	public static String getRealPath(HttpServlet servlet, String path) {
		
		String realPath = null;
		
		if (servlet != null) {
			realPath = getRealPath(servlet.getServletContext(), path);
		}
		
		return realPath;
	}
}
