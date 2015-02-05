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

import es.ieci.tecdoc.isicres.admin.beans.DocumentoTipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.beans.Estados;

public class RemoveDocumentoAsuntoAction extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AsuntoForm asuntoForm = (AsuntoForm) form;
		
		String posDocumento = asuntoForm.getPosDocumento();
		String estadoDocumento = asuntoForm.getEstadoDocumento();
		
		int posicion = 0;
		
		if(StringUtils.isNotEmpty(posDocumento)){
			posicion = Integer.parseInt(posDocumento);
		}
		
		if(posicion > 0){
			List listaDocumentos = (List) getListaFromSession(request, KEY_LISTA_DOCUMENTOS_ASUNTOS);
			
			DocumentoTipoAsuntoBean documento = (DocumentoTipoAsuntoBean) listaDocumentos.get(posicion-1);
			
			documento.setEstado(Integer.parseInt(estadoDocumento));
				
			//Si el documento esta en base de datos pasarlo a la lista de borrados
			if(Estados.isEliminado(documento.getEstado())){
				List listaDocumentosEliminados = (List) getListaFromSession(request, KEY_LISTA_DOCUMENTOS_ELIMINADOS_ASUNTOS);
				listaDocumentosEliminados.add(documento);
				setInSession(request, KEY_LISTA_DOCUMENTOS_ELIMINADOS_ASUNTOS,listaDocumentosEliminados);
			}
				
			listaDocumentos.remove(posicion-1);
			setInSession(request, KEY_LISTA_DOCUMENTOS_ASUNTOS, listaDocumentos);
			asuntoForm.resetDocumento();
			asuntoForm.change();
		}

		return mapping.findForward("success"); 
	}

}
