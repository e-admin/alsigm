package se.repositorios;

import ieci.tecdoc.sgm.core.services.tramitacion.dto.Firma;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoFichero;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoOcupacion;
import ieci.tecdoc.sgm.tram.SigemTramitacionServiceAdapter;
import ieci.tecdoc.sgm.tram.ws.client.TramitacionWebService;
import ieci.tecdoc.sgm.tram.ws.client.TramitacionWebServiceServiceLocator;
import ieci.tecdoc.sgm.tram.ws.client.dto.Binario;

import java.rmi.RemoteException;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.NotAvailableException;
import se.procedimientos.ProcedimientoConstants;
import se.repositorios.archigest.FirmaVO;
import se.repositorios.archigest.InfoFicheroVO;
import se.repositorios.archigest.InfoOcupacionVO;
import se.repositorios.exceptions.GestorRepositorioException;

import common.Constants;
import common.MultiEntityConstants;
import common.exceptions.SistemaExternoException;

public class GestorRepositorioSigem implements IGestorRepositorio{

	/** Modo de acceso **/
	public static final String API="API";
	public static final String WS="WS";

	/** Logger de la clase. */
	protected static final Logger logger = Logger.getLogger(GestorRepositorioSigem.class);

	/** Constante que indica el modo de acceso (API, WS) **/
	private static final String MODE="MODE";

	/**
	 * Modo de acceso
	 */
	private String mode=WS;

	/**
	 * Url de llamada al servicio web
	 */
	private String wsdlLocation=null;

	/**
	 * Usuario de llamada al servicio web
	 */
	protected String user=null;

	/**
	 * Contrase�a del usuario de llamada al servicio web
	 */
	protected String password=null;

	/**
	 * Entidad
	 */
	private String entity = null;

	/**
	 * Constructor.
	 */
	public GestorRepositorioSigem()
	{
	}


	/**
	 * Inicializa con los par�metros de configuraci�n.
	 * @param params Par�metros de configuraci�n.
	 * @throws SistemaExternoException si ocurre alg�n error.
	 */
	public void initialize(Properties params) throws SistemaExternoException
	{
		try
		{
			entity = (String) params.get(MultiEntityConstants.ENTITY_PARAM);

			mode= params.getProperty(MODE);
		}
		catch (Exception e)
		{
			logger.error("Error en la creaci\u00F3n del proxy de Custodia", e);
			throw new GestorRepositorioException(e);
		}
	}

    /**
     * Obtiene la informaci�n del fichero.
     * @param id Identificador del fichero.
     * @return Informaci�n del fichero.
     * @throws RemoteException si ocurre alg�n error.
     */
    public InfoFicheroVO getInfoFichero(String id) throws GestorRepositorioException, NotAvailableException {
    	if ((wsdlLocation == null) || (wsdlLocation.equals(Constants.STRING_EMPTY))){
    		throw new GestorRepositorioException("Error: no se ha proporcionado la url de llamada al servicio web");
    	}

    	InfoFicheroVO infoFicheroVO = null;

    	try {
			if(ProcedimientoConstants.API.equalsIgnoreCase(mode))
			{
				SigemTramitacionServiceAdapter service=new SigemTramitacionServiceAdapter();
				InfoFichero infoFichero =  service.getInfoFichero(entity,id);

				Firma [] firmasAux = infoFichero.getFirmas();
				FirmaVO [] firmasCreate = new FirmaVO[firmasAux.length];
				for(int i=0;i<firmasAux.length;i++)
    			{
					Firma firmaAux=firmasAux[i];

    				se.repositorios.archigest.FirmaVO firma=new se.repositorios.archigest.FirmaVO();
    				firma.setAutenticada(new Boolean(firmaAux.isAutenticada()));
    				firma.setAutor(firmaAux.getAutor());
    				firmasCreate[i]=firma;
    			}

				infoFicheroVO = new InfoFicheroVO(infoFichero.getFechaAlta(),firmasCreate, infoFichero.getNombre());
			}
			else
			{
				TramitacionWebServiceServiceLocator twsServiceLocator=new TramitacionWebServiceServiceLocator();
				twsServiceLocator.setTramitacionWebServiceEndpointAddress(wsdlLocation);
				TramitacionWebService service=twsServiceLocator.getTramitacionWebService();
				ieci.tecdoc.sgm.tram.ws.client.dto.InfoFichero infoFichero =  service.getInfoFichero(entity,id);

				ieci.tecdoc.sgm.tram.ws.client.dto.Firma [] firmasAux = infoFichero.getFirmas();
				FirmaVO [] firmasCreate = new FirmaVO[firmasAux.length];
				for(int i=0;i<firmasAux.length;i++)
    			{
					ieci.tecdoc.sgm.tram.ws.client.dto.Firma firmaAux=firmasAux[i];

    				se.repositorios.archigest.FirmaVO firma=new se.repositorios.archigest.FirmaVO();
    				firma.setAutenticada(new Boolean(firmaAux.isAutenticada()));
    				firma.setAutor(firmaAux.getAutor());
    				firmasCreate[i]=firma;
    			}

				infoFicheroVO = new InfoFicheroVO(infoFichero.getFechaAlta(),firmasCreate, infoFichero.getNombre());

			}
		}
		catch (Exception e)
		{
			throw new GestorRepositorioException(e);
		}

    	return infoFicheroVO;
    }

