package ieci.tecdoc.sgm.tram.thirdparty;

import ieci.tdw.ispac.api.BasicThirdPartyAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.thirdparty.ElectronicAddressAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.IElectronicAddressAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.IPostalAddressAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.IThirdPartyAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.PostalAddressAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.ThirdPartyAdapter;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.terceros.ServicioTerceros;
import ieci.tecdoc.sgm.core.services.terceros.TercerosException;
import ieci.tecdoc.sgm.core.services.terceros.dto.DireccionElectronica;
import ieci.tecdoc.sgm.core.services.terceros.dto.DireccionPostal;
import ieci.tecdoc.sgm.core.services.terceros.dto.Tercero;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * Implementación del API de acceso a terceros en SIGEM.
 *
 */
public class SigemThirdPartyAPI extends BasicThirdPartyAPI {

	/** Logger de la clase. */
	private static final Logger logger = Logger.getLogger(SigemThirdPartyAPI.class);
	
	/** Servicio de acceso a terceros. */
	private ServicioTerceros servicioTerceros = null;

	/**
	 * Constructor.
	 * @exception ISPACException si ocurre algún error.
	 */
	public SigemThirdPartyAPI() throws ISPACException {
		try {
			servicioTerceros = LocalizadorServicios.getServicioTerceros();
		} catch (SigemException e) {
			logger.error("Error al crear el servicio de terceros", e);
			throw new ISPACException(
					"Error al crear el servicio de terceros", e);
		}
	}

	/**
	 * Obtiene una lista de terceros en función del códigos de identificación. 
	 * @param code Código de identificación del tercero
	 * @param onlyDefaultValues Indica si se cargan solamente las direcciones asociadas por defecto
	 * @return lista de terceros asociados con un código de identificación, 
	 * cargando las direcciones (postal y telemática) por defecto si así se indica. 
	 * @throws ISPACException
	 */
	public IThirdPartyAdapter[] lookup(String code, boolean onlyDefaultValues) throws ISPACException {

		IThirdPartyAdapter [] terceros = null;

		try {
			String entityId = null;
			OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
			if (info != null)
				entityId = info.getOrganizationId();

			List listaTerceros = servicioTerceros.lookup(entityId, code);
			terceros = getThirdPartyAdapters(listaTerceros);
			
		} catch (TercerosException e) {
			logger.error("Error en la búsqueda de terceros", e);
			throw new ISPACException("Error en la búsqueda de terceros", e);
		}
		
		return terceros;
	}

	/**
	 * Obtiene una lista de terceros en función del nombre y dos apellidos.
	 * @param name nombre del tercero
	 * @param surnam1 primer apellido del tercero
	 * @param surname2 segundo apellido del tercero
	 * @param onlyDefaultValues Indica si se cargan solamente las direcciones asociadas por defecto
	 * @return Terceros que cumple la condición.
	 * @exception ISPACException si ocurre algún error.
	 */
	public IThirdPartyAdapter [] lookup(String name, String surname1, String surname2, 
			boolean onlyDefaultValues) throws ISPACException {
		
		IThirdPartyAdapter [] terceros = null;

		try {
			String entityId = null;
			OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
			if (info != null)
				entityId = info.getOrganizationId();
			
			List listaTerceros = servicioTerceros.lookup(entityId, name, surname1, 
					surname2, onlyDefaultValues);
			terceros = getThirdPartyAdapters(listaTerceros);
			
		} catch (TercerosException e) {
			logger.error("Error en la búsqueda de terceros", e);
			throw new ISPACException("Error en la búsqueda de terceros", e);
		}
		
		return terceros;
	}
	
	/**
	 * Obtiene un tercero a partir de su identificador.
	 * @param id identificador del tercero
	 * @param onlyDefaultValues Indica si se cargan las direcciones asociadas por defecto
	 * @return Información del tercero con sus direcciones por defecto si así se indica 
	 * @throws ISPACException
	 */
	public IThirdPartyAdapter lookupById(String id, boolean onlyDefaultValues) throws ISPACException {
		
		IThirdPartyAdapter tercero = null;

		try {
			String entityId = null;
			OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
			if (info != null)
				entityId = info.getOrganizationId();
			
			Tercero ter = servicioTerceros.lookupById(entityId, id, onlyDefaultValues);
			tercero = getThirdPartyAdapter(ter);
			
		} catch (TercerosException e) {
			logger.error("Error en la búsqueda del tercero", e);
			throw new ISPACException("Error en la búsqueda del tercero", e);
		}
		
		return tercero;
	}

