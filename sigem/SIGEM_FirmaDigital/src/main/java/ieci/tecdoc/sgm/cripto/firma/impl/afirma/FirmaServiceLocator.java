/**
 * FirmaServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.impl.afirma;

public class FirmaServiceLocator extends org.apache.axis.client.Service implements FirmaService {

    public FirmaServiceLocator() {
    }


    public FirmaServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FirmaServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ValidarFirma
    private java.lang.String ValidarFirma_address = "https://pre-afirma.redinteradministrativa.es/afirmaws/services/ValidarFirma";

    public java.lang.String getValidarFirmaAddress() {
        return ValidarFirma_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ValidarFirmaWSDDServiceName = "ValidarFirma";

    public java.lang.String getValidarFirmaWSDDServiceName() {
        return ValidarFirmaWSDDServiceName;
    }

    public void setValidarFirmaWSDDServiceName(java.lang.String name) {
        ValidarFirmaWSDDServiceName = name;
    }

    public Firma getValidarFirma() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ValidarFirma_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getValidarFirma(endpoint);
    }

    public Firma getValidarFirma(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ValidarFirmaSoapBindingStub _stub = new ValidarFirmaSoapBindingStub(portAddress, this);
            _stub.setPortName(getValidarFirmaWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setValidarFirmaEndpointAddress(java.lang.String address) {
        ValidarFirma_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (Firma.class.isAssignableFrom(serviceEndpointInterface)) {
                ValidarFirmaSoapBindingStub _stub = new ValidarFirmaSoapBindingStub(new java.net.URL(ValidarFirma_address), this);
                _stub.setPortName(getValidarFirmaWSDDServiceName());
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
        if ("ValidarFirma".equals(inputPortName)) {
            return getValidarFirma();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://afirmaws/services/ValidarFirma", "FirmaService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://afirmaws/services/ValidarFirma", "ValidarFirma"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ValidarFirma".equals(portName)) {
            setValidarFirmaEndpointAddress(address);
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
