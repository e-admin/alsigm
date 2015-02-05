package ieci.tecdoc.sgm.geolocalizacion.action;

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoCoordenadas;
import ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoPortal;
import ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoReferenciaCatastral;
import ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoVia;
import ieci.tecdoc.sgm.core.services.geolocalizacion.ServicioGeoLocalizacion;
import ieci.tecdoc.sgm.core.services.geolocalizacion.URLsPlano;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class VerPlanoAction extends Action {
	 public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	 		throws Exception {
	 		
		 try{
			 ServicioGeoLocalizacion oServicio = LocalizadorServicios.getServicioGeoLocalizacion();
			 String tipo = request.getParameter("tipo");
			 URLsPlano url = null;
			 
			 if ("1".equals(tipo)){
				 PeticionPlanoCoordenadas datosLocalizacion = new PeticionPlanoCoordenadas(
					 new Integer(request.getParameter("idMapa")).intValue(),
					 new Double(request.getParameter("coordX")).doubleValue(),
					 new Double(request.getParameter("coordY")).doubleValue(),
					 new Integer(request.getParameter("ancho")).intValue(),
					 new Integer(request.getParameter("alto")).intValue(),
					 new Integer(request.getParameter("escala")).intValue(),
					 new Integer(request.getParameter("codigoINE")).intValue());
				 url = oServicio.verPlanoPorCoordenadas(datosLocalizacion);
			 } else if ("2".equals(tipo)){
				 PeticionPlanoReferenciaCatastral datosLocalizacion = new PeticionPlanoReferenciaCatastral(
					 new Integer(request.getParameter("idMapa")).intValue(),
					 new Integer(request.getParameter("ancho")).intValue(),
					 new Integer(request.getParameter("alto")).intValue(),
					 new Integer(request.getParameter("escala")).intValue(),
					 new Integer(request.getParameter("codigoINE")).intValue(),
					 new String(request.getParameter("refCatastral")));
				 url = oServicio.verPlanoPorReferenciaCatastral(datosLocalizacion);
			}else if ("3".equals(tipo)){
				 PeticionPlanoVia datosLocalizacion = new PeticionPlanoVia(
					 new Integer(request.getParameter("idMapa")).intValue(),
					 new Integer(request.getParameter("ancho")).intValue(),
					 new Integer(request.getParameter("alto")).intValue(),
					 new Integer(request.getParameter("escala")).intValue(),
					 new Integer(request.getParameter("codigoINE")).intValue(),
					 new Integer(request.getParameter("idVia")).intValue());
				 url = oServicio.verPlanoPorIdVia(datosLocalizacion);
			 }else if ("4".equals(tipo)){
				 PeticionPlanoPortal datosLocalizacion = new PeticionPlanoPortal(
					 new Integer(request.getParameter("idMapa")).intValue(),
					 new Integer(request.getParameter("ancho")).intValue(),
					 new Integer(request.getParameter("alto")).intValue(),
					 new Integer(request.getParameter("escala")).intValue(),
					 new Integer(request.getParameter("codigoINE")).intValue(),
					 new Integer(request.getParameter("idPortal")).intValue());
				 url = oServicio.verPlanoPorIdNumeroPolicia(datosLocalizacion);
			 }
			request.setAttribute("plano", ""+url.getUrlMapServer());
			request.setAttribute("callejero", ""+url.getUrlGuiaUrbana());
		 } catch (Exception e) {
			 System.out.println("Error " + e);
		 }
		
		 return mapping.findForward("success");
	 }
}
