package ieci.tecdoc.sgm.certificacion.ws.client;

public class CertificacionWebServiceProxy implements ieci.tecdoc.sgm.certificacion.ws.client.CertificacionWebService {
  private String _endpoint = null;
  private ieci.tecdoc.sgm.certificacion.ws.client.CertificacionWebService certificacionWebService = null;
  
  public CertificacionWebServiceProxy() {
    _initCertificacionWebServiceProxy();
  }
  
  private void _initCertificacionWebServiceProxy() {
    try {
      certificacionWebService = (new ieci.tecdoc.sgm.certificacion.ws.client.CertificacionWebServiceServiceLocator()).getCertificacionWebService();
      if (certificacionWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)certificacionWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)certificacionWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (certificacionWebService != null)
      ((javax.xml.rpc.Stub)certificacionWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ieci.tecdoc.sgm.certificacion.ws.client.CertificacionWebService getCertificacionWebService() {
    if (certificacionWebService == null)
      _initCertificacionWebServiceProxy();
    return certificacionWebService;
  }
  
  public ieci.tecdoc.sgm.certificacion.ws.client.RetornoPdf generarCertificacionPagos(ieci.tecdoc.sgm.certificacion.ws.client.pago.Pago[] pagos, ieci.tecdoc.sgm.certificacion.ws.client.Usuario usuario, ieci.tecdoc.sgm.certificacion.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (certificacionWebService == null)
      _initCertificacionWebServiceProxy();
    return certificacionWebService.generarCertificacionPagos(pagos, usuario, entidad);
  }
  
  public ieci.tecdoc.sgm.certificacion.ws.client.RetornoServicio altaCertificacion(java.lang.String idUsuario, java.lang.String idFichero, ieci.tecdoc.sgm.certificacion.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (certificacionWebService == null)
      _initCertificacionWebServiceProxy();
    return certificacionWebService.altaCertificacion(idUsuario, idFichero, entidad);
  }
  
  public ieci.tecdoc.sgm.certificacion.ws.client.RetornoServicio eliminarCertificacion(java.lang.String idFichero, ieci.tecdoc.sgm.certificacion.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (certificacionWebService == null)
      _initCertificacionWebServiceProxy();
    return certificacionWebService.eliminarCertificacion(idFichero, entidad);
  }
  
  public ieci.tecdoc.sgm.certificacion.ws.client.Certificacion obtenerCertificacion(java.lang.String idUsuario, java.lang.String idFichero, ieci.tecdoc.sgm.certificacion.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (certificacionWebService == null)
      _initCertificacionWebServiceProxy();
    return certificacionWebService.obtenerCertificacion(idUsuario, idFichero, entidad);
  }
  
  
}