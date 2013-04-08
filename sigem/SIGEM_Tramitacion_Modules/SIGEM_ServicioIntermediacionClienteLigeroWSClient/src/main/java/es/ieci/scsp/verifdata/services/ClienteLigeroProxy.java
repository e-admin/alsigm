package es.ieci.scsp.verifdata.services;

public class ClienteLigeroProxy implements es.ieci.scsp.verifdata.services.ClienteLigero {
  private String _endpoint = null;
  private es.ieci.scsp.verifdata.services.ClienteLigero clienteLigero = null;
  
  public ClienteLigeroProxy() {
    _initClienteLigeroProxy();
  }
  
  public ClienteLigeroProxy(String endpoint) {
    _endpoint = endpoint;
    _initClienteLigeroProxy();
  }
  
  private void _initClienteLigeroProxy() {
    try {
      clienteLigero = (new es.ieci.scsp.verifdata.services.ClienteLigeroServiceLocator()).getClienteLigero();
      if (clienteLigero != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)clienteLigero)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)clienteLigero)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (clienteLigero != null)
      ((javax.xml.rpc.Stub)clienteLigero)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public es.ieci.scsp.verifdata.services.ClienteLigero getClienteLigero() {
    if (clienteLigero == null)
      _initClienteLigeroProxy();
    return clienteLigero;
  }
  
  public es.ieci.scsp.verifdata.model.dao.Servicio[] consultaProcedimientoByNIF(java.lang.String nifFuncionario, java.lang.String codigoProcedimiento) throws java.rmi.RemoteException{
    if (clienteLigero == null)
      _initClienteLigeroProxy();
    return clienteLigero.consultaProcedimientoByNIF(nifFuncionario, codigoProcedimiento);
  }
  
  
}