/**
 * NotificacionesWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.nt.ws.client;

public interface NotificacionesWebService extends java.rmi.Remote {
    public ieci.tecdoc.sgm.nt.ws.client.RetornoServicio actualizaEstados(ieci.tecdoc.sgm.nt.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.nt.ws.client.RetornoServicio actualizaEstado(java.lang.String numeroExpediente, int estado, java.util.Calendar fechaActualizacion, java.lang.String nifDestino, java.lang.String notiId, ieci.tecdoc.sgm.nt.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.nt.ws.client.InfoDocumento recuperaDocumento(ieci.tecdoc.sgm.nt.ws.client.CriterioBusquedaDocumentos poCriterio, ieci.tecdoc.sgm.nt.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.nt.ws.client.Notificacion detalleNotificacion(ieci.tecdoc.sgm.nt.ws.client.IdentificadorNotificacion poIdentificador, ieci.tecdoc.sgm.nt.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.nt.ws.client.Notificacion detalleNotificacionByNotiId(java.lang.String notiId, ieci.tecdoc.sgm.nt.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.nt.ws.client.Notificaciones consultarNotificaciones(ieci.tecdoc.sgm.nt.ws.client.CriterioBusquedaNotificaciones poCriterio, ieci.tecdoc.sgm.nt.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.nt.ws.client.EstadoNotificacion obtenerEstado(ieci.tecdoc.sgm.nt.ws.client.Notificacion poIdNotificacion, ieci.tecdoc.sgm.nt.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.nt.ws.client.EstadoNotificacionBD obtenerEstadoBD(int idEstado, ieci.tecdoc.sgm.nt.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.nt.ws.client.IdentificadorNotificacion altaNotificacion(ieci.tecdoc.sgm.nt.ws.client.Notificacion poNotificacion, ieci.tecdoc.sgm.nt.ws.client.Entidad entidad) throws java.rmi.RemoteException;
}
