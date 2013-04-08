/**
 * ValidacionRemoteServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.validacion.impl.afirma;

public class ValidacionRemoteServiceLocator extends org.apache.axis.client.Service implements ValidacionRemoteService {

    public ValidacionRemoteServiceLocator() {
    }


    public ValidacionRemoteServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ValidacionRemoteServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ValidarCertificado
    private java.lang.String ValidarCertificado_address = "https://${balanceador}/afirmaws/services/ValidarCertificado";

    public java.lang.String getValidarCertificadoAddress() {
        return ValidarCertificado_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ValidarCertificadoWSDDServiceName = "ValidarCertificado";

    public java.lang.String getValidarCertificadoWSDDServiceName() {
        return ValidarCertificadoWSDDServiceName;
    }

    public void setValidarCertificadoWSDDServiceName(java.lang.String name) {
        ValidarCertificadoWSDDServiceName = name;
    }

    public ValidacionRemote getValidarCertificado() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ValidarCertificado_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getValidarCertificado(endpoint);
    }

    public ValidacionRemote getValidarCertificado(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ValidarCertificadoSoapBindingStub _stub = new ValidarCertificadoSoapBindingStub(portAddress, this);
            _stub.setPortName(getValidarCertificadoWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setValidarCertificadoEndpointAddress(java.lang.String address) {
        ValidarCertificado_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ValidacionRemote.class.isAssignableFrom(serviceEndpointInterface)) {
                ValidarCertificadoSoapBindingStub _stub = new ValidarCertificadoSoapBindingStub(new java.net.URL(ValidarCertificado_address), this);
                _stub.setPortName(getValidarCertificadoWSDDServiceName());
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
        if ("ValidarCertificado".equals(inputPortName)) {
            return getValidarCertificado();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://afirmaws/services/ValidarCertificado", "ValidacionRemoteService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://afirmaws/services/ValidarCertificado", "ValidarCertificado"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ValidarCertificado".equals(portName)) {
            setValidarCertificadoEndpointAddress(address);
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
