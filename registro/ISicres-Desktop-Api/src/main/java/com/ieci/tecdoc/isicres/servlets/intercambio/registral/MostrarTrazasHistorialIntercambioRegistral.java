/**
 *
 * @author IECISA
 *
 * Este servlet se invoca para mostrar el historial del intercambio
 * Registral de un registro
 *
 */

package com.ieci.tecdoc.isicres.servlets.intercambio.registral;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.web.util.ContextoAplicacionUtil;

import es.ieci.tecdoc.fwktd.sir.core.vo.TrazabilidadVO;
import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;

/**
 * Servlet que muestra las trazas para un intercambio registral
 * @author Iecisa
 * @version $Revision$
 *
 */
public class MostrarTrazasHistorialIntercambioRegistral extends HttpServlet {

	private static Logger _logger = Logger.getLogger(MostrarTrazasHistorialIntercambioRegistral.class);
	private static final long serialVersionUID = 1L;

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

        IntercambioRegistralManager intercambioManager =  IsicresManagerProvider.getInstance().getIntercambioRegistralManager();

        UsuarioVO usuarioActual = null;
        String idIntercambio = RequestUtils.parseRequestParameterAsString(request, "idIntercambio");

		try{

			List<TrazabilidadVO> trazasIntercambio = intercambioManager.getTrazasIntercambioRegistral(idIntercambio);
			request.setAttribute("trazasIntercambio", trazasIntercambio);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/intercambio/include/trazasHistorialIntercambio.jsp");
			dispatcher.forward(request, response);
		}catch (Exception e) {
			_logger.error("Ha ocurrido un error al obtener las trazas del historial de intercambio registral.", e);

			String error = RBUtil.getInstance(ContextoAplicacionUtil.getCurrentLocale(usuarioActual)).getProperty(Keys.I18N_ISICRESIR_ERROR_SHOW_HISTORIAL_INTERCAMBIO);
			request.setAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR, error);

			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/jsp/intercambio/include/trazasHistorialIntercambio.jsp");
			rd.forward(request, response);
		}
    }

}
