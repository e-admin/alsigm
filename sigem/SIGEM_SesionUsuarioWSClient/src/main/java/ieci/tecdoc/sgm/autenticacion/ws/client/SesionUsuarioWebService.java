/**
 * SesionUsuarioWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.autenticacion.ws.client;

public interface SesionUsuarioWebService extends java.rmi.Remote {
    public ieci.tecdoc.sgm.autenticacion.ws.client.IdentificadorSesion loginExternalUser(ieci.tecdoc.sgm.autenticacion.ws.client.LoginExternalUser poLogin, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.autenticacion.ws.client.IdentificadorSesion loginCertificate(ieci.tecdoc.sgm.autenticacion.ws.client.LoginCertificado poLogin, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.autenticacion.ws.client.IdentificadorSesion loginCertificateAuth(ieci.tecdoc.sgm.autenticacion.ws.client.LoginCertificadoAutoridad poLogin, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.autenticacion.ws.client.RetornoServicio logout(ieci.tecdoc.sgm.autenticacion.ws.client.IdentificadorSesion sessionId, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.autenticacion.ws.client.RetornoServicio crearSesion(ieci.tecdoc.sgm.autenticacion.ws.client.SesionUsuario poSessionUsuario, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.autenticacion.ws.client.RetornoServicio borrarSesion(ieci.tecdoc.sgm.autenticacion.ws.client.IdentificadorSesion poIdSesion, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.autenticacion.ws.client.RetornoServicio limpiarSesiones(ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.autenticacion.ws.client.RetornoServicio limpiarSesionesTimeOut(ieci.tecdoc.sgm.autenticacion.ws.client.LimpiarSesiones timeout, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.autenticacion.ws.client.SesionUsuario obtenerSesion(ieci.tecdoc.sgm.autenticacion.ws.client.IdentificadorSesion poIdSesion, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.autenticacion.ws.client.InfoUsuario getInfoUsuario(ieci.tecdoc.sgm.autenticacion.ws.client.IdentificadorSesion poIdSesion, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.autenticacion.ws.client.MetodoAutenticacion getIdMetodoAutenticacion(ieci.tecdoc.sgm.autenticacion.ws.client.IdentificadorSesion poIdSesion, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.autenticacion.ws.client.MetodoAutenticacion getMetodoAutenticacion(ieci.tecdoc.sgm.autenticacion.ws.client.IdentificadorSesion poIdSesion, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException;
}
