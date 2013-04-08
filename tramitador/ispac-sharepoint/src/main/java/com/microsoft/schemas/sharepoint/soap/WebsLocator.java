package com.microsoft.schemas.sharepoint.soap;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

public class WebsLocator extends org.apache.axis.client.Service implements com.microsoft.schemas.sharepoint.soap.Webs {

	private static final long serialVersionUID = 1L;
	
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

    private void setSecurityData(WebsSoapStub _stub) {
        //Añadimos los datos de seguridad y timeout.
        if (StringUtils.isNotEmpty(user)){
        	_stub.setUsername(user);
        }
        if (StringUtils.isNotEmpty(password)){
        	_stub.setPassword(password);
        }
	}	
    public WebsLocator() {
    }


    public WebsLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WebsLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WebsSoap12
    private java.lang.String WebsSoap12_address = "http://istgodesa01/_vti_bin/webs.asmx";

    public java.lang.String getWebsSoap12Address() {
        return WebsSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WebsSoap12WSDDServiceName = "WebsSoap12";

    public java.lang.String getWebsSoap12WSDDServiceName() {
        return WebsSoap12WSDDServiceName;
    }

    public void setWebsSoap12WSDDServiceName(java.lang.String name) {
        WebsSoap12WSDDServiceName = name;
    }

    public com.microsoft.schemas.sharepoint.soap.WebsSoap getWebsSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WebsSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWebsSoap12(endpoint);
    }

    public com.microsoft.schemas.sharepoint.soap.WebsSoap getWebsSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.microsoft.schemas.sharepoint.soap.WebsSoap12Stub _stub = new com.microsoft.schemas.sharepoint.soap.WebsSoap12Stub(portAddress, this);
            _stub.setPortName(getWebsSoap12WSDDServiceName());
            
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWebsSoap12EndpointAddress(java.lang.String address) {
        WebsSoap12_address = address;
    }


    // Use to get a proxy class for WebsSoap
    private java.lang.String WebsSoap_address = "http://istgodesa01/_vti_bin/webs.asmx";

    public java.lang.String getWebsSoapAddress() {
        return WebsSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WebsSoapWSDDServiceName = "WebsSoap";

    public java.lang.String getWebsSoapWSDDServiceName() {
        return WebsSoapWSDDServiceName;
    }

    public void setWebsSoapWSDDServiceName(java.lang.String name) {
        WebsSoapWSDDServiceName = name;
    }

    public com.microsoft.schemas.sharepoint.soap.WebsSoap getWebsSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WebsSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWebsSoap(endpoint);
    }

    public com.microsoft.schemas.sharepoint.soap.WebsSoap getWebsSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.microsoft.schemas.sharepoint.soap.WebsSoapStub _stub = new com.microsoft.schemas.sharepoint.soap.WebsSoapStub(portAddress, this);
            _stub.setPortName(getWebsSoapWSDDServiceName());
            
            setSecurityData(_stub);
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWebsSoapEndpointAddress(java.lang.String address) {
        WebsSoap_address = address;
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
            if (com.microsoft.schemas.sharepoint.soap.WebsSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.microsoft.schemas.sharepoint.soap.WebsSoap12Stub _stub = new com.microsoft.schemas.sharepoint.soap.WebsSoap12Stub(new java.net.URL(WebsSoap12_address), this);
                _stub.setPortName(getWebsSoap12WSDDServiceName());
                return _stub;
            }
            if (com.microsoft.schemas.sharepoint.soap.WebsSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.microsoft.schemas.sharepoint.soap.WebsSoapStub _stub = new com.microsoft.schemas.sharepoint.soap.WebsSoapStub(new java.net.URL(WebsSoap_address), this);
                _stub.setPortName(getWebsSoapWSDDServiceName());
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
        if ("WebsSoap12".equals(inputPortName)) {
            return getWebsSoap12();
        }
        else if ("WebsSoap".equals(inputPortName)) {
            return getWebsSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Webs");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "WebsSoap12"));
            ports.add(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "WebsSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WebsSoap12".equals(portName)) {
            setWebsSoap12EndpointAddress(address);
        }
        else 
if ("WebsSoap".equals(portName)) {
            setWebsSoapEndpointAddress(address);
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
