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

import es.ieci.tecdoc.isicres.admin.beans.DocumentoTipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.beans.Estados;

public class AddDocumentoAsuntoAction extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AsuntoForm asuntoForm = (AsuntoForm) form;
		asuntoForm.toUpperCase(request);		
		
		List listaDocumentos = (List) getListaFromSession(request, KEY_LISTA_DOCUMENTOS_ASUNTOS); 

		String nombreDocumento = asuntoForm.getNombreDocumento();
		
		if(StringUtils.isNotBlank(asuntoForm.getNombreDocumento())){
			//Comprobar que el documento no esta duplicado
			boolean duplicado = false;
			
			for (Iterator iterator = listaDocumentos.iterator(); iterator.hasNext();) {
				DocumentoTipoAsuntoBean documento = (DocumentoTipoAsuntoBean) iterator.next();
				
				if(documento != null && nombreDocumento.equalsIgnoreCase(documento.getDescription())){
					duplicado = true;
					break;
				}
			}
			
			if(!duplicado){
				DocumentoTipoAsuntoBean documento = new DocumentoTipoAsuntoBean();
				
				documento.setDescription(asuntoForm.getNombreDocumento());
				documento.setEstado(Estados.NUEVO);
		
				listaDocumentos.add(documento);
				setInSession(request, KEY_LISTA_DOCUMENTOS_ASUNTOS, listaDocumentos);
				asuntoForm.resetDocumento();
				asuntoForm.change();
			}
			else{
				ActionErrors errores = new ActionErrors();
				ActionError error = null;
				error = new ActionError("ieci.tecdoc.sgm.rpadmin.elemento.documento.duplicado", nombreDocumento);
				errores.add("Error interno", error);
				saveErrors(request, errores);
			}
		}
		
		return mapping.findForward("success"); 
	}

}
