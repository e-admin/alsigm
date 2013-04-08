package com.ieci.tecdoc.isicres.servlets.intercambio.registral;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.web.util.ContextoAplicacionUtil;

import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.business.manager.LibroManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.ContextoAplicacionVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception.IntercambioRegistralException;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaSalidaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaEnumVO;
import es.ieci.tecdoc.isicres.api.web.util.CurrentUserSessionContextUtil;
import es.ieci.tecdoc.isicres.intercambio.registral.util.IntercambioRegistralManejarExcepciones;

public class BandejaSalidaIntercambioRegistral extends HttpServlet{

	private static Logger _logger = Logger.getLogger(BandejaSalidaIntercambioRegistral.class);
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
		
		Integer idOficina = null;
		Integer estadoBusqueda = null;
		Integer libroSeleccionado = null;
		List <BaseLibroVO> librosIntercambio = null;
		ContextoAplicacionVO contextoAplicacion = null;
		UsuarioVO usuario = null;
		
		List<BandejaSalidaItemVO> bandejaSalida = null;

		try{
			//obtenemos los manager a usar
			LibroManager libroManager = IsicresManagerProvider.getInstance().getLibroManager();
			IntercambioRegistralManager intercambioManager =  IsicresManagerProvider.getInstance().getIntercambioRegistralManager();
			
			//obtenemos los parametros a usar en la operativa
			contextoAplicacion = ContextoAplicacionUtil.getContextoAplicacion(req);
			
			usuario = contextoAplicacion.getUsuarioActual();
			
			idOficina = Integer.parseInt(contextoAplicacion.getOficinaActual().getId());
			
			libroSeleccionado = getIdLibroSeleccionado(req);
			
			estadoBusqueda = getEstadoBusqueda(req);
			
			librosIntercambio=getLibrosIntercambio(usuario, libroManager);
					
			// operativa de obtener la bandeja de salida
			bandejaSalida = intercambioManager.getBandejaSalidaIntercambioRegistral(estadoBusqueda,idOficina,libroSeleccionado);

			req.getSession().setAttribute("bandejaSalida", bandejaSalida);
			req.getSession().setAttribute("libroSeleccionado", libroSeleccionado);
			req.getSession().setAttribute("librosIntercambio", librosIntercambio);
			req.getSession().setAttribute("estado", estadoBusqueda);
			
			req.getSession().setAttribute("tipoBandeja", "0");

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/bandejaSalidaIntercambioRegistral.jsp");
			dispatcher.forward(req, resp);
		}catch (IntercambioRegistralException irEx) {
			_logger.error("Ha ocurrido un error al obtener la bandeja de salida de Intercambio Regisral.", irEx);
			req.setAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR, IntercambioRegistralManejarExcepciones.getInstance().manejarExcepcion(irEx, req.getLocale()));
			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/intercambioRegistralResult.jsp");
			rd.forward(req, resp);

		}catch (Exception e) {
			_logger.error("Ha ocurrido un error al obtener la bandeja de salida de intercambio registral.", e);

			String error = RBUtil.getInstance(ContextoAplicacionUtil.getCurrentLocale(usuario)).getProperty(com.ieci.tecdoc.isicres.desktopweb.Keys.I18N_ISICRESIR_OUTPUT_DESKTOP_ERROR);
			req.setAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR, error);

			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/bandejaSalidaIntercambioRegistral.jsp");
			rd.forward(req, resp);
		}

	}
	
	
	/**
	 * Metodo para obtener los libros de intercambio del usuario actual
	 * @param usuario
	 * @param libroManager
	 * @return
	 */
	protected List <BaseLibroVO> getLibrosIntercambio(UsuarioVO usuario,LibroManager libroManager){
		List <BaseLibroVO> result = new ArrayList();
		
		List librosEntradaIntercambio = libroManager.findLibrosEntradaIntercambioByUser(usuario);
		List librosSalidaIntercambio = libroManager.findLibrosSalidaIntercambioByUser(usuario);
		
		if (librosEntradaIntercambio!=null){
			result.addAll(librosEntradaIntercambio);
		}
		if (librosSalidaIntercambio!=null){
			result.addAll(librosSalidaIntercambio);
		}
		return result;
	}
	
	/**
	 * Metodo para obtener el identificador del libro selecionado por el usuario
	 * @param req
	 * @return
	 */
	protected Integer getIdLibroSeleccionado(HttpServletRequest req){
		Integer result = null;
		String libroSeleccionadoString = req.getParameter("libroSeleccionado");
		//si el no hay libro selecionado se selecionara el primero que encuente
		if(!StringUtils.isEmpty(libroSeleccionadoString)){
			result = Integer.parseInt(libroSeleccionadoString);
		}
		return result;
	}
	
	/**
	 * Metodo para obtener el estado por el que buscar el intercambio de salida
	 * @param req
	 * @return
	 */
	protected Integer getEstadoBusqueda(HttpServletRequest req){
		Integer result = EstadoIntercambioRegistralSalidaEnumVO.ENVIADO_VALUE;
		String estadoString = req.getParameter("estadosSalida");
		if(!StringUtils.isEmpty(estadoString))	{
			result = Integer.parseInt(estadoString);
		}
		
		return result;
	}
	
	

}
