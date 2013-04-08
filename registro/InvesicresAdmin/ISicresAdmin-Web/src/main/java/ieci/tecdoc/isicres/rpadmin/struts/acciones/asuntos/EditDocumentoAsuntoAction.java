package ieci.tecdoc.isicres.rpadmin.struts.acciones.asuntos;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.AsuntoForm;

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

public class EditDocumentoAsuntoAction extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AsuntoForm asuntoForm = (AsuntoForm) form;
		
		String posDocumento = asuntoForm.getPosDocumento();
		String nombreDocumento = asuntoForm.getNombreDocumento();
		String estadoDocumento = asuntoForm.getEstadoDocumento();
		
		int posicion = 0;
		
		if(StringUtils.isNotBlank(posDocumento)){
			posicion = Integer.parseInt(posDocumento);
		}

		if(posicion > 0){
			posicion = posicion -1;

			List listaDocumentos = (List) getListaFromSession(request, KEY_LISTA_DOCUMENTOS_ASUNTOS);
			
			boolean duplicado = false;
			
			for(int i=0;i<listaDocumentos.size();i++){
				if(i != posicion){
					DocumentoTipoAsuntoBean documento = (DocumentoTipoAsuntoBean) listaDocumentos.get(i);
					if(documento != null && nombreDocumento.equalsIgnoreCase(documento.getDescription())){
						duplicado = true;
						break;
					}
				}
			}
			
			if(!duplicado){
				DocumentoTipoAsuntoBean documento = (DocumentoTipoAsuntoBean) listaDocumentos.get(posicion);
				
				if(documento != null){
					documento.setEstado(Integer.parseInt(estadoDocumento));
					documento.setDescription(nombreDocumento);
				}
	
				
				asuntoForm.change();
				setInSession(request, KEY_LISTA_DOCUMENTOS_ASUNTOS, listaDocumentos);
			}
			else{
				ActionErrors errores = new ActionErrors();
				ActionError error = null;
				error = new ActionError("ieci.tecdoc.sgm.rpadmin.elemento.documento.duplicado", nombreDocumento);
				errores.add("Error interno", error);
				saveErrors(request, errores);
			}
	
		}
		asuntoForm.resetDocumento();
		return mapping.findForward("success"); 
	}

}
