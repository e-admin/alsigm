/**
 * MensajesCortosWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.mensajesCortos.ws.client;

public class MensajesCortosWebServiceServiceLocator extends org.apache.axis.client.Service implements ieci.tecdoc.sgm.mensajesCortos.ws.client.MensajesCortosWebServiceService {

    public MensajesCortosWebServiceServiceLocator() {
    }


    public MensajesCortosWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MensajesCortosWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MensajesCortosWebService
    private java.lang.String MensajesCortosWebService_address = "http://localhost:8080/SIGEM_MensajesCortosWS/services/MensajesCortosWebService";

    public java.lang.String getMensajesCortosWebServiceAddress() {
        return MensajesCortosWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MensajesCortosWebServiceWSDDServiceName = "MensajesCortosWebService";

    public java.lang.String getMensajesCortosWebServiceWSDDServiceName() {
        return MensajesCortosWebServiceWSDDServiceName;
    }

    public void setMensajesCortosWebServiceWSDDServiceName(java.lang.String name) {
        MensajesCortosWebServiceWSDDServiceName = name;
    }

    public ieci.tecdoc.sgm.mensajesCortos.ws.client.MensajesCortosWebService getMensajesCortosWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MensajesCortosWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMensajesCortosWebService(endpoint);
    }

    public ieci.tecdoc.sgm.mensajesCortos.ws.client.MensajesCortosWebService getMensajesCortosWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ieci.tecdoc.sgm.mensajesCortos.ws.client.MensajesCortosWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.mensajesCortos.ws.client.MensajesCortosWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getMensajesCortosWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMensajesCortosWebServiceEndpointAddress(java.lang.String address) {
        MensajesCortosWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ieci.tecdoc.sgm.mensajesCortos.ws.client.MensajesCortosWebService.class.isAssignableFrom(serviceEndpointInterface)) {
                ieci.tecdoc.sgm.mensajesCortos.ws.client.MensajesCortosWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.mensajesCortos.ws.client.MensajesCortosWebServiceSoapBindingStub(new java.net.URL(MensajesCortosWebService_address), this);
                _stub.setPortName(getMensajesCortosWebServiceWSDDServiceName());
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
        if ("MensajesCortosWebService".equals(inputPortName)) {
            return getMensajesCortosWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.ws.mensajesCortos.sgm.tecdoc.ieci", "MensajesCortosWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.ws.mensajesCortos.sgm.tecdoc.ieci", "MensajesCortosWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MensajesCortosWebService".equals(portName)) {
            setMensajesCortosWebServiceEndpointAddress(address);
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