	/**
	 * Obtiene un tercero a partir de su identificador interno y los identificadores de sus direcciones.
	 * @param id Identificador interno del tercero.
	 * @param postalAddressId Identificador de la dirección postal.
	 * @param electronicAddressId Identificador de la dirección electrónica.
	 * @return Información del tercero.
	 * @exception ISPACException si ocurre algún error.
	 */
	public IThirdPartyAdapter lookupById(String id, String postalAddressId, String electronicAddressId) 
			throws ISPACException {

		IThirdPartyAdapter tercero = null;

		try {
			String entityId = null;
			OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
			if (info != null)
				entityId = info.getOrganizationId();
			
			Tercero ter = servicioTerceros.lookupById(entityId, id, postalAddressId, 
					electronicAddressId);
			tercero = getThirdPartyAdapter(ter);
			
		} catch (TercerosException e) {
			logger.error("Error en la búsqueda del tercero", e);
			throw new ISPACException("Error en la búsqueda del tercero", e);
		}
		
		return tercero;
	}

    /**
     * Obtiene una colección de todas las direcciones postales para un tercero
     * @param id identificador de tercero
     * @return lista de direcciones de postales relacionadas con un tercero
     * @throws Exception
     */
    public IPostalAddressAdapter [] lookupPostalAddresses(String id) throws ISPACException {
    	
    	IPostalAddressAdapter [] direcciones = null;

		try {
			String entityId = null;
			OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
			if (info != null)
				entityId = info.getOrganizationId();
			
			DireccionPostal[] dirs = servicioTerceros.lookupPostalAddresses(entityId, id);
			direcciones = getPostalAddressesAdapter(dirs);
			
		} catch (TercerosException e) {
			logger.error("Error en la búsqueda de direcciones postales", e);
			throw new ISPACException("Error en la búsqueda de direcciones postales", e);
		}
		
		return direcciones;
    }

    /**
     * Obtiene la dirección postal por defecto para un tercero
     * @param id identificador de tercero
     * @return dirección postal por defecto
     * @throws ISPACException
     */
    public IPostalAddressAdapter lookupDefaultPostalAddress(String id) throws ISPACException {

		IPostalAddressAdapter address = null;

		try {
			String entityId = null;
			OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
			if (info != null)
				entityId = info.getOrganizationId();
			
			DireccionPostal dir = servicioTerceros.lookupDefaultPostalAddress(entityId, id);
			address = getPostalAddressAdapter(dir);
			
		} catch (TercerosException e) {
			logger.error("Error en la búsqueda de la dirección postal", e);
			throw new ISPACException("Error en la búsqueda de la dirección postal", e);
		}
		
		return address;
    }
    
    /**
     * Obtiene una colección de todas las direcciones electrónicas para un tercero
     * @param id identificador de tercero
     * @return colección de direcciones electrónicas
     * @throws ISPACException
     */
    public IElectronicAddressAdapter [] lookupElectronicAddresses(String id) throws ISPACException {

    	IElectronicAddressAdapter [] direcciones = null;

		try {
			String entityId = null;
			OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
			if (info != null)
				entityId = info.getOrganizationId();
			
			DireccionElectronica[] dirs = servicioTerceros.lookupElectronicAddresses(entityId, id);
			direcciones = getElectronicAddressesAdapter(dirs);
			
		} catch (TercerosException e) {
			logger.error("Error en la búsqueda de direcciones electrónicas", e);
			throw new ISPACException("Error en la búsqueda de direcciones electrónicas", e);
		}
		
		return direcciones;
    }

    /**
     * Obtiene la dirección electrónica por defecto para un tercero
     * @param id identificador de tercero
     * @return dirección electrónica
     * @throws ISPACException
     */
    public IElectronicAddressAdapter lookupDefaultElectronicAddress(String id) throws ISPACException {

		IElectronicAddressAdapter address = null;

		try {
			String entityId = null;
			OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
			if (info != null)
				entityId = info.getOrganizationId();
			
			DireccionElectronica dir = servicioTerceros.lookupDefaultElectronicAddress(entityId, id);
			address = getElectronicAddressAdapter(dir);
			
		} catch (TercerosException e) {
			logger.error("Error en la búsqueda de la dirección electrónica", e);
			throw new ISPACException("Error en la búsqueda de la dirección electrónica", e);
		}
		
		return address;
    }

	/**
	 * Obtiene una dirección postal según su identificador
	 * @param addressId identificador de dirección postal
	 * @return dirección postal
	 * @throws ISPACException
	 */
	public IPostalAddressAdapter getPostalAddress(String addressId) throws ISPACException {

		IPostalAddressAdapter address = null;

		try {
			String entityId = null;
			OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
			if (info != null)
				entityId = info.getOrganizationId();
			
			DireccionPostal dir = servicioTerceros.getPostalAddress(entityId, addressId);
			address = getPostalAddressAdapter(dir);
			
		} catch (TercerosException e) {
			logger.error("Error en la búsqueda de la dirección postal", e);
			throw new ISPACException("Error en la búsqueda de la dirección postal", e);
		}
		
		return address;
	}

