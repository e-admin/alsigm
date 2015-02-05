package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.LocaleHelper;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.LoginForm;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;

import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class LoginAction extends Action
{
	private static String APLICACION;
	private static Logger logger = Logger.getLogger(LoginAction.class);
	static {
		try {
			APLICACION = ISPACConfiguration.getInstance().get(ISPACConfiguration.TRAM_APPLICATION);
		} catch (ISPACException e) {
			ResourceBundle bundle = ResourceBundle.getBundle("ieci.tdw.ispac.api.errors.ISPACExceptionMessages");
			logger.error(bundle.getString("error.application.localization")+"\n"+e.toString());
		}
	}
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
				
		ActionMessages messages = new ActionMessages();
		LoginForm loginForm = (LoginForm) form;
		String user = loginForm.getUser();
		if(StringUtils.isBlank(user)){
			String keyMessage = "error.login.user";
			messages.add("user",new ActionMessage(keyMessage));
			saveErrors(request,messages);
			return mapping.findForward("fail");
		}
		
		String password = loginForm.getPassword();
		// Hay que autenticar al usuario
		SessionAPI sesion = SessionAPIFactory.getSessionAPI(request);
		String remoteHost = request.getRemoteHost();
		if (StringUtils.isBlank(remoteHost)) {
			remoteHost = request.getRemoteAddr();
		}

		try {
			if (loginForm.isForceExclussion()){
				//Se eliminan las sesiones activas del usuario actual para esta aplicacion
				IItemCollection itemCol = sesion.getActiveSessions(user, APLICACION);
				while(itemCol.next()){
					IItem itemSession = itemCol.value();
					sesion.deleteSession(itemSession.getString("ID"));
				}				
			}
			//Se valida el usuario
			IDirectoryEntry userEntry = sesion.validate(user, password);
			
			// Comprobar si el usuario tiene alguna sesion abierta
			// (sin contar la actual) para esta aplicacion
			IItemCollection itemcol = sesion.getActiveSessions(user, APLICACION);
			if (itemcol.next()) {
				request.setAttribute(ActionsConstants.USER, user);
				request.setAttribute(ActionsConstants.ACTIVE_SESSIONS, ""+(itemcol.toList().size()));
				return mapping.findForward("error");
			}

			//TODO: Añadir en el ThreadLocal el objeto AuditContext.
			AuditContext auditContext = new AuditContext();
			auditContext.setUserHost(request.getRemoteHost());
			auditContext.setUserIP(request.getRemoteAddr());
			auditContext.setUser(user);
			auditContext.setUserId(userEntry.getUID());
			
			AuditContextHolder.setAuditContext(auditContext);
			// Se registra la sesión del usuario
			sesion.login(remoteHost, user, userEntry, APLICACION, LocaleHelper.getLocale(request));
			
		} catch (ISPACException e) {
			String keyMessage = "error.login.connection";
			if (e  instanceof ISPACNullObject)
				keyMessage = "error.login.incorrecto";
			messages.add("user",new ActionMessage(keyMessage));
			saveErrors(request,messages);
			return mapping.findForward("fail");
		}

		String ticket = sesion.getTicket();

		Cookie cookieUser = new Cookie("user", ticket);
		response.addCookie(cookieUser);

		// creamos el objeto de estado del contexto
		StateContext stateContext = new StateContext();
		String stateticket = stateContext.getStateTicket();
		Cookie cookieInfo = new Cookie("contextInfo",stateticket);
		response.addCookie(cookieInfo);
		
		return mapping.findForward("success");
	}
}