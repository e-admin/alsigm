/**
 * RepositorioDocumentosWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.rde.ws.client;

public class RepositorioDocumentosWebServiceServiceLocator extends org.apache.axis.client.Service implements ieci.tecdoc.sgm.rde.ws.client.RepositorioDocumentosWebServiceService {

    public RepositorioDocumentosWebServiceServiceLocator() {
    }


    public RepositorioDocumentosWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public RepositorioDocumentosWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for RepositorioDocumentosWebService
    private java.lang.String RepositorioDocumentosWebService_address = "http://localhost:8080/SIGEM_RdeWS/services/RepositorioDocumentosWebService";

    public java.lang.String getRepositorioDocumentosWebServiceAddress() {
        return RepositorioDocumentosWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String RepositorioDocumentosWebServiceWSDDServiceName = "RepositorioDocumentosWebService";

    public java.lang.String getRepositorioDocumentosWebServiceWSDDServiceName() {
        return RepositorioDocumentosWebServiceWSDDServiceName;
    }

    public void setRepositorioDocumentosWebServiceWSDDServiceName(java.lang.String name) {
        RepositorioDocumentosWebServiceWSDDServiceName = name;
    }

    public ieci.tecdoc.sgm.rde.ws.client.RepositorioDocumentosWebService getRepositorioDocumentosWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(RepositorioDocumentosWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getRepositorioDocumentosWebService(endpoint);
    }

    public ieci.tecdoc.sgm.rde.ws.client.RepositorioDocumentosWebService getRepositorioDocumentosWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ieci.tecdoc.sgm.rde.ws.client.RepositorioDocumentosWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.rde.ws.client.RepositorioDocumentosWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getRepositorioDocumentosWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setRepositorioDocumentosWebServiceEndpointAddress(java.lang.String address) {
        RepositorioDocumentosWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ieci.tecdoc.sgm.rde.ws.client.RepositorioDocumentosWebService.class.isAssignableFrom(serviceEndpointInterface)) {
                ieci.tecdoc.sgm.rde.ws.client.RepositorioDocumentosWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.rde.ws.client.RepositorioDocumentosWebServiceSoapBindingStub(new java.net.URL(RepositorioDocumentosWebService_address), this);
                _stub.setPortName(getRepositorioDocumentosWebServiceWSDDServiceName());
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
        if ("RepositorioDocumentosWebService".equals(inputPortName)) {
            return getRepositorioDocumentosWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.ws.rde.sgm.tecdoc.ieci", "RepositorioDocumentosWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.ws.rde.sgm.tecdoc.ieci", "RepositorioDocumentosWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("RepositorioDocumentosWebService".equals(portName)) {
            setRepositorioDocumentosWebServiceEndpointAddress(address);
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
