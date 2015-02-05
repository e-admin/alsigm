/**
 * AdministracionSesionesBackOfficeWebService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService;

public interface AdministracionSesionesBackOfficeWebService_PortType extends java.rmi.Remote {
    public java.lang.String nuevaSesion(java.lang.String usuario, java.lang.String idEntidad) throws java.rmi.RemoteException;
    public boolean validarSesion(java.lang.String key) throws java.rmi.RemoteException;
    public void caducarSesion(java.lang.String key) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService.Sesion obtenerSesion(java.lang.String key) throws java.rmi.RemoteException;
    public boolean modificarDatosSesion(java.lang.String key, java.lang.String datosEspecificos) throws java.rmi.RemoteException;
}
