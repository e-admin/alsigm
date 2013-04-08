package ieci.tecdoc.sgm.pe.ws.client;

public class PagoTelematicoWebServiceProxy implements ieci.tecdoc.sgm.pe.ws.client.PagoTelematicoWebService {
  private String _endpoint = null;
  private ieci.tecdoc.sgm.pe.ws.client.PagoTelematicoWebService pagoTelematicoWebService = null;
  
  public PagoTelematicoWebServiceProxy() {
    _initPagoTelematicoWebServiceProxy();
  }
  
  private void _initPagoTelematicoWebServiceProxy() {
    try {
      pagoTelematicoWebService = (new ieci.tecdoc.sgm.pe.ws.client.PagoTelematicoWebServiceServiceLocator()).getPagoTelematicoWebService();
      if (pagoTelematicoWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)pagoTelematicoWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)pagoTelematicoWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (pagoTelematicoWebService != null)
      ((javax.xml.rpc.Stub)pagoTelematicoWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ieci.tecdoc.sgm.pe.ws.client.PagoTelematicoWebService getPagoTelematicoWebService() {
    if (pagoTelematicoWebService == null)
      _initPagoTelematicoWebServiceProxy();
    return pagoTelematicoWebService;
  }
  
  public ieci.tecdoc.sgm.pe.ws.client.Liquidacion altaLiquidacion(ieci.tecdoc.sgm.pe.ws.client.Liquidacion poLiquidacion, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (pagoTelematicoWebService == null)
      _initPagoTelematicoWebServiceProxy();
    return pagoTelematicoWebService.altaLiquidacion(poLiquidacion, entidad);
  }
  
  public ieci.tecdoc.sgm.pe.ws.client.RetornoServicio bajaLiquidacion(ieci.tecdoc.sgm.pe.ws.client.Liquidacion poLiquidacion, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (pagoTelematicoWebService == null)
      _initPagoTelematicoWebServiceProxy();
    return pagoTelematicoWebService.bajaLiquidacion(poLiquidacion, entidad);
  }
  
  public ieci.tecdoc.sgm.pe.ws.client.ListaLiquidaciones buscarLiquidaciones(ieci.tecdoc.sgm.pe.ws.client.CriterioBusquedaLiquidacion poCriterio, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (pagoTelematicoWebService == null)
      _initPagoTelematicoWebServiceProxy();
    return pagoTelematicoWebService.buscarLiquidaciones(poCriterio, entidad);
  }
  
  public ieci.tecdoc.sgm.pe.ws.client.ListaTasas buscarTasas(ieci.tecdoc.sgm.pe.ws.client.CriterioBusquedaTasa poCriterio, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (pagoTelematicoWebService == null)
      _initPagoTelematicoWebServiceProxy();
    return pagoTelematicoWebService.buscarTasas(poCriterio, entidad);
  }
  
  public ieci.tecdoc.sgm.pe.ws.client.Pago detallePago(ieci.tecdoc.sgm.pe.ws.client.Pago poPago, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (pagoTelematicoWebService == null)
      _initPagoTelematicoWebServiceProxy();
    return pagoTelematicoWebService.detallePago(poPago, entidad);
  }
  
  public ieci.tecdoc.sgm.pe.ws.client.Liquidacion modificarLiquidacion(ieci.tecdoc.sgm.pe.ws.client.Liquidacion poLiquidacion, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (pagoTelematicoWebService == null)
      _initPagoTelematicoWebServiceProxy();
    return pagoTelematicoWebService.modificarLiquidacion(poLiquidacion, entidad);
  }
  
  public ieci.tecdoc.sgm.pe.ws.client.Tasa obtenerDatosTasa(ieci.tecdoc.sgm.pe.ws.client.Tasa poTasa, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (pagoTelematicoWebService == null)
      _initPagoTelematicoWebServiceProxy();
    return pagoTelematicoWebService.obtenerDatosTasa(poTasa, entidad);
  }
  
  public ieci.tecdoc.sgm.pe.ws.client.DocumentoPago obtenerDocumentoPago(ieci.tecdoc.sgm.pe.ws.client.Pago poPago, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (pagoTelematicoWebService == null)
      _initPagoTelematicoWebServiceProxy();
    return pagoTelematicoWebService.obtenerDocumentoPago(poPago, entidad);
  }
  
  public ieci.tecdoc.sgm.pe.ws.client.Pago realizarPago(ieci.tecdoc.sgm.pe.ws.client.Pago poPago, ieci.tecdoc.sgm.pe.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (pagoTelematicoWebService == null)
      _initPagoTelematicoWebServiceProxy();
    return pagoTelematicoWebService.realizarPago(poPago, entidad);
  }
  
  
}