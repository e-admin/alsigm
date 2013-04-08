package ieci.tdw.ispac.ispaclib.util;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Properties;

public class ISPACVersion extends Properties
{

	private static ISPACVersion mInstance = null;

	public static final String FOLDER = "FOLDER";

	public static final String PRODUCT_VERSION = "product.version";
	public static final String VERSION = "version";
	public static final String DB_VERSION = "db.version";
	public static final String DATETIME = "datetime";

	/**
	 * Constructor.
	 */
	private ISPACVersion() {
		super();
	}

	/**
	 * Obtiene una instancia de la clase.
	 * @return Instancia de la clase.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static synchronized ISPACVersion getInstance() 
			throws ISPACException {
		if (mInstance == null) {
			mInstance = new ISPACVersion();
			mInstance.initiate("version.properties");
		}
		return mInstance;
	}

	protected void initiate(String fileName) throws ISPACException {
		
		FileInputStream in = null;

		try {
			if (fileName == null) {
				throw new ISPACException(
						"No se ha proporcionado el nombre del fichero de versión");
			}
			
			// Obtiene la carpeta donde está el fichero de configuración
			URL url = ISPACConfiguration.class.getClassLoader().getResource(fileName);
			
			if (url == null) {
				url = ClassLoader.getSystemResource(fileName);
			}
			
			if (url == null) {
				throw new ISPACException(
						"No existe el fichero de configuración " + fileName);
			}

			String sFolder = new String(url.getPath().getBytes(), 0, 
					url.getPath().lastIndexOf("/") + 1);

			put(ISPACVersion.FOLDER, sFolder);

			in = getFileInputStream(fileName);
			load(in);
			in.close();
		} catch (Exception e) {
			throw new ISPACException(e);
		}
	}

	public FileInputStream getFileInputStream(String fileName)
            throws ISPACException {

		if (fileName == null) {
			throw new ISPACException(
					"No se ha proporcionado el nombre del fichero de configuración");
		}
		
		try {
			String folder = get(ISPACVersion.FOLDER);
			File file = new File(folder, fileName);
			return new FileInputStream(file);

		} catch (FileNotFoundException e) {
			throw new ISPACException(e);
		}
	}

	public String get(String key) {
		return (String) super.get(key);
	}

	public void put(String key, String value) {
		super.put(key, value);
	}
}

