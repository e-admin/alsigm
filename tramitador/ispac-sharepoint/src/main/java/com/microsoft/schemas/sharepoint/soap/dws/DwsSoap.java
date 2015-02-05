/**
 * DwsSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap.dws;

public interface DwsSoap extends java.rmi.Remote {
    public java.lang.String createDws(java.lang.String name, java.lang.String users, java.lang.String title, java.lang.String documents) throws java.rmi.RemoteException;
    public java.lang.String deleteDws() throws java.rmi.RemoteException;
    public java.lang.String getDwsMetaData(java.lang.String document, java.lang.String id, boolean minimal) throws java.rmi.RemoteException;
    public java.lang.String getDwsData(java.lang.String document, java.lang.String lastUpdate) throws java.rmi.RemoteException;
    public java.lang.String updateDwsData(java.lang.String updates, java.lang.String meetingInstance) throws java.rmi.RemoteException;
    public java.lang.String removeDwsUser(java.lang.String id) throws java.rmi.RemoteException;
    public java.lang.String renameDws(java.lang.String title) throws java.rmi.RemoteException;
    public java.lang.String findDwsDoc(java.lang.String id) throws java.rmi.RemoteException;
    public java.lang.String canCreateDwsUrl(java.lang.String url) throws java.rmi.RemoteException;
    public java.lang.String createFolder(java.lang.String url) throws java.rmi.RemoteException;
    public java.lang.String deleteFolder(java.lang.String url) throws java.rmi.RemoteException;
}
