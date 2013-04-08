/**
 * GeoLocalizacionWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.GeoLocalizacionWebService;

public class GeoLocalizacionWebServiceServiceLocator extends org.apache.axis.client.Service implements es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceService {

    public GeoLocalizacionWebServiceServiceLocator() {
    }


    public GeoLocalizacionWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GeoLocalizacionWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for GeoLocalizacionWebService
    private java.lang.String GeoLocalizacionWebService_address = "http://localhost:8080/SIGEM_GeoLocalizacionWS/services/GeoLocalizacionWebService";

    public java.lang.String getGeoLocalizacionWebServiceAddress() {
        return GeoLocalizacionWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GeoLocalizacionWebServiceWSDDServiceName = "GeoLocalizacionWebService";

    public java.lang.String getGeoLocalizacionWebServiceWSDDServiceName() {
        return GeoLocalizacionWebServiceWSDDServiceName;
    }

    public void setGeoLocalizacionWebServiceWSDDServiceName(java.lang.String name) {
        GeoLocalizacionWebServiceWSDDServiceName = name;
    }

    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebService_PortType getGeoLocalizacionWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GeoLocalizacionWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGeoLocalizacionWebService(endpoint);
    }

    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebService_PortType getGeoLocalizacionWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub _stub = new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getGeoLocalizacionWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGeoLocalizacionWebServiceEndpointAddress(java.lang.String address) {
        GeoLocalizacionWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub _stub = new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub(new java.net.URL(GeoLocalizacionWebService_address), this);
                _stub.setPortName(getGeoLocalizacionWebServiceWSDDServiceName());
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
        if ("GeoLocalizacionWebService".equals(inputPortName)) {
            return getGeoLocalizacionWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "GeoLocalizacionWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "GeoLocalizacionWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("GeoLocalizacionWebService".equals(portName)) {
            setGeoLocalizacionWebServiceEndpointAddress(address);
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
