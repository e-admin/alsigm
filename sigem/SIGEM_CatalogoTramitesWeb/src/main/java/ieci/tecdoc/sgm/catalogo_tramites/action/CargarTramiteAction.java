package ieci.tecdoc.sgm.catalogo_tramites.action;
/*
 * $Id: CargarTramiteAction.java,v 1.4.2.2 2008/04/29 11:04:00 jnogales Exp $
 */
import ieci.tecdoc.sgm.catalogo_tramites.utils.Defs;
import ieci.tecdoc.sgm.core.admin.web.SesionAdminHelper;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.catalogo.Tramite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CargarTramiteAction extends CatalogoTramitesWebAction {
	private static final String SUCCESS_FORWARD =		"success";
	private static final String FAILURE_FORWARD =			"failure";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
        
        String id = request.getParameter("id");
        try{
            Tramite procedure = null;
            ServicioCatalogoTramites oServicio = LocalizadorServicios.getServicioCatalogoTramites();
            procedure = oServicio.getProcedure(id, true, SesionAdminHelper.obtenerEntidad(request));
            
            request.setAttribute("description", procedure.getDescription());
            request.setAttribute("topic", procedure.getTopic());
            request.setAttribute("addressee", procedure.getAddressee());
            String oficina = (procedure.getOficina()==null || "null".equalsIgnoreCase(procedure.getOficina()))?"":procedure.getOficina();
            procedure.setOficina(oficina);
            request.setAttribute("oficina", oficina);
            String idProcedimiento = (procedure.getIdProcedimiento()==null || "null".equalsIgnoreCase(procedure.getIdProcedimiento()))?"":procedure.getIdProcedimiento();
            request.setAttribute("idProcedimiento", idProcedimiento);
            procedure.setIdProcedimiento(idProcedimiento);
            boolean docLimit = procedure.getLimitDocs();
            if (docLimit)
                request.setAttribute("limit", "1");
            else
                request.setAttribute("limit", "0");
            boolean procFirma = procedure.getFirma();
            if (procFirma)
                request.setAttribute("firmap", "1");
            else
                request.setAttribute("firmap", "0");
            
            request.setAttribute("procedure", procedure);
        }catch(Exception e){
        	request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_TRAMITES);
	    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.toString());
	    	return mapping.findForward(FAILURE_FORWARD);
        }
        return mapping.findForward(SUCCESS_FORWARD);
   }
	
}

