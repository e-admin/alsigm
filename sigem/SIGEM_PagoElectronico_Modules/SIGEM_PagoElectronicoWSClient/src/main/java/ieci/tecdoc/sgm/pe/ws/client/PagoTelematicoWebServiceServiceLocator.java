/**
 * PagoTelematicoWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.pe.ws.client;

public class PagoTelematicoWebServiceServiceLocator extends org.apache.axis.client.Service implements ieci.tecdoc.sgm.pe.ws.client.PagoTelematicoWebServiceService {

    public PagoTelematicoWebServiceServiceLocator() {
    }


    public PagoTelematicoWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PagoTelematicoWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PagoTelematicoWebService
    private java.lang.String PagoTelematicoWebService_address = "http://localhost:8080/SIGEM_PagoElectronicoWS/services/PagoTelematicoWebService";

    public java.lang.String getPagoTelematicoWebServiceAddress() {
        return PagoTelematicoWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PagoTelematicoWebServiceWSDDServiceName = "PagoTelematicoWebService";

    public java.lang.String getPagoTelematicoWebServiceWSDDServiceName() {
        return PagoTelematicoWebServiceWSDDServiceName;
    }

    public void setPagoTelematicoWebServiceWSDDServiceName(java.lang.String name) {
        PagoTelematicoWebServiceWSDDServiceName = name;
    }

    public ieci.tecdoc.sgm.pe.ws.client.PagoTelematicoWebService getPagoTelematicoWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PagoTelematicoWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPagoTelematicoWebService(endpoint);
    }

    public ieci.tecdoc.sgm.pe.ws.client.PagoTelematicoWebService getPagoTelematicoWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ieci.tecdoc.sgm.pe.ws.client.PagoTelematicoWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.pe.ws.client.PagoTelematicoWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getPagoTelematicoWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPagoTelematicoWebServiceEndpointAddress(java.lang.String address) {
        PagoTelematicoWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ieci.tecdoc.sgm.pe.ws.client.PagoTelematicoWebService.class.isAssignableFrom(serviceEndpointInterface)) {
                ieci.tecdoc.sgm.pe.ws.client.PagoTelematicoWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.pe.ws.client.PagoTelematicoWebServiceSoapBindingStub(new java.net.URL(PagoTelematicoWebService_address), this);
                _stub.setPortName(getPagoTelematicoWebServiceWSDDServiceName());
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
        if ("PagoTelematicoWebService".equals(inputPortName)) {
            return getPagoTelematicoWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "PagoTelematicoWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "PagoTelematicoWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PagoTelematicoWebService".equals(portName)) {
            setPagoTelematicoWebServiceEndpointAddress(address);
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
