package ieci.tecdoc.sgm.geolocalizacion;

import ieci.tecdoc.sgm.geolocalizacion.datatypes.PeticionPlanoCoordenadas;
import ieci.tecdoc.sgm.geolocalizacion.datatypes.PeticionPlanoPortal;
import ieci.tecdoc.sgm.geolocalizacion.datatypes.PeticionPlanoReferenciaCatastral;
import ieci.tecdoc.sgm.geolocalizacion.datatypes.PeticionPlanoVia;
import ieci.tecdoc.sgm.geolocalizacion.datatypes.Portal;
import ieci.tecdoc.sgm.geolocalizacion.datatypes.ServicioURL;
import ieci.tecdoc.sgm.geolocalizacion.datatypes.Via;
import ieci.tecdoc.sgm.geolocalizacion.exception.GeoLocalizacionCodigosError;
import ieci.tecdoc.sgm.geolocalizacion.exception.GeoLocalizacionExcepcion;
import ieci.tecdoc.sgm.geolocalizacion.utils.UtilidadesXML;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.log4j.Logger;

import com.localgis.ln.ISOALocalGISHttpBindingStub;
import com.localgis.model.ot.ArrayOfMunicipioOT;
import com.localgis.model.ot.ArrayOfProvinciaOT;
import com.localgis.model.ot.ArrayOfTipoViaOT;
import com.localgis.model.ot.MunicipioOT;
import com.localgis.model.ot.ProvinciaOT;
import com.localgis.model.ot.TipoViaOT;
import com.localgis.model.ot.URLsPlano;
import com.localgis.web.core.model.ArrayOfLocalgisMap;
import com.localgis.web.core.model.LocalgisMap;

public class GeoLocalizacionManager {
		
	public static final Logger logger = Logger.getLogger(GeoLocalizacionManager.class);
	
	public static URLsPlano verPlanoPorCoordenadas(PeticionPlanoCoordenadas datosLocalizacion) throws GeoLocalizacionExcepcion{
		try {
			ISOALocalGISHttpBindingStub sGeoLocalizacion = GeoLocalizacionFactory.getInstance().obtenerServicioGeoLocalizacion();
			return sGeoLocalizacion.verPlanoPorCoordenadas(datosLocalizacion.getIdMapa(),
														datosLocalizacion.getCoordX(),
														datosLocalizacion.getCoordY(),
														datosLocalizacion.getAncho(),
														datosLocalizacion.getAlto(),
														datosLocalizacion.getEscala(),
														datosLocalizacion.getCodigoINEMunicipio());
		} catch (Exception e) {
			logger.error("Error al obtener plano por coordenadas [verPlanoPorCoordenadas][Exception]", e);
			throw new GeoLocalizacionExcepcion(GeoLocalizacionCodigosError.EC_OBTENER_PLANO_COORDENADAS, e.getCause());
		}
	}

	public static URLsPlano verPlanoPorReferenciaCatastral(PeticionPlanoReferenciaCatastral datosLocalizacion) throws GeoLocalizacionExcepcion{
		try {
			ISOALocalGISHttpBindingStub sGeoLocalizacion = GeoLocalizacionFactory.getInstance().obtenerServicioGeoLocalizacion();
			return sGeoLocalizacion.verPlanoPorReferenciaCatastral(datosLocalizacion.getIdMapa(),
														datosLocalizacion.getReferenciaCatastral(),
														datosLocalizacion.getAncho(),
														datosLocalizacion.getAlto(),
														datosLocalizacion.getEscala(),
														datosLocalizacion.getCodigoINEMunicipio());
		} catch (Exception e) {
			logger.error("Error al obtener plano por referencia catastral [verPlanoPorReferenciaCatastral][Exception]", e);
			throw new GeoLocalizacionExcepcion(GeoLocalizacionCodigosError.EC_OBTENER_PLANO_REFERENCIA_CATASTRAL, e.getCause());
		}
	}
	
	public static URLsPlano verPlanoPorIdVia(PeticionPlanoVia datosLocalizacion) throws GeoLocalizacionExcepcion{
		try {
			ISOALocalGISHttpBindingStub sGeoLocalizacion = GeoLocalizacionFactory.getInstance().obtenerServicioGeoLocalizacion();
			return sGeoLocalizacion.verPlanoPorIdVia(datosLocalizacion.getIdMapa(),
														datosLocalizacion.getVia(),
														datosLocalizacion.getAncho(),
														datosLocalizacion.getAlto(),
														datosLocalizacion.getEscala(),
														datosLocalizacion.getCodigoINEMunicipio());
		} catch (Exception e) {
			logger.error("Error al obtener plano por id de via [verPlanoPorIdVia][Exception]", e);
			throw new GeoLocalizacionExcepcion(GeoLocalizacionCodigosError.EC_OBTENER_PLANO_ID_VIA, e.getCause());
		}
	}
	
