package ieci.tdw.applets.ispacdocapplet.config;

import ieci.tdw.applets.ispacdocapplet.logging.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LocalConfig extends java.util.Properties {

	/** Nombre del fichero de configuración. */
	protected static final String CFG_FILE_NAME = ".ispacdocapplet";
	
	/** Fichero de propiedades. */
	private File file = null;
	
	
	/**
	 * Constructor.
	 * @throws IOException si ocurre algún error.
	 */
	public LocalConfig() {
		
		super();

		try {
			// Nombre del fichero de configuración
			String fileName = getCfgFileName();
			
			if (fileName != null) {
				
				// Obtiene el fichero de propiedades
				file = new File(fileName);
				if (!file.exists()) {
					file.createNewFile();
				}
				
				// Carga los datos del fichero de propiedades
				load(new FileInputStream(file));
			}
		} catch (Exception e) {
			Log.log("Error loading local configuration", e);
		}
	}

	/** 
	 * Obtiene el nombre completo del fichero de configuración.
	 * @return Nombre completo del fichero de configuración.
	 */
	protected static String getCfgFileName() {
		return new StringBuffer()
			.append(System.getProperty("user.home"))
			.append(File.separator)
			.append(CFG_FILE_NAME)
			.toString();
	}

	/**
	 * Guarda las propiedades.
	 * @throws IOException si ocurre algún error.
	 */
	public void store() throws IOException {
		OutputStream os = null;
		
		try {
			os = new FileOutputStream(file);
			store(os, null);
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

}
