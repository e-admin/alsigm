/**
 * AntivirusWebService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.AntivirusWebService;

public interface AntivirusWebService_PortType extends java.rmi.Remote {
    public es.ieci.tecdoc.sigem.AntivirusWebService.RetornoBooleano comprobarFicheroContenido(es.ieci.tecdoc.sigem.AntivirusWebService.RetornoArrayByte contenidoFichero, boolean borrar) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.AntivirusWebService.RetornoBooleano comprobarFicheroRuta(java.lang.String rutaFichero, boolean borrar) throws java.rmi.RemoteException;
}
