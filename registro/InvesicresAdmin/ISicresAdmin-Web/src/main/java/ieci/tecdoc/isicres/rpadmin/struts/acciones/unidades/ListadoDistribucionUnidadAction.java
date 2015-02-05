package ieci.tecdoc.isicres.rpadmin.struts.acciones.unidades;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Distribuciones;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.OrganizacionBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class ListadoDistribucionUnidadAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(ListadoDistribucionUnidadAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		String idDistribucion = null;
		String accion = (String)request.getAttribute("accion");
		//Se obtiene la entidad
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		if(accion == null || accion.equals("")) {
			idDistribucion = request.getParameter("idDistribucion");
		}

		if( idDistribucion == null || idDistribucion.equals("")) {
			idDistribucion = (String)request.getSession(false).getAttribute("idDistribucion");
		}
		Distribuciones distribuciones = oServicio.obtenerDistribuciones(Integer.parseInt(idDistribucion), entidad);
		request.getSession(false).setAttribute("idDistribucion", idDistribucion);

		request.setAttribute("distribuciones", distribuciones);

		OrganizacionBean organizacion = oServicio.obtenerOrganizacion(Integer.parseInt(idDistribucion), entidad);
		request.setAttribute("nombreOrganizacion", organizacion.getNombre());

		if (isLdapMethod(entidad.getIdentificador())){
			request.setAttribute("isLdapMethod", Boolean.TRUE.toString());
		} else {
			request.setAttribute("isLdapMethod", Boolean.FALSE.toString());
		}

		ActionErrors errores = request.getAttribute("errors") != null ? (ActionErrors)request.getAttribute("errors"): null;
		if(errores != null && !errores.isEmpty()) {
			saveErrors(request, errores);
		}

		ActionMessages messages = request.getAttribute("messages") != null ? (ActionMessages)request.getAttribute("messages"): null;
		if(messages != null && !messages.isEmpty()) {
			saveMessages(request, messages);
		}

		return mapping.findForward("success");
	}

}
