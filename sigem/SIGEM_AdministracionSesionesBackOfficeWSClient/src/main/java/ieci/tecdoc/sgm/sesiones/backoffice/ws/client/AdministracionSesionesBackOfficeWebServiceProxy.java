package ieci.tecdoc.sgm.sesiones.backoffice.ws.client;

public class AdministracionSesionesBackOfficeWebServiceProxy implements ieci.tecdoc.sgm.sesiones.backoffice.ws.client.AdministracionSesionesBackOfficeWebService {
  private String _endpoint = null;
  private ieci.tecdoc.sgm.sesiones.backoffice.ws.client.AdministracionSesionesBackOfficeWebService administracionSesionesBackOfficeWebService = null;
  
  public AdministracionSesionesBackOfficeWebServiceProxy() {
    _initAdministracionSesionesBackOfficeWebServiceProxy();
  }
  
  private void _initAdministracionSesionesBackOfficeWebServiceProxy() {
    try {
      administracionSesionesBackOfficeWebService = (new ieci.tecdoc.sgm.sesiones.backoffice.ws.client.AdministracionSesionesBackOfficeWebServiceServiceLocator()).getAdministracionSesionesBackOfficeWebService();
      if (administracionSesionesBackOfficeWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)administracionSesionesBackOfficeWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)administracionSesionesBackOfficeWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (administracionSesionesBackOfficeWebService != null)
      ((javax.xml.rpc.Stub)administracionSesionesBackOfficeWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ieci.tecdoc.sgm.sesiones.backoffice.ws.client.AdministracionSesionesBackOfficeWebService getAdministracionSesionesBackOfficeWebService() {
    if (administracionSesionesBackOfficeWebService == null)
      _initAdministracionSesionesBackOfficeWebServiceProxy();
    return administracionSesionesBackOfficeWebService;
  }
  
  public java.lang.String nuevaSesion(java.lang.String usuario, java.lang.String idEntidad) throws java.rmi.RemoteException{
    if (administracionSesionesBackOfficeWebService == null)
      _initAdministracionSesionesBackOfficeWebServiceProxy();
    return administracionSesionesBackOfficeWebService.nuevaSesion(usuario, idEntidad);
  }
  
  public boolean validarSesion(java.lang.String key) throws java.rmi.RemoteException{
    if (administracionSesionesBackOfficeWebService == null)
      _initAdministracionSesionesBackOfficeWebServiceProxy();
    return administracionSesionesBackOfficeWebService.validarSesion(key);
  }
  
  public void caducarSesion(java.lang.String key) throws java.rmi.RemoteException{
    if (administracionSesionesBackOfficeWebService == null)
      _initAdministracionSesionesBackOfficeWebServiceProxy();
    administracionSesionesBackOfficeWebService.caducarSesion(key);
  }
  
  public ieci.tecdoc.sgm.sesiones.backoffice.ws.client.Sesion obtenerSesion(java.lang.String key) throws java.rmi.RemoteException{
    if (administracionSesionesBackOfficeWebService == null)
      _initAdministracionSesionesBackOfficeWebServiceProxy();
    return administracionSesionesBackOfficeWebService.obtenerSesion(key);
  }
  
  public boolean modificarDatosSesion(java.lang.String key, java.lang.String datosEspecificos) throws java.rmi.RemoteException{
    if (administracionSesionesBackOfficeWebService == null)
      _initAdministracionSesionesBackOfficeWebServiceProxy();
    return administracionSesionesBackOfficeWebService.modificarDatosSesion(key, datosEspecificos);
  }
  
  
}