package ieci.tecdoc.sgm.terceros;

import ieci.tdw.ispac.ispaclib.invesicres.thirdparty.SicresThirdPartyAPI;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.thirdparty.IElectronicAddressAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.IPostalAddressAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.IThirdPartyAdapter;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tecdoc.sgm.core.services.terceros.ServicioTerceros;
import ieci.tecdoc.sgm.core.services.terceros.TercerosException;
import ieci.tecdoc.sgm.core.services.terceros.dto.DireccionElectronica;
import ieci.tecdoc.sgm.core.services.terceros.dto.DireccionPostal;
import ieci.tecdoc.sgm.core.services.terceros.dto.Tercero;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Implementación del servicio de Terceros en SICRES.
 *
 */
public class SigemTercerosServiceAdapter implements ServicioTerceros {

	/** Logger de la clase. */
	private static final Logger logger = 
		Logger.getLogger(SigemTercerosServiceAdapter.class);

	/** Nombre del origen de datos. */
	private String dsName = null;
	
	
	/**
	 * Constructor.
	 *
	 */
	public SigemTercerosServiceAdapter() {
		super();
	}

	/**
	 * Establece el origen de datos.
	 * @param dsName Origen de datos.
	 */
	public void setDsName(String dsName) {
		this.dsName = dsName;
	}
	
	private void setOrganizationUserInfo(String entityId){
		OrganizationUserInfo info = new OrganizationUserInfo();
		info.setOrganizationId(entityId);
		OrganizationUser.setOrganizationUserInfo(info);
	}	
	
