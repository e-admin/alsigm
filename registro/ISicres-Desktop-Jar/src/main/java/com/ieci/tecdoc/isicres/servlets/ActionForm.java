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
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.validationlist.ValidationListUseCase;

/**
 *
 * Este servlet permite validar el código introducido en los campos:
 *
 * - origen y destino del formulario modificar.
 *
 * - Otras oficinas del Usuario
 *
 * - Interesados
 *
 * @author jcebrien
 *
 */
public class ActionForm extends HttpServlet implements Keys {

	private static Logger _logger = Logger.getLogger(ActionForm.class);
    private ValidationListUseCase validationUseCase = null;

    public void init() throws ServletException {
        super.init();

        validationUseCase = new ValidationListUseCase();

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

        // identificador de archivo de usuario.
        String code = RequestUtils.parseRequestParameterAsString(request, "Code");
        // Identificador de registro a copiar.
        int initValue = RequestUtils.parseRequestParameterAsint(request, "InitValue");
        // identificador de campo.
        int fldId = RequestUtils.parseRequestParameterAsint(request, "FldId");
        // Tipo de validación.
        String action = RequestUtils.parseRequestParameterAsString(request, "Action");
        // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();
        // Texto del idioma. Ej: EU_
        String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
        // Número del idioma. Ej: 10
        Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
        // Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);
	  PrintWriter writer = response.getWriter ();
        try {
        	if (action.equals("ValidateUnit")){
                writer.write(validationUseCase.getValidateUnitActionForm(useCaseConf, action, fldId, initValue, code));
            	writer.flush();
            	writer.close();
        	}
        	if (action.equals("OtherOffices")){
                writer.write(validationUseCase.getOtherOfficesActionForm(useCaseConf, action, fldId, initValue));
            	writer.flush();
            	writer.close();
        	}
        	/*
        	 * Esto ha sido reemplazado por el CONTROLLER
        	if (action.equals("ValidateInt")){
                writer.write(validationUseCase.getValidateIntActionForm(useCaseConf, action, fldId, initValue, code));
            	writer.flush();
            	writer.close();
        	}*/
        } catch (ValidationException e) {
            _logger.fatal("Error de validacion", e);
            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
        } catch (AttributesException e) {
            _logger.fatal("Error en los atributos", e);
            ResponseUtils.generateJavaScriptError(writer , e, useCaseConf.getLocale());
        } catch (SessionException e) {
            _logger.fatal("Error en la sesion", e);
            ResponseUtils.generateJavaScriptLogSessionExpiredValidateUnit(writer, e.getMessage(), idioma, numIdioma);
        } catch (Exception e) {
            _logger.fatal("Error al cargar los valores", e);
            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_EXCEPTION_IMPOSSIBLETOLOADVALUES));
        }

    }

}