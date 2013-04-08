/**
 * FirmaRemoteServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.impl.afirma;

public class FirmaRemoteServiceLocator extends org.apache.axis.client.Service implements FirmaRemoteService {

    public FirmaRemoteServiceLocator() {
    }


    public FirmaRemoteServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FirmaRemoteServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for FirmaServidor
    private java.lang.String FirmaServidor_address = "https://localhost/afirmaws/services/FirmaServidor";

    public java.lang.String getFirmaServidorAddress() {
        return FirmaServidor_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String FirmaServidorWSDDServiceName = "FirmaServidor";

    public java.lang.String getFirmaServidorWSDDServiceName() {
        return FirmaServidorWSDDServiceName;
    }

    public void setFirmaServidorWSDDServiceName(java.lang.String name) {
        FirmaServidorWSDDServiceName = name;
    }

    public FirmaRemote getFirmaServidor() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(FirmaServidor_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getFirmaServidor(endpoint);
    }

    public FirmaRemote getFirmaServidor(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            FirmaServidorSoapBindingStub _stub = new FirmaServidorSoapBindingStub(portAddress, this);
            _stub.setPortName(getFirmaServidorWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setFirmaServidorEndpointAddress(java.lang.String address) {
        FirmaServidor_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (FirmaRemote.class.isAssignableFrom(serviceEndpointInterface)) {
                FirmaServidorSoapBindingStub _stub = new FirmaServidorSoapBindingStub(new java.net.URL(FirmaServidor_address), this);
                _stub.setPortName(getFirmaServidorWSDDServiceName());
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
        if ("FirmaServidor".equals(inputPortName)) {
            return getFirmaServidor();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://afirmaws/services/FirmaServidor", "FirmaRemoteService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://afirmaws/services/FirmaServidor", "FirmaServidor"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("FirmaServidor".equals(portName)) {
            setFirmaServidorEndpointAddress(address);
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
