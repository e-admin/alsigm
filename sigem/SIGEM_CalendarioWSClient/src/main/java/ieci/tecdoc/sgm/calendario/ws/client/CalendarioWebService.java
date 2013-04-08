/**
 * CalendarioWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.calendario.ws.client;

public interface CalendarioWebService extends java.rmi.Remote {
    public ieci.tecdoc.sgm.calendario.ws.client.Calendario obtenerCalendario(ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.calendario.ws.client.RetornoServicio nuevoCalendario(ieci.tecdoc.sgm.calendario.ws.client.Calendario calendario, ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.calendario.ws.client.RetornoServicio actualizarCalendario(ieci.tecdoc.sgm.calendario.ws.client.Calendario calendario, ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.calendario.ws.client.RetornoServicio eliminarCalendario(boolean borrarDias, ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.calendario.ws.client.CalendarioDia obtenerDiaCalendario(java.lang.String id, ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.calendario.ws.client.CalendarioDia nuevoDiaCalendario(ieci.tecdoc.sgm.calendario.ws.client.CalendarioDia dia, ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.calendario.ws.client.RetornoServicio actualizarDiaCalendario(ieci.tecdoc.sgm.calendario.ws.client.CalendarioDia dia, ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.calendario.ws.client.RetornoServicio eliminarDiaCalendario(java.lang.String id, ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.calendario.ws.client.CalendarioDias obtenerDiasCalendario(int tipo, ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.calendario.ws.client.RetornoCalendario proximoLaborable(java.lang.String fecha, ieci.tecdoc.sgm.calendario.ws.client.Entidad entidad) throws java.rmi.RemoteException;
}
