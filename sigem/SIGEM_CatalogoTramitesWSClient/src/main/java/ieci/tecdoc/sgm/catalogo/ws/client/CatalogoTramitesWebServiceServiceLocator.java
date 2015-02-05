/**
 * CatalogoTramitesWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.catalogo.ws.client;

public class CatalogoTramitesWebServiceServiceLocator extends org.apache.axis.client.Service implements ieci.tecdoc.sgm.catalogo.ws.client.CatalogoTramitesWebServiceService {

    public CatalogoTramitesWebServiceServiceLocator() {
    }


    public CatalogoTramitesWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CatalogoTramitesWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CatalogoTramitesWebService
    private java.lang.String CatalogoTramitesWebService_address = "http://localhost:8080/SIGEM_CatalogoTramitesWS/services/CatalogoTramitesWebService";

    public java.lang.String getCatalogoTramitesWebServiceAddress() {
        return CatalogoTramitesWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CatalogoTramitesWebServiceWSDDServiceName = "CatalogoTramitesWebService";

    public java.lang.String getCatalogoTramitesWebServiceWSDDServiceName() {
        return CatalogoTramitesWebServiceWSDDServiceName;
    }

    public void setCatalogoTramitesWebServiceWSDDServiceName(java.lang.String name) {
        CatalogoTramitesWebServiceWSDDServiceName = name;
    }

    public ieci.tecdoc.sgm.catalogo.ws.client.CatalogoTramitesWebService getCatalogoTramitesWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CatalogoTramitesWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCatalogoTramitesWebService(endpoint);
    }

    public ieci.tecdoc.sgm.catalogo.ws.client.CatalogoTramitesWebService getCatalogoTramitesWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ieci.tecdoc.sgm.catalogo.ws.client.CatalogoTramitesWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.catalogo.ws.client.CatalogoTramitesWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCatalogoTramitesWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCatalogoTramitesWebServiceEndpointAddress(java.lang.String address) {
        CatalogoTramitesWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ieci.tecdoc.sgm.catalogo.ws.client.CatalogoTramitesWebService.class.isAssignableFrom(serviceEndpointInterface)) {
                ieci.tecdoc.sgm.catalogo.ws.client.CatalogoTramitesWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.catalogo.ws.client.CatalogoTramitesWebServiceSoapBindingStub(new java.net.URL(CatalogoTramitesWebService_address), this);
                _stub.setPortName(getCatalogoTramitesWebServiceWSDDServiceName());
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
        if ("CatalogoTramitesWebService".equals(inputPortName)) {
            return getCatalogoTramitesWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "CatalogoTramitesWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "CatalogoTramitesWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CatalogoTramitesWebService".equals(portName)) {
            setCatalogoTramitesWebServiceEndpointAddress(address);
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
