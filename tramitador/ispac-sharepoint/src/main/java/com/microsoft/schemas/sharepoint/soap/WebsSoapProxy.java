package com.microsoft.schemas.sharepoint.soap;

public class WebsSoapProxy implements com.microsoft.schemas.sharepoint.soap.WebsSoap {
  private String _endpoint = null;
  private com.microsoft.schemas.sharepoint.soap.WebsSoap websSoap = null;
  
  public WebsSoapProxy() {
    _initWebsSoapProxy();
  }
  
  public WebsSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initWebsSoapProxy();
  }
  
  private void _initWebsSoapProxy() {
    try {
      websSoap = (new com.microsoft.schemas.sharepoint.soap.WebsLocator()).getWebsSoap();
      if (websSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)websSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)websSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (websSoap != null)
      ((javax.xml.rpc.Stub)websSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.microsoft.schemas.sharepoint.soap.WebsSoap getWebsSoap() {
    if (websSoap == null)
      _initWebsSoapProxy();
    return websSoap;
  }
  
  public com.microsoft.schemas.sharepoint.soap.GetWebCollectionResponseGetWebCollectionResult getWebCollection() throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    return websSoap.getWebCollection();
  }
  
  public com.microsoft.schemas.sharepoint.soap.GetWebResponseGetWebResult getWeb(java.lang.String webUrl) throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    return websSoap.getWeb(webUrl);
  }
  
  public com.microsoft.schemas.sharepoint.soap.GetListTemplatesResponseGetListTemplatesResult getListTemplates() throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    return websSoap.getListTemplates();
  }
  
  public com.microsoft.schemas.sharepoint.soap.GetAllSubWebCollectionResponseGetAllSubWebCollectionResult getAllSubWebCollection() throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    return websSoap.getAllSubWebCollection();
  }
  
  public java.lang.String webUrlFromPageUrl(java.lang.String pageUrl) throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    return websSoap.webUrlFromPageUrl(pageUrl);
  }
  
  public com.microsoft.schemas.sharepoint.soap.GetContentTypesResponseGetContentTypesResult getContentTypes() throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    return websSoap.getContentTypes();
  }
  
  public com.microsoft.schemas.sharepoint.soap.GetContentTypeResponseGetContentTypeResult getContentType(java.lang.String contentTypeId) throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    return websSoap.getContentType(contentTypeId);
  }
  
  public java.lang.String createContentType(java.lang.String displayName, java.lang.String parentType, com.microsoft.schemas.sharepoint.soap.CreateContentTypeNewFields newFields, com.microsoft.schemas.sharepoint.soap.CreateContentTypeContentTypeProperties contentTypeProperties) throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    return websSoap.createContentType(displayName, parentType, newFields, contentTypeProperties);
  }
  
  public com.microsoft.schemas.sharepoint.soap.UpdateContentTypeResponseUpdateContentTypeResult updateContentType(java.lang.String contentTypeId, com.microsoft.schemas.sharepoint.soap.UpdateContentTypeContentTypeProperties contentTypeProperties, com.microsoft.schemas.sharepoint.soap.UpdateContentTypeNewFields newFields, com.microsoft.schemas.sharepoint.soap.UpdateContentTypeUpdateFields updateFields, com.microsoft.schemas.sharepoint.soap.UpdateContentTypeDeleteFields deleteFields) throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    return websSoap.updateContentType(contentTypeId, contentTypeProperties, newFields, updateFields, deleteFields);
  }
  
  public com.microsoft.schemas.sharepoint.soap.DeleteContentTypeResponseDeleteContentTypeResult deleteContentType(java.lang.String contentTypeId) throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    return websSoap.deleteContentType(contentTypeId);
  }
  
  public com.microsoft.schemas.sharepoint.soap.UpdateContentTypeXmlDocumentResponseUpdateContentTypeXmlDocumentResult updateContentTypeXmlDocument(java.lang.String contentTypeId, com.microsoft.schemas.sharepoint.soap.UpdateContentTypeXmlDocumentNewDocument newDocument) throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    return websSoap.updateContentTypeXmlDocument(contentTypeId, newDocument);
  }
  
  public com.microsoft.schemas.sharepoint.soap.RemoveContentTypeXmlDocumentResponseRemoveContentTypeXmlDocumentResult removeContentTypeXmlDocument(java.lang.String contentTypeId, java.lang.String documentUri) throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    return websSoap.removeContentTypeXmlDocument(contentTypeId, documentUri);
  }
  
  public com.microsoft.schemas.sharepoint.soap.GetColumnsResponseGetColumnsResult getColumns() throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    return websSoap.getColumns();
  }
  
  public com.microsoft.schemas.sharepoint.soap.UpdateColumnsResponseUpdateColumnsResult updateColumns(com.microsoft.schemas.sharepoint.soap.UpdateColumnsNewFields newFields, com.microsoft.schemas.sharepoint.soap.UpdateColumnsUpdateFields updateFields, com.microsoft.schemas.sharepoint.soap.UpdateColumnsDeleteFields deleteFields) throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    return websSoap.updateColumns(newFields, updateFields, deleteFields);
  }
  
  public com.microsoft.schemas.sharepoint.soap.CustomizedPageStatus getCustomizedPageStatus(java.lang.String fileUrl) throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    return websSoap.getCustomizedPageStatus(fileUrl);
  }
  
  public void revertFileContentStream(java.lang.String fileUrl) throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    websSoap.revertFileContentStream(fileUrl);
  }
  
  public void revertAllFileContentStreams() throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    websSoap.revertAllFileContentStreams();
  }
  
  public void customizeCss(java.lang.String cssFile) throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    websSoap.customizeCss(cssFile);
  }
  
  public void revertCss(java.lang.String cssFile) throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    websSoap.revertCss(cssFile);
  }
  
  public java.lang.String getActivatedFeatures() throws java.rmi.RemoteException{
    if (websSoap == null)
      _initWebsSoapProxy();
    return websSoap.getActivatedFeatures();
  }
  
  
}