package com.tsol.modulos.buscador.actions;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.directory.DirectoryConnectorFactory;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryConnector;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.utils.LocaleHelper;
import ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.core.web.locale.LocaleFilterHelper;
import ieci.tecdoc.sgm.sesiones.backoffice.ws.client.Sesion;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SearchAction extends Action {

	private static final String APLICACION = "BUSCADOR_DOCS";

	private static final Logger LOGGER = Logger.getLogger(SearchAction.class);


	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String entidad = request.getParameter("entidad");
		String usuario = null;

		if (StringUtils.isBlank(entidad)) {

			// Información de la sesión del usuario
			Sesion sesionBO = AutenticacionBackOffice.obtenerDatos(request);
			if (sesionBO != null) {
				entidad = sesionBO.getIdEntidad();
				usuario = sesionBO.getUsuario();
			}
		}

		request.getSession().setAttribute("entidad", entidad);

		// Almacenamos la entidad en sesión, útil si queremos realizar
		// personalizaciones de imagenes, css, etc en función
		// de la entidad contra la que se está trabajando
		request.getSession().setAttribute(
				ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD,
				entidad);

		// Obtener el locale seleccionado por el filtro
		Locale locale = LocaleFilterHelper.getCurrentLocale(request);

		// Almacenamos el idioma seleccionado en sesión
		LocaleHelper.setLocale(request, locale);

		String remoteHost = request.getRemoteHost();
		if (StringUtils.isBlank(remoteHost)) {
			remoteHost = request.getRemoteAddr();
		}

		try {

			OrganizationUserInfo info = new OrganizationUserInfo();
			info.setOrganizationId(entidad);
			info.setUserName(usuario);

			SessionAPI sessionAPI = SessionAPIFactory.getSessionAPI(request, info);

			//Se eliminan las sesiones activas del usuario actual para esta aplicacion
			IItemCollection activeSessions = sessionAPI.getActiveSessions(usuario, APLICACION);
			while(activeSessions.next()){
				IItem activeSession = activeSessions.value();
				sessionAPI.deleteSession(activeSession.getString("ID"));
			}

			IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
			IDirectoryEntry userEntry = directory.getEntryFromUserName(usuario);
			info.setUserId(userEntry.getUID());

			// Auditoría
			AuditContext auditContext = new AuditContext();
			auditContext.setUserHost(request.getRemoteHost());
			auditContext.setUserIP(request.getRemoteAddr());
			auditContext.setUser(usuario);
			auditContext.setUserId(userEntry.getUID());
			// Añadir en el ThreadLocal el objeto AuditContext
			AuditContextHolder.setAuditContext(auditContext);

			sessionAPI.login(remoteHost, usuario, userEntry, APLICACION, locale);

			// Generar el ticket que se guarda en una cookie
			String ticket = sessionAPI.getTicket();
			Cookie cookieUser = new Cookie("user", ticket);
			response.addCookie(cookieUser);

			// creamos el objeto de estado del contexto
			StateContext stateContext = new StateContext();
			String stateticket = stateContext.getStateTicket();
			Cookie cookieInfo = new Cookie("contextInfo",stateticket);
			response.addCookie(cookieInfo);

		} catch (ISPACException e) {

			LOGGER.error("Se ha producido un error inesperado", e);

			ActionErrors errors = new ActionErrors();
			errors.add("error", new ActionError("search.error"));

			saveErrors(request, errors);
		}


		return mapping.findForward("success");
	}
}
