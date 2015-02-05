package ieci.tdw.applets.idocscan.helpers;

import ieci.tdw.applets.idocscan.exceptions.InstallerException;
import ieci.tdw.applets.idocscan.exceptions.PlatformNotSupportedException;

import java.io.File;

/**
 * Utilidad para la instalación de librerías.
 *
 */
public abstract class InstallerHelper {

	/**
	 * Constructor.
	 */
	protected InstallerHelper() {
		super();
	}

	/**
	 * Instancia un objeto de la clase.
	 * @return Instancia de la clase.
	 * @throws InstallerException si ocurre algún error.
	 */
	public static InstallerHelper getInstance() throws InstallerException {
		
		InstallerHelper helper = null;

		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.indexOf("windows") >= 0) {
			helper = new WindowsHelper();
		} else {
			throw new PlatformNotSupportedException(osName);
		}
		
		return ((InstallerHelper) (helper));
	}

	/**
	 * Obtiene el directorio de las librerías compartidas.
	 * @return directorio de las librerías compartidas.
	 * @throws InstallerException si ocurre algún error.
	 */
	public abstract File getLibraryDir() throws InstallerException;

	/**
	 * Obtiene la ruta completa de la librería.
	 * @param libFileName Nombre del fichero de la librería.
	 * @return Ruta completa del fichero.
	 */
	public abstract File getLibFile(String libFileName);
	
	/**
	 * Obtiene el nombre del zip con las librerías.
	 * @return nombre del zip con las librerías.
	 */
	public abstract String getLibrariesZipName();

	/**
	 * Método que se ejecuta antes de la instalación.
	 * @throws InstallerException si ocurre algún error.
	 */
	public void doBeforeLibrariesInstallation() throws InstallerException {}

	/**
	 * Método que se ejecuta después de la instalación.
	 * @throws InstallerException si ocurre algún error.
	 */
	public void doAfterInstallation() throws InstallerException {}
	
}