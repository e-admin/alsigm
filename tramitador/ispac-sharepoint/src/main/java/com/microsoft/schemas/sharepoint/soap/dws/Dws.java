/**
 * Dws.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap.dws;

public interface Dws extends javax.xml.rpc.Service {
    public java.lang.String getDwsSoapAddress();

    public com.microsoft.schemas.sharepoint.soap.dws.DwsSoap getDwsSoap() throws javax.xml.rpc.ServiceException;

    public com.microsoft.schemas.sharepoint.soap.dws.DwsSoap getDwsSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getDwsSoap12Address();

    public com.microsoft.schemas.sharepoint.soap.dws.DwsSoap getDwsSoap12() throws javax.xml.rpc.ServiceException;

    public com.microsoft.schemas.sharepoint.soap.dws.DwsSoap getDwsSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
