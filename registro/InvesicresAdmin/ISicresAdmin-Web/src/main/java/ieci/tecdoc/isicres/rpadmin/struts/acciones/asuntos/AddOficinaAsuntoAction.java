package ieci.tecdoc.isicres.rpadmin.struts.acciones.asuntos;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.AsuntoForm;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.isicres.admin.beans.Estados;
import es.ieci.tecdoc.isicres.admin.beans.OficinaTipoAsuntoBean;

public class AddOficinaAsuntoAction extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		AsuntoForm asuntoForm = (AsuntoForm) form;
		List listaOficinas = (List) getListaFromSession(request, KEY_LISTA_OFICINAS_ASUNTOS); 

		String idOficina = asuntoForm.getIdOficina();
		
		if(StringUtils.isNotBlank(idOficina)){
			//Comprobar que el documento no esta duplicado
			boolean duplicado = false;
			
			for (Iterator iterator = listaOficinas.iterator(); iterator.hasNext();) {
				OficinaTipoAsuntoBean oficina = (OficinaTipoAsuntoBean) iterator.next();
				
				if(oficina != null && idOficina.equals(new Integer(oficina.getIdOficina()).toString())){
					duplicado = true;
					break;
				}
			}
			
			if(!duplicado){
				OficinaTipoAsuntoBean oficina = new OficinaTipoAsuntoBean();
				
				oficina.setCodigoOficina(asuntoForm.getCodigoOficina());
				oficina.setIdOficina(Integer.parseInt(asuntoForm.getIdOficina()));
				oficina.setNombreOficina(asuntoForm.getNombreOficina());
				oficina.setEstado(Estados.NUEVO);
		
				listaOficinas.add(oficina);
				setInSession(request, KEY_LISTA_OFICINAS_ASUNTOS, listaOficinas);
				asuntoForm.resetOficina();
				asuntoForm.change();
			}
			else{
				ActionErrors errores = new ActionErrors();
				ActionError error = null;
				error = new ActionError("ieci.tecdoc.sgm.rpadmin.elemento.oficina.duplicada", asuntoForm.getNombreOficina());
				errores.add("Error interno", error);
				saveErrors(request, errores);

			}
		}
		
		return mapping.findForward("success"); 
		
	}

}
