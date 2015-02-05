package com.microsoft.schemas.sharepoint.soap;

public class CopySoapProxy implements com.microsoft.schemas.sharepoint.soap.CopySoap {
  private String _endpoint = null;
  private com.microsoft.schemas.sharepoint.soap.CopySoap copySoap = null;
  
  public CopySoapProxy() {
    _initCopySoapProxy();
  }
  
  public CopySoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initCopySoapProxy();
  }
  
  private void _initCopySoapProxy() {
    try {
      copySoap = (new com.microsoft.schemas.sharepoint.soap.CopyLocator()).getCopySoap();
      if (copySoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)copySoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)copySoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (copySoap != null)
      ((javax.xml.rpc.Stub)copySoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.microsoft.schemas.sharepoint.soap.CopySoap getCopySoap() {
    if (copySoap == null)
      _initCopySoapProxy();
    return copySoap;
  }
  
  public void copyIntoItemsLocal(java.lang.String sourceUrl, java.lang.String[] destinationUrls, org.apache.axis.holders.UnsignedIntHolder copyIntoItemsLocalResult, com.microsoft.schemas.sharepoint.soap.holders.CopyResultCollectionHolder results) throws java.rmi.RemoteException{
    if (copySoap == null)
      _initCopySoapProxy();
    copySoap.copyIntoItemsLocal(sourceUrl, destinationUrls, copyIntoItemsLocalResult, results);
  }
  
  public void copyIntoItems(java.lang.String sourceUrl, java.lang.String[] destinationUrls, com.microsoft.schemas.sharepoint.soap.FieldInformation[] fields, byte[] stream, org.apache.axis.holders.UnsignedIntHolder copyIntoItemsResult, com.microsoft.schemas.sharepoint.soap.holders.CopyResultCollectionHolder results) throws java.rmi.RemoteException{
    if (copySoap == null)
      _initCopySoapProxy();
    copySoap.copyIntoItems(sourceUrl, destinationUrls, fields, stream, copyIntoItemsResult, results);
  }
  
  public void getItem(java.lang.String url, org.apache.axis.holders.UnsignedIntHolder getItemResult, com.microsoft.schemas.sharepoint.soap.holders.FieldInformationCollectionHolder fields, javax.xml.rpc.holders.ByteArrayHolder stream) throws java.rmi.RemoteException{
    if (copySoap == null)
      _initCopySoapProxy();
    copySoap.getItem(url, getItemResult, fields, stream);
  }
  
  
}