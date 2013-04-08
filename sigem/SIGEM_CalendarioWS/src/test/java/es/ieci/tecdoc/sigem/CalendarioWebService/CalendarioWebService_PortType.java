/** 
 * CalendarioWebService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.CalendarioWebService;

public interface CalendarioWebService_PortType extends java.rmi.Remote {
    public es.ieci.tecdoc.sigem.CalendarioWebService.Calendario obtenerCalendario(es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio nuevoCalendario(es.ieci.tecdoc.sigem.CalendarioWebService.Calendario calendario, es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio actualizarCalendario(es.ieci.tecdoc.sigem.CalendarioWebService.Calendario calendario, es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio eliminarCalendario(boolean borrarDias, es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDia obtenerDiaCalendario(java.lang.String id, es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDia nuevoDiaCalendario(es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDia dia, es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio actualizarDiaCalendario(es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDia dia, es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio eliminarDiaCalendario(java.lang.String id, es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioDias obtenerDiasCalendario(int tipo, es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CalendarioWebService.RetornoCalendario proximoLaborable(java.lang.String fecha, es.ieci.tecdoc.sigem.CalendarioWebService.Entidad entidad) throws java.rmi.RemoteException;
}
