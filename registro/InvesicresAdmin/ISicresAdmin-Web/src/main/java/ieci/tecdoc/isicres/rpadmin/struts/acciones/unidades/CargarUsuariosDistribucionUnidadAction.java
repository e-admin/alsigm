package ieci.tecdoc.isicres.rpadmin.struts.acciones.unidades;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class CargarUsuariosDistribucionUnidadAction extends RPAdminWebAction {


	private static final Logger logger = Logger.getLogger(CargarUsuariosDistribucionUnidadAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		//Se obtiene la entidad
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		String id = request.getParameter("id");
		String tipo = request.getParameter("tipo");
		String accion = request.getParameter("accion");
		OptionsBean options = oServicio.obtenerUsuarios(Integer.parseInt(id), Integer.parseInt(tipo), entidad);

		request.setAttribute("usuarios", options);
		request.setAttribute("tipo", tipo);
		request.setAttribute("id", id);
		request.setAttribute("size", String.valueOf(options.count()));

		if( accion != null && accion.equalsIgnoreCase("nuevoUsuario")) {
			return mapping.findForward("nuevoUsuario");
		} else {
			return mapping.findForward("usuarios");
		}

	}

}
