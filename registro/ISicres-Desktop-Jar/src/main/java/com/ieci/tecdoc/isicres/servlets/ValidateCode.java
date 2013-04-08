/**
 * 
 * @author jcebrien
 * 
 */
package com.ieci.tecdoc.isicres.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 * Este servlet permite validar el valor introducido en el campo código de la ventana modal que se muestra al pulsar en
 * el botón editar de la lista de registros. También valida los campos del formulario modificar excepto origen y
 * destino.
 * 
 * @author jcebrien
 *  
 */
public class ValidateCode extends HttpServlet implements Keys {

	private static Logger _logger = Logger.getLogger(ValidateCode.class);
    private BookUseCase bookUseCase = null;

    public void init() throws ServletException {
        super.init();

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
        response.setContentType("text/xml; charset=UTF-8");
        
        // Identificador de la consulta.
        Integer archivePId = RequestUtils.parseRequestParameterAsInteger(request, "ArchivePId");
        // Identificador de campo.
        int fldId = RequestUtils.parseRequestParameterAsint(request, "FldId");
        // Código.
        String code = RequestUtils.parseRequestParameterAsString(request, "Code");
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
		PrintWriter writer = response.getWriter ();
        try {
        	writer.write(bookUseCase.validateQueryParam(useCaseConf, bookID, fldId, code));
        	writer.flush();
        	writer.close();
        } catch (SecurityException e) {
            _logger.fatal("Error de seguridad", e);
            ResponseUtils.generateJavaScriptError(writer , e, useCaseConf.getLocale());
        } catch (ValidationException e) {
            _logger.fatal("Error de validacion", e);
            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
        } catch (BookException e) {
            _logger.fatal("Error en el libro", e);
            ResponseUtils.generateJavaScriptError(writer , e);
        } catch (AttributesException e) {
            _logger.fatal("Error en los atributos", e);
            ResponseUtils.generateJavaScriptError(writer , e, useCaseConf.getLocale());
        } catch (SessionException e) {
            _logger.fatal("Error en la sesion", e);
            ResponseUtils.generateJavaScriptErrorSessionExpired(writer , e, idioma, numIdioma);
            //ResponseUtils.generateJavaScriptError(writer , e);
        } catch (Exception e) {
            _logger.fatal("Error en la validacion del codigo", e);
            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_FDRQRY_OBJ));
        }
    }

}