/**
 * RegistroTelematicoWebServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registro.ws.client.axis;

public interface RegistroTelematicoWebServiceService extends javax.xml.rpc.Service {
    public java.lang.String getRegistroTelematicoWebServiceAddress();

    public ieci.tecdoc.sgm.registro.ws.client.axis.RegistroTelematicoWebService_PortType getRegistroTelematicoWebService() throws javax.xml.rpc.ServiceException;

    public ieci.tecdoc.sgm.registro.ws.client.axis.RegistroTelematicoWebService_PortType getRegistroTelematicoWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