	/**
	 * Obtiene una lista de terceros en función del código de identificación.
	 * @param entityId Identificador de la entidad administrativa.
	 * @param code Código de identificación del tercero
	 * @return Lista de terceros ({@link Tercero}.
	 * @exception TercerosException si ocurre algún error.
	 */
	public List lookup(String entityId, String code) throws TercerosException {
		try {
			setOrganizationUserInfo(entityId);
			String poolName = OrganizationUser.getOrganizationUserInfo().getThirdPartyPoolName(dsName);
			
			SicresThirdPartyAPI sicresThirdPartyAPI = new SicresThirdPartyAPI(poolName);
			return getTercerosList(sicresThirdPartyAPI.lookup(code));
			
		} catch (Throwable e){
			logger.error("Error inesperado al obtener los terceros por código [" + code + "]", e);
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Obtiene una lista de terceros en función del códigos de identificación.
	 * @param entityId Identificador de la entidad administrativa. 
	 * @param code Código de identificación del tercero
	 * @param onlyDefaultValues Indica si se cargan solamente las direcciones asociadas por defecto
	 * @return Lista de terceros ({@link Tercero}. 
	 * @exception TercerosException si ocurre algún error.
	 */
	public List lookup(String entityId, String code, boolean defaultValues) throws TercerosException {
		try {
			setOrganizationUserInfo(entityId);
			String poolName = OrganizationUser.getOrganizationUserInfo().getThirdPartyPoolName(dsName);

			SicresThirdPartyAPI sicresThirdPartyAPI = new SicresThirdPartyAPI(poolName);

			return getTercerosList(sicresThirdPartyAPI.lookup(code, defaultValues));

		} catch (Throwable e){
			logger.error("Error inesperado al obtener los terceros por código [" + code + "]", e);
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Obtiene una lista de terceros en función del nombre y dos apellidos.
	 * @param entityId Identificador de la entidad administrativa.
	 * @param name Nombre del tercero
	 * @param surnam1 Primer apellido del tercero
	 * @param surname2 Segundo apellido del tercero
	 * @return Lista de terceros ({@link Tercero}.
	 * @exception TercerosException si ocurre algún error.
	 */
	public List lookup(String entityId, String name, String surname1, 
			String surname2) throws TercerosException {
		try {
			setOrganizationUserInfo(entityId);
			String poolName = OrganizationUser.getOrganizationUserInfo().getThirdPartyPoolName(dsName);

			SicresThirdPartyAPI sicresThirdPartyAPI = new SicresThirdPartyAPI(poolName);

			return getTercerosList(sicresThirdPartyAPI.lookup(name, surname1, surname2));

		} catch (Throwable e){
			logger.error("Error inesperado al obtener los terceros: nombre=["
					+ name + "], apellido1=[" + surname1 + "], apellido2=[" + surname2 + "]", e);
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Obtiene una lista de terceros en función del nombre y dos apellidos.
	 * @param entityId Identificador de la entidad administrativa.
	 * @param name Nombre del tercero
	 * @param surnam1 Primer apellido del tercero
	 * @param surname2 Segundo apellido del tercero
	 * @param onlyDefaultValues Indica si se cargan solamente las direcciones asociadas por defecto
	 * @return Lista de terceros ({@link Tercero}.
	 * @exception TercerosException si ocurre algún error.
	 */
	public List lookup(String entityId, String name, String surname1, 
			String surname2, boolean onlyDefaultValues) throws TercerosException {
		try {
			setOrganizationUserInfo(entityId);
			String poolName = OrganizationUser.getOrganizationUserInfo().getThirdPartyPoolName(dsName);

			SicresThirdPartyAPI sicresThirdPartyAPI = new SicresThirdPartyAPI(poolName);

			return getTercerosList(sicresThirdPartyAPI.lookup(name, surname1, surname2, onlyDefaultValues));

		} catch (Throwable e){
			logger.error("Error inesperado al obtener los terceros: nombre=["
					+ name + "], apellido1=[" + surname1 + "], apellido2=[" + surname2 + "]", e);
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Obtiene un tercero a partir de su identificador interno.
	 * @param entityId Identificador de la entidad administrativa.
	 * @param id Identificador interno del tercero.
	 * @return Información del tercero.
	 * @exception TercerosException si ocurre algún error.
	 */
	public Tercero lookupById(String entityId, String id) throws TercerosException {
		try {
			setOrganizationUserInfo(entityId);
			String poolName = OrganizationUser.getOrganizationUserInfo().getThirdPartyPoolName(dsName);

			SicresThirdPartyAPI sicresThirdPartyAPI = new SicresThirdPartyAPI(poolName);

			return getTercero(sicresThirdPartyAPI.lookupById(id));

		} catch (Throwable e){
			logger.error("Error inesperado al obtener el tercero con id [" + id + "]", e);
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	/**
	 * Obtiene un tercero a partir de su identificador.
	 * @param entityId Identificador de la entidad administrativa.
	 * @param id identificador del tercero
	 * @param onlyDefaultValues Indica si se cargan solamente las direcciones asociadas por defecto
	 * @return Información del tercero con sus direcciones por defecto si así se indica 
	 * @exception TercerosException si ocurre algún error.
	 */
	public Tercero lookupById(String entityId, String id, boolean onlyDefaultValues) 
			throws TercerosException {
		try {
			setOrganizationUserInfo(entityId);
			String poolName = OrganizationUser.getOrganizationUserInfo().getThirdPartyPoolName(dsName);

			SicresThirdPartyAPI sicresThirdPartyAPI = new SicresThirdPartyAPI(poolName);

			return getTercero(sicresThirdPartyAPI.lookupById(id, onlyDefaultValues));

		} catch (Throwable e){
			logger.error("Error inesperado al obtener el tercero con id [" + id + "]", e);
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Obtiene un tercero a partir de su identificador interno.
	 * @param entityId Identificador de la entidad administrativa.
	 * @param postalAddressId Identificador de la dirección postal.
	 * @param electronicAddressId Identificador de la dirección postal.
	 * @return Información del tercero.
	 * @exception TercerosException si ocurre algún error.
	 */
	public Tercero lookupById(String entityId, String id, String postalAddressId, 
			String electronicAddressId) throws TercerosException {
		
		try {
			setOrganizationUserInfo(entityId);
			String poolName = OrganizationUser.getOrganizationUserInfo().getThirdPartyPoolName(dsName);

			SicresThirdPartyAPI sicresThirdPartyAPI = new SicresThirdPartyAPI(poolName);

			return getTercero(sicresThirdPartyAPI.lookupById(id, postalAddressId, electronicAddressId));

		} catch (Throwable e){
			logger.error("Error inesperado al obtener el tercero con id [" + id + "]", e);
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
     * Obtiene una colección de todas las direcciones postales para un tercero
     * @param entityId Identificador de la entidad administrativa.
     * @param id identificador de tercero
     * @return lista de direcciones de postales relacionadas con un tercero
     * @exception TercerosException si ocurre algún error.
     */
	public DireccionPostal[] lookupPostalAddresses(String entityId, String id) throws TercerosException {
		try {
			setOrganizationUserInfo(entityId);
			String poolName = OrganizationUser.getOrganizationUserInfo().getThirdPartyPoolName(dsName);

			SicresThirdPartyAPI sicresThirdPartyAPI = new SicresThirdPartyAPI(poolName);

			return getDireccionesPostales(sicresThirdPartyAPI.lookupPostalAddresses(id));

		} catch (Throwable e){
			logger.error("Error inesperado al obtener las direcciones postales para el id [" + id + "]", e);
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
    /**
     * Obtiene la dirección postal por defecto para un tercero
     * @param entityId Identificador de la entidad administrativa.
     * @param id identificador de tercero
     * @return dirección postal por defecto
     * @exception TercerosException si ocurre algún error.
     */
	public DireccionPostal lookupDefaultPostalAddress(String entityId, String id) 
			throws TercerosException {
		try {
			setOrganizationUserInfo(entityId);
			String poolName = OrganizationUser.getOrganizationUserInfo().getThirdPartyPoolName(dsName);

			SicresThirdPartyAPI sicresThirdPartyAPI = new SicresThirdPartyAPI(poolName);

			return getDireccionPostal(sicresThirdPartyAPI.lookupDefaultPostalAddress(id));

		} catch (Throwable e){
			logger.error("Error inesperado al obtener la dirección postal por defecto para el id [" 
					+ id + "]", e);
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, e);
		}
	}

    /**
     * Obtiene una colección de todas las direcciones electrónicas para un tercero
     * @param entityId Identificador de la entidad administrativa.
     * @param id identificador de tercero
     * @return colección de direcciones electrónicas
     * @exception TercerosException si ocurre algún error.
     */
	public DireccionElectronica[] lookupElectronicAddresses(String entityId, String id) 
			throws TercerosException {
		try {
			setOrganizationUserInfo(entityId);
			String poolName = OrganizationUser.getOrganizationUserInfo().getThirdPartyPoolName(dsName);

			SicresThirdPartyAPI sicresThirdPartyAPI = new SicresThirdPartyAPI(poolName);

			return getDireccionesElectronicas(sicresThirdPartyAPI.lookupElectronicAddresses(id));

		} catch (Throwable e){
			logger.error("Error inesperado al obtener las direcciones electrónicas para el id [" 
					+ id + "]", e);
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, e);
		}
	}

    /**
     * Obtiene la dirección electrónica por defecto para un tercero
     * @param entityId Identificador de la entidad administrativa.
     * @param id identificador de tercero
     * @return dirección electrónica
     * @exception TercerosException si ocurre algún error.
     */
	public DireccionElectronica lookupDefaultElectronicAddress(String entityId, String id) 
			throws TercerosException {
		try {
			setOrganizationUserInfo(entityId);
			String poolName = OrganizationUser.getOrganizationUserInfo().getThirdPartyPoolName(dsName);

			SicresThirdPartyAPI sicresThirdPartyAPI = new SicresThirdPartyAPI(poolName);

			return getDireccionElectronica(sicresThirdPartyAPI.lookupDefaultElectronicAddress(id));

		} catch (Throwable e){
			logger.error("Error inesperado al obtener la dirección electrónica por defecto para el id [" 
					+ id + "]", e);
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Obtiene una dirección postal según su identificador
	 * @param entityId Identificador de la entidad administrativa.
	 * @param id identificador de dirección postal
	 * @return dirección postal
	 * @exception TercerosException si ocurre algún error.
	 */
	public DireccionPostal getPostalAddress(String entityId, String id) throws TercerosException {
		try {
			setOrganizationUserInfo(entityId);
			String poolName = OrganizationUser.getOrganizationUserInfo().getThirdPartyPoolName(dsName);

			SicresThirdPartyAPI sicresThirdPartyAPI = new SicresThirdPartyAPI(poolName);

			return getDireccionPostal(sicresThirdPartyAPI.getPostalAddress(id));

		} catch (Throwable e){
			logger.error("Error inesperado al obtener la dirección postal con id [" + id + "]", e);
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Obtiene una dirección electrónica según su identificador
	 * @param entityId Identificador de la entidad administrativa.
	 * @param id identificador de dirección electrónica
	 * @return dirección electrónica
	 * @exception TercerosException si ocurre algún error.
	 */
	public DireccionElectronica getElectronicAddress(String entityId, String id) throws TercerosException {
		try {
			setOrganizationUserInfo(entityId);
			String poolName = OrganizationUser.getOrganizationUserInfo().getThirdPartyPoolName(dsName);

			SicresThirdPartyAPI sicresThirdPartyAPI = new SicresThirdPartyAPI(poolName);

			return getDireccionElectronica(sicresThirdPartyAPI.getElectronicAddress(id));

		} catch (Throwable e){
			logger.error("Error inesperado al obtener la dirección electrónica con id [" + id + "]", e);
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	protected List getTercerosList(IThirdPartyAdapter[] adapters) {
		List terceros = new ArrayList();
		
		if (!ArrayUtils.isEmpty(adapters)) {
			for (int i = 0; i < adapters.length; i++) {
				if (adapters[i] != null) {
					terceros.add(getTercero(adapters[i]));
				}
			}
		}
		
		return terceros;
	}
	
	protected Tercero getTercero(IThirdPartyAdapter adapter) {
		Tercero tercero = null;
		
		if (adapter != null) {
			tercero = new Tercero();
			tercero.setIdExt(adapter.getIdExt());
			tercero.setTipoPersona(adapter.getTipoPersona());
			tercero.setIdentificacion(adapter.getIdentificacion());
			tercero.setNombre(adapter.getNombre());
			tercero.setPrimerApellido(adapter.getPrimerApellido());
			tercero.setSegundoApellido(adapter.getSegundoApellido());
			tercero.setNotificacionTelematica(adapter.isNotificacionTelematica());
			tercero.setDireccionesPostales(getDireccionesPostales(adapter.getDireccionesPostales()));
			tercero.setDireccionesElectronicas(getDireccionesElectronicas(adapter.getDireccionesElectronicas()));
		}
		
		return tercero;
	}

	protected DireccionPostal[] getDireccionesPostales(IPostalAddressAdapter[] adapters) {
		DireccionPostal[] dirs = null;
		
		if (adapters != null) {
			dirs = new DireccionPostal[adapters.length];
			for (int i = 0; i < dirs.length; i++) {
				dirs[i] = getDireccionPostal(adapters[i]);
			}
		}
		
		return dirs;
	}
	
	protected DireccionPostal getDireccionPostal(IPostalAddressAdapter adapter) {
		DireccionPostal dir = null;
		
		if (adapter != null) {
			dir = new DireccionPostal();
			dir.setId(adapter.getId());
			dir.setDireccionPostal(adapter.getDireccionPostal());
			dir.setTipoVia(adapter.getTipoVia());
			dir.setVia(adapter.getVia());
			dir.setBloque(adapter.getBloque());
			dir.setPiso(adapter.getPiso());
			dir.setPuerta(adapter.getPuerta());
			dir.setCodigoPostal(adapter.getCodigoPostal());
			dir.setPoblacion(adapter.getPoblacion());
			dir.setMunicipio(adapter.getMunicipio());
			dir.setProvincia(adapter.getProvincia());
			dir.setComunidadAutonoma(adapter.getComunidadAutonoma());
			dir.setPais(adapter.getPais());
			dir.setTelefono(adapter.getTelefono());
		}

		return dir;
	}

	protected DireccionElectronica[] getDireccionesElectronicas(IElectronicAddressAdapter[] adapters) {
		DireccionElectronica[] dirs = null;
		
		if (adapters != null) {
			dirs = new DireccionElectronica[adapters.length];
			for (int i = 0; i < dirs.length; i++) {
				dirs[i] = getDireccionElectronica(adapters[i]);
			}
		}
		
		return dirs;
	}

	protected DireccionElectronica getDireccionElectronica(IElectronicAddressAdapter adapter) {
		DireccionElectronica dir = null;
		
		if (adapter != null) {
			dir = new DireccionElectronica();
			dir.setId(adapter.getId());
			dir.setTipo(adapter.getTipo());
			dir.setDireccion(adapter.getDireccion());
		}
		
		return dir; 
	}

}
