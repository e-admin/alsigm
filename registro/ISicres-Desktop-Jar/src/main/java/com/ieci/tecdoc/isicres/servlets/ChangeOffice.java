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
 * @author LMVICENTE Genera La lista de libros. La lista se construye mediante
 *         una transformación de un xml y una xsl en html.
 *
 *
 * @creationDate 04-may-2004 12:54:25
 * @version
 * @since
 */
public class ChangeOffice extends HttpServlet implements Keys {

    /***************************************************************************
     * Attributes
     **************************************************************************/

    private static Logger _logger = Logger.getLogger(ChangeOffice.class);

    private TransformerFactory factory = null;

    private BookUseCase bookUseCase = null;

    /***************************************************************************
     * Constructors
     **************************************************************************/

    /***************************************************************************
     * Public methods
     **************************************************************************/

    /***************************************************************************
     * Protected methods
     **************************************************************************/

    public void init() throws ServletException {
        super.init();

        bookUseCase = new BookUseCase();
        factory = TransformerFactory.newInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doWork(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doWork(request, response);
    }

    /***************************************************************************
     * Private methods
     **************************************************************************/

    private void doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("text/html; charset=UTF-8");

        // identificador de archivo de usuario.
        String code = RequestUtils.parseRequestParameterAsString(request, "OfficeCode");

        // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();
        // Obtenemos el objeto de configuración del servidor de aplicaciones y
        // el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);
        PrintWriter writer = response.getWriter();
        try {
        	bookUseCase.validateOfficeCode(useCaseConf, code);

		// Borrar identificador del libro sobre el que se esta trabajando
		session.removeAttribute(Keys.J_BOOK);

		//Borrar identificador del registro sobre el que se esta trabajando
		session.removeAttribute(Keys.J_REGISTER);


		// Transformamos el xml mediante la xsl en html.
            // Los errores pueden ser de comunicación, de validación de
            // transformación, etc...

            Document xmlDocument = bookUseCase.getBooks(useCaseConf);
            String xslPath = ContextUtil.getRealPath(session.getServletContext(),XSL_LEST_RELATIVE_PATH);

            if (_logger.isDebugEnabled()) {
                _logger.debug("XSL path : " + xslPath);
            }

            Templates cachedXSLT = factory.newTemplates(new StreamSource(new InputStreamReader(new BufferedInputStream(
                    new FileInputStream(xslPath)))));
            Transformer transformer = cachedXSLT.newTransformer();
            DocumentSource source = new DocumentSource(xmlDocument);

            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
        } catch (ValidationException e) {
            _logger.fatal("Error de validacion", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
                    Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
        } catch (BookException e) {
            _logger.fatal("Error en el libro", e);
            ResponseUtils.generateJavaScriptError(writer, e);
        } catch (SessionException e) {
            _logger.fatal("Error en la sesion", e);
            ResponseUtils.generateJavaScriptError(writer, e);
        } catch (TransformerConfigurationException e) {
            _logger.fatal("Error al obtener la lista de libros", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
                    Keys.I18N_ISICRESSRV_ERR_CREATING_LESTREE_OBJ));
        } catch (TransformerFactoryConfigurationError e) {
            _logger.fatal("Error al obtener la lista de libros", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
                    Keys.I18N_ISICRESSRV_ERR_CREATING_LESTREE_OBJ));
        } catch (TransformerException e) {
            _logger.fatal("Error al obtener la lista de libros", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
                    Keys.I18N_ISICRESSRV_ERR_CREATING_LESTREE_OBJ));
        } catch (Exception e) {
            _logger.fatal("Error al obtener la lista de libros", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
                    Keys.I18N_ISICRESSRV_ERR_CREATING_LESTREE_OBJ));
        }
    }
    /***************************************************************************
     * Inner classes
     **************************************************************************/

    /***************************************************************************
     * Test brench
     **************************************************************************/

}

