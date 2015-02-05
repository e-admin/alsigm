/**
 * AdministracionUsuariosPortalWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService;

public class AdministracionUsuariosPortalWebServiceServiceLocator extends org.apache.axis.client.Service implements es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceService {

    public AdministracionUsuariosPortalWebServiceServiceLocator() {
    }


    public AdministracionUsuariosPortalWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AdministracionUsuariosPortalWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AdministracionUsuariosPortalWebService
    private java.lang.String AdministracionUsuariosPortalWebService_address = "http://localhost:8080/SIGEM_AutenticacionUsuariosWS/services/AdministracionUsuariosPortalWebService";

    public java.lang.String getAdministracionUsuariosPortalWebServiceAddress() {
        return AdministracionUsuariosPortalWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AdministracionUsuariosPortalWebServiceWSDDServiceName = "AdministracionUsuariosPortalWebService";

    public java.lang.String getAdministracionUsuariosPortalWebServiceWSDDServiceName() {
        return AdministracionUsuariosPortalWebServiceWSDDServiceName;
    }

    public void setAdministracionUsuariosPortalWebServiceWSDDServiceName(java.lang.String name) {
        AdministracionUsuariosPortalWebServiceWSDDServiceName = name;
    }

    public es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebService_PortType getAdministracionUsuariosPortalWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AdministracionUsuariosPortalWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAdministracionUsuariosPortalWebService(endpoint);
    }

    public es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebService_PortType getAdministracionUsuariosPortalWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceSoapBindingStub _stub = new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getAdministracionUsuariosPortalWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAdministracionUsuariosPortalWebServiceEndpointAddress(java.lang.String address) {
        AdministracionUsuariosPortalWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceSoapBindingStub _stub = new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceSoapBindingStub(new java.net.URL(AdministracionUsuariosPortalWebService_address), this);
                _stub.setPortName(getAdministracionUsuariosPortalWebServiceWSDDServiceName());
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
        if ("AdministracionUsuariosPortalWebService".equals(inputPortName)) {
            return getAdministracionUsuariosPortalWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.ws.usuario.sgm.tecdoc.ieci", "AdministracionUsuariosPortalWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.ws.usuario.sgm.tecdoc.ieci", "AdministracionUsuariosPortalWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AdministracionUsuariosPortalWebService".equals(portName)) {
            setAdministracionUsuariosPortalWebServiceEndpointAddress(address);
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
