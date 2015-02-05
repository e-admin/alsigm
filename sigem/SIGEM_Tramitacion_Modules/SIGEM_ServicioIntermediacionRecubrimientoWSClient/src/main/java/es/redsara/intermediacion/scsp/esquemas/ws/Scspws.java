/**
 * Scspws.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws;

public interface Scspws extends java.rmi.Remote {
    public es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta peticionSincrona(es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionSincrona peticionSincrona) throws java.rmi.RemoteException, es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault;
    public es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion.ConfirmacionPeticion peticionAsincrona(es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionAsincrona peticionAsincrona) throws java.rmi.RemoteException, es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault;
    public es.redsara.intermediacion.scsp.esquemas.ws.respuesta.RespuestaPdf peticionPdf(es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionPdf peticionPdf) throws java.rmi.RemoteException, es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault;
    public es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta peticionRecover(es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionRecover peticionRecover) throws java.rmi.RemoteException, es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault;
    public es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta solicitudRespuesta(es.redsara.intermediacion.scsp.esquemas.ws.solicitudRespuesta.SolicitudRespuesta solicitudRespuesta) throws java.rmi.RemoteException, es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault;
}
