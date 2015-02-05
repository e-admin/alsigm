package es.ieci.tecdoc.fwktd.csv.web.delegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interfaz del delegate para la gestión de captchas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface CaptchaDelegate {

	/**
	 * Renderiza el captcha.
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 */
	public void renderCaptcha(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * Comprueba si la respuesta del captcha es correcta.
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param answer
	 *            Respuesta del captcha.
	 * @return true si la respuesta es correcta, false en caso contrario.
	 */
	public boolean validarCaptcha(HttpServletRequest request, String answer);

}
