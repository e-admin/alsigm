package ieci.tecdoc.sgm.usuario.admin.struts.action;

import ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion;
import ieci.tecdoc.sgm.usuario.admin.struts.Constants;

import java.io.File;

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
public class DesconectarAction extends Action{
		private static final String SUCCESS_FORWARD =		"success";
		
		public ActionForward execute(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
  				throws Exception {

				String Url = AutenticacionAdministracion.obtenerUrlLogout(request);

				HttpSession session = request.getSession();
				session.invalidate();
	    
				request.setAttribute(Constants.URL_REDIRECCION, Url);
				
				return mapping.findForward(SUCCESS_FORWARD);
		}
	
		public ActionForward executeAction(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			
			return mapping.findForward(SUCCESS_FORWARD);
		}
}
