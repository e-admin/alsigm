/**
 * EntidadesWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.entidades.ws.client;

public class EntidadesWebServiceServiceLocator extends org.apache.axis.client.Service implements ieci.tecdoc.sgm.entidades.ws.client.EntidadesWebServiceService {

    public EntidadesWebServiceServiceLocator() {
    }


    public EntidadesWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EntidadesWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EntidadesWebService
    private java.lang.String EntidadesWebService_address = "http://localhost:8080/SIGEM_EntidadesWS/services/EntidadesWebService";

    public java.lang.String getEntidadesWebServiceAddress() {
        return EntidadesWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EntidadesWebServiceWSDDServiceName = "EntidadesWebService";

    public java.lang.String getEntidadesWebServiceWSDDServiceName() {
        return EntidadesWebServiceWSDDServiceName;
    }

    public void setEntidadesWebServiceWSDDServiceName(java.lang.String name) {
        EntidadesWebServiceWSDDServiceName = name;
    }

    public ieci.tecdoc.sgm.entidades.ws.client.EntidadesWebService getEntidadesWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EntidadesWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEntidadesWebService(endpoint);
    }

    public ieci.tecdoc.sgm.entidades.ws.client.EntidadesWebService getEntidadesWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ieci.tecdoc.sgm.entidades.ws.client.EntidadesWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.entidades.ws.client.EntidadesWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getEntidadesWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEntidadesWebServiceEndpointAddress(java.lang.String address) {
        EntidadesWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ieci.tecdoc.sgm.entidades.ws.client.EntidadesWebService.class.isAssignableFrom(serviceEndpointInterface)) {
                ieci.tecdoc.sgm.entidades.ws.client.EntidadesWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.entidades.ws.client.EntidadesWebServiceSoapBindingStub(new java.net.URL(EntidadesWebService_address), this);
                _stub.setPortName(getEntidadesWebServiceWSDDServiceName());
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
        if ("EntidadesWebService".equals(inputPortName)) {
            return getEntidadesWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "EntidadesWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "EntidadesWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("EntidadesWebService".equals(portName)) {
            setEntidadesWebServiceEndpointAddress(address);
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
