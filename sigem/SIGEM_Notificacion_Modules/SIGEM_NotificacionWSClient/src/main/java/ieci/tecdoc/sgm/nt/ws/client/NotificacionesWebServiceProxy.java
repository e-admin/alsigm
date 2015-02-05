package ieci.tecdoc.sgm.nt.ws.client;

public class NotificacionesWebServiceProxy implements ieci.tecdoc.sgm.nt.ws.client.NotificacionesWebService {
  private String _endpoint = null;
  private ieci.tecdoc.sgm.nt.ws.client.NotificacionesWebService notificacionesWebService = null;
  
  public NotificacionesWebServiceProxy() {
    _initNotificacionesWebServiceProxy();
  }
  
  private void _initNotificacionesWebServiceProxy() {
    try {
      notificacionesWebService = (new ieci.tecdoc.sgm.nt.ws.client.NotificacionesWebServiceServiceLocator()).getNotificacionesWebService();
      if (notificacionesWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)notificacionesWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)notificacionesWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (notificacionesWebService != null)
      ((javax.xml.rpc.Stub)notificacionesWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ieci.tecdoc.sgm.nt.ws.client.NotificacionesWebService getNotificacionesWebService() {
    if (notificacionesWebService == null)
      _initNotificacionesWebServiceProxy();
    return notificacionesWebService;
  }
  
  public ieci.tecdoc.sgm.nt.ws.client.RetornoServicio actualizaEstados(ieci.tecdoc.sgm.nt.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (notificacionesWebService == null)
      _initNotificacionesWebServiceProxy();
    return notificacionesWebService.actualizaEstados(entidad);
  }
  
  public ieci.tecdoc.sgm.nt.ws.client.RetornoServicio actualizaEstado(java.lang.String numeroExpediente, int estado, java.util.Calendar fechaActualizacion, java.lang.String nifDestino, java.lang.String notiId, ieci.tecdoc.sgm.nt.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (notificacionesWebService == null)
      _initNotificacionesWebServiceProxy();
    return notificacionesWebService.actualizaEstado(numeroExpediente, estado, fechaActualizacion, nifDestino,notiId, entidad);
  }
  
  public ieci.tecdoc.sgm.nt.ws.client.InfoDocumento recuperaDocumento(ieci.tecdoc.sgm.nt.ws.client.CriterioBusquedaDocumentos poCriterio, ieci.tecdoc.sgm.nt.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (notificacionesWebService == null)
      _initNotificacionesWebServiceProxy();
    return notificacionesWebService.recuperaDocumento(poCriterio, entidad);
  }
  
  public ieci.tecdoc.sgm.nt.ws.client.Notificacion detalleNotificacion(ieci.tecdoc.sgm.nt.ws.client.IdentificadorNotificacion poIdentificador, ieci.tecdoc.sgm.nt.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (notificacionesWebService == null)
      _initNotificacionesWebServiceProxy();
    return notificacionesWebService.detalleNotificacion(poIdentificador, entidad);
  }
  
  public ieci.tecdoc.sgm.nt.ws.client.Notificacion detalleNotificacionByNotiId(java.lang.String notiId, ieci.tecdoc.sgm.nt.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (notificacionesWebService == null)
      _initNotificacionesWebServiceProxy();
    return notificacionesWebService.detalleNotificacionByNotiId(notiId, entidad);
  }
  
  public ieci.tecdoc.sgm.nt.ws.client.Notificaciones consultarNotificaciones(ieci.tecdoc.sgm.nt.ws.client.CriterioBusquedaNotificaciones poCriterio, ieci.tecdoc.sgm.nt.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (notificacionesWebService == null)
      _initNotificacionesWebServiceProxy();
    return notificacionesWebService.consultarNotificaciones(poCriterio, entidad);
  }
  
  public ieci.tecdoc.sgm.nt.ws.client.EstadoNotificacion obtenerEstado(ieci.tecdoc.sgm.nt.ws.client.Notificacion poIdNotificacion, ieci.tecdoc.sgm.nt.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (notificacionesWebService == null)
      _initNotificacionesWebServiceProxy();
    return notificacionesWebService.obtenerEstado(poIdNotificacion, entidad);
  }
  
  public ieci.tecdoc.sgm.nt.ws.client.EstadoNotificacionBD obtenerEstadoBD(int idEstado, ieci.tecdoc.sgm.nt.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (notificacionesWebService == null)
      _initNotificacionesWebServiceProxy();
    return notificacionesWebService.obtenerEstadoBD(idEstado, entidad);
  }
  
  public ieci.tecdoc.sgm.nt.ws.client.IdentificadorNotificacion altaNotificacion(ieci.tecdoc.sgm.nt.ws.client.Notificacion poNotificacion, ieci.tecdoc.sgm.nt.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (notificacionesWebService == null)
      _initNotificacionesWebServiceProxy();
    return notificacionesWebService.altaNotificacion(poNotificacion, entidad);
  }
  
  
}