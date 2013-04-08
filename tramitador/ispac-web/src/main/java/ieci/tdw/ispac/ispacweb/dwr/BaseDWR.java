package ieci.tdw.ispac.ispacweb.dwr;

import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class BaseDWR {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(BaseDWR.class);
	
	
	public WebContext getWebContext() {
		return WebContextFactory.get();
	}
	
	public HttpServletRequest getHttpServletRequest() {
		return getWebContext().getHttpServletRequest();
	}

	public HttpServletResponse getHttpServletResponse() {
		return getWebContext().getHttpServletResponse();
	}

	public ServletConfig getServletConfig() {
		return getWebContext().getServletConfig();
	}

	public ServletContext getServletContext() {
		return getWebContext().getServletContext();
	}

	public ISessionAPI createSessionAPI() throws ISPACException {
		

		HttpServletRequest request = getHttpServletRequest();
		HttpServletResponse response = getHttpServletResponse();

		return SessionAPIFactory.getSessionAPI(request, response);
	}
	
	public void releaseSessionAPI(ISessionAPI sessionAPI) {
		try {
			if (sessionAPI != null) {
				sessionAPI.release();
			}
		} catch(Throwable t) {
			logger.error("Error al liberar la sesión de iSPAC", t);
		}
	}

	public String rewriteHref(String href) throws ISPACException {
		
		String context = getHttpServletRequest().getContextPath();
        String ispacbase = (String) getServletContext().getAttribute("ispacbase");
        
        String skin = (String) getHttpServletRequest().getSession().getAttribute("skin");
        if (StringUtils.isEmpty(skin)) {
        	skin = (String) getServletContext().getAttribute("skin");
        }
        
        return StaticContext.rewriteHref(StaticContext.getInstance().getBaseUrl(context),
        		ispacbase, skin, href);        
	}
}
