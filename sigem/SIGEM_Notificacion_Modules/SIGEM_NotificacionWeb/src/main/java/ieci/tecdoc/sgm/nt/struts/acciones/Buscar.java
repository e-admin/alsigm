/*
 * Buscar.java
 *
 * Created on 13 de junio de 2007, 12:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.struts.acciones;

import ieci.tecdoc.sgm.autenticacion.util.TipoSolicitante;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.notificaciones.CriterioBusquedaNotificaciones;
import ieci.tecdoc.sgm.core.services.notificaciones.Notificacion;
import ieci.tecdoc.sgm.core.services.notificaciones.Notificaciones;
import ieci.tecdoc.sgm.core.services.notificaciones.ServicioNotificaciones;
import ieci.tecdoc.sgm.core.services.sesion.InfoUsuario;
import ieci.tecdoc.sgm.core.services.sesion.ServicioSesionUsuario;
import ieci.tecdoc.sgm.core.user.web.ConstantesSesionUser;
import ieci.tecdoc.sgm.nt.struts.util.Misc;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * 
 * @author X73994NA
 */
public class Buscar extends NotificacionWebAction {

	/** Creates a new instance of Buscar */
	public Buscar() {
	}

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		String target = new String("success");
		String cnif = null;
		String codNoti = (String) request.getParameter("codNoti");
		java.util.Date desde = null;
		java.util.Date hasta = null;

		String aux = (String) request.getParameter("desde");
		if (aux != null && !aux.equals("")) {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					"dd-MM-yyyy", java.util.Locale.US);
			sdf.setCalendar(java.util.Calendar.getInstance());
			try {
				desde = sdf.parse(aux);
			} catch (java.text.ParseException e) {
				target = new String("failure");
				request.setAttribute("error",
						"Las fecha de busqueda desde no es valida");
			}
		}

		aux = (String) request.getParameter("hasta");
		if (aux != null && !aux.equals("")) {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					"dd-MM-yyyy", java.util.Locale.US);
			sdf.setCalendar(java.util.Calendar.getInstance());
			try {
				hasta = sdf.parse(aux);
			} catch (java.text.ParseException e) {
				target = new String("failure");
				request.setAttribute("error",
						"Las fecha de busqueda hasta no es valida");
			}
		}

		Integer estado = null;
		if (request.getParameter("estado") != null)
			estado = new Integer((String) request.getParameter("estado"));
		String tipo = (String) request.getParameter("tipo");

		cnif = (String)request.getSession().getAttribute("cnif");
		if (cnif == null || "".equals(cnif)) {
			ServicioSesionUsuario oServicio;
			try {
				oServicio = LocalizadorServicios.getServicioSesionUsuario();
				if(oServicio !=null) {
					InfoUsuario oUsuario = oServicio.getInfoUsuario((String)request.getSession().getAttribute(ConstantesSesionUser.ID_SESION),
											Misc.obtenerEntidad(request));
					if(oUsuario!=null) {
						
						// Persona fisica o juridica
					    if (oUsuario.getInQuality().equals(String.valueOf(TipoSolicitante.INDIVIDUAL))) {
					    	 
					    	// Persona fisica (individual)
					    	cnif = oUsuario.getId();
					    }
					    else {
					    	// Persona juridica (representante legal)
					    	cnif = oUsuario.getCIF();
					    }
					    
						request.getSession().setAttribute("cnif", cnif);
					}
				}
			} catch (SigemException e) {
				target = new String("failure");
				request.setAttribute("error",
					"Error obteniendo información del usuario: " + e.getMessage());
				return (mapping.findForward(target));				
			}				
		}
		
		// if no mane supplied Set the target to failure
		if (cnif == null || "".equals(cnif)) {
			target = new String("failure");
			request.setAttribute("error", "El documento de identidad es nulo");
		} else {
			if (!target.equals("failure")) {
				CriterioBusquedaNotificaciones criterios = new CriterioBusquedaNotificaciones();

				criterios.setNif(cnif);
				if (codNoti != null && !codNoti.equals(""))
					criterios.setNotificacion(codNoti);
				criterios.setFechaDesde(desde);
				criterios.setFechaHasta(hasta);
				if (estado == null)
					criterios.setEstado(new Integer(2));
				else if (estado.intValue() == -3)
					criterios.setEstado(null);
				else
					criterios.setEstado(estado);
				if (tipo != null && !tipo.equals(""))
					criterios.setTipo(tipo);

				//
	        	ServicioNotificaciones oServicio;
				try {
					oServicio = LocalizadorServicios.getServicioNotificaciones();
					Notificaciones notificaciones=oServicio.consultarNotificaciones(criterios, true, Misc.obtenerEntidad(request));
					rellenarDescripcionesEstado(notificaciones,request);
					
					request.setAttribute("Notificaciones",notificaciones);
					request.setAttribute("cnifDest", cnif);
				} catch (SigemException e) {
					target = new String("failure");
					request.setAttribute("error",
						"Error consultando las modificaciones: " + e.getMessage());
				}
			}
		}
		return (mapping.findForward(target));
	}
	
	private void rellenarDescripcionesEstado(Notificaciones notificaciones,HttpServletRequest request){
		String CONST_PREFFIX_NOTIFY_CONNECTOR="ieci.tecdoc.sgm.nt.notificacion.conector.";
		String CONST_PREFFIX_NOTIFY_STATE="estado.";
		String CONST_MINUS="minus";
		String CONST_SISNOT="sisnot";
		
		for(Iterator it=notificaciones.getNotificaciones().iterator();it.hasNext();){
			Notificacion noti=(Notificacion)it.next();
			
			
			String mensajeEstado=CONST_PREFFIX_NOTIFY_CONNECTOR+
				(noti.getSistemaId()==null?CONST_SISNOT:noti.getSistemaId().toLowerCase())+
				"."+CONST_PREFFIX_NOTIFY_STATE+(noti.getEstado().toString());
     	
			MessageResources messages=getResources(request);
			noti.setDescripcionEstado(messages.getMessage(request.getLocale(), mensajeEstado));
		}
		//return notificaciones;
	}

}
