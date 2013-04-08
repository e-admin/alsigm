/**
 * GeoLocalizacionWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.geolocalizacion.ws.client;

public interface GeoLocalizacionWebService extends java.rmi.Remote {
    public ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano verPlanoPorCoordenadas(ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoCoordenadas datosLocalizacion) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano verPlanoPorReferenciaCatastral(ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoReferenciaCatastral datosLocalizacion) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano verPlanoPorIdVia(ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoVia datosLocalizacion) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.geolocalizacion.ws.client.URLsPlano verPlanoPorIdNumeroPolicia(ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlanoPortal datosLocalizacion) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.geolocalizacion.ws.client.Mapas verPlanosPublicados(int codigoINEMunicipio) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.geolocalizacion.ws.client.Vias validarVia(java.lang.String nombreVia, int codigoINEMunicipio) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.geolocalizacion.ws.client.Portales validarPortal(int idVia, java.lang.String numeroPortal) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.geolocalizacion.ws.client.Portal obtenerPortal(int idPortal) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.geolocalizacion.ws.client.RetornoLogico validarDireccionPostal(java.lang.String tipoVia, java.lang.String nombreVia, java.lang.String numeroPortal, int codigoINEMunicipio) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.geolocalizacion.ws.client.Via validarDireccionPostalCompleta(java.lang.String tipoVia, java.lang.String nombreVia, java.lang.String numeroPortal, int codigoINEMunicipio) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.geolocalizacion.ws.client.Provincias obtenerProvincias() throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.geolocalizacion.ws.client.Municipios obtenerMunicipios(int idProvincia) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.geolocalizacion.ws.client.TiposVia obtenerTiposDeVia() throws java.rmi.RemoteException;
}
