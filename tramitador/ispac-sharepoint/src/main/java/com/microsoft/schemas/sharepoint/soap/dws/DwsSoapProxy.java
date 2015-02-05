package com.microsoft.schemas.sharepoint.soap.dws;

public class DwsSoapProxy implements com.microsoft.schemas.sharepoint.soap.dws.DwsSoap {
  private String _endpoint = null;
  private com.microsoft.schemas.sharepoint.soap.dws.DwsSoap dwsSoap = null;
  
  public DwsSoapProxy() {
    _initDwsSoapProxy();
  }
  
  public DwsSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initDwsSoapProxy();
  }
  
  private void _initDwsSoapProxy() {
    try {
      dwsSoap = (new com.microsoft.schemas.sharepoint.soap.dws.DwsLocator()).getDwsSoap();
      if (dwsSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)dwsSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)dwsSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (dwsSoap != null)
      ((javax.xml.rpc.Stub)dwsSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.microsoft.schemas.sharepoint.soap.dws.DwsSoap getDwsSoap() {
    if (dwsSoap == null)
      _initDwsSoapProxy();
    return dwsSoap;
  }
  
  public java.lang.String createDws(java.lang.String name, java.lang.String users, java.lang.String title, java.lang.String documents) throws java.rmi.RemoteException{
    if (dwsSoap == null)
      _initDwsSoapProxy();
    return dwsSoap.createDws(name, users, title, documents);
  }
  
  public java.lang.String deleteDws() throws java.rmi.RemoteException{
    if (dwsSoap == null)
      _initDwsSoapProxy();
    return dwsSoap.deleteDws();
  }
  
  public java.lang.String getDwsMetaData(java.lang.String document, java.lang.String id, boolean minimal) throws java.rmi.RemoteException{
    if (dwsSoap == null)
      _initDwsSoapProxy();
    return dwsSoap.getDwsMetaData(document, id, minimal);
  }
  
  public java.lang.String getDwsData(java.lang.String document, java.lang.String lastUpdate) throws java.rmi.RemoteException{
    if (dwsSoap == null)
      _initDwsSoapProxy();
    return dwsSoap.getDwsData(document, lastUpdate);
  }
  
  public java.lang.String updateDwsData(java.lang.String updates, java.lang.String meetingInstance) throws java.rmi.RemoteException{
    if (dwsSoap == null)
      _initDwsSoapProxy();
    return dwsSoap.updateDwsData(updates, meetingInstance);
  }
  
  public java.lang.String removeDwsUser(java.lang.String id) throws java.rmi.RemoteException{
    if (dwsSoap == null)
      _initDwsSoapProxy();
    return dwsSoap.removeDwsUser(id);
  }
  
  public java.lang.String renameDws(java.lang.String title) throws java.rmi.RemoteException{
    if (dwsSoap == null)
      _initDwsSoapProxy();
    return dwsSoap.renameDws(title);
  }
  
  public java.lang.String findDwsDoc(java.lang.String id) throws java.rmi.RemoteException{
    if (dwsSoap == null)
      _initDwsSoapProxy();
    return dwsSoap.findDwsDoc(id);
  }
  
  public java.lang.String canCreateDwsUrl(java.lang.String url) throws java.rmi.RemoteException{
    if (dwsSoap == null)
      _initDwsSoapProxy();
    return dwsSoap.canCreateDwsUrl(url);
  }
  
  public java.lang.String createFolder(java.lang.String url) throws java.rmi.RemoteException{
    if (dwsSoap == null)
      _initDwsSoapProxy();
    return dwsSoap.createFolder(url);
  }
  
  public java.lang.String deleteFolder(java.lang.String url) throws java.rmi.RemoteException{
    if (dwsSoap == null)
      _initDwsSoapProxy();
    return dwsSoap.deleteFolder(url);
  }
  
  
}