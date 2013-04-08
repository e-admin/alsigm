package se.repositorios.ispac;

import ieci.tdw.ispac.services.ws.client.TramitacionWebService;
import ieci.tdw.ispac.services.ws.client.TramitacionWebServiceServiceLocator;
import ieci.tdw.ispac.services.ws.client.dto.Binario;
import ieci.tdw.ispac.services.ws.client.dto.Firma;
import ieci.tdw.ispac.services.ws.client.dto.InfoFichero;
import ieci.tdw.ispac.services.ws.client.dto.InfoOcupacion;

import java.rmi.RemoteException;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.NotAvailableException;
import se.repositorios.IGestorRepositorio;
import se.repositorios.archigest.FirmaVO;
import se.repositorios.archigest.InfoFicheroVO;
import se.repositorios.archigest.InfoOcupacionVO;
import se.repositorios.exceptions.GestorRepositorioException;

import common.Constants;
import common.exceptions.SistemaExternoException;

public class GestorRepositorioIspac implements IGestorRepositorio {

	/** Modo de acceso **/
	public static final String API = "API";
	public static final String WS = "WS";

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(GestorRepositorioIspac.class);

	/**
	 * Url de llamada al servicio web
	 */
	private String wsdlLocation = null;

	/**
	 * Usuario de llamada al servicio web
	 */
	protected String user = null;

	/**
	 * Contraseña del usuario de llamada al servicio web
	 */
	protected String password = null;

	/**
	 * Constructor.
	 */
	public GestorRepositorioIspac() {
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
			ieci.tdw.ispac.services.ws.client.TramitacionWebServiceServiceLocator twsServiceLocator = new TramitacionWebServiceServiceLocator();
			twsServiceLocator
					.setTramitacionWebServiceEndpointAddress(wsdlLocation);
			TramitacionWebService service = twsServiceLocator
					.getTramitacionWebService();
			InfoFichero infoFichero = service.getInfoFichero(id);

			Firma[] firmasAux = infoFichero.getFirmas();
			FirmaVO[] firmasCreate = new FirmaVO[firmasAux.length];
			for (int i = 0; i < firmasAux.length; i++) {
				Firma firmaAux = firmasAux[i];

				se.repositorios.archigest.FirmaVO firma = new se.repositorios.archigest.FirmaVO();
				firma.setAutenticada(new Boolean(firmaAux.isAutenticada()));
				firma.setAutor(firmaAux.getAutor());
				firmasCreate[i] = firma;
			}

			infoFicheroVO = new InfoFicheroVO(infoFichero.getFechaAlta(),
					firmasCreate, infoFichero.getNombre());
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
			TramitacionWebServiceServiceLocator twsServiceLocator = new TramitacionWebServiceServiceLocator();
			twsServiceLocator
					.setTramitacionWebServiceEndpointAddress(wsdlLocation);
			TramitacionWebService service = twsServiceLocator
					.getTramitacionWebService();
			Binario bin = service.getFichero(localizador);
			contenido = bin.getContenido();
		} catch (Exception e) {
			throw new GestorRepositorioException(e);
		}

		return contenido;
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
			TramitacionWebServiceServiceLocator twsServiceLocator = new TramitacionWebServiceServiceLocator();
			twsServiceLocator
					.setTramitacionWebServiceEndpointAddress(wsdlLocation);
			TramitacionWebService service = twsServiceLocator
					.getTramitacionWebService();
			InfoOcupacion infoOcupacionAux = service.getInfoOcupacion();
			infoOcupacionVO = new InfoOcupacionVO(new Long(
					infoOcupacionAux.getEspacioOcupado()), new Long(
					infoOcupacionAux.getEspacioTotal()), new Long(
					infoOcupacionAux.getNumeroFicheros()));
		} catch (Exception e) {
			throw new GestorRepositorioException(e);
		}

		return infoOcupacionVO;
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
			TramitacionWebServiceServiceLocator twsServiceLocator = new TramitacionWebServiceServiceLocator();
			twsServiceLocator
					.setTramitacionWebServiceEndpointAddress(wsdlLocation);
			TramitacionWebService service = twsServiceLocator
					.getTramitacionWebService();
			service.eliminaFicheros(localizadores);
		} catch (Exception e) {
			throw new GestorRepositorioException(e);
		}
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
