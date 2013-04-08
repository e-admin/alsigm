package ieci.tecdoc.sgm.autenticacion.action;
/*
 *  $Id: SeleccionEntidadAction.java,v 1.1.2.6 2008/03/14 11:12:04 jnogales Exp $
 */
import ieci.tecdoc.sgm.autenticacion.AutenticacionManager;
import ieci.tecdoc.sgm.autenticacion.util.SesionInfo;
import ieci.tecdoc.sgm.autenticacion.utils.Defs;
import ieci.tecdoc.sgm.core.admin.web.AdministracionHelper;
import ieci.tecdoc.sgm.core.config.ports.PortsConfig;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.idioma.LectorIdiomas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SeleccionEntidadAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		try{

			HttpSession session = request.getSession();

			ArrayList idiomas = LectorIdiomas.getIdiomas();
			session.setAttribute(Defs.IDIOMAS_DISPONIBLES, idiomas);

			String redireccion = (String)request.getParameter(Defs.REDIRECCION);
			if (Defs.isNuloOVacio(redireccion)){
				redireccion = (String)request.getSession().getAttribute(Defs.REDIRECCION);
				if (Defs.isNuloOVacio(redireccion)){
					redireccion = new String("");
				}
			}
			if(!Defs.isNuloOVacio(redireccion)){
				session.setAttribute(Defs.REDIRECCION, redireccion);
			}


			String tramiteId = (String)request.getParameter(Defs.TRAMITE_ID);
			if(Defs.isNuloOVacio(tramiteId)){
				tramiteId = (String)request.getSession().getAttribute(Defs.TRAMITE_ID);
				if(Defs.isNuloOVacio(null)){
					tramiteId = new String("");
				}
			}
			if(!Defs.isNuloOVacio(tramiteId)){
				session.setAttribute(Defs.TRAMITE_ID, tramiteId);
			}

			String sessionId = (String)request.getParameter(Defs.SESION_ID);
			if(Defs.isNuloOVacio(sessionId)){
				sessionId = (String)request.getSession().getAttribute(Defs.SESION_ID);
				if(Defs.isNuloOVacio(sessionId)){
					sessionId = new String("");
				}
			}
			if(!Defs.isNuloOVacio(sessionId)){
				session.setAttribute(Defs.SESION_ID, sessionId);
			}

			String entidadId = (String)request.getParameter(Defs.ENTIDAD_ID);
			if(Defs.isNuloOVacio(entidadId)){
				entidadId = (String)request.getSession().getAttribute(Defs.ENTIDAD_ID);
				if(Defs.isNuloOVacio(entidadId)){
					entidadId = new String("");
				}
			}
			if(!Defs.isNuloOVacio(entidadId)){
				session.setAttribute(Defs.ENTIDAD_ID, entidadId);
			}

			String lang = (String)request.getParameter(Defs.LANG);
			if(Defs.isNuloOVacio(lang)){
				lang = (String)request.getSession().getAttribute(Defs.LANG);
				if(Defs.isNuloOVacio(lang)){
					lang = new String("");
				}
			}
			if(!Defs.isNuloOVacio(lang)){
				session.setAttribute(Defs.LANG, lang);
			}

			String country = (String)request.getParameter(Defs.COUNTRY);
			if(Defs.isNuloOVacio(country)){
				country = (String)request.getSession().getAttribute(Defs.COUNTRY);
				if(Defs.isNuloOVacio(country)){
					lang = new String("");
				}
			}
			if(!Defs.isNuloOVacio(country)){
				session.setAttribute(Defs.COUNTRY, country);
			}

			if(!Defs.isNuloOVacio(lang) && !Defs.isNuloOVacio(country)) {
				// Establecer el idioma
				request.getSession().setAttribute("org.apache.struts.action.LOCALE", new Locale(lang, country));
			}

			SesionInfo sessionInfo;
			if (!Defs.isNuloOVacio(sessionId)){
				sessionInfo = AutenticacionManager.getLogin(sessionId, tramiteId, entidadId);
				if (sessionInfo != null){
					String sn = sessionInfo.getSender().getCertificateSN();
					String url = (String)request.getSession().getServletContext().getAttribute("redir" + redireccion);
					String port;
					if (!Defs.isNuloOVacio(sn))
						port = PortsConfig.getCertPort();
					else
						port = PortsConfig.getHttpsPort();
					session.setAttribute(Defs.URL_REDIRECCION, url);
					session.setAttribute(Defs.URL_PUERTO, port);

					return mapping.findForward("logged");
				}
			}

			if(!Defs.isNuloOVacio(entidadId)){
				session.setAttribute(Defs.ENTIDAD_ID, entidadId);
				return mapping.findForward("success");
			}else{
				// Obtenemos la lista de entidades.
				List oLista = AdministracionHelper.obtenerListaEntidades();
				//Si sólo hay un ayuntamiento se salta directamente
				if(oLista.size()==1) {
					Entidad oEntidad = (Entidad)oLista.get(0);
					session.setAttribute(Defs.ENTIDAD_ID, oEntidad.getIdentificador());
			   		return mapping.findForward("success");
				} else {
					request.setAttribute("entidades", oLista);
			   		return mapping.findForward("entidades");
				}
			}

		}catch(Exception e){
	    	request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_OBTENER_TIPO_ACCESO);
	    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.toString());
	   		return mapping.findForward("failure");
	   	}
	}
}
