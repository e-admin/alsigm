/**
 * CatastroWebServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.catastro.ws.client;

public interface CatastroWebServiceService extends javax.xml.rpc.Service {
    public java.lang.String getCatastroWebServiceAddress();

    public ieci.tecdoc.sgm.catastro.ws.client.CatastroWebService getCatastroWebService() throws javax.xml.rpc.ServiceException;

    public ieci.tecdoc.sgm.catastro.ws.client.CatastroWebService getCatastroWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
