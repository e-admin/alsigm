package com.ieci.tecdoc.isicres.servlets.intercambio.registral;

import java.io.IOException;
import java.util.List;

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
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaEntradaItemVO;

public class MostrarDestinosIntercambioRegistral extends HttpServlet{

	private static Logger _logger = Logger.getLogger(MostrarDestinosIntercambioRegistral.class);
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
		Integer idUnidadAdministrativa = null;
		UseCaseConf useCaseConf = (UseCaseConf) mySession.getAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.J_USECASECONF);
		
		try{
			
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					useCaseConf.getSessionID());
			ScrOfic oficina = (ScrOfic) cacheBag
			.get(HibernateKeys.HIBERNATE_ScrOfic);
			
			if(oficina!=null)
			{
				idUnidadAdministrativa=oficina.getScrOrg().getId();
			}
			String estadoString = req.getParameter("estadosEntrada");
			Integer estado = null;
			if(!StringUtils.isEmpty(estadoString))
			{
				estado = Integer.parseInt(estadoString);
			}
			
			List inList = BookSession.getInBooks(useCaseConf.getSessionID(),
					useCaseConf.getLocale(), useCaseConf.getEntidadId());
			
			IntercambioRegistralManager intercambioManager =  IsicresManagerProvider.getInstance().getIntercambioRegistralManager();

			//¿Qué criterios? ¿sólo estado?
			List<BandejaEntradaItemVO> bandejaEntrada = intercambioManager.getBandejaEntradaIntercambioRegistral(estado,idUnidadAdministrativa);
			req.setAttribute("bandejaEntrada", bandejaEntrada);
			req.getSession().setAttribute("estado", estado);
			req.getSession().setAttribute("tipoBandeja", "1");
			req.getSession().setAttribute("listaLibrosEntrada", inList);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/bandejaEntradaIntercambioRegistral.jsp");
			dispatcher.forward(req, resp);	
		}catch (Exception e) {
			_logger.error("Ha ocurrido un error al obtener la bandeja de entrada de intercambio registral.", e);
			
			String error = RBUtil.getInstance(useCaseConf.getLocale()).getProperty(Keys.I18N_ISICRESIR_INPUT_DESKTOP_ERROR);
			req.setAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR, error);
			
			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/bandejaEntradaIntercambioRegistral.jsp");
			rd.forward(req, resp);
		}
			
	}
	
}