	public static URLsPlano verPlanoPorIdNumeroPolicia(PeticionPlanoPortal datosLocalizacion) throws GeoLocalizacionExcepcion{
		try {
			ISOALocalGISHttpBindingStub sGeoLocalizacion = GeoLocalizacionFactory.getInstance().obtenerServicioGeoLocalizacion();
			return sGeoLocalizacion.verPlanoPorIdNumeroPolicia(datosLocalizacion.getIdMapa(),
														datosLocalizacion.getPortal(),
														datosLocalizacion.getAncho(),
														datosLocalizacion.getAlto(),
														datosLocalizacion.getEscala(),
														datosLocalizacion.getCodigoINEMunicipio());
		} catch (Exception e) {
			logger.error("Error al obtener plano por id de portal [verPlanoPorIdNumeroPolicia][Exception]", e);
			throw new GeoLocalizacionExcepcion(GeoLocalizacionCodigosError.EC_OBTENER_PLANO_ID_NUMERO_POLICIA, e.getCause());
		}
	}
	
	public static LocalgisMap[] verPlanosPublicados(int idEntidadMunicipal) throws GeoLocalizacionExcepcion{
		try {
			ISOALocalGISHttpBindingStub sGeoLocalizacion = GeoLocalizacionFactory.getInstance().obtenerServicioGeoLocalizacion();
			ArrayOfLocalgisMap mapas = sGeoLocalizacion.verPlanosPublicados(idEntidadMunicipal);
			return (mapas != null ? mapas.getLocalgisMap(): null);
		} catch (Exception e) {
			logger.error("Error al obtener plano por publicados [verPlanosPublicados][Exception]", e);
			throw new GeoLocalizacionExcepcion(GeoLocalizacionCodigosError.EC_OBTENER_PLANOS_PUBLICADOS, e.getCause());
		}
	}
	
	public static Via[] validarVia(String nombreVia, int codigoINEMunicipio) throws GeoLocalizacionExcepcion {
		try {
			ServicioURL sGeoLocalizacion = GeoLocalizacionFactory.getInstance().obtenerServicioURL(
					nombreVia, ""+codigoINEMunicipio, GeoLocalizacionFactory.VALIDACION_POR_VIA
				);
			int status = sGeoLocalizacion.getConsulta().executeMethod(sGeoLocalizacion.getMetodo());
			if (status != HttpStatus.SC_OK) {
				return null;
			} else {
				byte[] responseBody = sGeoLocalizacion.getMetodo().getResponseBody();
				return UtilidadesXML.parsearXMLVias(new String(responseBody));
			}
		} catch (Exception e) {
			logger.error("Error al validar via [validarVia][Exception]", e);
			throw new GeoLocalizacionExcepcion(GeoLocalizacionCodigosError.EC_VALIDAR_VIA, e.getCause());
		}
	}
	
	public static Portal[] validarPortal(int idVia, String numeroPortal) throws GeoLocalizacionExcepcion {
		try {
			ServicioURL sGeoLocalizacion = GeoLocalizacionFactory.getInstance().obtenerServicioURL(
					""+idVia, numeroPortal, GeoLocalizacionFactory.VALIDACION_POR_NUMERO_PORTAL
				);
			int status = sGeoLocalizacion.getConsulta().executeMethod(sGeoLocalizacion.getMetodo());
			if (status != HttpStatus.SC_OK) {
				return null;
			} else {
				byte[] responseBody = sGeoLocalizacion.getMetodo().getResponseBody();
				return UtilidadesXML.parsearXMLPortales(new String(responseBody));
			}
		} catch (Exception e) {
			logger.error("Error al validar portal [validarPortal][Exception]", e);
			throw new GeoLocalizacionExcepcion(GeoLocalizacionCodigosError.EC_VALIDAR_NUMERO, e.getCause());
		}
	}
	
