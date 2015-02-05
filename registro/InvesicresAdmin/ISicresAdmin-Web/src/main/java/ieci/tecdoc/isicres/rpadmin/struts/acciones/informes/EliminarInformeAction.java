package ieci.tecdoc.isicres.rpadmin.struts.acciones.informes;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.asuntos.EliminarAsuntoAction;

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
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class EliminarInformeAction extends RPAdminWebAction {
	private static final Logger logger = Logger.getLogger(EliminarAsuntoAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward af = null;
		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		String id = (String)request.getParameter("idInforme");
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		try {
			oServicio.eliminarInforme(Integer.parseInt(id), entidad);
			ActionMessages messages = new ActionMessages();
			ActionMessage mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.informes.resultado.eliminadoOK");
			messages.add("Result: ", mesage);
			saveMessages(request, messages);
			af = mapping.findForward("success");
		}
		catch ( ISicresRPAdminException e)
		{
			logger.error("Error en la aplicación", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = null;
			error = new ActionError("errors.detail", e.getMessage());
			errores.add("Error interno", error);
			saveErrors(request, errores);
			af = mapping.findForward("success");
		}
		return af;

	}
}