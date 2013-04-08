package common.actions;

import java.io.File;
import java.util.Locale;

import javax.security.auth.Subject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.AppUserRIImpl;

import common.ConfigConstants;
import common.Constants;
import common.util.StringUtils;

import es.archigest.framework.web.filter.security.common.SecurityGlobals;

/**
 * Action que se ejecuta al entrar en la aplicación.
 */
public abstract class LoginBaseAction extends BaseAction {

	protected static final Logger logger = Logger
			.getLogger(LoginBaseAction.class);

	/**
	 * Realiza el proceso previo a la lógica de la acción.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void preProcess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Reescritura del pre para no ejecutar el del padre
		logger.info("Login PreProcess");
	}

	/**
	 * Realiza el login en la aplicación.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void executeLogic(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);

		// Información de la autenticación del usuario
		Subject subject = (Subject) request.getSession(true).getAttribute(
				SecurityGlobals.SUBJECT);

		Locale locale = null;

		// Obtener la cadena de idioma del subject
		String idioma = AppUserRIImpl.getIdiomaKey(subject);

		// Establecer el idioma a partir del pasado como información del usuario
		if ((idioma != null) && (!"".equals(idioma))) {
			String[] idiomaArray = idioma.split("_");
			if (idiomaArray != null) {
				if (idiomaArray.length == 1)
					locale = new Locale(idiomaArray[0]);
				else if (idiomaArray.length == 2)
					locale = new Locale(idiomaArray[0], idiomaArray[1]);
				else if (idiomaArray.length == 3)
					locale = new Locale(idiomaArray[0], idiomaArray[1],
							idiomaArray[2]);

				request.getSession().setAttribute(Globals.LOCALE_KEY, locale);
			}
		}

		if (session != null) {
			if (locale == null)
				locale = request.getLocale();

			String localeString = ConfigConstants.getInstance()
					.getLocaleDefault();
			if (locale != null) {
				String language = locale.getLanguage();
				String country = locale.getCountry();
				if (StringUtils.isNotEmpty(language)) {
					if (StringUtils.isEmpty(country))
						country = language.toUpperCase();

					ServletContext ctx = request.getSession()
							.getServletContext();
					String path = ctx.getRealPath(Constants.FILE_SEPARATOR
							+ Constants.HELP_FOLDER + Constants.FILE_SEPARATOR);

					path = path + Constants.FILE_SEPARATOR + language;// +Constants.LOCALE_LANGUAGE_COUNTRY_SEPARATOR+country;

					boolean exists = (new File(path)).exists();

					if (exists)
						localeString = language;// +Constants.LOCALE_LANGUAGE_COUNTRY_SEPARATOR+country;
				}
			}

			session.setAttribute(Constants.LOCALEKEY, localeString);
		}

		executeLogin(actionMapping, form, request, response);
	}

	protected abstract void executeLogin(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response);
}