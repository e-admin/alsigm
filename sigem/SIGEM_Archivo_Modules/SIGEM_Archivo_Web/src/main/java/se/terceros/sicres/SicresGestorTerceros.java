package se.terceros.sicres;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.terceros.ServicioTerceros;
import ieci.tecdoc.sgm.core.services.terceros.TercerosException;
import ieci.tecdoc.sgm.core.services.terceros.dto.Tercero;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.terceros.GestorTerceros;
import se.terceros.InfoTercero;
import se.terceros.InfoTerceroImpl;
import se.terceros.TipoAtributo;
import se.terceros.exceptions.GestorTercerosException;

import common.MultiEntityConstants;


/**
 * Implementación del interfaz para la gestión de terceros.
 */
public class SicresGestorTerceros implements GestorTerceros
{
	/** Logger de la clase. */
	private static Logger logger = Logger.getLogger(SicresGestorTerceros.class);
	
	/**
	 * Entidad
	 */
	private String entity=null;
	
	/**
	 * Constructor.
	 */
	public SicresGestorTerceros()
	{
	}

	
	/**
	 * Inicializa con los parámetros de configuración.
	 * @param params Parámetros de configuración.
	 */
	public void initialize(Properties params)
	{
		entity = params.getProperty(MultiEntityConstants.ENTITY_PARAM);
	}

	
	/**
	 * Recupera la información de un tercero.
	 * @param idTercero Identificador del tercero.
	 * @return Información del tercero.
	 * @throws GestorTercerosException si ocurre algún error.
	 */
	public InfoTercero recuperarTercero(String idTercero) 
		throws GestorTercerosException
	{		
		
		InfoTerceroImpl infoTercero = null;
    	
    	try {
			ServicioTerceros oServicio = LocalizadorServicios.getServicioTerceros();
			Tercero tercero = oServicio.lookupById(entity,idTercero);
    		
			infoTercero = new InfoTerceroImpl();
			infoTercero.setId(tercero.getIdExt());
			infoTercero.setIdentificacion(tercero.getIdentificacion());
			if (tercero.getDireccionPostalPredeterminada()!=null)
				infoTercero.setDireccion(tercero.getDireccionPostalPredeterminada().getDireccionPostal());
			infoTercero.setNombre(tercero.getNombre());
			infoTercero.setPrimerApellido(tercero.getPrimerApellido());
			infoTercero.setSegundoApellido(tercero.getSegundoApellido());
			infoTercero.setIdentificacion(tercero.getIdentificacion());
			
/*    		if(ProcedimientoConstants.API.equalsIgnoreCase(mode))
			{
				SigemTercerosServiceAdapter service=new SigemTercerosServiceAdapter();
				service.setDsName(dsName);
				Tercero tercero = service.lookupById(entity,idTercero);
				
				infoTercero = new InfoTerceroImpl();
				infoTercero.setId(tercero.getIdExt());
				infoTercero.setIdentificacion(tercero.getIdentificacion());
				infoTercero.setDireccion(tercero.getDireccionPostal());
				infoTercero.setNombre(tercero.getNombre());
				infoTercero.setPrimerApellido(tercero.getPrimerApellido());
				infoTercero.setSegundoApellido(tercero.getSegundoApellido());
				infoTercero.setIdentificacion(tercero.getIdentificacion());
			}
			else
			{
				TercerosWebServiceServiceLocator twsServiceLocator=new TercerosWebServiceServiceLocator();
				twsServiceLocator.setTercerosWebServiceEndpointAddress(wsdlLocation);
				TercerosWebService service=twsServiceLocator.getTercerosWebService();
				ieci.tecdoc.sgm.terceros.ws.client.dto.InfoTercero infoTerceroAux = service.lookupById(entity,idTercero);
				
				ieci.tecdoc.sgm.terceros.ws.client.dto.Tercero tercero = infoTerceroAux.getTercero();
				infoTercero = new InfoTerceroImpl();
				infoTercero.setId(tercero.getIdExt());
				infoTercero.setIdentificacion(tercero.getIdentificacion());
				infoTercero.setDireccion(tercero.getDireccionPostal());
				infoTercero.setNombre(tercero.getNombre());
				infoTercero.setPrimerApellido(tercero.getPrimerApellido());
				infoTercero.setSegundoApellido(tercero.getSegundoApellido());
				infoTercero.setIdentificacion(tercero.getIdentificacion());
			}*/
	
    	} catch (TercerosException e) {
			logger.error("Error al recuperar el tercero con id: " + idTercero, e);
			throw new GestorTercerosException(e);
    	} catch (SigemException e) {
			logger.error("Error al recuperar el tercero con id: " + idTercero, e);
			throw new GestorTercerosException(e);
    	}catch (Exception e){
			logger.error("Error al recuperar el tercero con id: " + idTercero, e);
			throw new GestorTercerosException(e);
		}
		
		return infoTercero;
	}

