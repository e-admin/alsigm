/**
 * WebsSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

public interface WebsSoap extends java.rmi.Remote {
    public com.microsoft.schemas.sharepoint.soap.GetWebCollectionResponseGetWebCollectionResult getWebCollection() throws java.rmi.RemoteException;
    public com.microsoft.schemas.sharepoint.soap.GetWebResponseGetWebResult getWeb(java.lang.String webUrl) throws java.rmi.RemoteException;
    public com.microsoft.schemas.sharepoint.soap.GetListTemplatesResponseGetListTemplatesResult getListTemplates() throws java.rmi.RemoteException;
    public com.microsoft.schemas.sharepoint.soap.GetAllSubWebCollectionResponseGetAllSubWebCollectionResult getAllSubWebCollection() throws java.rmi.RemoteException;
    public java.lang.String webUrlFromPageUrl(java.lang.String pageUrl) throws java.rmi.RemoteException;
    public com.microsoft.schemas.sharepoint.soap.GetContentTypesResponseGetContentTypesResult getContentTypes() throws java.rmi.RemoteException;
    public com.microsoft.schemas.sharepoint.soap.GetContentTypeResponseGetContentTypeResult getContentType(java.lang.String contentTypeId) throws java.rmi.RemoteException;
    public java.lang.String createContentType(java.lang.String displayName, java.lang.String parentType, com.microsoft.schemas.sharepoint.soap.CreateContentTypeNewFields newFields, com.microsoft.schemas.sharepoint.soap.CreateContentTypeContentTypeProperties contentTypeProperties) throws java.rmi.RemoteException;
    public com.microsoft.schemas.sharepoint.soap.UpdateContentTypeResponseUpdateContentTypeResult updateContentType(java.lang.String contentTypeId, com.microsoft.schemas.sharepoint.soap.UpdateContentTypeContentTypeProperties contentTypeProperties, com.microsoft.schemas.sharepoint.soap.UpdateContentTypeNewFields newFields, com.microsoft.schemas.sharepoint.soap.UpdateContentTypeUpdateFields updateFields, com.microsoft.schemas.sharepoint.soap.UpdateContentTypeDeleteFields deleteFields) throws java.rmi.RemoteException;
    public com.microsoft.schemas.sharepoint.soap.DeleteContentTypeResponseDeleteContentTypeResult deleteContentType(java.lang.String contentTypeId) throws java.rmi.RemoteException;
    public com.microsoft.schemas.sharepoint.soap.UpdateContentTypeXmlDocumentResponseUpdateContentTypeXmlDocumentResult updateContentTypeXmlDocument(java.lang.String contentTypeId, com.microsoft.schemas.sharepoint.soap.UpdateContentTypeXmlDocumentNewDocument newDocument) throws java.rmi.RemoteException;
    public com.microsoft.schemas.sharepoint.soap.RemoveContentTypeXmlDocumentResponseRemoveContentTypeXmlDocumentResult removeContentTypeXmlDocument(java.lang.String contentTypeId, java.lang.String documentUri) throws java.rmi.RemoteException;
    public com.microsoft.schemas.sharepoint.soap.GetColumnsResponseGetColumnsResult getColumns() throws java.rmi.RemoteException;
    public com.microsoft.schemas.sharepoint.soap.UpdateColumnsResponseUpdateColumnsResult updateColumns(com.microsoft.schemas.sharepoint.soap.UpdateColumnsNewFields newFields, com.microsoft.schemas.sharepoint.soap.UpdateColumnsUpdateFields updateFields, com.microsoft.schemas.sharepoint.soap.UpdateColumnsDeleteFields deleteFields) throws java.rmi.RemoteException;
    public com.microsoft.schemas.sharepoint.soap.CustomizedPageStatus getCustomizedPageStatus(java.lang.String fileUrl) throws java.rmi.RemoteException;
    public void revertFileContentStream(java.lang.String fileUrl) throws java.rmi.RemoteException;
    public void revertAllFileContentStreams() throws java.rmi.RemoteException;
    public void customizeCss(java.lang.String cssFile) throws java.rmi.RemoteException;
    public void revertCss(java.lang.String cssFile) throws java.rmi.RemoteException;
    public java.lang.String getActivatedFeatures() throws java.rmi.RemoteException;
}
