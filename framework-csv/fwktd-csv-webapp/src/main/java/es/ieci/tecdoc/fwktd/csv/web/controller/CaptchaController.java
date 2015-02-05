package es.ieci.tecdoc.fwktd.csv.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import es.ieci.tecdoc.fwktd.csv.web.delegate.CaptchaDelegate;

/**
 * Controller para mostrar captchas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CaptchaController extends AbstractController {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);

	/**
	 * Delegate para la gestión de captchas.
	 */
	private CaptchaDelegate captchaDelegate = null;

	/**
	 * Constructor.
	 */
	public CaptchaController() {
		super();
	}

	public CaptchaDelegate getCaptchaDelegate() {
		return captchaDelegate;
	}

	public void setCaptchaDelegate(CaptchaDelegate captchaDelegate) {
		this.captchaDelegate = captchaDelegate;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("Entrada en {}", this.getClass().getName());

		try {
			getCaptchaDelegate().renderCaptcha(request, response);
		} catch (Throwable t) {
			logger.error("Error al renderizar el captcha", t);
		}

		return null;
	}

}
