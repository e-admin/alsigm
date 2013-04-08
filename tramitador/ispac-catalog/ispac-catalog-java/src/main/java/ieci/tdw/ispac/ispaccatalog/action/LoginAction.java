package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaccatalog.action.form.LoginForm;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.LocaleHelper;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.security.UserCredentials;
import ieci.tdw.ispac.ispacweb.security.UserCredentialsHelper;

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
			APLICACION = ISPACConfiguration.getInstance().get(ISPACConfiguration.CTLG_APPLICATION);
		}
		catch (ISPACException e) {
			ResourceBundle bundle = ResourceBundle.getBundle("ieci.tdw.ispac.api.errors.ISPACExceptionMessages");
			logger.error(bundle.getString("error.application.localization")+"\n"+e.toString());
		}
	}

	public ActionForward execute(ActionMapping mapping, 
								 ActionForm form,
								 HttpServletRequest request, 
								 HttpServletResponse response) throws Exception {
		
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

		// Autentificación del usuario
		SessionAPI sesion = SessionAPIFactory.getSessionAPI(request);
		String remoteHost = request.getRemoteHost();
		if (StringUtils.isBlank(remoteHost)) {
			remoteHost = request.getRemoteAddr();
		}

		try {
			
			//Se valida el usuario
			IDirectoryEntry userEntry = sesion.validate(user, password);

			//TODO: Añadir en el ThreadLocal el objeto AuditContext.
			AuditContext auditContext = new AuditContext();
			auditContext.setUserHost(request.getRemoteHost());
			auditContext.setUserIP(request.getRemoteAddr());
			auditContext.setUser(user);
			auditContext.setUserId(userEntry.getUID());
			
			AuditContextHolder.setAuditContext(auditContext);

//			// Autenticación
//			sesion.login(remoteHost, user, password, APLICACION, LocaleHelper.getLocale(request));
			sesion.login(remoteHost, user, userEntry, APLICACION, LocaleHelper.getLocale(request));
		}
		catch (ISPACException e) {
			String keyMessage = "error.login.connection";
			if (e  instanceof ISPACNullObject)
				keyMessage = "error.login.incorrecto";
			messages.add("user",new ActionMessage(keyMessage));
			saveErrors(request,messages);
			return mapping.findForward("fail");
		}

		// Obtener las funciones del usuario
		UserCredentials userCredentials = UserCredentialsHelper.saveUserCredentials(request, sesion.getClientContext());

		// Autorización
		if (!isAuthorizedUser(userCredentials)) {
			messages.add("user",new ActionMessage("error.login.authorization"));
			saveErrors(request,messages);
			return mapping.findForward("fail");
		}

		// Generar el ticket que se guarda en una cookie
		String ticket = sesion.getTicket();
		Cookie cookieUser = new Cookie("user", ticket);
		response.addCookie(cookieUser);

		if (userCredentials.isCatalogAdministrator()
				|| userCredentials.containsAnyFunction(new int[] {
						ISecurityAPI.FUNC_INV_PROCEDURES_READ,
						ISecurityAPI.FUNC_INV_PROCEDURES_EDIT })) {
			return mapping.findForward("proceduresTree");
		} else {
			return mapping.findForward("success");
		}
	}
	
	private boolean isAuthorizedUser(UserCredentials userCredentials) throws ISPACException {
		
		boolean authorized = false;
		
		if (userCredentials.isCatalogAdministrator()
				|| userCredentials
						.containsAnyFunction(ISecurityAPI.CATALOG_ACCESS_FUNCTIONS)) {
			authorized = true;
		}
		
		return authorized;
	}
	
}