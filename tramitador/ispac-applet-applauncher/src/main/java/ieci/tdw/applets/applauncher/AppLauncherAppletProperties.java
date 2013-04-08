package ieci.tdw.applets.applauncher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class AppLauncherAppletProperties extends java.util.Properties {

	/** Nombre del fichero de configuración. */
	protected static final String CFG_FILE_NAME = ".applauncherapplet";
	
	/** Fichero de propiedades. */
	private File file = null;
	
	
	/**
	 * Constructor.
	 * @throws IOException si ocurre algún error.
	 */
	public AppLauncherAppletProperties() throws IOException {
		super();

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
	}

	/** 
	 * Obtiene el nombre completo del fichero de configuración.
	 * @return Nombre completo del fichero de configuración.
	 */
	protected static String getCfgFileName() {
		return new StringBuffer()
			.append(System.getProperty("user.home"))
			.append(File.separator)
			.append(".applauncherapplet")
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

	public static boolean checkAppPath(String appPath) {
		
		if ( (appPath == null) || (appPath.trim().length() == 0) ) {
			return false;
		}
		
		File file = new File(appPath);
		return file.isFile();
	}
}
