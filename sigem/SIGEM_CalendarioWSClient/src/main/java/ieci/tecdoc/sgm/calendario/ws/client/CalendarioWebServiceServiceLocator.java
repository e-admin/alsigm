/**
 * CalendarioWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.calendario.ws.client;

public class CalendarioWebServiceServiceLocator extends org.apache.axis.client.Service implements ieci.tecdoc.sgm.calendario.ws.client.CalendarioWebServiceService {

    public CalendarioWebServiceServiceLocator() {
    }


    public CalendarioWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CalendarioWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CalendarioWebService
    private java.lang.String CalendarioWebService_address = "http://localhost:8080/SIGEM_CalendarioWS/services/CalendarioWebService";

    public java.lang.String getCalendarioWebServiceAddress() {
        return CalendarioWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CalendarioWebServiceWSDDServiceName = "CalendarioWebService";

    public java.lang.String getCalendarioWebServiceWSDDServiceName() {
        return CalendarioWebServiceWSDDServiceName;
    }

    public void setCalendarioWebServiceWSDDServiceName(java.lang.String name) {
        CalendarioWebServiceWSDDServiceName = name;
    }

    public ieci.tecdoc.sgm.calendario.ws.client.CalendarioWebService getCalendarioWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CalendarioWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCalendarioWebService(endpoint);
    }

    public ieci.tecdoc.sgm.calendario.ws.client.CalendarioWebService getCalendarioWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	ieci.tecdoc.sgm.calendario.ws.client.CalendarioWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.calendario.ws.client.CalendarioWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCalendarioWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCalendarioWebServiceEndpointAddress(java.lang.String address) {
        CalendarioWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ieci.tecdoc.sgm.calendario.ws.client.CalendarioWebService.class.isAssignableFrom(serviceEndpointInterface)) {
            	ieci.tecdoc.sgm.calendario.ws.client.CalendarioWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.calendario.ws.client.CalendarioWebServiceSoapBindingStub(new java.net.URL(CalendarioWebService_address), this);
                _stub.setPortName(getCalendarioWebServiceWSDDServiceName());
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
        if ("CalendarioWebService".equals(inputPortName)) {
            return getCalendarioWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "CalendarioWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "CalendarioWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CalendarioWebService".equals(portName)) {
            setCalendarioWebServiceEndpointAddress(address);
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
