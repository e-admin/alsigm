package ieci.tecdoc.sgm.registro.action;

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.catalogo.Tramite;
import ieci.tecdoc.sgm.core.services.catalogo.Tramites;
import ieci.tecdoc.sgm.registro.form.SolicitudesRegistroForm;
import ieci.tecdoc.sgm.registro.utils.Defs;
import ieci.tecdoc.sgm.registro.utils.Misc;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que obtiene el listado de solicitudes que puede realizar
 * un usuario del sistema
 * @author José Antonio Nogales
 *
 */
public class SolicitudesRegistroAction extends RegistroWebAction{

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		//ActionErrors errors = null;
		Collection tramites = new ArrayList();
		
		String clase = request.getParameter("clase");
 		request.getSession().setAttribute("clase", clase);
		
		try{
			ServicioCatalogoTramites oServicio = LocalizadorServicios.getServicioCatalogoTramites();
			Tramites procs = oServicio.getProcedures(Misc.obtenerEntidad(request));
			for(int i=0; i<procs.count(); i++){
				Tramite proc = (Tramite)procs.get(i);
				tramites.add(new SolicitudesRegistroForm(proc.getId(), proc.getDescription()));
			}
	   	}catch(Exception e){
	   		request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_SOLICITUDES_REGISTRO);
	    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.toString());
	   		return mapping.findForward("failure");
	   	}
	   	request.setAttribute(Defs.TRAMITES, tramites);
	   	return mapping.findForward("success");
	}
}
