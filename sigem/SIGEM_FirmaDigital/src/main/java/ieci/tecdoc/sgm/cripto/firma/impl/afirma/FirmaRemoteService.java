/**
 * FirmaRemoteService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.impl.afirma;

public interface FirmaRemoteService extends javax.xml.rpc.Service {
    public java.lang.String getFirmaServidorAddress();

    public FirmaRemote getFirmaServidor() throws javax.xml.rpc.ServiceException;

    public FirmaRemote getFirmaServidor(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
