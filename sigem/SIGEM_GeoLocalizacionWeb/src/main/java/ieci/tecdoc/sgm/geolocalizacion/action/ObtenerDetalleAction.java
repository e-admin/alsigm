package ieci.tecdoc.sgm.geolocalizacion.action;

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.geolocalizacion.GeoLocalizacionServicioException;
import ieci.tecdoc.sgm.core.services.geolocalizacion.ServicioGeoLocalizacion;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Via;
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

public class ObtenerDetalleAction extends Action {
	
	private static final Logger logger = Logger.getLogger(ObtenerDetalleAction.class);
	private static final String ERROR_FORWARD = "failure";
	private static final String SUCCESS_FORWARD = "success";
	
	 public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	 		throws Exception {
	 		
		 try{
			 String tipoVia, nombreVia, numeroPortal, idPortal;
			 
			 tipoVia = request.getParameter(Defs.PARAMETRO_TIPO_VIA);
			 if (tipoVia != null) tipoVia = tipoVia.toUpperCase();
			 nombreVia = request.getParameter(Defs.PARAMETRO_NOMBRE_VIA);
			 if (nombreVia != null) nombreVia = nombreVia.toUpperCase();
			 numeroPortal = request.getParameter(Defs.PARAMETRO_NUMERO_PORTAL);
			 if (numeroPortal != null) numeroPortal = numeroPortal.toUpperCase();
			 idPortal = request.getParameter(Defs.PARAMETRO_ID_PORTAL);
			 if (idPortal != null) idPortal = idPortal.toUpperCase();
			 
			 request.setAttribute(Defs.PARAMETRO_TIPO_VIA, (tipoVia != null) ? tipoVia : "");
			 request.setAttribute(Defs.PARAMETRO_NOMBRE_VIA, (nombreVia != null) ? nombreVia : "");
			 request.setAttribute(Defs.PARAMETRO_NUMERO_PORTAL, (numeroPortal != null) ? numeroPortal : "");
			 request.setAttribute(Defs.PARAMETRO_ID_PORTAL, (idPortal != null) ? idPortal : "");
			 
			 ServicioGeoLocalizacion oServicio = LocalizadorServicios.getServicioGeoLocalizacion();
			 Via via = oServicio.validarDireccionPostalCompleta(tipoVia, nombreVia, numeroPortal, 34083);
			 
			 request.setAttribute(Defs.PARAMETRO_VIA, via);
		 } catch (GeoLocalizacionServicioException e) {
			 logger.error("Error al obtener detalle de portal [ObtenerDetalleAction][GeoLocalizacionServicioException]", e.fillInStackTrace());
			 if (e.getErrorCode() != Utilidades.formarCodigo(GeoLocalizacionCodigosError.EC_VALIDAR_VIA))
				 request.setAttribute(Defs.ERROR_KEY, e.getMessage());
			 return mapping.findForward(ERROR_FORWARD);
		 }
		
		 return mapping.findForward(SUCCESS_FORWARD);
	 }
}
