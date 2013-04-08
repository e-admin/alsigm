package auditoria.actions;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import util.StringOwnTokenizer;
import auditoria.ArchivoLogLevels;
import auditoria.AuditoriaConstants;
import auditoria.vos.CritUsuarioVO;

import common.actions.BaseAction;
import common.bi.GestionAuditoriaBI;
import common.bi.GestionControlUsuariosBI;
import common.bi.ServiceRepository;
import common.navigation.KeysClientsInvocations;

/**
 * Action que encapsula todas las acciones relacionadas con la criticidad de los
 * usuarios y grupos de usuarios de la aplicacion.
 */
public class UsuariosAction extends BaseAction {

	/**
	 * Muestra la pagina con los grupos de usuarios existentes.
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
	protected void listadoGruposExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de listadoGruposExecuteLogic");

		// Obtenemos el servicio para el usuario conectado
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionAuditoriaBI service = services.lookupGestionAuditoriaBI();

		removeInTemporalSession(request,
				AuditoriaConstants.LISTA_GRUPOS_WITH_LEVEL_KEY);
		Collection groupsWithLevel = service.getGroupsWithLevels();
		setInTemporalSession(request,
				AuditoriaConstants.LISTA_GRUPOS_WITH_LEVEL_KEY, groupsWithLevel);

		GestionControlUsuariosBI serviceControl = services
				.lookupGestionControlUsuariosBI();
		Collection groups = serviceControl.getGrupos();
		request.setAttribute(AuditoriaConstants.LISTA_GRUPOS_KEY, groups);

		// Obtenemos los niveles de log
		Collection niveles = service.getLogLevels();
		request.setAttribute(AuditoriaConstants.LISTA_NIVELES_KEY, niveles);

		// Guardar el enlace a la página
		saveCurrentInvocation(KeysClientsInvocations.AUDITORIA_USERSLIST,
				request);

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request, mapping.findForward("listado_grupos"));
	}

	/**
	 * Añade un nuevo grupo de los existentes en la aplicacio a los grupos de
	 * auditoria
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
	protected void anadirGrupoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de anadirGrupoExecuteLogic");
		// Obtenemos el servicio para el usuario conectado
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionAuditoriaBI service = services.lookupGestionAuditoriaBI();

		// Obtenemos los grupos a añadir
		String[] grupos = request
				.getParameterValues("detallesseleccionadosanadir");
		// Obtenemos los grupos de auditoria
		List gruposAuditoria = (List) getFromTemporalSession(request,
				AuditoriaConstants.LISTA_GRUPOS_WITH_LEVEL_KEY);
		// Añadimos los nuevos grupos a los de auditoria
		for (int i = 0; i < grupos.length; i++) {
			CritUsuarioVO group = new CritUsuarioVO();

			StringOwnTokenizer st = new StringOwnTokenizer(grupos[i], "|");
			group.setIdAuditado(st.nextToken());
			group.setName(st.nextToken());
			group.setNivel(ArchivoLogLevels.LOGLEVEL_NONE);
			group.setTipoAuditado("" + CritUsuarioVO.TIPO_GRUPO);

			if (!gruposAuditoria.contains(group))
				gruposAuditoria.add(group);
		}

		GestionControlUsuariosBI serviceControl = services
				.lookupGestionControlUsuariosBI();
		Collection groups = serviceControl.getGrupos();
		request.setAttribute(AuditoriaConstants.LISTA_GRUPOS_KEY, groups);

		// Obtenemos los niveles de log
		Collection niveles = service.getLogLevels();
		request.setAttribute(AuditoriaConstants.LISTA_NIVELES_KEY, niveles);

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request, mapping.findForward("listado_grupos"));
	}

	/**
	 * Establece el nivel para los usuarios seleccionados.
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

		// Obtenemos los grupos a establecer el nuevo nivel
		String[] usuariosModificados = request
				.getParameterValues("detallesseleccionadosasignar");
		// Obtenemos el nuevo nivel
		String level = request.getParameter("nivel");

		for (int i = 0; i < usuariosModificados.length; i++) {
			// Establecemos el nivel para la accion
			service.setGroupLogLevel(usuariosModificados[i],
					Integer.parseInt(level));
		}

		// Cargamos los datos y redirigimos a la pagina adecuada
		listadoGruposExecuteLogic(mapping, form, request, response);
	}
}
