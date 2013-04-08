/**
 * EstructuraOrganizativaWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.estructura.ws.client.axis;

public class EstructuraOrganizativaWebServiceServiceLocator extends org.apache.axis.client.Service implements ieci.tecdoc.sgm.estructura.ws.client.axis.EstructuraOrganizativaWebServiceService {

    public EstructuraOrganizativaWebServiceServiceLocator() {
    }


    public EstructuraOrganizativaWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EstructuraOrganizativaWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EstructuraOrganizativaWebService
    private java.lang.String EstructuraOrganizativaWebService_address = "http://localhost:8080/SIGEM_EstructuraOrganizativaWS/services/EstructuraOrganizativaWebService";

    public java.lang.String getEstructuraOrganizativaWebServiceAddress() {
        return EstructuraOrganizativaWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EstructuraOrganizativaWebServiceWSDDServiceName = "EstructuraOrganizativaWebService";

    public java.lang.String getEstructuraOrganizativaWebServiceWSDDServiceName() {
        return EstructuraOrganizativaWebServiceWSDDServiceName;
    }

    public void setEstructuraOrganizativaWebServiceWSDDServiceName(java.lang.String name) {
        EstructuraOrganizativaWebServiceWSDDServiceName = name;
    }

    public ieci.tecdoc.sgm.estructura.ws.client.axis.EstructuraOrganizativaWebService_PortType getEstructuraOrganizativaWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EstructuraOrganizativaWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEstructuraOrganizativaWebService(endpoint);
    }

    public ieci.tecdoc.sgm.estructura.ws.client.axis.EstructuraOrganizativaWebService_PortType getEstructuraOrganizativaWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ieci.tecdoc.sgm.estructura.ws.client.axis.EstructuraOrganizativaWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.estructura.ws.client.axis.EstructuraOrganizativaWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getEstructuraOrganizativaWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEstructuraOrganizativaWebServiceEndpointAddress(java.lang.String address) {
        EstructuraOrganizativaWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ieci.tecdoc.sgm.estructura.ws.client.axis.EstructuraOrganizativaWebService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                ieci.tecdoc.sgm.estructura.ws.client.axis.EstructuraOrganizativaWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.estructura.ws.client.axis.EstructuraOrganizativaWebServiceSoapBindingStub(new java.net.URL(EstructuraOrganizativaWebService_address), this);
                _stub.setPortName(getEstructuraOrganizativaWebServiceWSDDServiceName());
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
        if ("EstructuraOrganizativaWebService".equals(inputPortName)) {
            return getEstructuraOrganizativaWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "EstructuraOrganizativaWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "EstructuraOrganizativaWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("EstructuraOrganizativaWebService".equals(portName)) {
            setEstructuraOrganizativaWebServiceEndpointAddress(address);
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
