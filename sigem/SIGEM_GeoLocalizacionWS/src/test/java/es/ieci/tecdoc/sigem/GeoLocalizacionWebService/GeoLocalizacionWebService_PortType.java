/**
 * GeoLocalizacionWebService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.GeoLocalizacionWebService;

public interface GeoLocalizacionWebService_PortType extends java.rmi.Remote {
    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.URLsPlano verPlanoPorCoordenadas(es.ieci.tecdoc.sigem.GeoLocalizacionWebService.PeticionPlanoCoordenadas datosLocalizacion) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.URLsPlano verPlanoPorReferenciaCatastral(es.ieci.tecdoc.sigem.GeoLocalizacionWebService.PeticionPlanoReferenciaCatastral datosLocalizacion) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.URLsPlano verPlanoPorIdVia(es.ieci.tecdoc.sigem.GeoLocalizacionWebService.PeticionPlanoVia datosLocalizacion) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.URLsPlano verPlanoPorIdNumeroPolicia(es.ieci.tecdoc.sigem.GeoLocalizacionWebService.PeticionPlanoPortal datosLocalizacion) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Mapas verPlanosPublicados(int codigoINEMunicipio) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Vias validarVia(java.lang.String nombreVia, int codigoINEMunicipio) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Portales validarPortal(int idVia, java.lang.String numeroPortal) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Portal obtenerPortal(int idPortal) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.RetornoLogico validarDireccionPostal(java.lang.String tipoVia, java.lang.String nombreVia, java.lang.String numeroPortal, int codigoINEMunicipio) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Via validarDireccionPostalCompleta(java.lang.String tipoVia, java.lang.String nombreVia, java.lang.String numeroPortal, int codigoINEMunicipio) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Provincias obtenerProvincias() throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Municipios obtenerMunicipios(int idProvincia) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.TiposVia obtenerTiposDeVia() throws java.rmi.RemoteException;
}
