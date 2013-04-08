/**
 * ConsultaExpedientesWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.ct.ws.client;

public interface ConsultaExpedientesWebService extends java.rmi.Remote {
    public ieci.tecdoc.sgm.ct.ws.client.Expedientes consultarExpedientesNIF(ieci.tecdoc.sgm.ct.ws.client.CriterioBusquedaExpedientes poNif, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.Expedientes consultarExpedientes(ieci.tecdoc.sgm.ct.ws.client.CriterioBusquedaExpedientes poCriterio, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.Url obtenerURLAportacionExpedientes() throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.Url obtenerURLNotificacionExpedientes() throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.Url obtenerURLPagoTasas() throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.Expediente obtenerDetalle(ieci.tecdoc.sgm.ct.ws.client.Expediente poNumExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.HitosExpediente obtenerHistoricoExpediente(ieci.tecdoc.sgm.ct.ws.client.Expediente poNumExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.HitoExpediente obtenerHitoEstado(ieci.tecdoc.sgm.ct.ws.client.Expediente poNumExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.FicherosHito obtenerFicherosHito(ieci.tecdoc.sgm.ct.ws.client.HitoExpediente poGuidHito, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.FicherosHitos obtenerFicherosHitos(ieci.tecdoc.sgm.ct.ws.client.HitosExpediente poHitos, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio nuevoExpediente(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Interesado poInteresado, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio eliminarExpediente(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio nuevoInteresado(ieci.tecdoc.sgm.ct.ws.client.Interesado poInteresado, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio eliminarInteresado(ieci.tecdoc.sgm.ct.ws.client.Interesado poInteresado, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio eliminarInteresadoExpediente(java.lang.String numeroExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio establecerHitoActual(ieci.tecdoc.sgm.ct.ws.client.HitoExpediente poHito, ieci.tecdoc.sgm.ct.ws.client.FicherosHito poFicheros, ieci.tecdoc.sgm.ct.ws.client.Historico poHistorico, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio eliminarHitoActual(ieci.tecdoc.sgm.ct.ws.client.Expediente poNumExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio nuevoHitoHistorico(ieci.tecdoc.sgm.ct.ws.client.HitoExpediente poHito, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio eliminarHitoHistorico(ieci.tecdoc.sgm.ct.ws.client.HitoExpediente poHito, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.InfoDocumento cargarDocumento(ieci.tecdoc.sgm.ct.ws.client.InfoDocumento poInfodoc, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.InfoDocumento recogerDocumento(ieci.tecdoc.sgm.ct.ws.client.InfoDocumento poInfodoc, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.Expediente busquedaExpediente(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.Expedientes busquedaExpedientes(ieci.tecdoc.sgm.ct.ws.client.CriterioBusquedaExpedientes poCriterio, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio anexarFicherosHitoActual(ieci.tecdoc.sgm.ct.ws.client.FicherosHito poFicheros, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.NotificacionesPendientes recogerNotificaciones(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio altaSolicitudSubsanacion(ieci.tecdoc.sgm.ct.ws.client.Subsanacion poSubsanacion, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.Subsanaciones obtenerSubsanacionesHitoActual(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.Subsanaciones obtenerSubsanacionesHito(ieci.tecdoc.sgm.ct.ws.client.HitoExpediente poHitoExp, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio altaSolicitudPago(ieci.tecdoc.sgm.ct.ws.client.Pago poPago, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.Pagos obtenerPagosHitoActual(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.Pagos obtenerPagosHito(ieci.tecdoc.sgm.ct.ws.client.HitoExpediente poHitoExp, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio altaNotificacion(ieci.tecdoc.sgm.ct.ws.client.Notificacion poNotificacion, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.Notificaciones obtenerNotificionesHitoActual(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.Notificaciones obtenerNotificionesHito(ieci.tecdoc.sgm.ct.ws.client.HitoExpediente poHitoExp, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.RetornoLogico existenNotificaciones(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.RetornoLogico existenPagos(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.RetornoLogico existenSubsanaciones(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.RetornoLogico actualizarEstadoLocalGIS(java.lang.String idExpediente, java.lang.String estado, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.ct.ws.client.RetornoLogico publicarExpedienteLocalGIS(java.lang.String idExpediente, java.lang.String nif, java.lang.String tipoExpediente, java.lang.String estado, java.lang.String fecha, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException;
}
