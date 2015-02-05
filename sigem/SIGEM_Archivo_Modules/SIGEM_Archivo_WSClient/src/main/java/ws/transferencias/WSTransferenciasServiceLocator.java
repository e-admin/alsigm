/**
 * WSTransferenciasServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.transferencias;

public class WSTransferenciasServiceLocator extends org.apache.axis.client.Service implements ws.transferencias.WSTransferenciasService {

    public WSTransferenciasServiceLocator() {
    }


    public WSTransferenciasServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSTransferenciasServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSTransferencias
    private java.lang.String WSTransferencias_address = "http://localhost:8080/archidoc/services/WSTransferencias";

    public java.lang.String getWSTransferenciasAddress() {
        return WSTransferencias_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSTransferenciasWSDDServiceName = "WSTransferencias";

    public java.lang.String getWSTransferenciasWSDDServiceName() {
        return WSTransferenciasWSDDServiceName;
    }

    public void setWSTransferenciasWSDDServiceName(java.lang.String name) {
        WSTransferenciasWSDDServiceName = name;
    }

    public ws.transferencias.WSTransferencias getWSTransferencias() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSTransferencias_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSTransferencias(endpoint);
    }

    public ws.transferencias.WSTransferencias getWSTransferencias(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ws.transferencias.WSTransferenciasSoapBindingStub _stub = new ws.transferencias.WSTransferenciasSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSTransferenciasWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSTransferenciasEndpointAddress(java.lang.String address) {
        WSTransferencias_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ws.transferencias.WSTransferencias.class.isAssignableFrom(serviceEndpointInterface)) {
                ws.transferencias.WSTransferenciasSoapBindingStub _stub = new ws.transferencias.WSTransferenciasSoapBindingStub(new java.net.URL(WSTransferencias_address), this);
                _stub.setPortName(getWSTransferenciasWSDDServiceName());
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
        if ("WSTransferencias".equals(inputPortName)) {
            return getWSTransferencias();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://transferencias.ws/", "WSTransferenciasService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://transferencias.ws/", "WSTransferencias"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSTransferencias".equals(portName)) {
            setWSTransferenciasEndpointAddress(address);
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
