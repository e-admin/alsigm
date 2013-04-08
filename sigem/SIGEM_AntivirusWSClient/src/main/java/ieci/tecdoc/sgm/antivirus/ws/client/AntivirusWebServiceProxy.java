package ieci.tecdoc.sgm.antivirus.ws.client;

public class AntivirusWebServiceProxy implements ieci.tecdoc.sgm.antivirus.ws.client.AntivirusWebService {
  private String _endpoint = null;
  private ieci.tecdoc.sgm.antivirus.ws.client.AntivirusWebService antivirusWebService = null;
  
  public AntivirusWebServiceProxy() {
    _initAntivirusWebServiceProxy();
  }
  
  private void _initAntivirusWebServiceProxy() {
    try {
      antivirusWebService = (new ieci.tecdoc.sgm.antivirus.ws.client.AntivirusWebServiceServiceLocator()).getAntivirusWebService();
      if (antivirusWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)antivirusWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)antivirusWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (antivirusWebService != null)
      ((javax.xml.rpc.Stub)antivirusWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ieci.tecdoc.sgm.antivirus.ws.client.AntivirusWebService getAntivirusWebService() {
    if (antivirusWebService == null)
      _initAntivirusWebServiceProxy();
    return antivirusWebService;
  }
  
  public ieci.tecdoc.sgm.antivirus.ws.client.RetornoBooleano comprobarFicheroContenido(ieci.tecdoc.sgm.antivirus.ws.client.RetornoArrayByte contenidoFichero, boolean borrar) throws java.rmi.RemoteException{
    if (antivirusWebService == null)
      _initAntivirusWebServiceProxy();
    return antivirusWebService.comprobarFicheroContenido(contenidoFichero, borrar);
  }
  
  public ieci.tecdoc.sgm.antivirus.ws.client.RetornoBooleano comprobarFicheroRuta(java.lang.String rutaFichero, boolean borrar) throws java.rmi.RemoteException{
    if (antivirusWebService == null)
      _initAntivirusWebServiceProxy();
    return antivirusWebService.comprobarFicheroRuta(rutaFichero, borrar);
  }
  
  
}