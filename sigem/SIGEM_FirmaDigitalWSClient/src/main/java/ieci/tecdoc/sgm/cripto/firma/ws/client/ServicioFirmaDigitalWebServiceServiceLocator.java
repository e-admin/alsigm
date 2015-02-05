/**
 * ServicioFirmaDigitalWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.ws.client;

public class ServicioFirmaDigitalWebServiceServiceLocator extends org.apache.axis.client.Service implements ieci.tecdoc.sgm.cripto.firma.ws.client.ServicioFirmaDigitalWebServiceService {

    public ServicioFirmaDigitalWebServiceServiceLocator() {
    }


    public ServicioFirmaDigitalWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServicioFirmaDigitalWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for FirmaDigitalWebService
    private java.lang.String FirmaDigitalWebService_address = "http://localhost:8080/SIGEM_FirmaDigitalWS/services/FirmaDigitalWebService";

    public java.lang.String getFirmaDigitalWebServiceAddress() {
        return FirmaDigitalWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String FirmaDigitalWebServiceWSDDServiceName = "FirmaDigitalWebService";

    public java.lang.String getFirmaDigitalWebServiceWSDDServiceName() {
        return FirmaDigitalWebServiceWSDDServiceName;
    }

    public void setFirmaDigitalWebServiceWSDDServiceName(java.lang.String name) {
        FirmaDigitalWebServiceWSDDServiceName = name;
    }

    public ieci.tecdoc.sgm.cripto.firma.ws.client.ServicioFirmaDigitalWebService getFirmaDigitalWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(FirmaDigitalWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getFirmaDigitalWebService(endpoint);
    }

    public ieci.tecdoc.sgm.cripto.firma.ws.client.ServicioFirmaDigitalWebService getFirmaDigitalWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ieci.tecdoc.sgm.cripto.firma.ws.client.FirmaDigitalWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.cripto.firma.ws.client.FirmaDigitalWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getFirmaDigitalWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setFirmaDigitalWebServiceEndpointAddress(java.lang.String address) {
        FirmaDigitalWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ieci.tecdoc.sgm.cripto.firma.ws.client.ServicioFirmaDigitalWebService.class.isAssignableFrom(serviceEndpointInterface)) {
                ieci.tecdoc.sgm.cripto.firma.ws.client.FirmaDigitalWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.cripto.firma.ws.client.FirmaDigitalWebServiceSoapBindingStub(new java.net.URL(FirmaDigitalWebService_address), this);
                _stub.setPortName(getFirmaDigitalWebServiceWSDDServiceName());
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
        if ("FirmaDigitalWebService".equals(inputPortName)) {
            return getFirmaDigitalWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "ServicioFirmaDigitalWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "FirmaDigitalWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("FirmaDigitalWebService".equals(portName)) {
            setFirmaDigitalWebServiceEndpointAddress(address);
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
