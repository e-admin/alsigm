/**
 * FirmaService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.impl.afirma;

public interface FirmaUsuarioService extends javax.xml.rpc.Service {
	
    public java.lang.String getFirmaUsuarioAddress();

    public RegistrarFirma getFirmaUsuario() throws javax.xml.rpc.ServiceException;

    public RegistrarFirma getFirmaUsuario(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
