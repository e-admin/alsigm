/**
 * ConsultaExpedientesWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.ct.ws.client;

public class ConsultaExpedientesWebServiceServiceLocator extends org.apache.axis.client.Service implements ieci.tecdoc.sgm.ct.ws.client.ConsultaExpedientesWebServiceService {

    public ConsultaExpedientesWebServiceServiceLocator() {
    }


    public ConsultaExpedientesWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ConsultaExpedientesWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ConsultaExpedientesWebService
    private java.lang.String ConsultaExpedientesWebService_address = "http://localhost:8080/SIGEM_ConsultaWS/services/ConsultaExpedientesWebService";

    public java.lang.String getConsultaExpedientesWebServiceAddress() {
        return ConsultaExpedientesWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ConsultaExpedientesWebServiceWSDDServiceName = "ConsultaExpedientesWebService";

    public java.lang.String getConsultaExpedientesWebServiceWSDDServiceName() {
        return ConsultaExpedientesWebServiceWSDDServiceName;
    }

    public void setConsultaExpedientesWebServiceWSDDServiceName(java.lang.String name) {
        ConsultaExpedientesWebServiceWSDDServiceName = name;
    }

    public ieci.tecdoc.sgm.ct.ws.client.ConsultaExpedientesWebService getConsultaExpedientesWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ConsultaExpedientesWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getConsultaExpedientesWebService(endpoint);
    }

    public ieci.tecdoc.sgm.ct.ws.client.ConsultaExpedientesWebService getConsultaExpedientesWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ieci.tecdoc.sgm.ct.ws.client.ConsultaExpedientesWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.ct.ws.client.ConsultaExpedientesWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getConsultaExpedientesWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setConsultaExpedientesWebServiceEndpointAddress(java.lang.String address) {
        ConsultaExpedientesWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ieci.tecdoc.sgm.ct.ws.client.ConsultaExpedientesWebService.class.isAssignableFrom(serviceEndpointInterface)) {
                ieci.tecdoc.sgm.ct.ws.client.ConsultaExpedientesWebServiceSoapBindingStub _stub = new ieci.tecdoc.sgm.ct.ws.client.ConsultaExpedientesWebServiceSoapBindingStub(new java.net.URL(ConsultaExpedientesWebService_address), this);
                _stub.setPortName(getConsultaExpedientesWebServiceWSDDServiceName());
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
        if ("ConsultaExpedientesWebService".equals(inputPortName)) {
            return getConsultaExpedientesWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "ConsultaExpedientesWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "ConsultaExpedientesWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ConsultaExpedientesWebService".equals(portName)) {
            setConsultaExpedientesWebServiceEndpointAddress(address);
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
