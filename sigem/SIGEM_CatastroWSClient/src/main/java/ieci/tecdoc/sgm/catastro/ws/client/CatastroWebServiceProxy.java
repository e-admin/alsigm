package ieci.tecdoc.sgm.catastro.ws.client;

public class CatastroWebServiceProxy implements ieci.tecdoc.sgm.catastro.ws.client.CatastroWebService {
  private String _endpoint = null;
  private ieci.tecdoc.sgm.catastro.ws.client.CatastroWebService catastroWebService = null;
  
  public CatastroWebServiceProxy() {
    _initCatastroWebServiceProxy();
  }
  
  private void _initCatastroWebServiceProxy() {
    try {
      catastroWebService = (new ieci.tecdoc.sgm.catastro.ws.client.CatastroWebServiceServiceLocator()).getCatastroWebService();
      if (catastroWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)catastroWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)catastroWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (catastroWebService != null)
      ((javax.xml.rpc.Stub)catastroWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ieci.tecdoc.sgm.catastro.ws.client.CatastroWebService getCatastroWebService() {
    if (catastroWebService == null)
      _initCatastroWebServiceProxy();
    return catastroWebService;
  }
  
  public ieci.tecdoc.sgm.catastro.ws.client.RetornoLogico validarReferenciaCatastral(ieci.tecdoc.sgm.catastro.ws.client.RetornoCadena referenciaCatastral) throws java.rmi.RemoteException{
    if (catastroWebService == null)
      _initCatastroWebServiceProxy();
    return catastroWebService.validarReferenciaCatastral(referenciaCatastral);
  }
  
  public ieci.tecdoc.sgm.catastro.ws.client.Parcelas consultarCatastro(ieci.tecdoc.sgm.catastro.ws.client.RetornoCadena referenciaCatastral) throws java.rmi.RemoteException{
    if (catastroWebService == null)
      _initCatastroWebServiceProxy();
    return catastroWebService.consultarCatastro(referenciaCatastral);
  }
  
  
}