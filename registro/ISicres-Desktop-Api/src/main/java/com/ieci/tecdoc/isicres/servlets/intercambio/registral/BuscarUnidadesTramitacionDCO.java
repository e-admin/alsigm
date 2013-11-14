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

import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;

import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionDCO;

public class BuscarUnidadesTramitacionDCO extends HttpServlet{

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

			String tramunitCodeToFind = req.getParameter("tramunitCodeToFind");
			String tramunitNameToFind = req.getParameter("tramunitNameToFind");
			//Si se indica una entidad registral, se buscarán las unid. asociadas a dicha entidad
			if(StringUtils.isNotEmpty(codeEntityToFind)){
				List<UnidadTramitacionDCO> listaUnidadesTramitacionDCO = intercambioManager
				.buscarUnidadesTramitacionDCOByEntidad(codeEntityToFind, tramunitCodeToFind,
						tramunitNameToFind);

				req.setAttribute("listaUnidadesTramitacionDCO", listaUnidadesTramitacionDCO);
				req.setAttribute("tramunitCodeToFind", tramunitCodeToFind);
				req.setAttribute("tramunitNameToFind", tramunitNameToFind);

			}else{
				//buscamos todas las unidades de tramitación
				if(StringUtils.isNotEmpty(tramunitCodeToFind) || StringUtils.isNotEmpty(tramunitNameToFind))
				{
					List<UnidadTramitacionDCO> listaUnidadesTramitacionDCO = intercambioManager
							.buscarUnidadesTramitacionDCO(tramunitCodeToFind,
									tramunitNameToFind);

					req.setAttribute("listaUnidadesTramitacionDCO", listaUnidadesTramitacionDCO);
					req.setAttribute("tramunitCodeToFind", tramunitCodeToFind);
					req.setAttribute("tramunitNameToFind", tramunitNameToFind);

				}
			}

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/listadoUnidadesTramitacionDCO.jsp");
			dispatcher.forward(req, resp);
		}catch (Exception e) {
			_logger.error("Ha ocurrido un error en la búsqueda de unidades de tramitación.", e);

			String error = RBUtil.getInstance(useCaseConf.getLocale()).getProperty(Keys.I18N_ISICRESIR_INPUT_DESKTOP_ERROR);
			req.setAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR, error);

			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/intercambioRegistralResult.jsp");
			rd.forward(req, resp);
		}

	}

}
