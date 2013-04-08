/**
 * CriptoValidacionWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.validacion.ws.client;

public class CriptoValidacionWebServiceServiceLocator extends org.apache.axis.client.Service implements ieci.tecdoc.sgm.cripto.validacion.ws.client.CriptoValidacionWebServiceService {

    public CriptoValidacionWebServiceServiceLocator() {
    }


    public CriptoValidacionWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CriptoValidacionWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CriptoValidacionWebService
    private java.lang.String CriptoValidacionWebService_address = "http://localhost:8080/SIGEM_CriptoValidacionWS/services/CriptoValidacionWebService";

    public java.lang.String getCriptoValidacionWebServiceAddress() {
        return CriptoValidacionWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CriptoValidacionWebServiceWSDDServiceName = "CriptoValidacionWebService";

    public java.lang.String getCriptoValidacionWebServiceWSDDServiceName() {
        return CriptoValidacionWebServiceWSDDServiceName;
    }

    public void setCriptoValidacionWebServiceWSDDServiceName(java.lang.String name) {
        CriptoValidacionWebServiceWSDDServiceName = name;
    }

    public ieci.tecdoc.sgm.cripto.validacion.ws.client.CriptoValidacionWebService_PortType getCriptoValidacionWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CriptoValidacionWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCriptoValidacionWebService(endpoint);
    }

    public ieci.tecdoc.sgm.cripto.validacion.ws.client.CriptoValidacionWebService_PortType getCriptoValidacionWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ieci.tecdoc.sgm.cripto.validacion.ws.client.CriptoValidacionWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.cripto.validacion.ws.client.CriptoValidacionWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCriptoValidacionWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCriptoValidacionWebServiceEndpointAddress(java.lang.String address) {
        CriptoValidacionWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ieci.tecdoc.sgm.cripto.validacion.ws.client.CriptoValidacionWebService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                ieci.tecdoc.sgm.cripto.validacion.ws.client.CriptoValidacionWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.cripto.validacion.ws.client.CriptoValidacionWebServiceSoapBindingStub(new java.net.URL(CriptoValidacionWebService_address), this);
                _stub.setPortName(getCriptoValidacionWebServiceWSDDServiceName());
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
        if ("CriptoValidacionWebService".equals(inputPortName)) {
            return getCriptoValidacionWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "CriptoValidacionWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "CriptoValidacionWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CriptoValidacionWebService".equals(portName)) {
            setCriptoValidacionWebServiceEndpointAddress(address);
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
