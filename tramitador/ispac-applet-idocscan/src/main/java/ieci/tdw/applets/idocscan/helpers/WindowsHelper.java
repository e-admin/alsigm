package ieci.tdw.applets.idocscan.helpers;

import ieci.tdw.applets.idocscan.Messages;
import ieci.tdw.applets.idocscan.exceptions.InstallerException;

import java.io.File;

import javax.swing.JOptionPane;

/**
 * Utilidad para la instalación de librerías en Windows.
 *
 */
public class WindowsHelper extends InstallerHelper {

	/** 
	 * Constructor.
	 * 
	 */ 
	protected WindowsHelper() {
		super();
	}

	/**
	 * Obtiene el directorio de las librerías compartidas.
	 * @return directorio de las librerías compartidas.
	 * @throws InstallerException si ocurre algún error.
	 */
	public File getLibraryDir() {
		return new File(EnvHelper.getEnvVarIgnoreCase("SystemRoot"));
	}

	/**
	 * Obtiene el nombre del zip con las librerías.
	 * @return nombre del zip con las librerías.
	 */
    public String getLibrariesZipName() {
        return "win32libraries.zip";
    }

	/**
	 * Obtiene la ruta completa de la librería.
	 * @param libFileName Nombre del fichero de la librería.
	 * @return Ruta completa del fichero.
	 */
	public File getLibFile(String libFileName) {
		File file = null;
		
		File libDir = getLibraryDir();
		if (libDir.exists()) {
			file = new File(libDir, libFileName);
			if (!file.exists()) {
				file = null;
			}
		}
	
		return file;
	}

	/**
	 * Método que se ejecuta después de la instalación.
	 * @throws InstallerException si ocurre algún error.
	 */
	public void doAfterInstallation() {
		JOptionPane.showMessageDialog(null,
				Messages.getString("applet.windows.afterInstallation.message"));
	}

}