/**
 * RegistroTelematicoWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registro.ws.client.axis;

public class RegistroTelematicoWebServiceServiceLocator extends org.apache.axis.client.Service implements ieci.tecdoc.sgm.registro.ws.client.axis.RegistroTelematicoWebServiceService {

    public RegistroTelematicoWebServiceServiceLocator() {
    }


    public RegistroTelematicoWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public RegistroTelematicoWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for RegistroTelematicoWebService
    private java.lang.String RegistroTelematicoWebService_address = "http://localhost:8080/SIGEM_RegistroTelematicoWS/services/RegistroTelematicoWebService";

    public java.lang.String getRegistroTelematicoWebServiceAddress() {
        return RegistroTelematicoWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String RegistroTelematicoWebServiceWSDDServiceName = "RegistroTelematicoWebService";

    public java.lang.String getRegistroTelematicoWebServiceWSDDServiceName() {
        return RegistroTelematicoWebServiceWSDDServiceName;
    }

    public void setRegistroTelematicoWebServiceWSDDServiceName(java.lang.String name) {
        RegistroTelematicoWebServiceWSDDServiceName = name;
    }

    public ieci.tecdoc.sgm.registro.ws.client.axis.RegistroTelematicoWebService_PortType getRegistroTelematicoWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(RegistroTelematicoWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getRegistroTelematicoWebService(endpoint);
    }

    public ieci.tecdoc.sgm.registro.ws.client.axis.RegistroTelematicoWebService_PortType getRegistroTelematicoWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ieci.tecdoc.sgm.registro.ws.client.axis.RegistroTelematicoWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.registro.ws.client.axis.RegistroTelematicoWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getRegistroTelematicoWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setRegistroTelematicoWebServiceEndpointAddress(java.lang.String address) {
        RegistroTelematicoWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ieci.tecdoc.sgm.registro.ws.client.axis.RegistroTelematicoWebService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                ieci.tecdoc.sgm.registro.ws.client.axis.RegistroTelematicoWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.registro.ws.client.axis.RegistroTelematicoWebServiceSoapBindingStub(new java.net.URL(RegistroTelematicoWebService_address), this);
                _stub.setPortName(getRegistroTelematicoWebServiceWSDDServiceName());
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
        if ("RegistroTelematicoWebService".equals(inputPortName)) {
            return getRegistroTelematicoWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "RegistroTelematicoWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "RegistroTelematicoWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("RegistroTelematicoWebService".equals(portName)) {
            setRegistroTelematicoWebServiceEndpointAddress(address);
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
