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
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.validationlist.ValidationListUseCase;
/**
 * Se invoca para obtener la lista de provincias.
 * 
 * @author jcebrien
 *
 */
public class VldPoblacion extends HttpServlet implements Keys{

	private static Logger _logger = Logger.getLogger(VldPoblacion.class);
	ValidationListUseCase validationListUseCase = null;

    public void init() throws ServletException {
        super.init();
        validationListUseCase = new ValidationListUseCase();
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
        response.setContentType("text/xml; charset=UTF-8");
        
		// Obtenemos la sesión asociada al usuario.
	    HttpSession session = request.getSession();
	    // Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
	    // de sesión para este usuario en el servidor de aplicaciones.
	    UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);
		PrintWriter writer = response.getWriter ();
		try{
        	writer.write(validationListUseCase.getVldPoblacion(useCaseConf));
        	writer.flush();
        	writer.close();
        } catch (ValidationException e) {
            _logger.fatal("Error de validacion", e);
            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
        } catch (SessionException e) {
            _logger.fatal("Error en la sesion", e);
            ResponseUtils.generateJavaScriptLogSessionExpiredProvCityDir(writer , e.getMessage());
            //ResponseUtils.generateJavaScriptError(writer , e);
        } catch (TransformerFactoryConfigurationError e) {
            _logger.fatal("Error al recuperar la lista", e);
            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_POBLACION_OBJ));
        } catch (Exception e) {
            _logger.fatal("Error al recuperar la lista", e);
            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_POBLACION_OBJ));
        }
    }
}
