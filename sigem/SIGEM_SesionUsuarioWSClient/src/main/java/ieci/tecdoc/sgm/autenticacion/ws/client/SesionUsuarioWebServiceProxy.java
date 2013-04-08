package ieci.tecdoc.sgm.autenticacion.ws.client;

public class SesionUsuarioWebServiceProxy implements ieci.tecdoc.sgm.autenticacion.ws.client.SesionUsuarioWebService {
  private String _endpoint = null;
  private ieci.tecdoc.sgm.autenticacion.ws.client.SesionUsuarioWebService sesionUsuarioWebService = null;
  
  public SesionUsuarioWebServiceProxy() {
    _initSesionUsuarioWebServiceProxy();
  }
  
  private void _initSesionUsuarioWebServiceProxy() {
    try {
      sesionUsuarioWebService = (new ieci.tecdoc.sgm.autenticacion.ws.client.SesionUsuarioWebServiceServiceLocator()).getSesionUsuarioWebService();
      if (sesionUsuarioWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)sesionUsuarioWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)sesionUsuarioWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (sesionUsuarioWebService != null)
      ((javax.xml.rpc.Stub)sesionUsuarioWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ieci.tecdoc.sgm.autenticacion.ws.client.SesionUsuarioWebService getSesionUsuarioWebService() {
    if (sesionUsuarioWebService == null)
      _initSesionUsuarioWebServiceProxy();
    return sesionUsuarioWebService;
  }
  
  public ieci.tecdoc.sgm.autenticacion.ws.client.IdentificadorSesion loginExternalUser(ieci.tecdoc.sgm.autenticacion.ws.client.LoginExternalUser poLogin, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (sesionUsuarioWebService == null)
      _initSesionUsuarioWebServiceProxy();
    return sesionUsuarioWebService.loginExternalUser(poLogin, entidad);
  }
  
  public ieci.tecdoc.sgm.autenticacion.ws.client.IdentificadorSesion loginCertificate(ieci.tecdoc.sgm.autenticacion.ws.client.LoginCertificado poLogin, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException{
    if (sesionUsuarioWebService == null)
      _initSesionUsuarioWebServiceProxy();
    return sesionUsuarioWebService.loginCertificate(poLogin, poEntidad);
  }
  
  public ieci.tecdoc.sgm.autenticacion.ws.client.IdentificadorSesion loginCertificateAuth(ieci.tecdoc.sgm.autenticacion.ws.client.LoginCertificadoAutoridad poLogin, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException{
    if (sesionUsuarioWebService == null)
      _initSesionUsuarioWebServiceProxy();
    return sesionUsuarioWebService.loginCertificateAuth(poLogin, poEntidad);
  }
  
  public ieci.tecdoc.sgm.autenticacion.ws.client.RetornoServicio logout(ieci.tecdoc.sgm.autenticacion.ws.client.IdentificadorSesion sessionId, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException{
    if (sesionUsuarioWebService == null)
      _initSesionUsuarioWebServiceProxy();
    return sesionUsuarioWebService.logout(sessionId, poEntidad);
  }
  
  public ieci.tecdoc.sgm.autenticacion.ws.client.RetornoServicio crearSesion(ieci.tecdoc.sgm.autenticacion.ws.client.SesionUsuario poSessionUsuario, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException{
    if (sesionUsuarioWebService == null)
      _initSesionUsuarioWebServiceProxy();
    return sesionUsuarioWebService.crearSesion(poSessionUsuario, poEntidad);
  }
  
  public ieci.tecdoc.sgm.autenticacion.ws.client.RetornoServicio borrarSesion(ieci.tecdoc.sgm.autenticacion.ws.client.IdentificadorSesion poIdSesion, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException{
    if (sesionUsuarioWebService == null)
      _initSesionUsuarioWebServiceProxy();
    return sesionUsuarioWebService.borrarSesion(poIdSesion, poEntidad);
  }
  
  public ieci.tecdoc.sgm.autenticacion.ws.client.RetornoServicio limpiarSesiones(ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException{
    if (sesionUsuarioWebService == null)
      _initSesionUsuarioWebServiceProxy();
    return sesionUsuarioWebService.limpiarSesiones(poEntidad);
  }
  
  public ieci.tecdoc.sgm.autenticacion.ws.client.RetornoServicio limpiarSesionesTimeOut(ieci.tecdoc.sgm.autenticacion.ws.client.LimpiarSesiones timeout, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException{
    if (sesionUsuarioWebService == null)
      _initSesionUsuarioWebServiceProxy();
    return sesionUsuarioWebService.limpiarSesionesTimeOut(timeout, poEntidad);
  }
  
  public ieci.tecdoc.sgm.autenticacion.ws.client.SesionUsuario obtenerSesion(ieci.tecdoc.sgm.autenticacion.ws.client.IdentificadorSesion poIdSesion, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException{
    if (sesionUsuarioWebService == null)
      _initSesionUsuarioWebServiceProxy();
    return sesionUsuarioWebService.obtenerSesion(poIdSesion, poEntidad);
  }
  
  public ieci.tecdoc.sgm.autenticacion.ws.client.InfoUsuario getInfoUsuario(ieci.tecdoc.sgm.autenticacion.ws.client.IdentificadorSesion poIdSesion, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException{
    if (sesionUsuarioWebService == null)
      _initSesionUsuarioWebServiceProxy();
    return sesionUsuarioWebService.getInfoUsuario(poIdSesion, poEntidad);
  }
  
  public ieci.tecdoc.sgm.autenticacion.ws.client.MetodoAutenticacion getIdMetodoAutenticacion(ieci.tecdoc.sgm.autenticacion.ws.client.IdentificadorSesion poIdSesion, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException{
    if (sesionUsuarioWebService == null)
      _initSesionUsuarioWebServiceProxy();
    return sesionUsuarioWebService.getIdMetodoAutenticacion(poIdSesion, poEntidad);
  }
  
  public ieci.tecdoc.sgm.autenticacion.ws.client.MetodoAutenticacion getMetodoAutenticacion(ieci.tecdoc.sgm.autenticacion.ws.client.IdentificadorSesion poIdSesion, ieci.tecdoc.sgm.autenticacion.ws.client.Entidad poEntidad) throws java.rmi.RemoteException{
    if (sesionUsuarioWebService == null)
      _initSesionUsuarioWebServiceProxy();
    return sesionUsuarioWebService.getMetodoAutenticacion(poIdSesion, poEntidad);
  }
  
  
}