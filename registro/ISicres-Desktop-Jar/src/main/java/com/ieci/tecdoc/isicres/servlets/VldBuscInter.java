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
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.validationlist.ValidationListUseCase;
import com.ieci.tecdoc.common.utils.Configurator;
/**
 * Se invoca para obtener la lista de provincias.
 *
 * @author jcebrien
 *
 */
public class VldBuscInter extends HttpServlet implements Keys{

	private static Logger _logger = Logger.getLogger(VldBuscInter.class);
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

        // Cadena de busqueda para personas físicas.
        String strBuscar = RequestUtils.parseRequestParameterAsString(request, "wherePFis");
        // Cadena de busqueda para personas jurídicas.
        String strBuscar1 = RequestUtils.parseRequestParameterAsString(request, "wherePJur");
        // Cadena de busqueda Razón Social/Primer Apellido.
        Boolean searchPFis = RequestUtils.parseRequestParameterAsBoolean(request, "SearchPFis", Boolean.TRUE);
        // Cadena de busqueda Segundo Apellido.
        Boolean searchPJur  = RequestUtils.parseRequestParameterAsBoolean(request, "SearchPJur", Boolean.TRUE);
        // fila de inicio de busqueda.
        Integer inicio = RequestUtils.parseRequestParameterAsInteger(request, "InitValue");
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

		//Comprobamos si tenemos que mostrar o no las direcciones de interesados
		Integer viewDirInter = new Integer(Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_INTER_DIREC_VIEW));

		try{
        	writer.write(validationListUseCase.getBuscInter(useCaseConf, searchPFis, searchPJur, inicio, strBuscar, strBuscar1, viewDirInter.intValue()));
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
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_BUSCINTER_OBJ));
        } catch (Exception e) {
            _logger.fatal("Error al recuperar la lista", e);
            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_BUSCINTER_OBJ));
        }
    }
}
