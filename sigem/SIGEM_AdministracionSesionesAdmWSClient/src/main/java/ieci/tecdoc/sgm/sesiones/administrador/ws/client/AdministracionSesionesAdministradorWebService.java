/**
 * AdministracionSesionesAdministradorWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.sesiones.administrador.ws.client;

public interface AdministracionSesionesAdministradorWebService extends java.rmi.Remote {
    public java.lang.String nuevaSesion(java.lang.String usuario, int tipoUsuario) throws java.rmi.RemoteException;
    public java.lang.String nuevaSesionEntidad(java.lang.String key, java.lang.String idEntidad) throws java.rmi.RemoteException;
    public boolean validarSesion(java.lang.String key) throws java.rmi.RemoteException;
    public boolean validarSesionEntidad(java.lang.String key_entidad, java.lang.String idAplicacion) throws java.rmi.RemoteException;
    public void caducarSesion(java.lang.String key) throws java.rmi.RemoteException;
    public void caducarSesionEntidad(java.lang.String key_entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.sesiones.administrador.ws.client.Sesion obtenerSesion(java.lang.String key) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.sesiones.administrador.ws.client.Sesion obtenerSesionEntidad(java.lang.String key_entidad) throws java.rmi.RemoteException;
    public boolean modificarDatosSesion(java.lang.String key, java.lang.String datosEspecificos) throws java.rmi.RemoteException;
}
