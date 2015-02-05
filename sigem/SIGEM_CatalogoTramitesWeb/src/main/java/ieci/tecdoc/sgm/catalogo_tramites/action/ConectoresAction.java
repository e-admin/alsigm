
package ieci.tecdoc.sgm.catalogo_tramites.action;
/*
 * $Id: ConectoresAction.java,v 1.3.2.2 2008/04/29 11:04:00 jnogales Exp $
 */
import ieci.tecdoc.sgm.catalogo_tramites.form.ConectorForm;
import ieci.tecdoc.sgm.catalogo_tramites.utils.Defs;
import ieci.tecdoc.sgm.core.admin.web.SesionAdminHelper;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.catalogo.Conector;
import ieci.tecdoc.sgm.core.services.catalogo.Conectores;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.catalogo.TipoConector;
import ieci.tecdoc.sgm.core.services.catalogo.TiposConectores;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ConectoresAction extends CatalogoTramitesWebAction {
	private static final String SUCCESS_FORWARD =		"success";
	private static final String FAILURE_FORWARD =			"failure";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		
   	  Collection hookList = new ArrayList();
   	  Collection hookTypeList = new ArrayList();
   	  
   	  Conector hook;
   	  ConectorForm hooksBean = (ConectorForm)form;
           
   	  String act = hooksBean.getUserAction();
   	  try{
   		ServicioCatalogoTramites oServicio = LocalizadorServicios.getServicioCatalogoTramites();
	     if (act != null){
	   	    if (act.equals("add")){
	   	       hook = new Conector();
	   	       hook.setId(hooksBean.getIdentifier());
	   	       hook.setDescription(hooksBean.getDescription());
	   	       hook.setType(hooksBean.getTypeId());
  	           oServicio.addHook(hook, SesionAdminHelper.obtenerEntidad(request));
  	           hooksBean.setUserAction("load");
	   	  	}else if (act.equals("delete")){
	   	  	   oServicio.deleteHook(hooksBean.getHookId(), SesionAdminHelper.obtenerEntidad(request));
		       hooksBean.setIdentifier("");
		       hooksBean.setDescription("");
		       hooksBean.setTypeId(0);
	   	  	}else if(act.equals("load")){
		        hook = oServicio.getHook(hooksBean.getHookId(), SesionAdminHelper.obtenerEntidad(request));
		        hooksBean.setIdentifier(hook.getId());
		        hooksBean.setDescription(hook.getDescription());
		        hooksBean.setTypeId(new Integer(hook.getType()).intValue());
	   	  	}else if(act.equals("update")){
	   	       hook = new Conector();
	   	       hook.setId(hooksBean.getHookId());
	   	       hook.setDescription(hooksBean.getDescription());
	   	       hook.setType(hooksBean.getTypeId());
	   	       oServicio.updateHook(hook, SesionAdminHelper.obtenerEntidad(request));
  	           hooksBean.setIdentifier(hook.getId());
  	           hooksBean.setUserAction("load");
	   	  	}
	     }
         TipoConector hookType;
         TiposConectores hookTypes = oServicio.getHookTypes(SesionAdminHelper.obtenerEntidad(request));
         for (int i=0; i < hookTypes.count(); i++){
            hookType = (TipoConector)hookTypes.get(i);
            hookTypeList.add(new ConectorForm(hookType.getId(), hookType.getDescription()));
         }
         
         Conectores hooks = oServicio.getHooks(SesionAdminHelper.obtenerEntidad(request));
         for (int i=0; i < hooks.count(); i++) {
             hook = (Conector)hooks.get(i);
             hookList.add(new ConectorForm(hook.getId(), hook.getDescription()));
         }

   	  }catch (Exception e){
   		  request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_CONECTORES);
 		  request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.toString());
 		  request.setAttribute(Defs.REDIRECCION, "hooks.do");
 		  return mapping.findForward(FAILURE_FORWARD);
   	  }
   	  request.setAttribute("hooks", hookList);
   	  request.setAttribute("hookTypes", hookTypeList);
	  return mapping.findForward(SUCCESS_FORWARD);
   }

}
