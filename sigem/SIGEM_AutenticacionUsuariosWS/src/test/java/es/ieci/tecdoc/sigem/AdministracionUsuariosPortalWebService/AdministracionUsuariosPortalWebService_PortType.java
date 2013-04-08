/**
 * AdministracionUsuariosPortalWebService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService;

public interface AdministracionUsuariosPortalWebService_PortType extends java.rmi.Remote {
    public es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.RetornoServicio crearUsuario(es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Usuario user, es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.RetornoServicio eliminarUsuario(es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Usuario user, es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.ListaUsuarios buscarUsuarios(es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.CriterioBusquedaUsuario criteria, es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Usuario autenticarUsuario(es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Usuario user, es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Usuario obtenerUsuario(es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Usuario user, es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.RetornoServicio actualizarUsuario(es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Usuario user, es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Entidad entidad) throws java.rmi.RemoteException;
}
