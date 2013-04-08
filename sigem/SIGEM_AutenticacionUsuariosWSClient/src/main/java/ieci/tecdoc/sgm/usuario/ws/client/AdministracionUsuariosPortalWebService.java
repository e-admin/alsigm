/**
 * AdministracionUsuariosPortalWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.usuario.ws.client;

public interface AdministracionUsuariosPortalWebService extends java.rmi.Remote {
    public ieci.tecdoc.sgm.usuario.ws.client.RetornoServicio crearUsuario(ieci.tecdoc.sgm.usuario.ws.client.Usuario user, ieci.tecdoc.sgm.usuario.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.usuario.ws.client.RetornoServicio eliminarUsuario(ieci.tecdoc.sgm.usuario.ws.client.Usuario user, ieci.tecdoc.sgm.usuario.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.usuario.ws.client.ListaUsuarios buscarUsuarios(ieci.tecdoc.sgm.usuario.ws.client.CriterioBusquedaUsuario criteria, ieci.tecdoc.sgm.usuario.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.usuario.ws.client.Usuario autenticarUsuario(ieci.tecdoc.sgm.usuario.ws.client.Usuario user, ieci.tecdoc.sgm.usuario.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.usuario.ws.client.Usuario obtenerUsuario(ieci.tecdoc.sgm.usuario.ws.client.Usuario user, ieci.tecdoc.sgm.usuario.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.usuario.ws.client.RetornoServicio actualizarUsuario(ieci.tecdoc.sgm.usuario.ws.client.Usuario user, ieci.tecdoc.sgm.usuario.ws.client.Entidad entidad) throws java.rmi.RemoteException;
}
