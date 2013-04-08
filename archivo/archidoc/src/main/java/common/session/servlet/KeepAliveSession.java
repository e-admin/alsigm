package common.session.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import se.usuarios.AppUser;
import se.usuarios.ServiceClient;

import common.ConfigConstants;
import common.Constants;
import common.bi.GestionSessionBI;
import common.bi.ServiceRepository;
import common.exceptions.ConfigDbArchivoException;
import common.util.StringUtils;

public class KeepAliveSession extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void destroy() {
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) {

		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "No-cache");

		String ticket = request.getParameter("ticket");
		String entity = request.getParameter("entity");
		if (ticket != null && !ticket.equalsIgnoreCase("null")) {

			// Si está activado el parámetro de entidad requerida no hacer un
			// keep alive mientras el usuario
			// no esté autenticado
			if ((ConfigConstants.getInstance().getEntidadRequerida() == false)
					|| ((ConfigConstants.getInstance().getEntidadRequerida() == true) && (StringUtils
							.isNotEmpty(entity)))) {

				// Usuario de la aplicación
				try {
					HttpSession session = request.getSession();
					AppUser user = (AppUser) session
							.getAttribute(Constants.USUARIOKEY);

					if ((user != null) && (user.getEngine() != null)) {
						ServiceClient client = ServiceClient.create(user);

						// Repositorio de servicios
						ServiceRepository services = ServiceRepository
								.getInstance(client);
						GestionSessionBI serviceSession = services
								.lookupGestionSessionBI();
						serviceSession.keepAlive(ticket);
					}
					PrintWriter pw;
					try {
						pw = response.getWriter();
						pw.write("<response>ok</response>");
						pw.close();
					} catch (IOException e) {
						// Se ignora porque no debería hacer nada.
					}
				} catch (ConfigDbArchivoException e) {
				}
			}
		}
	}
}
