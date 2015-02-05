/**
 * CopySoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

public interface CopySoap extends java.rmi.Remote {
    public void copyIntoItemsLocal(java.lang.String sourceUrl, java.lang.String[] destinationUrls, org.apache.axis.holders.UnsignedIntHolder copyIntoItemsLocalResult, com.microsoft.schemas.sharepoint.soap.holders.CopyResultCollectionHolder results) throws java.rmi.RemoteException;
    public void copyIntoItems(java.lang.String sourceUrl, java.lang.String[] destinationUrls, com.microsoft.schemas.sharepoint.soap.FieldInformation[] fields, byte[] stream, org.apache.axis.holders.UnsignedIntHolder copyIntoItemsResult, com.microsoft.schemas.sharepoint.soap.holders.CopyResultCollectionHolder results) throws java.rmi.RemoteException;
    public void getItem(java.lang.String url, org.apache.axis.holders.UnsignedIntHolder getItemResult, com.microsoft.schemas.sharepoint.soap.holders.FieldInformationCollectionHolder fields, javax.xml.rpc.holders.ByteArrayHolder stream) throws java.rmi.RemoteException;
}
