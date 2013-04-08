package ieci.tecdoc.sgm.catalogo_tramites.action;
/*
 * $Id: ListaOrganosDestinatariosAction.java,v 1.5.2.2 2008/04/29 11:04:00 jnogales Exp $
 */
import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesCodigosError;
import ieci.tecdoc.sgm.catalogo_tramites.form.CalendarioForm;
import ieci.tecdoc.sgm.catalogo_tramites.form.OrganoDestinatarioForm;
import ieci.tecdoc.sgm.catalogo_tramites.utils.Defs;
import ieci.tecdoc.sgm.core.admin.web.SesionAdminHelper;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.calendario.Calendario;
import ieci.tecdoc.sgm.core.services.calendario.CalendarioDia;
import ieci.tecdoc.sgm.core.services.calendario.CalendarioDias;
import ieci.tecdoc.sgm.core.services.calendario.CalendarioExcepcion;
import ieci.tecdoc.sgm.core.services.calendario.ServicioCalendario;
import ieci.tecdoc.sgm.core.services.catalogo.OrganoDestinatario;
import ieci.tecdoc.sgm.core.services.catalogo.OrganosDestinatarios;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.dto.Entidad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class CalendarioAction extends CatalogoTramitesWebAction {
	private static final String SUCCESS_FORWARD =		"success";
	private static final String FAILURE_FORWARD =			"failure";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

		CalendarioForm  calendarForm = (CalendarioForm)form;
		Calendario calendario = null;
		CalendarioDia dia = null;
		Collection diasFijos = new ArrayList();
		Collection diasVariables = new ArrayList();
		String act = calendarForm.getUserAction();
        Entidad entidad = SesionAdminHelper.obtenerEntidad(request);
        
		try{
			ServicioCalendario oServicio = LocalizadorServicios.getServicioCalendario();
		   //comprobación de las acciones llevadas a cabo por el usuario
			if (act != null){
			  if (act.equals("addCalendar")) {
				  calendario = new Calendario();
				  calendario.setDias(convertirArray(calendarForm.getDays()));
				  calendario.setHoraInicio(calendarForm.getHoraInicio());
				  calendario.setHoraFin(calendarForm.getHoraFin());
				  calendario.setMinutoInicio(calendarForm.getMinutoInicio());
				  calendario.setMinutoFin(calendarForm.getMinutoFin());
	       		  oServicio.nuevoCalendario(calendario, entidad);
	       		  calendarForm.setUserAction("loadCalendar");
			  }else if (act.equals("updateCalendar")) {
				  calendario = new Calendario();
				  calendario.setDias(convertirArray(calendarForm.getDays()));
				  calendario.setHoraInicio(calendarForm.getHoraInicio());
				  calendario.setHoraFin(calendarForm.getHoraFin());
				  calendario.setMinutoInicio(calendarForm.getMinutoInicio());
				  calendario.setMinutoFin(calendarForm.getMinutoFin());
	       		  oServicio.actualizarCalendario(calendario, entidad);
	       		  calendarForm.setUserAction("loadCalendar");
			  }else if (act.equals("deleteCalendar")) {
				  oServicio.eliminarCalendario(new Boolean(calendarForm.getBorrarDias()).booleanValue(), entidad);
				  calendarForm.setUserAction("");
			  }else if (act.equals("addDay")){
	       		  dia = new CalendarioDia();
	       		  dia.setFecha(calendarForm.getDay());
	       		  dia.setDescripcion(calendarForm.getDescription());
	       		  dia.setFijo(calendarForm.getFix() == 0 ? true : false);
	       		  dia = oServicio.nuevoDiaCalendario(dia, entidad);
	       		  calendarForm.setUserAction("loadDay");
	       		  StringTokenizer fecha = new StringTokenizer(calendarForm.getDay(), "/");
	       		  calendarForm.setId(""+dia.getId());
	       		  calendarForm.setFechaMes(fecha.nextToken());
	       		  calendarForm.setFechaDia(fecha.nextToken());
			  }else if (act.equals("updateDay")){
	       		  dia = new CalendarioDia();
	       		  dia.setId(new Integer(calendarForm.getId()).intValue());
	       		  dia.setFecha(calendarForm.getDay());
	       		  dia.setDescripcion(calendarForm.getDescription());
	       		  dia.setFijo(calendarForm.getFix() == 0 ? true : false);
	       		  oServicio.actualizarDiaCalendario(dia, entidad);
	       		  calendarForm.setUserAction("loadDay");
	       		  StringTokenizer fecha = new StringTokenizer(calendarForm.getDay(), "/");
	       		  calendarForm.setId(""+dia.getId());
	       		  calendarForm.setFechaMes(fecha.nextToken());
	       		  calendarForm.setFechaDia(fecha.nextToken());
			  }else if (act.equals("deleteDay")){
				  oServicio.eliminarDiaCalendario(calendarForm.getId(), entidad);
				  calendarForm.setUserAction("");
				  calendarForm.setDescription("");
				  calendarForm.setFix(0);
				  calendarForm.setDay("");
				  calendarForm.setId("");
				  calendarForm.setFechaDia("");
				  calendarForm.setFechaMes("");
			  }else if (act.equals("")) {
				  String[] days = new String[7];
				  for(int i=0; i<7; i++)
					days[i] = "0";
				  request.setAttribute("laborables", days);
			  }
			  
			  try {
				  calendario = oServicio.obtenerCalendario(entidad);
				  calendarForm.setDays(convertirString(calendario.getDias()));
				  calendarForm.setHoraInicio(calendario.getHoraInicio());
				  calendarForm.setHoraFin(calendario.getHoraFin());
				  calendarForm.setMinutoInicio(calendario.getMinutoInicio());
				  calendarForm.setMinutoFin(calendario.getMinutoFin());
				  request.setAttribute("laborables", calendario.getDias());
				  request.setAttribute("existeCalendario", new Boolean(true));
			  } catch(Exception e1) {
				  if (e1 instanceof CalendarioExcepcion) {
					  calendarForm.setDays(convertirString(null));
					  calendarForm.setHoraInicio("");
					  calendarForm.setHoraFin("");
					  calendarForm.setMinutoInicio("");
					  calendarForm.setMinutoFin("");
					  request.setAttribute("laborables", convertirArray(null));
					  request.setAttribute("existeCalendario", new Boolean(false));
				  }else throw e1;
			  }
			}
		   
			CalendarioDias diasFijosObj = oServicio.obtenerDiasCalendario(CalendarioDia.TIPO_FIJO, entidad);
			if (diasFijosObj.count() > 0) {
				for (int i = 0; i < diasFijosObj.count() ; i++) {
					dia = (CalendarioDia)diasFijosObj.get(i);
					diasFijos.add(dia);
				} 
			}

			CalendarioDias diasVariablesObj = oServicio.obtenerDiasCalendario(CalendarioDia.TIPO_VARIABLE, entidad);
			if (diasVariablesObj.count() > 0) {
				for (int i = 0; i < diasVariablesObj.count() ; i++) {
					dia = (CalendarioDia)diasVariablesObj.get(i);
					diasVariables.add(dia);
				} 
			}
		} catch (Exception e) {
	   		  request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_CALENDARIO);
	 		  request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.toString());
	 		  request.setAttribute(Defs.REDIRECCION, "calendar.do");
	 		  return mapping.findForward(FAILURE_FORWARD);
		}
		request.setAttribute(Defs.PLUGIN_DIAS, request.getSession().getServletContext().getAttribute(Defs.PLUGIN_DIAS));
		request.setAttribute("diasFijos", diasFijos);
		request.setAttribute("diasVariables", diasVariables);
		return mapping.findForward(SUCCESS_FORWARD);
	}
	
	public static String[] convertirArray(String cadena) {
		if (cadena != null && !cadena.equals("")) {
			StringTokenizer st = new StringTokenizer(cadena, ";");
			String[] tokens = new String[st.countTokens()];
			int i=0;
			while (st.hasMoreElements()) {
				tokens[i++] = st.nextToken();
			}
			return tokens;
		}else{
			String[] tokens = new String[7];
			for(int i=0; i<7; i++)
				tokens[i] = "" + Calendario.TIPO_LABORABLE;
			return tokens;
		}
	}
	
	public static String convertirString(String[] cadenas) {
		StringBuffer st = new StringBuffer();
		if (cadenas != null && cadenas.length > 0) {
			for(int i=0; i<cadenas.length; i++)
				st.append(cadenas[i]).append(";");
			return st.substring(0, st.length()-1);
		} else {
			for(int i=0; i<7; i++)
				st.append(Calendario.TIPO_LABORABLE).append(";");
			return st.substring(0, st.length()-1);
		}
			
	}
}
