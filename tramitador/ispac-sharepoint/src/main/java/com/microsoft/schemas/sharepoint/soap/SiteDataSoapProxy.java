package com.microsoft.schemas.sharepoint.soap;

public class SiteDataSoapProxy implements com.microsoft.schemas.sharepoint.soap.SiteDataSoap {
  private String _endpoint = null;
  private com.microsoft.schemas.sharepoint.soap.SiteDataSoap siteDataSoap = null;
  
  public SiteDataSoapProxy() {
    _initSiteDataSoapProxy();
  }
  
  public SiteDataSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initSiteDataSoapProxy();
  }
  
  private void _initSiteDataSoapProxy() {
    try {
      siteDataSoap = (new com.microsoft.schemas.sharepoint.soap.SiteDataLocator()).getSiteDataSoap();
      if (siteDataSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)siteDataSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)siteDataSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (siteDataSoap != null)
      ((javax.xml.rpc.Stub)siteDataSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.microsoft.schemas.sharepoint.soap.SiteDataSoap getSiteDataSoap() {
    if (siteDataSoap == null)
      _initSiteDataSoapProxy();
    return siteDataSoap;
  }
  
  public void getSiteAndWeb(java.lang.String strUrl, org.apache.axis.holders.UnsignedIntHolder getSiteAndWebResult, javax.xml.rpc.holders.StringHolder strSite, javax.xml.rpc.holders.StringHolder strWeb) throws java.rmi.RemoteException{
    if (siteDataSoap == null)
      _initSiteDataSoapProxy();
    siteDataSoap.getSiteAndWeb(strUrl, getSiteAndWebResult, strSite, strWeb);
  }
  
  public void getSite(org.apache.axis.holders.UnsignedIntHolder getSiteResult, com.microsoft.schemas.sharepoint.soap.holders._sSiteMetadataHolder sSiteMetadata, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sWebWithTimeHolder vWebs, javax.xml.rpc.holders.StringHolder strUsers, javax.xml.rpc.holders.StringHolder strGroups, com.microsoft.schemas.sharepoint.soap.holders.ArrayOfStringHolder vGroups) throws java.rmi.RemoteException{
    if (siteDataSoap == null)
      _initSiteDataSoapProxy();
    siteDataSoap.getSite(getSiteResult, sSiteMetadata, vWebs, strUsers, strGroups, vGroups);
  }
  
  public void getWeb(org.apache.axis.holders.UnsignedIntHolder getWebResult, com.microsoft.schemas.sharepoint.soap.holders._sWebMetadataHolder sWebMetadata, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sWebWithTimeHolder vWebs, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sListWithTimeHolder vLists, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sFPUrlHolder vFPUrls, javax.xml.rpc.holders.StringHolder strRoles, com.microsoft.schemas.sharepoint.soap.holders.ArrayOfStringHolder vRolesUsers, com.microsoft.schemas.sharepoint.soap.holders.ArrayOfStringHolder vRolesGroups) throws java.rmi.RemoteException{
    if (siteDataSoap == null)
      _initSiteDataSoapProxy();
    siteDataSoap.getWeb(getWebResult, sWebMetadata, vWebs, vLists, vFPUrls, strRoles, vRolesUsers, vRolesGroups);
  }
  
  public void getList(java.lang.String strListName, org.apache.axis.holders.UnsignedIntHolder getListResult, com.microsoft.schemas.sharepoint.soap.holders._sListMetadataHolder sListMetadata, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sPropertyHolder vProperties) throws java.rmi.RemoteException{
    if (siteDataSoap == null)
      _initSiteDataSoapProxy();
    siteDataSoap.getList(strListName, getListResult, sListMetadata, vProperties);
  }
  
  public java.lang.String getListItems(java.lang.String strListName, java.lang.String strQuery, java.lang.String strViewFields, org.apache.axis.types.UnsignedInt uRowLimit) throws java.rmi.RemoteException{
    if (siteDataSoap == null)
      _initSiteDataSoapProxy();
    return siteDataSoap.getListItems(strListName, strQuery, strViewFields, uRowLimit);
  }
  
  public void enumerateFolder(java.lang.String strFolderUrl, org.apache.axis.holders.UnsignedIntHolder enumerateFolderResult, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sFPUrlHolder vUrls) throws java.rmi.RemoteException{
    if (siteDataSoap == null)
      _initSiteDataSoapProxy();
    siteDataSoap.enumerateFolder(strFolderUrl, enumerateFolderResult, vUrls);
  }
  
  public void getAttachments(java.lang.String strListName, java.lang.String strItemId, org.apache.axis.holders.UnsignedIntHolder getAttachmentsResult, com.microsoft.schemas.sharepoint.soap.holders.ArrayOfStringHolder vAttachments) throws java.rmi.RemoteException{
    if (siteDataSoap == null)
      _initSiteDataSoapProxy();
    siteDataSoap.getAttachments(strListName, strItemId, getAttachmentsResult, vAttachments);
  }
  
  public void getURLSegments(java.lang.String strURL, javax.xml.rpc.holders.BooleanHolder getURLSegmentsResult, javax.xml.rpc.holders.StringHolder strWebID, javax.xml.rpc.holders.StringHolder strBucketID, javax.xml.rpc.holders.StringHolder strListID, javax.xml.rpc.holders.StringHolder strItemID) throws java.rmi.RemoteException{
    if (siteDataSoap == null)
      _initSiteDataSoapProxy();
    siteDataSoap.getURLSegments(strURL, getURLSegmentsResult, strWebID, strBucketID, strListID, strItemID);
  }
  
  public void getListCollection(org.apache.axis.holders.UnsignedIntHolder getListCollectionResult, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sListHolder vLists) throws java.rmi.RemoteException{
    if (siteDataSoap == null)
      _initSiteDataSoapProxy();
    siteDataSoap.getListCollection(getListCollectionResult, vLists);
  }
  
  public java.lang.String getContent(com.microsoft.schemas.sharepoint.soap.ObjectType objectType, java.lang.String objectId, java.lang.String folderUrl, java.lang.String itemId, boolean retrieveChildItems, boolean securityOnly, javax.xml.rpc.holders.StringHolder lastItemIdOnPage) throws java.rmi.RemoteException{
    if (siteDataSoap == null)
      _initSiteDataSoapProxy();
    return siteDataSoap.getContent(objectType, objectId, folderUrl, itemId, retrieveChildItems, securityOnly, lastItemIdOnPage);
  }
  
  public void getSiteUrl(java.lang.String url, org.apache.axis.holders.UnsignedIntHolder getSiteUrlResult, javax.xml.rpc.holders.StringHolder siteUrl, javax.xml.rpc.holders.StringHolder siteId) throws java.rmi.RemoteException{
    if (siteDataSoap == null)
      _initSiteDataSoapProxy();
    siteDataSoap.getSiteUrl(url, getSiteUrlResult, siteUrl, siteId);
  }
  
  public void getChanges(com.microsoft.schemas.sharepoint.soap.ObjectType objectType, java.lang.String contentDatabaseId, javax.xml.rpc.holders.StringHolder lastChangeId, javax.xml.rpc.holders.StringHolder currentChangeId, int timeout, javax.xml.rpc.holders.StringHolder getChangesResult, javax.xml.rpc.holders.BooleanHolder moreChanges) throws java.rmi.RemoteException{
    if (siteDataSoap == null)
      _initSiteDataSoapProxy();
    siteDataSoap.getChanges(objectType, contentDatabaseId, lastChangeId, currentChangeId, timeout, getChangesResult, moreChanges);
  }
  
  
}