/**
 * ServicioFirmaDigitalWebService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.ws.client;

public interface ServicioFirmaDigitalWebService_PortType extends java.rmi.Remote {
    public ieci.tecdoc.sgm.cripto.firma.ws.client.Firma firmar(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido poContenido) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma validarFirma(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido pabB64Sign, ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido pabB64Content) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.cripto.firma.ws.client.RegistroFirma registrarFirma(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido signatrue, ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido certificate, ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido hash) throws java.rmi.RemoteException;
}
