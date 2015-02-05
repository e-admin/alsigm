package ieci.tecdoc.sgm.geolocalizacion.ws.server;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.services.geolocalizacion.GeoLocalizacionServicioException;
import ieci.tecdoc.sgm.core.services.geolocalizacion.ServicioGeoLocalizacion;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;

import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

public class GeoLocalizacionWebService {

	private static final Logger logger = Logger.getLogger(GeoLocalizacionWebService.class);
	
	private static final String SERVICE_NAME = ConstantesServicios.SERVICE_GEOLOCALIZACION;

	private ServicioGeoLocalizacion getServicioGeoLocalizacion() throws SOAPException, SigemException{
		String cImpl = UtilAxis.getImplementacion(org.apache.axis.MessageContext.getCurrentContext());
		if((cImpl == null) ||("".equals(cImpl))){
			return LocalizadorServicios.getServicioGeoLocalizacion();
		}else{
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".").append(cImpl);
			return LocalizadorServicios.getServicioGeoLocalizacion(sbImpl.toString());
		}
	}
	
	public URLsPlano verPlanoPorCoordenadas(PeticionPlanoCoordenadas datosLocalizacion){
		try {
			URLsPlano plano = getURLsPlanoWS(
					getServicioGeoLocalizacion().verPlanoPorCoordenadas(getPeticionPlanoCoordenadasServicio(datosLocalizacion))
				);
			return (URLsPlano)ServiciosUtils.completeReturnOK(plano);
		} catch (GeoLocalizacionServicioException e) {
			logger.error("Error al obtener el plano solicitado por coordenadas.", e);
			return (URLsPlano)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new URLsPlano()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener el plano solicitado por coordenadas.", e);
			return (URLsPlano)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new URLsPlano()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener el plano solicitado por coordenadas.", e);
			return (URLsPlano)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new URLsPlano()));			
		}
	}
	
	public URLsPlano verPlanoPorReferenciaCatastral(PeticionPlanoReferenciaCatastral datosLocalizacion){
		try {
			URLsPlano plano = getURLsPlanoWS(
					getServicioGeoLocalizacion().verPlanoPorReferenciaCatastral(getPeticionPlanoReferenciaCatastralServicio(datosLocalizacion))
				);
			return (URLsPlano)ServiciosUtils.completeReturnOK(plano);
		} catch (GeoLocalizacionServicioException e) {
			logger.error("Error al obtener el plano solicitado por número de referencia catastral.", e);
			return (URLsPlano)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new URLsPlano()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener el plano solicitado por número de referencia catastral.", e);
			return (URLsPlano)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new URLsPlano()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener el plano solicitado por número de referencia catastral.", e);
			return (URLsPlano)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new URLsPlano()));			
		}
	}
	
	public URLsPlano verPlanoPorIdVia(PeticionPlanoVia datosLocalizacion){
		try {
			URLsPlano plano = getURLsPlanoWS(
					getServicioGeoLocalizacion().verPlanoPorIdVia(getPeticionPlanoViaServicio(datosLocalizacion))
				);
			return (URLsPlano)ServiciosUtils.completeReturnOK(plano);
		} catch (GeoLocalizacionServicioException e) {
			logger.error("Error al obtener el plano solicitado por identificador de vía.", e);
			return (URLsPlano)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new URLsPlano()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener el plano solicitado por identificador de vía.", e);
			return (URLsPlano)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new URLsPlano()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener el plano solicitado por identificador de vía.", e);
			return (URLsPlano)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new URLsPlano()));			
		}
	}
	
	public URLsPlano verPlanoPorIdNumeroPolicia(PeticionPlanoPortal datosLocalizacion){
		try {
			URLsPlano plano = getURLsPlanoWS(
					getServicioGeoLocalizacion().verPlanoPorIdNumeroPolicia(getPeticionPlanoPortalServicio(datosLocalizacion))
				);
			return (URLsPlano)ServiciosUtils.completeReturnOK(plano);
		} catch (GeoLocalizacionServicioException e) {
			logger.error("Error al obtener el plano solicitado por identificador de policía.", e);
			return (URLsPlano)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new URLsPlano()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener el plano solicitado por identificador de policía.", e);
			return (URLsPlano)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new URLsPlano()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener el plano solicitado por identificador de policía.", e);
			return (URLsPlano)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new URLsPlano()));			
		}
	}
	
	public Mapas verPlanosPublicados(int codigoINEMunicipio){
		try {
			Mapas mapas = getMapasWS(
					getServicioGeoLocalizacion().verPlanosPublicados(codigoINEMunicipio)
				);
			return (Mapas)ServiciosUtils.completeReturnOK(mapas);
		} catch (GeoLocalizacionServicioException e) {
			logger.error("Error al obtener los planos publicados de un del municipio.", e);
			return (Mapas)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Mapas()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los planos publicados de un del municipio.", e);
			return (Mapas)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Mapas()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener los planos publicados de un del municipio.", e);
			return (Mapas)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Mapas()));			
		}
	}
	
	public Vias validarVia(String nombreVia, int codigoINEMunicipio){
		try {
			Vias vias = getViasWS(
					getServicioGeoLocalizacion().validarVia(nombreVia, codigoINEMunicipio)
				);
			return (Vias)ServiciosUtils.completeReturnOK(vias);
		} catch (GeoLocalizacionServicioException e) {
			logger.error("Error al validar la vía.", e);
			return (Vias)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Vias()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al validar la vía.", e);
			return (Vias)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Vias()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al validar la vía.", e);
			return (Vias)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Vias()));			
		}
	}
	
	public Portales validarPortal(int idVia, String numeroPortal){
		try {
			Portales portales = getPortalesWS(
					getServicioGeoLocalizacion().validarPortal(idVia, numeroPortal)
				);
			return (Portales)ServiciosUtils.completeReturnOK(portales);
		} catch (GeoLocalizacionServicioException e) {
			logger.error("Error al validar el portal.", e);
			return (Portales)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Portales()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al validar el portal.", e);
			return (Portales)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Portales()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al validar el portal.", e);
			return (Portales)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Portales()));			
		}
	}

	public Portal obtenerPortal(int idPortal){
		try {
			Portal portal = getPortalWS(
					getServicioGeoLocalizacion().obtenerPortal(idPortal)
				);
			return (Portal)ServiciosUtils.completeReturnOK(portal);
		} catch (GeoLocalizacionServicioException e) {
			logger.error("Error al obtener portal.", e);
			return (Portal)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Portal()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener portal.", e);
			return (Portal)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Portal()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener portal.", e);
			return (Portal)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Portal()));			
		}
	}
	
	public RetornoLogico validarDireccionPostal(String tipoVia, String nombreVia, String numeroPortal, int codigoINEMunicipio){
		try {
			RetornoLogico valido = getRetornoLogicoWS(
					getServicioGeoLocalizacion().validarDireccionPostal(tipoVia, nombreVia, numeroPortal, codigoINEMunicipio)
				);
			return (RetornoLogico)ServiciosUtils.completeReturnOK(valido);
		} catch (GeoLocalizacionServicioException e) {
			logger.error("Error al validar un direccion.", e);
			return (RetornoLogico)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoLogico()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al validar un direccion.", e);
			return (RetornoLogico)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoLogico()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al validar un direccion.", e);
			return (RetornoLogico)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new RetornoLogico()));			
		}
	}
	
	public Via validarDireccionPostalCompleta(String tipoVia, String nombreVia, String numeroPortal, int codigoINEMunicipio){
		try {
			Via via = getViaWS(
					getServicioGeoLocalizacion().validarDireccionPostalCompleta(tipoVia, nombreVia, numeroPortal, codigoINEMunicipio)
				);
			return (Via)ServiciosUtils.completeReturnOK(via);
		} catch (GeoLocalizacionServicioException e) {
			logger.error("Error al validar un direccion.", e);
			return (Via)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Via()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al validar un direccion.", e);
			return (Via)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Via()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al validar un direccion.", e);
			return (Via)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Via()));			
		}
	}
	
	public Provincias obtenerProvincias(){
		try {
			Provincias provincias = getProvinciasWS(
					getServicioGeoLocalizacion().obtenerProvincias()
				);
			return (Provincias)ServiciosUtils.completeReturnOK(provincias);
		} catch (GeoLocalizacionServicioException e) {
			logger.error("Error al obtener provincias.", e);
			return (Provincias)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Provincias()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener provincias.", e);
			return (Provincias)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Provincias()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener provincias.", e);
			return (Provincias)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Provincias()));			
		}
	}
	
	public Municipios obtenerMunicipios(int idProvincia){
		try {
			Municipios municipios = getMunicipiosWS(
					getServicioGeoLocalizacion().obtenerMunicipios(idProvincia)
				);
			return (Municipios)ServiciosUtils.completeReturnOK(municipios);
		} catch (GeoLocalizacionServicioException e) {
			logger.error("Error al obtener municipios.", e);
			return (Municipios)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Municipios()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener municipios.", e);
			return (Municipios)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Municipios()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener municipios.", e);
			return (Municipios)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Municipios()));			
		}
	}

	public TiposVia obtenerTiposDeVia(){
		try {
			TiposVia tiposVia = getTiposViaWS(
					getServicioGeoLocalizacion().obtenerTiposDeVia()
				);
			return (TiposVia)ServiciosUtils.completeReturnOK(tiposVia);
		} catch (GeoLocalizacionServicioException e) {
			logger.error("Error al obtener tipos de via.", e);
			return (TiposVia)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new TiposVia()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener tipos de via.", e);
			return (TiposVia)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new TiposVia()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener tipos de via.", e);
			return (TiposVia)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new TiposVia()));			
		}
	}
	
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoCoordenadas getPeticionPlanoCoordenadasServicio(PeticionPlanoCoordenadas oDatosLocalizacion) {
		if (oDatosLocalizacion == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoCoordenadas poDatosLocalizacion = new ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoCoordenadas();
		
		poDatosLocalizacion.setAlto(oDatosLocalizacion.getAlto());
		poDatosLocalizacion.setAncho(oDatosLocalizacion.getAncho());
		poDatosLocalizacion.setCodigoINEMunicipio(oDatosLocalizacion.getCodigoINEMunicipio());
		poDatosLocalizacion.setCoordX(oDatosLocalizacion.getCoordX());
		poDatosLocalizacion.setCoordY(oDatosLocalizacion.getCoordY());
		poDatosLocalizacion.setEscala(oDatosLocalizacion.getEscala());
		poDatosLocalizacion.setIdMapa(oDatosLocalizacion.getIdMapa());
		
		return  poDatosLocalizacion;
	}
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoReferenciaCatastral getPeticionPlanoReferenciaCatastralServicio(PeticionPlanoReferenciaCatastral oDatosLocalizacion) {
		if (oDatosLocalizacion == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoReferenciaCatastral poDatosLocalizacion = new ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoReferenciaCatastral();
		
		poDatosLocalizacion.setAlto(oDatosLocalizacion.getAlto());
		poDatosLocalizacion.setAncho(oDatosLocalizacion.getAncho());
		poDatosLocalizacion.setCodigoINEMunicipio(oDatosLocalizacion.getCodigoINEMunicipio());
		poDatosLocalizacion.setReferenciaCatastral(oDatosLocalizacion.getReferenciaCatastral());
		poDatosLocalizacion.setEscala(oDatosLocalizacion.getEscala());
		poDatosLocalizacion.setIdMapa(oDatosLocalizacion.getIdMapa());
		
		return  poDatosLocalizacion;
	}
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoVia getPeticionPlanoViaServicio(PeticionPlanoVia oDatosLocalizacion) {
		if (oDatosLocalizacion == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoVia poDatosLocalizacion = new ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoVia();
		
		poDatosLocalizacion.setAlto(oDatosLocalizacion.getAlto());
		poDatosLocalizacion.setAncho(oDatosLocalizacion.getAncho());
		poDatosLocalizacion.setCodigoINEMunicipio(oDatosLocalizacion.getCodigoINEMunicipio());
		poDatosLocalizacion.setVia(oDatosLocalizacion.getVia());
		poDatosLocalizacion.setEscala(oDatosLocalizacion.getEscala());
		poDatosLocalizacion.setIdMapa(oDatosLocalizacion.getIdMapa());
		
		return  poDatosLocalizacion;
	}
	
	private ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoPortal getPeticionPlanoPortalServicio(PeticionPlanoPortal oDatosLocalizacion) {
		if (oDatosLocalizacion == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoPortal poDatosLocalizacion = new ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoPortal();
		
		poDatosLocalizacion.setAlto(oDatosLocalizacion.getAlto());
		poDatosLocalizacion.setAncho(oDatosLocalizacion.getAncho());
		poDatosLocalizacion.setCodigoINEMunicipio(oDatosLocalizacion.getCodigoINEMunicipio());
		poDatosLocalizacion.setPortal(oDatosLocalizacion.getPortal());
		poDatosLocalizacion.setEscala(oDatosLocalizacion.getEscala());
		poDatosLocalizacion.setIdMapa(oDatosLocalizacion.getIdMapa());
		
		return  poDatosLocalizacion;
	}
	
	private URLsPlano getURLsPlanoWS(ieci.tecdoc.sgm.core.services.geolocalizacion.URLsPlano oURLsPlano) {
		if (oURLsPlano == null)
			return null;
		
		URLsPlano poURLsPlano = new URLsPlano();
		
		poURLsPlano.setUrlGuiaUrbana(oURLsPlano.getUrlGuiaUrbana());
		poURLsPlano.setUrlMapServer(oURLsPlano.getUrlMapServer());
		
		return  poURLsPlano;
	}

	private Mapas getMapasWS(ieci.tecdoc.sgm.core.services.geolocalizacion.Mapas oMapas) {
		if (oMapas == null || oMapas.count() == 0)
			return null;
		
		Mapas poMapas = new Mapas();
		Mapa[] poMapasArray = new Mapa[oMapas.count()];
		
		for(int i=0; i<oMapas.count(); i++)
			poMapasArray[i] = getMapaWS((ieci.tecdoc.sgm.core.services.geolocalizacion.Mapa)oMapas.get(i));
		poMapas.setMapas(poMapasArray);
		
		return  poMapas;
	}
	
	private Mapa getMapaWS(ieci.tecdoc.sgm.core.services.geolocalizacion.Mapa oMapa) {
		if (oMapa == null)
			return null;
		
		Mapa poMapa = new Mapa();
		
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
		
		return  poMapa;
	}
	
	private Via getViaWS(ieci.tecdoc.sgm.core.services.geolocalizacion.Via oVia) {
		if (oVia == null)
			return null;
		
		Via poVia = new Via();
		
		poVia.setClaseNombre(oVia.getClaseNombre());
		poVia.setCodigoINEMunicipio(oVia.getCodigoINEMunicipio());
		poVia.setCoordenadas(getConjuntoCoordenadasWS(oVia.getCoordenadas()));
		poVia.setEstatus(oVia.getEstatus());
		poVia.setFuente(oVia.getFuente());
		poVia.setIdioma(oVia.getIdioma());
		poVia.setIdVia(oVia.getIdVia());
		poVia.setNombreVia(oVia.getNombreVia());
		poVia.setPortales(getPortalesWS(oVia.getPortales()));
		poVia.setProvincia(oVia.getProvincia());
		poVia.setTipoVia(oVia.getTipoVia());
		
		return poVia;
	}
	
	private Vias getViasWS(ieci.tecdoc.sgm.core.services.geolocalizacion.Vias oVias) {
		if (oVias == null || oVias.count() == 0)
			return null;
		
		Vias poVias = new Vias();
		Via[] poViasArray = new Via[oVias.count()];
		
		for(int i=0; i<oVias.count(); i++)
			poViasArray[i] = getViaWS((ieci.tecdoc.sgm.core.services.geolocalizacion.Via)oVias.get(i));
		poVias.setVias(poViasArray);
		
		return  poVias;
	}
	
	private Portal getPortalWS(ieci.tecdoc.sgm.core.services.geolocalizacion.Portal oPortal) {
		if (oPortal == null)
			return null;
		
		Portal poPortal = new Portal();
		
		poPortal.setClaseNombre(oPortal.getClaseNombre());
		poPortal.setCodigoINEMunicipio(oPortal.getCodigoINEMunicipio());
		poPortal.setCoords(getCoordenadaWS(oPortal.getCoords()));
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
	
	private Portales getPortalesWS(ieci.tecdoc.sgm.core.services.geolocalizacion.Portales oPortales) {
		if (oPortales == null || oPortales.count() == 0)
			return null;
		
		Portales poPortales = new Portales();
		Portal[] poPortalesArray = new Portal[oPortales.count()];
		
		for(int i=0; i<oPortales.count(); i++)
			poPortalesArray[i] = getPortalWS((ieci.tecdoc.sgm.core.services.geolocalizacion.Portal)oPortales.get(i));
		poPortales.setPortales(poPortalesArray);
		
		return  poPortales;
	}
	
	private Coordenada getCoordenadaWS(ieci.tecdoc.sgm.core.services.geolocalizacion.Coordenada oCoordenada) {
		if (oCoordenada == null)
			return null;
		
		Coordenada poCoordenada = new Coordenada();
		
		poCoordenada.setCoordX(oCoordenada.getCoordX());
		poCoordenada.setCoordY(oCoordenada.getCoordY());
		
		return poCoordenada;
	}
	
	private ConjuntoCoordenadas getConjuntoCoordenadasWS(ieci.tecdoc.sgm.core.services.geolocalizacion.ConjuntoCoordenadas oConjuntoCoordenadas) {
		if (oConjuntoCoordenadas == null || oConjuntoCoordenadas.count() == 0)
			return null;
		
		ConjuntoCoordenadas poConjuntoCoordenadas = new ConjuntoCoordenadas();
		Coordenadas[] poConjuntoCoordenadasArray = new Coordenadas[oConjuntoCoordenadas.count()];
		
		for(int i=0; i<oConjuntoCoordenadas.count(); i++)
			poConjuntoCoordenadasArray[i] = getCoordenadasWS((ieci.tecdoc.sgm.core.services.geolocalizacion.Coordenadas)oConjuntoCoordenadas.get(i));
		poConjuntoCoordenadas.setConjuntoCoordenadas(poConjuntoCoordenadasArray);
		
		return  poConjuntoCoordenadas;
	}
	
	private Coordenadas getCoordenadasWS(ieci.tecdoc.sgm.core.services.geolocalizacion.Coordenadas oCoordenadas) {
		if (oCoordenadas == null || oCoordenadas.count() == 0)
			return null;
		
		Coordenadas poCoordenadas = new Coordenadas();
		Coordenada[] poCoordenadasArray = new Coordenada[oCoordenadas.count()];
		
		for(int i=0; i<oCoordenadas.count(); i++)
			poCoordenadasArray[i] = getCoordenadaWS((ieci.tecdoc.sgm.core.services.geolocalizacion.Coordenada)oCoordenadas.get(i));
		poCoordenadas.setCoordenadas(poCoordenadasArray);
		
		return  poCoordenadas;
	}
	
	private RetornoLogico getRetornoLogicoWS(boolean oRetornoLogico) {
		RetornoLogico poRetornoLogico = new RetornoLogico();
		
		poRetornoLogico.setValor(new Boolean(oRetornoLogico).toString());
		
		return poRetornoLogico;
	}
	
	private Provincia getProvinciaWS(ieci.tecdoc.sgm.core.services.geolocalizacion.Provincia oProvincia) {
		if (oProvincia == null)
			return null;
		
		Provincia poProvincia = new Provincia();
		
		poProvincia.setCodigoINE(oProvincia.getCodigoINE());
		poProvincia.setComunidadAutonoma(oProvincia.getComunidadAutonoma());
		poProvincia.setNombreCoOficial(oProvincia.getNombreCoOficial());
		poProvincia.setNombreOficial(oProvincia.getNombreOficial());
		
		return poProvincia;
	}
	
	private Provincias getProvinciasWS(ieci.tecdoc.sgm.core.services.geolocalizacion.Provincias oProvincias) {
		if (oProvincias == null || oProvincias.count() == 0)
			return null;
		
		Provincias poProvincias = new Provincias();
		Provincia[] poProvinciasArray = new Provincia[oProvincias.count()];
		
		for(int i=0; i<oProvincias.count(); i++)
			poProvinciasArray[i] = getProvinciaWS((ieci.tecdoc.sgm.core.services.geolocalizacion.Provincia)oProvincias.get(i));
		poProvincias.setProvincias(poProvinciasArray);
		
		return  poProvincias;
	}
	
	private Municipio getMunicipioWS(ieci.tecdoc.sgm.core.services.geolocalizacion.Municipio oMunicipio) {
		if (oMunicipio == null)
			return null;
		
		Municipio poMunicipio = new Municipio();
		
		poMunicipio.setCodigoINE(oMunicipio.getCodigoINE());
		poMunicipio.setNombreCoOficial(oMunicipio.getNombreCoOficial());
		poMunicipio.setNombreOficial(oMunicipio.getNombreOficial());
		
		return poMunicipio;
	}
	
	private Municipios getMunicipiosWS(ieci.tecdoc.sgm.core.services.geolocalizacion.Municipios oMunicipios) {
		if (oMunicipios == null || oMunicipios.count() == 0)
			return null;
		
		Municipios poMunicipios = new Municipios();
		Municipio[] poMunicipiosArray = new Municipio[oMunicipios.count()];
		
		for(int i=0; i<oMunicipios.count(); i++)
			poMunicipiosArray[i] = getMunicipioWS((ieci.tecdoc.sgm.core.services.geolocalizacion.Municipio)oMunicipios.get(i));
		poMunicipios.setMunicipios(poMunicipiosArray);
		
		return  poMunicipios;
	}
	
	private TipoVia getTipoViaWS(ieci.tecdoc.sgm.core.services.geolocalizacion.TipoVia oTipoVia) {
		if (oTipoVia == null)
			return null;
		
		TipoVia poTipoVia = new TipoVia();
		
		poTipoVia.setDescripcion(oTipoVia.getDescripcion());
		poTipoVia.setPatron(oTipoVia.getPatron());
		
		return poTipoVia;
	}
	
	private TiposVia getTiposViaWS(ieci.tecdoc.sgm.core.services.geolocalizacion.TiposVia oTiposVia) {
		if (oTiposVia == null || oTiposVia.count() == 0)
			return null;
		
		TiposVia poTiposVia = new TiposVia();
		TipoVia[] poTiposViaArray = new TipoVia[oTiposVia.count()];
		
		for(int i=0; i<oTiposVia.count(); i++)
			poTiposViaArray[i] = getTipoViaWS((ieci.tecdoc.sgm.core.services.geolocalizacion.TipoVia)oTiposVia.get(i));
		poTiposVia.setTiposVia(poTiposViaArray);
		
		return  poTiposVia;
	}
}
