/*
 * Creado el 16-mar-2006
 *
 * TODO Para cambiar la plantilla de este archivo generado, vaya a
 * Ventana - Preferencias - Java - Estilo de código - Plantillas de código
 */
package ieci.tecdoc.sgm.catalogo_tramites.action;
/*
 * $Id: EliminarDocumentoAction.java,v 1.3.2.2 2008/04/29 11:04:00 jnogales Exp $
 */
import ieci.tecdoc.sgm.catalogo_tramites.utils.Defs;
import ieci.tecdoc.sgm.core.admin.web.SesionAdminHelper;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author IECISA (Carlos Navas)
 *
 * TODO Para cambiar la plantilla de este comentario generado, vaya a
 * Ventana - Preferencias - Java - Estilo de código - Plantillas de código
 */
public class EliminarDocumentoAction extends CatalogoTramitesWebAction {
		private static final String SUCCESS_FORWARD =		"success";
		private static final String FAILURE_FORWARD =			"failure";
	
		public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
				HttpServletRequest request, HttpServletResponse response) {
	   	
	   		String id = request.getParameter("id");
	   		try{
                ServicioCatalogoTramites oServicio = LocalizadorServicios.getServicioCatalogoTramites();
                oServicio.deleteDocument(id, SesionAdminHelper.obtenerEntidad(request));
	   		}catch(Exception e){
	   			request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_DOCUMENTOS);
		    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.toString());
		    	return mapping.findForward(FAILURE_FORWARD);
	   		}
	  
	   		request.setAttribute("deletedID", id);
	   		return mapping.findForward(SUCCESS_FORWARD);
	   }

}
