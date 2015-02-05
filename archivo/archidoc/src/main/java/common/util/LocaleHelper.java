package common.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import util.ErrorsTag;

/**
 * Utilidad para gestionar el locale de la sesión.
 */
public class LocaleHelper {

	/**
	 * Establece el idioma de la aplicación.
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param locale
	 *            Información del idioma.
	 */
	public static void setLocale(HttpServletRequest request, Locale locale) {
		HttpSession session = request.getSession();

		// Configuración para Struts
		session.setAttribute(ErrorsTag.KEY_LOCALE, locale);

		// Configuración para JSTL
		Config.set(session, Config.FMT_LOCALE, locale);
	}

}
