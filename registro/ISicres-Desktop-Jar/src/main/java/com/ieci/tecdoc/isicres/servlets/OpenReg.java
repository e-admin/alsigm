package com.ieci.tecdoc.isicres.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.events.exception.EventException;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;


/**
 * Este servlet se invoca cuando se pulsa el botón de abrir registros desde la ventana de
 * resultados de búsqueda
 */
public class OpenReg extends HttpServlet implements Keys {

	private static Logger _logger = Logger.getLogger(OpenReg.class);
	private static final String GUIONBAJO = "_";
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

    	PrintWriter writer = response.getWriter();

    	// lista de registros seleccionados.
        String list = RequestUtils.parseRequestParameterAsString(request,"List");

        // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();

        // Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);
        Integer bookID = (Integer) session.getAttribute(Keys.J_BOOK);

        try {
        	List listIdsRegister = parseList(list);
	        if (_logger.isDebugEnabled()) {
	            for (Iterator it = listIdsRegister.iterator(); it.hasNext();) {
	                _logger.debug("===================> listIdsRegister " +  it.next());
	            }
	        }

	        bookUseCase.openRegisters(useCaseConf, bookID, listIdsRegister);

		} catch (BookException e) {
			_logger.fatal("Error abriendo registros.", e);
			ResponseUtils.generateJavaScriptLog(
					writer,
					RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
							Keys.I18N_ISICRESSRV_ERR_OPENING_REGS));
		} catch (EventException e) {
			_logger.fatal("Error en el evento al abrir registros", e);
			ResponseUtils.generateJavaScriptLog(
					writer,
					RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
							Keys.I18N_ISICRESSRV_ERR_OPENING_REGS));
		} catch (Throwable e) {
			_logger.fatal("Error abriendo registros.", e);
			ResponseUtils.generateJavaScriptLog(
					writer,
					RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
							Keys.I18N_ISICRESSRV_ERR_OPENING_REGS));
		}
    }

    private List parseList(String listIds)  {
    	List result = new ArrayList();
        StringTokenizer tokens = new StringTokenizer(listIds, GUIONBAJO);
        String token = null;
        int i=0;
        while (tokens.hasMoreTokens()) {
	        if (_logger.isDebugEnabled()) {
	        	_logger.debug("i: " + i);
	        }
        	if (i == 0){
        		tokens.nextToken();
        	}
        	token = tokens.nextToken();
	        if (_logger.isDebugEnabled()) {
	        	_logger.debug("Ids : " + token);
	        }
            result.add(new Integer(token));
            i++;
        }
    	return result;
    }

}