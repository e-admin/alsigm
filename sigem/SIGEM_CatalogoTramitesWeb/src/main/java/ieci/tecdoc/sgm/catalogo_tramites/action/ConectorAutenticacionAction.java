package ieci.tecdoc.sgm.catalogo_tramites.action;
/*
 * $Id: ConectorAutenticacionAction.java,v 1.5.2.2 2008/04/29 11:04:00 jnogales Exp $
 */
import ieci.tecdoc.sgm.catalogo_tramites.form.ConectorAutenticacionForm;
import ieci.tecdoc.sgm.catalogo_tramites.utils.Defs;
import ieci.tecdoc.sgm.core.admin.web.SesionAdminHelper;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.catalogo.Conector;
import ieci.tecdoc.sgm.core.services.catalogo.ConectorAutenticacion;
import ieci.tecdoc.sgm.core.services.catalogo.Conectores;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.catalogo.TipoConector;
import ieci.tecdoc.sgm.core.services.catalogo.Tramite;
import ieci.tecdoc.sgm.core.services.catalogo.Tramites;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ConectorAutenticacionAction extends CatalogoTramitesWebAction {
	private static final String SUCCESS_FORWARD =		"success";
	private static final String FAILURE_FORWARD =			"failure";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		
	   	  Collection procedureList = new ArrayList();
	   	  Collection hookList = new ArrayList();
	   	  ConectorAutenticacion authHook;
	   	  
	   	  ConectorAutenticacionForm hooksBean = (ConectorAutenticacionForm)form;
	      
	   	  String act = hooksBean.getUserAction();
	   	  try{
	   		 ServicioCatalogoTramites oServicio = LocalizadorServicios.getServicioCatalogoTramites();
		     if (act != null){
		   	    if (act.equals("add")){
		   	    	authHook = new ConectorAutenticacion();
		   	    	authHook.setTramiteId(hooksBean.getSelProcedureId());
		   	    	authHook.setConectorId(hooksBean.getSelHookId());
		   	    	oServicio.addAuthHooks(authHook, SesionAdminHelper.obtenerEntidad(request));
		   	    	hooksBean.setUserAction("load");
		   	  	}else if (act.equals("delete")){
		   	  		oServicio.deleteAuthHooks(hooksBean.getSelProcedureId(), hooksBean.getOldHookId(), SesionAdminHelper.obtenerEntidad(request));
		   	  		hooksBean.setSelProcedureId("");
		   	  		hooksBean.setSelHookId("");
		   	  		hooksBean.setOldHookId("");
		   	  	}else if(act.equals("load")){
			        authHook = oServicio.getAuthHook(hooksBean.getSelProcedureId(), hooksBean.getSelHookId(), SesionAdminHelper.obtenerEntidad(request));
			        hooksBean.setSelProcedureId(authHook.getTramiteId());
			        hooksBean.setSelHookId(authHook.getConectorId());
			        hooksBean.setUserAction("load");
		   	  	}else if(act.equals("update")){
		   	  		authHook = new ConectorAutenticacion();
		   	  		authHook.setTramiteId(hooksBean.getSelProcedureId());
		   	  		authHook.setConectorId(hooksBean.getSelHookId());
		   	  		oServicio.updateAuthHooks(authHook, hooksBean.getOldHookId(), SesionAdminHelper.obtenerEntidad(request));
		   	  		hooksBean.setUserAction("load");
		   	  	}else{
		   	  		hooksBean.setSelProcedureId("");
		   	  		hooksBean.setSelHookId("");	
		   	  	}
		     }
		     Tramite procedure;
		     Tramites procedures = oServicio.getProcedures(SesionAdminHelper.obtenerEntidad(request));
		     for (int i=0; i < procedures.count(); i++){
		    	 procedure = (Tramite)procedures.get(i);
		    	 procedureList.add(new ConectorAutenticacionForm(procedure.getId(), procedure.getDescription(), ConectorAutenticacionForm.PROCEDURE));
		     }
		     
	         Conector hook;
	         Conectores hooks = oServicio.getHooks(SesionAdminHelper.obtenerEntidad(request));
	         for (int i=0; i < hooks.count(); i++){
	            hook = (Conector)hooks.get(i);
	            if ( (hook.getType() == TipoConector.WEB_USER_AUTH) ||
	            		(hook.getType() == TipoConector.CERTIFICATE_AUTH) ||
	            		(hook.getType() == TipoConector.CERTIFICATE_WEB_AUTH))
	            		hookList.add(new ConectorAutenticacionForm(hook.getId(), hook.getDescription(), ConectorAutenticacionForm.HOOK));
	         }

	   	  }catch (Exception e){
	   		  request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_CONECTORES_AUTENTICACION);
	   		  request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.toString());
	   		  request.setAttribute(Defs.REDIRECCION, "authHook.do");
	   		  return mapping.findForward(FAILURE_FORWARD);
	   	  }
	   	  request.setAttribute("procedures", procedureList);
	   	  request.setAttribute("hooks", hookList);
		  return mapping.findForward(SUCCESS_FORWARD);
   }

}
