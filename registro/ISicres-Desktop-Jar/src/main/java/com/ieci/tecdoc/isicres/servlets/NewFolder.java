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
 * Este servlet se invoca cuando se pulsa Nuevo Registro. Se abrirá una nueva
 * carpeta nueva.
 *
 *
 * @author jcebrien
 *
 */
public class NewFolder extends HttpServlet implements Keys {
	private static Logger _logger = Logger.getLogger(NewFolder.class);

	private static final String INTERROGACION = "?";

    private static final String IGUAL = "=";

    private static final String AMPERSAN = "&";

    private static final String PUNTO_COMA = ";";

    private static final String BARRA = "|";

    public static long folderPId = 1;

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
    	Long archivePId = RequestUtils.parseRequestParameterAsLong(request, "ArchivePId ");
    	// Identificador para establecer si es un nuevo registro o es una copia.
    	Integer copyFdr = RequestUtils.parseRequestParameterAsInteger(request, "CopyFdr", new Integer(0));

    	// Obtenemos la sesión asociada al usuario.
    	HttpSession session = request.getSession();
    	// Recuperamos la fila de la carpeta
    	Integer row = RequestUtils.parseRequestParameterAsInteger(request, "Row", new Integer(-1));
    	// Texto del idioma. Ej: EU_
    	String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
    	// Número del idioma. Ej: 10
    	Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
    	// Recuperamos el id de libro
    	Integer bookID = (Integer) session.getAttribute(Keys.J_BOOK);
    	// Obtenemos el objeto de configuración del servidor de aplicaciones y
    	// el identificador
    	// de sesión para este usuario en el servidor de aplicaciones.

    	session.removeAttribute(Keys.J_REGISTER);
        session.removeAttribute(Keys.J_OPENFOLDER_FORM);
        session.removeAttribute(Keys.J_ROW);

    	UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);
    	PrintWriter writer = response.getWriter();
    	String fields = null;

    	try {
   			if (_logger.isDebugEnabled()) {
   				_logger.debug("getBeforeEncondedResponseURL(request, response) => "
   				+ getBeforeEncondedResponseURL(request, response));
   			}

   			Document xmlDocument = bookUseCase.getEmptyBookTree(useCaseConf, bookID, false, folderPId++,
   					-1, row.intValue(), getBeforeEncondedResponseURL(request, response), useCaseConf.getLocale(),true);

   			String xslPath = ContextUtil.getRealPath(session.getServletContext(), XSL_FRMT_RELATIVE_PATH);
   			StreamSource s = new StreamSource(new InputStreamReader(new BufferedInputStream(new FileInputStream(xslPath))));
    		Transformer transformer = factory.newTransformer(s);
    		DocumentSource source = new DocumentSource(xmlDocument);

    		StreamResult result = new StreamResult(writer);
    		transformer.transform(source, result);
    	} catch (ValidationException e) {
    		_logger.fatal("Error de validacion", e);
    		writer.write(Keys.ACTIVATE_OPEN_FOLDER);
    		ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
    	} catch (BookException e) {
    		_logger.fatal("Error en el libro", e);
    		writer.write(Keys.ACTIVATE_OPEN_FOLDER);
    		ResponseUtils.generateJavaScriptError(writer, e);
    	} catch (SessionException e) {
    		_logger.fatal("Error en la sesion", e);
    		ResponseUtils.generateJavaScriptErrorSessionExpiredOpenFolder(writer, e, idioma, numIdioma,  new Boolean(false));
    	} catch (TransformerConfigurationException e) {
    		_logger.fatal("Error al crear el registro", e);
    		writer.write(ACTIVATE_OPEN_FOLDER);
    		ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));
    	} catch (TransformerFactoryConfigurationError e) {
    		_logger.fatal("Error al crear el registro", e);
    		writer.write(ACTIVATE_OPEN_FOLDER);
    		ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));
    	} catch (TransformerException e) {
    		_logger.fatal("Error al crear el registro", e);
    		writer.write(ACTIVATE_OPEN_FOLDER);
    		ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));
    	} catch (Exception e) {
    		_logger.fatal("Error al crear el registro", e);
    		writer.write(Keys.ACTIVATE_OPEN_FOLDER);
    		ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_FDRQRY_OBJ));
    	}
    }

    private String getBeforeEncondedResponseURL(HttpServletRequest request, HttpServletResponse response) {
        String query = request.getQueryString();
        query = query.replaceFirst(J_OPENFOLDER_TYPE + IGUAL + J_OPENFOLDER_TYPE_XMLBOOKTREE_FORM_BUTTON,
                J_OPENFOLDER_TYPE + IGUAL + J_OPENFOLDER_TYPE_XMLBOOK);
        query = query.replaceFirst(J_OPENFOLDER_TYPE + IGUAL + J_OPENFOLDER_TYPE_XMLBOOKTREE_UDPATE_BUTTON,
                J_OPENFOLDER_TYPE + IGUAL + J_OPENFOLDER_TYPE_XMLBOOK);

        String path = request.getServletPath();
        path = path.substring(5, path.length());

        StringBuffer buffer = new StringBuffer();
        buffer.append(path);
        buffer.append(INTERROGACION);
        buffer.append(query);
        buffer.append(AMPERSAN);
        buffer.append(J_OPENFOLDER_TYPE);
        buffer.append(IGUAL);
        buffer.append(J_OPENFOLDER_TYPE_XMLBOOK);

        if (_logger.isDebugEnabled()) {
            _logger.debug(buffer.toString());
        }


        return response.encodeURL(buffer.toString());
    }

}