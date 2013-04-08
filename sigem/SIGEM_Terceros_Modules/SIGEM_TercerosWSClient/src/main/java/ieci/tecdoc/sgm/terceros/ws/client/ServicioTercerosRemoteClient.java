package ieci.tecdoc.sgm.terceros.ws.client;

import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;
import ieci.tecdoc.sgm.core.services.terceros.ServicioTerceros;
import ieci.tecdoc.sgm.core.services.terceros.TercerosException;
import ieci.tecdoc.sgm.core.services.terceros.dto.DireccionElectronica;
import ieci.tecdoc.sgm.core.services.terceros.dto.DireccionPostal;
import ieci.tecdoc.sgm.core.services.terceros.dto.Tercero;
import ieci.tecdoc.sgm.terceros.ws.client.dto.InfoDireccionElectronica;
import ieci.tecdoc.sgm.terceros.ws.client.dto.InfoDireccionPostal;
import ieci.tecdoc.sgm.terceros.ws.client.dto.InfoTercero;
import ieci.tecdoc.sgm.terceros.ws.client.dto.ListaDireccionesElectronicas;
import ieci.tecdoc.sgm.terceros.ws.client.dto.ListaDireccionesPostales;
import ieci.tecdoc.sgm.terceros.ws.client.dto.ListaTerceros;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ServicioTercerosRemoteClient implements ServicioTerceros {

	/** Servicio de acceso a Terceros. */
	private TercerosWebService service = null;
	
	
	/**
	 * Constructor.
	 *
	 */
	public ServicioTercerosRemoteClient() {
		super();
	}
	
	/**
	 * Establece el servicio de acceso a Terceros.
	 * @param service Servicio de acceso a Terceros.
	 */
	public void setService(TercerosWebService service) {
		this.service = service;
	}

	/**
	 * Obtiene la excepción del error.
	 * @param ret Retorno del servicio.
	 * @return Excepción lanzada.
	 */
	private TercerosException getTercerosException(IRetornoServicio ret) {
		return new TercerosException(Long.valueOf(ret.getErrorCode()).longValue());
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
			ListaTerceros ret = service.lookupByCode(entityId, code);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getTerceros(ret);
			} else {
				throw getTercerosException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
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
	public List lookup(String entityId, String code, boolean onlyDefaultValues) throws TercerosException {
		try {
			ListaTerceros ret = service.lookupByCodeValues(entityId, code, onlyDefaultValues);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getTerceros(ret);
			} else {
				throw getTercerosException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
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
			ListaTerceros ret = service.lookupByName(entityId, name, surname1, surname2);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getTerceros(ret);
			} else {
				throw getTercerosException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
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
			ListaTerceros ret = service.lookupByNameValues(entityId, name, surname1, surname2, 
					onlyDefaultValues);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getTerceros(ret);
			} else {
				throw getTercerosException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
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
			InfoTercero ret = service.lookupById(entityId, id);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getTercero(ret.getTercero());
			} else {
				throw getTercerosException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
	}

	/**
	 * Obtiene un tercero a partir de su identificador.
	 * @param entityId Identificador de la entidad administrativa.
	 * @param id identificador del tercero
	 * @param onlyonlyDefaultValues Indica si se cargan solamente las direcciones asociadas por defecto
	 * @return Información del tercero con sus direcciones por defecto si así se indica 
	 * @exception TercerosException si ocurre algún error.
	 */
	public Tercero lookupById(String entityId, String id, boolean onlyDefaultValues) 
			throws TercerosException {
		try {
			InfoTercero ret = service.lookupByIdValues(entityId, id, onlyDefaultValues);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getTercero(ret.getTercero());
			} else {
				throw getTercerosException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
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
			InfoTercero ret = service.lookupByIdAddresses(entityId, id, postalAddressId, electronicAddressId);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getTercero(ret.getTercero());
			} else {
				throw getTercerosException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
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
			ListaDireccionesPostales ret = service.lookupPostalAddresses(entityId, id);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getDireccionesPostales(ret);
			} else {
				throw getTercerosException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
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
			InfoDireccionPostal ret = service.lookupDefaultPostalAddress(entityId, id);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getDireccionPostal(ret.getDireccionPostal());
			} else {
				throw getTercerosException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
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
			ListaDireccionesElectronicas ret = service.lookupElectronicAddresses(entityId, id);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getDireccionesElectronicas(ret);
			} else {
				throw getTercerosException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
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
			InfoDireccionElectronica ret = service.lookupDefaultElectronicAddress(entityId, id);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getDireccionElectronica(ret.getDireccionElectronica());
			} else {
				throw getTercerosException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
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
			InfoDireccionPostal ret = service.getPostalAddress(entityId, id);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getDireccionPostal(ret.getDireccionPostal());
			} else {
				throw getTercerosException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
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
			InfoDireccionElectronica ret = service.getElectronicAddress(entityId, id);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getDireccionElectronica(ret.getDireccionElectronica());
			} else {
				throw getTercerosException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TercerosException(TercerosException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
	}

	private List getTerceros(ListaTerceros lista) {
		List terceros = new ArrayList();
		
		if ( (lista != null) && (lista.getTerceros() != null) ) {
			ieci.tecdoc.sgm.terceros.ws.client.dto.Tercero[] aux = 
				lista.getTerceros();
			for (int i = 0; i < aux.length; i++) {
				terceros.add(getTercero(aux[i]));
			}
		}
		
		return terceros;
	}
	
	private Tercero getTercero(
			ieci.tecdoc.sgm.terceros.ws.client.dto.Tercero ter) {
		
		Tercero tercero = null;
		
		if (ter != null) {
			tercero = new Tercero();
		    tercero.setIdExt(ter.getIdExt());
		    tercero.setIdentificacion(ter.getIdentificacion());
			tercero.setNombre(ter.getNombre());
			tercero.setPrimerApellido(ter.getPrimerApellido());
			tercero.setSegundoApellido(ter.getSegundoApellido());
		    tercero.setTipoPersona(ter.getTipoPersona());
		    tercero.setNotificacionTelematica(ter.isNotificacionTelematica());
		    tercero.setDireccionesPostales(getDireccionesPostales(ter.getDireccionesPostales()));
		    tercero.setDireccionesElectronicas(getDireccionesElectronicas(ter.getDireccionesElectronicas()));
		}
		
		return tercero;
	}
	
	protected DireccionPostal[] getDireccionesPostales(ListaDireccionesPostales lista) {
		DireccionPostal[] direccionesPostales = null;
		
		if ( (lista != null) && (lista.getDireccionesPostales() != null) ) {
			
			direccionesPostales = new DireccionPostal[lista.getDireccionesPostales().length];
			
			for (int i = 0; i < direccionesPostales.length; i++) {
				direccionesPostales[i] = getDireccionPostal(lista.getDireccionesPostales()[i]);
			}
		}
		
		return direccionesPostales;
	}

	protected DireccionPostal[] getDireccionesPostales(ieci.tecdoc.sgm.terceros.ws.client.dto.DireccionPostal[] dirs) {
		DireccionPostal[] direccionesPostales = null;
		
		if (dirs != null) {
			
			direccionesPostales = new DireccionPostal[dirs.length];
			
			for (int i = 0; i < direccionesPostales.length; i++) {
				direccionesPostales[i] = getDireccionPostal(dirs[i]);
			}
		}
		
		return direccionesPostales;
	}

	private DireccionPostal getDireccionPostal(ieci.tecdoc.sgm.terceros.ws.client.dto.DireccionPostal dir) {
	
		DireccionPostal direccionPostal = null;
		
		if (dir != null) {
			direccionPostal = new DireccionPostal();
			direccionPostal.setId(dir.getId());
			direccionPostal.setDireccionPostal(dir.getDireccionPostal());
			direccionPostal.setTipoVia(dir.getTipoVia());
			direccionPostal.setVia(dir.getVia());
			direccionPostal.setBloque(dir.getBloque());
			direccionPostal.setPiso(dir.getPiso());
			direccionPostal.setPuerta(dir.getPuerta());
			direccionPostal.setCodigoPostal(dir.getCodigoPostal());
			direccionPostal.setPoblacion(dir.getPoblacion());
			direccionPostal.setMunicipio(dir.getMunicipio());
			direccionPostal.setProvincia(dir.getProvincia());
			direccionPostal.setComunidadAutonoma(dir.getComunidadAutonoma());
			direccionPostal.setPais(dir.getPais());
			direccionPostal.setTelefono(dir.getTelefono());
		}
		
		return direccionPostal;
	}

	protected DireccionElectronica[] getDireccionesElectronicas(ListaDireccionesElectronicas lista) {
		DireccionElectronica[] direccionesElectronicas = null;
		
		if ( (lista != null) && (lista.getDireccionesElectronicas() != null) ) {
			
			direccionesElectronicas = new DireccionElectronica[lista.getDireccionesElectronicas().length];
			
			for (int i = 0; i < direccionesElectronicas.length; i++) {
				direccionesElectronicas[i] = getDireccionElectronica(lista.getDireccionesElectronicas()[i]);
			}
		}
		
		return direccionesElectronicas;
	}

	protected DireccionElectronica[] getDireccionesElectronicas(ieci.tecdoc.sgm.terceros.ws.client.dto.DireccionElectronica[] dirs) {
		DireccionElectronica[] direccionesElectronicas = null;
		
		if (dirs != null) {
			
			direccionesElectronicas = new DireccionElectronica[dirs.length];
			
			for (int i = 0; i < direccionesElectronicas.length; i++) {
				direccionesElectronicas[i] = getDireccionElectronica(dirs[i]);
			}
		}
		
		return direccionesElectronicas;
	}

	private DireccionElectronica getDireccionElectronica(ieci.tecdoc.sgm.terceros.ws.client.dto.DireccionElectronica dir) {
		
		DireccionElectronica direccionElectronica = null;
		
		if (dir != null) {
			direccionElectronica = new DireccionElectronica();
			direccionElectronica.setId(dir.getId());
			direccionElectronica.setTipo(dir.getTipo());
			direccionElectronica.setDireccion(dir.getDireccion());
		}
		
		return direccionElectronica;
	}
}
