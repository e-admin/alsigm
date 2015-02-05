/**
 * AntivirusWebServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.antivirus.ws.client;

public interface AntivirusWebServiceService extends javax.xml.rpc.Service {
    public java.lang.String getAntivirusWebServiceAddress();

    public ieci.tecdoc.sgm.antivirus.ws.client.AntivirusWebService getAntivirusWebService() throws javax.xml.rpc.ServiceException;

    public ieci.tecdoc.sgm.antivirus.ws.client.AntivirusWebService getAntivirusWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
