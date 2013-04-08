package ieci.tecdoc.sgm.geolocalizacion;

import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.geolocalizacion.ConjuntoCoordenadas;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Coordenada;
import ieci.tecdoc.sgm.core.services.geolocalizacion.GeoLocalizacionServicioException;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Mapa;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Mapas;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Municipio;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Municipios;
import ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoCoordenadas;
import ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoPortal;
import ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoReferenciaCatastral;
import ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoVia;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Portal;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Portales;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Provincia;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Provincias;
import ieci.tecdoc.sgm.core.services.geolocalizacion.ServicioGeoLocalizacion;
import ieci.tecdoc.sgm.core.services.geolocalizacion.TipoVia;
import ieci.tecdoc.sgm.core.services.geolocalizacion.TiposVia;
import ieci.tecdoc.sgm.core.services.geolocalizacion.URLsPlano;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Via;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Vias;
import ieci.tecdoc.sgm.geolocalizacion.exception.GeoLocalizacionExcepcion;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ServicioGeoLocalizacionAdapter implements ServicioGeoLocalizacion {
		
	public static final Logger logger = Logger.getLogger(ServicioGeoLocalizacionAdapter.class);
	
	public  URLsPlano verPlanoPorCoordenadas(PeticionPlanoCoordenadas datosLocalizacion) throws GeoLocalizacionServicioException {
		try {
			return getURLsPlanoServicio(
					GeoLocalizacionManager.verPlanoPorCoordenadas(getPeticionPlanoCoordenadasImpl(datosLocalizacion))
				);		
		} catch(GeoLocalizacionExcepcion e) {
			logger.error("Error al obtener el plano solicitado por sus coordenadas.", e);
			throw getGeoLocalizacionServicioException(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener el plano solicitado por sus coordenadas.", e);
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public  URLsPlano verPlanoPorReferenciaCatastral(PeticionPlanoReferenciaCatastral datosLocalizacion) throws GeoLocalizacionServicioException {
		try {
			return getURLsPlanoServicio(
					GeoLocalizacionManager.verPlanoPorReferenciaCatastral(getPeticionPlanoReferenciaCatastralImpl(datosLocalizacion))
				);		
		} catch(GeoLocalizacionExcepcion e) {
			logger.error("Error al obtener el plano solicitado por su número de referencia catastral.", e);
			throw getGeoLocalizacionServicioException(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener el plano solicitado por su número de referencia catastral.", e);
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public  URLsPlano verPlanoPorIdVia(PeticionPlanoVia datosLocalizacion) throws GeoLocalizacionServicioException {
		try {
			return getURLsPlanoServicio(
					GeoLocalizacionManager.verPlanoPorIdVia(getPeticionPlanoViaImpl(datosLocalizacion))
				);		
		} catch(GeoLocalizacionExcepcion e) {
			logger.error("Error al obtener el plano solicitado por su identificador de vía.", e);
			throw getGeoLocalizacionServicioException(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener el plano solicitado por su identificador de vía.", e);
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public  URLsPlano verPlanoPorIdNumeroPolicia(PeticionPlanoPortal datosLocalizacion) throws GeoLocalizacionServicioException {
		try {
			return getURLsPlanoServicio(
					GeoLocalizacionManager.verPlanoPorIdNumeroPolicia(getPeticionPlanoPortalImpl(datosLocalizacion))
				);		
		} catch(GeoLocalizacionExcepcion e) {
			logger.error("Error al obtener el plano solicitado por su número de policía.", e);
			throw getGeoLocalizacionServicioException(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener el plano solicitado por su número de policía.", e);
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public  Mapas verPlanosPublicados(int idEntidadMunicipal) throws GeoLocalizacionServicioException {
		try {
			return getMapasServicio(
					GeoLocalizacionManager.verPlanosPublicados(idEntidadMunicipal)
				);		
		} catch(GeoLocalizacionExcepcion e) {
			logger.error("Error al obtener los planos publicados del municipio.", e);
			throw getGeoLocalizacionServicioException(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener los planos publicados del municipio.", e);
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public  Vias validarVia(String nombreVia, int codigoINEMunicipio) throws GeoLocalizacionServicioException {
		try {
			return getViasServicio(
					GeoLocalizacionManager.validarVia(nombreVia, codigoINEMunicipio)
				);		
		} catch(GeoLocalizacionExcepcion e) {
			logger.error("Error al validar vía.", e);
			throw getGeoLocalizacionServicioException(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al validar vía.", e);
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public  Portales validarPortal(int idVia, String numeroPortal) throws GeoLocalizacionServicioException {
		try {
			return getPortalesServicio(
					GeoLocalizacionManager.validarPortal(idVia, numeroPortal)
				);		
		} catch(GeoLocalizacionExcepcion e) {
			logger.error("Error al validar número de portal.", e);
			throw getGeoLocalizacionServicioException(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al validar número de portal.", e);
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public  Portal obtenerPortal(int idPortal) throws GeoLocalizacionServicioException {
		try {
			return getPortalServicio(
					GeoLocalizacionManager.obtenerPortal(idPortal)
				);		
		} catch(GeoLocalizacionExcepcion e) {
			logger.error("Error al obtener datos de portal.", e);
			throw getGeoLocalizacionServicioException(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener datos de portal.", e);
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public  boolean validarDireccionPostal(String tipoVia, String nombreVia, String numeroPortal, int codigoINEMunicipio)  throws GeoLocalizacionServicioException {
		try {
			return GeoLocalizacionManager.validarDireccionPostal(tipoVia, nombreVia, numeroPortal, codigoINEMunicipio);		
		} catch(GeoLocalizacionExcepcion e) {
			logger.error("Error al validar dirección postal.", e);
			throw getGeoLocalizacionServicioException(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al validar dirección postal.", e);
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public  Via validarDireccionPostalCompleta(String tipoVia, String nombreVia, String numeroPortal, int codigoINEMunicipio)  throws GeoLocalizacionServicioException {
		try {
			return getViaServicio(
					GeoLocalizacionManager.validarDireccionPostalCompleta(tipoVia, nombreVia, numeroPortal, codigoINEMunicipio)
				);		
		} catch(GeoLocalizacionExcepcion e) {
			logger.error("Error al validar dirección postal.", e);
			throw getGeoLocalizacionServicioException(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al validar dirección postal.", e);
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public Provincias obtenerProvincias() throws GeoLocalizacionServicioException {
		try {
			return getProvinciasServicio(
					GeoLocalizacionManager.obtenerProvincias()
				);		
		} catch(GeoLocalizacionExcepcion e) {
			logger.error("Error al obtener el listado de provincias.", e);
			throw getGeoLocalizacionServicioException(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener el listado de provincias.", e);
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public Municipios obtenerMunicipios(int idProvincia) throws GeoLocalizacionServicioException {
		try {
			return getMunicipiosServicio(
					GeoLocalizacionManager.obtenerMunicipios(idProvincia)
				);		
		} catch(GeoLocalizacionExcepcion e) {
			logger.error("Error al obtener el listado de municipios.", e);
			throw getGeoLocalizacionServicioException(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener el listado de municipios.", e);
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public TiposVia obtenerTiposDeVia() throws GeoLocalizacionServicioException {
		try {
			return getTiposViaServicio(
					GeoLocalizacionManager.obtenerTiposDeVia()
				);		
		} catch(GeoLocalizacionExcepcion e) {
			logger.error("Error al obtener el listado de tipos de via.", e);
			throw getGeoLocalizacionServicioException(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener el listado de tipos de via.", e);
			throw new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	private GeoLocalizacionServicioException getGeoLocalizacionServicioException(GeoLocalizacionExcepcion poException){
		if(poException == null){
			return new GeoLocalizacionServicioException(GeoLocalizacionServicioException.EXC_GENERIC_EXCEPCION);
		}
		StringBuffer cCodigo = new StringBuffer(ConstantesServicios.SERVICE_GEOLOCALIZACION_ERROR_PREFIX);
		cCodigo.append(String.valueOf(poException.getErrorCode()));
		return new GeoLocalizacionServicioException(Long.valueOf(cCodigo.toString()).longValue(), poException);
		
	}

	private ieci.tecdoc.sgm.geolocalizacion.datatypes.PeticionPlanoCoordenadas getPeticionPlanoCoordenadasImpl(PeticionPlanoCoordenadas oPeticionPlano){
		if (oPeticionPlano == null)
			return null;
		
		ieci.tecdoc.sgm.geolocalizacion.datatypes.PeticionPlanoCoordenadas poPeticionPlano = new ieci.tecdoc.sgm.geolocalizacion.datatypes.PeticionPlanoCoordenadas();
		
		poPeticionPlano.setAlto(oPeticionPlano.getAlto());
		poPeticionPlano.setAncho(oPeticionPlano.getAncho());
		poPeticionPlano.setCodigoINEMunicipio(oPeticionPlano.getCodigoINEMunicipio());
		poPeticionPlano.setCoordX(oPeticionPlano.getCoordX());
		poPeticionPlano.setCoordY(oPeticionPlano.getCoordY());
		poPeticionPlano.setEscala(oPeticionPlano.getEscala());
		poPeticionPlano.setIdMapa(oPeticionPlano.getIdMapa());
		
		return poPeticionPlano;
	}
	
	private ieci.tecdoc.sgm.geolocalizacion.datatypes.PeticionPlanoReferenciaCatastral getPeticionPlanoReferenciaCatastralImpl(PeticionPlanoReferenciaCatastral oPeticionPlano){
		if (oPeticionPlano == null)
			return null;
		
		ieci.tecdoc.sgm.geolocalizacion.datatypes.PeticionPlanoReferenciaCatastral poPeticionPlano = new ieci.tecdoc.sgm.geolocalizacion.datatypes.PeticionPlanoReferenciaCatastral();
		
		poPeticionPlano.setAlto(oPeticionPlano.getAlto());
		poPeticionPlano.setAncho(oPeticionPlano.getAncho());
		poPeticionPlano.setCodigoINEMunicipio(oPeticionPlano.getCodigoINEMunicipio());
		poPeticionPlano.setEscala(oPeticionPlano.getEscala());
		poPeticionPlano.setIdMapa(oPeticionPlano.getIdMapa());
		poPeticionPlano.setReferenciaCatastral(oPeticionPlano.getReferenciaCatastral());
		
		return poPeticionPlano;
	}
	
	private ieci.tecdoc.sgm.geolocalizacion.datatypes.PeticionPlanoVia getPeticionPlanoViaImpl(PeticionPlanoVia oPeticionPlano){
		if (oPeticionPlano == null)
			return null;
		
		ieci.tecdoc.sgm.geolocalizacion.datatypes.PeticionPlanoVia poPeticionPlano = new ieci.tecdoc.sgm.geolocalizacion.datatypes.PeticionPlanoVia();
		
		poPeticionPlano.setAlto(oPeticionPlano.getAlto());
		poPeticionPlano.setAncho(oPeticionPlano.getAncho());
		poPeticionPlano.setCodigoINEMunicipio(oPeticionPlano.getCodigoINEMunicipio());
		poPeticionPlano.setEscala(oPeticionPlano.getEscala());
		poPeticionPlano.setIdMapa(oPeticionPlano.getIdMapa());
		poPeticionPlano.setVia(oPeticionPlano.getVia());
		
		return poPeticionPlano;
	}
	
	private ieci.tecdoc.sgm.geolocalizacion.datatypes.PeticionPlanoPortal getPeticionPlanoPortalImpl(PeticionPlanoPortal oPeticionPlano){
		if (oPeticionPlano == null)
			return null;
		
		ieci.tecdoc.sgm.geolocalizacion.datatypes.PeticionPlanoPortal poPeticionPlano = new ieci.tecdoc.sgm.geolocalizacion.datatypes.PeticionPlanoPortal();
		
		poPeticionPlano.setAlto(oPeticionPlano.getAlto());
		poPeticionPlano.setAncho(oPeticionPlano.getAncho());
		poPeticionPlano.setCodigoINEMunicipio(oPeticionPlano.getCodigoINEMunicipio());
		poPeticionPlano.setEscala(oPeticionPlano.getEscala());
		poPeticionPlano.setIdMapa(oPeticionPlano.getIdMapa());
		poPeticionPlano.setPortal(oPeticionPlano.getPortal());
		
		return poPeticionPlano;
	}
	
	private URLsPlano getURLsPlanoServicio(com.localgis.model.ot.URLsPlano  oURLsPlano){
		if (oURLsPlano == null)
			return null;
		
		URLsPlano poURLsPlano = new URLsPlano();
		
		poURLsPlano.setUrlGuiaUrbana(oURLsPlano.getUrlGuiaUrbana());
		poURLsPlano.setUrlMapServer(oURLsPlano.getUrlMapServer());
		
		return poURLsPlano;
	}
	
	private Mapas getMapasServicio(com.localgis.web.core.model.LocalgisMap[] oMapas){
		if (oMapas == null || oMapas.length == 0)
			return null;
		
		Mapas poMapas = new Mapas();
		for(int i=0; i<oMapas.length; i++)
			poMapas.add(getMapaServicio(oMapas[i]));
		
		return poMapas;
	}
	
	private Mapa getMapaServicio(com.localgis.web.core.model.LocalgisMap oMapa){
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
		
		return poMapa;
	}
	
	private Vias getViasServicio(ieci.tecdoc.sgm.geolocalizacion.datatypes.Via[] oVias){
		if (oVias == null || oVias.length == 0)
			return null;
		
		Vias poVias = new Vias();
		for(int i=0; i<oVias.length; i++)
			poVias.add(getViaServicio(oVias[i]));
		
		return poVias;
	}
	
	private Via getViaServicio(ieci.tecdoc.sgm.geolocalizacion.datatypes.Via oVia){
		if (oVia == null)
			return null;
		
		Via poVia= new Via();
		
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
	
	private Portales getPortalesServicio(ieci.tecdoc.sgm.geolocalizacion.datatypes.Portal[] oPortales){
		if (oPortales == null || oPortales.length == 0)
			return null;
		
		Portales poPortales = new Portales();
		for(int i=0; i<oPortales.length; i++)
			poPortales.add(getPortalServicio(oPortales[i]));
		
		return poPortales;
	}
	
	private Portal getPortalServicio(ieci.tecdoc.sgm.geolocalizacion.datatypes.Portal oPortal){
		if (oPortal == null)
			return null;
		
		Portal poPortal= new Portal();
		
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
	
	private Coordenada getCoordenadaServicio(ieci.tecdoc.sgm.geolocalizacion.datatypes.Coordenada oCoordenada){
		if (oCoordenada == null)
			return null;
		
		Coordenada poCoordenada= new Coordenada();
		
		poCoordenada.setCoordX(oCoordenada.getCoordX());
		poCoordenada.setCoordY(oCoordenada.getCoordY());
		
		return poCoordenada;
	}
	
	private ConjuntoCoordenadas getConjuntoCoordenadasServicio(ieci.tecdoc.sgm.geolocalizacion.datatypes.Coordenada[][] oCoordenadas){
		if (oCoordenadas == null || oCoordenadas.length == 0)
			return null;
		
		ConjuntoCoordenadas poConjuntoCoordenadas = new ConjuntoCoordenadas();
		for(int i=0; i<oCoordenadas.length; i++) {
			ieci.tecdoc.sgm.geolocalizacion.datatypes.Coordenada[] oCoordArray = oCoordenadas[i];
			List poCoordArray = new ArrayList(oCoordArray.length);
			for(int j=0; j<oCoordArray.length; j++)
				poCoordArray.add(getCoordenadaServicio(oCoordArray[j]));
			poConjuntoCoordenadas.setConjuntoCoordenadas(poCoordArray);
		}
		
		return poConjuntoCoordenadas;
	}
	
	private Provincias getProvinciasServicio(com.localgis.model.ot.ProvinciaOT[] oProvincias){
		if (oProvincias == null || oProvincias.length == 0)
			return null;
		
		Provincias poProvincias = new Provincias();
		for(int i=0; i<oProvincias.length; i++)
			poProvincias.add(getProvinciaServicio(oProvincias[i]));
		
		return poProvincias;
	}
	
	private Provincia getProvinciaServicio(com.localgis.model.ot.ProvinciaOT oProvincia){
		if (oProvincia == null)
			return null;
		
		Provincia poProvincia= new Provincia();
		
		poProvincia.setCodigoINE(oProvincia.getCodigoINE().intValue());
		poProvincia.setComunidadAutonoma(oProvincia.getComunidadAutonoma());
		poProvincia.setNombreCoOficial(oProvincia.getNombreCoOficial());
		poProvincia.setNombreOficial(oProvincia.getNombreOficial());
		
		return poProvincia;
	}
	
	private Municipios getMunicipiosServicio(com.localgis.model.ot.MunicipioOT[] oMunicipios){
		if (oMunicipios == null || oMunicipios.length == 0)
			return null;
		
		Municipios poMunicipios = new Municipios();
		for(int i=0; i<oMunicipios.length; i++)
			poMunicipios.add(getMunicipioServicio(oMunicipios[i]));
		
		return poMunicipios;
	}
	
	private Municipio getMunicipioServicio(com.localgis.model.ot.MunicipioOT oMunicipio){
		if (oMunicipio == null)
			return null;
		
		Municipio poMunicipio= new Municipio();
		
		poMunicipio.setCodigoINE(oMunicipio.getCodigoINE().intValue());
		poMunicipio.setNombreCoOficial(oMunicipio.getNombreCoOficial());
		poMunicipio.setNombreOficial(oMunicipio.getNombreOficial());
		
		return poMunicipio;
	}
	
	private TiposVia getTiposViaServicio(com.localgis.model.ot.TipoViaOT[] oTiposVia){
		if (oTiposVia == null || oTiposVia.length == 0)
			return null;
		
		TiposVia poTiposVia = new TiposVia();
		for(int i=0; i<oTiposVia.length; i++)
			poTiposVia.add(getTipoViaServicio(oTiposVia[i]));
		
		return poTiposVia;
	}
	
	private TipoVia getTipoViaServicio(com.localgis.model.ot.TipoViaOT oTipoVia){
		if (oTipoVia == null)
			return null;
		
		TipoVia poTipoVia= new TipoVia();
		
		poTipoVia.setDescripcion(oTipoVia.getDescripcion());
		poTipoVia.setPatron(oTipoVia.getPatron());
		
		return poTipoVia;
	}
}
