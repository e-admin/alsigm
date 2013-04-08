package ieci.tecdoc.sgm.terceros.ws.server;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.terceros.ServicioTerceros;
import ieci.tecdoc.sgm.core.services.terceros.TercerosException;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;
import ieci.tecdoc.sgm.terceros.ws.server.dto.DireccionElectronica;
import ieci.tecdoc.sgm.terceros.ws.server.dto.DireccionPostal;
import ieci.tecdoc.sgm.terceros.ws.server.dto.InfoDireccionElectronica;
import ieci.tecdoc.sgm.terceros.ws.server.dto.InfoDireccionPostal;
import ieci.tecdoc.sgm.terceros.ws.server.dto.InfoTercero;
import ieci.tecdoc.sgm.terceros.ws.server.dto.ListaDireccionesElectronicas;
import ieci.tecdoc.sgm.terceros.ws.server.dto.ListaDireccionesPostales;
import ieci.tecdoc.sgm.terceros.ws.server.dto.ListaTerceros;
import ieci.tecdoc.sgm.terceros.ws.server.dto.Tercero;

import java.util.List;

import javax.xml.soap.SOAPException;

import org.apache.axis.MessageContext;
import org.apache.log4j.Logger;


/**
 * Implementación del servicio web de Terceros.
 *
 */
public class TercerosWebService {

	/** Logger de la clase. */
	private static final Logger logger = 
		Logger.getLogger(TercerosWebService.class);
	
