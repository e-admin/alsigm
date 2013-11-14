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
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.web.util.ContextoAplicacionUtil;

import es.ieci.tecdoc.fwktd.sir.core.types.TipoRechazoEnum;
import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.business.vo.ContextoAplicacionVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception.IntercambioRegistralException;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.web.util.CurrentUserSessionContextUtil;
import es.ieci.tecdoc.isicres.intercambio.registral.util.IntercambioRegistralManejarExcepciones;

public class RechazarIntercambiosRegistrales extends HttpServlet{

	private static final String BANDEJA_ENTRADA_INTERCAMBIO_REGISTRAL_SERVLET = "/BandejaEntradaIntercambioRegistral.do";
	private static Logger _logger = Logger.getLogger(RechazarIntercambiosRegistrales.class);
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
		UseCaseConf useCaseConf = (UseCaseConf) mySession.getAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.J_USECASECONF);

		ContextoAplicacionVO contextoAplicacion=null;


		String paramUrlRequestDispatcher = req.getParameter("requestDispatcherUrl");
		String urlRequestDispatcher = BANDEJA_ENTRADA_INTERCAMBIO_REGISTRAL_SERVLET;
		if (StringUtils.isNotBlank(paramUrlRequestDispatcher)){
			urlRequestDispatcher = paramUrlRequestDispatcher;
		}

		try{

			contextoAplicacion=ContextoAplicacionUtil.getContextoAplicacion(req);

			String[] registrosSeleccionados = req.getParameterValues("checkRegistro");
			String motivoRechazo = req.getParameter("motivoRechazo");
			IntercambioRegistralManager intercambioManager =  IsicresManagerProvider.getInstance().getIntercambioRegistralManager();

			for (String idIntercambioRegistral : registrosSeleccionados) {
				 intercambioManager.rechazarIntercambioRegistralEntradaById(idIntercambioRegistral, Integer.toString(TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_INICIAL.getValue()),motivoRechazo);

			}
			String mensaje = RBUtil.getInstance(useCaseConf.getLocale()).getProperty(Keys.I18N_ISICRESIR_REJECT_OK);
			req.setAttribute(Keys.REQUEST_MSG, mensaje);

			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher(urlRequestDispatcher);

			rd.forward(req, resp);
		}catch (IntercambioRegistralException irEx) {
			_logger.error("Ha ocurrido un error al rechazar un Intercambio Regisral.", irEx);
			req.setAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR, IntercambioRegistralManejarExcepciones.getInstance().manejarExcepcion(irEx, req.getLocale()));
			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/intercambioRegistralResult.jsp");
			rd.forward(req, resp);

		}catch (Exception e) {
			_logger.error("Ha ocurrido un error indeterminado al rechazar los registros.", e);

			String error = RBUtil.getInstance(useCaseConf.getLocale()).getProperty(com.ieci.tecdoc.isicres.desktopweb.Keys.I18N_ISICRESIR_REJECT_ERROR);
			req.setAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR, error);

			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher(urlRequestDispatcher);
			rd.forward(req, resp);
		}
	}

}
