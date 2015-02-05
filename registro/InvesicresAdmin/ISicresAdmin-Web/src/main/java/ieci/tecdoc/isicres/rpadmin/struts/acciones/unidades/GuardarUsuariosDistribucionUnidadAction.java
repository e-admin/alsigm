package ieci.tecdoc.isicres.rpadmin.struts.acciones.unidades;


import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class GuardarUsuariosDistribucionUnidadAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(GuardarUsuariosDistribucionUnidadAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward af = null;

		try {

			ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

			//Se obtiene la entidad
			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			String idOrg = (String)request.getSession(false).getAttribute("idDistribucion");

			String id = (String)request.getParameter("id");
			String idTipo = (String)request.getParameter("idTipo");
			int[] ids = new int[1];
			ids[0] = Integer.parseInt(id);

			oServicio.crearDistribuciones(Integer.parseInt(idOrg), Integer.parseInt(idTipo), ids, entidad);

			ActionMessages messages = new ActionMessages();
			ActionMessage mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.unidades.distribucion.usuarios.guardadoOK");
			messages.add("Result: ", mesage);
			request.setAttribute("messages", messages);

	        af = mapping.findForward("success");

		} catch (Exception e) {

			logger.error("Error en la aplicacion", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			errores.add("Error interno", error);
			request.setAttribute("errors", errores);

			af = mapping.findForward("success");
		}

		return af;
	}


}
