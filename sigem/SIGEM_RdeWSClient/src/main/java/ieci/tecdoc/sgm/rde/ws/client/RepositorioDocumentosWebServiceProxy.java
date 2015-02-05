package ieci.tecdoc.sgm.rde.ws.client;

public class RepositorioDocumentosWebServiceProxy implements ieci.tecdoc.sgm.rde.ws.client.RepositorioDocumentosWebService {
  private String _endpoint = null;
  private ieci.tecdoc.sgm.rde.ws.client.RepositorioDocumentosWebService repositorioDocumentosWebService = null;
  
  public RepositorioDocumentosWebServiceProxy() {
    _initRepositorioDocumentosWebServiceProxy();
  }
  
  private void _initRepositorioDocumentosWebServiceProxy() {
    try {
      repositorioDocumentosWebService = (new ieci.tecdoc.sgm.rde.ws.client.RepositorioDocumentosWebServiceServiceLocator()).getRepositorioDocumentosWebService();
      if (repositorioDocumentosWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)repositorioDocumentosWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)repositorioDocumentosWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (repositorioDocumentosWebService != null)
      ((javax.xml.rpc.Stub)repositorioDocumentosWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ieci.tecdoc.sgm.rde.ws.client.RepositorioDocumentosWebService getRepositorioDocumentosWebService() {
    if (repositorioDocumentosWebService == null)
      _initRepositorioDocumentosWebServiceProxy();
    return repositorioDocumentosWebService;
  }
  
  public ieci.tecdoc.sgm.rde.ws.client.InfoDocumento recuperarDocumento(ieci.tecdoc.sgm.rde.ws.client.InfoDocumento poDocId, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (repositorioDocumentosWebService == null)
      _initRepositorioDocumentosWebServiceProxy();
    return repositorioDocumentosWebService.recuperarDocumento(poDocId, entidad);
  }
  
  public ieci.tecdoc.sgm.rde.ws.client.InfoDocumento guardarDocumento(ieci.tecdoc.sgm.rde.ws.client.InfoDocumento poDoc, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (repositorioDocumentosWebService == null)
      _initRepositorioDocumentosWebServiceProxy();
    return repositorioDocumentosWebService.guardarDocumento(poDoc, entidad);
  }
  
  public ieci.tecdoc.sgm.rde.ws.client.InfoDocumento guardarDocumentoGuid(ieci.tecdoc.sgm.rde.ws.client.InfoDocumento poDoc, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (repositorioDocumentosWebService == null)
      _initRepositorioDocumentosWebServiceProxy();
    return repositorioDocumentosWebService.guardarDocumentoGuid(poDoc, entidad);
  }
  
  public ieci.tecdoc.sgm.rde.ws.client.RetornoServicio eliminarDocumento(ieci.tecdoc.sgm.rde.ws.client.InfoDocumento poDoc, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (repositorioDocumentosWebService == null)
      _initRepositorioDocumentosWebServiceProxy();
    return repositorioDocumentosWebService.eliminarDocumento(poDoc, entidad);
  }
  
  public ieci.tecdoc.sgm.rde.ws.client.InfoDocumento obtenerHash(ieci.tecdoc.sgm.rde.ws.client.InfoDocumento poDoc, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (repositorioDocumentosWebService == null)
      _initRepositorioDocumentosWebServiceProxy();
    return repositorioDocumentosWebService.obtenerHash(poDoc, entidad);
  }
  
  public ieci.tecdoc.sgm.rde.ws.client.RetornoServicio almacenarDocumentos(ieci.tecdoc.sgm.rde.ws.client.InfoDocumentos poDocs, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (repositorioDocumentosWebService == null)
      _initRepositorioDocumentosWebServiceProxy();
    return repositorioDocumentosWebService.almacenarDocumentos(poDocs, entidad);
  }
  
  public ieci.tecdoc.sgm.rde.ws.client.RetornoServicio almacenarDocumentoTemporal(ieci.tecdoc.sgm.rde.ws.client.InfoDocumentoTemporal poDoc, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (repositorioDocumentosWebService == null)
      _initRepositorioDocumentosWebServiceProxy();
    return repositorioDocumentosWebService.almacenarDocumentoTemporal(poDoc, entidad);
  }
  
  public ieci.tecdoc.sgm.rde.ws.client.RetornoServicio eliminarDocumentoTemporal(ieci.tecdoc.sgm.rde.ws.client.InfoDocumentoTemporal poDoc, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (repositorioDocumentosWebService == null)
      _initRepositorioDocumentosWebServiceProxy();
    return repositorioDocumentosWebService.eliminarDocumentoTemporal(poDoc, entidad);
  }
  
  public ieci.tecdoc.sgm.rde.ws.client.InfoDocumentosTemporales obtenerDocumentosTemporales(ieci.tecdoc.sgm.rde.ws.client.InfoDocumentoTemporal poDoc, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (repositorioDocumentosWebService == null)
      _initRepositorioDocumentosWebServiceProxy();
    return repositorioDocumentosWebService.obtenerDocumentosTemporales(poDoc, entidad);
  }
  
  public ieci.tecdoc.sgm.rde.ws.client.InfoDocumentosTemporales obtenerDocumentosTemporalesCaducados(ieci.tecdoc.sgm.rde.ws.client.CriterioBusquedaDocs poDocs, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (repositorioDocumentosWebService == null)
      _initRepositorioDocumentosWebServiceProxy();
    return repositorioDocumentosWebService.obtenerDocumentosTemporalesCaducados(poDocs, entidad);
  }
  
  public ieci.tecdoc.sgm.rde.ws.client.ContenedorDocumento retrieveDocumentInfo(ieci.tecdoc.sgm.rde.ws.client.InfoDocumento poInfo, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (repositorioDocumentosWebService == null)
      _initRepositorioDocumentosWebServiceProxy();
    return repositorioDocumentosWebService.retrieveDocumentInfo(poInfo, entidad);
  }
  
  
}