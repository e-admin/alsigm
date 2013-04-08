package ieci.tecdoc.sgm.registro.action;

import ieci.tecdoc.sgm.registro.utils.Defs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MostrarErrorRegistroAction extends RegistroWebAction {
	  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) {
		  
		  HttpSession session = request.getSession();
		  
		  request.setAttribute(Defs.MENSAJE_ERROR, session.getAttribute(Defs.MENSAJE_ERROR));
		  request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, session.getAttribute(Defs.MENSAJE_ERROR_DETALLE));
	    
		  return mapping.findForward("success");
   	}
}
