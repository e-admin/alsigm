package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.audit.IspacAuditConstants;
import ieci.tdw.ispac.audit.business.IspacAuditoriaManager;
import ieci.tdw.ispac.audit.business.manager.impl.IspacAuditoriaManagerImpl;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventEntidadConsultaVO;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispacmgr.action.form.SearchForm;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacmgr.mgr.SchemeMgr;
import ieci.tdw.ispac.ispacmgr.mgr.SpacMgr;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IScheme;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.IWorklist;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;
import ieci.tdw.ispac.ispacweb.api.impl.states.ExpedientState;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowExpedientAction extends BaseAction {

	IspacAuditoriaManager auditoriaManager = new IspacAuditoriaManagerImpl();

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, SessionAPI session)
			throws Exception {

		ClientContext cct = session.getClientContext();
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IInvesflowAPI invesFlowAPI = cct.getAPI();
		request.getSession().removeAttribute("docEntidad");
		// ----
		// BPM
		// ----
		// Se adquiere el expediente
		IBPMAPI bpmAPI = null;
		boolean commit = true;
		try {
			bpmAPI = invesFlowAPI.getBPMAPI();
			// Iniciamos la sesion con el BPM
			bpmAPI.initBPMSession();
			request.getSession().setAttribute("stageId",
					request.getParameter(ManagerState.PARAM_STAGEID));
			IStage stage = invesFlowAPI.getStage(Integer.parseInt(request
					.getParameter(ManagerState.PARAM_STAGEID)));
			// Se invoca al BPM para adquirir la fase del expediente, si es
			// necesario
			if (!bpmAPI.acquireStage(stage.getString("ID_FASE_BPM"), cct.getRespId())) {
				// Si no se puede adquirir la fase se da la opcion de capturarla
				// de forma explícita
				request.setAttribute("menus", MenuFactory.getSingleMenu(cct, getResources(request)));
				request.setAttribute(ManagerState.PARAM_STAGEID, "" + stage.getKeyInt());
				return mapping.findForward("capture");
			}

		} catch (ISPACNullObject inoe) {

			ISPACInfo info = new ISPACInfo("exception.expedients.noStage");

			// Redireccionar a la página principal con el mensaje de aviso
			request.setAttribute(Globals.MESSAGE_KEY, info);
			request.setAttribute("refresh", "/showProcedureList.do");
			return mapping.findForward("refresh");
		} catch (ISPACException e) {
			commit = false;
			throw e;
		} finally {
			if (bpmAPI != null)
				bpmAPI.closeBPMSession(commit);
		}

		// Se cambia el estado de tramitación
		IState state = null;
		Map params = request.getParameterMap();
		try {
			state = managerAPI.enterState(getStateticket(request), ManagerState.EXPEDIENT, params);
		} catch (ISPACNullObject e) {

			// No se ha recuperado la fase porque el expediente ha cambiado de
			// fase
			// o se ha introducido un id de fase que no existe
			/*
			 * EntityForm entityForm = (EntityForm) form; String numexp =
			 * entityForm.getProperty("SPAC_EXPEDIENTES:NUMEXP"); if
			 * (StringUtils.isEmpty(numexp)) { numexp =
			 * entityForm.getProperty("SPAC_DT_TRAMITES:NUMEXP"); }
			 * 
			 * ISPACInfo info = null; if (!StringUtils.isEmpty(numexp)) { info =
			 * new ISPACInfo("exception.expedients.noStage.numexp", new String[]
			 * {numexp}); } else { info = new
			 * ISPACInfo("exception.expedients.noStage"); }
			 */
			ISPACInfo info = new ISPACInfo("exception.expedients.noStage.numexp",
					new String[] { managerAPI.currentState(getStateticket(request)).getNumexp() });

			// Redireccionar a la página principal con el mensaje de aviso
			request.setAttribute(Globals.MESSAGE_KEY, info);
			request.setAttribute("refresh", "/showProcedureList.do");
			return mapping.findForward("refresh");
		}

		IProcess process = invesFlowAPI.getProcess(state.getNumexp());
		if (process.getInt("ESTADO") == TXConstants.STATUS_DELETED) {
			state.setReadonly(true);
			state.setReadonlyReason(StateContext.READONLYREASON_DELETED_EXPEDIENT);
			request.setAttribute(ActionsConstants.READONLYSTATE, "" + state.getReadonlyReason());
		}

		// Cargamos los datos del esquema
		IScheme scheme = SchemeMgr.loadScheme(mapping, request, session, state);

		// Cargamos el expediente
		// SpacMgr.loadExpedient(session, state, request);

		if (!state.getReadonly()) {

			// Eliminamos si existe el atributo que indica el usuario que tiene
			// bloqueado el expediente
			request.getSession(false).removeAttribute("userLock");
		} else {
			String username = ((ExpedientState) state).getLockedStageUser(cct, state.getStageId());
			// Insertamos el atributo que indica el usuario que tiene bloqueado
			// el expediente
			request.getSession(false).setAttribute("userLock", username);
		}

		// enviamos un mapa con parámetros para el enlace de los hitos y la otra
		// vista
		Map linkParams = new HashMap();
		linkParams.put("stageId", String.valueOf(state.getStageId()));
		request.setAttribute("Params", linkParams);

		// ////////////////////////////////////////////////////////////////////
		// Formulario asociado a la acción
		EntityForm defaultForm = (EntityForm) form;
		EntityApp entityapp = null;

		// Visualiza los datos de la entidad
		// Si hay errores no se recargan los datos de la entidad
		// manteniéndose los datos introducidos que generan los errores
		if ((request.getAttribute(Globals.ERROR_KEY) == null)
				&& (request.getSession().getAttribute("infoAlert") == null)) {

			String path = getRealPath("");

			try {

				// Obtener la aplicación que gestiona la entidad
				entityapp = scheme.getDefaultEntityApp(state, path, false);
			} catch (ISPACNullObject e) {

				// Si no existe ningun registro creado para la entidad indicada
				// en el estado
				// buscamos el EntityApp pasando un registro vacio (caso de Alta
				// de entidad)
				entityapp = scheme.getEntityApp(state, state.getEntityId(),
						ISPACEntities.ENTITY_NULLREGKEYID, path, 0, false);
			}

			// Limpiar el formulario
			defaultForm.reset();

			// Establecer los datos
			defaultForm.setEntityApp(entityapp, cct.getLocale());
		}

		// Permite modificar los datos del formulario
		defaultForm.setReadonly(Boolean.toString(state.getReadonly()));
		defaultForm.setReadonlyReason("" + state.getReadonlyReason());
		if (state.getReadonly())
			request.setAttribute(ActionsConstants.READONLYSTATE, "" + state.getReadonlyReason());

		// Se actualiza el estado de tramitación
		storeStateticket(state, response);

		IWorklist managerwl = managerAPI.getWorklistAPI();

		// Responsabilidades del usuario conectado
		String resp = managerwl.getRespString(state);

		// Mandamos los parámetros entity y regentity
		request.setAttribute("entityid", Integer.toString(state.getEntityId()));
		request.setAttribute("entityregid", Integer.toString(state.getEntityRegId()));
		request.setAttribute("application", defaultForm.getEntityApp().getURL());

		// Pasamos el atributo del pcdId para la ayuda en linea
		request.setAttribute("pcdid", Integer.toString(state.getPcdId()));

		// Modificación de los documentos asociados a la fase activa
		request.setAttribute("stagePcdId", Integer.toString(state.getStagePcdId()));

		// Introducimos como atributo el action utilizado como enlace para todas
		// las entidades
		ActionForward action = mapping.findForward("showexp");
		String urlExp = action.getPath();
		request.setAttribute("urlExp", urlExp);

		// Y se mantiene la ordenación de las listas
		String displayTagOrderParams = getDisplayTagOrderParams(request);
		if (!StringUtils.isEmpty(displayTagOrderParams)) {
			urlExp = urlExp + "?" + displayTagOrderParams;
		}
		request.setAttribute("urlExpDisplayTagOrderParams", urlExp);
		request.setAttribute("displayTagOrderParams", displayTagOrderParams);

		// Si se recibe el parametro 'form' con el valor 'single'
		if (request.getParameter("form") != null && request.getParameter("form").equals("single")) {
			return mapping.findForward("singleSuccess");
		}

		// Cargamos el contexto de la cabecera (miga de pan)
		SchemeMgr.loadContextHeader(state, request, getResources(request), session);

		// Ahora el formulario de búsqueda está en sesión y se mantienen los
		// parámetros de la última búsqueda realizada
		String returnToSearch = null;
		SearchForm searchForm = (SearchForm) request.getSession().getAttribute(
				ActionsConstants.FORM_SEARCH_RESULTS);
		if (searchForm != null) {

			returnToSearch = searchForm.getDisplayTagParams();
		}

		// Menús
		request.setAttribute("menus",
				MenuFactory.getExpMenu(cct, state, getResources(request), returnToSearch, resp));

		// Cargamos enlaces para los expedientes relacionados
		SpacMgr.loadRelatedExpedient(session, request, state.getNumexp(), SpacMgr.ALL_EXPEDIENTS);

		// Cargamos las aplicaciones de gestion asociadas
		SpacMgr.loadAppGestion(session, state, request);

		// TODO: Auditar la consulta de la entidad

		logger.info("Auditando la consulta de la entidad");
		String numExpediente = state.getNumexp();
		
		auditConsultaEntidad(request, cct, entityapp,numExpediente);

		return mapping.findForward("success");
	}

	/**
	 * @param request
	 * @param cct
	 * @param entityapp
	 * @param evento
	 * @throws ISPACException
	 */
	private void auditConsultaEntidad(HttpServletRequest request, ClientContext cct,
			EntityApp entityapp, String numExpediente) throws ISPACException {
		if (entityapp != null) {
			IspacAuditEventEntidadConsultaVO evento = new IspacAuditEventEntidadConsultaVO();
			AuditContext auditContext = AuditContextHolder.getAuditContext();

			evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
			evento.setAppId(IspacAuditConstants.getAppId());

			evento.setIdUser(cct.getUser().getUID());
			evento.setUser(cct.getUser().getName());

			// TODO: Al avanzar la fase entityapp es nulo!!!
			evento.setEntidadAppName(entityapp.getAppName());
			evento.setEntidadAppId(String.valueOf(entityapp.getAppId()));

			// TODO: ¿Añadir también el número de expediente del trámite?
			evento.setNumExpediente(numExpediente);
			int id = entityapp.getEntityRegId();
			evento.setId(Integer.toString(id));

			evento.setFecha(new Date());

			evento.setUserHostName(request.getRemoteHost());
			evento.setUserIp(request.getRemoteAddr());
			if (id!=-1){
				auditoriaManager.audit(evento);
			}
		}
	}

}