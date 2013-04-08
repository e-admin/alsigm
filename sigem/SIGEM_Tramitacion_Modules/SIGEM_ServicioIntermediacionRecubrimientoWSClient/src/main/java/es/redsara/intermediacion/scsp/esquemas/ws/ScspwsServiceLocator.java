/**
 * ScspwsServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

public class ScspwsServiceLocator extends org.apache.axis.client.Service implements es.redsara.intermediacion.scsp.esquemas.ws.ScspwsService {

    public ScspwsServiceLocator() {
    }


    public ScspwsServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ScspwsServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for scspwsSoap11
    private java.lang.String scspwsSoap11_address = "http://HOST/CONTEXTO/ws";

    public java.lang.String getscspwsSoap11Address() {
        return scspwsSoap11_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String scspwsSoap11WSDDServiceName = "scspwsSoap11";

    public java.lang.String getscspwsSoap11WSDDServiceName() {
        return scspwsSoap11WSDDServiceName;
    }

    public void setscspwsSoap11WSDDServiceName(java.lang.String name) {
        scspwsSoap11WSDDServiceName = name;
    }

    public es.redsara.intermediacion.scsp.esquemas.ws.Scspws getscspwsSoap11() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(scspwsSoap11_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getscspwsSoap11(endpoint);
    }

    public es.redsara.intermediacion.scsp.esquemas.ws.Scspws getscspwsSoap11(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.redsara.intermediacion.scsp.esquemas.ws.ScspwsSoap11Stub _stub = new es.redsara.intermediacion.scsp.esquemas.ws.ScspwsSoap11Stub(portAddress, this);
            _stub.setPortName(getscspwsSoap11WSDDServiceName());
            
            
            HandlerRegistry hr = getHandlerRegistry();
            QName portName = new QName("http://intermediacion.redsara.es/scsp/esquemas/ws", "scspwsSoap11");
            List handlerChain = hr.getHandlerChain(portName);
            HandlerInfo hi = new HandlerInfo();
            hi.setHandlerClass(ResponseHandler.class);
            handlerChain.add(hi);
            
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setscspwsSoap11EndpointAddress(java.lang.String address) {
        scspwsSoap11_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.redsara.intermediacion.scsp.esquemas.ws.Scspws.class.isAssignableFrom(serviceEndpointInterface)) {
                es.redsara.intermediacion.scsp.esquemas.ws.ScspwsSoap11Stub _stub = new es.redsara.intermediacion.scsp.esquemas.ws.ScspwsSoap11Stub(new java.net.URL(scspwsSoap11_address), this);
                _stub.setPortName(getscspwsSoap11WSDDServiceName());
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
        if ("scspwsSoap11".equals(inputPortName)) {
            return getscspwsSoap11();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws", "scspwsService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws", "scspwsSoap11"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("scspwsSoap11".equals(portName)) {
            setscspwsSoap11EndpointAddress(address);
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
