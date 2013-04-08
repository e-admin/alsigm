package com.ieci.tecdoc.isicres.servlets;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.DocumentSource;

import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;

import es.ieci.tecdoc.fwktd.core.config.web.ContextUtil;

/**
 * @author 79426599 Created on 01-jun-2006
 */
public class GetSearchAsociarRegistros extends HttpServlet {

	private static Logger _logger = Logger
			.getLogger(GetSearchAsociarRegistros.class);

	private BookUseCase bookUseCase = null;

	private TransformerFactory factory = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		bookUseCase = new BookUseCase();
		factory = TransformerFactory.newInstance();
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

		// Tipo de distribucion (entrada o salida).
		// Obtenemos la sesión asociada al usuario.
		HttpSession session = request.getSession();

		// identificador de archivo de usuario.
		Integer archiveId = RequestUtils.parseRequestParameterAsInteger(
				request, "ArchiveId");
		// Identificador de carpeta.
		Integer folderId = RequestUtils.parseRequestParameterAsInteger(request,
				"FolderId");
		// Texto del idioma. Ej: EU_
		String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
		// Número del idioma. Ej: 10
		Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
		// Obtenemos el objeto de configuración del servidor de aplicaciones y
		// el identificador
		// de sesión para este usuario en el servidor de aplicaciones.
		UseCaseConf useCaseConf = (UseCaseConf) session
				.getAttribute(Keys.J_USECASECONF);
		// response.getWriter().write(ACTIVATE_DTR);
		PrintWriter writer = response.getWriter();
		
		try {
			// Transformamos el xml mediante la xsl en html.
			// Los errores pueden ser de comunicación, de validación, de
			// transformación, etc...
			Document xmlDocument = bookUseCase.getAsocRegsSearch(useCaseConf);

			String xslPath = ContextUtil.getRealPath(session.getServletContext(), Keys.XSL_ASOCREGSSEARCH_RELATIVE_PATH);
			
			Transformer transformer = factory.newTransformer(new StreamSource(
					new InputStreamReader(new BufferedInputStream(
							new FileInputStream(xslPath)))));
			
			DocumentSource source = new DocumentSource(xmlDocument);
			StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			_logger.fatal("Error en diálogo de busqueda de asociacion de registros", e);
			ResponseUtils.generateJavaScriptLogGetSearchAsocRegs(writer, RBUtil
					.getInstance(useCaseConf.getLocale()).getProperty(
							Keys.I18N_ISICRESSRV_ERR_CREATING_ASOCSREG_OBJ));
		} catch (TransformerFactoryConfigurationError e) {
			_logger.fatal("Error en diálogo de busqueda de asociacion de registros", e);
			ResponseUtils.generateJavaScriptLogGetSearchAsocRegs(writer, RBUtil
					.getInstance(useCaseConf.getLocale()).getProperty(
							Keys.I18N_ISICRESSRV_ERR_CREATING_ASOCSREG_OBJ));
		} catch (TransformerException e) {
			_logger.fatal("Error en diálogo de busqueda de asociacion de registros", e);
			ResponseUtils.generateJavaScriptLogGetSearchAsocRegs(writer, RBUtil
					.getInstance(useCaseConf.getLocale()).getProperty(
							Keys.I18N_ISICRESSRV_ERR_CREATING_ASOCSREG_OBJ));
		} catch (Exception e) {
			_logger.fatal("Error en diálogo de busqueda de asociacion de registros", e);
			ResponseUtils.generateJavaScriptLogGetSearchAsocRegs(writer, RBUtil
					.getInstance(useCaseConf.getLocale()).getProperty(
							Keys.I18N_ISICRESSRV_ERR_CREATING_ASOCSREG_OBJ));
		}

	}

}
