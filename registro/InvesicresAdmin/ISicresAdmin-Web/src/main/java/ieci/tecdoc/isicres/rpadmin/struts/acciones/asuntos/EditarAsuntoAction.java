package ieci.tecdoc.isicres.rpadmin.struts.acciones.asuntos;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.AsuntoForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.TipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class EditarAsuntoAction extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		setInSession(request, KEY_LISTA_OFICINAS_ASUNTOS, null);
		setInSession(request, KEY_LISTA_DOCUMENTOS_ASUNTOS, null);

		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		String idAsunto = (String)request.getParameter("idAsunto");
		if(idAsunto == null || idAsunto.equals("")) {
			idAsunto = (String)request.getSession(false).getAttribute("idAsunto");
		}

		if( idAsunto != null && !idAsunto.equals("")) {
			request.setAttribute("idAsunto", idAsunto);
			AsuntoForm asuntoForm = (AsuntoForm)form;
			//Si no tenemos oficina en el formulario es la primera vez que se carga, rellenar con datos de negocio
			if(asuntoForm.getId()==null) {
				TipoAsuntoBean asunto = oServicio.obtenerTipoAsunto(new Integer(idAsunto).intValue(), entidad);
				asuntoForm.set(asunto);
				setInSession(request, KEY_LISTA_OFICINAS_ASUNTOS, asunto.getOficinas().getLista());
				setInSession(request, KEY_LISTA_DOCUMENTOS_ASUNTOS, asunto.getDocumentos().getLista());
				setInSession(request, KEY_LISTA_OFICINAS_ELIMINADAS_ASUNTOS, null);
				setInSession(request, KEY_LISTA_DOCUMENTOS_ELIMINADOS_ASUNTOS, null);
			}
		}

		return mapping.findForward("success");
	}
}
