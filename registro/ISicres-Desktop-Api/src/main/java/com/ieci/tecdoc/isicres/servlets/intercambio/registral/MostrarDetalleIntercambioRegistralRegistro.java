/**
 *
 */
package com.ieci.tecdoc.isicres.servlets.intercambio.registral;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.web.util.ContextoAplicacionUtil;

import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.business.vo.ContextoAplicacionVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralEntradaEnumVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralEntradaVO;

/**
 * Servlet que muestra el detalle de un intercambio registral para un registro
 *
 * @author IECISA
 * @version $Revision$
 *
 */
public class MostrarDetalleIntercambioRegistralRegistro extends HttpServlet {

	private static Logger logger = Logger
			.getLogger(MostrarDetalleIntercambioRegistralRegistro.class);
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doWork(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doWork(req, resp);

	}

	protected void doWork(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("doWork(HttpServletRequest, HttpServletResponse) - start");
		}

		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("text/html; charset=UTF-8");

		IntercambioRegistralManager intercambioManager = IsicresManagerProvider
				.getInstance().getIntercambioRegistralManager();

		String idLibro = null;
		String idRegistro = null;
		UsuarioVO usuarioActual = null;

		AsientoRegistralVO asientoRegistralVO = null;
		try {
			// seteamos el contexto
			ContextoAplicacionVO contextoAplicacion = ContextoAplicacionUtil
					.getContextoAplicacion(request);
			// obtenemos los parámetros necesarios
			idLibro = contextoAplicacion.getRegistroActual().getIdLibro();
			idRegistro = contextoAplicacion.getRegistroActual().getIdRegistro();
			usuarioActual = contextoAplicacion.getUsuarioActual();
			// obtenemos la informacion del intercambio registral de entrada

			Integer estado = EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO
					.getValue();

			Integer idLibroInt = Integer.valueOf(idLibro);
			Integer idRegistroInt = Integer.valueOf(idRegistro);

			IntercambioRegistralEntradaVO intercambio = intercambioManager
					.getIntercambioRegistralEntradaByRegistro(idLibroInt,
							idRegistroInt, estado);
			if (intercambio != null) {
				String idIntercambio = String.valueOf(intercambio
						.getIdIntercambioInterno());
				asientoRegistralVO = intercambioManager
						.getIntercambioRegistralByIdIntercambio(idIntercambio);
			}

			if (asientoRegistralVO != null) {
				request.setAttribute("asientoRegistral", asientoRegistralVO);
				request.getRequestDispatcher(
						"/mostraInformacionIntercambio.jsp").include(request,
						response);
			} else {
				logger.warn("ERROR obtener el asiento registral");
				String error = RBUtil
						.getInstance(
								ContextoAplicacionUtil
										.getCurrentLocale(usuarioActual))
						.getProperty(
								Keys.I18N_ISICRESIR_ERROR_NO_DATA_SHOW_DETALLE_INTERCAMBIO);
				request.setAttribute(
						com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR,
						error);

				RequestDispatcher rd = getServletConfig().getServletContext()
						.getRequestDispatcher(
								"/mostraInformacionIntercambio.jsp");
				rd.forward(request, response);

			}
		} catch (Exception e) {
			logger.error("ERROR obtener el asiento registral", e);
			String error = RBUtil
					.getInstance(
							ContextoAplicacionUtil
									.getCurrentLocale(usuarioActual))
					.getProperty(
							Keys.I18N_ISICRESIR_ERROR_SHOW_DETALLE_INTERCAMBIO);
			request.setAttribute(
					com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR,
					error);

			RequestDispatcher rd = getServletConfig().getServletContext()
					.getRequestDispatcher("/mostraInformacionIntercambio.jsp");
			rd.forward(request, response);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("doWork(HttpServletRequest, HttpServletResponse) - end");
		}

	}

}
