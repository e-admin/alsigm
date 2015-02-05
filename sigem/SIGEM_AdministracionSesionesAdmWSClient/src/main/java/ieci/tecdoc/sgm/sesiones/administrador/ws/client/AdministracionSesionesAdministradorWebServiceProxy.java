package ieci.tecdoc.sgm.sesiones.administrador.ws.client;

public class AdministracionSesionesAdministradorWebServiceProxy implements ieci.tecdoc.sgm.sesiones.administrador.ws.client.AdministracionSesionesAdministradorWebService {
  private String _endpoint = null;
  private ieci.tecdoc.sgm.sesiones.administrador.ws.client.AdministracionSesionesAdministradorWebService administracionSesionesAdministradorWebService = null;
  
  public AdministracionSesionesAdministradorWebServiceProxy() {
    _initAdministracionSesionesAdministradorWebServiceProxy();
  }
  
  private void _initAdministracionSesionesAdministradorWebServiceProxy() {
    try {
      administracionSesionesAdministradorWebService = (new ieci.tecdoc.sgm.sesiones.administrador.ws.client.AdministracionSesionesAdministradorWebServiceServiceLocator()).getAdministracionSesionesAdministradorWebService();
      if (administracionSesionesAdministradorWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)administracionSesionesAdministradorWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)administracionSesionesAdministradorWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (administracionSesionesAdministradorWebService != null)
      ((javax.xml.rpc.Stub)administracionSesionesAdministradorWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ieci.tecdoc.sgm.sesiones.administrador.ws.client.AdministracionSesionesAdministradorWebService getAdministracionSesionesAdministradorWebService() {
    if (administracionSesionesAdministradorWebService == null)
      _initAdministracionSesionesAdministradorWebServiceProxy();
    return administracionSesionesAdministradorWebService;
  }
  
  public java.lang.String nuevaSesion(java.lang.String usuario, int tipoUsuario) throws java.rmi.RemoteException{
    if (administracionSesionesAdministradorWebService == null)
      _initAdministracionSesionesAdministradorWebServiceProxy();
    return administracionSesionesAdministradorWebService.nuevaSesion(usuario, tipoUsuario);
  }
  
  public java.lang.String nuevaSesionEntidad(java.lang.String key, java.lang.String idEntidad) throws java.rmi.RemoteException{
    if (administracionSesionesAdministradorWebService == null)
      _initAdministracionSesionesAdministradorWebServiceProxy();
    return administracionSesionesAdministradorWebService.nuevaSesionEntidad(key, idEntidad);
  }
  
  public boolean validarSesion(java.lang.String key) throws java.rmi.RemoteException{
    if (administracionSesionesAdministradorWebService == null)
      _initAdministracionSesionesAdministradorWebServiceProxy();
    return administracionSesionesAdministradorWebService.validarSesion(key);
  }
  
  public boolean validarSesionEntidad(java.lang.String key_entidad, java.lang.String idAplicacion) throws java.rmi.RemoteException{
    if (administracionSesionesAdministradorWebService == null)
      _initAdministracionSesionesAdministradorWebServiceProxy();
    return administracionSesionesAdministradorWebService.validarSesionEntidad(key_entidad, idAplicacion);
  }
  
  public void caducarSesion(java.lang.String key) throws java.rmi.RemoteException{
    if (administracionSesionesAdministradorWebService == null)
      _initAdministracionSesionesAdministradorWebServiceProxy();
    administracionSesionesAdministradorWebService.caducarSesion(key);
  }
  
  public void caducarSesionEntidad(java.lang.String key_entidad) throws java.rmi.RemoteException{
    if (administracionSesionesAdministradorWebService == null)
      _initAdministracionSesionesAdministradorWebServiceProxy();
    administracionSesionesAdministradorWebService.caducarSesionEntidad(key_entidad);
  }
  
  public ieci.tecdoc.sgm.sesiones.administrador.ws.client.Sesion obtenerSesion(java.lang.String key) throws java.rmi.RemoteException{
    if (administracionSesionesAdministradorWebService == null)
      _initAdministracionSesionesAdministradorWebServiceProxy();
    return administracionSesionesAdministradorWebService.obtenerSesion(key);
  }
  
  public ieci.tecdoc.sgm.sesiones.administrador.ws.client.Sesion obtenerSesionEntidad(java.lang.String key_entidad) throws java.rmi.RemoteException{
    if (administracionSesionesAdministradorWebService == null)
      _initAdministracionSesionesAdministradorWebServiceProxy();
    return administracionSesionesAdministradorWebService.obtenerSesionEntidad(key_entidad);
  }
  
  public boolean modificarDatosSesion(java.lang.String key, java.lang.String datosEspecificos) throws java.rmi.RemoteException{
    if (administracionSesionesAdministradorWebService == null)
      _initAdministracionSesionesAdministradorWebServiceProxy();
    return administracionSesionesAdministradorWebService.modificarDatosSesion(key, datosEspecificos);
  }
  
  
}