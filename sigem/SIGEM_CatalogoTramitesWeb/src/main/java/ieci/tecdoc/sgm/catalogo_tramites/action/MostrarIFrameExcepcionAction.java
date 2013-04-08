/*
 * Creado el 15-mar-2006
 *
 * TODO Para cambiar la plantilla de este archivo generado, vaya a
 * Ventana - Preferencias - Java - Estilo de código - Plantillas de código
 */
package ieci.tecdoc.sgm.catalogo_tramites.action;
/*
 * $Id: MostrarIFrameExcepcionAction.java,v 1.2.2.2 2008/04/29 11:04:00 jnogales Exp $
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MostrarIFrameExcepcionAction extends CatalogoTramitesWebAction {
		private static final String SUCCESS_FORWARD =		"success";
	
		public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
				HttpServletRequest request, HttpServletResponse response) {
	   	
	   		Exception e = (Exception) request.getAttribute(org.apache.struts.Globals.EXCEPTION_KEY);
	   		
	   		if(e != null){
		   		String stacktrace = (e.getCause()!=null) ? e.getCause().toString() : "";//ERegistreException.getExtendedMessageForJspUse(e);
		   		String msgError = e.getMessage();
		   		request.setAttribute("stacktrace", stacktrace);
		   		request.setAttribute("msgError", msgError);
	   		}
	   		return mapping.findForward(SUCCESS_FORWARD);
	   }

}
