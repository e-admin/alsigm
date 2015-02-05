package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.common.constants.ScopeConstants;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.utils.LocaleHelper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class SessionAPIFactory {

	/** Logger de la clase. */
	protected static Logger logger = Logger.getLogger(SessionAPIFactory.class);

	// String del estado de la aplicación
	public static final String ATTR_STATETICKET = "ATTR_STATETICKET";
	


	public static SessionAPI getSessionAPI() throws ISPACException {
		return new SessionAPI();
	}

	public static SessionAPI getSessionAPI(HttpServletRequest request) {
		
		SessionAPI sessionAPI = new SessionAPI();
		
		// Establece la información de multientidad
		setOrganizationInfo(request);

		return sessionAPI;
	}

	public static SessionAPI getSessionAPI(HttpServletRequest request, OrganizationUserInfo info) {
		
		request.getSession().setAttribute(ScopeConstants.ORGANIZATION_USER_INFO, info);
		return getSessionAPI(request);
	}
	
	public static SessionAPI getSessionAPI(HttpServletRequest request, String ticket) 
			throws ISPACException {
		
		SessionAPI sessionAPI = getSessionAPI(request);
		sessionAPI.init(ticket, LocaleHelper.getLocale(request));

		return sessionAPI;
	}

	public static SessionAPI getSessionAPI(HttpServletRequest request, HttpServletResponse response) 
			throws ISPACException {
		
		String ticket = null;
		
		Cookie[] allCookies = request.getCookies();
		if (allCookies != null) {
			for (int i = 0; i < allCookies.length; i++) {
				if ("user".equals(allCookies[i].getName())) {
					ticket = allCookies[i].getValue();
					response.addCookie(allCookies[i]);
				} else if ("contextInfo".equals(allCookies[i].getName())) {
					String stateticket = allCookies[i].getValue();
					setStateticket(request, stateticket);
				}
			}
		}

		return getSessionAPI(request, ticket);
	}


	protected static void setOrganizationInfo(HttpServletRequest request) {
		OrganizationUserInfo info = (OrganizationUserInfo) request.getSession()
				.getAttribute(ScopeConstants.ORGANIZATION_USER_INFO);
		if (info != null) {
			
			if (logger.isDebugEnabled()) {
				logger.debug("OrganizationUserInfo: " 
						+ "userName: " + info.getUserName()
						+ "userId: " + info.getUserId()
						+ "organizationName: " + info.getOrganizationName()
						+ "organizationId: " + info.getOrganizationId());
			}
				
			OrganizationUser.setOrganizationUserInfo(info);
		}
	}

	protected static void setStateticket(HttpServletRequest request,
			String stateticket) throws ISPACException {
		request.setAttribute(ATTR_STATETICKET, stateticket);
	}
	
}
