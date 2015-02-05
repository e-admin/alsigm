/**
 * EntidadesWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.entidades.ws.client;

public interface EntidadesWebService extends java.rmi.Remote {
    public ieci.tecdoc.sgm.entidades.ws.client.Entidad nuevaEntidad(ieci.tecdoc.sgm.entidades.ws.client.Entidad poEntidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.entidades.ws.client.RetornoServicio eliminarEntidad(ieci.tecdoc.sgm.entidades.ws.client.Entidad poEntidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.entidades.ws.client.Entidad actualizarEntidad(ieci.tecdoc.sgm.entidades.ws.client.Entidad poEntidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.entidades.ws.client.Entidad obtenerEntidad(java.lang.String identificador) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.entidades.ws.client.Entidades buscarEntidades(ieci.tecdoc.sgm.entidades.ws.client.CriterioBusquedaEntidades poCriterio) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.entidades.ws.client.Entidades obtenerEntidades() throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.entidades.ws.client.RetornoCadena obtenerIdentificadorEntidad() throws java.rmi.RemoteException;
}
