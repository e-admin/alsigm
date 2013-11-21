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

import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.web.util.ContextoAplicacionUtil;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.business.vo.ContextoAplicacionVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception.IntercambioRegistralException;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionIntercambioRegistralVO;
import es.ieci.tecdoc.isicres.intercambio.registral.util.IntercambioRegistralManejarExcepciones;

public class ReenviarIntercambiosRegistrales extends HttpServlet{

	private static final String BANDEJA_ENTRADA_INTERCAMBIO_REGISTRAL_SERVLET = "/BandejaEntradaIntercambioRegistral.do";
	private static Logger _logger = Logger.getLogger(ReenviarIntercambiosRegistrales.class);
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
		String entidad = useCaseConf.getEntidadId();
		ContextoAplicacionVO contextoAplicacion=null;

		String paramUrlRequestDispatcher = req.getParameter("requestDispatcherUrl");
		String urlRequestDispatcher = BANDEJA_ENTRADA_INTERCAMBIO_REGISTRAL_SERVLET;
		if (StringUtils.isNotBlank(paramUrlRequestDispatcher)){
			urlRequestDispatcher = paramUrlRequestDispatcher;
		}

		try{
			contextoAplicacion=ContextoAplicacionUtil.getContextoAplicacion(req);

			String[] registrosSeleccionados = req.getParameterValues("checkRegistro");
			String entityCode = req.getParameter("entityCode");
			String entityName = req.getParameter("entityName");
			String tramunitCode = req.getParameter("tramunitCode");
			String tramunitName = req.getParameter("tramunitName");
			String observaciones = req.getParameter("observaciones");
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					useCaseConf.getSessionID());
			ScrOfic oficina = (ScrOfic)cacheBag.get(HibernateKeys.HIBERNATE_ScrOfic);
			IntercambioRegistralManager intercambioManager =  IsicresManagerProvider.getInstance().getIntercambioRegistralManager();
			UnidadTramitacionIntercambioRegistralVO unidadTramitacionDestino = new UnidadTramitacionIntercambioRegistralVO();
			unidadTramitacionDestino.setCodeEntity(entityCode);
			unidadTramitacionDestino.setNameEntity(entityName);
			unidadTramitacionDestino.setCodeTramunit(tramunitCode);
			unidadTramitacionDestino.setNameTramunit(tramunitName);

			for (String idIntercambioRegistral : registrosSeleccionados) {
				//intercambioManager.reenviarIntercambioRegistralEntradaById(idIntercambioRegistral, "usuario","contacto",observaciones,unidadTramitacionDestino);
				intercambioManager.reenviarIntercambioRegistralEntradaById(idIntercambioRegistral, unidadTramitacionDestino, observaciones);

			}
			String mensaje = RBUtil.getInstance(useCaseConf.getLocale()).getProperty(Keys.I18N_ISICRESIR_SEND_OK);
			req.setAttribute(Keys.REQUEST_MSG, mensaje);

			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher(urlRequestDispatcher);
			rd.forward(req, resp);

		}catch (IntercambioRegistralException irEx) {
			_logger.error("Ha ocurrido un error al reenviar un Intercambio Regisral.", irEx);
			req.setAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR, IntercambioRegistralManejarExcepciones.getInstance().manejarExcepcion(irEx, req.getLocale()));
			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/intercambioRegistralResult.jsp");
			rd.forward(req, resp);

		}catch (Exception e) {
			_logger.error("Ha ocurrido un error al reenviar los registros. Consulte su administrador", e);

			String error = RBUtil.getInstance(useCaseConf.getLocale()).getProperty(Keys.I18N_ISICRESIR_SEND_ERROR);
			req.setAttribute(Keys.REQUEST_ERROR, error);

			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher(urlRequestDispatcher);
			rd.forward(req, resp);
		}
	}

}
