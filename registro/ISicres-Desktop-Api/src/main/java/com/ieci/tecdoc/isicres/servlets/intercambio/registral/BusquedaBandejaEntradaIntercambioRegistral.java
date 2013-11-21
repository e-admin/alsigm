package com.ieci.tecdoc.isicres.servlets.intercambio.registral;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

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
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.types.CriterioBusquedaIREntradaEnum;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.types.OperadorCriterioBusquedaIREnum;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaEntradaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriterioBusquedaIREntradaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIREntradaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralEntradaEnumVO;
import es.ieci.tecdoc.isicres.intercambio.registral.util.IntercambioRegistralManejarExcepciones;

public class BusquedaBandejaEntradaIntercambioRegistral extends HttpServlet{

	private static final String BANDEJA_ENTRADA_INTERCAMBIO_REGISTRAL_JSP_PATH = "/bandejaEntradaBusquedaIntercambioRegistral.jsp";
	private static Logger logger = Logger.getLogger(BusquedaBandejaEntradaIntercambioRegistral.class);
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

		setHeaders(resp);

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
			String identificadorIntercambio = req.getParameter("identificadorIntercambio");

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
				logger.error("No hay ningún libro de intercambio para este usuario.");

				String error = RBUtil.getInstance(usuario.getConfiguracionUsuario().getLocale()).getProperty(Keys.I18N_ISICRESIR_INPUT_DESKTOP_ERROR);
				req.setAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR, error);

				RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher(BANDEJA_ENTRADA_INTERCAMBIO_REGISTRAL_JSP_PATH);
				rd.forward(req, resp);
			}else{
				CriteriosBusquedaIREntradaVO criterios = new CriteriosBusquedaIREntradaVO();

				addCriterioOficina(idOficina, criterios);
				addCriterioIdIntercambio(identificadorIntercambio, criterios);


				//obtenemos la bandeja de entrada para la oficina con la que esta conectado el usuario y el estado solicitado
				EstadoIntercambioRegistralEntradaEnumVO estadoIntercambioRegistralEntradaEnumVO = EstadoIntercambioRegistralEntradaEnumVO.getEnum(estado);

				addCriterioEstado(criterios,
						estadoIntercambioRegistralEntradaEnumVO);

				List<BandejaEntradaItemVO> bandejaEntrada = new LinkedList<BandejaEntradaItemVO>();

				bandejaEntrada = intercambioManager.findBandejaEntradaByCriterios(estadoIntercambioRegistralEntradaEnumVO, criterios);

				req.setAttribute("bandejaEntrada", bandejaEntrada);
				req.setAttribute("identificadorIntercambio", identificadorIntercambio);
				req.getSession().setAttribute("estado", estado);
				req.getSession().setAttribute("tipoBandeja", "1");
				req.getSession().setAttribute("librosIntercambio", librosIntercambio);
				req.getSession().setAttribute("librosEntrada", librosEntradaIntercambio);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(BANDEJA_ENTRADA_INTERCAMBIO_REGISTRAL_JSP_PATH);
				dispatcher.forward(req, resp);
			}

		}catch (IntercambioRegistralException irEx) {
			logger.error("Ha ocurrido un error al obtener la bandeja de entrada de Intercambio Regisral.", irEx);
			req.setAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR, IntercambioRegistralManejarExcepciones.getInstance().manejarExcepcion(irEx, req.getLocale()));
			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/intercambioRegistralResult.jsp");
			rd.forward(req, resp);

		}catch (Exception e) {
			logger.error("Ha ocurrido un error al obtener la bandeja de entrada de intercambio registral.", e);

			String error = RBUtil.getInstance(ContextoAplicacionUtil.getCurrentLocale(usuario)).getProperty(Keys.I18N_ISICRESIR_INPUT_DESKTOP_ERROR);
			req.setAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR, error);

			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher(BANDEJA_ENTRADA_INTERCAMBIO_REGISTRAL_JSP_PATH);
			rd.forward(req, resp);
		}

	}

	private void setHeaders(HttpServletResponse resp) {
		resp.setDateHeader("Expires", 0);
		resp.setHeader("Cache-Control", "no-cache");
		resp.setHeader("Pragma", "no-cache");
		resp.setContentType("text/html; charset=UTF-8");
	}

	private void addCriterioEstado(
			CriteriosBusquedaIREntradaVO criterios,
			EstadoIntercambioRegistralEntradaEnumVO estadoIntercambioRegistralEntradaEnumVO) {
		CriterioBusquedaIREntradaVO criterioEstado = new CriterioBusquedaIREntradaVO();
		criterioEstado.setNombre(CriterioBusquedaIREntradaEnum.STATE);
		criterioEstado.setOperador(OperadorCriterioBusquedaIREnum.EQUAL);
		criterioEstado.setValor(estadoIntercambioRegistralEntradaEnumVO);

		criterios.addCriterioVO(criterioEstado);
	}

	private void addCriterioOficina(Integer idOficina,
			CriteriosBusquedaIREntradaVO criterios) {
		CriterioBusquedaIREntradaVO criterioOficina = new CriterioBusquedaIREntradaVO();
		criterioOficina.setNombre(CriterioBusquedaIREntradaEnum.ID_OFICINA);
		criterioOficina.setOperador(OperadorCriterioBusquedaIREnum.EQUAL);
		criterioOficina.setValor(idOficina);
		criterios.addCriterioVO(criterioOficina);
	}

	private void addCriterioIdIntercambio(String identificadorIntercambio,
			CriteriosBusquedaIREntradaVO criterios) {
		if (StringUtils.isNotEmpty(identificadorIntercambio)){
			CriterioBusquedaIREntradaVO criterioIdentificadorIntercambio = new CriterioBusquedaIREntradaVO();
			criterioIdentificadorIntercambio.setNombre(CriterioBusquedaIREntradaEnum.ID_EXCHANGE_SIR);
			criterioIdentificadorIntercambio.setOperador(OperadorCriterioBusquedaIREnum.LIKE);
			criterioIdentificadorIntercambio.setValor(identificadorIntercambio);
			criterios.addCriterioVO(criterioIdentificadorIntercambio);
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
