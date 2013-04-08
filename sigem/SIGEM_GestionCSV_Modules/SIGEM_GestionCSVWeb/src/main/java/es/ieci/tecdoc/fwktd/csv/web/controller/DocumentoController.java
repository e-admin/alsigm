package es.ieci.tecdoc.fwktd.csv.web.controller;

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

import es.ieci.tecdoc.fwktd.core.locale.web.filter.LocaleFilterBasicHelper;
import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.fwktd.csv.core.exception.CSVException;
import es.ieci.tecdoc.fwktd.csv.web.delegate.CaptchaDelegate;
import es.ieci.tecdoc.fwktd.csv.web.delegate.DocumentoDelegate;
import es.ieci.tecdoc.fwktd.csv.web.vo.InfoDocumentoVO;

/**
 * Controller para la gestión de documentos.
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class DocumentoController extends MultiActionController {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory.getLogger(DocumentoController.class);

	private static final String INFO_DOCUMENTO_SESSION_KEY = "csv.infoDocumento";

	private static final String CAPTCHA_ANSWER = "captcha_answer";
	private static final String CSV = "csv";
	private static final String INFO_DOCUMENTO = "infoDocumento";
	private static final String MENSAJE_ERROR = "mensajeError";

	private static final String PARAMETRO_ID_ENTIDAD = "idEntidad";
	private static final String PARAMETRO_LISTA_ENTIDADES = "listaEntidades";

	/**
	 * Delegate para documentos.
	 */
	private DocumentoDelegate documentoDelegate = null;

	/**
	 * Delegate para la gestión de captchas.
	 */
	private CaptchaDelegate captchaDelegate = null;

	/**
	 * Nombre de la vista.
	 */
	private String viewName = "documento";

	/**
	 * Nombre de la vista para mostrar error en la descarga.
	 */
	private String downloadErrorViewName = "download.error";

	/**
	 * Indica si se utiliza un captcha para proteger la aplicación.
	 */
	private boolean useCaptcha = true;

	/**
	 * Constructor.
	 */
	public DocumentoController() {
		super();
	}

	public DocumentoDelegate getDocumentoDelegate() {
		return documentoDelegate;
	}

	public void setDocumentoDelegate(DocumentoDelegate documentoDelegate) {
		this.documentoDelegate = documentoDelegate;
	}

	public CaptchaDelegate getCaptchaDelegate() {
		return captchaDelegate;
	}

	public void setCaptchaDelegate(CaptchaDelegate captchaDelegate) {
		this.captchaDelegate = captchaDelegate;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getDownloadErrorViewName() {
		return downloadErrorViewName;
	}

	public void setDownloadErrorViewName(String downloadErrorViewName) {
		this.downloadErrorViewName = downloadErrorViewName;
	}

	public boolean isUseCaptcha() {
		return useCaptcha;
	}

	public void setUseCaptcha(boolean useCaptcha) {
		this.useCaptcha = useCaptcha;
	}

	/**
	 * Muestra el formulario para la búsqueda de documentos.
	 * 
	 * @param request
	 *            HttpServletRequest.
	 * @param response
	 *            HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public ModelAndView doform(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// Eliminar la información del documento en sesión
		HttpSession session = request.getSession();
		session.removeAttribute(INFO_DOCUMENTO_SESSION_KEY);

		List listaEntidades = obtenerListaEntidades();
		ModelAndView mav = new ModelAndView(getViewName());
		if (listaEntidades.size() == 1) {
			Entidad oEntidad = (Entidad) listaEntidades.get(0);
			mav.addObject(PARAMETRO_ID_ENTIDAD, oEntidad.getIdentificador());
		} else {
			mav.addObject(PARAMETRO_LISTA_ENTIDADES, listaEntidades);
		}

		// Mostrar la página con el buscador de documentos
		return mav;
	}

	/**
	 * Buscar el documento a partir de su CSV.
	 * 
	 * @param request
	 *            HttpServletRequest.
	 * @param response
	 *            HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public ModelAndView dosearch(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String csv = WebUtils.findParameterValue(request, CSV);
		String captchaAnswer = WebUtils.findParameterValue(request, CAPTCHA_ANSWER);
		String idEntidad = WebUtils.findParameterValue(request, PARAMETRO_ID_ENTIDAD);
		HttpSession session = request.getSession();
		session.setAttribute(PARAMETRO_ID_ENTIDAD, idEntidad);
		MultiEntityContextHolder.setEntity(idEntidad);

		InfoDocumentoVO infoDocumento = null;
		String mensajeError = null;

		// Eliminar la información del documento en sesión
		request.getSession().removeAttribute(INFO_DOCUMENTO_SESSION_KEY);

		logger.info("CSV: [{}]", csv);
		logger.info("Captcha: [{}]", captchaAnswer);
		logger.info("Entidad: [{}]", idEntidad);

		if (StringUtils.isBlank(csv)) {
			logger.info("El CSV está vacío");
			mensajeError = getMessageSourceAccessor().getMessage("error.csv.empty");
		} else if (isUseCaptcha() && !getCaptchaDelegate().validarCaptcha(request, captchaAnswer)) {
			logger.info("Respuesta del captcha no válida");
			mensajeError = getMessageSourceAccessor().getMessage("error.csv.captcha.invalid");
		} else {

			// Obtener el locale
			Locale locale = LocaleFilterBasicHelper.getCurrentLocale(request);
			logger.info("Locale establecido: [{}]", locale);

			try {

				// Obtener la información del documento
				infoDocumento = getDocumentoDelegate().getInfoDocumento(csv, locale);
				if (infoDocumento == null) {
					logger.info("No se ha encontrado el documento con CSV [{}]", csv);
					mensajeError = getMessageSourceAccessor().getMessage("error.csv.notFound");
				}

				logger.info("InfoDocumento: {}", infoDocumento);

				// Guardar la información del documento en sesión
				request.getSession().setAttribute(INFO_DOCUMENTO_SESSION_KEY, infoDocumento);

			} catch (CSVException e) {
				logger.error("Error inesperado obteniendo la información del CSV [" + csv + "]", e);
				mensajeError = getMessageSourceAccessor().getMessage("error.general",
						new Object[] { e.getLocalizedMessage() });
			} catch (Throwable t) {
				logger.error("Error inesperado obteniendo la información del CSV [" + csv + "]", t);
				mensajeError = getMessageSourceAccessor().getMessage("error.general",
						new Object[] { t.getLocalizedMessage() });
			}
		}

		ModelAndView mav = new ModelAndView(getViewName());
		mav.addObject(CSV, csv);
		mav.addObject(INFO_DOCUMENTO, infoDocumento);
		mav.addObject(MENSAJE_ERROR, mensajeError);

		return mav;
	}

	/**
	 * Descarga el documento.
	 * 
	 * @param request
	 *            HttpServletRequest.
	 * @param response
	 *            HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public ModelAndView dodownload(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String mensajeError = null;
		String idEntidad = (String) request.getSession().getAttribute(PARAMETRO_ID_ENTIDAD);
		MultiEntityContextHolder.setEntity(idEntidad);

		// Obtener la información del documento en sesión
		InfoDocumentoVO infoDocumento = (InfoDocumentoVO) request.getSession().getAttribute(
				INFO_DOCUMENTO_SESSION_KEY);
		logger.info("InfoDocumento: {}", infoDocumento);

		if (infoDocumento == null) {
			logger.info("No se ha encontrado la información del documento en la sesión del usuario");
			mensajeError = getMessageSourceAccessor()
					.getMessage("error.csv.infoDocumento.notFound");
		} else {

			// Comprobar si el documento tiene marca de disponible
			if (!infoDocumento.isDisponible()) {
				logger.info("El documento con CSV [{}] no está disponible", infoDocumento.getCsv());
				mensajeError = getMessageSourceAccessor().getMessage(
						"error.csv.document.notAvailable", new Object[] { infoDocumento.getCsv() });
			} else {

				try {

					// Comprobar si el documento está disponible en la
					// aplicación externa					
					if (!getDocumentoDelegate().existeContenidoDocumento(infoDocumento.getId())) {
						logger.info(
								"El documento con CSV [{}] no está disponible en la aplicación externa",
								infoDocumento.getCsv());
						mensajeError = getMessageSourceAccessor().getMessage(
								"error.csv.document.notAvailable",
								new Object[] { infoDocumento.getCsv() });
					} else {

						logger.info("Descargando el documento con CSV [{}]", infoDocumento.getCsv());

						// Descarga del documento
						response.setContentType(infoDocumento.getTipoMime());
						response.setHeader("Content-Disposition", "attachment; filename=\""
								+ infoDocumento.getNombre() + "\"");

						getDocumentoDelegate().writeDocumento(infoDocumento.getId(),
								response.getOutputStream());

						return null;
					}

				} catch (CSVException e) {
					logger.error("Error inesperado obteniendo la información del CSV ["
							+ infoDocumento.getCsv() + "]", e);
					mensajeError = getMessageSourceAccessor().getMessage("error.general",
							new Object[] { e.getLocalizedMessage() });
				} catch (Throwable t) {
					logger.error("Error inesperado obteniendo la información del CSV ["
							+ infoDocumento.getCsv() + "]", t);
					mensajeError = getMessageSourceAccessor().getMessage("error.general",
							new Object[] { t.getLocalizedMessage() });
				}
			}
		}

		ModelAndView mav = new ModelAndView(getDownloadErrorViewName());
		mav.addObject(CSV, (infoDocumento != null ? infoDocumento.getCsv() : null));
		mav.addObject(INFO_DOCUMENTO, infoDocumento);
		mav.addObject(MENSAJE_ERROR, mensajeError);

		return mav;
	}

	/**
	 * Obtiene la lista de entidades del sistema SIGEM
	 * 
	 * @return List ArrayList de objetos
	 *         eci.tecdoc.sgm.core.services.dto.Entidad
	 */
	private List obtenerListaEntidades() {
		try {
			ServicioEntidades oServicio = LocalizadorServicios.getServicioEntidades();
			List oLista = oServicio.obtenerEntidades();
			return getEntidades(oLista);
		} catch (Exception e) {
			return new ArrayList();
		}
	}

	private List getEntidades(List oLista) {
		if (oLista == null)
			return new ArrayList();

		for (int i = 0; i < oLista.size(); i++) {
			oLista.set(i,
					getEntidad((ieci.tecdoc.sgm.core.services.entidades.Entidad) oLista.get(i)));
		}

		return oLista;
	}

	private Entidad getEntidad(ieci.tecdoc.sgm.core.services.entidades.Entidad oEntidad) {
		if (oEntidad == null)
			return null;

		Entidad poEntidad = new Entidad();

		poEntidad.setIdentificador(oEntidad.getIdentificador());
		poEntidad.setNombre(oEntidad.getNombreLargo());

		return poEntidad;
	}

}
