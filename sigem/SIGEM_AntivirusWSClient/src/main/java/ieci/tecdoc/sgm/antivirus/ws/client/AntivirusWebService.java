/**
 * AntivirusWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.antivirus.ws.client;

public interface AntivirusWebService extends java.rmi.Remote {
    public ieci.tecdoc.sgm.antivirus.ws.client.RetornoBooleano comprobarFicheroContenido(ieci.tecdoc.sgm.antivirus.ws.client.RetornoArrayByte contenidoFichero, boolean borrar) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.antivirus.ws.client.RetornoBooleano comprobarFicheroRuta(java.lang.String rutaFichero, boolean borrar) throws java.rmi.RemoteException;
}
