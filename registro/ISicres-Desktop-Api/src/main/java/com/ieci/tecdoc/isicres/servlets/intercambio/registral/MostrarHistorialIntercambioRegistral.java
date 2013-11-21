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
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.web.util.ContextoAplicacionUtil;

import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.business.vo.ContextoAplicacionVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralEntradaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralSalidaVO;
import es.ieci.tecdoc.isicres.api.web.util.CurrentUserSessionContextUtil;

public class MostrarHistorialIntercambioRegistral extends HttpServlet {

	private static Logger _logger = Logger.getLogger(MostrarHistorialIntercambioRegistral.class);
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

        String idLibro = null;
        String idRegistro = null;
        String idOficina = null;
        UsuarioVO usuarioActual = null;

		try{


			//seteamos el contexto
			ContextoAplicacionVO contextoAplicacion = ContextoAplicacionUtil.getContextoAplicacion(request);
			//obtenemos los parámetros necesarios
			idLibro = contextoAplicacion.getRegistroActual().getIdLibro();
			idRegistro = contextoAplicacion.getRegistroActual().getIdRegistro();
			idOficina = contextoAplicacion.getOficinaActual().getId();
			usuarioActual = contextoAplicacion.getUsuarioActual();

			//obtenemos la informacion del intercambio registral de entrada
			List<IntercambioRegistralEntradaVO> historialIntercambioRegistralEntrada = intercambioManager
					.getHistorialIntercambioRegistralEntrada(idLibro,
							idRegistro,null,false);



			//obtenemos la informacion del intercambio registral de salida
			List<IntercambioRegistralSalidaVO> historialIntercambioRegistralSalida = intercambioManager
					.getHistorialIntercambioRegistralSalida(
							idLibro, idRegistro,null,false);

			//comprobamos si los historial tanto el de entrada como el de salida son vacios
			if((historialIntercambioRegistralEntrada.size()>0) || (historialIntercambioRegistralSalida.size() >0)){
				request.setAttribute("historialIntercambioEntrada", historialIntercambioRegistralEntrada);
				request.setAttribute("historialIntercambioSalida", historialIntercambioRegistralSalida);
			}else{
				//si ambos son vacios devolvemos un mensaje de error
				if(_logger.isDebugEnabled()){
					_logger.debug("No se han encontrado elementos para generar el historial del intercambio registral");
				}
				String error = RBUtil.getInstance(ContextoAplicacionUtil.getCurrentLocale(usuarioActual)).getProperty(Keys.I18N_ISICRESIR_ERROR_NO_DATA_SHOW_HISTORIAL_INTERCAMBIO);
				request.setAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR, error);
			}

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/historialIntercambio.jsp");
			dispatcher.forward(request, response);
		}catch (Exception e) {
			_logger.error("Ha ocurrido un error al obtener el historial de intercambio registral.", e);

			String error = RBUtil.getInstance(ContextoAplicacionUtil.getCurrentLocale(usuarioActual)).getProperty(Keys.I18N_ISICRESIR_ERROR_SHOW_HISTORIAL_INTERCAMBIO);
			request.setAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR, error);

			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/historialIntercambio.jsp");
			rd.forward(request, response);
		}
    }

}
