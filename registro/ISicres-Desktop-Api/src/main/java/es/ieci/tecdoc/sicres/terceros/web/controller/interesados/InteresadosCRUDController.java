package es.ieci.tecdoc.sicres.terceros.web.controller.interesados;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.ClosureUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.RepresentanteInteresadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.util.InteresadosDecorator;
import es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosDelegate;
import es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosFacade;

/**
 *
 * @author IECISA
 *
 */
public class InteresadosCRUDController extends MultiActionController {

	protected static final String INTERESADOS = "interesados";

	/**
	 *
	 * @param request
	 * @param response
	 * @param session
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelAndView addOrUpdateInteresado(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		addInteresado(request, session);

		return new ModelAndView(
				"forward:/interesado/crud.action?method=listInteresados");
	}

	/**
	 * Metodo que asigna el interesado que se pasa como parametro en la request (ID)
	 * @param request
	 * @param session
	 */
	private void addInteresado(HttpServletRequest request, HttpSession session) {
		ArrayList interesados = (ArrayList) WebUtils
				.getOrCreateSessionAttribute(session, INTERESADOS,
						ArrayList.class);

		String id = WebUtils.findParameterValue(request, "tercero.id");

		TerceroValidadoVO terceroValidadoVO = getTercerosDelegate()
				.retrieve(id);

		InteresadoVO interesado = new InteresadoVO();
		interesado.setId(String.valueOf(interesados.size()));
		interesado.setOrden(interesados.size());
		interesado.setTercero(terceroValidadoVO);

		BaseDireccionVO direccionNotificacion = getDireccionNotificacion(terceroValidadoVO);
		interesado.setDireccionNotificacion(direccionNotificacion);

		// Si no se ha añadido el tercero como interesado se añade
		InteresadoVO inter = null;
		if (null != (inter = (InteresadoVO) CollectionUtils.find(interesados,
				new BeanPropertyValueEqualsPredicate("tercero.id", id)))) {

			interesado.setId(inter.getId());

			// Mantenemos el representante que se hubiera seleccionado
			interesado.setRepresentante(inter.getRepresentante());

			interesados.set(Integer.valueOf(interesado.getId()), interesado);
		} else {
			interesados.add(interesado);
		}

		WebUtils.setSessionAttribute(request, INTERESADOS, interesados);
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param session
	 */
	@SuppressWarnings("rawtypes")
	public ModelAndView deleteInteresado(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ArrayList interesados = (ArrayList) WebUtils
				.getOrCreateSessionAttribute(session, INTERESADOS,
						ArrayList.class);

		String[] ids = ServletRequestUtils.getStringParameters(request,
				"tercero.id");

		if (!ArrayUtils.isEmpty(ids)) {
			List<Predicate> predicates = new ArrayList<Predicate>();
			for (String id : ids) {
				predicates.add(new BeanPropertyValueEqualsPredicate(
						"tercero.id", id));
			}
			Collection interesadosToMaintain = CollectionUtils.selectRejected(
					interesados, PredicateUtils.anyPredicate(predicates));

			WebUtils.setSessionAttribute(request, INTERESADOS,
					interesadosToMaintain);
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("No se elimina ningun interesado porque no se ha recibido ningun identificador");
			}
		}

		return new ModelAndView(
				"forward:/interesado/crud.action?method=listInteresados");
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param session
	 */
	public ModelAndView addOrUpdateRepresentante(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ArrayList interesados = (ArrayList) WebUtils
				.getOrCreateSessionAttribute(session, INTERESADOS,
						ArrayList.class);

		String idTercero = WebUtils.findParameterValue(request, "tercero.id");
		String idRepresentante = WebUtils.findParameterValue(request,
				"representante.id");

		if (StringUtils.equals(idTercero, idRepresentante)) {
			try {
				response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
			} catch (IOException e) {
			}

			return null;
		} else {

			InteresadoVO interesado = (InteresadoVO) CollectionUtils.find(
					interesados, new BeanPropertyValueEqualsPredicate(
							"tercero.id", idTercero));

			TerceroValidadoVO terceroRepresentanteVO = getTercerosDelegate()
					.retrieve(idRepresentante);

			RepresentanteInteresadoVO representante = new RepresentanteInteresadoVO();
			representante.setRepresentante(terceroRepresentanteVO);
			representante
					.setDireccionNotificacion(getDireccionNotificacion(terceroRepresentanteVO));

			interesado.setRepresentante(representante);

			WebUtils.setSessionAttribute(request, INTERESADOS, interesados);

			return new ModelAndView(
					"forward:/interesado/crud.action?method=listInteresados");
		}
	}

	/**
	 * Metodo que borra el representante de los interesados pasados como parametro
	 *
	 * @param request
	 * @param response
	 * @param session
	 */
	public ModelAndView deleteRepresentante(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ArrayList interesados = (ArrayList) WebUtils
				.getOrCreateSessionAttribute(session, INTERESADOS,
						ArrayList.class);

		//Obtenemos los ids de los interesados
		String idInteresadosToResetRepresentante = WebUtils.findParameterValue(request,
				"interesadosToResetRepresentante");

		String[] ids = null;
		if(StringUtils.isNotBlank(idInteresadosToResetRepresentante)){
			ids = idInteresadosToResetRepresentante.split(",");
		}

		if (!ArrayUtils.isEmpty(ids)) {
			List<Predicate> predicates = new ArrayList<Predicate>();
			for (String id : ids) {
				//Añadimos criterio de busqueda
				predicates.add(new BeanPropertyValueEqualsPredicate(
						"tercero.id", id));
			}
			// Buscamos de la coleccion de interesados a los interesados pasados
			// como parametro
			Collection interesadosToResetRepresentante = CollectionUtils
					.select(interesados,
							PredicateUtils.anyPredicate(predicates));

			// Borramos los representantes para los interesados indicados
			CollectionUtils.forAllDo(interesadosToResetRepresentante,
					ClosureUtils.invokerClosure("setRepresentante",
							new Class[] { RepresentanteInteresadoVO.class },
							new Object[] { null }));

		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("No se elimina ningun representante porque no se ha recibido ningun identificador");
			}
		}

		return new ModelAndView(
				"forward:/interesado/crud.action?method=listInteresados");
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	public ModelAndView listInteresados(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ArrayList interesados = (ArrayList) WebUtils
				.getOrCreateSessionAttribute(session, INTERESADOS,
						ArrayList.class);

		ModelAndView mav = new ModelAndView("interesado.list");
		mav.addObject("list", interesados);

		return mav;
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	public ModelAndView refreshDireccionInteresado(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ArrayList interesados = (ArrayList) WebUtils
				.getOrCreateSessionAttribute(session, INTERESADOS,
						ArrayList.class);

		try {
			String idTercero = ServletRequestUtils.getStringParameter(request,
					"tercero.id");
			String idDireccion = ServletRequestUtils.getStringParameter(
					request, "direccion.id");

			InteresadoVO interesado = (InteresadoVO) CollectionUtils.find(
					interesados, new BeanPropertyValueEqualsPredicate(
							"tercero.id", idTercero));

			if (null != interesado) {

				List<BaseDireccionVO> direcciones = getTercerosFacade()
						.getDirecciones(
								(TerceroValidadoVO) interesado.getTercero());
				BaseDireccionVO direccion = (BaseDireccionVO) CollectionUtils
						.find(direcciones,
								new BeanPropertyValueEqualsPredicate("id",
										idDireccion));
				if (null != direccion) {
					interesado.setDireccionNotificacion(direccion);
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("No existe ninguna direccion con identificador "
								+ idDireccion);
					}
				}
			} else {
				if (logger.isDebugEnabled()) {
					StringBuffer sb = new StringBuffer();
					sb.append("No existe ningun interesado con identificador ")
							.append(idTercero);
					sb.append(".No se actualiza la direccion del interesado");
					logger.debug(sb.toString());
				}
			}

		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}

		ModelAndView mav = new ModelAndView("interesado.list");
		mav.addObject("list", interesados);

		return mav;
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	public ModelAndView refreshDireccionRepresentante(
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ArrayList interesados = (ArrayList) WebUtils
				.getOrCreateSessionAttribute(session, INTERESADOS,
						ArrayList.class);
		String idTercero = StringUtils.EMPTY;
		String idDireccion = StringUtils.EMPTY;
		try {
			idTercero = ServletRequestUtils.getStringParameter(request,
					"tercero.id");
			idDireccion = ServletRequestUtils.getStringParameter(request,
					"direccion.id");

			InteresadoVO interesado = (InteresadoVO) CollectionUtils.find(
					interesados, new BeanPropertyValueEqualsPredicate(
							"tercero.id", idTercero));

			if (null != interesado) {

				List<BaseDireccionVO> direcciones = getTercerosFacade()
						.getDirecciones(
								(BaseTerceroVO) interesado.getRepresentante()
										.getRepresentante());
				BaseDireccionVO direccion = (BaseDireccionVO) CollectionUtils
						.find(direcciones,
								new BeanPropertyValueEqualsPredicate("id",
										idDireccion));
				if (null != direccion) {
					interesado.getRepresentante().setDireccionNotificacion(
							direccion);
				} else {
					if (logger.isDebugEnabled()) {
						StringBuffer sb = new StringBuffer();
						sb.append(
								"No se ha encontrado la direccion con identificador ")
								.append(idDireccion);
						sb.append(".No se actualiza la direccion del representante para el interesado con identificador ");
						sb.append(idTercero);
						logger.debug(sb.toString());
					}
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("No existe ningun representante para el interesado con identificador "
							+ idTercero);
				}
			}
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer();
			sb.append("No se ha podido actualizar la direccion del representante del interesado con identificador ");
			sb.append(idTercero);
			logger.error(sb.toString(), e);
		}

		ModelAndView mav = new ModelAndView("interesado.list");
		mav.addObject("list", interesados);

		return mav;
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	public ModelAndView slideUpInteresado(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ArrayList interesados = (ArrayList) WebUtils
				.getOrCreateSessionAttribute(session, INTERESADOS,
						ArrayList.class);
		String idTercero = StringUtils.EMPTY;
		try {
			idTercero = ServletRequestUtils.getStringParameter(request,
					"tercero.id");
			InteresadoVO interesado = (InteresadoVO) CollectionUtils.find(
					interesados, new BeanPropertyValueEqualsPredicate(
							"tercero.id", idTercero));
			if (null != interesado) {
				int newOrden = Math.max(interesado.getOrden() - 1, 0);
				if (newOrden != interesado.getOrden()) {
					interesado.setOrden(newOrden);
					InteresadoVO replacedInteresado = (InteresadoVO) interesados
							.set(interesado.getOrden(), interesado);
					replacedInteresado.setOrden(newOrden + 1);
					interesados.set(replacedInteresado.getOrden(),
							replacedInteresado);
				}
			}
		} catch (ServletRequestBindingException e) {
			logger.error(
					"Error recuperando parametros de entrada para incrementar el orden de un interesado",
					e);
		}

		ModelAndView mav = new ModelAndView("interesado.list");
		mav.addObject("list", interesados);

		return mav;
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	public ModelAndView slideDownInteresado(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ArrayList interesados = (ArrayList) WebUtils
				.getOrCreateSessionAttribute(session, INTERESADOS,
						ArrayList.class);
		String idTercero = StringUtils.EMPTY;
		try {
			idTercero = ServletRequestUtils.getStringParameter(request,
					"tercero.id");
			InteresadoVO interesado = (InteresadoVO) CollectionUtils.find(
					interesados, new BeanPropertyValueEqualsPredicate(
							"tercero.id", idTercero));
			if (null != interesado) {
				int newOrden = Math.min(interesado.getOrden() + 1,
						interesados.size() - 1);
				if (newOrden != interesado.getOrden()) {
					interesado.setOrden(newOrden);
					InteresadoVO replacedInteresado = (InteresadoVO) interesados
							.set(interesado.getOrden(), interesado);
					replacedInteresado.setOrden(newOrden - 1);
					interesados.set(replacedInteresado.getOrden(),
							replacedInteresado);
				}
			}
		} catch (ServletRequestBindingException e) {
			logger.error(
					"Error recuperando parametros de entrada para decrementar el orden de un interesado",
					e);
		}

		ModelAndView mav = new ModelAndView("interesado.list");
		mav.addObject("list", interesados);

		return mav;
	}

	/**
	 * Elimina de la sesion el atributo que mantiene los interesados del
	 * registro.
	 *
	 * @param request
	 * @param response
	 * @param session
	 */
	public ModelAndView flush(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		List<InteresadoVO> interesados = (List<InteresadoVO>) WebUtils
				.getOrCreateSessionAttribute(session, INTERESADOS,
						ArrayList.class);

		String decoratedInteresados = getInteresadosDecorator()
				.interesados2string(interesados);

		session.removeAttribute(INTERESADOS);

		ModelAndView mav = new ModelAndView("interesados.flush");
		mav.addObject(INTERESADOS, decoratedInteresados);
		return mav;
	}

	/**
	 * Metodo que asigna el interesado que llega por la request invocado desde el formulario del registro
	 *
	 * @param request
	 * @param response
	 * @param session
	 *
	 * @return
	 */
	public ModelAndView flushFormRegister(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		//añadimos el interesado
		addInteresado(request, session);
		//retornamos el listado de interesados del registro
		return flush(request, response, session);
	}


	/**
	 *
	 * @param request
	 * @param session
	 */
	protected void setInteresados(HttpServletRequest request,
			HttpSession session) {
		session.removeAttribute(INTERESADOS);

		String interesadosAsCadena;
		try {
			interesadosAsCadena = ServletRequestUtils.getStringParameter(
					request, INTERESADOS);

			List<InteresadoVO> interesados = getInteresadosDecorator()
					.string2interesados(interesadosAsCadena);

			WebUtils.setSessionAttribute(request, INTERESADOS, interesados);
		} catch (ServletRequestBindingException e) {
			StringBuffer sb = new StringBuffer();
			sb.append("No se ha podido cargar el parametro ").append(
					INTERESADOS);
			logger.error(sb.toString(), e);
		}
	}

	/**
	 *
	 * @param terceroValidadoVO
	 * @return
	 */
	private BaseDireccionVO getDireccionNotificacion(
			TerceroValidadoVO terceroValidadoVO) {
		if (null != terceroValidadoVO.getDireccionFisicaPrincipal()) {
			return terceroValidadoVO.getDireccionFisicaPrincipal();
		} else if (null != terceroValidadoVO.getDireccionTelematicaPrincipal()) {
			return terceroValidadoVO.getDireccionTelematicaPrincipal();
		} else
			return null;
	}

	public TercerosFacade getTercerosFacade() {
		return tercerosFacade;
	}

	public void setTercerosFacade(TercerosFacade tercerosFacade) {
		this.tercerosFacade = tercerosFacade;
	}

	public TercerosDelegate getTercerosDelegate() {
		return tercerosDelegate;
	}

	public void setTercerosDelegate(TercerosDelegate tercerosDelegate) {
		this.tercerosDelegate = tercerosDelegate;
	}

	public InteresadosDecorator getInteresadosDecorator() {
		return interesadosDecorator;
	}

	public void setInteresadosDecorator(
			InteresadosDecorator interesadosDecorator) {
		this.interesadosDecorator = interesadosDecorator;
	}

	protected TercerosFacade tercerosFacade;

	protected TercerosDelegate tercerosDelegate;

	protected InteresadosDecorator interesadosDecorator;

	protected static final Logger logger = Logger
			.getLogger(InteresadosCRUDController.class);

}