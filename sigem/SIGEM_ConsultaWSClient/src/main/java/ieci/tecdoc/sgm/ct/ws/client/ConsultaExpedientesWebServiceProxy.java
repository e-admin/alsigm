package ieci.tecdoc.sgm.ct.ws.client;

public class ConsultaExpedientesWebServiceProxy implements ieci.tecdoc.sgm.ct.ws.client.ConsultaExpedientesWebService {
  private String _endpoint = null;
  private ieci.tecdoc.sgm.ct.ws.client.ConsultaExpedientesWebService consultaExpedientesWebService = null;

  public ConsultaExpedientesWebServiceProxy() {
    _initConsultaExpedientesWebServiceProxy();
  }
  
  private void _initConsultaExpedientesWebServiceProxy() {
    try {
      consultaExpedientesWebService = (new ieci.tecdoc.sgm.ct.ws.client.ConsultaExpedientesWebServiceServiceLocator()).getConsultaExpedientesWebService();
      if (consultaExpedientesWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)consultaExpedientesWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)consultaExpedientesWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }

    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }

  public String getEndpoint() {
    return _endpoint;
  }

  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (consultaExpedientesWebService != null)
      ((javax.xml.rpc.Stub)consultaExpedientesWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

  }

  public ieci.tecdoc.sgm.ct.ws.client.ConsultaExpedientesWebService getConsultaExpedientesWebService() {
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService;
  }

  public ieci.tecdoc.sgm.ct.ws.client.Expedientes consultarExpedientesNIF(ieci.tecdoc.sgm.ct.ws.client.CriterioBusquedaExpedientes poNif, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.consultarExpedientesNIF(poNif, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.Expedientes consultarExpedientes(ieci.tecdoc.sgm.ct.ws.client.CriterioBusquedaExpedientes poCriterio, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.consultarExpedientes(poCriterio, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.Url obtenerURLAportacionExpedientes() throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.obtenerURLAportacionExpedientes();
  }

  public ieci.tecdoc.sgm.ct.ws.client.Url obtenerURLNotificacionExpedientes() throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.obtenerURLNotificacionExpedientes();
  }

  public ieci.tecdoc.sgm.ct.ws.client.Url obtenerURLPagoTasas() throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.obtenerURLPagoTasas();
  }

  public ieci.tecdoc.sgm.ct.ws.client.Expediente obtenerDetalle(ieci.tecdoc.sgm.ct.ws.client.Expediente poNumExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.obtenerDetalle(poNumExpediente, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.HitosExpediente obtenerHistoricoExpediente(ieci.tecdoc.sgm.ct.ws.client.Expediente poNumExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.obtenerHistoricoExpediente(poNumExpediente, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.HitoExpediente obtenerHitoEstado(ieci.tecdoc.sgm.ct.ws.client.Expediente poNumExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.obtenerHitoEstado(poNumExpediente, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.FicherosHito obtenerFicherosHito(ieci.tecdoc.sgm.ct.ws.client.HitoExpediente poGuidHito, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.obtenerFicherosHito(poGuidHito, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.FicherosHitos obtenerFicherosHitos(ieci.tecdoc.sgm.ct.ws.client.HitosExpediente poHitos, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.obtenerFicherosHitos(poHitos, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio nuevoExpediente(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Interesado poInteresado, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.nuevoExpediente(poExpediente, poInteresado, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio eliminarExpediente(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.eliminarExpediente(poExpediente, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio nuevoInteresado(ieci.tecdoc.sgm.ct.ws.client.Interesado poInteresado, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.nuevoInteresado(poInteresado, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio eliminarInteresado(ieci.tecdoc.sgm.ct.ws.client.Interesado poInteresado, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.eliminarInteresado(poInteresado, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio eliminarInteresadoExpediente(java.lang.String numeroExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.eliminarInteresadoExpediente(numeroExpediente, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio establecerHitoActual(ieci.tecdoc.sgm.ct.ws.client.HitoExpediente poHito, ieci.tecdoc.sgm.ct.ws.client.FicherosHito poFicheros, ieci.tecdoc.sgm.ct.ws.client.Historico poHistorico, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.establecerHitoActual(poHito, poFicheros, poHistorico, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio eliminarHitoActual(ieci.tecdoc.sgm.ct.ws.client.Expediente poNumExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.eliminarHitoActual(poNumExpediente, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio nuevoHitoHistorico(ieci.tecdoc.sgm.ct.ws.client.HitoExpediente poHito, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.nuevoHitoHistorico(poHito, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio eliminarHitoHistorico(ieci.tecdoc.sgm.ct.ws.client.HitoExpediente poHito, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.eliminarHitoHistorico(poHito, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.InfoDocumento cargarDocumento(ieci.tecdoc.sgm.ct.ws.client.InfoDocumento poInfodoc, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.cargarDocumento(poInfodoc, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.InfoDocumento recogerDocumento(ieci.tecdoc.sgm.ct.ws.client.InfoDocumento poInfodoc, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.recogerDocumento(poInfodoc, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.Expediente busquedaExpediente(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.busquedaExpediente(poExpediente, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.Expedientes busquedaExpedientes(ieci.tecdoc.sgm.ct.ws.client.CriterioBusquedaExpedientes poCriterio, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.busquedaExpedientes(poCriterio, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio anexarFicherosHitoActual(ieci.tecdoc.sgm.ct.ws.client.FicherosHito poFicheros, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.anexarFicherosHitoActual(poFicheros, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.NotificacionesPendientes recogerNotificaciones(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.recogerNotificaciones(poExpediente, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio altaSolicitudSubsanacion(ieci.tecdoc.sgm.ct.ws.client.Subsanacion poSubsanacion, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.altaSolicitudSubsanacion(poSubsanacion, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.Subsanaciones obtenerSubsanacionesHitoActual(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.obtenerSubsanacionesHitoActual(poExpediente, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.Subsanaciones obtenerSubsanacionesHito(ieci.tecdoc.sgm.ct.ws.client.HitoExpediente poHitoExp, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.obtenerSubsanacionesHito(poHitoExp, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio altaSolicitudPago(ieci.tecdoc.sgm.ct.ws.client.Pago poPago, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.altaSolicitudPago(poPago, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.Pagos obtenerPagosHitoActual(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.obtenerPagosHitoActual(poExpediente, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.Pagos obtenerPagosHito(ieci.tecdoc.sgm.ct.ws.client.HitoExpediente poHitoExp, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.obtenerPagosHito(poHitoExp, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.RetornoServicio altaNotificacion(ieci.tecdoc.sgm.ct.ws.client.Notificacion poNotificacion, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.altaNotificacion(poNotificacion, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.Notificaciones obtenerNotificionesHitoActual(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.obtenerNotificionesHitoActual(poExpediente, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.Notificaciones obtenerNotificionesHito(ieci.tecdoc.sgm.ct.ws.client.HitoExpediente poHitoExp, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.obtenerNotificionesHito(poHitoExp, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.RetornoLogico existenNotificaciones(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.existenNotificaciones(poExpediente, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.RetornoLogico existenPagos(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.existenPagos(poExpediente, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.RetornoLogico existenSubsanaciones(ieci.tecdoc.sgm.ct.ws.client.Expediente poExpediente, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.existenSubsanaciones(poExpediente, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.RetornoLogico actualizarEstadoLocalGIS(java.lang.String idExpediente, java.lang.String estado, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.actualizarEstadoLocalGIS(idExpediente, estado, entidad);
  }

  public ieci.tecdoc.sgm.ct.ws.client.RetornoLogico publicarExpedienteLocalGIS(java.lang.String idExpediente, java.lang.String nif, java.lang.String tipoExpediente, java.lang.String estado, java.lang.String fecha, ieci.tecdoc.sgm.ct.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (consultaExpedientesWebService == null)
      _initConsultaExpedientesWebServiceProxy();
    return consultaExpedientesWebService.publicarExpedienteLocalGIS(idExpediente, nif, tipoExpediente, estado, fecha, entidad);
  }


}