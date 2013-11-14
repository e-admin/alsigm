package com.ieci.tecdoc.isicres.servlets;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
import com.ieci.tecdoc.common.exception.ReportException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.reports.ReportsUseCase;

import es.ieci.tecdoc.fwktd.core.config.web.ContextUtil;

/**
 * Servlet encargado de obtener el listado de informes
 *
 * @author jcebrien
 *
 */
public class Report extends HttpServlet implements Keys {

	private static Logger _logger = Logger.getLogger(Report.class);
    private ReportsUseCase reportsUseCase = null;
    private TransformerFactory factory = null;

    private static Map reportsType = new HashMap(4);

    static {
        reportsType.put("CM", new Integer(com.ieci.tecdoc.common.isicres.Keys.REPORT_TYPE_CM));
        reportsType.put("LM", new Integer(com.ieci.tecdoc.common.isicres.Keys.REPORT_TYPE_LM));
        reportsType.put("RMD", new Integer(com.ieci.tecdoc.common.isicres.Keys.REPORT_TYPE_RMD));
        reportsType.put("RMO", new Integer(com.ieci.tecdoc.common.isicres.Keys.REPORT_TYPE_RMO));
    }

    public void init() throws ServletException {
        super.init();

        reportsUseCase = new ReportsUseCase();
        factory = TransformerFactory.newInstance();
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

        // identificador de archivo de usuario.
        Integer archivePId = RequestUtils.parseRequestParameterAsInteger(request, "ArchivePId ");
        // opción seleccionada.
        String opcion = RequestUtils.parseRequestParameterAsString(request, "Opcion");
        // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();
        // Texto del idioma. Ej: EU_
        String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
        // Número del idioma. Ej: 10
        Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
        // Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);
        Integer bookID = (Integer) session.getAttribute(Keys.J_BOOK);
        int enabled = 1;
        PrintWriter writer = response.getWriter();

        try {
            // Transformamos el xml mediante la xsl en html.
            // Los errores pueden ser de comunicación, de validación, de transformación, etc...
            Document xmlDocument = reportsUseCase.getReportsList(useCaseConf, bookID, (Integer) reportsType.get(opcion));
            String xslPath = ContextUtil.getRealPath(session.getServletContext(), XSL_REPORT_RELATIVE_PATH);
            Templates cachedXSLT = factory.newTemplates(new StreamSource(new InputStreamReader(new BufferedInputStream(
                    new FileInputStream(xslPath)))));
            Transformer transformer = cachedXSLT.newTransformer();
            DocumentSource source = new DocumentSource(xmlDocument);

            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
        } catch (ReportException e) {
            _logger.fatal("Error en el informe", e);
            ResponseUtils.generateJavaScriptError(writer, e);
        } catch (ValidationException e) {
            _logger.fatal("Error de validation", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
        } catch (BookException e) {
            _logger.fatal("Error en el libro", e);
            ResponseUtils.generateJavaScriptError(writer, e);
        } catch (SessionException e) {
            _logger.fatal("Error en la sesion", e);
            ResponseUtils.generateJavaScriptLogSessionExpiredVldRes(writer, e.getMessage(), idioma, numIdioma, enabled);
            //ResponseUtils.generateJavaScriptError(response, e);
        } catch (TransformerConfigurationException e) {
            _logger.fatal("Error recuperando la lista", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_REPORT_OBJ));
        } catch (TransformerFactoryConfigurationError e) {
            _logger.fatal("Error recuperando la lista", e);
            e.printStackTrace(System.err);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_REPORT_OBJ));
        } catch (TransformerException e) {
            _logger.fatal("Error recuperando la lista", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_REPORT_OBJ));
        } catch (Exception e) {
            _logger.fatal("Error recuperando la lista", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_REPORT_OBJ));
        }
    }

}