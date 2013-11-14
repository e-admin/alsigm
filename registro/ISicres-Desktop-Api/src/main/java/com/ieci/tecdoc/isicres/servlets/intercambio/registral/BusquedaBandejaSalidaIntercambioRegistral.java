package com.ieci.tecdoc.isicres.servlets.intercambio.registral;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.web.util.ContextoAplicacionUtil;

import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.business.manager.LibroManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.ContextoAplicacionVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception.IntercambioRegistralException;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.types.CriterioBusquedaIRSalidaEnum;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.types.OperadorCriterioBusquedaIREnum;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaSalidaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriterioBusquedaIRSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIRSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaEnumVO;
import es.ieci.tecdoc.isicres.intercambio.registral.util.IntercambioRegistralManejarExcepciones;

public class BusquedaBandejaSalidaIntercambioRegistral extends HttpServlet {

	private static final String BANDEJA_SALIDA_BUSQUEDA_INTERCAMBIO_REGISTRAL_JSP_PATH = "/bandejaSalidaBusquedaIntercambioRegistral.jsp";

	private static Logger logger = Logger
			.getLogger(BusquedaBandejaSalidaIntercambioRegistral.class);

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doWork(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doWork(req, resp);
	}

	protected void doWork(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setDateHeader("Expires", 0);
		resp.setHeader("Cache-Control", "no-cache");
		resp.setHeader("Pragma", "no-cache");
		resp.setContentType("text/html; charset=UTF-8");

		Integer idOficina = null;
		Integer estado = null;
		Integer libroSeleccionado = null;
		List<BaseLibroVO> librosIntercambio = null;
		ContextoAplicacionVO contextoAplicacion = null;
		UsuarioVO usuario = null;

		List<BandejaSalidaItemVO> bandejaSalida = null;

		try {
			// obtenemos los manager a usar
			LibroManager libroManager = IsicresManagerProvider.getInstance()
					.getLibroManager();
			IntercambioRegistralManager intercambioManager = IsicresManagerProvider
					.getInstance().getIntercambioRegistralManager();

			// obtenemos los parametros a usar en la operativa
			contextoAplicacion = ContextoAplicacionUtil
					.getContextoAplicacion(req);

			usuario = contextoAplicacion.getUsuarioActual();

			idOficina = Integer.parseInt(contextoAplicacion.getOficinaActual()
					.getId());

			libroSeleccionado = getIdLibroSeleccionado(req);

			estado = getEstadoBusqueda(req);

			String identificadorIntercambio = req
					.getParameter("identificadorIntercambio");

			librosIntercambio = getLibrosIntercambio(usuario, libroManager);

			CriteriosBusquedaIRSalidaVO criterios = new CriteriosBusquedaIRSalidaVO();

			//addCriterioLibro(libroSeleccionado, criterios);

			addCriterioOficina(idOficina, criterios);
			addCriterioIdIntercambio(identificadorIntercambio, criterios);

			// obtenemos la bandeja de entrada para la oficina con la que esta
			// conectado el usuario y el estado solicitado
			EstadoIntercambioRegistralSalidaEnumVO estadoIntercambioRegistralSalidaEnumVO = EstadoIntercambioRegistralSalidaEnumVO
					.getEnum(estado);

			addCriterioEstado(criterios,
					estadoIntercambioRegistralSalidaEnumVO);

			// operativa de obtener la bandeja de salida
			bandejaSalida = intercambioManager.findBandejaSalidaByCriterios(estadoIntercambioRegistralSalidaEnumVO, criterios, libroSeleccionado);

			req.getSession().setAttribute("bandejaSalida", bandejaSalida);
			req.setAttribute("identificadorIntercambio", identificadorIntercambio);

			req.getSession().setAttribute("libroSeleccionado",
					libroSeleccionado);
			req.getSession().setAttribute("librosIntercambio",
					librosIntercambio);
			req.getSession().setAttribute("estado", estado);

			req.getSession().setAttribute("tipoBandeja", "0");

			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(
							BANDEJA_SALIDA_BUSQUEDA_INTERCAMBIO_REGISTRAL_JSP_PATH);
			dispatcher.forward(req, resp);
		} catch (IntercambioRegistralException irEx) {
			logger.error(
					"Ha ocurrido un error al obtener la bandeja de salida de Intercambio Regisral.",
					irEx);
			req.setAttribute(
					com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR,
					IntercambioRegistralManejarExcepciones.getInstance()
							.manejarExcepcion(irEx, req.getLocale()));
			RequestDispatcher rd = getServletConfig().getServletContext()
					.getRequestDispatcher("/intercambioRegistralResult.jsp");
			rd.forward(req, resp);

		} catch (Exception e) {
			logger.error(
					"Ha ocurrido un error al obtener la bandeja de salida de intercambio registral.",
					e);

			String error = RBUtil
					.getInstance(
							ContextoAplicacionUtil.getCurrentLocale(usuario))
					.getProperty(
							com.ieci.tecdoc.isicres.desktopweb.Keys.I18N_ISICRESIR_OUTPUT_DESKTOP_ERROR);
			req.setAttribute(
					com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR,
					error);

			RequestDispatcher rd = getServletConfig().getServletContext()
					.getRequestDispatcher(
							BANDEJA_SALIDA_BUSQUEDA_INTERCAMBIO_REGISTRAL_JSP_PATH);
			rd.forward(req, resp);
		}

	}

