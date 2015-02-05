package com.ieci.tecdoc.isicres.servlets.intercambio.registral;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.session.folder.FolderSession;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;

import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionIntercambioRegistralVO;

public class BuscarDestinoIntercambioRegistral extends HttpServlet{

	private static Logger _logger = Logger.getLogger(BuscarDestinoIntercambioRegistral.class);
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
		Integer bookId = Integer.valueOf(req.getParameter("ArchiveId"));
		Integer folderId = Integer.valueOf(req.getParameter("FolderId"));

		UseCaseConf useCaseConf = (UseCaseConf) mySession.getAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.J_USECASECONF);
		UnidadTramitacionIntercambioRegistralVO unidadPorDefecto=null;
		try{
			AxSf axsf = FolderSession.getBookFolder(useCaseConf.getSessionID(),
					bookId, folderId, useCaseConf.getLocale(), useCaseConf
					.getEntidadId());
			ScrOrg unidadAdministrativaDestino = axsf.getFld8();
			if(unidadAdministrativaDestino!=null)
			{
				//Consultar mapeo por defecto.
				IntercambioRegistralManager intercambioManager =  IsicresManagerProvider.getInstance().getIntercambioRegistralManager();
				unidadPorDefecto = intercambioManager.getUnidadTramitacionIntercambioRegistralVOByIdScrOrgs(unidadAdministrativaDestino.getId().toString());
			}

			if(unidadPorDefecto!=null)
			{
				req.setAttribute("defaultEntityCode", unidadPorDefecto.getCodeEntity());
				req.setAttribute("defaultEntityName", unidadPorDefecto.getNameEntity());
				req.setAttribute("defaultTramunitCode", unidadPorDefecto.getCodeTramunit());
				req.setAttribute("defaultTramunitName", unidadPorDefecto.getNameTramunit());
			}
			req.setAttribute("bookId", bookId.toString());
			req.setAttribute("folderId", folderId.toString());
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/busquedaDestinoIntercambioRegistral.jsp");
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
