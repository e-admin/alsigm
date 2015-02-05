package ieci.tdw.ispac.ispacweb.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * Utilidad para buscar ficheros.
 * 
 */
public class ResourceUtil {

	/**
	 * Obtiene la ruta real de un recurso.
	 * @param context Contexto del servlet.
	 * @param resource Recurso.
	 * @return Ruta real del recurso.
	 */
	public static String getFileRealPath(ServletContext context, String resource) {
		
		String realPath = null;
		
		if (context != null) {
			realPath = context.getRealPath(resource);
		}
		
		return realPath;
	}
	
    /**
     * Obtiene la ruta completa de un fichero. La búsqueda de realiza en el 
     * orden siguiente:
     * <ul>
     * 	<li>Classpath</li>
     * 	<li>Ruta relativa al directorio de la aplicación</li>
     * 	<li>Ruta absoluta</li>
     * </ul>
     * @param config Configuración del servlet.
     * @param resource Nombre del fichero.
     * @return Ruta completa del fichero.
     */
    public static String getFilePath(ServletConfig config, String resource) {
    	
    	String path = null;
    	
    	if ((config != null) && (resource != null)) {
    		
			// Comprobar el fichero en el classpath de la aplicación
    		URL url = ResourceUtil.class.getClassLoader().getResource(resource);
    		if (url != null) {
    			path = url.getFile();
    		} else {

    			// Comprobar el fichero en el directorio de la aplicación
				File file = new File(getFileRealPath(config.getServletContext(), resource));
				
				try {
					if (file.isFile()) {
						path = file.getCanonicalPath();
					} else {
						
		    			// Fichero con path absoluto
						file = new File(resource);
						if (file.isFile()) {
							path = file.getCanonicalPath();
						}
					}
				} catch (IOException e) {
					path = null;
				}
    		}
    	}
    	
    	return path;
    }

}
