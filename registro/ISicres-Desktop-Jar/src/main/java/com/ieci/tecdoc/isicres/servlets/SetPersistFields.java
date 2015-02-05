/*
 * Created on 10-may-2005
 *
 */
package com.ieci.tecdoc.isicres.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;

/**
 * Este servlet se invoca cuando se pulsa el botón aceptar de la ventana de campos persistentes.
 * 
 * @author jcebrien
 *
 */
public class SetPersistFields extends HttpServlet implements Keys {
	
	private static Logger _logger = Logger.getLogger(SetPersistFields.class);
    private BookUseCase bookUseCase = null;

    public void init() throws ServletException {
        super.init();
        bookUseCase = new BookUseCase();
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
        
        // Identificador de libro de regitro.
        Integer archiveId = RequestUtils.parseRequestParameterAsInteger(request, "ArchiveId");
        // Lista de campos persistentes.
        String fields = RequestUtils.parseRequestParameterAsString(request, "Fields");
        // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();
        // Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);
        Map archiveFields = (Map) session.getAttribute(J_PERSISTFIELDS);
        if (archiveFields.containsKey(archiveId)){
        	archiveFields.remove(archiveId);
        	archiveFields.put(archiveId, fields);
        } else {
        	archiveFields.put(archiveId, fields);
        }
        session.setAttribute(Keys.J_PERSISTFIELDS, archiveFields);
        StringBuffer cadena = new StringBuffer();
        String perFields = null;
        String perFieldsWithId = null;
        for (Iterator it = archiveFields.keySet().iterator(); it.hasNext();) {
        	Integer key = (Integer) it.next();
        	perFields = (String) archiveFields.get(key);
        	if(archiveId == key){
        		perFieldsWithId = perFields;
        	}
        	cadena.append(key.toString());
        	cadena.append("&");
        	cadena.append(perFields);
        	cadena.append("&");
        	cadena.append("#");
        }
		PrintWriter writer = response.getWriter ();
        
        try {
	            bookUseCase.savePersistFields(useCaseConf, cadena.toString());
	            ResponseUtils.generateJavaScriptLogSetPersistFields(writer, perFieldsWithId);
        } catch (RemoteException e) {
            _logger.fatal("Error de comunicaciones", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_EXCEPTION_REMOTEEXCEPTION));
        } catch (ValidationException e) {
            _logger.fatal("Error de validacion", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
        } catch (BookException e) {
            _logger.fatal("Error en el libro", e);
            ResponseUtils.generateJavaScriptError(writer, e);
        } catch (SessionException e) {
            _logger.fatal("Error en la sesion", e);
            ResponseUtils.generateJavaScriptLogSessionExpiredCloseFolder(writer, e.getMessage());
            //ResponseUtils.generateJavaScriptError(response, e);
        } catch (Exception e) {
            _logger.fatal("Error al cerrar el registro", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_SETPERSISTFIELDS_OBJ));
        }
     }
}
