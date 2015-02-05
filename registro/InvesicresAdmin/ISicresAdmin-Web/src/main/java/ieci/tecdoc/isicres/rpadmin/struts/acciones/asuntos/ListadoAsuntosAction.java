package ieci.tecdoc.isicres.rpadmin.struts.acciones.asuntos;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.TiposAsuntoBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class ListadoAsuntosAction extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		TiposAsuntoBean tiposAsunto = oServicio.obtenerTiposAsunto(entidad);
		request.setAttribute("tiposAsunto", tiposAsunto);

		// Flg que establece si venimos de la pagina de filtros de los libros
		String fila = (String)request.getParameter("fila");

		if( fila != null && !fila.equals("")) {
			request.setAttribute("size", String.valueOf(tiposAsunto.count()));
			request.setAttribute("fila", fila);
			return mapping.findForward("asuntos");
		} else {
			setInSession(request, KEY_LISTA_OFICINAS_ASUNTOS, null);
			setInSession(request, KEY_LISTA_DOCUMENTOS_ASUNTOS, null);
			setInSession(request, KEY_LISTA_OFICINAS_ELIMINADAS_ASUNTOS, null);
			setInSession(request, KEY_LISTA_DOCUMENTOS_ELIMINADOS_ASUNTOS, null);
			return mapping.findForward("success");
		}
	}
}