	public static Portal obtenerPortal(int idPortal) throws GeoLocalizacionExcepcion {
		try {
			ServicioURL sGeoLocalizacion = GeoLocalizacionFactory.getInstance().obtenerServicioURL(
					""+idPortal, null, GeoLocalizacionFactory.VALIDACION_POR_ID_PORTAL
				);
			int status = sGeoLocalizacion.getConsulta().executeMethod(sGeoLocalizacion.getMetodo());
			if (status != HttpStatus.SC_OK) {
				return null;
			} else {
				byte[] responseBody = sGeoLocalizacion.getMetodo().getResponseBody();
				//return UtilidadesXML.parsearXMLPortal(new String(responseBody));
				Portal[] portales = UtilidadesXML.parsearXMLPortales(new String(responseBody));
				return portales[0];
			}
		} catch (Exception e) {
			logger.error("Error al obtener portal [obtenerPortal][Exception]", e);
			throw new GeoLocalizacionExcepcion(GeoLocalizacionCodigosError.EC_VALIDAR_NUMERO, e.getCause());
		}
	}
	
	public static boolean validarDireccionPostal(String tipoVia, String nombreVia, String numeroPortal, int codigoINEMunicipio) throws GeoLocalizacionExcepcion {
		try {
			Via via =  validarDireccionPostalCompleta(tipoVia, nombreVia, numeroPortal, codigoINEMunicipio);
			return (via != null);
		} catch (Exception e) {
			logger.error("Error al validar direccion postal [validarDireccion][Exception]", e);
			throw new GeoLocalizacionExcepcion(GeoLocalizacionCodigosError.EC_VALIDAR_DIRECCION_POSTAL, e.getCause());
		}
	}
	
	public static Via validarDireccionPostalCompleta(String tipoVia, String nombreVia, String numeroPortal, int codigoINEMunicipio) throws GeoLocalizacionExcepcion {
		try {
			Via[] vias = validarVia(nombreVia, codigoINEMunicipio);
			int index = -1;
			if (vias != null) {
				int num = 0;
				for (int i=0; i<vias.length; i++)
					if (vias[i].getTipoVia().equalsIgnoreCase(tipoVia) && vias[i].getNombreVia().equalsIgnoreCase(nombreVia)) {
						num++;
						index = i;
					}
				if (num != 1)
					return null;
			} else return null;
			
			Portal[] portales = validarPortal(vias[index].getIdVia(), numeroPortal);
			if (portales == null)
				return null;
			vias[index].setPortales(portales);
			return vias[index];
		} catch (Exception e) {
			logger.error("Error al validar direccion postal completa [validarDireccionPostalCompleta][Exception]", e);
			throw new GeoLocalizacionExcepcion(GeoLocalizacionCodigosError.EC_VALIDAR_DIRECCION_POSTAL, e.getCause());
		}
	}
	
	public static ProvinciaOT[] obtenerProvincias() throws GeoLocalizacionExcepcion{
		try {
			ISOALocalGISHttpBindingStub sGeoLocalizacion = GeoLocalizacionFactory.getInstance().obtenerServicioGeoLocalizacion();
			ArrayOfProvinciaOT provincias = sGeoLocalizacion.obtenerProvincias();
			return (provincias != null ? provincias.getProvinciaOT() : null);
		} catch (Exception e) {
			logger.error("Error al obtener listado de provincias [obtenerProvincias][Exception]", e);
			throw new GeoLocalizacionExcepcion(GeoLocalizacionCodigosError.EC_OBTENER_PROVINCIAS, e.getCause());
		}
	}
	
	public static MunicipioOT[] obtenerMunicipios(int idProvincia) throws GeoLocalizacionExcepcion{
		try {
			ISOALocalGISHttpBindingStub sGeoLocalizacion = GeoLocalizacionFactory.getInstance().obtenerServicioGeoLocalizacion();
			ArrayOfMunicipioOT municipios = sGeoLocalizacion.obtenerMunicipios(idProvincia);
			return (municipios != null ? municipios.getMunicipioOT() : null);
		} catch (Exception e) {
			logger.error("Error al obtener listado de municipios [obtenerMunicipios][Exception]", e);
			throw new GeoLocalizacionExcepcion(GeoLocalizacionCodigosError.EC_OBTENER_MUNICIPIOS, e.getCause());
		}
	}
	
	public static TipoViaOT[] obtenerTiposDeVia() throws GeoLocalizacionExcepcion{
		try {
			ISOALocalGISHttpBindingStub sGeoLocalizacion = GeoLocalizacionFactory.getInstance().obtenerServicioGeoLocalizacion();
			ArrayOfTipoViaOT tiposVia = sGeoLocalizacion.obtenerTiposDeVia();
			return (tiposVia != null ? tiposVia.getTipoViaOT() : null);
		} catch (Exception e) {
			logger.error("Error al obtener listado de tipos de via [obtenerTiposDeVia][Exception]", e);
			throw new GeoLocalizacionExcepcion(GeoLocalizacionCodigosError.EC_OBTENER_TIPOS_VIA, e.getCause());
		}
	}
}
