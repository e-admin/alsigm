package ieci.tecdoc.isicres.rpadmin.struts.acciones.oficinas;

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
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class EliminarOficinaAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(EliminarOficinaAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward af = null;
		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		String id = (String)request.getParameter("idOficina");

		try {
			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());
			if (isLdapMethod(entidad.getIdentificador())){
				oServicio.eliminarOficinaLDAP(Integer.parseInt(id), entidad);
			}
			else{
				oServicio.eliminarOficina(Integer.parseInt(id), entidad);
			}

			ActionMessages messages = new ActionMessages();
			ActionMessage mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.oficinas.resultado.eliminadoOK");
			messages.add("Result: ", mesage);
			saveMessages(request, messages);
			af = mapping.findForward("success");
		}
		catch ( ISicresRPAdminException e)
		{

			logger.error("Error en la aplicación", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = null;
			if( e.getErrorCode() == ISicresRPAdminException.OFICINA_CON_USUARIOS) {
				error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensage.oficinas.con.usuarios.eliminar");
			}
			else if(e.getErrorCode() == ISicresRPAdminException.OFICINA_CON_REFERENCIAS) {
				error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensage.oficinas.referenciada.eliminar");
			}
			else
			{
				error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			}
			errores.add("Error interno", error);
			saveErrors(request, errores);
			af = mapping.findForward("success");
		}
		return af;
	}

}
