package ieci.tdw.ispac.ispaclib.util;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Lee el fichero <i>location.properties</i> para obtener la ruta
 * de los ficheros de configuración.
 * @deprecated Las clases de lectura de ficheros de configuración deben extender la clase PropertiesConfiguration.
 */
public class ISPACConfigLocation extends Properties {
	
	/** Instancia de la clase. */
	private static ISPACConfigLocation mInstance = null;

	/** Nombre del fichero de localizaciones. */
	private static final String LOCATION_PROPERTIES_FILE = "location.properties";

	public final static String APP_PATH = "APP_PATH";
	
	
	/**
	 * Constructor.
	 *
	 */
	private ISPACConfigLocation() {
		super();
	}

	/**
	 * Obtiene una instancia de la clase.
	 * @return Instancia de la clase.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static synchronized ISPACConfigLocation getInstance()
			throws ISPACException {
		if (mInstance == null) {
			mInstance = new ISPACConfigLocation();
			mInstance.initiate();
		}
		return mInstance;
	}

	/**
	 * Carga las propiedades de configuración. 
	 * @throws ISPACException si ocurre algún error.
	 */
    private void initiate() throws ISPACException {
		try {
			InputStream in = ISPACConfigLocation.class.getClassLoader()
					.getResourceAsStream(LOCATION_PROPERTIES_FILE);
			if (in != null) {
				load(in);
				in.close();
			}
		} catch (Exception e) {
			throw new ISPACException(e);
		}
	}

    /**
     * Obtiene una ruta de un fichero de localización.
     * @param key Nombre de la localización.
     * @return Ruta del fichero de configuración.
     */
	public String get(String key) {
		return (String) super.get(key);
	}

    /**
     * Establece una ruta de un fichero de localización.
     * @param key Nombre de la localización.
     * @param value Ruta del fichero de configuración.
     */
	public void put(String key, String value) {
		super.put(key, value);
	}
	
    /**
     * Obtiene una ruta de un fichero de localización.
     * @param key Nombre de la localización.
     * @return Ruta del fichero de configuración.
     */
	public static String getLocation(String key) {
		String location = null;
		
		try {
			location = ISPACConfigLocation.getInstance().get(key);
		} catch (ISPACException e) {
			location = null;
		}
		
		return location;
	}

    /**
     * Obtiene la URL de un fichero de configuración.
     * @param key Nombre de la localización.
     * @return URL del fichero de configuración.
     */
    public static URL getLocationURL(String key) {
    	return getFileURL(getLocation(key));
    }

    /**
     * Obtiene la URL de un fichero. La búsqueda de realiza en el orden siguiente:
     * <ul>
     * 	<li>Classpath de la aplicación</li>
     * <li>Classpath del sistema</li>
     * 	<li>Ruta absoluta</li>
     * </ul>
     * @param resource Nombre del fichero.
     * @return URL del fichero.
     */
    public static URL getFileURL(String resource) {
    	
    	URL url = null;
    	
    	if (resource != null) {
    		
			// Comprobar el fichero en el classpath de la aplicación
    		url = ISPACConfigLocation.class.getClassLoader().getResource(resource);
    		if (url == null) {

    			// Comprobar el fichero en el classpath del sistema
    			url = ClassLoader.getSystemResource(resource);
    			if (url == null) {
    				
    				try {

    					// Fichero con path relativo a la aplicación
    					String appPath = ISPACConfigLocation.getInstance()
    						.get(APP_PATH);
    					if (StringUtils.isNotBlank(appPath)) {
	    					File file = new File(appPath + resource);
	    					if (file.isFile()) {
	    						url = file.toURL();
	    					}
    					}
    				} catch (Exception e) {
    					url = null;
    				}

    				if (url == null) {
	    				try {
	    					
	    					// Fichero con path absoluto
	    					File file = new File(resource);
	    					if (file.isFile()) {
	    						url = file.toURL();
	    					}
	
	    				} catch (Exception e) {
	    					url = null;
	    				}
    				}
    			}
    		}
    	}
    	
    	return url;
    }

}
