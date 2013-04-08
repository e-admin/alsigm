package util;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import common.Constants;

/**
 * Tag que extiende la funcionalidad del html:messages de Struts permitiendo la
 * visualizacion de mensajes estando estos almacenados bien en el request bien
 * en la sesion
 * 
 */
public class MessagesTag extends TagSupport {

	private static final long serialVersionUID = 3938374953238127883L;

	public static final String KEY_LOCALE = "org.apache.struts.action.LOCALE";

	public final static String KEY_MESSAGES = "org.archigest.sigia.KEY_MESSAGE";

	public final static void saveMessages(HttpServletRequest request,
			ActionMessages messages) {
		request.getSession(true).setAttribute(KEY_MESSAGES, messages);
	}

	public MessagesTag() {
		bundle = null;
		locale = KEY_LOCALE;
		name = KEY_MESSAGES;
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
		ActionMessages messages = null;
		try {
			messages = (ActionMessages) pageContext.getSession().getAttribute(
					KEY_MESSAGES);

			if (messages == null)
				messages = RequestUtils.getActionMessages(pageContext, name);
			else
				pageContext.getSession().removeAttribute(KEY_MESSAGES);

		} catch (JspException e) {
			RequestUtils.saveException(pageContext, e);
			throw e;
		}
		if (messages == null || messages.isEmpty())
			return EVAL_PAGE;
		boolean headerPresent = RequestUtils.present(pageContext, bundle,
				locale, "messages.header");
		boolean footerPresent = RequestUtils.present(pageContext, bundle,
				locale, "messages.footer");
		boolean prefixPresent = RequestUtils.present(pageContext, bundle,
				locale, "messages.prefix");
		boolean suffixPresent = RequestUtils.present(pageContext, bundle,
				locale, "messages.suffix");
		StringBuffer results = new StringBuffer();
		boolean headerDone = false;
		String message = null;
		Map mapElementosMensaje = null;

		try {
			mapElementosMensaje = (Map) pageContext.getRequest().getAttribute(
					Constants.LISTA_ELEMENTOS_MENSAJE_KEY);
			if (mapElementosMensaje == null) {
				mapElementosMensaje = (Map) pageContext.getSession()
						.getAttribute(Constants.LISTA_ELEMENTOS_MENSAJE_KEY);
			}
		} catch (Exception e) {
		}

		for (Iterator reports = property != null ? messages.get(property)
				: messages.get(); reports.hasNext();) {
			ActionMessage report = (ActionMessage) reports.next();
			if (!headerDone) {
				if (headerPresent) {
					message = RequestUtils.message(pageContext, bundle, locale,
							"messages.header");
					results.append(message);
					results.append(lineEnd);
				}
				headerDone = true;
			}
			if (prefixPresent) {
				message = RequestUtils.message(pageContext, bundle, locale,
						"messages.prefix");
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

			if ((mapElementosMensaje != null)
					&& (mapElementosMensaje.size() > 0)) {
				List ltElementos = (List) mapElementosMensaje.get(report
						.getKey());
				if ((ltElementos != null) && (!ltElementos.isEmpty())) {
					results.append("</br>");
					ListIterator it = ltElementos.listIterator();
					while (it.hasNext()) {
						String item = (String) it.next();
						results.append("&nbsp;" + item + "</br>");
						results.append(lineEnd);
					}
					pageContext.getSession().removeAttribute(
							Constants.LISTA_ELEMENTOS_MENSAJE_KEY);
				}
			}

			if (suffixPresent) {
				message = RequestUtils.message(pageContext, bundle, locale,
						"messages.suffix");
				results.append(message);
			}
		}

		if (headerDone && footerPresent) {
			message = RequestUtils.message(pageContext, bundle, locale,
					"messages.footer");
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
		name = "org.apache.struts.action.MESSAGE";
		property = null;
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