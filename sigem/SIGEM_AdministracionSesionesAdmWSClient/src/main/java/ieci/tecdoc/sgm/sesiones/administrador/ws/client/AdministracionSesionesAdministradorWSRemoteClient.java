package ieci.tecdoc.sgm.sesiones.administrador.ws.client;

import ieci.tecdoc.sgm.core.services.admsesion.administracion.ServicioAdministracionSesionesAdministrador;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

public class AdministracionSesionesAdministradorWSRemoteClient 
	implements ServicioAdministracionSesionesAdministrador {
	
	private AdministracionSesionesAdministradorWebService service = null;
	
	private String address = null;

	public AdministracionSesionesAdministradorWSRemoteClient(){
		try {
			service = new AdministracionSesionesAdministradorWebServiceServiceLocator().getAdministracionSesionesAdministradorWebService();
		} catch(ServiceException e) {
			service = null;
		}
	}
	
	public AdministracionSesionesAdministradorWSRemoteClient (String address){
		try {
			service = new AdministracionSesionesAdministradorWebServiceServiceLocator(getAddress()).getAdministracionSesionesAdministradorWebService();
		}catch(ServiceException e){
			service = null;
		}
	}
	
	public AdministracionSesionesAdministradorWebService getService() {
		return service;
	}

	public void setService(AdministracionSesionesAdministradorWebService service) {
		this.service = service;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String nuevaSesion (String usuario, int tipoUsuario) {
		try {
			return getService().nuevaSesion(usuario, tipoUsuario);
		} catch (RemoteException e) {
			return null;
		} 
	}
	
	public String nuevaSesionEntidad (String usuario, String idEntidad) {
		try {
			return getService().nuevaSesionEntidad(usuario, idEntidad);
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
	
	public boolean validarSesionEntidad (String key_entidad, String idAplicacion) {
		try {
			return getService().validarSesionEntidad(key_entidad, idAplicacion);
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
	
	public void caducarSesionEntidad (String key_entidad) {
		try {
			getService().caducarSesionEntidad(key_entidad);
		} catch (RemoteException e) {
			return;
		} 
	}
	
	public ieci.tecdoc.sgm.core.services.admsesion.administracion.Sesion obtenerSesion(String key) {
		try {
			Sesion sesion = getService().obtenerSesion(key);
			return getSesion(sesion);
		} catch (RemoteException e) {
			return null;
		} 
	}
	
	public ieci.tecdoc.sgm.core.services.admsesion.administracion.Sesion obtenerSesionEntidad(String key_entidad) {
		try {
			Sesion sesion = getService().obtenerSesionEntidad(key_entidad);
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
	
	private ieci.tecdoc.sgm.core.services.admsesion.administracion.Sesion getSesion (Sesion sesion){
		if (sesion!=null){
			ieci.tecdoc.sgm.core.services.admsesion.administracion.Sesion newSesion = new ieci.tecdoc.sgm.core.services.admsesion.administracion.Sesion();
			newSesion.setDatosEspecificos(sesion.getDatosEspecificos());
			newSesion.setIdEntidad(sesion.getIdEntidad());
			newSesion.setIdSesion(sesion.getIdSesion());
			newSesion.setUsuario(sesion.getUsuario());
			newSesion.setTipoUsuario(sesion.getTipoUsuario());
			return newSesion;
		}
		return null;
	}
}
