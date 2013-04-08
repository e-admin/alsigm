package ieci.tecdoc.sgm.registro.action;

import ieci.tecdoc.sgm.registro.utils.Defs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ObtenerJustificanteAction extends RegistroWebAction {
	  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) {
	    
	    try{
		    return mapping.findForward("success");	    	
	    }catch(Exception e){
	    	request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_OBTENER_JUSTIFICANTE);
	    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.toString());
	    	return mapping.findForward("failure");
   		}		    

   	}
}
