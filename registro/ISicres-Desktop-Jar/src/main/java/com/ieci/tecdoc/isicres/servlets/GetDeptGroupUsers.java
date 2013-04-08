package com.ieci.tecdoc.isicres.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.validationlist.ValidationListUseCase;

/**
 * Servlet implementation class for Servlet: GetDeptGroupUsers
 *
 */
 public class GetDeptGroupUsers extends HttpServlet implements Keys {
		private static Logger _logger = Logger.getLogger(GetDeptGroupUsers.class);

		ValidationListUseCase validationListUseCase = null;

		public void init() throws ServletException {
			super.init();
			validationListUseCase = new ValidationListUseCase();
		}

		protected void doGet(HttpServletRequest request,
				HttpServletResponse response) throws ServletException, IOException {
			doWork(request, response);
		}

		protected void doPost(HttpServletRequest request,
				HttpServletResponse response) throws ServletException, IOException {
			doWork(request, response);
		}

		private void doWork(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			response.setDateHeader("Expires", 0);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setContentType("text/xml; charset=UTF-8");

			// Obtenemos la sesión asociada al usuario.
			HttpSession session = request.getSession();
			// Identificador de de tipo de usuario. Departamento (1), Grupo (2), Usuario (3).
			Integer userTypeId = RequestUtils.parseRequestParameterAsInteger(request,
					"UserType", new Integer(0));
			// Texto del idioma. Ej: EU_
			String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
			// Número del idioma. Ej: 10
			Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);

			// Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
			// de sesión para este usuario en el servidor de aplicaciones.
			UseCaseConf useCaseConf = (UseCaseConf) session
					.getAttribute(Keys.J_USECASECONF);
			PrintWriter writer = response.getWriter();

			try {
				writer.write(validationListUseCase
						.getDeptsGroupsUsers(useCaseConf, userTypeId));
				writer.flush();
				writer.close();

			} catch (ValidationException e) {
				_logger.fatal("Error al recuperar la lista de usuarios", e);
				ResponseUtils.generateJavaScriptLog(writer, e.getMessage());
			} catch (SessionException e) {
				_logger.fatal("Error en la sesion", e);
				ResponseUtils.generateJavaScriptErrorSessionExpiredOpenFolder(
						writer, e, idioma, numIdioma, Boolean.FALSE);
				//ResponseUtils.generateJavaScriptError(out, e);
			} catch (Exception e) {
				_logger.fatal("Error al recuperar la lista de usuarios", e);
				ResponseUtils.ResponseErrorMsg(writer, RBUtil.getInstance(
						useCaseConf.getLocale()).getProperty(
								Keys.I18N_EXCEPTION_IMPOSSIBLETOLOADVALUES));
			}
		}
}