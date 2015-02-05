package common.actions;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import common.util.LocaleHelper;
import common.util.StringUtils;

/**
 * Action para el cambio de idioma.
 */
public abstract class LanguageAction extends BaseAction {

	/**
	 * Método para cambiar el idioma de la aplicación.
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
	protected void changeLanguageExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String language = request.getParameter("language");
		String country = request.getParameter("country");
		String variant = request.getParameter("variant");

		if (StringUtils.isNotBlank(language)) {
			if (country == null)
				country = "";

			if (variant == null)
				variant = "";

			// Establecer el locale
			LocaleHelper.setLocale(request, new Locale(language, country,
					variant));
		}

		goLastClientExecuteLogic(mappings, form, request, response);
	}

}