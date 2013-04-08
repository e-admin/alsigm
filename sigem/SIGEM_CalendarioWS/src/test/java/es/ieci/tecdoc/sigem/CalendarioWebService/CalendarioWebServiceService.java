/** 
 * CalendarioWebServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.CalendarioWebService;

public interface CalendarioWebServiceService extends javax.xml.rpc.Service {
    public java.lang.String getCalendarioWebServiceAddress();

    public es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioWebService_PortType getCalendarioWebService() throws javax.xml.rpc.ServiceException;

    public es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioWebService_PortType getCalendarioWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
