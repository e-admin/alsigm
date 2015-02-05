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
 * Este servlet se invoca para abrir el historico de distribución de una carpeta.
 * 

 * @author jcebrien
 *
 */
public class DtrFdr extends HttpServlet implements Keys{
 
	private static Logger _logger = Logger.getLogger(DtrFdr.class);
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
    private void doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("text/html; charset=UTF-8");
        
        // identificador de archivo de usuario.
        Integer archiveId = RequestUtils.parseRequestParameterAsInteger(request, "ArchiveId");
        // Identificador de carpeta.
        Integer folderId = RequestUtils.parseRequestParameterAsInteger(request, "FolderId");
        // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();
        // Texto del idioma. Ej: EU_
        String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
        // Número del idioma. Ej: 10
        Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
        // Formulario o no.
        Boolean form = (Boolean) session.getAttribute(Keys.J_OPENFOLDER_FORM);
        // Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);
        //response.getWriter().write(ACTIVATE_TREE_1);
        //response.getWriter().write("LISTA DE DISTRIBUCION");
        PrintWriter writer = response.getWriter();
        try {
                Document xmlDocument = bookUseCase.getDtrFdrResults(useCaseConf, archiveId, folderId.intValue());

                String xslPath = ContextUtil.getRealPath(session.getServletContext(),XSL_DTRLISTFDR_RELATIVE_PATH);
                StreamSource s = new StreamSource(new InputStreamReader(new BufferedInputStream(
                        new FileInputStream(xslPath))));
                Templates cachedXSLT = factory.newTemplates(s);
                Transformer transformer = cachedXSLT.newTransformer();
                DocumentSource source = new DocumentSource(xmlDocument);

                StreamResult result = new StreamResult(writer);
                transformer.transform(source, result);
                
        } catch (ValidationException e) {
            _logger.fatal("Error de validacion", e);
            writer.write(ACTIVATE_TREE_1);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
        } catch (BookException e) {
            _logger.fatal("Error en el libro", e);
            writer.write(ACTIVATE_TREE_1);
            ResponseUtils.generateJavaScriptError(writer, e);
        } catch (SessionException e) {
            _logger.fatal("Error en la sesion", e);
            ResponseUtils.generateJavaScriptLogSessionExpiredDtrfdr(writer, e.getMessage(), idioma, numIdioma);
            //ResponseUtils.generateJavaScriptError(response, e);
        } catch (TransformerConfigurationException e) {
            _logger.fatal("Error en la distribucion", e);
            writer.write(ACTIVATE_TREE_1);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_DTR_OBJ));
        } catch (TransformerFactoryConfigurationError e) {
            _logger.fatal("Error en la distribucion", e);
            writer.write(ACTIVATE_TREE_1);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_DTR_OBJ));
        } catch (TransformerException e) {
            _logger.fatal("Error en la distribucion", e);
            writer.write(ACTIVATE_TREE_1);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_DTR_OBJ));
        } catch (Exception e) {
            _logger.fatal("Error en la distribucion", e);
            writer.write(ACTIVATE_TREE_1);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_DTR_OBJ));
        }
    }
	
	
}
