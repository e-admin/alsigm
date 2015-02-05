package ieci.tecdoc.isicres.rpadmin.struts.acciones.asuntos;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.AsuntoForm;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.isicres.admin.beans.Estados;
import es.ieci.tecdoc.isicres.admin.beans.OficinaTipoAsuntoBean;

public class RemoveOficinaAsuntoAction extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		AsuntoForm asuntoForm = (AsuntoForm) form;
		
		String posOficina = asuntoForm.getPosOficina();
		String estadoOficina = asuntoForm.getEstadoOficina();
		
		int posicion = 0;
		
		if(StringUtils.isNotEmpty(posOficina)){
			posicion = Integer.parseInt(posOficina);
		}
		
		if(posicion > 0){
			List listaOficinas = (List) getListaFromSession(request, KEY_LISTA_OFICINAS_ASUNTOS);
			
			OficinaTipoAsuntoBean oficina = (OficinaTipoAsuntoBean) listaOficinas.get(posicion-1);
			
			oficina.setEstado(Integer.parseInt(estadoOficina));
				
			//Si el documento esta en base de datos pasarlo a la lista de borrados
			if(Estados.isEliminado(oficina.getEstado())){
				List listaOficinasEliminados = (List) getListaFromSession(request, KEY_LISTA_OFICINAS_ELIMINADAS_ASUNTOS);
				listaOficinasEliminados.add(oficina);
				setInSession(request, KEY_LISTA_OFICINAS_ELIMINADAS_ASUNTOS,listaOficinasEliminados);
			}
				
			listaOficinas.remove(posicion-1);
			setInSession(request, KEY_LISTA_OFICINAS_ASUNTOS, listaOficinas);
			asuntoForm.resetOficina();
			asuntoForm.change();
		}

		return mapping.findForward("success"); 
	}
}
