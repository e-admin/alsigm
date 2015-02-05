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
import java.rmi.RemoteException;

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
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.validationlist.ValidationListUseCase;

import es.ieci.tecdoc.fwktd.core.config.web.ContextUtil;

/**
 * Este servlet se invoca para obtener la lista de direcciones de una persona.
 * 
 * @author jcebrien
 *  
 */
public class VldDirInter extends HttpServlet implements Keys {

	private static Logger _logger = Logger.getLogger(VldDirInter.class);
    ValidationListUseCase validationListUseCase = null;
    TransformerFactory factory = null;

    public void init() throws ServletException {
        super.init();

        validationListUseCase = new ValidationListUseCase();
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
        
        // Identificador de persona.
        Integer idPerson = RequestUtils.parseRequestParameterAsInteger(request, "PersonId");
        // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();
        // Texto del idioma. Ej: EU_
        String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
        // Número del idioma. Ej: 10
        Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
        // Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);
        /////////////////////////////
	  PrintWriter writer = response.getWriter  ();
        try {
            
	        if (_logger.isDebugEnabled()) {
	        	_logger.debug("idPerson servlet ["+idPerson+"]");
	        }
            
            
            Object xmlDocument = validationListUseCase.getVldDirection(useCaseConf, idPerson, true, 0);
            if (xmlDocument != null) {
                
    	        if (_logger.isDebugEnabled()) {
    	        	_logger.debug("xmlDocument servlet ["+xmlDocument+"]");
    	        }
    	        
                if (xmlDocument instanceof String) {
                    ResponseUtils.generateJavaScriptLogVldDirection(writer , RBUtil.getInstance(useCaseConf.getLocale())
                            .getProperty((String) xmlDocument));
                } else {
                	String xslPath = ContextUtil.getRealPath(session.getServletContext(), XSL_VLDDIRINTER_RELATIVE_PATH);
                	StreamSource s = new StreamSource(new InputStreamReader(new BufferedInputStream(
                            new FileInputStream(xslPath))));
                    Templates cachedXSLT = factory.newTemplates(s);
                    Transformer transformer = cachedXSLT.newTransformer();
                    DocumentSource source = new DocumentSource((Document) xmlDocument);

                    StreamResult result = new StreamResult(writer );
                    transformer.transform(source, result);
                }
            }
        } catch (RemoteException e) {
            _logger.fatal("Error de comunicaciones", e);
            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_EXCEPTION_REMOTEEXCEPTION));
        } catch (ValidationException e) {
            _logger.fatal("Error de validacion", e);
            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
        } catch (AttributesException e) {
            _logger.fatal("Error en los atributos", e);
            ResponseUtils.generateJavaScriptError(writer , e, useCaseConf.getLocale());
        } catch (SessionException e) {
            _logger.fatal("Error en la sesion", e);
            ResponseUtils.generateJavaScriptLogSessionExpiredProvCityDir(writer, e.getMessage());
            //ResponseUtils.generateJavaScriptError(writer , e);
        } catch (TransformerConfigurationException e) {
            _logger.fatal("Error al recuperar la lista", e);
            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_DIRECTION_OBJ));
        } catch (TransformerFactoryConfigurationError e) {
            _logger.fatal("Error al recuperar la lista", e);
            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_DIRECTION_OBJ));
        } catch (TransformerException e) {
            _logger.fatal("Error al recuperar la lista", e);
            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_DIRECTION_OBJ));
        } catch (Exception e) {
            _logger.fatal("Error al recuperar la lista", e);
            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_DIRECTION_OBJ));
        }

    }

}