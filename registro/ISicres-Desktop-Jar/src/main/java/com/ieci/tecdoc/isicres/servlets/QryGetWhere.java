/**
 * 
 * @author jcebrien
 * 
 */
package com.ieci.tecdoc.isicres.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.TransformerFactory;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;

/**
 * @author jcebrien
 * 
 */
public class QryGetWhere extends HttpServlet implements Keys {

	private static Logger _logger = Logger.getLogger(QryGetWhere.class);
	private TransformerFactory factory = null;
	private BookUseCase bookUseCase = null;

	public void init() throws ServletException {
		super.init();

		factory = TransformerFactory.newInstance();
		bookUseCase = new BookUseCase();
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
		response.setContentType("text/html; charset=UTF-8");

		// Identificador de la consulta.
		Integer fdrQryPId = RequestUtils.parseRequestParameterAsInteger(
				request, "FdrQryPId");
		// Lista de ordenación de los campos.
		String listOrder = RequestUtils.parseRequestParameterAsStringWithEmpty(
				request, "99999");
		// Obtenemos la sesión asociada al usuario.
		HttpSession session = request.getSession();
		// Obtenemos el objeto de configuración del servidor de aplicaciones y
		// el identificador
		// de sesión para este usuario en el servidor de aplicaciones.
		UseCaseConf useCaseConf = (UseCaseConf) session
				.getAttribute(J_USECASECONF);
		// Recuperamos el id de libro
		Integer bookID = (Integer) session.getAttribute(Keys.J_BOOK);
		// Texto del idioma. Ej: EU_
		String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
		// Número del idioma. Ej: 10
		Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
		// Tipo de informe a imprimir.
		Integer opcion = (Integer) session.getAttribute(Keys.J_REPORTOPTION);
		PrintWriter writer = response.getWriter();
		if (listOrder.equals("")) {
			listOrder = XML_FLD_UPPER_TEXT + 1;
		}
		try {
			List badCtrls = null;
			badCtrls = bookUseCase.validateQueryParams(useCaseConf, bookID,
					request.getParameterMap());
			if (badCtrls.isEmpty()) {
				Map tranlations = bookUseCase.translateQueryParams(useCaseConf,
						bookID, request.getParameterMap());
				int size = bookUseCase.openTableResults(useCaseConf, bookID,
						request.getParameterMap(), tranlations, opcion,
						listOrder);
				if (size == 0) {
					ResponseUtils.generateJavaScriptLog(writer, RBUtil
							.getInstance(useCaseConf.getLocale()).getProperty(
									Keys.I18N_EXCEPTION_NO_DATA_TO_REPORT));
					writer.write("<SCRIPT LANGUAGE=JavaScript>");
					writer
							.write("top.Main.Reports.ShowFmt.DisableQryControls(false);");
					writer.write("</SCRIPT>");
				} else {
					session.setAttribute(Keys.J_SIZE_REPORT, new Integer(size));
					writer.write("<HTML>");
					writer.write("<HEAD>");
					writer.write("<SCRIPT LANGUAGE=JavaScript>");
					writer
							.write("top.Main.Reports.ShowFmt.DoOnLoadResponse();");
					writer.write("</SCRIPT>");
					writer.write("</HEAD>");
					writer.write("<BODY tabIndex=-1>");
					writer.write("</BODY>");
					writer.write("</HTML>");
				}
			} else {
				writer
						.write("<HTML><HEAD><SCRIPT LANGUAGE=JavaScript>top.Main.Reports.ShowFmt.ClearAllInvalids();");
				for (Iterator it = badCtrls.iterator(); it.hasNext();) {
					writer.write("top.Main.Reports.ShowFmt.SetBadField(\""
							+ it.next().toString() + "\");");
				}
				writer
						.write("top.Main.Reports.ShowFmt.DisableQryControls(false);</SCRIPT></HEAD><BODY tabIndex=-1><LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"css/global.css\"/>\")</BODY></HTML>");
			}
			if (badCtrls != null && !badCtrls.isEmpty()) {
				ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(
						useCaseConf.getLocale()).getProperty(
						Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));
				writer.write(ACTIVATE_SEVERAL);
			}
		} catch (RemoteException e) {
			_logger.fatal("Error de comunicaciones", e);
			writer.write(ACTIVATE_SEVERAL);
			ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(
					useCaseConf.getLocale()).getProperty(
					Keys.I18N_EXCEPTION_REMOTEEXCEPTION));
		} catch (SecurityException e) {
			_logger.fatal("Error de seguridad", e);
			writer.write(ACTIVATE_SEVERAL);
			ResponseUtils.generateJavaScriptError(writer, e, useCaseConf
					.getLocale());
		} catch (ValidationException e) {
			_logger.fatal("Error de validacion", e);
			e.printStackTrace(System.err);
			writer.write(ACTIVATE_SEVERAL);
			ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(
					useCaseConf.getLocale()).getProperty(
					Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
		} catch (BookException e) {
			_logger.fatal("Error en el libro", e);
			writer.write(ACTIVATE_SEVERAL);
			ResponseUtils.generateJavaScriptError(writer, e);
		} catch (AttributesException e) {
			_logger.fatal("Error en los atributos", e);
			writer.write(ACTIVATE_SEVERAL);
			ResponseUtils.generateJavaScriptError(writer, e, useCaseConf
					.getLocale());
		} catch (SessionException e) {
			_logger.fatal("Error en la sesion", e);
			writer.write(ACTIVATE_SEVERAL);
			ResponseUtils.generateJavaScriptErrorSessionExpired(writer, e,
					idioma, numIdioma);
			// ResponseUtils.generateJavaScriptError(writer, e);
		} catch (Exception e) {
			_logger.fatal("Error al cargar la lista", e);
			writer.write(ACTIVATE_SEVERAL);
			ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(
					useCaseConf.getLocale()).getProperty(
					Keys.I18N_ISICRESSRV_ERR_CREATING_FDRQRY_OBJ));
		}
	}

}
