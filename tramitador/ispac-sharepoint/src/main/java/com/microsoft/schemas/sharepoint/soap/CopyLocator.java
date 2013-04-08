/**
 * CopyLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

public class CopyLocator extends org.apache.axis.client.Service implements com.microsoft.schemas.sharepoint.soap.Copy {

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

    private void setSecurityData(CopySoapStub _stub) {
        //Añadimos los datos de seguridad y timeout.
        if (StringUtils.isNotEmpty(user)){
        	_stub.setUsername(user);
        }
        if (StringUtils.isNotEmpty(password)){
        	_stub.setPassword(password);
        }
	}	
	public CopyLocator() {
    }


    public CopyLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CopyLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CopySoap
    private java.lang.String CopySoap_address = "http://istgodesa01/_vti_bin/copy.asmx";

    public java.lang.String getCopySoapAddress() {
        return CopySoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CopySoapWSDDServiceName = "CopySoap";

    public java.lang.String getCopySoapWSDDServiceName() {
        return CopySoapWSDDServiceName;
    }

    public void setCopySoapWSDDServiceName(java.lang.String name) {
        CopySoapWSDDServiceName = name;
    }

    public com.microsoft.schemas.sharepoint.soap.CopySoap getCopySoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CopySoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCopySoap(endpoint);
    }

    public com.microsoft.schemas.sharepoint.soap.CopySoap getCopySoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.microsoft.schemas.sharepoint.soap.CopySoapStub _stub = new com.microsoft.schemas.sharepoint.soap.CopySoapStub(portAddress, this);
            _stub.setPortName(getCopySoapWSDDServiceName());
            
            setSecurityData(_stub);
            
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCopySoapEndpointAddress(java.lang.String address) {
        CopySoap_address = address;
    }


    // Use to get a proxy class for CopySoap12
    private java.lang.String CopySoap12_address = "http://istgodesa01/_vti_bin/copy.asmx";

    public java.lang.String getCopySoap12Address() {
        return CopySoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CopySoap12WSDDServiceName = "CopySoap12";

    public java.lang.String getCopySoap12WSDDServiceName() {
        return CopySoap12WSDDServiceName;
    }

    public void setCopySoap12WSDDServiceName(java.lang.String name) {
        CopySoap12WSDDServiceName = name;
    }

    public com.microsoft.schemas.sharepoint.soap.CopySoap getCopySoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CopySoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCopySoap12(endpoint);
    }

    public com.microsoft.schemas.sharepoint.soap.CopySoap getCopySoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.microsoft.schemas.sharepoint.soap.CopySoap12Stub _stub = new com.microsoft.schemas.sharepoint.soap.CopySoap12Stub(portAddress, this);
            _stub.setPortName(getCopySoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCopySoap12EndpointAddress(java.lang.String address) {
        CopySoap12_address = address;
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
            if (com.microsoft.schemas.sharepoint.soap.CopySoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.microsoft.schemas.sharepoint.soap.CopySoapStub _stub = new com.microsoft.schemas.sharepoint.soap.CopySoapStub(new java.net.URL(CopySoap_address), this);
                _stub.setPortName(getCopySoapWSDDServiceName());
                return _stub;
            }
            if (com.microsoft.schemas.sharepoint.soap.CopySoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.microsoft.schemas.sharepoint.soap.CopySoap12Stub _stub = new com.microsoft.schemas.sharepoint.soap.CopySoap12Stub(new java.net.URL(CopySoap12_address), this);
                _stub.setPortName(getCopySoap12WSDDServiceName());
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
        if ("CopySoap".equals(inputPortName)) {
            return getCopySoap();
        }
        else if ("CopySoap12".equals(inputPortName)) {
            return getCopySoap12();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Copy");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopySoap"));
            ports.add(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopySoap12"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CopySoap".equals(portName)) {
            setCopySoapEndpointAddress(address);
        }
        else 
if ("CopySoap12".equals(portName)) {
            setCopySoap12EndpointAddress(address);
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