	private void addCriterioLibro(Integer libroSeleccionado,
			CriteriosBusquedaIRSalidaVO criterios) {
		if (libroSeleccionado != null){
			CriterioBusquedaIRSalidaVO criterioLibro = new CriterioBusquedaIRSalidaVO();
			criterioLibro.setNombre(CriterioBusquedaIRSalidaEnum.ID_ARCHIVADOR);
			criterioLibro.setOperador(OperadorCriterioBusquedaIREnum.EQUAL);
			criterioLibro.setValor(libroSeleccionado);
			criterios.addCriterioVO(criterioLibro);
		}
	}

	private void addCriterioEstado(
			CriteriosBusquedaIRSalidaVO criterios,
			EstadoIntercambioRegistralSalidaEnumVO estadoIntercambioRegistralSalidaEnumVO) {
		CriterioBusquedaIRSalidaVO criterioEstado = new CriterioBusquedaIRSalidaVO();
		criterioEstado.setNombre(CriterioBusquedaIRSalidaEnum.STATE);
		criterioEstado.setOperador(OperadorCriterioBusquedaIREnum.EQUAL);
		criterioEstado.setValor(estadoIntercambioRegistralSalidaEnumVO);

		criterios.addCriterioVO(criterioEstado);
	}

	private void addCriterioOficina(Integer idOficina,
			CriteriosBusquedaIRSalidaVO criterios) {
		CriterioBusquedaIRSalidaVO criterioOficina = new CriterioBusquedaIRSalidaVO();
		criterioOficina.setNombre(CriterioBusquedaIRSalidaEnum.ID_OFICINA);
		criterioOficina.setOperador(OperadorCriterioBusquedaIREnum.EQUAL);
		criterioOficina.setValor(idOficina);
		criterios.addCriterioVO(criterioOficina);
	}

	private void addCriterioIdIntercambio(String identificadorIntercambio,
			CriteriosBusquedaIRSalidaVO criterios) {
		if (StringUtils.isNotEmpty(identificadorIntercambio)) {
			CriterioBusquedaIRSalidaVO criterioIdentificadorIntercambio = new CriterioBusquedaIRSalidaVO();
			criterioIdentificadorIntercambio
					.setNombre(CriterioBusquedaIRSalidaEnum.ID_EXCHANGE_SIR);
			criterioIdentificadorIntercambio
					.setOperador(OperadorCriterioBusquedaIREnum.LIKE);
			criterioIdentificadorIntercambio.setValor(identificadorIntercambio);
			criterios.addCriterioVO(criterioIdentificadorIntercambio);
		}
	}

	/**
	 * Metodo para obtener los libros de intercambio del usuario actual
	 *
	 * @param usuario
	 * @param libroManager
	 * @return
	 */
	protected List<BaseLibroVO> getLibrosIntercambio(UsuarioVO usuario,
			LibroManager libroManager) {
		List<BaseLibroVO> result = new ArrayList();

		List librosEntradaIntercambio = libroManager
				.findLibrosEntradaIntercambioByUser(usuario);
		List librosSalidaIntercambio = libroManager
				.findLibrosSalidaIntercambioByUser(usuario);

		if (librosEntradaIntercambio != null) {
			result.addAll(librosEntradaIntercambio);
		}
		if (librosSalidaIntercambio != null) {
			result.addAll(librosSalidaIntercambio);
		}
		return result;
	}

	/**
	 * Metodo para obtener el identificador del libro selecionado por el usuario
	 *
	 * @param req
	 * @return
	 */
	protected Integer getIdLibroSeleccionado(HttpServletRequest req) {
		Integer result = null;
		String libroSeleccionadoString = req.getParameter("libroSeleccionado");
		// si el no hay libro selecionado se selecionara el primero que encuente
		if (!StringUtils.isEmpty(libroSeleccionadoString)) {
			result = Integer.parseInt(libroSeleccionadoString);
		}
		return result;
	}

	/**
	 * Metodo para obtener el estado por el que buscar el intercambio de salida
	 *
	 * @param req
	 * @return
	 */
	protected Integer getEstadoBusqueda(HttpServletRequest req) {
		Integer result = EstadoIntercambioRegistralSalidaEnumVO.ENVIADO_VALUE;
		String estadoString = req.getParameter("estadosSalida");
		if (!StringUtils.isEmpty(estadoString)) {
			result = Integer.parseInt(estadoString);
		}

		return result;
	}

}
