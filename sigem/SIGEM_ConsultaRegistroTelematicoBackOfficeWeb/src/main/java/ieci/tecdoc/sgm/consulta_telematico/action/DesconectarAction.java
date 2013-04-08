package ieci.tecdoc.sgm.consulta_telematico.action;

import ieci.tecdoc.sgm.consulta_telematico.utils.Defs;
import ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Clase que desconecta a un usuario.
 *
 */
public class DesconectarAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
  		throws Exception {

		HttpSession session = request.getSession();

	    try {
	    	session.removeAttribute(Defs.SESION_ID);
	    	session.removeAttribute(Defs.ENTIDAD_ID);
	    	session.removeAttribute(Defs.PERMISO_CRT);

	    	request.setAttribute(Defs.URL, AutenticacionBackOffice.obtenerUrlLogout(request));

	    	return mapping.findForward("success");
	    } catch(Exception e){
	    	return mapping.findForward("success");
   		}
   	}
}
