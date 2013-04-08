package ieci.tecdoc.sgm.usuario.ws.client;

public class AdministracionUsuariosPortalWebServiceProxy implements ieci.tecdoc.sgm.usuario.ws.client.AdministracionUsuariosPortalWebService {
  private String _endpoint = null;
  private ieci.tecdoc.sgm.usuario.ws.client.AdministracionUsuariosPortalWebService administracionUsuariosPortalWebService = null;
  
  public AdministracionUsuariosPortalWebServiceProxy() {
    _initAdministracionUsuariosPortalWebServiceProxy();
  }
  
  private void _initAdministracionUsuariosPortalWebServiceProxy() {
    try {
      administracionUsuariosPortalWebService = (new ieci.tecdoc.sgm.usuario.ws.client.AdministracionUsuariosPortalWebServiceServiceLocator()).getAdministracionUsuariosPortalWebService();
      if (administracionUsuariosPortalWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)administracionUsuariosPortalWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)administracionUsuariosPortalWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (administracionUsuariosPortalWebService != null)
      ((javax.xml.rpc.Stub)administracionUsuariosPortalWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ieci.tecdoc.sgm.usuario.ws.client.AdministracionUsuariosPortalWebService getAdministracionUsuariosPortalWebService() {
    if (administracionUsuariosPortalWebService == null)
      _initAdministracionUsuariosPortalWebServiceProxy();
    return administracionUsuariosPortalWebService;
  }
  
  public ieci.tecdoc.sgm.usuario.ws.client.RetornoServicio crearUsuario(ieci.tecdoc.sgm.usuario.ws.client.Usuario user, ieci.tecdoc.sgm.usuario.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (administracionUsuariosPortalWebService == null)
      _initAdministracionUsuariosPortalWebServiceProxy();
    return administracionUsuariosPortalWebService.crearUsuario(user, entidad);
  }
  
  public ieci.tecdoc.sgm.usuario.ws.client.RetornoServicio eliminarUsuario(ieci.tecdoc.sgm.usuario.ws.client.Usuario user, ieci.tecdoc.sgm.usuario.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (administracionUsuariosPortalWebService == null)
      _initAdministracionUsuariosPortalWebServiceProxy();
    return administracionUsuariosPortalWebService.eliminarUsuario(user, entidad);
  }
  
  public ieci.tecdoc.sgm.usuario.ws.client.ListaUsuarios buscarUsuarios(ieci.tecdoc.sgm.usuario.ws.client.CriterioBusquedaUsuario criteria, ieci.tecdoc.sgm.usuario.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (administracionUsuariosPortalWebService == null)
      _initAdministracionUsuariosPortalWebServiceProxy();
    return administracionUsuariosPortalWebService.buscarUsuarios(criteria, entidad);
  }
  
  public ieci.tecdoc.sgm.usuario.ws.client.Usuario autenticarUsuario(ieci.tecdoc.sgm.usuario.ws.client.Usuario user, ieci.tecdoc.sgm.usuario.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (administracionUsuariosPortalWebService == null)
      _initAdministracionUsuariosPortalWebServiceProxy();
    return administracionUsuariosPortalWebService.autenticarUsuario(user, entidad);
  }
  
  public ieci.tecdoc.sgm.usuario.ws.client.Usuario obtenerUsuario(ieci.tecdoc.sgm.usuario.ws.client.Usuario user, ieci.tecdoc.sgm.usuario.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (administracionUsuariosPortalWebService == null)
      _initAdministracionUsuariosPortalWebServiceProxy();
    return administracionUsuariosPortalWebService.obtenerUsuario(user, entidad);
  }
  
  public ieci.tecdoc.sgm.usuario.ws.client.RetornoServicio actualizarUsuario(ieci.tecdoc.sgm.usuario.ws.client.Usuario user, ieci.tecdoc.sgm.usuario.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (administracionUsuariosPortalWebService == null)
      _initAdministracionUsuariosPortalWebServiceProxy();
    return administracionUsuariosPortalWebService.actualizarUsuario(user, entidad);
  }
  
  
}