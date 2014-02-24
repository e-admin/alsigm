/**
 * ServicioRegistroWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registropresencial.ws.server;

public class ServicioRegistroWebServiceServiceLocator extends org.apache.axis.client.Service implements ieci.tecdoc.sgm.registropresencial.ws.server.ServicioRegistroWebServiceService {

    public ServicioRegistroWebServiceServiceLocator() {
    }


    public ServicioRegistroWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServicioRegistroWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServicioRegistroWebService
    private java.lang.String ServicioRegistroWebService_address = "http://localhost:8080/SIGEM_RegistroPresencialWS/services/ServicioRegistroWebService";

    public java.lang.String getServicioRegistroWebServiceAddress() {
        return ServicioRegistroWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServicioRegistroWebServiceWSDDServiceName = "ServicioRegistroWebService";

    public java.lang.String getServicioRegistroWebServiceWSDDServiceName() {
        return ServicioRegistroWebServiceWSDDServiceName;
    }

    public void setServicioRegistroWebServiceWSDDServiceName(java.lang.String name) {
        ServicioRegistroWebServiceWSDDServiceName = name;
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.ServicioRegistroWebService getServicioRegistroWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServicioRegistroWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServicioRegistroWebService(endpoint);
    }

    public ieci.tecdoc.sgm.registropresencial.ws.server.ServicioRegistroWebService getServicioRegistroWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ieci.tecdoc.sgm.registropresencial.ws.server.ServicioRegistroWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.registropresencial.ws.server.ServicioRegistroWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getServicioRegistroWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServicioRegistroWebServiceEndpointAddress(java.lang.String address) {
        ServicioRegistroWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ieci.tecdoc.sgm.registropresencial.ws.server.ServicioRegistroWebService.class.isAssignableFrom(serviceEndpointInterface)) {
                ieci.tecdoc.sgm.registropresencial.ws.server.ServicioRegistroWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.registropresencial.ws.server.ServicioRegistroWebServiceSoapBindingStub(new java.net.URL(ServicioRegistroWebService_address), this);
                _stub.setPortName(getServicioRegistroWebServiceWSDDServiceName());
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
        if ("ServicioRegistroWebService".equals(inputPortName)) {
            return getServicioRegistroWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ServicioRegistroWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "ServicioRegistroWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ServicioRegistroWebService".equals(portName)) {
            setServicioRegistroWebServiceEndpointAddress(address);
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
