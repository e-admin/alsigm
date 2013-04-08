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
import java.util.StringTokenizer;

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
 *
 * Servlet que se invoca cuando se generan los registros en cadena.
 *
 * @author jcebrien
 *
 */
public class NewFldBuc extends HttpServlet implements Keys {

	private static Logger _logger = Logger.getLogger(NewFldBuc.class);

	private static final String INTERROGACION = "?";

    private static final String IGUAL = "=";

    private static final String BLANCO = " ";

    private static final String AMPERSAN = "&";

    private static final String PUNTO_COMA = ";";

    private static final String BARRA = "|";

    public static long staticFolderPId = 1;

    private BookUseCase bookUseCase = null;

    private TransformerFactory factory = null;

    public void init() throws ServletException {
        super.init();

        bookUseCase = new BookUseCase();
        factory = TransformerFactory.newInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doWork(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doWork(request, response);
    }



    private void doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException,
    IOException {
    	response.setDateHeader("Expires", 0);
    	response.setHeader("Cache-Control", "no-cache");
    	response.setHeader("Pragma", "no-cache");
    	response.setContentType("text/html; charset=UTF-8");

    	// identificador de archivo de usuario.
    	Integer bookID = RequestUtils.parseRequestParameterAsInteger(request, "ArchiveId");
    	// 	Identificador de registro.
    	Integer archivePId = RequestUtils.parseRequestParameterAsInteger(request, "ArchivePId");
    	// 	Identificador de carpeta.
    	Integer folderId = RequestUtils.parseRequestParameterAsInteger(request, "FolderId");
    	// Identificador de carpeta.
    	Long folderPId = RequestUtils.parseRequestParameterAsLong(request, "FolderPId");
    	// 	Si datos en el formulario.
    	Boolean frmData = RequestUtils.parseRequestParameterAsBoolean(request, "FrmData");
    	// Obtenemos la sesión asociada al usuario.
    	HttpSession session = request.getSession();
    	// Texto del idioma. Ej: EU_
    	String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
    	// Número del idioma. Ej: 10
    	Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
    	//Integer bookID = (Integer) session.getAttribute(Keys.J_BOOK);
    	Integer row = RequestUtils.parseRequestParameterAsInteger(request, "Row", new Integer(-1));
    	// Obtenemos el objeto de configuración del servidor de aplicaciones y
    	// el identificador
    	// de sesión para este usuario en el servidor de aplicaciones.
    	UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);
    	PrintWriter writer = response.getWriter();

    	try {
    		bookUseCase.closeFolder(useCaseConf, bookID, folderId.intValue());
            setStrCarpeta(session, bookID, folderId.intValue());
            session.removeAttribute(Keys.J_REGISTER);
            session.removeAttribute(Keys.J_OPENFOLDER_FORM);

    		Document xmlDocument = bookUseCase.getEmptyBookTree(useCaseConf, bookID, false,
                staticFolderPId++, -1, row.intValue(), null, useCaseConf.getLocale(),true);

    		String xslPath = ContextUtil.getRealPath(session.getServletContext(), XSL_FRMT_RELATIVE_PATH);
    		StreamSource s = new StreamSource(new InputStreamReader(new BufferedInputStream(
                new FileInputStream(xslPath))));
    		Transformer transformer = factory.newTransformer(s);
    		DocumentSource source = new DocumentSource(xmlDocument);

    		StreamResult result = new StreamResult(writer);
    		transformer.transform(source, result);
    	} catch (ValidationException e) {
    		_logger.fatal("Error de validacion", e);
    		writer.write(Keys.ACTIVATE_NEW_FOLDER);
    		ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
    				.getProperty(Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
    	} catch (BookException e) {
    		_logger.fatal("Error en el libro", e);
    		writer.write(Keys.ACTIVATE_NEW_FOLDER);
    		ResponseUtils.generateJavaScriptError(writer, e);
    	} catch (SessionException e) {
    		_logger.fatal("Error en la sesion", e);
    		ResponseUtils.generateJavaScriptErrorSessionExpiredOpenFolder(writer, e, idioma, numIdioma,
    				new Boolean(false));
    	} catch (TransformerConfigurationException e) {
    		_logger.fatal("Error en los valores", e);
    		writer.write(ACTIVATE_NEW_FOLDER);
    		ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
    				.getProperty(Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));
    	} catch (TransformerFactoryConfigurationError e) {
    		_logger.fatal("Error en los valores", e);
    		writer.write(ACTIVATE_NEW_FOLDER);
    		ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
    				.getProperty(Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));
    	} catch (TransformerException e) {
    		_logger.fatal("Error en los valores", e);
    		writer.write(ACTIVATE_NEW_FOLDER);
    		ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
    				.getProperty(Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));
    	} catch (Exception e) {
    		_logger.fatal("Error en los valores", e);
    		writer.write(Keys.ACTIVATE_NEW_FOLDER);
    		ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
    				.getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_FDRQRY_OBJ));
    	}
    }

    // Este método quita archiveId y folderId de la cadena
    private void setStrCarpeta(HttpSession session, Integer archiveId, int folderId) {
        StringBuffer buffer = new StringBuffer();
        String archFolder = (String) session.getAttribute(Keys.J_STRCARPETA);
        if (archFolder != null) {
            StringTokenizer tokens = new StringTokenizer(archFolder, PUNTO_COMA);
            String aux = null;
            String auxArch = null;
            String auxFolder = null;
            while (tokens.hasMoreTokens()) {
                aux = tokens.nextToken();
                auxArch = aux.substring(0, aux.indexOf(BARRA));
                auxFolder = aux.substring(aux.indexOf(BARRA) + 1, aux.length());
                if (!auxArch.equals(archiveId.toString()) || !auxFolder.equals(Integer.toString(folderId))) {
                    buffer.append(aux);
                    buffer.append(PUNTO_COMA);
                }
            }
        }
        session.setAttribute(Keys.J_STRCARPETA, buffer.toString());
    }
}