/**
 * FirmaService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.impl.afirma;

public interface FirmaService extends javax.xml.rpc.Service {
    public java.lang.String getValidarFirmaAddress();

    public Firma getValidarFirma() throws javax.xml.rpc.ServiceException;

    public Firma getValidarFirma(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
