package ieci.tecdoc.sgm.geolocalizacion.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.geolocalizacion.GeoLocalizacionServicioException;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Portal;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Portales;
import ieci.tecdoc.sgm.core.services.geolocalizacion.ServicioGeoLocalizacion;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Via;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Vias;
import ieci.tecdoc.sgm.geolocalizacion.exception.GeoLocalizacionCodigosError;
import ieci.tecdoc.sgm.geolocalizacion.exception.GeoLocalizacionExcepcion;
import ieci.tecdoc.sgm.geolocalizacion.utils.Defs;
import ieci.tecdoc.sgm.geolocalizacion.utils.OrdenadorListados;
import ieci.tecdoc.sgm.geolocalizacion.utils.Utilidades;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ValidarDireccionPostalAction extends Action {
	
	private static final Logger logger = Logger.getLogger(ValidarDireccionPostalAction.class);
	private static final String ERROR_FORWARD = "failure";
	private static final String SUCCESS_FORWARD = "success";
	
	 public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	 		throws Exception {
	 		
		 try{
			 String accion = (request.getParameter(Defs.PARAMETRO_ACCION) != null) ? request.getParameter(Defs.PARAMETRO_ACCION) : (String)request.getAttribute(Defs.PARAMETRO_ACCION);
			 //String idEntidad = (request.getParameter(Defs.PARAMETRO_ID_ENTIDAD) != null) ? request.getParameter(Defs.PARAMETRO_ID_ENTIDAD) : (String)request.getAttribute(Defs.PARAMETRO_ID_ENTIDAD);
			 String tipoVia, nombreVia, numeroPortal, idVia;
			 
			 tipoVia = request.getParameter(Defs.PARAMETRO_TIPO_VIA);
			 if (tipoVia != null) tipoVia = tipoVia.toUpperCase();
			 nombreVia = request.getParameter(Defs.PARAMETRO_NOMBRE_VIA);
			 if (nombreVia != null) nombreVia = nombreVia.toUpperCase();
			 numeroPortal = request.getParameter(Defs.PARAMETRO_NUMERO_PORTAL);
			 if (numeroPortal != null) numeroPortal = numeroPortal.toUpperCase();
			 idVia = request.getParameter(Defs.PARAMETRO_ID_VIA);
			 if (idVia != null) idVia = idVia.toUpperCase();
			 
			 request.setAttribute(Defs.PARAMETRO_TIPO_VIA, (tipoVia != null) ? tipoVia : "");
			 request.setAttribute(Defs.PARAMETRO_NOMBRE_VIA, (nombreVia != null) ? nombreVia : "");
			 request.setAttribute(Defs.PARAMETRO_NUMERO_PORTAL, (numeroPortal != null) ? numeroPortal : "");
			 request.setAttribute(Defs.PARAMETRO_ACCION, (accion != null) ? accion : "");
			 request.setAttribute(Defs.PARAMETRO_ID_VIA, (idVia != null) ? idVia : "");
			 
			 if (Defs.ACCION_BUSQUEDA.equals(accion)) {
				 request.getSession().setAttribute(Defs.PARAMETRO_TIPO_VIA_BUSQUEDA, (tipoVia != null) ? tipoVia : "");
				 request.getSession().setAttribute(Defs.PARAMETRO_NOMBRE_VIA_BUSQUEDA, (nombreVia != null) ? nombreVia : "");
				 request.getSession().setAttribute(Defs.PARAMETRO_NUMERO_PORTAL_BUSQUEDA, (numeroPortal != null) ? numeroPortal : "");
			 } 
			 
			 ServicioGeoLocalizacion oServicio = LocalizadorServicios.getServicioGeoLocalizacion();

			 Via via = null;
			 Vias vias = new Vias();
			 if (Defs.ACCION_BUSQUEDA.equals(accion) && !Utilidades.esVacio(nombreVia)) {
				 if (!Utilidades.esVacio(tipoVia) && !Utilidades.esVacio(numeroPortal)) { 
					 if  ((via = oServicio.validarDireccionPostalCompleta(tipoVia, nombreVia, numeroPortal, 34083)) == null){
						 vias = oServicio.validarVia(Utilidades.formarNombreBusqueda(nombreVia), 34083);
					 }else{
						 request.setAttribute(Defs.PARAMETRO_ACCION, Defs.ACCION_PORTALES);
						 request.setAttribute(Defs.PARAMETRO_ID_VIA, ""+via.getIdVia());
						 request.setAttribute(Defs.LISTADO_PORTALES, via.getPortales().getPortales());
					 }
				 } else {
					 vias = oServicio.validarVia(Utilidades.formarNombreBusqueda(nombreVia), 34083);
				 }
				 if (vias != null) {
					 List viasOrdenadas = ordenarVias(vias.getVias());
					 request.setAttribute(Defs.LISTADO_VIAS, viasOrdenadas);
				 } else request.setAttribute(Defs.LISTADO_VIAS, new ArrayList(0));
			 } else if(Defs.ACCION_PORTALES.equals(accion)) {
				 vias = oServicio.validarVia(Utilidades.formarNombreBusqueda(nombreVia), 34083);
				 Via seleccionada = null;
				 int iidVia = new Integer(idVia).intValue();
				 for(int i=0; i<vias.count(); i++)
					 if (iidVia == ((Via)vias.getVias().get(i)).getIdVia()) {
						 seleccionada = (Via)vias.getVias().get(i);
						 break;
					 }
				 List portales = seleccionada.getPortales().getPortales();
				 
				 for(int j=0; j<portales.size(); j++) {
					 Portal portal = oServicio.obtenerPortal(((Portal)portales.get(j)).getIdPortal());
					 portales.set(j, portal);
				 }
				 List portalesOrdenados = ordenarPortales(portales);
				 request.setAttribute(Defs.LISTADO_PORTALES, portalesOrdenados);
			 } else {
				 if (Defs.ACCION_BUSQUEDA.equals(accion))
					 throw new GeoLocalizacionExcepcion(GeoLocalizacionCodigosError.EC_NOMBRE_VIA_INVALIDO);
			 }
		
		 } catch (GeoLocalizacionExcepcion e) {
			 logger.error("Error al validar la direccion postal [ValidarDireccionPostalAction][GeoLocalizacionExcepcion]", e.fillInStackTrace());
			 request.setAttribute(Defs.ERROR_KEY, e.getMessage());
			 return mapping.findForward(ERROR_FORWARD);
		 } catch (GeoLocalizacionServicioException e) {
			 logger.error("Error al validar la direccion postal [ValidarDireccionPostalAction][GeoLocalizacionServicioException]", e.fillInStackTrace());
			 if (e.getErrorCode() != Utilidades.formarCodigo(GeoLocalizacionCodigosError.EC_VALIDAR_VIA))
				 request.setAttribute(Defs.ERROR_KEY, e.getMessage());
			 return mapping.findForward(ERROR_FORWARD);
		 }
		
		 return mapping.findForward(SUCCESS_FORWARD);
	 }
	 
	 
	 private List ordenarVias(List viasDesordenadas) {
		 OrdenadorListados ordenador = new OrdenadorListados(OrdenadorListados.TIPO_VIA);
		 Via[] vias = new Via[viasDesordenadas.size()];
		 for(int i=0; i<viasDesordenadas.size(); i++)
			 vias[i] = (Via)viasDesordenadas.get(i);
		 Arrays.sort(vias, ordenador);
		 for(int i=0; i<vias.length; i++)
			 viasDesordenadas.set(i, vias[i]);
		 return viasDesordenadas;
	 }
	 
	 private List ordenarPortales(List portalesDesordenados) {
		 OrdenadorListados ordenador = new OrdenadorListados(OrdenadorListados.TIPO_PORTAL);
		 Portal[] portales = new Portal[portalesDesordenados.size()];
		 for(int i=0; i<portalesDesordenados.size(); i++)
			 portales[i] = (Portal)portalesDesordenados.get(i);
		 Arrays.sort(portales, ordenador);
		 for(int i=0; i<portales.length; i++)
			 portalesDesordenados.set(i, portales[i]);
		 return portalesDesordenados;
	 }
}
