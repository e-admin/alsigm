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
import org.springframework.util.CollectionUtils;

import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.web.util.ContextoAplicacionUtil;

import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.business.manager.LibroManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.ContextoAplicacionVO;
import es.ieci.tecdoc.isicres.api.business.vo.LibroEntradaVO;
import es.ieci.tecdoc.isicres.api.business.vo.LibroSalidaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception.IntercambioRegistralException;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaEntradaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralEntradaEnumVO;
import es.ieci.tecdoc.isicres.api.web.util.CurrentUserSessionContextUtil;
import es.ieci.tecdoc.isicres.intercambio.registral.util.IntercambioRegistralManejarExcepciones;

public class BandejaEntradaIntercambioRegistral extends HttpServlet{

	private static Logger _logger = Logger.getLogger(BandejaEntradaIntercambioRegistral.class);
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
		
		//datos necesarios para la operativa
		ContextoAplicacionVO contexto = null;
		UsuarioVO usuario = null;
		Integer estado = null;
		Integer idOficina = null;
		List<LibroEntradaVO> librosEntradaIntercambio = null;
		List<LibroSalidaVO> librosSalidaIntercambio = null;
		List <BaseLibroVO> librosIntercambio = null;
		

		try{

			//obtenemos los manager a usar
			LibroManager libroManager = IsicresManagerProvider.getInstance().getLibroManager();
			IntercambioRegistralManager intercambioManager =  IsicresManagerProvider.getInstance().getIntercambioRegistralManager();
			
			//obtenemos el estado a buscar			
			estado = getEstadoBusqueda(req);

			//obtenemos y seteamos el contexto de aplicacion para usar en el api
			contexto = ContextoAplicacionUtil.getContextoAplicacion(req);
			usuario = contexto.getUsuarioActual();
			idOficina = Integer.parseInt(contexto.getOficinaActual().getId());
			
			//obtenemos los libros de entrada habilitados para intercambio registral del usuario actual
			// cuando aceptamos un intercambio este queda reflejado en un libro de entrada
			librosEntradaIntercambio = getLibrosEntradaIntercambio(usuario, libroManager);
			librosSalidaIntercambio = getLibrosSalidaIntercambio(usuario, libroManager);
			librosIntercambio = getAllLibrosIntercambio(librosEntradaIntercambio,librosSalidaIntercambio );
			
			if(CollectionUtils.isEmpty(librosEntradaIntercambio)){
				_logger.error("No hay ningún libro de intercambio para este usuario.");

				String error = RBUtil.getInstance(usuario.getConfiguracionUsuario().getLocale()).getProperty(Keys.I18N_ISICRESIR_INPUT_DESKTOP_ERROR);
				req.setAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR, error);

				RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/bandejaEntradaIntercambioRegistral.jsp");
				rd.forward(req, resp);
			}else{
				//obtenemos la bandeja de entrada para la oficina con la que esta conectado el usuario y el estado solicitado
				List<BandejaEntradaItemVO> bandejaEntrada = intercambioManager.getBandejaEntradaIntercambioRegistral(estado,idOficina);
				req.setAttribute("bandejaEntrada", bandejaEntrada);
				req.getSession().setAttribute("estado", estado);
				req.getSession().setAttribute("tipoBandeja", "1");
				req.getSession().setAttribute("librosIntercambio", librosIntercambio);
				req.getSession().setAttribute("librosEntrada", librosEntradaIntercambio);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/bandejaEntradaIntercambioRegistral.jsp");
				dispatcher.forward(req, resp);
			}

		}catch (IntercambioRegistralException irEx) {
			_logger.error("Ha ocurrido un error al obtener la bandeja de entrada de Intercambio Regisral.", irEx);
			req.setAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR, IntercambioRegistralManejarExcepciones.getInstance().manejarExcepcion(irEx, req.getLocale()));
			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/intercambioRegistralResult.jsp");
			rd.forward(req, resp);

		}catch (Exception e) {
			_logger.error("Ha ocurrido un error al obtener la bandeja de entrada de intercambio registral.", e);

			String error = RBUtil.getInstance(ContextoAplicacionUtil.getCurrentLocale(usuario)).getProperty(Keys.I18N_ISICRESIR_INPUT_DESKTOP_ERROR);
			req.setAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR, error);

			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/bandejaEntradaIntercambioRegistral.jsp");
			rd.forward(req, resp);
		}

	}
	
	
	
	
	/**
	 * Metodo para obtener los libros de entrada del usuario actual
	 * @param usuario
	 * @param libroManager
	 * @return
	 */
	protected List <LibroEntradaVO> getLibrosEntradaIntercambio(UsuarioVO usuario, LibroManager libroManager){ 
		List <LibroEntradaVO> result = libroManager.findLibrosEntradaIntercambioByUser(usuario);
		return result;
	}
	
	/**
	 * Metodo para obtener los libros de entrada del usuario actual
	 * @param usuario
	 * @param libroManager
	 * @return
	 */
	protected List <LibroSalidaVO> getLibrosSalidaIntercambio(UsuarioVO usuario, LibroManager libroManager){ 
		List <LibroSalidaVO> result = libroManager.findLibrosSalidaIntercambioByUser(usuario);
		return result;
	}
	
	protected List <BaseLibroVO> getAllLibrosIntercambio(List <LibroEntradaVO> librosEntradaIntercambio, List <LibroSalidaVO> librosSalidaIntercambio ){
		List <BaseLibroVO> result = new ArrayList<BaseLibroVO>();
		
		if (librosEntradaIntercambio !=null){
			result.addAll(librosEntradaIntercambio);
		}
		
		if (librosSalidaIntercambio !=null){
			result.addAll(librosSalidaIntercambio);
		}
		
		return result;
	}
	
	
	
	/**
	 * Metodo para obtener el estado por el que filtrar la bandeja de entrada
	 * @param req
	 * @return
	 */
	protected Integer getEstadoBusqueda(HttpServletRequest req){
		Integer result = EstadoIntercambioRegistralEntradaEnumVO.PENDIENTE_VALUE;
		//obtenemos el estado a buscar			
		String estadoString = req.getParameter("estadosEntrada");
		if(!StringUtils.isEmpty(estadoString)){
			result = Integer.parseInt(estadoString);
		}
		return result;
	}

}
