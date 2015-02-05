package ieci.tecdoc.sgm.consulta_telematico.action;

import ieci.tecdoc.sgm.consulta_telematico.utils.Defs;
import ieci.tecdoc.sgm.core.user.web.WebAuthenticationHelper;

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
	public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
  		throws Exception {
	    
	    try{
	    	HttpSession session = request.getSession();
	    	String sessionId = (String)session.getAttribute(Defs.SESION_ID);
	    	if (sessionId == null && sessionId.equals("")){
	    		return mapping.findForward("success");
	    	}

			StringBuffer sbUrl = new StringBuffer();
			sbUrl.append(WebAuthenticationHelper.getWebAuthDesconectURL(request));
			
	    	session.removeAttribute(Defs.SESION_ID);
	    	session.removeAttribute(Defs.ENTIDAD_ID);
	    	session.removeAttribute(Defs.CNIF_USUARIO);
	    	session.removeAttribute(Defs.NOMBRE_USUARIO);
	    	session.invalidate();
	    	
	    	request.setAttribute(Defs.URL, sbUrl.toString());
	    	
	    	return mapping.findForward("success");
	    }catch(Exception e){
	    	return mapping.findForward("success");
   		}		    
   	}
}
