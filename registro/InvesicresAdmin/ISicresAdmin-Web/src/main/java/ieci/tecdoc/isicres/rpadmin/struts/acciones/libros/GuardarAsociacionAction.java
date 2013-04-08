package ieci.tecdoc.isicres.rpadmin.struts.acciones.libros;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.PermisosSicresForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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
import es.ieci.tecdoc.isicres.admin.beans.Libro;
import es.ieci.tecdoc.isicres.admin.beans.PermisosSicres;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class GuardarAsociacionAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(GuardarAsociacionAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {


		ActionForward af = null;

		try {

			ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			PermisosSicresForm permisosForm = (PermisosSicresForm)form;
			PermisosSicres permisosSicres = new PermisosSicres();
			BeanUtils.copyProperties(permisosSicres, permisosForm);

			if(logger.isDebugEnabled()) {
				logger.debug("La asociación modificada es: " + permisosSicres.toString());
			}

			oServicio.modificarPermisos(permisosSicres, entidad);
			String idLibro = (String)request.getParameter("idLibro");
			String tipoLibro = (String)request.getParameter("tipoLibro");

			if( tipoLibro != null && Integer.parseInt(tipoLibro) == Libro.LIBRO_ENTRADA) {
				request.setAttribute("idLibroE", idLibro);
			} else {
				request.setAttribute("idLibroS", idLibro);
			}

			ActionMessages messages = new ActionMessages();
			ActionMessage mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.libros.resultado.editadoOK");
			messages.add("Result: ", mesage);
			saveMessages(request, messages);
			af = mapping.findForward("success");

		} catch ( Exception e) {

			logger.error("Error en la aplicación", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			errores.add("Error interno", error);
			saveErrors(request, errores);
			af = mapping.findForward("error");
		}

		return af;
	}

}
