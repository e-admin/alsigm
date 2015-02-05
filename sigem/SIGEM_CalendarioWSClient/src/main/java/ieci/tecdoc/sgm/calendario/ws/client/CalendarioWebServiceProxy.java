package ieci.tecdoc.sgm.calendario.ws.client;

public class CalendarioWebServiceProxy implements ieci.tecdoc.sgm.calendario.ws.client.CalendarioWebService {
  private String _endpoint = null;
  private ieci.tecdoc.sgm.calendario.ws.client.CalendarioWebService calendarioWebService = null;
  
  public CalendarioWebServiceProxy() {
    _initCalendarioWebServiceProxy();
  }
  
  public CalendarioWebServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initCalendarioWebServiceProxy();
  }
  
  private void _initCalendarioWebServiceProxy() {
    try {
      calendarioWebService = (new ieci.tecdoc.sgm.calendario.ws.client.CalendarioWebServiceServiceLocator()).getCalendarioWebService();
      if (calendarioWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)calendarioWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)calendarioWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (calendarioWebService != null)
      ((javax.xml.rpc.Stub)calendarioWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ieci.tecdoc.sgm.calendario.ws.client.CalendarioWebService getCalendarioWebService() {
    if (calendarioWebService == null)
      _initCalendarioWebServiceProxy();
    return calendarioWebService;
  }
  
  public ieci.tecdoc.sgm.calendario.ws.client.Calendario obtenerCalendario(ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (calendarioWebService == null)
      _initCalendarioWebServiceProxy();
    return calendarioWebService.obtenerCalendario(entidad);
  }
  
  public ieci.tecdoc.sgm.calendario.ws.client.RetornoServicio nuevoCalendario(ieci.tecdoc.sgm.calendario.ws.client.Calendario calendario, ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (calendarioWebService == null)
      _initCalendarioWebServiceProxy();
    return calendarioWebService.nuevoCalendario(calendario, entidad);
  }
  
  public ieci.tecdoc.sgm.calendario.ws.client.RetornoServicio actualizarCalendario(ieci.tecdoc.sgm.calendario.ws.client.Calendario calendario, ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (calendarioWebService == null)
      _initCalendarioWebServiceProxy();
    return calendarioWebService.actualizarCalendario(calendario, entidad);
  }
  
  public ieci.tecdoc.sgm.calendario.ws.client.RetornoServicio eliminarCalendario(boolean borrarDias, ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (calendarioWebService == null)
      _initCalendarioWebServiceProxy();
    return calendarioWebService.eliminarCalendario(borrarDias, entidad);
  }
  
  public ieci.tecdoc.sgm.calendario.ws.client.CalendarioDia obtenerDiaCalendario(java.lang.String id, ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (calendarioWebService == null)
      _initCalendarioWebServiceProxy();
    return calendarioWebService.obtenerDiaCalendario(id, entidad);
  }
  
  public ieci.tecdoc.sgm.calendario.ws.client.CalendarioDia nuevoDiaCalendario(ieci.tecdoc.sgm.calendario.ws.client.CalendarioDia dia, ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (calendarioWebService == null)
      _initCalendarioWebServiceProxy();
    return calendarioWebService.nuevoDiaCalendario(dia, entidad);
  }
  
  public ieci.tecdoc.sgm.calendario.ws.client.RetornoServicio actualizarDiaCalendario(ieci.tecdoc.sgm.calendario.ws.client.CalendarioDia dia, ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (calendarioWebService == null)
      _initCalendarioWebServiceProxy();
    return calendarioWebService.actualizarDiaCalendario(dia, entidad);
  }
  
  public ieci.tecdoc.sgm.calendario.ws.client.RetornoServicio eliminarDiaCalendario(java.lang.String id, ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (calendarioWebService == null)
      _initCalendarioWebServiceProxy();
    return calendarioWebService.eliminarDiaCalendario(id, entidad);
  }
  
  public ieci.tecdoc.sgm.calendario.ws.client.CalendarioDias obtenerDiasCalendario(int tipo, ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (calendarioWebService == null)
      _initCalendarioWebServiceProxy();
    return calendarioWebService.obtenerDiasCalendario(tipo, entidad);
  }
  
  public ieci.tecdoc.sgm.calendario.ws.client.RetornoCalendario proximoLaborable(java.lang.String fecha, ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (calendarioWebService == null)
      _initCalendarioWebServiceProxy();
    return calendarioWebService.proximoLaborable(fecha, entidad);
  }
  
  
}