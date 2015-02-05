/**
 * ClienteLigeroServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.ieci.scsp.verifdata.services;

public class ClienteLigeroServiceLocator extends org.apache.axis.client.Service implements es.ieci.scsp.verifdata.services.ClienteLigeroService {

    public ClienteLigeroServiceLocator() {
    }


    public ClienteLigeroServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ClienteLigeroServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ClienteLigero
    private java.lang.String ClienteLigero_address = "http://tempuri.org/verificacionDatos/services/ClienteLigero";

    public java.lang.String getClienteLigeroAddress() {
        return ClienteLigero_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ClienteLigeroWSDDServiceName = "ClienteLigero";

    public java.lang.String getClienteLigeroWSDDServiceName() {
        return ClienteLigeroWSDDServiceName;
    }

    public void setClienteLigeroWSDDServiceName(java.lang.String name) {
        ClienteLigeroWSDDServiceName = name;
    }

    public es.ieci.scsp.verifdata.services.ClienteLigero getClienteLigero() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ClienteLigero_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getClienteLigero(endpoint);
    }

    public es.ieci.scsp.verifdata.services.ClienteLigero getClienteLigero(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.ieci.scsp.verifdata.services.ClienteLigeroSoapBindingStub _stub = new es.ieci.scsp.verifdata.services.ClienteLigeroSoapBindingStub(portAddress, this);
            _stub.setPortName(getClienteLigeroWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setClienteLigeroEndpointAddress(java.lang.String address) {
        ClienteLigero_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.ieci.scsp.verifdata.services.ClienteLigero.class.isAssignableFrom(serviceEndpointInterface)) {
                es.ieci.scsp.verifdata.services.ClienteLigeroSoapBindingStub _stub = new es.ieci.scsp.verifdata.services.ClienteLigeroSoapBindingStub(new java.net.URL(ClienteLigero_address), this);
                _stub.setPortName(getClienteLigeroWSDDServiceName());
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
        if ("ClienteLigero".equals(inputPortName)) {
            return getClienteLigero();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://services.verifdata.scsp.ieci.es", "ClienteLigeroService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://services.verifdata.scsp.ieci.es", "ClienteLigero"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ClienteLigero".equals(portName)) {
            setClienteLigeroEndpointAddress(address);
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
