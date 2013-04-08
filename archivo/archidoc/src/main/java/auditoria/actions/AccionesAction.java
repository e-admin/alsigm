package auditoria.actions;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import se.usuarios.ServiceClient;
import util.StringOwnTokenizer;
import auditoria.AuditoriaConstants;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionAuditoriaBI;
import common.bi.ServiceRepository;
import common.navigation.KeysClientsInvocations;

/**
 * Action que encapsula todas las acciones relacionadas con la criticidad de las
 * acciones de los modulos de la aplicacion.
 */
public class AccionesAction extends BaseAction {

	/**
	 * Muestra la pagina con los modulos existentes y el display con las
	 * acciones para ese modulo.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void listadoModulosExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de listadoModulosExecuteLogic");

		// Obtenemos el servicio para el usuario conectado
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionAuditoriaBI service = services.lookupGestionAuditoriaBI();
		Collection modules = service.getModules();

		request.setAttribute(AuditoriaConstants.LISTA_MODULOS_KEY, modules);

		// Obtenemos el identificador del modulo
		String module = request.getParameter(Constants.MODULE);
		if (module != null && module.trim().length() > 0) {
			Collection actions = service.getActionsWithLevels(Integer
					.parseInt(module));

			request.setAttribute(AuditoriaConstants.LISTA_ACCIONES_KEY, actions);
			request.setAttribute(AuditoriaConstants.SHOW_ACTIONS, new Boolean(
					true));
			setInTemporalSession(request, AuditoriaConstants.MODULE_KEY, module);
		}

		// Obtenemos los niveles de log
		Collection niveles = service.getLogLevels();
		request.setAttribute(AuditoriaConstants.LISTA_NIVELES_KEY, niveles);

		// Guardar el enlace a la página
		saveCurrentInvocation(KeysClientsInvocations.AUDITORIA_ACTIONLIST,
				request);

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward(Constants.FORWARD_LISTADO_ACCIONES));
	}

	/**
	 * Establece el nivel de criticidad para las acciones seleccionadas de un
	 * módulo.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void establecerNivelExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de establecerNivelExecuteLogic");
		// Obtenemos el servicio para el usuario conectado
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionAuditoriaBI service = services.lookupGestionAuditoriaBI();

		// Obtenemos el identificador del modulo
		String module = (String) getFromTemporalSession(request,
				AuditoriaConstants.MODULE_KEY);
		// Obtenemos las acciones modificadas
		String accionesModificadas = request.getParameter(Constants.ACCIONES);
		Set accionesAModificar = new HashSet();
		if (accionesModificadas != null
				&& accionesModificadas.trim().length() > 0) {
			StringOwnTokenizer st = new StringOwnTokenizer(accionesModificadas,
					"|");

			while (st.hasMoreTokens()) {
				accionesAModificar.add(st.nextToken());
			}
		}

		Iterator it = accionesAModificar.iterator();
		while (it.hasNext()) {
			String id = (String) it.next();

			// Obtenemos el nuevo nivel para la accion
			String level = request.getParameter(Constants.ACCION_ + id);
			// Establecemos el nivel para la accion
			service.setActionLogLevel(Integer.parseInt(module),
					Integer.parseInt(id), Integer.parseInt(level));
		}

		// Obtenemos los módulos existenetes
		Collection modules = service.getModules();
		request.setAttribute(AuditoriaConstants.LISTA_MODULOS_KEY, modules);

		// Obtenemos las acciones para el módulo
		if (module != null) {
			Collection actions = service.getActionsWithLevels(Integer
					.parseInt(module));

			request.setAttribute(AuditoriaConstants.LISTA_ACCIONES_KEY, actions);
			request.setAttribute(AuditoriaConstants.SHOW_ACTIONS, new Boolean(
					true));
			setInTemporalSession(request, AuditoriaConstants.MODULE_KEY, module);
			((DynaActionForm) form).set(Constants.MODULE, module);
		}
		// Obtenemos los niveles de log
		Collection niveles = service.getLogLevels();
		request.setAttribute(AuditoriaConstants.LISTA_NIVELES_KEY, niveles);

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward(Constants.FORWARD_LISTADO_ACCIONES));
	}
}
