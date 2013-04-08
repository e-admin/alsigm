package ieci.tecdoc.sgm.autenticacion.action;

import ieci.tecdoc.sgm.autenticacion.AutenticacionManager;
import ieci.tecdoc.sgm.autenticacion.utils.Defs;
import ieci.tecdoc.sgm.autenticacion.utils.UserAuthServiceHelper;
import ieci.tecdoc.sgm.core.config.ports.PortsConfig;
import ieci.tecdoc.sgm.core.services.autenticacion.DatosUsuario;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ValidacionUsuarioAction  extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		String entidadId = (String)session.getAttribute(Defs.ENTIDAD_ID);
		
		String usuario = (String)request.getParameter(Defs.USUARIO);
		String password = (String)request.getParameter(Defs.PASSWORD);
		String sessionId = null;
		String sessionIdIni = null;
		
		try{
			sessionIdIni = (String)session.getAttribute(Defs.SESION_ID);

			DatosUsuario oUser = UserAuthServiceHelper.get(usuario, password, entidadId);
			
			if (sessionIdIni!=null && !sessionIdIni.equals(""))
				sessionId = AutenticacionManager.login(sessionIdIni, oUser.getName(), oUser.getLastname(), oUser.getEmail(), oUser.getId(), entidadId);
			else sessionId = AutenticacionManager.login(null, oUser.getName(), oUser.getLastname(), oUser.getEmail(), oUser.getId(), entidadId);
			session.setAttribute(Defs.SESION_ID, sessionId);
			
			String redireccion = (String)session.getAttribute(Defs.REDIRECCION);
			String url = (String)request.getSession().getServletContext().getAttribute("redir" + redireccion);
			String port = PortsConfig.getHttpsPort();
			session.setAttribute(Defs.URL_REDIRECCION, url);
			session.setAttribute(Defs.URL_PUERTO, port);
		}catch(Exception e){
			request.setAttribute("invalid_user", "true");
			return mapping.findForward("invalid_user");
	   	}
	   	return mapping.findForward("success");
	}
}
