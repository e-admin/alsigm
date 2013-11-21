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

import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.SQLValidator;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;

import es.ieci.tecdoc.fwktd.core.config.web.ContextUtil;

/**
 * Este servlet permite navegar por las páginas donde se muestran los registros obtenidos después de realizar una
 * consulta en el formulario de consulta y pulsar el botón Aceptar.
 *
 * @author jcebrien
 *
 */
public class Tbltext2 extends HttpServlet implements Keys {

	private static Logger _logger = Logger.getLogger(Tbltext2.class);
    private TransformerFactory factory = null;
    private BookUseCase bookUseCase = null;

    public void init() throws ServletException {
        super.init();

        factory = TransformerFactory.newInstance();
        bookUseCase = new BookUseCase();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doWork(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doWork(request, response);
    }

    private void doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("text/html; charset=UTF-8");

        // Identificador de la consulta.
        Integer fdrQryPId = RequestUtils.parseRequestParameterAsInteger(request, "FdrQryPId");
        // Identificador de registro.
        Integer row = RequestUtils.parseRequestParameterAsInteger(request, "Row");
        // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();
        // Obtenemos el orden de los campos
        String orderByTable = RequestUtils.parseRequestParameterAsString(request, "orderByTable");
        // Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);
        // Recuperamos el id de libro
        Integer bookID = (Integer) session.getAttribute(Keys.J_BOOK);
        // Texto del idioma. Ej: EU_
        String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
        // Número del idioma. Ej: 10
        Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
		PrintWriter writer = response.getWriter ();
        try {
			// Validamos si la ordenación que recibimos es correcta y no haya
			// sido modificada con SQL malicioso
			SQLValidator.getInstance().validateOrderQueryRegister(orderByTable);

            Document xmlDocument = null;

            switch (row.intValue()) {
            case 0:
                {
                    xmlDocument = bookUseCase.getTableResults(useCaseConf,
                            bookID,
                            com.ieci.tecdoc.common.isicres.Keys.QUERY_FIRST_PAGE, orderByTable);
                    break;
                }
            case -1:
                {
                    xmlDocument = bookUseCase.getTableResults(useCaseConf,
                            bookID,
                            com.ieci.tecdoc.common.isicres.Keys.QUERY_LAST_PAGE, orderByTable);
                    break;
                }
            case -2:
                {
                    xmlDocument = bookUseCase.getTableResults(useCaseConf,
                            bookID,
                            com.ieci.tecdoc.common.isicres.Keys.QUERY_PREVIOUS_PAGE, orderByTable);
                    break;
                }
            case -3:
                {
                    xmlDocument = bookUseCase.getTableResults(useCaseConf,
                            bookID,
                            com.ieci.tecdoc.common.isicres.Keys.QUERY_NEXT_PAGE, orderByTable);
                    break;
                }
            default:
                {
                    xmlDocument = bookUseCase.getTableResultsForRow(useCaseConf, bookID, row.intValue(), orderByTable);
                    break;
                }
            }

            // Transformamos el xml mediante la xsl en html.
            // Los errores pueden ser de comunicación, de validación, de transformación, etc...
            String xslPath = ContextUtil.getRealPath(session.getServletContext(), XSL_TBLTEXT_RELATIVE_PATH);
            StreamSource s = new StreamSource(new InputStreamReader(new BufferedInputStream(
                    new FileInputStream(xslPath))));
            Templates cachedXSLT = factory.newTemplates(s);
            Transformer transformer = cachedXSLT.newTransformer();
            DocumentSource source = new DocumentSource(xmlDocument);

            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
        } catch (SecurityException e) {
            _logger.fatal("Error de seguridad", e);
            writer.write(ACTIVATE_SEVERAL_2);
            ResponseUtils.generateJavaScriptError(writer , e, useCaseConf.getLocale());
        } catch (ValidationException e) {
            _logger.fatal("Error de validacion", e);
            writer.write(ACTIVATE_SEVERAL_2);
            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
        } catch (BookException e) {
            _logger.fatal("Error en el libro", e);
            writer.write(ACTIVATE_SEVERAL_2);
            ResponseUtils.generateJavaScriptError(writer , e);
        } catch (AttributesException e) {
            _logger.fatal("Error en los atributos", e);
            writer.write(ACTIVATE_SEVERAL);
            ResponseUtils.generateJavaScriptError(writer, e, useCaseConf.getLocale());
        } catch (SessionException e) {
            _logger.fatal("Error en la sesion", e);
            writer.write(ACTIVATE_SEVERAL_2);
            ResponseUtils.generateJavaScriptErrorSessionExpired(writer, e, idioma, numIdioma);
            //ResponseUtils.generateJavaScriptError(writer, e);
        } catch (TransformerConfigurationException e) {
            _logger.fatal("Error al cargar la lista", e);
            writer.write(ACTIVATE_SEVERAL_2);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));
        } catch (TransformerFactoryConfigurationError e) {
            _logger.fatal("Error al cargar la lista", e);
            writer.write(ACTIVATE_SEVERAL_2);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));
        } catch (TransformerException e) {
            _logger.fatal("Error al cargar la lista", e);
            writer.write(ACTIVATE_SEVERAL_2);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));
        } catch (Exception e) {
            _logger.fatal("Error al cargar la lista", e);
            writer.write(ACTIVATE_SEVERAL_2);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));
        }

    }
}