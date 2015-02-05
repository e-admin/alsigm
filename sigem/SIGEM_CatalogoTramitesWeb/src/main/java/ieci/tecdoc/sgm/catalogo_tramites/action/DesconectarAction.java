package ieci.tecdoc.sgm.catalogo_tramites.action;
/*
 * $Id: DesconectarAction.java,v 1.1.2.2 2008/04/29 11:04:00 jnogales Exp $
 */
import ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Clase que desconecta a un usuario del Sistema. Elimina la entrada de la tabla
 * de sesiones y los posibles ficheros temporales creados (en caso que existan)
 * @author José Antonio Nogales
 *
 */
public class DesconectarAction extends CatalogoTramitesWebAction {
		private static final String SUCCESS_FORWARD =		"success";
		
		public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
				HttpServletRequest request, HttpServletResponse response) {

				String url = AutenticacionAdministracion.obtenerUrlLogout(request);
				
				HttpSession session = request.getSession();
				session.invalidate();
	    
				try {
					response.sendRedirect(url);
					return null;
				} catch(Exception e) { }
				
				return mapping.findForward(SUCCESS_FORWARD);
		}
	
}
