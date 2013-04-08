package ieci.tecdoc.sgm.geolocalizacion.ws.client;

import java.rmi.RemoteException;

import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;
import ieci.tecdoc.sgm.core.services.geolocalizacion.GeoLocalizacionServicioException;
import ieci.tecdoc.sgm.core.services.geolocalizacion.ServicioGeoLocalizacion;

public class GeoLocalizacionWSRemoteClient implements ServicioGeoLocalizacion{

	private GeoLocalizacionWebService service;

	public GeoLocalizacionWebService getService() {
		return service;
	}

	public void setService(GeoLocalizacionWebService service) {
		this.service = service;
	}
	
	public ieci.tecdoc.sgm.core.services.geolocalizacion.URLsPlano verPlanoPorCoordenadas(ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoCoordenadas datosLocalizacion) throws GeoLocalizacionServicioException {
		try{
			URLsPlano plano =  getService().verPlanoPorCoordenadas(getPeticionPlanoCoordenadasWS(datosLocalizacion));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)plano)){
				return getURLsPlanoServicio(plano);
			}else{
				throw getGeoLocalizacionException((IRetornoServicio)plano);
			}
		}catch (RemoteException e) {
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.geolocalizacion.URLsPlano verPlanoPorReferenciaCatastral(ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoReferenciaCatastral datosLocalizacion) throws GeoLocalizacionServicioException {
		try{
			URLsPlano plano =  getService().verPlanoPorReferenciaCatastral(getPeticionPlanoReferenciaCatastralWS(datosLocalizacion));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)plano)){
				return getURLsPlanoServicio(plano);
			}else{
				throw getGeoLocalizacionException((IRetornoServicio)plano);
			}
		}catch (RemoteException e) {
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.geolocalizacion.URLsPlano verPlanoPorIdVia(ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoVia datosLocalizacion) throws GeoLocalizacionServicioException {
		try{
			URLsPlano plano =  getService().verPlanoPorIdVia(getPeticionPlanoViaWS(datosLocalizacion));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)plano)){
				return getURLsPlanoServicio(plano);
			}else{
				throw getGeoLocalizacionException((IRetornoServicio)plano);
			}
		}catch (RemoteException e) {
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.geolocalizacion.URLsPlano verPlanoPorIdNumeroPolicia(ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoPortal datosLocalizacion) throws GeoLocalizacionServicioException {
		try{
			URLsPlano plano =  getService().verPlanoPorIdNumeroPolicia(getPeticionPlanoPortalWS(datosLocalizacion));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)plano)){
				return getURLsPlanoServicio(plano);
			}else{
				throw getGeoLocalizacionException((IRetornoServicio)plano);
			}
		}catch (RemoteException e) {
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.geolocalizacion.Mapas verPlanosPublicados(int idEntidadMunicipal) throws GeoLocalizacionServicioException {
		try{
			Mapas mapas =  getService().verPlanosPublicados(idEntidadMunicipal);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)mapas)){
				return getMapasServicio(mapas);
			}else{
				throw getGeoLocalizacionException((IRetornoServicio)mapas);
			}
		}catch (RemoteException e) {
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.geolocalizacion.Vias validarVia(String nombreVia, int codigoINEMunicipio) throws GeoLocalizacionServicioException {
		try{
			Vias vias =  getService().validarVia(nombreVia, codigoINEMunicipio);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)vias)){
				return getViasServicio(vias);
			}else{
				throw getGeoLocalizacionException((IRetornoServicio)vias);
			}
		}catch (RemoteException e) {
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.geolocalizacion.Portales validarPortal(int idVia, String numeroPortal) throws GeoLocalizacionServicioException {
		try{
			Portales portales =  getService().validarPortal(idVia, numeroPortal);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)portales)){
				return getPortalesServicio(portales);
			}else{
				throw getGeoLocalizacionException((IRetornoServicio)portales);
			}
		}catch (RemoteException e) {
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.geolocalizacion.Portal obtenerPortal(int idPortal) throws GeoLocalizacionServicioException {
		try{
			Portal portal =  getService().obtenerPortal(idPortal);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)portal)){
				return getPortalServicio(portal);
			}else{
				throw getGeoLocalizacionException((IRetornoServicio)portal);
			}
		}catch (RemoteException e) {
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public boolean validarDireccionPostal(String tipoVia, String nombreVia, String numeroPortal, int codigoINEMunicipio) throws GeoLocalizacionServicioException {
		try{
			RetornoLogico valido =  getService().validarDireccionPostal(tipoVia, nombreVia, numeroPortal, codigoINEMunicipio);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)valido)){
				return getRetornoLogicoServicio(valido);
			}else{
				throw getGeoLocalizacionException((IRetornoServicio)valido);
			}
		}catch (RemoteException e) {
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.geolocalizacion.Via validarDireccionPostalCompleta(String tipoVia, String nombreVia, String numeroPortal, int codigoINEMunicipio) throws GeoLocalizacionServicioException {
		try{
			Via via =  getService().validarDireccionPostalCompleta(tipoVia, nombreVia, numeroPortal, codigoINEMunicipio);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)via)){
				return getViaServicio(via);
			}else{
				throw getGeoLocalizacionException((IRetornoServicio)via);
			}
		}catch (RemoteException e) {
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.geolocalizacion.Provincias obtenerProvincias() throws GeoLocalizacionServicioException {
		try{
			Provincias provincias =  getService().obtenerProvincias();
			if(ServiciosUtils.isReturnOK((IRetornoServicio)provincias)){
				return getProvinciasServicio(provincias);
			}else{
				throw getGeoLocalizacionException((IRetornoServicio)provincias);
			}
		}catch (RemoteException e) {
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.geolocalizacion.Municipios obtenerMunicipios(int idProvincia) throws GeoLocalizacionServicioException {
		try{
			Municipios municipios =  getService().obtenerMunicipios(idProvincia);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)municipios)){
				return getMunicipiosServicio(municipios);
			}else{
				throw getGeoLocalizacionException((IRetornoServicio)municipios);
			}
		}catch (RemoteException e) {
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.geolocalizacion.TiposVia obtenerTiposDeVia() throws GeoLocalizacionServicioException {
		try{
			TiposVia tiposVia =  getService().obtenerTiposDeVia();
			if(ServiciosUtils.isReturnOK((IRetornoServicio)tiposVia)){
				return getTiposViaServicio(tiposVia);
			}else{
				throw getGeoLocalizacionException((IRetornoServicio)tiposVia);
			}
		}catch (RemoteException e) {
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	private GeoLocalizacionServicioException getGeoLocalizacionException(IRetornoServicio oReturn){
		return new GeoLocalizacionServicioException(Long.valueOf(oReturn.getErrorCode()).longValue());
	}
	
	private PeticionPlanoCoordenadas getPeticionPlanoCoordenadasWS(ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoCoordenadas oDatosLocalizacion) {
		if (oDatosLocalizacion == null)
			return null;
		
		PeticionPlanoCoordenadas poDatosLocalizacion = new PeticionPlanoCoordenadas();
		
		poDatosLocalizacion.setAlto(oDatosLocalizacion.getAlto());
		poDatosLocalizacion.setAncho(oDatosLocalizacion.getAncho());
		poDatosLocalizacion.setCodigoINEMunicipio(oDatosLocalizacion.getCodigoINEMunicipio());
		poDatosLocalizacion.setCoordX(oDatosLocalizacion.getCoordX());
		poDatosLocalizacion.setCoordY(oDatosLocalizacion.getCoordY());
		poDatosLocalizacion.setEscala(oDatosLocalizacion.getEscala());
		poDatosLocalizacion.setIdMapa(oDatosLocalizacion.getIdMapa());
		
		return poDatosLocalizacion;
	}
	
	private PeticionPlanoReferenciaCatastral getPeticionPlanoReferenciaCatastralWS(ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoReferenciaCatastral oDatosLocalizacion) {
		if (oDatosLocalizacion == null)
			return null;
		
		PeticionPlanoReferenciaCatastral poDatosLocalizacion = new PeticionPlanoReferenciaCatastral();
		
		poDatosLocalizacion.setAlto(oDatosLocalizacion.getAlto());
		poDatosLocalizacion.setAncho(oDatosLocalizacion.getAncho());
		poDatosLocalizacion.setCodigoINEMunicipio(oDatosLocalizacion.getCodigoINEMunicipio());
		poDatosLocalizacion.setReferenciaCatastral(oDatosLocalizacion.getReferenciaCatastral());
		poDatosLocalizacion.setEscala(oDatosLocalizacion.getEscala());
		poDatosLocalizacion.setIdMapa(oDatosLocalizacion.getIdMapa());
		
		return poDatosLocalizacion;
	}
	
	private PeticionPlanoVia getPeticionPlanoViaWS(ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoVia oDatosLocalizacion) {
		if (oDatosLocalizacion == null)
			return null;
		
		PeticionPlanoVia poDatosLocalizacion = new PeticionPlanoVia();
		
		poDatosLocalizacion.setAlto(oDatosLocalizacion.getAlto());
		poDatosLocalizacion.setAncho(oDatosLocalizacion.getAncho());
		poDatosLocalizacion.setCodigoINEMunicipio(oDatosLocalizacion.getCodigoINEMunicipio());
		poDatosLocalizacion.setVia(oDatosLocalizacion.getVia());
		poDatosLocalizacion.setEscala(oDatosLocalizacion.getEscala());
		poDatosLocalizacion.setIdMapa(oDatosLocalizacion.getIdMapa());
		
		return poDatosLocalizacion;
	}
	
	private PeticionPlanoPortal getPeticionPlanoPortalWS(ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoPortal oDatosLocalizacion) {
		if (oDatosLocalizacion == null)
			return null;
		
		PeticionPlanoPortal poDatosLocalizacion = new PeticionPlanoPortal();
		
		poDatosLocalizacion.setAlto(oDatosLocalizacion.getAlto());
		poDatosLocalizacion.setAncho(oDatosLocalizacion.getAncho());
		poDatosLocalizacion.setCodigoINEMunicipio(oDatosLocalizacion.getCodigoINEMunicipio());
		poDatosLocalizacion.setPortal(oDatosLocalizacion.getPortal());
		poDatosLocalizacion.setEscala(oDatosLocalizacion.getEscala());
		poDatosLocalizacion.setIdMapa(oDatosLocalizacion.getIdMapa());
		
		return poDatosLocalizacion;
	}
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.URLsPlano getURLsPlanoServicio(URLsPlano oURLsPlano) {
		if (oURLsPlano == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.URLsPlano poURLsPlano = new ieci.tecdoc.sgm.core.services.geolocalizacion.URLsPlano();
		
		poURLsPlano.setUrlGuiaUrbana(oURLsPlano.getUrlGuiaUrbana());
		poURLsPlano.setUrlMapServer(oURLsPlano.getUrlMapServer());
		
		return poURLsPlano;
	}
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.Mapas  getMapasServicio(Mapas oMapas) {
		if (oMapas == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.Mapas poMapas = new ieci.tecdoc.sgm.core.services.geolocalizacion.Mapas();
		for(int i=0; i<oMapas.getMapas().length; i++)
			poMapas.add(getMapaServicio(oMapas.getMapas()[i]));
		
		return poMapas;
	}
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.Mapa getMapaServicio(Mapa oMapa) {
		if (oMapa == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.Mapa poMapa = new ieci.tecdoc.sgm.core.services.geolocalizacion.Mapa();
		
		poMapa.setMapdefault(oMapa.getMapdefault());
		poMapa.setMapid(oMapa.getMapid());
		poMapa.setMapidgeopista(oMapa.getMapidgeopista());
		poMapa.setMapidentidad(oMapa.getMapidentidad());
		poMapa.setMappublic(oMapa.getMappublic());
		poMapa.setMaxx(oMapa.getMaxx());
		poMapa.setMaxy(oMapa.getMaxy());
		poMapa.setMinx(oMapa.getMinx());
		poMapa.setMiny(oMapa.getMiny());
		poMapa.setName(oMapa.getName());
		poMapa.setSrid(oMapa.getSrid());
		
		return poMapa;
	}
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.Vias  getViasServicio(Vias oVias) {
		if (oVias == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.Vias poVias = new ieci.tecdoc.sgm.core.services.geolocalizacion.Vias();
		for(int i=0; i<oVias.getVias().length; i++)
			poVias.add(getViaServicio(oVias.getVias()[i]));
		
		return poVias;
	}
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.Via getViaServicio(Via oVia) {
		if (oVia == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.Via poVia = new ieci.tecdoc.sgm.core.services.geolocalizacion.Via();
		
		poVia.setClaseNombre(oVia.getClaseNombre());
		poVia.setCodigoINEMunicipio(oVia.getCodigoINEMunicipio());
		poVia.setCoordenadas(getConjuntoCoordenadasServicio(oVia.getCoordenadas()));
		poVia.setEstatus(oVia.getEstatus());
		poVia.setFuente(oVia.getFuente());
		poVia.setIdioma(oVia.getIdioma());
		poVia.setIdVia(oVia.getIdVia());
		poVia.setNombreVia(oVia.getNombreVia());
		poVia.setPortales(getPortalesServicio(oVia.getPortales()));
		poVia.setProvincia(oVia.getProvincia());
		poVia.setTipoVia(oVia.getTipoVia());
		
		return poVia;
	}
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.Portales  getPortalesServicio(Portales oPortales) {
		if (oPortales == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.Portales poPortales = new ieci.tecdoc.sgm.core.services.geolocalizacion.Portales();
		for(int i=0; i<oPortales.getPortales().length; i++)
			poPortales.add(getPortalServicio(oPortales.getPortales()[i]));
		
		return poPortales;
	}
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.Portal getPortalServicio(Portal oPortal) {
		if (oPortal == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.Portal poPortal = new ieci.tecdoc.sgm.core.services.geolocalizacion.Portal();
		
		poPortal.setClaseNombre(oPortal.getClaseNombre());
		poPortal.setCodigoINEMunicipio(oPortal.getCodigoINEMunicipio());
		poPortal.setCoords(getCoordenadaServicio(oPortal.getCoords()));
		poPortal.setEstatus(oPortal.getEstatus());
		poPortal.setFuente(oPortal.getFuente());
		poPortal.setIdioma(oPortal.getIdioma());
		poPortal.setIdPortal(oPortal.getIdPortal());
		poPortal.setIdVia(oPortal.getIdVia());
		poPortal.setNumPortal(oPortal.getNumPortal());
		poPortal.setProvincia(oPortal.getProvincia());
		poPortal.setTipoPortal(oPortal.getTipoPortal());
		
		return poPortal;
	}
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.ConjuntoCoordenadas  getConjuntoCoordenadasServicio(ConjuntoCoordenadas oConjuntoCoordenadas) {
		if (oConjuntoCoordenadas == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.ConjuntoCoordenadas poConjuntoCoordenadas = new ieci.tecdoc.sgm.core.services.geolocalizacion.ConjuntoCoordenadas();
		for(int i=0; i<oConjuntoCoordenadas.getConjuntoCoordenadas().length; i++)
			poConjuntoCoordenadas.add(getCoordenadasServicio(oConjuntoCoordenadas.getConjuntoCoordenadas()[i]));
		
		return poConjuntoCoordenadas;
	}
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.Coordenadas  getCoordenadasServicio(Coordenadas oCoordenadas) {
		if (oCoordenadas == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.Coordenadas poCoordenadas = new ieci.tecdoc.sgm.core.services.geolocalizacion.Coordenadas();
		for(int i=0; i<oCoordenadas.getCoordenadas().length; i++)
			poCoordenadas.add(getCoordenadaServicio(oCoordenadas.getCoordenadas()[i]));
		
		return poCoordenadas;
	}
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.Coordenada getCoordenadaServicio(Coordenada oCoordenada) {
		if (oCoordenada == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.Coordenada poCoordenada = new ieci.tecdoc.sgm.core.services.geolocalizacion.Coordenada();
		
		poCoordenada.setCoordX(oCoordenada.getCoordX());
		poCoordenada.setCoordY(oCoordenada.getCoordY());
				
		return poCoordenada;
	}
	
	private boolean getRetornoLogicoServicio(RetornoLogico oRetornoLogico) {
		if (oRetornoLogico == null)
			return false;
		return new Boolean(oRetornoLogico.getValor()).booleanValue();
	}
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.Provincias  getProvinciasServicio(Provincias oProvincias) {
		if (oProvincias == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.Provincias poProvincias = new ieci.tecdoc.sgm.core.services.geolocalizacion.Provincias();
		for(int i=0; i<oProvincias.getProvincias().length; i++)
			poProvincias.add(getProvinciaServicio(oProvincias.getProvincias()[i]));
		
		return poProvincias;
	}
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.Provincia getProvinciaServicio(Provincia oProvincia) {
		if (oProvincia == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.Provincia poProvincia = new ieci.tecdoc.sgm.core.services.geolocalizacion.Provincia();
		
		poProvincia.setCodigoINE(oProvincia.getCodigoINE());
		poProvincia.setComunidadAutonoma(oProvincia.getComunidadAutonoma());
		poProvincia.setNombreCoOficial(oProvincia.getNombreCoOficial());
		poProvincia.setNombreOficial(oProvincia.getNombreOficial());
		
		return poProvincia;
	}
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.Municipios  getMunicipiosServicio(Municipios oMunicipios) {
		if (oMunicipios == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.Municipios poMunicipios = new ieci.tecdoc.sgm.core.services.geolocalizacion.Municipios();
		for(int i=0; i<oMunicipios.getMunicipios().length; i++)
			poMunicipios.add(getMunicipioServicio(oMunicipios.getMunicipios()[i]));
		
		return poMunicipios;
	}
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.Municipio getMunicipioServicio(Municipio oMunicipio) {
		if (oMunicipio == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.Municipio poMunicipio = new ieci.tecdoc.sgm.core.services.geolocalizacion.Municipio();
		
		poMunicipio.setCodigoINE(oMunicipio.getCodigoINE());
		poMunicipio.setNombreCoOficial(oMunicipio.getNombreCoOficial());
		poMunicipio.setNombreOficial(oMunicipio.getNombreOficial());
		
		return poMunicipio;
	}
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.TiposVia  getTiposViaServicio(TiposVia oTiposVia) {
		if (oTiposVia == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.TiposVia poTiposVia = new ieci.tecdoc.sgm.core.services.geolocalizacion.TiposVia();
		for(int i=0; i<oTiposVia.getTiposVia().length; i++)
			poTiposVia.add(getTipoViaServicio(oTiposVia.getTiposVia()[i]));
		
		return poTiposVia;
	}
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.TipoVia getTipoViaServicio(TipoVia oTipoVia) {
		if (oTipoVia == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.TipoVia poTipoVia = new ieci.tecdoc.sgm.core.services.geolocalizacion.TipoVia();
		
		poTipoVia.setDescripcion(oTipoVia.getDescripcion());
		poTipoVia.setPatron(oTipoVia.getPatron());
		
		return poTipoVia;
	}
}
