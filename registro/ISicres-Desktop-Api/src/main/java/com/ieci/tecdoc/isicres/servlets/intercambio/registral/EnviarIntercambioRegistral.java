package com.ieci.tecdoc.isicres.servlets.intercambio.registral;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.web.util.ContextoAplicacionUtil;

import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.business.manager.RegistroManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.ContextoAplicacionVO;
import es.ieci.tecdoc.isicres.api.business.vo.LibroEntradaVO;
import es.ieci.tecdoc.isicres.api.business.vo.LibroSalidaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.enums.TipoLibroEnum;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception.IntercambioRegistralException;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionIntercambioRegistralVO;
import es.ieci.tecdoc.isicres.intercambio.registral.util.IntercambioRegistralManejarExcepciones;

/**
 * Envia un intercambio registral desde la propia pantalla del registro
 *
 */
public class EnviarIntercambioRegistral extends HttpServlet{

	private static Logger _logger = Logger.getLogger(EnviarIntercambioRegistral.class);
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

		//managers a usar
		IntercambioRegistralManager intercambioManager =  IsicresManagerProvider.getInstance().getIntercambioRegistralManager();
		RegistroManager registroManager = IsicresManagerProvider.getInstance().getRegistroManager();


		//parametros a usar
		String idLibro = null;
		String idRegistro = null;
		UnidadTramitacionIntercambioRegistralVO unidadDestino;

		Integer idOficina = null;
		String tipoOrigen = null;
		UsuarioVO usuario = null;
		BaseRegistroVO registro=null;

		try{
			//obtenemos los datos necesarios
			ContextoAplicacionVO contextoAplicacion = ContextoAplicacionUtil.getContextoAplicacion(req);
			usuario = contextoAplicacion.getUsuarioActual();
			idOficina = Integer.parseInt(contextoAplicacion.getOficinaActual().getId());
			registro = contextoAplicacion.getRegistroActual();
			unidadDestino =getUnidadTramitacionIntercambioRegistral(req);
			idLibro= registro.getIdLibro();
			idRegistro= registro.getIdRegistro();
			tipoOrigen = Integer.toString(getTipoLibro(contextoAplicacion.getLibroActual()).getValue());


			//realizamos el intercambio registral
			String id = intercambioManager.enviarIntercambioRegistralSalida(idLibro, idRegistro, idOficina.toString(), usuario.getFullName(), tipoOrigen, unidadDestino);

			req.setAttribute("successMessage", "El registro se ha enviado correctamente. Identificador de intercambio: "+id);
			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/intercambioRegistralResult.jsp");
			rd.forward(req, resp);

		}catch (IntercambioRegistralException irEx) {
			_logger.error("Ha ocurrido un error al enviar el registro.", irEx);
			req.setAttribute("errorMessage", IntercambioRegistralManejarExcepciones.getInstance().manejarExcepcion(irEx, req.getLocale()));
			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/intercambioRegistralResult.jsp");
			rd.forward(req, resp);
		}
		catch (Exception e) {
			_logger.error("Ha ocurrido un error al enviar el registro.", e);
			String error = RBUtil.getInstance(ContextoAplicacionUtil.getCurrentLocale(usuario)).getProperty(com.ieci.tecdoc.isicres.desktopweb.Keys.I18N_ISICRESIR_SEND_ERROR);
			req.setAttribute("errorMessage", error);
			req.setAttribute(com.ieci.tecdoc.isicres.desktopweb.Keys.REQUEST_ERROR, error);

			RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/intercambioRegistralResult.jsp");
			rd.forward(req, resp);
		}
	}



	/**
	 * Obtener unidad de tramitacion destinataria del envio de intercambio registral
	 * @param req
	 * @return
	 */
	protected UnidadTramitacionIntercambioRegistralVO getUnidadTramitacionIntercambioRegistral(HttpServletRequest req){

		UnidadTramitacionIntercambioRegistralVO result = new UnidadTramitacionIntercambioRegistralVO();

		String entityCode = req.getParameter("entityCode");
		String entityName = req.getParameter("entityName");
		String tramunitCode = req.getParameter("tramunitCode");
		String tramunitName = req.getParameter("tramunitName");

		result.setCodeEntity(entityCode);
		result.setCodeTramunit(tramunitCode);
		result.setNameEntity(entityName);
		result.setNameTramunit(tramunitName);

		return result;
	}

	/**
	 * obtenemos el tipo de libro
	 * @param libro
	 * @return
	 */
	protected TipoLibroEnum getTipoLibro(BaseLibroVO libro){
		TipoLibroEnum result = null;

		if (libro instanceof LibroEntradaVO) {
			result = TipoLibroEnum.ENTRADA;
		}

		if (libro instanceof LibroSalidaVO) {
			result = TipoLibroEnum.SALIDA;
		}

		return result;
	}

}
