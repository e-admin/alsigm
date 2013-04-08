package se.repositorios;

import java.rmi.RemoteException;

import se.NotAvailableException;
import se.Parametrizable;
import se.repositorios.archigest.InfoFicheroVO;
import se.repositorios.archigest.InfoOcupacionVO;
import se.repositorios.exceptions.GestorRepositorioException;

/**
 * Interfaz para los conectores con el Sistemas Gestor del Repositorio.
 */
public interface IGestorRepositorio extends Parametrizable {

	/**
	 * Obtiene la información del fichero.
	 * 
	 * @param id
	 *            Identificador del fichero.
	 * @return Información del fichero.
	 * @throws RemoteException
	 *             si ocurre algún error.
	 */
	public InfoFicheroVO getInfoFichero(String id)
			throws GestorRepositorioException, NotAvailableException;

	/**
	 * Obtiene el contenido de un fichero.
	 * 
	 * @param localizador
	 *            Localizador del fichero.
	 * @return Contenido del fichero.
	 * @throws RemoteException
	 *             si ocurre algún error.
	 */
	public byte[] getFichero(String localizador)
			throws GestorRepositorioException, NotAvailableException;

	/**
	 * Elimina los ficheros del repositorio.
	 * 
	 * @param localizadores
	 *            Localizadores de los ficheros.
	 * @throws RemoteException
	 *             si ocurre algún error.
	 */
	public void eliminaFicheros(String[] localizadores)
			throws GestorRepositorioException, NotAvailableException;

	/**
	 * Obtiene la información de ocupación del repositorio.
	 * 
	 * @return Información de ocupación.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public InfoOcupacionVO getInfoOcupacion()
			throws GestorRepositorioException, NotAvailableException;

	/**
	 * Establece la url a la que llamar
	 * 
	 * @param wsdlLocation
	 *            URL a la que llamar
	 */
	public void setWsdlLocation(String wsdlLocation);

	/**
	 * Establece el usuario de llamada a servicios web
	 * 
	 * @param user
	 *            usuario de llamada a servicios web
	 */
	public void setUser(String user);

	/**
	 * Establece la contraseña del usuario de llamada a servicios web
	 * 
	 * @param user
	 *            contraseña del usuario de llamada a servicios web
	 */
	public void setPassword(String password);
}
