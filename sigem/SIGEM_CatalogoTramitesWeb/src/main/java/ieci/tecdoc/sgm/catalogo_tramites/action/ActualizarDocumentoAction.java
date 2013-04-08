package ieci.tecdoc.sgm.catalogo_tramites.action;
/*
 * $Id: ActualizarDocumentoAction.java,v 1.4.2.2 2008/04/29 11:04:00 jnogales Exp $
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

public class ActualizarDocumentoAction extends CatalogoTramitesWebAction {
		private static final String SUCCESS_FORWARD =		"success";
		private static final String FAILURE_FORWARD =			"failure";
	
		public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
				HttpServletRequest request, HttpServletResponse response) {
	
		   	String id = request.getParameter("documents");
		    String description = request.getParameter("description");
		    String extension = request.getParameter("extension");
		    String signatureHook = request.getParameter("signature");
		    String contentHook = request.getParameter("content");
		    
		    try{
			    Documento document = new Documento();
			    document.setId(id);
			    document.setDescription(description);
			    document.setExtension(extension);
			    document.setSignatureHook(signatureHook);
			    document.setValidationHook(contentHook);
			    
			    ServicioCatalogoTramites oServicio = LocalizadorServicios.getServicioCatalogoTramites();
                oServicio.updateDocument(document, SesionAdminHelper.obtenerEntidad(request));
                
                request.setAttribute("document", document);
		    }catch(Exception e){
	   			request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_DOCUMENTOS);
		    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.toString());
		    	return mapping.findForward(FAILURE_FORWARD);
	   		}		    
		    return mapping.findForward(SUCCESS_FORWARD);
	   }
}
