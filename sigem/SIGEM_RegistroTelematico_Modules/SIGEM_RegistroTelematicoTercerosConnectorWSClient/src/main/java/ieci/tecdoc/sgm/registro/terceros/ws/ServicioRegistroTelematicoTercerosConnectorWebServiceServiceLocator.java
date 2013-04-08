/**
 * ServicioRegistroTelematicoTercerosConnectorWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registro.terceros.ws;

public class ServicioRegistroTelematicoTercerosConnectorWebServiceServiceLocator extends org.apache.axis.client.Service implements ieci.tecdoc.sgm.registro.terceros.ws.ServicioRegistroTelematicoTercerosConnectorWebServiceService {

    public ServicioRegistroTelematicoTercerosConnectorWebServiceServiceLocator() {
    }


    public ServicioRegistroTelematicoTercerosConnectorWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServicioRegistroTelematicoTercerosConnectorWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServicioRegistroTelematicoTercerosConnectorWebService
    private java.lang.String ServicioRegistroTelematicoTercerosConnectorWebService_address = "http://localhost:8080/SIGEM_RegistroTelematicoTercerosConnectorWS/services/ServicioRegistroTelematicoTercerosConnectorWebService";

    public java.lang.String getServicioRegistroTelematicoTercerosConnectorWebServiceAddress() {
        return ServicioRegistroTelematicoTercerosConnectorWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServicioRegistroTelematicoTercerosConnectorWebServiceWSDDServiceName = "ServicioRegistroTelematicoTercerosConnectorWebService";

    public java.lang.String getServicioRegistroTelematicoTercerosConnectorWebServiceWSDDServiceName() {
        return ServicioRegistroTelematicoTercerosConnectorWebServiceWSDDServiceName;
    }

    public void setServicioRegistroTelematicoTercerosConnectorWebServiceWSDDServiceName(java.lang.String name) {
        ServicioRegistroTelematicoTercerosConnectorWebServiceWSDDServiceName = name;
    }

    public ieci.tecdoc.sgm.registro.terceros.ws.ServicioRegistroTelematicoTercerosConnectorWebService_PortType getServicioRegistroTelematicoTercerosConnectorWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServicioRegistroTelematicoTercerosConnectorWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServicioRegistroTelematicoTercerosConnectorWebService(endpoint);
    }

    public ieci.tecdoc.sgm.registro.terceros.ws.ServicioRegistroTelematicoTercerosConnectorWebService_PortType getServicioRegistroTelematicoTercerosConnectorWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ieci.tecdoc.sgm.registro.terceros.ws.ServicioRegistroTelematicoTercerosConnectorWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.registro.terceros.ws.ServicioRegistroTelematicoTercerosConnectorWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getServicioRegistroTelematicoTercerosConnectorWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServicioRegistroTelematicoTercerosConnectorWebServiceEndpointAddress(java.lang.String address) {
        ServicioRegistroTelematicoTercerosConnectorWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ieci.tecdoc.sgm.registro.terceros.ws.ServicioRegistroTelematicoTercerosConnectorWebService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                ieci.tecdoc.sgm.registro.terceros.ws.ServicioRegistroTelematicoTercerosConnectorWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.registro.terceros.ws.ServicioRegistroTelematicoTercerosConnectorWebServiceSoapBindingStub(new java.net.URL(ServicioRegistroTelematicoTercerosConnectorWebService_address), this);
                _stub.setPortName(getServicioRegistroTelematicoTercerosConnectorWebServiceWSDDServiceName());
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
        if ("ServicioRegistroTelematicoTercerosConnectorWebService".equals(inputPortName)) {
            return getServicioRegistroTelematicoTercerosConnectorWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.terceros.registro.sgm.tecdoc.ieci", "ServicioRegistroTelematicoTercerosConnectorWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.terceros.registro.sgm.tecdoc.ieci", "ServicioRegistroTelematicoTercerosConnectorWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ServicioRegistroTelematicoTercerosConnectorWebService".equals(portName)) {
            setServicioRegistroTelematicoTercerosConnectorWebServiceEndpointAddress(address);
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
