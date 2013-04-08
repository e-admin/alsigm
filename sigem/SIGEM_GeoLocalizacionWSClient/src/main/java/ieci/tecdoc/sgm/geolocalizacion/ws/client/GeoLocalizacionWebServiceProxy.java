package ieci.tecdoc.sgm.geolocalizacion.ws.client;

public class GeoLocalizacionWebServiceProxy implements ieci.tecdoc.sgm.geolocalizacion.ws.client.GeoLocalizacionWebService {
  private String _endpoint = null;
  private ieci.tecdoc.sgm.geolocalizacion.ws.client.GeoLocalizacionWebService geoLocalizacionWebService = null;
  
  public GeoLocalizacionWebServiceProxy() {
    _initGeoLocalizacionWebServiceProxy();
  }
  
  private void _initGeoLocalizacionWebServiceProxy() {
    try {
      geoLocalizacionWebService = (new ieci.tecdoc.sgm.geolocalizacion.ws.client.GeoLocalizacionWebServiceServiceLocator()).getGeoLocalizacionWebService();
      if (geoLocalizacionWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)geoLocalizacionWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)geoLocalizacionWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (geoLocalizacionWebService != null)
      ((javax.xml.rpc.Stub)geoLocalizacionWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ieci.tecdoc.sgm.geolocalizacion.ws.client.GeoLocalizacionWebService getGeoLocalizacionWebService() {
    if (geoLocalizacionWebService == null)
      _initGeoLocalizacionWebServiceProxy();
    return geoLocalizacionWebService;
  }
  
  public ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano verPlanoPorCoordenadas(ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoCoordenadas datosLocalizacion) throws java.rmi.RemoteException{
    if (geoLocalizacionWebService == null)
      _initGeoLocalizacionWebServiceProxy();
    return geoLocalizacionWebService.verPlanoPorCoordenadas(datosLocalizacion);
  }
  
  public ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano verPlanoPorReferenciaCatastral(ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoReferenciaCatastral datosLocalizacion) throws java.rmi.RemoteException{
    if (geoLocalizacionWebService == null)
      _initGeoLocalizacionWebServiceProxy();
    return geoLocalizacionWebService.verPlanoPorReferenciaCatastral(datosLocalizacion);
  }
  
  public ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano verPlanoPorIdVia(ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoVia datosLocalizacion) throws java.rmi.RemoteException{
    if (geoLocalizacionWebService == null)
      _initGeoLocalizacionWebServiceProxy();
    return geoLocalizacionWebService.verPlanoPorIdVia(datosLocalizacion);
  }
  
  public ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano verPlanoPorIdNumeroPolicia(ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoPortal datosLocalizacion) throws java.rmi.RemoteException{
    if (geoLocalizacionWebService == null)
      _initGeoLocalizacionWebServiceProxy();
    return geoLocalizacionWebService.verPlanoPorIdNumeroPolicia(datosLocalizacion);
  }
  
  public ieci.tecdoc.sgm.geolocalizacion.ws.client.Mapas verPlanosPublicados(int codigoINEMunicipio) throws java.rmi.RemoteException{
    if (geoLocalizacionWebService == null)
      _initGeoLocalizacionWebServiceProxy();
    return geoLocalizacionWebService.verPlanosPublicados(codigoINEMunicipio);
  }
  
  public ieci.tecdoc.sgm.geolocalizacion.ws.client.Vias validarVia(java.lang.String nombreVia, int codigoINEMunicipio) throws java.rmi.RemoteException{
    if (geoLocalizacionWebService == null)
      _initGeoLocalizacionWebServiceProxy();
    return geoLocalizacionWebService.validarVia(nombreVia, codigoINEMunicipio);
  }
  
  public ieci.tecdoc.sgm.geolocalizacion.ws.client.Portales validarPortal(int idVia, java.lang.String numeroPortal) throws java.rmi.RemoteException{
    if (geoLocalizacionWebService == null)
      _initGeoLocalizacionWebServiceProxy();
    return geoLocalizacionWebService.validarPortal(idVia, numeroPortal);
  }
  
  public ieci.tecdoc.sgm.geolocalizacion.ws.client.Portal obtenerPortal(int idPortal) throws java.rmi.RemoteException{
    if (geoLocalizacionWebService == null)
      _initGeoLocalizacionWebServiceProxy();
    return geoLocalizacionWebService.obtenerPortal(idPortal);
  }
  
  public ieci.tecdoc.sgm.geolocalizacion.ws.client.RetornoLogico validarDireccionPostal(java.lang.String tipoVia, java.lang.String nombreVia, java.lang.String numeroPortal, int codigoINEMunicipio) throws java.rmi.RemoteException{
    if (geoLocalizacionWebService == null)
      _initGeoLocalizacionWebServiceProxy();
    return geoLocalizacionWebService.validarDireccionPostal(tipoVia, nombreVia, numeroPortal, codigoINEMunicipio);
  }
  
  public ieci.tecdoc.sgm.geolocalizacion.ws.client.Via validarDireccionPostalCompleta(java.lang.String tipoVia, java.lang.String nombreVia, java.lang.String numeroPortal, int codigoINEMunicipio) throws java.rmi.RemoteException{
    if (geoLocalizacionWebService == null)
      _initGeoLocalizacionWebServiceProxy();
    return geoLocalizacionWebService.validarDireccionPostalCompleta(tipoVia, nombreVia, numeroPortal, codigoINEMunicipio);
  }
  
  public ieci.tecdoc.sgm.geolocalizacion.ws.client.Provincias obtenerProvincias() throws java.rmi.RemoteException{
    if (geoLocalizacionWebService == null)
      _initGeoLocalizacionWebServiceProxy();
    return geoLocalizacionWebService.obtenerProvincias();
  }
  
  public ieci.tecdoc.sgm.geolocalizacion.ws.client.Municipios obtenerMunicipios(int idProvincia) throws java.rmi.RemoteException{
    if (geoLocalizacionWebService == null)
      _initGeoLocalizacionWebServiceProxy();
    return geoLocalizacionWebService.obtenerMunicipios(idProvincia);
  }
  
  public ieci.tecdoc.sgm.geolocalizacion.ws.client.TiposVia obtenerTiposDeVia() throws java.rmi.RemoteException{
    if (geoLocalizacionWebService == null)
      _initGeoLocalizacionWebServiceProxy();
    return geoLocalizacionWebService.obtenerTiposDeVia();
  }
  
  
}