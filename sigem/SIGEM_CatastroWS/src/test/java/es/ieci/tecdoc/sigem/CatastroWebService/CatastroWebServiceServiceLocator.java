/**
 * CatastroWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.CatastroWebService;

public class CatastroWebServiceServiceLocator extends org.apache.axis.client.Service implements es.ieci.tecdoc.sigem.CatastroWebService.CatastroWebServiceService {

    public CatastroWebServiceServiceLocator() {
    }


    public CatastroWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CatastroWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CatastroWebService
    private java.lang.String CatastroWebService_address = "http://localhost:8080/SIGEM_CatastroWS/services/CatastroWebService";

    public java.lang.String getCatastroWebServiceAddress() {
        return CatastroWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CatastroWebServiceWSDDServiceName = "CatastroWebService";

    public java.lang.String getCatastroWebServiceWSDDServiceName() {
        return CatastroWebServiceWSDDServiceName;
    }

    public void setCatastroWebServiceWSDDServiceName(java.lang.String name) {
        CatastroWebServiceWSDDServiceName = name;
    }

    public es.ieci.tecdoc.sigem.CatastroWebService.CatastroWebService_PortType getCatastroWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CatastroWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCatastroWebService(endpoint);
    }

    public es.ieci.tecdoc.sigem.CatastroWebService.CatastroWebService_PortType getCatastroWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.ieci.tecdoc.sigem.CatastroWebService.CatastroWebServiceSoapBindingStub _stub = new es.ieci.tecdoc.sigem.CatastroWebService.CatastroWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCatastroWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCatastroWebServiceEndpointAddress(java.lang.String address) {
        CatastroWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.ieci.tecdoc.sigem.CatastroWebService.CatastroWebService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                es.ieci.tecdoc.sigem.CatastroWebService.CatastroWebServiceSoapBindingStub _stub = new es.ieci.tecdoc.sigem.CatastroWebService.CatastroWebServiceSoapBindingStub(new java.net.URL(CatastroWebService_address), this);
                _stub.setPortName(getCatastroWebServiceWSDDServiceName());
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
        if ("CatastroWebService".equals(inputPortName)) {
            return getCatastroWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "CatastroWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "CatastroWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CatastroWebService".equals(portName)) {
            setCatastroWebServiceEndpointAddress(address);
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
