package util;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import common.Constants;

/**
 * Tag que extiende la funcionalidad del html:errors de Struts permitiendo la
 * visualizacion de errores estando estos almacenados bien en el request bien en
 * la sesion
 * 
 */
public class ErrorsTag extends TagSupport {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final String KEY_LOCALE = "org.apache.struts.action.LOCALE";

	public final static String KEY_ERRORS = "org.archigest.sigia.KEY_ERROR";

	public final static void saveErrors(HttpServletRequest request,
			ActionErrors errors) {
		request.getSession(true).setAttribute(KEY_ERRORS, errors);
	}

	public ErrorsTag() {
		bundle = null;
		locale = KEY_LOCALE;
		name = KEY_ERRORS;
		property = null;
	}

	public String getBundle() {
		return bundle;
	}

	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public int doStartTag() throws JspException {
		ActionErrors errors = null;
		try {
			errors = (ActionErrors) pageContext.getSession().getAttribute(
					KEY_ERRORS);

			if (errors == null)
				errors = RequestUtils.getActionErrors(pageContext, name);
			else
				pageContext.getSession().removeAttribute(KEY_ERRORS);

		} catch (JspException e) {
			RequestUtils.saveException(pageContext, e);
			throw e;
		}
		if (errors == null || errors.isEmpty())
			return EVAL_PAGE;
		boolean headerPresent = RequestUtils.present(pageContext, bundle,
				locale, "errors.header");
		boolean footerPresent = RequestUtils.present(pageContext, bundle,
				locale, "errors.footer");
		boolean prefixPresent = RequestUtils.present(pageContext, bundle,
				locale, "errors.prefix");
		boolean suffixPresent = RequestUtils.present(pageContext, bundle,
				locale, "errors.suffix");
		StringBuffer results = new StringBuffer();
		boolean headerDone = false;
		String message = null;
		Map mapElementosError = null;

		try {
			mapElementosError = (Map) pageContext.getRequest().getAttribute(
					Constants.LISTA_ELEMENTOS_ERROR_KEY);
			if (mapElementosError == null) {
				mapElementosError = (Map) pageContext.getSession()
						.getAttribute(Constants.LISTA_ELEMENTOS_ERROR_KEY);
			}
		} catch (Exception e) {
		}

		for (Iterator reports = property != null ? errors.get(property)
				: errors.get(); reports.hasNext();) {
			ActionError report = (ActionError) reports.next();
			if (!headerDone) {
				if (headerPresent) {
					message = RequestUtils.message(pageContext, bundle, locale,
							"errors.header");
					results.append(message);
					results.append(lineEnd);
				}
				headerDone = true;
			}
			if (prefixPresent) {
				message = RequestUtils.message(pageContext, bundle, locale,
						"errors.prefix");
				results.append(message);
			}
			// si el mensaje no se encuentra en el resource se saca su key
			message = RequestUtils.message(pageContext, bundle, locale,
					report.getKey(), report.getValues());
			if (message != null) {
				results.append(message);
				results.append(lineEnd);
			} else {
				results.append(report.getKey());
			}

			if ((mapElementosError != null) && (mapElementosError.size() > 0)) {
				List ltElementos = (List) mapElementosError
						.get(report.getKey());
				if ((ltElementos != null) && (!ltElementos.isEmpty())) {
					results.append("</br>");
					ListIterator it = ltElementos.listIterator();
					while (it.hasNext()) {
						String item = (String) it.next();
						results.append("&nbsp;" + item + "</br>");
						results.append(lineEnd);
					}
					pageContext.getSession().removeAttribute(
							Constants.LISTA_ELEMENTOS_ERROR_KEY);
				}
			}

			if (suffixPresent) {
				message = RequestUtils.message(pageContext, bundle, locale,
						"errors.suffix");
				results.append(message);
			}
		}

		if (headerDone && footerPresent) {
			message = RequestUtils.message(pageContext, bundle, locale,
					"errors.footer");
			results.append(message);
			results.append(lineEnd);
		}
		ResponseUtils.write(pageContext, results.toString());
		return EVAL_PAGE;
	}

	public void release() {
		super.release();
		bundle = "org.apache.struts.action.MESSAGE";
		locale = KEY_LOCALE;
		name = "org.apache.struts.action.ERROR";
		property = null;
	}

	/**
	 * Permite obtener el texto para añadir a un error a partir de una lista de
	 * cadenas
	 * 
	 * @param ltMessages
	 *            Lista de cadenas
	 * @return texto para añadir a un error a partir de una lista de cadenas
	 */
	public final static String getTextoElementosLista(List ltMessages) {
		StringBuffer results = new StringBuffer();
		if ((ltMessages != null) && (!ltMessages.isEmpty())) {
			results.append("</br>");
			ListIterator it = ltMessages.listIterator();
			while (it.hasNext()) {
				String item = (String) it.next();
				results.append("&nbsp;" + item + "</br>");
				results.append(lineEnd);
			}
		}

		return results.toString();
	}

	protected String bundle;

	protected static Locale defaultLocale = Locale.getDefault();

	protected static String lineEnd = System.getProperty("line.separator");

	protected String locale;

	protected static MessageResources messages = MessageResources
			.getMessageResources("org.apache.struts.taglib.html.LocalStrings");

	protected String name;

	protected String property;

}