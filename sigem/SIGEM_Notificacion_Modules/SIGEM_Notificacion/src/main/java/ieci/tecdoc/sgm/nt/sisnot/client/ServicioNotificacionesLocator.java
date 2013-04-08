/**
 * ServicioNotificacionesLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.nt.sisnot.client;

import ieci.tecdoc.sgm.nt.ConstantesNotificacion;
import ieci.tecdoc.sgm.nt.config.NotificacionesConfig;
import ieci.tecdoc.sgm.nt.config.beans.ConectorDefinition;

public class ServicioNotificacionesLocator extends org.apache.axis.client.Service implements ieci.tecdoc.sgm.nt.sisnot.client.ServicioNotificaciones {

    public ServicioNotificacionesLocator() {
    }


    public ServicioNotificacionesLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServicioNotificacionesLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServicioNotificacionesEndPointPort
   // private java.lang.String ServicioNotificacionesEndPointPort_address = "http://cnavarros:8080/snSisnot/snServicioNotificacionesEJB/ServicioNotificacionesEndPointPort";
    private java.lang.String ServicioNotificacionesEndPointPort_address = "http://cnavarros:8080/snSisnot/snServicioNotificacionesEJB/ServicioNotificacionesEndPointPort";

    public java.lang.String getServicioNotificacionesEndPointPortAddress() {
        return ServicioNotificacionesEndPointPort_address;
    }

    
//    public java.lang.String getServicioNotificacionesEndPointPortAddress() {
//        java.util.Properties propiedades = new java.util.Properties();
//        
//        ConectorDefinition def=NotificacionesConfig.getConectorDefinition(ConstantesNotificacion.ID_SISTEMA_SISNOT); 
//        
//        try{
//        	ConectorDefinition conDef=NotificacionesConfig.getConectorDefinition(ConstantesNotificacion.ID_SISTEMA_SISNOT); 
//        	if(conDef!=null) return conDef.getUrl();
//        }catch (Exception e){
//            //no se hace nada. la excepción saltará posteriormente
//        }
//        //return ServicioNotificacionesEndPointPort_address;
//        return null;
//    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServicioNotificacionesEndPointPortWSDDServiceName = "ServicioNotificacionesEndPointPort";

    public java.lang.String getServicioNotificacionesEndPointPortWSDDServiceName() {
        return ServicioNotificacionesEndPointPortWSDDServiceName;
    }

    public void setServicioNotificacionesEndPointPortWSDDServiceName(java.lang.String name) {
        ServicioNotificacionesEndPointPortWSDDServiceName = name;
    }

    public ieci.tecdoc.sgm.nt.sisnot.client.ServicioNotificacionesEndPoint getServicioNotificacionesEndPointPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(getServicioNotificacionesEndPointPortAddress());
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServicioNotificacionesEndPointPort(endpoint);
    }

    public ieci.tecdoc.sgm.nt.sisnot.client.ServicioNotificacionesEndPoint getServicioNotificacionesEndPointPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	ieci.tecdoc.sgm.nt.sisnot.client.ServicioNotificacionesEndPointBindingStub _stub = new ieci.tecdoc.sgm.nt.sisnot.client.ServicioNotificacionesEndPointBindingStub(portAddress, this);
            _stub.setPortName(getServicioNotificacionesEndPointPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ieci.tecdoc.sgm.nt.sisnot.client.ServicioNotificacionesEndPoint.class.isAssignableFrom(serviceEndpointInterface)) {
            	ieci.tecdoc.sgm.nt.sisnot.client.ServicioNotificacionesEndPointBindingStub _stub = new ieci.tecdoc.sgm.nt.sisnot.client.ServicioNotificacionesEndPointBindingStub(new java.net.URL(getServicioNotificacionesEndPointPortAddress()), this);
                _stub.setPortName(getServicioNotificacionesEndPointPortWSDDServiceName());
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
        if ("ServicioNotificacionesEndPointPort".equals(inputPortName)) {
            return getServicioNotificacionesEndPointPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle", "ServicioNotificaciones");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle", "ServicioNotificacionesEndPointPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
  /* public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ServicioNotificacionesEndPointPort".equals(portName)) {
            setServicioNotificacionesEndPointPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }
*/
    /**
    * Set the endpoint address for the specified port name.
    */
   /* public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }*/

}
