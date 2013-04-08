/**
 * PagoTelematicoWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.pe.ws.client;

public interface PagoTelematicoWebService extends java.rmi.Remote {
    public ieci.tecdoc.sgm.pe.ws.client.Liquidacion altaLiquidacion(ieci.tecdoc.sgm.pe.ws.client.Liquidacion poLiquidacion, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.pe.ws.client.RetornoServicio bajaLiquidacion(ieci.tecdoc.sgm.pe.ws.client.Liquidacion poLiquidacion, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.pe.ws.client.ListaLiquidaciones buscarLiquidaciones(ieci.tecdoc.sgm.pe.ws.client.CriterioBusquedaLiquidacion poCriterio, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.pe.ws.client.ListaTasas buscarTasas(ieci.tecdoc.sgm.pe.ws.client.CriterioBusquedaTasa poCriterio, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.pe.ws.client.Pago detallePago(ieci.tecdoc.sgm.pe.ws.client.Pago poPago, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.pe.ws.client.Liquidacion modificarLiquidacion(ieci.tecdoc.sgm.pe.ws.client.Liquidacion poLiquidacion, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.pe.ws.client.Tasa obtenerDatosTasa(ieci.tecdoc.sgm.pe.ws.client.Tasa poTasa, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.pe.ws.client.DocumentoPago obtenerDocumentoPago(ieci.tecdoc.sgm.pe.ws.client.Pago poPago, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.pe.ws.client.Pago realizarPago(ieci.tecdoc.sgm.pe.ws.client.Pago poPago, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException;
}
