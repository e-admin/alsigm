/**
 * 
 * @author jcebrien
 * 
 */
package com.ieci.tecdoc.isicres.servlets;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.rmi.RemoteException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Templates;
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

import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;

import es.ieci.tecdoc.fwktd.core.config.web.ContextUtil;
/**
 * @author jcebrien
 *  
 */
public class FrmData extends HttpServlet implements Keys {
	private static Logger _logger = Logger.getLogger(FrmData.class);
	private static final String INTIAL_PATH = "cache";
	private static final String SEND_REDIRECT = "SendRedirect";
	private static final String INTERROGACION = "?";
	private static final String IGUAL = "=";
	private static final String AMPERSAN = "&";
	private BookUseCase bookUseCase = null;
	private TransformerFactory factory = null;
	public void init() throws ServletException {
		super.init();
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
		
		// Identificador fila.
		Integer row = RequestUtils.parseRequestParameterAsInteger(request,
				"Row");
		// Pagina.
		int page = RequestUtils.parseRequestParameterAsint(request, "Page");
		// Identificador de libro.
		Integer bookID = RequestUtils.parseRequestParameterAsInteger(
				request, "ArchiveId");
		// Identificador de registro.
		Integer folderId = RequestUtils.parseRequestParameterAsInteger(
				request, "FolderId");
		Integer folderPId = RequestUtils.parseRequestParameterAsInteger(
				request, "FolderPId");
		Integer archivePId = RequestUtils.parseRequestParameterAsInteger(
				request, "ArchivePId");
		// Identificador de la carpeta de consulta.
		Integer fdrQryPId = RequestUtils.parseRequestParameterAsInteger(
				request, "FdrQryPId");
		Integer copyFdr = RequestUtils.parseRequestParameterAsInteger(request,
				"CopyFdr", new Integer(0));
		// Obtenemos la sesión asociada al usuario.
		HttpSession session = request.getSession();
		// Texto del idioma. Ej: EU_
		String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
		// Número del idioma. Ej: 10
		Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
		// Desde formulario(true) o modificar(false).
		Boolean form = (Boolean) session.getAttribute(Keys.J_OPENFOLDER_FORM);
		if (form == null) {
			form = Boolean.FALSE;
		}
		// Obtenemos el objeto de configuración del servidor de aplicaciones y
		// el identificador
		// de sesión para este usuario en el servidor de aplicaciones.
		UseCaseConf useCaseConf = (UseCaseConf) session
				.getAttribute(J_USECASECONF);
		// Recuperamos el registro actual
		PrintWriter writer = response.getWriter();
		try {
			if (request.getParameter(SEND_REDIRECT) == null) {
				StringBuffer buffer = new StringBuffer();
				buffer.append(INTIAL_PATH);
				buffer.append(request.getServletPath());
				buffer.append(INTERROGACION);
				buffer.append(request.getQueryString());
				buffer.append(AMPERSAN);
				buffer.append(SEND_REDIRECT);
				buffer.append(IGUAL);
				buffer.append(1);
				response.sendRedirect(buffer.toString());
			}
			Document xmlDocument = null;
			if (copyFdr.intValue() == 0) {
				xmlDocument = bookUseCase.getBookFolderPage(useCaseConf,
						bookID, folderId.intValue(), page, form
								.booleanValue());
			} else {
				xmlDocument = bookUseCase.getBookFolderInitialPageFromCopy(useCaseConf,
						bookID, copyFdr.intValue(), page, form.booleanValue());
			}
			String xslPath = ContextUtil.getRealPath(session.getServletContext(), XSL_FMRDATA_RELATIVE_PATH);
			StreamSource s = new StreamSource(new InputStreamReader(new BufferedInputStream(
                    new FileInputStream(xslPath))));
			Templates cachedXSLT = factory.newTemplates(s);
			Transformer transformer = cachedXSLT.newTransformer();
			DocumentSource source = new DocumentSource(xmlDocument);
			StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);
		} catch (RemoteException e) {
            _logger.fatal("Error de comunicaciones", e);
			writer.write(Keys.ACTIVATE_FRM_DATA);
			ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(
					useCaseConf.getLocale()).getProperty(
					Keys.I18N_EXCEPTION_REMOTEEXCEPTION));
		} catch (ValidationException e) {
            _logger.fatal("Error de validacion", e);
			writer.write(Keys.ACTIVATE_FRM_DATA);
			ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(
					useCaseConf.getLocale()).getProperty(
					Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
		} catch (BookException e) {
            _logger.fatal("Error en el libro", e);
			writer.write(Keys.ACTIVATE_FRM_DATA);
			ResponseUtils.generateJavaScriptError(writer, e);
		} catch (SessionException e) {
            _logger.fatal("Error en la sesion", e);
			ResponseUtils.generateJavaScriptErrorSessionExpiredOpenFolder(
			        writer, e, idioma, numIdioma, form);
			//ResponseUtils.generateJavaScriptError(response, e);
		} catch (TransformerConfigurationException e) {
            _logger.fatal("Error al recuperar el formulario", e);
			writer.write(ACTIVATE_FRM_DATA);
			ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(
					useCaseConf.getLocale()).getProperty(
					Keys.I18N_ISICRESSRV_ERR_CREATING_FDRQRY_OBJ));
		} catch (TransformerFactoryConfigurationError e) {
            _logger.fatal("Error al recuperar el formulario", e);
			writer.write(ACTIVATE_FRM_DATA);
			ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(
					useCaseConf.getLocale()).getProperty(
					Keys.I18N_ISICRESSRV_ERR_CREATING_FDRQRY_OBJ));
		} catch (TransformerException e) {
            _logger.fatal("Error al recuperar el formulario", e);
			writer.write(ACTIVATE_FRM_DATA);
			ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(
					useCaseConf.getLocale()).getProperty(
					Keys.I18N_ISICRESSRV_ERR_CREATING_FDRQRY_OBJ));
		} catch (Exception e) {
            _logger.fatal("Error al recuperar el formulario", e);
			writer.write(Keys.ACTIVATE_FRM_DATA);
			ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(
					useCaseConf.getLocale()).getProperty(
					Keys.I18N_ISICRESSRV_ERR_CREATING_FDRQRY_OBJ));
		}
	}
}