    /**
     * Obtiene el contenido de un fichero.
     * @param localizador Localizador del fichero.
     * @return Contenido del fichero.
     * @throws RemoteException si ocurre alg�n error.
     */
    public byte[] getFichero(String localizador) throws GestorRepositorioException, NotAvailableException {
    	if ((wsdlLocation == null) || (wsdlLocation.equals(Constants.STRING_EMPTY))){
    		throw new GestorRepositorioException("Error: no se ha proporcionado la url de llamada al servicio web");
    	}

    	byte [] contenido = null;

    	try {
			if(ProcedimientoConstants.API.equalsIgnoreCase(mode))
			{
				SigemTramitacionServiceAdapter servicio=new SigemTramitacionServiceAdapter();
				contenido = servicio.getFichero(entity,localizador);
			}
			else
			{
				TramitacionWebServiceServiceLocator twsServiceLocator=new TramitacionWebServiceServiceLocator();
				twsServiceLocator.setTramitacionWebServiceEndpointAddress(wsdlLocation);
				TramitacionWebService service=twsServiceLocator.getTramitacionWebService();
				Binario bin = service.getFichero(entity,localizador);
				contenido = bin.getContenido();
			}
		}
		catch (Exception e)
		{
			throw new GestorRepositorioException(e);
		}

    	return contenido;
    }

    /**
     * Obtiene la informaci�n de ocupaci�n del repositorio.
     * @return Informaci�n de ocupaci�n.
     * @throws Exception si ocurre alg�n error.
     */
    public InfoOcupacionVO getInfoOcupacion() throws GestorRepositorioException, NotAvailableException {
    	if ((wsdlLocation == null) || (wsdlLocation.equals(Constants.STRING_EMPTY))){
    		throw new GestorRepositorioException("Error: no se ha proporcionado la url de llamada al servicio web");
    	}

    	InfoOcupacionVO infoOcupacionVO = null;

    	try {
			if(ProcedimientoConstants.API.equalsIgnoreCase(mode))
			{
				SigemTramitacionServiceAdapter servicio=new SigemTramitacionServiceAdapter();
				InfoOcupacion infoOcupacionAux = servicio.getInfoOcupacion(entity);
				infoOcupacionVO = new InfoOcupacionVO(	new Long(infoOcupacionAux.getEspacioOcupado()),
														new Long(infoOcupacionAux.getEspacioTotal()),
														new Long(infoOcupacionAux.getNumeroFicheros()));
			}
			else
			{
				TramitacionWebServiceServiceLocator twsServiceLocator=new TramitacionWebServiceServiceLocator();
				twsServiceLocator.setTramitacionWebServiceEndpointAddress(wsdlLocation);
				TramitacionWebService service=twsServiceLocator.getTramitacionWebService();
				ieci.tecdoc.sgm.tram.ws.client.dto.InfoOcupacion infoOcupacionAux = service.getInfoOcupacion(entity);
				infoOcupacionVO = new InfoOcupacionVO(	new Long(infoOcupacionAux.getEspacioOcupado()),
														new Long(infoOcupacionAux.getEspacioTotal()),
														new Long(infoOcupacionAux.getNumeroFicheros()));
			}
		}
		catch (Exception e)
		{
			throw new GestorRepositorioException(e);
		}

    	return infoOcupacionVO;
    }

    /**
     * Elimina los ficheros del repositorio.
     * @param localizadores Localizadores de los ficheros.
     * @throws RemoteException si ocurre alg�n error.
     */
    public void eliminaFicheros(String [] localizadores) throws GestorRepositorioException, NotAvailableException{

    	if ((wsdlLocation == null) || (wsdlLocation.equals(Constants.STRING_EMPTY))){
    		throw new GestorRepositorioException("Error: no se ha proporcionado la url de llamada al servicio web");
    	}

    	try {
			if(ProcedimientoConstants.API.equalsIgnoreCase(mode))
			{
				SigemTramitacionServiceAdapter servicio=new SigemTramitacionServiceAdapter();
				servicio.eliminaFicheros(entity,localizadores);
			}
			else
			{
				TramitacionWebServiceServiceLocator twsServiceLocator=new TramitacionWebServiceServiceLocator();
				twsServiceLocator.setTramitacionWebServiceEndpointAddress(wsdlLocation);
				TramitacionWebService service=twsServiceLocator.getTramitacionWebService();
				service.eliminaFicheros(entity,localizadores);
			}
		}
		catch (Exception e)
		{
			throw new GestorRepositorioException(e);
		}
    }


	/**
	 * Permite establecer la URL de llamada
	 * @param wsdlLocation el wsdlLocation a establecer
	 */
	public void setWsdlLocation(String wsdlLocation) {
		this.wsdlLocation = wsdlLocation;
	}


	/**
	 * @param password el password a establecer
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @param user el user a establecer
	 */
	public void setUser(String user) {
		this.user = user;
	}


}
