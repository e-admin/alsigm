package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ShowHelpAction extends BaseAction {

	public static final Logger logger = Logger.getLogger(ShowHelpAction.class);
	

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
	
		String tipoObj = request.getParameter("tipoObj");
		String idObj=request.getParameter("idObj");
	
		
		//Obtenemos el contenido de la ayuda para el objeto y el tipo objeto recibido.	
		IInvesflowAPI invesflowAPI = session.getAPI();
		ICatalogAPI catalogAPI=invesflowAPI.getCatalogAPI();
		String idioma=session.getClientContext().getLocale().getLanguage();
		IItem itemAyuda=catalogAPI.getCTHelp(tipoObj, idObj,idioma );
		if(itemAyuda!=null){
			request.setAttribute("contenido", itemAyuda.get("CONTENIDO"));
		}
		
		return mapping.findForward("success");
	}

}
