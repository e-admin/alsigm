package ieci.tecdoc.sgm.catastro.action;

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.catastro.ServicioCatastro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ValidarReferenciaCatastralAction extends Action {
	 public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	 		throws Exception {
	 		
		 try{
			 String referenciaCatastral = request.getParameter("referenciaCatastral");
			 ServicioCatastro oServicio = LocalizadorServicios.getServicioCatastro();
			 boolean valido = oServicio.validarReferenciaCatastral(referenciaCatastral);
			 request.setAttribute("valido", ""+valido);
		 } catch (Exception e) {
			 System.out.println("Error " + e);
		 }
		
		 return mapping.findForward("success");
	 }
}
