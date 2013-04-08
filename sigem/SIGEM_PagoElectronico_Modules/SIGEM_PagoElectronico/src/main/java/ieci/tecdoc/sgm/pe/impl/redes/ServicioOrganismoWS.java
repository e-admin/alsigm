/**
 * ServicioOrganismoWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.pe.impl.redes;

import ieci.tecdoc.sgm.pe.impl.redes.AltaMasiva;
import ieci.tecdoc.sgm.pe.impl.redes.BusquedaEeff;
import ieci.tecdoc.sgm.pe.impl.redes.BusquedaSop;
import ieci.tecdoc.sgm.pe.impl.redes.Cuaderno57;
import ieci.tecdoc.sgm.pe.impl.redes.Cuaderno60_1_2;
import ieci.tecdoc.sgm.pe.impl.redes.Cuaderno60_3;
import ieci.tecdoc.sgm.pe.impl.redes.Cuaderno65;
import ieci.tecdoc.sgm.pe.impl.redes.CuadernoRespuesta57;
import ieci.tecdoc.sgm.pe.impl.redes.CuadernoRespuesta60_1_2;
import ieci.tecdoc.sgm.pe.impl.redes.CuadernoRespuesta60_3;
import ieci.tecdoc.sgm.pe.impl.redes.CuadernoRespuesta65;
import ieci.tecdoc.sgm.pe.impl.redes.ResultadoPagoLotesWS;

public interface ServicioOrganismoWS extends java.rmi.Remote {
    public java.lang.Object[] consulta(BusquedaSop busquedaSop) throws java.rmi.RemoteException;
    public java.lang.Object[] consultaEeff(BusquedaEeff busquedaEeff) throws java.rmi.RemoteException;
    public java.lang.Object[] cargaMasiva(AltaMasiva altaMasiva) throws java.rmi.RemoteException;
    public CuadernoRespuesta60_1_2 pago60_1_2(Cuaderno60_1_2 cuaderno60_1_2) throws java.rmi.RemoteException;
    public CuadernoRespuesta60_3 pago60_3(Cuaderno60_3 cuaderno60_3) throws java.rmi.RemoteException;
    public CuadernoRespuesta65 pago65(Cuaderno65 cuaderno65) throws java.rmi.RemoteException;
    public CuadernoRespuesta57 pago57(Cuaderno57 cuaderno57) throws java.rmi.RemoteException;
    public ResultadoPagoLotesWS pagoLotes(java.lang.String[] cuadernos) throws java.rmi.RemoteException;
}
