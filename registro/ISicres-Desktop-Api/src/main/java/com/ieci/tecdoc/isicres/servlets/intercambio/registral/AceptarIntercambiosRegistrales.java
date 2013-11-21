package com.ieci.tecdoc.isicres.servlets.intercambio.registral;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;

import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.business.vo.ContextoAplicacionVO;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception.IntercambioRegistralException;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.web.util.CurrentUserSessionContextUtil;
import es.ieci.tecdoc.isicres.intercambio.registral.util.IntercambioRegistralManejarExcepciones;

public class AceptarIntercambiosRegistrales extends HttpServlet{

	private static final String BANDEJA_ENTRADA_INTERCAMBIO_REGISTRAL_SERVLET = "/BandejaEntradaIntercambioRegistral.do";
	private static Logger _logger = Logger.getLogger(AceptarIntercambiosRegistrales.class);
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doWork(req,resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doWork(req,resp);
	}

	protected void doWork(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		resp.setDateHeader("Expires", 0);
		resp.setHeader("Cache-Control", "no-cache");
		resp.setHeader("Pragma", "no-cache");
		resp.setContentType("text/html; charset=UTF-8");

		HttpSession mySession = req.getSession();


		ContextoAplicacionVO contextoAplicacion=null;

		IntercambioRegistralManager intercambioManager =  IsicresManagerProvider.getInstance().getIntercambioRegistralManager();

		String paramUrlRequestDispatcher = req.getParameter("requestDispatcherUrl");
		String urlRequestDispatcher = BANDEJA_ENTRADA_INTERCAMBIO_REGISTRAL_SERVLET;
		if (StringUtils.isNotBlank(paramUrlRequestDispatcher)){
			urlRequestDispatcher = paramUrlRequestDispatcher;
		}

		try{

			//seteamos el contxto de aplicacion
			CurrentUserSessionContextUtil currentUserSessionContextUtil= new CurrentUserSessionContextUtil();
			contextoAplicacion=currentUserSessionContextUtil.getContextoAplicacionActual(req);

			String[] registrosSeleccionados = req.getParameterValues("checkRegistro");
			boolean llegoDocFisica = Boolean.valueOf(req.getParameter("docRecibida"));

			String user = contextoAplicacion.getUsuarioActual().getLoginName();

			Integer idOficina = Integer.parseInt(contextoAplicacion.getOficinaActual().getId());
			String codOficina = ((OficinaVO)contextoAplicacion.getOficinaActual()).getCodigoOficina();
			String idLibro = req.getParameter("idLibro");


			for (String idIntercambioRegistral : registrosSeleccionados) {
				 intercambioManager.aceptarIntercambioRegistralEntradaById(idIntercambioRegistral,idLibro, user,idOficina,codOficina,llegoDocFisica);

			}

			String mensaje = RBUtil.getInstance(contextoAplicacion.getUsuarioActual().getConfiguracionUsuario().getLocale()).getProperty(Keys.I18N_ISICRESIR_ACCEPT_OK);
			req.setAttribute(Keys.REQUEST_MSG, mensaje);

			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher(urlRequestDispatcher);
			rd.forward(req, resp);

		}catch (IntercambioRegistralException irEx) {
			_logger.error("Ha ocurrido un error al aceptar el registro.", irEx);
			req.setAttribute("errorMessage", IntercambioRegistralManejarExcepciones.getInstance().manejarExcepcion(irEx, contextoAplicacion.getUsuarioActual().getConfiguracionUsuario().getLocale()));
			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/intercambioRegistralResult.jsp");
			rd.forward(req, resp);

		}catch (Exception e) {
			_logger.error("Ha ocurrido un error al aceptar los registros.", e);

			String error = RBUtil.getInstance(contextoAplicacion.getUsuarioActual().getConfiguracionUsuario().getLocale()).getProperty(Keys.I18N_ISICRESIR_ACCEPT_ERROR);
			req.setAttribute(Keys.REQUEST_ERROR, error);

			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher(urlRequestDispatcher);
			rd.forward(req, resp);
		}
	}

}
