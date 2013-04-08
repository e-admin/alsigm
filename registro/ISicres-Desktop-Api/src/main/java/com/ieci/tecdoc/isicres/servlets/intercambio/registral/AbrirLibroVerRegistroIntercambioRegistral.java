package com.ieci.tecdoc.isicres.servlets.intercambio.registral;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.web.util.ContextoAplicacionUtil;

import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.ContextoAplicacionVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

/**
 * Abre un libro para poder visualizar un registro
 * @author iecisa
 *
 */
public class AbrirLibroVerRegistroIntercambioRegistral extends HttpServlet{

	private static Logger _logger = Logger.getLogger(AbrirLibroVerRegistroIntercambioRegistral.class);
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

		try {
			String otherParams="";
			HttpSession session = req.getSession();
			
			//identifiadores de libro y registro a abrir
			String idRegistro = (String) req.getParameter("FolderId");
			String idLibro = (String) req.getParameter("ArchiveId");
			
			
			// seteamos el registro y libro del registro para abrirlo
			// con permiso para acceder
			session.setAttribute(Keys.J_REGISTER, Integer.valueOf(idRegistro));
			session.setAttribute(Keys.J_BOOK, Integer.valueOf(idLibro));
			
			ContextoAplicacionVO contextoAplicacion = ContextoAplicacionUtil
					.getContextoAplicacion(req);

			BaseLibroVO libro = new BaseLibroVO();
			libro.setId(idLibro);
			libro.setIdArchivador(idLibro);
			UsuarioVO usuario = contextoAplicacion.getUsuarioActual();

			// abrimos el libro para verificar permisos
			IsicresManagerProvider.getInstance().getLibroManager().abrirLibro(
					usuario, libro);

			

			// El registro desde la bandeja siempre se abre como  solo lectura
			otherParams="Form=true";
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/default.jsp?"+otherParams);
			dispatcher.forward(req, resp);
		} catch (Exception e) {
			_logger.error("Error al abrir el registro desde la bandeja de salida de intercambio registral", e);
		}

	}

}