	/** Nombre del servicio. */
	private static final String SERVICE_NAME = 
		ConstantesServicios.SERVICE_THIRDPARTY;
	
	
	private ServicioTerceros getServicioTerceros() 
			throws SOAPException, SigemException {
		
		String cImpl = UtilAxis.getImplementacion(
				MessageContext.getCurrentContext());
		
		if ((cImpl == null) || ("".equals(cImpl))) {
			return LocalizadorServicios.getServicioTerceros();
		} else {
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".")
					.append(cImpl);
			return LocalizadorServicios.getServicioTerceros(
					sbImpl.toString());
		}
	}

	/**
	 * Obtiene una lista de terceros en función del código de identificación.
	 * @param entityId Identificador de la entidad administrativa.
	 * @param code Código de identificación del tercero
	 * @return Lista de terceros.
	 */
	public ListaTerceros lookupByCode(String entityId, String code) {
		
		ListaTerceros lista = new ListaTerceros();
		
		try {
			
			// Lista de terceros
			List terceros = getServicioTerceros().lookup(entityId, code);
			
			// Convertir los terceros al formato de salida
			lista.setTerceros(getTerceros(terceros));
			
		} catch (TercerosException e) {
			logger.error("Error al obtener los terceros por codigo.", e);
			return (ListaTerceros) 
				ServiciosUtils.completeReturnError(lista,e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los terceros por codigo.", e);
			return (ListaTerceros) 
				ServiciosUtils.completeReturnError(lista,e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener los terceros por codigo.", e);
			return (ListaTerceros) 
				ServiciosUtils.completeReturnError(lista);
		}
		
		return (ListaTerceros) ServiciosUtils.completeReturnOK(lista);	
	}

	/**
	 * Obtiene una lista de terceros en función del código de identificación.
	 * @param entityId Identificador de la entidad administrativa.
	 * @param code Código de identificación del tercero
	 * @param onlyDefaultValues Indica si se cargan solamente las direcciones asociadas por defecto
	 * @return Lista de terceros.
	 */
	public ListaTerceros lookupByCodeValues(String entityId, String codigo, boolean onlyDefaultValues) {
		
		ListaTerceros lista = new ListaTerceros();
		
		try {
			
			// Lista de terceros
			List terceros = getServicioTerceros().lookup(entityId, codigo, onlyDefaultValues);
			
			// Convertir los terceros al formato de salida
			lista.setTerceros(getTerceros(terceros));
			
		} catch (TercerosException e) {
			logger.error("Error al obtener los terceros por código.", e);
			return (ListaTerceros) 
				ServiciosUtils.completeReturnError(lista, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los terceros por código.", e);
			return (ListaTerceros) 
				ServiciosUtils.completeReturnError(lista, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener los terceros por código.", e);
			return (ListaTerceros) 
				ServiciosUtils.completeReturnError(lista);
		}
		
		return (ListaTerceros) ServiciosUtils.completeReturnOK(lista);	
	}


	/**
	 * Obtiene una lista de terceros en función del nombre y dos apellidos.
	 * @param entityId Identificador de la entidad administrativa.
	 * @param name Nombre del tercero
	 * @param surnam1 Primer apellido del tercero
	 * @param surname2 Segundo apellido del tercero
	 * @return Lista de terceros.
	 */
	public ListaTerceros lookupByName(String entityId, String name, String surname1, 
			String surname2) {
		
		ListaTerceros lista = new ListaTerceros();
		
		try {
			
			// Lista de terceros
			List terceros = getServicioTerceros().lookup(entityId, name, surname1, 
					surname2);
			
			// Convertir los terceros al formato de salida
			lista.setTerceros(getTerceros(terceros));
			
		} catch (TercerosException e) {
			logger.error("Error al obtener los terceros por nombre y apellidos.", e);
			return (ListaTerceros) 
				ServiciosUtils.completeReturnError(lista, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los terceros por nombre y apellidos.", e);
			return (ListaTerceros) 
				ServiciosUtils.completeReturnError(lista, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener los terceros por nombre y apellidos.", e);
			return (ListaTerceros) 
				ServiciosUtils.completeReturnError(lista);
		}
		
		return (ListaTerceros) ServiciosUtils.completeReturnOK(lista);	
	}

	/**
	 * Obtiene una lista de terceros en función del nombre y dos apellidos.
	 * @param entityId Identificador de la entidad administrativa.
	 * @param name Nombre del tercero
	 * @param surnam1 Primer apellido del tercero
	 * @param surname2 Segundo apellido del tercero
	 * @param onlyDefaultValues Indica si se cargan solamente las direcciones asociadas por defecto
	 * @return Lista de terceros.
	 */
	public ListaTerceros lookupByNameValues(String entityId, String name, String surname1, 
			String surname2, boolean onlyDefaultValues) {
		
		ListaTerceros lista = new ListaTerceros();
		
		try {
			
			// Lista de terceros
			List terceros = getServicioTerceros().lookup(entityId, name, surname1, 
					surname2, onlyDefaultValues);
			
			// Convertir los terceros al formato de salida
			lista.setTerceros(getTerceros(terceros));
			
		} catch (TercerosException e) {
			logger.error("Error al obtener los terceros por nombre y apellidos.", e);
			return (ListaTerceros) 
				ServiciosUtils.completeReturnError(lista, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los terceros por nombre y apellidos.", e);
			return (ListaTerceros) 
				ServiciosUtils.completeReturnError(lista, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener los terceros por nombre y apellidos.", e);
			return (ListaTerceros) 
				ServiciosUtils.completeReturnError(lista);
		}
		
		return (ListaTerceros) ServiciosUtils.completeReturnOK(lista);	
	}

	/**
	 * Obtiene un tercero a partir de su identificador interno.
	 * @param entityId Identificador de la entidad administrativa.
	 * @param id Identificador interno del tercero.
	 * @return Información del tercero.
	 */
	public InfoTercero lookupById(String entityId, String id) {
		
		InfoTercero infoTercero = new InfoTercero();
		
		try {
			
			// Lista de terceros
			ieci.tecdoc.sgm.core.services.terceros.dto.Tercero tercero = 
				getServicioTerceros().lookupById(entityId, id);
			
			// Convertir el tercero al formato de salida
			infoTercero.setTercero(getTercero(tercero));
			
		} catch (TercerosException e) {
			logger.error("Error al obtener el tercero.", e);
			return (InfoTercero) 
				ServiciosUtils.completeReturnError(infoTercero, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener el tercero.", e);
			return (InfoTercero) 
				ServiciosUtils.completeReturnError(infoTercero, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener el tercero.", e);
			return (InfoTercero) 
				ServiciosUtils.completeReturnError(infoTercero);
		}
		
		return (InfoTercero) ServiciosUtils.completeReturnOK(infoTercero);	
	}
	
	/**
	 * Obtiene un tercero a partir de su identificador.
	 * @param entityId Identificador de la entidad administrativa.
	 * @param id identificador del tercero
	 * @param onlyDefaultValues Indica si se cargan solamente las direcciones asociadas por defecto
	 * @return Información del tercero con sus direcciones por defecto si así se indica 
	 */
	public InfoTercero lookupByIdValues(String entityId, String id, boolean onlyDefaultValues) {
		
		InfoTercero infoTercero = new InfoTercero();
		
		try {
			
			// Lista de terceros
			ieci.tecdoc.sgm.core.services.terceros.dto.Tercero tercero = 
				getServicioTerceros().lookupById(entityId, id, onlyDefaultValues);
			
			// Convertir el tercero al formato de salida
			infoTercero.setTercero(getTercero(tercero));
			
		} catch (TercerosException e) {
			logger.error("Error al obtener el tercero.", e);
			return (InfoTercero) 
				ServiciosUtils.completeReturnError(infoTercero, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener el tercero.", e);
			return (InfoTercero) 
				ServiciosUtils.completeReturnError(infoTercero, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener el tercero.", e);
			return (InfoTercero) 
				ServiciosUtils.completeReturnError(infoTercero);
		}
		
		return (InfoTercero) ServiciosUtils.completeReturnOK(infoTercero);	
	}
	
	/**
	 * Obtiene un tercero a partir de su identificador interno.
	 * @param entityId Identificador de la entidad administrativa.
	 * @param postalAddressId Identificador de la dirección postal.
	 * @param electronicAddressId Identificador de la dirección postal.
	 * @return Información del tercero.
	 */
	public InfoTercero lookupByIdAddresses(String entityId, String id, String postalAddressId, 
			String electronicAddressId) {
		
		InfoTercero infoTercero = new InfoTercero();
		
		try {
			
			// Lista de terceros
			ieci.tecdoc.sgm.core.services.terceros.dto.Tercero tercero = 
				getServicioTerceros().lookupById(entityId, id, postalAddressId, electronicAddressId);
			
			// Convertir el tercero al formato de salida
			infoTercero.setTercero(getTercero(tercero));
			
		} catch (TercerosException e) {
			logger.error("Error al obtener el tercero.", e);
			return (InfoTercero) 
				ServiciosUtils.completeReturnError(infoTercero, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener el tercero.", e);
			return (InfoTercero) 
				ServiciosUtils.completeReturnError(infoTercero, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener el tercero.", e);
			return (InfoTercero) 
				ServiciosUtils.completeReturnError(infoTercero);
		}
		
		return (InfoTercero) ServiciosUtils.completeReturnOK(infoTercero);	
	}
	
	/**
     * Obtiene una colección de todas las direcciones postales para un tercero
     * @param entityId Identificador de la entidad administrativa.
     * @param id identificador de tercero
     * @return lista de direcciones de postales relacionadas con un tercero
     */
    public ListaDireccionesPostales lookupPostalAddresses(String entityId, String id) {
    	
    	ListaDireccionesPostales lista = new ListaDireccionesPostales();
		
		try {
			
			// Lista de direcciones postales
			ieci.tecdoc.sgm.core.services.terceros.dto.DireccionPostal[] direcciones = 
				getServicioTerceros().lookupPostalAddresses(entityId, id);
			
			// Convertir las direcciones al formato de salida
			lista.setDireccionesPostales(getDireccionesPostales(direcciones));
			
		} catch (TercerosException e) {
			logger.error("Error al obtener las direcciones postales.", e);
			return (ListaDireccionesPostales) 
				ServiciosUtils.completeReturnError(lista, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener las direcciones postales.", e);
			return (ListaDireccionesPostales) 
				ServiciosUtils.completeReturnError(lista, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener las direcciones postales.", e);
			return (ListaDireccionesPostales) 
				ServiciosUtils.completeReturnError(lista);
		}
		
		return (ListaDireccionesPostales) ServiciosUtils.completeReturnOK(lista);	
    }
    
    /**
     * Obtiene la dirección postal por defecto para un tercero
     * @param entityId Identificador de la entidad administrativa.
     * @param id identificador de tercero
     * @return dirección postal por defecto
     */
    public InfoDireccionPostal lookupDefaultPostalAddress(String entityId, String id) {
    	
		InfoDireccionPostal infoDireccion = new InfoDireccionPostal();
		
		try {
			
			// Información de la dirección postal
			ieci.tecdoc.sgm.core.services.terceros.dto.DireccionPostal direccion = 
				getServicioTerceros().lookupDefaultPostalAddress(entityId, id);
			
			// Convertir la dirección al formato de salida
			infoDireccion.setDireccionPostal(getDireccionPostal(direccion));
			
		} catch (TercerosException e) {
			logger.error("Error al obtener la dirección postal por defecto.", e);
			return (InfoDireccionPostal) 
				ServiciosUtils.completeReturnError(infoDireccion, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener la dirección postal por defecto.", e);
			return (InfoDireccionPostal) 
				ServiciosUtils.completeReturnError(infoDireccion, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener la dirección postal por defecto.", e);
			return (InfoDireccionPostal) 
				ServiciosUtils.completeReturnError(infoDireccion);
		}
		
		return (InfoDireccionPostal) ServiciosUtils.completeReturnOK(infoDireccion);	
    }
    
    /**
     * Obtiene una colección de todas las direcciones electrónicas para un tercero
     * @param entityId Identificador de la entidad administrativa.
     * @param id identificador de tercero
     * @return colección de direcciones electrónicas
     */
    public ListaDireccionesElectronicas lookupElectronicAddresses(String entityId, String id) {
    	
    	ListaDireccionesElectronicas lista = new ListaDireccionesElectronicas();
		
		try {
			
			// Lista de direcciones electrónicas
			ieci.tecdoc.sgm.core.services.terceros.dto.DireccionElectronica[] direcciones = 
				getServicioTerceros().lookupElectronicAddresses(entityId, id);
			
			// Convertir las direcciones al formato de salida
			lista.setDireccionesElectronicas(getDireccionesElectronicas(direcciones));
			
		} catch (TercerosException e) {
			logger.error("Error al obtener las direcciones electrónicas.", e);
			return (ListaDireccionesElectronicas) 
				ServiciosUtils.completeReturnError(lista, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener las direcciones electrónicas.", e);
			return (ListaDireccionesElectronicas) 
				ServiciosUtils.completeReturnError(lista, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener las direcciones electrónicas.", e);
			return (ListaDireccionesElectronicas) 
				ServiciosUtils.completeReturnError(lista);
		}
		
		return (ListaDireccionesElectronicas) ServiciosUtils.completeReturnOK(lista);	
    }

    /**
     * Obtiene la dirección electrónica por defecto para un tercero
     * @param entityId Identificador de la entidad administrativa.
     * @param id identificador de tercero
     * @return dirección electrónica
     */
    public InfoDireccionElectronica lookupDefaultElectronicAddress(String entityId, String id) {
    	
		InfoDireccionElectronica infoDireccion = new InfoDireccionElectronica();
		
		try {
			
			// Información de la dirección electrónica
			ieci.tecdoc.sgm.core.services.terceros.dto.DireccionElectronica direccion = 
				getServicioTerceros().lookupDefaultElectronicAddress(entityId, id);
			
			// Convertir la dirección al formato de salida
			infoDireccion.setDireccionElectronica(getDireccionElectronica(direccion));
			
		} catch (TercerosException e) {
			logger.error("Error al obtener la dirección electrónica por defecto.", e);
			return (InfoDireccionElectronica) 
				ServiciosUtils.completeReturnError(infoDireccion, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener la dirección electrónica por defecto.", e);
			return (InfoDireccionElectronica) 
				ServiciosUtils.completeReturnError(infoDireccion, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener la dirección electrónica por defecto.", e);
			return (InfoDireccionElectronica) 
				ServiciosUtils.completeReturnError(infoDireccion);
		}
		
		return (InfoDireccionElectronica) ServiciosUtils.completeReturnOK(infoDireccion);	
    }

	/**
	 * Obtiene una dirección postal según su identificador
	 * @param entityId Identificador de la entidad administrativa.
	 * @param id identificador de dirección postal
	 * @return dirección postal
	 */
	public InfoDireccionPostal getPostalAddress(String entityId, String id) {
		
		InfoDireccionPostal infoDireccion = new InfoDireccionPostal();
		
		try {
			
			// Información de la dirección postal
			ieci.tecdoc.sgm.core.services.terceros.dto.DireccionPostal direccion = 
				getServicioTerceros().getPostalAddress(entityId, id);
			
			// Convertir la dirección al formato de salida
			infoDireccion.setDireccionPostal(getDireccionPostal(direccion));
			
		} catch (TercerosException e) {
			logger.error("Error al obtener la dirección postal.", e);
			return (InfoDireccionPostal) 
				ServiciosUtils.completeReturnError(infoDireccion, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener la dirección postal.", e);
			return (InfoDireccionPostal) 
				ServiciosUtils.completeReturnError(infoDireccion, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener la dirección postal.", e);
			return (InfoDireccionPostal) 
				ServiciosUtils.completeReturnError(infoDireccion);
		}
		
		return (InfoDireccionPostal) ServiciosUtils.completeReturnOK(infoDireccion);	
	}

	/**
	 * Obtiene una dirección electrónica según su identificador
	 * @param entityId Identificador de la entidad administrativa.
	 * @param id identificador de dirección electrónica
	 * @return dirección electrónica
	 */
	public InfoDireccionElectronica getElectronicAddress(String entityId, String id) {
		
		InfoDireccionElectronica infoDireccion = new InfoDireccionElectronica();
		
		try {
			
			// Información de la dirección electrónica
			ieci.tecdoc.sgm.core.services.terceros.dto.DireccionElectronica direccion = 
				getServicioTerceros().getElectronicAddress(entityId, id);
			
			// Convertir la dirección al formato de salida
			infoDireccion.setDireccionElectronica(getDireccionElectronica(direccion));
			
		} catch (TercerosException e) {
			logger.error("Error al obtener la dirección electrónica.", e);
			return (InfoDireccionElectronica) 
				ServiciosUtils.completeReturnError(infoDireccion, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener la dirección electrónica.", e);
			return (InfoDireccionElectronica) 
				ServiciosUtils.completeReturnError(infoDireccion, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener la dirección electrónica.", e);
			return (InfoDireccionElectronica) 
				ServiciosUtils.completeReturnError(infoDireccion);
		}
		
		return (InfoDireccionElectronica) ServiciosUtils.completeReturnOK(infoDireccion);	
	}

	private Tercero[] getTerceros(List ters) {
		Tercero [] terceros = null;
		
		if (ters != null) {
			terceros = new Tercero[ters.size()];
			for (int i = 0; i < ters.size(); i++) {
				terceros[i] = getTercero((ieci.tecdoc.sgm.core.services.terceros.dto.Tercero)ters.get(i));
			}
		}
		
		return terceros;
	}
	
	private Tercero getTercero(
			ieci.tecdoc.sgm.core.services.terceros.dto.Tercero ter) {
		Tercero tercero = null;
		
		if (ter != null) {
			tercero = new Tercero();
		    tercero.setIdExt(ter.getIdExt());
		    tercero.setIdentificacion(ter.getIdentificacion());
			tercero.setNombre(ter.getNombre());
			tercero.setPrimerApellido(ter.getPrimerApellido());
			tercero.setSegundoApellido(ter.getSegundoApellido());
		    tercero.setNotificacionTelematica(ter.isNotificacionTelematica());
		    tercero.setTipoPersona(ter.getTipoPersona());
		    tercero.setDireccionesElectronicas(getDireccionesElectronicas(ter.getDireccionesElectronicas()));
		    tercero.setDireccionesPostales(getDireccionesPostales(ter.getDireccionesPostales()));
		}
			
		return tercero;
	}

	protected DireccionPostal[] getDireccionesPostales(ieci.tecdoc.sgm.core.services.terceros.dto.DireccionPostal[] dirs) {
		DireccionPostal[] direccionesPostales = null;
		
		if (dirs != null) {
			
			direccionesPostales = new DireccionPostal[dirs.length];
			
			for (int i = 0; i < direccionesPostales.length; i++) {
				direccionesPostales[i] = getDireccionPostal(dirs[i]);
			}
		}
		
		return direccionesPostales;
	}

	private DireccionPostal getDireccionPostal(ieci.tecdoc.sgm.core.services.terceros.dto.DireccionPostal dir) {
	
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

	protected DireccionElectronica[] getDireccionesElectronicas(ieci.tecdoc.sgm.core.services.terceros.dto.DireccionElectronica[] dirs) {
		DireccionElectronica[] direccionesElectronicas = null;
		
		if (dirs != null) {
			
			direccionesElectronicas = new DireccionElectronica[dirs.length];
			
			for (int i = 0; i < direccionesElectronicas.length; i++) {
				direccionesElectronicas[i] = getDireccionElectronica(dirs[i]);
			}
		}
		
		return direccionesElectronicas;
	}

	private DireccionElectronica getDireccionElectronica(ieci.tecdoc.sgm.core.services.terceros.dto.DireccionElectronica dir) {
		
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
