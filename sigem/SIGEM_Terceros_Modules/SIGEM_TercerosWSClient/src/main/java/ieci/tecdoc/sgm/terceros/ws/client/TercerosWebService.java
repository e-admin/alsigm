package ieci.tecdoc.sgm.terceros.ws.client;

import ieci.tecdoc.sgm.terceros.ws.client.dto.InfoDireccionElectronica;
import ieci.tecdoc.sgm.terceros.ws.client.dto.InfoDireccionPostal;
import ieci.tecdoc.sgm.terceros.ws.client.dto.InfoTercero;
import ieci.tecdoc.sgm.terceros.ws.client.dto.ListaDireccionesElectronicas;
import ieci.tecdoc.sgm.terceros.ws.client.dto.ListaDireccionesPostales;
import ieci.tecdoc.sgm.terceros.ws.client.dto.ListaTerceros;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TercerosWebService extends Remote {
	
	public ListaTerceros lookupByCode(String entityId, String codigo) throws RemoteException;
	
	public ListaTerceros lookupByCodeValues(String entityId, String codigo, boolean defaultValues) 
		throws RemoteException;

	public ListaTerceros lookupByName(String entityId, String nombre, String apellido1,
			String apellido2) throws RemoteException;

	public ListaTerceros lookupByNameValues(String entityId, String nombre, String apellido1,
			String apellido2, boolean defaultValues) throws RemoteException;

	public InfoTercero lookupById(String entityId, String id) throws RemoteException;

	public InfoTercero lookupByIdValues(String entityId, String id, boolean defaultValues) 
		throws RemoteException;

	public InfoTercero lookupByIdAddresses(String entityId, String id, String postalAddressId, 
			String electronicAddressId) throws RemoteException;
	
    public ListaDireccionesPostales lookupPostalAddresses(String entityId, String id) 
    	throws RemoteException;
    
    public InfoDireccionPostal lookupDefaultPostalAddress(String entityId, String id) 
    	throws RemoteException;
    
    public ListaDireccionesElectronicas lookupElectronicAddresses(String entityId, String id) 
    	throws RemoteException;

    public InfoDireccionElectronica lookupDefaultElectronicAddress(String entityId, String id) 
    	throws RemoteException;

	public InfoDireccionPostal getPostalAddress(String entityId, String id) 
		throws RemoteException;

	public InfoDireccionElectronica getElectronicAddress(String entityId, String id) 
		throws RemoteException;
	
}
