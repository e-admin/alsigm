package ieci.tecdoc.sgm.autenticacion.action;
/*
 *  $Id: DesconectarAction.java,v 1.5.2.5 2008/10/13 08:47:45 jnogales Exp $
 */

import ieci.tecdoc.sgm.autenticacion.utils.Defs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.sesion.ServicioSesionUsuario;

/**
 * Clase que desconecta a un usuario del Sistema. Elimina la entrada de la tabla
 * de sesiones y los posibles ficheros temporales creados (en caso que existan)
 * @author José Antonio Nogales
 *
 */
public class DesconectarAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
  		throws Exception {
	    
	    try{
	    	HttpSession session = request.getSession();
	    	String sessionId = (String)request.getAttribute(Defs.SESION_ID);
	    	String entidadId = (String)request.getAttribute(Defs.ENTIDAD_ID);
	    	if (	((sessionId == null) || ("".equals(sessionId)))
	    			||((entidadId == null) || ("".equals(entidadId)))
	    	){
	    		sessionId = (String)request.getParameter(Defs.SESION_ID);
	    		entidadId = (String)request.getParameter(Defs.ENTIDAD_ID);
	    		if ((sessionId == null) || ("".equals(sessionId))){
		    		request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_DESCONEXION);
			    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, "Error: El usuario no se encontraba logado o su sesión había caducado");
		    		return mapping.findForward("failure");	    			
	    		}
	    	}
	    	ServicioSesionUsuario oServicio = LocalizadorServicios.getServicioSesionUsuario();
	    	Entidad oEntidad = new Entidad();
	    	oEntidad.setIdentificador(entidadId);
	    	try {
	    		oServicio.borrarSesion(sessionId, oEntidad);
	    	} catch(Exception e) { }

	    	session.removeAttribute(Defs.SESION_ID);
	    	session.removeAttribute(Defs.ENTIDAD_ID);	    	
	    	session.invalidate();
	    }catch(Exception e){
	    	request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_DESCONEXION);
	    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.toString());
	    	return mapping.findForward("failure");
   		}		    
	    request.setAttribute(Defs.MENSAJE_INFORMATIVO, Defs.MENSAJE_INFORMATIVO_DESCONEXION);
	    return mapping.findForward("success");
   	}
}
