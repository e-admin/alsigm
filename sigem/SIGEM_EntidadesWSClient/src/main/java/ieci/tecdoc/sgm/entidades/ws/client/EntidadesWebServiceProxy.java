package ieci.tecdoc.sgm.entidades.ws.client;

public class EntidadesWebServiceProxy implements ieci.tecdoc.sgm.entidades.ws.client.EntidadesWebService {
  private String _endpoint = null;
  private ieci.tecdoc.sgm.entidades.ws.client.EntidadesWebService entidadesWebService = null;
  
  public EntidadesWebServiceProxy() {
    _initEntidadesWebServiceProxy();
  }
  
  private void _initEntidadesWebServiceProxy() {
    try {
      entidadesWebService = (new ieci.tecdoc.sgm.entidades.ws.client.EntidadesWebServiceServiceLocator()).getEntidadesWebService();
      if (entidadesWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)entidadesWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)entidadesWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (entidadesWebService != null)
      ((javax.xml.rpc.Stub)entidadesWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ieci.tecdoc.sgm.entidades.ws.client.EntidadesWebService getEntidadesWebService() {
    if (entidadesWebService == null)
      _initEntidadesWebServiceProxy();
    return entidadesWebService;
  }
  
  public ieci.tecdoc.sgm.entidades.ws.client.Entidad nuevaEntidad(ieci.tecdoc.sgm.entidades.ws.client.Entidad poEntidad) throws java.rmi.RemoteException{
    if (entidadesWebService == null)
      _initEntidadesWebServiceProxy();
    return entidadesWebService.nuevaEntidad(poEntidad);
  }
  
  public ieci.tecdoc.sgm.entidades.ws.client.RetornoServicio eliminarEntidad(ieci.tecdoc.sgm.entidades.ws.client.Entidad poEntidad) throws java.rmi.RemoteException{
    if (entidadesWebService == null)
      _initEntidadesWebServiceProxy();
    return entidadesWebService.eliminarEntidad(poEntidad);
  }
  
  public ieci.tecdoc.sgm.entidades.ws.client.Entidad actualizarEntidad(ieci.tecdoc.sgm.entidades.ws.client.Entidad poEntidad) throws java.rmi.RemoteException{
    if (entidadesWebService == null)
      _initEntidadesWebServiceProxy();
    return entidadesWebService.actualizarEntidad(poEntidad);
  }
  
  public ieci.tecdoc.sgm.entidades.ws.client.Entidad obtenerEntidad(java.lang.String identificador) throws java.rmi.RemoteException{
    if (entidadesWebService == null)
      _initEntidadesWebServiceProxy();
    return entidadesWebService.obtenerEntidad(identificador);
  }
  
  public ieci.tecdoc.sgm.entidades.ws.client.Entidades buscarEntidades(ieci.tecdoc.sgm.entidades.ws.client.CriterioBusquedaEntidades poCriterio) throws java.rmi.RemoteException{
    if (entidadesWebService == null)
      _initEntidadesWebServiceProxy();
    return entidadesWebService.buscarEntidades(poCriterio);
  }
  
  public ieci.tecdoc.sgm.entidades.ws.client.Entidades obtenerEntidades() throws java.rmi.RemoteException{
    if (entidadesWebService == null)
      _initEntidadesWebServiceProxy();
    return entidadesWebService.obtenerEntidades();
  }
  
  public ieci.tecdoc.sgm.entidades.ws.client.RetornoCadena obtenerIdentificadorEntidad() throws java.rmi.RemoteException{
    if (entidadesWebService == null)
      _initEntidadesWebServiceProxy();
    return entidadesWebService.obtenerIdentificadorEntidad();
  }
  
  
}