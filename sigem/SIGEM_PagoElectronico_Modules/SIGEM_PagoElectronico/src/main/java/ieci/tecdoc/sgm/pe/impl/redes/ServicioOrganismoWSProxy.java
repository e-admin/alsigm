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
import ieci.tecdoc.sgm.pe.impl.redes.ServicioOrganismoWS;
import ieci.tecdoc.sgm.pe.impl.redes.ServicioOrganismoWSServiceLocator;

public class ServicioOrganismoWSProxy implements ServicioOrganismoWS {
  private String _endpoint = null;
  private ServicioOrganismoWS servicioOrganismoWS = null;
  
  public ServicioOrganismoWSProxy() {
    _initServicioOrganismoWSProxy();
  }
  
  public ServicioOrganismoWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initServicioOrganismoWSProxy();
  }
  
  private void _initServicioOrganismoWSProxy() {
    try {
      servicioOrganismoWS = (new ServicioOrganismoWSServiceLocator()).getServicioOrganismoWS();
      if (servicioOrganismoWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)servicioOrganismoWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)servicioOrganismoWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (servicioOrganismoWS != null)
      ((javax.xml.rpc.Stub)servicioOrganismoWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ServicioOrganismoWS getServicioOrganismoWS() {
    if (servicioOrganismoWS == null)
      _initServicioOrganismoWSProxy();
    return servicioOrganismoWS;
  }
  
  public java.lang.Object[] consulta(BusquedaSop busquedaSop) throws java.rmi.RemoteException{
    if (servicioOrganismoWS == null)
      _initServicioOrganismoWSProxy();
    return servicioOrganismoWS.consulta(busquedaSop);
  }
  
  public java.lang.Object[] consultaEeff(BusquedaEeff busquedaEeff) throws java.rmi.RemoteException{
    if (servicioOrganismoWS == null)
      _initServicioOrganismoWSProxy();
    return servicioOrganismoWS.consultaEeff(busquedaEeff);
  }
  
  public java.lang.Object[] cargaMasiva(AltaMasiva altaMasiva) throws java.rmi.RemoteException{
    if (servicioOrganismoWS == null)
      _initServicioOrganismoWSProxy();
    return servicioOrganismoWS.cargaMasiva(altaMasiva);
  }
  
  public ResultadoPagoLotesWS pagoLotes(java.lang.String[] cuadernos) throws java.rmi.RemoteException{
    if (servicioOrganismoWS == null)
      _initServicioOrganismoWSProxy();
    return servicioOrganismoWS.pagoLotes(cuadernos);
  }
  
  public CuadernoRespuesta60_1_2 pago60_1_2(Cuaderno60_1_2 cuaderno60_1_2) throws java.rmi.RemoteException{
    if (servicioOrganismoWS == null)
      _initServicioOrganismoWSProxy();
    return servicioOrganismoWS.pago60_1_2(cuaderno60_1_2);
  }
  
  public CuadernoRespuesta60_3 pago60_3(Cuaderno60_3 cuaderno60_3) throws java.rmi.RemoteException{
    if (servicioOrganismoWS == null)
      _initServicioOrganismoWSProxy();
    return servicioOrganismoWS.pago60_3(cuaderno60_3);
  }
  
  public CuadernoRespuesta65 pago65(Cuaderno65 cuaderno65) throws java.rmi.RemoteException{
    if (servicioOrganismoWS == null)
      _initServicioOrganismoWSProxy();
    return servicioOrganismoWS.pago65(cuaderno65);
  }
  
  public CuadernoRespuesta57 pago57(Cuaderno57 cuaderno57) throws java.rmi.RemoteException{
    if (servicioOrganismoWS == null)
      _initServicioOrganismoWSProxy();
    return servicioOrganismoWS.pago57(cuaderno57);
  }
  
  
}