/**
 * CatastroWebServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.CatastroWebService;

public interface CatastroWebServiceService extends javax.xml.rpc.Service {
    public java.lang.String getCatastroWebServiceAddress();

    public es.ieci.tecdoc.sigem.CatastroWebService.CatastroWebService_PortType getCatastroWebService() throws javax.xml.rpc.ServiceException;

    public es.ieci.tecdoc.sigem.CatastroWebService.CatastroWebService_PortType getCatastroWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
