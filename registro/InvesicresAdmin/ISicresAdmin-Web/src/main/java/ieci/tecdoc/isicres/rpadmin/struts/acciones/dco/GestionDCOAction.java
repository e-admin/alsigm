package ieci.tecdoc.isicres.rpadmin.struts.acciones.dco;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class GestionDCOAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(GestionDCOAction.class);

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String actionForward;

			// obtenemos la informacion de sesion para saber si el usuario es superusuario
			boolean superUser = (Boolean) request.getSession().getAttribute(
					"isSuperuser");

			if (superUser) {
				//si es superusuario puede realizar la operativa
				actionForward = operarativaDCO(mapping, request);
			} else {
				logger.error("Error al intentar inicializar/actualizar DCO el usuario no tiene permisos");
				//El usuario no puede continuar con la operativa
				ActionErrors errores = new ActionErrors();
				ActionError error = new ActionError(
						"ieci.tecdoc.rpadmin.dco.error.user.not.permissions");
				errores.add("Error interno", error);
				saveErrors(request, errores);
				actionForward = "error";
			}
			return mapping.findForward(actionForward);

		} catch (Exception e) {
			logger.error("Error en la aplicación", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = new ActionError(
					"ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			errores.add("Error interno", error);
			saveErrors(request, errores);
			return mapping.findForward("error");
		}
	}

	/**
	 * Método que ejecuta la operativa correspondiente a INICILIAZAR o ACTUALIZAR DCO
	 * @param mapping
	 * @param request
	 * @return
	 */
	private String operarativaDCO(ActionMapping mapping,
			HttpServletRequest request) {
		ActionMessages messages = new ActionMessages();

		//Obtenemos la operativa a seguir
		String operativa = (String) request.getParameter("accion");

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		if(StringUtils.equalsIgnoreCase(operativa, "INICIALIZAR")){
			if(logger.isDebugEnabled()){
				logger.debug("Ejecutamos la operativa de inicializar DCO");
			}
			//Inicializamos DCO
			oServicio.inicializarDCO();
			ActionMessage mesage = new ActionMessage("ieci.tecdoc.rpadmin.dco.msg.action.init.ok");
			messages.add("Result: ", mesage);
			saveMessages(request, messages);
		}else{
			if(StringUtils.equalsIgnoreCase(operativa, "ACTUALIZAR")){
				if(logger.isDebugEnabled()){
					logger.debug("Ejecutamos la operativa de actualizar DCO");
				}
				//Actualizamos DCO
				oServicio.actualizarDCO();
				ActionMessage mesage = new ActionMessage("ieci.tecdoc.rpadmin.dco.msg.action.update.ok");
				messages.add("Result: ", mesage);
				saveMessages(request, messages);
			}else{
				//La operativa indicada no es correcta
				StringBuffer sb = new StringBuffer();
				sb.append("La operativa que se ha indicado [").append(operativa).append("] no es válida");
				logger.error(sb.toString());

				ActionErrors errores = new ActionErrors();
				ActionError error = new ActionError("ieci.tecdoc.rpadmin.dco.error.operative.not.valid");
				errores.add("Error interno", error);
				saveErrors(request, errores);
				return "error";
			}
		}
		return "success";
	}
}
