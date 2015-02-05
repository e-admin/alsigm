/**
 * 
 */
package com.ieci.tecdoc.isicres.servlets.intercambio.registral;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
public class MostrarIntercambioRegistral extends HttpServlet {

	private static Logger logger = Logger.getLogger(MostrarIntercambioRegistral.class);
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		doWork(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doWork(req, resp);

	}

	protected void doWork(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("doWork(HttpServletRequest, HttpServletResponse) - start");
		}
		
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("text/html; charset=UTF-8");
		
		IntercambioRegistralManager intercambioManager =  IsicresManagerProvider.getInstance().getIntercambioRegistralManager();
	      
		
		String idIntercambio = request.getParameter("idIntercambio");
		AsientoRegistralVO asientoRegistralVO = null;
		try {
			asientoRegistralVO = intercambioManager.getIntercambioRegistralByIdIntercambio(idIntercambio);
		} catch (Exception e) {
			// TODO Bloque catch auto-generado
			logger.error("ERROR obtener el asiento registral con el identificador" + idIntercambio,e);
		}
		
					
		request.setAttribute("asientoRegistral", asientoRegistralVO);
		request.getRequestDispatcher("WEB-INF/jsp/intercambio/mostrarIntercambio.jsp").include(request, response);

		if (logger.isDebugEnabled()) {
			logger.debug("doWork(HttpServletRequest, HttpServletResponse) - end");
		}
	}

}
