package ieci.tdw.ispac.ispacweb.session;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class KeepAliveSession extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(KeepAliveSession.class);
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void destroy() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "No-cache");

		try {
			String ticket = request.getParameter("ticket");
			if (StringUtils.isNotBlank(ticket)) {
				SessionAPI session = SessionAPIFactory.getSessionAPI(request, response);
				session.keepAlive(ticket);
			}
			
		} catch (ISPACException e) {
			logger.warn("Error en el keepAlive", e);
			
		}
	}

}
