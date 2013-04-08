package ieci.tecdoc.sgm.catalogo_tramites.action;
/*
 * $Id: CargarDocumentoAction.java,v 1.3.2.2 2008/04/29 11:04:00 jnogales Exp $
 */
import ieci.tecdoc.sgm.catalogo_tramites.utils.Defs;
import ieci.tecdoc.sgm.core.admin.web.SesionAdminHelper;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.catalogo.Documento;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class CargarDocumentoAction extends CatalogoTramitesWebAction {
		private static final String SUCCESS_FORWARD =		"success";
		private static final String FAILURE_FORWARD =			"failure";
	
		public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
				HttpServletRequest request, HttpServletResponse response) {
	   	
		    String id = request.getParameter("id");

		    try{
                ServicioCatalogoTramites oServicio = LocalizadorServicios.getServicioCatalogoTramites();
			    Documento document = oServicio.getDocument(id, SesionAdminHelper.obtenerEntidad(request));
	
			    if (document.getExtension() == null)
			        document.setExtension("");
			    if (document.getSignatureHook() == null)
			        document.setSignatureHook("");
			    if (document.getValidationHook() == null)
			        document.setValidationHook("");
			    
			    request.setAttribute("document", document);

		    }catch(Exception e){
		    	request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_DOCUMENTOS);
		    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.toString());
		    	return mapping.findForward(FAILURE_FORWARD);
	   		}

		    return mapping.findForward(SUCCESS_FORWARD);
	   }
	   
}
