/**
 * ServicioNotificacionesEndPoint.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.nt.sisnot.client;

public interface ServicioNotificacionesEndPoint extends java.rmi.Remote {
    public java.lang.String calcularAnagrama(java.lang.String string_1) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.nt.sisnot.client.types.EstadoNotificacion consultaEstadoNotificacion(long long_1) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.nt.sisnot.client.types.InfoSuscripcion consultaSuscripcion(java.lang.String string_1, java.lang.String string_2) throws java.rmi.RemoteException;
    public java.lang.String consultaSuscripcionSimple(java.lang.String string_1, java.lang.String string_2) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.nt.sisnot.client.types.ConfirmacionAltaNotificacion crearNotificacion(ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacion altaNotificacion_1) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.nt.sisnot.client.types.map.ConfirmacionAltaNotificacion[] crearNotificacionMultidestinatario(ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacionMultidestinatario altaNotificacionMultidestinatario_1) throws java.rmi.RemoteException;
    public java.lang.String[] crearNotificacionMultidestinatarioSimple(java.lang.String string_1, java.lang.String[] arrayOfString_2, java.lang.String string_3, java.lang.String string_4, java.lang.String string_5, java.lang.String string_6, java.lang.String string_7, java.lang.String string_8, java.lang.String string_9, java.lang.String string_10, java.lang.String string_11) throws java.rmi.RemoteException;
    public java.lang.String crearNotificacionSimple(java.lang.String string_1, java.lang.String string_2, java.lang.String string_3, java.lang.String string_4, java.lang.String string_5, java.lang.String string_6, java.lang.String string_7, java.lang.String string_8, java.lang.String string_9, java.lang.String string_10, java.lang.String string_11) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.nt.sisnot.client.types.map.ConfirmacionAltaNotificacion[] crearNotificaciones(ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacion[] arrayOfAltaNotificacion_1) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.nt.sisnot.client.types.ConfirmacionAltaNotificacion[][] crearNotificacionesMultidestinatario(ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacionMultidestinatario[] arrayOfAltaNotificacionMultidestinatario_1) throws java.rmi.RemoteException;
}