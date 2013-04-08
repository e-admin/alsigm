/**
 * SiteDataSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

public interface SiteDataSoap extends java.rmi.Remote {
    public void getSiteAndWeb(java.lang.String strUrl, org.apache.axis.holders.UnsignedIntHolder getSiteAndWebResult, javax.xml.rpc.holders.StringHolder strSite, javax.xml.rpc.holders.StringHolder strWeb) throws java.rmi.RemoteException;
    public void getSite(org.apache.axis.holders.UnsignedIntHolder getSiteResult, com.microsoft.schemas.sharepoint.soap.holders._sSiteMetadataHolder sSiteMetadata, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sWebWithTimeHolder vWebs, javax.xml.rpc.holders.StringHolder strUsers, javax.xml.rpc.holders.StringHolder strGroups, com.microsoft.schemas.sharepoint.soap.holders.ArrayOfStringHolder vGroups) throws java.rmi.RemoteException;
    public void getWeb(org.apache.axis.holders.UnsignedIntHolder getWebResult, com.microsoft.schemas.sharepoint.soap.holders._sWebMetadataHolder sWebMetadata, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sWebWithTimeHolder vWebs, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sListWithTimeHolder vLists, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sFPUrlHolder vFPUrls, javax.xml.rpc.holders.StringHolder strRoles, com.microsoft.schemas.sharepoint.soap.holders.ArrayOfStringHolder vRolesUsers, com.microsoft.schemas.sharepoint.soap.holders.ArrayOfStringHolder vRolesGroups) throws java.rmi.RemoteException;
    public void getList(java.lang.String strListName, org.apache.axis.holders.UnsignedIntHolder getListResult, com.microsoft.schemas.sharepoint.soap.holders._sListMetadataHolder sListMetadata, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sPropertyHolder vProperties) throws java.rmi.RemoteException;
    public java.lang.String getListItems(java.lang.String strListName, java.lang.String strQuery, java.lang.String strViewFields, org.apache.axis.types.UnsignedInt uRowLimit) throws java.rmi.RemoteException;
    public void enumerateFolder(java.lang.String strFolderUrl, org.apache.axis.holders.UnsignedIntHolder enumerateFolderResult, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sFPUrlHolder vUrls) throws java.rmi.RemoteException;
    public void getAttachments(java.lang.String strListName, java.lang.String strItemId, org.apache.axis.holders.UnsignedIntHolder getAttachmentsResult, com.microsoft.schemas.sharepoint.soap.holders.ArrayOfStringHolder vAttachments) throws java.rmi.RemoteException;
    public void getURLSegments(java.lang.String strURL, javax.xml.rpc.holders.BooleanHolder getURLSegmentsResult, javax.xml.rpc.holders.StringHolder strWebID, javax.xml.rpc.holders.StringHolder strBucketID, javax.xml.rpc.holders.StringHolder strListID, javax.xml.rpc.holders.StringHolder strItemID) throws java.rmi.RemoteException;
    public void getListCollection(org.apache.axis.holders.UnsignedIntHolder getListCollectionResult, com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sListHolder vLists) throws java.rmi.RemoteException;
    public java.lang.String getContent(com.microsoft.schemas.sharepoint.soap.ObjectType objectType, java.lang.String objectId, java.lang.String folderUrl, java.lang.String itemId, boolean retrieveChildItems, boolean securityOnly, javax.xml.rpc.holders.StringHolder lastItemIdOnPage) throws java.rmi.RemoteException;
    public void getSiteUrl(java.lang.String url, org.apache.axis.holders.UnsignedIntHolder getSiteUrlResult, javax.xml.rpc.holders.StringHolder siteUrl, javax.xml.rpc.holders.StringHolder siteId) throws java.rmi.RemoteException;
    public void getChanges(com.microsoft.schemas.sharepoint.soap.ObjectType objectType, java.lang.String contentDatabaseId, javax.xml.rpc.holders.StringHolder lastChangeId, javax.xml.rpc.holders.StringHolder currentChangeId, int timeout, javax.xml.rpc.holders.StringHolder getChangesResult, javax.xml.rpc.holders.BooleanHolder moreChanges) throws java.rmi.RemoteException;
}
