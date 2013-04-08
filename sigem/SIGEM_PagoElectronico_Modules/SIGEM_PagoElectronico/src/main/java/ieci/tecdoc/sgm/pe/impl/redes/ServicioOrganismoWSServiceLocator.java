/**
 * ServicioOrganismoWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.pe.impl.redes;

import ieci.tecdoc.sgm.pe.impl.redes.ServicioOrganismoWS;
import ieci.tecdoc.sgm.pe.impl.redes.ServicioOrganismoWSService;
import ieci.tecdoc.sgm.pe.impl.redes.ServicioOrganismoWSSoapBindingStub;

public class ServicioOrganismoWSServiceLocator extends org.apache.axis.client.Service implements ServicioOrganismoWSService {

    public ServicioOrganismoWSServiceLocator() {
    }


    public ServicioOrganismoWSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServicioOrganismoWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServicioOrganismoWS
//    private java.lang.String ServicioOrganismoWS_address = "https://spt.demo.red.es/PAGO/services/ServicioOrganismoWS";
    private java.lang.String ServicioOrganismoWS_address = Configuracion.obtenerPropiedad(Configuracion.KEY_ENDPOINT_URL);

    public java.lang.String getServicioOrganismoWSAddress() {
        return ServicioOrganismoWS_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServicioOrganismoWSWSDDServiceName = "ServicioOrganismoWS";

    public java.lang.String getServicioOrganismoWSWSDDServiceName() {
        return ServicioOrganismoWSWSDDServiceName;
    }

    public void setServicioOrganismoWSWSDDServiceName(java.lang.String name) {
        ServicioOrganismoWSWSDDServiceName = name;
    }

    public ServicioOrganismoWS getServicioOrganismoWS() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServicioOrganismoWS_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServicioOrganismoWS(endpoint);
    }

    public ServicioOrganismoWS getServicioOrganismoWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ServicioOrganismoWSSoapBindingStub _stub = new ServicioOrganismoWSSoapBindingStub(portAddress, this);
            _stub.setPortName(getServicioOrganismoWSWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServicioOrganismoWSEndpointAddress(java.lang.String address) {
        ServicioOrganismoWS_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ServicioOrganismoWS.class.isAssignableFrom(serviceEndpointInterface)) {
                ServicioOrganismoWSSoapBindingStub _stub = new ServicioOrganismoWSSoapBindingStub(new java.net.URL(ServicioOrganismoWS_address), this);
                _stub.setPortName(getServicioOrganismoWSWSDDServiceName());
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
        if ("ServicioOrganismoWS".equals(inputPortName)) {
            return getServicioOrganismoWS();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        //return new javax.xml.namespace.QName("https://spt.demo.red.es/PAGO/services/ServicioOrganismoWS", "ServicioOrganismoWSService");
        return new javax.xml.namespace.QName(Configuracion.obtenerPropiedad(Configuracion.KEY_ENDPOINT_URL), "ServicioOrganismoWSService");    	
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            //ports.add(new javax.xml.namespace.QName("https://spt.demo.red.es/PAGO/services/ServicioOrganismoWS", "ServicioOrganismoWS"));
            ports.add(new javax.xml.namespace.QName(Configuracion.obtenerPropiedad(Configuracion.KEY_ENDPOINT_URL), "ServicioOrganismoWS"));            
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ServicioOrganismoWS".equals(portName)) {
            setServicioOrganismoWSEndpointAddress(address);
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
