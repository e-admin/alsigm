package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
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
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.LocaleHelper;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.core.web.locale.LocaleFilterHelper;
import ieci.tecdoc.sgm.sesiones.backoffice.ws.client.Sesion;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EntryAction extends Action {

	private static String APLICACION;
	static {
		try {
			APLICACION = ISPACConfiguration.getInstance().get(ISPACConfiguration.TRAM_APPLICATION);
		} catch (ISPACException e) {
			e.printStackTrace();
		}
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Auntenticar el usuario
		if (AutenticacionBackOffice.autenticar(request)) {

			// Información de la sesión del usuario
			Sesion sesionBO = AutenticacionBackOffice.obtenerDatos(request);

			String entityId = sesionBO.getIdEntidad();

			HttpSession session = request.getSession();

			//Almacenamos la entidad en sesión, útil si queremos realizar personalizaciones de imagenes, css, etc en función
			//de la entidad contra la que se está trabajando
			session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD, entityId);

			// Obtener el locale seleccionado por el filtro
			Locale locale = LocaleFilterHelper.getCurrentLocale(request);

			// Almacenamos el idioma seleccionado en sesión
			LocaleHelper.setLocale(request, locale);

			String entityName = null;
			String userName = sesionBO.getUsuario();

			String remoteHost = request.getRemoteHost();
			if (StringUtils.isBlank(remoteHost)) {
				remoteHost = request.getRemoteAddr();
			}

			try {

				OrganizationUserInfo info = new OrganizationUserInfo();
				info.setOrganizationId(entityId);
				info.setOrganizationName(entityName);
				info.setUserName(userName);

				ISessionAPI sesion = SessionAPIFactory.getSessionAPI(request, info);

				//Se eliminan las sesiones activas del usuario actual para esta aplicacion
				IItemCollection itemCol = sesion.getActiveSessions(userName, APLICACION);
				while(itemCol.next()){
					IItem itemSession = itemCol.value();
					sesion.deleteSession(itemSession.getString("ID"));
				}

				IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
				IDirectoryEntry userEntry = directory.getEntryFromUserName(userName);
				info.setUserId(userEntry.getUID());

				// Auditoría
				AuditContext auditContext = new AuditContext();
				auditContext.setUserHost(request.getRemoteHost());
				auditContext.setUserIP(request.getRemoteAddr());
				auditContext.setUser(userName);
				auditContext.setUserId(userEntry.getUID());
				// Añadir en el ThreadLocal el objeto AuditContext
				AuditContextHolder.setAuditContext(auditContext);

				sesion.login(remoteHost, userName, userEntry, APLICACION, locale);

				Cookie cookieUser = new Cookie("user", sesion.getTicket());
				response.addCookie(cookieUser);

			} catch (ISPACException e) {
				composeReturnError("error.login.incorrecto", request);
				return mapping.findForward("fail");
			}


			// creamos el objeto de estado del contexto
			StateContext stateContext = new StateContext();
			String stateticket = stateContext.getStateTicket();
			Cookie cookieInfo = new Cookie("contextInfo",stateticket);
			response.addCookie(cookieInfo);

			return mapping.findForward("success");
		}
		composeReturnError("error.login.data.invalid", request);
		return mapping.findForward("fail");
	}

	private void composeReturnError(String resourceKey,HttpServletRequest request){
		String url = AutenticacionBackOffice.obtenerUrlLogout(request);
		request.setAttribute("urlLogout", url);
		ActionMessages messages = new ActionMessages();
		messages.add("user",new ActionMessage(resourceKey));
		saveErrors(request,messages);
	}



}