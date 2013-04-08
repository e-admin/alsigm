/**
 * AntivirusWebServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.AntivirusWebService;

public interface AntivirusWebServiceService extends javax.xml.rpc.Service {
    public java.lang.String getAntivirusWebServiceAddress();

    public es.ieci.tecdoc.sigem.AntivirusWebService.AntivirusWebService_PortType getAntivirusWebService() throws javax.xml.rpc.ServiceException;

    public es.ieci.tecdoc.sigem.AntivirusWebService.AntivirusWebService_PortType getAntivirusWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