	/**
	 * Recupera la lista de terceros que tienen el valor valorAtrib 
	 * como subtexto en el valor del atributo que se indica en tipoAtrib.
	 * <p>Los objetos de la lista tienen que implementar el interfaz
	 * {@link InfoTercero}</p>
	 * @param tipoAtrib Tipo de atributo ({@TipoAtributo}).
	 * @param valorAtrib Valor del atributo.
	 * @return Lista de terceros.
	 * @throws GestorTercerosException si ocurre algún error.
	 */
	public List recuperarTerceros(short tipoAtrib, String valorAtrib) throws GestorTercerosException
	{
		List retTerceros = new ArrayList();
		
    	try {
			ServicioTerceros oServicio = LocalizadorServicios.getServicioTerceros();

			List terceros = null;
			
			switch (tipoAtrib)
			{
				case TipoAtributo.IDENTIFICACION:
				{
					terceros = oServicio.lookup(entity,valorAtrib); 
					break;
				}
				
				case TipoAtributo.NOMBRE:
				case TipoAtributo.RAZON_SOCIAL:
				{
					terceros = oServicio.lookup(entity,valorAtrib, null, null);
					break;
				}
				case TipoAtributo.APELLIDO1:
				{
					terceros = oServicio.lookup(entity,null, valorAtrib, null);
					break;
				}
				case TipoAtributo.APELLIDO2:
				{
					terceros = oServicio.lookup(entity,null, null, valorAtrib);
					break;
				}

				default:
					throw new GestorTercerosException("Tipo de atributo no v\u00E1lido (" + tipoAtrib + ")");
			}
			
			if (terceros!=null) {
				ListIterator it = terceros.listIterator();
				while (it.hasNext()){
					Tercero tercero = (Tercero) it.next();
					
					InfoTerceroImpl infoTercero = new InfoTerceroImpl();
					infoTercero = new InfoTerceroImpl();
					infoTercero.setId(tercero.getIdExt());
					infoTercero.setIdentificacion(tercero.getIdentificacion());
					if (tercero.getDireccionPostalPredeterminada()!=null)
						infoTercero.setDireccion(tercero.getDireccionPostalPredeterminada().getDireccionPostal());
					infoTercero.setNombre(tercero.getNombre());
					infoTercero.setPrimerApellido(tercero.getPrimerApellido());
					infoTercero.setSegundoApellido(tercero.getSegundoApellido());
					infoTercero.setIdentificacion(tercero.getIdentificacion());
					retTerceros.add(infoTercero);
				}
			} 
    		
/*    		if(ProcedimientoConstants.API.equalsIgnoreCase(mode))
			{
				SigemTercerosServiceAdapter service=new SigemTercerosServiceAdapter();
				service.setDsName(dsName);
				List terceros = null;
				
				switch (tipoAtrib)
				{
					case TipoAtributo.IDENTIFICACION:
					{
						terceros = service.lookup(entity,valorAtrib); 
						break;
					}
					
					case TipoAtributo.NOMBRE:
					case TipoAtributo.RAZON_SOCIAL:
					{
						terceros = service.lookup(entity,valorAtrib, null, null);
						break;
					}
					case TipoAtributo.APELLIDO1:
					{
						terceros = service.lookup(entity,null, valorAtrib, null);
						break;
					}
					case TipoAtributo.APELLIDO2:
					{
						terceros = service.lookup(entity,null, null, valorAtrib);
						break;
					}

					default:
						throw new GestorTercerosException("Tipo de atributo no v\u00E1lido (" + tipoAtrib + ")");
				}
				
				if (terceros!=null) {
					ListIterator it = terceros.listIterator();
					while (it.hasNext()){
						Tercero tercero = (Tercero) it.next();
						
						InfoTerceroImpl infoTercero = new InfoTerceroImpl();
						infoTercero = new InfoTerceroImpl();
						infoTercero.setId(tercero.getIdExt());
						infoTercero.setIdentificacion(tercero.getIdentificacion());
						infoTercero.setDireccion(tercero.getDireccionPostal());
						infoTercero.setNombre(tercero.getNombre());
						infoTercero.setPrimerApellido(tercero.getPrimerApellido());
						infoTercero.setSegundoApellido(tercero.getSegundoApellido());
						infoTercero.setIdentificacion(tercero.getIdentificacion());
						retTerceros.add(infoTercero);
					}
				}
			}
			else
			{
				TercerosWebServiceServiceLocator twsServiceLocator=new TercerosWebServiceServiceLocator();
				twsServiceLocator.setTercerosWebServiceEndpointAddress(wsdlLocation);
				TercerosWebService service=twsServiceLocator.getTercerosWebService();

				ListaTerceros terceros = null;
				
				switch (tipoAtrib)
				{
					case TipoAtributo.IDENTIFICACION:
					{
						terceros = service.lookupByCode(entity,valorAtrib); 
						break;
					}
					
					case TipoAtributo.NOMBRE:
					case TipoAtributo.RAZON_SOCIAL:
					{
						terceros = service.lookupByName(entity,valorAtrib, null, null);
						break;
					}
					case TipoAtributo.APELLIDO1:
					{
						terceros = service.lookupByName(entity,null, valorAtrib, null);
						break;
					}
					case TipoAtributo.APELLIDO2:
					{
						terceros = service.lookupByName(entity,null, null, valorAtrib);
						break;
					}

					default:
						throw new GestorTercerosException("Tipo de atributo no v\u00E1lido (" + tipoAtrib + ")");
				}
				
				if (terceros!=null) {
					ieci.tecdoc.sgm.terceros.ws.client.dto.Tercero [] tercerosArray = terceros.getTerceros();

					for (int i = 0; i < tercerosArray.length; i++){
						ieci.tecdoc.sgm.terceros.ws.client.dto.Tercero tercero = tercerosArray[i];
						
						InfoTerceroImpl infoTercero = new InfoTerceroImpl();
						infoTercero = new InfoTerceroImpl();
						infoTercero.setId(tercero.getIdExt());
						infoTercero.setIdentificacion(tercero.getIdentificacion());
						infoTercero.setDireccion(tercero.getDireccionPostal());
						infoTercero.setNombre(tercero.getNombre());
						infoTercero.setPrimerApellido(tercero.getPrimerApellido());
						infoTercero.setSegundoApellido(tercero.getSegundoApellido());
						infoTercero.setIdentificacion(tercero.getIdentificacion());
						retTerceros.add(infoTercero);
					}
				}
			}
		}*/
		} catch (TercerosException e) {
			logger.error("Error al recuperar los terceros: tipoAtrib=[" + tipoAtrib + "], valorAtrib=[" + valorAtrib + "]", e);
			throw new GestorTercerosException(e);
		} catch (SigemException e) {
			logger.error("Error al recuperar los terceros: tipoAtrib=[" + tipoAtrib + "], valorAtrib=[" + valorAtrib + "]", e);
			throw new GestorTercerosException(e);
		} catch (GestorTercerosException e){
			logger.error("Error al recuperar los terceros: tipoAtrib=[" + tipoAtrib + "], valorAtrib=[" + valorAtrib + "]", e);
			throw e;
		} catch (Exception e){
			logger.error("Error al recuperar los terceros: tipoAtrib=[" + tipoAtrib + "], valorAtrib=[" + valorAtrib + "]", e);
			throw new GestorTercerosException(e);
		}
		
		return retTerceros;
	}

	
	/**
	 * Recupera la lista de terceros.
	 * <p>Los objetos de la lista tienen que implementar el interfaz
	 * {@link InfoTercero}</p>
	 * @param nombre Nombre del tercero.
	 * @param apellido1 Primer apellido del tercero.
	 * @param apellido2 Segundo apellido del tercero.
	 * @return Lista de terceros.
	 * @throws GestorTercerosException si ocurre algún error.
	 */
	public List recuperarTerceros(String nombre, String apellido1, String apellido2) throws GestorTercerosException
	{
		
		List lista = new ArrayList();
    	
    	try {

			ServicioTerceros oServicio = LocalizadorServicios.getServicioTerceros();
			List terceros = oServicio.lookup(entity, nombre, apellido1, apellido2 );
			
			ListIterator it = terceros.listIterator();
			while (it.hasNext()){
				Tercero tercero = (Tercero) it.next();
				
				InfoTerceroImpl infoTercero = new InfoTerceroImpl();
				infoTercero = new InfoTerceroImpl();
				infoTercero.setId(tercero.getIdExt());
				infoTercero.setIdentificacion(tercero.getIdentificacion());
				if (tercero.getDireccionPostalPredeterminada()!=null)
					infoTercero.setDireccion(tercero.getDireccionPostalPredeterminada().getDireccionPostal());
				infoTercero.setNombre(tercero.getNombre());
				infoTercero.setPrimerApellido(tercero.getPrimerApellido());
				infoTercero.setSegundoApellido(tercero.getSegundoApellido());
				infoTercero.setIdentificacion(tercero.getIdentificacion());
				lista.add(infoTercero);
			}
			
/*    		if(ProcedimientoConstants.API.equalsIgnoreCase(mode))
			{
				SigemTercerosServiceAdapter service=new SigemTercerosServiceAdapter();
				service.setDsName(dsName);
				List terceros = service.lookup( entity, nombre, apellido1, apellido2 );
				
				ListIterator it = terceros.listIterator();
				while (it.hasNext()){
					Tercero tercero = (Tercero) it.next();
					
					InfoTerceroImpl infoTercero = new InfoTerceroImpl();
					infoTercero = new InfoTerceroImpl();
					infoTercero.setId(tercero.getIdExt());
					infoTercero.setIdentificacion(tercero.getIdentificacion());
					infoTercero.setDireccion(tercero.getDireccionPostal());
					infoTercero.setNombre(tercero.getNombre());
					infoTercero.setPrimerApellido(tercero.getPrimerApellido());
					infoTercero.setSegundoApellido(tercero.getSegundoApellido());
					infoTercero.setIdentificacion(tercero.getIdentificacion());
					lista.add(infoTercero);
				}
			}
			else
			{
				TercerosWebServiceServiceLocator twsServiceLocator=new TercerosWebServiceServiceLocator();
				twsServiceLocator.setTercerosWebServiceEndpointAddress(wsdlLocation);
				TercerosWebService service=twsServiceLocator.getTercerosWebService();
				ListaTerceros terceros = service.lookupByName(entity,nombre, apellido1, apellido2 );
				ieci.tecdoc.sgm.terceros.ws.client.dto.Tercero [] tercerosArray = terceros.getTerceros();

				for (int i = 0; i < tercerosArray.length; i++){
					ieci.tecdoc.sgm.terceros.ws.client.dto.Tercero tercero = tercerosArray[i];
					
					InfoTerceroImpl infoTercero = new InfoTerceroImpl();
					infoTercero = new InfoTerceroImpl();
					infoTercero.setId(tercero.getIdExt());
					infoTercero.setIdentificacion(tercero.getIdentificacion());
					infoTercero.setDireccion(tercero.getDireccionPostal());
					infoTercero.setNombre(tercero.getNombre());
					infoTercero.setPrimerApellido(tercero.getPrimerApellido());
					infoTercero.setSegundoApellido(tercero.getSegundoApellido());
					infoTercero.setIdentificacion(tercero.getIdentificacion());
					lista.add(infoTercero);
				}
			}
		}*/
		} catch (TercerosException e) {
			logger.error("Error al recuperar los terceros: nombre=[" + nombre + "], apellido1=[" + apellido1 + "], apellido2=[" + apellido2 + "]", e);
			throw new GestorTercerosException(e);
		} catch (SigemException e) {
			logger.error("Error al recuperar los terceros: nombre=[" + nombre + "], apellido1=[" + apellido1 + "], apellido2=[" + apellido2 + "]", e);
			throw new GestorTercerosException(e);
		} catch (Exception e) {
			logger.error("Error al recuperar los terceros: nombre=[" + nombre + "], apellido1=[" + apellido1 + "], apellido2=[" + apellido2 + "]", e);
			throw new GestorTercerosException(e);
		}
		
		return lista;
	}
}