	/**
	 * Obtiene una dirección electrónica según su identificador
	 * @param addressId identificador de dirección electrónica
	 * @return dirección electrónica
	 * @throws ISPACException
	 */
	public IElectronicAddressAdapter getElectronicAddress(String addressId) throws ISPACException {

		IElectronicAddressAdapter address = null;

		try {
			String entityId = null;
			OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
			if (info != null)
				entityId = info.getOrganizationId();
			
			DireccionElectronica dir = servicioTerceros.getElectronicAddress(entityId, addressId);
			address = getElectronicAddressAdapter(dir);
			
		} catch (TercerosException e) {
			logger.error("Error en la búsqueda de la dirección electrónica", e);
			throw new ISPACException("Error en la búsqueda de la dirección electrónica", e);
		}
		
		return address;
	}

	private static IThirdPartyAdapter[] getThirdPartyAdapters(List lista) {

		ThirdPartyAdapter[] terceros = null;

		if (lista != null) {
			terceros = new ThirdPartyAdapter[lista.size()];
			for (int i = 0; i < lista.size(); i++) {
				terceros[i] = getThirdPartyAdapter((Tercero) lista.get(i));
			}
		}

		return terceros;
	}

	private static ThirdPartyAdapter getThirdPartyAdapter(Tercero ter) {
		
		ThirdPartyAdapter tercero = null;
		
		if (ter != null) {
			tercero = new ThirdPartyAdapter();
			tercero.setIdExt(ter.getIdExt());
			tercero.setTipoPersona(ter.getTipoPersona());
			tercero.setIdentificacion(ter.getIdentificacion());
			tercero.setNombre(ter.getNombre());
			tercero.setPrimerApellido(ter.getPrimerApellido());
			tercero.setSegundoApellido(ter.getSegundoApellido());
			tercero.setNotificacionTelematica(ter.isNotificacionTelematica());
			tercero.setDireccionesPostales(getPostalAddressesAdapter(ter.getDireccionesPostales()));
			tercero.setDireccionesElectronicas(getElectronicAddressesAdapter(ter.getDireccionesElectronicas()));
		}
		
		return tercero;
	}

	private static IElectronicAddressAdapter[] getElectronicAddressesAdapter(DireccionElectronica[] dirs) {

		IElectronicAddressAdapter[] direcciones = null;

		if (dirs != null) {
			direcciones = new ElectronicAddressAdapter[dirs.length];
			for (int i = 0; i < dirs.length; i++) {
				direcciones[i] = getElectronicAddressAdapter(dirs[i]);
			}
		}

		return direcciones;
	}

	private static IElectronicAddressAdapter getElectronicAddressAdapter(DireccionElectronica dir) {
		
		ElectronicAddressAdapter direccion = null;
		
		if (dir != null) {
			direccion = new ElectronicAddressAdapter();
			direccion.setId(dir.getId());
			direccion.setTipo(dir.getTipo());
			direccion.setDireccion(dir.getDireccion());
		}
		
		return direccion;
	}

	private static IPostalAddressAdapter[] getPostalAddressesAdapter(DireccionPostal[] dirs) {

		IPostalAddressAdapter[] direcciones = null;

		if (dirs != null) {
			direcciones = new PostalAddressAdapter[dirs.length];
			for (int i = 0; i < dirs.length; i++) {
				direcciones[i] = getPostalAddressAdapter(dirs[i]);
			}
		}

		return direcciones;
	}

	private static IPostalAddressAdapter getPostalAddressAdapter(DireccionPostal dir) {
		
		PostalAddressAdapter direccion = null;
		
		if (dir != null) {
			direccion = new PostalAddressAdapter();
			direccion.setId(dir.getId());
			direccion.setDireccionPostal(dir.getDireccionPostal());
			direccion.setTipoVia(dir.getTipoVia());
			direccion.setVia(dir.getVia());
			direccion.setBloque(dir.getBloque());
			direccion.setPiso(dir.getPiso());
			direccion.setPuerta(dir.getPuerta());
			direccion.setCodigoPostal(dir.getCodigoPostal());
			direccion.setPoblacion(dir.getPoblacion());
			direccion.setMunicipio(dir.getMunicipio());
			direccion.setProvincia(dir.getProvincia());
			direccion.setComunidadAutonoma(dir.getComunidadAutonoma());
			direccion.setPais(dir.getPais());
			direccion.setTelefono(dir.getTelefono());
		}
		
		return direccion;
	}

}
