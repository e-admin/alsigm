package ieci.tecdoc.isicres.rpadmin.struts.acciones.libros;

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
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaImpl;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminOficinaManager;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class ActualizarNumeracionOficinasAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(GuardarNumeracionLibroAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		String idLibro = (String)request.getParameter("idLibro");
		String id = (String)request.getParameter("deptId");
		try {
			int idBook = Integer.parseInt(idLibro);
			int idOficina = Integer.parseInt(id);
			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			SicresOficinaImpl oficina = ISicresRPAdminOficinaManager.getOficinaById(idOficina, entidad.getIdentificador());

			oServicio.actualizarNumeracionOficinaAsociadaALibro(idBook, oficina.getId(), entidad);

			ActionMessages messages = new ActionMessages();
			ActionMessage mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.libros.oficinas.numeracion.actualizacionOk");
			messages.add("Result: ", mesage);
			saveMessages(request, messages);
		} catch (ISicresRPAdminException e) {
			logger.error("Error en la aplicación", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			errores.add("Error interno", error);
			saveErrors(request, errores);
		}
		ActionForward af = mapping.findForward("success");
		ActionForward edicion = new ActionForward();
		edicion.setName(af.getName());
		edicion.setPath(af.getPath()+"?idLibro="+idLibro);
		return edicion;
	}
}