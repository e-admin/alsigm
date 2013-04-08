package ieci.tecdoc.sgm.consulta_telematico.action;

import ieci.tecdoc.sgm.autenticacion.util.TipoSolicitante;
import ieci.tecdoc.sgm.consulta_telematico.utils.Defs;
import ieci.tecdoc.sgm.consulta_telematico.utils.Utils;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.sesion.InfoUsuario;
import ieci.tecdoc.sgm.core.services.sesion.ServicioSesionUsuario;
import ieci.tecdoc.sgm.core.services.sesion.SesionUsuario;
import ieci.tecdoc.sgm.core.services.telematico.RegistroConsulta;
import ieci.tecdoc.sgm.core.services.telematico.Registros;
import ieci.tecdoc.sgm.core.services.telematico.ServicioRegistroTelematico;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListadoRegistrosAction extends ConsultaRegistroTelematicoWebAction {

	private static final Logger logger = Logger.getLogger(ListadoRegistrosAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

    	HttpSession session = request.getSession();

    	String idEntidad = (String)session.getAttribute(Defs.ENTIDAD_ID);
    	if (idEntidad == null || "".equals(idEntidad)) {
    		return mapping.findForward(Defs.LOGIN_FORWARD);
    	}

    	String idSesion = (String)session.getAttribute(Defs.SESION_ID);
    	if (idSesion == null || "".equals(idSesion)) {
    		return mapping.findForward(Defs.LOGIN_FORWARD);
    	}

    	try {
	    	ServicioSesionUsuario oServicioAuth = LocalizadorServicios.getServicioSesionUsuario();
	    	ServicioRegistroTelematico oServicioRT = LocalizadorServicios.getServicioRegistroTelematico();

	    	Entidad entidad = new Entidad();
	    	entidad.setIdentificador(idEntidad);

	    	SesionUsuario oSesion = null;
	    	try {
	    		oSesion =  oServicioAuth.obtenerSesion(idSesion, entidad);
	    	}
	    	catch(Exception e) {
	    		if (oSesion == null || oSesion.getSessionId() == null) {
	    			return mapping.findForward(Defs.LOGIN_FORWARD);
	    		}
	    	}

			String cnif = (String) session.getAttribute(Defs.CNIF_USUARIO);
			if (cnif == null || "".equals(cnif)) {

		    	InfoUsuario solicitante = oSesion.getSender();

				// Persona fisica o juridica
			    if (solicitante.getInQuality().equals(String.valueOf(TipoSolicitante.INDIVIDUAL))) {

			    	// Persona fisica (individual)
			    	cnif = solicitante.getId();
					session.setAttribute(Defs.NOMBRE_USUARIO, solicitante.getName());
			    }
			    else {
			    	// Persona juridica (representante legal)
			    	cnif = solicitante.getCIF();
			    	String nombreUsuario = solicitante.getSocialName();
			    	if ((solicitante.getName() != null) && (!solicitante.getName().trim().equals(""))) {
			    		 nombreUsuario += " (" + solicitante.getName() + ")";
			    	}
					session.setAttribute(Defs.NOMBRE_USUARIO, nombreUsuario);
			    }

		    	session.setAttribute(Defs.CNIF_USUARIO , cnif);
			}

	    	try {
	    		RegistroConsulta registroConsulta = new RegistroConsulta();
	        	registroConsulta.setSenderId(cnif);
	        	//registroConsulta.setStatus(Defs.REGISTRO_OK);
	        	Registros registros = oServicioRT.obtenerRegistrosParaMostrar(registroConsulta, entidad);

	        	if (registros != null) {
	        		request.setAttribute(Defs.REGISTROS, Utils.obtenerListado(registros, oServicioRT, entidad));
	        	}
	        	else {
	        		request.setAttribute(Defs.REGISTROS, new ArrayList());

					return mapping.findForward(Defs.FAILURE_FORWARD);
				}
			}
	    	catch (Exception e) {
				logger.error("Se ha producido un error al obtener los registros", e.fillInStackTrace());
				request.setAttribute(Defs.REGISTROS, new ArrayList());

				return mapping.findForward(Defs.FAILURE_FORWARD);
			}
    	}
    	catch (Exception e) {
			logger.error("Se ha producido un error al obtener los registros", e.fillInStackTrace());
			request.setAttribute(Defs.REGISTROS, new ArrayList());

			return mapping.findForward(Defs.FAILURE_FORWARD);
		}

    	return mapping.findForward(Defs.SUCCESS_FORWARD);
	}

}
