/**
 * SiteDataLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

public class SiteDataLocator extends org.apache.axis.client.Service implements com.microsoft.schemas.sharepoint.soap.SiteData {

	private String user;
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}    

    private void setSecurityData(SiteDataSoapStub _stub) {
        //Añadimos los datos de seguridad y timeout.
        if (StringUtils.isNotEmpty(user)){
        	_stub.setUsername(user);
        }
        if (StringUtils.isNotEmpty(password)){
        	_stub.setPassword(password);
        }
	}
	
    public SiteDataLocator() {
    }


    public SiteDataLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SiteDataLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SiteDataSoap12
    private java.lang.String SiteDataSoap12_address = "http://istgodesa01/_vti_bin/sitedata.asmx";

    public java.lang.String getSiteDataSoap12Address() {
        return SiteDataSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SiteDataSoap12WSDDServiceName = "SiteDataSoap12";

    public java.lang.String getSiteDataSoap12WSDDServiceName() {
        return SiteDataSoap12WSDDServiceName;
    }

    public void setSiteDataSoap12WSDDServiceName(java.lang.String name) {
        SiteDataSoap12WSDDServiceName = name;
    }

    public com.microsoft.schemas.sharepoint.soap.SiteDataSoap getSiteDataSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SiteDataSoap12_address);
            
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSiteDataSoap12(endpoint);
    }

    public com.microsoft.schemas.sharepoint.soap.SiteDataSoap getSiteDataSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.microsoft.schemas.sharepoint.soap.SiteDataSoap12Stub _stub = new com.microsoft.schemas.sharepoint.soap.SiteDataSoap12Stub(portAddress, this);
            _stub.setPortName(getSiteDataSoap12WSDDServiceName());

            
            
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }



	public void setSiteDataSoap12EndpointAddress(java.lang.String address) {
        SiteDataSoap12_address = address;
    }


    // Use to get a proxy class for SiteDataSoap
    private java.lang.String SiteDataSoap_address = "http://istgodesa01/_vti_bin/sitedata.asmx";

    public java.lang.String getSiteDataSoapAddress() {
        return SiteDataSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SiteDataSoapWSDDServiceName = "SiteDataSoap";

    public java.lang.String getSiteDataSoapWSDDServiceName() {
        return SiteDataSoapWSDDServiceName;
    }

    public void setSiteDataSoapWSDDServiceName(java.lang.String name) {
        SiteDataSoapWSDDServiceName = name;
    }

    public com.microsoft.schemas.sharepoint.soap.SiteDataSoap getSiteDataSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SiteDataSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSiteDataSoap(endpoint);
    }

    public com.microsoft.schemas.sharepoint.soap.SiteDataSoap getSiteDataSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.microsoft.schemas.sharepoint.soap.SiteDataSoapStub _stub = new com.microsoft.schemas.sharepoint.soap.SiteDataSoapStub(portAddress, this);
            _stub.setPortName(getSiteDataSoapWSDDServiceName());

            setSecurityData(_stub);
            
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSiteDataSoapEndpointAddress(java.lang.String address) {
        SiteDataSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.microsoft.schemas.sharepoint.soap.SiteDataSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.microsoft.schemas.sharepoint.soap.SiteDataSoap12Stub _stub = new com.microsoft.schemas.sharepoint.soap.SiteDataSoap12Stub(new java.net.URL(SiteDataSoap12_address), this);
                _stub.setPortName(getSiteDataSoap12WSDDServiceName());
                return _stub;
            }
            if (com.microsoft.schemas.sharepoint.soap.SiteDataSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.microsoft.schemas.sharepoint.soap.SiteDataSoapStub _stub = new com.microsoft.schemas.sharepoint.soap.SiteDataSoapStub(new java.net.URL(SiteDataSoap_address), this);
                _stub.setPortName(getSiteDataSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SiteDataSoap12".equals(inputPortName)) {
            return getSiteDataSoap12();
        }
        else if ("SiteDataSoap".equals(inputPortName)) {
            return getSiteDataSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "SiteData");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "SiteDataSoap12"));
            ports.add(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "SiteDataSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SiteDataSoap12".equals(portName)) {
            setSiteDataSoap12EndpointAddress(address);
        }
        else 
if ("SiteDataSoap".equals(portName)) {
            setSiteDataSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

    

	
}
