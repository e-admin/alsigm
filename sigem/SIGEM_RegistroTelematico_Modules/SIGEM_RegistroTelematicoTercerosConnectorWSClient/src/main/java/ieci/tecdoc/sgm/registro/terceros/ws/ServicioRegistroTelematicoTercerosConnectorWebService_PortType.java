/**
 * ServicioRegistroTelematicoTercerosConnectorWebService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registro.terceros.ws;

public interface ServicioRegistroTelematicoTercerosConnectorWebService_PortType extends java.rmi.Remote {
    public ieci.tecdoc.sgm.registro.terceros.ws.TerceroVO buscarTercero(java.lang.String identificador) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.registro.terceros.ws.TerceroVO buscarTerceroPorEntidad(java.lang.String entidad, java.lang.String identificador) throws java.rmi.RemoteException;
}
