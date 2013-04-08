/**
 * CertificacionWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.certificacion.ws.client;

public class CertificacionWebServiceServiceLocator extends org.apache.axis.client.Service implements ieci.tecdoc.sgm.certificacion.ws.client.CertificacionWebServiceService {

    public CertificacionWebServiceServiceLocator() {
    }


    public CertificacionWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CertificacionWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CertificacionWebService
    private java.lang.String CertificacionWebService_address = "http://localhost:8080/SIGEM_CertificacionWS/services/CertificacionWebService";

    public java.lang.String getCertificacionWebServiceAddress() {
        return CertificacionWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CertificacionWebServiceWSDDServiceName = "CertificacionWebService";

    public java.lang.String getCertificacionWebServiceWSDDServiceName() {
        return CertificacionWebServiceWSDDServiceName;
    }

    public void setCertificacionWebServiceWSDDServiceName(java.lang.String name) {
        CertificacionWebServiceWSDDServiceName = name;
    }

    public ieci.tecdoc.sgm.certificacion.ws.client.CertificacionWebService getCertificacionWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CertificacionWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCertificacionWebService(endpoint);
    }

    public ieci.tecdoc.sgm.certificacion.ws.client.CertificacionWebService getCertificacionWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ieci.tecdoc.sgm.certificacion.ws.client.CertificacionWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.certificacion.ws.client.CertificacionWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCertificacionWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCertificacionWebServiceEndpointAddress(java.lang.String address) {
        CertificacionWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ieci.tecdoc.sgm.certificacion.ws.client.CertificacionWebService.class.isAssignableFrom(serviceEndpointInterface)) {
                ieci.tecdoc.sgm.certificacion.ws.client.CertificacionWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.certificacion.ws.client.CertificacionWebServiceSoapBindingStub(new java.net.URL(CertificacionWebService_address), this);
                _stub.setPortName(getCertificacionWebServiceWSDDServiceName());
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
        if ("CertificacionWebService".equals(inputPortName)) {
            return getCertificacionWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.ws.certificacion.sgm.tecdoc.ieci", "CertificacionWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.ws.certificacion.sgm.tecdoc.ieci", "CertificacionWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CertificacionWebService".equals(portName)) {
            setCertificacionWebServiceEndpointAddress(address);
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
