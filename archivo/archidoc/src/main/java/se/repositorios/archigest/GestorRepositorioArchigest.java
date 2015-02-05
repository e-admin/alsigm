package se.repositorios.archigest;

import java.rmi.RemoteException;
import java.util.Properties;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import se.NotAvailableException;
import se.repositorios.IGestorRepositorio;
import se.repositorios.exceptions.GestorRepositorioException;

import common.Constants;
import common.exceptions.SistemaExternoException;

public class GestorRepositorioArchigest implements IGestorRepositorio {

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(GestorRepositorioArchigest.class);

	/**
	 * Url de llamada al servicio web
	 */
	protected String wsdlLocation = null;

	/**
	 * Usuario de llamada al servicio web
	 */
	private String user = null;

	/**
	 * Contraseña del usuario de llamada al servicio web
	 */
	private String password = null;

	/**
	 * Constructor.
	 */
	public GestorRepositorioArchigest() {
	}

	/**
	 * Inicializa con los parámetros de configuración.
	 * 
	 * @param params
	 *            Parámetros de configuración.
	 * @throws SistemaExternoException
	 *             si ocurre algún error.
	 */
	public void initialize(Properties params) throws SistemaExternoException {
		try {

		} catch (Exception e) {
			logger.error("Error en la creaci\u00F3n del proxy de Custodia", e);
			throw new GestorRepositorioException(e);
		}
	}

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
			throws GestorRepositorioException, NotAvailableException {
		if ((wsdlLocation == null)
				|| (wsdlLocation.equals(Constants.STRING_EMPTY))) {
			throw new GestorRepositorioException(
					"Error: no se ha proporcionado la url de llamada al servicio web");
		}

		InfoFicheroVO infoFicheroVO = null;

		try {
			WSRepositorioProxy proxy = new WSRepositorioProxy(wsdlLocation);
			proxy.setUsername(user);
			proxy.setPassword(password);
			infoFicheroVO = proxy.getInfoFichero(id);
		} catch (Exception e) {
			throw new GestorRepositorioException(e);
		}

		return infoFicheroVO;
	}

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
			throws GestorRepositorioException, NotAvailableException {
		if ((wsdlLocation == null)
				|| (wsdlLocation.equals(Constants.STRING_EMPTY))) {
			throw new GestorRepositorioException(
					"Error: no se ha proporcionado la url de llamada al servicio web");
		}

		byte[] contenido = null;

		try {
			WSRepositorioProxy proxy = new WSRepositorioProxy(wsdlLocation);
			proxy.setUsername(user);
			proxy.setPassword(password);
			contenido = ArrayUtils.toPrimitive(proxy.getFichero(localizador));
		} catch (Exception e) {
			throw new GestorRepositorioException(e);
		}

		return contenido;
	}

	/**
	 * Elimina los ficheros del repositorio.
	 * 
	 * @param localizadores
	 *            Localizadores de los ficheros.
	 * @throws RemoteException
	 *             si ocurre algún error.
	 */
	public void eliminaFicheros(String[] localizadores)
			throws GestorRepositorioException, NotAvailableException {

		if ((wsdlLocation == null)
				|| (wsdlLocation.equals(Constants.STRING_EMPTY))) {
			throw new GestorRepositorioException(
					"Error: no se ha proporcionado la url de llamada al servicio web");
		}

		try {
			WSRepositorioProxy proxy = new WSRepositorioProxy(wsdlLocation);
			proxy.setUsername(user);
			proxy.setPassword(password);
			proxy.eliminaFicheros(localizadores);
		} catch (Exception e) {
			throw new GestorRepositorioException(e);
		}
	}

	/**
	 * Obtiene la información de ocupación del repositorio.
	 * 
	 * @return Información de ocupación.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public InfoOcupacionVO getInfoOcupacion()
			throws GestorRepositorioException, NotAvailableException {
		if ((wsdlLocation == null)
				|| (wsdlLocation.equals(Constants.STRING_EMPTY))) {
			throw new GestorRepositorioException(
					"Error: no se ha proporcionado la url de llamada al servicio web");
		}

		InfoOcupacionVO infoOcupacionVO = null;

		try {
			WSRepositorioProxy proxy = new WSRepositorioProxy(wsdlLocation);
			proxy.setUsername(user);
			proxy.setPassword(password);
			infoOcupacionVO = proxy.getInfoOcupacion();
		} catch (Exception e) {
			throw new GestorRepositorioException(e);
		}

		return infoOcupacionVO;
	}

	/**
	 * Permite establecer la URL de llamada
	 * 
	 * @param wsdlLocation
	 *            el wsdlLocation a establecer
	 */
	public void setWsdlLocation(String wsdlLocation) {
		this.wsdlLocation = wsdlLocation;
	}

	/**
	 * @param password
	 *            el password a establecer
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param user
	 *            el user a establecer
	 */
	public void setUser(String user) {
		this.user = user;
	}

}
