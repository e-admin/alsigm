package ieci.tecdoc.sgm.sesiones.backoffice.ws.client;

import ieci.tecdoc.sgm.core.services.admsesion.backoffice.ServicioAdministracionSesionesBackOffice;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

public class AdministracionSesionesBackOfficeWSRemoteClient 
		implements ServicioAdministracionSesionesBackOffice{
	
	private AdministracionSesionesBackOfficeWebService service = null;
	
	private String address = null;

	public AdministracionSesionesBackOfficeWSRemoteClient(){
		try {
			service = new AdministracionSesionesBackOfficeWebServiceServiceLocator().getAdministracionSesionesBackOfficeWebService();
		} catch(ServiceException e) {
			service = null;
		}
	}
	
	public AdministracionSesionesBackOfficeWSRemoteClient (String address){
		try {
			service = new AdministracionSesionesBackOfficeWebServiceServiceLocator(getAddress()).getAdministracionSesionesBackOfficeWebService();
		}catch(ServiceException e){
			service = null;
		}
	}
	
	public AdministracionSesionesBackOfficeWebService getService() {
		return service;
	}

	public void setService(AdministracionSesionesBackOfficeWebService service) {
		this.service = service;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String nuevaSesion (String usuario, String idEntidad) {
		try {
			return getService().nuevaSesion(usuario, idEntidad);
		} catch (RemoteException e) {
			return null;
		} 
	}
	
	public boolean validarSesion (String key) {
		try {
			return getService().validarSesion(key);
		} catch (RemoteException e) {
			return false;
		} 
	}
	
	public void caducarSesion (String key) {
		try {
			getService().caducarSesion(key);
		} catch (RemoteException e) {
			return;
		} 
	}
	
	public ieci.tecdoc.sgm.core.services.admsesion.backoffice.Sesion obtenerSesion(String key) {
		try {
			Sesion sesion = getService().obtenerSesion(key);
			return getSesion(sesion);
		} catch (RemoteException e) {
			return null;
		} 
	}
	
	public boolean modificarDatosSesion(String key, String datosEspecificos) {
		try {
			return getService().modificarDatosSesion(key, datosEspecificos);
		} catch (RemoteException e) {
			return false;
		} 
	}
	
	private ieci.tecdoc.sgm.core.services.admsesion.backoffice.Sesion getSesion (Sesion sesion){
		if (sesion!=null){ 
			ieci.tecdoc.sgm.core.services.admsesion.backoffice.Sesion newSesion = new ieci.tecdoc.sgm.core.services.admsesion.backoffice.Sesion();
			newSesion.setDatosEspecificos(sesion.getDatosEspecificos());
			newSesion.setIdEntidad(sesion.getIdEntidad());
			newSesion.setIdSesion(sesion.getIdSesion());
			newSesion.setUsuario(sesion.getUsuario());
			return newSesion;
		}
		return null;
	}
}
