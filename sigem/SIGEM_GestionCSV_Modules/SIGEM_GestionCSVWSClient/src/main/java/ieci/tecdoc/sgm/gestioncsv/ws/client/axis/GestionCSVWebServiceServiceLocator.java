/**
 * GestionCSVWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.gestioncsv.ws.client.axis;

public class GestionCSVWebServiceServiceLocator extends org.apache.axis.client.Service implements ieci.tecdoc.sgm.gestioncsv.ws.client.axis.GestionCSVWebServiceService {

    public GestionCSVWebServiceServiceLocator() {
    }


    public GestionCSVWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GestionCSVWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for GestionCSVWebService
    private java.lang.String GestionCSVWebService_address = "http://localhost:8080/SIGEM_GestionCSVWS/services/GestionCSVWebService";

    public java.lang.String getGestionCSVWebServiceAddress() {
        return GestionCSVWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GestionCSVWebServiceWSDDServiceName = "GestionCSVWebService";

    public java.lang.String getGestionCSVWebServiceWSDDServiceName() {
        return GestionCSVWebServiceWSDDServiceName;
    }

    public void setGestionCSVWebServiceWSDDServiceName(java.lang.String name) {
        GestionCSVWebServiceWSDDServiceName = name;
    }

    public ieci.tecdoc.sgm.gestioncsv.ws.client.axis.GestionCSVWebService_PortType getGestionCSVWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GestionCSVWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGestionCSVWebService(endpoint);
    }

    public ieci.tecdoc.sgm.gestioncsv.ws.client.axis.GestionCSVWebService_PortType getGestionCSVWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ieci.tecdoc.sgm.gestioncsv.ws.client.axis.GestionCSVWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.gestioncsv.ws.client.axis.GestionCSVWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getGestionCSVWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGestionCSVWebServiceEndpointAddress(java.lang.String address) {
        GestionCSVWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ieci.tecdoc.sgm.gestioncsv.ws.client.axis.GestionCSVWebService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                ieci.tecdoc.sgm.gestioncsv.ws.client.axis.GestionCSVWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.gestioncsv.ws.client.axis.GestionCSVWebServiceSoapBindingStub(new java.net.URL(GestionCSVWebService_address), this);
                _stub.setPortName(getGestionCSVWebServiceWSDDServiceName());
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
        if ("GestionCSVWebService".equals(inputPortName)) {
            return getGestionCSVWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.ws.gestioncsv.sgm.tecdoc.ieci", "GestionCSVWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.ws.gestioncsv.sgm.tecdoc.ieci", "GestionCSVWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("GestionCSVWebService".equals(portName)) {
            setGestionCSVWebServiceEndpointAddress(address);
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
