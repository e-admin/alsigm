package ieci.tecdoc.sgm.registro.action;

import ieci.tecdoc.sgm.core.user.web.WebAuthenticationHelper;
import ieci.tecdoc.sgm.registro.utils.Defs;
import ieci.tecdoc.sgm.registro.utils.FilenameTmpFilter;

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
	public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
  		throws Exception {

	    try{
	    	HttpSession session = request.getSession();
	    	String sessionId = (String)session.getAttribute(Defs.SESION_ID);
	    	if (sessionId == null || sessionId.equals("")){
	    		request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_DESCONEXION);
		    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, "Error: El usuario no se encontraba logado o su sesión había caducado");
	    		return mapping.findForward("failure");
	    	}

			StringBuffer sbUrl = new StringBuffer();
			sbUrl.append(WebAuthenticationHelper.getWebAuthDesconectURL(request));

	    	String tmpXmlPath = (String)session.getServletContext().getRealPath("") + session.getServletContext().getAttribute(Defs.PLUGIN_TMP_PATH_XML);
	    	String tmpUploadPath = (String)session.getServletContext().getRealPath("") + session.getServletContext().getAttribute(Defs.PLUGIN_TMP_PATH_UPLOAD);
	    	deleteTmpFiles(tmpXmlPath, sessionId + "_");
	    	deleteTmpFiles(tmpUploadPath, sessionId + "_");

	    	session.removeAttribute(Defs.SESION_ID);
	    	session.invalidate();
			response.sendRedirect(sbUrl.toString());
	    }catch(Exception e){
	    	request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_DESCONEXION);
	    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.getMessage());
	    	return mapping.findForward("failure");
   		}

	    return null;
   	}

	public void deleteTmpFiles(String path, String filter){
		File lostFiles = new File(path);
    	File[] lostFileList = lostFiles.listFiles(new FilenameTmpFilter(filter));
    	if (lostFileList != null && lostFileList.length > 0){
    		for (int i=0; i<lostFileList.length; i++)
    			lostFileList[i].delete();
    	}
	}
}
