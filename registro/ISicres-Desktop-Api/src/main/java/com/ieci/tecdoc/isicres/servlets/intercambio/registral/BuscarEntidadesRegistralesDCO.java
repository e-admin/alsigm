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

import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;

import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EntidadRegistralDCO;

public class BuscarEntidadesRegistralesDCO extends HttpServlet{

	private static Logger _logger = Logger.getLogger(BuscarEntidadesRegistralesDCO.class);
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

		try{
			IntercambioRegistralManager intercambioManager =  IsicresManagerProvider.getInstance().getIntercambioRegistralManager();

			String codeEntityToFind = req.getParameter("codeEntityToFind");
			String nameEntityToFind = req.getParameter("nameEntityToFind");
			if(StringUtils.isNotEmpty(codeEntityToFind) || StringUtils.isNotEmpty(nameEntityToFind))
			{
				List<EntidadRegistralDCO> listaEntidadesDCO = intercambioManager.buscarEntidadesRegistralesDCO(codeEntityToFind, nameEntityToFind);
				req.setAttribute("listaEntidadesRegistralesDCO", listaEntidadesDCO);
				req.setAttribute("codeEntityToFind", codeEntityToFind);
				req.setAttribute("nameEntityToFind", nameEntityToFind);
			}


			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/listadoEntidadesRegistralesDCO.jsp");
			dispatcher.forward(req, resp);
		}catch (Exception e) {
			_logger.error("Ha ocurrido un error al buscar entidades registrales en el DCO.", e);

			String error = RBUtil.getInstance(useCaseConf.getLocale()).getProperty(com.ieci.tecdoc.isicres.desktopweb.Keys.I18N_ISICRESIR_ERROR_BUSCAR_IN_DCO);
			req.setAttribute("errorMessage", error);

			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/intercambioRegistralResult.jsp");
			rd.forward(req, resp);
		}

	}

}
