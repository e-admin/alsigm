/**
 * CertificacionWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.certificacion.ws.client;

public interface CertificacionWebService extends java.rmi.Remote {
    public ieci.tecdoc.sgm.certificacion.ws.client.RetornoPdf generarCertificacionPagos(ieci.tecdoc.sgm.certificacion.ws.client.pago.Pago[] pagos, ieci.tecdoc.sgm.certificacion.ws.client.Usuario usuario, ieci.tecdoc.sgm.certificacion.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.certificacion.ws.client.RetornoServicio altaCertificacion(java.lang.String idUsuario, java.lang.String idFichero, ieci.tecdoc.sgm.certificacion.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.certificacion.ws.client.RetornoServicio eliminarCertificacion(java.lang.String idFichero, ieci.tecdoc.sgm.certificacion.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.certificacion.ws.client.Certificacion obtenerCertificacion(java.lang.String idUsuario, java.lang.String idFichero, ieci.tecdoc.sgm.certificacion.ws.client.Entidad entidad) throws java.rmi.RemoteException;
}
