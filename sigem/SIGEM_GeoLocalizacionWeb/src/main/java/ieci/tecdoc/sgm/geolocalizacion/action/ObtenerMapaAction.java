package ieci.tecdoc.sgm.geolocalizacion.action;

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Coordenada;
import ieci.tecdoc.sgm.core.services.geolocalizacion.GeoLocalizacionServicioException;
import ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoCoordenadas;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Portal;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Portales;
import ieci.tecdoc.sgm.core.services.geolocalizacion.ServicioGeoLocalizacion;
import ieci.tecdoc.sgm.core.services.geolocalizacion.URLsPlano;
import ieci.tecdoc.sgm.geolocalizacion.exception.GeoLocalizacionCodigosError;
import ieci.tecdoc.sgm.geolocalizacion.utils.Defs;
import ieci.tecdoc.sgm.geolocalizacion.utils.Utilidades;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ObtenerMapaAction extends Action {
	
	private static final Logger logger = Logger.getLogger(ObtenerMapaAction.class);
	private static final String ERROR_FORWARD = "failure";
	private static final String SUCCESS_FORWARD = "success";
	
	 public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	 		throws Exception {
	 		
		 try{
			 String idVia, numeroPortal, ajusteZoom;
			 int ajuste;
			 
			 idVia = request.getParameter(Defs.PARAMETRO_ID_VIA);
			 if (idVia != null) idVia = idVia.toUpperCase();
			 numeroPortal = request.getParameter(Defs.PARAMETRO_NUMERO_PORTAL);
			 if (numeroPortal != null) numeroPortal = numeroPortal.toUpperCase();
			 ajusteZoom = request.getParameter(Defs.PARAMETRO_AJUSTE_ZOOM);
			 ajuste = (ajusteZoom == null) ? 0 : new Integer(ajusteZoom).intValue();
			 
			 request.setAttribute(Defs.PARAMETRO_ID_VIA, (idVia != null) ? idVia : "");
			 request.setAttribute(Defs.PARAMETRO_NUMERO_PORTAL, (numeroPortal != null) ? numeroPortal : "");
			 request.setAttribute(Defs.PARAMETRO_AJUSTE_ZOOM, new Integer(ajuste));
			 
			 ServicioGeoLocalizacion oServicio = LocalizadorServicios.getServicioGeoLocalizacion();
			 Portales portales = oServicio.validarPortal(new Integer(idVia).intValue(), numeroPortal);
			 Portal portal;
			 
			 int zoomCalculado = ((Integer)request.getSession().getServletContext().getAttribute(Defs.PLUGIN_ZOOM_MAPA)).intValue();
			 
			 for(int i=0; i<portales.count(); i++) {
				 portal = (Portal)portales.getPortales().get(i);
				 if (numeroPortal.equals(portal.getNumPortal())) {
					 Coordenada coords = portal.getCoords();
					 PeticionPlanoCoordenadas datosLocalizacion = new PeticionPlanoCoordenadas(
							 ((Integer)request.getSession().getServletContext().getAttribute(Defs.PLUGIN_CAPA_MAPA)).intValue(),
							 coords.getCoordX(),
							 coords.getCoordY(),
							 ((Integer)request.getSession().getServletContext().getAttribute(Defs.PLUGIN_ANCHO_MAPA)).intValue(),
							 ((Integer)request.getSession().getServletContext().getAttribute(Defs.PLUGIN_ALTO_MAPA)).intValue(),
							 ((zoomCalculado + ajuste) > 100) ? zoomCalculado + ajuste : 100,
							 34083);
					 URLsPlano plano = oServicio.verPlanoPorCoordenadas(datosLocalizacion);
					 request.setAttribute(Defs.PARAMETRO_URL_PLANO, plano.getUrlMapServer());
				 }
			 }
		 } catch (GeoLocalizacionServicioException e) {
			 logger.error("Error al obtener plano [ObtenerMapaAction][GeoLocalizacionServicioException]", e.fillInStackTrace());
			 if (e.getErrorCode() != Utilidades.formarCodigo(GeoLocalizacionCodigosError.EC_VALIDAR_VIA))
				 request.setAttribute(Defs.ERROR_KEY, e.getMessage());
			 return mapping.findForward(ERROR_FORWARD);
		 }
		
		 return mapping.findForward(SUCCESS_FORWARD);
	 }
}
